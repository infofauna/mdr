package ch.cscf.jeci.services.security.implementation;

import ch.cscf.jeci.domain.entities.security.Group;
import ch.cscf.jeci.domain.entities.security.Role;
import ch.cscf.jeci.domain.entities.security.RoleGroup;
import ch.cscf.jeci.domain.entities.security.User;
import ch.cscf.jeci.domain.entities.utility.SetUtils;
import ch.cscf.jeci.persistence.daos.interfaces.admin.*;
import ch.cscf.jeci.persistence.storedprocedures.StoredProcedureExecutionException;
import ch.cscf.jeci.services.security.interfaces.PermissionsLoader;
import ch.cscf.jeci.services.security.interfaces.SSOService;
import ch.cscf.jeci.services.security.interfaces.SessionUserService;
import ch.cscf.jeci.utils.EncryptionUtil;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: henryp
 */
public class JeciRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Long applicationId;

    private String applicationName;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GroupDAO groupRepository;

    @Autowired
    private SecurityDAO securityDAO;

    @Autowired
    private JeciApplicationDAO applicationDAO;

    @Autowired
    private SSOService ssoService;

    @Autowired
    SessionUserService sessionUserService;

    @Autowired
    private TransactionTemplate txTemplate;

    @Autowired
    private PermissionsLoader permissionsLoader;

    public JeciRealm(Long applicationId, String applicationName) {
        setName("JeciRealm for "+applicationName);
        this.applicationId=applicationId;
    }

    @PostConstruct
    public void initBean(){
        this.applicationName=applicationDAO.findById(applicationId).getCode().toLowerCase();
    }

    @Override
    @Autowired
    public void setCacheManager(CacheManager cacheManager) {
        super.setCacheManager(cacheManager);
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if(super.supports(token)) return true;
        return token instanceof JeciSsoAuthenticationToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        final User userDetached =  (User)principals.fromRealm(getName()).iterator().next();
        final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        final Multimap<String,String> permissionsByRole = permissionsLoader.loadPermissionsByRole();

        logger.info("Loading authorization data for user "+userDetached.getUsername());

        txTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                //User user = userDAO.findById(userDetached.getId(), "roles", "groups");

                User user = userDAO.findById(userDetached.getId());
                /**

                 Set<String> userPermissions = new HashSet<>();

                //add real roles and permissions
                for(Role role : user.getRolesForApp(applicationId)){
                    String appRoleName = applicationName + ":" + role.getName().toLowerCase();
                    logger.info("Adding role : " + appRoleName);
                    info.addRole(role.getName());
                    Collection<String> rolePermissions = permissionsByRole.get(appRoleName);
                    logger.info("Adding permissions : "+rolePermissions);
                    info.addStringPermissions(rolePermissions);
                    userPermissions.addAll(rolePermissions);
                }
                 **/

                List<RoleGroup> roleGroups =user.getRoleGroups();
                List<String> collectedPermissions = roleGroups.stream()
                        .map(roleGroup -> {
                            String key = getAppCodeRoleName(roleGroup);
                            return permissionsByRole.get(key);
                        })
                        .flatMap(permissions -> permissions.stream())
                        .distinct()
                        .collect(Collectors.toList());

                addSamplePersmissions(user, collectedPermissions, "midat");
                addSamplePersmissions(user, collectedPermissions, "mds");

                Set<String> userPermissions = new HashSet<String>(collectedPermissions);
                info.setStringPermissions(userPermissions);
            }
        });

        return info;

    }

    /**
     * complet adding the smaple permissions
     * @param user
     * @param collectedPermissions
     * @param appCode
     */
    private void addSamplePersmissions(User user, List<String> collectedPermissions, String appCode) {
        if(isUserNationalContributor(user,appCode)|| isUserHasGroupWithWriteAccess(user,appCode)){
            collectedPermissions.add(appCode.toLowerCase() + ":samples:create");
            collectedPermissions.add(appCode.toLowerCase() + ":samples:update");
            collectedPermissions.add(appCode.toLowerCase() + ":samples:delete");
        }
    }



    private String getAppCodeRoleName(RoleGroup roleGroup) {
        return (roleGroup.getRole().getApplication().getCode()+ ":" + roleGroup.getRole().getName()).toLowerCase();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        User user;

        if(authenticationToken instanceof JeciSsoAuthenticationToken){
            JeciSsoAuthenticationToken ssoToken = (JeciSsoAuthenticationToken) authenticationToken;

            String ssoSessionId = ssoToken.getSsoSessionId();
            user = userDAO.findForSso(ssoSessionId, ssoToken.getHost());
            if(user != null){
                ssoService.incrementSSOTokenForUser(user.getId());
            }else{
                String msg = "No SSO session found for session ID = " + ssoSessionId;
                logger.info(msg);
                throw new AuthenticationException(msg);
            }

        }else{

            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

            logger.info("authenticating user '{}' with password '**********'...", token.getUsername());

            String password = new String(token.getPassword());
            String passwordHash = EncryptionUtil.getHash(password);
            String languageCode = LocaleContextHolder.getLocale().getLanguage();
            String currentHttpSessionId = ssoService.getCurrentHttpSessionId();
            String currentClientIp = ssoService.getCurrentClientIp();

            Map<String, Object> loginResult;

            try{
                loginResult = securityDAO.authenticate(token.getUsername(), password, passwordHash, languageCode, currentClientIp, applicationId, currentHttpSessionId);
            }catch(StoredProcedureExecutionException e){
                throw new AuthenticationException(e.getMessage(), e);
            }

            user = txTemplate.execute((TransactionStatus status) -> {
                Long userId = (Long)loginResult.get(SecurityDAO.OUT_USER_ID);
               // User userInCallbackScope = userDAO.findById(userId, "roleGroups");

                User userInCallbackScope = userDAO.findById(userId);

                if(!checkIfUserHasLoginPermissionForApp(userInCallbackScope)){
                    String message = MessageFormat.format("The user \"{0}\" was authenticated but does not have permission to access this app. The :login permission ir required to authenticate.", userInCallbackScope.getUsername());
                    logger.warn(message);
                    throw new AuthenticationException(message);
                }
                return userInCallbackScope;
            });
        }

        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }

    private boolean checkIfUserHasLoginPermissionForApp(User user){

        final Multimap<String, String> permsByRoles = permissionsLoader.loadPermissionsByRole();
        return user.getRoleGroups().stream()
                .map(roleGroup -> roleGroup.getRole().getApplication().getCode().toLowerCase() + ":" + roleGroup.getRole().getName().toLowerCase())
                .flatMap(roleName -> permsByRoles.get(roleName).stream())
                .anyMatch(perm -> perm.equals(this.applicationName.toLowerCase()+":login"));


          }
    /**
     * checks if for a given userId has the a valid token at this moment of check and he has national user
     * permissions for a given applicatoion
     * @param userId
     * @param appCode
     * @return
     */
    protected boolean userHasNationalUserPermission(Long userId, String appCode)  {

        boolean check= false;
        User user = userDAO.findById(userId);
        validateUserLoginAccess(user);

        if (user != null) {

            check = isUserNational(user, appCode);

        }
        return check;
    }

    /**
     * check for user if he has the national user role for a given  application
     * @param user
     * @param appCode
     * @return
     */
    protected boolean isUserNational(User user, String appCode) {
        boolean check;
        final Set<Long> userGroupIds = getUserGroupIdsWithReadAccessAsSetofLong(user, appCode);

        List<Group> groups= groupRepository.findAllGroupsExceptPublic();
        final Set<Long> groupIds =groups.stream().map(rg -> rg.getId()).collect(Collectors.toSet());

        check = SetUtils.equals(userGroupIds, groupIds);
        return check;
    }


    /**
     * checks if for a given userId has the a valid token at this moment of check and he has national contributor
     * permissions for a given applicatoion
     * @param userId
     * @param appCode
     * @return
     */
    protected boolean userHasNationalContributorPermission(Long userId, String appCode) {

        boolean check= false;
        User user = userDAO.findById(userId);
        //validateUserLoginAccess(user);

        if (user != null) {
            check = isUserNationalContributor(user,appCode);

        }
        return check;
    }

    /**
     * check for user if he has the national contributor role for a given  application
     * @param user
     * @param appCode
     * @return
     */
    protected boolean isUserNationalContributor(User user, String appCode) {
        boolean check;
        final Set<Long> userGroupIds = getUserGroupIdsWithWriteAccessAsSetofLong(user, appCode);

        List<Group> groups= groupRepository.findAllGroupsExceptPublic();
        final Set<Long> groupIds =groups.stream().map(rg -> rg.getId()).collect(Collectors.toSet());

        check = SetUtils.equals(userGroupIds, groupIds);
        return check;
    }

    /**
     * check if the current user has at least a group with write access in his group list
     * @param user
     * @param appCode
     * @return
     */
    protected boolean isUserHasGroupWithWriteAccess(User user, String appCode) {
        boolean check;
        final Set<Long> userGroupIds = getUserGroupIdsWithWriteAccessAsSetofLong(user, appCode);

        check = !userGroupIds.isEmpty();
        return check;
    }

    private Set<Long> getUserGroupIdsWithReadAccessAsSetofLong(User user, String appCode) {
        List<RoleGroup> userGroups = user.getRoleGroups().stream().filter(rg ->
                rg.getGroup() != null && rg.getRole().getApplication().getCode().equalsIgnoreCase(appCode)).collect(Collectors.toList());
        return userGroups.stream().map(rg -> rg.getGroup().getId()).collect(Collectors.toSet());
    }

    private Set<Long> getUserGroupIdsWithWriteAccessAsSetofLong(User user, String appCode) {
        List<RoleGroup> userGroups = user.getRoleGroups().stream().filter(rg ->
                rg.getGroup() != null && rg.getRole().getApplication().getCode().equalsIgnoreCase(appCode)
                        && rg.getWritable()).collect(Collectors.toList());
        return userGroups.stream().map(rg -> rg.getGroup().getId()).collect(Collectors.toSet());
    }


    private Set<String> getUserGroupNamesWithWriteAccessAsSetofString(User user, String appCode) {
        List<RoleGroup> userGroups = user.getRoleGroups().stream().filter(rg ->
                rg.getGroup() != null && rg.getRole().getApplication().getCode().equalsIgnoreCase(appCode)
                        && rg.getWritable()).collect(Collectors.toList());
        return userGroups.stream().map(rg -> appCode+":"+rg.getGroup().getName()).collect(Collectors.toSet());
    }

    /**
     * check if user has Read Permission On This Sample
     * @param userId
     * @param sampleGroupIds
     * @param appCode
     * @return
     */
    protected boolean userHasReadPermissionOnThisSample(Long userId, Set<Long> sampleGroupIds, String appCode){

        boolean check= false;
        User user = userDAO.findById(userId);
        validateUserLoginAccess(user);

        if (user != null) {
            final Set<Long> userGroupIds = getUserGroupIdsWithReadAccessAsSetofLong(user, appCode);
            Set<Long> intersection = new HashSet(Sets.intersection(userGroupIds, sampleGroupIds));
            check = !intersection.isEmpty();
        }
        return check;
    }

    /**
     * check if user has write Permission On This Sample
     * @param userId
     * @param sampleGroupIds
     * @param appCode
     * @return
     */
    protected boolean userHasReadWritePermissionOnThisSample(Long userId, Set<Long> sampleGroupIds, String appCode)   {

        boolean check= false;
        User user = userDAO.findById(userId);
        validateUserLoginAccess(user);

        if (user != null) {
            final Set<Long> userGroupIds = getUserGroupIdsWithWriteAccessAsSetofLong(user, appCode);
            Set<Long> intersection = new HashSet(Sets.intersection(userGroupIds, sampleGroupIds));
            check = !intersection.isEmpty();
        }
        return check;
    }

    private  boolean validateUserLoginAccess(User user)  {

        if(checkIfUserHasLoginPermissionForApp(user)){
            return true;
        }else {
            throw new AuthenticationException("Force expiration user session on "+getApplicationName()+" , permissions have been changed ");
        }
    }

    public String getApplicationName() {
        return applicationName;
    }
}
