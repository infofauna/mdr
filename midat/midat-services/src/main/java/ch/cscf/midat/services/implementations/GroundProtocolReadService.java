package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.dto.midat.GroundProtocolDTO;
import ch.cscf.jeci.domain.entities.midat.GroundProtocolItem;
import ch.cscf.jeci.domain.entities.midat.GroundProtocolMapping;
import ch.cscf.jeci.persistence.daos.interfaces.midat.GroundProtocolItemDAO;
import ch.cscf.jeci.persistence.daos.interfaces.midat.GroundProtocolMappingDAO;
import ch.cscf.jeci.services.general.I18nService;
import ch.cscf.jeci.persistence.daos.thesaurus.interfaces.ThesaurusReadOnlyService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: henryp
 */
@Service
public class GroundProtocolReadService implements ch.cscf.midat.services.interfaces.GroundProtocolReadService {

    List<GroundProtocolMapping> rootMappings;

    @Autowired
    private ThesaurusReadOnlyService thesaurus;

    @Autowired
    private I18nService i18NService;

    @Autowired
    private GroundProtocolItemDAO groundProtocolItemDAO;

    @Autowired
    private GroundProtocolMappingDAO groundProtocolMappingDAO;


    @PostConstruct
    public void initBean(){
        rootMappings = groundProtocolMappingDAO.findAllRootMappingsSorted();
    }


    /**
     * Builds a ground protocol DTO for a given sample ID.
     * It builds a hierarchy of imbricated DTOs that can be used to display the data in a nicely formatted way.
     */

    @Override
    @Transactional
    public GroundProtocolDTO getGroundProtocol(Long sampleId){

       // List<GroundProtocolMapping> rootMappings = groundProtocolMappingDAO.findAllRootMappingsSorted();
        Map<Long, GroundProtocolItem> itemsByMappingId = buildItemsByMappingIdMapForSample(sampleId);

        if(itemsByMappingId.isEmpty()){
            return null;
        }

        GroundProtocolDTO rootDto = new GroundProtocolDTO("ROOT");

        for(GroundProtocolMapping mapping : rootMappings){
            buildGroundMappingDTO(mapping, itemsByMappingId, rootDto);
        }

        if(rootDto.getChildren().isEmpty()){
            return null;
        }

        return rootDto;
    }

    /**
     * Builds a map where the keys are the mapping ID and the values are protocol items
     * @param sampleId
     * @return
     */
    private Map<Long, GroundProtocolItem> buildItemsByMappingIdMapForSample(Long sampleId){

        List<GroundProtocolItem> items = groundProtocolItemDAO.findByParentSample(sampleId);
        return Maps.uniqueIndex(items, item -> item.getSourceMapping().getId());
    }


    /**
     * Recursively build the DTO for a mapping and it's value
     * @param mapping
     * @param itemsByMappingId
     * @param parentDTO
     */
    private void buildGroundMappingDTO(GroundProtocolMapping mapping, Map<Long, GroundProtocolItem> itemsByMappingId, GroundProtocolDTO parentDTO){

        //get the item for that mapping from the items map
        GroundProtocolItem item = itemsByMappingId.get(mapping.getId());

        //no item found and no children to the mapping : dead-end, we can do nothing
        if(item == null && mapping.getChildren().isEmpty()){
            return;
        }

        //get localized label for the mapping
        String label = thesaurus.getLocalizedString(ThesaurusCodes.REALM_MIDATGRND, mapping.getLabel().getCode(), i18NService.currentLanguageCode());

        //type not null --> use the value, add it to the parent
        if(mapping.getType() != null && item != null){
            //for checkbox type mappings the value is the label
            if(mapping.getType().getCode().equals(ThesaurusCodes.DATATYPE_CHECKBOX)){
                if(item.getValue() != null && item.getValue().length()>0) {
                    parentDTO.addValue(label);
                }
            //for other types the value is the actual value of the item
            }else{
                if(item.getValue() != null) {
                    parentDTO.addValue(item.getValue());
                }
            }
        }

        //if mapping has children, build a new DTO and recursively map children
        if(!mapping.getChildren().isEmpty()){

            GroundProtocolDTO dto = new GroundProtocolDTO(label);
            parentDTO.addChild(dto);

            for(GroundProtocolMapping childMapping : mapping.getChildren()){
                buildGroundMappingDTO(childMapping, itemsByMappingId, dto);
            }
        }
    }

}
