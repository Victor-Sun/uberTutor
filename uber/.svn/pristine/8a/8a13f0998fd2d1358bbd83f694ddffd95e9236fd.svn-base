/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * Adds clipboard support to a gantt panel.
 *
 * *Note that the grid must use the {@link Ext.grid.selection.SpreadsheetModel spreadsheet selection model} to utilize this plugin.*
 *
 * This class supports the following `{@link #formats formats}`
 * for grid data:
 *
 *  * `text` - Cell content stripped of HTML tags. Data from clipboard cannot be pasted into gantt in this format.
 *  * `raw` - Underlying field values based on `dataIndex`. Alternatively you can define getRawData/putRawData on
 *  column class to implement any special logic for copying/pasting complex values. For example refer to
 *  {@link Gnt.column.ResourceAssignment#getRawData} and {@link Gnt.column.ResourceAssignment#putRawData}
 *
 * Only `text` format is valid for the `{@link Ext.grid.plugin.Clipboard#system system}`
 * clipboard format.
 */
Ext.define('Gnt.plugin.Clipboard', {
    extend : 'Ext.grid.plugin.Clipboard',
    alias  : 'plugin.gantt_clipboard',

    requires : [
        'Gnt.patches.DelimitedValue',
        'Gnt.patches.AbstractClipboard'
    ],

    memory : 'raw',

    formats : {
        raw : {
            get : 'getRawData',
            put : 'putRawData'
        }
    },

    // TODO: implement conversion from visible value to data value
    putTextData : function (data, format) {
        return;
    },

    /**
     * Will copy raw values to clipboard
     * @param {String} format Value of {@link #source} config
     * @param {Boolean} erase When true, values in original record will be replaced with field defaults
     */
    getRawData : function (format, erase) {
        var me       = this,
            selModel = me.getCmp().getSelectionModel(),
            ret      = [],
            isRaw    = format === 'raw',
            isText   = format === 'text',
            lastRecord, row;

        // Embed info about what is being copied
        ret.schedulingFields = {};

        selModel.getSelected().eachCell(function (cellContext) {
            var column    = cellContext.column,
                view      = cellContext.column.getView(),
                record    = cellContext.record,
                dataIndex = column.dataIndex,
                data;

            // Ignore columns that we don't want to/cannot copy (check column, row numberer, AddNew, dragcolumn etc.)
            if (!me.isColumnCopyPasteable(column, format)) {
                return;
            }

            // if new record cells started
            if (lastRecord !== record) {
                lastRecord = record;
                // start new row array
                ret.push(row = []);
            }

            // Start/End/Duration columns might need special treatment when pasting so we mark if they copied
            // We need to do it only for the first row
            if (ret.length < 2) {
                if (Gnt.column.StartDate && column instanceof Gnt.column.StartDate) {
                    ret.schedulingFields.startDate = 1;
                } else if (Gnt.column.EndDate && column instanceof Gnt.column.EndDate) {
                    ret.schedulingFields.endDate = 1;
                } else if (Gnt.column.Duration && column instanceof Gnt.column.Duration) {
                    ret.schedulingFields.duration = 1;
                }
            }

            if (isRaw) {
                if (column.getRawData) {
                    data = column.getRawData(record);
                } else if (dataIndex) {
                    data = record.data[dataIndex];
                }
            } else {
                // Try to access the view node.
                var viewNode = view.all.item(cellContext.rowIdx);
                // If we could not, it's because it's outside of the rendered block - recreate it.
                if (!viewNode) {
                    viewNode = Ext.fly(view.createRowElement(record, cellContext.rowIdx));
                }

                var cell = viewNode.down(column.getCellInnerSelector());
                data = cell.dom.innerHTML;

                if (isText) {
                    data = Ext.util.Format.stripTags(data);
                }
            }

            row.push(data);

            if (erase && dataIndex) {
                record.set(dataIndex, record.getField(dataIndex).getDefaultValue());
            }
        });

        return ret;
    },

    getCellData : function (format, erase) {
        return Ext.util.TSV.encode(this.getRawData(format, erase));
    },

    // @private
    // Returns if the provided column can be copied or pasted in the provided format
    isColumnCopyPasteable : function (column, format) {
        return !column.ignoreExport && (format != 'raw' || column.dataIndex || column.getRawData);
    },

    /**
     * Will paste values from clipboard
     * @param {Object} data Data to paste
     * @param {String} format Value of {@link #source} config. If clipboard contain some data for few formats - will
     * be called few times during one paste
     */
    putRawData : function (data, format) {
        var me          = this,
            recCount    = data.length,
            view        = this.getCmp().getView(),
            maxRowIdx   = view.dataSource.getCount() - 1,
            maxColIdx   = view.getVisibleColumnManager().getColumns().length - 1,
            navModel    = view.getNavigationModel(),
            destination = navModel.getPosition(),
            sourceColIdx, dataIndex, destinationStartColumn,
            currentColumn, currentValue, dataObject, field, start, end;

        if (!destination || this.getCmp().isReadOnly()) return;

        destination = new Ext.grid.CellContext(view).setPosition(destination.record, destination.column);

        destinationStartColumn = destination.colIdx;

        for (var sourceRowIdx = 0; sourceRowIdx < recCount; sourceRowIdx++) {
            var row        = data[sourceRowIdx];
            var targetTask = destination.record;
            dataObject     = {};

            // skip target record if it's read only
            if (targetTask.isReadOnly()) continue;

            // Collect new values in dataObject
            for (sourceColIdx = 0; destination.colIdx < maxColIdx && sourceColIdx < row.length;) {
                currentColumn = destination.column;
                currentValue  = row[sourceColIdx];

                // Ignore columns that we don't want to/cannot copy (check column, row numberer, AddNew, dragcolumn etc.)
                if (me.isColumnCopyPasteable(currentColumn, format)) {
                    var skip;

                    if (data.schedulingFields.startDate && data.schedulingFields.endDate) {
                        skip = currentColumn.fieldProperty === 'durationField' ||
                            currentColumn.fieldProperty === 'startDateField' ||
                            currentColumn.fieldProperty === 'endDateField';

                        if (currentColumn.fieldProperty === 'startDateField') start = currentValue;
                        if (currentColumn.fieldProperty === 'endDateField') end = currentValue;
                    }

                    dataIndex = currentColumn.dataIndex;

                    if (!skip) {
                        if (currentColumn.putRawData) {
                            currentColumn.putRawData(currentValue, destination.record);
                        } else if (dataIndex && dataIndex !== 'index') {
                            field = currentColumn.field;

                            if (field) {
                                if (!field.getErrors(currentValue).length) {
                                    dataObject[dataIndex] = currentValue;
                                }
                            } else {
                                dataObject[dataIndex] = currentValue;
                            }
                        }
                    }

                    sourceColIdx++;
                }

                // If we are at the end of the destination row, break the column loop.
                if (destination.colIdx === maxColIdx) {
                    break;
                }

                destination.setColumn(destination.colIdx + 1);
            }

            targetTask.beginEdit();

            // Update the record in one go.
            targetTask.set(dataObject);

            // setting start + end, need to be done manually
            if (start !== undefined && end !== undefined) {
                targetTask.setStartEndDate(start, end);
            }

            targetTask.endEdit();

            // If we are at the end of the destination store, break the row loop.
            if (destination.rowIdx === maxRowIdx) {
                break;
            }

            // Jump to next row in destination
            destination.setPosition(destination.rowIdx + 1, destinationStartColumn);
        }
    }
});