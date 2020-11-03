package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSampleDocument is a Querydsl query type for SampleDocument
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleDocument extends EntityPathBase<SampleDocument> {

    private static final long serialVersionUID = 655575608L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSampleDocument sampleDocument = new QSampleDocument("sampleDocument");

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

    public final StringPath fileName = createString("fileName");

    //inherited
    public final NumberPath<Long> id;

    public final ch.cscf.jeci.domain.entities.midat.QMIDATProtocolVersion protocolVersion;

    public final QSample sample;

    public final NumberPath<Long> sampleId = createNumber("sampleId", Long.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QSampleDocument(String variable) {
        this(SampleDocument.class, forVariable(variable), INITS);
    }

    public QSampleDocument(Path<? extends SampleDocument> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleDocument(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleDocument(PathMetadata metadata, PathInits inits) {
        this(SampleDocument.class, metadata, inits);
    }

    public QSampleDocument(Class<? extends SampleDocument> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.protocolVersion = inits.isInitialized("protocolVersion") ? new ch.cscf.jeci.domain.entities.midat.QMIDATProtocolVersion(forProperty("protocolVersion"), inits.get("protocolVersion")) : null;
        this.sample = inits.isInitialized("sample") ? new QSample(forProperty("sample"), inits.get("sample")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

