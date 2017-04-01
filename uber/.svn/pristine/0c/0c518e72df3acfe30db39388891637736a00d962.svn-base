/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.column.Sequence
@extends Ext.grid.column.Column

A "calculated" column which displays the sequential position of the task in the project. 
See {@link Gnt.model.Task#getSequenceNumber} for details.


*/
Ext.define("Gnt.column.Sequence", {
    extend      : "Ext.grid.column.Column",
    alias       : [
        "widget.sequencecolumn",
        "widget.ganttcolumn.sequence"
    ],

    mixins      : ['Gnt.mixin.Localizable'],

    /**
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

            - text : '#'
     */

    /**
     * @cfg {Number} width The width of the column.
     */
    width       : 40,

    /**
     * @cfg {String} align The alignment of the text in the column.
     */
    align       : 'right',

    sortable    : false,
    dataIndex   : 'index',

    constructor : function (config) {
        config = config || {};

        this.text   = config.text || this.L('text');

        this.callParent(arguments);
    },

    renderer    : function (value, meta, task) {
        return task.getSequenceNumber();
    }
});
