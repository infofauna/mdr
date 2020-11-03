package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.domain.entities.midat.sample.SampleStation;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleStationDAO;
import ch.cscf.jeci.services.security.interfaces.SessionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: abdallah kanso
 */
@Service
public class StationUpdateService implements ch.cscf.midat.services.interfaces.StationUpdateService {


    @Autowired
    private SampleStationDAO stationDAO;


    @Autowired
    private SessionUserService sessionUserService;

   /*

    @Override
    @Transactional
    public FeatureCollection getAllAuthorizedStationsAsFeatures(boolean filterForEdition) {

        final Collection<SampleStation> stations;

        Long userId = sessionUserService.getSessionUserId();

        //add restrictions by group, if user does not have the "national" permission
        boolean filterByGroups = !sessionUserService.isSessionUserPermitted("midat:national");
        if(filterByGroups){
            User user = sessionUserService.getSessionUser();
            Set<String> groupNames = user.getGroups().stream().map(group -> group.getName()).collect(Collectors.toSet());

            //should not happen, but data can be wrong !
            if(groupNames.isEmpty()){
                throw new IllegalStateException("Any user should always have membership at least of the PUBLIC group. Found a user with zero group membership : "+user.getUsername());
            }

            if(filterForEdition){
                groupNames.remove("PUBLIC");
            }

            //should not happen, but data can be wrong !
            if(groupNames.isEmpty()){
                return new FeatureCollection();
            }

            stations = stationDAO.findPublishedStationsBelongingToGroups(userId, groupNames);
        }else{
            stations = stationDAO.findPublishedStations(userId);
        }

        final Collection<SampleStationItem> items = itemDAO.list();

        final FeatureCollection featureCollection = new FeatureCollection();
        final Map<Long, Feature> featuresById = new HashMap<>(stations.size());

        final long currentLanguageId = i18NService.currentLanguageId();

        for(SampleStation station : stations){
            Feature feature = buildFeatureFromStation(station);
            featuresById.put(station.getId(), feature);
            featureCollection.add(feature);
        }

        for(SampleStationItem item : items){
            boolean overwrite = item.getLanguageId() == currentLanguageId;
            setItemValueAsFeatureProperty(featuresById, item, overwrite);
        }

        return featureCollection;
    }
*/

    @Override
    @Transactional
    public SampleStation linkunlinkStation(List<Long> cStations, Long mStation) {
        stationDAO.unlinkStationsFromParentStationById(mStation);
        if(cStations !=null){
            stationDAO.linkStationsToParentStationById(cStations,mStation);
        }
        return stationDAO.findByIdDetached(mStation);
    }

    /*
    @Override
    @Transactional
    public SampleStation linkStation(Long cStation, Long mStation) {
        SampleStation parent = stationDAO.findByIdDetached(mStation);
        SampleStation child = stationDAO.findByIdDetached(cStation);
        child.setParent(parent);
        stationDAO.merge(child);
        return child;
    }
    */

}
