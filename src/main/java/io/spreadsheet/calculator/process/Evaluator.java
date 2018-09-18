package io.spreadsheet.calculator.process;

import io.spreadsheet.calculator.constants.DefaultValues;
import io.spreadsheet.calculator.model.SpreadSheet;

import java.util.Deque;
import java.util.StringTokenizer;
import java.util.concurrent.LinkedBlockingDeque;

import static com.google.common.base.Preconditions.checkNotNull;

public class Evaluator {
    private String expr;
    private SpreadSheet dependency;

    private Evaluator(String expr) {
        this.expr = expr;
    }

    private Evaluator(SpreadSheet dependency, String expr) {
        this.expr = expr;
        setDependency(dependency);
    }

    public static Evaluator of(String expr) {
        checkNotNull(expr);
        return new Evaluator(expr);
    }

    public static Evaluator from(SpreadSheet dependency, String expr) {
        checkNotNull(expr);
        return new Evaluator(dependency, expr);
    }

    public void setDependency(SpreadSheet dependency) {
        checkNotNull(dependency);
        this.dependency = dependency;
    }

    public String evaluate() {
        Deque<String> evalStack = new LinkedBlockingDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(expr, DefaultValues.DEFAULT_SEPARATOR);
        if(1<tokenizer.countTokens()) {
            while(tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                //binary operation
                if(evalStack.size()<2) {
                    //unary operation
                    switch (token) {
                        case DefaultValues.PLUSPLUS:
                            evalStack.push(String.format("%.4f", CalculationExtensions.PLUSPLUS.apply(Double.valueOf(evalStack.pop()))));
                            break;
                        case DefaultValues.MINUSMINUS:
                            evalStack.push(String.format("%.4f", CalculationExtensions.MINUSMINUS.apply(Double.valueOf(evalStack.pop()))));
                            break;
                        default:
                            evalStack.push(evaluateInternal(token, evalStack));
                            break;
                    }
                } else {
                    switch (token) {
                        case DefaultValues.PLUS:
                            evalStack.push(String.format("%.4f", Calculations.ADD.apply(Double.valueOf(evalStack.pop()),Double.valueOf(evalStack.pop()))));
                        break;
                        case DefaultValues.MINUS:
                            evalStack.push(String.format("%.4f", Calculations.SUB.apply(Double.valueOf(evalStack.pop()),Double.valueOf(evalStack.pop()))));
                        break;
                        case DefaultValues.MULTIPLY:
                            evalStack.push(String.format("%.4f", Calculations.MUL.apply(Double.valueOf(evalStack.pop()),Double.valueOf(evalStack.pop()))));
                        break;
                        case DefaultValues.DIVIDE:
                            evalStack.push(String.format("%.4f", Calculations.DIV.apply(Double.valueOf(evalStack.pop()),Double.valueOf(evalStack.pop()))));
                        break;
                        default:
                            evalStack.push(evaluateInternal(token, evalStack));
                        break;
                    }
                }
            }
            return evalStack.pop();
        } else {
            return evaluateInternal(expr, evalStack);
        }
    }

    private String evaluateInternal(String token, Deque<String> stack) {
        if(dependency.isSpreedSheetCell(token)) {
            if(dependency.hasErrors(token)) {
                return token + " HAS ERROR";
            }
            //evaluate and add into stack
            Evaluator innerexpr = Evaluator.of(dependency.getValue(token));
            innerexpr.setDependency(dependency);
            return String.format("%.4f", Double.valueOf(innerexpr.evaluate()));
        } else {
            switch (token) {
                case DefaultValues.PLUSPLUS:
                    return String.format("%.4f", CalculationExtensions.PLUSPLUS.apply(Double.valueOf(stack.pop())));
                case DefaultValues.MINUSMINUS:
                    return String.format("%.4f", CalculationExtensions.MINUSMINUS.apply(Double.valueOf(stack.pop())));
                default:
                    return String.format("%.4f", Double.valueOf(token));
            }
        }
    }
}
