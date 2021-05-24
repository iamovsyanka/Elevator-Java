package by.ovsyanka.domain.Person;

import java.util.Random;

public class PersonGenerate {

    private final int minWeight = 20;
    private final int maxWeight = 150;
    private final int maxFloor;

    public PersonGenerate(int maxFloor) {
        this.maxFloor = maxFloor;
    }

    public Person generate() {
        int weight = new Random().ints(minWeight, maxWeight).findFirst().getAsInt();
        int currentFloor = new Random().ints(1, maxFloor).findFirst().getAsInt();
        int selectedFloor;
        do {
            selectedFloor = new Random().ints(1, maxFloor).findFirst().getAsInt();
        } while (currentFloor == selectedFloor);

        return Person.of(weight, currentFloor, selectedFloor);
    }
}
