package ch.cscf.jeci.test;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import java.lang.reflect.Field;

/**
 * Utility method for use in unit tests.
 *
 * @author: Pierre Henry
 */
public class TestTools {


    /**
     * The Id field of the AbstractBaseEntity class is private and doesn't have a setter.
     * That is because It is not supposed to be modified directly by the progrmammer, only by JPA.
     *
     * However it can be useful for unit testing to build entity objects with a given id value.
     *
     * This method allows to set the value of the id field on an entity.
     *
     * This method should stay in the test scope. If we think we need it in the main code, something stinks...
     *
     * @param entity
     * @param id
     */
    public static void setEntityId(BaseEntity entity, Long id){

        try{
            Field privateIdField = BaseEntity.class.getDeclaredField("id");
            privateIdField.setAccessible(true);
            privateIdField.set(entity, id);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
