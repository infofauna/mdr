package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleInstitutionDAO;
import ch.cscf.jeci.services.security.interfaces.SessionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: abdallah kanso
 */
@Service
public class InstitutionReadService implements ch.cscf.midat.services.interfaces.InstitutionReadService {

    @Autowired
    private SampleInstitutionDAO sampleInstitutionDAO;


    @Autowired
    private SessionUserService sessionUserService;


    @Override
    @Transactional
    public List<String> findPrincipalInstitutionNamesLike(String query) {
        if (sessionUserService.userHasNationalUserPermissionForMidat(sessionUserService.getSessionUserId())) {
            return sampleInstitutionDAO.findPrincipalInstitutionNamesLike(query, sessionUserService.getSessionUserId());
        } else {
            return sampleInstitutionDAO.findPrincipalInstitutionNamesLikeInGroups(query, sessionUserService.getSessionUserId(), sessionUserService.getSessionUserGroupsIdsForMidat());
        }
    }


}
