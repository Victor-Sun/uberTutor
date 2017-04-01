/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.selection.SpreadsheetModel', {
    extend  : 'Ext.grid.selection.SpreadsheetModel',

    uses    : [
        'Gnt.column.WBS'
    ],

    alias   : 'selection.gantt_spreadsheet',

    privates : {
        getNumbererColumnConfig : function() {
            var me = this;

            return {
                xtype        : 'wbscolumn',
                text         : '&nbsp;',
                width        : me.rowNumbererHeaderWidth,
                editRenderer : '&#160;',
                tdCls        : me.rowNumbererTdCls,
                cls          : me.rowNumbererHeaderCls,
                // disabling all interactions
                sortable     : false,
                //resizable  : false,
                draggable    : false,
                hideable     : false,
                menuDisabled : true,
                // to remove possibility to unlock column
                lockable     : false,

                ignoreExport : true,

                locked       : true
            };
        }
    }
});