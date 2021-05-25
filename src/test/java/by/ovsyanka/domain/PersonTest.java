package by.ovsyanka.domain;

import by.ovsyanka.domain.person.Person;
import by.ovsyanka.domain.person.PersonGenerate;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private final int weight = 50;
    private final int currentFloor = 3;
    private final int selectedFloor = 7;

    @Test
    public void testCreatePerson() {
        Person person = Person.of(weight, currentFloor, selectedFloor);

        assertNotNull(person);
    }

    @Test
    public void testCreateRandomPerson() {
        for (int i = 0; i < 5; i++) {
            int randomWeight = new Random().ints(20, 150).findFirst().getAsInt();
            assertDoesNotThrow(() -> Person.of(randomWeight, currentFloor, selectedFloor));
        }
    }

    @Test
    public void testGetWeight() {
        Person person = Person.of(weight, currentFloor, selectedFloor);

        assertEquals(weight, person.getWeight());
    }

    @Test
    public void testGetCurrentFloor() {
        Person person = Person.of(weight, currentFloor, selectedFloor);

        assertEquals(currentFloor, person.getCurrentFloor());
    }

    @Test
    public void testGeneratePerson() {
        Person person = PersonGenerate.generate(10);

        assertNotNull(person);
    }

    @Test
    public void testGenerateInvalidPerson() {
        assertThrows(IllegalArgumentException.class, () -> PersonGenerate.generate(0));
        assertThrows(IllegalArgumentException.class, () -> PersonGenerate.generate(-10));
    }
}
