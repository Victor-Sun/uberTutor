/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

 @class Gnt.column.ManuallyScheduled
 @extends Ext.grid.column.Column

 A Column showing the `Manually Scheduled` field of a task.
 */

Ext.define("Gnt.column.ManuallyScheduled", {
    extend              : "Ext.grid.Column",
    alias               : [
        'widget.manuallyscheduledcolumn',
        'widget.ganttcolumn.manuallyscheduledcolumn'
    ],

    requires            : ['Gnt.field.ManuallyScheduled'],

    mixins              : ['Gnt.column.mixin.TaskFieldColumn'],

    width               : 50,
    align               : 'center',

    instantUpdate       : false,

    fieldProperty       : 'manuallyScheduledField',

    editor              : 'manuallyscheduledfield',

    defaultEditor       : 'manuallyscheduledfield',

    initComponent : function () {
        this.initTaskFieldColumn();

        this.callParent(arguments);
    },

    getValueToRender : function (value, meta, task) {
        return this.field.valueToVisible(task.isManuallyScheduled());
    }
});
