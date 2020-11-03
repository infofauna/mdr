package ch.cscf.jeci.domain.entities.systematics;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTaxonomicRank is a Querydsl query type for TaxonomicRank
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTaxonomicRank extends EntityPathBase<TaxonomicRank> {

    private static final long serialVersionUID = -497874535L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTaxonomicRank taxonomicRank = new QTaxonomicRank("taxonomicRank");

    public final ch.cscf.jeci.domain.entities.thesaurus.QAbstractCodesRealm _super;

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
    public final ch.cscf.jeci.domain.entities.thesaurus.QLanguage defaultLanguage;

    //inherited
    public final NumberPath<Long> defaultLanguageId;

    //inherited
    public final StringPath designation;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final NumberPath<Long> parentId;

    public final QTaxonomicRank parentRank;

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QTaxonomicRank(String variable) {
        this(TaxonomicRank.class, forVariable(variable), INITS);
    }

    public QTaxonomicRank(Path<? extends TaxonomicRank> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QTaxonomicRank(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QTaxonomicRank(PathMetadata metadata, PathInits inits) {
        this(TaxonomicRank.class, metadata, inits);
    }

    public QTaxonomicRank(Class<? extends TaxonomicRank> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.thesaurus.QAbstractCodesRealm(type, metadata, inits);
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
        this.parentRank = inits.isInitialized("parentRank") ? new QTaxonomicRank(forProperty("parentRank"), inits.get("parentRank")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

