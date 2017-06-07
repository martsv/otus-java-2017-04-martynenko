package ru.otus.hw9;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.hw9.executor.ConnectionHelper;
import ru.otus.hw9.executor.Executor;
import ru.otus.hw9.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class ExecutorTest {

    private Connection connection;
    private Executor executor;

    @Before
    public void before() throws SQLException {
        connection = ConnectionHelper.getConnection();
        executor = new Executor(connection);
        executor.execUpdate("create table users (id bigserial not null, name varchar(255), age integer not null default 0, primary key (id))");
    }

    @After
    public void after() throws SQLException {
        executor.execUpdate("drop table users");
        connection.close();
    }

    @Test
    public void testLoad() throws SQLException {
        executor.execUpdate("insert into users (name, age) values ('larry', 35)");

        User user = executor.load(1, User.class);

        Assert.assertEquals(1, user.getId());
        Assert.assertEquals("larry", user.getName());
        Assert.assertEquals(35, user.getAge());
    }

    @Test
    public void testSaveAndLoad() throws SQLException {
        executor.execUpdate("insert into users (name, age) values ('larry', 35)");

        User user = new User();
        user.setId(1);
        user.setName("bill");
        user.setAge(30);

        executor.save(user);

        User newUser = executor.load(1, User.class);

        Assert.assertEquals(1, newUser.getId());
        Assert.assertEquals("bill", newUser.getName());
        Assert.assertEquals(30, newUser.getAge());
    }

}
