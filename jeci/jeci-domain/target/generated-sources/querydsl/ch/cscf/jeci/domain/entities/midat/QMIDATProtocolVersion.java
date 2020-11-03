package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMIDATProtocolVersion is a Querydsl query type for MIDATProtocolVersion
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMIDATProtocolVersion extends EntityPathBase<MIDATProtocolVersion> {

    private static final long serialVersionUID = 207997686L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMIDATProtocolVersion mIDATProtocolVersion = new QMIDATProtocolVersion("mIDATProtocolVersion");

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

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    //inherited
    public final NumberPath<Long> id;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue localizedDescriptionThesaurusValue;

    public final ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue protocolType;

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public final BooleanPath usedByDefault = createBoolean("usedByDefault");

    public QMIDATProtocolVersion(String variable) {
        this(MIDATProtocolVersion.class, forVariable(variable), INITS);
    }

    public QMIDATProtocolVersion(Path<? extends MIDATProtocolVersion> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QMIDATProtocolVersion(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QMIDATProtocolVersion(PathMetadata metadata, PathInits inits) {
        this(MIDATProtocolVersion.class, metadata, inits);
    }

    public QMIDATProtocolVersion(Class<? extends MIDATProtocolVersion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new ch.cscf.jeci.domain.entities.base.QBaseEntity(type, metadata, inits);
        this.creationDate = _super.creationDate;
        this.creationUser = _super.creationUser;
        this.dbCreationDate = _super.dbCreationDate;
        this.dbCreationUser = _super.dbCreationUser;
        this.dbUpdateDate = _super.dbUpdateDate;
        this.dbUpdateUser = _super.dbUpdateUser;
        this.id = _super.id;
        this.localizedDescriptionThesaurusValue = inits.isInitialized("localizedDescriptionThesaurusValue") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("localizedDescriptionThesaurusValue"), inits.get("localizedDescriptionThesaurusValue")) : null;
        this.protocolType = inits.isInitialized("protocolType") ? new ch.cscf.jeci.domain.entities.thesaurus.QThesaurusValue(forProperty("protocolType"), inits.get("protocolType")) : null;
        this.status = _super.status;
        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;
    }

}

