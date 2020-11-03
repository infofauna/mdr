package ch.cscf.jeci.services.entitymanagers.interfaces;

import ch.cscf.jeci.domain.entities.base.Project;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;

import java.util.List;

/**
 * Created by kansoa on 28.06.2017
 */
public interface ProjectManager {

    List<Project> listFilterByMultiCriteriasAndTranslate(String orderByCol, SortOrder sortOrder, Page page,
                                                         String codeName, String institutionName);

}
