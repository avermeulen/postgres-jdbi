import co.projectcodex.jdbi.Subject;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CallFunctionTest {

    Jdbi jdbi = Jdbi.create("jdbc:postgresql://localhost/schools-database");

    @Test
    public void shouldBeAbleToCallFunction() {

        List<Subject> subjects = jdbi.withHandle(h ->
                h.createQuery("select * from find_subjects()")
                        .mapToBean(Subject.class)
                        .list());

        assertTrue(subjects.size() > 0);

        for (Subject subject : subjects) {
            System.out.println(subject);
        }

    }

    @Test
    public void shouldBeAbleToCreateASubject() {

        jdbi.withHandle(h -> {

            h.execute("delete from subject where name = ?", "Life Orientation");

            boolean createdMaths = h.select("select * from create_subject(?)", "Mathematics")
                    .mapTo(boolean.class)
                    .findOnly();

            assertEquals(false, createdMaths);

            boolean createdLO = h.select("select * from create_subject(?)", "Life Orientation")
                    .mapTo(boolean.class)
                    .findOnly();

            assertEquals(true, createdLO);

            return null;

        });

    }




}
