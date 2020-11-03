package ch.cscf.jeci.persistence.hibernate;

import ch.cscf.jeci.domain.entities.midat.sample.SampleAttributeSource;

/**
 * @author: henryp
 */
public class AuthorshipSourceUserType extends PersistentEnumUserType<SampleAttributeSource>{

    @Override
    public Class<SampleAttributeSource> returnedClass() {
        return SampleAttributeSource.class;
    }
}
