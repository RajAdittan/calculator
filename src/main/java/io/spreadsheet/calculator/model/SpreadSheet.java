package io.spreadsheet.calculator.model;

import io.spreadsheet.calculator.caching.DataCache;
import io.spreadsheet.calculator.caching.SpreadCellCache;
import io.spreadsheet.calculator.constants.DefaultValues;
import io.spreadsheet.calculator.input.DataReader;
import io.spreadsheet.calculator.output.DataWriter;
import io.spreadsheet.calculator.process.Evaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringTokenizer;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkNotNull;

public class SpreadSheet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpreadSheet.class);

    private final DataCache dataCache;

    private SpreadSheet(DataCache dataCache) {
        this.dataCache = dataCache;
    }

    public static SpreadSheet from(DataReader dataReader) {
        checkNotNull(dataReader, "specified dataReader is null");

        //read header to identify the spread dimensions
        Header header = dataReader.readHeader();
        checkNotNull(header, "unable to read header info from specified input stream");
        //Note: do not remove the input display values
        System.out.printf("%d %d%s", header.getRows(), header.getColumns(),System.lineSeparator());
        DataCache dataCache = SpreadCellCache.of(header);
        checkNotNull(dataCache);
        //read contents, load items into DataCache
        while(dataReader.hasNext()) {
            DataItem item = dataReader.readData();
            checkNotNull(item, "unable to read DataItem from input stream");
            if(DefaultValues.DEFAULT_DATA!=item) {
                if(LOGGER.isInfoEnabled()) {
                    LOGGER.info(String.format("adding item: %s", item.toString()));
                }
                dataCache.add(item);
                //Note: do not remove the input display values
                System.out.println(item.getValue());
            } else {
                LOGGER.warn("an item of null (or) default data is found from input stream, ignoring");
            }
        }
        return new SpreadSheet(dataCache);
    }

    public static void to(SpreadSheet source, DataWriter writer) {
        checkNotNull(source, "specified source is null");
        checkNotNull(writer, "specifed writer is null");

        writer.writeHeader(source.header());
        source.consumeSheet(writer::writeData);
    }

    public static SpreadCell exprOf(SpreadSheet sheet, String id, String expr) {
        checkNotNull(sheet, "specified sheet is null");
        checkNotNull(id, "specified id is null");
        checkNotNull(expr, "specified expr is null");

        SpreadCell cell = sheet.dataCache().findById(id);
        checkNotNull(cell, "DataCache::findById returns null for specified id:" + id);

        return SpreadCell.exprOf(cell, expr);
    }

    public Boolean isSpreedSheetCell(String id) {
        if(id.length()==0 || id=="") return false;
        return Boolean.valueOf(id.charAt(0) >= 'A' && id.charAt(0) <= 'Z');
    }

    public Boolean hasErrors(String id) {
        try {
            checkNotNull(id, "specified id is null");
            SpreadCell spreadCell = dataCache().findById(id);
            checkNotNull(spreadCell, "unable to find SpreadCell for the specified id:" + id);
            if (spreadCell.isValidated() &&
                ("CYCLIC DEPENDENCY".equals(spreadCell.getValue()) ||
                        spreadCell.getValue().contains("HAS ERROR"))) {
                return Boolean.valueOf(true);
            }
        }catch (ArrayIndexOutOfBoundsException aibe) {
            LOGGER.error("ERR:-", "specified id does not exist in DataCache"+System.lineSeparator(), aibe);
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public String getValue(String id) {
        SpreadCell item = dataCache.findById(id);
        return null!=item ? item.getExpr() : "";
    }

    public SpreadCell calculateSpread(SpreadCell cell) {
        if(checkCyclicDependency(cell)) {
            if(LOGGER.isWarnEnabled()) {
                LOGGER.warn(cell.toString());
                LOGGER.warn(String.format("expr: %s found to be cyclic in the given spreadsheet, hence could not be evaluated.", cell.getExpr()));
            }
            return SpreadCell.valueOf(cell, "CYCLIC DEPENDENCY", String.format("expr: %s found to be cyclic in the given spreadsheet, hence could not be evaluated", cell.getExpr()));
        }
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info(cell.toString());
        }
        if(cell.isValidated()) return cell;
        String expr = cell.getExpr();
        Evaluator expo = Evaluator.from(this, expr);
        setValue(cell, expo.evaluate());
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("expr: %s \t\t:= %s%s", cell.getExpr(), cell.getValue(), System.lineSeparator()));
        }
        return cell;
    }

    public void calculateSpread() {
        IntStream.range(0, dataCache.rowsCount())
                .forEach(
                        row ->
                           IntStream.range(1, 1+dataCache.columnsCount())
                           .forEach(
                                   col -> {
                                       SpreadCell spreadCell = dataCache.find(row, col);
                                       if(!spreadCell.isValidated()) {
                                           spreadCell = calculateSpread(spreadCell);
                                           LOGGER.info(spreadCell.toString());
                                       }
                                   }
                           )

        );
    }

    public void consumeSheet(Consumer<SpreadCell> spreadCellConsumer) {
        IntStream.range(0, dataCache.rowsCount())
                .forEach( row ->
                    IntStream.range(1, 1+dataCache.columnsCount())
                            .forEach(col -> spreadCellConsumer.accept(dataCache.find(row, col)))
        );
    }

    public Boolean checkCyclicDependency(SpreadCell spreadCell) {
        String expr = spreadCell.getExpr();
        if(expr.contains(DefaultValues.DEFAULT_SEPARATOR)) {
            StringTokenizer tokenizer = new StringTokenizer(expr, DefaultValues.DEFAULT_SEPARATOR);
            while(tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
               if(isSpreedSheetCell(token)) {
                   SpreadCell spreadCell1 = dataCache.findById(token);
                   Integer level = Integer.valueOf(1);
                   return internalCheckCyclicDependency(spreadCell, spreadCell1, level);
               }
            }
        } else {
            if(isSpreedSheetCell(expr)) {
                SpreadCell spreadCell1 = dataCache.findById(expr);
                Integer level = Integer.valueOf(1);
                return internalCheckCyclicDependency(spreadCell, spreadCell1, level);
            }
        }
        return Boolean.valueOf(false);
    }

    private Boolean internalCheckCyclicDependency(SpreadCell spreadCell, SpreadCell spreadCell1, Integer level) {
        if(spreadCell.equals(spreadCell1)) return Boolean.valueOf(true);
        if(null==spreadCell1) return false;
        String expr =spreadCell1.getExpr();
        if(null==expr) return false;
        if(expr.contains(DefaultValues.DEFAULT_SEPARATOR)) {
            StringTokenizer tokenizer = new StringTokenizer(expr, DefaultValues.DEFAULT_SEPARATOR);
            while(tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                if(isSpreedSheetCell(token)) {
                    SpreadCell spreadCell2 = dataCache.findById(token);
                    level = level + Integer.valueOf(1);
                    return internalCheckCyclicDependency(spreadCell, spreadCell2, level);
                }
            }
        } else {
            if(isSpreedSheetCell(expr)) {
                SpreadCell spreadCell2 = dataCache.findById(expr);
                level = level+Integer.valueOf(1);
                return internalCheckCyclicDependency(spreadCell, spreadCell2, level);
            }
        }
        return Boolean.valueOf(false);
    }

    private Header header() {
        return Header.of(dataCache.rowsCount(), dataCache.columnsCount());
    }
    private DataCache dataCache() { return dataCache; }

    public String setValue(SpreadCell cell, String value) {
        SpreadCell.valueOf(cell, value, true);
        return cell.getId();
    }
}
