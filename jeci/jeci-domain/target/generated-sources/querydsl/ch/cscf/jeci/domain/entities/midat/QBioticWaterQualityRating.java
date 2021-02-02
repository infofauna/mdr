package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBioticWaterQualityRating is a Querydsl query type for BioticWaterQualityRating
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBioticWaterQualityRating extends EntityPathBase<BioticWaterQualityRating> {

    private static final long serialVersionUID = -1756262884L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBioticWaterQualityRating bioticWaterQualityRating = new QBioticWaterQualityRating("bioticWaterQualityRating");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue bgColor;

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

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue designation;

    public final NumberPath<Double> fromValue = createNumber("fromValue", Double.class);

    //inherited
    public final NumberPath<Long> id;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue indexType;

    public final StringPath rangeLegendText = createString("rangeLegendText");

    public final ch.cscf.jeci.domain.entities.midat.sample.QSampleIndiceVersion sampleIndiceVersion;

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue textColor;

    public final NumberPath<Double> toValue = createNumber("toValue", Double.class);

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QBioticWaterQualityRating(String variable) {
        this(BioticWaterQualityRating.class, forVariable(variable), INITS);
    }

    public QBioticWaterQualityRating(Path<? extends BioticWaterQualityRating> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QBioticWaterQualityRating(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QBioticWaterQualityRating(PathMetadata metadata, PathInits inits) {
        this(BioticWaterQualityRating.class, metadata, inits);
    }

    public QBioticWaterQualityRating(Class<? extends BioticWaterQualityRating> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.bgColor = inits.isInitialized("bgColor") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("bgColor"), inits.get("bgColor")) : null;
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.designation = inits.isInitialized("designation") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("designation"), inits.get("designation")) : null;
        this.id = _super.id;
        this.indexType = inits.isInitialized("indexType") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("indexType"), inits.get("indexType")) : null;
        this.sampleIndiceVersion = inits.isInitialized("sampleIndiceVersion") ? new ch.cscf.jeci.domain.entities.midat.sample.QSampleIndiceVersion(forProperty("sampleIndiceVersion"), inits.get("sampleIndiceVersion")) : null;
        this.status = _super.status;
        this.textColor = inits.isInitialized("textColor") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("textColor"), inits.get("textColor")) : null;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

