package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QProtocolIndex is a Querydsl query type for ProtocolIndex
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProtocolIndex extends EntityPathBase<ProtocolIndex> {

    private static final long serialVersionUID = 1003740265L;

    public static final QProtocolIndex protocolIndex = new QProtocolIndex("protocolIndex");

    public final NumberPath<Double> avgIBCH = createNumber("avgIBCH", Double.class);

    public final NumberPath<Double> avgMAKROINDEX = createNumber("avgMAKROINDEX", Double.class);

    public final NumberPath<Double> avgSPEAR = createNumber("avgSPEAR", Double.class);

    public final StringPath canton = createString("canton");

    public final NumberPath<Double> countIBCH = createNumber("countIBCH", Double.class);

    public final NumberPath<Double> countMAKROINDEX = createNumber("countMAKROINDEX", Double.class);

    public final NumberPath<Double> countSPEAR = createNumber("countSPEAR", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> maxIBCH = createNumber("maxIBCH", Double.class);

    public final NumberPath<Double> maxMAKROINDEX = createNumber("maxMAKROINDEX", Double.class);

    public final NumberPath<Double> maxSPEAR = createNumber("maxSPEAR", Double.class);

    public final NumberPath<Double> minIBCH = createNumber("minIBCH", Double.class);

    public final NumberPath<Double> minMAKROINDEX = createNumber("minMAKROINDEX", Double.class);

    public final NumberPath<Double> minSPEAR = createNumber("minSPEAR", Double.class);

    public QProtocolIndex(String variable) {
        super(ProtocolIndex.class, forVariable(variable));
    }

    public QProtocolIndex(Path<? extends ProtocolIndex> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProtocolIndex(PathMetadata metadata) {
        super(ProtocolIndex.class, metadata);
    }

}

