package ch.cscf.jeci.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Comparator;

/**
 * This utility class is a comparator that compares to object of a given class, using one of the properties of the objects.
 * For example, if the property name specified id "id", the comparator will attempt to access the property called "id"
 * of both objects, and return a comparison value based on the value of this property.
 * Typical use for this class is to sort a collection based on a property (e.g. name, id...)
 * The property used must be of a type that implements Comparable, so it works well with Strings, integers, Date, etc...
 * @author Pierre Henry
 *
 * @param <T>
 */
public class PropertyBeanComparator<T> implements Comparator<T>{
	
	static Logger logger = LoggerFactory.getLogger(PropertyBeanComparator.class);
	
	private String comparedPropertyName;
	
	public PropertyBeanComparator(String propertyName) {
		this.comparedPropertyName=propertyName;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int compare(T o1, T o2) {
		if(o1 == null || o2 == null){
			throw new IllegalArgumentException("Both parameters to the compare method must not be null !");
		}
		
		Comparable value1=null, value2=null;
		
		try {
			value1 = (Comparable)PropertyUtils.getProperty(o1, comparedPropertyName);
		} catch (Exception e) {
			logger.error(MessageFormat.format("The property {0} cannot be accessed on bean of class {1}.", comparedPropertyName, o1.getClass().getCanonicalName()));
		}
		
		try {
			value2 = (Comparable)PropertyUtils.getProperty(o2, comparedPropertyName);
		} catch (Exception e) {
			logger.error(MessageFormat.format("The property {0} cannot be accessed on bean of class {1}.", comparedPropertyName, o2.getClass().getCanonicalName()));
		}
		
		if(value1==null && value2==null){
			return 0;
		}
		if(value1 == null){
			return -1; 
		}
		if(value2 == null){
			return 1;
		}
		return value1.compareTo(value2);
	}

}
