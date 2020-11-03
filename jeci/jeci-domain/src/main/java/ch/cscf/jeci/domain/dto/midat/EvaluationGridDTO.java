package ch.cscf.jeci.domain.dto.midat;

import java.util.Map;

/**
 * @author: henryp
 */
public class EvaluationGridDTO {

    private String[][] grid;

    private Map<String, String> extraFields;

    public String[][] getGrid() {
        return grid;
    }

    public void setGrid(String[][] grid) {
        this.grid = grid;
    }

    public Map<String, String> getExtraFields() {
        return extraFields;
    }

    public void setExtraFields(Map<String, String> extraFields) {
        this.extraFields = extraFields;
    }
}
