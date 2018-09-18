package io.spreadsheet.calculator.process

class CalculationExtensionsTest extends GroovyTestCase {
    void testCalculationExtensions() {
        println "++1.0123 :=" + CalculationExtensions.PLUSPLUS.apply(1.0123f);
        println "--1.0123 :=" + CalculationExtensions.MINUSMINUS.apply(1.0123f);
    }
}
