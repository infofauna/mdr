package ch.cscf.midat.web.controllers.api;

import ch.cscf.jeci.domain.entities.midat.MIDATProtocolVersion;
import ch.cscf.jeci.web.controllers.api.SelectOptionsJSONController;
import ch.cscf.midat.services.interfaces.ProtocolImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by henryp on 04/12/15.
 */
@Controller
public class ProtocolVersionsController {

    @Autowired
    private ProtocolImportService service;


    @RequestMapping("/api/options/protocol-type/{protocolTypeId}/protocol-versions")
    public @ResponseBody
    List<SelectOptionsJSONController.Option> getProtocolVersionsForProtocolType(@PathVariable Long protocolTypeId){

        Collection<MIDATProtocolVersion> versions = service.findVersionsForProtocolType(protocolTypeId);

        List<SelectOptionsJSONController.Option> options = new ArrayList<>();
        for(MIDATProtocolVersion version: versions){
            SelectOptionsJSONController.Option option = new SelectOptionsJSONController.Option(version.getLocalizedDescription(), version.getId().toString());
            option.setLabel(version.getLocalizedDescription());
            option.setValue(version.getId().toString());
            options.add(option);
        }
        return options;
    }
}
