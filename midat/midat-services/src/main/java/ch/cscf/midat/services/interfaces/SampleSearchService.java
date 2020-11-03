package ch.cscf.midat.services.interfaces;

import ch.cscf.jeci.domain.dto.midat.SampleSearchParameters;
import ch.cscf.jeci.domain.dto.midat.SearchResultDTO;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;

import java.util.List;
import java.util.Map;

/**
 * @author: henryp
 */
public interface SampleSearchService {


    List<SearchResultDTO> search(SampleSearchParameters parameters, Page page, String orderBy, SortOrder sortOrder);

    List<Map<String, Object>> searchForExport(SampleSearchParameters parameters);

    List<SearchResultDTO> searchWithinRectangle(Double west, Double east, Double north, Double south, Page page, String orderBy, SortOrder sortOrder, boolean editionMode,String startDate,String endDate);

    List<Map<String, Object>> searchWithinRectangleForExport(Double west, Double east, Double north, Double south, boolean editionMode,String startDate,String endDate,boolean isFull);

    List<SearchResultDTO> searchByStationId(Long stationId, Page page, String orderBy, SortOrder sortOrder, boolean editionMode);

    List<Map<String, Object>> searchByStationIdForExport(Long stationId, boolean editionMode,boolean isFull);
}
