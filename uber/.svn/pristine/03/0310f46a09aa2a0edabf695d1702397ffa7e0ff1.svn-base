/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Sch.patches.CellEditor', {
    extend : 'Sch.util.Patch',

    target   : 'Ext.grid.CellEditor',

    minVersion : '6.0.2',

    overrides : {
        // Sencha issue: https://www.sencha.com/forum/showthread.php?305120&p=1135354#post1135354
        // Covered by 1002_tabbing_3
        onEditComplete: function(remainVisible, canceling) {
            var me = this,
                activeElement = Ext.Element.getActiveElement();
            me.editing = false;
            // Must refresh the boundEl in case DOM has been churned during edit.
            me.boundEl = me.context.getCell();

            // PATCH
            if (me.boundEl) {
                // Restore cells content to visibility
                me.restoreCell();
                // IF we are just terminating, and NOT being terminated due to focus
                // having moved out of this editor, then we must prevent any upcoming blur
                // from letting focus fly out of the view.
                // onFocusLeave will have no effect because the editing flag is cleared.
                if (me.boundEl.contains(activeElement) && me.boundEl.dom !== activeElement) {
                    me.boundEl.focus();
                }
            }
            // END PATCH

            // When being asked to process edit completion, if we are hiding
            // move the el into detached body to protect it from garbage collection.
            if (!remainVisible) {
                me.cacheElement();
            }
            me.superclass.onEditComplete.apply(me, arguments);
            // Do not rely on events to sync state with editing plugin,
            // Inform it directly.
            if (canceling) {
                me.editingPlugin.cancelEdit(me);
            } else {
                me.editingPlugin.onEditComplete(me, me.getValue(), me.startValue);
            }
        }
    }
});