package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIndexAvaliabilityCalendar is a Querydsl query type for IndexAvaliabilityCalendar
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QIndexAvaliabilityCalendar extends EntityPathBase<IndexAvaliabilityCalendar> {

    private static final long serialVersionUID = -1027801376L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIndexAvaliabilityCalendar indexAvaliabilityCalendar = new QIndexAvaliabilityCalendar("indexAvaliabilityCalendar");

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

    public final NumberPath<Double> elevationMax = createNumber("elevationMax", Double.class);

    public final NumberPath<Double> elevationMin = createNumber("elevationMin", Double.class);

    public final NumberPath<Integer> endDay = createNumber("endDay", Integer.class);

    public final NumberPath<Integer> endMonth = createNumber("endMonth", Integer.class);

    //inherited
    public final NumberPath<Long> id;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue midatIndice;

    public final NumberPath<Long> midatIndiceId = createNumber("midatIndiceId", Long.class);

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue midatWindow;

    public final NumberPath<Long> midatWindowId = createNumber("midatWindowId", Long.class);

    public final NumberPath<Integer> startDay = createNumber("startDay", Integer.class);

    public final NumberPath<Integer> startMonth = createNumber("startMonth", Integer.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QIndexAvaliabilityCalendar(String variable) {
        this(IndexAvaliabilityCalendar.class, forVariable(variable), INITS);
    }

    public QIndexAvaliabilityCalendar(Path<? extends IndexAvaliabilityCalendar> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QIndexAvaliabilityCalendar(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QIndexAvaliabilityCalendar(PathMetadata metadata, PathInits inits) {
        this(IndexAvaliabilityCalendar.class, metadata, inits);
    }

    public QIndexAvaliabilityCalendar(Class<? extends IndexAvaliabilityCalendar> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.midatIndice = inits.isInitialized("midatIndice") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("midatIndice"), inits.get("midatIndice")) : null;
        this.midatWindow = inits.isInitialized("midatWindow") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("midatWindow"), inits.get("midatWindow")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

