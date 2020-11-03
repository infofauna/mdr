package ch.cscf.jeci.domain.dto.midat;

/**
 * Created by abdallahkanso on 11.07.17.
 */
public class ProjectDTO {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    private String designation;
    public ProjectDTO(){}
}
