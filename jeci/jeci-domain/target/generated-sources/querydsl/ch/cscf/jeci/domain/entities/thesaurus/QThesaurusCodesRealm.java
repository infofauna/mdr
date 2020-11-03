package ch.cscf.jeci.domain.entities.thesaurus;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QThesaurusCodesRealm is a Querydsl query type for ThesaurusCodesRealm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QThesaurusCodesRealm extends EntityPathBase<ThesaurusCodesRealm> {

    private static final long serialVersionUID = 736363697L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QThesaurusCodesRealm thesaurusCodesRealm = new QThesaurusCodesRealm("thesaurusCodesRealm");

    public final QAbstractCodesRealm _super;

    //inherited
    public final StringPath code;

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

    // inherited
    public final QLanguage defaultLanguage;

    //inherited
    public final NumberPath<Long> defaultLanguageId;

    //inherited
    public final StringPath designation;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final NumberPath<Long> parentId;

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QThesaurusCodesRealm(String variable) {
        this(ThesaurusCodesRealm.class, forVariable(variable), INITS);
    }

    public QThesaurusCodesRealm(Path<? extends ThesaurusCodesRealm> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QThesaurusCodesRealm(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QThesaurusCodesRealm(PathMetadata metadata, PathInits inits) {
        this(ThesaurusCodesRealm.class, metadata, inits);
    }

    public QThesaurusCodesRealm(Class<? extends ThesaurusCodesRealm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QAbstractCodesRealm(type, metadata, inits);
        this.code = _super.code;
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.defaultLanguage = _super.defaultLanguage;
        this.defaultLanguageId = _super.defaultLanguageId;
        this.designation = _super.designation;
        this.id = _super.id;
        this.parentId = _super.parentId;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

