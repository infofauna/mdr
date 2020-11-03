package ch.cscf.jeci.domain.entities.security;

import ch.cscf.jeci.domain.annotations.LocalizedProperty;
import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;

import javax.persistence.*;

@Entity()
@Table(name="ADMIN_GROUP", schema = "appmanager")
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="AGR_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="AGR_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "AGR_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "AGR_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "AGR_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "AGR_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "AGR_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "AGR_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "AGR_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "AGR_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_ADMIN_ROLE")
public class Group extends BaseEntity {

    @Column(name="AGR_name", length = 30, nullable = false, unique = true)
	private String name;

    @Column(name="AGR_description", length = 128)
	private String description;

    @ManyToOne()
    @JoinColumn(name = "AGR_CVL_ID")
	private ThesaurusValue localizedDescriptionThesaurusValue;

    @LocalizedProperty("localizedDescriptionThesaurusValue")
    @Transient
    private String localizedDescription;

    public Group(){}

	public String getName() {
		return name;
	}

    public String getDescription() {
		return description;
	}

    public ThesaurusValue getLocalizedDescriptionThesaurusValue() {
        return localizedDescriptionThesaurusValue;
    }

    public String getLocalizedDescription() {
        return localizedDescription;
    }
}
