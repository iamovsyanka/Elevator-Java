package by.ovsyanka.domain;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.UUID;

@Slf4j
@ToString
public class Person {

    private final UUID id;

    @Getter
    private final int weight;
    private final int minWeight = 20;
    private final int maxWeight = 150;

    private final int maxFloor;
    @Getter
    private final int currentFloor;
    @Getter
    private final int selectedFloor;

    public Person(int weight, int maxFloor, int currentFloor, int selectedFloor) {
        checkArgument(weight >= minWeight && weight <= maxWeight,
                String.format("Weight must be between %s and %s", minWeight, maxWeight));
        checkArgument(currentFloor <= maxFloor && currentFloor >= 1,
                String.format("Current floor must be less than %s and more than 0", maxFloor + 1));
        checkArgument(currentFloor != selectedFloor,
                "Selected floor cannot be equal to the current one");
        checkArgument(selectedFloor <= maxFloor && selectedFloor >= 1,
                "Selected floor must be less than %s and more than 0", maxFloor + 1);

        this.id = UUID.randomUUID();
        this.weight = weight;
        this.maxFloor = maxFloor;
        this.currentFloor = currentFloor;
        this.selectedFloor = selectedFloor;
    }

    public static Person of(int weight, int maxFloor, int currentFloor, int selectedFloor) {
        return new Person(weight, maxFloor, currentFloor, selectedFloor);
    }
}
