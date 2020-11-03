package ch.cscf.midat.services.interfaces;

import java.util.List;

/**
 * @author: abdallah kanso
 */
public interface InstitutionReadService {

    List<String> findPrincipalInstitutionNamesLike(String query);

}
