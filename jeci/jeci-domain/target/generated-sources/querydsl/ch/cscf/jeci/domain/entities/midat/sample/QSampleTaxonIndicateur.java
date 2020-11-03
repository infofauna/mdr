package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSampleTaxonIndicateur is a Querydsl query type for SampleTaxonIndicateur
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleTaxonIndicateur extends EntityPathBase<SampleTaxonIndicateur> {

    private static final long serialVersionUID = -2086186557L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSampleTaxonIndicateur sampleTaxonIndicateur = new QSampleTaxonIndicateur("sampleTaxonIndicateur");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSample sample;

    public final NumberPath<Long> sampleId = createNumber("sampleId", Long.class);

    public final StringPath taxonIndicateurName = createString("taxonIndicateurName");

    public QSampleTaxonIndicateur(String variable) {
        this(SampleTaxonIndicateur.class, forVariable(variable), INITS);
    }

    public QSampleTaxonIndicateur(Path<? extends SampleTaxonIndicateur> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleTaxonIndicateur(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleTaxonIndicateur(PathMetadata metadata, PathInits inits) {
        this(SampleTaxonIndicateur.class, metadata, inits);
    }

    public QSampleTaxonIndicateur(Class<? extends SampleTaxonIndicateur> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sample = inits.isInitialized("sample") ? new QSample(forProperty("sample"), inits.get("sample")) : null;
    }

}

