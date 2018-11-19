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
    public List<BaseDailyReport> ParseList() throws JAXBException, IOException {
        File folder = new File("daily-reports/");
        File[] listOfFiles = folder.listFiles();
        List<BaseDailyReport> reports = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String FileName = listOfFiles[i].getName();
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

                //System.out.println("Extension " + Extension);
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        return reports;
    }
}