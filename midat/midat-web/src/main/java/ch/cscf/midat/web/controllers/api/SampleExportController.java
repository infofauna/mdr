package ch.cscf.midat.web.controllers.api;

import ch.cscf.jeci.domain.dto.midat.SampleSearchParameters;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.LanguageDAO;
import ch.cscf.jeci.services.general.I18nService;
import ch.cscf.jeci.utils.CSVWriter;
import ch.cscf.jeci.utils.FileUtils;
import ch.cscf.jeci.web.controllers.api.AbstractRestController;
import ch.cscf.midat.services.interfaces.SampleSearchService;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by henryp on 26/06/15.
 */
@Controller
@RequestMapping("/api/samples/export")
public class SampleExportController extends AbstractRestController {

    @Autowired
    private SampleSearchService sampleSearchService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LanguageDAO languageDAO;

    @Autowired
    private I18nService i18n;

    /**
     * Header translations table. Rows are the languages, columns the values.
     */
    private List<String> valueNames = Lists.newArrayList(
            "sample.id", "labRecordField.sampleNumber", "sample.sampleDateDay", "sample.sampleDateMonth",
            "sample.sampleDateYear", "sampleStation.stationNumber", "sampleStation.coordinates.x",
            "sampleStation.coordinates.y", "sampleStation.coordinates.z", "watercourse.value", "locality.value",
            "calledPlace.value", "sample.ibchIndexValue", "sample.spearIndexValue", "sample.makroIndexValue",
            "labRecordField.frequency", "labRecordField.modifiedFrequency", "labRecordField.stadium",
            "labRecordField.taxon.phylum", "labRecordField.taxon.class", "labRecordField.taxon.order",
            "labRecordField.taxon.family", "labRecordField.taxon.genus", "labRecordField.taxon.species",
            "protocolFieldMapping.ibchTaxon", "labRecordField.taxon.id",
            "protocolFieldMapping.ibchDeterminingGroup", "project.designation", "principalInstitution.name",
            "mandataryInstitution.name", "determinator", "operator");

    private final Table<String, String, String> headerTranslations = HashBasedTable.create();

    @PostConstruct
    public void loadTranslations(){

        String propertyPrefix = "export.csv.columns.";
        List<Locale> locales = languageDAO.findUiSelectableLanguages().stream().map(l -> new Locale(l.getCode())).collect(Collectors.toList());

        for(Locale locale : locales){
            String langKey = locale.getLanguage();

            for(String valueName : valueNames){
                String translation = messageSource.getMessage(propertyPrefix + valueName, null, valueName, locale);
                headerTranslations.put(langKey, valueName, translation);
            }
        }

    }

    //  Export dans la Recherche standard
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "text/csv;charset=windows-1252")
    public void search(@RequestBody SampleSearchParameters sampleSearchParameters, HttpServletResponse response)
    {
        List<Map<String, Object>> data = sampleSearchService.searchForExport(sampleSearchParameters);
        writeCsvToResponse(response, data);
    }

    //  Export dans la Recherche géo-localisée
    @RequestMapping(value = "/inside-rectangle", method = RequestMethod.GET)
    public void searchInsideRectangle(
            @RequestParam("west") Double west,
            @RequestParam("east") Double east,
            @RequestParam("north") Double north,
            @RequestParam("south") Double south,
            @RequestParam("editionMode") boolean editionMode,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("isFull") boolean isFull,
            HttpServletResponse response
    )
    {
        List<Map<String, Object>> data = sampleSearchService.searchWithinRectangleForExport(west, east, north, south, editionMode,startDate,endDate,isFull);
        writeCsvToResponse(response, data);
    }

    //  Export dans la Recherche géo-localisée
    @RequestMapping(value = "/station/{stationId}", method = RequestMethod.GET)
    public void searchByStationId(@PathVariable("stationId") Long stationId,
                                  @RequestParam("isFull") boolean isFull,
                                  @RequestParam("editionMode") boolean editionMode,
                                  HttpServletResponse response){

        List<Map<String, Object>> data = sampleSearchService.searchByStationIdForExport(stationId, editionMode,isFull);
        writeCsvToResponse(response, data);
    }

    private void writeCsvToResponse(HttpServletResponse response, List<Map<String, Object>> data) {
        response.setContentType("text/csv;charset=Windows-1252"); //main target is Excel, which does not handle UTF-8 very well so we force windows encoding
        response.setCharacterEncoding("Windows-1252");
        response.setHeader("Content-Disposition", "attachment; filename=\"MIDAT-data-export-" + FileUtils.getDateStamp() + ".csv\""); //make the browser save it as a file

        try {
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "Windows-1252");
            Map<String, String> headers = headerTranslations.row(i18n.currentLanguageCode());
            CSVWriter printer = new CSVWriter(';', valueNames, headers, data, writer);
            printer.writeToOutput();
            writer.flush();
            writer.close();
        }catch(IOException e){
            throw new RuntimeException("IO exception writing CSV to response output stream !");
        }
    }

}
