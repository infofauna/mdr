package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProtocolImportHeader is a Querydsl query type for ProtocolImportHeader
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProtocolImportHeader extends EntityPathBase<ProtocolImportHeader> {

    private static final long serialVersionUID = 831952667L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProtocolImportHeader protocolImportHeader = new QProtocolImportHeader("protocolImportHeader");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final StringPath absoluteNumberFlag = createString("absoluteNumberFlag");

    public final DateTimePath<java.util.Date> analysisDate = createDateTime("analysisDate", java.util.Date.class);

    public final NumberPath<Long> analysisPersonId = createNumber("analysisPersonId", Long.class);

    public final StringPath autreneoz_1 = createString("autreneoz_1");

    public final StringPath autreneoz_2 = createString("autreneoz_2");

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

    public final StringPath determinator = createString("determinator");

    public final StringPath documentUrl = createString("documentUrl");

    public final StringPath elevation = createString("elevation");

    public final ArrayPath<byte[], Byte> excelFileBytes = createArray("excelFileBytes", byte[].class);

    public final StringPath excelFileName = createString("excelFileName");

    public final StringPath excelSheetName = createString("excelSheetName");

    public final StringPath ibchq = createString("ibchq");

    public final StringPath ibchValue = createString("ibchValue");

    public final StringPath ibchValue_r = createString("ibchValue_r");

    //inherited
    public final NumberPath<Long> id;

    public final NumberPath<Long> languageId = createNumber("languageId", Long.class);

    public final StringPath locality = createString("locality");

    public final ListPath<ProtocolImportLog, QProtocolImportLog> logs = this.<ProtocolImportLog, QProtocolImportLog>createList("logs", ProtocolImportLog.class, QProtocolImportLog.class, PathInits.DIRECT2);

    public final NumberPath<Long> mandataryInstitutionId = createNumber("mandataryInstitutionId", Long.class);

    public final StringPath observationDateText = createString("observationDateText");

    public final StringPath operator = createString("operator");

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    public final NumberPath<Long> precisionLevelId = createNumber("precisionLevelId", Long.class);

    public final NumberPath<Long> principalInstituionId = createNumber("principalInstituionId", Long.class);

    public final QMIDATProtocolVersion protocolVersion;

    public final NumberPath<Long> protocolVersionId = createNumber("protocolVersionId", Long.class);

    public final NumberPath<Long> referenceSystemId = createNumber("referenceSystemId", Long.class);

    public final NumberPath<Long> samplePersonId = createNumber("samplePersonId", Long.class);

    public final NumberPath<Long> sampleProjectId = createNumber("sampleProjectId", Long.class);

    public final StringPath sampleStationNumber = createString("sampleStationNumber");

    public final StringPath sommeAbon = createString("sommeAbon");

    public final StringPath sommeNeoz = createString("sommeNeoz");

    public final StringPath sommePt = createString("sommePt");

    public final StringPath sommeTxcor = createString("sommeTxcor");

    public final StringPath sommeTxobs = createString("sommeTxobs");

    public final StringPath spearValue = createString("spearValue");

    public final StringPath startPointX = createString("startPointX");

    public final StringPath startPointY = createString("startPointY");

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public final StringPath valeurGi = createString("valeurGi");

    public final StringPath valeurVt = createString("valeurVt");

    public final ComparablePath<Character> validated = createComparable("validated", Character.class);

    public final StringPath vc = createString("vc");

    public final StringPath waterCourse = createString("waterCourse");

    public QProtocolImportHeader(String variable) {
        this(ProtocolImportHeader.class, forVariable(variable), INITS);
    }

    public QProtocolImportHeader(Path<? extends ProtocolImportHeader> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProtocolImportHeader(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProtocolImportHeader(PathMetadata metadata, PathInits inits) {
        this(ProtocolImportHeader.class, metadata, inits);
    }

    public QProtocolImportHeader(Class<? extends ProtocolImportHeader> type, PathMetadata metadata, PathInits inits) {
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

