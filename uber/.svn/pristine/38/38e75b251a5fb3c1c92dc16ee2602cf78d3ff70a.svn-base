/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.model.utilization.ResourceStoreUtilizationNegotiationStrategy', {

    extend : 'Gnt.model.utilization.DefaultUtilizationNegotiationStrategy',

    uses : [
        'Ext.Date',
        'Gnt.model.UtilizationEvent'
    ],

    config : {
        resourceUtilizationStore    : null,
        underUtilizationThreshold   : null,
        overUtilizationThreshold    : null
    },

    constructor : function(config) {
        var me = this;
        me.initConfig(config);
    },

    getUtilizationInfoForUtilizationEvent : function(utilizationEvent) {
        var UE               = Gnt.model.UtilizationEvent,
            me               = this,
            store            = me.getResourceUtilizationStore(),
            id               = utilizationEvent.getId(),
            utilizationInfoCache = store && store.utilizationInfoCache || {},
            result           = utilizationInfoCache[id] || null,
            originalResource,
            originalResourceTimespan,
            originalAssignments,
            allocationAcc;

        if (!result) {

            // If we have no cache result then we must request utilization information calculation for event resource, more precisely to it's surrogate summary event,
            // it's utilization information contains data about assignements, combining it with assignment sort order which is defined by assignmentComparator(),
            // we can designate the assignments which give overallocation to the resource according to bussiness logic required.

            // Obtaining summary event
            originalResource = utilizationEvent.getOriginalResource();
            originalResourceTimespan = me.calculateResourceAssignmentsTimespan(originalResource);

            // Obtaining all assignment under this surrogate summary event, we will need the assignment list later in the code
            originalAssignments = [];
            originalResource.forEachAssignment(function(a) { originalAssignments.push(a); });
            // and sort them according to strategy assignment sorting method
            originalAssignments.sort(function(a, b) { return me.assignmentsComparator(a, b); });

            // Calculating

            me.forEachTimeSpanInterval(originalResourceTimespan, function getUtilizationInfoForUtilizationEventForEachTimeIntervalCallback(intervalStartDate, intervalEndDate) {
                var info = originalResource.getUtilizationInfo(intervalStartDate, intervalEndDate, me.getUnderUtilizationThreshold(), me.getOverUtilizationThreshold()),
                    surrogateId,
                    subcache;

                // Here we have utilization information for original resource on a particular interval, this data contains utilization information for the resource,
                // as well as for each assignment under this resource.

                // Caching the resource utilization information
                surrogateId = UE.getSurrogateIdFor(originalResource);
                subcache    = utilizationInfoCache[surrogateId] = (utilizationInfoCache[surrogateId] || {});

                Ext.Object.each(info, function(key, value) {
                    subcache[me.self.makeUtilizationInfoKey(intervalStartDate, intervalEndDate, key)] = value;
                });

                // Now extracting assignments information from resource utilization information, and caching them as well.

                // Alongside we do additional calculation for assignment over/under/optimal allocation flag which is required by our bussiness logic
                allocationAcc = 0;

                Ext.Array.each(originalAssignments, function(originalAssignment) {
                    var subinfo,
                        originalId = originalAssignment.getId();

                    if (info.assignmentInfo.hasOwnProperty(originalId)) {
                        surrogateId = UE.getSurrogateIdFor(originalAssignment);
                        subcache    = utilizationInfoCache[surrogateId] = (utilizationInfoCache[surrogateId] || {});
                        subinfo     = info.assignmentInfo[originalId];

                        // If resource tells us that it neither underallocated nor overallocated then assignments shouldn't report those flag either
                        /*
                        if (!info.isOverallocated && !info.isUnderllocated) {
                            subinfo.isOverallocated = false;
                            subinfo.isUnderallocated = false;
                        }
                        // If resource tells that it's overallocated then we must determine exactly what assignments bring the overallocation
                        else if (info.isOverallocated) {
                            allocationAcc += subinfo.allocationMs;
                            if (allocationAcc > info.allocationMs - info.allocationDeltaMs) {
                                subinfo.isOverallocated = true;
                                subinfo.isUnderallocated = false;
                            }
                            else {
                                subinfo.isOverallocated = false;
                            }
                        }
                        */

                        Ext.Object.each(subinfo, function(key, value) {
                            subcache[me.self.makeUtilizationInfoKey(intervalStartDate, intervalEndDate, key)] = value;
                        });
                    }
                });
            });

            // Now we have required information cached, and can fill result in.
            result = utilizationInfoCache[id] || {};

        } // !result

        return result;
    },

    getUtilizationInfoForAssignmentEventInterval : function(utilizationEvent, intervalStartDate) {
        var ED            = Ext.Date,
            me            = this,
            resourceStore = me.getResourceUtilizationStore(),
            timeAxis      = resourceStore.getTimeAxis(),
            tickIndex     = timeAxis.getTickFromDate(intervalStartDate),
            intervalEndDate;

        // when "autoAdjust : true" tickIndex might get equal ticket count and not -1 (if we pass end date of last tick to getTickFromDate())
        if (tickIndex !== -1 && tickIndex < timeAxis.count()) {
            intervalEndDate = timeAxis.getAt(tickIndex).getEndDate();
        }
        else {
            intervalEndDate = ED.add(intervalStartDate, 1, timeAxis.unit);
        }

        return me.callParent([utilizationEvent, intervalStartDate, intervalEndDate]);
    },

    forEachTimeSpanInterval : function(timespan, callback) {
        var me            = this,
            resourceStore = me.getResourceUtilizationStore(),
            timeAxis      = resourceStore.getTimeAxis(),
            startDate, endDate;

        if (timeAxis) {
            startDate = timespan.getStartDate ? timespan.getStartDate() : timespan.startDate;
            endDate   = timespan.getEndDate   ? timespan.getEndDate()   : timespan.endDate;

            startDate = new Date(Math.max(startDate, timeAxis.getStart()));
            endDate   = new Date(Math.min(endDate, timeAxis.getEnd()));

            // timeAxis.generateTicks() adjust start/end dates to time axis main unit boundaries, which is not
            // what we want here, adjusted start date might be lesser then time span start date, as well as
            // adjusted end date might be greater then time span end date, if, for example, main unit is week
            // whereas unit is day.
            Ext.Array.each(timeAxis.generateTicks(startDate, endDate, timeAxis.getUnit()), function(tick) {
                if (tick.start - startDate >= 0 && tick.end - endDate <= 0) {
                    callback(tick.start, tick.end);
                }
            });
        }
        else {
            me.callParent([timespan, callback]);
        }
    },

    adjustStartDateToTick : function(date) {
        var me = this,
            resourceStore = me.getResourceUtilizationStore(),
            timeAxis      = resourceStore.getTimeAxis();

        return timeAxis.floorDate(date, false, timeAxis.unit, 1);
    },

    adjustEndDateToTick : function(date) {
        var me = this,
            resourceStore = me.getResourceUtilizationStore(),
            timeAxis      = resourceStore.getTimeAxis();

        return timeAxis.ceilDate(date, false, timeAxis.unit, 1);
    }
});
