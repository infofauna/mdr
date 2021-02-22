package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.dto.errors.Message;
import ch.cscf.jeci.domain.dto.midat.LabProtocolFormValues;
import ch.cscf.jeci.domain.entities.base.EntityStatus;
import ch.cscf.jeci.domain.entities.base.Project;
import ch.cscf.jeci.domain.entities.midat.*;
import ch.cscf.jeci.domain.entities.midat.sample.*;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.InstitutionDAO;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.LanguageDAO;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.PersonDAO;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.ProjectDAO;
import ch.cscf.jeci.persistence.daos.interfaces.midat.*;
import ch.cscf.jeci.persistence.daos.thesaurus.interfaces.ThesaurusReadOnlyService;
import ch.cscf.jeci.services.async.interfaces.AsyncTaskExecutorService;
import ch.cscf.jeci.services.general.EntityFieldTranslatorService;
import ch.cscf.jeci.services.general.I18nService;
import ch.cscf.jeci.services.security.interfaces.SessionUserService;
import ch.cscf.midat.services.interfaces.ProtocolVersionCachedRepository;
import ch.cscf.midat.services.interfaces.SampleSearchService;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: henryp
 */
@Service
public class ProtocolImportService implements ch.cscf.midat.services.interfaces.ProtocolImportService  {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThesaurusReadOnlyService thesaurusReadOnlyService;

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private InstitutionDAO institutionDAO;

    @Autowired
    private LanguageDAO languageDAO;

    @Autowired
    private MIDATProtocolVersionDAO protocolVersionDAO;

    @Autowired
    private ProtocolImportDAO protocolImportDAO;

    @Autowired
    private ProtocolImportHeaderDAO protocolImportHeaderDAO;


    @Autowired
    private GridProtocolImportDAO gridProtocolImportDAO;


    @Autowired
    private GroundProtocolImportDAO groundProtocolImportDAO;

    @Autowired
    private I18nService i18NService;

    @Autowired
    private SampleDAO sampleDAO;

    @Autowired
    private SampleStationDAO stationDAO;

    @Autowired
    private AsyncTaskExecutorService executorService;

    @Autowired
    private SessionUserService sessionUserService;

    @Autowired
    private EntityFieldTranslatorService translator;

    @Autowired
    private SampleCommentDAO sampleCommentDAO;

    @Autowired
    private ProtocolVersionCachedRepository protocolVersionCachedRepository;

    @Autowired
    private ExcelParserService excelParserService;


    @Autowired
    private LabProtocolImportDAO labProtocolImportDAO;



    @Autowired
    private ImportMassDataDetailDAO importMassDataDetailDAO;

    private Map<Long, ProtocolImportHeader> ongoingImports = new HashMap<>();

    @Override
    public ProtocolImportHeader buildNewImportHeader(){
        ProtocolImportHeader protocolImportHeader = new ProtocolImportHeader();
        Long selectedLanguageId = i18NService.currentLanguageId();
        protocolImportHeader.setLanguageId(selectedLanguageId);
        return protocolImportHeader;
    }

    /*
      MIDAT-061 in the Screen Validation result if you  click the Cancel button,
      the system will go back to the data import page with some pre-filled values baded on the protocol you have canceled
      as the comments parts are not part from the ProtocolImportHeader they are persisted in the DB,
      so we need to manage them at the session level
      */
    @Override
    public ProtocolImportHeader buildNewImportHeaderBasedOn(Long existingProtocolImportHeaderId) {
        ProtocolImportHeader existing = protocolImportHeaderDAO.findById(existingProtocolImportHeaderId);
        ProtocolImportHeader newImportHeader = new ProtocolImportHeader();
        newImportHeader.setLanguageId(existing.getLanguageId());
        newImportHeader.setSamplePersonId(existing.getSamplePersonId());
        newImportHeader.setAnalysisPersonId(existing.getAnalysisPersonId());
        newImportHeader.setAnalysisDate(existing.getAnalysisDate());
        newImportHeader.setDocumentUrl(existing.getDocumentUrl());
        newImportHeader.setMandataryInstitutionId(existing.getMandataryInstitutionId());
        newImportHeader.setPrincipalInstituionId(existing.getPrincipalInstituionId());
        newImportHeader.setProtocolVersionId(existing.getProtocolVersionId());
        newImportHeader.setPrecisionLevelId(existing.getPrecisionLevelId());
        newImportHeader.setReferenceSystemId(existing.getReferenceSystemId());

        //set the project
        newImportHeader.setSampleProjectId(existing.getSampleProjectId());

        //room to set the comments values
        newImportHeader.setCommentIds(existing.getCommentIds());
        newImportHeader.setCommentOtherChk(existing.isCommentOtherChk());
        newImportHeader.setCommentOther(existing.getCommentOther());

        return newImportHeader;
    }

    @Override
    @Transactional(readOnly = true)
    public LabProtocolFormValues getLabFormValues(){

        String lan = i18NService.currentLanguageCode();

        LabProtocolFormValues values = new LabProtocolFormValues();
/*
        values.setReferenceSystems(thesaurusReadOnlyService.getEntriesForRealm(ThesaurusCodes.REALM_SYSTLREF, lan));
        values.setInstitutions(institutionDAO.list("name"));
        values.setLanguages(languageDAO.findUiSelectableLanguages());
        values.setPersons(personDAO.list("lastName"));
        List<Project> _projects =loadActifProjects();
        values.setProjects(_projects);
        values.setComments(thesaurusReadOnlyService.getEntriesForRealm(ThesaurusCodes.REALM_MIDATLOADCOMMENT, lan));
        */

        values.setReferenceSystems(thesaurusReadOnlyService.getEntriesForRealm(ThesaurusCodes.REALM_SYSTLREF, lan));
        values.setInstitutions(institutionDAO.list("name").stream().filter(p -> p.getStatus() == EntityStatus.ACTIVE).collect(Collectors.toList()));
        values.setLanguages(languageDAO.findUiSelectableLanguages());
        values.setPersons(personDAO.list("lastName").stream().filter(p -> p.getStatus() == EntityStatus.ACTIVE).collect(Collectors.toList()));
        values.setProjects(projectDAO.list("code").stream().filter(p -> p.getStatus() == EntityStatus.ACTIVE).collect(Collectors.toList()));
        values.setComments(thesaurusReadOnlyService.getEntriesForRealm(ThesaurusCodes.REALM_MIDATLOADCOMMENT, lan));


        List<MIDATProtocolVersion> versions = protocolVersionDAO.findForType(thesaurusReadOnlyService.getValueId(ThesaurusCodes.REALM_MIDATPROTO, ThesaurusCodes.MIDATPROTO_LABORATORY));
        // only show active versions
        List<MIDATProtocolVersion> activeVersions = versions.stream().filter(v -> v.getStatus() == EntityStatus.ACTIVE).collect(Collectors.toList());

        translator.fillLocalizedFields(activeVersions);
        values.setProtocolVersions(activeVersions);

        return values;
    }

    private List<Project> loadActifProjects() {
        List<Project> _list = projectDAO.list("designation");
        List<Project> _filtered = new ArrayList<>();

        if(_list !=null && _list.size()>0){
            _filtered = _list.stream().filter(project -> project.getStatus() == EntityStatus.ACTIVE).collect(Collectors.toList());
         }
         return _filtered;
    }


    @Override
    @Transactional(readOnly = true)
    public LabProtocolFormValues getMassFormValues(){

        String lan = i18NService.currentLanguageCode();

        LabProtocolFormValues values = new LabProtocolFormValues();

        /*
        values.setIsMass(true);
        values.setReferenceSystems(thesaurusReadOnlyService.getEntriesForRealm(ThesaurusCodes.REALM_SYSTLREF, lan));
        values.setInstitutions(institutionDAO.list("name"));
        values.setLanguages(languageDAO.findUiSelectableLanguages());
        values.setPersons(personDAO.list("lastName"));

        List<Project> _projects =loadActifProjects();
        values.setProjects(_projects);

        values.setComments(thesaurusReadOnlyService.getEntriesForRealm(ThesaurusCodes.REALM_MIDATLOADCOMMENT, lan));
        */
        values.setIsMass(true);
        values.setReferenceSystems(thesaurusReadOnlyService.getEntriesForRealm(ThesaurusCodes.REALM_SYSTLREF, lan));
        values.setInstitutions(institutionDAO.list("name").stream().filter(p -> p.getStatus() == EntityStatus.ACTIVE).collect(Collectors.toList()));
        values.setLanguages(languageDAO.findUiSelectableLanguages());
        values.setPersons(personDAO.list("lastName").stream().filter(p -> p.getStatus() == EntityStatus.ACTIVE).collect(Collectors.toList()));
        values.setProjects(projectDAO.list("code").stream().filter(p -> p.getStatus() == EntityStatus.ACTIVE).collect(Collectors.toList()));
        values.setComments(thesaurusReadOnlyService.getEntriesForRealm(ThesaurusCodes.REALM_MIDATLOADCOMMENT, lan));


        List<MIDATProtocolVersion> versions = protocolVersionDAO.findForType(thesaurusReadOnlyService.getValueId(ThesaurusCodes.REALM_MIDATPROTO, ThesaurusCodes.MIDATPROTO_MASS));
        translator.fillLocalizedFields(versions);
        values.setProtocolVersions(versions);

        return values;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Collection<?>> getAdditionalDataFormValues(){

        Map<String, Collection<?>> result = new HashMap<>();

        /*
        result.put("languages", languageDAO.findUiSelectableLanguages());
        result.put("persons", personDAO.list("lastName"));
        List<Project> _projects =loadActifProjects();
        result.put("projects", _projects);
        */

        result.put("languages", languageDAO.findUiSelectableLanguages());
        result.put("persons", personDAO.list("lastName").stream().filter(p -> p.getStatus() == EntityStatus.ACTIVE).collect(Collectors.toList()));
        result.put("projects",projectDAO.list("code").stream().filter(p -> p.getStatus() == EntityStatus.ACTIVE).collect(Collectors.toList()));

        return result;
    }


    /**
    @Override
    @Transactional
    public List<Message> importProtocolDataAndExcelFile(ProtocolImportHeader protocolImportHeader){

        Long protocolImportId = protocolImportDAO.executeImportProcedure(protocolImportHeader, sessionUserService.getSessionUserId());
        protocolImportHeader.setId(protocolImportId);

        List<Message> messages = protocolImportDAO.executeValidateProtocolProcedure(protocolImportId, i18NService.currentLanguageId(), sessionUserService.getSessionUserId());

        return messages;
    }
    **/


    /*
      MIDAT-060 :
      when you hit continue button, the system will save the protocol in the DB using normal logic and then return back the ID
      as the comments are not a part of the protocolImportHeader we should put them in the session for any furthur use
     */

    @Override
    @Transactional
    public ProtocolImportHeader importProtocolDataAndExcelFile(ProtocolImportHeader protocolImportHeader){

        Sheet excelSheet = excelParserService.getExcelSheet(protocolImportHeader);
        Map<String, Integer> columnIndexes = excelParserService.extractExcelSheetColumnIndexes(excelSheet);
        excelParserService.parseExcelSheetForImportProtocolHeader( protocolImportHeader,  excelSheet, columnIndexes);
        // set the IPH_STATUS to A
        protocolImportHeader.setStatus(EntityStatus.ACTIVE);


        logger.info(">>>>>>>> protocolImportHeader.getSphId()) "+protocolImportHeader.getSphId());

        /**
         * When parentId is not this means we are adding additional data (sample grid or Field protocol)
         */
        if(protocolImportHeader.getSphId()!= null)
        {
            Sample sample = sampleDAO.findById(protocolImportHeader.getSphId());
            protocolImportHeader.setParentId(sample.getSampleIphId());
        }
        protocolImportHeaderDAO.persist(protocolImportHeader);


        Long protocolImportHeaderId = protocolImportHeader.getId();;
        logger.info(">>>>>>>> newly added protocolImportHeaderId "+protocolImportHeader.getId());
        if (protocolVersionCachedRepository.isOfType(protocolImportHeader.getProtocolVersionId(), ThesaurusCodes.MIDATPROTO_LABORATORY)) {
                logger.info(">>>>>>>> parse  MIDATPROTO_LABORATORY");
                List<LabProtocolImport>  labProtocolImports = excelParserService
                        .parseExcelSheetForLabProtocolImport(protocolImportHeaderId, protocolImportHeader.getProtocolVersionId(), excelSheet, columnIndexes);
                // loop over the labProtocolImports and persist them
                labProtocolImports.stream().forEach(p->{
                    labProtocolImportDAO.persist(p);
                });
        }

        if (protocolVersionCachedRepository.isOfType(protocolImportHeader.getProtocolVersionId(), ThesaurusCodes.MIDATPROTO_GRDEVAL)) {
            logger.info(">>>>>>>> parse  MIDATPROTO_GRDEVAL");
            List<GridProtocolImport>  gridProtocolImports = excelParserService
                    .parseExcelSheetForGridProtocolImport(protocolImportHeaderId, protocolImportHeader.getProtocolVersionId(), excelSheet, columnIndexes);
            // loop over the gridProtocolImports and persist them
            gridProtocolImports.stream().forEach(p->{
                gridProtocolImportDAO.persist(p);
            });
        }

        if (protocolVersionCachedRepository.isOfType(protocolImportHeader.getProtocolVersionId(), ThesaurusCodes.MIDATPROTO_GROUND)) {
            logger.info(">>>>>>>> parse  MIDATPROTO_GROUND");
            List<GroundProtocolImport>  groundProtocolImports = excelParserService
                    .parseExcelSheetForGroundProtocolImport(protocolImportHeaderId, protocolImportHeader.getProtocolVersionId(), excelSheet, columnIndexes);
            // loop over the groundProtocolImports and persist them
            groundProtocolImports.stream().forEach(p->{
                groundProtocolImportDAO.persist(p);
            });

        }
        protocolImportHeader.setId(protocolImportHeaderId);
        return protocolImportHeader;
    }

    @Override
    @Transactional
    public List<Message> executeValidateProtocolProcedure(ProtocolImportHeader protocolImportHeader){
        List<Message> messages = protocolImportDAO.executeValidateProtocolProcedure(protocolImportHeader.getId(), i18NService.currentLanguageId(), sessionUserService.getSessionUserId());
        return messages;
    }

    /*
    @Override
    public Long asyncImportMassDataAndExcelFile(ProtocolImportHeader protocolImportHeader){
        logger.info("Starting mass import process...");



        logger.info("Copying mass data into the DB.");
        final Long protocolImportId = protocolImportDAO.executeImportProcedure(protocolImportHeader, sessionUserService.getSessionUserId());
        logger.info("Mass data successfully copied to the DB. ID of the created protocol import header : "+protocolImportId);

        protocolImportHeader.setId(protocolImportId);

        //user id and language are used in the task thread but have to be obtained in the request thread
        final Long userLanguageId = i18NService.currentLanguageId();
        final Long sessionUserId = sessionUserService.getSessionUserId();

        logger.info("Adding new asynchronous task to the async executor service for the validation of the mass data.");
        Long taskStatusId = executorService.submitTask(new AsyncTaskExecutorService.CallableTask() {

            private Long id;

            @Override
            public Object call() throws Exception {
                try {
                    logger.info("Starting the validation of the mass data for import ID {}", protocolImportId);
                    List<Message> messages = protocolImportDAO.executeValidateProtocolProcedure(protocolImportId, userLanguageId, sessionUserId, this.id);
                    logger.info("The validation of the mass data for import ID {} terminated normally. Now returning the listAndTranslate of messages.", protocolImportId);
                    return messages;
                } catch (Exception e) {
                    logger.error("Error in mass import asynchronous task :", e);
                    throw e;
                }
            }

            @Override
            public Long getId() {
                return id;
            }

            @Override
            public void setId(Long id) {
                this.id = id;
            }
        });

        logger.info("Successfully added asynchronous task with ID : "+taskStatusId);
        ongoingImports.put(taskStatusId, protocolImportHeader);
        return taskStatusId;
    }
     */


    @Override
    @Transactional
    public Long asyncImportMassDataAndExcelFile(ProtocolImportHeader protocolImportHeader){
        logger.info("Starting mass import process...");

        // insert the metadata of the file in PROTOCOLIMPORTHEADER table
        protocolImportHeader.setStatus(EntityStatus.ACTIVE);
        protocolImportHeaderDAO.persist(protocolImportHeader);

        // get the IPH_ID
        Long protocolImportId = protocolImportHeader.getId();;
        logger.info("Mass import protocolImportHeaderId:"+protocolImportId);

        Sheet excelSheet = excelParserService.getExcelSheet(protocolImportHeader);


        List<ImportMassDataDetail>  massImports = excelParserService.parseExcelSheetForMass(protocolImportHeader,excelSheet);
        // loop over the labProtocolImports and persist them
        massImports.stream().forEach(p->{
            importMassDataDetailDAO.persist(p);
        });


        protocolImportHeader.setId(protocolImportId);

        //user id and language are used in the task thread but have to be obtained in the request thread
        final Long userLanguageId = i18NService.currentLanguageId();
        final Long sessionUserId = sessionUserService.getSessionUserId();

        logger.info("Adding new asynchronous task to the async executor service for the validation of the mass data.");
        Long taskStatusId = executorService.submitTask(new AsyncTaskExecutorService.CallableTask() {

            private Long id;

            @Override
            public Object call() throws Exception {
                try {
                    logger.info("Starting the validation of the mass data for import ID {}", protocolImportId);
                    List<Message> messages = protocolImportDAO.executeValidateProtocolProcedure(protocolImportId, userLanguageId, sessionUserId, this.id);
                    logger.info("The validation of the mass data for import ID {} terminated normally. Now returning the listAndTranslate of messages.", protocolImportId);
                    return messages;
                } catch (Exception e) {
                    logger.error("Error in mass import asynchronous task :", e);
                    throw e;
                }
            }

            @Override
            public Long getId() {
                return id;
            }

            @Override
            public void setId(Long id) {
                this.id = id;
            }
        });

        logger.info("Successfully added asynchronous task with ID : "+taskStatusId);
        ongoingImports.put(taskStatusId, protocolImportHeader);
        return taskStatusId;
    }

    @Override
    public ProtocolImportHeader getHeaderForTask(Long taskId){
        ProtocolImportHeader header = ongoingImports.get(taskId);
        if(header == null){
            throw new IllegalStateException("No import header stored for task id "+taskId);
        }
        ongoingImports.remove(taskId);
        return header;
    }

    @Override
    public List<Message> getMassImportTaskResult(Long taskId){
        return (List<Message>) executorService.getTaskResult(taskId);
    }

    @Override
    @Transactional
    public Long confirmImportProtocol(Long importProtocolHeaderId, boolean publish, boolean makePublic){
        return protocolImportDAO.executeConfirmProtocolProcedure(importProtocolHeaderId, i18NService.currentLanguageId(), sessionUserService.getSessionUserId(), publish, makePublic);
    }

    @Override
    @Transactional()
    public ProtocolImportHeader buildProtocolImportForAdditionalDataBasedOnParent(Long parentSampleId) {
        Sample parent = sampleDAO.findById(parentSampleId);

        if(parent == null){
            throw new IllegalArgumentException("Invalid protocol id : "+ parentSampleId);
        }

        ProtocolImportHeader additionalDataImport = new ProtocolImportHeader();
        additionalDataImport.setSphId(parentSampleId);
        additionalDataImport.setParentId(parent.getSampleIphId());
        additionalDataImport.setAnalysisDate(parent.getAnalysisDate());
        //additionalDataImport.setLanguageId(parent.getOriginalImport().getLanguageId());

        return additionalDataImport;
    }

    @Override
    @Transactional
    public Map<String, Object> getParentInfo(Long sampleId){
        Sample sample = sampleDAO.findById(sampleId);

        Map<String, Object> parentInfo = new HashMap<>();

        Long langCode = null;
        parentInfo.put("sample", sample);

        logger.info("getParentInfo: sample.getSampleMassOriginalFile() is :"+sample.getSampleMassOriginalFile());

        if(sample.getSampleMassOriginalFile() !=null){
            langCode= sample.getSampleMassOriginalFile().getLanguage().getId();
            parentInfo.put("massImport", true);
            if( sample.getSampleMassOriginalFile() !=null){
                parentInfo.put("labProtocolMassFile", sample.getSampleMassOriginalFile());
            }
            logger.info("getParentInfo: SampleMassOriginalFile() - langCode is :"+langCode);
        }else{
            langCode= sample.getOriginalFiles().iterator().next().getLanguage().getId();
            parentInfo.put("massImport", false);
            logger.info("getParentInfo: getOriginalFiles() - langCode is :"+langCode);
        }

        parentInfo.put("watercourse", stationDAO.getWatercourseForStation(sample.getStation().getId(), langCode));

        //determine the authorship for the different protocols and operations (operator, determinator)
        for(SampleAttribute source : sample.getAttributes()){
            //Operator for lab protocol
            if(source.getProtocolType().getCode().equals(ThesaurusCodes.MIDATPROTO_LABORATORY) && source.getSourceType() == SampleAttributeSource.PERSON_TABLE
                    && source.getAttributeType().getCode().equals(ThesaurusCodes.MIDATHDITMTY_OPERATOR)){
                parentInfo.put("labProtocolInfoOperator", source);
                continue;
            }

            //Determinator for lab protocol
            if(source.getProtocolType().getCode().equals(ThesaurusCodes.MIDATPROTO_LABORATORY) && source.getSourceType() == SampleAttributeSource.PERSON_TABLE
                    && source.getAttributeType().getCode().equals(ThesaurusCodes.MIDATHDITMTY_DETERMINATOR)){
                parentInfo.put("labProtocolInfoDeterminator", source);
                continue;
            }

            //Operator for eval grid if any
            if(source.getProtocolType().getCode().equals(ThesaurusCodes.MIDATPROTO_GRDEVAL)){
                parentInfo.put("evaluationGridInfo", source);
                continue;
            }

            //Operator for field protocol if any
            if(source.getProtocolType().getCode().equals(ThesaurusCodes.MIDATPROTO_GROUND)){
                parentInfo.put("fieldProtocolInfo", source);
                continue;
            }
        }

        //load the original files
        for(SampleOriginalFile file : sample.getOriginalFiles()){

            if(file.getProtocolVersion().getProtocolType().getCode().equals(ThesaurusCodes.MIDATPROTO_LABORATORY)){
                parentInfo.put("labProtocolFile", file);
                continue;
            }

            if(file.getProtocolVersion().getProtocolType().getCode().equals(ThesaurusCodes.MIDATPROTO_GRDEVAL)){
                parentInfo.put("evaluationGridFile", file);
                continue;
            }

            if(file.getProtocolVersion().getProtocolType().getCode().equals(ThesaurusCodes.MIDATPROTO_GROUND)){
                parentInfo.put("fieldProtocolFile", file);
                continue;
            }
        }

        return parentInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<MIDATProtocolVersion> findVersionsForProtocolType(Long protocolTypeId){

        List<MIDATProtocolVersion> versions = protocolVersionDAO.findForType(protocolTypeId);
        translator.fillLocalizedFields(versions);
        return versions;
    }

    @Override
    @Transactional
    public void addCommentsToSample(Long sampleId, Set<Long> commentIds, Boolean commentOtherChk, String commentOther) {

        Sample sample = sampleDAO.findById(sampleId);
        //make sure not null
        if(commentIds !=null){
            for(Long commentId:commentIds){
                SampleComment sampleComment = new SampleComment();
                sampleComment.setSample(sample);
                sampleComment.setStatus(EntityStatus.ACTIVE);
                sampleComment.setSampleCommentId(commentId);
                sampleCommentDAO.persist(sampleComment);
            }
        }


        if(commentOtherChk){
            sample.setSampleCommentOther(commentOther);
        }
        sampleDAO.merge(sample);


    }


}
