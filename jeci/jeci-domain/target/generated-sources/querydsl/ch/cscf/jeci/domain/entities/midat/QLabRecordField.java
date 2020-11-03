package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLabRecordField is a Querydsl query type for LabRecordField
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLabRecordField extends EntityPathBase<LabRecordField> {

    private static final long serialVersionUID = -1803525651L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLabRecordField labRecordField = new QLabRecordField("labRecordField");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final StringPath comment = createString("comment");

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

    public final QLabProtocolFieldMapping fieldMapping;

    public final NumberPath<Long> freqLum = createNumber("freqLum", Long.class);

    public final NumberPath<Long> frequency = createNumber("frequency", Long.class);

    //inherited
    public final NumberPath<Long> id;

    public final ch.cscf.jeci.domain.entities.midat.sample.QSample master;

    public final NumberPath<Long> modifiedFrequency = createNumber("modifiedFrequency", Long.class);

    public final StringPath sampleNumber = createString("sampleNumber");

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public final StringPath stadium = createString("stadium");

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    public final ch.cscf.jeci.domain.entities.systematics.QTaxon taxon;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QLabRecordField(String variable) {
        this(LabRecordField.class, forVariable(variable), INITS);
    }

    public QLabRecordField(Path<? extends LabRecordField> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLabRecordField(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLabRecordField(PathMetadata metadata, PathInits inits) {
        this(LabRecordField.class, metadata, inits);
    }

    public QLabRecordField(Class<? extends LabRecordField> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.fieldMapping = inits.isInitialized("fieldMapping") ? new QLabProtocolFieldMapping(forProperty("fieldMapping"), inits.get("fieldMapping")) : null;
        this.id = _super.id;
        this.master = inits.isInitialized("master") ? new ch.cscf.jeci.domain.entities.midat.sample.QSample(forProperty("master"), inits.get("master")) : null;
        this.status = _super.status;
        this.taxon = inits.isInitialized("taxon") ? new ch.cscf.jeci.domain.entities.systematics.QTaxon(forProperty("taxon"), inits.get("taxon")) : null;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

