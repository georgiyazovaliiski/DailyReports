package repositories;

import annotation.Table;
import connectionResources.Connector;
import connectionResources.QueryBuilder;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import connectionResources.QueryInfo;
import models.DBModels.BaseEntity;

public abstract class RepositoryBase<T>{
    private Connector connector;
    private Connection con;
    private QueryBuilder builder;
    private Class<T> typeParameterClass;


    public RepositoryBase() throws SQLException, NoSuchMethodException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.connector = new Connector();
        this.connector.createConnection();
        this.con = connector.getConnection();
        this.builder = new QueryBuilder();
        this.typeParameterClass = (Class<T>) ((Class)((ParameterizedType)this.getClass().
                getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    protected Connector connector() throws SQLException, IOException, ClassNotFoundException {
        if(this.connector == null){
            connector = new Connector();
            this.connector.createConnection();
            con = connector.getConnection();
            return this.connector;
        }
        else {
            return this.connector;
        }
    }

    /*public void Add(T Entity){
        QueryInfo query = builder.insert(Entity.getClass().getAnnotation(Table.class).value(), getClassFields(new ArrayList<Field>(),Entity.getClass()));
        System.out.println(query.getQuery());
    }*/

    public Optional<T> get(int id) throws SQLException, NoSuchMethodException, IllegalAccessException, IOException, ClassNotFoundException, InstantiationException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
        String tableName = typeParameterClass.getAnnotation(Table.class).name();
        QueryInfo query = builder.select(new String[]{"*"},tableName,"Id");

        PreparedStatement stmnt = con.prepareStatement(query.getQuery(),
                Statement.RETURN_GENERATED_KEYS);

        stmnt.setInt(1,id);

        ResultSet rs = stmnt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();


        T Entity = instantiateClass();
        //System.out.println("TUK: " + Entity);
        if(rs.next()){
            List<Field> fields = getClassFields(new ArrayList<Field>(),typeParameterClass);

            for (Field field : fields) {
                field.setAccessible(true);
                field.set(Entity, rs.getObject(field.getName(), field.getType()));
            }
        }
        return Optional.ofNullable(Entity);
    }

    public Optional<List<T>> get() throws SQLException, IOException, ClassNotFoundException{
        System.out.println("Not implemented yet");
        return null;
    }

    public Optional<Integer> insert(T Entity) throws SQLException, IOException, ClassNotFoundException, IllegalAccessException {
        QueryInfo query = builder.insert(Entity.getClass().getAnnotation(Table.class).name(), getClassFields(new ArrayList<Field>(),Entity.getClass()));

        PreparedStatement stmnt = con.prepareStatement(query.getQuery(),
                Statement.RETURN_GENERATED_KEYS);

        int counter = 1;
        for (Field field : Entity.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            field.setAccessible(true);
            stmnt.setString(counter,field.get(Entity).toString());
            counter++;
        }
        boolean executed = stmnt.execute();

        ResultSet rs = stmnt.getGeneratedKeys();

        int Id = 0;
        if(rs.next()){
            Id = rs.getInt(1);
        }

        Optional result = Optional.ofNullable(Id);
        return result;
    }

    public void update(T Entity) throws SQLException, IOException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        List<Field> fields = new ArrayList<>();
        getClassFields(fields,typeParameterClass);
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        String[] names = new String[fieldNames.size()];
        fieldNames.toArray(names);
        QueryInfo query = this.builder.update(names,Entity.getClass().getAnnotation(Table.class).name(),"id");

        PreparedStatement stmnt = con.prepareStatement(query.getQuery(),
                Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = stmnt.getGeneratedKeys();

        int counter = 1;
        for (Field field : fields) {
            String fieldName = field.getName();
            field.setAccessible(true);
            //System.out.println(field.getName() + " : " + field.get(Entity));
            stmnt.setString(counter,field.get(Entity).toString());
            counter++;
        }

        Field idField = fields.get(fields.size() - 1);
        //System.out.println(idField);

        stmnt.setInt(counter, (Integer) idField.get(Entity));

        //System.out.println(stmnt);

        if(stmnt.execute()){
            System.out.println("Updated successfully!");
        };
    }

    public void delete(int id) throws SQLException, IOException, ClassNotFoundException{
        System.out.println("Not implemented yet");
    }

    private List<Field> getClassFields(List<Field> fields, Class<?> tClass) {
        fields.addAll(Arrays.asList(tClass.getDeclaredFields()));

        if (tClass.getSuperclass() != null)
            getClassFields(fields, tClass.getSuperclass());

        return fields;
    }

    private T instantiateClass() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Class<?> someClass = Class.forName(typeParameterClass.getName());
        Class<? extends BaseEntity> creatorClass = someClass.asSubclass(BaseEntity.class);
        Constructor<? extends BaseEntity> creatorCtor = creatorClass.getConstructor();
        T Entity = (T) creatorCtor.newInstance((Object[]) null);
        return Entity;
    }
}