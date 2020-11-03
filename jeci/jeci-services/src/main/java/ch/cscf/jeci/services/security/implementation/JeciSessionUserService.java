package ch.cscf.jeci.services.security.implementation;

import ch.cscf.jeci.domain.entities.security.RoleGroup;
import ch.cscf.jeci.domain.entities.security.User;
import ch.cscf.jeci.persistence.daos.interfaces.admin.UserDAO;
import ch.cscf.jeci.services.security.interfaces.SessionUserService;
import ch.unine.sitel.o2m.Mapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by henryp on 09/03/15.
 */
@Service
public class JeciSessionUserService implements SessionUserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    Mapper mapper;

    @Autowired
    JeciRealm realm;

    @Transactional
    public Map<String,Object> getSessionUserWithMappedFields(String... fields){

        Subject subject = SecurityUtils.getSubject();
        User user = userDAO.findById(((User) subject.getPrincipal()).getId());

        Map<String, Object> userMap = mapper.map(user, Arrays.asList(fields));

        Collection<String> permissions = realm.doGetAuthorizationInfo(subject.getPrincipals()).getStringPermissions();
        userMap.put("permissions", permissions);

        return userMap;
    }

    public Long getSessionUserId(){
        return ((User)SecurityUtils.getSubject().getPrincipal()).getId();
    }

    @Override
    public String getSessionUserName() {
        return ((User)SecurityUtils.getSubject().getPrincipal()).getUsername();
    }

    @Transactional
    public User getSessionUser(){
        return userDAO.findById(getSessionUserId());
    }


    @Transactional
    public Set<String> getSessionUserRolesNamesForMidat(){
        return getSessionUser().getRoleGroups().stream()
                .filter(roleGroup-> roleGroup.getGroup() == null &&
                                roleGroup.getRole().getApplication().getCode().equalsIgnoreCase(realm.getApplicationName())
                        ).collect(Collectors.toSet()).stream()
                .map(roleGroup -> roleGroup.getRole().getName().toLowerCase()).collect(Collectors.toSet());
    }

    @Override
    public boolean isSessionUserPermitted(String... permissions) {
        return SecurityUtils.getSubject().isPermittedAll(permissions);
    }

    @Override
    public Set<Long> getSessionUserGroupsIdsForMidat() {
        return getSessionUser().getRoleGroups().stream()
                .filter(roleGroup-> roleGroup.getGroup() != null &&
                        roleGroup.getRole().getApplication().getCode().equalsIgnoreCase(realm.getApplicationName())).collect(Collectors.toSet()).stream()
                .map(roleGroup -> roleGroup.getGroup().getId()).collect(Collectors.toSet());
    }


    public boolean userHasNationalUserPermissionForMidat(Long userId)  {
        return realm.userHasNationalUserPermission(userId, realm.getApplicationName());
    }

    public boolean userHasNationalContributorPermissionForMidat(Long userId)  {
        return realm.userHasNationalContributorPermission(userId,realm.getApplicationName());
    }

    public boolean userHasReadPermissionOnThisSampleForMidat(Long userId, Set<Long> sampleGroupIds) {
        return realm.userHasReadPermissionOnThisSample(userId,sampleGroupIds,realm.getApplicationName());
    }

    public boolean userHasReadWritePermissionOnThisSampleForMidat(Long userId, Set<Long> sampleGroupIds)  {
        return realm.userHasReadWritePermissionOnThisSample(userId,sampleGroupIds,realm.getApplicationName());
    }



}
