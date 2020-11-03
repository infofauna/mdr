package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEvaluationGridItem is a Querydsl query type for EvaluationGridItem
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEvaluationGridItem extends EntityPathBase<EvaluationGridItem> {

    private static final long serialVersionUID = -385368858L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEvaluationGridItem evaluationGridItem = new QEvaluationGridItem("evaluationGridItem");

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

    public final QEvaluationGridFieldMapping mapping;

    public final NumberPath<Long> mappingId = createNumber("mappingId", Long.class);

    public final ch.cscf.jeci.domain.entities.midat.sample.QSample master;

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public final StringPath value = createString("value");

    public QEvaluationGridItem(String variable) {
        this(EvaluationGridItem.class, forVariable(variable), INITS);
    }

    public QEvaluationGridItem(Path<? extends EvaluationGridItem> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QEvaluationGridItem(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QEvaluationGridItem(PathMetadata metadata, PathInits inits) {
        this(EvaluationGridItem.class, metadata, inits);
    }

    public QEvaluationGridItem(Class<? extends EvaluationGridItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.mapping = inits.isInitialized("mapping") ? new QEvaluationGridFieldMapping(forProperty("mapping"), inits.get("mapping")) : null;
        this.master = inits.isInitialized("master") ? new ch.cscf.jeci.domain.entities.midat.sample.QSample(forProperty("master"), inits.get("master")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

