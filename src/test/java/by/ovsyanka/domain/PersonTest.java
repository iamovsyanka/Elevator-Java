package by.ovsyanka.domain;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private final int weight = 50;
    private final int maxFloor = 10;
    private final int currentFloor = 3;
    private final int selectedFloor = 7;

    @Test
    public void testCreatePerson() {
        Person person = Person.of(weight, maxFloor, currentFloor, selectedFloor);

        assertNotNull(person);
    }

    @Test
    public void testCreateRandomPerson() {
        for (int i = 0; i < 5; i++) {
            int randomWeight = new Random().ints(20, 150).findFirst().getAsInt();
            assertDoesNotThrow(() -> Person.of(randomWeight, maxFloor, currentFloor, selectedFloor));
        }
    }

    @Test
    public void testGetWeight() {
        Person person = Person.of(weight, maxFloor, currentFloor, selectedFloor);

        assertEquals(weight, person.getWeight());
    }

    @Test
    public void testInvalidWeight() {
        //less than min
        assertThrows(IllegalArgumentException.class, () -> Person.of(19, maxFloor, currentFloor, selectedFloor));
        assertThrows(IllegalArgumentException.class, () -> Person.of(-10, maxFloor, currentFloor, selectedFloor));

        //more than max
        assertThrows(IllegalArgumentException.class, () -> Person.of(151, maxFloor, currentFloor, selectedFloor));
        assertThrows(IllegalArgumentException.class, () -> Person.of(200, maxFloor, currentFloor, selectedFloor));
    }

    @Test
    public void testMinWeight() {
        assertDoesNotThrow(() -> Person.of(20, maxFloor, currentFloor, selectedFloor));
    }

    @Test
    public void testMaxWeight() {
        assertDoesNotThrow(() -> Person.of(150, maxFloor, currentFloor, selectedFloor));
    }

    @Test
    public void testGetCurrentFloor() {
        Person person = Person.of(weight, maxFloor, currentFloor, selectedFloor);

        assertEquals(currentFloor, person.getCurrentFloor());
    }

    @Test
    public void testMaxCurrentFloor() {
        assertDoesNotThrow(() -> Person.of(weight, maxFloor, maxFloor, selectedFloor));
    }

    @Test
    public void testMinCurrentFloor() {
        assertDoesNotThrow(() -> Person.of(weight, maxFloor, 1, selectedFloor));
    }

    @Test
    public void testInvalidCurrentFloor() {
        //less than 1
        assertThrows(IllegalArgumentException.class, () -> Person.of(weight, maxFloor, 0, selectedFloor));
        assertThrows(IllegalArgumentException.class, () -> Person.of(weight, maxFloor, -10, selectedFloor));

        //more than max
        assertThrows(IllegalArgumentException.class, () -> Person.of(weight, maxFloor, maxFloor + 1, selectedFloor));
        assertThrows(IllegalArgumentException.class, () -> Person.of(weight, maxFloor, maxFloor + 100, selectedFloor));
    }

    @Test
    public void testGetSelectedFloor() {
        Person person = Person.of(weight, maxFloor, currentFloor, selectedFloor);

        assertEquals(selectedFloor, person.getSelectedFloor());
    }

    @Test
    public void testMaxSelectedFloor() {
        assertDoesNotThrow(() -> Person.of(weight, maxFloor, currentFloor, maxFloor));
    }

    @Test
    public void testMinSelectedFloor() {
        assertDoesNotThrow(() -> Person.of(weight, maxFloor, currentFloor, 1));
    }

    @Test
    public void testInvalidSelectedFloor() {
        //less than 1
        assertThrows(IllegalArgumentException.class, () -> Person.of(weight, maxFloor, currentFloor, 0));
        assertThrows(IllegalArgumentException.class, () -> Person.of(weight, maxFloor, currentFloor, -10));

        //more than max
        assertThrows(IllegalArgumentException.class, () -> Person.of(weight, maxFloor, currentFloor, maxFloor + 1));
        assertThrows(IllegalArgumentException.class, () -> Person.of(weight, maxFloor, currentFloor, maxFloor + 100));
    }

    @Test
    public void testSelectCurrentFloor() {
        assertThrows(IllegalArgumentException.class, () -> Person.of(weight, maxFloor, currentFloor, currentFloor));
    }
}
