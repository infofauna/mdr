package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEvaluationGridFieldMapping is a Querydsl query type for EvaluationGridFieldMapping
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEvaluationGridFieldMapping extends EntityPathBase<EvaluationGridFieldMapping> {

    private static final long serialVersionUID = -2131451577L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEvaluationGridFieldMapping evaluationGridFieldMapping = new QEvaluationGridFieldMapping("evaluationGridFieldMapping");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final StringPath colCode = createString("colCode");

    public final ComparablePath<Character> colIndex = createComparable("colIndex", Character.class);

    public final StringPath contentDefinitionCode = createString("contentDefinitionCode");

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

    public final StringPath rowCode = createString("rowCode");

    public final NumberPath<Integer> rowIndex = createNumber("rowIndex", Integer.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QEvaluationGridFieldMapping(String variable) {
        this(EvaluationGridFieldMapping.class, forVariable(variable), INITS);
    }

    public QEvaluationGridFieldMapping(Path<? extends EvaluationGridFieldMapping> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QEvaluationGridFieldMapping(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QEvaluationGridFieldMapping(PathMetadata metadata, PathInits inits) {
        this(EvaluationGridFieldMapping.class, metadata, inits);
    }

    public QEvaluationGridFieldMapping(Class<? extends EvaluationGridFieldMapping> type, PathMetadata metadata, PathInits inits) {
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

