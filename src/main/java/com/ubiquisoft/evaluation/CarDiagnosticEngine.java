package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarDiagnosticEngine {

    public void executeDiagnostics(Car car) {

        /*
         * Implement basic diagnostics and print results to console.
         *
         * The purpose of this method is to find any problems with a car's data or parts.
         *
         * Diagnostic Steps:
         *      First   - Validate the 3 data fields are present, if one or more are
         *                then print the missing fields to the console
         */
        if (!checkFields(car)){
            return;
        }
        /*
         *      Second  - Validate that no parts are missing using the 'getMissingPartsMap' method in the Car class,
         *                if one or more are then run each missing part and its count through the provided missing part method.
         */
        if (!checkMissingParts(car)) {
            return;
        }

        /*
         *      Third   - Validate that all parts are in working condition, if any are not
         *                then run each non-working part through the provided damaged part method.
         *
         */
        if (!checkDamagedParts(car)) {
            return;
        }
        /*
         *      Fourth  - If validation succeeds for the previous steps then print something to the console informing the user as such.
         * A damaged part is one that has any condition other than NEW, GOOD, or WORN.
         */
        System.out.println("All parts are in Working Condition");

        /*
         * Important:
         *      If any validation fails, complete whatever step you are actively one and end diagnostics early.
         *
         * Treat the console as information being read by a user of this application. Attempts should be made to ensure
         * console output is as least as informative as the provided methods.
         */
    }

    protected static boolean checkMissingParts(Car car) {
        Map<PartType, Integer> missingPartsMap = car.getMissingPartsMap();
        missingPartsMap.forEach(CarDiagnosticEngine::printMissingPart);

        return missingPartsMap.isEmpty();
    }

    protected static boolean checkDamagedParts(Car car) {
        List<Part> damagedParts = car.getParts().stream().filter(part -> !part.isInWorkingCondition())
                .collect(Collectors.toList());
        damagedParts.forEach(damagedPart -> printDamagedPart(damagedPart.getType(), damagedPart.getCondition()));

        return damagedParts.isEmpty() ;
    }

    protected static boolean checkFields(Car car) {
        if (car.getMake() == null) {
            System.out.println("Make is missing");

            return false;
        }
        if (car.getYear() == null) {
            System.out.println("Year is missing");

            return false;
        }
        if (car.getModel() == null) {
            System.out.println("Model is missing");

            return false;
        }

        return true;
    }

    private static void printMissingPart(PartType partType, Integer count) {
        if (partType == null) throw new IllegalArgumentException("PartType must not be null");
        if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

        System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
    }

    private static void printDamagedPart(PartType partType, ConditionType condition) {
        if (partType == null) throw new IllegalArgumentException("PartType must not be null");
        if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

        System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
    }

    public static void main(String[] args) throws JAXBException {
        // Load classpath resource
        InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar.xml");

        // Verify resource was loaded properly
        if (xml == null) {
            System.err.println("An error occurred attempting to load SampleCar.xml");

            System.exit(1);
        }

        // Build JAXBContext for converting XML into an Object
        JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Car car = (Car) unmarshaller.unmarshal(xml);

        // Build new Diagnostics Engine and execute on deserialized car object.

        CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

        diagnosticEngine.executeDiagnostics(car);

    }

}
