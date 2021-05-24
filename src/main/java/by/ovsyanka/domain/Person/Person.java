package by.ovsyanka.domain.Person;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Getter
@Slf4j
@ToString
public class Person {

    private final UUID id;
    private final int weight;
    private final int currentFloor;
    private final int selectedFloor;

    public Person(int weight, int currentFloor, int selectedFloor) {
        this.id = UUID.randomUUID();
        this.weight = weight;
        this.currentFloor = currentFloor;
        this.selectedFloor = selectedFloor;
    }

    public static Person of(int weight, int currentFloor, int selectedFloor) {
        return new Person(weight, currentFloor, selectedFloor);
    }
}
