package ch.cscf.jeci.domain.entities.utility;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTaskStatus is a Querydsl query type for TaskStatus
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTaskStatus extends EntityPathBase<TaskStatus> {

    private static final long serialVersionUID = -1879937065L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTaskStatus taskStatus = new QTaskStatus("taskStatus");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    //inherited
    public final DateTimePath<java.util.Date> creationDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser creationUser;

    public final StringPath currentStep = createString("currentStep");

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

    public final NumberPath<Integer> numOfRecords = createNumber("numOfRecords", Integer.class);

    public final NumberPath<Integer> percentageProcessed = createNumber("percentageProcessed", Integer.class);

    public final NumberPath<Integer> secondsLeft = createNumber("secondsLeft", Integer.class);

    public final NumberPath<Integer> secondsTotal = createNumber("secondsTotal", Integer.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QTaskStatus(String variable) {
        this(TaskStatus.class, forVariable(variable), INITS);
    }

    public QTaskStatus(Path<? extends TaskStatus> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QTaskStatus(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QTaskStatus(PathMetadata metadata, PathInits inits) {
        this(TaskStatus.class, metadata, inits);
    }

    public QTaskStatus(Class<? extends TaskStatus> type, PathMetadata metadata, PathInits inits) {
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

