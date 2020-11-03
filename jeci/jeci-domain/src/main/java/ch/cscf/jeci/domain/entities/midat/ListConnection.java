package ch.cscf.jeci.domain.entities.midat;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity()
@Table(name = "V_LISTECONNEXION", schema = "APPMANAGER")
public class ListConnection {

    @Id
    @Column(name="ID")
    private Long id;

    @Column(name="Application")
    private String application;

    @Column(name="Login")
    private String login;

    @Column(name = "LDAP")
    @Type(type = "yes_no")
    private boolean ldap;


    @Column(name="Lastname")
    private String lastname;


    @Column(name="Firstname")
    private String firstname;

    @Column(name="Address")
    private String address;

    @Column(name="LOCALITY")
    private String locality;

    @Column(name = "CREATION_DATE")
    private String creationDate;

    @Column(name = "firstconnection")
    private String firstconnection;


    @Column(name = "lastconnection")
    private String lastconnection;

    @Column(name="connectioncount")
    private Long connectioncount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isLdap() {
        return ldap;
    }

    public void setLdap(boolean ldap) {
        this.ldap = ldap;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getFirstconnection() {
        return firstconnection;
    }

    public void setFirstconnection(String firstconnection) {
        this.firstconnection = firstconnection;
    }

    public String getLastconnection() {
        return lastconnection;
    }

    public void setLastconnection(String lastconnection) {
        this.lastconnection = lastconnection;
    }

    public Long getConnectioncount() {
        return connectioncount;
    }

    public void setConnectioncount(Long connectioncount) {
        this.connectioncount = connectioncount;
    }
}
