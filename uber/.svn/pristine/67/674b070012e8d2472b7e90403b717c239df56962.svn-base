/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.column.PercentDone
@extends Ext.grid.column.Number

A Column representing the `PercentDone` field of the task. The column is editable when adding a
`Sch.plugin.TreeCellEditing` plugin to your Gantt panel. The overall setup will look like this:

    var gantt = Ext.create('Gnt.panel.Gantt', {
        height      : 600,
        width       : 1000,

        columns         : [
            ...
            {
                xtype       : 'percentdonecolumn',
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
Ext.define("Gnt.column.PercentDone", {
    extend             : "Ext.grid.column.Number",

    requires           : ['Gnt.field.Percent'],

    alias              : [
        "widget.percentdonecolumn",
        "widget.ganttcolumn.percentdone"
    ],

    mixins             : ['Gnt.column.mixin.TaskFieldColumn'],

    width              : 50,
    format             : '0',
    align              : 'center',

    editor             : 'percentfield',

    defaultEditor      : 'percentfield',

    fieldProperty      : 'percentDoneField',
    useRenderer        : false,

    initComponent : function () {
        this.initTaskFieldColumn({
            decimalPrecision : 0
        });

        this.callParent(arguments);
    }

});
