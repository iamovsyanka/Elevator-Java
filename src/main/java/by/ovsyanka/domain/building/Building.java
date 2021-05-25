package by.ovsyanka.domain.building;

import by.ovsyanka.domain.elevator.Elevator;
import by.ovsyanka.domain.floor.Floor;
import by.ovsyanka.domain.enums.DoorState;
import by.ovsyanka.domain.person.Person;
import by.ovsyanka.domain.person.PersonGenerate;
import by.ovsyanka.services.BuildingService;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.List;

@Getter
@Slf4j
@ToString
public class Building extends Thread {

    private final int timeGeneratePerson = 4_000;

    private BuildingService buildingService;
    private List<Floor> floors;
    private volatile List<Elevator> elevators;
    private ArrayDeque<Integer> queueFloor;

    private Building(int floorsNumber, int elevatorsNumber) {
        this.queueFloor = new ArrayDeque<>();
        this.buildingService = new BuildingService();
        this.floors = buildingService.createListOfFloors(floorsNumber);
        this.elevators = buildingService.createListOfElevators(elevatorsNumber);
    }

    public static Building of(int floorsNumber, int elevatorsNumber) {
        return new Building(floorsNumber, elevatorsNumber);
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            Person person = PersonGenerate.generate(floors.size());
            int currentFloor = person.getCurrentFloor();
            var floor = floors.get(currentFloor - 1);
            floor.addPerson(person);
            if (floor.needPressButton(person)) {
                this.queueFloor.add(currentFloor);
            }
            log.info("{} in floor {}", person, floor);

            sleep(timeGeneratePerson);
        }
    }

    @SneakyThrows
    public void handle() {
        elevators.forEach(Thread::start);

        while (true) {
            elevators.forEach(elevator -> {
                if (!elevator.isSelectedFloor() && !queueFloor.isEmpty() && elevator.isFree()) {
                    log.info("Elevator {}: must go to {}", elevator.getId(), queueFloor.getFirst());
                    elevator.setSelectedFloor(queueFloor.removeFirst());
                    return;
                }
                if (!elevator.getState().equals(State.TIMED_WAITING))
                    if (!elevator.isFree()) {
                        var currentFloor = elevator.getCurrentFloor();
                        var floor = floors.get(currentFloor - 1);

                        if (floor.isButtonsPressed()) {
                            if (elevator.isEmpty()) {
                                buildingService.fillEmptyElevator(elevator, floor, queueFloor, currentFloor);
                            } else {
                                buildingService.fillElevator(elevator, floor, queueFloor, currentFloor);
                            }
                        } else {
                            currentFloor = elevator.getCurrentFloor();
                            if (elevator.getNextFloor() != currentFloor) {
                                floor = floors.get(currentFloor - 1);
                                if (elevator.isEmpty() && elevator.getNextFloor() == -1
                                        && elevator.getDoorState() == DoorState.CLOSED && floor.isEmpty()) {
                                    elevator.clearElevator();
                                }
                            } else {
                                buildingService.getFromElevator(elevator);
                            }
                        }
                    }
            });
        }
    }
}