package models.reportcomponents;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

public class Company {
    private String Name;

    private String CompanyPrefix;

    public Company(String name, String companyPrefix) {
        this.Name = name;
        this.CompanyPrefix = companyPrefix;
    }

    public String getCompanyPrefix() {
        return CompanyPrefix;
    }

    public void setCompanyPrefix(String companyPrefix) {
        CompanyPrefix = companyPrefix;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
