package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroundProtocolMapping is a Querydsl query type for GroundProtocolMapping
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGroundProtocolMapping extends EntityPathBase<GroundProtocolMapping> {

    private static final long serialVersionUID = -358819106L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroundProtocolMapping groundProtocolMapping = new QGroundProtocolMapping("groundProtocolMapping");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final StringPath cellColumnValue = createString("cellColumnValue");

    public final StringPath cellRowValue = createString("cellRowValue");

    public final ListPath<GroundProtocolMapping, QGroundProtocolMapping> children = this.<GroundProtocolMapping, QGroundProtocolMapping>createList("children", GroundProtocolMapping.class, QGroundProtocolMapping.class, PathInits.DIRECT2);

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

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue label;

    public final QGroundProtocolMapping parent;

    public final QMIDATProtocolVersion protocolVersion;

    public final NumberPath<Long> protocolVersionId = createNumber("protocolVersionId", Long.class);

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue type;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QGroundProtocolMapping(String variable) {
        this(GroundProtocolMapping.class, forVariable(variable), INITS);
    }

    public QGroundProtocolMapping(Path<? extends GroundProtocolMapping> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QGroundProtocolMapping(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QGroundProtocolMapping(PathMetadata metadata, PathInits inits) {
        this(GroundProtocolMapping.class, metadata, inits);
    }

    public QGroundProtocolMapping(Class<? extends GroundProtocolMapping> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.label = inits.isInitialized("label") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("label"), inits.get("label")) : null;
        this.parent = inits.isInitialized("parent") ? new QGroundProtocolMapping(forProperty("parent"), inits.get("parent")) : null;
        this.protocolVersion = inits.isInitialized("protocolVersion") ? new QMIDATProtocolVersion(forProperty("protocolVersion"), inits.get("protocolVersion")) : null;
        this.status = _super.status;
        this.type = inits.isInitialized("type") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("type"), inits.get("type")) : null;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

