package ch.cscf.jeci.domain.dto.jeci;


/**
 * Created by kanso
 */
public class MappingHeaderItem {

    private String cellRowValue, cellColumnValue, fieldNameTo;

    public MappingHeaderItem(String cellRowValue, String cellColumnValue, String fieldNameTo) {
        this.cellRowValue = cellRowValue;
        this.cellColumnValue = cellColumnValue;
        this.fieldNameTo = fieldNameTo;
    }

    public String getCellRowValue() {
        return cellRowValue;
    }

    public String getCellColumnValue() {
        return cellColumnValue;
    }

    public String getFieldNameTo() {
        return fieldNameTo;
    }

    public void setCellRowValue(String cellRowValue) {
        this.cellRowValue = cellRowValue;
    }

    public void setCellColumnValue(String cellColumnValue) {
        this.cellColumnValue = cellColumnValue;
    }

    public void setFieldNameTo(String fieldNameTo) {
        this.fieldNameTo = fieldNameTo;
    }
}
