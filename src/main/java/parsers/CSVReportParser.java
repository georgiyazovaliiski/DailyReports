package parsers;

import models.reportcomponents.Employee;
import models.reports.BaseDailyReport;
import models.reports.CSVDailyReport;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVReportParser extends BaseReportParser {
    public BaseDailyReport Report(String FileName) throws IOException {

        CSVDailyReport csvDailyReport = new CSVDailyReport();
        List<Employee> employees = new ArrayList<>();
        try (
                //new File(".."+File.separator+".."+File.separator+"daily-reports"+File.separator+FileName)
                Reader reader = Files.newBufferedReader(Paths.get(".."+ File.separator+".."+File.separator+"daily-reports"+File.separator+FileName));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim());
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by Header names
                String city = csvRecord.get("city");
                String employee = csvRecord.get("employee");
                String turnover = csvRecord.get("turnover");
                employees.add(new Employee(city,employee,turnover));
            }
        }

        csvDailyReport.setEmployees(employees);
        return csvDailyReport;
    }
}
