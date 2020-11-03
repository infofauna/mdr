package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLabProtocolImport is a Querydsl query type for LabProtocolImport
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLabProtocolImport extends EntityPathBase<LabProtocolImport> {

    private static final long serialVersionUID = -2035653863L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLabProtocolImport labProtocolImport = new QLabProtocolImport("labProtocolImport");

    public final ch.cscf.jeci.domain.entities.base.QBaseEntity _super;

    public final NumberPath<Long> columnId = createNumber("columnId", Long.class);

    public final StringPath columnValue = createString("columnValue");

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

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    //inherited
    public final EnumPath<ch.cscf.jeci.domain.entities.base.EntityStatus> status;

    //inherited
    public final DateTimePath<java.util.Date> updateDate;

    // inherited
    public final ch.cscf.jeci.domain.entities.security.QUser updateUser;

    public QLabProtocolImport(String variable) {
        this(LabProtocolImport.class, forVariable(variable), INITS);
    }

    public QLabProtocolImport(Path<? extends LabProtocolImport> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLabProtocolImport(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLabProtocolImport(PathMetadata metadata, PathInits inits) {
        this(LabProtocolImport.class, metadata, inits);
    }

    public QLabProtocolImport(Class<? extends LabProtocolImport> type, PathMetadata metadata, PathInits inits) {
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

