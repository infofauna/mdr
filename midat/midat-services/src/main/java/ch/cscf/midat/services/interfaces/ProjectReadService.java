package ch.cscf.midat.services.interfaces;

import java.util.List;

/**
 * @author: abdallah kanso
 */
public interface ProjectReadService {

    List<String> findProjectNamesLike(String query);

}
