package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QImportMassDataDetail is a Querydsl query type for ImportMassDataDetail
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QImportMassDataDetail extends EntityPathBase<ImportMassDataDetail> {

    private static final long serialVersionUID = -364056699L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QImportMassDataDetail importMassDataDetail = new QImportMassDataDetail("importMassDataDetail");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final StringPath calledplace = createString("calledplace");

    public final StringPath canton = createString("canton");

    public final StringPath codeprecision = createString("codeprecision");

    public final StringPath comment = createString("comment");

    //inherited
    public final DateTimePath<java.util.Date> creationDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser creationUser;

    public final StringPath day = createString("day");

    //inherited
    public final DateTimePath<java.util.Date> dbCreationDate;

    //inherited
    public final StringPath dbCreationUser;

    //inherited
    public final DateTimePath<java.util.Date> dbUpdateDate;

    //inherited
    public final StringPath dbUpdateUser;

    public final StringPath determinator = createString("determinator");

    public final NumberPath<Long> excelLineNumber = createNumber("excelLineNumber", Long.class);

    public final StringPath family = createString("family");

    public final StringPath freq1 = createString("freq1");

    public final StringPath freq2 = createString("freq2");

    public final StringPath freqlum = createString("freqlum");

    public final StringPath genus = createString("genus");

    public final StringPath highertaxon = createString("highertaxon");

    public final StringPath ibchindexprovide = createString("ibchindexprovide");

    //inherited
    public final NumberPath<Long> id;

    public final StringPath indicetype = createString("indicetype");

    public final StringPath locality = createString("locality");

    public final StringPath makroindexprovide = createString("makroindexprovide");

    public final StringPath month = createString("month");

    public final StringPath observers = createString("observers");

    public final StringPath oid = createString("oid");

    public final StringPath oidlink = createString("oidlink");

    public final StringPath period = createString("period");

    public final StringPath precode = createString("precode");

    public final StringPath project = createString("project");

    public final NumberPath<Long> protocolHeaderId = createNumber("protocolHeaderId", Long.class);

    public final StringPath remarkcode1 = createString("remarkcode1");

    public final StringPath remarkcode2 = createString("remarkcode2");

    public final StringPath remarkcode3 = createString("remarkcode3");

    public final StringPath remarkcode4 = createString("remarkcode4");

    public final StringPath remarktext = createString("remarktext");

    public final StringPath reporturl = createString("reporturl");

    public final StringPath samplenumber = createString("samplenumber");

    public final StringPath samplingmethod = createString("samplingmethod");

    public final StringPath spearindexprovide = createString("spearindexprovide");

    public final StringPath species = createString("species");

    public final StringPath stadium = createString("stadium");

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    public final StringPath subspecies = createString("subspecies");

    public final StringPath swisscoordX = createString("swisscoordX");

    public final StringPath swisscoordY = createString("swisscoordY");

    public final StringPath swisscoordZ = createString("swisscoordZ");

    public final StringPath systemprecision = createString("systemprecision");

    public final StringPath taxonDef = createString("taxonDef");

    public final StringPath taxonibch = createString("taxonibch");

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public final ComparablePath<Character> validated = createComparable("validated", Character.class);

    public final StringPath watercourse = createString("watercourse");

    public final StringPath year = createString("year");

    public QImportMassDataDetail(String variable) {
        this(ImportMassDataDetail.class, forVariable(variable), INITS);
    }

    public QImportMassDataDetail(Path<? extends ImportMassDataDetail> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QImportMassDataDetail(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QImportMassDataDetail(PathMetadata metadata, PathInits inits) {
        this(ImportMassDataDetail.class, metadata, inits);
    }

    public QImportMassDataDetail(Class<? extends ImportMassDataDetail> type, PathMetadata metadata, PathInits inits) {
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

