/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.model.utilization.DefaultUtilizationNegotiationStrategy', {

    uses : [
        'Ext.Date',
        'Ext.Object'
    ],

    /**
     * This method will be used by UtilizationEvent mostly to check if it's synchronized with primary data.
     * This method return value is suitable for comparison by {@link Ext.Object.equals}, but it isn't suitable
     * for direct usage. It's an object with volatile and cryptic set of keys which depend on event's start/end dates.
     *
     * @param  {Gnt.model.UtilizationEvent} utilizationEvent
     * @return {Object}
     */
    getUtilizationInfoForUtilizationEvent : function(utilizationEvent) {
        var me     = this,
            result = {};

        me.forEachTimeSpanInterval(utilizationEvent, function getUtilizationInfoForUtilizationEventForEachTimeIntervalCallback(dayStartDate, dayEndDate) {
            var info;

            if (utilizationEvent.isSurrogateAssignment()) {
                info = utilizationEvent.getOriginalAssignment().getUtilizationInfo(dayStartDate, dayEndDate);
            }
            else if (utilizationEvent.isSurrogateSummary()) {
                info = utilizationEvent.getOriginalResource().getUtilizationInfo(dayStartDate, dayEndDate);
            }
            else {
                // <debug>
                Ext.Error.raise('Unknown utilization event type');
                // </debug>
                // @TODO: #2773 - Rhyno parse error - Syntax error while building the app
                var foo = false;
            }

            Ext.Object.each(info, function(key, value) {
                result[me.self.makeUtilizationInfoKey(dayStartDate, dayEndDate, key)] = value;
            });

            return info;
        });

        return result;
    },

    /**
     * This method is in turn should return a value suitable for direct usage for particular interval within event
     * start/end dates.
     *
     * Note: utilization information is supposed to be consumed by intervals, by default it's daily intervals.
     *
     * @param  {Gnt.model.UtilizationEvent} utilizationEvent
     * @param  {Date} intervalStartDate Interval start date
     * @return {Object}  info
     * @return {Boolean} info.isUtilized
     * @return {Number}  info.allocationMs
     * @return {Number}  info.allocationDeltaMs
     * @return {Boolean} info.isOverallocated
     * @return {Boolean} info.isUnderllocated
     */
    getUtilizationInfoForAssignmentEventInterval : function(utilizationEvent, intervalStartDate, /* private */intervalEndDate) {
        var ED     = Ext.Date,
            me     = this,
            result = {
                isUtilized        : false,
                allocationMs      : 0,
                allocationDeltaMs : 0,
                isOverallocated   : false,
                isUnderallocated  : false,
                assignmentInfo    : null,
                taskInfo          : null
            },
            info  = utilizationEvent.getUtilizationInfo();

        intervalEndDate = intervalEndDate || ED.add(intervalStartDate, ED.DAY, 1);

        Ext.Object.each(result, function(key, value) {
            var infoKey = me.self.makeUtilizationInfoKey(intervalStartDate, intervalEndDate, key);
            result[key] = info.hasOwnProperty(infoKey) ? info[infoKey] : value;
        });

        return result;
    },

    /**
     * Iterates over a timespan time intervals and calls the callback providing interval start/end dates
     * as parameters. For this strategy interval is one day, other strategies might use other intervals.
     *
     * @param {Sch.model.Range} timespan Any object supporting interface having getStartDate()/getEndDate() methods or startDate/endDate properties.
     * @param {Function} callback
     * @param {Date} callback.startDate
     * @param {Date} callback.endDate
     */
    forEachTimeSpanInterval : function(timespan, callback) {
        var ED = Ext.Date,
            startDate = timespan.getStartDate ? timespan.getStartDate() : timespan.startDate,
            endDate   = timespan.getEndDate   ? timespan.getEndDate()   : timespan.endDate,
            date;

        date = ED.clearTime(startDate, true);
        while (date < endDate) {
            startDate = date;
            date      = ED.add(date, ED.DAY, 1);
            callback(startDate, date);
        }
    },

    /**
     * Adjusts event start date to strategy tick.
     *
     * Note: this strategy uses daily intervals so ticks are at the begining of the days, other strategies might use
     *       different ticks.
     *
     * @param {Date} date
     * @return {Date}
     */
    adjustStartDateToTick : function(date) {
        return date && Ext.Date.clearTime(date, true) || date;
    },

    /**
     * Adjust event end date to strategy tick.
     *
     * Note: this strategy uses daily intervals so ticks are at the begining of the days, other strategies might use
     *       different ticks.
     *
     * @param {Date} date
     * @return {Date}
     */
    adjustEndDateToTick : function(date) {
        var ED = Ext.Date,
            result = date,
            testDate;

        if (date) {
            testDate = new Date(date.getTime() - 1);
            // If we were at the beggining of a day, then date given designates ending of timespan which actually ends at 23:59:59.999
            if (testDate.getDate() != date.getDate()) {
                // Returning a clone
                result = new Date(date);
            }
            // otherwise given date point somewhere at mid day, so we adjust it to point to the end of that day (see previous comment);
            else {
                result = ED.clearTime(ED.add(date, ED.DAY, 1));
            }
        }

        return result;
    },

    /**
     * Calculates timespan occupied by resource assignments and adjusts it to tick used
     *
     * @param {Gnt.model.Resource} resource
     * @return {Object} timespan
     * @return {Date}   timespan.startDate
     * @return {Date}   timespan.endDate
     */
    calculateResourceAssignmentsTimespan : function(resource) {
        var me = this,
            gotAssignments = false,
            minStartDate,
            maxEndDate;

        minStartDate = new Date(+8640000000000000);
        maxEndDate   = new Date(-8640000000000000);

        resource.forEachAssignment(function resourceAssignmentsTimespanCalculationEnumeratorCallback(assignment) {
            var task = assignment.getTask(),
                taskStartDate = task && task.getStartDate() || minStartDate,
                taskEndDate   = task && task.getEndDate()   || maxEndDate;

            if (minStartDate > taskStartDate) {
                minStartDate = taskStartDate;
            }
            if (maxEndDate < taskEndDate) {
                maxEndDate = taskEndDate;
            }
            gotAssignments = true;
        });

        return {
            startDate : gotAssignments && me.adjustStartDateToTick(minStartDate) || null,
            endDate   : gotAssignments && me.adjustEndDateToTick(maxEndDate)     || null
        };
    },

    /**
     * Assignments comparison function, by default order by task name
     *
     * @param {Gnt.model.Assignment} a
     * @param {Gnt.model.Assignment} b
     * @return {Number}
     */
    assignmentsComparator : function(a, b) {

        if (a.getTask().getName() > b.getTask().getName()) {
            return 1;
        }

        return -1;
    },

    inheritableStatics : {
        /**
         * Makes utilization information key.
         *
         * @private
         */
        makeUtilizationInfoKey : function(startDate, endDate, suffix) {
            return [startDate.getTime(), endDate.getTime(), suffix].join('-');
        }
    }
});
