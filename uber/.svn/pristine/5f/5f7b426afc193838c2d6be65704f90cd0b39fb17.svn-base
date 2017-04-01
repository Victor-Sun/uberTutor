/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.model.UtilizationEvent', {
    extend   : 'Sch.model.Event',

    uses : [
        'Ext.Date',
        'Ext.Object',
        'Gnt.model.Resource',
        'Gnt.model.Assignment'
    ],

    mixins : [
        'Gnt.model.utilization.UtilizationNegotiationStrategyMixin'
    ],

    originalResource   : null,
    originalAssignment : null,

    customizableFields : [
        { name: 'utilizationInfo', type : 'auto', persist : false, defaultValue : null }
    ],

    utilizationInfoField : 'utilizationInfo',

    constructor : function (config) {
        var me = this,
            originalResource,
            originalAssignment;

        config = me.initUtilizationNegotiationStrategyMixin(config);

        if (config.originalResource) {
            originalResource = config.originalResource;
            delete config.originalResource;
        }

        if (config.originalAssignment) {
            originalAssignment = config.originalAssignment;
            delete config.originalAssignment;
        }

        me.callParent(arguments);

        originalResource   && me.setOriginalResource(originalResource);
        originalAssignment && me.setOriginalAssignment(originalAssignment);
    },

    /**
     * Returns true if this surrogate event designates an assignment
     *
     * @return {Boolean}
     */
    isSurrogateAssignment : function() {
        return !!this.originalAssignment;
    },

    /**
     * Returns true if this surrogate event designates an assignment summary for a resource
     *
     * @return {Boolean}
     */
    isSurrogateSummary : function() {
        return !!this.originalResource;
    },

    /**
     * Returns original assignment corresponding to this surrogate.
     *
     * @return {Gnt.model.Assignment|null}
     */
    getOriginalAssignment : function() {
        return this.originalAssignment;
    },

    /**
     * Sets original assignment corresponding to this surrogate.
     *
     * @param {Gnt.model.Assignment} assignment
     */
    setOriginalAssignment : function(assignment) {
        var me = this;

        if (me.originalAssignment !== assignment) {
            me.originalAssignment = assignment;
            if (!me.isInSyncWithOriginal()) {
                me.syncFromOriginal();
            }
        }
    },

    /**
     * Returns original resource corresponding to this surrogate.
     *
     * @return {Gnt.model.Resource|null}
     */
    getOriginalResource : function() {
        var me         = this,
            resource   = me.originalResource,
            assignment = me.originalAssignment;

        return resource || assignment && assignment.getResource() || null;
    },

    /**
     * Sets original resource corresponding to this surrogate.
     *
     * @param {Gnt.model.Resource} resource
     */
    setOriginalResource : function(resource) {
        var me = this;

        if (me.originalResource !== resource) {
            me.originalResource = resource;
            if (!me.isInSyncWithOriginal()) {
                me.syncFromOriginal();
            }
        }
    },

    /**
     * Returns original task corresponding to this surrogate.
     *
     * @return {Gnt.model.Task|null}
     */
    getOriginalTask : function() {
        var me         = this,
            assignment = me.originalAssignment;

        return assignment && assignment.getTask() || null;
    },

    /**
     * Checks if this node is properly synchronized with original node.
     *
     * @return {Boolean}
     */
    isInSyncWithOriginal : function() {
        var me         = this,
            result     = true,
            assignment,
            resource,
            task,
            span,
            utilizationInfo;

        if (me.isSurrogateAssignment()) {

            assignment = me.getOriginalAssignment(),
            task       = me.getOriginalTask();

            if (assignment) {
                result = result && (me.getId() == me.self.getSurrogateIdFor(assignment));
            }
            if (task && !task.isUnscheduled()) {
                result = result && (me.getStartDate() - me.adjustStartDateToTick(task.getStartDate()) === 0);
                result = result && (me.getEndDate()   - me.adjustEndDateToTick(task.getEndDate())     === 0);
            }
        }
        else if (me.isSurrogateSummary()) {

            resource = me.getOriginalResource();
            span     = me.calculateSurrogateSummaryTimeSpan();

            result = result && (me.getId() == me.self.getSurrogateIdFor(resource));
            result = result && (me.getStartDate() - span.startDate === 0);
            result = result && (me.getEndDate()   - span.endDate   === 0);
        } else {
            // <debug>
            Ext.Error.raise('Unknown surrogate type');
            // </debug>
            // @TODO: #2773 - Rhyno parse error - Syntax error while building the app
            var foo = false;
        }

        // Utilization info calculation takes considerable amount of time, thus we check it only if previous checks
        // has passed, not some utilization negotiation strategies might cache previously calculated information,
        // thus this code might not be too slow
        if (result) {
            utilizationInfo = me.getUtilizationNegotiationStrategy().getUtilizationInfoForUtilizationEvent(me);
            result = Ext.Object.equals(me.getUtilizationInfo(), utilizationInfo);
        }

        return result;
    },

    /**
     * Updates the record from original record(s)
     */
    syncFromOriginal : function() {
        var me         = this,
            assignment,
            resource,
            task,
            span,
            utilizationInfo;

        me.beginEdit();

        if (me.isSurrogateAssignment()) {

            assignment = me.getOriginalAssignment(),
            task       = me.getOriginalTask();

            if (assignment) {
                me.setId(me.self.getSurrogateIdFor(assignment));
            }
            if (task && !task.isUnscheduled()) {
                me.setStartEndDate(
                    me.adjustStartDateToTick(task.getStartDate()),
                    me.adjustEndDateToTick(task.getEndDate())
                );
            }
        }
        else if (me.isSurrogateSummary()) {

            resource = me.getOriginalResource();

            if (resource) {
                me.setId(me.self.getSurrogateIdFor(resource));
            }

            span     = me.calculateSurrogateSummaryTimeSpan();
            me.setStartEndDate(span.startDate, span.endDate);
        }

        // Some strategies might cache the utilization information, thus this might not take too much time.
        utilizationInfo = me.getUtilizationNegotiationStrategy().getUtilizationInfoForUtilizationEvent(me);
        if (!Ext.Object.equals(me.getUtilizationInfo(), utilizationInfo)) {
            me.setUtilizationInfo(utilizationInfo);
        }

        me.endEdit();
    },

    getUtilizationInfo : function() {
        var me = this,
            info = me.get(me.utilizationInfoField);

        if (!info) {
            info = me.getUtilizationNegotiationStrategy().getUtilizationInfoForUtilizationEvent(me);
            me.set(me.utilizationInfoField, info);
        }

        return info;
    },

    /**
     * Updates original record with values from surrogate
     */
    syncToOriginal : function() {
        var me = this,
            task;

        if (me.isSurrogateAssignment()) {

            task = me.getOriginalTask();

            if (task) {
                task.setStartEndDate(me.getStartDate(), me.getEndDate(), true);
            }
        }
    },

    /**
     * Checks if this node is properly synchronized with similar surrogate node.
     *
     * @param {MyApp.model.UtilizationEvent} surrogateEvent
     * @return {Boolean}
     */
    isInSyncWithSurrogate : function(surrogateEvent) {
        var me = this;

        return me.getOriginalResource() === surrogateEvent.getOriginalResource() &&
               me.getOriginalAssignment() === surrogateEvent.getOriginalAssignment() &&
               me.getId() === surrogateEvent.getId() &&
               me.getStartDate() - surrogateEvent.getStartDate() === 0 &&
               me.getEndDate() - surrogateEvent.getEndDate() === 0 &&
               Ext.Object.equals(me.getUtilizationInfo(), surrogateEvent.getUtilizationInfo());
    },

    /**
     * Updates the record from similar surrogate record
     *
     * @param {MyApp.model.UtilizationEvent} surrogateEvent
     */
    syncFromSurrogate : function(surrogateEvent) {
        var me = this;

        me.originalResource   = surrogateEvent.originalResource;   // direct property access is intentional here
        me.originalAssignment = surrogateEvent.originalAssignment; // direct property access is intentional here

        me.beginEdit();

        me.setId(surrogateEvent.getId());
        me.setStartEndDate(surrogateEvent.getStartDate(), surrogateEvent.getEndDate());
        // Objects are compared by reference by default, so we can't rely on default set() method logic to check
        // if the value is the same.
        if (!Ext.Object.equals(me.getUtilizationInfo(), surrogateEvent.getUtilizationInfo())) {
            me.setUtilizationInfo(surrogateEvent.getUtilizationInfo());
        }

        me.endEdit();
    },

    /**
     * Returns an utilization info for this surrogate event for particular interval during which the event designating
     * an assignment or assignment summary lasts. Which intervals are defined for the event depend on event start/end
     * dates and ticks which are used by event's utilization info calculation strategy. Default strategy
     * {@link MyApp.model.UtilizationRecord.DefaultUtilizationNegotiationStrategy} uses daily intervals
     * (which start at the beginning of a day), other strategies might use different ticks. Programmer must know
     * the context, i.e. strategy, in which the particular surrogate event record is used to properly use this method.
     *
     * @param {Date} intervalStartDate
     * @return {Object}  info
     * @return {Boolean} info.isUtilized
     * @return {Number}  info.allocationMs
     * @return {Number}  info.allocationDeltaMs
     * @return {Boolean} info.isOverallocated
     * @return {Boolean} info.isUnderallocated
     */
    getUtilizationInfoForInterval : function(intervalStartDate) {
        var me = this;
        return me.getUtilizationNegotiationStrategy().getUtilizationInfoForAssignmentEventInterval(me, intervalStartDate);
    },

    /**
     * Iterates over intervals defined for this surrogate event calling callback for each
     *
     * @param {Function} callback
     * @param {Date} callback.intervalStartDate
     * @param {Date} callback.intervalEndDate
     */
    forEachInterval : function(callback) {
        var me = this;
        return me.getUtilizationNegotiationStrategy().forEachTimeSpanInterval(me, callback);
    },

    calculateSurrogateSummaryTimeSpan : function() {
        var me = this;
        return me.getUtilizationNegotiationStrategy().calculateResourceAssignmentsTimespan(me.getOriginalResource());
    },

    adjustStartDateToTick : function(date) {
        var me = this;
        return me.getUtilizationNegotiationStrategy().adjustStartDateToTick(date);
    },

    adjustEndDateToTick : function(date) {
        var me = this;
        return me.getUtilizationNegotiationStrategy().adjustEndDateToTick(date);
    },

    clone : function(session) {
        var me = this,
            clone = me.callParent([session]);

        clone.setUtilizationNegotiationStrategy(me.getUtilizationNegotiationStrategy());

        return clone;
    },

    inheritableStatics : {
        /**
         * Returns id for a surrogate record corresponding to original assignment or resource record
         *
         * @return {String}
         */
        getSurrogateIdFor : function(record) {
            var id = record.getId();

            if (record instanceof Gnt.model.Resource) {
                id = 'resource-' + id;
            }
            else if (record instanceof Gnt.model.Assignment) {
                id = 'assignment-' + id;
            }
            else {
                // <debug>
                Ext.Error.raise('Wrong original record type');
                // </debug>
                // @TODO: #2773 - Rhyno parse error - Syntax error while building the app
                var foo = false;
            }

            return id;
        }
    }
});
