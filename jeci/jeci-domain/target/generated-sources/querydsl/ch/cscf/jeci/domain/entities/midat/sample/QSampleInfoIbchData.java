package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSampleInfoIbchData is a Querydsl query type for SampleInfoIbchData
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleInfoIbchData extends EntityPathBase<SampleInfoIbchData> {

    private static final long serialVersionUID = 580757875L;

    public static final QSampleInfoIbchData sampleInfoIbchData = new QSampleInfoIbchData("sampleInfoIbchData");

    public final StringPath colibch = createString("colibch");

    public final NumberPath<Long> countEphemeroptera = createNumber("countEphemeroptera", Long.class);

    public final NumberPath<Long> countPlecoptera = createNumber("countPlecoptera", Long.class);

    public final NumberPath<Long> countTricoptera = createNumber("countTricoptera", Long.class);

    public final NumberPath<Long> groupIndicateur = createNumber("groupIndicateur", Long.class);

    public final NumberPath<Long> ibch = createNumber("ibch", Long.class);

    public final NumberPath<Long> sphId = createNumber("sphId", Long.class);

    public final StringPath station = createString("station");

    public final NumberPath<Long> sumTaxon = createNumber("sumTaxon", Long.class);

    public final StringPath taxonIndicateur = createString("taxonIndicateur");

    public final StringPath type = createString("type");

    public final NumberPath<Long> vt = createNumber("vt", Long.class);

    public QSampleInfoIbchData(String variable) {
        super(SampleInfoIbchData.class, forVariable(variable));
    }

    public QSampleInfoIbchData(Path<? extends SampleInfoIbchData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSampleInfoIbchData(PathMetadata metadata) {
        super(SampleInfoIbchData.class, metadata);
    }

}

