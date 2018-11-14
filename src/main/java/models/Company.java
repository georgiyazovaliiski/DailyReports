package models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

public class Company {
    private Cities Cities;

    public models.Cities getCities() {
        return Cities;
    }

    public void setCities(models.Cities cities) {
        Cities = cities;
    }
}
