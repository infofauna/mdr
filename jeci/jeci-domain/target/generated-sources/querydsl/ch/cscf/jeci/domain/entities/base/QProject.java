package ch.cscf.jeci.domain.entities.base;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProject is a Querydsl query type for Project
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProject extends EntityPathBase<Project> {

    private static final long serialVersionUID = -676565126L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProject project = new QProject("project");

    public final QBaseEntity _super;

    public final StringPath code = createString("code");

    public final StringPath codeCh = createString("codeCh");

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

    public final NumberPath<Long> debutAnnee = createNumber("debutAnnee", Long.class);

    public final NumberPath<Long> debutJour = createNumber("debutJour", Long.class);

    public final NumberPath<Long> debutMois = createNumber("debutMois", Long.class);

    public final StringPath description = createString("description");

    public final StringPath designation = createString("designation");

    public final DateTimePath<java.util.Date> echeanceBlocage = createDateTime("echeanceBlocage", java.util.Date.class);

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final NumberPath<Long> finAnnee = createNumber("finAnnee", Long.class);

    public final NumberPath<Long> finJour = createNumber("finJour", Long.class);

    public final NumberPath<Long> finMois = createNumber("finMois", Long.class);

    //inherited
    public final NumberPath<Long> id;

    public final QInstitution mandataryInstitution;

    public final NumberPath<Long> mandataryInstitutionId = createNumber("mandataryInstitutionId", Long.class);

    public final StringPath mandataryInstitutionName = createString("mandataryInstitutionName");

    public final NumberPath<Long> mandataryInstitutionPersonId = createNumber("mandataryInstitutionPersonId", Long.class);

    public final StringPath mandataryInstitutionRespFirstName = createString("mandataryInstitutionRespFirstName");

    public final StringPath mandataryInstitutionRespLastName = createString("mandataryInstitutionRespLastName");

    public final QInstitution principalInstitution;

    public final NumberPath<Long> principalInstitutionId = createNumber("principalInstitutionId", Long.class);

    public final StringPath principalInstitutionName = createString("principalInstitutionName");

    public final NumberPath<Long> principalInstitutionPersonId = createNumber("principalInstitutionPersonId", Long.class);

    public final StringPath principalInstitutionRespFirstName = createString("principalInstitutionRespFirstName");

    public final StringPath principalInstitutionRespLastName = createString("principalInstitutionRespLastName");

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue projectLima;

    public final NumberPath<Long> projectLimaId = createNumber("projectLimaId", Long.class);

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue projectOrigin;

    public final NumberPath<Long> projectOriginId = createNumber("projectOriginId", Long.class);

    public final NumberPath<Long> projectProjectId = createNumber("projectProjectId", Long.class);

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue projectType;

    public final NumberPath<Long> projectTypeId = createNumber("projectTypeId", Long.class);

    public final ListPath<ch.cscf.jeci.domain.entities.midat.sample.Sample, ch.cscf.jeci.domain.entities.midat.sample.QSample> samples = this.<ch.cscf.jeci.domain.entities.midat.sample.Sample, ch.cscf.jeci.domain.entities.midat.sample.QSample>createList("samples", ch.cscf.jeci.domain.entities.midat.sample.Sample.class, ch.cscf.jeci.domain.entities.midat.sample.QSample.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    //inherited
    public final EnumPath<EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public final StringPath url = createString("url");

    public final BooleanPath voluntaryWork = createBoolean("voluntaryWork");

    public QProject(String variable) {
        this(Project.class, forVariable(variable), INITS);
    }

    public QProject(Path<? extends Project> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProject(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProject(PathMetadata metadata, PathInits inits) {
        this(Project.class, metadata, inits);
    }

    public QProject(Class<? extends Project> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.mandataryInstitution = inits.isInitialized("mandataryInstitution") ? new QInstitution(forProperty("mandataryInstitution"), inits.get("mandataryInstitution")) : null;
        this.principalInstitution = inits.isInitialized("principalInstitution") ? new QInstitution(forProperty("principalInstitution"), inits.get("principalInstitution")) : null;
        this.projectLima = inits.isInitialized("projectLima") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("projectLima"), inits.get("projectLima")) : null;
        this.projectOrigin = inits.isInitialized("projectOrigin") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("projectOrigin"), inits.get("projectOrigin")) : null;
        this.projectType = inits.isInitialized("projectType") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("projectType"), inits.get("projectType")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

