/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.sencha.com/forum/showthread.php?308572-quot-cacheDeactivatedEditors-quot-error
Ext.define('Sch.patches.CellEditing', {
    extend : 'Sch.util.Patch',

    target     : 'Ext.grid.plugin.CellEditing',
    // seems to be fixed in 6.0.2
    maxVersion : '6.0.2',

    // TODO: investigate if this should be moved to TreeCellEditing instead
    overrides : {
        activateCell : function (position) {

            var retVal = this.callParent(arguments);

            // When you remove row with active editor you may face a race condition, when
            // garbage collection is triggered to soon. By that time editor is removed from document body, but not yet
            // moved to detached body element. We raise this flag to let garbage collector know editor should not be
            // removed yet. We need to raise it on editor node and all nested nodes to keep editor intact

            // if beforeedit was cancelled, retVal will be undefined
            // #2715, covered by 100_cell_editing
            if (!!retVal) {
                var editor = this.getEditor(position.record, position.column);

                if (editor) {
                    // Do not move this code to separate override, or make sure that button 'add new task' in advanced gantt
                    // focuses editor.
                    editor.el.skipGarbageCollection = true;

                    editor.el.select('*').each(function (node) {
                        Ext.get(node).skipGarbageCollection = true;
                    });
                }
            }

            return retVal;
        }
    }
});