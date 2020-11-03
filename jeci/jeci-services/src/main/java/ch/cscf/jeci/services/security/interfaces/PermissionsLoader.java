package ch.cscf.jeci.services.security.interfaces;

import com.google.common.collect.Multimap;

/**
 * Created by henryp on 17/03/15.
 */
public interface PermissionsLoader {
    Multimap<String, String> loadPermissionsByRole();
}
