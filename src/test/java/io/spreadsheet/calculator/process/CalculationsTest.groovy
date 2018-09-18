package io.spreadsheet.calculator.process

class CalculationsTest extends GroovyTestCase {

    void testCalculations() {
        println "10 + 20 :=" + Calculations.ADD.apply(10, 20);
        println "10 - 20 :=" + Calculations.SUB.apply(10, 20);
        println "10 * 20 :=" + Calculations.MUL.apply(10, 20);
        println "10 / 20 :=" + Calculations.DIV.apply(10, 20);
    }
}
