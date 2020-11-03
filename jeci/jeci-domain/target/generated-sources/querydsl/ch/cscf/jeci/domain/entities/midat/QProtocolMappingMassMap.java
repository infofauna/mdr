package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProtocolMappingMassMap is a Querydsl query type for ProtocolMappingMassMap
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProtocolMappingMassMap extends EntityPathBase<ProtocolMappingMassMap> {

    private static final long serialVersionUID = -1545776285L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProtocolMappingMassMap protocolMappingMassMap = new QProtocolMappingMassMap("protocolMappingMassMap");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final StringPath aliasColumnName = createString("aliasColumnName");

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

    public final NumberPath<Long> languageId = createNumber("languageId", Long.class);

    public final NumberPath<Long> mappingFieldId = createNumber("mappingFieldId", Long.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QProtocolMappingMassMap(String variable) {
        this(ProtocolMappingMassMap.class, forVariable(variable), INITS);
    }

    public QProtocolMappingMassMap(Path<? extends ProtocolMappingMassMap> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProtocolMappingMassMap(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProtocolMappingMassMap(PathMetadata metadata, PathInits inits) {
        this(ProtocolMappingMassMap.class, metadata, inits);
    }

    public QProtocolMappingMassMap(Class<? extends ProtocolMappingMassMap> type, PathMetadata metadata, PathInits inits) {
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

