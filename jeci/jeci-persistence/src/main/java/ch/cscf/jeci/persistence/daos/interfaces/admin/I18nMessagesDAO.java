package ch.cscf.jeci.persistence.daos.interfaces.admin;

import ch.cscf.jeci.domain.dto.errors.Message;

/**
 * @author: henryp
 */
public interface I18nMessagesDAO {

    Message getLocalizedMessage(Long errorCode, Long languageId);

}
