package by.ovsyanka.domain;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    public void testCreatePerson() {
        Person person = Person.of(50);

        assertNotNull(person);
    }

    @Test
    public void testCreateRandomPerson() {
        for (int i = 0; i < 5; i++) {
            int randomWeight = new Random().ints(20, 150).findFirst().getAsInt();
            assertDoesNotThrow(() -> Person.of(randomWeight));
        }
    }

    @Test
    public void testGetWeight() {
        Person person = Person.of(50);

        assertEquals(50, person.getWeight());
    }

    @Test
    public void testInvalidWeight() {
        //less than min
        assertThrows(IllegalArgumentException.class, () -> Person.of(19));
        assertThrows(IllegalArgumentException.class, () -> Person.of(-10));

        //more than max
        assertThrows(IllegalArgumentException.class, () -> Person.of(151));
        assertThrows(IllegalArgumentException.class, () -> Person.of(200));
    }

    @Test
    public void testMinWeight() {
        assertDoesNotThrow(() -> Person.of(20));
    }

    @Test
    public void testMaxWeight() {
        assertDoesNotThrow(() -> Person.of(150));
    }
}
