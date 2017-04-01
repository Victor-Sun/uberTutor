/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.column.SchedulingMode
@extends Ext.grid.column.Column

A Column showing the `SchedulingMode` field of a task. The column is editable when adding a
`Sch.plugin.TreeCellEditing` plugin to your Gantt panel. The overall setup will look like this:

    var gantt = Ext.create('Gnt.panel.Gantt', {
        height      : 600,
        width       : 1000,

        columns         : [
            ...
            {
                xtype       : 'schedulingmodecolumn',
                width       : 80
            }
            ...
        ],

        plugins             : [
            Ext.create('Sch.plugin.TreeCellEditing', {
                clicksToEdit: 1
            })
        ],
        ...
    })


*/
Ext.define("Gnt.column.SchedulingMode", {
    extend              : 'Ext.grid.column.Column',

    requires            : ['Gnt.field.SchedulingMode'],
    mixins              : ['Gnt.column.mixin.TaskFieldColumn'],

    alias               : [
        'widget.schedulingmodecolumn',
        'widget.ganttcolumn.schedulingmode'
    ],

    /**
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

            - text : 'Mode'
     */

    /**
     * @cfg {Number} width The width of the column.
     */
    width               : 100,

    /**
     * @cfg {String} align The alignment of the text in the column.
     */
    align               : 'left',

    /**
     * @cfg {Array} data A 2-dimensional array used for editing in combobox. The first item of inner arrays will be treated as "value" and 2nd - as "display"
     */
    data                : null,

    fieldProperty       : 'schedulingModeField',

    editor              : 'schedulingmodefield',

    defaultEditor       : 'schedulingmodefield',

    instantUpdate       : false,

    initComponent : function () {
        this.initTaskFieldColumn({
            store : this.data
        });

        this.callParent(arguments);
    }

});
