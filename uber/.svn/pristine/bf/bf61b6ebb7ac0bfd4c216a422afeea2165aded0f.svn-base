/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.field.StartDate
@extends Gnt.field.Date

A specialized field for editing the task start date value. This class inherits from the `Ext.form.field.Date` field
so any of its configuration options can be used. You can find this field in {@link Gnt.widget.TaskForm}
and in {@link Gnt.column.StartDate} but you can use it in your own components as well (see "Using field standalone" below).

This field requires to be bound to {@link Gnt.model.Task task} instance, which is used for date value processing
(calendars, holidays etc).

#Task interacting

By default field instantly applies all changes to the bound task. This can be turned off with the {@link #instantUpdate} option.

#Using field standalone

To use this field standalone you have to provide {@link Gnt.model.Task task} instance to it. You can make it by two ways:

 - Set the {@link #task} configuration option at field constructing step. Like this:

        var startDateField = Ext.create('Gnt.field.StartDate', {
            task : someTask
        });

 - Or by calling {@link #setTask} method after field was created. Like this:

        startDateField.setTask(someTask);

* **Note:** If task does not belong to any {@link Gnt.data.TaskStore} you also **have to** specify {@link #taskStore} config option for this field otherwise it won't work:

        // some task not inserted in the task store yet
        var someTask    = new Gnt.model.Task({ ... })

        var startDateField = Ext.create('Gnt.field.StartDate', {
            task        : someTask,
            // need to provide a task store instance in this case
            taskStore   : taskStore
        });

* **Note**, that value displayed in the field can be different from the value in the task model when editing milestones.
Please refer to {@link #adjustMilestones} for details.

*/
Ext.define('Gnt.field.StartDate', {
    extend              : 'Gnt.field.Date',

    alias               : 'widget.startdatefield',

    keepDuration        : true,

    taskField           : 'startDateField',
    getTaskValueMethod  : 'getStartDate',
    setTaskValueMethod  : 'setStartDate',

    isBaseline          : false,

    valueToVisible : function (value, task) {
        task = task || this.task;

        return task.getDisplayStartDate(this.format, this.adjustMilestones, value, true, this.isBaseline);
    },

    visibleToValue : function (value) {
        var task = this.task;

        // Special treatment of milestone task dates
        if (task && value) {
            var isMidnight = !Ext.isDate(this.lastValue) || this.lastValue - Ext.Date.clearTime(this.lastValue, true) === 0;

            if (this.adjustMilestones && task.isMilestone(this.isBaseline) && value - Ext.Date.clearTime(value, true) === 0 && isMidnight) {

                // the standard ExtJS date picker will only allow to choose the date, not time
                // we set the time of the selected date to the earliest availability hour for that date
                // in case the date has no availability intervals we use the date itself

                value   = task.getCalendar().getCalendarDay(value).getAvailabilityEndFor(value) || value;

            }
        }

        return value;
    }

});
