package by.ovsyanka.domain;

import by.ovsyanka.domain.elevator.Elevator;
import by.ovsyanka.domain.enums.Direction;
import by.ovsyanka.domain.enums.DoorState;
import by.ovsyanka.domain.floor.Floor;
import by.ovsyanka.domain.person.Person;
import by.ovsyanka.domain.person.PersonGenerate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorTest {

    @Test
    public void testCreateElevator() {
        Elevator elevator = Elevator.of(1);

        assertNotNull(elevator);
        assertEquals(-1, elevator.getSelectedFloor());
        assertEquals(1, elevator.getCurrentFloor());
        assertEquals(DoorState.CLOSED, elevator.getDoorState());
        assertFalse(elevator.isMove());
        assertTrue(elevator.isFree());
    }

    @Test
    public void testAddNullPerson() {
        Elevator elevator = Elevator.of(1);

        assertThrows(NullPointerException.class, () -> elevator.addPeople(null));
    }

    @Test
    public void testSelectedFloor() {
        Elevator elevator = Elevator.of(1);

        assertDoesNotThrow(() -> elevator.setSelectedFloor(10));
        assertDoesNotThrow(() -> elevator.setSelectedFloor(5));
    }

    @Test
    public void testIsSelectedFloor() {
        Elevator elevator = Elevator.of(1);

        assertDoesNotThrow(() -> elevator.setSelectedFloor(10));
        assertTrue(elevator.isSelectedFloor());
    }

    @Test
    public void testIsNoSelectedFloor() {
        Elevator elevator = Elevator.of(1);

        assertFalse(elevator.isSelectedFloor());
    }

    @Test
    public void testGetDirection() {
        Elevator elevator = Elevator.of(1);
        assertDoesNotThrow(() -> elevator.setCurrentFloor(1));
        assertDoesNotThrow(() -> elevator.addPeople(List.of(PersonGenerate.generate(5))));
        assertEquals(Direction.UP, elevator.getDirection());
    }

    @Test
    public void testGetNextFloor() {
        Elevator elevator = Elevator.of(1);
        assertDoesNotThrow(() -> elevator.setCurrentFloor(1));
        assertDoesNotThrow(() -> elevator.addPeople(List.of(PersonGenerate.generate(5))));
        assertNotEquals(-1, elevator.getNextFloor());
    }

    @Test
    public void testIsEmpty() {
        Elevator elevator = Elevator.of(1);
        assertDoesNotThrow(() -> elevator.setCurrentFloor(1));
        assertDoesNotThrow(() -> elevator.addPeople(List.of(PersonGenerate.generate(5))));
        assertFalse(elevator.isEmpty());
    }

    @Test
    public void testGetFreePlace() {
        Elevator elevator = Elevator.of(1);
        assertDoesNotThrow(() -> elevator.setCurrentFloor(1));
        assertDoesNotThrow(() -> elevator.addPeople(List.of(Person.of(40, 2, 5))));
        assertEquals(elevator.getMaxWeight() - 40, elevator.getFreeSpace());

        assertDoesNotThrow(() -> elevator.addPeople(List.of(Person.of(50, 2, 5))));
        assertEquals(elevator.getMaxWeight() - 40 - 50, elevator.getFreeSpace());
    }
}
