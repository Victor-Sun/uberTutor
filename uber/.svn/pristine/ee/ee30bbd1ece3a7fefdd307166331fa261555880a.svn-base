/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/*
 * @class Gnt.column.AssignmentUnits
 * @extends Ext.grid.Column
 * @private
 * Private class used inside Gnt.widget.AssignmentGrid.
 */
Ext.define("Gnt.column.AssignmentUnits", {
    extend   : "Ext.grid.column.Number",
    mixins   : [ "Gnt.mixin.Localizable" ],
    requires : [
        "Gnt.field.Percent"
    ],

    alias  : "widget.assignmentunitscolumn",

    dataIndex : 'Units',
    format    : '0 %',
    align     : 'left',

    editor : {
        xtype         : 'percentfield',
        minValue      : 0,
        maxValue      : undefined,
        step          : 10,
        // https://www.sencha.com/forum/showthread.php?305411-Tabbing-in-grid-picker-stops-editing
        // IE10/9 cannot handle this correctly and will stop editing
        selectOnFocus : Ext.isIE ? false : true
    },

    constructor : function (config) {
        config = config || {};

        this.text  = config.text || this.L('text');
        this.scope = this;

        this.callParent(arguments);
    },

    // HACK, without 3 arguments the grid doesn't behave sanely
    renderer : function (value, meta, record) {
        if (value) return Ext.util.Format.number(value, this.format);
    }
});
