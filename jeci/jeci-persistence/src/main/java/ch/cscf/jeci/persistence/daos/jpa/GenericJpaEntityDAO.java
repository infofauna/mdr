package ch.cscf.jeci.persistence.daos.jpa;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;
import ch.cscf.jeci.persistence.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic JPA DAO class, designed to work with the AbstractBaseEntity class as superclass for all entities.
 * It implements most commonly needed methods such as findById, persist, delete etc.
 * including some advanced methods for loading lists of entities with options such as prefetched relationships, sort column etc.
 */
public abstract class GenericJpaEntityDAO<T extends BaseEntity> implements GenericEntityDAO<T> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static final String ROOT_ALIAS="rootEntity";

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbc;


    protected JdbcTemplate getClassicJdbc(){
        return jdbc;
    }

    protected NamedParameterJdbcTemplate getJdbc(){
        return namedJdbc;
    }

    /**
     * Shared, thread-safe proxy for the actual transactional EntityManager, managed by Spring
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Access to the entity manager, used by subclasses only, hence protected.
     * @return
     */
    protected EntityManager getEm(){
        return em;
    }

    /**
     * The actual type of the entity accessed by this DAO implementation.
     */
    protected Class<T> persistentClass;

    /**
     * Default constructor.
     * Its main job is to obtain the actual class of the entities it will handle.
     */
    @SuppressWarnings("unchecked")
    public GenericJpaEntityDAO(){
        try{
            this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }catch(ClassCastException e){
            //this might be due to the fact that the object is actually a CGLIB subclass, in that case we have to do an extra superclass call:
            this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getSuperclass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }

        logger.info("Instantiating Generic JPA DAO for entity class [{}]", this.persistentClass);

    }

    /**
     * Obtains an entity by id.
     * @param id
     * @return
     */
    @Override
    public T findById(Long id, String... relationshipsToFetch) {
        logger.debug("Retrieving entity of class [{}]", this.persistentClass);


        CriteriaBuilder builder = getEm().getCriteriaBuilder();
        CriteriaQuery criteria =  builder.createQuery(persistentClass);
        Root<T> root = criteria.from(persistentClass);
        root.alias(ROOT_ALIAS);

        for(String relationshipName : relationshipsToFetch){
            try {
                root.fetch(relationshipName, JoinType.LEFT);
            }catch(IllegalArgumentException e){
                logger.error("The relationship name "+relationshipName+" is probably not a valid relationship name for entity class "+persistentClass.getName(), e);
            }
        }

        //where id={:id}
        criteria.where(builder.equal(root.get("id"), id));

        //Create the query based on the criteria query
        TypedQuery<T> query = getEm().createQuery(criteria);

        //execute query
        List<T> resultList = query.getResultList();
        if(resultList.isEmpty()){
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public T findByIdDetached(Long id) {
        T e = findById(id);
        getEm().detach(e);
        return e;
    }

    /**
     * Persists an entity. Used to make new entities persistent.
     * @param entity
     */
    @Override
    public void persist(T entity){
        logger.debug("Saving entity of class [{}]", this.persistentClass);
        em.persist(entity);
    }

    @Override
    public T merge(T entity){
        logger.debug("Merging entity of class [{}]", this.persistentClass);
        return em.merge(entity);
    }

    /**
     * Refreshes an entity.
     * @param entity
     */
    @Override
    public void refresh(T entity){
        em.refresh(entity);
    }

    /**
     * Deletes an entity. The entity will no longer be persistent and the corresponding row will be deleted from the database table.
     * @param entity
     */
    @Override
    public void delete(T entity){
        logger.debug("Deleting entity of class [{}]", this.persistentClass);
        em.remove(entity);
    }

    /**
     * @see GenericEntityDAO#list()
     * @return
     */
    @Override
    public List<T> list(){
        return listImpl(null, SortOrder.DEFAULT, null, null, null);
    }

    /**
     * @see GenericEntityDAO#list(String)
     * @return
     */
    @Override
    public List<T> list(String orderByCol) {
        return listImpl(orderByCol, SortOrder.DEFAULT, null, null, null);
    }

    /**
     * @see GenericEntityDAO#list(String)
     * @return
     */
    @Override
    public List<T> list(String orderByCol, List<String> relationshipsToFetch) {
        return listImpl(orderByCol, SortOrder.DEFAULT, null, null, relationshipsToFetch);
    }

    /**
     * @see GenericEntityDAO#list(String, Page)
     * @return
     */
    @Override
    public List<T> list(String orderByCol, SortOrder sortOrder) {
        return listImpl(orderByCol, sortOrder, null, null, null);
    }


    /**
     * @see GenericEntityDAO#list(String, Page)
     * @return
     */
    @Override
    public List<T> list(String orderByCol, Page page) {
        return listImpl(orderByCol, SortOrder.DEFAULT, page, null, null);
    }

    /**
     * @see GenericEntityDAO#list(String, SortOrder, Page)
     * @return
     */
    @Override
    public List<T> list(String orderByCol, SortOrder sortOrder, Page page) {
        return listImpl(orderByCol, sortOrder, page, null, null);
    }


    /**
     * This is the generic method to list entities, which is called by all the other list methods specified on the interface.
     * It allows powerful customization of the request :
     * @param orderByCol The column used for sorting. Can be null.
     * @param sortOrder asc or desc sort sortOrder as defined in the SortOrder enum. Can be null. If null, the <code>SortOrder.DEFAULT</code> will be used.
     * @param page a Page object specifying the first and last row to be returned, and used to pass back the total number of rows available. It can be null, in which case no paging restriction is applied.
     * @param restrictions A list of further (JPA) restrictions. Can be null. Should be used by concrete subclasses that need to implement more refined criteria but still want to benefit from the paging and sorting functionalities offered by this generic implementation.
     * @return A list of entities of the class managed by this DAS, matching the
     */
    protected List<T> listImpl(String orderByCol, SortOrder sortOrder, Page page, List<Predicate> restrictions, List<String> relationshipsToFetch) {

        logger.debug("Retrieving a collection of entities of class [{}]", this.persistentClass);

        if(restrictions == null){
            restrictions = new ArrayList<>();
        }

        //Create criteria query
        CriteriaBuilder builder = getEm().getCriteriaBuilder();
        CriteriaQuery criteria =  builder.createQuery(persistentClass);
        Root<T> root = criteria.from(persistentClass);
        root.alias(ROOT_ALIAS);

        if(relationshipsToFetch != null && !relationshipsToFetch.isEmpty()){
            for(String relationshipName : relationshipsToFetch){
                try {
                    root.fetch(relationshipName, JoinType.LEFT);
                }catch(IllegalArgumentException e){
                    logger.error("The relationship name "+relationshipName+" is probably not a valid relationship name for entity class "+persistentClass.getName(), e);
                }

            }
        }

        //build a AND predicate including all restrictions defined so far
        Predicate where = builder.and(restrictions.toArray(new Predicate[restrictions.size()]));
        criteria.where(where);

        //if a Page object is provided, retrieve the total count() of the query
        if(page != null){

            logger.debug("Paging active : {}", page);

            CriteriaQuery<Long> count = builder.createQuery(Long.class);
            Root<T> rootCount = count.from(persistentClass);
            rootCount.alias(ROOT_ALIAS);
            count.select(builder.count(rootCount));
            getEm().createQuery(count);
            count.where(where);
            TypedQuery<Long> q = getEm().createQuery(count);
            Long totalCount = q.getSingleResult();

            logger.debug("Found {} entities matching our criteria.", totalCount);

            if(totalCount > Integer.MAX_VALUE){ //should not really happen in this app but we never know...
                logger.warn("The row count value of {} is bigger than the maximum integer value. Overflow will happen and the pagin will not work properly !!", totalCount);
            }

            page.setTotalRows((int)totalCount.longValue());
        }

        //If sortOrder by column if provided, check that it is a valid one and apply sortOrder by clause
        if(orderByCol!=null){
            if(sortOrder == null){
                sortOrder = SortOrder.DEFAULT;
            }
            if(isValidPersistentField(orderByCol)){
                if(sortOrder==SortOrder.desc){
                    criteria.orderBy(builder.desc(root.get(orderByCol)));
                }else{
                    criteria.orderBy(builder.asc(root.get(orderByCol)));
                }
            }else{
                throw new IllegalArgumentException(MessageFormat.format("''{0}'' is not a valid persistent field to 'sortOrder by' the class ''{1}''.", orderByCol, persistentClass.getName()));
            }
        }

        //Create the query based on the criteria query
        TypedQuery<T> query = getEm().createQuery(criteria);

        // apply paging restrictions
        if(page != null){
            logger.debug("Paging : fetching rows {} to {}", page.getFirstRow(), page.getLastRow());
            query.setFirstResult(page.getFirstRow().intValue());
            query.setMaxResults(page.getPageSize().intValue());
        }

        //execute query
        return query.getResultList();
    }

    /**
     * @see GenericEntityDAO#listDetailsForMaster(String, ch.cscf.jeci.persistence.SortOrder, ch.cscf.jeci.persistence.daos.Page, Long, String)
     */
    @Override
    public List<T> listDetailsForMaster(String orderByCol, SortOrder sortOrder, Page page, Long masterId, String masterRelationshipName) {

        if(!isValidPersistentField(masterRelationshipName)){
            throw new IllegalArgumentException(MessageFormat.format("''{0}'' is not a valid persistent field to of the class ''{1}''.", masterRelationshipName, persistentClass.getName()));
        }

        CriteriaBuilder builder = getEm().getCriteriaBuilder();
        Root<T> root = builder.createQuery(persistentClass).from(persistentClass);
        root.alias(ROOT_ALIAS);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get(masterRelationshipName), masterId));

        return listImpl(orderByCol, sortOrder, page, predicates, null);
    }

    /**
     * See GenericEntityDAO#isValidPersistentField(String)
     */
    @Override
    public boolean isValidPersistentField(String fieldName){

        Field field = ReflectionUtil.getDeclaredFieldFromClassAndSuperClasses(persistentClass, fieldName);
        if(field == null){
            return false;
        }

        if(java.lang.reflect.Modifier.isStatic(field.getModifiers()) ||
                java.lang.reflect.Modifier.isFinal(field.getModifiers()) ||
                field.getAnnotation(Transient.class) != null){
            return false;
        }
        return true;
    }

    protected String prepareStringForLikeSearch(String query){
        String result = "%"+query.trim().toLowerCase()+"%";
        return result;
    }

}

