package io.spreadsheet.calculator.constants;

import io.spreadsheet.calculator.model.DataItem;
import io.spreadsheet.calculator.model.Header;

public class DefaultValues {
    public static final String PLUS="+";
    public static final String PLUSPLUS="++";
    public static final String MINUS="-";
    public static final String MINUSMINUS="--";
    public static final String MULTIPLY="*";
    public static final String DIVIDE="/";

    public static final String DEFAULT_SEPARATOR=" ";
    public static final Header DEFAULT_HEADER
            = Header.of(Integer.valueOf(0), Integer.valueOf(0));
    public static final DataItem DEFAULT_DATA
            = DataItem.of(Integer.valueOf(0), Integer.valueOf(0), "");

    public static final String CELL_IS_NULL = "specified cell is null";
    public static final String ID_IS_NULL = "specified id is null";

    /**
     * Disable object creation of constant value classes
     */
    private DefaultValues() {
    }
}
