package models.DBModels;

import annotation.Table;

@Table(name = "companies")
public class Company extends BaseEntity {
    private String company_name;

    private String company_prefix;

    public Company(){

    }

    public Company(String name, String company_prefix) {
        this.company_name = name;
        this.company_prefix = company_prefix;
    }

    public Company(int id, String name, String company_prefix) {
        super(id);
        this.company_name = name;
        this.company_prefix = company_prefix;
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
