package by.ovsyanka.domain;

import by.ovsyanka.domain.floor.Floor;
import by.ovsyanka.domain.person.Person;
import by.ovsyanka.domain.person.PersonGenerate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FloorTest {

    @Test
    public void testCreateFloor() {
        Floor floor = Floor.of(5);

        assertNotNull(floor);
    }

    @Test
    public void testCheckEmptyQueue() {
        Floor floor = Floor.of(5);

        assertTrue(floor.isEmpty());
    }

    @Test
    public void testCheckQueue() {
        Floor floor = Floor.of(5);
        floor.addPerson(PersonGenerate.generate(7));

        assertFalse(floor.isEmpty());
    }

    @Test
    public void testCreateInvalidFloor() {
        assertThrows(IllegalArgumentException.class, () -> Floor.of(-1));
        assertThrows(IllegalArgumentException.class, () -> Floor.of(0));
        assertThrows(IllegalArgumentException.class, () -> Floor.of(-10));
    }

    @Test
    public void testNotPressedButtons() {
        Floor floor = Floor.of(5);

        assertFalse(floor.isButtonsPressed());
    }

    @Test
    public void testPressedButtons() {
        Floor floor = Floor.of(5);
        floor.pressElevatorButton(4);

        assertTrue(floor.isButtonsDownPressed());
        assertFalse(floor.isButtonUpPressed());

        floor.notPressedButtons();
        floor.pressElevatorButton(7);
        assertFalse(floor.isButtonsDownPressed());
        assertTrue(floor.isButtonUpPressed());
    }

    @Test
    public void testAddNullPerson() {
        Floor floor = Floor.of(5);

        assertThrows(NullPointerException.class, () -> floor.addPerson(null));
    }

    @Test
    public void testAddPerson() {
        Floor floor = Floor.of(5);
        Person person = PersonGenerate.generate(10);
        floor.addPerson(person);

        assertEquals(person, floor.getQueue().element());
    }

    @Test
    public void testGetPeopleInElevator() {
        Floor floor = Floor.of(5);

        floor.addPerson(Person.of(40, 1, 5));
        floor.addPerson(Person.of(50, 1, 5));

        var people = floor.getPeopleInElevator(110);

        assertNotNull(people);
    }
}
