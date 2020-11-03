package ch.cscf.jeci.web.controllers.api.list;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;
import ch.cscf.jeci.services.general.EntityFieldTranslatorService;
import ch.cscf.jeci.web.controllers.api.AbstractRestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: henryp
 */
public abstract class AbstractListController<E extends BaseEntity> extends AbstractRestController {

    protected GenericEntityDAO<E> mainDAO;

    @Autowired
    protected EntityFieldTranslatorService entityFieldTranslatorService;

    public AbstractListController(){}

    protected AbstractListController(GenericEntityDAO<E> mainDAO) {
        this.mainDAO = mainDAO;
    }

    protected JqGridPageModel<E> list(Integer pageNumber, Integer numberOfRows, String orderBy, SortOrder sortOrder)
    {

        final Page page = new Page();
        page.setPageNumber(pageNumber);
        page.setPageSize(numberOfRows);

        final List<E> rows = mainDAO.list(orderBy, sortOrder, page);

        //fill the thesaurus localized values
        for(E entity : rows){
            entityFieldTranslatorService.fillLocalizedFields(entity);
        }

        JqGridPageModel result = new JqGridPageModel(rows, pageNumber, page.getPageSize(), page.getTotalRows());

        return result;
    }



}
