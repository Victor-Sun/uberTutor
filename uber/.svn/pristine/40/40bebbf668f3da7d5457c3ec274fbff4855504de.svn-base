/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 @class Sch.widget.ColumnPicker
 @private
 @extends Ext.form.field.ComboBox

 Columnpicker widget for picking columns from a panel.
 */

Ext.define('Sch.widget.ColumnPicker', {
    extend            : 'Ext.form.field.ComboBox',

    requires          : [
        'Ext.data.Store'
    ],

    multiSelect       : true,
    valueField        : 'id',
    displayField      : 'name',

    forceSelection    : true,

    editable          : false,

    listConfig        : {
        cls : 'sch-columnpicker-list'
    },

    /**
     * @cfg {Ext.grid.column.Column[]} An array of columns to choose from
     */
    columns           : null,

    /**
     * @cfg {String} columnEmptyText Text to show when column text is empty
     */
    columnEmptyText   : null,

    columnEmptyRegExp : /&(nbsp|#160);/,

    initComponent : function () {

        this.store = new Ext.data.Store({
            proxy  : 'memory',
            fields : ['id', 'name', 'column'],
            data   : this.processColumns(this.columns)
        });

        this.callParent(arguments);
    },

    processColumns : function (columns) {
        var me      = this,
            data    = [],
            value   = [];

        Ext.Array.each(columns, function (column) {

            data.push({
                id     : column.id,
                name   : me.getColumnTitle(column),
                column : column
            });

            if (!column.isHidden()) {
                value.push(column.id);
            }
        });

        this.value = this.value || value;

        return data;
    },


    getColumnTitle : function (column) {
        var me = this;
        return column.text.match(me.columnEmptyRegExp) ? me.columnEmptyText : column.text;
    },


    getSelectedColumns : function () {

        var me    = this,
            value = me.getValue();

        if (!Ext.isArray(value)) {
            value = [value];
        }

        return Ext.Array.map(value, function (id) {
            return me.store.getById(id).get('column');
        });
    }
});
