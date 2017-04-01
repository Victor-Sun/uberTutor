/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * @class Gnt.plugin.Replicator
 * @extends Ext.grid.selection.Replicator
 *
 * This class provides selection replication feature to gantt panel and should be used instead of Ext.grid.selection.Replicator.
 * In addition to simple columns like {@link Gnt.column.Name}, it will also copy complex values like dependencies and resource
 * assignments. Following columns will be ignored:
 *
 * - {@link Gnt.column.LateEndDate}
 * - {@link Gnt.column.LateStartDate}
 * - {@link Gnt.column.Milestone}
 * - {@link Gnt.column.Sequence}
 * - {@link Gnt.column.Slack}
 * - {@link Gnt.column.WBS}
 *
 */
Ext.define('Gnt.plugin.Replicator', {
    extend  : 'Ext.grid.selection.Replicator',

    alias   : 'plugin.gantt_selectionreplicator',

    init    : function (gantt) {
        this.gantt = gantt;
        this.callParent(arguments);
    },

    /**
     * This is the method which is called when the {@link Ext.grid.selection.SpreadsheetModel} selection model's extender
     * handle is dragged and released. It is passed contextual information about the selection and the extension area.
     * By default, the selection is extended to encompass the selection area, return false to prevent that.
     * @param {Gnt.panel.Gantt} ownerGrid
     * @param {Ext.grid.selection.Selection} sel
     * @param {Object} extension
     */
    replicateSelection  : function (ownerGrid, sel, extension) {
        var me = this;

        if (extension.columns || sel.isColumns || this.gantt.isReadOnly()) {
            return;
        }

        var selFirstRowIdx = sel.getFirstRowIndex(),
            selLastRowIdx = sel.getLastRowIndex(),
            selectedRowCount = selLastRowIdx - selFirstRowIdx + 1,
            store = sel.view.dataSource,
            startIdx,
            endIdx,
            increment,
            record,
            columns = me.columns,
            colCount = columns.length,
            column, values, lastTwoRecords, prevValues, prevValue, x, y, i, j;

        // Single row, just duplicate values into extension
        if (selectedRowCount === 1) {
            values = me.getColumnValues(store.getAt(selFirstRowIdx));
        }

        // Multiple rows, take the numeric values from the closest two rows, calculate an array of differences and propagate it
        else {
            values = new Array(colCount);
            if (extension.rows < 0) {
                lastTwoRecords = [
                    store.getAt(selFirstRowIdx + 1),
                    store.getAt(selFirstRowIdx)
                ];
            } else {
                lastTwoRecords = [
                    store.getAt(selLastRowIdx - 1),
                    store.getAt(selLastRowIdx)
                ];
            }
            lastTwoRecords[0] = me.getColumnValues(lastTwoRecords[0]);
            lastTwoRecords[1] = me.getColumnValues(lastTwoRecords[1]);

            // The values array will be the differences between all numeric columns in the selection of the
            // closet two records.
            for (j = 0; j < colCount; j++) {
                x = lastTwoRecords[1][j];
                y = lastTwoRecords[0][j];
                if (!isNaN(x) && !isNaN(y)) {
                    values[j] = Number(x) - Number(y);
                }
            }
        }

        // Loop from end to start of extension area
        if (extension.rows < 0) {
            startIdx = extension.end.rowIdx;
            endIdx = extension.start.rowIdx - 1;
            increment = -1;
        } else {
            startIdx = extension.start.rowIdx;
            endIdx = extension.end.rowIdx + 1;
            increment = 1;
        }

        // When we copy complex values like dependencies or assignments, we will trigger view refresh
        // that is going to clear selection. In fact we don't need to clear selection, because nothing really
        // changed in locked view when we do it, so we unbind this private listener to prevent clearing the selection
        me.gantt.lockedGrid.view.un('beforerefresh', me.gantt.selModel.onBeforeViewRefresh, me.gantt.selModel);
        me.gantt.normalGrid.view.un('beforerefresh', me.gantt.selModel.onBeforeViewRefresh, me.gantt.selModel);

        // Replicate single selected row
        if (selectedRowCount === 1) {
            for (i = startIdx; i !== endIdx; i += increment) {
                record = store.getAt(i);

                if (record.isReadOnly()) continue;

                for (j = 0; j < colCount; j++) {
                    column = columns[j];
                    if (column.putRawData) {
                        column.putRawData(Ext.clone(values[j]), record);
                    } else if (column.dataIndex) {
                        record.set(column.dataIndex, values[j]);
                    }
                }
            }
        }
        // Add differences from closest two rows
        else {
            for (i = startIdx; i !== endIdx; i += increment) {
                record = store.getAt(i);

                if (record.isReadOnly()) continue;

                prevValues = me.getColumnValues(store.getAt(i - increment));
                for (j = 0; j < colCount; j++) {
                    column = columns[j];
                    if (column.dataIndex) {
                        prevValue = prevValues[j];
                        if (!isNaN(prevValue)) {
                            if (prevValue instanceof Date) {
                                record.set(column.dataIndex, Sch.util.Date.add(prevValue, 'ms', values[j]));
                            } else if (!Ext.isEmpty(prevValue)) {
                                record.set(column.dataIndex, Ext.coerce(Number(prevValue) + values[j], prevValue));
                            }
                        }
                    }
                }
            }
        }

        me.gantt.lockedGrid.view.on('beforerefresh', me.gantt.selModel.onBeforeViewRefresh, me.gantt.selModel);
        me.gantt.normalGrid.view.on('beforerefresh', me.gantt.selModel.onBeforeViewRefresh, me.gantt.selModel);
    },

    getColumnValues: function(record) {
        return Ext.Array.map(this.columns, function(column) {
            if (column.getRawData) {
                return column.getRawData(record);
            } else if (column.dataIndex) {
                return record.get(column.dataIndex);
            }
        });
    }
});