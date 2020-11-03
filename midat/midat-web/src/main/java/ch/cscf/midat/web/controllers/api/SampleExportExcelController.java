package ch.cscf.midat.web.controllers.api;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.dto.midat.SampleSearchParameters;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.LanguageDAO;
import ch.cscf.jeci.persistence.daos.thesaurus.interfaces.ThesaurusReadOnlyService;
import ch.cscf.jeci.services.general.I18nService;
import ch.cscf.jeci.services.security.interfaces.SessionUserService;
import ch.cscf.jeci.utils.ExcelWriter;
import ch.cscf.jeci.utils.FileUtils;
import ch.cscf.jeci.web.controllers.api.AbstractRestController;
import ch.cscf.midat.services.interfaces.SampleSearchService;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by henryp on 26/06/15.
 */
@Controller
@RequestMapping("/api/samples/export-excel")
public class SampleExportExcelController extends AbstractRestController {

    @Autowired
    private SampleSearchService sampleSearchService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LanguageDAO languageDAO;

    @Autowired
    private I18nService i18n;

    @Autowired
    private ThesaurusReadOnlyService thesaurus;


    @Autowired
    private SessionUserService sessionUserService;

    private String pageCode ="";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * restricted headers for admin's profile only
     */
    private static final List<String> ADMIN_RESTRICTED_HEADERS =
            Lists.newArrayList( "mandataryInstitution.name","sample.published","sample.groupes","sample.sampleDate");


    /**
     * restricted headers for user's profile only
     */
    private static final List<String> USER_RESTRICTED_HEADERS = Lists.newArrayList("sample.sampleDate");

    private List<String> FAUNA_HEADER_NAMES_FILTERED =Lists.newArrayList();
    private List<String> GRID_HEADER_NAMES_FILTERED  =Lists.newArrayList() ;


    /**
     * Header translations For Fauna export sheet - it should be identical to the expressionsForExport
     */
    private static final  List<String> FAUNA_HEADER_NAMES = Lists.newArrayList(
             "sample.id",
                       "labRecordField.sampleNumber",
                       "sample.sampleDateDay",
                       "sample.sampleDateMonth",
                       "sample.sampleDateYear",
                       "sampleStation.stationNumber",
                       "sampleStation.stationStations",
                       "sampleStation.stationPrincipale",
                       "watercourse.value",
                       "locality.value",
                       "sampleStation.stationCanton",
                       "sampleStation.coordinates.x",
                       "sampleStation.coordinates.y",
                       "sampleStation.coordinates.z",
                       "sampleStation.stationGewissNumber",


                        "labRecordField.taxon.phylum",
                        "labRecordField.taxon.class",
                        "labRecordField.taxon.order",
                        "labRecordField.taxon.family",
                        "labRecordField.taxon.genus",
                        "labRecordField.taxon.species",
                        "labRecordField.stadium",
                        "labRecordField.comment",

                        "protocolFieldMapping.ibchTaxon",
                        "labRecordField.taxon.id",
                        "labRecordField.frequency",

                        "protocolFieldMapping.ibchDeterminingGroup",

                        //"calledPlace.value",
                        //"labRecordField.modifiedFrequency",
                        "sample.taxon.no.ibch",

                        "sample.ibchIndexValue",
                        "sample.ibchIndexRatingLabel",
                        "sample.spearIndexValue",
                        "sample.spearIndexRatingLabel",
                        "sample.makroIndexValue",
                        "sample.makroIndexRatingLabel",

                        "project.designation",
                        "principalInstitution.name",
                        "mandataryInstitution.name",
                        "operator",
                        "determinator"
            );



     /**
     * Header translations For Grid export sheet
     */
     private static final List<String> GRID_HEADER_NAMES = Lists.newArrayList(
              "sample.id",
             "sample.sampleProtocolType.code",
                        "labRecordField.sampleNumber",
                        "sample.sampleDateDay",
                        "sample.sampleDateMonth",
                        "sample.sampleDateYear",
                        "sampleStation.stationNumber",
                        "sampleStation.stationStations",
                        "sampleStation.stationPrincipale",
                        "watercourse.value",
                        "locality.value",
                        "sampleStation.stationCanton",
                        "sampleStation.coordinates.x",
                        "sampleStation.coordinates.y",
                        "sampleStation.coordinates.z",
                        "sampleStation.stationGewissNumber",
                        "sample.ibchIndexValue",
                        "sample.ibchIndexRatingLabel",
                        "sample.spearIndexValue",
                        "sample.spearIndexRatingLabel",
                        "sample.makroIndexValue",
                        "sample.makroIndexRatingLabel",
                        "sample.ephemeroptera",
                        "sample.plecoptera",
                        "sample.trichoptera",
                        "project.designation",
                        "principalInstitution.name",
                        "mandataryInstitution.name",
                        "sample.published",
                        "sample.groupes",
                        "sample.sampleDate",
                        "sample.evalgrid.header",
                        "sample.groundproto.header"
              );


    private final Table<String, String, String> translations = HashBasedTable.create();

    @PostConstruct
    public void loadTranslations(){



        String columnsPrefix = "export.xlsx.columns.";

        List<Locale> locales = languageDAO.findUiSelectableLanguages().stream().map(l -> new Locale(l.getCode())).collect(Collectors.toList());

        for(Locale locale : locales){
            String langKey = locale.getLanguage();

            for(String valueName : FAUNA_HEADER_NAMES){
                String translation = messageSource.getMessage(columnsPrefix + valueName, null, valueName, locale);
                translations.put(langKey, valueName, translation);
            }

            for(String valueName : GRID_HEADER_NAMES){
                String translation = messageSource.getMessage(columnsPrefix + valueName, null, valueName, locale);
               translations.put(langKey, valueName, translation);
            }
            //rating translations
            String translationRating = messageSource.getMessage("search.details.indexratings.popover.rating", null, locale);
            String translationRatinIBCHgIndex = messageSource.getMessage("export.xlsx.columns.sample.ibchIndexValue", null, locale);
            String translationRatinMAKROIndex = messageSource.getMessage("export.xlsx.columns.sample.makroIndexValue", null, locale);
            String translationRatinSPEARgIndex = messageSource.getMessage("export.xlsx.columns.sample.spearIndexValue", null, locale);

            translations.put(langKey, "sample.ibchIndexRatingLabel",  translationRating +"("+translationRatinIBCHgIndex+")");
            translations.put(langKey, "sample.makroIndexRatingLabel", translationRating +"("+translationRatinMAKROIndex+")");
            translations.put(langKey, "sample.spearIndexRatingLabel", translationRating +"("+translationRatinSPEARgIndex+")");

            //published & notPublished translations
            String translationPublishedValue = messageSource.getMessage("export.xlsx.columns.published.value", null, locale);
            String translationNotPublishedValue = messageSource.getMessage("export.xlsx.columns.notPublished.value", null, locale);

            translations.put(langKey, "published.value",  translationPublishedValue);
            translations.put(langKey, "notPublished.value", translationNotPublishedValue);

        }

    }

    private void filterExportedHeadersByProfile() {

        FAUNA_HEADER_NAMES_FILTERED =Lists.newArrayList();
        GRID_HEADER_NAMES_FILTERED  =Lists.newArrayList() ;

        if (SecurityUtils.getSubject().isPermitted("midat:national")) {

            if(USER_RESTRICTED_HEADERS.size() >0){
                //case: admin export filter out  users's headers  - exclude the header if the header presnts in the ADMIN_RESTRICTED_HEADERS List
                 FAUNA_HEADER_NAMES.stream()
                        .forEach(header->{
                            String h =  USER_RESTRICTED_HEADERS.stream().filter(rHeader->header.equalsIgnoreCase(rHeader))
                                    .findAny()
                                    .orElse(null);
                            if(h == null) FAUNA_HEADER_NAMES_FILTERED.add(header);
                        });

                GRID_HEADER_NAMES.stream()
                        .forEach(header->{
                            String h =  USER_RESTRICTED_HEADERS.stream().filter(rHeader->header.equalsIgnoreCase(rHeader))
                                    .findAny()
                                    .orElse(null);
                            if(h == null) GRID_HEADER_NAMES_FILTERED.add(header);
                        });
            }else{
                //when there is no restriction
                FAUNA_HEADER_NAMES_FILTERED = FAUNA_HEADER_NAMES;
                GRID_HEADER_NAMES_FILTERED  = GRID_HEADER_NAMES;
            }
        }else{

            if(ADMIN_RESTRICTED_HEADERS.size()>0){
                //case: user export filter out  admin's headers  - exclude the header if the header presnts in the ADMIN_RESTRICTED_HEADERS List
                FAUNA_HEADER_NAMES.stream()
                        .forEach(header->{
                            String h =  ADMIN_RESTRICTED_HEADERS.stream().filter(rHeader->header.equalsIgnoreCase(rHeader))
                                    .findAny()
                                    .orElse(null);

                            if(h == null) FAUNA_HEADER_NAMES_FILTERED.add(header);
                        });

                GRID_HEADER_NAMES.stream()
                        .forEach(header->{
                            String h =  ADMIN_RESTRICTED_HEADERS.stream().filter(rHeader->header.equalsIgnoreCase(rHeader))
                                    .findAny()
                                    .orElse(null);
                            if(h == null) GRID_HEADER_NAMES_FILTERED.add(header);
                        });
            }else{
                //when there is no restriction
                FAUNA_HEADER_NAMES_FILTERED = FAUNA_HEADER_NAMES;
                GRID_HEADER_NAMES_FILTERED  = GRID_HEADER_NAMES;
            }
        }
    }

    //  Export dans la Recherche standard
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void search(@RequestBody SampleSearchParameters sampleSearchParameters,@RequestParam("pageCode") String pageCode,  @RequestParam("isFull") boolean isFull,HttpServletResponse response) {
        this.pageCode = pageCode;
        boolean noexport =  notAllowedToExportData("Export dans la Recherche standard");

        if(noexport){
            throw new IllegalArgumentException("Export is not allowed for this user ...");
        }else{
        List<Map<String, Object>> data = sampleSearchService.searchForExport(sampleSearchParameters);
        writeCsvToResponse(response, data,isFull);
        }
    }

    //  Export dans la Recherche géo-localisée
    @RequestMapping(value = "/inside-rectangle", method = RequestMethod.GET)
    public void searchInsideRectangle(
            @RequestParam("west") Double west,
            @RequestParam("east") Double east,
            @RequestParam("north") Double north,
            @RequestParam("south") Double south,
            @RequestParam("editionMode") boolean editionMode,
            @RequestParam("pageCode") String pageCode,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("isFull") boolean isFull,
            HttpServletResponse response
    )
    {
        this.pageCode = pageCode;
        boolean noexport = notAllowedToExportData("Export dans la Recherche géo-localisée");
        if(noexport){
            throw new IllegalArgumentException("Export is not allowed for this user ...");
        }else{
            List<Map<String, Object>> data = sampleSearchService.searchWithinRectangleForExport(west, east, north, south, editionMode,startDate,endDate,isFull);
            writeCsvToResponse(response, data,isFull);
        }

    }

    //  Export dans la Recherche géo-localisée
    @RequestMapping(value = "/station/{stationId}", method = RequestMethod.GET)
    public void searchByStationId(@PathVariable("stationId") Long stationId,@RequestParam("pageCode") String pageCode,@RequestParam("editionMode") boolean editionMode,
                                  @RequestParam("isFull") boolean isFull,HttpServletResponse response){
        this.pageCode = pageCode;
        boolean noexport = notAllowedToExportData("Export dans la Recherche géo-localisée");
        if(noexport){
            throw new IllegalArgumentException("Export is not allowed for this user ...");
        }else{
        List<Map<String, Object>> data = sampleSearchService.searchByStationIdForExport(stationId,editionMode,isFull);
        writeCsvToResponse(response, data,isFull);
        }
    }

    private void writeCsvToResponse(HttpServletResponse response, List<Map<String, Object>> data, boolean isFull) {

        filterExportedHeadersByProfile();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"MIDAT-data-export-" + FileUtils.getDateStamp() + ".xlsx\""); //make the browser save it as a file
        try {
            Map<String, String> excelTranslations = translations.row(i18n.currentLanguageCode());

            for(Map<String,Object> row : data){

                Object ibchIndexRatingCode  = row.get("sample.ibchIndexRatingCode");
                String ibchIndexRatingLabel ="";
                if(ibchIndexRatingCode != null ){
                    ibchIndexRatingLabel = thesaurus.getLocalizedString(ThesaurusCodes.REALM_BIOLSTATETXT, String.valueOf(ibchIndexRatingCode), i18n.currentLanguageCode());
                }
                row.put("sample.ibchIndexRatingLabel",ibchIndexRatingLabel);

                Object  makroIndexRatingCode  = row.get("sample.makroIndexRatingCode");
                String  makroIndexRatingLabel = "";
                if(makroIndexRatingCode !=null) {
                    makroIndexRatingLabel = thesaurus.getLocalizedString(ThesaurusCodes.REALM_BIOLSTATETXT, String.valueOf(makroIndexRatingCode), i18n.currentLanguageCode());
                }
                row.put("sample.makroIndexRatingLabel",makroIndexRatingLabel);

                Object  spearIndexRatingCode = row.get("sample.spearIndexRatingCode");
                String  spearIndexRatingLabel ="";
                if(spearIndexRatingCode!=null){
                    spearIndexRatingLabel =thesaurus.getLocalizedString(ThesaurusCodes.REALM_BIOLSTATETXT, String.valueOf(spearIndexRatingCode), i18n.currentLanguageCode());
                }
                row.put("sample.spearIndexRatingLabel",spearIndexRatingLabel);

                //update the publish not publish values
                Object  published  = row.get("sample.published");
                String  publishedValue = "";
                if(published !=null) {
                    publishedValue = String.valueOf(published).equalsIgnoreCase("false")?excelTranslations.get("notPublished.value"):excelTranslations.get("published.value");
                }
                row.put("sample.published",publishedValue);

                //staion cantion
                Object thesaurusCanton  = row.get("sampleStation.stationCanton");
                if(thesaurusCanton !=null) {
                    ThesaurusValue thesaurusValue =(ThesaurusValue)thesaurusCanton;
                    String cantonDes = thesaurus.getLocalizedString(ThesaurusCodes.REALM_CANTON, thesaurusValue.getCode(), i18n.currentLanguageCode());
                    row.put("sampleStation.stationCanton",cantonDes);
                }



            }

            OutputStream out = response.getOutputStream();
            ExcelWriter printer = new ExcelWriter(GRID_HEADER_NAMES_FILTERED,FAUNA_HEADER_NAMES_FILTERED,excelTranslations,pageCode, data,isFull, out);
            printer.writeToOutput();
            OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
            writer.flush();
            writer.close();
        }catch(IOException e){
            throw new RuntimeException("IO exception writing XLSX to response output stream !");
        }
    }

    private boolean notAllowedToExportData(String msg){
        logger.info("notAllowedToExportData -- method :[" + msg + "]");
        Set<String> sessionUserRolesNames = sessionUserService.getSessionUserRolesNamesForMidat();
        String noexport = sessionUserRolesNames.stream().filter(role -> {
            logger.info("notAllowedToExportData -- role :[" + role + "]");
            return role.equalsIgnoreCase("NOEXPORT");
        }).findAny().orElse(null);

        return noexport != null;
    }

}
