package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.midat.IndexAvaliabilityCalendar;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * Created by abdallahkanso on 13.07.17.
 */
public interface IndexAvaliabilityCalendarDAO  extends GenericEntityDAO<IndexAvaliabilityCalendar> {

    List<IndexAvaliabilityCalendar> getIndiceAvaliabiltyCalByDateElevation(Integer day, Integer month, Double elevation );
}
