package ch.cscf.jeci.services.general;

import java.util.Locale;

/**
 * Created by henryp on 02/04/15.
 */
public interface I18nService {
    Long currentLanguageId();

    String currentLanguageCode();

    Locale currentLocale();

    String getMessage(String key);

    String getMessage(String key, Object... args);

    Long languageIdForCode(String languageCode);
}
