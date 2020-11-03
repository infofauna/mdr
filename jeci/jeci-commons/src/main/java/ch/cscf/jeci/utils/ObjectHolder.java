package ch.cscf.jeci.utils;

/**
 * Class that can be used to attribute variables from an inner class.
 * @author Pierre Henry
 *
 * @param <T>
 */
public class ObjectHolder<T> {
	
	private T object;

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
}
