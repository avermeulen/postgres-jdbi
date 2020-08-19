package co.projectcodex.jdbi;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class Person {
    String firstName;
    int counter;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
