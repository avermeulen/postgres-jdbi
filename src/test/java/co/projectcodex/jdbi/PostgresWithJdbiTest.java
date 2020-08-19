package co.projectcodex.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class PostgresWithJdbiTest {

    Jdbi jdbi = Jdbi.create("jdbc:postgresql://localhost/greeter");

    @BeforeEach
    void beforeEach() {
        jdbi.withHandle(h -> {
            h.execute("delete from person");
            return null;
        });
    }


    @Test
    public void shouldBeAbleToConnectToPostgreSQL() {

        int count = jdbi.withHandle(h -> h.createQuery("select count(*) from person")
                .mapTo(int.class)
                .findOnly());

        assertTrue(count >= 0);

    }

    @Test
    public void shouldBeAbleToCleanTable() {

        int count = jdbi.withHandle(h -> {
            return h.createQuery("select count(*) from person")
                    .mapTo(int.class)
                    .findOnly();
        });
        assertEquals(0, count);
    }

    @Test
    public void shouldBeAbleToInsertPerson() {

        int counter = jdbi.withHandle(h -> {
            h.execute("insert into person (first_name, counter) values (?, ?)", "TheName", 7);

            int count = h.createQuery("select count(*) from person where counter = 7")
                    .mapTo(int.class)
                    .findOnly();

            return count;
        });

        assertEquals(1, counter);
    }

    @Test
    public void shouldBeAbleToFindAll() {

        List<Person> people = jdbi.withHandle(h -> {
            h.execute("insert into person (first_name, counter) values (?, ?)", "Name two", 7);
            h.execute("insert into person (first_name, counter) values (?, ?)", "Name three", 6);
            h.execute("insert into person (first_name, counter) values (?, ?)", "Name four", 5);

            List<Person> listPerson = h.createQuery("select first_name, counter from person")
                    .mapToBean(Person.class)
                    .list();
            return listPerson;
        });

        assertEquals(3, people.size());
        assertEquals("Name three", people.get(1).getFirstName());


    }





}
