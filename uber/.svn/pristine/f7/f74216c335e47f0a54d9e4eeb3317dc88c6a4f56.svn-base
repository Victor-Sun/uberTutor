/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.field.ConstraintDate', {

    extend              : 'Gnt.field.Date',
    alias               : 'widget.constraintdatefield',

    // This is required to properly handle the field's read only state as designated in task's isEditable() method
    taskField           : 'constraintDateField',
    getTaskValueMethod  : 'getConstraintDate',
    setTaskValueMethod  : 'setConstraintDate',

    reAssertValue       : false,

    valueToVisible : function (value, task) {
        task    = task || this.task;

        var me              = this,
            constraintClass = task && task.getConstraintClass(),
            format          = me.format || Ext.Date.defaultFormat;

        if (constraintClass) {
            return constraintClass.getDisplayableConstraintDateForFormat(value, format, task);
        } else {
            return value;
        }
    },

    visibleToValue : function (value) {
        var me              = this,
            format          = me.format || Ext.Date.defaultFormat,
            task            = me.task,
            constraintClass = task && task.getConstraintClass();

        if (constraintClass && !Ext.isEmpty(value)) {
            value = constraintClass.adjustConstraintDateFromDisplayableWithFormat(value, format, task);
        }

        return value;
    }

});
