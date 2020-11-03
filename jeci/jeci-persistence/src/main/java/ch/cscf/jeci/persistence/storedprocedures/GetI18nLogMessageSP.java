package ch.cscf.jeci.persistence.storedprocedures;

import ch.cscf.jeci.domain.entities.midat.ProtocolImportHeader;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: henryp
 */
public class GetI18nLogMessageSP extends StoredProcedure {

    private static final String SQL = "appmanager.PKG_LOGMESSAGEHEADERW.p_getlogmessage";

    public GetI18nLogMessageSP(DataSource ds){

        setDataSource(ds);
        setSql(SQL);


        compile();
    }

    public Long execute(ProtocolImportHeader protocolImportHeader, Long userId){
        Map<String, Object> inputs = new HashMap<>(1);

        throw new NotImplementedException();
    }




}
