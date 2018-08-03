package com.ubiquisoft.evaluation.domain;

import com.ubiquisoft.evaluation.PartTypeCountingCollector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;

	private List<Part> parts;

	/*
	 * Return map of the part types missing.
	 *
	 * Each car requires one of each of the following types:
	 *      ENGINE, ELECTRICAL, FUEL_FILTER, OIL_FILTER
	 * and four of the type: TIRE
	 *
	 * Example: a car only missing three of the four tires should return a map like this:
	 *
	 *      {
	 *          "TIRE": 3
	 *      }
	 */
	public Map<PartType, Integer> getMissingPartsMap() {
		Map<PartType, Integer> map = getParts().stream().map(Part::getType).collect(new PartTypeCountingCollector());
		Map<PartType, Integer> missingParts = new HashMap<>();

		Arrays.asList(PartType.ENGINE, PartType.ELECTRICAL, PartType.FUEL_FILTER, PartType.OIL_FILTER)
				.forEach(partType -> addIfMissing(map, missingParts, partType));

		Integer tiresCount = map.get(PartType.TIRE);
		if (tiresCount == null) {
			missingParts.put(PartType.TIRE, 0);
		} else if (tiresCount < 4) {
		    //TODO check negative case
			missingParts.put(PartType.TIRE, 4 - tiresCount);
		}

		return missingParts;
	}

    private void addIfMissing(Map<PartType, Integer> map, Map<PartType, Integer> missingParts, PartType partType) {
        if (!map.containsKey(partType)) {
            missingParts.put(partType, 1);
        }
    }

    @Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
