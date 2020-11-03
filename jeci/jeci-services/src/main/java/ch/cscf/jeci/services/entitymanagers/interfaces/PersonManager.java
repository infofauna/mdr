package ch.cscf.jeci.services.entitymanagers.interfaces;

import ch.cscf.jeci.domain.entities.base.Person;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;

import java.util.List;

/**
 * Created by henryp on 15/09/15.
 */
public interface PersonManager {

    List<Person> listFilterByNamesAndTranslate(String orderByCol, SortOrder sortOrder, Page page, String query);


}
