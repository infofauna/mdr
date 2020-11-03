package ch.cscf.jeci.domain.entities.thesaurus;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QThesaurusValue is a Querydsl query type for ThesaurusValue
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QThesaurusValue extends EntityPathBase<ThesaurusValue> {

    private static final long serialVersionUID = -1262910007L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QThesaurusValue thesaurusValue = new QThesaurusValue("thesaurusValue");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final StringPath code = createString("code");

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

    public final ListPath<LocalizedThesaurusEntry, QLocalizedThesaurusEntry> localizedEntries = this.<LocalizedThesaurusEntry, QLocalizedThesaurusEntry>createList("localizedEntries", LocalizedThesaurusEntry.class, QLocalizedThesaurusEntry.class, PathInits.DIRECT2);

    public final QThesaurusValue parentValue;

    public final QThesaurusCodesRealm realm;

    public final NumberPath<Long> sortOrder = createNumber("sortOrder", Long.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QThesaurusValue(String variable) {
        this(ThesaurusValue.class, forVariable(variable), INITS);
    }

    public QThesaurusValue(Path<? extends ThesaurusValue> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QThesaurusValue(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QThesaurusValue(PathMetadata metadata, PathInits inits) {
        this(ThesaurusValue.class, metadata, inits);
    }

    public QThesaurusValue(Class<? extends ThesaurusValue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.parentValue = inits.isInitialized("parentValue") ? new QThesaurusValue(forProperty("parentValue"), inits.get("parentValue")) : null;
        this.realm = inits.isInitialized("realm") ? new QThesaurusCodesRealm(forProperty("realm"), inits.get("realm")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

