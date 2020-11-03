package ch.cscf.midat.services.interfaces;

import ch.cscf.jeci.domain.dto.jeci.AuditingInfo;
import ch.cscf.jeci.domain.dto.midat.SampleDetailDTO;
import ch.cscf.jeci.domain.entities.midat.ListConnection;
import ch.cscf.jeci.domain.entities.midat.ProtocolIndex;
import ch.cscf.jeci.domain.entities.midat.sample.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author: henryp
 */
public interface SampleReadUpdateDeleteService {

    SampleDetailDTO getSampleDetails(Long sampleId);

    void deleteProtocol(Long sampleHeaderId, Long protocolVersionId);

    void deleteSample(Long sampeHeaderId);

    void batchUpdateField(List<Long> sampleIds, String fieldName, Object value);

    void changeSampledPublishedStatus(List<Long> sampleIds, boolean published);

    void makeSamplesPublic(List<Long> sampleIds, boolean pub);

    void deleteSamples(List<Long> sampleIds);

    AuditingInfo getAuditInfoForSample(Long sampleId);

    List<SampleImportDisplayLog> loadSampleImportLog(Long sampleId);

    List<SampleStatPerProtocolMonth> getSampleProtocolPerMonth();
    List<SampleProtocolPerUserCanton> getSampleProtocolPerUserCanton();

    List<ListConnection> getUsersConnectionHistory();

    List<ProtocolIndex> getSampleIndexHistory();

    SampleInfoIbchData loadSampleInfoIbchData(Long sampleId);

    SampleIndiceHistory loadSampleIndiceHistoryData(Long sampleId);
}
