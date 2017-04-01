/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.constraint.FinishNoEarlierThan', {
    extend      : 'Gnt.constraint.Base',

    alias       : 'gntconstraint.finishnoearlierthan',

    singleton   : true,

    requires    : ['Sch.util.Date'],

    /**
     * @cfg {Object} l10n
     * An object, purposed for the class localization. Contains the following keys/values:
     *
     *      - "name" : "Finish no earlier then",
     *      - "Move the task to finish on {0}" : "Move the task to finish on {0}"
     */


    isSatisfied : function (task, date) {
        var endDate = task.getEndDate();

        date = date || task.getConstraintDate();

        return !date || !endDate || endDate >= date;
    },


    getResolutionOptions : function (callback, task, date, precision) {
        var me          = this,
            resolutions = me.callParent(arguments);

        date = date || task.getConstraintDate();

        resolutions.push({
            id      : 'move-task',
            title   : me.L("Move the task to finish on {0}"),
            resolve : function () {
                task.setEndDateWithoutPropagation(date, true);
                callback();
            }
        });

        return resolutions;
    },


    getInitialConstraintDate : function(task) {
        return task.getEndDate();
    },


    getDisplayableConstraintDateForFormat : function(date, format, task) {
        if (date && !Ext.Date.formatContainsHourInfo(format) && (date - Ext.Date.clearTime(date, true) === 0)) {
            date = Sch.util.Date.add(date, Sch.util.Date.DAY, -1);
        }
        return date;
    },


    adjustConstraintDateFromDisplayableWithFormat : function(date, format, task) {
        if (date && !Ext.Date.formatContainsHourInfo(format) && (date - Ext.Date.clearTime(date, true) === 0)) {
            date = Sch.util.Date.add(date, Sch.util.Date.DAY, 1);
        }
        return date;
    }
});
