package ch.cscf.jeci.services.general;

import ch.cscf.jeci.domain.entities.thesaurus.Language;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.LanguageDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author: henryp
 */
@Service
public class I18nServiceImpl implements I18nService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Object[] EMPTY = {};

    @Autowired
    protected LanguageDAO dao;

    @Autowired
    protected TransactionTemplate tt;

    @Autowired(required = false)
    protected ReloadableResourceBundleMessageSource source;

    protected final Map<String, Long> codeToId = new HashMap<>();

    protected final Map<Long, String> idToCode = new HashMap<>();

    @PostConstruct
    public void setup(){

        //pre-load the language code-to-ID and ID-to-code matching tables
        tt.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                logger.info("Building the language code-to-ID and ID-to-code mapping tables.");

                for(Language lang : dao.list()){
                    logger.info("Mapping : {} <--> {}", lang.getCode(), lang.getId());
                    codeToId.put(lang.getCode(), lang.getId());
                    idToCode.put(lang.getId(), lang.getCode());
                }
            }
        });
    }

    @Override
    public Long currentLanguageId(){
        String code = LocaleContextHolder.getLocale().getLanguage();
        return codeToId.get(code);
    }

    @Override
    public String currentLanguageCode(){
        return LocaleContextHolder.getLocale().getLanguage();
    }

    @Override
    public Locale currentLocale(){
        return LocaleContextHolder.getLocale();
    }

    @Override
    public String getMessage(String key){
        return source.getMessage(key, EMPTY, currentLocale());
    }

    @Override
    public String getMessage(String key, Object... args){
        return source.getMessage(key, args, currentLocale());
    }

    @Override
    public Long languageIdForCode(String languageCode){
        return codeToId.get(languageCode);
    }

}
