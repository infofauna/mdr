package ch.cscf.jeci.services.security.interfaces;

import ch.cscf.jeci.domain.entities.security.User;

import java.util.Map;
import java.util.Set;

/**
 * Created by henryp on 17/03/15.
 */
public interface SessionUserService {

    Map<String,Object> getSessionUserWithMappedFields(String... fields);

    Long getSessionUserId();

    String getSessionUserName();

    User getSessionUser();

    Set<String> getSessionUserRolesNamesForMidat();

    Set<Long> getSessionUserGroupsIdsForMidat();

    boolean isSessionUserPermitted(String... permissions);

    boolean userHasNationalUserPermissionForMidat(Long userId);
    boolean userHasNationalContributorPermissionForMidat(Long userId);
    boolean userHasReadPermissionOnThisSampleForMidat(Long userId, Set<Long> sampleGroupIds);
    boolean userHasReadWritePermissionOnThisSampleForMidat(Long userId, Set<Long> sampleGroupIds);




}
