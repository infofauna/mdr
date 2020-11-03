package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.midat.ProtocolImportHeader;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.Map;

/**
 * @author: kanso
 */
public interface ProtocolImportHeaderDAO extends GenericEntityDAO<ProtocolImportHeader> {

    Long insertHeaderInfo(Map<String,String> headerData);
    Long persistWithRet(ProtocolImportHeader entity);
    Long getSequenceNextValue();
}
