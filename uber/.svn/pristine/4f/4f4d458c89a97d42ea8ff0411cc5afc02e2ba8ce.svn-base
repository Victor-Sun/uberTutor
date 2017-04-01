/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.column.ShowInTimeline
@extends Ext.grid.column.Check

A Column showing if a task has to be displayed in the {@link Gnt.panel.Timeline project timeline}.

    var gantt = Ext.create('Gnt.panel.Gantt', {
        height      : 600,
        width       : 1000,

        // Setup your static columns
        columns         : [
            ...
            {
                xtype       : 'showintimelinecolumn',
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
    });

*/
Ext.define("Gnt.column.ShowInTimeline", {
    extend          : 'Ext.grid.column.Check',
    alias           : [
        'widget.showintimelinecolumn',
        'widget.ganttcolumn.showintimeline'
    ],

    uses : [
        'Gnt.patches.CheckColumn'
    ],

    mixins          : ['Gnt.column.mixin.TaskFieldColumn'],

    fieldProperty   : 'showInTimelineField',

    initComponent : function (config) {
        this.initTaskFieldColumn();
        this.callParent([ config ]);

        this.tdCls = (this.tdCls || '') + ' gnt-showintimeline-cell';
    },


    onReadOnlySet : function (gantt, readOnly) {
        this.setDisabled(readOnly);
    },


    taskFieldRenderer : function () {
        var result  = this.defaultRenderer.apply(this, arguments);

        this.applyColumnCls.apply(this, arguments);

        return result;
    }

});
