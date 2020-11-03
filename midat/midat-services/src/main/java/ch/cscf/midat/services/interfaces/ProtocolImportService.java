package ch.cscf.midat.services.interfaces;

import ch.cscf.jeci.domain.dto.midat.LabProtocolFormValues;
import ch.cscf.jeci.domain.dto.errors.Message;
import ch.cscf.jeci.domain.entities.midat.MIDATProtocolVersion;
import ch.cscf.jeci.domain.entities.midat.ProtocolImportHeader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: henryp
 */
public interface ProtocolImportService {

    ProtocolImportHeader buildNewImportHeader();

    ProtocolImportHeader buildNewImportHeaderBasedOn(Long existingProtocolImportHeaderId);

    LabProtocolFormValues getLabFormValues();

    LabProtocolFormValues getMassFormValues();

    ProtocolImportHeader importProtocolDataAndExcelFile(ProtocolImportHeader protocolImportHeader);

    List<Message> executeValidateProtocolProcedure(ProtocolImportHeader protocolImportHeader);

    Long asyncImportMassDataAndExcelFile(ProtocolImportHeader protocolImportHeader);

    ProtocolImportHeader getHeaderForTask(Long taskId);

    List<Message> getMassImportTaskResult(Long taskId);

    Long confirmImportProtocol(Long importProtocolHeaderId, boolean publish, boolean makePublic);

    ProtocolImportHeader buildProtocolImportForAdditionalDataBasedOnParent(Long labProtocolId);

    Map<String, Object> getParentInfo(Long id);

    Map<String, Collection<?>> getAdditionalDataFormValues();

    @Transactional(readOnly = true)
    Collection<MIDATProtocolVersion> findVersionsForProtocolType(Long protocolTypeId);

    @Transactional
    void addCommentsToSample(Long sampleId, Set<Long> commentIds,Boolean commentOtherChk,String commentOther );


}
