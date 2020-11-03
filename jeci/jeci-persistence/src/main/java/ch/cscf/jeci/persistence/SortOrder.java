package ch.cscf.jeci.persistence;

/**
 * Simple enum denoting ascending or descending sort sortOrder and the default.
 */
public enum SortOrder {
    asc, desc;
    public static final SortOrder DEFAULT = asc;
}
