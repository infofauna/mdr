package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.dto.midat.BioticWaterQualityRatingDTO;
import ch.cscf.jeci.domain.dto.midat.FilterDTO;
import ch.cscf.jeci.domain.entities.midat.sample.SampleStationItemView;
import ch.cscf.jeci.domain.entities.midat.sample.StationSamplesView;
import ch.cscf.jeci.domain.entities.security.User;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleStationDAO;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleStationItemDAO;
import ch.cscf.jeci.persistence.daos.thesaurus.interfaces.ThesaurusReadOnlyService;
import ch.cscf.jeci.services.general.I18nService;
import ch.cscf.jeci.services.security.interfaces.SessionUserService;
import com.google.common.collect.Iterables;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: henryp
 */
@Service
public class StationReadService implements ch.cscf.midat.services.interfaces.StationReadService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String WATERCOURSE = "watercourse";
    public static final String LOCALITY = "locality";

    private static final String COLOR_CODE_NOT_CALCULATED_INDEX = "#808080";
    private static final String COLOR_CODE_DEFAULT = "#TODO_RED";


    @Autowired
    private SampleStationDAO stationDAO;

    @Autowired
    private SampleStationItemDAO itemDAO;

    @Autowired
    private I18nService i18NService;

    @Autowired
    private ThesaurusReadOnlyService thesaurus;

    @Autowired
    private SessionUserService sessionUserService;

    @Autowired
    private GeoStationsSession stationsSession;

    @Autowired
    private ch.cscf.midat.services.interfaces.BioticWaterQualityRatingReadService qualityRatingReadService;

    private long watercourseItemTypeId;
    private long localityItemTypeId;
    private  Collection<SampleStationItemView> items;

    @PostConstruct
    public void init() {
        watercourseItemTypeId = thesaurus.getValueId(ThesaurusCodes.REALM_MIDATSTITMTY, ThesaurusCodes.MIDATSTITMTY_WATERCOURSE);
        localityItemTypeId = thesaurus.getValueId(ThesaurusCodes.REALM_MIDATSTITMTY, ThesaurusCodes.MIDATSTITMTY_LOCALITY);
    }


    @Override
    @Transactional
    public FeatureCollection getAllAuthorizedStationsAsFeatures(boolean filterForEdition) {
        long start = System.currentTimeMillis();
        final Collection<StationSamplesView> stations;
        final List<StationSamplesView> initialStations;

        Long userId = sessionUserService.getSessionUserId();
        boolean isAppManager = sessionUserService.getSessionUserRolesNamesForMidat().contains("manager");

        if(filterForEdition){
            //add restrictions by group, if user does not have the "national" permission
            boolean filterByGroups = !sessionUserService.userHasNationalContributorPermissionForMidat(sessionUserService.getSessionUser().getId());
            if(filterByGroups){
                Set<String> groupNames = getUserGroupsNames(false);//sessionUser.getGroups().stream().map(group -> group.getName()).collect(Collectors.toSet());
                if (groupNames.isEmpty()) {
                    return new FeatureCollection();
                }
                initialStations =stationDAO.findPublishedStationsBelongingToGroups(userId, groupNames, filterForEdition);

            }else{
                initialStations = stationDAO.findPublishedStations(userId,isAppManager, filterForEdition);
            }
        }else{
            boolean filterByGroups = !sessionUserService.userHasNationalUserPermissionForMidat(sessionUserService.getSessionUser().getId());

            if(filterByGroups){
                Set<String> groupNames = getUserGroupsNames(true);//sessionUser.getGroups().stream().map(group -> group.getName()).collect(Collectors.toSet());
                groupNames.add("PUBLIC");

                if (groupNames.isEmpty()) {
                    return new FeatureCollection();
                }
                initialStations =stationDAO.findPublishedStationsBelongingToGroups(userId, groupNames, filterForEdition);
            }else{
                initialStations = stationDAO.findPublishedStations(userId,isAppManager, filterForEdition);
            }

        }


        if(stationsSession.getItems() !=null && stationsSession.getItems().size()>0){
            items = stationsSession.getItems();
        }else{
            items = stationDAO.findSampleStationItems();
            stationsSession.setItems(items);
        }


        ArrayList<SampleStationItemView> newItems = new ArrayList<>(items);
        /* store the stations in the session in order to filter them when changing the date slider ...*/
        stationsSession.setStations(initialStations);
        stationsSession.setSampleStationItemView(newItems);

        stations = stationsSession.getStations();
        //filterDuplicatedSessionsIfAny(stations);

        final FeatureCollection featureCollection = new FeatureCollection();
        final Map<Long, Feature> featuresById = new HashMap<>(stations.size());

        final long currentLanguageId = i18NService.currentLanguageId();

        for (StationSamplesView station : stations) {
            Feature feature = buildFeatureFromStation(station);
            featuresById.put(station.getStationId(), feature);
            featureCollection.add(feature);
        }



        for (SampleStationItemView item : items) {
            boolean overwrite = item.getLanguageId() == currentLanguageId;
            setItemValueAsFeatureProperty(featuresById, item, overwrite);
        }

        return featureCollection;
    }

    private void filterDuplicatedSessionsIfAny(Collection<StationSamplesView> stations) {
        if(stations.size()>0){
         HashSet<Object> seen = new HashSet<>();
         stations.removeIf(s -> !seen.add(s.getStationId()));
        }
    }

    private Set<String> getUserGroupNamesForMidat(User user) {
        return user.getRoleGroups().stream()
                .filter(roleGroup-> roleGroup.getGroup() != null &&
                        roleGroup.getRole().getApplication().getCode().equalsIgnoreCase("midat")).collect(Collectors.toSet()).stream()
                .map(roleGroup -> roleGroup.getGroup().getName()).collect(Collectors.toSet());
    }

    private void setItemValueAsFeatureProperty(Map<Long, Feature> featuresById, SampleStationItemView item, boolean overwrite) {
        Feature station = featuresById.get(item.getStationId());
        if (station != null) {
            String propertyName = getPropertyNameForItem(item);
            if (propertyName != null) {
                if (overwrite || station.getProperty(propertyName) == null) {
                    station.setProperty(propertyName, item.getValue());
                }
            }
        }
    }

    private Feature buildFeatureFromStation(StationSamplesView station) {
        Feature feature = new Feature();
        feature.setGeometry(new Point(station.getCoordinateX(), station.getCoordinateY()));
        feature.setId("" + station.getStationId());
        feature.setProperty("stationNumber", station.getStationNumber());
        return feature;
    }

    private String getPropertyNameForItem(SampleStationItemView item) {
        if (item.getTypeId() == watercourseItemTypeId) {
            return WATERCOURSE;
        }
        if (item.getTypeId() == localityItemTypeId) {
            return LOCALITY;
        }
        return null;
    }

    @Override
    @Transactional
    public List<String> findWatercourseNamesLike(String query) {
        if (sessionUserService.userHasNationalUserPermissionForMidat(sessionUserService.getSessionUserId())) {
            return itemDAO.findWatercourseNamesLike(query, sessionUserService.getSessionUserId());
        } else {
            return itemDAO.findWatercourseNamesLikeInGroups(query, sessionUserService.getSessionUserId(), sessionUserService.getSessionUserGroupsIdsForMidat());
        }
    }

    @Override
    @Transactional
    public List<String> findLocalityNamesLike(String query) {
        if (sessionUserService.userHasNationalUserPermissionForMidat(sessionUserService.getSessionUserId())) {
            return itemDAO.findLocalityNamesLike(query, sessionUserService.getSessionUserId());
        } else {
            return itemDAO.findLocalityNamesLikeInGroups(query, sessionUserService.getSessionUserId(), sessionUserService.getSessionUserGroupsIdsForMidat());
        }
    }


    @Override
    @Transactional
    public List<String> findStationNumbersLike(String query) {
        if (sessionUserService.userHasNationalUserPermissionForMidat(sessionUserService.getSessionUserId())) {
            return stationDAO.findStationNumbersLike(query, sessionUserService.getSessionUserId());
        } else {
            return stationDAO.findStationNumbersLikeInGroups(query, sessionUserService.getSessionUserId(), sessionUserService.getSessionUserGroupsIdsForMidat());
        }
    }

    @Override
    @Transactional
    public List<String> findGewissNumbersLike(String query) {
        if (sessionUserService.userHasNationalUserPermissionForMidat(sessionUserService.getSessionUserId())) {
            return stationDAO.findGewissNumbersLike(query, sessionUserService.getSessionUserId());
        } else {
            return stationDAO.findGewissNumbersLikeInGroups(query, sessionUserService.getSessionUserId(), sessionUserService.getSessionUserGroupsIdsForMidat());
        }
    }


    @Override
    @Transactional
    public FeatureCollection getAuthorizedStationsBasedOnPresetFiltersAsFeatures(boolean filterForEdition) {
        long start = System.currentTimeMillis();

        final List<StationSamplesView> stations;
        final List<StationSamplesView> initialStations;

        Long userId = sessionUserService.getSessionUserId();
        boolean isAppManager = sessionUserService.getSessionUserRolesNamesForMidat().contains("manager");
        boolean isNationalContributor = sessionUserService.userHasNationalContributorPermissionForMidat(userId);

        if(filterForEdition){
            //add restrictions by group, if user does not have the "national" permission
            boolean filterByGroups = !sessionUserService.userHasNationalContributorPermissionForMidat(sessionUserService.getSessionUser().getId());
            if(filterByGroups){
                Set<String> groupNames = getUserGroupsNames(false);
                if (groupNames.isEmpty()) {
                    return new FeatureCollection();
                }
                initialStations =stationDAO.findFilteredPublishedStationsBelongingToGroups(userId, groupNames,stationsSession.getParameters(),stationsSession.hasFiltersOtherThanDateAndKey(), filterForEdition);

            }else{
                initialStations = stationDAO.findFilteredPublishedStations(userId,stationsSession.getParameters(),stationsSession.hasFiltersOtherThanDateAndKey(),isAppManager, filterForEdition);
            }
        }else{
            boolean filterByGroups = !sessionUserService.userHasNationalUserPermissionForMidat(sessionUserService.getSessionUser().getId());

            if(filterByGroups){
                Set<String> groupNames = getUserGroupsNames(true);
                groupNames.add("PUBLIC");

                if (groupNames.isEmpty()) {
                    return new FeatureCollection();
                }
                initialStations =stationDAO.findFilteredPublishedStationsBelongingToGroups(userId, groupNames,stationsSession.getParameters(),stationsSession.hasFiltersOtherThanDateAndKey(), filterForEdition);
            }else{
                initialStations = stationDAO.findFilteredPublishedStations(userId,stationsSession.getParameters(),stationsSession.hasFiltersOtherThanDateAndKey(),isAppManager, filterForEdition);
            }

        }


        if(stationsSession.getItems() !=null && stationsSession.getItems().size()>0){
            items = stationsSession.getItems();
        }else{
            items = stationDAO.findSampleStationItems();
            stationsSession.setItems(items);
        }

        /* store the stations in the session in order to filter them when changing the date slider ...*/
        stationsSession.setStations(initialStations);
        stationsSession.setSampleStationItemView(new ArrayList<>(items));

        stations = stationsSession.getStations();

        final FeatureCollection featureCollection = new FeatureCollection();
        final Map<Long, Feature> featuresById = new HashMap<>(stations.size());

        final long currentLanguageId = i18NService.currentLanguageId();

        for (StationSamplesView station : stations) {
            Feature feature = buildFeatureFromStation(station);
            featuresById.put(station.getStationId(), feature);
            featureCollection.add(feature);
        }


        for (SampleStationItemView item : items) {
            boolean overwrite = item.getLanguageId() == currentLanguageId;
            setItemValueAsFeatureProperty(featuresById, item, overwrite);
        }


        return featureCollection;
    }

    @Override
    @Transactional
    public FeatureCollection getFilteredAuthorizedStationsAsFeatures(boolean filterForEdition, String index, String startDate, String endDate) {


        final Collection<StationSamplesView> stations = filterAuthorizedStations( stationsSession.getStations(),filterForEdition);
        final Collection<SampleStationItemView> items = stationsSession.getSampleStationItemView();


        final FeatureCollection featureCollection = new FeatureCollection();
        final Map<Long, Feature> featuresById = new HashMap<>(stations.size());
        final long currentLanguageId = i18NService.currentLanguageId();


        Map<Long, String> colorById = new HashMap<>(stations.size());

        List<StationSamplesView> filteredStations = stations.stream().filter(station -> {
            List<StationSamplesView> samples = station.getStationSamples(); // original samples per station
            /* filter out samples that are out the date range [startDate, endDate] */
            List<StationSamplesView> filteredSamples = samples.stream().filter(s -> stationsSession.isSampleDateBetweenStartAndEndDate(s.getSampleDate(), startDate, endDate)).collect(Collectors.toList());
            return filteredSamples != null && filteredSamples.size() > 0;
        }).collect(Collectors.toList());

        // get the station display color based on the selected index
        filteredStations.stream().forEach(station -> {
            List<StationSamplesView> allSamples = station.getStationSamples();

            List<StationSamplesView> filteredSamples = allSamples.stream().filter(s -> stationsSession.isSampleDateBetweenStartAndEndDate(s.getSampleDate(), startDate, endDate)).collect(Collectors.toList());
            // sort samples by sampleDate ...
            Collections.sort(filteredSamples, new Comparator<StationSamplesView>() {
                public int compare(StationSamplesView o1, StationSamplesView o2) {
                    if (o1.getSampleDate() == null || o2.getSampleDate() == null)
                        return 0;
                    return o1.getSampleDate().compareTo(o2.getSampleDate());
                }
            });


            StationSamplesView recentSample = Iterables.getLast(filteredSamples, null);
            String color = null;

            if (recentSample != null && index.trim().length() > 0) {
                color = getColorCodePerIndexValue(index,

                        recentSample.getIbchIndexValue(),
                        recentSample.getIbchLegendVersionId(),

                        recentSample.getSpearIndexValue(),
                        recentSample.getSpearLegendVersionId(),

                        recentSample.getMakroIndexValue(),
                        recentSample.getMakroLegendVersionId()

                );
                logger.info("Station["+station.getStationNumber()+"] Color["+color+"] was calculated based on Sample date ["+recentSample.getSampleDate()+"]");
                colorById.put(station.getStationId(), color);
            }
        });

        if (filteredStations != null) {
            logger.info("We still have :" + filteredStations.size() + " Stations / " + stations.size() + " Stations");
        }

        for (StationSamplesView station : filteredStations) {
            Feature feature = buildFeatureV2FromStation(station, colorById.get(station.getStationId()));
            featuresById.put(station.getStationId(), feature);
            featureCollection.add(feature);
        }

        for (SampleStationItemView item : items) {
            boolean overwrite = item.getLanguageId() == currentLanguageId;
            setItemValueAsFeatureProperty(featuresById, item, overwrite);
        }

        return featureCollection;
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

    /* get the filters in order to display them ine the map page */
    @Override
    public List<FilterDTO> getFilters(){
        return stationsSession.getFilters();
    }


    /*
    returns the color code per  index && index value
   */
    private String getColorCodePerIndexValue(String index,
                                             Double ibchIndexValue,
                                             Integer ibchLegendVersionId,
                                             Double spearIndexValue,
                                             Integer spearLegendVersionId,
                                             Double makroIndexValue,
                                             Integer makroLegendVersionId) {
        String colorCode = null;
        if ((ibchIndexValue == null && ThesaurusCodes.MIDATINDICE_IBCH.equalsIgnoreCase(index))
                || (spearIndexValue == null && ThesaurusCodes.MIDATINDICE_SPEARINDEX.equalsIgnoreCase(index))
                || (makroIndexValue == null && ThesaurusCodes.MIDATINDICE_MAKROINDEX.equalsIgnoreCase(index))
                || (ibchLegendVersionId == null)
                || (spearLegendVersionId == null)
                || (makroLegendVersionId == null)
                ) {
            colorCode = COLOR_CODE_NOT_CALCULATED_INDEX;
        } else {
            if (ThesaurusCodes.MIDATINDICE_IBCH.equalsIgnoreCase(index)) {
                BioticWaterQualityRatingDTO ibchRating = qualityRatingReadService.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_IBCH, ibchIndexValue.intValue(),ibchLegendVersionId);
                colorCode = ibchRating.bgColorCode;
            } else if (ThesaurusCodes.MIDATINDICE_SPEARINDEX.equalsIgnoreCase(index)) {
                BioticWaterQualityRatingDTO spearRating = qualityRatingReadService.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_SPEARINDEX, spearIndexValue.intValue(),spearLegendVersionId);
                colorCode = spearRating.bgColorCode;
            } else if (ThesaurusCodes.MIDATINDICE_MAKROINDEX.equalsIgnoreCase(index)) {
                BioticWaterQualityRatingDTO makroRating = qualityRatingReadService.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_MAKROINDEX, makroIndexValue.intValue(),makroLegendVersionId);
                colorCode = makroRating.bgColorCode;
            }
        }
        return colorCode;
    }

    private Feature buildFeatureV2FromStation(StationSamplesView station, String stationColor) {
        Feature feature = new Feature();
        feature.setGeometry(new Point(station.getCoordinateX(), station.getCoordinateY()));
        feature.setId("" + station.getStationId());
        feature.setProperty("stationNumber", station.getStationNumber());

        if (stationColor != null && stationColor.trim().length() > 0) {
            feature.setProperty("stationColor", stationColor);
        }
        return feature;
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



}
