package by.ovsyanka.services.interfaces;

import by.ovsyanka.domain.elevator.Elevator;
import by.ovsyanka.domain.floor.Floor;

import java.util.List;
import java.util.Queue;

public interface IBuildingService {
    List<Floor> createListOfFloors(int floorsNumber);
    List<Elevator> createListOfElevators(int elevatorsNumber);
    void takeFromFloor(Elevator elevator, Floor floor);
    void getFromElevator(Elevator elevator);
    void fillElevator(Elevator elevator, Floor floor, Queue queueFloor, int currentFloor);
    void fillEmptyElevator(Elevator elevator, Floor floor, Queue queueFloor, int currentFloor);
}
