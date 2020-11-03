package ch.cscf.jeci.domain.entities.thesaurus;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author: henryp
 */
public class ThesaurusRealm {

    public static final String CODE_PRINCIPAL = "P";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String realmCode;

    private String realmDesignation;

    private String defaultLanguage;

    private List<String> codeValues = new ArrayList<>(100);

    /**
     * Map by realmCode value, containng entries by language, e.g. ["NE" --> ["FR" --> {entries for Neuchâtel (possibly including synonyms)}]]
     */
    private Map<String, Multimap<String, LocalizedThesaurusEntry>> entries = new HashMap<>();

    /**
     * This map is used as a cache for computed list of entries by language.
     * I.e. it will contain lists of entries for every value of this realm in each language.
     * These lists can be returned "as is" to the UI without further processing.
     * This is typically useful for select lists and menus where all the given values of a given realm
     * must be displayed in a given language.
     * If for example an entry is missing in a given language, this will already contain the entry in the default language.
     * It will also contain only the primary entry in each language.
     * This map is initially empty and is used as a cache :
     * when the entries for a given language are computed from the base map they are stored in this map for future use.
     */
    private ListMultimap<String, LocalizedThesaurusEntry> cachedEntriesByLanguage = ArrayListMultimap.create();


    /**
     * Constructor for a realm.
     * It loads all the entries into the main entries map.
     * @param code
     * @param defaultLanguage
     * @param allEntries
     */
    public ThesaurusRealm(String code, String designation, String defaultLanguage, List<LocalizedThesaurusEntry> allEntries){

        logger.info("Loading Thesaurus Realm \"{}\" with default language {}...", code, defaultLanguage);

        this.realmCode =code;
        this.realmDesignation=designation;
        this.defaultLanguage=defaultLanguage;

        for(LocalizedThesaurusEntry entry : allEntries){

            if(!codeValues.contains(entry.getCode())){
                codeValues.add(entry.getCode());
                //logger.debug("Added realmCode value \"{}\".", entry.getCode());
            }

            if(!entries.containsKey(entry.getCode())){
                HashMultimap<String, LocalizedThesaurusEntry> multimap = HashMultimap.create();
                entries.put(entry.getCode(), multimap);
            }

            entries.get(entry.getCode()).put(entry.getLanguage(), entry);
        }

        String codes = String.join(", ", codeValues);
        logger.info("Added code values for realm "+this.realmCode+" : "+codes);
    }

    /**
     * Returns all the entries in a given language.
     * The list is furtehr processed : for each known value of the realm, an entry is looked for in the given language.
     * If no entry is found for any code in the given language, an entry is looked for in the realm's default language.
     * If no entry is found in the default language, an exception will be raised, because it would mean a structural flaw in the data.
     * @param language
     * @return
     *
     */
    public List<LocalizedThesaurusEntry> getEntries(String language){

        List<LocalizedThesaurusEntry> cachedEntries = cachedEntriesByLanguage.get(language);
        if(!cachedEntries.isEmpty()){
            return cachedEntries;
        }

        List<LocalizedThesaurusEntry> result = new ArrayList<>(codeValues.size());

        // For each realmCode value in this realm, try to find a thesaurus entry in the desired language
        for(String codeValue : codeValues){
            result.add(getEntryForCode(codeValue, language));
        }
        //cache the computed list
        cachedEntries.addAll(result);
        return result;
    }

    public LocalizedThesaurusEntry getEntryForCode(String codeValue, String language) {

        //try to get an entry in the required language
        Multimap<String, LocalizedThesaurusEntry> entriesForValue = entries.get(codeValue);

        if(entriesForValue == null){
            throw new IllegalArgumentException(MessageFormat.format("The realm {0} does not contain any value with the code {1}.", realmCode, codeValue));
        }

        Collection<LocalizedThesaurusEntry> entriesInRequiredLanguage = entriesForValue.get(language);
        if(entriesInRequiredLanguage != null && !entriesInRequiredLanguage.isEmpty()){
            return tryFindPrincipalEntry(codeValue, language);
        }

        //Not found in desired language, try default language
        Collection<LocalizedThesaurusEntry> entriesInDefaultLanguage = entriesForValue.get(defaultLanguage);
        if(entriesInDefaultLanguage != null && !entriesInDefaultLanguage.isEmpty()){
            return tryFindPrincipalEntry(codeValue, defaultLanguage);
        }

        //problem...
        throw new IllegalStateException(MessageFormat.format(
                "For the thresaurus realm \"{0}\" and the code value \"{1}\" there was no entry found in the required language ({2}) nor the default language ({3}). The state of the DB is incoherent.",
                realmCode, codeValue, language, defaultLanguage));
    }

    private LocalizedThesaurusEntry tryFindPrincipalEntry(String codeValue, String language){
        for(LocalizedThesaurusEntry entry : entries.get(codeValue).get(language)){
            if(entry.getType().equals(CODE_PRINCIPAL)){
                return entry;
            }
        }
        return(entries.get(codeValue).get(language).iterator().next());
    }

    public String getRealmCode() {
        return realmCode;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public String getRealmDesignation() {
        return realmDesignation;
    }
}
