/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.sencha.com/forum/showthread.php?308916-Crash-when-hiding-columns-spreadsheet-model&p=1128276#post1128276
Ext.define('Gnt.patches.LockingView', {
    extend      : 'Sch.util.Patch',

    target      : 'Ext.grid.locking.View',
    minVersion  : '6.0.0',

    overrides   : {
        getCellByPosition: function(pos, returnDom) {
            if (pos && !pos.column) return null;

            return this.callParent(arguments);
        },

        onCellDeselect: function(cellContext) {
            if (cellContext && !cellContext.column) return;

            return this.callParent(arguments);
        }
    }
});