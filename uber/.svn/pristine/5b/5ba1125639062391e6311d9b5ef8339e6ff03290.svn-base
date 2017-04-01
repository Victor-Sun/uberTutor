/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * @class Gnt.model.Assignment
 *
 * This class represent a single assignment of a resource to a task in your gantt chart. It is a subclass of the {@link Sch.model.Customizable} class, which in its turn subclasses {@link Ext.data.Model}.
 * Please refer to documentation of those classes to become familar with the base interface of this class.
 *
 * An Assignment has the following fields:
 *
 *   - `Id` - The id of the assignment
 *   - `ResourceId` - The id of the resource assigned
 *   - `TaskId` - The id of the task to which the resource is assigned
 *   - `Units` - An integer value representing how much of the resource's availability that is dedicated to this task
 *
 * The names of these fields can be customized by subclassing this class. Please refer to {@link Sch.model.Customizable} for details.
 *
 * See also: {@link Gnt.column.ResourceAssignment}
 */
Ext.define('Gnt.model.Assignment', {
    extend  : 'Sch.model.Assignment',

    uses : [
        'Sch.util.Date'
    ],

    customizableFields  : [
        { name : 'TaskId' },
        { name : 'Units', type : 'float', defaultValue : 100 }
    ],

    /**
     * @cfg {String} taskIdField The name of the field identifying the task to which an event belongs. Defaults to "TaskId".
     */
    taskIdField  : 'TaskId',
    eventIdField : 'TaskId',

    constructor : function(data, session) {
        var me = this;
        me.eventIdField = me.taskIdField;
        me.callParent([data, session]);
    },

    getEventId : function() {
        var me = this;
        return me.get(me.taskIdField);
    },

    setEventId : function(eventId) {
        var me = this;
        return me.set(me.taskIdField, eventId);
    },

    /**
     * @cfg {String} unitsField The name of the field identifying the units of this assignment. Defaults to "Units".
     */
    unitsField              : 'Units',

    /**
     * Returns the associated task store instance
     *
     * @return {Gnt.data.TaskStore}
     */
    getTaskStore : function() {
        return this.store && this.store.getTaskStore() || null;
    },

    getEventStore : function() {
        return this.getTaskStore();
    },

    /**
     * Returns the units of this assignment
     *
     * @return {Number} units
     */
    getUnits : function () {
        var me = this;

        return Math.max(0, me.get(me.unitsField));
    },


    /**
     * Sets the units of this assignment
     *
     * @param {Number} value The new value for units
     */
    setUnits : function (value) {
        var me = this;

        // <debug>
        value < 0 &&
            Ext.Error.raise("`Units` value for an assignment can't be less than 0");
        // </debug>

        me.set(me.unitsField, value);
    },


    /**
     * Returns the task associated with this assignment.
     *
     * @privateparam {Gnt.data.TaskStore} [taskStore]
     * @return {Gnt.model.Task} Instance of task
     */
    getTask : function (taskStore) {
        var me = this;
        return me.getEvent(taskStore);
    },

    /**
     * Returns associated task name
     *
     * @privateparam {Gnt.data.TaskStore} [taskStore]
     * @return {String}
     */
    getTaskName : function (taskStore) {
        var task = this.getTask(taskStore);
        return task && task.getName() || '';
    },

    /**
     * Returns the effort, contributed by the resource of this assignment to a task of this assignment.
     *
     * @param {String} unit Unit to return the effort in. Defaults to the `EffortUnit` field of the task
     *
     * @return {Number} effort
     */
    getEffort : function (unit) {
        var me          = this,
            task        = me.getTask(),
            totalEffort = 0;

        task.forEachAvailabilityIntervalWithResources(
            {
                startDate   : task.getStartDate(),
                endDate     : task.getEndDate(),
                resources   : [ me.getResource() ]
            },
            function (intervalStartDate, intervalEndDate, currentAssignments) {
                var i, totalUnits;

                for (i in currentAssignments) {
                    totalUnits = currentAssignments[ i ].units;
                }

                totalEffort += (intervalEndDate - intervalStartDate) * totalUnits / 100;
            }
        );

        return task.getProjectCalendar().convertMSDurationToUnit(totalEffort, unit || task.getEffortUnit());
    },

    /**
     * Returns an effort which will be spent by the resource assignment designated resource on the assignment
     * designated task at the given date.
     *
     * @param {Date} date
     * @param {String} [unit]
     */
    getEffortAtDate: function (date, unit) {
        var SUD         = Sch.util.Date,
            me          = this,
            task        = me.getTask(),
            startDate   = task && task.getStartDate(),
            endDate     = task && task.getEndDate(),
            resource    = me.getResource(),
            totalEffort = 0;

        if (task && resource && startDate && endDate) {

            date = SUD.constrain(date, startDate, endDate);

            task.forEachAvailabilityIntervalWithResources(
                {
                    startDate: startDate,
                    endDate: date,
                    resources: [resource]
                },
                function (from, till, assignments) {
                    var totalUnits = 0;

                    // Actually we have an object with one key, and the loop here is just to get to that key
                    // since it's name is unknown
                    for (var i in assignments) totalUnits = assignments[i].units;

                    totalEffort += (till - from) * totalUnits / 100;
                }
            );

            totalEffort = task.getCalendar().convertMSDurationToUnit(totalEffort, unit || task.getEffortUnit());
        }

        return totalEffort;
    }
});
