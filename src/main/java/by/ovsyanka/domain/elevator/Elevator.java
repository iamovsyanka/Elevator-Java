package by.ovsyanka.domain.elevator;

import by.ovsyanka.domain.enums.Direction;
import by.ovsyanka.domain.enums.DoorState;
import by.ovsyanka.domain.person.Person;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Getter
@Setter
@ToString
public class Elevator extends Thread {

    private final long id;
    private final int timeElevator = 2_000;
    private final int timeDoor = 1_000;
    private final int maxWeight = 200;
    private int timeToSelectedFloor = timeElevator * (Math.abs(this.selectedFloor - this.currentFloor));

    private int currentFloor;
    private int selectedFloor;

    private int numberOfPeopleTransported = 0;

    private DoorState doorState;

    private boolean isMove;
    private boolean isFree;

    private List<Person> people;

    private Elevator(int id) {
        this.id = id;

        people = new ArrayList<>();
        this.currentFloor = 1;
        this.selectedFloor = -1;
        this.doorState = DoorState.CLOSED;
        this.isMove = false;
        this.isFree = true;
    }

    public static Elevator of(int id) {
        return new Elevator(id);
    }

    @SneakyThrows
    private void move() {
        log.info("~Elevator {} go from {} to {}", this.getId(), this.currentFloor, this.selectedFloor);

        sleep(timeToSelectedFloor);
        isFree = false;
        isMove = false;
        currentFloor = selectedFloor;
        selectedFloor = -1;
        log.info("~Elevator {} arrived at {}", this.getId(), this.currentFloor);
    }

    @SneakyThrows
    private void openDoor() {
        sleep(timeDoor);
        this.doorState = DoorState.OPEN;
        log.info("---------The elevator {} door is open", getId());
    }

    @SneakyThrows
    private void closeDoor() {
        sleep(timeDoor);
        if (isEmpty()) {
            this.isFree = true;
        }
        this.doorState = DoorState.CLOSED;
        log.info(String.valueOf(people));
        log.info("---------The elevator {} door is closed", getId());
    }

    private void checkFloor() {
        if (currentFloor > getNextFloor()) {
            currentFloor--;
        } else {
            currentFloor++;
        }
        if (currentFloor != getNextFloor()) {
            log.info("Elevator {}: at {}, next floor is {}", getId(), currentFloor, getNextFloor());
        } else {
            log.info("Elevator {}: at {}", getId(), currentFloor);
        }
    }

    public int getNextFloor() {
        return this.people.isEmpty() ? -1 : this.people.get(0).getSelectedFloor();
    }

    public void clearElevator() {
        this.isFree = true;
    }

    public int getFreeSpace() {
        return maxWeight - people.stream().mapToInt(Person::getWeight).sum();
    }

    public Direction getDirection() {
        return currentFloor <= getNextFloor() ? Direction.UP : Direction.DOWN;
    }

    public boolean isEmpty() {
        return this.people.isEmpty();
    }

    @SneakyThrows
    @Override
    public void run() {
        log.info("~~~Elevator {} start", getId());

        while (true) {
            sleep(1);

            if (isMove) {
                move();
            }
            switch (doorState) {
                case OPENS:
                    openDoor();
                    break;
                case CLOSES:
                    closeDoor();
                    break;
                case CLOSED: {
                    if (!people.isEmpty()) {
                        checkFloor();
                        sleep(timeElevator);
                    }
                }
                break;
            }
        }
    }

    public boolean isSelectedFloor() {
        return selectedFloor != -1;
    }

    public void setSelectedFloor(int selectedFloor) {
        this.selectedFloor = selectedFloor;
        isMove = true;
    }

    public void addPeople(List<Person> people) {
        checkNotNull(people, "People cannot be null");
        this.people.addAll(people);
    }

    public void releasePeopleByFloor() {
        List<Person> releaseList = people.stream()
                .filter(x -> x.getSelectedFloor() == currentFloor)
                .collect(Collectors.toList());

        people.removeAll(releaseList);
        numberOfPeopleTransported += releaseList.size();
        if (releaseList.size() != 0)
            log.info("Elevator {}: count of all people {}", getId(), numberOfPeopleTransported);
    }
}