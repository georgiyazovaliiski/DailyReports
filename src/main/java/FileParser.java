import models.DBModels.Report;
import models.reportcomponents.Company;
import models.reports.BaseDailyReport;
import parsers.CSVReportParser;
import parsers.XMLReportParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {
    public List<BaseDailyReport> ParseList(String FilePath) throws JAXBException, IOException {
        File folder = new File(FilePath);
        File[] listOfFiles = folder.listFiles();

        List<BaseDailyReport> reports = new ArrayList<>();
        if(listOfFiles == null){
            File file = folder;
            return parseSingle(file,reports);
        }

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                reports = parseSingle(listOfFiles[i],reports);
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        return reports;
    }
    public List<BaseDailyReport> parseSingle(File file, List<BaseDailyReport> reports) throws JAXBException, IOException {
        String FileName = file.getName();
        String Extension = FileName.split("\\.")[1];
        Pattern pattern = Pattern.compile("^(([0-9]+-){2}[0-9]+-.+\\.[a-zA-Z]+)$");
        Pattern patternForDates = Pattern.compile("([0-9]+-){2}([0-9]+){2}");

        Matcher matcherForDate = patternForDates.matcher(FileName);

        Matcher matcher = pattern.matcher(FileName);
        while (matcher.find()) {
            if(matcher.group()!=null){
                while (matcherForDate.find()) {
                    String[] params = FileName.split("\\.")[0].split("-");
                    String CompanyName = params[params.length-1];
                    String Date = matcherForDate.group();

                    BaseDailyReport dailyReport;
                    TableChecker checker = new TableChecker();

                    if(Extension.equals("xml")){ //If the file is .XML
                        XMLReportParser parser = new XMLReportParser();
                        dailyReport = parser.Report(FileName);
                        dailyReport.setCompany(new Company(CompanyName,CompanyName));
                        dailyReport.setDate(LocalDate.parse(Date));
                        reports.add(dailyReport);
                    }
                    else if(Extension.equals("csv")){ //If the file is .CSV
                        CSVReportParser parser = new CSVReportParser();
                        dailyReport = parser.Report(FileName);
                        dailyReport.setCompany(new Company(CompanyName,CompanyName));
                        dailyReport.setDate(LocalDate.parse(Date));
                        reports.add(dailyReport);
                    }
                }
                break;
            }
        }
        return reports;
        //System.out.println("Extension " + Extension);
    }
}
