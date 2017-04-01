/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.sencha.com/forum/showthread.php?324990-Ext-grid-column-Check-not-checking-presence-of-config-variable
Ext.define('Gnt.patches.CheckColumn', {
    extend : 'Sch.util.Patch',

    target   :'Ext.grid.column.Check',

    minVersion : '6.2.0',

    overrides : {
        constructor : function(config) {
            this.callParent([config || {}]);
        }
    }
});