package io.spreadsheet.calculator.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum CalculationExtensions implements UnaryCalculation {
    /**
     * ++ operator implementation
     */
    PLUSPLUS {
        final Logger logger = LoggerFactory.getLogger("PLUSPLUS");
        @Override
        public Double apply(Double value) {
            if(logger.isInfoEnabled()) {
                logger.info(String.format("++ : ++(%.4f)=%.4f", value, value + Double.valueOf(1)));
            }
            return Double.valueOf(value + Double.valueOf(1));
        }
    },
    /**
     * -- operator implementation
     */
    MINUSMINUS {
        final Logger logger = LoggerFactory.getLogger("MINUSMINUS");
        @Override
        public Double apply(Double value) {
            if(logger.isInfoEnabled()) {
                logger.info(String.format("-- : --(%.4f)=%.4f", value, value - Double.valueOf(1)));
            }
            return Double.valueOf(value - Double.valueOf(1));
        }
    }
}
