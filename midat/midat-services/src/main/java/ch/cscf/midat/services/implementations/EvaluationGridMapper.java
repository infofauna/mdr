package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.domain.entities.midat.EvaluationGridFieldMapping;
import ch.cscf.jeci.domain.entities.midat.EvaluationGridItem;

import java.util.Map;

/**
 * @author: henryp
 */
public class EvaluationGridMapper {

    private EvaluationGridFieldMapping[][] mappingTable;

    private Map<Long, EvaluationGridItem> itemsByMappingId;

    private String[][] targetGrid;

    private int currentRow, currentCol;


    public EvaluationGridMapper(EvaluationGridFieldMapping[][] mappingTable, Map<Long, EvaluationGridItem> itemsByMappingId) {
        this.mappingTable = mappingTable;
        this.itemsByMappingId = itemsByMappingId;
        targetGrid=new String[EvaluationGridReadService.ROWS][EvaluationGridReadService.COLS];
    }


    public String[][] buildGrid(){
        currentRow=0;

        for(EvaluationGridFieldMapping[] mappingsForTheRow : mappingTable){
            mapRow(mappingsForTheRow);
            currentRow++;
        }

        return targetGrid;
    }

    private void mapRow(EvaluationGridFieldMapping[] mappingsForTheRow){
        currentCol=0;
        for(EvaluationGridFieldMapping mapping : mappingsForTheRow){
            if(mapping != null) {
                mapItem(mapping);
            }
            currentCol++;
        }
    }

    private void mapItem(EvaluationGridFieldMapping mapping){
        EvaluationGridItem item = itemsByMappingId.get(mapping.getId());
        if(item != null){
            targetGrid[currentRow][currentCol]=item.getValue();
        }
    }


}
