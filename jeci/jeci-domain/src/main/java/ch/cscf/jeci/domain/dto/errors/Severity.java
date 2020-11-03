package ch.cscf.jeci.domain.dto.errors;

/**
 * @author: henryp
 */
public enum Severity {

    F(3, "fatal"),
    E(2, "error"),
    W(1, "warning"),
    I(0, "information");

    private Severity(Integer weight, String designation) {
        this.weight = weight;
        this.designation = designation;
    }

    private Integer weight;

    private String designation;

    public Integer getWeight() {
        return weight;
    }

    public String getDesignation() {
        return designation;
    }
}
