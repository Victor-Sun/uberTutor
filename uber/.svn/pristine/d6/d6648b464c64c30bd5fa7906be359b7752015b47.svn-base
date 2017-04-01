/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.data.DependencyStore
@extends Ext.data.Store

 A class representing a collection of dependencies between the tasks in the {@link Gnt.data.TaskStore}.
 Contains a collection of {@link Gnt.model.Dependency} records.

## Custom validation

You can subclass this Store class like any other Ext JS class and add your own custom validation for Dependencies:

    Ext.define('MyDependencyStore', {
        extend              : 'Gnt.data.DependencyStore',

        // Override this method to provide custom logic defining what constitutes a valid dependency
        getDependencyError  : function (from, to) {
            // Support default validation rules
            var error = this.callParent(arguments);
            if (error) return error;

            // Example: Don`t allow links between tasks of type A, to tasks with type B
            var isDepInput = from instanceof Ext.data.Model;
            var sourceTask = isDepInput ? from.getSourceTask() : this.getTaskById(from);
            var targetTask = isDepInput ? from.getTargetTask() : this.getTaskById(to);

            // Let`s return a custom error code -100 for such case (it must be a negative value)
            if (sourceTask.getType() !== 'A' || targetTask.getType() !== 'B') return -100;

            // No errors
            return 0;
        },

        ...
    })

*/
Ext.define('Gnt.data.DependencyStore', {
    extend          : 'Sch.data.DependencyStore',

    uses            : [
        'Gnt.data.util.TaskDependencyCache'
    ],

    model           : 'Gnt.model.Dependency',

    alias           : 'store.gantt_dependencystore',

    /**
     * @property {Gnt.data.TaskStore} taskStore The task store to which this dependency store is associated.
     * Usually is configured automatically, by the task store itself.
     *
     * @readonly
     * @deprecated 4.2 Use {@link getTaskStore} instead
     */
    taskStore       : null,

    methodsCache    : null,

    /**
     * @cfg {Boolean} strictDependencyValidation A boolean flag indicating whether a strict validation of dependencies should be applied.
     * This mode will detect indirect cycles between parent-child relationships.
     * For example, the following cases will be considered as invalid (here `P1`, `P2`, `P3` are parent tasks and `T1`, `T2`, `T3` - their children respectively):

                P1 =========
                T1 ++++++          P2 =========
                                   T2    +++++        P3 ===============
                                                      T3 +++++++++++++++

     * These cases will be considered as cycles:
     *
     * - if we have dependency `P1---->P2` then dependency `T2---->P1` will be invalid
     * - if we have dependency `P1---->T2` then dependency `T2---->T1` will be invalid
     * - if we have dependency `P1---->P2` then dependency `T2---->T1` will be invalid
     * - if we have dependencies `P1---->P2---->P3` then dependency `T3---->T1` will be invalid
     * - if we have dependency `P1---->T2` then dependency `P2---->T1` will be invalid
     * - if we have dependencies `P1---->T2` `P2---->T3` then dependency `P3---->T1` will be invalid
     * - if we have dependencies `T1---->P2` `T2---->P3` then dependency `T3---->P1` will be invalid
     *
     * and these cases will be treated as transitivity (or duplicating) dependency when {@link #transitiveDependencyValidation} is set to `True`:
     *
     * - if we have dependency `P1---->P2` then dependency `T1---->P2` will be invalid
     * - if we have dependency `P1---->T2` then dependency `T1---->T2` will be invalid
     * - if we have dependency `P1---->P2` then dependency `T1---->T2` will be invalid
     * - if we have dependencies `P1---->P2---->P3` then dependency `T1---->T3` will be invalid
     * - if we have dependency `P1---->T2` then dependency `T1---->P2` will be invalid
     * - if we have dependencies `P1---->T2` `P2---->T3` then dependency `T1---->P3` will be invalid
     * - if we have dependencies `T1---->P2` `T2---->P3` then dependency `P1---->T3` will be invalid
     */
    strictDependencyValidation  : false,

    /**
     * @cfg {Boolean} transitiveDependencyValidation When set to true, alternative routes between tasks are considered invalid.
     * For example if we have dependencies `A---->B---->C` and `D---->C` then dependency `A---->D` will be treated as invalid
     * because it builds an alternative route from task `A` to task `C`.
     *
     * When {@link #strictDependencyValidation} is `True` this setting also enables detecting transitivity between groups of tasks.
     * Please see {@link #strictDependencyValidation} description for examples.
     */
    transitiveDependencyValidation  : false,

    /**
     * @cfg {String[]} allowedDependencyTypes
     *
     * Represents a list of the dependency types that are allowed in this store. Any {@link Gnt.panel.Gantt} panel associated with this store
     * will query this store for this information. If set to `null` (the default value) all types of dependencies are allowed.
     * To restrict the allowed dependencies set, provide it as an array of strings, corresponding to the names in the {@link Gnt.model.Dependency#Type} enumerable.
     *
     * For example:
     *
     *          allowedDependencyTypes : [ 'StartToEnd', 'EndToEnd' ]
     *
     */
    allowedDependencyTypes      : null,

    /**
     * @cfg {Boolean} allowParentTaskDependencies Set to `false` to disallow dependencies involving parent tasks.
     */
    allowParentTaskDependencies : true,


    constructor : function(config) {
        var me = this;

        me.callParent([config]);

        me.init();
    },

    createEventDependencyCache : function() {
        return new Gnt.data.util.TaskDependencyCache(this);
    },

    init : function() {
        var me = this;

        me.methodsCache   = {};

        me.on({
            add         : me.onDependencyAdd,
            update      : me.onDependencyUpdate,
            // seems we can't use "bulkremove" event, because one can listen to `remove` event on the task store
            // and expect correct state in it
            remove      : me.onDependencyRemove,
            priority    : 100,
            scope       : me
        });
    },


    // Checks if a provided task is fully scheduled and sets its empty start/end/duration fields otherwise.
    scheduleTask : function (task) {
        var taskStore = this.getTaskStore(),
            startDate = task.getStartDate(),
            endDate   = task.getEndDate();

        if (!startDate || !endDate) {
            task.beginEdit();

            // if task doesn't have a start date let's set it to the project start date
            if (!startDate) {
                task.setStartDateWithoutPropagation(taskStore.getProjectStartDate(), undefined !== task.getDuration());
            }
            // default duration is gonna be 1 duration unit (day by default)
            if (!endDate) {
                task.setDurationWithoutPropagation(1);
            }

            task.endEdit();
        }
    },


    // Checks tasks linked by dependency if they are fully scheduled
    // and fills their missing start/end/duration values otherwise.
    scheduleLinkedTasks : function (from, to) {
        // schedule predecessor (if required)
        this.scheduleTask(from);

        // if successor has no start date and `cascadeChanges` is disabled we'll try to
        // schedule it by alignByIncomingDependencies() call
        //
        // TODO: I consider it a code smell to check task store property from within a dependency store
        if (!to.getStartDate() && !from.getTaskStore().cascadeChanges) {
            to.alignByIncomingDependencies();
        }

        // schedule successor (if required)
        this.scheduleTask(to);
    },


    onDependencyAdd : function (me, dependencies) {
        // Looping over added dependnecies but
        //     need to ignore the initial "add" events for data provided in the config
        Ext.Array.each(dependencies, function(dependency) {
            var from, to;

            // <debug>
            if (!me.isValidDependencyType(dependency.getType())) {
                throw 'This dependency type is invalid. Check Gnt.data.DependencyStore#allowedDependencyTypes value';
            }
            // </debug>

            // ensure that tasks being linked are fully scheduled only if we are not currently undoing or redoing
            from = dependency.getSourceTask();
            to   = dependency.getTargetTask();

            if (from && to && !me.isUndoingOrRedoing()) {
                me.scheduleLinkedTasks(from, to);
            }
        });

        me.resetMethodsCache();
    },


    onDependencyRemove : function (me, dependencies, index, isMove) {
        var taskStore       = this.getTaskStore();
        var tasksToAdjust   = [];

        Ext.Array.each(dependencies, function (dependency) {
            // dependencies are already removed from the dependecies store and has no reference to it
            // so `getTaskStore` on the dependency instance won't work, need to provide `taskStore` manually
            var to = dependency.getTargetTask(taskStore);

            // Only tasks which are part of the tree store, i.e. which have parent node should be adjusted,
            // there's no point in adjusting tasks which are removed from the store.
            // Note: it's yet to be investigated why tasks with parentNode == null are returned by
            //       dependency.getSourceTask() / dependency.getTargetTask()
            if (to &&!me.isUndoingOrRedoing() && !to.isManuallyScheduled() && to.isTaskStored()) {
                tasksToAdjust.push(to);
            }
        });

        me.resetMethodsCache();

        // Readjust any target tasks only if we are not currently undoing and tasks are contained by a tree store
        if (!me.isUndoingOrRedoing()) {
            Ext.Array.each(tasksToAdjust, function(task) {
                task.adjustToCalendarWithoutPropagation();
            });
        }
    },

    onDependencyUpdate : function (me, dependency, operation) {
        var taskStore,
            previous,
            newFromTask,
            newToTask,
            fromChanged,
            toChanged;

        if (operation != Ext.data.Model.COMMIT) {
            taskStore   = me.getTaskStore();
            previous    = dependency.previous;
            newFromTask = dependency.getSourceTask();
            newToTask   = dependency.getTargetTask();
            fromChanged = previous && dependency.fromField in previous;
            toChanged   = previous && dependency.toField in previous;

            // Re-scheduling updated tasks only if we are not currently undoing or redoing
            if ((fromChanged || toChanged) && newFromTask && newToTask && !me.isUndoingOrRedoing()) {
                me.scheduleLinkedTasks(newFromTask, newToTask);
            }

            me.resetMethodsCache();
        }
    },

    // TODO: document
    reduceTaskDependencies : function(task, reduceFn, result, flat, depsGetterFn) {
        return this.reduceEventDependencies(task, reduceFn, result, flat, depsGetterFn);
    },

    // TODO: document
    reduceTaskIncomingDependencies : function(task, reduceFn, result, flat) {
        return this.reduceEventIncomingDependencies(task, reduceFn, result, flat);
    },

    // TODO: document
    reduceTaskOutgoingDependencies : function(task, reduceFn, result, flat) {
        return this.reduceEventOutgoingDependencies(task, reduceFn, result, flat);
    },

    // TODO: document
    mapTaskDependencies : function(task, fn, filterFn, flat, depsGetterFn) {
        return this.mapEventDependencies(task, fn, filterFn, flat, depsGetterFn);
    },

    // TODO: document
    mapTaskIncomingDependencies : function(task, fn, filterFn, flat) {
        return this.mapEventIncomingDependencies(task, fn, filterFn, flat);
    },

    // TODO: document
    mapTaskOutgoingDependencies : function(task, fn, filterFn, flat) {
        return this.mapEventOutgoingDependencies(task, fn, filterFn, flat);
    },

    /**
     * Returns all dependencies of for a certain task (both incoming and outgoing)
     *
     * @param {Gnt.model.Task} task
     *
     * @return {Gnt.model.Dependency[]}
     *
     * @deprecated 4.2 Use {@link getTaskDependencies} instead
     */
    getDependenciesForTask : function(task) {
        return this.getEventDependencies(task, true);
    },


    /**
     * Returns all dependencies of for a certain task (both incoming and outgoing)
     *
     * @param {Gnt.model.Task} task
     * @param {Boolean} flat
     * @return {Gnt.model.Dependency[]}
     */
    getTaskDependencies : function(task, flat) {
        return this.getEventDependencies(task, flat);
    },


    /**
     * Returns all incoming dependencies of the given task
     *
     * @param {Gnt.model.Task} task
     *
     * @return {Gnt.model.Dependency[]}
     *
     * @deprecated 4.2 Use {@link getTaskIncomingDependencies} instead
     */
    getIncomingDependenciesForTask : function(task, doNotClone) {
        return this.getEventIncomingDependencies(task, true);
    },

    // TODO: document
    getTaskIncomingDependencies : function(task, flat) {
        return this.getEventIncomingDependencies(task, flat);
    },

    /**
     * Returns all outcoming dependencies of a task
     *
     * @param {Gnt.model.Task} task
     *
     * @return {Gnt.model.Dependency[]}
     *
     * @deprecated 4.2 Use {@link getTaskOutgoingDependencies} instead
     */
    getOutgoingDependenciesForTask : function (task, doNotClone) {
        return this.getEventOutgoingDependencies(task, true);
    },

    // TODO: document
    getTaskOutgoingDependencies : function(task, flat) {
        return this.getEventOutgoingDependencies(task, flat);
    },

    // TODO: document
    getTaskPredecessors : function(task, flat) {
        return this.getEventPredecessors(task, flat);
    },

    // TODO: document
    getTaskSuccessors : function(task, flat) {
        return this.getEventSuccessors(task, flat);
    },

    // TODO: document
    removeTaskDependencies : function(task, flat) {
        return this.removeEventDependencies(task, flat);
    },

    // TODO: document
    removeTaskIncomingDependencies : function(task, flat) {
        return this.removeEventIncomingDependencies(task, flat);
    },

    // TODO: document
    removeTaskOutgoingDependencies : function(task, flat) {
        return this.removeEventOutgoingDependencies(task, flat);
    },

    // @private
    // Serializes array of dependencies. Used during cache key calculation.
    getKeyByDeps : function (dependencies, fromField, toField) {
        if (!dependencies || !dependencies.length) return '';

        var key     = '';

        for (var i = 0, l = dependencies.length; i < l; i++) {
            var dep     = dependencies[i];

            key += (dep.getSourceId && dep.getSourceId() || dep[fromField]) + ':' +
                (dep.getTargetId && dep.getTargetId() || dep[toField]) + ',';
        }

        return key;
    },


    buildCacheKey : function (sourceId, targetId, ignoreDepRecords, addDepRecords, context) {
        var fromField       = context.fromField || (context.fromField = this.model.prototype.fromField),
            toField         = context.toField || (context.toField = this.model.prototype.toField),
            ignoreDepKey    = context.ignoreDepKey,
            addDepKey       = context.addDepKey;

        // let's preserve key part calculated by ignoreDepRecords and addDepRecords since they will not change
        if (!context.hasOwnProperty('ignoreDepKey')) {
            context.ignoreDepKey    = ignoreDepKey    = ignoreDepRecords && this.getKeyByDeps(ignoreDepRecords, fromField, toField) || '';
            context.addDepKey       = addDepKey       = addDepRecords && this.getKeyByDeps(addDepRecords, fromField, toField) || '';
        }

        // calculate cache key for provided arguments
        return sourceId + '-' + targetId + '-' + ignoreDepKey + '-' + addDepKey;
    },


    /**
     * Returns `true` if there is a dependency (either direct or "transitive") between tasks
     * with `sourceId` and `targetId`
     *
     * @param {String} sourceId
     * @param {String} targetId
     * @param {Gnt.model.Dependency[]} [ignoreDepRecords] If provided, dependencies in this array will be ignored during transitivity search.
     * @param {Gnt.model.Dependency[]/Object[]} [addDepRecords] If provided, search will be done supposing that specified records exist in the dependency store.
     *
     * @return {Boolean}
     */
    hasTransitiveDependency: function (sourceId, targetId, ignoreDepRecords, addDepRecords, context) {
        context             = context || { visitedTasks : {}};

        // calculate cache key for provided arguments
        var cacheKey        = this.buildCacheKey(sourceId, targetId, ignoreDepRecords, addDepRecords, context);

        var visitedTasks    = context.visitedTasks,
            extraSuccessors = context.extraSuccessors;

        if (this.isCachedResultAvailable('hasTransitiveDependency', cacheKey)) {
            return this.methodsCache.hasTransitiveDependency[ cacheKey ];
        }

        var me              = this,
            fromField       = context.fromField,
            toField         = context.toField,
            sourceTask      = this.getTaskById(sourceId),
            i, l;

        // protection from cycles
        if (visitedTasks[ sourceId ]) return false;

        visitedTasks[ sourceId ] = true;

        if (sourceTask) {
            // if list of dependencies to be created is provided let's
            // organize it as a hash containing successors list by task ids
            if (addDepRecords && !extraSuccessors) {
                extraSuccessors = context.extraSuccessors     = {};

                for (i = 0, l = addDepRecords.length; i < l; i++) {
                    var dep     = addDepRecords[i];
                    var from    = dep.getSourceId && dep.getSourceId() || dep[fromField];

                    extraSuccessors[from] = extraSuccessors[from] || [];
                    extraSuccessors[from].push(dep);
                }
            }

            var dependency,
                successors      = sourceTask.successors;

            // add successors to be added to existing successors
            if (extraSuccessors && extraSuccessors[sourceId]) successors = successors.concat(extraSuccessors[sourceId]);

            for (i = 0, l = successors.length; i < l; i++) {
                dependency  = successors[ i ];
                var target  = dependency.getTargetId && dependency.getTargetId() || dependency[toField];

                if ((!ignoreDepRecords || Ext.Array.indexOf(ignoreDepRecords, dependency) == -1) &&
                    (target === targetId || me.hasTransitiveDependency(target, targetId, ignoreDepRecords, addDepRecords, context))) {
                    return this.setCachedResult('hasTransitiveDependency', cacheKey, true);
                }
            }
        }

        return this.setCachedResult('hasTransitiveDependency', cacheKey, false);
    },


    successorsHaveTransitiveDependency : function (sourceId, targetId, ignoreDepRecords, addDepRecords, context) {
        context             = context || {};

        // calculate cache key for provided arguments
        var cacheKey        = this.buildCacheKey(sourceId, targetId, ignoreDepRecords, addDepRecords, context);

        var task            = targetId instanceof Gnt.model.Task ? targetId : this.getTaskById(targetId);

        if (this.isCachedResultAvailable('successorsHaveTransitiveDependency', cacheKey)) {
            return this.methodsCache.successorsHaveTransitiveDependency[ cacheKey ];
        }

        for (var i = 0, l = task.successors.length; i < l; i++) {
            var toId    = task.successors[i].getTargetId();

            if (this.hasTransitiveDependency(sourceId, toId, ignoreDepRecords, addDepRecords) ||
                this.predecessorsHaveTransitiveDependency(sourceId, toId, ignoreDepRecords, addDepRecords) ||
                this.successorsHaveTransitiveDependency(sourceId, toId, ignoreDepRecords, addDepRecords, context))
                    return this.setCachedResult('successorsHaveTransitiveDependency', cacheKey, true);
        }

        return this.setCachedResult('successorsHaveTransitiveDependency', cacheKey, false);
    },


    predecessorsHaveTransitiveDependency : function (sourceId, targetId, ignoreDepRecords, addDepRecords, context) {
        context             = context || {};

        // calculate cache key for provided arguments
        var cacheKey        = this.buildCacheKey(sourceId, targetId, ignoreDepRecords, addDepRecords, context);

        var task            = sourceId instanceof Gnt.model.Task ? sourceId : this.getTaskById(sourceId);

        if (this.isCachedResultAvailable('predecessorsHaveTransitiveDependency', cacheKey)) {
            return this.methodsCache.predecessorsHaveTransitiveDependency[ cacheKey ];
        }

        for (var i = 0, l = task.predecessors.length; i < l; i++) {
            var fromId    = task.predecessors[i].getSourceId();

            if (this.hasTransitiveDependency(fromId, targetId, ignoreDepRecords, addDepRecords) ||
                this.successorsHaveTransitiveDependency(fromId, targetId, ignoreDepRecords, addDepRecords) ||
                this.predecessorsHaveTransitiveDependency(fromId, targetId, ignoreDepRecords, addDepRecords, context))
                    return this.setCachedResult('predecessorsHaveTransitiveDependency', cacheKey, true);
        }

        return this.setCachedResult('predecessorsHaveTransitiveDependency', cacheKey, false);
    },


    isPartOfTransitiveDependency : function (sourceId, targetId, ignoreDepRecords, addDepRecords) {
        var task    = sourceId instanceof Gnt.model.Task ? sourceId : this.getTaskById(sourceId);

        if (!task.predecessors.length && !task.successors.length) return false;

        if (task.predecessors.length) {
            return this.predecessorsHaveTransitiveDependency.apply(this, arguments);
        } else {
            return this.successorsHaveTransitiveDependency.apply(this, arguments);
        }
    },


    getCycle : function (context) {
        context             = context || {};

        Ext.applyIf(context, {
            ignoreTasks     : {},
            visitedTasks    : {},
            path            : [],
            task            : this.getAt(0).getSourceTask()
        });

        var visitedTasks    = context.visitedTasks,
            ignoreTasks     = context.ignoreTasks,
            path            = context.path,
            task            = context.task,
            taskId          = task.getId();

        if (ignoreTasks[taskId]) return;

        path.push(task);

        if (visitedTasks[taskId]) return path;

        visitedTasks[taskId]    = true;

        var successors          = task.successors;

        for (var i = 0, l = successors.length; i < l; i++) {
            context.task    = successors[ i ].getTargetTask();

            var cycle       = this.getCycle(context);

            if (cycle) return cycle;
        }

        path.pop();
        delete visitedTasks[taskId];
    },


    // TODO improve so it can detect all cycles
    getCycles : function () {
        var me          = this,
            result      = [],
            ignoreTasks = {};

        this.each(function (dep) {
            var path    = me.getCycle({ task : dep.getSourceTask(), ignoreTasks : ignoreTasks });

            if (path) {
                for (var i = 0, l = path.length; i < l; i++) {
                    ignoreTasks[path[i]]    = true;
                }
                result.push(path);
            }
        });

        return result;
    },


    resetMethodsCache : function () {
        this.methodsCache   = {};
    },


    isCachedResultAvailable : function (method, key) {
        return this.methodsCache[method] && this.methodsCache[method].hasOwnProperty(key);
    },


    getCachedResult : function (method, key) {
        return this.methodsCache[ method ][ key ];
    },


    setCachedResult : function (method, key, value) {
        this.methodsCache[method]       = this.methodsCache[method] || {};
        this.methodsCache[method][key]  = value;

        return value;
    },

    //@private
    getGroupTopTasks : function(sourceGroup, targetGroup) {
        var sourceGroupLength    = sourceGroup.length,
            targetGroupLength    = targetGroup.length,
            i                    = sourceGroupLength,
            j                    = targetGroupLength,
            sourceTopParent, targetTopParent;

        do {
            sourceTopParent     = sourceGroup[i];
            targetTopParent     = targetGroup[j];
            i--;
            j--;
        } while (sourceTopParent == targetTopParent && i >= 0 && j>=0);

        return [sourceTopParent, targetTopParent];
    },

    groupsHasTransitiveDependency : function (sourceId, targetId, ignoreDepRecords, addDepRecords, context) {
        var ctx     = context || {
            targets         : null,
            visitedTasks    : {}
        };

        var root                = this.getTaskStore().getRootNode(),
            result              = false,
            me                  = this,
            source              = this.getTaskById(sourceId),
            target              = this.getTaskById(targetId),
            visitedTasks        = ctx.visitedTasks,
            targets             = ctx.targets;

        if (!ctx.targetGroup) {
            ctx.targetGroup     = target.getTopParent(true);
        }

        var fromField       = ctx.fromField || (ctx.fromField = this.model.prototype.fromField),
            toField         = ctx.toField || (ctx.toField = this.model.prototype.toField),
            ignoreDepKey    = ctx.ignoreDepKey,
            addDepKey       = ctx.addDepKey;

        // get groups top elements based on their intersection
        var groups          = this.getGroupTopTasks(source.getTopParent(true), ctx.targetGroup),
            sourceTopParent = groups[0],
            targetTopParent = groups[1];

        if (sourceTopParent === source && targetTopParent === target && source.isLeaf() && target.isLeaf()) {
            return this.hasTransitiveDependency(sourceId, targetId, ignoreDepRecords);
        }

        // let's preserve key part calculated by ignoreDepRecords and addDepRecords since they will not change
        if (!ctx.hasOwnProperty('ignoreDepKey')) {
            ctx.ignoreDepKey    = ignoreDepKey    = ignoreDepRecords && this.getKeyByDeps(ignoreDepRecords, fromField, toField) || '';
            ctx.addDepKey       = addDepKey       = addDepRecords && this.getKeyByDeps(addDepRecords, fromField, toField) || '';
        }

        // calculate cache key for provided arguments
        var cacheKey        = sourceTopParent.getId() + '-' + targetTopParent.getId() + '-' + ignoreDepKey + '-' + addDepKey;

        if (this.isCachedResultAvailable('groupsHasTransitiveDependency', cacheKey)) {
            return this.methodsCache.groupsHasTransitiveDependency[cacheKey];
        }

        // if top target element has changed for this source group
        // then we need to regather "targets" hash
        if (targetTopParent !== ctx.targetTopParent) {
            ctx.targetTopParent     = targetTopParent;
            targets                 = ctx.targets = {};
            // collect children Ids into targets hash
            targetTopParent.cascadeBy(function (task) { targets[ task.getId() ] = true; });
        }

        var extraSuccessors     = ctx.extraSuccessors;

        // if list of dependencies to be created is provided let's
        // organize it as a hash containing successors list by task ids
        if (addDepRecords && !extraSuccessors) {
            extraSuccessors = ctx.extraSuccessors     = {};

            for (var i = 0, l = addDepRecords.length; i < l; i++) {
                var dep     = addDepRecords[i];
                var from    = dep.getSourceId && dep.getSourceId() || dep[fromField];

                extraSuccessors[from] = extraSuccessors[from] || [];
                extraSuccessors[from].push(dep);
            }
        }

        // for each source group task we check if any of its succeeding task is in "targets" hash
        sourceTopParent.cascadeBy(function (task) {
            if (task !== root) {
                var taskId      = task.getId();

                // data cycles protection
                if (visitedTasks[ taskId ]) return false;

                visitedTasks[ taskId ]  = true;

                var successors          = task.successors;

                // add successors to be added to existing successors
                if (extraSuccessors && extraSuccessors[taskId]) successors = successors.concat(extraSuccessors[taskId]);

                for (var i = 0, l = successors.length; i < l; i++) {
                    var dependency  = successors[i],
                        toId        = dependency.getTargetId && dependency.getTargetId() || dependency[toField];
                    // if succeeding task is not in ignore list
                    // and it's in "targets" then we found transitivity
                    // otherwise we go deeper
                    if ((!ignoreDepRecords || Ext.Array.indexOf(ignoreDepRecords, dependency) == -1) &&
                        (targets[toId] || me.groupsHasTransitiveDependency(toId, targetId, ignoreDepRecords, addDepRecords, ctx))) {
                        result  = true;
                        return false;
                    }
                }
            }
        });

        // update cache
        return this.setCachedResult('groupsHasTransitiveDependency', cacheKey, result);
    },


    /**
     * Validates a provided dependency and returns a corresponding error code or zero if no error was detected.
     * This method can validate either an existing {@link Gnt.model.Dependency} instance or a proposed (about to be created) link
     * that can be specified as source and target task identifiers plus the dependency type.
     *
     * If you subclass this class, you can provide your own version of this method.
     * Please note that this method is supposed to return a negative integer error code so
     * ensure that you choose some unused values for any new kind of validation.
     * Don't forget to call the parent implementation if you also want to check for cyclic dependencies etc.
     *
     * These scenarios are considered invalid:
     *
     * - a task linking to itself
     * - a dependency between a child and one of its parents
     * - transitive dependencies (this check is done only when {@link #transitiveDependencyValidation} is set to `True`), e.g. if A -> B, B -> C, then A -> C is not valid, or if A -> B, A -> C, then B -> C is not valid
     * - cyclic dependencies, e.g. if A -> B, B -> C, then C -> A is not valid
     *
     * **Note:** This method behavior depends on {@link #transitiveDependencyValidation} and {@link #strictDependencyValidation} option.
     * The first config enables so called _transitivity_ validation. And when {@link #strictDependencyValidation} is turned on,
     * the system tries to detect cycles (and transitivity if {@link #transitiveDependencyValidation} enabled) cases between groups of tasks.
     *
     * The method can be used either by providing a dependency as the first argument (then `toId` and `type` should be omitted):
     *
     *      // checking dependency record
     *      switch (dependencyStore.getDependencyError(dependency)) {
     *          case -3: case -8: case -5:
     *              alert('This dependency builds duplicating transitivity');
     *              break;
     *          case -4: case -7:
     *              alert('This is a cyclic dependency');
     *              break;
     *          ...
     *      }
     *
     * or by providing identifiers of the source and target tasks as well as the type of the dependency (if `type` is not provided it defaults to End-To-Start):
     *
     *      // check if 11 --> 15 dependency is between parent & child
     *      if (dependencyStore.getDependencyError(11, 15) == -9) {
     *          alert('This is a dependency between parent and its child');
     *      }
     *
     * @param {Gnt.model.Dependency/Mixed} dependencyOrFromId Either a dependency or the source task id
     * @param {Mixed} [toId] The target task id. Should be omitted if `dependencyOrFromId` is {@link Gnt.model.Dependency} instance.
     * @param {Number} [type] The type of the dependency. Should be omitted if `dependencyOrFromId` is {@link Gnt.model.Dependency} instance.
     * @param {Gnt.model.Dependency[]/Object[]} [dependenciesToAdd] If provided, validation will be done assuming that the specified records exist in the dependency store.
     * @param {Gnt.model.Dependency[]} [dependenciesToRemove]  If provided, validation will be done assuming that the specified records DO NOT exist in the dependency store.
     * @return {Number} Returns zero if dependency is valid.
     * Full list of possible values is:
     *
     *  - `0`  dependency is valid
     *  - `-1`  other error (wrong input data provided: empty source/target Id(s) or source Id equals target Id)
     *  - `-2`  source (or target) task is not found
     *  - `-3`  transitive dependency (returned only when {@link #transitiveDependencyValidation} is `True`)
     *  - `-4`  cyclic dependency
     *  - `-5`  transitive dependency (dependency being validated is part of larger transitive route) (returned only when {@link #transitiveDependencyValidation} is `True`)
     *  - `-7`  cyclic dependency between groups
     *  - `-8`  transitive dependency between groups (returned only when {@link #transitiveDependencyValidation} is `True`)
     *  - `-9`  dependency between parent and child
     *  - `-10` wrong dependency type
     *  - `-11` dependencies to/from parent tasks not allowed
     *  - `-12` dependencies to projects not allowed
     *  - `-13` dependencies to other project tasks not allowed
     */
    getDependencyError : function (dependencyOrFromId, toId, type, dependenciesToAdd, dependenciesToRemove, calledFromThisDepModel) {
        // `calledFromThisModel` is used when called from `isValid` method of depedency model
        var fromId, fromTask, toTask;

        var modelInput      = dependencyOrFromId instanceof Gnt.model.Dependency;

        // Normalize input
        if (modelInput) {
            fromId                  = dependencyOrFromId.getSourceId();
            fromTask                = this.getTaskById(fromId);

            // if dependency provided then `toId` and `type` arguments can be skipped
            dependenciesToAdd       = toId;
            dependenciesToRemove    = type;

            // if dependency being validated presented in dependenciesToAdd list
            if (dependenciesToAdd && Ext.Array.contains(dependenciesToAdd, dependencyOrFromId)) {
                // make list copy
                dependenciesToAdd   = dependenciesToAdd.slice();
                // and remove dependency from it
                Ext.Array.remove(dependenciesToAdd, dependencyOrFromId);
            }

            type                    = dependencyOrFromId.getType();
            toId                    = dependencyOrFromId.getTargetId();
            toTask                  = this.getTaskById(toId);

            // if we've been called with dependencies model as 1st arg (modelInput) and that dependency
            // is already in the dep store, this case is identical to called "isValid" method on the dependency record
            if (dependencyOrFromId.store) calledFromThisDepModel = dependencyOrFromId;
        } else {
            fromId          = dependencyOrFromId;
            fromTask        = this.getTaskById(fromId);
            toTask          = this.getTaskById(toId);

            if (type === undefined) {
                // get default dependency type from the dependency class
                var defaultType = this.model.getField(this.model.prototype.typeField).defaultValue;
                type            = defaultType !== undefined ? defaultType : this.model.Type.EndToStart;
            }
        }

        if (!calledFromThisDepModel && modelInput && !dependencyOrFromId.isValid()) {
            return -1;
        } else if (!fromId || !toId || fromId == toId) {
            return -1;
        }

        // Both tasks need to exist for the link to make sense
        if (!fromTask || !toTask) return -2;

        // check dependency type
        if (!this.isValidDependencyType(type)) return -10;

        // Also, not allowed to setup a link between a parent and its child
        if (fromTask.contains(toTask) || toTask.contains(fromTask)) return -9;

        var depsToIgnore;
        if (dependenciesToRemove || calledFromThisDepModel) {
            depsToIgnore    = [];
            // ignore dependency itself during transitivities/cycles search
            if (calledFromThisDepModel) depsToIgnore.push(calledFromThisDepModel);
            if (dependenciesToRemove) depsToIgnore    = depsToIgnore.concat(dependenciesToRemove);
        }

        // checking the presence of transitivity in forward direction (fromId -> toId) - prevents actual transitivity
        if (this.transitiveDependencyValidation) {
            if (this.hasTransitiveDependency(fromId, toId, depsToIgnore, dependenciesToAdd)) return -3;
        } else {
            // check if tasks are already linked directly
            if (this.areTasksLinkedForward(fromId, toId, depsToIgnore, dependenciesToAdd)) return -3;
        }
        // checking the presence of transitivity in backward direction (toId -> fromId) - prevents cycles
        if (this.hasTransitiveDependency(toId, fromId, depsToIgnore, dependenciesToAdd)) return -4;

        // checking the presence of transitivity between fromId-task and some of toId-task successors
        // or between some of fromId-task predecessors and toId-task
        // it detects cases when we have 1->2, 1->3 dependencies and validating 2->3 dependency
        // and when we have 2->3, 1->3 dependencies and validating 1->2 dependency
        if (this.transitiveDependencyValidation && this.isPartOfTransitiveDependency(fromId, toId, depsToIgnore, dependenciesToAdd)) return -5;

        // if strict dependencies validation mode enabled
        if (this.strictDependencyValidation) {
            // let's check if there is an opposite relation between the tasks parent-child stacks (to prevent cycle)
            if (this.groupsHasTransitiveDependency(toId, fromId, depsToIgnore, dependenciesToAdd)) return -7;
            // also check if there is some other relation of the same direction (to prevent transitivity)
            if (this.transitiveDependencyValidation && this.groupsHasTransitiveDependency(fromId, toId, depsToIgnore, dependenciesToAdd)) return -8;
        }

        if (!this.allowParentTaskDependencies && (!fromTask.isLeaf() || !toTask.isLeaf())) return -11;

        // project record itself cannot be linked w/ a dependency
        if (toTask.isProject || fromTask.isProject) {
            return -12;
        }

        // children of a project are connectible depending on "AllowDependencies" flag
        var toProject   = toTask.getProject(),
            fromProject = fromTask.getProject();

        if (toProject != fromProject) {
            if (toProject && !toProject.getAllowDependencies() || fromProject && !fromProject.getAllowDependencies()) {
                return -13;
            }
        }

        return 0;
    },

    isValidDependencyType   : function (type) {
        if (this.allowedDependencyTypes) {
            var result  = false,
                model   = this.model;

            Ext.each(this.allowedDependencyTypes, function (name) {
                if (model.Type[name] == type) {
                    result = true;

                    return false;
                }
            });

            return result;
        }

        return true;
    },

    /**
     * Returns `true` if a dependency (or about to be created dependency) between two tasks is valid.
     *
     * **Please note,** If you subclass this class, that this method is just a wrapper over {@link #getDependencyError}
     * method so if you want to implement a custom validation please override {@link #getDependencyError}.
     * And don't forget to call the parent implementation if you also want the check for cyclic dependencies etc.
     *
     * These scenarios are considered invalid:
     *
     * - a task linking to itself
     * - a dependency between a child and one of its parents
     * - transitive dependencies, e.g. if A -> B, B -> C, then A -> C is not valid, or if A -> B, A -> C, then B -> C is not valid
     * - cyclic dependencies, e.g. if A -> B, B -> C, then C -> A is not valid
     *
     * **Note:** This method behavior depends on {@link #strictDependencyValidation} option.
     * When {@link #strictDependencyValidation} is turned on the system tries to detect cycles and transitivity cases between _groups of tasks_.
     *
     * Method can be used either by providing dependency in first argument (and then `toId` and `type` **should** be omitted):
     *
     *      // validating dependency record
     *      if (!dependencyStore.isValidDependency(dependency)) ...
     *
     * or by providing identifiers of source and target tasks and type of dependency (here `type` can be omitted as well if no further arguments are required):
     *
     *      // if 11 --> 15 dependency is valid
     *      if (dependencyStore.isValidDependency(11, 15)) {
     *          // let`s create it
     *          dependencyStore.add({ From: 11, To: 15 })
     *      }
     *
     * @param {Gnt.model.Dependency/Mixed} dependencyOrFromId Either a dependency or the source task id
     * @param {Mixed} [toId] The target task id. Should be omitted if `dependencyOrFromId` is {@link Gnt.model.Dependency} instance.
     * @param {Number} [type] The type of the dependency. Should be omitted if `dependencyOrFromId` is {@link Gnt.model.Dependency} instance.
     * @param {Gnt.model.Dependency[]/Object[]} [dependenciesToAdd] If provided, validation will be done supposing that specified records exist in the dependency store.
     * @param {Gnt.model.Dependency[]} [dependenciesToRemove]  If provided, validation will be done supposing that specified records DO NOT exist in the dependency store.
     * @return {Boolean}
     */
    isValidDependency : function (dependencyOrFromId, toId, type, dependenciesToAdd, dependenciesToRemove, calledFromThisDepModel) {
        return !this.getDependencyError(dependencyOrFromId, toId, type, dependenciesToAdd, dependenciesToRemove, calledFromThisDepModel);
    },


    /**
     * Returns true if there is a direct forward dependency between the two tasks.
     * Please see also {@link #areTasksLinked} method to check both forward and backward directions.
     *
     * @param {Mixed/Gnt.model.Task} fromTask The id of source task
     * @param {Mixed/Gnt.model.Task} toTask The id of target task
     *
     * @return {Boolean}
     */
    areTasksLinkedForward : function (fromTaskOrId, toTaskOrId, dependenciesToRemove, dependenciesToAdd) {
        var from        = fromTaskOrId instanceof Gnt.model.Task ? fromTaskOrId : this.getTaskById(fromTaskOrId);
        var to          = toTaskOrId instanceof Gnt.model.Task ? toTaskOrId : this.getTaskById(toTaskOrId);

        if (!from || !to) return false;

        var model       = this.model.prototype,
            fromField   = model.fromField,
            toField     = model.toField;

        var cacheKey    = from.getId() + '-' + to.getId() + '-' +
            (this.getKeyByDeps(dependenciesToRemove, fromField, toField) || '') + '-' +
            (this.getKeyByDeps(dependenciesToAdd, fromField, toField) || '');

        if (this.isCachedResultAvailable('areTasksLinkedForward', cacheKey)) {
            return this.methodsCache.areTasksLinkedForward[ cacheKey ];
        }

        var successors      = from.successors,
            predecessors    = to.predecessors,
            dep, i, l;
        // loop over source task successors and check if some of them is presented in target tasks predecessors
        for (i = 0, l = successors.length; i < l; i++) {
            dep             = successors[i];
            // ignore dependencies provided in dependenciesToRemove array
            if ((!dependenciesToRemove || !Ext.Array.contains(dependenciesToRemove, dep)) && Ext.Array.contains(predecessors, dep))
                return this.setCachedResult('areTasksLinkedForward', cacheKey, true);
        }

        // if provided list of dependencies that we must consider as existing in the store
        if (dependenciesToAdd) {
            var sourceId, targetId;

            // let's loop over it and check if link between task is presented there
            for (i = 0, l = dependenciesToAdd.length; i < l; i++) {
                dep         = dependenciesToAdd[i];
                sourceId    = dep.getSourceId && dep.getSourceId() || dep[fromField];
                targetId    = dep.getTargetId && dep.getTargetId() || dep[toField];

                if (sourceId == from.getId() && targetId == to.getId())
                    return this.setCachedResult('areTasksLinkedForward', cacheKey, true);
            }
        }

        return this.setCachedResult('areTasksLinkedForward', cacheKey, false);
    },


    /**
     * Returns true if there is a direct dependency between the two tasks. The dependency can be forward (from 1st task to 2nd)
     * or backward (from 2nd to 1st).
     *
     * @param {Mixed/Gnt.model.Task} id1 The id of 1st task
     * @param {Mixed/Gnt.model.Task} id2 The id of 2nd task
     *
     * @return {Boolean}
     */
    areTasksLinked : function (fromTaskOrId, toTaskOrId) {
        var from        = fromTaskOrId instanceof Gnt.model.Task ? fromTaskOrId : this.getTaskById(fromTaskOrId);
        var to          = toTaskOrId instanceof Gnt.model.Task ? toTaskOrId : this.getTaskById(toTaskOrId);

        if (!from || !to) return false;

        var cacheKey    = from.getId() + '-' + to.getId();

        if (this.isCachedResultAvailable('areTasksLinked', cacheKey)) {
            return this.methodsCache.areTasksLinked[ cacheKey ];
        }

        return this.setCachedResult('areTasksLinked', cacheKey, this.areTasksLinkedForward(from, to) || this.areTasksLinkedForward(to, from));
    },

    /**
     * Returns dependency model instance linking tasks with given ids. The dependency can be forward (from 1st
     * task to 2nd) or backward (from 2nd to 1st).
     *
     * @param {String} id1 The id of 1st task
     * @param {String} id2 The id of 2nd task
     *
     * @return {Gnt.model.Dependency|undefined}
     * @deprecated 4.2 Use {@link getTasksLinkingDependency} instead
     */
    getByTaskIds: function (id1, id2) {
        return this.getTasksLinkingDependency(id1, id2);
    },


    /**
     * Returns dependency model instance linking tasks with given ids. The dependency can be forward (from 1st
     * task to 2nd) or backward (from 2nd to 1st).
     *
     * @param {Gnt.model.Task|String} sourceTask 1st task
     * @param {Gnt.model.Task|String} targetTask 2nd task
     * @return {Gnt.model.Dependency|Null}
     */
    getDependencyForSourceAndTargetTasks : function(sourceTask, targetTask) {
        return this.getDependencyForSourceAndTargetEvents(sourceTask, targetTask);
    },


    /**
     * Returns a dependency model instance linking given tasks if such dependency exists in the store.
     * The dependency can be forward (from 1st event to 2nd) or backward (from 2nd to 1st).
     *
     * @param {Gnt.model.Task|Null} sourceEvent
     * @param {Gnt.model.Task|Null} targetEvent
     * @return {Scm.model.Dependency|Null}
     */
    getTasksLinkingDependency : function(task1, task2) {
        return this.getEventsLinkingDependency(task1, task2);
    },


    getTaskById : function (id) {
        var taskStore = this.getTaskStore();
        return taskStore && taskStore.getModelById(id) || null;
    },


    /**
     * Returns the source task of the dependency
     *
     * @param {Gnt.model.Dependency} dependency The dependency
     * @return {Gnt.model.Task} The source task of this dependency
     */
    getSourceTask : function(dependencyOrId) {
        var id = dependencyOrId instanceof Gnt.model.Dependency ? dependencyOrId.getSourceId() : dependencyOrId;
        return this.getTaskById(id);
    },

    /**
     * Returns the target task of the dependency
     * @param {Gnt.model.Dependency} dependency The dependency
     * @return {Gnt.model.Task} The target task of this dependency
     */
    getTargetTask : function(dependencyOrId) {
        var id = dependencyOrId instanceof Gnt.model.Dependency ? dependencyOrId.getTargetId() : dependencyOrId;
        return this.getTaskById(id);
    },


    /**
     * Returns the {@link Gnt.data.TaskStore} instance, to which this dependency store is attached.
     * @return {Gnt.data.TaskStore}
     */
    getTaskStore : function() {
        return this.getEventStore();
    },

    /**
     * Sets the associated task store instance.
     *
     * @param {Gnt.data.TaskStore} store
     */
    setTaskStore : function(store) {
        var me = this;

        me.taskStore = store;
        me.setEventStore(store);
    }
});
