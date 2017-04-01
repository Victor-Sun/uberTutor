/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.sencha.com/forum/showthread.php?305868-Ext.util.TSV-decode-goes-infinite-loop
Ext.define('Gnt.patches.DelimitedValue', {
    extend: 'Sch.util.Patch',

    target: 'Ext.util.DelimitedValue',

    minVersion: '6.0.0',

    overrides: {
        decode: function (input) {
            if (input === "") {
                return [];
            }

            return this.callParent(arguments);
        }
    }
});