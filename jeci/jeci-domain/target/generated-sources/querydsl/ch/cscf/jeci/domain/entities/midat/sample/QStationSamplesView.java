package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStationSamplesView is a Querydsl query type for StationSamplesView
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStationSamplesView extends EntityPathBase<StationSamplesView> {

    private static final long serialVersionUID = 38037581L;

    public static final QStationSamplesView stationSamplesView = new QStationSamplesView("stationSamplesView");

    public final NumberPath<Double> coordinateX = createNumber("coordinateX", Double.class);

    public final NumberPath<Double> coordinateY = createNumber("coordinateY", Double.class);

    public final NumberPath<Double> ibchIndexValue = createNumber("ibchIndexValue", Double.class);

    public final NumberPath<Integer> ibchLegendVersionId = createNumber("ibchLegendVersionId", Integer.class);

    public final NumberPath<Double> makroIndexValue = createNumber("makroIndexValue", Double.class);

    public final NumberPath<Integer> makroLegendVersionId = createNumber("makroLegendVersionId", Integer.class);

    public final DateTimePath<java.util.Date> sampleDate = createDateTime("sampleDate", java.util.Date.class);

    public final NumberPath<Long> sampleId = createNumber("sampleId", Long.class);

    public final NumberPath<Double> spearIndexValue = createNumber("spearIndexValue", Double.class);

    public final NumberPath<Integer> spearLegendVersionId = createNumber("spearLegendVersionId", Integer.class);

    public final NumberPath<Long> stationId = createNumber("stationId", Long.class);

    public final StringPath stationNumber = createString("stationNumber");

    public QStationSamplesView(String variable) {
        super(StationSamplesView.class, forVariable(variable));
    }

    public QStationSamplesView(Path<? extends StationSamplesView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStationSamplesView(PathMetadata metadata) {
        super(StationSamplesView.class, metadata);
    }

}

