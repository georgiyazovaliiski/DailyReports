import models.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        Cities cities = new Cities();

        City City1 = new City();
        City City2 = new City();

        List<Department> departments = new ArrayList<Department>();
        List<Department> departments2 = new ArrayList<Department>();
        Department department = new Department();
        department.setDepartmentName("Games");
        department.setFullName("Georgi Yazovaliyski");
        department.setTurnOver(3.4566);
        departments.add(department);
        departments2.add(department);

        City1.setDepartments(departments);
        City1.setName("New1");
        City2.setDepartments(departments2);
        City2.setName("New2");

        List<City> citiesList = new ArrayList<City>();
        citiesList.add(City1);
        citiesList.add(City2);
        cities.setCities(citiesList);
/*
        SimpleClass simple = new SimpleClass();

        simple.setName("Gotovo");*/
/*
        JAXBContext context = JAXBContext.newInstance(Cities.class);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(cities, new File("cities.xml"));*/

        JAXBContext jaxbContext = JAXBContext.newInstance(Cities.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        Cities cities1 = (Cities) unmarshaller.unmarshal(new File("2018-01-01-agivu.xml"));

        for (City city : cities1.getCities()) {
            System.out.println(city.getName());
            for (Department departmentToDisplay : city.getDepartments()) {
                System.out.println(departmentToDisplay.getDepartmentName());
                System.out.println(departmentToDisplay.getFullName());
                System.out.println(departmentToDisplay.getTurnOver());
            }
        }
    }
}