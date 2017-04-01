/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.constraint.MustStartOn', {
    extend  : 'Gnt.constraint.Base',

    alias       : 'gntconstraint.muststarton',

    singleton   : true,

    requires    : ['Sch.util.Date'],

    /**
     * @cfg {Object} l10n
     * An object, purposed for the class localization. Contains the following keys/values:
     *
     *      - "name" : "Must start on",
     *      - "Move the task to start at {0}" : "Move the task to start at {0}"
     */

    isSatisfied : function (task, date, precision) {
        var startDate = task.getStartDate();

        date = date || task.getConstraintDate();

        // read the following as: !date || !startDate || (startDate.valueOf() == date.valueOf())
        return !date || !startDate || (Sch.util.Date.compareWithPrecision(startDate, date, precision) === 0);
    },


    getResolutionOptions : function (callback, task, date, precision) {
        var me          = this,
            resolutions = me.callParent(arguments);

        date = date || task.getConstraintDate();

        resolutions.push({
            id      : 'move-task',
            title   : me.L("Move the task to start at {0}"),
            resolve : function () {
                task.setStartDateWithoutPropagation(date, true);
                callback();
            }
        });

        return resolutions;
    },


    getInitialConstraintDate : function(task) {
        return task.getStartDate();
    }
});
