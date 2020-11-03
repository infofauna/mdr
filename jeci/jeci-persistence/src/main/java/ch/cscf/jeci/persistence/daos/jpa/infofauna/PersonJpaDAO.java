package ch.cscf.jeci.persistence.daos.jpa.infofauna;

import ch.cscf.jeci.domain.entities.base.EntityStatus;
import ch.cscf.jeci.domain.entities.base.Person;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.PersonDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: henryp
 */
@Repository
public class PersonJpaDAO extends GenericJpaEntityDAO<Person> implements PersonDAO {


    @Override
    public List<Person> listAndFilterByNames(String orderByCol, SortOrder sortOrder, Page page, String query) {

        query = prepareStringForLikeSearch(query);

        List<Predicate> predicates = new ArrayList<>();
        CriteriaBuilder builder = getEm().getCriteriaBuilder();
        CriteriaQuery criteria =  builder.createQuery(Person.class);
        Root<Person> root = criteria.from(Person.class);
        root.alias(ROOT_ALIAS);


        Expression<Boolean> statusPath = root.get("status");
        predicates.add(builder.and(builder.equal(statusPath, EntityStatus.ACTIVE)));

        Expression<String> firstNamePath = root.get("firstName");
        Expression<String> lowerFirstNamePath = builder.lower(firstNamePath);
        Expression<String> lastNamePath = root.get("lastName");
        Expression<String> lowerLastNamePath = builder.lower(lastNamePath);
        predicates.add(builder.or(builder.like(lowerFirstNamePath, query), builder.like(lowerLastNamePath, query)));

        return super.listImpl(orderByCol, sortOrder, page, predicates, null);
    }
}
