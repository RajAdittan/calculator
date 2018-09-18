package io.spreadsheet.calculator.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Calculations implements BinaryCalculation {
    /**
     * (+) operartor implementation
     */
    ADD {
        final Logger logger = LoggerFactory.getLogger("ADD");
        @Override
        public Double apply(Double rhs, Double lhs) {
            if(logger.isInfoEnabled()) {
                logger.info(String.format("+ : (%.4f, %.4f)", lhs, rhs));
            }
            return Double.valueOf(lhs + rhs);
        }
    },
    /**
     * (-) operator implementation
     */
    SUB {
        final Logger logger = LoggerFactory.getLogger("SUB");
        @Override
        public Double apply(Double rhs, Double lhs) {
            if(logger.isInfoEnabled()) {
                logger.info(String.format("- : (%.4f, %.4f)", lhs, rhs));
            }
            return Double.valueOf(lhs - rhs);
        }
    },
    /**
     * (*) operator implementation
     */
    MUL {
        final Logger logger = LoggerFactory.getLogger("MUL");
        @Override
        public Double apply(Double rhs, Double lhs) {
            if(logger.isInfoEnabled()) {
                logger.info(String.format("* : (%.4f, %.4f)", lhs, rhs));
            }
            return Double.valueOf(lhs * rhs);
        }
    },
    /**
     * (/) operator implementation
     */
    DIV {
        final Logger logger = LoggerFactory.getLogger("DIV");
        @Override
        public Double apply(Double rhs, Double lhs) {
            if(logger.isInfoEnabled()) {
                logger.info(String.format("/ : (%.4f, %.4f)", lhs, rhs));
            }
            return Double.valueOf(lhs / rhs);
        }
    }
}
