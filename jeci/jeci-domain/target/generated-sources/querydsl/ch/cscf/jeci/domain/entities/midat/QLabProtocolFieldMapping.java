package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLabProtocolFieldMapping is a Querydsl query type for LabProtocolFieldMapping
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLabProtocolFieldMapping extends EntityPathBase<LabProtocolFieldMapping> {

    private static final long serialVersionUID = -629904664L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLabProtocolFieldMapping labProtocolFieldMapping = new QLabProtocolFieldMapping("labProtocolFieldMapping");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final StringPath cellColumnValue = createString("cellColumnValue");

    public final StringPath cellRowValue = createString("cellRowValue");

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

    public final NumberPath<Long> ibchDeterminingGroup = createNumber("ibchDeterminingGroup", Long.class);

    public final StringPath ibchTaxon = createString("ibchTaxon");

    //inherited
    public final NumberPath<Long> id;

    public final QMIDATProtocolVersion protocolVersion;

    public final NumberPath<Long> protocolVersionId = createNumber("protocolVersionId", Long.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QLabProtocolFieldMapping(String variable) {
        this(LabProtocolFieldMapping.class, forVariable(variable), INITS);
    }

    public QLabProtocolFieldMapping(Path<? extends LabProtocolFieldMapping> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLabProtocolFieldMapping(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLabProtocolFieldMapping(PathMetadata metadata, PathInits inits) {
        this(LabProtocolFieldMapping.class, metadata, inits);
    }

    public QLabProtocolFieldMapping(Class<? extends LabProtocolFieldMapping> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.protocolVersion = inits.isInitialized("protocolVersion") ? new QMIDATProtocolVersion(forProperty("protocolVersion"), inits.get("protocolVersion")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

