package ch.cscf.jeci.persistence.storedprocedures.midat;

import ch.cscf.jeci.persistence.daos.interfaces.midat.ProtocolImportDAO;
import ch.cscf.jeci.persistence.storedprocedures.StoredProcedure;
import org.springframework.jdbc.core.SqlParameter;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * This SP deletes a "protocol" entry for a given protocol header.
 * The protocol version id determines which entry has to be deleted : the lab protocol, evaluation grid, or the field protocol.
 * For the lab protocol, the header and everything else that depends on it is deleted. FOr the other 2, only the additional data is deleted and the api is untouched.
 * @author: henryp
 */
public class DeleteSampleHeaderSP extends StoredProcedure {

    private static final String SQL = "midat.PKG_SAMPLEHEADERW.P_DELETEPROTOCOLENTRY";

    public DeleteSampleHeaderSP(DataSource ds){

        setDataSource(ds);
        setSql(SQL);

        declareParameter(new SqlParameter(ProtocolImportDAO.IN_SAMPLE_HEADER_ID, Types.BIGINT));
        compile();
    }

    public void execute(Long sampleHeaderID){
        Map<String, Object> inputs = new HashMap<>(2);
        inputs.put(ProtocolImportDAO.IN_SAMPLE_HEADER_ID, sampleHeaderID);
        execute(inputs);
    }
}
