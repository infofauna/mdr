package ch.cscf.jeci.persistence.daos.thesaurus.implementation;

import ch.cscf.jeci.domain.entities.thesaurus.LocalizedThesaurusEntry;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusCodesRealm;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusRealm;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.LocalizedThesaurusEntryDAO;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.ThesaurusCodesRealmDAO;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author: henryp
 */
@Service
public class ThesaurusReadOnlyService implements ch.cscf.jeci.persistence.daos.thesaurus.interfaces.ThesaurusReadOnlyService {

    public static final int REALMS_MAP_INITIAL_CAPACITY = 200;
    public static final int VALUES_MAPS_INITIAL_CAPACITY = 1000;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThesaurusCodesRealmDAO thesaurusCodesRealmDAO;

    @Autowired
    private LocalizedThesaurusEntryDAO localizedThesaurusEntryDAO;

    private Map<String, ThesaurusRealm> realms = new HashMap<>(REALMS_MAP_INITIAL_CAPACITY);

    private Map<Long, String> valueIdToRealmCode = new HashMap<>(VALUES_MAPS_INITIAL_CAPACITY);

    @Autowired
    private org.springframework.transaction.support.TransactionTemplate tt;

    @PostConstruct
    public void init(){
       refreshCache();
    }

    @Override
    public void refreshCache() {
        tt.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                status.setRollbackOnly();
                loadThesaurusData();
            }
        });
    }

    private void loadThesaurusData(){
        long startTime = System.currentTimeMillis();

        realms = new HashMap<>(REALMS_MAP_INITIAL_CAPACITY);
        Collection<ThesaurusCodesRealm> allRealms = thesaurusCodesRealmDAO.list("code", Lists.newArrayList("defaultLanguage"));

        for(ThesaurusCodesRealm realm : allRealms){
            String realmCode = realm.getCode();
            List<LocalizedThesaurusEntry> realmEntries = localizedThesaurusEntryDAO.findEntriesForRealm(realmCode);
            realms.put(realmCode, new ThesaurusRealm(realmCode, realm.getDesignation(), realm.getDefaultLanguage().getCode(), realmEntries));
        }

        long duration = System.currentTimeMillis() - startTime;

        logger.info("Finished loading Thesaurus data in memory, in "+duration+" ms.");
    }

    @Override
    public ThesaurusRealm getRealm(String realmCode) {
        return realms.get(realmCode);
    }

    @Override
    public List<ThesaurusRealm> getRealms() {
        List<ThesaurusRealm> realmsList = new ArrayList<>(realms.values().size());
        realmsList.addAll(realms.values());
        Collections.sort(realmsList, new Comparator<ThesaurusRealm>(){
            @Override
            public int compare(ThesaurusRealm t1, ThesaurusRealm t2) {
                return t1.getRealmCode().compareTo(t2.getRealmCode());
            }
        } );
        return realmsList;
    }

    @Override
    public List<LocalizedThesaurusEntry> getChildEntriesForRealmWithParent(String realmCode, Long parentId, String language) {
        List<LocalizedThesaurusEntry> allEntries = getEntriesForRealm(realmCode, language);
        List<LocalizedThesaurusEntry> filteredList = new ArrayList<>();
        for(LocalizedThesaurusEntry entry : allEntries){
            if(entry.getParentValueId().equals(parentId)){
                filteredList.add(entry);
            }
        }
        return filteredList;
    }

    @Override
    public Long getValueId(String realmCode, String valueCode) {
        ThesaurusRealm realm = realms.get(realmCode);
        if(realm == null){
            throw new IllegalArgumentException("No thesaurus realm with the code \""+realmCode+"\". Try reloading the thesaurus data.");
        }

        List<LocalizedThesaurusEntry> entries = realm.getEntries(realm.getDefaultLanguage());

        for(LocalizedThesaurusEntry entry : entries){
            if(entry.getCode().equals(valueCode)){
                return entry.getValueId();
            }
        }
        throw new IllegalArgumentException("No thesaurus value with the code \""+valueCode+"\" in the realm with the code \""+realmCode+"\". Try reloading the thesaurus data.");
    }

    @Override
    public String getRealmCodeForValue(Long valueId) {
        String cachedValue  = valueIdToRealmCode.get(valueId);
        if(cachedValue !=null){
            return cachedValue;
        }

        String realmCode = localizedThesaurusEntryDAO.getRealmCodeForValueId(valueId);
        valueIdToRealmCode.put(valueId, realmCode);

        return realmCode;
    }

    @Override
    public LocalizedThesaurusEntry getLocalizedEntry(String realmCode, String valueCode, String language) {
        return getRealm(realmCode).getEntryForCode(valueCode, language);
    }

    @Override
    public String getLocalizedString(String realmCode, String valueCode, String language) {
        return getLocalizedEntry(realmCode, valueCode, language).getDesignation();
    }

    @Override
    public List<LocalizedThesaurusEntry> getEntriesForRealm(String realmCode, String language) {
        return realms.get(realmCode).getEntries(language);
    }
}
