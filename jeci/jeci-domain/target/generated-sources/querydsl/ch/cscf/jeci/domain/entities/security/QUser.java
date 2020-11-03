package ch.cscf.jeci.domain.entities.security;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 140158715L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final BooleanPath arma = createBoolean("arma");

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

    public final StringPath designation = createString("designation");

    //inherited
    public final NumberPath<Long> id;

    public final ch.cscf.jeci.domain.entities.thesaurus.QLanguage language;

    public final NumberPath<Long> languageId = createNumber("languageId", Long.class);

    public final BooleanPath ldap = createBoolean("ldap");

    public final StringPath password = createString("password");

    public final ch.cscf.jeci.domain.entities.base.QPerson person;

    public final NumberPath<Long> personId = createNumber("personId", Long.class);

    public final ListPath<RoleGroup, QRoleGroup> roleGroups = this.<RoleGroup, QRoleGroup>createList("roleGroups", RoleGroup.class, QRoleGroup.class, PathInits.DIRECT2);

    public final StringPath ssoIpAddress = createString("ssoIpAddress");

    public final NumberPath<Integer> ssoSessionCounter = createNumber("ssoSessionCounter", Integer.class);

    public final StringPath ssoSessionId = createString("ssoSessionId");

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final QUser updateUser;

    public final StringPath username = createString("username");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUser(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.language = inits.isInitialized("language") ? new ch.cscf.jeci.domain.entities.thesaurus.QLanguage(forProperty("language"), inits.get("language")) : null;
        this.person = inits.isInitialized("person") ? new ch.cscf.jeci.domain.entities.base.QPerson(forProperty("person"), inits.get("person")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

