package ch.cscf.jeci.persistence.hibernate;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * @author: henryp
 */
public class JeciNamingStrategy extends ImprovedNamingStrategy {


    @Override
    public String columnName(String columnName) {
        return super.columnName(columnName);
    }
}
