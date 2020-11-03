package ch.cscf.jeci.persistence.daos.interfaces.infofauna;

import ch.cscf.jeci.domain.entities.base.Person;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: henryp
 */
public interface PersonDAO extends GenericEntityDAO<Person> {

    List<Person> listAndFilterByNames(String orderByCol, SortOrder sortOrder, Page page, String query);

}
