package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.PartType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class PartTypeCountingCollector implements Collector<PartType, Map<PartType, Integer>, Map<PartType, Integer>> {
    @Override
    public Supplier<Map<PartType, Integer>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<PartType, Integer>, PartType> accumulator() {
        return (map, element) -> map.merge(element, 1, (a, b) -> a + b);
    }

    @Override
    public BinaryOperator<Map<PartType, Integer>> combiner() {
        return (left, right) -> {
            left.putAll(right);

            return left;
        };
    }

    @Override
    public Function<Map<PartType, Integer>, Map<PartType, Integer>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.singleton(Characteristics.IDENTITY_FINISH);
    }
}
