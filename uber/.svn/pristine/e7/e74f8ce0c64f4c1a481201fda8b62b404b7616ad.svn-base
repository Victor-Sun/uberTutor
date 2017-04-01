/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.field.EndDate
@extends Gnt.field.Date

A specialized field for editing the task end date value. This class inherits from the `Ext.form.field.Date` field
and any of its configuration options can be used. You can find this field used in the {@link Gnt.widget.TaskForm}
and in the {@link Gnt.column.StartDate} classes but you can also use it in your own components.
See "Using field standalone" in the documentation of {@link Gnt.field.StartDate}.

This field must be bound to a {@link Gnt.model.Task task} instance, which is used for date value processing
(calendars, holidays etc).

#Task interaction

By default the field instantly applies all changes to the bound task. This can be turned off with the {@link #instantUpdate} option.

#Using field standalone

Please refer to {@link Gnt.field.StartDate} for details.

* **Note**, that the value displayed in the field can be different from the value in the data model when editing milestones
or when the date does not contain any time information (hours/minutes etc). This is because in our component, an end date represents a distinct point
on the timeaxis. For example: if from a user perspective, a task starts at 2013/01/01 and ends at 2013/01/02 -
this means that the task actually ends at 2013/01/02 23:59:59.9999. In the task model we store
2013/01/03 00:00:00, but in the field we show 2013/01/02. See also {@link #adjustMilestones}.

*/
Ext.define('Gnt.field.EndDate', {

    extend              : 'Gnt.field.Date',

    requires            : ['Sch.util.Date'],

    alias               : 'widget.enddatefield',

    taskField           : 'endDateField',
    getTaskValueMethod  : 'getEndDate',
    setTaskValueMethod  : 'setEndDate',

    /**
     * @cfg {Boolean} validateStartDate When set to `true`, the field will validate a "startDate <= endDate" condition and will not allow user to save invalid value.
     * Set it to `false` if you use different validation mechanism.
     */
    validateStartDate   : true,

    /**
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

            - endBeforeStartText : 'End date is before start date'
     */

    valueToVisible : function (value, task) {
        task = task || this.task;

        return task.getDisplayEndDate(this.format, this.adjustMilestones, value, true);
    },

    visibleToValue : function (value) {
        if (value && this.task) {

            if (!Ext.Date.formatContainsHourInfo(this.format) && value - Ext.Date.clearTime(value, true) === 0) {
                // the standard ExtJS date picker will only allow to choose the date, not time
                // we set the time of the selected date to the latest availability hour for that date
                // in case the date has no availability intervals we use the date itself
                value = this.task.getCalendar().getCalendarDay(value).getAvailabilityEndFor(value) ||
                    Sch.util.Date.add(value, Sch.util.Date.DAY, 1);
            }

        } else {
            value = null;
        }

        return value;
    },


    isValidAgainstStartDate : function (value) {
        return !this.task || !value || value >= this.task.getStartDate();
    },


    // @OVERRIDE
    getErrors : function (value) {
        var errors = this.callParent(arguments);

        if (errors && errors.length) {
            return errors;
        }

        if (this.validateStartDate) {
            if (!this.isValidAgainstStartDate( this.rawToValue(value)) ) {
                return [this.L('endBeforeStartText')];
            }
        }

        return [];
    }
});
