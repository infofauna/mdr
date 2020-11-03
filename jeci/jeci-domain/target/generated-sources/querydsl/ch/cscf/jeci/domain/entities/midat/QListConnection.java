package ch.cscf.jeci.domain.entities.midat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QListConnection is a Querydsl query type for ListConnection
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QListConnection extends EntityPathBase<ListConnection> {

    private static final long serialVersionUID = -1693387603L;

    public static final QListConnection listConnection = new QListConnection("listConnection");

    public final StringPath address = createString("address");

    public final StringPath application = createString("application");

    public final NumberPath<Long> connectioncount = createNumber("connectioncount", Long.class);

    public final StringPath creationDate = createString("creationDate");

    public final StringPath firstconnection = createString("firstconnection");

    public final StringPath firstname = createString("firstname");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastconnection = createString("lastconnection");

    public final StringPath lastname = createString("lastname");

    public final BooleanPath ldap = createBoolean("ldap");

    public final StringPath locality = createString("locality");

    public final StringPath login = createString("login");

    public QListConnection(String variable) {
        super(ListConnection.class, forVariable(variable));
    }

    public QListConnection(Path<? extends ListConnection> path) {
        super(path.getType(), path.getMetadata());
    }

    public QListConnection(PathMetadata metadata) {
        super(ListConnection.class, metadata);
    }

}

