package ch.cscf.jeci.domain.entities.base;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPerson is a Querydsl query type for Person
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPerson extends EntityPathBase<Person> {

    private static final long serialVersionUID = -865016108L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPerson person = new QPerson("person");

    public final QBaseEntity _super;

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue country;

    public final NumberPath<Long> countryId = createNumber("countryId", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> creationDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser creationUser;

    public final DateTimePath<java.util.Date> dateOfBirth = createDateTime("dateOfBirth", java.util.Date.class);

    //inherited
    public final DateTimePath<java.util.Date> dbCreationDate;

    //inherited
    public final StringPath dbCreationUser;

    //inherited
    public final DateTimePath<java.util.Date> dbUpdateDate;

    //inherited
    public final StringPath dbUpdateUser;

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue gender;

    public final NumberPath<Long> genderId = createNumber("genderId", Long.class);

    //inherited
    public final NumberPath<Long> id;

    public final ch.cscf.jeci.domain.entities.thesaurus.QLanguage language;

    public final NumberPath<Long> languageId = createNumber("languageId", Long.class);

    public final StringPath lastName = createString("lastName");

    public final StringPath locality = createString("locality");

    public final StringPath mobilePhone = createString("mobilePhone");

    public final StringPath privatePhone = createString("privatePhone");

    public final StringPath proPhone = createString("proPhone");

    //inherited
    public final EnumPath<EntityStatus> status;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue title;

    public final NumberPath<Long> titleId = createNumber("titleId", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public final StringPath zipCode = createString("zipCode");

    public QPerson(String variable) {
        this(Person.class, forVariable(variable), INITS);
    }

    public QPerson(Path<? extends Person> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPerson(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPerson(PathMetadata metadata, PathInits inits) {
        this(Person.class, metadata, inits);
    }

    public QPerson(Class<? extends Person> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QBaseEntity(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("country"), inits.get("country")) : null;
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.gender = inits.isInitialized("gender") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("gender"), inits.get("gender")) : null;
        this.id = _super.id;
        this.language = inits.isInitialized("language") ? new ch.cscf.jeci.domain.entities.thesaurus.QLanguage(forProperty("language"), inits.get("language")) : null;
        this.status = _super.status;
        this.title = inits.isInitialized("title") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("title"), inits.get("title")) : null;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

