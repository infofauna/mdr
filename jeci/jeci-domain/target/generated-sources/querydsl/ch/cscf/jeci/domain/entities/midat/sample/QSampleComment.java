package ch.cscf.jeci.domain.entities.midat.sample;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSampleComment is a Querydsl query type for SampleComment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSampleComment extends EntityPathBase<SampleComment> {

    private static final long serialVersionUID = -1411555934L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSampleComment sampleComment1 = new QSampleComment("sampleComment1");

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

    //inherited
    public final NumberPath<Long> id;

    public final QSample sample;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue sampleComment;

    public final NumberPath<Long> sampleCommentId = createNumber("sampleCommentId", Long.class);

    public final NumberPath<Long> sampleId = createNumber("sampleId", Long.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QSampleComment(String variable) {
        this(SampleComment.class, forVariable(variable), INITS);
    }

    public QSampleComment(Path<? extends SampleComment> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleComment(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSampleComment(PathMetadata metadata, PathInits inits) {
        this(SampleComment.class, metadata, inits);
    }

    public QSampleComment(Class<? extends SampleComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.sample = inits.isInitialized("sample") ? new QSample(forProperty("sample"), inits.get("sample")) : null;
        this.sampleComment = inits.isInitialized("sampleComment") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("sampleComment"), inits.get("sampleComment")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

