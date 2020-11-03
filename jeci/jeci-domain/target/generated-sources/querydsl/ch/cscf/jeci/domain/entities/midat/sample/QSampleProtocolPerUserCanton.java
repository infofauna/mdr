package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSampleProtocolPerUserCanton is a Querydsl query type for SampleProtocolPerUserCanton
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleProtocolPerUserCanton extends EntityPathBase<SampleProtocolPerUserCanton> {

    private static final long serialVersionUID = -1536252298L;

    public static final QSampleProtocolPerUserCanton sampleProtocolPerUserCanton = new QSampleProtocolPerUserCanton("sampleProtocolPerUserCanton");

    public final StringPath canton = createString("canton");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath login = createString("login");

    public final NumberPath<Long> numberLab = createNumber("numberLab", Long.class);

    public final NumberPath<Long> numberMass = createNumber("numberMass", Long.class);

    public QSampleProtocolPerUserCanton(String variable) {
        super(SampleProtocolPerUserCanton.class, forVariable(variable));
    }

    public QSampleProtocolPerUserCanton(Path<? extends SampleProtocolPerUserCanton> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSampleProtocolPerUserCanton(PathMetadata metadata) {
        super(SampleProtocolPerUserCanton.class, metadata);
    }

}

