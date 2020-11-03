package ch.cscf.jeci.utils;


import com.google.common.collect.Sets;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by aka on 11/01/17.
 */

public class ExcelWriter {

    private Collection<String> gridSheetColumns;
    private Collection<String> fuanaSheetColumns;
    private Map<String, String> headerMapping;
    private List<Map<String, Object>> data;
    private OutputStream outStream;

    private String GRID = "grid";
    private String FAUNA = "fauna";

    private Map<String, CellStyle> styles;
    private String[] titles; // worksheet title rows
    private String pageCode;
    private int rownum;
    private boolean isFull;


    public ExcelWriter(Collection<String> gridSheetColumns,
                       Collection<String> fuanaSheetColumns,
                       Map<String, String> headerMapping,
                       String pageCode,
                       List<Map<String, Object>> data,
                       boolean isFull,
                       OutputStream outStream) {

        this.gridSheetColumns = gridSheetColumns;
        this.fuanaSheetColumns = fuanaSheetColumns;
        this.headerMapping = headerMapping;
        this.data = data;
        this.outStream = outStream;
        this.pageCode = pageCode;
        this.isFull = isFull;
    }

    /**
     * helper method to convert string-double to integer
     */
    private static String convertDoubleStringToIntegerString(String val) {
        try {
            double newVal = Math.round(Double.valueOf(val));
            Number n = (Number) newVal;
            return String.valueOf(n.intValue());
        } catch (NumberFormatException e) {
            return "";
        }
    }

    /**
     * helper method to convert string-double to docuble with one deci
     */
    private static String convertDoubleStringToDoubleStringWithOneDeci(String val) {
        double newVal = Math.round(Double.valueOf(val) * 10) / 10.0;
        return String.valueOf(newVal);
    }

    /**
     * Helper method to check if a given value is numeric or not
     */
    private static boolean isNumeric(String str) {
        return str.replaceFirst("\\.", "").chars().allMatch(Character::isDigit);
    }

    /**
     * Create a library of cell styles
     */
    static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style;
        DataFormat dateDataFormat = wb.createDataFormat();

        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 14);
        titleFont.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        titleFont.setFontHeightInPoints((short) 14);
        titleFont.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(titleFont);
        style.setDataFormat(dateDataFormat.getFormat("dd.mm.yyyy  HH:mm:ss.SSS"));
        styles.put("titleDate", style);

        Font headerFont = wb.createFont();
        headerFont.setFontHeightInPoints((short) 11);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cellNumeric", style);

        return styles;
    }

    public void writeToOutput() throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        styles = createStyles(wb);

        Sheet sheetGrid = wb.createSheet(pageCode + "- Grid");
        writeSheet(sheetGrid, GRID);

        // if full export is requested add the fauna sheet
        if(this.isFull){
            Sheet sheetFauna = wb.createSheet(pageCode + "- Faune");
            writeSheet(sheetFauna, FAUNA);

        }

        //deliver the excel
        wb.write(outStream);
        wb.close();
    }

    private void writeSheet(Sheet sheet, String type) throws IOException {

        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(35);

        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellStyle(styles.get("title"));
        titleCell.setCellValue(headerMapping.get("sample.sampleDate"));

        Cell titleCellDate = titleRow.createCell(1);
        titleCellDate.setCellStyle(styles.get("titleDate"));
        titleCellDate.setCellValue(new Date());

        //header row
        writeHeaders(sheet, type);

        //data rows
        rownum = 2;
        writeDataRows(sheet, type);

    }

    private void getTitles(Collection<String> columns) {
        List<String> headers = new ArrayList<>(columns.size());
        if (headerMapping == null) {
            for (String col : columns) {
                headers.add(col);
            }
        } else {
            for (String col : columns) {
                headers.add(headerMapping.get(col));
            }
        }
        titles = headers.toArray(new String[headers.size()]);
    }

    private void writeHeaders(Sheet sheet, String type) throws IOException {
        getTitles(type.equalsIgnoreCase(GRID) ? gridSheetColumns : fuanaSheetColumns);
        //header row
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < titles.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
            headerCell.setCellStyle(styles.get("header"));
            sheet.autoSizeColumn(i);
        }
    }

    private void writeDataRows(Sheet sheet, String type) {
        Set<String> sampleIds = Sets.newHashSet();
        Collection<String> columns = type.equalsIgnoreCase(GRID) ? gridSheetColumns : fuanaSheetColumns;

        List columnsList = new ArrayList(columns);

        for (Map<String, Object> row : data) {
            String idStr = String.valueOf(row.get("sample.id"));
            if (type.equalsIgnoreCase(GRID) && sampleIds.contains(idStr)) continue;
            sampleIds.add(idStr);
            List<Object> values = new ArrayList<Object>(type.equalsIgnoreCase(GRID) ? gridSheetColumns.size() : fuanaSheetColumns.size());
            for (String colName : columns) {
                values.add(row.get(colName));
            }
            Row r = sheet.createRow(rownum++);
            for (int j = 0; j < titles.length; j++) {
                Cell cell = r.createCell(j);
                String cellValue = String.valueOf(values.get(j));

                cell.setCellStyle(isNumeric(cellValue) ? styles.get("cellNumeric") : styles.get("cell"));
                String column = (String) columnsList.get(j);

                if (column.equalsIgnoreCase("sampleStation.coordinates.x") ||
                        column.equalsIgnoreCase("sampleStation.coordinates.y") ||
                        column.equalsIgnoreCase("sampleStation.coordinates.z")) {
                    cellValue = convertDoubleStringToIntegerString(cellValue);
                }

                cell.setCellValue(cellValue.equalsIgnoreCase("null") ? " " : cellValue);
            }
        }

    }
}

