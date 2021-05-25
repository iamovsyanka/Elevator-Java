package by.ovsyanka.domain;

import by.ovsyanka.domain.building.Building;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BuildingTest {

    @Test
    public void testCreateBuilding() {
        Building building = Building.of(4, 4);

        assertNotNull(building);
        assertEquals(4, building.getElevators().size());
        assertEquals(4, building.getFloors().size());
    }
}
