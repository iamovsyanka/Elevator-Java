import by.ovsyanka.domain.building.Building;

class Main {
    public static void main(String[] args) {
        final int floorsNumber = 10;
        final int elevatorsNumber = 3;

        Building office = Building.of(floorsNumber, elevatorsNumber);
        office.start();
        office.handle();
    }
}
