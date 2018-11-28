package repositories.baserepositories;

import annotation.Table;
import connectionResources.Connector;
import connectionResources.QueryBuilder;

import java.beans.PropertyVetoException;
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
    protected Connection con;
    protected QueryBuilder builder;
    protected Class<T> typeParameterClass;
    protected String tableName;


    public RepositoryBase() throws SQLException, NoSuchMethodException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.connector = new Connector();
        this.connector.createConnection();
        this.con = connector.getConnection();
        this.builder = new QueryBuilder();
        this.typeParameterClass = (Class<T>) ((Class)((ParameterizedType)this.getClass().
                getGenericSuperclass()).getActualTypeArguments()[0]);
        this.tableName = this.typeParameterClass.getAnnotation(Table.class).name();
        //System.out.println("FINAL: " + this.tableName);
    }

    public void setConnection(Connection con){
        this.con = con;
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

    public Optional<T> get(int id) throws SQLException, NoSuchMethodException, IllegalAccessException, IOException, ClassNotFoundException, InstantiationException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
        String tableName = this.tableName;
        QueryInfo query = builder.select(new String[]{"*"},tableName,"Id");
        //System.out.println(query.getQuery());
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
        QueryInfo query = builder.select(new String[]{"*"},tableName);

        System.out.println(query.getQuery());
        return null;
    }

    public Optional<Integer> insert(T Entity) throws SQLException, IOException, ClassNotFoundException, IllegalAccessException {


        List<Field> fields = getClassFields(new ArrayList<Field>(),Entity.getClass());

        System.out.println("FIELD LIST SIZE: " + fields.size());

        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            field.setAccessible(true);
            if(field.getName().contains("department")){
                System.out.println("FIELDCHE:" + field.get(Entity).toString());
                if(field.get(Entity).toString().equals("0") || field.get(Entity).toString().equals(0)){
                    fields.remove(field);
                    i--;
                }
            }
        }
        System.out.println("FIELD LIST SIZE AFTER: " + fields.size());

        QueryInfo query = builder.insert(
                this.tableName,
                fields
        );

        PreparedStatement stmnt = con.prepareStatement(query.getQuery(),
                Statement.RETURN_GENERATED_KEYS);

        int counter = 1;
        for (Field field : Entity.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            field.setAccessible(true);
            if(fields.contains(field)) {
                stmnt.setString(counter, field.get(Entity).toString());
                counter++;
            }
        }
        System.out.println("TUK! : " + stmnt);
        boolean executed = stmnt.execute();

        ResultSet rs = stmnt.getGeneratedKeys();

        int Id = 0;
        if(rs.next()){
            Id = rs.getInt(1);
        }

        Optional result = Optional.ofNullable(Id);
        return result;
    }

    public void update(T Entity) throws SQLException, NoSuchFieldException, IOException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        List<Field> fields = new ArrayList<>();
        getClassFields(fields,typeParameterClass);
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        String[] names = new String[fieldNames.size()];
        fieldNames.toArray(names);
        QueryInfo query = this.builder.update(names,this.tableName,"id");

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
            //System.out.println("Updated successfully!");
        };
    }

    public void delete(int id) throws SQLException, IOException, ClassNotFoundException{
        System.out.println("Not implemented yet");
    }

    protected List<Field> getClassFields(List<Field> fields, Class<?> tClass) {
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