package repositories;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    Optional<T> get(int id) throws SQLException,NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchFieldException;

    Optional<List<T>> get() throws SQLException, IOException, ClassNotFoundException;

    Optional<Integer> insert(T Entity) throws SQLException, IOException, ClassNotFoundException, IllegalAccessException, NoSuchMethodException;

    void update(T Entity) throws SQLException, IOException, ClassNotFoundException;

    void delete(int id) throws SQLException, IOException, ClassNotFoundException;
}
