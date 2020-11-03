package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroundProtocolItem is a Querydsl query type for GroundProtocolItem
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGroundProtocolItem extends EntityPathBase<GroundProtocolItem> {

    private static final long serialVersionUID = 431387395L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroundProtocolItem groundProtocolItem = new QGroundProtocolItem("groundProtocolItem");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

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

    public final ch.cscf.jeci.domain.entities.midat.sample.QSample parentSample;

    public final QGroundProtocolMapping sourceMapping;

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public final StringPath value = createString("value");

    public QGroundProtocolItem(String variable) {
        this(GroundProtocolItem.class, forVariable(variable), INITS);
    }

    public QGroundProtocolItem(Path<? extends GroundProtocolItem> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QGroundProtocolItem(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QGroundProtocolItem(PathMetadata metadata, PathInits inits) {
        this(GroundProtocolItem.class, metadata, inits);
    }

    public QGroundProtocolItem(Class<? extends GroundProtocolItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.parentSample = inits.isInitialized("parentSample") ? new ch.cscf.jeci.domain.entities.midat.sample.QSample(forProperty("parentSample"), inits.get("parentSample")) : null;
        this.sourceMapping = inits.isInitialized("sourceMapping") ? new QGroundProtocolMapping(forProperty("sourceMapping"), inits.get("sourceMapping")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

