package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProtocolImportLog is a Querydsl query type for ProtocolImportLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProtocolImportLog extends EntityPathBase<ProtocolImportLog> {

    private static final long serialVersionUID = 1613293942L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProtocolImportLog protocolImportLog = new QProtocolImportLog("protocolImportLog");

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

    public final NumberPath<Long> exceptionNumber = createNumber("exceptionNumber", Long.class);

    public final StringPath fieldName = createString("fieldName");

    //inherited
    public final NumberPath<Long> id;

    public final QProtocolImportHeader ownerProtocol;

    public final ListPath<ProtocolImportLogParam, QProtocolImportLogParam> parameters = this.<ProtocolImportLogParam, QProtocolImportLogParam>createList("parameters", ProtocolImportLogParam.class, QProtocolImportLogParam.class, PathInits.DIRECT2);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QProtocolImportLog(String variable) {
        this(ProtocolImportLog.class, forVariable(variable), INITS);
    }

    public QProtocolImportLog(Path<? extends ProtocolImportLog> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProtocolImportLog(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProtocolImportLog(PathMetadata metadata, PathInits inits) {
        this(ProtocolImportLog.class, metadata, inits);
    }

    public QProtocolImportLog(Class<? extends ProtocolImportLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.ownerProtocol = inits.isInitialized("ownerProtocol") ? new QProtocolImportHeader(forProperty("ownerProtocol"), inits.get("ownerProtocol")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

