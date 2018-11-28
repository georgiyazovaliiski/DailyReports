package repositories.baserepositories;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    Optional<T> get(int id) throws SQLException,NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchFieldException;

    Optional<List<T>> get() throws SQLException,NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchFieldException;

    Optional<Integer> insert(T Entity)throws SQLException,NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchFieldException;

    void update(T Entity) throws SQLException,NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchFieldException;

    void delete(int id) throws SQLException,NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchFieldException;

    void setConnection(Connection con);
}
