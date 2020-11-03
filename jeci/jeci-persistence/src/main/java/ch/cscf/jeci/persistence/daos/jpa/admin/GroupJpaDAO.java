package ch.cscf.jeci.persistence.daos.jpa.admin;

import ch.cscf.jeci.domain.entities.security.Group;
import ch.cscf.jeci.persistence.daos.interfaces.admin.GroupDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author: henryp
 */
@Repository
public class GroupJpaDAO extends GenericJpaEntityDAO<Group> implements GroupDAO {

    @Override
    public Group findByName(String groupName) {
        return getEm().createQuery("select g from Group g where name=:name", Group.class).setParameter("name", groupName).getSingleResult();
    }

    @Override
    public Long getIdOfPublicGroup() {
        return findByName("PUBLIC").getId();
    }

    @Override
    public List<Group> findAllGroupsExceptPublic(){
        return getEm().createQuery("select g from Group g where name not in ('PUBLIC') order by g.name", Group.class).getResultList();
    }

}
