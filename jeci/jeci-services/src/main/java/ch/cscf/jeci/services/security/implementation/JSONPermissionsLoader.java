package ch.cscf.jeci.services.security.implementation;

import ch.cscf.jeci.services.security.interfaces.PermissionsLoader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by henryp on 04/03/15.
 */
@Service
public class JSONPermissionsLoader implements PermissionsLoader {

    @Value("${security.permissionsfile}")
    private String filePath;


    /**
     * Loads the JSON file that contains the mapping between roles and string permissions.
     * @return A multimap with the key being the role name and the values being the string permissions for the role.
     * Role names and permissions values are always converted to lower case.
     */
    @Override
    public Multimap<String, String> loadPermissionsByRole(){

        Multimap<String, String> permissionsByRole = HashMultimap.create();
        JsonNode root = loadFile();
        if(! root.isObject()){
            throw new IllegalStateException("Incorrect JSON syntax. The root node must be an object with an array property named roles.");
        }

        JsonNode roles = root.get("roles");
        if(! roles.isArray()){
            throw new IllegalStateException("Incorrect JSON syntax. The root node must be an object with an array property named roles.");
        }

        roles.forEach(role -> {
            if(!role.isObject() || !role.has("name") || !role.has("permissions") || !role.get("permissions").isArray()){
                throw new IllegalStateException("Incorrect JSON syntax. Each role node must contain a name property and a permissions array properties.");
            }
            String roleName = role.get("name").textValue();
            JsonNode permissions = role.get("permissions");
            permissions.forEach(permission -> permissionsByRole.put(roleName, permission.textValue()));
        });

        return permissionsByRole;
    }

    private JsonNode loadFile(){
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);

        try {
            return mapper.readTree(inputStream);
        }catch(Exception e){
            throw new RuntimeException("Problem reading permissions JSON file.", e);
        }
    }
}
