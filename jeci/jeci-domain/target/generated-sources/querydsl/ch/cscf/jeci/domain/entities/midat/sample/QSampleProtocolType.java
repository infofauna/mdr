package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSampleProtocolType is a Querydsl query type for SampleProtocolType
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleProtocolType extends EntityPathBase<SampleProtocolType> {

    private static final long serialVersionUID = -586930033L;

    public static final QSampleProtocolType sampleProtocolType = new QSampleProtocolType("sampleProtocolType");

    public final StringPath code = createString("code");

    public final StringPath designation = createString("designation");

    public final BooleanPath hasGrid = createBoolean("hasGrid");

    public final BooleanPath hasGround = createBoolean("hasGround");

    public final NumberPath<Long> sampleId = createNumber("sampleId", Long.class);

    public QSampleProtocolType(String variable) {
        super(SampleProtocolType.class, forVariable(variable));
    }

    public QSampleProtocolType(Path<? extends SampleProtocolType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSampleProtocolType(PathMetadata metadata) {
        super(SampleProtocolType.class, metadata);
    }

}

