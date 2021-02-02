package ch.cscf.jeci.web.controllers.api;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.entities.midat.sample.SampleIndiceVersion;
import ch.cscf.jeci.domain.entities.thesaurus.LocalizedThesaurusEntry;
import ch.cscf.jeci.persistence.daos.thesaurus.interfaces.ThesaurusReadOnlyService;
import ch.cscf.jeci.services.general.I18nService;
import ch.cscf.midat.services.interfaces.IndiceVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: henryp
 */
@Controller
@RequestMapping("/api/options")
public class SelectOptionsJSONController extends AbstractRestController {

    @Autowired
    private ThesaurusReadOnlyService thesaurusReadOnlyService;

    @Autowired
    private IndiceVersionService indiceVersionService;


    @Autowired
    private I18nService i18NService;

    @RequestMapping("/references/{referenceSystemId}/precision-levels")
    public @ResponseBody List<Option> getDetailsLevelsForReferenceSystem(@PathVariable Long referenceSystemId){

        List<LocalizedThesaurusEntry> entries = thesaurusReadOnlyService.getChildEntriesForRealmWithParent(ThesaurusCodes.REALM_SYSTLPREC, referenceSystemId, i18NService.currentLanguageCode());
        List<Option> options = new ArrayList<>();
        for(LocalizedThesaurusEntry entry: entries){
            Option option = new Option();
            map(entry, option);
            options.add(option);
        }

        return options;
    }

    @RequestMapping("/midat-index-types")
    public @ResponseBody List<Option> getMidatIndexTypes(){

        List<LocalizedThesaurusEntry> entries = thesaurusReadOnlyService.getEntriesForRealm(ThesaurusCodes.REALM_MIDATINDICE, i18NService.currentLanguageCode());


        List<SampleIndiceVersion> activeIndexes = indiceVersionService.getActiveVersions();

        List<Option> options = new ArrayList<>();

        for(LocalizedThesaurusEntry entry : entries){
            Option option = new Option();

            //don't add the all value ;
            if(entry.getCode().equalsIgnoreCase("ALL")){
                continue;
            }
            // add only active indexes
            List<SampleIndiceVersion> activeIndexesVal = activeIndexes.stream().filter(v -> {
                if (v.getMidatIndice().intValue() == entry.getValueId().intValue() ) {
                    return true;
                }
                return false;
            }).collect(Collectors.toList());

            if(activeIndexesVal.size() == 0){
                continue;
            }

            option.setLabel(entry.getCode());
            option.setValue(entry.getCode());
            options.add(option);
        }
        return options;
    }



    private void map(LocalizedThesaurusEntry thesaurusEntry, Option option){
        option.setValue(thesaurusEntry.getValueId().toString());
        option.setLabel(thesaurusEntry.getDesignation());
    }

    public static class Option {

        public Option() {
        }

        public Option(String value, String label) {
            this.value = value;
            this.label = label;
        }

        private String value, label;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
