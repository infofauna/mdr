package ch.cscf.midat.services.interfaces;

import java.util.List;

/**
 * @author: abdallah kanso
 */
public interface SampleIntervalReadService {

    List<String> findSampleInterval(String query);

}
