/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// We patched grid navigation model to not focus rows in normal view in order to save scroll in IE
// this is why under some conditions keyevent contain wrong view and target. This can break navigation
// 2117_key_navigation
Ext.define('Sch.patches.NavigationModel', {
    extend      : 'Sch.util.Patch',

    target      : 'Ext.grid.NavigationModel',

    minVersion  : '6.0.0',

    overrides   : {
        setPosition: function(recordIndex, columnIndex, keyEvent, suppressEvent, preventNavigation) {
            var me = this;
            // We need to only handle pageup/pagedown keys, because they call setPosition(record, null,...) which trigger
            // special path that leads to error when there's lastFocused property that holds
            // column from normal view and current view is locked
            // #2428
            if (Ext.isIE && keyEvent && (keyEvent.getKey() === keyEvent.PAGE_DOWN || keyEvent.getKey() === keyEvent.PAGE_UP)) {
                var lastFocused = me.lastFocused;
                if (keyEvent.view.isLockedView && lastFocused && keyEvent.view.getVisibleColumnManager().indexOf(lastFocused.column) === -1) {
                    keyEvent.view = keyEvent.view.lockingPartner;
                }
            }

            // TODO: get rid of this when 6.0.1 support is dropped
            // https://www.assembla.com/spaces/bryntum/tickets/2657
            // setPosition now will also set actionableMode to false if we changed position
            if (Ext.getVersion().equals('6.0.1.250')) {
                me.patchedSetPosition.apply(this, arguments);
            } else {
                return me.callParent(arguments);
            }
        },

        // TODO: get rid of this when 6.0.1 support is dropped
        // #2657 - Cannot edit locked cell after click in normal view
        patchedSetPosition : function (recordIndex, columnIndex, keyEvent, suppressEvent, preventNavigation) {
            var me = this,
                view, scroller, selModel, dataSource, columnManager, newRecordIndex, newColumnIndex, newRecord, newColumn,
                clearing = recordIndex == null && columnIndex == null,
                isClear = me.record == null && me.recordIndex == null && me.item == null;

            // Work out the view we are operating on.
            // If they passed a CellContext, use the view from that.
            // Otherwise, use the view injected into the event by Ext.view.View#processEvent.
            // Otherwise, use the last focused view.
            // Failing that, use the view we were bound to.
            if (recordIndex && recordIndex.isCellContext) {
                view = recordIndex.view;
            } else if (keyEvent && keyEvent.view) {
                view = keyEvent.view;
            } else if (me.lastFocused) {
                view = me.lastFocused.view;
            } else {
                view = me.view;
            }
            // In case any async focus was requested before this call.
            view.getFocusTask().cancel();
            // Return if the view was destroyed between the deferSetPosition call and now, or if the call is a no-op
            // or if there are no items which could be focused.
            if (view.destroyed || !view.refreshCounter || !view.ownerCt || clearing && isClear || !view.all.getCount()) {
                return;
            }
            selModel = view.getSelectionModel();
            dataSource = view.dataSource;
            columnManager = view.getVisibleColumnManager();
            // If a CellContext is passed, use it.
            // Passing null happens on blur to remove focus class.
            if (recordIndex && recordIndex.isCellContext) {
                newRecord = recordIndex.record;
                newRecordIndex = recordIndex.rowIdx;
                newColumnIndex = Math.min(recordIndex.colIdx, columnManager.getColumns().length - 1);
                newColumn = columnManager.getColumns()[newColumnIndex];
                // If the record being focused is not available (eg, after a removal), then go to the same position
                if (dataSource.indexOf(newRecord) === -1) {
                    scroller = view.getScrollable();
                    // Change recordIndex so that the "No movement" test is bypassed if the record is not found
                    me.recordIndex = -1;
                    // If the view will not jump upwards to bring the next row under the mouse as expected
                    // because it's at the end, focus the previous row
                    if (scroller.getPosition().y >= scroller.getMaxPosition().y - view.all.last(true).offsetHeight) {
                        recordIndex.rowIdx--;
                    }
                    newRecordIndex = Math.min(recordIndex.rowIdx, dataSource.getCount() - 1);
                    newRecord = dataSource.getAt(newRecordIndex);
                }
            } else {
                // Both axes are null, we defocus
                if (clearing) {
                    newRecord = newRecordIndex = null;
                } else {
                    // AbstractView's default behaviour on focus is to call setPosition(0);
                    // A call like this should default to the last column focused, or column 0;
                    if (columnIndex == null) {
                        columnIndex = me.lastFocused ? me.lastFocused.column : 0;
                    }
                    if (typeof recordIndex === 'number') {
                        newRecordIndex = Math.max(Math.min(recordIndex, dataSource.getCount() - 1), 0);
                        newRecord = dataSource.getAt(recordIndex);
                    }
                    // row is a Record
                    else if (recordIndex.isEntity) {
                        newRecord = recordIndex;
                        newRecordIndex = dataSource.indexOf(newRecord);
                    }
                    // row is a grid row
                    else if (recordIndex.tagName) {
                        newRecord = view.getRecord(recordIndex);
                        newRecordIndex = dataSource.indexOf(newRecord);
                        if (newRecordIndex === -1) {
                            newRecord = null;
                        }
                    } else {
                        if (isClear) {
                            return;
                        }
                        clearing = true;
                        newRecord = newRecordIndex = null;
                    }
                }
                // Record position was successful
                if (newRecord) {
                    // If the record being focused is not available (eg, after a sort), then go to 0,0
                    if (newRecordIndex === -1) {
                        // Change recordIndex so that the "No movement" test is bypassed if the record is not found
                        me.recordIndex = -1;
                        newRecord = dataSource.getAt(0);
                        newRecordIndex = 0;
                        columnIndex = null;
                    }
                    // No columnIndex passed, and no previous column position - default to column 0
                    if (columnIndex == null) {
                        if (!(newColumn = me.column)) {
                            newColumnIndex = 0;
                            newColumn = columnManager.getColumns()[0];
                        }
                    } else if (typeof columnIndex === 'number') {
                        newColumn = columnManager.getColumns()[columnIndex];
                        newColumnIndex = columnIndex;
                    } else {
                        newColumn = columnIndex;
                        newColumnIndex = columnManager.indexOf(columnIndex);
                    }
                } else {
                    clearing = true;
                    newColumn = newColumnIndex = null;
                }
            }

            // If we are in actionable mode and focusing a cell, exit actionable mode at the requested position
            if (view.actionableMode && !clearing) {
                var newPosition = new Ext.grid.CellContext(view).setPosition(newRecord, newColumn);
                // in 6.0.2, where this fix is originating, pressing ENTER in editor doesn't make it to loose focus
                // in versions prior to 6.0.2 it does, so we need to focus current position again so exiting
                // actionable mode would save focused position and could restore it
                // https://www.assembla.com/spaces/bryntum/tickets/2716
                // covered by 090_cell_editing in gantt
                me.focusPosition(newPosition);
                return view.ownerGrid.setActionableMode(false, newPosition);
            }
            // No movement; just ensure the correct item is focused and return early.
            // Do not push current position into previous position, do not fire events.
            if (newRecordIndex === me.recordIndex && newColumnIndex === me.columnIndex && view === me.position.view) {
                return me.focusPosition(me.position);
            }
            if (me.cell) {
                me.cell.removeCls(me.focusCls);
            }
            // Track the last position.
            // Used by SelectionModels as the navigation "from" position.
            me.previousRecordIndex = me.recordIndex;
            me.previousRecord = me.record;
            me.previousItem = me.item;
            me.previousCell = me.cell;
            me.previousColumn = me.column;
            me.previousColumnIndex = me.columnIndex;
            me.previousPosition = me.position.clone();
            // Track the last selectionStart position to correctly track ranges (i.e., SHIFT + selection).
            me.selectionStart = selModel.selectionStart;
            // Set our CellContext to the new position
            me.position.setAll(view, me.recordIndex = newRecordIndex, me.columnIndex = newColumnIndex, me.record = newRecord, me.column = newColumn);
            if (clearing) {
                me.item = me.cell = null;
            } else {
                me.focusPosition(me.position, preventNavigation);
            }
            // Legacy API is that the SelectionModel fires focuschange events and the TableView fires rowfocus and cellfocus events.
            if (!suppressEvent) {
                selModel.fireEvent('focuschange', selModel, me.previousRecord, me.record);
                view.fireEvent('rowfocus', me.record, me.item, me.recordIndex);
                view.fireEvent('cellfocus', me.record, me.cell, me.position);
            }
            // If we have moved, fire an event
            if (keyEvent && !preventNavigation && me.cell !== me.previousCell) {
                me.fireNavigateEvent(keyEvent);
            }
        }
    }
});