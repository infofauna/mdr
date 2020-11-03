package ch.cscf.jeci.services.general;

import ch.cscf.jeci.domain.annotations.LocalizedProperty;
import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;
import ch.cscf.jeci.persistence.daos.thesaurus.interfaces.ThesaurusReadOnlyService;
import ch.cscf.jeci.persistence.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @author: henryp
 */
@Service
public class EntityFieldTranslatorService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThesaurusReadOnlyService thesaurusReadOnlyService;

    @Autowired
    private I18nService i18NService;

    @Transactional(readOnly = true)
    public void fillLocalizedFields(Collection<? extends BaseEntity> entities){
        for(BaseEntity entity : entities){
            fillLocalizedFields(entity);
        }
    }

    @Transactional(readOnly = true)
    public void fillLocalizedFields(BaseEntity entity){

        Class<?> entityClass = entity.getClass();

        for(Field field : ReflectionUtil.getAllFields(entityClass)){
            if(isFieldLocalized(field)) {
               fillLocalizedField(field, entityClass, entity);
            }
        }
    }

    private boolean isFieldLocalized(Field field){
        LocalizedProperty localizedProperty = field.getAnnotation(LocalizedProperty.class);
        return localizedProperty != null;
    }

    private void fillLocalizedField(Field field, Class<?> entityClass, Object entity){
        LocalizedProperty localizedProperty = field.getAnnotation(LocalizedProperty.class);

        if(localizedProperty == null){
            throw new IllegalArgumentException("The field provided has no annotation of type "+LocalizedProperty.class.getName());
        }

        Field sourceCodeValueField = getValueField(field, entityClass, localizedProperty.value());

        if (sourceCodeValueField != null) {
            setLocalizedFieldValue(field, sourceCodeValueField, entityClass, entity);
        }
    }

    private Field getValueField(Field field, Class<?> entityClass, String fieldName) {

        Field sourceCodeValueField = ReflectionUtil.getDeclaredFieldFromClassAndSuperClasses(entityClass, fieldName);

        if(sourceCodeValueField == null){
            logger.warn("The field {} of the class {} is annotated with {} but the specified value \"{}\" does not match any field.", field.getName(), entityClass.getCanonicalName(), LocalizedProperty.class.getName(), fieldName);
            return null;
        }

        if(!sourceCodeValueField.getType().equals(ThesaurusValue.class)){
            logger.warn("The field {} of the class {} is annotated with {} but the specified value \"{}\" does not match a field f type {}.", field.getName(), entityClass.getCanonicalName(), LocalizedProperty.class.getName(),  fieldName, ThesaurusValue.class.getName());
            return null;
        }

        return sourceCodeValueField;
    }

    private void setLocalizedFieldValue(Field targetField, Field sourceCodeValueField, Class<?> entityClass, Object entity) {
        try{
            sourceCodeValueField.setAccessible(true);
            ThesaurusValue value = (ThesaurusValue) sourceCodeValueField.get(entity);

            if(value != null){ //the source value could be null
                targetField.setAccessible(true);
                targetField.set(entity, thesaurusReadOnlyService.getLocalizedString(value.getRealm().getCode(), value.getCode(), i18NService.currentLanguageCode()));
            }
        }catch(IllegalAccessException e){
            logger.warn("The field {} of the class {} is annotated with {} but the specified source value field cannot be read.", targetField.getName(), entityClass.getCanonicalName(), LocalizedProperty.class.getName());
        }
    }
}
