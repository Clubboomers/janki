package main.mainwindow;

import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JTable;

public class CellRenderer extends DefaultTableCellRenderer {
    // An array of colors to set the foreground color for each column
    private Color[] columnColors;

    public CellRenderer(Color[] columnColors) {
        this.columnColors = columnColors;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Get the default renderer component
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Color foreground = (value != null && isNumeric(value.toString()) && Integer.parseInt(value.toString()) > 0) ? columnColors[column] : table.getForeground();
        c.setForeground(foreground);

        return c;
    }

    /**
     * Hack because value instanceof Integer doesn't work
     * @param str the string to check (if it's a number)
     * @return true if str is a number
     */
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  // matches a number with an optional decimal point
    }

    /* {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
                Component component = super.prepareRenderer(renderer, rowIndex, columnIndex);
                Object value = getValueAt(rowIndex, columnIndex); // Use JTable.getValueAt
                System.out.println(value instanceof Integer);
                if (columnIndex == NEW_COLUMN && value instanceof Integer && (Integer) value > 0) {
                    component.setForeground(NEW_COLOR);
                } else if (columnIndex == LEARNING_COLUMN && value instanceof Integer && (Integer) value > 0) {
                    component.setForeground(LEARNING_COLOR);
                } else if (columnIndex == DUE_COLUMN && value instanceof Integer && (Integer) value > 0) {
                    component.setForeground(DUE_COLOR);
                } else {
                    deckTable.setForeground(deckTable.getForeground());
                }
                return component;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                if (convertColumnIndexToModel(column) == NEW_COLUMN ||
                        convertColumnIndexToModel(column) == LEARNING_COLUMN ||
                        convertColumnIndexToModel(column) == DUE_COLUMN) {
                    return Integer.class;
                } else {
                    return super.getColumnClass(column);
                }
            }
        }*/;
        /*deckTable.setDefaultRenderer(Integer.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column) {
                Component c = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
                if (value instanceof Integer) {
                    int intValue = (Integer) value;
                    if (intValue > 0) {
                        // Set the foreground color for each column (new, learning, due)
                        if (column == NEW_COLUMN) {
                            c.setForeground(NEW_COLOR);
                        } else if (column == LEARNING_COLUMN) {
                            c.setForeground(LEARNING_COLOR);
                        } else if (column == DUE_COLUMN) {
                            c.setForeground(DUE_COLOR);
                        }
                    }
                }
                c.setForeground(NEW_COLOR);
                return c;
            }
        });*/
}
