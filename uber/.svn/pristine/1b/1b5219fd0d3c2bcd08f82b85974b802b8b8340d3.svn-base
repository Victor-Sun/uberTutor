/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.sencha.com/forum/showthread.php?305782-TreeViewDragDrop-cannot-be-disabled
Ext.define('Gnt.patches.SpreadsheetModel', {
    extend : 'Sch.util.Patch',

    target   :'Ext.grid.selection.SpreadsheetModel',

    minVersion : '6.0.0',

    applyFn : function () {
        var overrides = {
            privates    : {
                // prevent selecting cells in normal view
                onMouseMove : function (e, target) {
                    // if mouse is moving over scheduling view - do nothing
                    if (!Ext.fly(target).up('.sch-ganttview')) {
                        this.callParent(arguments);
                    }
                },

                // do not start drag selection on click in dragdrop column
                handleMouseDown : function(view, cellNode, cellIndex, record) {
                    // prevent selection start on click in normal view
                    if (!(view instanceof Gnt.view.Gantt)) {
                        var isRowReorderCell = cellNode.className.indexOf('sch-gantt-column-dragdrop') >= 0;

                        if (isRowReorderCell) {
                            // dragdrop plugin need a selection to work on, also
                            this.selectRows([record], this.isSelected(record));
                        } else {
                            this.callParent(arguments);
                        }
                    }
                }
            }
        };

        if (Ext.getVersion().isLessThan('6.0.2')) {
            overrides.select = function(records, keepExisting, suppressEvent) {
                // API docs are inherited
                var me = this,
                    sel = me.selected,
                    view = me.view,
                    store = view.dataSource,
                    len, i, record,
                    changed = false;
                // Ensure selection object is of the correct type
                if (!sel || !sel.isRows || sel.view !== view) {
                    me.resetSelection(true);
                    sel = me.selected = new Ext.grid.selection.Rows(view);
                } else if (!keepExisting) {
                    sel.clear();
                }
                if (!Ext.isArray(records)) {
                    records = [
                        records
                    ];
                }
                len = records.length;
                for (i = 0; i < len; i++) {
                    record = records[i];
                    if (typeof record === 'number') {
                        record = store.getAt(record);
                    }
                    if (!sel.contains(record)) {
                        sel.add(record);
                        changed = true;
                    }
                }
                if (changed) {
                    me.updateHeaderState();
                    // here was a bug in ext prior to 6.0.2
                    if (!suppressEvent) {
                        me.fireSelectionChange();
                    }
                }
            };
        }

        Ext.override(Ext.grid.selection.SpreadsheetModel, overrides);
    }
});