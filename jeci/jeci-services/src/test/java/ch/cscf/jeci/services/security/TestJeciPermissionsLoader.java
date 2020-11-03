package ch.cscf.jeci.services.security;

import ch.cscf.jeci.services.security.implementation.JSONPermissionsLoader;
import com.google.common.collect.Multimap;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

public class TestJeciPermissionsLoader {

    @Test
    public void testLoadPermissionsByRole() throws Exception {

        JSONPermissionsLoader loader = new JSONPermissionsLoader();
        ReflectionTestUtils.setField(loader, "filePath", "roles-permissions-test.json");

        Multimap<String, String> permissionsByRole = loader.loadPermissionsByRole();
        assertNotNull(permissionsByRole);
        assertThat(permissionsByRole.get("test1"), contains("p1", "p2", "p3"));
        assertThat(permissionsByRole.get("test2"), contains("p4", "p5", "p6"));
    }

    @Test(expected = RuntimeException.class)
    public void testLoadPermissionsInvalidJSON() throws Exception {

        JSONPermissionsLoader loader = new JSONPermissionsLoader();
        ReflectionTestUtils.setField(loader, "filePath", "roles-permissions-test-invalid.json");
        loader.loadPermissionsByRole();
    }

    @Test(expected = IllegalStateException.class)
    public void testLoadPermissionsIncorrectJSONvalues() throws Exception {

        JSONPermissionsLoader loader = new JSONPermissionsLoader();
        ReflectionTestUtils.setField(loader, "filePath", "roles-permissions-test-incorrect.json");
        loader.loadPermissionsByRole();
    }
}