package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.dto.midat.EvaluationGridDTO;
import ch.cscf.jeci.domain.entities.midat.EvaluationGridFieldMapping;
import ch.cscf.jeci.domain.entities.midat.EvaluationGridItem;
import ch.cscf.jeci.persistence.daos.interfaces.midat.EvaluationGridFieldMappingDAO;
import ch.cscf.jeci.persistence.daos.interfaces.midat.EvaluationGridItemDAO;
import ch.cscf.jeci.services.general.I18nService;
import ch.cscf.jeci.persistence.daos.thesaurus.interfaces.ThesaurusReadOnlyService;
import com.google.common.primitives.Chars;
import com.google.common.primitives.Ints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: henryp
 */
@Service
public class EvaluationGridReadService implements ch.cscf.midat.services.interfaces.EvaluationGridReadService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final int ROWS=12, COLS=8;

    //mapping rows index from the DB (that are meant to parse the excel file) to absolute indexes
    //leaving the first row and the first col for headers
    List<Integer> rowIndexMapping = Ints.asList(new int[]{0, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20});
    List<Character> colIndexMapping = Chars.asList(new char[]{' ','B','E','F','G','H','I','J'});



    @Autowired
    private EvaluationGridItemDAO gridItemDAO;

    @Autowired
    private EvaluationGridFieldMappingDAO mappingDAO;

    @Autowired
    private ThesaurusReadOnlyService thesaurus;

    @Autowired
    private I18nService i18NService;

    private EvaluationGridFieldMapping[][] buildMappingTable(){

        EvaluationGridFieldMapping[][] mappingTable = new EvaluationGridFieldMapping[ROWS][COLS];

        Collection<EvaluationGridFieldMapping> mappings = mappingDAO.getAllMappingsOrderedByRowAndCol();

        for(EvaluationGridFieldMapping mapping : mappings){

            int row = rowIndexMapping.indexOf(mapping.getRowIndex());
            int col = colIndexMapping.indexOf(mapping.getColIndex());

            try {
                mappingTable[row][col] = mapping;
            }catch(ArrayIndexOutOfBoundsException e){
                logger.info("The mapping with col={} and row={} is not contained in the boundaries of this mapping.");
            }
        }

        return mappingTable;
    }

    @Override
    public EvaluationGridDTO getEvalutationGrid(Long sampleId) {

        List<EvaluationGridItem> items = gridItemDAO.findBySampleId(sampleId);
        Map<Long, EvaluationGridItem> itemsByMappingId = buildMapOfItemsByMappingId(items);

        if(items.isEmpty()){
            return null;
        }

        EvaluationGridFieldMapping[][] mappingTable = buildMappingTable();

        EvaluationGridMapper mapper = new EvaluationGridMapper(mappingTable, itemsByMappingId);
        String[][] grid = mapper.buildGrid();

        addHeaders(grid, mappingTable);

        EvaluationGridDTO dto = new EvaluationGridDTO();
        dto.setGrid(grid);
        dto.setExtraFields(getExtraFields(itemsByMappingId));


        return dto;
    }

    private Map<Long, EvaluationGridItem> buildMapOfItemsByMappingId(List<EvaluationGridItem> itemsList){

        Map<Long, EvaluationGridItem> itemsByMappingId = new HashMap<>(itemsList.size());

        for(EvaluationGridItem item : itemsList){
            itemsByMappingId.put(item.getMappingId(), item);
        }

        return itemsByMappingId;
    }


    private void addHeaders(String[][] targetGrid, EvaluationGridFieldMapping[][] mappingTable){
        addColumnHeaders(targetGrid, mappingTable);
        addRowHeaders(targetGrid, mappingTable);
    }

    private void addColumnHeaders(String[][] targetGrid, EvaluationGridFieldMapping[][] mappingTable) {
        for(int i = 0; i< targetGrid[0].length; i++){
            EvaluationGridFieldMapping mapping = mappingTable[1][i];
            if(mapping!=null) {
                String columnHeaderCode = mapping.getColCode();
                String columnHeader = thesaurus.getLocalizedString(ThesaurusCodes.REALM_MIDATITGRDCL, columnHeaderCode, i18NService.currentLanguageCode());
                targetGrid[0][i]=columnHeader;
            }
        }
    }

    private void addRowHeaders(String[][] targetGrid, EvaluationGridFieldMapping[][] mappingTable) {
        for(int i = 0; i< targetGrid.length; i++){
            EvaluationGridFieldMapping mapping = mappingTable[i][2];

            if(mapping != null){
                String rowHeaderCode = mapping.getRowCode();
                String rowHeader = thesaurus.getLocalizedString(ThesaurusCodes.REALM_MIDATITGRDRO, rowHeaderCode, i18NService.currentLanguageCode());
                targetGrid[i][0] = rowHeader;
            }
        }
    }

    private Map<String,String> getExtraFields(Map<Long, EvaluationGridItem> itemsByMappingId){

        Map<String,String> extraFields = new HashMap<>();

        EvaluationGridFieldMapping mappingForAverageWidth, mappingForDominantSubstrat, mappingForSectionlength;

        mappingForAverageWidth = mappingDAO.findMappingByThesaurusValueCodeForContentDefinition(ThesaurusCodes.MIDATITGRDCE_LARGEURMOY);
        mappingForDominantSubstrat = mappingDAO.findMappingByThesaurusValueCodeForContentDefinition(ThesaurusCodes.MIDATITGRDCE_SUBSTRDOM);
        mappingForSectionlength = mappingDAO.findMappingByThesaurusValueCodeForContentDefinition(ThesaurusCodes.MIDATITGRDCE_LENGTH);




        EvaluationGridItem averageWidthItem = itemsByMappingId.get(mappingForAverageWidth.getId());
        if(averageWidthItem != null){
            String labelForAverageWidth = thesaurus.getLocalizedString(ThesaurusCodes.REALM_MIDATITGRDCE, ThesaurusCodes.MIDATITGRDCE_LARGEURMOY, i18NService.currentLanguageCode());
            extraFields.put(labelForAverageWidth, averageWidthItem.getValue());
        }

        EvaluationGridItem dominantSubstratItem = itemsByMappingId.get(mappingForDominantSubstrat.getId());
        if(dominantSubstratItem != null){
            String labelForDominantSubstrat = thesaurus.getLocalizedString(ThesaurusCodes.REALM_MIDATITGRDCE, ThesaurusCodes.MIDATITGRDCE_SUBSTRDOM, i18NService.currentLanguageCode());
            extraFields.put(labelForDominantSubstrat, dominantSubstratItem.getValue());
        }

        EvaluationGridItem sectionlengthItem = itemsByMappingId.get(mappingForSectionlength.getId());
        if(sectionlengthItem != null){
            String labelForSectionlength = thesaurus.getLocalizedString(ThesaurusCodes.REALM_MIDATITGRDCE, ThesaurusCodes.MIDATITGRDCE_LENGTH, i18NService.currentLanguageCode());
            extraFields.put(labelForSectionlength, sectionlengthItem.getValue());
        }

        return extraFields;
    }

}
