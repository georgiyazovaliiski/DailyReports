package parsers;

import models.reportcomponents.Cities;
import models.reportcomponents.City;
import models.reportcomponents.Department;
import models.reports.BaseDailyReport;
import models.reports.XMLDailyReport;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLReportParser extends BaseDailyReport{
    public BaseDailyReport Report(String FileName) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Cities.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        Cities cities1 = (Cities) unmarshaller.unmarshal(new File("daily-reports/" + FileName));
        XMLDailyReport report = new XMLDailyReport();
        report.setCities(cities1);

        return report;

        // TESTING OUTPUT AND DATA GATHERING
        /*for (City city : cities1.getCities()) {
            System.out.println(city.getName());
            for (Department departmentToDisplay : city.getDepartments()) {
                System.out.println(departmentToDisplay.getDepartmentName());
                System.out.println(departmentToDisplay.getFullName());
                System.out.println(departmentToDisplay.getTurnOver());
            }
        }*/
    }
}
