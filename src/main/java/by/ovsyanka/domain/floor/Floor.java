package by.ovsyanka.domain.floor;

import by.ovsyanka.domain.enums.Direction;
import by.ovsyanka.domain.enums.StateButton;
import by.ovsyanka.domain.person.Person;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Getter
@ToString
public class Floor {

    private final int number;
    private StateButton up;
    private StateButton down;

    private Queue<Person> queue;

    public Floor(int number) {
        checkArgument(number >= 1, "Number of floor must be more than 0");

        this.number = number;
        queue = new LinkedList<>();

        notPressedButtons();
    }

    public static Floor of(int number) {
        return new Floor(number);
    }

    private void pressButtonUp() {
        up = StateButton.ON;
    }

    private void pressButtonDown() {
        down = StateButton.ON;
    }

    public void addPerson(Person person) {
        checkNotNull(person, "Person cannot be null");
        queue.offer(person);
    }

    public boolean needPressButton(Person person) {
        checkNotNull(person, "Person cannot be null");

        if (!isButtonsPressed()) {
            if (person.getDirection() == Direction.DOWN) {
                pressButtonDown();
            } else {
                pressButtonUp();
            }
            return true;
        } else {
            return false;
        }
    }

    public List<Person> getPeopleInElevator(int freeSpace) {
        List<Person> list = new ArrayList<>();
        var sum = 0;
        for (var person : queue) {
            if (freeSpace >= sum + person.getWeight() && person.getDirection() == this.pressedButtonDirection()) {
                list.add(person);
                sum += person.getWeight();
            }

        }
        queue.removeAll(list);

        return list;
    }

    public void pressButtonIfPeopleOnFloorNotEmpty() {
        if (!queue.isEmpty() && queue.element() != null) {
            int selectedFloor = queue.element().getSelectedFloor();
            pressElevatorButton(selectedFloor);
        }
    }

    public void pressElevatorButton(int selectedFloor) {
        if (selectedFloor > number) {
            pressButtonUp();
        } else {
            pressButtonDown();
        }
    }

    public Direction pressedButtonDirection() {
        return up == StateButton.ON ? Direction.UP : Direction.DOWN;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean isButtonsPressed() {
        return up == StateButton.ON || down == StateButton.ON;
    }

    public boolean isButtonUpPressed() {
        return up == StateButton.ON;
    }

    public boolean isButtonsDownPressed() {
        return down == StateButton.ON;
    }

    public void notPressedButtons() {
        up = StateButton.OFF;
        down = StateButton.OFF;
    }
}
