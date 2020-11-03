package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProtocolImportLogParam is a Querydsl query type for ProtocolImportLogParam
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProtocolImportLogParam extends EntityPathBase<ProtocolImportLogParam> {

    private static final long serialVersionUID = -537806665L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProtocolImportLogParam protocolImportLogParam = new QProtocolImportLogParam("protocolImportLogParam");

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

    public final QProtocolImportLog ownerLog;

    public final NumberPath<Integer> sequenceNumber = createNumber("sequenceNumber", Integer.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public final StringPath value = createString("value");

    public QProtocolImportLogParam(String variable) {
        this(ProtocolImportLogParam.class, forVariable(variable), INITS);
    }

    public QProtocolImportLogParam(Path<? extends ProtocolImportLogParam> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProtocolImportLogParam(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProtocolImportLogParam(PathMetadata metadata, PathInits inits) {
        this(ProtocolImportLogParam.class, metadata, inits);
    }

    public QProtocolImportLogParam(Class<? extends ProtocolImportLogParam> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.ownerLog = inits.isInitialized("ownerLog") ? new QProtocolImportLog(forProperty("ownerLog"), inits.get("ownerLog")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

