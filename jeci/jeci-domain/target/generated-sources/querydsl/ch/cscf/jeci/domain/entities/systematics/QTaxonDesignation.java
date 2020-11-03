package ch.cscf.jeci.domain.entities.systematics;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTaxonDesignation is a Querydsl query type for TaxonDesignation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTaxonDesignation extends EntityPathBase<TaxonDesignation> {

    private static final long serialVersionUID = -1631055390L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTaxonDesignation taxonDesignation = new QTaxonDesignation("taxonDesignation");

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

    public final StringPath designation = createString("designation");

    public final NumberPath<Long> designationTypeId = createNumber("designationTypeId", Long.class);

    //inherited
    public final NumberPath<Long> id;

    public final NumberPath<Long> languageId = createNumber("languageId", Long.class);

    public final QTaxon masterTaxon;

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    public final NumberPath<Long> taxonId = createNumber("taxonId", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QTaxonDesignation(String variable) {
        this(TaxonDesignation.class, forVariable(variable), INITS);
    }

    public QTaxonDesignation(Path<? extends TaxonDesignation> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QTaxonDesignation(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QTaxonDesignation(PathMetadata metadata, PathInits inits) {
        this(TaxonDesignation.class, metadata, inits);
    }

    public QTaxonDesignation(Class<? extends TaxonDesignation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.masterTaxon = inits.isInitialized("masterTaxon") ? new QTaxon(forProperty("masterTaxon"), inits.get("masterTaxon")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

