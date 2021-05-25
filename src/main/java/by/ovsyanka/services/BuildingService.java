package by.ovsyanka.services;

import by.ovsyanka.domain.elevator.Elevator;
import by.ovsyanka.domain.enums.DoorState;
import by.ovsyanka.domain.floor.Floor;
import by.ovsyanka.services.interfaces.IBuildingService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class BuildingService implements IBuildingService {

    @Override
    public List<Floor> createListOfFloors(int floorsNumber) {
        log.info("Count floors: {}", floorsNumber);

        return IntStream.range(1, floorsNumber + 1)
                .mapToObj(Floor::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<Elevator> createListOfElevators(int elevatorsNumber) {
        List<Elevator> elevators = new ArrayList<>();
        for (var i = 0; i < elevatorsNumber; i++) {
            elevators.add(Elevator.of(i));
        }
        log.info("Elevators floors: {}", elevatorsNumber);

        return elevators;
    }

    @Override
    public void takeFromFloor(Elevator elevator, Floor floor) {
        int freeSpaceInElevator = elevator.getFreeSpace();
        var humans = floor.getPeopleInElevator(freeSpaceInElevator);
        if (humans != null) {
            elevator.addPeople(humans);
        }
        floor.notPressedButtons();
        floor.pressButtonIfPeopleOnFloorNotEmpty();
    }

    @Override
    public void getFromElevator(Elevator elevator) {
        if (elevator.getDoorState() != DoorState.OPEN) {
            if (elevator.getDoorState() != DoorState.OPENS) {
                elevator.setDoorState(DoorState.OPENS);
            }
            return;
        }
        elevator.releasePeopleByFloor();
        elevator.setDoorState(DoorState.CLOSES);
    }

    @Override
    public void fillElevator(Elevator elevator, Floor floor, Queue queueFloor, int currentFloor) {
        if (elevator.getDirection() == floor.pressedButtonDirection()) {
            if(elevator.getDoorState()!= DoorState.OPEN) {
                elevator.setDoorState(DoorState.OPENS);
                return;
            }
            elevator.releasePeopleByFloor();
            queueFloor.remove(currentFloor);
            takeFromFloor(elevator, floor);
            elevator.setDoorState(DoorState.CLOSES);
        } else {
            if (elevator.getNextFloor() == currentFloor) {
                if(elevator.getDoorState()!= DoorState.OPEN) {
                    if(elevator.getDoorState()!= DoorState.OPENS) {
                        elevator.setDoorState(DoorState.OPENS);
                    }
                    return;
                }
                elevator.releasePeopleByFloor();
                elevator.setDoorState(DoorState.CLOSES);
            }
        }
    }

    @Override
    public void fillEmptyElevator(Elevator elevator, Floor floor, Queue queueFloor, int selectedFloor) {
        if (elevator.getDoorState() != DoorState.OPEN) {
            if (elevator.getDoorState() != DoorState.OPENS) {
                elevator.setDoorState(DoorState.OPENS);
            }
            return;
        }
        queueFloor.remove(selectedFloor);
        takeFromFloor(elevator, floor);
        elevator.setDoorState(DoorState.CLOSES);
    }
}
