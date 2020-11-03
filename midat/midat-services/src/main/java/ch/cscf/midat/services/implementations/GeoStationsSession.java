package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.domain.dto.midat.FilterDTO;
import ch.cscf.jeci.domain.dto.midat.SampleSearchParameters;
import ch.cscf.jeci.domain.entities.midat.sample.SampleStationItemView;
import ch.cscf.jeci.domain.entities.midat.sample.StationSamplesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GeoStationsSession {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<StationSamplesView> stations;
    private List<SampleStationItemView> sampleStationItemView;
    private SampleSearchParameters parameters;

    private List<FilterDTO> filterList;
    private String searchKey;
    private boolean hasDateFilter = false;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private  Collection<SampleStationItemView> items;

    public List<SampleStationItemView> getSampleStationItemView() {
        return sampleStationItemView;
    }

    public void setSampleStationItemView(List<SampleStationItemView> sampleStationItemView) {
        this.sampleStationItemView = sampleStationItemView;
    }

    public List<StationSamplesView> getStations() {
        return stations;
    }

    public void setStations(List<StationSamplesView> stations) {

        final Map<Long, StationSamplesView> stationSamplesViewMap = new HashMap<>(stations.size());
        List<StationSamplesView> groupedStations = new ArrayList<>();
        if(stations!=null && stations.size()>0){
            stations.stream().forEach(s ->{
                Long stationId = s.getStationId();
                if(stationSamplesViewMap.get(stationId) != null || (stationSamplesViewMap.get(stationId) == null && stationSamplesViewMap.containsKey(stationId))){
                    // already treated
                }else{
                    List<StationSamplesView> collect = stations.stream().filter(s1 -> s1.getStationId().intValue() == stationId.intValue()).collect(Collectors.toList());
                    s.setStationSamples(collect);
                    stationSamplesViewMap.put(stationId, s);
                    groupedStations.add(s);
                }
            });
        }
        this.stations = groupedStations;
    }



    public SampleSearchParameters getParameters() {
        return parameters;
    }

    public void setParameters(SampleSearchParameters parameters) {
        this.parameters = parameters;
        searchKey = randomUUID().toString();
        filterList = buildFilters(parameters);
    }


    /*
       build the filters
     */
    private List<FilterDTO> buildFilters(SampleSearchParameters parameters) {
        List<FilterDTO> filterDTOList = new ArrayList<>();

        this.hasDateFilter = false;
        //first of all add the key
        FilterDTO filterDTOKey = new FilterDTO();
        filterDTOKey.setCodeName("searchKey");
        filterDTOKey.setValue(searchKey);
        filterDTOKey.setDisplay(false);
        filterDTOList.add(filterDTOKey);

        //watercourse restriction
        if (parameters != null && parameters.getWatercourse() != null && parameters.getWatercourse().length() > 0) {
            String waterCourseName = parameters.getWatercourse();
            FilterDTO filterDTO = new FilterDTO();
            filterDTO.setCodeName("search.form.watercourse");
            filterDTO.setValue(waterCourseName);
            filterDTOList.add(filterDTO);
        }

        //locality restriction
        if (parameters != null && parameters.getLocality() != null && parameters.getLocality().length() > 0) {
            String localityName = parameters.getLocality();
            FilterDTO filterDTO = new FilterDTO();
            filterDTO.setCodeName("search.form.locality");
            filterDTO.setValue(localityName);
            filterDTOList.add(filterDTO);
        }

        //restrection StationNumber
        if (parameters != null && parameters.getStationNumber() != null && parameters.getStationNumber().length() > 0) {
            String stationNumber = parameters.getStationNumber();
            FilterDTO filterDTO = new FilterDTO();
            filterDTO.setCodeName("search.resulttable.stationNumber");
            filterDTO.setValue(stationNumber);
            filterDTOList.add(filterDTO);
        }

        //restrection project
        if (parameters != null && parameters.getProject() != null && parameters.getProject().length() > 0) {
            String project = parameters.getProject();
            FilterDTO filterDTO = new FilterDTO();
            filterDTO.setCodeName("search.resulttable.project");
            filterDTO.setValue(project);
            filterDTOList.add(filterDTO);
        }
        // search.resulttable.protocolType restriction
        if (parameters != null && parameters.getSelectedProtocolType() != null && parameters.getSelectedProtocolType().length() > 0) {
            String protocolType = parameters.getSelectedProtocolType();
            FilterDTO filterDTO = new FilterDTO();
            filterDTO.setCodeName("search.resulttable.protocolType");
            filterDTO.setValue(protocolType);
            filterDTOList.add(filterDTO);
        }

        //restrection GewissNumber
        if (parameters != null && parameters.getGewissNumber() != null && parameters.getGewissNumber().length() > 0) {
            String gewissNumber = parameters.getGewissNumber();
            FilterDTO filterDTO = new FilterDTO();
            filterDTO.setCodeName("search.details.station.gewiss");
            filterDTO.setValue(gewissNumber);
            filterDTOList.add(filterDTO);
        }

        //restriction principal institution
        if (parameters != null && parameters.getPrincipalInstitution() != null && parameters.getPrincipalInstitution().length() > 0) {
            String pInstitution = parameters.getPrincipalInstitution();
            FilterDTO filterDTO = new FilterDTO();
            filterDTO.setCodeName("search.details.sample.principal");
            filterDTO.setValue(pInstitution);
            filterDTOList.add(filterDTO);
        }

        //dates restrictions
        if (parameters != null && parameters.getMinDate() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(parameters.getMinDate());
            cal.set(Calendar.DAY_OF_MONTH, 1);
            logger.info("#1.search.form.dates-0 :" + sdf.format(parameters.getMinDate()));
            logger.info("#1.search.form.dates-1 :" + sdf.format(cal.getTime()));

            FilterDTO filterDTO = new FilterDTO();
            filterDTO.setCodeName("searchCriteria.minDate");
            filterDTO.setValue(sdf.format(cal.getTime()));
            filterDTO.setDisplay(false);
            filterDTOList.add(filterDTO);

            this.hasDateFilter = true;

        }
        if (parameters != null && parameters.getMaxDate() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(parameters.getMaxDate());
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            logger.info("#1.searchState.searchCriteria.maxDate-0 :" + sdf.format(parameters.getMaxDate()));
            logger.info("#1.searchState.searchCriteria.maxDate-1 :" + sdf.format(cal.getTime()));

            FilterDTO filterDTO = new FilterDTO();
            filterDTO.setCodeName("searchCriteria.maxDate");
            filterDTO.setValue(sdf.format(cal.getTime()));
            filterDTO.setDisplay(false);
            filterDTOList.add(filterDTO);

            this.hasDateFilter = true;
        }

        //index restrictions
        if (parameters != null && parameters.getIndexType() != null && (parameters.getMinBioIndex() != null || parameters.getMaxBioIndex() != null)) { //we need at least index type and one of min/max

            String value="";
            if(parameters.getMinBioIndex() != null ){
                value += parameters.getMinBioIndex()+" ≤ ";
            }
            value += parameters.getIndexType();

            if(parameters.getMaxBioIndex() != null ){
                value +=" ≤ "+parameters.getMaxBioIndex();
            }

            FilterDTO filterDTO = new FilterDTO();
            filterDTO.setCodeName("search.form.index");
            filterDTO.setValue(value);
            filterDTOList.add(filterDTO);
        }

        //published restriction
        if (parameters != null && parameters.getPublished() == null) {
            logger.info("#1.searchState.publised.status :search.form.published.all");
        }

        if (parameters != null && parameters.getPublished() != null) {
            String indexValue;
            if (parameters.getPublished()) {
                indexValue = "search.form.published.true";
            } else {
                indexValue = "search.form.published.false";
            }
            FilterDTO filterDTO = new FilterDTO();
            filterDTO.setCodeName("search.form.published.label");
            filterDTO.setValue(indexValue);
            filterDTOList.add(filterDTO);

            logger.info("#1.searchState.publised.status :" + indexValue);

        }

        //group restrictions
        //selected group is always more restrictive than user group.
        // We assume that selected group will always be a group that user is part of. Check is made in service method.
        if (parameters != null && parameters.getSelectedGroup() != null) {
            String selectedGroup = parameters.getSelectedGroup();
            FilterDTO filterDTO = new FilterDTO();
            filterDTO.setCodeName("search.form.group.label");
            filterDTO.setValue(selectedGroup);
            filterDTOList.add(filterDTO);
        }

        return filterDTOList;
    }

    public List<FilterDTO> getFilters() {
        List<FilterDTO> filterDTOList = filterList != null ? filterList : new ArrayList<FilterDTO>();
        logger.info("getFilters:" + filterDTOList.toString());
        return filterDTOList;
    }

    public boolean hasFiltersOtherThanDateAndKey() {

        if (this.hasDateFilter) {
            logger.info("Filter [with date filter] length :"+ this.getFilters().size());
            return this.getFilters().size() > 2;
        } else {
            logger.info("Filter [without date filter] length :"+ this.getFilters().size());
            return this.getFilters().size() > 1;
        }
    }

    public String getSearchKey() {
        return searchKey;
    }

    /*
        returns true when the sample date is in between start and the end dates, if not it will return false
    */
    public boolean isSampleDateBetweenStartAndEndDate(Date sampleDate, String startDateStr, String endDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date startDate = null, endDate = null;
        boolean isBetween = false;
        try {
            startDate = sdf.parse(startDateStr);
            endDate = sdf.parse(endDateStr);

            if (startDate.getTime() <= sampleDate.getTime() && sampleDate.getTime() < endDate.getTime()) {
                isBetween = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        if (isBetween) {
            logger.info(sdf2.format(startDate) + " <= " + sdf2.format(sampleDate) + " < " + sdf2.format(endDate) + "  -----> True ");
        } else {
            logger.info(sdf2.format(startDate) + " <= " + sdf2.format(sampleDate) + " < " + sdf2.format(endDate) + "  -----> False ");
        }
        return isBetween;
    }
    public Collection<SampleStationItemView> getItems() {
        return items;
    }

    public void setItems(Collection<SampleStationItemView> items) {
        this.items = items;
    }

}
