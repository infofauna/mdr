package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.EvaluationGridFieldMapping;
import ch.cscf.jeci.domain.entities.midat.sample.SampleHydroregime;
import ch.cscf.jeci.persistence.daos.interfaces.midat.HydroregimeDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;


@Repository
public class HydroregimeJpaDAO extends GenericJpaEntityDAO<SampleHydroregime> implements HydroregimeDAO {


    @Override
    public SampleHydroregime getSampleHydroregime(Integer hdrCode) {
        return
                getEm()
                        .createQuery("select hydro from SampleHydroregime hydro where hydro.order=:hdrCode", SampleHydroregime.class)
                        .setParameter("hdrCode", hdrCode)
                        .getSingleResult();

    }

}



