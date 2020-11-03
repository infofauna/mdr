package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSampleStationItem is a Querydsl query type for SampleStationItem
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleStationItem extends EntityPathBase<SampleStationItem> {

    private static final long serialVersionUID = 1771785258L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSampleStationItem sampleStationItem = new QSampleStationItem("sampleStationItem");

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

    public final ch.cscf.jeci.domain.entities.thesaurus.QLanguage language;

    public final NumberPath<Long> languageId = createNumber("languageId", Long.class);

    public final QSampleStation station;

    public final NumberPath<Long> stationId = createNumber("stationId", Long.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue type;

    public final NumberPath<Long> typeId = createNumber("typeId", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public final StringPath value = createString("value");

    public QSampleStationItem(String variable) {
        this(SampleStationItem.class, forVariable(variable), INITS);
    }

    public QSampleStationItem(Path<? extends SampleStationItem> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleStationItem(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleStationItem(PathMetadata metadata, PathInits inits) {
        this(SampleStationItem.class, metadata, inits);
    }

    public QSampleStationItem(Class<? extends SampleStationItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.language = inits.isInitialized("language") ? new ch.cscf.jeci.domain.entities.thesaurus.QLanguage(forProperty("language"), inits.get("language")) : null;
        this.station = inits.isInitialized("station") ? new QSampleStation(forProperty("station"), inits.get("station")) : null;
        this.status = _super.status;
        this.type = inits.isInitialized("type") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("type"), inits.get("type")) : null;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

