/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.model.task.Constraints
@mixin
@protected

Internal mixin class providing additional logic and functionality related to task constraints.

*/
Ext.define('Gnt.model.task.Constraints', {

    requires : [
        'Gnt.constraint.Base',
        'Gnt.constraint.StartNoEarlierThan',
        'Gnt.constraint.StartNoLaterThan',
        'Gnt.constraint.FinishNoEarlierThan',
        'Gnt.constraint.FinishNoLaterThan',
        'Gnt.constraint.MustStartOn',
        'Gnt.constraint.MustFinishOn'
    ],


    /**
     * Sets the constraint type and constraining date (if applicable) to the task.
     *
     * @param {String} type
     *  Constraint type, see {@link #setConstraintType} for further description.
     * @param {Date}   date
     *  Constraint date
     * @param {Function} [callback] Callback to call after constraint application and constraint conflict resolution
     *  if any.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for cancelling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    setConstraint : function(type, date, callback) {
        var me = this;

        function changer() {
            return me.setConstraintWithoutPropagation(type, date);
        }

        if (type) {
            me.propagateChanges(changer, callback);
        }
        else {
            changer();
            callback && callback(false, {});
        }
    },

    setConstraintWithoutPropagation : function (type, date) {
        var me = this,
            constraint;

        if (type) {
            constraint = Gnt.constraint.Base.getConstraintClass(type);
        }

        if (!date && constraint)  {
            date = constraint.getInitialConstraintDate(me);
        }

        me.beginEdit();
        me.set(me.constraintTypeField, type || '');
        me.set(me.constraintDateField, date);
        me.endEdit();

        return true;
    },

    /**
     * Sets the constraint type of the task. The type string can be one of the following values:
     *
     *  - finishnoearlierthan
     *  - finishnolaterthan
     *  - mustfinishon
     *  - muststarton
     *  - startnoearlierthan
     *  - startnolaterthan
     *
     * @param {String} type
     *  Constraint type
     * @param {Function} [callback] Callback to call after constraint application and constraint conflict resolution
     *  if any.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    setConstraintType : function (type, callback) {
        this.setConstraint(type, this.getConstraintDate(), callback);
    },


    setConstraintTypeWithoutPropagation : function (type, callback) {
        this.setConstraintWithoutPropagation(type, this.getConstraintDate(), callback);
    },


    /**
     * Sets the constraint date of the task.
     *
     * @param {Date}   date
     *  Constraint date
     * @param {Function} [callback] Callback to call after constraint application and constraint conflict resolution
     *  if any.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    setConstraintDate : function (date, callback) {
        this.setConstraint(this.getConstraintType(), date, callback);
    },


    setConstraintDateWithoutPropagation : function (date) {
        this.setConstraintWithoutPropagation(this.getConstraintType(), date);
    },


    /**
     * Checks whether a constraint is set for the task.
     *
     * @return {Boolean}
     */
    hasConstraint : function() {
        return !!this.getConstraintType();
    },


    /**
     * Returns a constraint singleton class corresponding to the constraint type currently set for the task.
     *
     * @return {Gnt.constraint.Base} subclass of
     */
    getConstraintClass : function() {
        return Gnt.constraint.Base.getConstraintClass(this.getConstraintType());
    },


    /**
     * Returns true if task has no constraint set or if a constraint set is satisfied by the task.
     *
     * @return {Boolean}
     */
    isConstraintSatisfied : function () {
        var me = this;

        return !me.hasConstraint() || me.getConstraintClass().isSatisfied(me, me.getConstraintDate());
    },


    /**
     * Verifies the constraint of the task.
     *
     * @param {Function} [onceResolvedContinueHere] Callback function to be called after constraint conflict resolution.
     * @param {Boolean}  onceResolvedContinueHere.constraintSatisfied Flag showing whether constraint has been satisfied or violated.
     * @param {Boolean}  onceResolvedContinueHere.cancelChanges Flag showing whether a user has opted for changes to be canceled.
     * @return {Boolean} True if no constraint conflict has been found, false otherwise
     *
     * @private
     */
    verifyConstraint : function(onceResolvedContinueHere) {
        var me = this,
            taskStore,
            precision,
            constraintResolutionContext,
            constraintSatisfied,
            hasConflictListener;

        // <debug>
        !onceResolvedContinueHere || Ext.isFunction(onceResolvedContinueHere) ||
            Ext.Error.raise("Can't verify task's constraint, resultion callback is invalid!");
        // </debug>

        constraintSatisfied         = me.isConstraintSatisfied();
        onceResolvedContinueHere    = onceResolvedContinueHere && Ext.Function.pass(onceResolvedContinueHere, [constraintSatisfied]);
        taskStore                   = me.getTaskStore(true);
        hasConflictListener         = taskStore && taskStore.hasListener('constraintconflict');
        precision                   = taskStore && taskStore.constraintDatePrecision || Sch.util.Date.DAY;
        constraintResolutionContext = !constraintSatisfied && me.getConstraintClass().getResolution(onceResolvedContinueHere, me, null, precision);

        if (!constraintSatisfied && hasConflictListener) {
            /**
             * @event constraintconflict
             *
             * Fires when task constraint conflict has been found and requires a resolution.
             *
             * @param {Gnt.model.Task} task The task whose constraint is violated
             * @param {Object} context Constraint resolution context
             * @param {String} context.title The description of the
             *
             * @member Gnt.data.TaskStore
             */
            taskStore.fireEvent('constraintconflict', me, constraintResolutionContext);
        }
        else if (!constraintSatisfied) {
            constraintResolutionContext.cancelAction();
        }
        else if (onceResolvedContinueHere) {
            onceResolvedContinueHere(false);
        }

        return constraintSatisfied;
    },

    /**
     * Returns working time start for the given date, task's calendar and calendars the task assigned resources
     * calendars are taken into account.
     *
     * If there's no working time present at the given date then returns false.
     *
     * @param {Date} date
     * @return {Date|false}
     */
    getWorkingTimeStartForDate : function(date) {
        var me     = this,
            result = false,
            startDate,
            endDate;

        // <debug>
        date instanceof Date ||
            Ext.Error.raise('Can\'t get working time start for a date, invalid date given!');
        // </debug>

        startDate = Ext.Date.clearTime(date, true);
        endDate   = Sch.util.Date.add(startDate, Sch.util.Date.DAY, 1);

        me.forEachAvailabilityInterval({
            isForward   : true,
            startDate   : startDate,
            endDate     : endDate,
            segments    : false,
            resources   : true,
            fn          : function(from, to) {
                result = new Date(from);
                return false;
            }
        });

        return result;
    },

    /**
     * Returns working time end for the given date, task's calendar and calendars the task assigned resources
     * calendars are taken into account.
     *
     * If there's no working time present at the given date then returns false.
     *
     * @param {Date} date
     * @return {Date|false}
     */
    getWorkingTimeEndForDate : function(date) {
        var me     = this,
            result = false,
            startDate,
            endDate;

        // <debug>
        date instanceof Date ||
            Ext.Error.raise('Can\'t get working time end for a date, invalid date given!');
        // </debug>

        startDate = Ext.Date.clearTime(date, true);
        endDate   = Sch.util.Date.add(startDate, Sch.util.Date.DAY, 1);

        me.forEachAvailabilityInterval({
            isForward   : false,
            startDate   : startDate,
            endDate     : endDate,
            segments    : false,
            resources   : true,
            fn          : function(from, to) {
                result = new Date(to);
                return false;
            }
        });

        return result;
    },

    /**
     * Returns nearest working time start (earliest for the day) in the given search direction up to given search limit
     *
     * @param {Date} date Date to start searching from.
     * @param {Boolean} searchBackward Search direction flag.
     * @param {Integer} searchLimit Amount of days to search in.
     *
     * @return {Date|false}
     */
    getNearestWorkingTimeStartForDate : function(date, searchBackward, searchLimit) {
        var me = this,
            step,
            result;

        // <debug>
        date instanceof Date ||
            Ext.Error.raise('Can\'t get nearest working time start for a date, invalid date given!');
        // </debug>

        searchBackward = searchBackward || false;
        searchLimit    = searchLimit    || 365;
        step           = searchBackward ? -1 : 1;

        result = me.getWorkingTimeStartForDate(date);

        while (!result && searchLimit--) {
            date   = Sch.util.Date.add(date, Sch.util.Date.DAY, step);
            result = me.getWorkingTimeStartForDate(date);
        }

        return result;
    },

    /**
     * Returns nearest working time end (latest for the day) in the given search direction up to given search limit
     *
     * @param {Date} date Date to start searching from.
     * @param {Boolean} searchBackward Search direction flag.
     * @param {Integer} searchLimit Amount of days to search in.
     *
     * @return {Date|false}
     */
    getNearestWorkingTimeEndForDate : function(date, searchBackward, searchLimit) {
        var me = this,
            step,
            result;

        // <debug>
        date instanceof Date ||
            Ext.Error.raise('Can\'t get nearest working time end for a date, invalid date given!');
        // </debug>

        searchBackward = searchBackward || false;
        searchLimit    = searchLimit    || 365;
        step           = searchBackward ? -1 : 1;

        result = me.getWorkingTimeEndForDate(date);

        while (!result && searchLimit--) {
            date   = Sch.util.Date.add(date, Sch.util.Date.DAY, step);
            result = me.getWorkingTimeEndForDate(date);
        }

        return result;
    },


    /**
     * Returns working time interval for the given datetime, if datetime is not withing working time interval,
     * then returns false, task's calendar and calendars the task assigned resources calendars are taken into account.
     *
     * @param {Date} dateTime
     * @return {Object|false} Object containing _startDate_, _endDate_ properties or false.
     */
    getWorkingTimeIntervalForDateTime : function(dateTime) {
        var me     = this,
            result = false,
            startDate,
            endDate;

        // <debug>
        dateTime instanceof Date ||
            Ext.Error.raise('Can\'t get task\'s working time intarval for a datetime, invalid datetime given!');
        // </debug>

        startDate = Ext.Date.clearTime(dateTime, true);
        endDate   = Sch.util.Date.add(startDate, Sch.util.Date.DAY, 1);

        dateTime = dateTime.valueOf();

        me.forEachAvailabilityInterval({
            isForward   : true,
            startDate   : startDate,
            endDate     : endDate,
            segments    : false,
            resources   : true,
            fn          : function(from, to) {
                if (from <= dateTime && dateTime <= to) {
                    result = {
                        startDate : new Date(from),
                        endDate   : new Date(to)
                    };
                }
                return !result;
            }
        });

        return result;
    },

    /**
     * Checks whether the given time is within working time interval for the task, task's calendar and calendars
     * the task assigned resources calendars are taken into account.
     *
     * @param {Date} dateTime
     * @return {Boolean}
     */
    isDateTimeWithinWorkingTimeInterval : function(dateTime) {
        var me = this;

        // <debug>
        dateTime instanceof Date ||
            Ext.Error.raise('Can\'t check if datetime is within working time intarval for a task, invalid datetime given!');
        // </debug>

        return me.getWorkingTimeIntervalForDateTime(dateTime) !== false;
    }
});
