/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.sencha.com/forum/showthread.php?301772-Wrong-value-selected-in-combobox-editor
// IMPORTANT: this bug still exists in Ext6, but this fix doesn't work and tests seem to be green.
// Do not remove unless you're 100% sure
Ext.define('Gnt.patches.CellEditing', {
    extend : 'Sch.util.Patch',

    target     : 'Ext.grid.plugin.CellEditing',
    minVersion : '5.1.1',
    maxVersion : '6.0.1.250',

    overrides : {
        showEditor: function(ed, context, value) {
            // clean lastSelectedRecords cache for combobox if record was changed
            if (ed.context && ed.context.record !== context.record && 
                ed.field instanceof Ext.form.field.ComboBox) {
                ed.field.lastSelectedRecords = null;
            }
            this.callParent(arguments);
        },

        cancelEdit: function(activeEd) {
            var me = this,
                context = me.context;
            // This is in response to the CellEditor firing a canceledit event.
            if (activeEd && activeEd.isCellEditor) {
                me.context.value = activeEd.context.value = ('editedValue' in activeEd) ? activeEd.editedValue : activeEd.getValue();
                // Editing flag cleared in superclass.
                // canceledit event fired in superclass.

                // PATCH
                // canceledit is fired with wrong context
                // https://fiddle.sencha.com/#fiddle/19jr
                // 1002_tabbing_4
                var currentContext = me.context;
                me.context = activeEd.context;
                me.callParent(arguments);
                me.context = currentContext;
                // PATCH END

                // Clear our current editing context.
                // We only do this if we have not already started editing a new context.
                if (activeEd.context === context) {
                    me.setActiveEditor(null);
                    me.setActiveColumn(null);
                    me.setActiveRecord(null);
                } else // Re-instate editing flag after callParent
                {
                    me.editing = true;
                }
            } else // This is a programmatic call to cancel any active edit
            {
                activeEd = me.getActiveEditor();
                if (activeEd && activeEd.field) {
                    activeEd.cancelEdit();
                }
            }
        }
    }
});