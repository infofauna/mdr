package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.domain.entities.midat.sample.SampleHydroregime;
import ch.cscf.jeci.domain.entities.midat.sample.SampleIndiceVersion;
import ch.cscf.jeci.persistence.daos.interfaces.midat.HydroregimeDAO;
import ch.cscf.jeci.persistence.daos.interfaces.midat.IndiceVersionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class HydroregimeService implements ch.cscf.midat.services.interfaces.HydroregimeService {

    @Autowired
    private HydroregimeDAO hydroregimeDAO;

    @Override
    @Transactional
    public SampleHydroregime getSampleHydroregime(Integer hdrOrder) {
            return hydroregimeDAO.getSampleHydroregime(hdrOrder);
    }


}