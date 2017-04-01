/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.model.task.More
@mixin
@protected

Internal mixin class providing additional logic and functionality belonging to the Task model class.

*/
Ext.define('Gnt.model.task.More', {

    propagating : false,

    /**
     * Increases the indentation level of this task in the tree
     *
     * @param {Function} [callback] Callback function to call after task has been indented and changes among dependent tasks was propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    indent : function(callback) {
        var me = this,
            previousSibling = me.previousSibling,
            cancelFn;

        if (previousSibling) {
            var taskStore = me.getTaskStore();
            taskStore.beginIndent();

            me.propagateChanges(
                function() {
                    return me.indentWithoutPropagation(function(fn) {
                        cancelFn = fn;
                    });
                },
                function(cancelChanges, affectedTasks) {
                    if (cancelChanges) {
                        cancelFn && cancelFn();
                    }
                    else {
                        previousSibling.expand();
                    }
                    taskStore.endIndent();
                    callback && callback(cancelChanges, affectedTasks);
                }
            );
        }
        else {  // TODO: actually an exception should be thrown here, but BC is such BC
            callback && callback(false, {});
        }
    },


    indentWithoutPropagation : function (cancelFeedback) {
        var me              = this,
            previousSibling = me.previousSibling,
            originalParent,
            originalIndex,
            wasLeaf,
            segments,
            removeContext;

        removeContext = {
            parentNode          : me.parentNode,
            previousSibling     : me.previousSibling,
            nextSibling         : me.nextSibling
        };

        // This data we need for canceling
        originalParent = me.parentNode;
        originalIndex  = originalParent.indexOf(me);

        wasLeaf = previousSibling.get('leaf');
        if (wasLeaf) {
            segments = previousSibling.getSegments();
            previousSibling.markAsParent();
        }

        // This clears the removeContext object, put it back below
        previousSibling.appendChild(me);

        // if new ancestors of the task have incoming dependencies
        // we need to re-align the task to take them into account
        if (me.getParentsIncomingDependencies().length) {
            me.alignByIncomingDependenciesWithoutPropagation();
        }

        // http://www.sencha.com/forum/showthread.php?270802-4.2.1-NodeInterface-removeContext-needs-to-be-passed-as-an-arg
        me.removeContext = removeContext;

        cancelFeedback && cancelFeedback(function() {
            originalParent.insertChild(originalIndex, me);

            if (wasLeaf) {
                // turn previous sibling back to leaf
                previousSibling.set('leaf', true);
                // and if it was segmented restore segments
                segments && previousSibling.setSegmentsWithoutPropagation(segments);
            }
        });

        return me;
    },

    /**
     * Decreases the indentation level of this task in the tree
     * @param {Function} [callback] Callback function to call after task has been indented and changes among dependent tasks was propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    outdent : function(callback) {
        var me = this,
            parentNode = me.parentNode,
            cancelFn;

        if (parentNode && !parentNode.isRoot()) {
            var taskStore = me.getTaskStore();

            // need to do view refresh after indent to sync scroll between locked/normal view
            // and get rid of extra nodes
            taskStore.beginIndent();

            me.propagateChanges(
                function() {
                    return me.outdentWithoutPropagation(function(fn) {
                        cancelFn = fn;
                    });
                },
                function(cancelChanges, affectedTasks) {
                    cancelChanges && cancelFn && cancelFn();
                    taskStore.endIndent();
                    callback && callback(cancelChanges, affectedTasks);
                }
            );
        }
        else {  // TODO: actually an exception should be thrown here, but BC is such BC
            callback && callback(false, {});
        }

    },

    outdentWithoutPropagation : function (cancelFeedback) {
        var me = this,
            originalParent,
            originalIndex,
            removeContext;

        removeContext = {
            parentNode          : me.parentNode,
            previousSibling     : me.previousSibling,
            nextSibling         : me.nextSibling
        };

        // This data we need for canceling
        originalParent = me.parentNode;
        originalIndex  = originalParent.indexOf(me);

        // This clears the removeContext object, put it back below
        if (originalParent.nextSibling) {
            originalParent.parentNode.insertBefore(me, originalParent.nextSibling);
        } else {
            originalParent.parentNode.appendChild(me);
        }

        // http://www.sencha.com/forum/showthread.php?270802-4.2.1-NodeInterface-removeContext-needs-to-be-passed-as-an-arg
        me.removeContext = removeContext;

        cancelFeedback && cancelFeedback(function() {
            originalParent.insertChild(originalIndex, me);
        });

        // Changes propagation will be collected using original parent as the source point
        return originalParent;
    },


    removeInvalidDependencies : function() {
        var depStore    = this.getDependencyStore(),
            deps        = this.getAllDependencies();

        for (var i = 0; i < deps.length; i++) {

            if (!deps[i].isValid()) {
                depStore.remove(deps[i]);
            }
        }
    },

    removeDependenciesToParents : function(newParentNode) {
        var me          = this,
            linkedTasks = me.getSuccessors().concat(me.getPredecessors());

        newParentNode.bubble(function(parent) {
            if (Ext.Array.indexOf(linkedTasks, parent) >= 0) {
                me.removeLinkToTask(parent);
            }
        });
    },


    /**
     * Returns true if the task has at least one dependency
     *
     * @return {Boolean}
     */
    hasDependencies : function () {
        return this.hasIncomingDependencies() || this.hasOutgoingDependencies();
    },


    /**
     * Returns all dependencies of this task (both incoming and outgoing)
     *
     * @return {Gnt.model.Dependency[]}
     */
    getAllDependencies : function () {
        return this.predecessors.concat(this.successors);
    },

    /**
     * Returns true if this task has at least one incoming dependency
     *
     * @return {Boolean}
     */
    hasIncomingDependencies : function () {
        return this.predecessors.length > 0;
    },

    /**
     * Returns true if this task has at least one outgoing dependency
     *
     * @return {Boolean}
     */
    hasOutgoingDependencies : function () {
        return this.successors.length > 0;
    },

    /**
     * Returns all incoming dependencies of this task
     *
     * @param {Boolean} [doNotClone=false] Whether to **not** create a shallow copy of the underlying {@link Gnt.model.Task#predecessors} property.
     * Passing `true` is more performant, but make sure you don't modify the array in this case.
     *
     * @return {Gnt.model.Dependency[]}
     */
    getIncomingDependencies : function (doNotClone) {
        return doNotClone ? this.predecessors : this.predecessors.slice();
    },

    getParentsIncomingDependencies : function (parentNode) {
        var parent = this.parentNode,
            result = [],
            toAppend;

        while (parent) {
            // provided parentNode restricts to take into account dependencies from only its descendants
            if (parentNode) {
                toAppend = Ext.Array.filter(parent.getIncomingDependencies(), function (dependency) {
                    var sourceTask = dependency.getSourceTask();
                    return sourceTask && sourceTask.isAncestor(parentNode);
                });
            } else {
                toAppend = parent.getIncomingDependencies();
            }

            result = result.concat(toAppend);

            parent = parent.parentNode;
        }

        return result;
    },

    /**
     * Returns all outcoming dependencies of this task
     *
     * @param {Boolean} [doNotClone=false] Whether to **not** create a shallow copy of the underlying {@link Gnt.model.Task#successors} property.
     * Passing `true` is more performant, but make sure you don't modify the array in this case.
     *
     * @return {Gnt.model.Dependency[]}
     */
    getOutgoingDependencies : function (doNotClone) {
        return doNotClone ? this.successors : this.successors.slice();
    },


    // TODO: see if this is needed or can be removed or substituted by another method
    // NOTE: return value is never used anywhere in our code base
    /**
     * @private
     * Internal method, constrains the task according to its incoming dependencies
     * @param {Gnt.data.TaskStore} taskStore The task store
     * @return {Boolean} true if the task was updated as a result.
     */
    alignByIncomingDependencies : function (taskStore, currentCascadeBatch, callback) {
        var result      = this.alignByIncomingDependenciesWithoutPropagation(taskStore, currentCascadeBatch);

        this.propagateChanges(null, callback, true);

        return result;
    },


    alignByIncomingDependenciesWithoutPropagation : function (taskStore, currentCascadeBatch, parentNode) {
        // we don't affect the task by incoming dependencies if it's either:
        //  - a manually scheduled task
        //  - a readonly task
        if (this.isManuallyScheduled() || this.isReadOnly()) {
            return false;
        }

        var changed             = false;

        taskStore               = taskStore || this.getTaskStore();

        var constrainContext    = this.getIncomingDependenciesConstraintContext(taskStore, parentNode);

        if (constrainContext) {
            var startDate       = constrainContext.startDate;
            var endDate         = constrainContext.endDate;

            if (startDate && startDate - this.getStartDate() !== 0) {
                currentCascadeBatch && currentCascadeBatch.addAffected(this);

                this.setStartDateWithoutPropagation(startDate, true, taskStore.skipWeekendsDuringDragDrop);

                changed         = true;
            } else if (endDate && endDate - this.getEndDate() !== 0) {
                currentCascadeBatch && currentCascadeBatch.addAffected(this);

                this.setEndDateWithoutPropagation(endDate, true, taskStore.skipWeekendsDuringDragDrop);

                changed         = true;
            }
        }

        return changed;
    },


    getDependencyCalendar : function (dependency, dependenciesCalendar, taskStore) {
        if (!dependenciesCalendar) {
            dependenciesCalendar = (taskStore || this.getTaskStore()).dependenciesCalendar;
        }

        var projectCalendar = this.getProjectCalendar(),
            fromTask        = dependency.getSourceTask(taskStore),
            targetTask      = dependency.getTargetTask(taskStore),
            calendar;

        switch (dependenciesCalendar) {
            case 'project' : calendar = projectCalendar; break;
            case 'source'  : calendar = fromTask.getCalendar(); break;
            case 'target'  : calendar = targetTask.getCalendar(); break;
            default:
                throw "Unsupported value for `dependenciesCalendar` config option";
        }

        return calendar;
    },


    getIncomingDependenciesConstraintContext : function (providedTaskStore, parentNode) {
        var me = this,
            // the task incoming dependencies plus the ones inherited from its ancestors
            incomingDependencies = this.getIncomingDependencies(true).concat(this.getParentsIncomingDependencies(parentNode));

        if (!incomingDependencies.length || this.isUnscheduled()) {
            return null;
        }

        var DepType             = Gnt.model.Dependency.Type,
            earliestStartDate   = new Date(0), // This will break for tasks later then 01.01.1970
            earliestEndDate     = new Date(0), // This will break for tasks later then 01.01.1970
            constrainingStartTask,
            constrainingEndTask;

        var dependenciesCalendar = (providedTaskStore || this.getTaskStore()).dependenciesCalendar;

        Ext.Array.each(incomingDependencies, function (dependency) {
            var fromTask = dependency.getSourceTask(providedTaskStore);

            if (fromTask && (!parentNode || fromTask.isAncestor(parentNode))) {
                // get calendar instance to use for lag calculations
                var calendar = me.getDependencyCalendar(dependency, dependenciesCalendar, providedTaskStore);

                var lag         = dependency.getLag() || 0,
                    lagUnit     = dependency.getLagUnit(),
                    start       = fromTask.getStartDate(),
                    end         = fromTask.getEndDate();

                switch (dependency.getType()) {
                    case DepType.StartToEnd:
                        if (start) {
                            start   = calendar.skipWorkingTime(start, lag, lagUnit);
                            if (earliestEndDate < start) {
                                earliestEndDate     = start;
                                constrainingEndTask = fromTask;
                            }
                        }
                        break;

                    case DepType.StartToStart:
                        if (start) {
                            start   = calendar.skipWorkingTime(start, lag, lagUnit);
                            if (earliestStartDate < start) {
                                earliestStartDate     = start;
                                constrainingStartTask = fromTask;
                            }
                        }
                        break;

                    case DepType.EndToStart:
                        if (end) {
                            end     = calendar.skipWorkingTime(end, lag, lagUnit);
                            if (earliestStartDate < end) {
                                earliestStartDate     = end;
                                constrainingStartTask = fromTask;
                            }
                        }
                        break;

                    case DepType.EndToEnd:
                        if (end) {
                            end     = calendar.skipWorkingTime(end, lag, lagUnit);
                            if (earliestEndDate < end) {
                                earliestEndDate     = end;
                                constrainingEndTask = fromTask;
                            }
                        }
                        break;

                    default:
                        throw 'Invalid dependency type: ' + dependency.getType();
                }

            }
        });

        var result = {
            startDate           : earliestStartDate > 0 ? earliestStartDate : null,
            endDate             : earliestEndDate > 0 ? earliestEndDate : null,

            constrainingTask    : constrainingStartTask || constrainingEndTask
        };

        // if we have both start & end constrained ...
        if (result.startDate && result.endDate) {
            // we need to compare them to get effective constraint value (and task)
            // so we convert earliestEndDate constraint to task start date and compare it with earliestStartDate
            var start   = this.calculateStartDate(result.endDate, this.getDuration());

            if (start > earliestStartDate) {
                result.startDate        = start;
                result.constrainingTask = constrainingEndTask;
            }

            // get rid of endDate constraint since we just processed it
            result.endDate = null;
        }

        // Skip non-working time

        if (result.startDate) {
            result.startDate = this.skipNonWorkingTime(result.startDate);
        }
        // for EndDate we skip it backward
        if (result.endDate) {
            result.endDate = this.skipNonWorkingTime(result.endDate, false);
        }

        return result.constrainingTask && result;
    },


    /**
     * @private
     * Internal method, called recursively to query for the longest duration of the chain structure
     * @return {Gnt.model.Task[]} chain An array forming a chain of linked tasks
     */
    getCriticalPaths: function () {
        var cPath = [this],
            ctx   = this.getIncomingDependenciesConstraintContext(),
            task  = this;

        // if there is a constraining
        while (ctx) {

            var startDate = ctx.startDate;
            var endDate   = ctx.endDate;

            // If the task being constrained has not distance between its constraining limit and real start(end) date
            // we consider it a part of the critical path
            if ((startDate && startDate - task.getStartDate() >= 0) || (endDate && endDate - task.getEndDate() >= 0)) {

                task = ctx.constrainingTask;

                cPath.push(task);

                ctx = task.getIncomingDependenciesConstraintContext();

            } else {
                ctx = null;
            }
        }

        return cPath;
    },


    /**
     * @removed Please use {@link Gnt.data.TaskStore#cascadeChangesForTask} method instead.
     * @method cascadeChanges
     */


    /**
     * Adds the passed task to the collection of child tasks.
     * @param {Gnt.model.Task} subtask The new subtask
     * @param {Function} [callback] Callback function to call after task has been added and changes among dependent tasks was propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     * @return {Gnt.model.Task} The added subtask task
     */
    addSubtask : function(subtask, callback) {
        var me = this,
            compatResult,
            cancelFn;

        me.propagateChanges(
            function() {
                return me.addSubtaskWithoutPropagation(subtask, function cancelAndResultFeedback(fn, result) {
                    cancelFn = fn;
                    compatResult = result;
                });
            },
            function onPropagationComplete(cancelChanges, affectedTasks) {
                cancelChanges && cancelFn && cancelFn();
                callback && callback(cancelChanges, affectedTasks);
            }
        );

        return compatResult;
    },

    addSubtaskWithoutPropagation : function(subtask, cancelAndResultFeedback) {
        var me = this,
            originalParent,
            originalIndex,
            propagationSources,
            wasLeaf,
            segments;

        originalParent = subtask.parentNode;
        originalIndex  = originalParent && originalParent.indexOf(me);

        wasLeaf = me.get('leaf');
        if (wasLeaf) {
            me.markAsParent();
            segments = me.getSegments();
        }

        subtask = me.appendChild(subtask);
        me.expand();

        cancelAndResultFeedback && cancelAndResultFeedback(function() {
            if (originalParent) {
                originalParent.insertChild(originalIndex, subtask);
            }
            else {
                me.removeChild(subtask);
            }

            wasLeaf && me.set('leaf', true);
            wasLeaf && segments && me.setSegmentsWithoutPropagation(segments);

        }, subtask);

        // Changes propagation will be collected using affected parents as the source points
        if (!originalParent) {
            propagationSources = subtask;
        }
        else if (me !== originalParent && me.getTaskStore(true) === originalParent.getTaskStore(true)) {
            propagationSources = [subtask, originalParent];
        }

        return propagationSources;
    },

    /**
     * Inserts the passed task to the collection of child tasks at the given index.
     * @param {Integer} index Tne new subtask index
     * @param {Gnt.model.Task} subtask The new subtask
     * @param {Function} [callback] Callback function to call after task has been inserted and changes among dependent tasks was propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     * @return {Gnt.model.Task} The inserted subtask
     */
    insertSubtask : function(index, subtask, callback) {
        var me = this,
            compatResult,
            cancelFn;

        me.propagateChanges(
            function() {
                return me.insertSubtaskWithoutPropagation(index, subtask, function cancelAndResultFeedback(fn, result) {
                    cancelFn = fn;
                    compatResult = result;
                });
            },
            function onPropagationComplete(cancelChanges, affectedTasks) {
                cancelChanges && cancelFn && cancelFn();
                callback && callback(cancelChanges, affectedTasks);
            }
        );

        return compatResult;
    },


    insertSubtaskWithoutPropagation : function(index, subtask, cancelAndResultFeedback) {
        var me = this,
            originalParent,
            originalIndex,
            propagationSources,
            wasLeaf,
            segments;

        originalParent = subtask.parentNode;
        originalIndex  = originalParent && originalParent.indexOf(me);

        wasLeaf = me.get('leaf');
        if (wasLeaf) {
            me.markAsParent();
            segments = me.getSegments();
        }

        subtask = me.insertChild(index, subtask);
        me.expand();

        cancelAndResultFeedback && cancelAndResultFeedback(function() {
            if (originalParent) {
                originalParent.insertChild(originalIndex, subtask);
            }
            else {
                me.removeChild(subtask);
            }

            wasLeaf && me.set('leaf', true);
            wasLeaf && segments && me.setSegmentsWithoutPropagation(segments);

        }, subtask);

        // Changes propagation will be collected using affected parents as the source points
        if (!originalParent) {
            propagationSources = subtask;
        }
        else if (me !== originalParent && me.getTaskStore(true) === originalParent.getTaskStore(true)) {
            propagationSources = [subtask, originalParent];
        }

        return propagationSources;
    },


    /**
     * Constraints aware removes the passed subtask from this task child nodes.
     *
     * @param {Gnt.model.Task} [subtask] The subtask to remove
     * @param {Function} [callback] Callback function to call after the subtask has been removed and changes among dependent tasks was propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    removeSubtask : function(subtask, callback) {
        var me = this,
            cancelFn;

        // if the subtask is already removed then we have nothing to do
        if (me.indexOf(subtask) !== -1) {
            me.propagateChanges(
                function() {
                    return me.removeSubtaskWithoutPropagation(subtask, function cancelFeedback(fn) {
                        cancelFn = fn;
                    });
                },
                function onPropagationComplete(cancelChanges, affectedTasks) {
                    cancelChanges && cancelFn && cancelFn();
                    callback && callback(cancelChanges, affectedTasks);
                }
            );
        }
        else {
            callback && callback(false, {});
        }
    },

    removeSubtaskWithoutPropagation : function(subtask, cancelFeedback) {
        var me = this,
            indexOfSubtask = me.indexOf(subtask),
            subtree,
            dependencyStore,
            assignmentStore,
            dependencies,
            assignments,
            dependenciesIndices,
            assignmentsIndices,
            i, len, r;

        // <debug>
        indexOfSubtask != -1 ||
            Ext.Error.raise("Can't remove subtask `" + subtask.getId() + "` from task `" + me.getId() + "` subtask is not a child of the task!");
        // </debug>

        dependencyStore     = me.getDependencyStore();
        assignmentStore     = me.getAssignmentStore();
        dependencies        = dependencyStore && dependencyStore.getDependenciesForTask(subtask);
        assignments         = assignmentStore && subtask.getAssignments();
        subtree             = [];
        dependenciesIndices = [];
        assignmentsIndices  = [];

        // Collecting all the descendants of the subtask.
        subtask.cascadeBy(function(node) {
            subtree.push(node);
        });

        // Collecting dependencies and assignments of the subtree
        for (i = 0, len = subtree.length; (dependencyStore || assignmentStore) && i < len; i++) {
            r = subtree[i];
            dependencyStore && (dependencies = dependencies.concat(dependencyStore.getDependenciesForTask(r)));
            assignmentStore && (assignments  = assignments.concat(r.getAssignments()));
        }

        // Sorting dependencies in index order for future restoration
        dependencies = dependencyStore && Ext.Array.unique(dependencies);
        dependencies = dependencyStore && Ext.Array.sort(dependencies, function(a, b) {
            return dependencyStore.indexOf(a) < dependencyStore.indexOf(b) ? -1 : 1; // 0 is not an option here
        });
        // Collecting dependencies indices
        for (i = 0, len = dependencies && dependencies.length; dependencyStore && i < len; i++) {
            dependenciesIndices.push(dependencyStore.indexOf(dependencies[i]));
        }

        // Sorting assignments in index order for future restoration
        assignments = assignmentStore && Ext.Array.sort(assignments, function(a, b) {
            return assignmentStore.indexOf(a) < assignmentStore.indexOf(b) ? -1 : 1; // 0 is not an option here
        });
        // Collecting assignments indicies
        for (i = 0, len = assignments && assignments.length; assignmentStore && i < len; i++) {
            assignmentsIndices.push(assignmentStore.indexOf(assignments[i]));
        }

        // It's important to remove subtask/subtree first. If we will remove assignments/dependencies first then
        // subtree tasks might be adjusted and such adjustments might envolve pricy calculations which then will be
        // made void by removing a task they affecting.

        // Removing subtask (which will remove subtree as well)
        subtask = me.removeChild(subtask);

        // Removing all assignments
        assignmentStore && assignmentStore.remove(assignments);
        // Removing all dependencies
        dependencyStore && dependencyStore.remove(dependencies);

        // Converting self to leaf if required
        if (me.childNodes.length === 0 && me.convertEmptyParentToLeaf) {
            me.set('leaf', true);
        }

        cancelFeedback && cancelFeedback(function() {
            // Restoring everything back
            me.insertChild(indexOfSubtask, subtask);
            for (i = 0, len = assignments && assignments.length; assignmentStore && i < len; i++) {
                assignmentStore.insert(assignmentsIndices[i], assignments[i]);
            }
            for (i = 0, len = dependencies && dependencies.length; dependencyStore && i < len; i++) {
                dependencyStore.insert(dependenciesIndices[i], dependencies[i]);
            }
        });

        return me;
    },

    /**
     * Adds the passed task as a successor and creates a new dependency between the two tasks.
     * @param {Gnt.model.Task} [successor] The new successor
     * @param {Function} [callback] Callback function to call after task has been added and changes among dependent tasks was propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     * @return {Gnt.model.Task} the successor task
     */
    addSuccessor : function(successor, callback) {
        var me = this,
            compatResult,
            cancelFn;

        me.propagateChanges(
            function() {
                return me.addSuccessorWithoutPropagation(successor, function cancelAndResultFeedback(fn, result) {
                    cancelFn = fn;
                    compatResult = result;
                });
            },
            function onPropagationComplete(cancelChanges, affectedTasks) {
                cancelChanges && cancelFn && cancelFn();
                callback && callback(cancelChanges, affectedTasks);
            }
        );

        return compatResult;
    },


    addSuccessorWithoutPropagation : function(successor, cancelAndResultFeedback) {
        var me              = this,
            parentNode      = me.parentNode,
            index           = parentNode.indexOf(me),
            insertCancelFn,
            linkCancelFn;

        successor           = successor           || new me.self();
        // we set a calendar to the task being added to be able to call calculateEndDate() for it
        // since we do it before the task gets into the task store and the project calendar is not reachable
        successor.calendar  = successor.calendar  || me.getCalendar();
        successor.taskStore = successor.taskStore || me.getTaskStore(true);

        if (me.getEndDate()) {
            successor.beginEdit();
            successor.set(me.startDateField, me.getEndDate());
            successor.set(me.endDateField, successor.calculateEndDate(me.getEndDate(), 1, Sch.util.Date.DAY));
            successor.set(me.durationField, 1);
            successor.set(me.durationUnitField, Sch.util.Date.DAY);
            successor.endEdit();
        }

        // adding successor below
        parentNode.insertSubtaskWithoutPropagation(index + 1, successor, function(fn, result) {
            insertCancelFn = fn;
            successor      = result;
        });

        me.linkToWithoutPropagation(successor, Gnt.model.Dependency.Type.EndToStart, function(fn) {
            linkCancelFn = fn;
        });

        cancelAndResultFeedback && cancelAndResultFeedback(function() {
            linkCancelFn();
            insertCancelFn();
        }, successor);

        return me;
    },

    /**
     * Adds the passed task as a milestone below this task.
     * @param {Gnt.model.Task} milestone (optional) The milestone
     * @param {Function} [callback] Callback function to call after task has been added and changes among dependent tasks was propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     * @return {Gnt.model.Task} the new milestone.
     */
    addMilestone : function(milestone, callback) {
        var me        = this,
            date      = me.getEndDate();

        if (!milestone) {
            milestone = new me.self();
        }
        else if (Ext.isObject(milestone) && !(milestone instanceof Gnt.model.Task)) {
            milestone = new me.self(milestone);
        }

        if (date && !milestone.isMilestone()) {
            milestone.calendar = milestone.calendar || me.getCalendar();
            milestone.setStartEndDate(date, date);
        }

        return me.addTaskBelow(milestone, callback);
   },

    /**
     * Adds the passed task as a predecessor and creates a new dependency between the two tasks.
     * @param {Gnt.model.Task} [predecessor] The new predecessor
     * @param {Function} [callback] Callback function to call after task has been added and changes among dependent tasks was propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     * @return {Gnt.model.Task} the new predecessor
     */
    addPredecessor : function(predecessor, callback) {
        var me = this,
            compatResult,
            cancelFn;

        me.propagateChanges(
            function() {
                return me.addPredecessorWithoutPropagation(predecessor, function cancelAndResultFeedback(fn, result) {
                    cancelFn = fn;
                    compatResult = result;
                });
            },
            function onPropagationComplete(cancelChanges, affectedTasks) {
                cancelChanges && cancelFn();
                callback && callback(cancelChanges, affectedTasks);
            }
        );

        return compatResult;
    },


    addPredecessorWithoutPropagation : function(predecessor, cancelAndResultFeedback) {
        var me              = this,
            parentNode      = me.parentNode,
            index           = parentNode.indexOf(me),
            insertCancelFn,
            linkCancelFn;

        predecessor           = predecessor           || new me.self();
        predecessor.calendar  = predecessor.calendar  || me.getCalendar(); // WTF, why so?
        predecessor.taskStore = predecessor.taskStore || me.getTaskStore(true);

        if (me.getStartDate()) {
            predecessor.beginEdit();
            predecessor.set(me.startDateField, predecessor.calculateStartDate(me.getStartDate(), 1, Sch.util.Date.DAY));
            predecessor.set(me.endDateField, me.getStartDate());
            predecessor.set(me.durationField, 1);
            predecessor.set(me.durationUnitField, Sch.util.Date.DAY);
            predecessor.endEdit();
        }

        parentNode.insertSubtaskWithoutPropagation(index, predecessor, function(fn, result) {
            insertCancelFn = fn;
            predecessor    = result;
        });

        predecessor.linkToWithoutPropagation(me, Gnt.model.Dependency.Type.EndToStart, function(fn) {
            linkCancelFn   = fn;
        });

        cancelAndResultFeedback && cancelAndResultFeedback(function() {
            linkCancelFn();
            insertCancelFn();
        }, predecessor);

        return predecessor;
    },

    /**
     * Returns all the successor tasks of this task
     *
     * @return {Gnt.model.Task[]}
     */
    getSuccessors: function () {
        var deps    = this.successors,
            res     = [];

        for (var i = 0, len = deps.length; i < len; i++) {
            var task = deps[i].getTargetTask();

            if (task) res.push(task);
        }

        return res;
    },

    /**
     * Returns all the predecessor tasks of a this task.
     *
     * @return {Gnt.model.Task[]}
     */
    getPredecessors: function () {
        var deps    = this.predecessors,
            res     = [];

        for (var i = 0, len = deps.length; i < len; i++) {
            var task = deps[i].getSourceTask();

            if (task) res.push(task);
        }

        return res;
    },

    /**
     * Adds the passed task (or creates a new task) before itself
     * @param {Gnt.model.Task} addTaskAbove (optional) The task to add
     * @param {Function} [callback] Callback function to call after task has been added and changes among dependent tasks was propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     * @return {Gnt.model.Task} the newly added task
     */
    addTaskAbove : function (task, callback) {
        var me = this,
            parentNode = me.parentNode,
            index = parentNode.indexOf(me),
            compatResult,
            cancelFn;

        task = task || new me.self();

        me.propagateChanges(
            function() {
                return parentNode.insertSubtaskWithoutPropagation(index, task, function cancelAndResultFeedback(fn, result) {
                    cancelFn = fn;
                    compatResult = result;
                });
            },
            function onPropagationComplete(cancelChanges, affectedTasks) {
                cancelChanges && cancelFn();
                callback && callback(cancelChanges, affectedTasks);
            }
        );

        return compatResult;
    },

    /**
     * Adds the passed task (or creates a new task) after itself
     * @param {Gnt.model.Task} task (optional) The task to add
     * @param {Function} [callback] Callback function to call after task has been added and changes among dependent tasks was propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     * @return {Gnt.model.Task} the newly added task
     */
    addTaskBelow : function (task, callback) {
        var me = this,
            parentNode = me.parentNode,
            index = parentNode.indexOf(me) + 1,
            compatResult,
            cancelFn;

        task = task || new me.self();

        me.propagateChanges(
            function() {
                return parentNode.insertSubtaskWithoutPropagation(index, task, function cancelAndResultFeedback(fn, result) {
                    cancelFn = fn;
                    compatResult = result;
                });
            },
            function onPropagationComplete(cancelChanges, affectedTasks) {
                cancelChanges && cancelFn();
                callback && callback(cancelChanges, affectedTasks);
            }
        );

        return compatResult;
    },

    // Returns true if this task model is 'above' the passed task model
    isAbove : function(otherTask) {
        var me          = this,
            minDepth    = Math.min(me.data.depth, otherTask.data.depth);

        var current     = this;

        // Walk upwards until tasks are on the same level
        while (current.data.depth > minDepth) {
            current     = current.parentNode;

            if (current == otherTask) return false;
        }
        while (otherTask.data.depth > minDepth) {
            otherTask   = otherTask.parentNode;

            if (otherTask == me) return true;
        }

        // At this point, depth of both tasks should be identical.
        // Walk up to find common parent, to be able to compare indexes
        while (otherTask.parentNode !== current.parentNode) {
            otherTask   = otherTask.parentNode;
            current     = current.parentNode;
        }

        return otherTask.data.index > current.data.index;
    },

    /**
     * Cascades the children of a task. The given function is not called for this node itself.
     * @param {Function} fn The function to call for each child
     * @param {Object} scope The 'this' object to use for the function, defaults to the current node.
     */
    cascadeChildren : function(fn, scope) {
        var me = this;

        if (me.isLeaf()) return;

        var childNodes      = this.childNodes;

        for (var i = 0, len = childNodes.length; i < len; i++) childNodes[ i ].cascadeBy(fn, scope);
    },

    /**
     * Returns the _slack_ (or _float_) of this task.
     * The _slack_ is the amount of time that this task can be delayed without causing a delay
     * to any of its successors.
     *
     * @param {String} unit The time unit used to calculate the slack.
     * @return {Number} The _slack_ of this task.
     */
    getSlack : function (unit) {
        unit = unit || Sch.util.Date.DAY;

        var earlyStart  = this.getEarlyStartDate(),
            lateStart   = this.getLateStartDate();

        if (!earlyStart || !lateStart) return null;

        // slack taking into account only working period of time
        return this.getCalendar().calculateDuration(earlyStart, lateStart, unit);
    },

    /**
     * Returns the _early start date_ of this task.
     * The _early start date_ is the earliest possible start date of a task.
     * This value is calculated based on the earliest end dates of the task predecessors.
     * If the task has no predecessors, its start date is the early start date.
     *
     * @return {Date} The early start date.
     */
    getEarlyStartDate : function () {
        var store = this.getTaskStore(true);
        if (!store) return this.getStartDate();

        var internalId = this.internalId;
        if (store.earlyStartDates[internalId]) return store.earlyStartDates[internalId];

        var dt, result = 0, i, l;

        // for a parent task we take the minimum Early Start from its children
        if (this.childNodes.length) {

            for (i = 0, l = this.childNodes.length; i < l; i++) {
                dt = this.childNodes[i].getEarlyStartDate();
                if (dt < result || !result) result = dt;
            }

            store.earlyStartDates[internalId] = result;

            return result;
        }

        // for manually scheduled task we simply return its start date
        if (this.isManuallyScheduled()) {
            result = store.earlyStartDates[internalId] = this.getStartDate();
            return result;
        }

        var deps = this.getIncomingDependencies(true);

        if (!deps.length) {
            result = store.earlyStartDates[internalId] = this.getStartDate();
            return result;
        }

        var depType     = Gnt.model.Dependency.Type,
            ownCalendar = this.getCalendar();

        // Early Start Date is the largest of Early Finish Dates of the preceding tasks
        for (i = 0, l = deps.length; i < l; i++) {

            var dependency         = deps[i],
                fromTask           = dependency.getSourceTask(),
                // get calendar instance to use for lag calculations
                dependencyCalendar = this.getDependencyCalendar(dependency);

            if (fromTask) {
                switch (dependency.getType()) {
                    case depType.StartToStart:
                        dt  = fromTask.getEarlyStartDate();
                        break;
                    case depType.StartToEnd:
                        dt  = fromTask.getEarlyStartDate();
                        // minus duration to get start
                        dt  = ownCalendar.calculateStartDate(dt, this.getDuration(), this.getDurationUnit());
                        break;
                    case depType.EndToStart:
                        dt  = fromTask.getEarlyEndDate();
                        break;
                    case depType.EndToEnd:
                        dt  = fromTask.getEarlyEndDate();
                        // minus duration to get start
                        dt  = ownCalendar.calculateStartDate(dt, this.getDuration(), this.getDurationUnit());
                        break;
                }

                // plus dependency Lag
                var lag = dependency.getLag();
                if (dt && lag) dt = dependencyCalendar.skipWorkingTime(dt, lag, dependency.getLagUnit());

                if (dt) {
                    dt = ownCalendar.skipNonWorkingTime(dt, true);
                }
            }

            if (dt > result) result = dt;
        }

        // store found value into the cache
        store.earlyStartDates[internalId] = result;

        return result;
    },

    /**
     * Returns the _early end date_ of the task.
     * The _early end date_ is the earliest possible end date of the task.
     * This value is calculated based on the earliest end dates of predecessors.
     * If the task has no predecessors then its end date is used as its earliest end date.
     *
     * @return {Date} The early end date.
     */
    getEarlyEndDate : function () {
        var store = this.getTaskStore(true);

        if (!store) return this.getEndDate();

        var internalId = this.internalId;

        if (store.earlyEndDates[internalId]) return store.earlyEndDates[internalId];

        var result = 0;
        // for parent task we take maximum Early Finish from its children
        if (this.childNodes.length) {
            var dt, i, l;

            for (i = 0, l = this.childNodes.length; i < l; i++) {
                dt = this.childNodes[i].getEarlyEndDate();
                if (dt > result) result = dt;
            }

            store.earlyEndDates[internalId] = result;

            return result;
        }

        // for manually scheduled task we simply return its end date
        if (this.isManuallyScheduled())  {
            result = store.earlyEndDates[internalId] = this.getEndDate();

            return result;
        }

        // Early Finish Date is Early Start Date plus duration
        var value = this.getEarlyStartDate();

        if (!value) return null;

        result = store.earlyEndDates[internalId] = this.getCalendar().calculateEndDate(value, this.getDuration(), this.getDurationUnit());

        return result;
    },

    /**
     * Returns the _late end date_ of the task.
     * The _late end date_ is the latest possible end date of the task.
     * This value is calculated based on the latest start dates of its successors.
     * If the task has no successors, the project end date is used as its latest end date.
     *
     * @return {Date} The late end date.
     */
    getLateEndDate : function () {
        var store = this.getTaskStore(true);
        if (!store) return this.getEndDate();

        var internalId = this.internalId;
        if (store.lateEndDates[internalId]) return store.lateEndDates[internalId];

        var dt, result = 0, i, l;

        // for parent task we take maximum Late Finish from its children
        if (this.childNodes.length) {
            for (i = 0, l = this.childNodes.length; i < l; i++) {
                dt = this.childNodes[i].getLateEndDate();
                if (dt > result) result = dt;
            }

            store.lateEndDates[internalId] = result;

            return result;
        }

        // for manually scheduled task we simply return its end date
        if (this.isManuallyScheduled())  {
            result = store.lateEndDates[internalId] = this.getEndDate();
            return result;
        }

        var deps = this.getOutgoingDependencies(true);

        if (!deps.length) {
            result = store.lateEndDates[internalId] = store.getProjectEndDate();
            return result;
        }

        var depType         = Gnt.model.Dependency.Type,
            ownCalendar     = this.getCalendar();

        // Late Finish Date is the smallest of Late Start Dates of succeeding tasks
        for (i = 0, l = deps.length; i < l; i++) {

            var dependency         = deps[i],
                toTask             = dependency.getTargetTask(),
                // get calendar instance to use for lag calculations
                dependencyCalendar = this.getDependencyCalendar(dependency);

            if (toTask) {
                switch (dependency.getType()) {
                    case depType.StartToStart: // start-to-start
                        dt  = toTask.getLateStartDate();
                        // plus duration to get end
                        dt  = ownCalendar.calculateEndDate(dt, this.getDuration(), this.getDurationUnit());
                        break;
                    case depType.StartToEnd: // start-to-end
                        dt  = toTask.getLateEndDate();
                        // plus duration to get end
                        dt  = ownCalendar.calculateEndDate(dt, this.getDuration(), this.getDurationUnit());
                        break;
                    case depType.EndToStart: // end-to-start
                        dt  = toTask.getLateStartDate();
                        break;
                    case depType.EndToEnd: // end-to-end
                        dt  = toTask.getLateEndDate();
                        break;
                }

                // minus dependency Lag
                var lag = dependency.getLag();
                if (lag) dt  = dependencyCalendar.skipWorkingTime(dt, -lag, dependency.getLagUnit());

                dt = ownCalendar.skipNonWorkingTime(dt, false);

                if (dt < result || !result) result = dt;
            }
        }

        // cache found value
        store.lateEndDates[internalId] = result || store.getProjectEndDate();

        return store.lateEndDates[internalId];
    },

    /**
     * Returns the _late start date_ of the task.
     * The _late start date_ is the latest possible start date of this task.
     * This value is calculated based on the latest start dates of its successors.
     * If the task has no successors, this value is calculated as the _project end date_ minus the task duration
     * (_project end date_ is the latest end date of all the tasks in the taskStore).
     *
     * @return {Date} The late start date.
     */
    getLateStartDate : function () {
        var store = this.getTaskStore(true);
        if (!store) return this.getStartDate();

        var internalId = this.internalId;
        if (store.lateStartDates[internalId]) return store.lateStartDates[internalId];

        var result;
        // for parent task we take minimum Late Start from its children
        if (this.childNodes.length) {
            var dt, i, l;

            for (i = 0, l = this.childNodes.length; i < l; i++) {
                dt = this.childNodes[i].getLateStartDate();
                if (dt < result || !result) result = dt;
            }

            store.lateStartDates[internalId] = result;

            return result;
        }

        // for manually scheduled task we simply return its start date
        if (this.isManuallyScheduled())  {
            result = store.lateStartDates[internalId] = this.getStartDate();
            return result;
        }

        // Late Start Date is Late Finish Date minus duration
        var value = this.getLateEndDate();
        if (!value) return null;

        result = store.lateStartDates[internalId] = this.getCalendar().calculateStartDate(value, this.getDuration(), this.getDurationUnit());

        return result;
    },


    getTopParent : function (all) {
        var root    = this.getTaskStore().getRoot(),
            p       = this,
            path    = [ this ],
            result;

        while (p) {
            if (p === root) return all ? path : result;

            path.push(p);

            result  = p;
            p       = p.parentNode;
        }
    },


    // TODO: use for something - as a fast way of iterating over all children of some parent in depth
    getInDepthWalker : function (includeThisNode) {
        var current         = includeThisNode ? this : this.childNodes && this.childNodes[ 0 ];
        var stopAt          = this;

        var visitedParents  = {};

        var next            = function (from) {
            var current     = from;
            var internalId  = current.internalId;

            if (current.isLeaf() || !current.childNodes.length)
                current     = current.nextSibling;
            else {
                if (visitedParents[ internalId ] === true) {
                    visitedParents[ internalId ] = false;

                    current = current.nextSibling;
                } else {
                    visitedParents[ internalId ] = true;

                    current = current.childNodes[ 0 ];
                }
            }

            if (!current) {
                current     = from;

                do {
                    if (current === stopAt) return null;

                    current = current.parentNode;

                    if (current === stopAt) return null;
                } while (visitedParents[ current.internalId ] === false);

                return next(current);
            }

            return current;
        };

        return function () {
            var task    = current;

            if (current) current  = next(current);

            return task;
        };
    },


    /**
     * Propagates changes done in `changer` function to the task to all dependent tasks. The action is asynchronous
     * since changes propagation might violate some constraints applied, which in it's turn might require user
     * interaction.
     *
     * @param {Function} [changer] A function which should apply changes to the task
     *  A changer might return:
     *  - true - in this case the task will be considered as propagation source and propagation will be done only
     *    if the task has outstanding changes to propagate;
     *  - false or nothing - to cancel changes and skip propagation entirely;
     *  - a task instance, or array of task instances - to considered given instances as propagation source(s) and do
     *    the propagation
     *  If changer is not given or it's equal to Ext.emptyFn then propagation will be forcefully executed and tasks
     *  will be aligned/constrained according to their dependencies and/or constraints.
     * @param {Gnt.model.Task} changer.task The task
     * @param {Function} [callback] A callback function which will be called after changes propagation.
     * @param {Boolean}  callback.cancel Flag showing whether entire changes transaction has been canceled
     *  and nothing is changed.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     * @param {Boolean}  [forceCascadeChanges=task's task store `cascadeChanges` option] Flag indicating whether to propagate changes to dependent tasks.
     */
    propagateChanges : function(changer, callback, forceCascadeChanges) {
        var me = this,
            propagationSources,
            affectedTasks,
            taskStore,
            cascadeBatch;

        // <debug>
        !changer || Ext.isFunction(changer) ||
            Ext.Error.raise("Can't propagate changes to a task, invalid changer function given");
        !callback || Ext.isFunction(callback) ||
            Ext.Error.raise("Can't propagate changes to a task, invalid callback function given");
        // </debug>

        taskStore = me.getTaskStore(true);
        forceCascadeChanges = arguments.length == 3 ? forceCascadeChanges : taskStore && taskStore.cascadeChanges;

        if (!me.propagating && taskStore) {

            me.propagating = true;
            affectedTasks  = {};
            taskStore.suspendAutoSync();
            cascadeBatch   = taskStore.startBatchCascade();
            taskStore.startProjection();

            try {
                propagationSources = (changer && changer !== Ext.emptyFn) ? changer(me) : [me];
            }
            catch(e) {
                taskStore.rejectProjection();
                taskStore.endBatchCascade();
                taskStore.resumeAutoSync(taskStore.autoSync);
                me.propagating = false;
                throw e;
            }

            if (propagationSources === true) {
                propagationSources = me.isProjected() && [me] || false;
            }
            else if (propagationSources) {
                propagationSources = [].concat(propagationSources);
            }

            // Propagating
            if (propagationSources) {
                me.propagateChangesThroughDependentTasks(
                    taskStore.getLinearWalkingSequenceForDependentTasks(
                        propagationSources, {
                            self        : true,
                            ancestors   : taskStore.recalculateParents,
                            descendants : taskStore.moveParentAsGroup,
                            successors  : forceCascadeChanges,
                            cycles      : taskStore.cycleResolutionStrategy
                        }
                    ),
                    taskStore,
                    cascadeBatch,
                    propagationSources,
                    forceCascadeChanges,
                    affectedTasks,
                    function propagateChangesThroughDependentTasksCallback(cancelChanges, affectedTasks) {
                        if (cancelChanges) {
                            taskStore.rejectProjection();
                            affectedTasks = {};
                        }
                        else {
                            taskStore.commitProjection();
                        }
                        taskStore.endBatchCascade();

                        me.propagating = false;
                        callback && callback(cancelChanges, affectedTasks);

                        taskStore.resumeAutoSync(taskStore.autoSync && !cancelChanges && !Ext.Object.isEmpty(affectedTasks));
                    }
                );
            }
            else {
                taskStore.rejectProjection();
                taskStore.endBatchCascade();
                me.propagating = false;
                callback && callback(false, {});
                taskStore.resumeAutoSync(taskStore.autoSync);
            }
        }
        // No task store
        else if (!me.propagating) {
            me.propagating = true;

            try {
                changer && changer(me);
            }
            catch (e) {
                me.propagating = false;
                throw e;
            }

            me.verifyConstraint(function(constraintSatisfied, cancelChanges) {
                affectedTasks = {};
                cancelChanges = !!cancelChanges;
                if (!cancelChanges) {
                    affectedTasks[me.getId()] = me;
                }
                me.propagating = false;
                callback && callback(cancelChanges, affectedTasks);
            });
        }
        // We are currently propagating
        else {
            callback && callback(true, {});
        }
    },


    /**
     * @private
     *
     * @param {Array} linearWalkingSequence
     * @param {Gnt.model.Task} linearWalkingSequence[0] Step task
     * @param {String}         linearWalkingSequence[1] Color of the visiting step
     *  - 'green'  - Task is ready to be processed
     *  - 'yellow' - Branch task is ready to process it's children
     * @param {Object}         linearWalkingSequence[2] Set of all collected dependent tasks
     * @param {Object}         linearWalkingSequence[3] Dependency data
     * @param {Gnt.data.TaskStore} taskStore
     * @param {Object}             cascadeBatch
     * @param {Gnt.model.Task[]}   propagationSources
     * @param {Boolean}            forceCascadeChanges
     * @param {Object}             affectedTasks
     * @param {Function}           callback
     * @param {Integer}            startAt
     */
    propagateChangesThroughDependentTasks : function(linearWalkingSequence, taskStore, cascadeBatch, propagationSources, forceCascadeChanges, affectedTasks, callback, startAt) {
        var me = this,
            i, len,
            constraintSatisfied;

        startAt             = startAt    || 0;
        constraintSatisfied = true;

        for (i = startAt, len = linearWalkingSequence.length; constraintSatisfied && i < len; ++i) {

            constraintSatisfied = me.processTaskConstraints(
                linearWalkingSequence,
                i,
                taskStore,
                cascadeBatch,
                propagationSources,
                forceCascadeChanges,
                affectedTasks,
                function(linearWalkingIndex, constraintSatisfied, propagationCanceled, affectedTasks) {
                    // This callback might be called either synchronously or asynchronously thus we can't rely on
                    // `i` variable here. That's because if it is called synchronously then `i` will not yet be
                    // incremented by the for loop counter incrementing part, and if it's called asynchronously
                    // then `i` will be already incremented by the for loop directive. Thus we got the index
                    // for which this callback is called for as a parameter

                    // Stop condition
                    if (propagationCanceled || (linearWalkingIndex == len - 1)) {
                        callback(propagationCanceled, affectedTasks);
                    }
                    // Continue by recursion condition
                    else if (!constraintSatisfied) {
                        me.propagateChangesThroughDependentTasks(
                            linearWalkingSequence,
                            taskStore,
                            cascadeBatch,
                            propagationSources,
                            forceCascadeChanges,
                            affectedTasks,
                            callback,
                            linearWalkingIndex + 1
                        );
                    }
                    // Else constraint is satisfied and we will continue by the for loop
                }
            );
        }
    },

    /**
     * @private
     *
     * Will return `false` if a constraint conflict has been detected and awaiting for resolution, once resolved
     * the callback method will be called.
     */
    processTaskConstraints : function(linearWalkingSequence, linearWalkingIndex, taskStore, cascadeBatch, propagationSources, forceCascadeChanges, affectedTasks, callback) {
        var me                             = this,
            step                           = linearWalkingSequence[linearWalkingIndex],
            task                           = step[0],
            color                          = step[1],
            isParent                       = task.hasChildNodes(),
            isLeaf                         = !isParent,
            autoScheduled                  = !(task.isManuallyScheduled() || task.isReadOnly() || Ext.Array.contains(propagationSources, task)),
            cascadeChanges                 = forceCascadeChanges || taskStore.cascadeChanges,
            recalculateParents             = taskStore.recalculateParents,
            moveParentAsGroup              = taskStore.moveParentAsGroup,
            parentNode                     = task.parentNode,
            parentNodeStartDate            = parentNode && (parentNode.getStartDate()),
            parentNodeUnprojectedStartDate = parentNode && (parentNode.getUnprojected(parentNode.startDateField)),
            parentNodeDateOffset           = parentNode && (parentNodeStartDate - parentNodeUnprojectedStartDate),
            offsetFromParent;

        function areIncomingDependenciesAffectedOrPropagationSourcesIncoming(task, affectedTasks, propagationSources) {
            var incomingDeps = task.getIncomingDependencies(true),
                result = false,
                i, len, dep, fromTask;

            for (i = 0, len = incomingDeps.length; !result && i < len; ++i) {
                dep = incomingDeps[i];
                fromTask = dep.getSourceTask();
                result = fromTask && affectedTasks.hasOwnProperty(fromTask.getId()) ||
                                     Ext.Array.contains(propagationSources, fromTask);
            }

            return result;
        }

        switch (true) {
            case autoScheduled && isLeaf   && color == 'green'  && parentNodeDateOffset && moveParentAsGroup:
            case autoScheduled && isParent && color == 'yellow' && parentNodeDateOffset && moveParentAsGroup:
                // TODO: we ignore case when parent StartDate set to NULL
                // since we cannot calculate proper dates to shift child tasks at
                if (parentNodeStartDate) {
                    var startDate = task.getStartDate();

                    if (startDate >= parentNodeUnprojectedStartDate) {
                        offsetFromParent = task.calculateDuration(parentNodeUnprojectedStartDate, startDate, null, { segments : false });
                        task.setStartDateWithoutPropagation(task.calculateEndDate(parentNodeStartDate, offsetFromParent, null, { segments : false }), true, taskStore.skipWeekendsDuringDragDrop);

                    // if the summary task starts after this one
                    } else {
                        // force to not take segments into account during new start date calculating
                        offsetFromParent = task.calculateDuration(startDate, parentNodeUnprojectedStartDate, null, { segments : false });
                        task.setStartDateWithoutPropagation(task.calculateStartDate(parentNodeStartDate, offsetFromParent, null, { segments : false }), true, taskStore.skipWeekendsDuringDragDrop);
                    }

                    // Passing a parent node here limits the constraining to incoming dependencies incoming from
                    // that parent node descendants only, outer nodes are not taken into account
                    areIncomingDependenciesAffectedOrPropagationSourcesIncoming(task, affectedTasks, propagationSources) &&
                        task.alignByIncomingDependenciesWithoutPropagation(taskStore, null, parentNode);
                }

                break;

            case autoScheduled && isLeaf   && color == 'green'  && cascadeChanges:
            case autoScheduled && isParent && color == 'yellow' && cascadeChanges:

                areIncomingDependenciesAffectedOrPropagationSourcesIncoming(task, affectedTasks, propagationSources) &&
                    task.alignByIncomingDependenciesWithoutPropagation(taskStore, null);
                break;

            case isParent && color == 'green' && recalculateParents:

                task.refreshCalculatedParentNodeData();
                break;
        }

        if (task.isProjected()) {
            cascadeBatch.addAffected(task);
            affectedTasks[task.getId()] = task;
        }

        return task.verifyConstraint(function(constraintSatisfied, propagationCanceled) {
            var yellowStep,
                yellowStepIdx;

            // In case a parent node is adjusted according to it's children and such an adjustment violates
            // the parent node constraint then we rewind back to the same parent node yellow step to readjust
            // it and it's children once again allowing a user to reconsider (by showing him constraint violation
            // dialog, for example). We rewince by calling a callback with ajusted step index.
            if (!constraintSatisfied && isParent && autoScheduled && taskStore.recalculateParents && color == 'green') {
                yellowStep = Ext.Array.findBy(linearWalkingSequence, function(step, index) {
                    var stepTask  = step[0],
                        stepColor = step[1];

                    yellowStepIdx = index;

                    return task === stepTask && stepColor == 'yellow';
                });
                // yellowStep must always be present in the linear walking sequence.
                callback(yellowStepIdx, constraintSatisfied, !!propagationCanceled, affectedTasks);
            }
            else {
                callback(linearWalkingIndex, constraintSatisfied, !!propagationCanceled, affectedTasks);
            }
        });
    },

    removeLinkToTask : function(task) {
        var depStore    = this.getDependencyStore();
        var task1Id     = this.getId();
        var task2Id     = task.getId();

        Ext.Array.each(this.getAllDependencies(), function(dep) {
            if ((dep.getSourceId() === task1Id && dep.getTargetId() === task2Id) ||
                (dep.getSourceId() === task2Id && dep.getTargetId() === task1Id) )
            {
                depStore.remove(dep);
                return false;
            }
        });
    },

    convertEmptyParentToLeafTask : function() {
        this.beginEdit();
        this.set('leaf', true);
        this.setDurationWithoutPropagation(1, Sch.util.Date.DAY);
        this.endEdit();
    },

    hasEndPredecessorsButNoStartPredecessors : function() {
        var incoming = this.getIncomingDependencies();
        var result = incoming.length > 0;
        var Type   = Gnt.model.Dependency.Type;

        Ext.Array.each(incoming, function(dep) {
            if (dep.getType() === Type.StartToStart || dep.getType() === Type.EndToStart) {
                return result = false;
            }
        });

        return result;
    },

    isCompleted : function() {
        return this.getPercentDone() >= 100;
    }
});
