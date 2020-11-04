package ch.cscf.midat.services.interfaces;


import ch.cscf.jeci.domain.entities.midat.sample.SampleIndiceVersion;

import java.util.List;

public interface IndiceVersionService {
     List<SampleIndiceVersion> getActiveVersions();
}
