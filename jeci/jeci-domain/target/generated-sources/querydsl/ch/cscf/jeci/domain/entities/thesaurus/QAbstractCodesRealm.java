package ch.cscf.jeci.domain.entities.thesaurus;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAbstractCodesRealm is a Querydsl query type for AbstractCodesRealm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAbstractCodesRealm extends EntityPathBase<AbstractCodesRealm> {

    private static final long serialVersionUID = 95552509L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAbstractCodesRealm abstractCodesRealm = new QAbstractCodesRealm("abstractCodesRealm");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final StringPath code = createString("code");

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

    public final QLanguage defaultLanguage;

    public final NumberPath<Long> defaultLanguageId = createNumber("defaultLanguageId", Long.class);

    public final StringPath designation = createString("designation");

    //inherited
    public final NumberPath<Long> id;

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QAbstractCodesRealm(String variable) {
        this(AbstractCodesRealm.class, forVariable(variable), INITS);
    }

    public QAbstractCodesRealm(Path<? extends AbstractCodesRealm> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAbstractCodesRealm(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAbstractCodesRealm(PathMetadata metadata, PathInits inits) {
        this(AbstractCodesRealm.class, metadata, inits);
    }

    public QAbstractCodesRealm(Class<? extends AbstractCodesRealm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.defaultLanguage = inits.isInitialized("defaultLanguage") ? new QLanguage(forProperty("defaultLanguage"), inits.get("defaultLanguage")) : null;
        this.id = _super.id;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

