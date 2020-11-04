package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.domain.entities.base.EntityStatus;
import ch.cscf.jeci.domain.entities.midat.*;
import ch.cscf.jeci.persistence.daos.interfaces.midat.*;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: kansoa
 */
@Service
public class ExcelParserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** Mapped columns for import header **/
    static final String IPH_ABSOLUTENUMBERFLAG = "absoluteNumberFlag";
    static final String IPH_AUTRENEOZ_1 = "autreneoz_1";
    static final String IPH_AUTRENEOZ_2 = "autreneoz_2";
    static final String IPH_DETERMINATOR = "determinator";
    static final String IPH_ELEVATION = "elevation";
    static final String IPH_IBCHQ = "ibchq";
    static final String IPH_IBCHVALUE = "ibchValue";
    static final String IPH_IBCHVALUE_R = "ibchValue_r";
    static final String IPH_LOCALITY = "locality";
    static final String IPH_OBSERVATIONDATETXT = "observationDateText";
    static final String IPH_OID = "sampleStationNumber";
    static final String IPH_OPERATOR = "operator";
    static final String IPH_SOMMEABON = "sommeAbon";
    static final String IPH_SOMMEEPT = "sommePt";
    static final String IPH_SOMMENEOZ = "sommeNeoz";
    static final String IPH_SOMMETXCOR = "sommeTxcor";
    static final String IPH_SOMMETXOBS = "sommeTxobs";
    static final String IPH_SPEARVALUE = "spearValue";
    static final String IPH_STARTPOINT_X = "startPointX";
    static final String IPH_STARTPOINT_Y = "startPointY";
    static final String IPH_VALEURGI = "valeurGi";
    static final String IPH_VALEURVT = "valeurVt";
    static final String IPH_VC = "vc";
    static final String IPH_WATERCOURSE = "waterCourse";


   /** Mapped columns for Mass import **/
    static final String IMD_HIGHERTAXON = "highertaxon";
    static final String IMD_FAMILY ="family";
    static final String IMD_GENUS ="genus";
    static final String IMD_SPECIES ="species";
    static final String IMD_SUBSPECIES ="subspecies";
    static final String IMD_FREQ1 ="freq1";
    static final String IMD_FREQ2 ="freq2";
    static final String IMD_FREQLUM ="freqlum";
    static final String IMD_STADIUM ="stadium";
    static final String IMD_SAMPLINGMETHOD ="samplingmethod";
    static final String IMD_INDICETYPE ="indicetype";
    static final String IMD_PERIOD ="period";
    static final String IMD_DAY ="day";
    static final String IMD_MONTH ="month";
    static final String IMD_YEAR ="year";
    static final String IMD_WATERCOURSE ="watercourse";
    static final String IMD_LOCALITY ="locality";
    static final String IMD_CALLEDPLACE ="calledplace";
    static final String IMD_SWISSCOORD_X ="swisscoordX";
    static final String IMD_SWISSCOORD_Y ="swisscoordY";
    static final String IMD_SWISSCOORD_Z ="swisscoordZ";
    static final String IMD_OBSERVERS ="observers";
    static final String IMD_PROJECT ="project";
    static final String IMD_TAXON_DEF ="taxonDef";
    static final String IMD_CODEPRECISION ="codeprecision";
    static final String IMD_SYSTEMPRECISION="systemprecision";
    static final String IMD_SAMPLENUMBER="samplenumber";
    static final String IMD_CANTON = "canton";
    static final String IMD_OID= "oid";
    static final String IMD_PRCODE="precode";
    static final String IMD_COMMENT="comment";
    static final String IMD_REPORTURL="reporturl";
    static final String IMD_TAXONIBCH="taxonibch";
    static final String IMD_MAKROINDEXPROVIDE="makroindexprovide";
    static final String IMD_SPEARINDEXPROVIDE="spearindexprovide";
    static final String IMD_IBCHINDEXPROVIDE="ibchindexprovide";
    static final String IMD_REMARKCODE1="remarkcode1";
    static final String IMD_REMARKCODE2="remarkcode2";
    static final String IMD_REMARKCODE3="remarkcode3";
    static final String IMD_REMARKCODE4="remarkcode4";
    static final String IMD_REMARKTEXT="remarktext";
    static final String IMD_OIDLINK="oidlink";
    static final String IMD_DETERMINATOR="determinator";


    @Autowired
    private ProtocolMappingHeaderDAO protocolMappingHeaderDAO;

    @Autowired
    private LaboProtocolMappingDAO labProtocolFieldMappingDAO;

    @Autowired
    private EvaluationGridFieldMappingDAO evaluationGridFieldMappingDAO;

    @Autowired
    private GroundProtocolMappingDAO groundProtocolMappingDAO;


    @Autowired
    private ProtocolMappingMassMapDAO protocolMappingMassMapDAO;

    @Autowired
    private ProtocolMappingMassFieldDAO protocolMappingMassFieldDAO;


    private  FormulaEvaluator evaluator ;

    /*
      NB: masse file are not covered in the below method
    */

    /**
     * helper method uses reflection
     *
     * @param object
     * @param fieldName
     * @param fieldValue
     * @return
     */
    private static boolean set(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }

    /*********** IMPORTPROTOCOLHEADER *********************************************************************************/

    /**
     * Extract data from excelsheet for IMPORTPROTOCOLHEADER
     *
     * @param protocolImportHeader
     * @param excelSheet
     * @param columnIndexes
     */
    public void parseExcelSheetForImportProtocolHeader(ProtocolImportHeader protocolImportHeader,
                                                       Sheet excelSheet,
                                                       Map<String, Integer> columnIndexes) {
            List<ProtocolMappingHeader> mappingHeaders = getHeaderMappings(protocolImportHeader.getProtocolVersionId());
            setHeaderData(excelSheet, columnIndexes, mappingHeaders, protocolImportHeader);
    }

    /**
     * Helper method to set prepare data to be persisted in IMPORTPROTOCOLHEADER
     *
     * @param excelSheet
     * @param excelSheetColumnIndexes
     * @param mappingHeaders
     * @param protocolImportHeader
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void setHeaderData(Sheet excelSheet,
                               Map<String, Integer> excelSheetColumnIndexes,
                               List<ProtocolMappingHeader> mappingHeaders,
                               ProtocolImportHeader protocolImportHeader)  {
        Class<ExcelParserService> excelParserService = ExcelParserService.class;

        // loop over the mapping rules stocked in DB for this type of protocol
        mappingHeaders.stream().forEach(mh -> {
            // mh.getCellColumnValue() = A, B ..AD
            String dbCellColumnValue = mh.getCellColumnValue();

            // mh.getCellRowValue() = 0,1, 3...
            int dbCellRowIndex = Integer.valueOf(mh.getCellRowValue()).intValue() - 1;

            // return null when there is no row at the provided cellRowIndex
            Row row = excelSheet.getRow(dbCellRowIndex);

            // make sure that the cell is not null to avoid null pointer exception
            if (excelSheetColumnIndexes.get(dbCellColumnValue) != null && row !=null) {
                Cell cell = row.getCell(excelSheetColumnIndexes.get(dbCellColumnValue));
                try {
                    java.lang.reflect.Field field = excelParserService.getDeclaredField(mh.getFieldNameTo());
                    String propName = (String) field.get(excelParserService);
                    set(protocolImportHeader, propName, getCellValue(cell));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }



    /*********** IMPORTPROTOCOLLABO ***********************************************************************************/

    /**
     * Extract data from excelsheet for IMPORTPROTOCOLLABO
     *
     * @param parentId
     * @param verisonId
     * @param excelSheet
     * @param columnIndexes
     * @return
     */
    public List<LabProtocolImport> parseExcelSheetForLabProtocolImport(Long parentId,
                                                                       Long verisonId,
                                                                       Sheet excelSheet,
                                                                       Map<String, Integer> columnIndexes) {
        List<LabProtocolImport> labProtocolImports = new ArrayList<>();
        List<LabProtocolFieldMapping> mappingHeaders = getLabProtocolMappings(verisonId);
        setLabData(parentId, excelSheet, columnIndexes, mappingHeaders, labProtocolImports);
        return labProtocolImports;
    }

    /**
     * Helper method to set prepare data to be persisted in IMPORTPROTOCOLLABO
     *
     * @param parentId
     * @param excelSheet
     * @param excelSheetColumnIndexes
     * @param mappingHeaders
     * @param labProtocolImports
     */
    private void setLabData(Long parentId,
                            Sheet excelSheet,
                            Map<String, Integer> excelSheetColumnIndexes,
                            List<LabProtocolFieldMapping> mappingHeaders,
                            List<LabProtocolImport> labProtocolImports) {
        mappingHeaders.stream().forEach(mh -> {
            String dbCellColumnValue = mh.getCellColumnValue();
            // mh.getCellRowValue() = 0,1, 3...
            int dbCellRowIndex = Integer.valueOf(mh.getCellRowValue()).intValue() - 1;

            // return null when there is no row at the provided cellRowIndex
            Row row = excelSheet.getRow(dbCellRowIndex);

            // TODO check with FB why we have these values and how to handle them
            /**
            if (cellColumnValue.indexOf(":") > -1) {
                String[] cellColumnValues = cellColumnValue.split(":");
                cellColumnValue = cellColumnValues[0];
            }
             **/

            // make sure that the cell is not null to avoid null pointer exception
            if (excelSheetColumnIndexes.get(dbCellColumnValue) != null) {
                Cell cell = row.getCell(excelSheetColumnIndexes.get(dbCellColumnValue));
                LabProtocolImport labProtocolImport = new LabProtocolImport();
                String cellValue = getCellValue(cell);
                labProtocolImport.setStatus(EntityStatus.ACTIVE);
                labProtocolImport.setColumnId(mh.getId());
                labProtocolImport.setColumnValue(cellValue);
                labProtocolImport.setParentId(parentId);
                labProtocolImports.add(labProtocolImport);
            }
        });

    }


    /*********** IMPORTPROTOCOLGRID ***********************************************************************************/
    /**
     * Extract data from excelsheet for IMPORTPROTOCOLGRID
     *
     * @param parentId
     * @param verisonId
     * @param excelSheet
     * @param columnIndexes
     * @return
     */
    public List<GridProtocolImport> parseExcelSheetForGridProtocolImport(Long parentId,
                                                                         Long verisonId,
                                                                         Sheet excelSheet,
                                                                         Map<String, Integer> columnIndexes) {
        List<GridProtocolImport> gridProtocolImports = new ArrayList<>();
        List<EvaluationGridFieldMapping> mappingHeaders = getEvaluationGridFieldMappings(verisonId);
        setGridData(parentId, excelSheet, columnIndexes, mappingHeaders, gridProtocolImports);
        return gridProtocolImports;
    }

    /**
     * Helper method to set prepare data to be persisted in IMPORTPROTOCOLGRID
     *
     * @param parentId
     * @param excelSheet
     * @param excelSheetColumnIndexes
     * @param mappingHeaders
     * @param gridProtocolImports
     */
    private void setGridData(Long parentId,
                             Sheet excelSheet,
                             Map<String, Integer> excelSheetColumnIndexes,
                             List<EvaluationGridFieldMapping> mappingHeaders,
                             List<GridProtocolImport> gridProtocolImports) {
        mappingHeaders.stream().forEach(mh -> {
            String dbCellColumnValue = String.valueOf(mh.getColIndex());

            // mh.getRowIndex() = 0,1,2 ...x
            int dbCellRowIndex = mh.getRowIndex().intValue() - 1;

            // return null when there is no row at the provided cellRowIndex
            Row row = excelSheet.getRow(dbCellRowIndex);

            // make sure that the cell is not null to avoid null pointer exception
            if (excelSheetColumnIndexes.get(dbCellColumnValue) != null) {
                Cell cell = row.getCell(excelSheetColumnIndexes.get(dbCellColumnValue));
                GridProtocolImport gridProtocolImport = new GridProtocolImport();
                String cellValue = getCellValue(cell);
                gridProtocolImport.setStatus(EntityStatus.ACTIVE);
                gridProtocolImport.setColumnId(mh.getId());
                gridProtocolImport.setColumnValue(cellValue);
                gridProtocolImport.setParentId(parentId);
                gridProtocolImports.add(gridProtocolImport);
            }
        });

    }

    /*********** IMPORTPROTOCOLGRND ***********************************************************************************/

    /**
     * Extract data from excelsheet for IMPORTPROTOCOLGRND
     *
     * @param parentId
     * @param verisonId
     * @param excelSheet
     * @param columnIndexes
     * @return
     */
    public List<GroundProtocolImport> parseExcelSheetForGroundProtocolImport(Long parentId,
                                                                             Long verisonId,
                                                                             Sheet excelSheet,
                                                                             Map<String, Integer> columnIndexes) {
        List<GroundProtocolImport> groundProtocolImports = new ArrayList<>();
        List<GroundProtocolMapping> mappingHeaders = getGroundProtocolMappings(verisonId);
        setGroundData(parentId, excelSheet, columnIndexes, mappingHeaders, groundProtocolImports);
        return groundProtocolImports;
    }

    /**
     * Helper method to set prepare data to be persisted in IMPORTPROTOCOLGRND
     *
     * @param parentId
     * @param excelSheet
     * @param excelSheetColumnIndexes
     * @param mappingHeaders
     * @param groundProtocolImports
     */
    private void setGroundData(Long parentId,
                               Sheet excelSheet,
                               Map<String, Integer> excelSheetColumnIndexes,
                               List<GroundProtocolMapping> mappingHeaders,
                               List<GroundProtocolImport> groundProtocolImports) {
        mappingHeaders.stream().forEach(mh -> {
            String dbCellColumnValue = mh.getCellColumnValue();

            // mh.getCellRowValue() = 0,1, 3...
            int dbCellRowIndex = Integer.valueOf(mh.getCellRowValue()).intValue() - 1;

            // return null when there is no row at the provided cellRowIndex
            Row row = excelSheet.getRow(dbCellRowIndex);


            // make sure that the cell is not null to avoid null pointer exception
            if (excelSheetColumnIndexes.get(dbCellColumnValue) != null) {
                Cell cell = row.getCell(excelSheetColumnIndexes.get(dbCellColumnValue));
                GroundProtocolImport groundProtocolImport = new GroundProtocolImport();
                String cellValue = getCellValue(cell);

                groundProtocolImport.setStatus(EntityStatus.ACTIVE);
                groundProtocolImport.setColumnId(mh.getId());
                groundProtocolImport.setColumnValue(cellValue);
                groundProtocolImport.setParentId(parentId);
                groundProtocolImports.add(groundProtocolImport);
            }
        });

    }


    /****************************** MASS Import **********************************************************************/


    /**
     * Extract data from excelsheet for Mass import
     * @param protocolImportHeader
     * @param excelSheet
     */
    public List<ImportMassDataDetail>  parseExcelSheetForMass(ProtocolImportHeader protocolImportHeader,Sheet excelSheet) {
        Class<ExcelParserService> excelParserService = ExcelParserService.class;

        List<ProtocolMappingMassMap> mappingMassMapList = getMappingMassMap(protocolImportHeader.getLanguageId());
        List<ProtocolMappingMassField> mappingMassFieldList = getMappingMassField(protocolImportHeader.getProtocolVersionId());

        Map<String, Long> mappingMassMap = mappingMassMapList.stream()
                .collect(Collectors.toMap(ProtocolMappingMassMap::getAliasColumnName, ProtocolMappingMassMap::getMappingFieldId));

        Map<Long,String> mappingMassFieldMap = mappingMassFieldList.stream()
                .collect(Collectors.toMap(ProtocolMappingMassField::getId,ProtocolMappingMassField::getColumnName));

        List<ImportMassDataDetail> importMassDataDetailList = new ArrayList<>();


        Map<Integer,String> columnPropValues = extractExcelSheetColumnPropValues( excelSheet,  mappingMassMap ,mappingMassFieldMap);

        int noOfColumns = excelSheet.getRow(1).getLastCellNum(); // header

        // Iterating over Rows and Columns using for-each loop
        for (Row row: excelSheet) {
            // start at line 1
            if(row.getRowNum() >0) {
                // if a row is completely empty then consider this as the end of the excel
                if (isEmptyRow(row, noOfColumns)) {
                    break;
                } else {
                    ImportMassDataDetail importMassDataDetail = new ImportMassDataDetail();
                    for (int col = 0; col < noOfColumns; col++) {
                        Cell cell = row.getCell(col);
                        try {
                            String dbField = columnPropValues.get(Integer.valueOf(col));
                            if(dbField != null){
                                java.lang.reflect.Field field = excelParserService.getDeclaredField(dbField);
                                String propName = (String) field.get(excelParserService);
                                set(importMassDataDetail, propName, getCellValue(cell));
                                importMassDataDetail.setProtocolHeaderId(protocolImportHeader.getId());
                                importMassDataDetail.setExcelLineNumber(Long.valueOf(row.getRowNum()+1));
                            }
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    // add this line
                    importMassDataDetailList.add(importMassDataDetail);
                }
            }

        }
        return importMassDataDetailList;
    }


    /**
     * check if row is empty
     * @param row
     * @return
     */
    private boolean isEmptyRow (Row row, Integer noOfColumns){
        boolean isEmptyStatus = true;
        for (int col = 0; col < noOfColumns; col++) {
            Cell cell = row.getCell(col);
            String cellValue = getCellValue(cell);
            if(cellValue != null && cellValue.trim().length()>0){
                isEmptyStatus = false;
                break;
            }
        }
        return isEmptyStatus;
    }

    /**
     *
     * @param excelSheet
     * @param mappingMassMap
     * @param mappingMassFieldMap
     * @return
     */
    private Map<Integer,String> extractExcelSheetColumnPropValues(Sheet excelSheet, Map<String, Long> mappingMassMap ,Map<Long,String> mappingMassFieldMap) {
        Map<Integer,String> columnPropValues = new HashMap<>();
        int noOfColumns = excelSheet.getRow(1).getLastCellNum();
        Row row = excelSheet.getRow(0);
        for (int col = 0; col < noOfColumns; col++) {
            Cell cell = row.getCell(col);
            Long mappingFieldId = mappingMassMap.get(getCellValue(cell));
            String columnName = mappingMassFieldMap.get(mappingFieldId);
            if (cell != null && columnName !=null) {
                 columnPropValues.put(cell.getColumnIndex(), columnName);
            }
        }
        return columnPropValues;

    }


    /******************************  EXCEL Helper Methods ************************************************************/

    /**
     * get the ExcelSheet
     *
     * @param protocolImportHeader
     * @return
     */
    public Sheet getExcelSheet(ProtocolImportHeader protocolImportHeader) {
        Sheet sheet = null;
        InputStream excelFile = new ByteArrayInputStream(protocolImportHeader.getExcelFileBytes());
        try {
            Workbook workbook = WorkbookFactory.create(excelFile);
            sheet = workbook.getSheet(protocolImportHeader.getExcelSheetName());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheet;
    }

    /**
     * extractColumnIndexes
     * the purpose of this method is to map the column letter values A,B,C... AD with their column indexes
     *  A -> 0
     *  B -> 1
     *  .
     *  .
     *  AB ->23
     * @param excelSheet
     * @return
     */
    public Map<String, Integer> extractExcelSheetColumnIndexes(Sheet excelSheet) {
        Map<String, Integer> columnsIndex = new HashMap<>();
        int noOfColumns = excelSheet.getRow(0).getLastCellNum();
        Row row = excelSheet.getRow(0);
        for (int col = 0; col < noOfColumns; col++) {
            Cell cell = row.getCell(col);
            if (cell != null) {
                String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
                columnsIndex.put(columnLetter, cell.getColumnIndex());
            }
        }
        return columnsIndex;

    }




    /**
     * get the cell value from the excel
     *
     * @param cell
     * @return
     */

    private String getCellValue(Cell cell) {

        String cellValue = "";

        if(cell != null){
            switch (cell.getCellTypeEnum()) {
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case STRING:
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = targetFormat.format(cell.getDateCellValue());
                        // DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                        // Date mTimeStamp = format.parse(String.valueOf(cell.getDateCellValue()));
                        cellValue =dateString;
                    } else {
                        cellValue = replacePeriodByComma(cell);
                    }
                    break;
                case FORMULA:
                    switch (cell.getCachedFormulaResultType()) {
                        case BOOLEAN:
                            cellValue=String.valueOf(cell.getBooleanCellValue());
                            break;
                        case NUMERIC:
                            cellValue= replacePeriodByComma(cell);
                            break;
                        case STRING:
                            cellValue=String.valueOf(cell.getRichStringCellValue());
                            break;
                    }
                    break;
                default:
                    logger.info(" >>>>>>>>>> cell is empty");
            }
        }
        return cellValue;
    }

    /**
     * This is a helper method used to replace the period by a comma
     * @param cell
     * @return
     */
    private String replacePeriodByComma(Cell cell) {
        return String.valueOf(cell.getNumericCellValue()) == null
                ? null : String.valueOf(cell.getNumericCellValue()).replace('.', ',');
    }


/*
    private String getCellValue(Cell cell) {
        String stringCellValue = "";
        if(cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK){
            CellValue cellValue = getEvaluator().evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    stringCellValue =String.valueOf(cellValue.getBooleanValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = targetFormat.format(cell.getDateCellValue());
                        // DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                        // Date mTimeStamp = format.parse(String.valueOf(cell.getDateCellValue()));
                        stringCellValue =dateString;
                    } else {
                        stringCellValue = String.valueOf(cellValue.getNumberValue());
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    stringCellValue =String.valueOf(cellValue.getStringValue());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                case Cell.CELL_TYPE_ERROR:
                    break;
                // CELL_TYPE_FORMULA will never happen
                case Cell.CELL_TYPE_FORMULA:
                    break;
            }

        }


        return stringCellValue;

    }
*/

    /******************************** Mappging Methods ****************************************************************/

    /**
     * get the mapping rules for header of all protocols
     *
     * @param versionId
     * @return
     */
    private List<ProtocolMappingHeader> getHeaderMappings(Long versionId) {
        return protocolMappingHeaderDAO.findMappingByVersion(versionId)
                .stream()
                .filter(m -> m.getCellRowValue() != null && m.getCellColumnValue() != null)
                .collect(Collectors.toList());
    }

    /**
     * get the mapping rules for Labo protocol
     * filter out nullable column index values or row index values
     *
     * @param versionId
     * @return
     */
    private List<LabProtocolFieldMapping> getLabProtocolMappings(Long versionId) {
        return labProtocolFieldMappingDAO.findLaboMappingByVersion(versionId).stream()
                .filter(m -> m.getCellRowValue() != null && m.getCellColumnValue() != null)
                .collect(Collectors.toList());
    }

    /**
     * get the mapping rules for Ground protocol
     * filter out nullable column index values or row index values
     *
     * @param versionId
     * @return
     */
    private List<GroundProtocolMapping> getGroundProtocolMappings(Long versionId) {
        return groundProtocolMappingDAO.findGroundMappingByVersion(versionId).stream()
                .filter(m -> m.getCellRowValue() != null && m.getCellColumnValue() != null)
                .collect(Collectors.toList());
    }

    /**
     * get the mapping rules for Evaluation Grid protocol
     * filter out nullable column index values or row index values
     *
     * @param versionId
     * @return
     */
    private List<EvaluationGridFieldMapping> getEvaluationGridFieldMappings(Long versionId) {
        return evaluationGridFieldMappingDAO.findGridMappingByVersion(versionId).stream()
                .filter(m -> m.getRowIndex() != null && m.getColIndex() != null)
                .collect(Collectors.toList());
    }


    /**
     * Get mass mapping per language ID
     * @param langId
     * @return
     */
    private List<ProtocolMappingMassMap> getMappingMassMap(Long langId){

        return protocolMappingMassMapDAO.findMappingByLang(langId).stream()
                .filter(m -> m.getAliasColumnName() != null && m.getMappingFieldId() != null)
                .collect(Collectors.toList());
    }


    /**
     * Get db feild  columns mapping
     * @param versionId
     * @return
     */
    private List<ProtocolMappingMassField> getMappingMassField(Long versionId){
        return protocolMappingMassFieldDAO.findMappingByVersion(versionId).stream()
                .filter(m -> m.getColumnName() != null )
                .collect(Collectors.toList());
    }


    public FormulaEvaluator getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(FormulaEvaluator evaluator) {
        this.evaluator = evaluator;
    }
}

