package ch.cscf.jeci.services.security.implementation;

import ch.cscf.jeci.domain.entities.security.Group;
import ch.cscf.jeci.domain.entities.security.RoleGroup;
import ch.cscf.jeci.domain.entities.security.User;
import ch.cscf.jeci.persistence.daos.interfaces.admin.GroupDAO;
import ch.cscf.jeci.services.general.EntityFieldTranslatorService;
import ch.cscf.jeci.services.security.interfaces.SessionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by henryp on 06/03/15.
 */
@Service
public class GroupReadServiceImpl implements ch.cscf.jeci.services.security.interfaces.GroupReadService {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    SessionUserService sessionUserService;

    @Autowired
    private EntityFieldTranslatorService translatorService;

    @Override
    @Transactional
    public List<Group> listOrderByNameAndTranslate() {
        List<Group> groups = groupDAO.list("name");
        translatorService.fillLocalizedFields(groups);
        return groups;
    }

    @Override
    @Transactional
    public List<Group> getGroupsAuthorizedForUserForSampleSearch() {

        if(sessionUserService.userHasNationalUserPermissionForMidat(sessionUserService.getSessionUserId())){
            return listOrderByNameAndTranslate();
        }else{
            List<Group> groups = new ArrayList<>();
            User user = sessionUserService.getSessionUser();
            List<RoleGroup> userRoleGroups = user.getRoleGroups();

            List<Group> userGroups = userRoleGroups.stream().filter(rg -> rg.getGroup() != null &&
                    rg.getRole().getApplication().getCode().equalsIgnoreCase("midat")).collect(Collectors.toList()).stream().map(
                    rg -> rg.getGroup()).collect(Collectors.toList()
            );

            groups.addAll(userGroups);
            groups.sort((Group g1, Group g2) -> g1.getName().compareTo(g2.getName()));
            translatorService.fillLocalizedFields(groups);
            return groups;
        }
    }
}
