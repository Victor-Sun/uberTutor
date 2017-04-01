/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.field.BaselineStartDate
@extends Gnt.field.StartDate

A specialized field for editing the task baseline start date value. This class inherits from the `Ext.form.field.Date` field
so any of its configuration options can be used. You can find this field in {@link Gnt.widget.TaskForm}
and in {@link Gnt.column.BaselineStartDate} but you can use it in your own components as well.

*/
Ext.define('Gnt.field.BaselineStartDate', {
    extend              : 'Gnt.field.StartDate',

    alias               : 'widget.baselinestartdatefield',

    taskField           : 'baselineStartDateField',
    getTaskValueMethod  : 'getBaselineStartDate',
    setTaskValueMethod  : 'setBaselineStartDate',

    isBaseline          : true,

    /**
     * @hide
     * @cfg keepDuration
     */

    applyChanges : function (toTask, silent) {
        toTask  = toTask || this.task;

        this.setTaskValue(toTask, this.value);

        // since we have an "applyChanges" method different from the one provided by "TaskField" mixin
        // we need to fire "taskupdated" ourself
        if (!silent) toTask.fireEvent('taskupdated', toTask, this);
    }

});
