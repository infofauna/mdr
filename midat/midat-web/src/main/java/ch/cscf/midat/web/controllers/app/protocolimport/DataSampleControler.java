package ch.cscf.midat.web.controllers.app.protocolimport;

import ch.cscf.jeci.web.controllers.AbstractController;
import ch.cscf.midat.services.interfaces.ProtocolImportService;
import ch.cscf.midat.services.interfaces.ProtocolVersionCachedRepository;
import ch.cscf.midat.services.interfaces.SampleReadUpdateDeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: henryp
 */
@Controller()
@RequestMapping("/app/samples")
public class DataSampleControler extends AbstractController{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProtocolImportService service;

    @Autowired
    private SampleReadUpdateDeleteService sampleService;

    @Autowired
    private ProtocolVersionCachedRepository protocolVersionDAO;

    /**
     *
     * @param protocolHeaderId
     * @param mvcModel
     * @return
     */
    @RequestMapping(value= "{protocolHeaderId:[0-9]+}/protocol-type/{protocolVersionId:[0-9]+}", method= RequestMethod.DELETE)
    public String deleteAdditionalData(@PathVariable Long protocolHeaderId, @PathVariable Long protocolVersionId, Model mvcModel){

        logger.info("Request to delete protocol entry with version id {} for protocol header id {}", protocolVersionId, protocolHeaderId);

        sampleService.deleteProtocol(protocolHeaderId, protocolVersionId);

        if(protocolVersionDAO.isOfType(protocolVersionId, "LABORATORY")){
            addSuccessMessage("import.protocol.additional.importedProtocols.deleteHeader.success", true);
            return "redirect:/app/home";
        }else{
            addSuccessMessage("import.protocol.additional.importedProtocols.deleteEntry.success", true);
            return "redirect:/app/import/lab/"+protocolHeaderId+"/additional";
        }
    }
}
