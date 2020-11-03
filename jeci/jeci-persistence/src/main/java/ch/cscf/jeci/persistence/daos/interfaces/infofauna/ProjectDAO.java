package ch.cscf.jeci.persistence.daos.interfaces.infofauna;

import ch.cscf.jeci.domain.entities.base.Project;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: henryp
 */
public interface ProjectDAO extends GenericEntityDAO<Project> {

    List<Project> listFilterByMultiCriterias(String orderByCol, SortOrder sortOrder, Page page,
                                             String codeName, String institutionName);
}
