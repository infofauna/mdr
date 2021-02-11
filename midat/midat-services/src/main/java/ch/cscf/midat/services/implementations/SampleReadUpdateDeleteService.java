package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.dto.jeci.AuditingInfo;
import ch.cscf.jeci.domain.dto.midat.*;
import ch.cscf.jeci.domain.entities.base.Institution;
import ch.cscf.jeci.domain.entities.midat.IndexAvaliabilityCalendar;
import ch.cscf.jeci.domain.entities.midat.LabRecordField;
import ch.cscf.jeci.domain.entities.midat.ListConnection;
import ch.cscf.jeci.domain.entities.midat.ProtocolIndex;
import ch.cscf.jeci.domain.entities.midat.sample.*;
import ch.cscf.jeci.domain.entities.security.Group;
import ch.cscf.jeci.domain.entities.thesaurus.LocalizedThesaurusEntry;
import ch.cscf.jeci.exceptions.NotFoundException;
import ch.cscf.jeci.persistence.daos.interfaces.admin.GroupDAO;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.LanguageDAO;
import ch.cscf.jeci.persistence.daos.interfaces.midat.*;
import ch.cscf.jeci.persistence.daos.thesaurus.interfaces.ThesaurusReadOnlyService;
import ch.cscf.jeci.services.general.EntityFieldTranslatorService;
import ch.cscf.jeci.services.general.I18nService;
import ch.cscf.jeci.services.security.interfaces.SessionUserService;
import ch.cscf.jeci.services.systematics.interfaces.SystematicsReadOnlyService;
import ch.cscf.midat.services.interfaces.BioticWaterQualityRatingReadService;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: henryp
 */
@Service
public class SampleReadUpdateDeleteService implements ch.cscf.midat.services.interfaces.SampleReadUpdateDeleteService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final ArrayList<String> RANK_CODES_TO_INCLUDE_IN_REPRESENTATION = Lists.newArrayList("PHYLUM", "CLASS", "ORDER", "FAMILY", "GENUS", "SPECIES");
    @Autowired
    private SampleDAO sampleDAO;

    @Autowired
    private ProtocolImportDAO protocolImportDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private SampleStationDAO sampleStationDAO;

    @Autowired
    private EntityFieldTranslatorService localizerService;

    @Autowired
    private SystematicsReadOnlyService systematicsService;

    @Autowired
    private ThesaurusReadOnlyService thesaurusReadOnlyService;

    @Autowired
    private EvaluationGridReadService gridReadService;

    @Autowired
    private ch.cscf.midat.services.interfaces.GroundProtocolReadService groundProtocolReadService;

    @Autowired
    private BioticWaterQualityRatingReadService bioticWaterQualityRatingReadService;

    @Autowired
    private SessionUserService sessionUserService;

    @Autowired
    private I18nService i18n;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LanguageDAO languageDAO;

    @Autowired
    private IndexAvaliabilityCalendarDAO indexAvaliabilityCalendarDAO;

    @Autowired
    private HydroregimeDAO hydroregimeDAO;

    private List<String> valueNames = Lists.newArrayList("search.details.station.number","search.details.station.distance");

    private final Table<String, String, String> headerTranslations = HashBasedTable.create();

    private final double ELEVATION_DEFAULT = 300.0;
    private final String HORS_PERIOD_CALENDAR ="O";

    private  Map<String, List<SampleImportDisplayLog>> importLogs ;

    @PostConstruct
    public void loadTranslations() {
        importLogs = new HashMap<String, List<SampleImportDisplayLog>>();
        String propertyPrefix = "";
        List<Locale> locales = languageDAO.findUiSelectableLanguages().stream().map(l -> new Locale(l.getCode())).collect(Collectors.toList());

        for (Locale locale : locales) {
            String langKey = locale.getLanguage();

            for (String valueName : valueNames) {
                String translation = messageSource.getMessage(propertyPrefix + valueName, null, valueName, locale);
                headerTranslations.put(langKey, valueName, translation);
            }
        }

    }

    @Override
    @Transactional
    public void deleteProtocol(Long sampleHeaderId, Long protocolVersionId) {
        protocolImportDAO.deleteProtocolEntry(sampleHeaderId, protocolVersionId);
    }

    @Override
    @Transactional
    public void deleteSample(Long sampleId) {
        Sample sample = sampleDAO.findById(sampleId);
        if (sample == null) {
            throw new NotFoundException("Sample", sampleId.toString());
        }
        checkSampleAuthorization(sample, false);
        protocolImportDAO.deleteSample(sampleId);
    }

    @Override
    @Transactional
    public void batchUpdateField(List<Long> sampleIds, String fieldName, Object value) {
        checkSamplesAuthorization(sampleIds, false);
        sampleDAO.batchUpdateField(sampleIds, fieldName, value);
    }

    @Override
    @Transactional
    public void changeSampledPublishedStatus(List<Long> sampleIds, boolean published) {
        Long userId = sessionUserService.getSessionUserId();
        boolean isAppManager = sessionUserService.getSessionUserRolesNamesForMidat().contains("manager");
        boolean isNationalContributor = sessionUserService.userHasNationalContributorPermissionForMidat(userId);

        for (Long sampleId : sampleIds) {
            Sample sample = sampleDAO.findById(sampleId);

            if (!sample.getCreationUser().getId().equals(sessionUserService.getSessionUserId()) && !(isAppManager && isNationalContributor)) {
                throw new UnauthorizedException(MessageFormat.format("The user {0} is not authorized modify the publication status of sample with ID={1} because it was not created by him.",
                        sessionUserService.getSessionUserName(), sample.getId()));
            }
        }

        batchUpdateField(sampleIds, "published", published);
    }

    @Transactional
    @Override
    public void makeSamplesPublic(List<Long> sampleIds, boolean pub) {

        checkSamplesAuthorization(sampleIds, false);

        Group pubGroup = groupDAO.findByName("PUBLIC");

        for (Long sampleId : sampleIds) {
            Sample sample = sampleDAO.findById(sampleId);
            if (pub) {
                sample.getGroups().add(pubGroup);
            } else {
                sample.getGroups().remove(pubGroup);
            }
        }
    }


    @Transactional
    @Override
    public void deleteSamples(List<Long> sampleIds) {

        checkSamplesAuthorization(sampleIds, false);

        for (Long id : sampleIds) {
            protocolImportDAO.deleteSample(id);
        }
    }

    @Override
    public AuditingInfo getAuditInfoForSample(Long sampleId) {
        Sample sample = sampleDAO.findById(sampleId, "creationUser", "updateUser");

        AuditingInfo auditingInfo = new AuditingInfo(sample.getCreationDate(), sample.getDbCreationDate(), sample.getUpdateDate(), sample.getDbUpdateDate(),
                sample.getCreationUser() == null ? "" : sample.getCreationUser().getUsername(),
                sample.getDbCreationUser() == null ? "" : sample.getDbCreationUser(),
                sample.getUpdateUser() == null ? "" : sample.getUpdateUser().getUsername(),
                sample.getDbUpdateUser() == null ? "" : sample.getDbUpdateUser()
        );

        return auditingInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public SampleDetailDTO getSampleDetails(Long sampleId) {

        Sample sample = sampleDAO.loadSampleForDetailsView(sampleId);

        if (sample == null) {
            throw new NotFoundException("Sample", sampleId.toString());
        }

        checkSampleAuthorization(sample, true);

        if (!sample.getPublished()) {
            if (!sample.getCreationUser().getId().equals(sessionUserService.getSessionUserId())) {
                throw new UnauthorizedException(MessageFormat.format("The user {0} is not authorized view the sample with ID={1} because it is not published, and was not created by him.",
                        sessionUserService.getSessionUserName(), sample.getId()));
            }
        }

        localizerService.fillLocalizedFields(sample);
        localizerService.fillLocalizedFields(sample.getMandataryInstitution());
        localizerService.fillLocalizedFields(sample.getPrincipalInstitution());

        SampleDetailDTO dto = new SampleDetailDTO();

        // getting the the station link buffer value from the Thesaurus - aka 26.07.2019
        String raduisString =thesaurusReadOnlyService.getLocalizedString(ThesaurusCodes.MIDATHDITMTY_MIDATPARAM,"STALINKBUF",null);
        double raduis =Double.valueOf(raduisString);
        dto.setStaLinkBuffInMeter(raduis);
        dto.setProtocolType(sample.getSampleProtocolType().getCode());

        dto.setSampleId(sampleId);
        dto.setSampleDate(sample.getSampleDate());
        dto.setSampleDateDay(sample.getSampleDateDay());
        dto.setSampleDateMonth(sample.getSampleDateMonth());
        dto.setSampleDateYear(sample.getSampleDateYear());
        dto.setAnalysisDate(sample.getAnalysisDate());
        dto.setIsAboluteNumbers(sample.isAbsoluteNumbers());

        /**
         * Midat Plus
         */
        dto.setIbchLegendVersionId(sample.getIbchLegendVersionId());
        dto.setSpearLegendVersionId(sample.getSpearLegendVersionId());
        dto.setMakroLegendVersionId(sample.getMakroLegendVersionId());

        dto.setValeurGi(sample.getValeurGi());
        dto.setTaxonIndicateur(sample.getTaxonIndicateur());
        dto.setGiFinal(sample.getGiFinal());
        dto.setValuerVt(sample.getValuerVt());
        dto.setIbchRobust(sample.getIbchRobust());
        dto.setTaxonSumFamily(sample.getTaxonSumFamily());
        dto.setEphemeropteraCounter(sample.getEphemeropteraCounter());
        dto.setTricopteraCounter(sample.getTricopteraCounter());
        dto.setPlecopteraCounter(sample.getPlecopteraCounter());
        dto.setSommeAbon(sample.getSommeAbon());
        dto.setIbchQ(sample.getIbchQ());
        dto.setValeurCorrection(sample.getValeurCorrection());

        //if no history found disable the archived history tab
        dto.setHasHistory(false);
        logger.info("getSampleIndiceHistoryList:",sample.getSampleIndiceHistoryList());
        if(sample.getSampleIndiceHistoryList()!=null){
            List<SampleIndiceHistory> history = sample.getSampleIndiceHistoryList()
                    .stream().filter(h -> h.getVersionSeq().intValue() == -1)
                    .collect(Collectors.toList());
            if(history !=null && history.size() == 1 ){
                dto.setHasHistory(true);
            }
        }

        if(sample.getIbchQ()!=null){
            String hydroCvlCode=  hydroregimeDAO.getSampleHydroregime(sample.getIbchQ()).getHdrCode();
            String desc= thesaurusReadOnlyService.getLocalizedString(ThesaurusCodes.REALM_IBCHQ, hydroCvlCode, i18n.currentLanguageCode());
            dto.setIbchQDesignation(desc);
        }else{
            dto.setIbchQDesignation("");
        }

        Map<String, String> documents = sample.getDocuments().stream().collect(
                Collectors.toMap(document -> {
                    String protocolTypeCode = document.getProtocolVersion().getProtocolType().getCode();
                    String protocolType = thesaurusReadOnlyService.getLocalizedString(ThesaurusCodes.REALM_MIDATPROTO, protocolTypeCode, i18n.currentLanguageCode());
                    return protocolType;

                }, SampleDocument::getFileName));

        dto.setDocuments(documents);

        extractSampleComment(sample, dto);

        extractSampleTaxonIndicateurs(sample, dto);

        if (sample.getSampleProject() != null) {
            dto.setProject(sample.getSampleProject().getCode() + " - " + sample.getSampleProject().getDesignation());
        }

        dto.setSampleCommentOther(sample.getSampleCommentOther());
       //extractComment(dto);  //as the comments will be ready only in the sample so no need to get them.

        if (sample.getPrincipalInstitution() != null) {
            dto.setPrincipalInstitution(sample.getPrincipalInstitution().getName());
        }

        if (sample.getMandataryInstitution() != null) {
            dto.setMandataryInstitution(sample.getMandataryInstitution().getName());
        }

        if (sample.getPrecisionI18n() != null) {
            dto.setPrecision(sample.getPrecisionI18n());
        }

        if (sample.getPrecisionReferenceSystemI18n() != null) {
            dto.setPrecisionReferenceSystem(sample.getPrecisionReferenceSystemI18n());
        }

        dto.setAuditingInfo(getAuditInfoForSample(sampleId));

        extractLabRecordFieldsToDTO(sample, dto);
        extractLocalityAndWatercourseToDTO(sample, dto);
        extractAuthorshipToDTO(sample, dto);
        extractEvaluationGridToDTO(sample, dto);
        extractGroundProtocolDataToDTO(sample, dto);
        extractMassStatusToDTO(sample, dto);
        extractWaterQualityGradeToDTO(sample, dto);

        return dto;
    }

    private void extractComment(SampleDetailDTO dto) {
        List<LocalizedThesaurusEntry> comments = thesaurusReadOnlyService.getRealm(ThesaurusCodes.REALM_MIDATLOADCOMMENT).getEntries(i18n.currentLanguageCode());
        List<CommentDTO> commentDTOList = new ArrayList<CommentDTO>();
        comments.forEach(c -> {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(c.getValueId());
            commentDTO.setDesignation(c.getDesignation());
            commentDTOList.add(commentDTO);
        });
        dto.setComments(commentDTOList);
    }

    private void extractSampleComment(Sample sample, SampleDetailDTO dto) {
        ArrayList<SampleComment> sampleComments = Lists.newArrayList(sample.getComments());

        List<CommentDTO> sampleCommentDTOList = new ArrayList<CommentDTO>();
        sampleComments.forEach(c -> {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(c.getSampleComment().getId());
            commentDTO.setDesignation(thesaurusReadOnlyService.getLocalizedString(ThesaurusCodes.REALM_MIDATLOADCOMMENT, c.getSampleComment().getCode(), i18n.currentLanguageCode()));
            sampleCommentDTOList.add(commentDTO);
        });
        dto.setSampleComments(sampleCommentDTOList);
    }


    private void extractSampleTaxonIndicateurs(Sample sample, SampleDetailDTO dto){
        ArrayList<SampleTaxonIndicateur> sampleTaxonIndicateurs = Lists.newArrayList(sample.getSampleTaxonIndicateur());
        List<SampleTaxonIndicateurDTO> SampleTaxonIndicateurDTOList = new ArrayList<SampleTaxonIndicateurDTO>();
        sampleTaxonIndicateurs.forEach(c -> {
            SampleTaxonIndicateurDTO sampleTaxonIndicateurDTO = new SampleTaxonIndicateurDTO();
            sampleTaxonIndicateurDTO.setTaxonIndicateurName(c.getTaxonIndicateurName());
            SampleTaxonIndicateurDTOList.add(sampleTaxonIndicateurDTO);
        });
        dto.setSampleTaxonIndicateurs(SampleTaxonIndicateurDTOList);
    }

    /**
     * Checks that the current user is authorized to manipulate the  samples specified by their ids.
     * Throws an UnauthorizedException otherwise.
     * The boolea parameter specified if the access is required for read-only of also write/delete.
     *
     * @param sampleIds
     * @param readOnly
     */
    private void checkSamplesAuthorization(List<Long> sampleIds, boolean readOnly) {
        for (Long sampleId : sampleIds) {
            Sample sample = sampleDAO.findById(sampleId);
            checkSampleAuthorization(sample, readOnly);
        }
    }


    /**
     * Checks that the current user is authorized to manipulate the given sample
     * Throws an UnauthorizedException otherwise.
     * The boolean parameter specified if the access is required for read-only of also write/delete.
     *
     * @param sample
     * @param readOnly
     */
    private void checkSampleAuthorization(Sample sample, boolean readOnly) {
         if(readOnly){
             if (!sessionUserService.userHasNationalUserPermissionForMidat(sessionUserService.getSessionUserId())) {

                 boolean userHasReadPermissionOnThisSample = sessionUserService.userHasReadPermissionOnThisSampleForMidat(sessionUserService.getSessionUserId(),sample.getGroupIds());
                 Long idOfPublicGroup = groupDAO.getIdOfPublicGroup();
                 List<Long> collect = sample.getGroupIds().stream().filter(sampleId -> sampleId == idOfPublicGroup).collect(Collectors.toList());
                 if (!userHasReadPermissionOnThisSample && collect.size() == 0) {
                     throw new UnauthorizedException(MessageFormat.format("The user {0} is not authorized to read sample with ID={1} because it does not belong to any group she/he is part of.", sessionUserService.getSessionUser().getUsername(), sample.getId()));
                 }
             }
         }else{
             if (!sessionUserService.userHasNationalContributorPermissionForMidat(sessionUserService.getSessionUserId())) {

                 boolean userHasReadWritePermissionOnThisSample = sessionUserService.userHasReadWritePermissionOnThisSampleForMidat(sessionUserService.getSessionUserId(),sample.getGroupIds());
                 if (!userHasReadWritePermissionOnThisSample) {
                     throw new UnauthorizedException(MessageFormat.format("The user {0} is not authorized to write sample with ID={1} because it does not belong to any group she/he is part of.", sessionUserService.getSessionUser().getUsername(), sample.getId()));
                 }
             }
         }
        //check that sample belongs to one of the groups of the current user

    }

    private void extractLabRecordFieldsToDTO(Sample sample, SampleDetailDTO dto) {

        List<LabRecordFieldDTO> labRecordFieldDTOs = new ArrayList<>();

        for (LabRecordField field : sample.getLabRecordFields()) {
            LabRecordFieldDTO fieldDTO = new LabRecordFieldDTO();
            //labRecordFieldDTOs.add(fieldDTO);
            fieldDTO.setComment(field.getComment());
            fieldDTO.setFrequency(field.getFrequency());
            fieldDTO.setStadium(field.getStadium());
            fieldDTO.setSortOrder(field.getSortOrder());

            if (field.getTaxon() != null) {
                logger.info("field.getTaxon().getId():",field.getTaxon().getId());
                fieldDTO.setTaxonId(field.getTaxon().getId());
                fieldDTO.setInfofaunaTaxon(systematicsService.taxonToString(field.getTaxon().getId(), RANK_CODES_TO_INCLUDE_IN_REPRESENTATION, " / "));

            }

            if (field.getFieldMapping() != null) {
                fieldDTO.setIbchTaxon(field.getFieldMapping().getIbchTaxon());
            }
            labRecordFieldDTOs.add(fieldDTO);
        }

        //labRecordFieldDTOs.sort((o1, o2) -> o1.getSortOrder().compareTo(o2.getSortOrder()));
        if(labRecordFieldDTOs.size()>0){
            labRecordFieldDTOs.sort((o1, o2) -> {
                if(o1.getSortOrder() !=null && o2.getSortOrder() !=null){
                    return o1.getSortOrder().compareTo(o2.getSortOrder());
                }
                return 0;
            });
        }


        dto.setLabRecordFields(labRecordFieldDTOs);
    }

    private void extractLocalityAndWatercourseToDTO(Sample sample, SampleDetailDTO dto) {

        final Long watercourseTypeId = thesaurusReadOnlyService.getValueId(ThesaurusCodes.REALM_MIDATHDITMTY, ThesaurusCodes.MIDATHDITMTY_WATERCOURSE);
        final Long localityTypeId = thesaurusReadOnlyService.getValueId(ThesaurusCodes.REALM_MIDATHDITMTY, ThesaurusCodes.MIDATHDITMTY_LOCALITY);
        final Long calledPlaceTypeId = thesaurusReadOnlyService.getValueId(ThesaurusCodes.REALM_MIDATHDITMTY, ThesaurusCodes.MIDATHDITMTY_CALLEDPLACE);

        final double x1 = sample.getStation().getCoordinates().getX();
        final double y1 = sample.getStation().getCoordinates().getY();

        dto.setStationNumber(sample.getStation().getStationNumber());
        dto.setStationCoordinatesX(x1);
        dto.setStationCoordinatesY(y1);

        dto.setStationGewissNumber(sample.getStation().getStationGewissNumber());
        dto.setStationId(sample.getStation().getId());

        if(sample.getStation().getParent() == null){
            List<SampleStation> sampleStationStations = sample.getStation().getStationStations();
            List<StationDTO> sampleSationStationsDTOList = getStationDTOS(sample.getStation().getId(), x1, y1, sampleStationStations,sample.getPrincipalInstitution());
            dto.setSampleStationStations(sampleSationStationsDTOList);

            // getting the the station link buffer value from the Thesaurus
            String raduisString =thesaurusReadOnlyService.getLocalizedString(ThesaurusCodes.MIDATHDITMTY_MIDATPARAM,"STALINKBUF",null);
            double raduis =Double.valueOf(raduisString);

            List<SampleStation> stationStations = sampleStationDAO.findStationsInsideCercle(sample.getStation(), raduis);
            List<StationDTO> sationStationsDTOList = getStationDTOS(sample.getStation().getId(), x1, y1, stationStations,sample.getPrincipalInstitution());
            dto.setStationStations(sationStationsDTOList);
        }else{

            dto.setSampleStationStations(Collections.emptyList());
            dto.setStationStations(Collections.emptyList());

            StationDTO mainStationDTO = getStationDTO(x1,y1,sample.getStation().getParent(),sample.getPrincipalInstitution());
            dto.setMainStation(mainStationDTO);
        }


        extractStationCantonForSampleDTO(sample.getStation(), dto);

        // double altitude = sample.getStation().getCoordinates().getCoordinates()[0].z;
        double altitude = sample.getStation().getCoordinateZ();
        dto.setStationAltitude(Double.valueOf(altitude));

        for (SampleAttribute attribute : sample.getAttributes()) {

            if (attribute.getAttributeTypeId().equals(watercourseTypeId)) {
                dto.setWatercourse(attribute.getValue());
            }

            if (attribute.getAttributeTypeId().equals(localityTypeId)) {
                dto.setLocality(attribute.getValue());
            }

            if (attribute.getAttributeTypeId().equals(calledPlaceTypeId)) {
                dto.setNamedPlace(attribute.getValue());
            }
        }

    }

    private void extractStationCantonForSampleDTO(SampleStation station, SampleDetailDTO dto) {
        if(  station.getStationCanton()==null){
             dto.setStationCanton("");
        }else{
            String cantonCode = station.getStationCanton().getCode();
            String cantonDes = thesaurusReadOnlyService.getLocalizedString(ThesaurusCodes.REALM_CANTON, cantonCode, i18n.currentLanguageCode());
            dto.setStationCanton(cantonDes);
        }
    }

    private List<StationDTO> getStationDTOS(long id, double x1, double y1, List<SampleStation> sampleStationStations,Institution principalInstitution) {
        List<StationDTO> sampleSationStationsDTOList = new ArrayList<StationDTO>();
        sampleStationStations.forEach(s -> {
            /* exclude the station itself && (make sure that the station you add  is not linked yet or already linked to this main station */
            if (s.getId() != id && (s.getParent()== null || (s.getParent()!=null && s.getParent().getId() == id ))) {
                StationDTO stationDTO = getStationDTO(x1, y1, s,principalInstitution);
                sampleSationStationsDTOList.add(stationDTO);
            }
        });
        return sampleSationStationsDTOList;
    }

    private StationDTO getStationDTO(double x1, double y1, SampleStation s,Institution principalInstitution) {
        StationDTO stationDTO = new StationDTO();
        double x2 = s.getCoordinates().getX();
        double y2 = s.getCoordinates().getY();

        double distance = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        DecimalFormat df2 = new DecimalFormat("###.##");
        distance = Double.valueOf(df2.format(distance));

        stationDTO.setStationDistance(distance);

        extractStationCantonForStationDTO(s, stationDTO);

        stationDTO.setStationCoordinatesX(x2);
        stationDTO.setStationCoordinatesY(y2);

        // double altitude = s.getCoordinates().getCoordinates()[0].z;

        if(s.getCoordinateZ() != null){
            stationDTO.setStationAltitude(s.getCoordinateZ());
        }

        stationDTO.setStationNumber(s.getStationNumber());
        stationDTO.setStationGewissNumber(s.getStationGewissNumber());
        stationDTO.setStationPrincipalInstitution(principalInstitution.getName());
        stationDTO.setStationPrincipalInstitutionId(s.getStationPrincipalInstitutionId());
        stationDTO.setId(s.getId());

        Map<String, String> headers = headerTranslations.row(i18n.currentLanguageCode());
        String display = headers.get("search.details.station.number")+" : "+ s.getStationNumber() + ", "+headers.get("search.details.station.distance")+" : " + distance + " m";
        stationDTO.setStationDisplay(display);
        return stationDTO;
    }

    private void extractStationCantonForStationDTO(SampleStation s, StationDTO stationDTO) {
        if(s.getStationCanton() == null){
            stationDTO.setStationCanton("");
        }else{
            String canCode = s.getStationCanton().getCode();
            String canDes = thesaurusReadOnlyService.getLocalizedString(ThesaurusCodes.REALM_CANTON, canCode, i18n.currentLanguageCode());
            stationDTO.setStationCanton(canDes);
        }
    }

    private void extractAuthorshipToDTO(Sample sample, SampleDetailDTO dto) {

        final Long determinatorTypeId = thesaurusReadOnlyService.getValueId(ThesaurusCodes.REALM_MIDATHDITMTY, ThesaurusCodes.MIDATHDITMTY_DETERMINATOR);
        final Long operatorTypeId = thesaurusReadOnlyService.getValueId(ThesaurusCodes.REALM_MIDATHDITMTY, ThesaurusCodes.MIDATHDITMTY_OPERATOR);

        String determinator = null, operator = null;

        if (sample.getAttributes() != null) {
            //person from DB has precedence over person from Excel file
            for (SampleAttribute source : sample.getAttributes()) {

                if (source.getAttributeTypeId().equals(determinatorTypeId)) {
                    if (source.getSourceType() == SampleAttributeSource.PERSON_TABLE) {
                        determinator = source.getValue();
                    } else {
                        if (determinator == null) {
                            determinator = source.getValue();
                        }
                    }
                }

                if (source.getAttributeTypeId().equals(operatorTypeId)) {
                    if (source.getSourceType() == SampleAttributeSource.PERSON_TABLE) {
                        operator = source.getValue();
                    } else {
                        if (operator == null) {
                            operator = source.getValue();
                        }
                    }
                }
            }
        }

        dto.setValidationPerson(determinator);
        dto.setSamplePerson(operator);
    }


    private void extractEvaluationGridToDTO(Sample sample, SampleDetailDTO dto) {

        dto.setEvaluationGrid(gridReadService.getEvalutationGrid(sample.getId()));

    }

    private void extractMassStatusToDTO(Sample sample, SampleDetailDTO dto) {
        dto.setMassImport(true);
        for (SampleAttribute source : sample.getAttributes()) {
            if (source.getProtocolType().getCode().equals(ThesaurusCodes.MIDATPROTO_LABORATORY)) {
                dto.setMassImport(false);
                break;
            }
        }
    }

    private void extractGroundProtocolDataToDTO(Sample sample, SampleDetailDTO dto) {
        dto.setGroundProtocol(groundProtocolReadService.getGroundProtocol(sample.getId()));
    }

    private void extractWaterQualityGradeToDTO(Sample sample, SampleDetailDTO dto) {

        BioticWaterQualityRatingDTO ibchQuality = null, makroindexQuality = null, spearQuality = null;

        Integer sampleDateDay = sample.getSampleDateDay();
        Integer sampleDateMonth =sample.getSampleDateMonth() ;

        // double sampleStationElevation = sample.getStation().getCoordinates().getCoordinates()[0].z;
        double sampleStationElevation = sample.getStation().getCoordinateZ();

        if(Double.isNaN(sampleStationElevation) ||(!Double.isNaN(sampleStationElevation) && sampleStationElevation < ELEVATION_DEFAULT  ) ) {
            sampleStationElevation = ELEVATION_DEFAULT;
        }

        String ibchExtraMsgCode =HORS_PERIOD_CALENDAR;
        String ibchCalendarDateInterval=null;
        List<IndexAvaliabilityCalendar> indexAvaliabilityCalendarList = indexAvaliabilityCalendarDAO.getIndiceAvaliabiltyCalByDateElevation(sampleDateDay,sampleDateMonth,sampleStationElevation);
        // extract the IBCH extra msg code
        if(indexAvaliabilityCalendarList !=null ){
            List<IndexAvaliabilityCalendar> ibchAvaliabilityCalendar = indexAvaliabilityCalendarList.stream().filter(i -> i.getMidatIndice().getCode().equalsIgnoreCase(ThesaurusCodes.MIDATINDICE_IBCH)).collect(Collectors.toList());
            if(ibchAvaliabilityCalendar.size() ==1){
                IndexAvaliabilityCalendar  ibchAvaliabilityCalendarItem =  ibchAvaliabilityCalendar.get(0);
                ibchExtraMsgCode = ibchAvaliabilityCalendarItem.getMidatWindow().getCode();
                ibchCalendarDateInterval = ibchAvaliabilityCalendarItem.getStartDay()+"."+ibchAvaliabilityCalendarItem.getStartMonth() +" - "+ibchAvaliabilityCalendarItem.getEndDay()+"."+ibchAvaliabilityCalendarItem.getEndMonth();
            }
        }

        if (sample.getIbchIndexValue() != null && sample.getIbchLegendVersionId() !=null) {
            ibchQuality = bioticWaterQualityRatingReadService.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_IBCH, sample.getIbchIndexValue(),sample.getIbchLegendVersionId());
        }
        if (sample.getMakroIndexValue() != null && sample.getMakroLegendVersionId() !=null) {
            makroindexQuality = bioticWaterQualityRatingReadService.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_MAKROINDEX, sample.getMakroIndexValue(),sample.getMakroLegendVersionId());
        }
        if (sample.getSpearIndexValue() != null && sample.getSpearLegendVersionId() !=null ) {
            spearQuality = bioticWaterQualityRatingReadService.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_SPEARINDEX, sample.getSpearIndexValue(),sample.getSpearLegendVersionId());
        }

        BioticIndexesDTO indexesDTO = new BioticIndexesDTO(sample.getIbchIndexValue(), sample.getMakroIndexValue(), sample.getSpearIndexValue(), ibchQuality, makroindexQuality, spearQuality,ibchExtraMsgCode,ibchCalendarDateInterval);

        dto.setBioticIndexes(indexesDTO);
    }
    @Override
    @Transactional(readOnly = true)
    public List<SampleImportDisplayLog> loadSampleImportLog(Long sampleId){
        List<SampleImportDisplayLog> journal = new ArrayList<>();

        SampleImportDisplayLog sampleImportDisplayLog=   sampleDAO.getImportLogKey(sampleId, i18n.currentLanguageId());
        String key = sampleImportDisplayLog.getIpoIphId()+"@"+i18n.currentLanguageId();
        if(importLogs.get(key) != null || (importLogs.get(key) == null && importLogs.containsKey(key))){
            journal =  importLogs.get(key);
        }else{
            journal = sampleDAO.loadSampleImportLog(sampleId, i18n.currentLanguageId());
            importLogs.put(key, journal);
        }

        return journal;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SampleStatPerProtocolMonth> getSampleProtocolPerMonth(){
        return sampleDAO.getSampleProtocolPerMonth();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SampleProtocolPerUserCanton> getSampleProtocolPerUserCanton(){
        return sampleDAO.getSampleProtocolPerUserCanton();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListConnection> getUsersConnectionHistory(){
        return sampleDAO.getUsersConnectionHistory();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProtocolIndex> getSampleIndexHistory(){
        return sampleDAO.getSampleIndexHistory();
    }



    /** **/

    @Override
    @Transactional(readOnly = true)
    public SampleInfoIbchData loadSampleInfoIbchData(Long sampleId){
        return sampleDAO.loadSampleInfoIbchData(sampleId);
    }


    // midat plus
    @Override
    @Transactional(readOnly = true)
    public SampleIndiceHistory loadSampleIndiceHistoryData(Long sampleId){
        BioticWaterQualityRatingDTO ibchQuality = null,  spearQuality = null;
        SampleIndiceHistory sampleIndiceHistory = sampleDAO.loadSampleIndiceHistoryData(sampleId);

        if (sampleIndiceHistory.getIbchIndexValue() != null && sampleIndiceHistory.getIbchLegendVersionId() !=null) {
            ibchQuality = bioticWaterQualityRatingReadService.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_IBCH, sampleIndiceHistory.getIbchIndexValue(),sampleIndiceHistory.getIbchLegendVersionId());
            sampleIndiceHistory.setIbchQuality(ibchQuality);
        }

        if (sampleIndiceHistory.getSpearIndexValue() != null && sampleIndiceHistory.getSpearLegendVersionId() !=null ) {
            spearQuality = bioticWaterQualityRatingReadService.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_SPEARINDEX, sampleIndiceHistory.getSpearIndexValue(),sampleIndiceHistory.getSpearLegendVersionId());
            sampleIndiceHistory.setSpearQuality(spearQuality);
        }

        if(sampleIndiceHistory.getIbchQ()!=null){
            String hydroCvlCode=  hydroregimeDAO.getSampleHydroregime(sampleIndiceHistory.getIbchQ()).getHdrCode();
            String desc= thesaurusReadOnlyService.getLocalizedString(ThesaurusCodes.REALM_IBCHQ, hydroCvlCode, i18n.currentLanguageCode());
            sampleIndiceHistory.setIbchQDesignation(desc);
        }else{
            sampleIndiceHistory.setIbchQDesignation("");
        }


        return sampleIndiceHistory;
    }

}
