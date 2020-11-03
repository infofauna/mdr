package ch.cscf.jeci.domain.entities.systematics;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTaxon is a Querydsl query type for Taxon
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTaxon extends EntityPathBase<Taxon> {

    private static final long serialVersionUID = -2111811883L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTaxon taxon = new QTaxon("taxon");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final StringPath author = createString("author");

    public final StringPath authorYear = createString("authorYear");

    public final SetPath<Taxon, QTaxon> children = this.<Taxon, QTaxon>createSet("children", Taxon.class, QTaxon.class, PathInits.DIRECT2);

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

    public final ListPath<TaxonDesignation, QTaxonDesignation> designations = this.<TaxonDesignation, QTaxonDesignation>createList("designations", TaxonDesignation.class, QTaxonDesignation.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id;

    public final QTaxon parent;

    public final NumberPath<Long> parentTaxonId = createNumber("parentTaxonId", Long.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    public final QTaxon synonymTarget;

    public final QTaxonomicRank taxonomicRank;

    public final NumberPath<Long> taxonomicRankId = createNumber("taxonomicRankId", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public final StringPath year = createString("year");

    public QTaxon(String variable) {
        this(Taxon.class, forVariable(variable), INITS);
    }

    public QTaxon(Path<? extends Taxon> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QTaxon(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QTaxon(PathMetadata metadata, PathInits inits) {
        this(Taxon.class, metadata, inits);
    }

    public QTaxon(Class<? extends Taxon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.parent = inits.isInitialized("parent") ? new QTaxon(forProperty("parent"), inits.get("parent")) : null;
        this.status = _super.status;
        this.synonymTarget = inits.isInitialized("synonymTarget") ? new QTaxon(forProperty("synonymTarget"), inits.get("synonymTarget")) : null;
        this.taxonomicRank = inits.isInitialized("taxonomicRank") ? new QTaxonomicRank(forProperty("taxonomicRank"), inits.get("taxonomicRank")) : null;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

