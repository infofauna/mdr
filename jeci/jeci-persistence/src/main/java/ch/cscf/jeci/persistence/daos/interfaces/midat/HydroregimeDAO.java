package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.midat.sample.SampleHydroregime;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;


public interface HydroregimeDAO extends GenericEntityDAO<SampleHydroregime> {
    SampleHydroregime getSampleHydroregime(Integer hdrCode) ;
}



