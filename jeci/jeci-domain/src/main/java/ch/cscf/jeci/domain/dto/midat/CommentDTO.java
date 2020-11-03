package ch.cscf.jeci.domain.dto.midat;

/**
 * Created by abdallahkanso on 12.07.17.
 */
public class CommentDTO {
    private Long id;
    private String designation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
