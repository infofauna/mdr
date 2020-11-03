package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProtocolMappingMassField is a Querydsl query type for ProtocolMappingMassField
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProtocolMappingMassField extends EntityPathBase<ProtocolMappingMassField> {

    private static final long serialVersionUID = 561441089L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProtocolMappingMassField protocolMappingMassField = new QProtocolMappingMassField("protocolMappingMassField");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final StringPath codeConcept = createString("codeConcept");

    public final StringPath columnName = createString("columnName");

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

    public final QMIDATProtocolVersion protocolVersion;

    public final NumberPath<Long> protocolVersionId = createNumber("protocolVersionId", Long.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QProtocolMappingMassField(String variable) {
        this(ProtocolMappingMassField.class, forVariable(variable), INITS);
    }

    public QProtocolMappingMassField(Path<? extends ProtocolMappingMassField> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProtocolMappingMassField(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProtocolMappingMassField(PathMetadata metadata, PathInits inits) {
        this(ProtocolMappingMassField.class, metadata, inits);
    }

    public QProtocolMappingMassField(Class<? extends ProtocolMappingMassField> type, PathMetadata metadata, PathInits inits) {
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

