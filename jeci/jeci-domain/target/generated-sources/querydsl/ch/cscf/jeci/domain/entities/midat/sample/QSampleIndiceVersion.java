package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSampleIndiceVersion is a Querydsl query type for SampleIndiceVersion
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleIndiceVersion extends EntityPathBase<SampleIndiceVersion> {

    private static final long serialVersionUID = -504563217L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSampleIndiceVersion sampleIndiceVersion = new QSampleIndiceVersion("sampleIndiceVersion");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    //inherited
    public final DateTimePath<java.util.Date> creationDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser creationUser;

    public final ComparablePath<Character> current = createComparable("current", Character.class);

    //inherited
    public final DateTimePath<java.util.Date> dbCreationDate;

    //inherited
    public final StringPath dbCreationUser;

    //inherited
    public final DateTimePath<java.util.Date> dbUpdateDate;

    //inherited
    public final StringPath dbUpdateUser;

    //inherited
    public final NumberPath<Long> id;

    public final NumberPath<Integer> midatIndice = createNumber("midatIndice", Integer.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QSampleIndiceVersion(String variable) {
        this(SampleIndiceVersion.class, forVariable(variable), INITS);
    }

    public QSampleIndiceVersion(Path<? extends SampleIndiceVersion> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleIndiceVersion(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleIndiceVersion(PathMetadata metadata, PathInits inits) {
        this(SampleIndiceVersion.class, metadata, inits);
    }

    public QSampleIndiceVersion(Class<? extends SampleIndiceVersion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

