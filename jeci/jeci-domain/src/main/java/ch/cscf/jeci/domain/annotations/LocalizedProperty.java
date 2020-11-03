package ch.cscf.jeci.domain.annotations;

import java.lang.annotation.*;

/**
 * @author: henryp
 *
 * This annotation can be applied to a field of an entity. The value must be the name of a ThesaurusValue field.
 * The field annotated will automatically be filled with the thesaurus value specified, in the current user's language,
 * by the EntityFieldTranslatorService.
 */

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LocalizedProperty {

    String value();

    String forceLanguage() default "";

}
