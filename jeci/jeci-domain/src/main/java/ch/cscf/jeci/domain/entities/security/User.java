package ch.cscf.jeci.domain.entities.security;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.base.Person;
import ch.cscf.jeci.domain.entities.thesaurus.Language;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(
    name="ADMIN_USER", schema = "APPMANAGER"
)
@AssociationOverrides( {
    @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="USR_USR_ID_CREATE")),
    @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="USR_USR_ID_MODIFY"))
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "USR_ID")),
    @AttributeOverride(name = "status", column = @Column(name = "USR_STATUS")),
    @AttributeOverride(name = "creationDate", column = @Column(name = "USR_USR_CREATE_DATE")),
    @AttributeOverride(name = "updateDate", column = @Column(name = "USR_USR_MODIFY_DATE")),
    @AttributeOverride(name = "dbCreationDate", column = @Column(name = "USR_CREDATE")),
    @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "USR_MODDATE")),
    @AttributeOverride(name = "dbCreationUser", column = @Column(name = "USR_CREUSER")),
    @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "USR_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="APPMANAGER.SEQ_ADMIN_USER")
@Where(clause = "USR_STATUS = 'A'")        //carefull : this is proprietary Hibernate stuff. If soft dele
public class User extends BaseEntity {

    @Size(max = 255)
    @NotEmpty
	@Column(length=255, unique = true, nullable = false, name="USR_USER_LOGIN")
	private String username;

    @Column(name="USR_PASSWORD")
	private String password;

    @Column(name="USR_DESIGNATION")
    private String designation;

    @Column(name="USR_TOKEN")
    private String ssoSessionId;

    @Column(name="USR_IS_LDAP_LOGIN")
    @Type(type="yes_no")
    private Boolean ldap;

    @Column(name="USR_SSOCOUNTER")
    private Integer ssoSessionCounter;

    @Column(name="USR_IPADDRESS")
    private String ssoIpAddress;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<RoleGroup> roleGroups;

    @Column(name="USR_ARMA_ONLY")
    @Type(type="yes_no")
    private Boolean arma;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USR_PER_ID", updatable = false, insertable = false)
    private Person person;

    @Column(name = "USR_PER_ID")
    private Long personId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USR_LAN_ID", updatable = false, insertable = false)
    private Language language;

    @Column(name = "USR_LAN_ID")
    private Long languageId;


    public User(){}

	@Override
	public String toString() {
		return "User [username=" + username + "]";
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
    public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    public Boolean getLdap() {
        return ldap;
    }

    public void setLdap(Boolean ldap) {
        this.ldap = ldap;
    }

    public String getSsoSessionId() {
        return ssoSessionId;
    }

    public void setSsoSessionId(String ssoToken) {
        this.ssoSessionId = ssoToken;
    }

    public Integer getSsoSessionCounter() {
        return ssoSessionCounter;
    }

    public void setSsoSessionCounter(Integer ssoSessionCounter) {
        this.ssoSessionCounter = ssoSessionCounter;
    }

    public String getSsoIpAddress() {
        return ssoIpAddress;
    }

    public void setSsoIpAddress(String ssoIpAddress) {
        this.ssoIpAddress = ssoIpAddress;
    }

    public Person getPerson() {
        return person;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Language getLanguage() {
        return language;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public Boolean getArma() {
        return arma;
    }

    public void setArma(Boolean arma) {
        this.arma = arma;
    }


    public List<RoleGroup> getRoleGroups() {
        return roleGroups;
    }

    public void setRoleGroups(List<RoleGroup> roleGroups) {
        this.roleGroups = roleGroups;
    }
}
