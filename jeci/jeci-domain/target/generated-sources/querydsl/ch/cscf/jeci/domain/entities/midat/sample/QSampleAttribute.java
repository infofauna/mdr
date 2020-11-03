package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSampleAttribute is a Querydsl query type for SampleAttribute
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleAttribute extends EntityPathBase<SampleAttribute> {

    private static final long serialVersionUID = -2082450145L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSampleAttribute sampleAttribute = new QSampleAttribute("sampleAttribute");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue attributeType;

    public final NumberPath<Long> attributeTypeId = createNumber("attributeTypeId", Long.class);

    public final ch.cscf.jeci.domain.entities.base.QPerson author;

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

    //inherited
    public final NumberPath<Long> id;

    public final NumberPath<Long> languageId = createNumber("languageId", Long.class);

    public final QSample parent;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue protocolType;

    public final NumberPath<Long> protocolTypeId = createNumber("protocolTypeId", Long.class);

    public final EnumPath<SampleAttributeSource> sourceType = createEnum("sourceType", SampleAttributeSource.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public final StringPath value = createString("value");

    public QSampleAttribute(String variable) {
        this(SampleAttribute.class, forVariable(variable), INITS);
    }

    public QSampleAttribute(Path<? extends SampleAttribute> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleAttribute(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleAttribute(PathMetadata metadata, PathInits inits) {
        this(SampleAttribute.class, metadata, inits);
    }

    public QSampleAttribute(Class<? extends SampleAttribute> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.attributeType = inits.isInitialized("attributeType") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("attributeType"), inits.get("attributeType")) : null;
        this.author = inits.isInitialized("author") ? new ch.cscf.jeci.domain.entities.base.QPerson(forProperty("author"), inits.get("author")) : null;
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.parent = inits.isInitialized("parent") ? new QSample(forProperty("parent"), inits.get("parent")) : null;
        this.protocolType = inits.isInitialized("protocolType") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("protocolType"), inits.get("protocolType")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

