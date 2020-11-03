package ch.cscf.jeci.domain.dto.midat;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * @author: henryp
 */
public class SampleSearchParameters {

    private String watercourse;

    private String locality;

    private Date minDate, maxDate;

    private String indexType;

    private Double minBioIndex, maxBioIndex;

    private String selectedGroup;

    boolean filterByUserGroups = false;

    boolean editionMode = false;

    boolean isAppManager = false;
    boolean isNationalContributor = false;

    Boolean published;

    Long creationUserId;


    public boolean isAppManager() {
        return isAppManager;
    }

    public void setAppManager(boolean appManager) {
        isAppManager = appManager;
    }



    private String stationNumber;
    private String project;
    private String gewissNumber;
    private String principalInstitution;
    private String selectedProtocolType;

    private boolean isFullExport;

    Collection<String> userGroups = Collections.emptyList();

    public String getWatercourse() {
        return watercourse;
    }

    public void setWatercourse(String watercourse) {
        this.watercourse = watercourse;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public Double getMinBioIndex() {
        return minBioIndex;
    }

    public void setMinBioIndex(Double minBioIndex) {
        this.minBioIndex = minBioIndex;
    }

    public Double getMaxBioIndex() {
        return maxBioIndex;
    }

    public void setMaxBioIndex(Double maxBioIndex) {
        this.maxBioIndex = maxBioIndex;
    }

    public boolean isFilterByUserGroups() {
        return filterByUserGroups;
    }

    public void setFilterByUserGroups(boolean filterByUserGroups) {
        this.filterByUserGroups = filterByUserGroups;
    }

    public Collection<String> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Collection<String> userGroups) {
        this.userGroups = userGroups;
    }

    public String getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(String selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public boolean isEditionMode() {
        return editionMode;
    }

    public void setEditionMode(boolean editionMode) {
        this.editionMode = editionMode;
    }

    public Long getCreationUserId() {
        return creationUserId;
    }

    public void setCreationUserId(Long creationUserId) {
        this.creationUserId = creationUserId;
    }


    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getGewissNumber() {
        return gewissNumber;
    }

    public void setGewissNumber(String gewissNumber) {
        this.gewissNumber = gewissNumber;
    }

    public String getPrincipalInstitution() {
        return principalInstitution;
    }

    public void setPrincipalInstitution(String principalInstitution) {
        this.principalInstitution = principalInstitution;
    }

    public boolean isNationalContributor() {
        return isNationalContributor;
    }

    public void setNationalContributor(boolean nationalContributor) {
        isNationalContributor = nationalContributor;
    }

    public String getSelectedProtocolType() {
        return selectedProtocolType;
    }

    public void setSelectedProtocolType(String selectedProtocolType) {
        this.selectedProtocolType = selectedProtocolType;
    }

    public boolean isFullExport() {
        return isFullExport;
    }

    public void setFullExport(boolean fullExport) {
        isFullExport = fullExport;
    }
}

