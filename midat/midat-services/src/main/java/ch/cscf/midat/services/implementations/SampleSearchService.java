package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.dto.midat.*;
import ch.cscf.jeci.domain.entities.midat.sample.Sample;
import ch.cscf.jeci.domain.entities.midat.sample.SampleInfoIbchData;
import ch.cscf.jeci.domain.entities.midat.sample.SampleStation;
import ch.cscf.jeci.domain.entities.midat.sample.StationSamplesView;
import ch.cscf.jeci.domain.entities.security.User;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.admin.GroupDAO;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleDAO;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleStationDAO;
import ch.cscf.jeci.persistence.daos.thesaurus.interfaces.ThesaurusReadOnlyService;
import ch.cscf.jeci.services.security.interfaces.SessionUserService;
import ch.cscf.jeci.services.systematics.interfaces.SystematicsReadOnlyService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vividsolutions.jts.geom.Point;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: henryp
 */
@Service
public class SampleSearchService implements ch.cscf.midat.services.interfaces.SampleSearchService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SampleDAO sampleDAO;

    @Autowired
    private SampleStationDAO stationDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private SessionUserService sessionUserService;

    @Autowired
    private ch.cscf.midat.services.interfaces.BioticWaterQualityRatingReadService qualityRatingReadService;

    @Autowired
    private SystematicsReadOnlyService systematics;


    @Autowired
    private GeoStationsSession stationsSession;

    @Autowired
    private ch.cscf.midat.services.interfaces.GroundProtocolReadService groundProtocolReadService;


    @Autowired
    private EvaluationGridReadService gridReadService;


    @Autowired
    private ThesaurusReadOnlyService thesaurusReadOnlyService;


    HashMap<Long, SampleStation> mapSamplesStation = new HashMap<Long, SampleStation>();
    HashMap<Long, Sample> mapSamples = new HashMap<Long, Sample>();


    /**
     * General search method that accepts a SampleSearchParameter describing the criteria to apply.
     * Security restrictions are applied based on the currently authenticated user's role and groups.
     */
    @Override
    @Transactional
    public List<SearchResultDTO> search(SampleSearchParameters parameters, Page page, String orderBy, SortOrder sortOrder) {
        buildSecurityRestrictions(parameters);

        stationsSession.setParameters(parameters);

        List<SearchResultDTO> results = sampleDAO.search(parameters, orderBy, sortOrder, page);
        populateIndexesDTO(results);

        populateGroups(results);

        poplulateStationAdditionalInfo(results);

        return results;
    }

    /**
     * General search method that accepts a SampleSearchParameter describing the criteria to apply.
     * Security restrictions are applied based on the currently authenticated user's role and groups.
     */

    @Override
    @Transactional
    public List<Map<String, Object>> searchForExport(SampleSearchParameters parameters) {
        buildSecurityRestrictions(parameters);

        List<Map<String, Object>> results = sampleDAO.searchForExport(parameters);

        mapSamplesStation = new HashMap<Long, SampleStation>();
        mapSamples = new HashMap<Long, Sample>();

        // newly added for export excel
        long startPopulateGroupsForExport = System.currentTimeMillis();
        populateGroupsForExport(results, parameters.isFullExport());
        long finishPopulateGroupsForExport = System.currentTimeMillis();

        long startFillCalculatedValues = System.currentTimeMillis();
        fillCalculatedValues(results , parameters.isFullExport());
        long finishFillCalculatedValues = System.currentTimeMillis();

        long startPopulateIndexesForExport = System.currentTimeMillis();
        populateIndexesForExport(results, parameters.isFullExport());
        long finishPopulateIndexesForExport = System.currentTimeMillis();


        long startCountTaxonsFamilyByName = System.currentTimeMillis();
        countTaxonsFamilyByName(results, parameters.isFullExport());
        long finishCountTaxonsFamilyByName = System.currentTimeMillis();



        long startPopulateTaxonNbrPerIBCHCalculation = System.currentTimeMillis();
        populateTaxonNbrPerIBCHCalculation(results, parameters.isFullExport());
        long finishPopulateTaxonNbrPerIBCHCalculation = System.currentTimeMillis();

        long startPopulateAttachedFiles = System.currentTimeMillis();
        populateAttachedFiles(results, parameters.isFullExport());
        long finishPopulateAttachedFiles = System.currentTimeMillis();


        logger.info(">>>>>>>>>>>>>>>> 1 - CalculatedValues time elapsed:"+ (finishFillCalculatedValues-startFillCalculatedValues));
        logger.info(">>>>>>>>>>>>>>>> 2 - PopulateGroupsForExport time elapsed:"+ (finishPopulateGroupsForExport-startPopulateGroupsForExport));
        logger.info(">>>>>>>>>>>>>>>> 3 - PopulateIndexesForExport time elapsed:"+ (finishPopulateIndexesForExport-startPopulateIndexesForExport));
        logger.info(">>>>>>>>>>>>>>>> 4 - CountTaxonsFamilyByName time elapsed:"+ (finishCountTaxonsFamilyByName-startCountTaxonsFamilyByName));
        logger.info(">>>>>>>>>>>>>>>> 5 - PopulateTaxonNbrPerIBCHCalculation time elapsed:"+ (finishPopulateTaxonNbrPerIBCHCalculation-startPopulateTaxonNbrPerIBCHCalculation));
        logger.info(">>>>>>>>>>>>>>>> 6 - PopulateAttachedFiles time elapsed:"+ (finishPopulateAttachedFiles-startPopulateAttachedFiles));

        return results;
    }


    /**
     * Search method that returns samples inside a rectangular spatial selection. The rectangle is always parallel to north/south and east/west axis.
     * It is specified by the nroth, west, south and east coordinates in swiss system.
     * Security restrictions are applied based on the currently authenticated user's role and groups.
     */
    @Override
    @Transactional
    public List<SearchResultDTO> searchWithinRectangle(Double west, Double east, Double north, Double south, Page page, String orderBy, SortOrder sortOrder, boolean editionMode,String startDate,String endDate){

        Long userId= sessionUserService.getSessionUserId();
        boolean isAppManager = sessionUserService.getSessionUserRolesNamesForMidat().contains("manager");
        boolean isNationalContributor = sessionUserService.userHasNationalContributorPermissionForMidat(userId);

        List<Long> stationsIds = findStationsInRectangle(west, east, north, south,userId,isAppManager,isNationalContributor, editionMode);

        if(stationsIds.isEmpty()){
            page.setPageNumber(0);
            page.setTotalRows(0);
            return Collections.emptyList();
        }else{
            List<Long> filteredStationsIds = getFilteredStationsFromSelection(startDate, endDate, stationsIds ,editionMode);

            if(filteredStationsIds.isEmpty()){
                page.setPageNumber(0);
                page.setTotalRows(0);
                return Collections.emptyList();
            }else{
                stationsIds = filteredStationsIds;
            }
        }

        List<SearchResultDTO> results = sampleDAO.searchByStations(stationsIds,userId, orderBy, sortOrder, page,isAppManager,isNationalContributor, editionMode);
        populateIndexesDTO(results);
        populateGroups(results);
        poplulateStationAdditionalInfo(results);

        return results;
    }

    private List<Long> getFilteredStationsFromSelection(String startDate, String endDate, List<Long> stationsIds, boolean editionMode) {
        // TODO: 04.10.18 apply filtering logic
        final Collection<StationSamplesView> stations =  filterAuthorizedStations(stationsSession.getStations(),editionMode);

        List<Long> filteredStationsIds = new ArrayList<>();

        List<StationSamplesView> filteredStations = stations.stream().filter(station -> {
            List<StationSamplesView> samples = station.getStationSamples(); // original samples per station
            /* filter out samples that are out the date range [startDate, endDate] */
            List<StationSamplesView> filteredSamples = samples.stream().filter(s -> stationsSession.isSampleDateBetweenStartAndEndDate(s.getSampleDate(), startDate, endDate)).collect(Collectors.toList());
            return filteredSamples != null && filteredSamples.size() > 0;
        }).collect(Collectors.toList());

        for(int i=0;i<filteredStations.size();i++){
            Long id = filteredStations.get(i).getStationId();
            if(stationsIds.contains(id)){
                filteredStationsIds.add(id);
            }
        }
        return filteredStationsIds;
    }

    private Collection<StationSamplesView> filterAuthorizedStations(Collection<StationSamplesView> stations, boolean editionMode){
        Long userId = sessionUserService.getSessionUserId();
        boolean isAppManager = sessionUserService.getSessionUserRolesNamesForMidat().contains("manager");
        boolean isNationalContributor = sessionUserService.userHasNationalContributorPermissionForMidat(userId);
        List<StationSamplesView> filteredStations = new ArrayList<StationSamplesView>();
        if(!isAppManager || !isNationalContributor){
            Collection<String> groupNames= editionMode? getUserGroupsNames(false): getUserGroupsNames(true);
            List<Long> sIds = stations.stream().map(s->s.getStationId()).collect(Collectors.toList());
            filteredStations.addAll(stationDAO.findStationSamplesViewByIds(sIds,userId,groupNames,editionMode));
        }else{
            return stations;
        }
        return filteredStations;
    }

    /**
     * Same as searchWithinRectangle but returns denormalized data tailored for CSV export.
     */
    @Override
    @Transactional
    public List<Map<String, Object>> searchWithinRectangleForExport(Double west, Double east, Double north, Double south, boolean editionMode,String startDate,String endDate,boolean isFull){

        Long userId = sessionUserService.getSessionUserId();
        boolean isAppManager = sessionUserService.getSessionUserRolesNamesForMidat().contains("manager");
        boolean isNationalContributor = sessionUserService.userHasNationalContributorPermissionForMidat(userId);

        List<Long> stationsIds = findStationsInRectangle(west, east, north, south, userId,isAppManager,isNationalContributor,editionMode);

        if(stationsIds.isEmpty()){
            return Collections.emptyList();
        }else{
            List<Long> filteredStationsIds = getFilteredStationsFromSelection(startDate, endDate, stationsIds,editionMode);
            if(filteredStationsIds.isEmpty()){
                return Collections.emptyList();
            }else{
                stationsIds = filteredStationsIds;
            }
        }

        List<Map<String, Object>> rows = sampleDAO.searchByStationsForExport(stationsIds, userId,isAppManager,isNationalContributor,editionMode);


        // newly added for export excel
        populateGroupsForExport(rows, isFull);

        fillCalculatedValues(rows, isFull);
        populateIndexesForExport(rows,isFull);
        countTaxonsFamilyByName(rows,isFull);
        populateTaxonNbrPerIBCHCalculation(rows,isFull);

        populateAttachedFiles(rows,isFull);
        return rows;
    }

    /**
     * Search method that returns all samples attributed to a given station.
     * Security restrictions are applied based on the currently authenticated user's role and groups.
     */
    @Override
    @Transactional
    public List<SearchResultDTO> searchByStationId(Long stationId, Page page, String orderBy, SortOrder sortOrder,boolean editionMode) {
        Long userId =  sessionUserService.getSessionUserId();
        boolean isAppManager = sessionUserService.getSessionUserRolesNamesForMidat().contains("manager");
        boolean isNationalContributor = sessionUserService.userHasNationalContributorPermissionForMidat(userId);

        //checkIfStationIsAuthorized(stationId,editionMode);
        List<Long> stationsIds = Arrays.asList(new Long[]{stationId});


        List<SearchResultDTO> results = sampleDAO.searchByStations(stationsIds, userId, orderBy, sortOrder, page,isAppManager,isNationalContributor, editionMode);
        populateIndexesDTO(results);
        populateGroups(results);
        poplulateStationAdditionalInfo(results);
        return results;
    }

    @Override
    @Transactional
    public List<Map<String, Object>> searchByStationIdForExport(Long stationId, boolean editionMode,boolean isFull) {

        Long userId =  sessionUserService.getSessionUserId();
        boolean isAppManager = sessionUserService.getSessionUserRolesNamesForMidat().contains("manager");
        boolean isNationalContributor = sessionUserService.userHasNationalContributorPermissionForMidat(userId);

        //checkIfStationIsAuthorized(stationId,editionMode);
        List<Long>  stationsIds = Lists.newArrayList(stationId);
        List<Map<String, Object>> rows = sampleDAO.searchByStationsForExport(stationsIds, userId,isAppManager,isNationalContributor, editionMode);

        populateGroupsForExport(rows,isFull);
        fillCalculatedValues(rows,isFull);
        populateIndexesForExport(rows,isFull);
        countTaxonsFamilyByName(rows,isFull);
        populateTaxonNbrPerIBCHCalculation(rows,isFull);
        populateAttachedFiles(rows,isFull);
        return rows;
    }

    /**
     * Modifieds the raw results from DAO to add some special fields that can't be obtained easily from a single DB query.
     * Sucha as : names of the taxa in the hierarchy for each row, extract coordinates from Point, etc.
     * @param results
     */
    private void fillCalculatedValues(List<Map<String, Object>> results, boolean isFull) {
        for(Map<String, Object> row : results){

            Point point = (Point) row.get("sampleStation.coordinates");

            row.put("sampleStation.coordinates.x", point.getX());
            row.put("sampleStation.coordinates.y", point.getY());

            // double z = point.getCoordinates()[0].z;
            if(row.get("sampleStation.coordinateZ") != null ){
                double z = (double) row.get("sampleStation.coordinateZ");
                if(!Double.isNaN(z)) {
                    row.put("sampleStation.coordinates.z", z);
                }
            }

            if( row.get("labRecordField.taxon.id") !=null){

                Long taxonId = (long) row.get("labRecordField.taxon.id");
                row.put("labRecordField.taxon.phylum", systematics.getAncestorDesignationForRank(taxonId, "PHYLUM", true));
                row.put("labRecordField.taxon.class", systematics.getAncestorDesignationForRank(taxonId, "CLASS", true));
                row.put("labRecordField.taxon.order", systematics.getAncestorDesignationForRank(taxonId, "ORDER", true));
                row.put("labRecordField.taxon.family", systematics.getAncestorDesignationForRank(taxonId, "FAMILY", true));
                row.put("labRecordField.taxon.genus", systematics.getAncestorDesignationForRank(taxonId, "GENUS", true));
                row.put("labRecordField.taxon.species", systematics.getAncestorDesignationForRank(taxonId, "SPECIES", true));
            }



            //persons
            if(row.get("sampleAttributeDeterminatorX.value") != null){
                row.put("determinator", row.get("sampleAttributeDeterminatorX.value"));
            }else{
                row.put("determinator", row.get("sampleAttributeDeterminator.value"));
            }

            if(row.get("sampleAttributeOperatorX.value") != null){
                row.put("operator", row.get("sampleAttributeOperatorX.value"));
            }else{
                row.put("operator", row.get("sampleAttributeOperator.value"));
            }

            //full date in dd.MM.yyyy format
            row.put("sample.sampleDate", String.valueOf(row.get("sample.sampleDateDay"))
                    .concat(".").concat(String.valueOf(row.get("sample.sampleDateMonth")))
                    .concat(".").concat(String.valueOf(row.get("sample.sampleDateYear"))));

            //add stations liées
            Long stationId = null;
            if(row.get("sampleStation.id") !=null){
                stationId = (long) row.get("sampleStation.id");
                SampleStation stationById = null;

                if(mapSamplesStation.get(stationId) != null || (mapSamplesStation.get(stationId) == null && mapSamplesStation.containsKey(stationId))){
                    stationById =  mapSamplesStation.get(stationId);
                }else{
                    stationById= stationDAO.findById(stationId);
                    mapSamplesStation.put(stationId, stationById);
                }

                List<SampleStation> stations = stationById.getStationStations();
                if(stations!= null && stations.size()>0){
                    String linkedStations = getLinkedStations(stations);
                    row.put("sampleStation.stationStations",linkedStations);
                }

                if(stationById.getParent() !=null && stationById.getParent().getStationNumber()!=null){
                    row.put("sampleStation.stationPrincipale", stationById.getParent().getStationNumber());
                }else{
                    row.put("sampleStation.stationPrincipale", "");
                }
            }

            //add sample.ephemeroptera

        }
     }


    private void countTaxonsFamilyByName(List<Map<String, Object>> results, boolean isFull){

        //collect the sample ids
        for(Map<String, Object> row : results){
            row.put("sample.ephemeroptera",String.valueOf(row.get("sample.ephemeropteraCounter")));
            row.put("sample.trichoptera",String.valueOf(row.get("sample.tricopteraCounter")));
            row.put("sample.plecoptera",String.valueOf(row.get("sample.plecopteraCounter")));
        }
    }


    private void populateTaxonNbrPerIBCHCalculation(List<Map<String, Object>> results, boolean isFull){

        //collect the sample ids
        for(Map<String, Object> row : results){
            row.put("sample.taxon.no.ibch",String.valueOf(row.get("sample.taxonSumFamily")));
        }

     }

    private void populateAttachedFiles(List<Map<String, Object>> results, boolean isFull){

        final String Checkmark  = "\u2713";
        Set<String> sampleIds = Sets.newHashSet();
        //collect the sample ids
        for(Map<String, Object> row : results){
            String idStr = String.valueOf(row.get("sample.id"));
            sampleIds.add(idStr);
        }

        //count per sample id the taxons fmaily .. and add them to the map
        sampleIds.forEach(sampleId ->{
            Long sampleIdLong = Long.valueOf(sampleId);

            Sample sample = mapSamples.get(sampleIdLong);


            GroundProtocolDTO groundProtocol = null;
            EvaluationGridDTO evalutationGrid = null;


            if(sample.getSampleProtocolType().isHasGround()){
                groundProtocol = groundProtocolReadService.getGroundProtocol(sampleIdLong);
            }

            if(sample.getSampleProtocolType().isHasGrid()){
                evalutationGrid= gridReadService.getEvalutationGrid(sampleIdLong);

            }

            for(Map<String, Object> row : results){
                if(sampleId.equalsIgnoreCase(String.valueOf(row.get("sample.id")))){
                    if(groundProtocol!=null){
                        row.put("sample.evalgrid.header",Checkmark);
                    }else{
                        row.put("sample.evalgrid.header","");
                    }
                    if(evalutationGrid!=null){
                        row.put("sample.groundproto.header",Checkmark);
                    }else{
                        row.put("sample.groundproto.header","");
                    }
                }
            }

        });

    }

     private String  getLinkedStations(List<SampleStation> stations) {

        String joinedStationNumbers = stations.stream()
                .map(SampleStation::getStationNumber)
                .collect(Collectors.joining(", ")); // "station1, station2, station3

        return joinedStationNumbers;
    }


    private void checkIfStationIsAuthorized(Long stationId, boolean editionMode) {
        //boolean filterByGroups = !sessionUserService.isSessionUserPermitted("midat:national");
        boolean filterByGroups = !sessionUserService.userHasNationalUserPermissionForMidat(sessionUserService.getSessionUserId());
        boolean isAppManager = sessionUserService.getSessionUserRolesNamesForMidat().contains("manager");
        if(filterByGroups) {
            Set<String> groupNames = getUserGroupsNames(true);

            Set<Long> authorizedStationIds = stationDAO.findPublishedStationsBelongingToGroups(sessionUserService.getSessionUserId(), groupNames, editionMode).stream().map(station -> station.getStationId()).collect(Collectors.toSet());

            if (!authorizedStationIds.contains(stationId)) {
                throw new UnauthorizedException("The user tried to select samples for a station he is not authorized.");
            }
        }
    }

    private List<Long> findStationsInRectangle(Double west, Double east, Double north, Double south,  Long userId, boolean isAppManager,boolean isNationalContributor, boolean editionMode) {
        List<Long> stationsIds;

        // requested access is for write
        if(editionMode){
            //add restrictions by group, if user does not have the "national" permission
            boolean filterByGroups = !sessionUserService.userHasNationalContributorPermissionForMidat(sessionUserService.getSessionUser().getId());
            if(filterByGroups){
                Set<String> groupNames = getUserGroupsNames(false);//sessionUser.getGroups().stream().map(group -> group.getName()).collect(Collectors.toSet());

                stationsIds = stationDAO.findStationsIdsInsideRectangleAndBelongingToGroups(west, east, north, south, groupNames, userId, editionMode);
            }else{
                stationsIds = stationDAO.findStationsIdsInsideRectangle(west, east, north, south, userId,  isAppManager, editionMode);
            }
            return stationsIds;
        }else{
            boolean filterByGroups = !sessionUserService.userHasNationalUserPermissionForMidat(sessionUserService.getSessionUser().getId());

            if(filterByGroups){
                Set<String> groupNames = getUserGroupsNames(true);//sessionUser.getGroups().stream().map(group -> group.getName()).collect(Collectors.toSet());
                groupNames.add("PUBLIC");
                stationsIds = stationDAO.findStationsIdsInsideRectangleAndBelongingToGroups(west, east, north, south, groupNames, userId, editionMode);
            }else{
                stationsIds = stationDAO.findStationsIdsInsideRectangle(west, east, north, south, userId,  isAppManager, editionMode);
            }
            return stationsIds;
        }
    }

    /**
     * @return A set os strings representing the names of the groups the currently logged in user belongs to.
     */

    private Set<String> getUserGroupsNames(boolean readOnly) {
        User user = sessionUserService.getSessionUser();
        if(!readOnly){
            return user.getRoleGroups().stream()
                    .filter(roleGroup-> roleGroup.getGroup() != null && roleGroup.getWritable() &&
                            roleGroup.getRole().getApplication().getCode().equalsIgnoreCase("midat")).collect(Collectors.toSet()).stream()
                    .map(roleGroup -> roleGroup.getGroup().getName()).collect(Collectors.toSet());
        }else{
            return user.getRoleGroups().stream()
                    .filter(roleGroup-> roleGroup.getGroup() != null  &&
                            roleGroup.getRole().getApplication().getCode().equalsIgnoreCase("midat")).collect(Collectors.toSet()).stream()
                    .map(roleGroup -> roleGroup.getGroup().getName()).collect(Collectors.toSet());
        }

    }

    /**
     * Applies security restrictions to the given parameters object, based on the currently logged in user.
     * @param parameters
     */
    private void buildSecurityRestrictions(SampleSearchParameters parameters) {
        User sessionUser = sessionUserService.getSessionUser();
        Long userId = sessionUser.getId();
        parameters.setCreationUserId(userId);
        parameters.setAppManager(sessionUserService.getSessionUserRolesNamesForMidat().contains("manager"));
        parameters.setNationalContributor(sessionUserService.userHasNationalContributorPermissionForMidat(userId));

        // requested access is for write
        if(parameters.isEditionMode()){
            //add restrictions by group, if user does not have the "national" permission
            boolean filterByGroups = !sessionUserService.userHasNationalContributorPermissionForMidat(userId);
            parameters.setFilterByUserGroups(filterByGroups);
            if(filterByGroups){
                Set<String> includedGroups = getUserGroupsNames(false);
                parameters.setUserGroups(includedGroups);
            }
        }else{
            boolean filterByGroups = !sessionUserService.userHasNationalUserPermissionForMidat(userId);
            parameters.setFilterByUserGroups(filterByGroups);
            if(filterByGroups){
                Set<String> includedGroups = getUserGroupsNames(true);
                includedGroups.add("PUBLIC");
                parameters.setUserGroups(includedGroups);
            }
        }
    }

    /**
     * Populates the indexes DTOs fields on the given listAndTranslate of SearchResultDTOs.
     * The ratings values ar obtained from the special dedicated service, so they are cached. This should not involve any DB hit.
     */
    private void populateIndexesDTO(List<SearchResultDTO> results) {
        for(SearchResultDTO result : results ){

            BioticWaterQualityRatingDTO ibchRating =
                    result.getIbchIndexValue() == null || result.getIbchLegendVersionId() == null  ? null : qualityRatingReadService.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_IBCH, result.getIbchIndexValue(),result.getIbchLegendVersionId());

            BioticWaterQualityRatingDTO makroRating =
                    result.getMakroIndexValue() == null || result.getMakroLegendVersionId() == null ? null : qualityRatingReadService.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_MAKROINDEX, result.getMakroIndexValue(),result.getMakroLegendVersionId());

            BioticWaterQualityRatingDTO spearRating =
                    result.getSpearIndexValue() == null || result.getSpearLegendVersionId() == null ? null : qualityRatingReadService.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_SPEARINDEX, result.getSpearIndexValue(),result.getSpearLegendVersionId());



            BioticIndexesDTO bioticIndexes = new BioticIndexesDTO(
                    result.getIbchIndexValue(), result.getMakroIndexValue(), result.getSpearIndexValue(),
                    ibchRating, makroRating, spearRating,null,null
            );
            result.setIndexes(bioticIndexes);
        }
    }

    /**
     * Populates the groups on the listAndTranslate of SearchResultDTOs recevied.
     */
    private void populateGroups(List<SearchResultDTO> results) {
        for (SearchResultDTO result : results) {
            Sample sample = sampleDAO.findById(result.getSampleId());
            result.setGroups(sample.getGroups().stream().map(group -> group.getName()).collect(Collectors.toList()));
        }
    }


    /**
     * Populates the stations liées  or the stations principal
     */
    private void poplulateStationAdditionalInfo(List<SearchResultDTO> results) {
        for (SearchResultDTO result : results) {

            // getting the the station link buffer value from the Thesaurus - aka 26.07.2019
            String raduisString =thesaurusReadOnlyService.getLocalizedString(ThesaurusCodes.MIDATHDITMTY_MIDATPARAM,"STALINKBUF",null);
            double raduis =Double.valueOf(raduisString);
            result.setStaLinkBuffInMeter(raduis);


            SampleStation sampleStation = stationDAO.findById(result.getStationId());
            result.setMainStations(sampleStation.getStationStations()!=null && sampleStation.getStationStations().size()>0);
            result.setLinkedToMainStation(sampleStation.getParent() !=null);
        }
    }


    /**
     *
     * Used to populate the groups as a String
     */
    private  void populateGroupsForExport(List<Map<String, Object>> results, boolean isFull){

        int smapleNumbers = 0;
        for(Map<String, Object> row : results){
            //add the groups:
            Sample sample;
            Long sampleId = Long.valueOf(String.valueOf(row.get("sample.id")));
            smapleNumbers++;
            if(mapSamples.get(sampleId) != null || (mapSamples.get(sampleId) == null && mapSamples.containsKey(sampleId))){
                sample =  mapSamples.get(sampleId);
            }else{
                sample = sampleDAO.findById((sampleId));
                mapSamples.put(sampleId, sample);

                // add the station also
                Long stationId = (long) row.get("sampleStation.id");
                mapSamplesStation.put(stationId, sample.getStation());
            }
            List<String> collect = sample.getGroups().stream().map(group -> group.getName()).collect(Collectors.toList());
            String groupsStr = String.join(", ",collect);
            row.put("sample.groupes", groupsStr);
        }
        logger.info("smapleNumbers is:"+smapleNumbers);
    }

    /**
     * populateIndexesForExport
     */

    private void populateIndexesForExport(List<Map<String, Object>> results,boolean isFull){
        for(Map<String, Object> row : results){

            BioticWaterQualityRatingDTO ibchRating =
                    row.get("sample.ibchIndexValue") == null || row.get("sample.ibchLegendVersionId") == null ? null : qualityRatingReadService.
                            getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_IBCH,Double.valueOf(String.valueOf(row.get("sample.ibchIndexValue"))),Integer.valueOf(String.valueOf(row.get("sample.ibchLegendVersionId"))));

            BioticWaterQualityRatingDTO makroRating =
                    row.get("sample.makroIndexValue") == null || row.get("sample.makroLegendVersionId") == null ? null : qualityRatingReadService.
                            getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_MAKROINDEX,Double.valueOf(String.valueOf(row.get("sample.makroIndexValue"))),Integer.valueOf(String.valueOf(row.get("sample.makroLegendVersionId"))));

            BioticWaterQualityRatingDTO spearRating =
                    row.get("sample.spearIndexValue") == null || row.get("sample.spearLegendVersionId") ==null ? null : qualityRatingReadService.
                            getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_SPEARINDEX, Double.valueOf(String.valueOf(row.get("sample.spearIndexValue"))),Integer.valueOf(String.valueOf(row.get("sample.spearLegendVersionId"))));

            row.put("sample.ibchIndexRatingCode",   ibchRating  == null ? null : ibchRating.ratingCode);
            row.put("sample.ibchIndexBgColorCode",  ibchRating  == null ? null : ibchRating.bgColorCode);
            row.put("sample.ibchIndexTextColorCode",ibchRating  == null ? null : ibchRating.textColorCode);


            row.put("sample.makroIndexRatingCode",   makroRating  == null ? null : makroRating.ratingCode);
            row.put("sample.makroIndexBgColorCode",  makroRating  == null ? null : makroRating.bgColorCode);
            row.put("sample.makroIndexTextColorCode",makroRating  == null ? null : makroRating.textColorCode);

            row.put("sample.spearIndexRatingCode",   spearRating  == null ? null : spearRating.ratingCode);
            row.put("sample.spearIndexBgColorCode",  spearRating  == null ? null : spearRating.bgColorCode);
            row.put("sample.spearIndexTextColorCode",spearRating  == null ? null : spearRating.textColorCode);
        }
    }

}
