package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.ProtocolImportHeader;
import ch.cscf.jeci.persistence.daos.interfaces.midat.ProtocolImportHeaderDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author: henryp
 */
@Repository
public class ProtocolImportHeaderJpaDAO extends GenericJpaEntityDAO<ProtocolImportHeader> implements ProtocolImportHeaderDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());



    public Long persistWithRet(ProtocolImportHeader entity){
        EntityManager em = getEm();
       // EntityTransaction et = em.getTransaction();
        //et.begin();
        em.persist(entity);
        //et.commit();
        return entity.getId();
    }

    public Long insertHeaderInfo(Map<String,String> headerData){
        String q = getNativeQuery(headerData);
        logger.info(">>>>>>>>>>>>>>>>>>insertHeaderInfo query is :"+q);

        EntityManager em = getEm();
        //EntityTransaction et = em.getTransaction();
        //et.begin();


        Query query = em.createNativeQuery(q);
        int rt = query.executeUpdate();
        logger.info(">>>>>>>>>>>>>>>>>>insertHeaderInfo return value:"+rt);

        //et.commit();
        return Long.valueOf(rt);
    }


    private String getNativeQuery(Map<String,String> headerData){
        StringBuffer queryBuff = new StringBuffer(1024);
        queryBuff.append(" INSERT INTO MIDAT.IMPORTPROTOCOLHEADER ");
        String columns = "";
        String columnsValue ="";

        for (Map.Entry<String, String> entry : headerData.entrySet()) {
            columns +=entry.getKey()+", " ;
            columnsValue +=entry.getValue()+", " ;
        }
        columns = columns.substring(0, columns.length() - 2);
        columnsValue = columnsValue.substring(0, columnsValue.length() - 2);

        queryBuff.append("( "+columns+" )");
        queryBuff.append(" values ");
        queryBuff.append("( "+columnsValue+" )");
        return queryBuff.toString();
    }


    public Long getSequenceNextValue() {
        String sql = "select MIDAT.SEQ_IMPORTPROTOCOLHEADER.nextval from dual";
        BigDecimal nextId = (BigDecimal) getEm().createNativeQuery(sql).getSingleResult();
        return nextId.longValue();

    }


}
