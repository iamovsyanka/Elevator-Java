package by.ovsyanka.domain.person;

import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class PersonGenerate {

    private static final int minWeight = 20;
    private static final int maxWeight = 150;

    public static Person generate(int maxFloor) {
        checkArgument(maxFloor > 1, "Max count of floor must be > 1");

        int weight = new Random().ints(minWeight, maxWeight).findFirst().getAsInt();
        int currentFloor = new Random().ints(1, maxFloor).findFirst().getAsInt();
        int selectedFloor;
        do {
            selectedFloor = new Random().ints(1, maxFloor).findFirst().getAsInt();
        } while (currentFloor == selectedFloor);

        return Person.of(weight, currentFloor, selectedFloor);
    }
}
