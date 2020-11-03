package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleProjectDAO;
import ch.cscf.jeci.services.security.interfaces.SessionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: abdallah kanso
 */

@Service
public class ProjectReadService implements ch.cscf.midat.services.interfaces.ProjectReadService {

    @Autowired
    private SampleProjectDAO sampleProjectDAO;


    @Autowired
    private SessionUserService sessionUserService;


    @Override
    @Transactional
    public List<String> findProjectNamesLike(String query) {
        if (sessionUserService.userHasNationalUserPermissionForMidat(sessionUserService.getSessionUserId())) {
            return sampleProjectDAO.findProjectNamesLike(query, sessionUserService.getSessionUserId());
        } else {
            return sampleProjectDAO.findProjectNamesLikeInGroups(query, sessionUserService.getSessionUserId(), sessionUserService.getSessionUserGroupsIdsForMidat());
        }
    }


}

