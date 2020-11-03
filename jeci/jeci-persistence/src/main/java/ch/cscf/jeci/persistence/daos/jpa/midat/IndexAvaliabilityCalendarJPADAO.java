package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.base.EntityStatus;
import ch.cscf.jeci.domain.entities.midat.IndexAvaliabilityCalendar;
import ch.cscf.jeci.persistence.daos.interfaces.midat.IndexAvaliabilityCalendarDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by abdallahkanso on 13.07.17.
 */

@Repository
public class IndexAvaliabilityCalendarJPADAO extends GenericJpaEntityDAO<IndexAvaliabilityCalendar> implements IndexAvaliabilityCalendarDAO {

    @Override
    public List<IndexAvaliabilityCalendar> getIndiceAvaliabiltyCalByDateElevation(Integer day, Integer month, Double elevation) {

        EntityStatus active = EntityStatus.ACTIVE;
        String jpql = "select a from IndexAvaliabilityCalendar a " +
                        " where "+elevation +" BETWEEN a.elevationMin and a.elevationMax "+
                        " and "+day +" BETWEEN a.startDay and a.endDay "+
                        " and "+month +"  BETWEEN a.startMonth and a.endMonth and a.status ='"+String.valueOf(active.getDbCode())+"'";


        List<IndexAvaliabilityCalendar> list = getEm().createQuery(jpql,IndexAvaliabilityCalendar.class).getResultList();
        return list;
    }
}
