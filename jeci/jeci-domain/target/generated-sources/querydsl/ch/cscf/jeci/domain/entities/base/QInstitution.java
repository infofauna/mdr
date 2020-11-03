package ch.cscf.jeci.domain.entities.base;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInstitution is a Querydsl query type for Institution
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInstitution extends EntityPathBase<Institution> {

    private static final long serialVersionUID = -1310019175L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInstitution institution = new QInstitution("institution");

    public final QBaseEntity _super;

    public final StringPath acronym = createString("acronym");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final BooleanPath arma = createBoolean("arma");

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue country;

    public final NumberPath<Long> countryId = createNumber("countryId", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> creationDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser creationUser;

    //inherited
    public final DateTimePath<java.util.Date> dbCreationDate;

    //inherited
    public final StringPath dbCreationUser;

    //inherited
    public final DateTimePath<java.util.Date> dbUpdateDate;

    //inherited
    public final StringPath dbUpdateUser;

    public final StringPath fax = createString("fax");

    //inherited
    public final NumberPath<Long> id;

    public final StringPath locality = createString("locality");

    public final StringPath name = createString("name");

    public final QPerson personInCharge;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue personInChargeFunction;

    public final NumberPath<Long> personInChargeFunctionId = createNumber("personInChargeFunctionId", Long.class);

    public final NumberPath<Long> personInChargeId = createNumber("personInChargeId", Long.class);

    public final StringPath phone = createString("phone");

    public final ListPath<ch.cscf.jeci.domain.entities.midat.sample.Sample, ch.cscf.jeci.domain.entities.midat.sample.QSample> samples = this.<ch.cscf.jeci.domain.entities.midat.sample.Sample, ch.cscf.jeci.domain.entities.midat.sample.QSample>createList("samples", ch.cscf.jeci.domain.entities.midat.sample.Sample.class, ch.cscf.jeci.domain.entities.midat.sample.QSample.class, PathInits.DIRECT2);

    //inherited
    public final EnumPath<EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public final StringPath url = createString("url");

    public final StringPath zipCode = createString("zipCode");

    public QInstitution(String variable) {
        this(Institution.class, forVariable(variable), INITS);
    }

    public QInstitution(Path<? extends Institution> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInstitution(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInstitution(PathMetadata metadata, PathInits inits) {
        this(Institution.class, metadata, inits);
    }

    public QInstitution(Class<? extends Institution> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QBaseEntity(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("country"), inits.get("country")) : null;
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.personInCharge = inits.isInitialized("personInCharge") ? new QPerson(forProperty("personInCharge"), inits.get("personInCharge")) : null;
        this.personInChargeFunction = inits.isInitialized("personInChargeFunction") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("personInChargeFunction"), inits.get("personInChargeFunction")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

