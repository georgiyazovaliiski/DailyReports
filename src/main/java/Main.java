
import parsers.XMLReportParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.beans.PropertyVetoException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws JAXBException, IOException, SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, PropertyVetoException {
        Engine en = new Engine();
        en.Run(args);
    }
}