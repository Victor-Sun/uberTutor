/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// covered by multiple tests, e.g. 061_dragdrop_filtered_timeaxis, 069_scrollintoview, 2101_view
Ext.define('Sch.patches.RowSynchronizer', {
    extend     : 'Sch.util.Patch',

    target     : 'Ext.grid.locking.RowSynchronizer',

    minVersion : '6.0.0',

    maxVersion : '6.0.1',

    overrides  : {
        finish : function (other) {
            if (!other) return;

            return this.callParent(arguments);
        }
    }
});
