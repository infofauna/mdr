package ch.cscf.jeci.domain.dto.midat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: henryp
 */
public class GroundProtocolDTO {


    private String label;

    private List<String> values;

    private List<GroundProtocolDTO> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public GroundProtocolDTO(String label){
        this.label=label;
        children=new ArrayList<>();
        values= new ArrayList<>();
    }

    public List<GroundProtocolDTO> getChildren() {
        return children;
    }

    public void addChild(GroundProtocolDTO child){
        children.add(child);
    }

    public void addValue(String value){
        values.add(value);
    }
}
