package ch.cscf.jeci.domain.entities.base;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBaseEntity is a Querydsl query type for BaseEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseEntity extends EntityPathBase<BaseEntity> {

    private static final long serialVersionUID = 1475881491L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBaseEntity baseEntity = new QBaseEntity("baseEntity");

    public final DateTimePath<java.util.Date> creationDate = createDateTime("creationDate", java.util.Date.class);

    public final ch.cscf.jeci.domain.entities.security.QUser creationUser;

    public final DateTimePath<java.util.Date> dbCreationDate = createDateTime("dbCreationDate", java.util.Date.class);

    public final StringPath dbCreationUser = createString("dbCreationUser");

    public final DateTimePath<java.util.Date> dbUpdateDate = createDateTime("dbUpdateDate", java.util.Date.class);

    public final StringPath dbUpdateUser = createString("dbUpdateUser");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<EntityStatus> status = createEnum("status", EntityStatus.class);

    public final DateTimePath<java.util.Date> updateDate = createDateTime("updateDate", java.util.Date.class);

    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QBaseEntity(String variable) {
        this(BaseEntity.class, forVariable(variable), INITS);
    }

    public QBaseEntity(Path<? extends BaseEntity> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QBaseEntity(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QBaseEntity(PathMetadata metadata, PathInits inits) {
        this(BaseEntity.class, metadata, inits);
    }

    public QBaseEntity(Class<? extends BaseEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creationUser = inits.isInitialized("creationUser") ? new ch.cscf.jeci.domain.entities.security.QUser(forProperty("creationUser"), inits.get("creationUser")) : null;
        this.updateUser = inits.isInitialized("updateUser") ? new ch.cscf.jeci.domain.entities.security.QUser(forProperty("updateUser"), inits.get("updateUser")) : null;
    }

}

