package ch.cscf.jeci.services.general;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * @author: henryp
 */
public class CustomReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    /**
     * This method allows to get a listAndTranslate of the messages available from this bundle source.
     * It is used in ch.cscf.jeci.web.jsi18n.JsI18nInterceptor.
     * @param prefix
     * @param localeCode
     * @return
     */
    public Map<String, String> getMessagesStartingWith(String prefix, String localeCode) {

        Map<String, String> messages = new HashMap<>();
        Locale locale = new Locale(localeCode);


        PropertiesHolder propertiesHolder = getMergedProperties(locale);
        Properties properties = propertiesHolder.getProperties();

        for(Object key : properties.keySet()){
            if(((String)key).startsWith(prefix)) {
                messages.put((String) key, StringEscapeUtils.escapeJavaScript((String)properties.get(key)));
            }
        }

        return messages;
    }

}
