package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleIntervalDAO;
import ch.cscf.jeci.services.security.interfaces.SessionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: abdallah kanso
 */

@Service
public class SampleIntervalReadService implements ch.cscf.midat.services.interfaces.SampleIntervalReadService {

    @Autowired
    private SampleIntervalDAO sampleIntervalDAO;


    @Autowired
    private SessionUserService sessionUserService;


    @Override
    @Transactional
    public List<String> findSampleInterval(String query) {
            return sampleIntervalDAO.findSampleInterval(sessionUserService.getSessionUserId());
    }


}

