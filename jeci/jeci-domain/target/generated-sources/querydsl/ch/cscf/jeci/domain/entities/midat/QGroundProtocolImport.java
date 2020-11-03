package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroundProtocolImport is a Querydsl query type for GroundProtocolImport
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGroundProtocolImport extends EntityPathBase<GroundProtocolImport> {

    private static final long serialVersionUID = -2054672491L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroundProtocolImport groundProtocolImport = new QGroundProtocolImport("groundProtocolImport");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final NumberPath<Long> columnId = createNumber("columnId", Long.class);

    public final StringPath columnValue = createString("columnValue");

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

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QGroundProtocolImport(String variable) {
        this(GroundProtocolImport.class, forVariable(variable), INITS);
    }

    public QGroundProtocolImport(Path<? extends GroundProtocolImport> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QGroundProtocolImport(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QGroundProtocolImport(PathMetadata metadata, PathInits inits) {
        this(GroundProtocolImport.class, metadata, inits);
    }

    public QGroundProtocolImport(Class<? extends GroundProtocolImport> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

