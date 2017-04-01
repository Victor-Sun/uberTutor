/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.column.WBS
@extends Ext.grid.column.Date

A "calculated" Column which displays the WBS (Work Breakdown Structure) for the tasks - the position of the task in the project tree structure.
*/
Ext.define("Gnt.column.WBS", {
    extend      : "Ext.grid.column.Column",
    alias       : [
        "widget.wbscolumn",
        "widget.ganttcolumn.wbs"
    ],
    mixins      : ['Gnt.mixin.Localizable'],

    /**
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

            - text : 'WBS'
     */

    /**
     * @cfg {Number} width The width of the column.
     */
    width       : 40,

    /**
     * @cfg {String} align The alignment of the text in the column.
     */
    align       : 'left',
    sortable    : false,

    constructor : function (config) {
        config = config || {};        

        this.text   = config.text || this.L('text');

        this.callParent(arguments);

        this.tdCls = (this.tdCls || '') + ' gnt-wbs-cell';
    },

    renderer    : function (value, meta, task) {
        return task.getWBSCode();
    }
});
