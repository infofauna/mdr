package ch.cscf.jeci.services.entitymanagers.implementations;

import ch.cscf.jeci.domain.entities.base.Person;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.PersonDAO;
import ch.cscf.jeci.services.entitymanagers.interfaces.PersonManager;
import ch.cscf.jeci.services.general.EntityFieldTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by henryp on 15/09/15.
 */
@Service
public class PersonManagerImpl implements PersonManager {

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private EntityFieldTranslatorService translatorService;

    @Override
    @Transactional
    public List<Person> listFilterByNamesAndTranslate(String orderByCol, SortOrder sortOrder, Page page, String query) {
        List<Person> persons = personDAO.listAndFilterByNames(orderByCol, sortOrder, page, query);
        translatorService.fillLocalizedFields(persons);
        return persons;
    }
}
