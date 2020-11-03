package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.sample.Sample;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleIntervalDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Abdallah kanso
 */

@Repository
public class SampleIntervalJPADAO extends GenericJpaEntityDAO<Sample> implements SampleIntervalDAO {

    @Override
    public List<String> findSampleInterval( Long userId) {
        return findSampleIntervalQuery(userId);
    }


    private List<String> findSampleIntervalQuery( Long userId) {
        //MM-dd-YYYY
        return
                getEm()
                        .createQuery("select concat(s.sampleDateMonth, '-', s.sampleDateDay, '-', s.sampleDateYear) as sampleDate from Sample s where (s.sampleDate = (select min(s2.sampleDate) from Sample s2  where (s2.published=true or s2.creationUser.id=:userId)) or s.sampleDate =  (select max(s2.sampleDate) from Sample s2  where (s2.published=true or s2.creationUser.id=:userId))) order by 1 desc ", String.class)
                        .setParameter("userId", userId)
                        .getResultList();
    }



}
