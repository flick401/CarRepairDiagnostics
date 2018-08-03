package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
class CarDiagnosticEngineTest {

    @Test
    void main() {
        buildCar();
    }

    private Car buildCar() {
        Car car = new Car();
        List<Part> parts = new ArrayList<>();
        Part engine = new Part();
        car.setMake("Jeep");
        car.setModel("Wrangler");
        car.setYear("2018");

        engine.setType(PartType.ENGINE);
        Part fuelFilter = new Part();
        fuelFilter.setType(PartType.FUEL_FILTER);
        Part oilFilter = new Part();
        oilFilter.setType(PartType.OIL_FILTER);
        Part tire1 = new Part();

        tire1.setType(PartType.TIRE);

        Part tire2 = new Part();
        tire2.setType(PartType.TIRE);
        Part tire3 = new Part();
        tire3.setType(PartType.TIRE);
        Part tire4 = new Part();
        tire3.setType(PartType.TIRE);

        Part electrical = new Part();
        electrical.setType(PartType.ELECTRICAL);


        parts.add(engine);
        parts.add(electrical);
        parts.add(tire1);
        parts.add(tire2);
        parts.add(tire3);
        parts.add(tire4);
        parts.add(oilFilter);
        parts.add(fuelFilter);
        car.setParts(parts);
        return car;
    }


    @Test
    void testCheckMissingParts() {
        Car car = buildCar();
        boolean result = CarDiagnosticEngine.checkMissingParts(car);
        Assert.assertFalse(result);
    }

    @Test
    void testCheckFieldsMissingMake() {
        Car car = buildCar();
        car.setMake(null);
        boolean result = CarDiagnosticEngine.checkFields(car);
        Assert.assertFalse(result);
    }

    @Test
    void testCheckFields() {
        Car car = buildCar();
        boolean result = CarDiagnosticEngine.checkFields(car);
        Assert.assertTrue(result);
    }


}