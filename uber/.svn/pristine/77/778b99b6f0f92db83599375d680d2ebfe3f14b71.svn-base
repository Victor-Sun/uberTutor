/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 @class Gnt.widget.taskeditor.BaseEditor
 @extends Ext.tab.Panel

 This is the baseclass for editors, it keeps the references to the stores and the loaded task instances.

 */

Ext.define('Gnt.widget.taskeditor.BaseEditor', {

    extend                  : 'Ext.tab.Panel',

    requires                : ['Gnt.util.Data'],

    mixins                  : ['Gnt.mixin.Localizable'],
    margin                  : '5 0 0 0',

    height                  : 340,
    width                   : 600,
    layout                  : 'fit',

    border                  : false,

    plain                   : false,

    defaults                : {
        margin          : 5,
        border          : false
    },

    eventIndicator          : 'task',

    /**
     * @cfg {Gnt.model.Task} task The task to edit.
     */
    task                    : null,

    //private a buffer for the task
    taskBuffer              : null,

    /**
     * @cfg {Gnt.data.TaskStore} taskStore A store with tasks.
     *
     * **Note:** This is a required option if the task being edited doesn't belong to any task store.
     */
    taskStore               : null,

    /**
     * @cfg {Gnt.data.AssignmentStore} assignmentStore A store with assignments.
     *
     * **Note:** It has to be provided to show the `Resources` tab (See also {@link #resourceStore}).
     */
    assignmentStore         : null,

    /**
     * @cfg {Gnt.data.ResourceStore} resourceStore A store with resources.
     *
     * **Note:** It has to be provided to show the `Resources` tab (See also {@link #assignmentStore}).
     */
    resourceStore           : null,

    tabBar                  : {
        cls : 'gnt-taskeditor-header'
    },

    clonedStores            : null,

    /**
     * @event beforeupdatetask
     * Fires before task updating occurs. Return `false` to prevent the update.
     * @param {Gnt.widget.taskeditor.BaseEditor} taskEditor The task editor widget instance.
     * @param {Function} proceedCallback The function which can be called manually to continue task updating. Example:
     *
     *      var taskEditor = Ext.create('Gnt.widget.taskeditor.TaskEditor', {
     *          listeners   : {
     *              beforeupdatetask    : function (taskeditor, proceedCallback) {
     *                  var me  = this;
     *                  Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?', function (buttonId) {
     *                      if (buttonId == 'yes') {
     *                          // here we continue updating asynchronously after user click "Yes" button
     *                          proceedCallback();
     *                          me.hide();
     *                      }
     *                  });
     *                  // here we return false to stop updating
     *                  return false;
     *              }
     *          }
     *      });
     *
     */

    /**
     * @event afterupdatetask
     * Fires after a task has been updated.
     *
     * This event can be used to do some extra processing after task was updated by task editor.
     * For example in case when you have some additional fields you can implement saving of them using this event.
     * Also please take a look at {@link #loadtask} event to have an example of how to implement custom data loading.
     *
     *      // some custom user form
     *      var customForm = new Gnt.widget.taskeditor.TaskForm({
     *          title : 'Custom form panel',
     *          items : [
     *              {
     *                  fieldLabel  : 'Foo field',
     *                  // foo - is the name of custom task field
     *                  name        : 'foo',
     *                  allowBlank  : false
     *              }
     *          ],
     *          taskStore   : taskStore
     *      });
     *
     *      var taskEditor = Ext.create('Gnt.plugin.TaskEditor', {
     *          // register custom form as an additional tab
     *          items       : customForm,
     *          listeners   : {
     *              afterupdatetask : function (taskeditor) {
     *                  // update form fields to loaded task
     *                  customForm.updateRecord();
     *              },
     *              ....
     *          }
     *      });
     *
     * @param {Gnt.widget.taskeditor.BaseEditor} taskEditor The task editor instance.
     */

    /**
     * @event validate
     * Fires when task validating occurs.
     * @param {Gnt.widget.taskeditor.BaseEditor} taskEditor The task editor instance.
     * @param {Ext.Component} tabToFocus The tab panel item where one or more invalid fields was detected.
     *
     * Fires during a {@link #method-validate} method call when task validation occurs.
     * Return `false` to make the validation fail, but take care of marking invalid component somehow (to let user know of error)
     * since normally invalid components are being highlighted during validate call.
     * For example:
     *
     *      var taskEditor = Ext.create('Gnt.widget.taskeditor.TaskEditor', {
     *          items       : {
     *              title   : 'Some custom tab',
     *              items   : [{
     *                  xtype       : 'textfield',
     *                  fieldLabel  : 'Enter your name',
     *                  id          : 'enter-your-name',
     *                  allowBlank  : false,
     *                  blankText   : 'Please enter your name'
     *              }]
     *          },
     *          listeners   : {
     *              validate    : function (taskeditor, tabToFocus) {
     *                  var field = taskeditor.down('#enter-your-name');
     *                  // if validation of our field failed
     *                  if (!field.isValid()) {
     *                      // if no other tabs with some invalid control
     *                      if (!tabToFocus) {
     *                          var activeTab = taskeditor.getActiveTab();
     *                          // if our field is not placed at currently active tab
     *                          if (!field.isDescendantOf(activeTab)) {
     *                              // then we'll switch to tab where our field resides
     *                              taskeditor.setActiveTab(taskeditor.getTabByComponent(field));
     *                          }
     *                      }
     *                      // return false since validation failed
     *                      return false;
     *                  }
     *              }
     *          }
     *      });
     *
     */

    /**/
    constructor : function (config) {
        var me  = this;

        config  = config || {};

        Ext.apply(me, config);

        // Prepare empty store clones (data loading occurs in loadTask() method).
        if (!me.clonedStores) {
            me.clonedStores = (me.task || me.taskStore) ? me.cloneStores() : {};
        }

        var items   = me.buildItems(config);

        var its     = me.items;

        // user defined tabs go after our predefined ones
        if (its) {
            items.push.apply(items, Ext.isArray(its) ? its : [its]);

            delete config.items;
        }

        me.items  = items;

        // if we have the only tab let's hide the tabBar
        if (me.items.length <= 1) {
            config.tabBar   = config.tabBar || {};
            Ext.applyIf(config.tabBar, { hidden : true });
        }

        this.callParent([config]);
    },


    buildItems : function () {
        return [];
    },


    cloneTaskBranch : function (task, taskCopy) {
        task            = task || this.task;

        var me              = this,
            taskStore       = me.getTaskStore(),
            root            = taskStore && taskStore.getRoot(),
            clonedStores    = me.clonedStores,
            taskClone,
            lastTask;

        // loop over task parents till the root node
        task.bubble(function (task) {
            if (task !== root) {
                var copy    = taskCopy[task.getId()];

                // stop if we met already copied task
                if (copy) {
                    // we attach cloned tasks to existing copy
                    if (lastTask) copy.appendChild(lastTask);
                    // "result.branch" will be empty in this case
                    lastTask = null;

                    return false;

                } else {
                    copy                    = me.cloneTask(task);
                    taskCopy[task.getId()]  = copy;
                    copy.taskStore          = clonedStores.taskStore;
                }

                if (lastTask) {
                    copy.appendChild(lastTask);
                } else {
                    taskClone   = copy;
                }

                lastTask        = copy;
            }
        });

        return {
            branch  : lastTask,
            task    : taskClone
        };
    },


    cloneRelevantTasks : function (task) {
        task            = task || this.task;

        var me          = this,
            taskCopy    = {};

        // clone task with its parents
        var cloned      = me.cloneTaskBranch(task, taskCopy),
            taskBuffer  = cloned.task,
            tasks       = [ cloned.branch ];

        // clone predecessors
        Ext.Array.each(task.predecessors, function (d) {
            var cloned      = me.cloneTaskBranch(d.getSourceTask(), taskCopy);
            if (cloned.branch) tasks.push(cloned.branch);
        });

        // clone successors
        Ext.Array.each(task.successors, function (d) {
            var cloned      = me.cloneTaskBranch(d.getTargetTask(), taskCopy);
            if (cloned.branch) tasks.push(cloned.branch);
        });

        return {
            task    : taskBuffer,
            tasks   : tasks
        };
    },


    /**
     * Loads task data into task editor.
     * @param {Gnt.model.Task} task Task to load to editor.
     */
    loadTask : function (task) {
        if (!task) return;

        // clone stores ..if they were not cloned yet
        this.clonedStores = this.cloneStores({ task : task });

        // fill cloned stores with data
        this.loadClonedStores(this.clonedStores, task);

    },


    // We need fake taskStore to give task copy ability to ask it for the project calendar
    cloneTaskStore : function (task, config) {
        var store   = this.getTaskStore();

        if (!store) return null;

        var copy                                = new store.self(Ext.apply({
            isCloned                            : true,
            // TODO: ideally we need to clone calendar manager as well
            // but this is not that trivial since adding records to a calendar manager
            // automatically causes calendars creation
            calendarManager                     : store.calendarManager,
            // Ticket #1815:
            // Important, not to confuse the StoreManager
            storeId                             : null,
            calendar                            : store.getCalendar(),
            model                               : store.model,
            weekendsAreWorkdays                 : store.weekendsAreWorkdays,
            cascadeChanges                      : store.cascadeChanges,
            batchSync                           : false,
            recalculateParents                  : false,
            skipWeekendsDuringDragDrop          : store.skipWeekendsDuringDragDrop,
            moveParentAsGroup                   : store.moveParentAsGroup,
            enableDependenciesForParentTasks    : store.enableDependenciesForParentTasks,
            availabilitySearchLimit             : store.availabilitySearchLimit,
            // Switch auto normalization, since it might corrupt parent nodes auto-calculated fields (Effort etc.)
            // because we don't clone all the children
            autoNormalizeNodes                  : false,
            dependenciesCalendar                : store.dependenciesCalendar,
            proxy                               : {
                type        : 'memory',
                reader      : {
                    type    : 'json'
                }
            }
        }, config));

        // on bind different calendar to the original task store we do the same for the copy
        this.mon(store, {
            calendarset : function (store, calendar) {
                copy.setCalendar(calendar);
            }
        });

        return copy;
    },


    cloneDependencyStore : function (task, config) {
        var taskStore   = this.getTaskStore();
        var store       = this.dependencyStore || taskStore && taskStore.getDependencyStore();

        if (!store) return null;

        return new store.self(Ext.apply({
            isCloned                    : true,
            model                       : store.model,
            strictDependencyValidation  : store.strictDependencyValidation,
            allowedDependencyTypes      : store.allowedDependencyTypes,
            proxy                       : {
                type        : 'memory',
                reader      : {
                    type    : 'json'
                }
            }
        }, config));
    },


    cloneAssignmentStore : function (task, config) {
        var taskStore   = this.getTaskStore();
        var store       = this.assignmentStore || taskStore && taskStore.getAssignmentStore();

        if (!store) return null;

        return new store.self(Ext.apply({
            isCloned        : true,
            model           : store.model,
            proxy           : {
                type        : 'memory',
                reader      : {
                    type    : 'json'
                }
            }
        }, config));
    },


    cloneResourceStore : function (task, config) {
        var taskStore   = this.getTaskStore();
        var store       = this.resourceStore || taskStore && taskStore.getResourceStore();

        if (!store) return null;

        return new store.self(Ext.apply({
            isCloned        : true,
            model           : store.model,
            proxy           : {
                type        : 'memory',
                reader      : {
                    type    : 'json'
                }
            }
        }, config));
    },


    cloneStores : function (config) {
        config                  = config || {};

        var task                = config.task || this.task,
            clonedStores        = this.clonedStores || {},
            resourceStore       = clonedStores.resourceStore    || this.cloneResourceStore(task, config && config.resourceStore),
            assignmentStore     = clonedStores.assignmentStore  || this.cloneAssignmentStore(task, config && config.assignmentStore),
            dependencyStore     = clonedStores.dependencyStore  || this.cloneDependencyStore(task, config && config.dependencyStore);

        var taskStore           = clonedStores.taskStore || this.cloneTaskStore(task, Ext.apply({
            assignmentStore     : assignmentStore,
            resourceStore       : resourceStore,
            dependencyStore     : dependencyStore
        }, config && config.taskStore));

        resourceStore.taskStore = taskStore;

        Ext.apply(clonedStores, {
            resourceStore   : resourceStore,
            assignmentStore : assignmentStore,
            dependencyStore : dependencyStore,
            taskStore       : taskStore
        });

        return clonedStores;
    },


    getTaskStore : function (task) {
        task    = task || this.task;

        return this.taskStore || task && task.getTaskStore();
    },


    loadClonedStores : function (clonedStores, task) {
        // copy relevant tasks for TaskStore clone
        var me          = this,
            data        = me.cloneRelevantTasks(task),
            tasks       = data.tasks,
            taskBuffer  = data.task;

        taskBuffer.taskStore.on({
            update : function (store, record, operation) {
                if (record === taskBuffer && operation == Ext.data.Model.EDIT) {
                    me.onTaskUpdated.call(me, record);
                    record.fireEvent(me.eventIndicator + 'updated', record);
                }
            }
        });

        // fill task store clone w/ related task copies
        clonedStores.taskStore.setRootNode({
            expanded    : true,
            children    : tasks
        });

        me.loadClonedDependencyStore(clonedStores, task);
        me.loadClonedResourceStore(clonedStores, task);
        me.loadClonedAssignmentStore(clonedStores, task);

        me.taskBuffer = taskBuffer;
    },


    loadClonedDependencyStore : function (clonedStores, task) {
        clonedStores    = clonedStores || this.clonedStores;

        clonedStores.dependencyStore && clonedStores.dependencyStore.loadData(
            Gnt.util.Data.cloneModelSet(
                task.getAllDependencies(),
                function (copy, original) { copy.setId(original.getId()); }
            )
        );
    },


    loadClonedResourceStore : function (clonedStores) {
        clonedStores    = clonedStores || this.clonedStores;

        clonedStores.resourceStore && clonedStores.resourceStore.loadData(
            Gnt.util.Data.cloneModelSet(
                this.resourceStore || this.getTaskStore().getResourceStore(),
                function (copy, original) { copy.setId(original.getId()); }
            )
        );
    },


    loadClonedAssignmentStore : function (clonedStores, task) {
        clonedStores    = clonedStores || this.clonedStores;

        clonedStores.assignmentStore && clonedStores.assignmentStore.loadData(
            Gnt.util.Data.cloneModelSet(
                task.getAssignments(),
                function (copy, original) { copy.setId(original.getId()); }
            )
        );
    },


    cloneTask : function (task) {
        return task.copy(task.getId(), false);
    },


    /**
     * Returns the task editor tab that contains specified component.
     * @return {Ext.Component} Tab containing specified component or `undefined` if item is not found.
     */
    getTabByComponent : function (component) {
        var result;
        this.items.each(function (el) {
            if (component === el || component.isDescendantOf(el)) {
                result = el;
                return false;
            }
        }, this);

        return result;
    },

    /**
     * Checks data loaded or entered to task editor for errors.
     * Calls isValid methods of taskForm, dependencyGrid, advancedForm (if corresponding objects are presented at the task editor).
     * In case some of calls returns `false` switch active tab so that user can view invalid object.
     * Validation can be customized by handling {@link #event-validate} event.
     *
     * Returns `false` in that case.
     * @return {Boolean} Returns `true` if all components are valid.
     */
    validate : function() {
        var result,
            activeTab = this.getActiveTab(),
            invalidTabs = [],
            tabToActivate;

        result = this.doValidate(function (tab) {
            invalidTabs.push(tab);
        });

        if (!result && activeTab && !Ext.Array.contains(invalidTabs, activeTab)) {
            tabToActivate = invalidTabs[0];
            this.setActiveTab(tabToActivate);
        }
        else if (!result && activeTab) {
            tabToActivate = activeTab;
        }
        else if (!result) {
            tabToActivate = invalidTabs[0];
        }

        // validation result
        return (this.fireEvent('validate', this, tabToActivate) !== false) && result;
    },

    /**
     * Persists the values in this task editor into corresponding {@link Gnt.model.Task} object provided to showTask.
     * @return {Boolean} Returns `true` if task was updated. Returns False if some {@link #beforeupdatetask} listener returns False.
     */
    updateTask : function () {
        var me     = this,
            result = false;

        if (me.fireEvent('beforeupdate' + me.eventIndicator, me, function() { me.doUpdateTask(); }) !== false) {
            me.doUpdateTask();
            me.fireEvent('afterupdate' + me.eventIndicator, me);
            result = true;
        }

        return result;
    },

    onDestroy : function() {
        if (this.clonedStores.taskStore) {
            this.clonedStores.taskStore.destroy();
        }

        this.callParent(arguments);
    },

    doValidate : function () {
        return true;
    },

    isDataValid : function () {
        return this.doValidate();
    },

    isDataChanged : function () {
        return false;
    },

    doUpdateTask : function () {
        throw 'Abstract method called';
    },

    /**
     * Updates underlying components readOnly state as reaction on either the editor readOnly state change
     * or the task being editing update.
     * @protected
     */
    updateReadOnly : function () {
        throw 'Abstract method called';
    },

    getReadOnly : function () {
        return this.readOnly;
    },

    setReadOnly : function (readOnly) {
        this.readOnly = readOnly;

        this.updateReadOnly();
    },

    onTaskUpdated : function () {
        this.updateReadOnly();
    }

});
