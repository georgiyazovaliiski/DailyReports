package models.DBModels;

public class Company extends BaseEntity {
    private String company_name;

    private String company_prefix;

    public Company(String name) {
        this.company_name = name;
    }

    public String getCompanyPrefix() {
        return company_prefix;
    }

    public void setCompanyPrefix(String companyPrefix) {
        company_prefix = companyPrefix;
    }

    public String getName() {
        return company_name;
    }

    public void setName(String name) {
        company_name = name;
    }
}
