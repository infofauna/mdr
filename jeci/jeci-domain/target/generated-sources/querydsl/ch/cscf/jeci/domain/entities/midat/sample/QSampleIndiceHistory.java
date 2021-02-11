package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSampleIndiceHistory is a Querydsl query type for SampleIndiceHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleIndiceHistory extends EntityPathBase<SampleIndiceHistory> {

    private static final long serialVersionUID = 70762923L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSampleIndiceHistory sampleIndiceHistory = new QSampleIndiceHistory("sampleIndiceHistory");

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

    public final NumberPath<Integer> ephemeropteraCounter = createNumber("ephemeropteraCounter", Integer.class);

    public final NumberPath<Double> giFinal = createNumber("giFinal", Double.class);

    public final NumberPath<Double> ibchIndexValue = createNumber("ibchIndexValue", Double.class);

    public final NumberPath<Integer> ibchLegendVersionId = createNumber("ibchLegendVersionId", Integer.class);

    public final NumberPath<Integer> ibchQ = createNumber("ibchQ", Integer.class);

    public final NumberPath<Double> ibchRobust = createNumber("ibchRobust", Double.class);

    //inherited
    public final NumberPath<Long> id;

    public final NumberPath<Double> makroIndexValue = createNumber("makroIndexValue", Double.class);

    public final NumberPath<Integer> makroLegendVersionId = createNumber("makroLegendVersionId", Integer.class);

    public final NumberPath<Integer> plecopteraCounter = createNumber("plecopteraCounter", Integer.class);

    public final QSample sample;

    public final NumberPath<Long> sampleId = createNumber("sampleId", Long.class);

    public final NumberPath<Integer> sommeAbon = createNumber("sommeAbon", Integer.class);

    public final NumberPath<Double> spearIndexValue = createNumber("spearIndexValue", Double.class);

    public final NumberPath<Integer> spearLegendVersionId = createNumber("spearLegendVersionId", Integer.class);

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

    public final NumberPath<Integer> versionSeq = createNumber("versionSeq", Integer.class);

    public QSampleIndiceHistory(String variable) {
        this(SampleIndiceHistory.class, forVariable(variable), INITS);
    }

    public QSampleIndiceHistory(Path<? extends SampleIndiceHistory> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleIndiceHistory(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleIndiceHistory(PathMetadata metadata, PathInits inits) {
        this(SampleIndiceHistory.class, metadata, inits);
    }

    public QSampleIndiceHistory(Class<? extends SampleIndiceHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.sample = inits.isInitialized("sample") ? new QSample(forProperty("sample"), inits.get("sample")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

