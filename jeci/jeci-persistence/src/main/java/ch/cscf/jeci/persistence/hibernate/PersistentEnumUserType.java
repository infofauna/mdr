package ch.cscf.jeci.persistence.hibernate;

import ch.cscf.jeci.domain.entities.base.PersistentEnum;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author: henryp
 */
public abstract class PersistentEnumUserType<T extends PersistentEnum> implements UserType {

    @Override
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return cached;
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable)value;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    /**
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        char code = rs.getString(names[0]).charAt(0);
        if(rs.wasNull()) {
            return null;
        }
        for(PersistentEnum value : returnedClass().getEnumConstants()) {
            if(code == value.getDbCode()) {
                return value;
            }
        }
        throw new IllegalStateException("The ID value for the enum type " + returnedClass().getSimpleName() + " on the class "+owner.getClass().getSimpleName()+" is not a known ID.");
    }
     **/

    /**
     * AKA  added more protection to cover cases when a taxon is not found instead of getting null pointer exception
     * @param rs
     * @param names
     * @param session
     * @param owner
     * @return
     * @throws HibernateException
     * @throws SQLException
     */

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        if(rs.wasNull()) {
            return null;
        }
        if(rs.getString(names[0]) != null){
            char code = rs.getString(names[0]).charAt(0);
            for(PersistentEnum value : returnedClass().getEnumConstants()) {
                if(code == value.getDbCode()) {
                    return value;
                }
            }
        }else{
            return null;
        }
        throw new IllegalStateException("The ID value for the enum type " + returnedClass().getSimpleName() + " on the class "+owner.getClass().getSimpleName()+" is not a known ID.");
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.INTEGER);
        } else {
            st.setString(index,""+((PersistentEnum)value).getDbCode());
        }
    }

    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }

    @Override
    public abstract Class<T> returnedClass();

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.INTEGER};
    }

}
