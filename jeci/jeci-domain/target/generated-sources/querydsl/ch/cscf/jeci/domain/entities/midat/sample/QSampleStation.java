package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSampleStation is a Querydsl query type for SampleStation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleStation extends EntityPathBase<SampleStation> {

    private static final long serialVersionUID = 35876983L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSampleStation sampleStation = new QSampleStation("sampleStation");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    // custom
    public final com.querydsl.spatial.jts.JTSPointPath coordinates = new com.querydsl.spatial.jts.JTSPointPath(forProperty("coordinates"));

    public final NumberPath<Double> coordinateZ = createNumber("coordinateZ", Double.class);

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

    public final ListPath<SampleStationItem, QSampleStationItem> items = this.<SampleStationItem, QSampleStationItem>createList("items", SampleStationItem.class, QSampleStationItem.class, PathInits.DIRECT2);

    public final QSampleStation parent;

    public final ListPath<Sample, QSample> samples = this.<Sample, QSample>createList("samples", Sample.class, QSample.class, PathInits.DIRECT2);

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue stationCanton;

    public final NumberPath<Long> stationCantonId = createNumber("stationCantonId", Long.class);

    public final StringPath stationGewissNumber = createString("stationGewissNumber");

    public final StringPath stationNumber = createString("stationNumber");

    public final NumberPath<Long> stationPrincipalInstitutionId = createNumber("stationPrincipalInstitutionId", Long.class);

    public final ListPath<SampleStation, QSampleStation> stationStations = this.<SampleStation, QSampleStation>createList("stationStations", SampleStation.class, QSampleStation.class, PathInits.DIRECT2);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QSampleStation(String variable) {
        this(SampleStation.class, forVariable(variable), INITS);
    }

    public QSampleStation(Path<? extends SampleStation> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleStation(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleStation(PathMetadata metadata, PathInits inits) {
        this(SampleStation.class, metadata, inits);
    }

    public QSampleStation(Class<? extends SampleStation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.parent = inits.isInitialized("parent") ? new QSampleStation(forProperty("parent"), inits.get("parent")) : null;
        this.stationCanton = inits.isInitialized("stationCanton") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("stationCanton"), inits.get("stationCanton")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

