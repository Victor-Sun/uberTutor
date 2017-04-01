/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.assembla.com/spaces/bryntum/tickets/2470
// https://www.sencha.com/forum/showthread.php?307231
Ext.define('Sch.patches.TableView', {
    extend     : 'Sch.util.Patch',

    target     : 'Ext.view.Table',

    minVersion : '6.0.1',
    maxVersion : '6.0.1.9999',

    // Since the issue seems solved in ext 6.0.2 the patch simply copies few methods from it
    overrides  : {

        // in ext6.0.2 this method appears in cellediting plugin
        // but we use it here to not override two classes
        suspendCellEditing : function (actionable) {
            var me = actionable,
                editor = me.activeEditor;

            if (editor && editor.editing) {
                me.suspendedEditor = editor;
                me.suspendEvents();
                editor.suspendEvents();
                editor.cancelEdit(true);
                editor.resumeEvents();
                me.resumeEvents();
            }
        },

        // in ext6.0.2 this method appears in cellediting plugin
        // but we use it here to not override two classes
        resumeCellEditing : function (actionable, position) {
            var me = actionable,
                editor = me.activeEditor = me.suspendedEditor,
                result;

            if (editor) {
                me.suspendEvents();
                editor.suspendEvents();
                result = me.activateCell(position, true, true);

                // Sencha doesn't re-focus field if a refresh happens as a side effect while an editing is happening
                // Use case: Duration field with instant update
                // https://www.assembla.com/spaces/bryntum/tickets/2688-duration-editor-loses-focus-after-triggering-a-change-affecting-many-tasks/details#
                // Tested in Gantt 1013_duration.t.js
                editor.field && editor.field.focus(false, true);

                editor.resumeEvents();
                me.resumeEvents();
            }

            return result;
        },

        suspendActionableMode: function() {
            var me = this,
                actionables = me.grid.actionables,
                len = actionables.length,
                i;

            for (i = 0; i < len; i++) {
                var actionable = actionables[i];

                if (Ext.grid.plugin.CellEditing && actionable instanceof Ext.grid.plugin.CellEditing) {
                    me.suspendCellEditing(actionable);
                }
            }
        },

        resumeActionableMode: function(position) {
            var me = this,
                actionables = me.grid.actionables,
                len = actionables.length,
                i,
                activated;

            // Disable tabbability of elements within this view.
            me.toggleChildrenTabbability(false);

            for (i = 0; i < len; i++) {
                var actionable = actionables[i];

                if (!activated && Ext.grid.plugin.CellEditing && actionable instanceof Ext.grid.plugin.CellEditing) {
                    activated = me.resumeCellEditing(actionable, position);
                }

            }
            // If non of the Actionable responded, attempt to find a naturally focusable child element.
            if (!activated) {
                me.activateCell(position);
            }
        },

        saveFocusState: function() {
            var me = this,
                store = me.dataSource,
                actionableMode = me.actionableMode,
                navModel = me.getNavigationModel(),
                focusPosition = actionableMode ? me.actionPosition : navModel.getPosition(true),
                activeElement = Ext.Element.getActiveElement(true),
                focusCell = focusPosition && focusPosition.view === me && focusPosition.getCell(),
                refocusRow, refocusCol;

            // The navModel may return a position that is in a locked partner, so check that
            // the focusPosition's cell contains the focus before going forward.
            if (focusCell && focusCell.contains(activeElement)) {
                // Separate this from the instance that the nav model is using.
                focusPosition = focusPosition.clone();

                // While we deactivate the focused element, suspend focus processing on it.
                activeElement.suspendFocusEvents();

                // Suspend actionable mode.
                // Each Actionable must silently save its state
                // ready to resume when focus can be restored.
                if (actionableMode) {
                    me.suspendActionableMode();
                }
                // Clear position, otherwise the setPosition onthe other side
                // will be rejected as a no-op if the resumption position is logically
                // equivalent.
                else {
                    navModel.setPosition();
                }

                // Do not leave the element in tht state in case refresh fails, and restoration
                // closeure not called.
                activeElement.resumeFocusEvents();

                // The following function will attempt to refocus back in the same mode to the same cell
                // as it was at before based upon the previous record (if it's still inthe store), or the row index.
                return function() {
                    // If we still have data, attempt to refocus in the same mode.
                    if (store.getCount()) {

                        // Adjust expectations of where we are able to refocus according to what kind of destruction
                        // might have been wrought on this view's DOM during focus save.
                        refocusRow = Math.min(focusPosition.rowIdx, me.all.getCount() - 1);
                        refocusCol = Math.min(focusPosition.colIdx, me.getVisibleColumnManager().getColumns().length - 1);
                        focusPosition = new Ext.grid.CellContext(me).setPosition(
                                store.contains(focusPosition.record) ? focusPosition.record : refocusRow, refocusCol);

                        if (actionableMode) {
                            me.resumeActionableMode(focusPosition);
                        } else {
                            // Pass "preventNavigation" as true so that that does not cause selection.
                            navModel.setPosition(focusPosition, null, null, null, true);
                        }
                    }
                    // No rows - focus associated column header
                    else {
                        focusPosition.column.focus();
                    }
                };
            }
            return Ext.emptyFn;
        },

        // this method is part of ext6.0.2 but pulling it would force us to
        // include a lot of related code as well ..so we just use this dummy instead
        activateCell : function () {
            return true;
        },

        // #2628 - Vertical scroll is set to 0 when resizing event in vertical mode
        // In 6.0.1+ resizing start will focus event node and this will trigger actionable mode on view and focus
        // first row in view
        onFocusEnter : function (e) {
            if (!Ext.fly(e.target).hasCls('sch-event')) {
                this.callParent(arguments);
            }
        }
    }
});