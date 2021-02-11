package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSample is a Querydsl query type for Sample
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSample extends EntityPathBase<Sample> {

    private static final long serialVersionUID = -718577155L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSample sample = new QSample("sample");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final BooleanPath absoluteNumbers = createBoolean("absoluteNumbers");

    public final DateTimePath<java.util.Date> analysisDate = createDateTime("analysisDate", java.util.Date.class);

    public final SetPath<SampleAttribute, QSampleAttribute> attributes = this.<SampleAttribute, QSampleAttribute>createSet("attributes", SampleAttribute.class, QSampleAttribute.class, PathInits.DIRECT2);

    public final SetPath<SampleComment, QSampleComment> comments = this.<SampleComment, QSampleComment>createSet("comments", SampleComment.class, QSampleComment.class, PathInits.DIRECT2);

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

    public final SetPath<SampleDocument, QSampleDocument> documents = this.<SampleDocument, QSampleDocument>createSet("documents", SampleDocument.class, QSampleDocument.class, PathInits.DIRECT2);

    public final NumberPath<Integer> ephemeropteraCounter = createNumber("ephemeropteraCounter", Integer.class);

    public final NumberPath<Double> giFinal = createNumber("giFinal", Double.class);

    public final SetPath<ch.cscf.jeci.domain.entities.security.Group, ch.cscf.jeci.domain.entities.security.QGroup> groups = this.<ch.cscf.jeci.domain.entities.security.Group, ch.cscf.jeci.domain.entities.security.QGroup>createSet("groups", ch.cscf.jeci.domain.entities.security.Group.class, ch.cscf.jeci.domain.entities.security.QGroup.class, PathInits.DIRECT2);

    public final NumberPath<Double> ibchIndexValue = createNumber("ibchIndexValue", Double.class);

    public final NumberPath<Integer> ibchLegendVersionId = createNumber("ibchLegendVersionId", Integer.class);

    public final NumberPath<Integer> ibchQ = createNumber("ibchQ", Integer.class);

    public final NumberPath<Double> ibchRobust = createNumber("ibchRobust", Double.class);

    //inherited
    public final NumberPath<Long> id;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue indiceType;

    public final ListPath<ch.cscf.jeci.domain.entities.midat.LabRecordField, ch.cscf.jeci.domain.entities.midat.QLabRecordField> labRecordFields = this.<ch.cscf.jeci.domain.entities.midat.LabRecordField, ch.cscf.jeci.domain.entities.midat.QLabRecordField>createList("labRecordFields", ch.cscf.jeci.domain.entities.midat.LabRecordField.class, ch.cscf.jeci.domain.entities.midat.QLabRecordField.class, PathInits.DIRECT2);

    public final NumberPath<Double> makroIndexValue = createNumber("makroIndexValue", Double.class);

    public final NumberPath<Integer> makroLegendVersionId = createNumber("makroLegendVersionId", Integer.class);

    public final ch.cscf.jeci.domain.entities.base.QInstitution mandataryInstitution;

    public final SetPath<SampleOriginalFile, QSampleOriginalFile> originalFiles = this.<SampleOriginalFile, QSampleOriginalFile>createSet("originalFiles", SampleOriginalFile.class, QSampleOriginalFile.class, PathInits.DIRECT2);

    public final NumberPath<Integer> plecopteraCounter = createNumber("plecopteraCounter", Integer.class);

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue precision;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue precisionReferenceSystem;

    public final ch.cscf.jeci.domain.entities.base.QInstitution principalInstitution;

    public final StringPath project = createString("project");

    public final ch.cscf.jeci.domain.entities.midat.QMIDATProtocolVersion protocolVersion;

    public final BooleanPath published = createBoolean("published");

    public final StringPath sampleCommentOther = createString("sampleCommentOther");

    public final DateTimePath<java.util.Date> sampleDate = createDateTime("sampleDate", java.util.Date.class);

    public final NumberPath<Integer> sampleDateDay = createNumber("sampleDateDay", Integer.class);

    public final NumberPath<Integer> sampleDateMonth = createNumber("sampleDateMonth", Integer.class);

    public final NumberPath<Integer> sampleDateYear = createNumber("sampleDateYear", Integer.class);

    public final ListPath<SampleIndiceHistory, QSampleIndiceHistory> sampleIndiceHistoryList = this.<SampleIndiceHistory, QSampleIndiceHistory>createList("sampleIndiceHistoryList", SampleIndiceHistory.class, QSampleIndiceHistory.class, PathInits.DIRECT2);

    public final QSampleMassOriginalFile sampleMassOriginalFile;

    public final NumberPath<Long> sampleMassOriginalFileId = createNumber("sampleMassOriginalFileId", Long.class);

    public final ch.cscf.jeci.domain.entities.base.QProject sampleProject;

    public final NumberPath<Long> sampleProjectId = createNumber("sampleProjectId", Long.class);

    public final QSampleProtocolType sampleProtocolType;

    public final ListPath<SampleTaxonIndicateur, QSampleTaxonIndicateur> sampleTaxonIndicateur = this.<SampleTaxonIndicateur, QSampleTaxonIndicateur>createList("sampleTaxonIndicateur", SampleTaxonIndicateur.class, QSampleTaxonIndicateur.class, PathInits.DIRECT2);

    public final NumberPath<Integer> sommeAbon = createNumber("sommeAbon", Integer.class);

    public final NumberPath<Double> spearIndexValue = createNumber("spearIndexValue", Double.class);

    public final NumberPath<Integer> spearLegendVersionId = createNumber("spearLegendVersionId", Integer.class);

    public final QSampleStation station;

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    public final StringPath taxonIndicateur = createString("taxonIndicateur");

    public final NumberPath<Integer> taxonSumFamily = createNumber("taxonSumFamily", Integer.class);

    public final NumberPath<Integer> tricopteraCounter = createNumber("tricopteraCounter", Integer.class);

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public final NumberPath<Double> valeurCorrection = createNumber("valeurCorrection", Double.class);

    public final NumberPath<Double> valeurGi = createNumber("valeurGi", Double.class);

    public final NumberPath<Double> valuerVt = createNumber("valuerVt", Double.class);

    public QSample(String variable) {
        this(Sample.class, forVariable(variable), INITS);
    }

    public QSample(Path<? extends Sample> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSample(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSample(PathMetadata metadata, PathInits inits) {
        this(Sample.class, metadata, inits);
    }

    public QSample(Class<? extends Sample> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.indiceType = inits.isInitialized("indiceType") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("indiceType"), inits.get("indiceType")) : null;
        this.mandataryInstitution = inits.isInitialized("mandataryInstitution") ? new ch.cscf.jeci.domain.entities.base.QInstitution(forProperty("mandataryInstitution"), inits.get("mandataryInstitution")) : null;
        this.precision = inits.isInitialized("precision") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("precision"), inits.get("precision")) : null;
        this.precisionReferenceSystem = inits.isInitialized("precisionReferenceSystem") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("precisionReferenceSystem"), inits.get("precisionReferenceSystem")) : null;
        this.principalInstitution = inits.isInitialized("principalInstitution") ? new ch.cscf.jeci.domain.entities.base.QInstitution(forProperty("principalInstitution"), inits.get("principalInstitution")) : null;
        this.protocolVersion = inits.isInitialized("protocolVersion") ? new ch.cscf.jeci.domain.entities.midat.QMIDATProtocolVersion(forProperty("protocolVersion"), inits.get("protocolVersion")) : null;
        this.sampleMassOriginalFile = inits.isInitialized("sampleMassOriginalFile") ? new QSampleMassOriginalFile(forProperty("sampleMassOriginalFile"), inits.get("sampleMassOriginalFile")) : null;
        this.sampleProject = inits.isInitialized("sampleProject") ? new ch.cscf.jeci.domain.entities.base.QProject(forProperty("sampleProject"), inits.get("sampleProject")) : null;
        this.sampleProtocolType = inits.isInitialized("sampleProtocolType") ? new QSampleProtocolType(forProperty("sampleProtocolType")) : null;
        this.station = inits.isInitialized("station") ? new QSampleStation(forProperty("station"), inits.get("station")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

