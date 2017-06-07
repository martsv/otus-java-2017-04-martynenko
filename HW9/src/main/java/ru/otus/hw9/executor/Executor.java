package ru.otus.hw9.executor;

import ru.otus.hw9.model.DataSet;
import ru.otus.hw9.model.Model;
import ru.otus.hw9.model.ModelField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class Executor {

    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public <T extends DataSet> void save(T dataSet) throws SQLException {
        Class<? extends DataSet> clazz = dataSet.getClass();

        Model model = Model.loadModel(clazz);

        String update = "update " + model.getTableName() + " set ";
        String fields = model.getModelFields().stream()
                .map(modelField -> modelField.getDbFieldName() + " = " + modelField.getValue(dataSet))
                .collect(Collectors.joining(", "));
        String where = " where id = " + model.getIdField().getValue(dataSet);

        execUpdate(update + fields + where);
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        Model model = Model.loadModel(clazz);

        T data = model.getInstance();
        data.setId(id);

        String sql = "select * from " + model.getTableName() + " where id = " + id;
        execQuery(sql,
                result -> {
                    result.next();
                    for (ModelField modelField: model.getModelFields()) {
                        modelField.setValue(data, result.getObject(modelField.getDbFieldName()));
                    }
                });

        return data;
    }

    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            return stmt.getUpdateCount();
        }
    }

    public void execQuery(String query, ResultHandler handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            handler.handle(result);
        }
    }

}
