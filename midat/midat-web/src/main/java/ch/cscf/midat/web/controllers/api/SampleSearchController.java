package ch.cscf.midat.web.controllers.api;

import ch.cscf.jeci.domain.dto.midat.SampleSearchParameters;
import ch.cscf.jeci.domain.dto.midat.SearchResultDTO;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.web.controllers.api.AbstractRestController;
import ch.cscf.jeci.web.controllers.api.list.JqGridPageModel;
import ch.cscf.midat.services.interfaces.SampleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: henryp
 */
@Controller
@RequestMapping("/api/midat-search")
public class SampleSearchController extends AbstractRestController {

    @Autowired
    private SampleSearchService sampleSearchService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody JqGridPageModel<SearchResultDTO> search(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer rowsPerPage,
            @RequestParam(value = "orderBy", required = false, defaultValue="sampleDate") String orderBy,
            @RequestParam(value = "sortOrder", required = false, defaultValue="desc") SortOrder sortOrder,
            @RequestBody SampleSearchParameters sampleSearchParameters
    )
    {
        final Page page = new Page();
        page.setPageNumber(pageNumber);
        page.setPageSize(rowsPerPage);

        JqGridPageModel<SearchResultDTO> result = new JqGridPageModel<>(
                sampleSearchService.search(sampleSearchParameters, page, orderBy, sortOrder),
                page.getPageNumber(), page.getPageSize(), page.getTotalRows());

        return result;
    }

    @RequestMapping(value = "/inside-rectangle", method = RequestMethod.GET)
    public @ResponseBody JqGridPageModel<SearchResultDTO> searchInsideRectangle(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer rowsPerPage,
            @RequestParam(value = "orderBy", required = false, defaultValue="sampleDate") String orderBy,
            @RequestParam(value = "sortOrder", required = false, defaultValue="desc") SortOrder sortOrder,
            @RequestParam("west") Double west,
            @RequestParam("east") Double east,
            @RequestParam("north") Double north,
            @RequestParam("south") Double south,
            @RequestParam("editionMode") boolean editionMode,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate

    )
    {

        final Page page = new Page();
        page.setPageNumber(pageNumber);
        page.setPageSize(rowsPerPage);

        List<SearchResultDTO> searchResultDTOs = sampleSearchService.searchWithinRectangle(west, east, north, south, page, orderBy, sortOrder, editionMode,startDate,endDate);
        JqGridPageModel<SearchResultDTO> result = new JqGridPageModel<>(
                searchResultDTOs,
                page.getPageNumber(), page.getPageSize(), page.getTotalRows());

        return result;
    }

    @RequestMapping(value = "/station/{stationId}", method = RequestMethod.GET)
    public @ResponseBody JqGridPageModel<SearchResultDTO> searchByStationId(
            @PathVariable("stationId") Long stationId,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer rowsPerPage,
            @RequestParam(value = "orderBy", required = false, defaultValue="sampleDate") String orderBy,
            @RequestParam(value = "sortOrder", required = false, defaultValue="desc") SortOrder sortOrder,
            @RequestParam("editionMode") boolean editionMode
        )
    {

        final Page page = new Page();
        page.setPageNumber(pageNumber);
        page.setPageSize(rowsPerPage);

        JqGridPageModel<SearchResultDTO> result = new JqGridPageModel<>(
                sampleSearchService.searchByStationId(stationId, page, orderBy, sortOrder,editionMode),
                page.getPageNumber(), page.getPageSize(), page.getTotalRows());

        return result;
    }
}
