package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.domain.entities.midat.sample.SampleIndiceVersion;
import ch.cscf.jeci.persistence.daos.interfaces.midat.IndiceVersionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class IndiceVersionService implements ch.cscf.midat.services.interfaces.IndiceVersionService {

    @Autowired
    private IndiceVersionDAO indiceVersionDAO;

    @Override
    @Transactional
    public List<SampleIndiceVersion> getActiveVersions() {
            return (List<SampleIndiceVersion>) indiceVersionDAO.list();
    }


}