package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSampleMassOriginalFile is a Querydsl query type for SampleMassOriginalFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleMassOriginalFile extends EntityPathBase<SampleMassOriginalFile> {

    private static final long serialVersionUID = 1037974558L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSampleMassOriginalFile sampleMassOriginalFile = new QSampleMassOriginalFile("sampleMassOriginalFile");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final ArrayPath<byte[], Byte> bytes = createArray("bytes", byte[].class);

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

    public final ch.cscf.jeci.domain.entities.thesaurus.QLanguage language;

    public final ch.cscf.jeci.domain.entities.midat.QMIDATProtocolVersion protocolVersion;

    public final ListPath<Sample, QSample> samples = this.<Sample, QSample>createList("samples", Sample.class, QSample.class, PathInits.DIRECT2);

    public final StringPath sheetName = createString("sheetName");

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QSampleMassOriginalFile(String variable) {
        this(SampleMassOriginalFile.class, forVariable(variable), INITS);
    }

    public QSampleMassOriginalFile(Path<? extends SampleMassOriginalFile> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleMassOriginalFile(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleMassOriginalFile(PathMetadata metadata, PathInits inits) {
        this(SampleMassOriginalFile.class, metadata, inits);
    }

    public QSampleMassOriginalFile(Class<? extends SampleMassOriginalFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.language = inits.isInitialized("language") ? new ch.cscf.jeci.domain.entities.thesaurus.QLanguage(forProperty("language"), inits.get("language")) : null;
        this.protocolVersion = inits.isInitialized("protocolVersion") ? new ch.cscf.jeci.domain.entities.midat.QMIDATProtocolVersion(forProperty("protocolVersion"), inits.get("protocolVersion")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

