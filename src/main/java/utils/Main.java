package utils;

import javax.xml.bind.JAXBException;
import java.beans.PropertyVetoException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws JAXBException, IOException, SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, PropertyVetoException {
        Engine en = new Engine();
        en.Run(args);
    }
}