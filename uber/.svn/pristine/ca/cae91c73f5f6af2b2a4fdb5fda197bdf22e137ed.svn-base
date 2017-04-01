/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// http://www.sencha.com/forum/showthread.php?295802-5.1-Knightly-Picker-collapses-on-ENTER-key&p=1080010#post1080010
Ext.define('Gnt.patches.CellEditor', {
    extend : 'Sch.util.Patch',

    target     : 'Ext.grid.CellEditor',

    minVersion : '6.0.0',
    maxVersion : '6.0.2',

    overrides : {
        destroy : function () {
            clearTimeout(this.restoreTimer);
            this.callParent(arguments);
        },
        // 1017_duration_editor_place in IE9, Ext6.0.0
        // Sencha fixed this in 6.0.1
        onViewRefresh : function(view) {
            var me = this,
                dom = me.el && me.el.dom,
                cell,
                context = me.context;
            if (dom) {
                // Update the context with the possibly new contextual data
                // (refresh might have been caused by a sort or column move etc)
                cell = view.getCellByPosition(context, true);
                // If the refresh was caused by eg column removal, the cell will not exist.
                // In this case, terminate the edit.
                if (!cell) {
                    me.allowBlur = me.wasAllowBlur;
                    me.completeEdit();
                    Ext.getDetachedBody().dom.appendChild(dom);
                    return;
                }
                context.node = view.getNode(context.record);
                context.row = view.getRow(context.record);
                context.cell = cell;
                context.rowIdx = view.indexOf(context.row);
                cell.insertBefore(dom, cell.firstChild);
                me.boundEl = me.container = Ext.get(cell);
                me.realign(true);
                // If the view was refreshed while we were editing, replace it.
                // On IE, the blur event will fire asynchronously, so we must leave
                // allowBlur as false for a very short while longer.
                // After which we reset it, and refocus the field.
                if (me.editing) {
                    if (Ext.isIE) {
                        me.restoreTimer = Ext.defer(function () {
                            // May have been destroyed immediately after refreshing!?
                            if (!me.destroyed) {
                                me.allowBlur = me.wasAllowBlur;
                                // PATCH: only enable defered focus if current editor is active one,
                                // otherwise it'll lead to stopped editing.
                                // Covered by 1002_tabbing_4
                                me.editingPlugin.getActiveEditor() === me && me.field.focus();
                            }
                        }, 10);
                    } else {
                        me.allowBlur = me.wasAllowBlur;
                        me.field.focus();
                    }
                }
            }
        }
    }
});