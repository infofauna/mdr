package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSampleImportDisplayLog is a Querydsl query type for SampleImportDisplayLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleImportDisplayLog extends EntityPathBase<SampleImportDisplayLog> {

    private static final long serialVersionUID = 705828036L;

    public static final QSampleImportDisplayLog sampleImportDisplayLog = new QSampleImportDisplayLog("sampleImportDisplayLog");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> ipoIphId = createNumber("ipoIphId", Long.class);

    public final NumberPath<Long> lanId = createNumber("lanId", Long.class);

    public final StringPath message = createString("message");

    public final StringPath severity = createString("severity");

    public final NumberPath<Long> sphId = createNumber("sphId", Long.class);

    public QSampleImportDisplayLog(String variable) {
        super(SampleImportDisplayLog.class, forVariable(variable));
    }

    public QSampleImportDisplayLog(Path<? extends SampleImportDisplayLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSampleImportDisplayLog(PathMetadata metadata) {
        super(SampleImportDisplayLog.class, metadata);
    }

}

