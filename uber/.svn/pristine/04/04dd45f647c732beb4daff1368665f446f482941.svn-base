/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.data.CrudManager

A class implementing a central collection of all data stores related to the Gantt chart.
It allows you to load all stores in a single server request and persist all of their changes in one request as well. This
 helps you use a transactional 'all-or-nothing' approach to saving your data.
This class uses AJAX as a transport mechanism and JSON as the encoding format.

# Gantt stores

The class supports all the Gantt specific stores: resources, assignments, dependencies, calendars and tasks.
For these stores, the class has separate configs ({@link #resourceStore}, {@link #assignmentStore}, {@link #dependencyStore}, {@link #taskStore})
to register them. The class can also grab them from the task store (this behavior can be changed using {@link #addRelatedStores} config).

    var taskStore = Ext.create('Gnt.data.TaskStore', {
        calendarManager : calendarManager,
        resourceStore   : resourceStore,
        dependencyStore : dependencyStore,
        assignmentStore : assignmentStore
    });

    var crudManager = Ext.create('Gnt.data.CrudManager', {
        autoLoad        : true,
        // We specify TaskStore only. The rest stores will be taken from it.
        taskStore       : taskStore,
        transport       : {
            load    : {
                url     : 'php/read.php'
            },
            sync    : {
                url     : 'php/save.php'
            }
        }
    });


# Calendars

The CrudManager class supports bulk loading of project calendars.
To do this, the {@link #calendarManager} config has to be specified or it can be specified on a {@link Gnt.data.TaskStore#calendarManager task store}
(while having {@link #addRelatedStores} is enabled).

    var calendarManager   = Ext.create('Gnt.data.CalendarManager', {
        calendarClass   : 'Gnt.data.calendar.BusinessTime'
    });

    ...

    var taskStore     = Ext.create('MyTaskStore', {
        // taskStore calendar will automatically be set when calendarManager gets loaded
        calendarManager : calendarManager,
        resourceStore   : resourceStore,
        dependencyStore : dependencyStore,
        assignmentStore : assignmentStore
    });

    var crudManager   = Ext.create('Gnt.data.CrudManager', {
        autoLoad        : true,
        taskStore       : taskStore,
        transport       : {
            load    : {
                url     : 'php/read.php'
            },
            sync    : {
                url     : 'php/save.php'
            }
        }
    });

# AJAX request configuration

To configure AJAX request parameters please take a look at the {@link #transport} config.

    var crudManager = Ext.create('Sch.data.CrudManager', {
        autoLoad        : true,
        taskStore       : taskStore,
        transport       : {
            load    : {
                url         : 'php/read.php',
                // use GET request
                method      : 'GET',
                // pass request JSON in "rq" parameter
                paramName   : 'rq',
                // extra HTTP request parameters
                params      : {
                    foo     : 'bar'
                }
            },
            sync    : {
                url     : 'php/save.php'
            }
        }
    });


# Extra stores

Along with the Gantt specific stores any number of additional stores can be specified
using {@link #stores} config on a construction step or {@link #addStore} method in the runtime:

    var crudManager = Ext.create('Gnt.data.CrudManager', {
        // extra stores
        stores          : [ 'departments', 'messages' ],
        taskStore       : taskStore,
        transport       : {
            load    : {
                url     : 'php/read.php'
            },
            sync    : {
                url     : 'php/save.php'
            }
        }
    });

    // append 'documents' store
    crudManager.addStore('documents');

    // now when we registered all the stores let's load them
    crudManager.load();

* **Note:** Any extra stores provided in {@link #stores} config will be loaded **before** the gantt specific stores.
If for some reason you need to change that loading order you should use {@link #addStore} method:

    // append store3 to the end so it will be loaded last
    crudManager.addStore(store3);

*/
Ext.define('Gnt.data.CrudManager', {
    extend              : 'Sch.crud.AbstractManager',

    mixins              : ['Sch.crud.encoder.Json', 'Sch.crud.transport.Ajax'],

    /**
     * @cfg {Gnt.data.CalendarManager/Object} calendarManager A calendar manager instance or its descriptor.
     */
    calendarManager     : null,
    /**
     * @cfg {Gnt.data.TaskStore/String/Object} taskStore Tasks store or its descriptor or its identifier.
     */
    taskStore           : null,
    /**
     * @cfg {Gnt.data.DependencyStore/Object} dependencyStore A store with dependencies or its descriptor.
     */
    dependencyStore     : null,
    /**
     * @cfg {Gnt.data.ResourceStore/Object} resourceStore A store with resources or its descriptor.
     */
    resourceStore       : null,
    /**
     * @cfg {Gnt.data.AssignmentStore/Object} assignmentStore A store with assignments or its descriptor.
     */
    assignmentStore     : null,

    /**
     * @cfg {Boolean} addRelatedStores
     * When set to `true` this class will try to get the {@link #calendarManager}, {@link #dependencyStore}, {@link #resourceStore} and {@link #assignmentStore} stores from
     * the specified {@link #taskStore} instance.
     */
    addRelatedStores    : true,

    constructor : function (config) {
        config  = config || {};

        var calendarManager = config.calendarManager || this.calendarManager,
            taskStore       = config.taskStore || this.taskStore || new Gnt.data.TaskStore({ proxy : 'memory' }),
            assignmentStore = config.assignmentStore || this.assignmentStore,
            resourceStore   = config.resourceStore || this.resourceStore,
            dependencyStore = config.dependencyStore || this.dependencyStore,
            // list of stores to add
            stores          = [];

        // retrieve stores registered on the provided taskStore
        if (taskStore && config.addRelatedStores !== false) {
            var extracted   = this.getTaskStoreInfo(taskStore, config);

            calendarManager = calendarManager || extracted.calendarManager;
            assignmentStore = assignmentStore || extracted.assignmentStore;
            resourceStore   = resourceStore || extracted.resourceStore;
            dependencyStore = dependencyStore || extracted.dependencyStore;
        }

        // calendars go first in the stores loading order
        if (calendarManager) {
            // Call this early manually to be able to add listeners before calling the superclass constructor
            this.mixins.observable.constructor.call(this, config);

            this.addCalendarManager(calendarManager, stores);
        }

        // ..then resources, assignments, dependencies and finally tasks
        if (resourceStore) stores.push(resourceStore);
        if (assignmentStore) stores.push(assignmentStore);
        if (dependencyStore) stores.push(dependencyStore);
        if (taskStore) stores.push(taskStore);

        if (stores.length) {
            var syncSequence   = [];

            // For applying sync results we have a different order:
            // calendars -> resources -> tasks -> assignments -> dependencies
            if (calendarManager) syncSequence.push(calendarManager);
            if (resourceStore) syncSequence.push(resourceStore);
            if (taskStore) syncSequence.push(taskStore);
            if (assignmentStore) syncSequence.push(assignmentStore);
            if (dependencyStore) syncSequence.push(dependencyStore);

            if (syncSequence.length) {
                config.syncApplySequence    = (config.syncApplySequence || config.stores || []).concat(syncSequence);
            }

            var _stores = config.stores || this.stores;
            if (_stores && !Ext.isArray(_stores)) _stores = [_stores];

            // all the Gantt related stores will go after the user defined stores (specified in config.stores)
            config.stores = (_stores || []).concat(stores);
        }

        this.callParent([ config ]);

        // make sure we have properties set to proper stores descriptors
        this.calendarManager    = this.getStoreDescriptor(calendarManager);
        this.resourceStore      = this.getStoreDescriptor(resourceStore);
        this.assignmentStore    = this.getStoreDescriptor(assignmentStore);
        this.dependencyStore    = this.getStoreDescriptor(dependencyStore);
        this.taskStore          = this.getStoreDescriptor(taskStore);
    },


    getTaskStoreInfo : function (taskStore, config) {
        if (!(taskStore instanceof Ext.data.AbstractStore)) {
            if (typeof taskStore == 'string') {
                taskStore   = Ext.data.StoreManager.get(taskStore);
            } else {
                taskStore   = taskStore.store;
            }
        }

        var result          = {},
            calendarManager = config.calendarManager,
            assignmentStore = config.assignmentStore,
            resourceStore   = config.resourceStore,
            dependencyStore = config.dependencyStore;

        if (!calendarManager) result.calendarManager = taskStore.calendarManager;
        if (!assignmentStore) result.assignmentStore = taskStore.getAssignmentStore();
        if (!resourceStore) result.resourceStore = taskStore.getResourceStore();
        if (!dependencyStore) result.dependencyStore = taskStore.getDependencyStore();

        return result;
    },


    addCalendarManager : function (calendarManager, stores) {
        var store, descriptor;

        if (calendarManager instanceof Ext.data.AbstractStore) {
            store       = calendarManager;
            descriptor  = { store : calendarManager };

        } else if (typeof calendarManager == 'object') {
            store       = calendarManager.store;
            descriptor  = calendarManager;

        } else {
            calendarManager = Ext.data.StoreManager.get(calendarManager);
            store           = calendarManager;
            descriptor      = { store : calendarManager };
        }

        var model   = (store.getModel && store.getModel() || store.model).prototype;

        // register calendar manager sub-stores being kept in "Days" field
        if (!descriptor.stores) {
            descriptor.stores  = [{
                storeId     : model.daysField,
                idProperty  : model.idProperty
            }];
        }

        this.calendarManager    = descriptor;

        // on calendar manager data get loaded we gonna set the project calendar
        store.on('load', this.onCalendarManagerLoad, this);

        this.mon(store, {
            dayadd          : this.onStoreChange,
            dayupdate       : this.onStoreChange,
            dayremove       : this.onStoreChange,
            daybulkremove   : this.onStoreChange,
            scope           : this
        });

        stores.push(descriptor);
    },


    onCalendarManagerLoad : function (store) {
        var projectCalendar     = store.getProjectCalendar(),
            oldCalendarId       = projectCalendar && projectCalendar.getCalendarId(),
            newCalendarId       = store.metaData && store.metaData.projectCalendar;

        // if project calendar has changed
        if (oldCalendarId != newCalendarId) {
            store.setProjectCalendar(newCalendarId);
        }
    },


    applyLoadResponse : function () {
        // let's ignore calendars events during data loading since we don't want tasks to get moved after stores loading
        var cm  = this.getCalendarManager();
        cm && cm.suspendCalendarsEvents();

        this.callParent(arguments);

        cm && cm.resumeCalendarsEvents();
    },


    /**
     * Returns the calendar manager bound to the crud manager.
     * @return {Gnt.data.CalendarManager} The calendar manager bound to the crud manager.
     */
    getCalendarManager : function () {
        return this.calendarManager && this.calendarManager.store;
    },

    /**
     * Returns the resource store bound to the crud manager.
     * @return {Gnt.data.ResourceStore} The resource store bound to the crud manager.
     */
    getResourceStore : function () {
        return this.resourceStore && this.resourceStore.store;
    },

    /**
     * Returns the dependency store bound to the crud manager.
     * @return {Gnt.data.DependencyStore} The dependency store bound to the crud manager.
     */
    getDependencyStore : function () {
        return this.dependencyStore && this.dependencyStore.store;
    },

    /**
     * Returns the assignment store bound to the crud manager.
     * @return {Gnt.data.AssignmentStore} The assignment store bound to the crud manager.
     */
    getAssignmentStore : function () {
        return this.assignmentStore && this.assignmentStore.store;
    },

    /**
     * Returns the task store bound to the crud manager.
     * @return {Gnt.data.TaskStore} The task store bound to the crud manager.
     */
    getTaskStore : function () {
        return this.taskStore && this.taskStore.store;
    },


    prepareUpdated : function (list, stores, storeInfo) {
        if (list[0] instanceof Gnt.model.Task) {
            // Root should not be updated since the gantt doesn't modify this (though Ext JS might)
            list = Ext.Array.filter(list, function(node) { return !node.isRoot(); });

            var result  = this.callParent([list, stores, storeInfo]);

            // if resetIdsBeforeSync mode is enabled and we deal with tasks
            // we need to reset ids for tasks segments as well
            if (this.resetIdsBeforeSync) {
                var segmentsField   = list[0].segmentsField,
                    proto           = Ext.ClassManager.get(list[0].segmentClassName).prototype,
                    idProperty      = proto.idProperty,
                    phantomIdField  = proto.phantomIdField;

                for (var i = 0; i < result.length; i++) {
                    var segmentsData    = result[i][segmentsField];
                    if (segmentsData) {
                        for (var j = 0; j < segmentsData.length; j++) {
                            var segment = segmentsData[j];
                            if (segment[phantomIdField]) delete segment[idProperty];
                        }
                    }
                }
            }

            return result;
        }

        return this.callParent(arguments);
    },


    prepareAdded : function (list) {
        var result  = this.callParent(arguments);

        // if resetIdsBeforeSync mode is enabled and we deal with tasks
        // we need to reset ids for tasks segments as well
        if (this.resetIdsBeforeSync && list[0] instanceof Gnt.model.Task) {
            var segmentsField   = list[0].segmentsField,
                idProperty      = Ext.ClassManager.get(list[0].segmentClassName).prototype.idProperty;

            for (var i = 0; i < result.length; i++) {
                var segmentsData    = result[i][segmentsField];
                if (segmentsData) {
                    for (var j = 0; j < segmentsData.length; j++) {
                        delete segmentsData[j][idProperty];
                    }
                }
            }
        }

        return result;
    },


    applyChangesToTask : function (record, changes) {
        // apply changes to segments
        if (changes.hasOwnProperty(record.segmentsField)) {

            var segments        = record.getSegments(),
                segmentsField   = record.segmentsField,
                phantomIdField  = segments && segments[0].phantomIdField,
                idProperty      = segments && segments[0].idProperty,
                segmentsChanges = changes[segmentsField];

            // If the task is segmented we're gonna try to modify existing segments one by one
            if (segments) {
                // loop over transferred segments if any
                if (segmentsChanges) {

                    for (var i = segmentsChanges.length - 1; i >= 0; i--) {
                        // get transferred segment change
                        var segmentChange   = segmentsChanges[i],
                            phantomId       = segmentChange[phantomIdField],
                            id              = segmentChange[idProperty],
                            segment         = null;

                        // let's find corresponding segment to update
                        for (var j = 0; j < segments.length; j++) {
                            segment     = segments[j];

                            // we detect it using either phantom or real id
                            if ((segment.get(phantomIdField) == phantomId) || (segment.getId() == id)) {
                                // let's apply transferred changes to found segment
                                this.applyChangesToRecord(segment, segmentChange);
                                break;
                            }
                        }
                    }
                }

                // need to get rid of "Segments" field since we already loaded segments changes
                // (otherwise the task will do a simple setSegments() call)
                delete changes[segmentsField];
            }
        }
    },


    applyChangesToRecord : function (record, changes, stores) {
        // if we deal with a task let's call special applyChangesToTask method before
        // it will apply changes to the task segments (if they passed)
        if (record instanceof Gnt.model.Task) {
            this.ignoreUpdates++;

            this.applyChangesToTask.apply(this, arguments);

            this.ignoreUpdates--;
        }

        this.callParent(arguments);
    }
});
