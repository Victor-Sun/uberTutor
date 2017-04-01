/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.sencha.com/forum/showthread.php?305782-TreeViewDragDrop-cannot-be-disabled
Ext.define('Gnt.patches.TreeViewDragDrop', {
    extend : 'Sch.util.Patch',

    target     : 'Ext.tree.plugin.TreeViewDragDrop',
    minVersion : '6.0.0',

    overrides : {
        disable : function () {
            this.callParent(arguments);

            this.dragZone && this.dragZone.lock();
            this.dropZone && this.dropZone.lock();
        },

        enable  : function () {
            this.callParent(arguments);

            this.dragZone && this.dragZone.unlock();
            this.dropZone && this.dropZone.unlock();
        }
    }
});