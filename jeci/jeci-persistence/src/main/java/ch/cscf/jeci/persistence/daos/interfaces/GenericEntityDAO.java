package ch.cscf.jeci.persistence.daos.interfaces;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;

import java.util.Collection;
import java.util.List;

/**
 * @author: henryp
 */
public interface GenericEntityDAO<T extends BaseEntity> {

    /**
     * Obtains an entity by id.
     * @param id
     * @return
     */
    T findById(Long id, String... relationshipsToFetch);

    /**
     * Obtains an entity by id and detaches it before returning it.
     * @param id
     * @return
     */
    T findByIdDetached(Long id);

    /**
     * Persists an entity. Used to make new entities persistent.
     * @param entity
     */
    void persist(T entity);


    T merge(T entity);


    /**
     * Refreshes an entity.
     * @param entity
     */
    void refresh(T entity);

    /**
     * Deletes an entity. The entity will no longer be persistent and the corresponding row will be deleted from the database table.
     * @param entity
     */
    void delete(T entity);

    /**
     * Loads all existing persistent instances of the managed entity, without any particular sortOrder.
     * @return
     */
    Collection<T> list();


    /**
     * Loads all existing persistent instances of the managed entity.
     * This method allows to specify a column name for sorting.
     * @param orderByCol The name of the entity field by which the list should be sorted
     * @return
     */
    List<T> list(String orderByCol);

    /**
     * Loads all existing persistent instances of the managed entity.
     * This method allows to specify a column name for sorting and any number of relationship to pre-fetch.
     * @param orderByCol
     * @param relationshipsToFetch
     * @return
     */
    List<T> list(String orderByCol, List<String> relationshipsToFetch);


    /**
     * Loads the persistent instances of the managed entity specified by the pagination Page object passed and the ordering specified.
     * @param orderByCol  The name of the entity field by which the list should be sorted
     * @param page A Page object that specifies the paging parameters for the list to be loaded : number of rows to load and number of the first row to row
     * @return
     */
    List<T> list(String orderByCol, Page page);

    /**
     * Loads the persistent instances of the managed entity with the sorting specified.
     * @param orderByCol  The name of the entity field by which the list should be sorted. If <code>orderByCol</code> does not match any existing field, a runtime exception will be thrown by the underlying implementation (JPA).
     * @param order asc or desc sort sortOrder as defined in the <code>SortOrder</code> enum.
     * @return
     */
    List<T> list(String orderByCol, SortOrder order);

    /**
     * Loads the persistent instances of the managed entity specified by the pagination Page object passed and the ordering specified.
     * It also allows to specify a ascending or descending sortOrder for the sort.
     * @param orderByCol  The name of the entity field by which the list should be sorted. If <code>orderByCol</code> does not match any existing field, a runtime exception will be thrown by the underlying implementation (JPA).
     * @param order asc or desc sort sortOrder as defined in the SortOrder enum.
     * @param page A Page object that specifies the paging parameters for the list to be loaded : number of rows to load and number of the first row to row
     * @return
     */
    List<T> list(String orderByCol, SortOrder order, Page page);

    /**
     * Loads the details side of a master-detail or parent-child relationship.
     * If you had an <code>Order</code> and an <code>Item</code> entities,
     * with a many-to-one relationship from item to sortOrder, you could call this method on the ItemDAO class like this :<br/>
     * <code>
     *  itemDAO.listDetailsForMaster("name", SortOrder.asc, page, 12, "parentOrder");
     * </code>
     * <br/>
     * Where 12 is the ID of an sortOrder and <code>"parentOrder"</code> is the name of the many-to-one field that references <code>Order</code>
     *
     * If <code>masterRelationshipName</code> does not match an existing relationship field, a runtime exception will be thrown by the underlying implementation (JPA).
     *
     * @param orderByCol  The name of the entity field by which the list should be sorted. If <code>orderByCol</code> does not match any existing field, a runtime exception will be thrown by the underlying implementation (JPA).
     * @param order asc or desc sort sortOrder as defined in the SortOrder enum.
     * @param page A Page object that specifies the paging parameters for the list to be loaded : number of rows to load and number of the first row to row
     * @param masterId ID of the master entity.
     * @param masterRelationshipName Name of the detail-to-master (many-to-one) relationship field on the detail entity.
     * @return
     */
    List<T> listDetailsForMaster(String orderByCol, SortOrder order, Page page, Long masterId, String masterRelationshipName);

    /**
     * @param fieldName The supposed name of a persistent filed belonging to the entity class managed by this DAO.
     * @return <code>true</code> if the entity class managed by this DAO has a persistent field with the given name
     */
    boolean isValidPersistentField(String fieldName);


}
