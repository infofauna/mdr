package ch.cscf.jeci.domain.entities.security;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;

@Entity()
@Table(name="ADMIN_ROLE", schema = "appmanager")
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="APR_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="APR_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "APR_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "APR_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "APR_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "APR_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "APR_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "APR_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "APR_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "APR_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_ADMIN_ROLE")
public class Role extends BaseEntity {

    @Column(name="apr_name", length = 30, nullable = false, unique = true)
	private String name;

    @Column(name="apr_description", length = 128)
	private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="APR_APL_ID")
    private JeciApplication application;

    @Column(name="APR_SORTORDER")
    private Integer sortOrder;

	public Role(){}

	public String getName() {
		return name;
	}

    public String getDescription() {
		return description;
	}

    public JeciApplication getApplication() {
        return application;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
