/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.field.Effort
@extends Gnt.field.Duration

A specialized field, allowing a user to also specify a duration unit when editing the effort value.
This class inherits from the {@link Gnt.field.Duration} field, which inherits from `Ext.form.field.Number` so any regular {@link Ext.form.field.Number} configs can be used (like `minValue/maxValue` etc).

*/
Ext.define('Gnt.field.Effort', {
    extend                  : 'Gnt.field.Duration',

    requires                : ['Gnt.util.DurationParser'],

    alias                   : 'widget.effortfield',
    alternateClassName      : 'Gnt.widget.EffortField',

    mixins                  : ['Gnt.mixin.Localizable'],

    /**
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

     - invalidText : 'Invalid value'
     */

    taskField               : 'effortField',
    getDurationUnitMethod   : 'getEffortUnit',
    setTaskValueMethod      : 'setEffort',
    getTaskValueMethod      : 'getEffort',

    applyChanges : function (toTask) {
        toTask = toTask || this.task;

        this.setTaskValue(toTask, this.getValue() || null, this.durationUnit);

        // since we have an "applyChanges" method different from the one provided by "TaskField" mixin
        // we need to fire "taskupdated" ourself
        toTask.fireEvent('taskupdated', toTask, this);
    }
});
