package ch.cscf.midat.services.interfaces;

/**
 * @author: henryp
 */
public interface ProtocolVersionCachedRepository {

    boolean isOfType(Long protocolVersionId, String typeCode);

    String getTypeCodeForVersionId(Long protocolVersionId);

}
