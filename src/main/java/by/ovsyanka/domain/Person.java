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

    public Person(int weight) {
        checkArgument(weight >= minWeight && weight <= maxWeight,  String.format("Weight must be between %s and %s", minWeight, maxWeight));

        this.id = UUID.randomUUID();
        this.weight = weight;
    }

    public static Person of(int weight) {
        return new Person(weight);
    }
}
