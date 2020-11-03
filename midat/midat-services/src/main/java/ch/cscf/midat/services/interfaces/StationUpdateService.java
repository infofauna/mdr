package ch.cscf.midat.services.interfaces;

import ch.cscf.jeci.domain.entities.midat.sample.SampleStation;
import org.geojson.FeatureCollection;

import java.util.List;

/**
 * @author: abdallah kanso
 */
public interface StationUpdateService {
     SampleStation linkunlinkStation(List<Long> cStations,Long mStation);
}
