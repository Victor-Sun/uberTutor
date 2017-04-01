/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.data.ResourceUtilizationStore', {
    extend : 'Sch.data.ResourceTreeStore',
    uses   : [
        'Gnt.data.ResourceUtilizationEventStore',
        'Gnt.model.Resource',
        'Gnt.model.Assignment',
        'Gnt.model.UtilizationEvent',
        'Gnt.model.utilization.ResourceStoreUtilizationNegotiationStrategy'
    ],

    mixins : [
        'Gnt.model.utilization.UtilizationNegotiationStrategyMixin'
    ],

    model      : 'Gnt.model.UtilizationResource',
    eventModel : 'Gnt.model.UtilizationEvent',

    autoDestroy : true,

    storeId : null,

    root  : { expanded : true },
    proxy : 'memory',

    config : {
        defaultResourceExpandedState : false,
        taskStore                    : null,
        resourceStore                : null,
        assignmentStore              : null,
        timeAxis                     : null,
        underUtilizationThreshold    : null,
        overUtilizationThreshold     : null

        // Can't make eventStore to be a configuration property since parent class has similar property defined
        //eventStore                 : null
    },

    folderSort : false,

    taskStoreDetacher       : null,
    resourceStoreDetacher   : null,
    assignmentStoreDetacher : null,
    eventStoreDetacher      : null,
    timeAxisDetacher        : null,

    utilizationInfoCache : null,

    syncingWithOriginal : false,

    utilizationNegotiationStrategyClass : 'Gnt.model.utilization.ResourceStoreUtilizationNegotiationStrategy',

    /**
     * @constructor
     */
    constructor : function (config) {
        var me = this;

        me.eventModel = Ext.ClassManager.get(this.eventModel);

        me.utilizationInfoCache = {};

        me.callParent([config]);

        me.initUtilizationNegotiationStrategyMixin({
            utilizationNegotiationStrategy : Ext.create(me.utilizationNegotiationStrategyClass, {
                underUtilizationThreshold : me.getUnderUtilizationThreshold(),
                overUtilizationThreshold  : me.getOverUtilizationThreshold(),
                resourceUtilizationStore  : me
            })
        });

        if (!me.getEventStore()) {
            me.setEventStore(new Gnt.data.ResourceUtilizationEventStore({
                model         : me.eventModel,
                resourceStore : me
            }));
        }

        me.setupSorters();
    },

    destroy : function () {
        var me = this;

        me.callParent(arguments);

        clearTimeout(me.fireReassignTimer);

        Ext.destroy(me.getEventStore());
        me.setEventStore(null);
    },

    setupSorters : function () {
        var me = this;

        me.setSorters([{
            sorterFn : function (a, b) {
                var result;

                // Sorting resources alphanumerically
                if (a.isSurrogateResource() && b.isSurrogateResource() && a.getName() > b.getName()) {
                    result = 1;
                }
                else if (a.isSurrogateResource() && b.isSurrogateResource() && a.getName() < b.getName()) {
                    result = -1;
                }
                else if (a.isSurrogateResource() && b.isSurrogateResource()) {
                    result = 0;
                }
                // Placing resources before assignments
                else if (a.isSurrogateResource() && b.isSurrogateAssignment()) {
                    result = 1;
                }
                else if (a.isSurrogateAssignment() && b.isSurrogateResource()) {
                    result = -1;
                }
                // Assignments are compared according to strategy
                else if (a.isSurrogateAssignment() && b.isSurrogateAssignment()) {
                    result = me.getUtilizationNegotiationStrategy().assignmentsComparator(a.getOriginalAssignment(), b.getOriginalAssignment());
                }
                else {
                    result = 0;
                }

                return result;
            }
        }]);
    },

    updateTaskStore : function (newStore, oldStore) {
        var me = this;

        oldStore && Ext.destroy(me.taskStoreDetacher);

        if (newStore) {
            me.setResourceStore(newStore.getResourceStore() || me.setResourceStore());
            me.setAssignmentStore(newStore.getAssignmentStore() || me.getAssignmentStore());

            me.taskStoreDetacher = me.mon(newStore, {
                load        : me.onSourceDataTouch,
                refresh     : me.onSourceDataTouch,
                clear       : me.onSourceDataTouch,
                add         : me.onSourceDataTouch,
                append      : me.onSourceDataTouch,
                update      : me.onSourceDataTouch,
                remove      : me.onSourceDataTouch,
                bulkremove  : me.onSourceDataTouch,
                move        : me.onSourceDataTouch,
                scope       : me,
                destroyable : true
            });
        }
    },

    updateResourceStore : function (newStore, oldStore) {
        var me = this;

        oldStore && Ext.destroy(me.resourceStoreDetacher);

        if (newStore) {
            me.resourceStoreDetacher = me.mon(newStore, {
                load        : me.onSourceDataTouch,
                refresh     : me.onSourceDataTouch,
                clear       : me.onSourceDataTouch,
                add         : me.onSourceDataTouch,
                update      : me.onSourceDataTouch,
                remove      : me.onSourceDataTouch,
                bulkremove  : me.onSourceDataTouch,
                scope       : me,
                destroyable : true
            });
        }
    },

    updateAssignmentStore : function (newStore, oldStore) {
        var me = this;

        oldStore && Ext.destroy(me.assignmentStoreDetacher);

        if (newStore) {
            me.assignmentStoreDetacher = me.mon(newStore, {
                load        : me.onSourceDataTouch,
                refresh     : me.onSourceDataTouch,
                clear       : me.onSourceDataTouch,
                add         : me.onSourceDataTouch,
                update      : me.onSourceDataTouch,
                remove      : me.onSourceDataTouch,
                bulkremove  : me.onSourceDataTouch,
                scope       : me,
                destroyable : true
            });
        }
    },

    setEventStore : function (newStore) {
        var me       = this,
            oldStore = me.eventStore;

        me.callParent([newStore]);

        if (oldStore != newStore) {
            oldStore && Ext.destroy(me.eventStoreDetacher);

            if (newStore) {
                me.utilizationEventStoreDetacher = me.mon(newStore, {
                    update : me.onUtilizationEventStoreUpdate,
                    scope  : me
                });
            }
        }
    },

    updateTimeAxis : function (newTimeAxis, oldTimeAxis) {
        var me = this;

        oldTimeAxis && Ext.destroy(me.timeAxisDetacher);

        if (newTimeAxis) {
            me.timeAxisDetacher = me.mon(newTimeAxis, {
                reconfigure : me.onSourceDataTouch,
                scope       : me,
                destroyable : true
            });
        }
    },

    makeSurrogateResource : function (resource) {
        var me    = this,
            model = me.model;

        return new me.model({
            Id               : model.getSurrogateIdFor(resource),
            originalResource : resource,
            expanded         : me.getDefaultResourceExpandedState()
        });
    },

    makeSurrogateAssignment : function (assignment) {
        var model = this.model;

        return new model({
            Id                 : model.getSurrogateIdFor(assignment),
            originalAssignment : assignment,
            leaf               : true
        });
    },

    makeSurrogateAssignmentEvent : function (resourceOrAssignment) {
        var me         = this,
            eventModel = me.eventModel,
            id         = eventModel.getSurrogateIdFor(resourceOrAssignment);

        return new eventModel({
            Id                             : id,
            ResourceId                     : id,
            originalResource               : resourceOrAssignment instanceof Gnt.model.Resource && resourceOrAssignment || null,
            originalAssignment             : resourceOrAssignment instanceof Gnt.model.Assignment && resourceOrAssignment || null,
            utilizationNegotiationStrategy : me.getUtilizationNegotiationStrategy()
        });
    },

    makeSurrogateResourceBranch : function (resource) {
        var me                        = this,
            surrogateResource         = me.makeSurrogateResource(resource),
            surrogateAssignmentEvents = [],
            surrogateSummaryEvent;

        // Surrogate assignments
        resource.forEachAssignment(function (assignment) {
            var task = assignment.getTask();

            if (task && !task.isUnscheduled()) {
                surrogateResource.appendChild(me.makeSurrogateAssignment(assignment));
                surrogateAssignmentEvents.push(me.makeSurrogateAssignmentEvent(assignment));
            }
        });

        // Surrogate summary (only if there are any assignments)
        if (surrogateAssignmentEvents.length > 0) {
            surrogateSummaryEvent = me.makeSurrogateAssignmentEvent(resource);
            surrogateAssignmentEvents.push(surrogateSummaryEvent);
        }

        return {
            resource : surrogateResource,
            events   : surrogateAssignmentEvents
        };
    },

    /**
     * Fills in the resource utilization store from primary (Task/Resource/Assignment) stores.
     */
    fillStore : function () {
        var me                    = this,
            root                  = me.getRoot(),
            resourceStore         = me.getResourceStore(),
            utilizationEventStore = me.getEventStore(),
            resources             = resourceStore && resourceStore.getRange(),
            resourcesToAdd        = [],
            eventsToAdd           = [];

        me.syncingWithOriginal = true;

        root.removeAll();
        utilizationEventStore.removeAll();

        resources && Ext.Array.each(resources, function (resource) {
            var branch  = me.makeSurrogateResourceBranch(resource);

            resourcesToAdd.push(branch.resource);
            eventsToAdd = eventsToAdd.concat(branch.events);
        });

        utilizationEventStore.add(eventsToAdd);
        root.appendChild(resourcesToAdd);

        me.sorters && me.sorters.getCount() && root.sort();

        me.syncingWithOriginal = false;
    },

    /**
     * The task is to effectively synchronize two data structures.
     * First (Current) is a pair of:
     *  - resource tree store with nodes designating resources
     *    and leafs designating resource assignments;
     *  - flat event store, where each event designates a task assignment.
     * Second (Correct) is a triplet of:
     *  - resource store;
     *  - task store;
     *  - assignment store.
     *
     * Synchronization is one way, from second triplet to first pair of stores.
     *
     * How do I synchronize? The primary assumption here is that touching DOM is expensive.
     *
     * I have current structure in this store and it's event store
     * I might build correct structure in a separate store with events suspended,
     * sort it accordingly (store's sort method support external sorters collection).
     * Then I shall compare and build a list of actions executing which, one by one, I will
     * make current data structure to be correct.
     *
     * Synchronization steps:
     * ----------------------
     * 1. Create and fill correct store (with events suspended).
     * 2. Sort correct store using sorters collection from current store.
     * 3. Walk through the current store and remove all assignments from this store and events store
     *    which are not present in the correct store.
     * 4. Walk through the current store and remove all resource from this store which are not
     *    present in the correct store.
     * 5. Walk through the current store:
     *    - for surrogate resource node check if it's referencing the same original resource record
     *      as the one present in correct store for the surrogate resource with the same id, if objects
     *      are different then setup the current surrogate resource node to use new original record
     *      - otherwise just sync the node from original;
     *    - for surrogate assignment node check if it's referencing the same original assignment record
     *      as the one present in correct store for the surrogate assignment with the same id, if objects
     *      are different then setup the current surrogate assignment node to use new original record,
     *      the same do for the surrogate assignment event in current event store
     *      - otherwise just sync the node from original;
     * 6. Walk through the correct store and for each surrogate resource which is not present in the current store
     *    insert it into current store under the same index it's present in the correct store.
     * 7. Walk through the correct store and for each surrogate assignment which is not present in the current store
     *    insert it into current store under the same index it's present in the correct store. Do the same for
     *    the surrogate assignment event.
     * 8. Walk through the correct store and check if corresponding records in current store are under the same index
     *    if not, then rearrange them according to correct store.
     *
     * After actions are executed the correct store should be destroyed, just to clean up some memory, as well
     * for surrogate events were not shared by two event stores.
     *
     * @private
     */
    diffSyncStore : function () {
        var me = this,
            correctStore;

        me.syncingWithOriginal = true;

        /**
         * @event sync-start
         *
         * Fired when store starts synchronization with original (Task/Resource/Assignment) stores
         *
         * @param {Gnt.data.ResourceUtilizationStore} me
         */
        me.fireEvent('sync-start', me);

        // Temporary solution until since buffered renderer sometimes skips fine grained store events and
        // doesn't updates corresponding grid rows. For now we're forced to fire 'refresh' at the end of synchronization
        me.suspendEvents();

        // Creating and filling correct store
        correctStore = new me.self({
            underUtilizationThreshold : me.getUnderUtilizationThreshold(),
            overUtilizationThreshold  : me.getOverUtilizationThreshold()
        });
        correctStore.suspendEvents();
        correctStore.setTimeAxis(me.getTimeAxis());
        correctStore.setTaskStore(me.getTaskStore());
        correctStore.fillStore();

        // Remove outdated surrogate assingments
        me.removeOutdatedSurrogateAssignments(me, correctStore);
        // Remove outdated surrogate resources
        me.removeOutdatedSurrogateResources(me, correctStore);
        // Add new surrogate resources
        me.addNewSurrogateResources(me, correctStore);
        // Add new surrogate assignments
        me.addNewSurrogateAssignments(me, correctStore);
        // Sync present surrogate assingments
        me.updatePresentSurrogates(me, correctStore);
        // Rearrange present surrogate records
        me.rearrangePresentSurrogates(me, correctStore);

        me.utilizationInfoCache = correctStore.utilizationInfoCache;

        // Clean up
        Ext.destroy(correctStore);

        // Temporary solution until I understand why buffered renderer sometimes skips fine grained sotre events and
        // doesn't updates corresponding grid rows. For now I'm forced to fire 'refresh' at the and of synchronization
        me.resumeEvents();

        /**
         * @event sync-end
         *
         * Fired when store completes synchronization with original (Task/Resource/Assignment) stores
         *
         * @param {Gnt.data.ResourceUtilizationStore} me
         */
        me.fireEvent('sync-complete', me);
        me.fireEvent('refresh', me);

        me.syncingWithOriginal = false;
    },

    removeOutdatedSurrogateAssignments : function (currentStore, correctStore) {
        var currentRoot    = currentStore.getRoot(),
            nodesToDelete  = [],
            eventsToDelete = [];

        currentRoot.cascadeBy(function (currentNode) {
            var correctNode = correctStore.getModelById(currentNode.getId());

            if (!currentNode.isRoot() && currentNode.isSurrogateAssignment()) {

                if (!correctNode || (correctNode.parentNode.getId() != currentNode.parentNode.getId())) {

                    nodesToDelete.push(currentNode);
                    eventsToDelete = eventsToDelete.concat(currentNode.getEvents());
                }
            }
            else if (!currentNode.isRoot() && currentNode.isSurrogateResource()) {
                // Remove events from the eventStore bound to the Panel which:
                // 1. Are parent nodes in the 'true' store with 0 children (meaning a resource is unassigned)
                // or
                // 2. Are surrogate assignments that ended up on a summary row due to reassign (drag drop etc)
                eventsToDelete = eventsToDelete.concat(
                    Ext.Array.filter(
                        currentNode.getEvents(),
                        function (event) {
                            return event.isSurrogateAssignment() || (correctNode && correctNode.childNodes.length === 0);
                        }
                    )
                );
            }
        });

        currentStore.getEventStore().remove(eventsToDelete);

        Ext.Array.each(nodesToDelete, function (currentNode) {
            currentNode.remove();
        });
    },

    removeOutdatedSurrogateResources : function (currentStore, correctStore) {
        var currentRoot    = currentStore.getRoot(),
            nodesToDelete  = [],
            eventsToDelete = [];

        currentRoot.cascadeBy(function (currentNode) {
            var correctNode;

            if (!currentNode.isRoot() && currentNode.isSurrogateResource()) {

                correctNode = correctStore.getModelById(currentNode.getId());

                if (!correctNode) {
                    nodesToDelete.push(currentNode);
                    eventsToDelete = eventsToDelete.concat(currentNode.getEvents());
                }
            }
        });

        currentStore.getEventStore().remove(eventsToDelete);

        Ext.Array.each(nodesToDelete, function (currentNode) {
            currentNode.remove();
        });
    },

    addNewSurrogateResources : function (currentStore, correctStore) {
        var correctRoot = correctStore.getRoot(),
            currentRoot = currentStore.getRoot(),
            eventStore  = currentStore.getEventStore(),
            eventsToAdd = [];

        correctRoot.cascadeBy(function (correctNode) {
            var currentNode;

            if (!correctNode.isRoot() && correctNode.isSurrogateResource()) {

                currentNode = currentStore.getModelById(correctNode.getId());

                if (!currentNode) {

                    currentRoot.insertChild(
                        correctNode.get('index'),
                        currentStore.makeSurrogateResource(
                            correctNode.getOriginalResource()
                        )
                    );
                    eventsToAdd.push(
                        currentStore.makeSurrogateAssignmentEvent(correctNode.getOriginalResource())
                    );
                }
                else {

                    //#2762 - ResourceUtilizationStore should update resource summaries 
                    if (currentNode.getEvents().length === 0) {
                        eventsToAdd = eventsToAdd.concat(Ext.Array.map(correctNode.getEvents(), function (ev) {
                            return ev.clone();
                        }));
                    }
                }
            }
        });

        eventStore.add(eventsToAdd);
    },

    addNewSurrogateAssignments : function (currentStore, correctStore) {
        var correctRoot       = correctStore.getRoot(),
            currentEventStore = currentStore.getEventStore(),
            toAdd             = [];

        correctRoot.cascadeBy(function (correctNode) {
            var currentNode,
                correctParent,
                currentParent;

            if (!correctNode.isRoot() && correctNode.isSurrogateAssignment()) {

                currentNode = currentStore.getModelById(correctNode.getId());

                if (!currentNode) {

                    correctParent = correctNode.parentNode;
                    currentParent = currentStore.getModelById(correctParent.getId());

                    if (currentParent) {
                        currentParent.insertChild(
                            correctNode.get('index'),
                            currentStore.makeSurrogateAssignment(
                                correctNode.getOriginalAssignment()
                            )
                        );
                        toAdd = toAdd.concat(Ext.Array.map(correctNode.getEvents(), function (ev) {
                            return ev.clone();
                        }));
                    }
                }
            }
        });

        currentEventStore.add(toAdd);
    },

    updatePresentSurrogates : function (currentStore, correctStore) {
        var currentRoot = currentStore.getRoot();

        currentRoot.cascadeBy(function (currentNode) {
            var correctNode,
                correctEvent,
                currentEvent;

            if (!currentNode.isRoot()) {

                correctNode = correctStore.getModelById(currentNode.getId());

                if (correctNode) {

                    if (!currentNode.isInSyncWithSurrogate(correctNode)) {
                        currentNode.syncFromSurrogate(correctNode);
                    }

                    correctEvent = correctNode.getEvents()[0]; // We always have 1 to [0,1] relation between assignment and event

                    if (correctEvent) {

                        currentEvent = currentStore.getEventStore().getModelById(correctEvent.getId());

                        // Event might be moved by drag & drop to another resource and removed at assignments remove step
                        if (currentEvent && !currentEvent.isInSyncWithSurrogate(correctEvent)) {
                            currentEvent.syncFromSurrogate(correctEvent);
                        }
                    } else {
                        currentNode.syncFromOriginal(correctNode);
                    }
                }
            }
        });
    },

    rearrangePresentSurrogates : function (currentStore, correctStore) {
        var correctRoot = correctStore.getRoot();

        correctRoot.cascadeBy(function (correctNode) {
            var currentNode;

            if (!correctNode.isRoot()) {

                currentNode = currentStore.getModelById(correctNode.getId());

                if (currentNode && currentNode.get('index') != correctNode.get('index')) {

                    currentNode.parentNode.insertChild(correctNode.get('index'), currentNode);
                }
            }
        });
    },

    clearUtilizationInfoCache : function () {
        this.utilizationInfoCache = {};
    },

    /**
     * Returns this store (utilization information) model which corresponding to particular original model a resource or
     * an assignment.
     *
     * @param {Gnt.model.Resource|Gnt.model.Assignment} originalModel
     * @return {Gnt.model.UtilizationResource}
     */
    getModelByOriginal : function (originalModel) {
        var me = this;
        return me.getModelById(me.model.getSurrogateIdFor(originalModel));
    },

    onSourceDataTouch : Ext.Function.createBuffered(function () {
        var me = this;

        if (!me.isDestroyed) {
            me.clearUtilizationInfoCache();
            me.diffSyncStore();
        }
    }, 10),

    onUtilizationEventStoreUpdate : function (utilizationEventStore, surrogateEvent, operation, modifiedFieldNames) {
        var me = this;

        if (!me.syncingWithOriginal) {
            if (!surrogateEvent.isInSyncWithOriginal()) {
                surrogateEvent.syncToOriginal();
            }

            if (Ext.Array.contains(modifiedFieldNames, surrogateEvent.resourceIdField)) {

                var surrogateResource = surrogateEvent.getResource();

                // TODO REMOVE
                me.fireReassignTimer = Ext.Function.defer(
                    function () {
                        me.fireEvent(
                            'reassign',
                            me,
                            surrogateResource.getOriginalResource(),
                            surrogateResource.getOriginalTask(), // might be null
                            surrogateEvent.getOriginalResource(),
                            surrogateEvent.getOriginalTask(),
                            surrogateEvent.getOriginalAssignment()
                        );
                    },
                    1
                );
            }
        }
    }
});
