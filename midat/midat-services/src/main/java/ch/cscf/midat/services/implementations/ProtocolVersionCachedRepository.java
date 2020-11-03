package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.entities.midat.MIDATProtocolVersion;
import ch.cscf.jeci.domain.entities.thesaurus.LocalizedThesaurusEntry;
import ch.cscf.jeci.persistence.daos.interfaces.midat.MIDATProtocolVersionDAO;
import ch.cscf.jeci.persistence.daos.thesaurus.interfaces.ThesaurusReadOnlyService;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: henryp
 */
@Service
public class ProtocolVersionCachedRepository implements ch.cscf.midat.services.interfaces.ProtocolVersionCachedRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThesaurusReadOnlyService thesaurusReadOnlyService;

    @Autowired
    private MIDATProtocolVersionDAO protocolVersionDAO;

    private Map<Long, String> versionToTypeCache = new HashMap();
    private Multimap<String, Long> typeToVersionCache = HashMultimap.create();

    @PostConstruct
    public void init(){

        logger.info("Pre-loading the MIDAT protocol version to protocol type map...");
        for(LocalizedThesaurusEntry typeEntry : thesaurusReadOnlyService.getEntriesForRealm(ThesaurusCodes.REALM_MIDATPROTO, "fr")){
            String protocolTypeCode = typeEntry.getCode();
            logger.info("\tGetting protocol versions for protocol type : "+protocolTypeCode);
            for(MIDATProtocolVersion version : protocolVersionDAO.findForType(typeEntry.getValueId())){
                logger.info("\t\t{} --> version [{} {}]", protocolTypeCode, version.getId(), version.getDescription());
                versionToTypeCache.put(version.getId(), protocolTypeCode);
                typeToVersionCache.put(protocolTypeCode, version.getId());
            }
        }

    }


    @Override
    public boolean isOfType(Long protocolVersionId, String typeCode) {
        String typeCodeFound = versionToTypeCache.get(protocolVersionId);
        return typeCodeFound != null && typeCode.equals(typeCodeFound);
    }

    @Override
    public String getTypeCodeForVersionId(Long protocolVersionId) {
        return versionToTypeCache.get(protocolVersionId);
    }
}
