/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.model.UtilizationResource', {
    extend   : 'Sch.model.Resource',

    requires : [
        'Ext.data.NodeInterface'
    ],

    uses : [
        'Gnt.model.Resource',
        'Gnt.model.Assignment'
    ],

    customizableFields: [
        { name: 'TaskName' },
        { name: 'TaskSequenceNumber' }
    ],

    taskNameField           : 'TaskName',
    taskSequenceNumberField : 'TaskSequenceNumber',

    originalResource   : null,
    originalAssignment : null,

    /**
     * @constructor
     */
    constructor : function(cfg) {
        var me = this,
            originalResource,
            originalAssignment;

        if (cfg.originalResource) {
            originalResource = cfg.originalResource;
            delete cfg.originalResource;
        }

        if (cfg.originalAssignment) {
            originalAssignment = cfg.originalAssignment;
            delete cfg.originalAssignment;
        }

        me.callParent(arguments);

        originalResource   && me.setOriginalResource(originalResource);
        originalAssignment && me.setOriginalAssignment(originalAssignment);
    },

    /**
     * Returns original resource corresponding to this surrogate.
     *
     * @return {Gnt.model.Resource|null}
     */
    getOriginalResource : function() {
        return this.originalResource || this.originalAssignment && this.originalAssignment.getResource();
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
     * @param {Gnt.model.Assignment} resource
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
     * Returns original task corresponding to this surrogate.
     *
     * @return {Gnt.model.Task|null}
     */
    getOriginalTask : function() {
        var assignment = this.getOriginalAssignment();
        return assignment && assignment.getTask() || null;
    },

    /**
     * Checks whether this node represents a surrogate resource
     *
     * @return {Boolean}
     */
    isSurrogateResource : function() {
        return !this.getOriginalAssignment();
    },

    /**
     * Checks whether this node represents a surrogate assignment
     *
     * @return {Boolean}
     */
    isSurrogateAssignment : function() {
        return !!this.getOriginalAssignment();
    },

    /**
     * Checks if this node is properly synchronized with original node.
     *
     * @return {Boolean}
     */
    isInSyncWithOriginal : function() {
        var me         = this,
            resource   = me.getOriginalResource(),   // Name, Id
            assignment = me.getOriginalAssignment(), // Id
            task       = me.getOriginalTask(),       // Name, Sequence number
            result     = true;

        if (assignment) {
            result = result && (me.getId() == me.self.getSurrogateIdFor(assignment));
            if (task) {
                result = result && (me.getTaskName() == task.getName());
                result = result && (me.getTaskSequenceNumber() == task.getSequenceNumber());
            }
        }
        else if (resource) {
            result = result && (me.getId() == me.self.getSurrogateIdFor(resource));
            result = result && (me.getName() == resource.getName());
        }

        return result;
    },

    /**
     * Updates the record from original record(s)
     */
    syncFromOriginal : function() {
        var me         = this,
            resource   = me.getOriginalResource(),
            assignment = me.getOriginalAssignment(),
            task       = me.getOriginalTask();

        me.beginEdit();

        if (resource && !assignment) {
            me.setId(me.self.getSurrogateIdFor(resource));
        }

        if (assignment && !resource) {
            me.setId(me.self.getSurrogateIdFor(assignment));
        }

        if (resource) {
            me.setName(resource.getName());
        }

        if (task) {
            me.setTaskName(task.getName());
            me.setTaskSequenceNumber(task.getSequenceNumber());
        }

        me.endEdit();
    },

    /**
     * Updates original record with values from surrogate
     */
    syncToOriginal : function() {
        // <debug>
        Ext.Error.raise('Not implemented');
        // </debug>
        // @TODO #2773 - Rhyno parse error - Syntax error while building the app
        var foo = false;
    },

    /**
     * Checks if this node is properly synchronized with similar surrogate node.
     *
     * @param {Gnt.model.UtilizationResource} surrogateRecord
     * @return {Boolean}
     */
    isInSyncWithSurrogate : function(surrogateRecord) {
        var me = this;

        return me.originalResource === surrogateRecord.originalResource &&
               me.originalAssignment === surrogateRecord.originalAssignment &&
               me.getName() === surrogateRecord.getName() &&
               me.getTaskName() === surrogateRecord.getTaskName() &&
               me.getTaskSequenceNumber() === surrogateRecord.getTaskSequenceNumber();
    },

    /**
     * Updates the record from similar surrogate record
     *
     * @param {MyApp.model.UtilizationResoure} surrogateRecord
     */
    syncFromSurrogate : function(surrogateRecord) {
        var me = this;

        me.originalResource   = surrogateRecord.originalResource;   // direct property access is intentional here
        me.originalAssignment = surrogateRecord.originalAssignment; // direct property access is intentional here

        me.beginEdit();

        me.setId(surrogateRecord.getId());
        me.setName(surrogateRecord.getName());
        me.setTaskName(surrogateRecord.getTaskName());
        me.setTaskSequenceNumber(surrogateRecord.getTaskSequenceNumber());

        me.endEdit();
    },

    inheritableStatics : {
        /**
         * Returns id for a surrogate record corresponding to original resource or assignment record
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
    },

    /**
     * TODO: remove this override when it's fixed in Sch.model.Resource
     */
    getResourceStore : function() {
        return this.store;
    }

}, function () {
    Ext.data.NodeInterface.decorate(this);
});
