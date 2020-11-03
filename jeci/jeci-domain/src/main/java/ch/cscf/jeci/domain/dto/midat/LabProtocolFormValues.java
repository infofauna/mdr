package ch.cscf.jeci.domain.dto.midat;

import ch.cscf.jeci.domain.entities.base.Institution;
import ch.cscf.jeci.domain.entities.base.Person;
import ch.cscf.jeci.domain.entities.base.Project;
import ch.cscf.jeci.domain.entities.midat.MIDATProtocolVersion;
import ch.cscf.jeci.domain.entities.thesaurus.Language;
import ch.cscf.jeci.domain.entities.thesaurus.LocalizedThesaurusEntry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: henryp
 */
public class LabProtocolFormValues {

    private boolean isMass;

    private List<Person> persons;

    private List<Project> projects;


    private List<LocalizedThesaurusEntry> comments;

    private Set<Long> commentIds = new HashSet<>();

    private List<Institution> institutions;

    private List<MIDATProtocolVersion> protocolVersions;

    private List<Language> languages;

    private List<LocalizedThesaurusEntry> referenceSystems;

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }

    public List<MIDATProtocolVersion> getProtocolVersions() {
        return protocolVersions;
    }

    public void setProtocolVersions(List<MIDATProtocolVersion> protocolVersions) {
        this.protocolVersions = protocolVersions;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<LocalizedThesaurusEntry> getReferenceSystems() {
        return referenceSystems;
    }

    public void setReferenceSystems(List<LocalizedThesaurusEntry> referenceSystems) {
        this.referenceSystems = referenceSystems;
    }

    public boolean isMass() {
        return isMass;
    }

    public void setIsMass(boolean isMass) {
        this.isMass = isMass;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<LocalizedThesaurusEntry> getComments() {
        return comments;
    }

    public void setComments(List<LocalizedThesaurusEntry> comments) {
        this.comments = comments;
    }
    public Set<Long> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(Set<Long> commentIds) {
        this.commentIds = commentIds;
    }
}
