/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.field.BaselineEndDate
@extends Gnt.field.EndDate

A specialized field for editing the task baseline end date value. This class inherits from the `Ext.form.field.Date` field
and any of its configuration options can be used. You can find this field used in the {@link Gnt.widget.TaskForm}
and in the {@link Gnt.column.BaselineStartDate} classes but you can also use it in your own components.

This field must be bound to a {@link Gnt.model.Task task} instance, which is used for date value processing
(calendars, holidays etc).

#Task interaction

By default the field instantly applies all changes to the bound task. This can be turned off with the {@link #instantUpdate} option.

* **Note**, that the value displayed in the field can be different from the value in the data model when editing milestones
or when the date does not contain any time information (hours/minutes etc). This is because in our component, an end date represents a distinct point
on the timeaxis. For example: if from a user perspective, a task starts at 2013/01/01 and ends at 2013/01/02 -
this means that the task actually ends at 2013/01/02 23:59:59.9999. In the task model we store
2013/01/03 00:00:00, but in the field we show 2013/01/02. See also {@link #adjustMilestones}.

*/
Ext.define('Gnt.field.BaselineEndDate', {

    extend              : 'Gnt.field.EndDate',

    alias               : 'widget.baselineenddatefield',

    /**
     * @hide
     * @cfg keepDuration
     */

    taskField           : 'baselineEndDateField',
    getTaskValueMethod  : 'getBaselineEndDate',
    setTaskValueMethod  : 'setBaselineEndDate',

    isValidAgainstStartDate : function (value) {
        return !this.task || !value || value >= this.task.getBaselineStartDate();
    },

    applyChanges : function (toTask, silent) {
        toTask  = toTask || this.task;

        this.setTaskValue(toTask, this.value || null);

        // since we have an "applyChanges" method different from the one provided by "TaskField" mixin
        // we need to fire "taskupdated" ourself
        if (!silent) toTask.fireEvent('taskupdated', toTask, this);
    }

});
