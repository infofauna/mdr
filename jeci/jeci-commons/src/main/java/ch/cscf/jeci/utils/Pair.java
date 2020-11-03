package ch.cscf.jeci.utils;

/**
 * Simple "tuple" class to hold two unrelated values. Useful for when you want to return 2 values in a method but don't
 * want to use a list, array, map or to create a class for it ! For example : return a list of objects limited to X
 * objects and a flag to indicate if there are more objects available.
 * See : http://stackoverflow.com/questions/457629/how-to-return-multiple-objects-from-a-java-method
 * @param <A> Type of the first item in the pair
 * @param <B> Type of the second item in the pair
 * @author Pierre Henry
 */
public class Pair<A,B> {

    public static <P, Q> Pair<P, Q> makePair(P p, Q q) {
        return new Pair<P, Q>(p, q);
    }

    public final A a;
    public final B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((a == null) ? 0 : a.hashCode());
        result = prime * result + ((b == null) ? 0 : b.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        @SuppressWarnings("rawtypes")
        Pair other = (Pair) obj;
        if (a == null) {
            if (other.a != null) {
                return false;
            }
        } else if (!a.equals(other.a)) {
            return false;
        }
        if (b == null) {
            if (other.b != null) {
                return false;
            }
        } else if (!b.equals(other.b)) {
            return false;
        }
        return true;
    }

    public boolean isInstance(Class<?> classA, Class<?> classB) {
        return classA.isInstance(a) && classB.isInstance(b);
    }

    @SuppressWarnings("unchecked")
    public static <P, Q> Pair<P, Q> cast(Pair<?, ?> pair, Class<P> pClass, Class<Q> qClass) {

        if (pair.isInstance(pClass, qClass)) {
            return (Pair<P, Q>) pair;
        }

        throw new ClassCastException();

    }

}