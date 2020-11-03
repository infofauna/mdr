package ch.cscf.jeci.domain.entities.security;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJeciApplication is a Querydsl query type for JeciApplication
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QJeciApplication extends EntityPathBase<JeciApplication> {

    private static final long serialVersionUID = 1057551999L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJeciApplication jeciApplication = new QJeciApplication("jeciApplication");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final StringPath code = createString("code");

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

    public final StringPath description = createString("description");

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final QUser updateUser;

    public QJeciApplication(String variable) {
        this(JeciApplication.class, forVariable(variable), INITS);
    }

    public QJeciApplication(Path<? extends JeciApplication> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QJeciApplication(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QJeciApplication(PathMetadata metadata, PathInits inits) {
        this(JeciApplication.class, metadata, inits);
    }

    public QJeciApplication(Class<? extends JeciApplication> type, PathMetadata metadata, PathInits inits) {
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

