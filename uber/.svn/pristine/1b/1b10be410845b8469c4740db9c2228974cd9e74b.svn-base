/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * @class Gnt.data.AssignmentStore
 * @extends Schdata.AssignmentStore
 *
 * A class representing a collection of assignments between tasks in the {@link Gnt.data.TaskStore} and resources
 * in the {@link Gnt.data.ResourceStore}.
 *
 * Contains a collection of {@link Gnt.model.Assignment} records.
 */
Ext.define('Gnt.data.AssignmentStore', {
    extend                : 'Sch.data.AssignmentStore',

    requires              : [
        'Gnt.model.Assignment'
    ],

    model                 : 'Gnt.model.Assignment',
    alias                 : 'store.gantt_assignmentstore',

    storeId               : 'assignments',

    // Overriden from Sch.data.AssignmentStore due to the logic required is handled by the Gantt codebase
    attachToEventStore    : Ext.emptyFn,
    attachToResourceStore : Ext.emptyFn,

    /**
     * Returns the associated task store instance.
     *
     * @return {Gnt.data.TaskStore}
     */
    getTaskStore : function () {
        return this.getEventStore();
    },

    /**
     * Sets associated task store instance
     *
     * @parem {Gnt.data.TaskStore} store
     */
    setTaskStore : function (store) {
        return this.setEventStore(store);
    },


    /**
     * Maps over task assignments.
     *
     * @param {Gnt.model.Task/Mixed} task
     * @param {Function} [fn=Ext.identityFn]
     * @param {Function} [filterFn=Ext.returnTrue]
     * @return {Mixed[]}
     */
    mapAssignmentsForTask : function (task, fn, filterFn) {
        return this.mapAssignmentsForEvent(task, fn, filterFn);
    },

    /**
     * Returns all assignments for a given task.
     *
     * @param {Gnt.model.Task/Mixed} task
     * @return {Gnt.model.Assignment[]}
     */
    getAssignmentsForTask : function(task) {
        return this.getAssignmentsForEvent(task);
    },

    /**
     * Removes all assignments for given event
     *
     * @param {Gnt.model.Task/Mixed} task
     */
    removeAssignmentsForTask : function(task) {
        return this.removeAssignmentsForEvent(task);
    },

    /**
     * Returns all resources assigned to a task.
     *
     * @param {Gnt.model.Task/Mixed} task
     * @return {Gnt.model.Resource[]}
     */
    getResourcesForTask : function (task) {
        return this.getResourcesForEvent(task);
    },

    /**
     * Returns all tasks assigned to a resource
     *
     * @param {Gnt.model.Resource/Mixed} resource
     * @return {Gnt.model.Task[]}
     */
    getTasksForResource : function (resource) {
        return this.getEventsForResource(resource);
    },

    /**
     * Creates and adds assignment record for a given task and a resource.
     *
     * @param {Gnt.model.Task/Mixed} task
     * @param {Gnt.model.Resource/Mixed} resource
     * @return {Gnt.model.Assignment}
     */
    assignTaskToResource : function(task, resource, units) {
        return this.assignEventToResource(task, resource, function(assignment) {
            assignment.setUnits(units);
            return assignment;
        });
    },

    /**
     * Removes assignment record for a given task and a resource.
     *
     * @param {Gnt.model.Task/Mixed} task
     * @param {Gnt.model.Resource/Mixed} resource
     * @return {Gnt.model.Assignment}
     */
    unassignTaskFromResource : function (task, resource) {
        return this.unassignEventFromResource(task, resource);
    },

    /**
     * Checks whether a task is assigned to a resource.
     *
     * @param {Gnt.model.Task/Mixed} evnt
     * @param {Gnt.model.Resource/Mixed} resource
     * @param {Function} [fn] Function which will resieve assignment record if one present
     * @return {Boolean}
     */
    isTaskAssignedToResource : function (task, resource, fn) {
        return this.isEventAssignedToResource(task, resource, fn);
    },

    /**
     * Returns assignment record for given task and resource
     *
     * @param {Gnt.model.Task} event
     * @param {Gnt.model.Resource} resource
     * @return {Gnt.model.Assignment}
     */
    getAssignmentForTaskAndResource : function (task, resource) {
        return this.getAssignmentForEventAndResource(task, resource);
    }
});
