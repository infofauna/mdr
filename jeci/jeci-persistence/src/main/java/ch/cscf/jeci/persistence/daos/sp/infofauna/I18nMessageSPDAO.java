package ch.cscf.jeci.persistence.daos.sp.infofauna;

import ch.cscf.jeci.domain.dto.errors.Message;
import ch.cscf.jeci.persistence.daos.interfaces.admin.I18nMessagesDAO;

/**
 * @author: henryp
 */
public class I18nMessageSPDAO  implements I18nMessagesDAO{

    public static final String IN_ERROR_CODE="inErrorCode";
    public static final String IN_="in";
    public static final String OUT_="";

    @Override
    public Message getLocalizedMessage(Long errorCode, Long languageId){



        return null;
    }

}
