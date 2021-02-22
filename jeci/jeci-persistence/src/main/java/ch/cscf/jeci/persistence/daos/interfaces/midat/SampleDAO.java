package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.dto.midat.SampleSearchParameters;
import ch.cscf.jeci.domain.dto.midat.SearchResultDTO;
import ch.cscf.jeci.domain.entities.midat.ListConnection;
import ch.cscf.jeci.domain.entities.midat.ProtocolIndex;
import ch.cscf.jeci.domain.entities.midat.sample.*;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;
import java.util.Map;

/**
 * @author: henryp
 */
public interface SampleDAO extends GenericEntityDAO<Sample> {


    List<SearchResultDTO> search(SampleSearchParameters searchParameters, String orderBy, SortOrder sortOrder, Page page);


    List<SearchResultDTO> searchByStations(List<Long> stationIds, Long userId, String orderBy, SortOrder sortOrder,
                                           Page page,boolean isAppManager,boolean isNationalContributor, boolean editionMode);

    List<Map<String, Object>> searchForExport(SampleSearchParameters parameters);

    List<Map<String, Object>> searchByStationsForExport(List<Long> stationIds, Long userId,boolean isAppManager,boolean isNationalContributor, boolean editionMode);

    void batchUpdateField(List<Long> sampleIds, String fieldName, Object value);

    Sample loadSampleForDetailsView(Long sampleId);
    Sample loadSampleByIphId(Long sampleIphId);

    List<Long> searchSamplesStations(SampleSearchParameters searchParameters);
    SampleImportDisplayLog getImportLogKey(Long sampleId, Long languageId);
    List<SampleImportDisplayLog> loadSampleImportLog(Long sampleId, Long languageId);
    List<SampleStatPerProtocolMonth> getSampleProtocolPerMonth();

    List<SampleProtocolPerUserCanton> getSampleProtocolPerUserCanton();
    List<ListConnection> getUsersConnectionHistory();
    List<ProtocolIndex> getSampleIndexHistory();
    SampleInfoIbchData loadSampleInfoIbchData(Long sampleId);

    SampleIndiceHistory loadSampleIndiceHistoryData(Long sampleId);
}
