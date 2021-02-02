package ch.cscf.midat.services.interfaces;


import ch.cscf.jeci.domain.entities.midat.sample.SampleHydroregime;

public interface HydroregimeService {
    SampleHydroregime getSampleHydroregime(Integer hdrOrder) ;
}
