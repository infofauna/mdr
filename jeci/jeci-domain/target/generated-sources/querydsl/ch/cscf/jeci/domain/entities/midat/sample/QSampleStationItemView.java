package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSampleStationItemView is a Querydsl query type for SampleStationItemView
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleStationItemView extends EntityPathBase<SampleStationItemView> {

    private static final long serialVersionUID = 1435358703L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSampleStationItemView sampleStationItemView = new QSampleStationItemView("sampleStationItemView");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ch.cscf.jeci.domain.entities.thesaurus.QLanguage language;

    public final NumberPath<Long> languageId = createNumber("languageId", Long.class);

    public final NumberPath<Long> stationId = createNumber("stationId", Long.class);

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue type;

    public final NumberPath<Long> typeId = createNumber("typeId", Long.class);

    public final StringPath value = createString("value");

    public QSampleStationItemView(String variable) {
        this(SampleStationItemView.class, forVariable(variable), INITS);
    }

    public QSampleStationItemView(Path<? extends SampleStationItemView> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleStationItemView(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleStationItemView(PathMetadata metadata, PathInits inits) {
        this(SampleStationItemView.class, metadata, inits);
    }

    public QSampleStationItemView(Class<? extends SampleStationItemView> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.language = inits.isInitialized("language") ? new ch.cscf.jeci.domain.entities.thesaurus.QLanguage(forProperty("language"), inits.get("language")) : null;
        this.type = inits.isInitialized("type") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("type"), inits.get("type")) : null;
    }

}

