package excelserialization;

import models.DBModels.Report;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelWriter {

    private static String FILE_NAME;

    public static void write(List<ReportPOJO> reports) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date d = new Date();
        FILE_NAME = "excel-report-" + dateFormat.format(d) + ".xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
        String[] datatypes = {
                "Id","Date","Company Name","City Name","Department Name","Employee Name","Turnover"
        };
        
        int rowNum = 0;
        boolean inputDepartment = true;
        Row rowBase = sheet.createRow(rowNum++);
        int colNum = 0;
        for (String datatype : datatypes) {
            if(datatype.equals("Department Name"))
                if(reports.get(0).getDepartmentName().equals("") || reports.get(0).getDepartmentName() == null)
                {
                    inputDepartment = false;
                    continue;
                }
            Cell baseCell = rowBase.createCell(colNum++);
            baseCell.setCellValue(datatype);

            XSSFCellStyle style = workbook.createCellStyle();
            //APPLY STYLE!
        }

        for (ReportPOJO report : reports) {
            Row row = sheet.createRow(rowNum++);
            colNum = 0;

            Cell cellId = row.createCell(colNum++);
            cellId.setCellValue(report.getId());            //ID
            Cell cellDate = row.createCell(colNum++);
            cellDate.setCellValue(report.getDate().toString()); // DATE
            Cell cellCompanyName = row.createCell(colNum++);
            cellCompanyName.setCellValue(report.getCompanyName());   //COMPANY NAME
            Cell cellCityName = row.createCell(colNum++);
            cellCityName.setCellValue(report.getCityName());    //CITY NAME
            if(inputDepartment) {
                Cell cellDep = row.createCell(colNum++);
                cellDep.setCellValue(report.getDepartmentName());   // DEPARTMENT NAME
            }
            Cell cellEmployee = row.createCell(colNum++);
            cellEmployee.setCellValue(report.getEmployeeName()); // EMPLOYEE NAME
            Cell cellTurnover = row.createCell(colNum++);
            cellTurnover.setCellValue(report.getTurnover()); // TURNOVER
        }

        //System.out.println("Creating excel");

        try {
            FileOutputStream outputStream = new FileOutputStream(new File(FILE_NAME));
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println("Done");
    }
}