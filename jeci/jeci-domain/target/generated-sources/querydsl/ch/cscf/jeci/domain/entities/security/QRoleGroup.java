package ch.cscf.jeci.domain.entities.security;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoleGroup is a Querydsl query type for RoleGroup
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoleGroup extends EntityPathBase<RoleGroup> {

    private static final long serialVersionUID = 345383545L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoleGroup roleGroup = new QRoleGroup("roleGroup");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    //inherited
    public final DateTimePath<java.util.Date> creationDate;

    // inherited
    public final QUser creationUser;

    //inherited
    public final DateTimePath<java.util.Date> dbCreationDate;

    //inherited
    public final StringPath dbCreationUser;

    //inherited
    public final DateTimePath<java.util.Date> dbUpdateDate;

    //inherited
    public final StringPath dbUpdateUser;

    public final QGroup group;

    //inherited
    public final NumberPath<Long> id;

    public final QRole role;

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final QUser updateUser;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final BooleanPath writable = createBoolean("writable");

    public QRoleGroup(String variable) {
        this(RoleGroup.class, forVariable(variable), INITS);
    }

    public QRoleGroup(Path<? extends RoleGroup> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRoleGroup(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRoleGroup(PathMetadata metadata, PathInits inits) {
        this(RoleGroup.class, metadata, inits);
    }

    public QRoleGroup(Class<? extends RoleGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.group = inits.isInitialized("group") ? new QGroup(forProperty("group"), inits.get("group")) : null;
        this.id = _super.id;
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role"), inits.get("role")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

