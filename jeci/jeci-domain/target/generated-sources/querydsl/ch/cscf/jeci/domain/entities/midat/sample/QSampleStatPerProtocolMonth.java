package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSampleStatPerProtocolMonth is a Querydsl query type for SampleStatPerProtocolMonth
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleStatPerProtocolMonth extends EntityPathBase<SampleStatPerProtocolMonth> {

    private static final long serialVersionUID = 729862332L;

    public static final QSampleStatPerProtocolMonth sampleStatPerProtocolMonth = new QSampleStatPerProtocolMonth("sampleStatPerProtocolMonth");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> month = createNumber("month", Long.class);

    public final NumberPath<Long> numberLab = createNumber("numberLab", Long.class);

    public final NumberPath<Long> numberMass = createNumber("numberMass", Long.class);

    public final NumberPath<Long> year = createNumber("year", Long.class);

    public QSampleStatPerProtocolMonth(String variable) {
        super(SampleStatPerProtocolMonth.class, forVariable(variable));
    }

    public QSampleStatPerProtocolMonth(Path<? extends SampleStatPerProtocolMonth> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSampleStatPerProtocolMonth(PathMetadata metadata) {
        super(SampleStatPerProtocolMonth.class, metadata);
    }

}

