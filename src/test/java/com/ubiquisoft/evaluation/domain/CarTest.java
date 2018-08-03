package com.ubiquisoft.evaluation.domain;


import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
class CarTest {

    @Test
    public void testMissingParts() {
        Car car = new Car();
        List<Part> parts = new ArrayList<>();
        Part engine = new Part();;
        engine.setType(PartType.ENGINE);

        Part tire1 = new Part();;
        tire1.setType(PartType.TIRE);

         Part tire2 = new Part();
        tire2.setType(PartType.TIRE);
        Part tire3 = new Part();
        tire3.setType(PartType.TIRE);

        Part electrical = new Part();
        electrical.setType(PartType.ELECTRICAL);



        parts.add(engine);
        parts.add(electrical);
        parts.add(tire1);
        parts.add(tire2);
        parts.add(tire3);
        car.setParts(parts);
        Map<PartType, Integer> missingPartsMap = car.getMissingPartsMap();
        assertEquals(1, (int) missingPartsMap.get(PartType.TIRE));
        assertEquals(1, (int) missingPartsMap.get(PartType.OIL_FILTER));
        assertEquals(1, (int) missingPartsMap.get(PartType.FUEL_FILTER));

    }
}