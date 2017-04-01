/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.data.ResourceStore
@extends Sch.data.ResourceStore

A class representing the collection of the resources - {@link Gnt.model.Resource} records.

*/

Ext.define('Gnt.data.ResourceStore', {

    requires    : [
        'Gnt.model.Resource'
    ],

    extend      : 'Sch.data.ResourceStore',

    storeId     : 'resources',
    model       : 'Gnt.model.Resource',
    alias       : 'store.gantt_resourcestore',


    /**
     * @property {Gnt.data.TaskStore} taskStore The task store to which this resource store is associated.
     * Usually is configured automatically, by the task store itself.
     */
    taskStore   : null,

    constructor : function (config) {

        this.callParent([config]);

        this.on({
            load            : this.normalizeResources,
            remove          : this.onResourceRemoved,

            // Our internal listeners should be ran before any client listeners
            priority        : 100
        });
    },


    normalizeResources : function () {
        // scan through all resources and re-assign the "calendarId" property to get the listeners in place
        this.each(function (resource) {
            if (!resource.normalized) {
                var calendarId      = resource.getCalendarId();

                if (calendarId) resource.setCalendarId(calendarId, true);

                resource.normalized     = true;
            }
        });
    },

    // Performance optimization possibility: Assignment store datachange will cause a full refresh
    // so removing a resource will currently cause 2 refreshes. Not critical since this is not a very common use case
    onResourceRemoved : function(store, resources) {
        var assignmentStore = this.getAssignmentStore();

        Ext.Array.each(resources, function(resource) {
            assignmentStore.removeAssignmentsForResource(resource);
        });
    },

    /**
     * Returns the associated task store instance.
     *
     * @return {Gnt.data.TaskStore|null}
     */
    getTaskStore : function() {
        return this.taskStore;
    },

    /**
     * Sets associated task store instance
     *
     * @param {Gnt.data.TaskStore} store
     */
    setTaskStore : function(store) {
        this.taskStore = store;
    },

    /**
     * Returns the associated assignment store instance.
     *
     * @return {Gnt.data.AssignmentStore|null}
     */
    getAssignmentStore : function() {
        var taskStore = this.getTaskStore();
        return taskStore && taskStore.getAssignmentStore() || null;
    },

    /**
     * Returns the associated dependency store
     *
     * @return {Gnt.data.DependencyStore|null}
     */
    getDependencyStore : function() {
        var taskStore = this.getTaskStore();
        return taskStore && taskStore.getDependencyStore() || null;
    }
});
