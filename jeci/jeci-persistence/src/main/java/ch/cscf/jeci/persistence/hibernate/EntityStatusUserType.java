package ch.cscf.jeci.persistence.hibernate;

import ch.cscf.jeci.domain.entities.base.EntityStatus;

/**
 * @author: henryp
 */
public class EntityStatusUserType extends PersistentEnumUserType<EntityStatus>{

    @Override
    public Class<EntityStatus> returnedClass() {
        return EntityStatus.class;
    }
}
