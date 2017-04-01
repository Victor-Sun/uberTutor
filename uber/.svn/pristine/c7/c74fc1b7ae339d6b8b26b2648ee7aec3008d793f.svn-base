/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.field.DeadlineDate
@extends Gnt.field.Date

A specialized field for editing the task deadline date value. This class inherits from the `Ext.form.field.Date` field
and any of its configuration options can be used.

This field must be bound to a {@link Gnt.model.Task task} instance, which is used for date value processing
(calendars, holidays etc).

#Using field standalone

Please refer to {@link Gnt.field.StartDate} for details.

* **Note**, that the value displayed in the field can be different from the value in the data model since an end date represents a distinct point
on the timeaxis but we want to render just the date part. For example: If deadline is set to 2013/01/02 -
this means that the data model value would be 2013/01/03 00:00:00, but in the field we show 2013/01/02.
*/
Ext.define('Gnt.field.DeadlineDate', {
    extend              : 'Gnt.field.Date',
    requires            : ['Sch.util.Date'],
    alias               : 'widget.deadlinedatefield',

    taskField           : 'deadlineDateField',
    getTaskValueMethod  : 'getDeadlineDate',
    setTaskValueMethod  : 'setDeadlineDate',

    valueToVisible : function (value, task) {
        task = task || this.task;

        return task.getDisplayEndDate(this.format, true, value, true);
    },

    visibleToValue : function (value) {
        if (value && value - Ext.Date.clearTime(value, true) === 0) {
            // the standard ExtJS date picker will only allow to choose the date, not time
            // we set the time of the selected date to the latest availability hour for that date
            // in case the date has no availability intervals we use the date itself
            value = Sch.util.Date.add(value, Sch.util.Date.DAY, 1);
        }

        return value;
    }
});
