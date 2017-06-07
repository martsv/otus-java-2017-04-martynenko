package ru.otus.hw9;

import ru.otus.hw9.executor.ConnectionHelper;
import ru.otus.hw9.executor.Executor;
import ru.otus.hw9.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        Connection connection = ConnectionHelper.getConnection();
        Executor executor = new Executor(connection);

        try {
            executor.execUpdate("create table users (id bigserial not null, name varchar(255), age integer not null default 0, primary key (id))");
            System.out.println("Table created");
            executor.execUpdate("insert into users (name, age) values ('gates', 30)");
            System.out.println("User added");

            User user = executor.load(1, User.class);
            System.out.println(user);

            user.setName("bill");
            user.setAge(25);
            executor.save(user);

            User newUser = executor.load(1, User.class);
            System.out.println(newUser);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                executor.execUpdate("drop table users");
                System.out.println("Done!");

                connection.close();
            } catch (Exception err) {
                System.err.println(err.getMessage());
            }
        }

    }
}
