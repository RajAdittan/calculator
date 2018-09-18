package io.spreadsheet.calculator.process;

import java.util.function.BiFunction;

/**
 * Simple binary operation calculation interface for binary expression processing
 */
public interface BinaryCalculation
        extends BiFunction<Double, Double, Double> {
}
