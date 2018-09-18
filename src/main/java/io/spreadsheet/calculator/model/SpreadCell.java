package io.spreadsheet.calculator.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class SpreadCell {
    private String id;
    private String expr;
    private String value;
    private boolean validated;
    private String errors;

    private SpreadCell(String id) {
        this.id = id;
        this.value = "";
    }

    private SpreadCell(String id, String expr) {
        this.id = id;
        this.expr = expr;
    }

    public static SpreadCell of(String id) {
        checkNotNull(id, "specified id is null");
        return new SpreadCell(id);
    }

    public static SpreadCell from(DataItem item) {
        checkNotNull(item, "specified item is null");
        String id = generateId(item.getRow(), item.getCol());
        return new SpreadCell(id, item.getValue());
    }

    public static SpreadCell exprOf(SpreadCell cell, String expr) {
        checkNotNull(cell, "specified cell is null");
        checkNotNull(expr, "specified expr is null");
        cell.setExpr(expr);
        return cell;
    }

    public static SpreadCell valueOf(SpreadCell cell, String value, boolean validated) {
        checkNotNull(value, "specified cell is null");
        cell.setValue(value);
        cell.setValidated(validated);
        return cell;
    }

    public static SpreadCell valueOf(SpreadCell cell, String value, String errors) {
        checkNotNull(value, "specified cell is null");
        cell.setValue(value);
        cell.setValidated(true);
        cell.setErrors(errors);
        return cell;
    }

    public static Integer row(String id) {
        checkNotNull(id);
        Integer row = Integer.valueOf(id.charAt(0)-'A');
        checkNotNull(row);
        checkArgument(row >-1);
        return row;
    }

    public static Integer column(String id) {
        checkNotNull(id);
        Integer col = Integer.valueOf(id.substring(1));
        checkNotNull(col);
        checkArgument(col >-1);
        return col;
    }

    public boolean isValidated() {
        return validated;
    }

    public String getId() {
        return id;
    }

    public String getExpr() {
        return expr;
    }

    public String getValue() {
        return value;
    }

    private void setExpr(String expr) { this.expr=expr; }

    private void setValue(String value) {
        this.value = value;
    }

    private void setValidated(boolean value) {
        this.validated = value;
    }

    private void setErrors(String errors) { this.errors = errors; }

    @Override
    public boolean equals(Object obj) {
        SpreadCell spreadCell = (SpreadCell)obj;
        checkNotNull(spreadCell, "cast Object to SpreadCell has errors");
        //value equals
        return spreadCell.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "SpreadCell{" +
                "id='" + id + '\'' +
                ", expr='" + expr + '\'' +
                ", value='" + value + '\'' +
                ", validated=" + validated +
                ", errors=" + errors +
                '}';
    }

    static String generateId(Integer row, Integer col) {
        checkArgument(row >-1, "specified row is out of bounds");
        checkArgument(col >-1, "specified col is out of bounds");
        return String.format("%c%d", 'A'+row, col);
    }
}
