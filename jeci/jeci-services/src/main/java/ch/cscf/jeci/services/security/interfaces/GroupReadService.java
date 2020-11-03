package ch.cscf.jeci.services.security.interfaces;

import ch.cscf.jeci.domain.entities.security.Group;

import java.util.List;

/**
 * Created by henryp on 06/03/15.
 */
public interface GroupReadService {

    List<Group> listOrderByNameAndTranslate();

    List<Group> getGroupsAuthorizedForUserForSampleSearch();
}
