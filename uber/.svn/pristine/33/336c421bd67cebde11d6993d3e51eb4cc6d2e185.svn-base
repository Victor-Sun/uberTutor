/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.widget.taskeditor.TaskEditor
@extends Gnt.widget.taskeditor.BaseEditor

A widget used to display and edit task information.
By default the widget is an Ext.tab.Panel instance which can contain the following tabs:

 - General information
 - Predecessors
 - Resources
 - Advanced
 - Notes

You can easily add new custom tabs using {@link #items} config.

# General

{@img gantt/images/taskeditor-panel-general.png}

Contains a customizable {@link Gnt.widget.TaskForm form} instance for viewing and editing the following task data:

 - the name of the task
 - the start date of the task
 - the end date of the task
 - the task duration
 - the task effort
 - the current status of a task, expressed as the percentage completed
 - the baseline start date of the task (editing of this field is optional)
 - the baseline end date of the task (editing of this field is optional)
 - the baseline status of a task, expressed as the percentage completed (editing of this field is optional)

### Task form customization

There is a {@link #taskFormConfig} config which can be used to customize the form panel.

        Ext.create('Gnt.widget.taskeditor.TaskEditor', {
            // Configure the form located in the "General" tab
            taskFormConfig : {
                // turn off fields highlighting
                highlightTaskUpdates : false,
                // alter panel margin
                margin : 20
            }
        });

### Fields configuration

The {@link Gnt.widget.TaskForm} class has a config for each field presented at the `General` tab.
And using {@link #taskFormConfig} we can get access for those options to setup fields.
For example:

        Ext.create('Gnt.widget.taskeditor.TaskEditor', {
            // setup form located at "General" tab
            taskFormConfig : {
                // set Baseline Finish Date field invisible
                baselineFinishConfig : {
                    hidden : true
                }
            }
        });

Here are some more configs for other fields:

 - {@link Gnt.widget.TaskForm#taskNameConfig taskNameConfig} (the name of the task field)
 - {@link Gnt.widget.TaskForm#startConfig startConfig} (the start date of the task field)
 - {@link Gnt.widget.TaskForm#finishConfig finishConfig} (the end date of the task field)
 - {@link Gnt.widget.TaskForm#durationConfig durationConfig} (the task duration field)

Please see {@link Gnt.widget.TaskForm} class to see the full list of available config options.

### Extending the General field set

If you want to add a new field to the `General` tab you will have to extend the {@link Gnt.widget.TaskForm TaskForm} class.
After that you will need to configure the task editor to use your extended class:

        // extend standard TaskForm class
        Ext.define('MyTaskForm', {
            extend : 'Gnt.widget.taskeditor.TaskForm',

            constructor : function(config) {
                this.callParent(arguments);

                // add some custom field
                this.add({
                    fieldLabel  : 'Foo',
                    name        : 'Name',
                    width       : 200
                });
            }
        });

        // Let task editor know which class to use
        Ext.create('Gnt.widget.taskeditor.TaskEditor', {
            // to use MyTaskForm to build the "General" tab
            taskFormClass : 'MyTaskForm'
        });

#Predecessors

Contains a {@link Gnt.widget.DependencyGrid grid} instance displaying the predecessors for the task.
You can add, edit or remove dependencies of the task using this panel.

{@img gantt/images/taskeditor-panel-predecessors.png}

You can enable/disable this tab by setting the {@link #showDependencyGrid} option.
To rename this tab you can use `dependencyText` property of {@link #l10n} config.
Customizing the grid itself can be done via the {@link #dependencyGridConfig} config.
To change make this tab display successors instead of predecessors - use the following code:

        Ext.create('Gnt.widget.taskeditor.TaskEditor', {
            l10n : {
                // here we change tab title
                dependencyText : 'Successors'
            },
            // here is the grid config
            dependencyGridConfig : {
                // set grid to display successors
                direction : 'successors'
            }
        });

### Customizing the dependency grid class

You can also configure the task editor to use a custom class to build this tab using the {@link #dependencyGridClass} option.
If you need to add an extra column to the grid, you can do it like this:

        // extend standard DependencyGrid
        Ext.define('MyDependencyGrid', {
            extend: 'Gnt.widget.DependencyGrid',

            // extend buildColumns method to append extra column
            buildColumns : function () {
                // add custom column as last one
                return this.callParent(arguments).concat({
                    header    : 'Foo',
                    dataIndex : 'foo',
                    width     : 100
                });
            }
        });

        // setup task editor
        Ext.create('Gnt.widget.taskeditor.TaskEditor', {
            // to use extended class to build tab
            dependencyGridClass : 'MyDependencyGrid'
        });

#Resources

Contains a {@link Gnt.widget.AssignmentEditGrid grid} instance displaying the task assignments.
It allows you to add, edit or remove task assignments.

{@img gantt/images/taskeditor-panel-resources2.png}

It also supports inline resource adding (for more details, take a look at the {@link Gnt.widget.AssignmentEditGrid#addResources} config.

{@img gantt/images/taskeditor-panel-resources1.png}

You can enable/disable this tab by setting the {@link #showAssignmentGrid} option.
To rename this tab you can use the `resourcesText` property of {@link #l10n} config.
Customizing the grid can be done via the {@link #assignmentGridConfig} config.

Example:

        Ext.create('Gnt.widget.taskeditor.TaskEditor', {
            assignmentStore : assignmentStore,
            resourceStore : resourceStore,
            l10n : {
                // rename tab
                resourcesText : 'Assignments'
            },
            // here is grid the config
            assignmentGridConfig : {
                // disable in-place resources adding
                addResources : false
            }
        });

### Customizing the assignment grid class

You can use a custom grid class for this tab by using the {@link #assignmentGridClass} option.
Example: if you need to add extra column to the grid you can do it like this:

        // Extend the standard AssignmentGrid
        Ext.define('MyAssignmentGrid', {
            extend: 'Gnt.widget.AssignmentEditGrid',

            // extend buildColumns method to append extra column
            buildColumns : function () {
                // add custom column as last one
                return this.callParent(arguments).concat({
                    header       : 'Foo',
                    dataIndex    : 'foo',
                    width        : 100
                });
            }
        });

        // setup task editor
        Ext.create('Gnt.widget.taskeditor.TaskEditor', {
            // use extended class
            assignmentGridClass : 'MyAssignmentGrid'
        });

#Advanced

Contains a {@link Gnt.widget.AdvancedForm form} instance which can be customized, allowing the user to view and edit the following task data:

 - calendar assigned to the task
 - scheduling mode for the task
 - manually scheduled flag
 - WBS code
 - rollup flag
 - constraint type
 - constraint date
 - read only flag

{@img gantt/images/taskeditor-panel-advanced.png}

You can enable/disable this tab by setting the {@link #showAdvancedForm} option.
To rename this tab you can use the `advancedText` property of {@link #l10n} config.

Customizing the form itself can be done via the {@link #advancedFormConfig} config. For example this is how form content can be overwritten:

        Ext.create("Gnt.widget.taskeditor.TaskEditor", {
            advancedFormConfig: {
                items: [
                     // new fields that will go here
                     // will replace standard presented in the "Advanced" tab
                     ...
                ]
            }
        });

### Customizing the form class

You can use your own custom class to build this tab by using the {@link #advancedFormClass} config:
For example if you need to add some extra field you can do it like this:

        // Extend standard TaskForm class
        Ext.define('MyAdvancedForm', {
            extend : 'Gnt.widget.taskeditor.AdvancedForm',

            constructor : function(config) {
                this.callParent(arguments);

                // add some custom field
                this.add({
                    fieldLabel  : 'Foo',
                    name        : 'Name',
                    width       : 200
                });
            }
        });

        // setup task editor
        Ext.create("Gnt.widget.taskeditor.TaskEditor", {
            // to use new class to build the "Advanced" tab
            advancedFormClass: 'MyAdvancedForm',
        });

#Notes

Contains an {@link Ext.form.field.HtmlEditor HTML editor instance} for viewing and editing a freetext note about the task.

{@img gantt/images/taskeditor-panel-notes.png}

You can enable/disable this tab by setting the {@link #showNotes} option.
To rename this tab you can use the `notesText` property of {@link #l10n} config.
Customizing the grid itself can be done via the {@link #notesConfig} config.

*/
Ext.define('Gnt.widget.taskeditor.TaskEditor', {

    extend                  : 'Gnt.widget.taskeditor.BaseEditor',

    alias                   : 'widget.taskeditor',

    requires                : [
        'Ext.form.field.HtmlEditor',
        'Ext.layout.container.Table',
        'Gnt.widget.taskeditor.TaskForm',
        'Gnt.widget.taskeditor.AdvancedForm',
        'Gnt.widget.AssignmentEditGrid',
        'Gnt.widget.DependencyGrid'
    ],

    alternateClassName      : ['Gnt.widget.TaskEditor'],

    /**
     * @event loadtask
     * Fires after task has been loaded into the editor.
     *
     * This event can be used to do additional data loading if task editor was extended with some extra fields.
     * Also please take a look at {@link #afterupdatetask} event to have an example of how to implement custom data saving.
     *
     *      // some custom user form
     *      var customForm = new Gnt.widget.taskeditor.TaskForm({
     *          title : 'Custom form panel',
     *          items : [
     *              {
     *                  fieldLabel  : 'Foo field',
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
     *              // populate custom form with task values
     *              loadtask : function (taskeditor, task) {
     *                  customForm.loadRecord(task);
     *              },
     *              ....
     *          }
     *      });
     *
     * @param {Gnt.widget.taskeditor.TaskEditor} taskEditor The task editor widget instance.
     * @param {Gnt.model.Task} task The task.
     */

    /**
     * @cfg {String} taskFormClass Class representing the form in the `General` tab.
     *
     * This option supposed to be used to implement a custom form in the `General` tab content.
     */
    taskFormClass           : 'Gnt.widget.taskeditor.TaskForm',

    /**
     * @cfg {String} advancedFormClass Class representing the form in the `Advanced` tab.
     *
     * This option supposed to be used to implement a custom form in the `Advanced` tab content.
     */
    advancedFormClass       : 'Gnt.widget.taskeditor.AdvancedForm',

    /**
     * @cfg {Boolean} showAssignmentGrid `true` to display the `Resources` tab.
     */
    showAssignmentGrid      : true,

    /**
     * @cfg {Boolean} showDependencyGrid `true` to display the `Predecessors` tab.
     */
    showDependencyGrid      : true,

    /**
     * @cfg {Boolean} allowParentTaskDependencies `false` to hide a `Predecessors` tab for parent tasks
     * (requires {@link #showDependencyGrid} to be `false` as well) and also exclude parent tasks from the list
     * of possible predecessors.
     */
    allowParentTaskDependencies     : true,

    /**
     * @cfg {Boolean} showNotes `true` to display the `Notes` tab.
     */
    showNotes               : true,

    /**
     * @cfg {Boolean} showAdvancedForm `true` to display the `Advanced` tab.
     */
    showAdvancedForm        : true,

    /**
     * @cfg {Boolean} showRollup `true` to display rollup field on the `Advanced` tab.
     */
    showRollup              :  false,

    /**
     * @cfg {Boolean} showReadOnly `false` to hide readonly field on the `Advanced` tab.
     */
    showReadOnly            :  true,

    /**
     * @cfg {Object/Object[]} items A single item, or an array of child Components to be **appended** after default tabs to this container.
     * For example:
     *
     *      var taskEditor = Ext.create('Gnt.widget.taskeditor.TaskEditor', {
     *          items: [{
     *              title   : "Some custom tab",
     *              items   : [{
     *                  xtype       : 'textfield',
     *                  fieldLabel  : 'Enter your name',
     *                  id          : 'enter-your-name',
     *                  allowBlank  : false,
     *                  blankText   : 'Please enter your name'
     *              }]
     *          }]
     *      });
     */

    /**
     * @cfg {Boolean} showBaseline `true` to display baseline fields in the `General` tab.
     */
    showBaseline            : true,

    /**
     * @cfg {Object} taskFormConfig Configuration options to be supplied to the `General` tab.
     * For possible options take a look at the {@link Gnt.widget.TaskForm}.
     */
    taskFormConfig          : null,

    /**
     * @cfg {String} dependencyGridClass Class representing the grid panel in the `Predecessor` tab.
     *
     * Override this to provide your own implementation subclassing the {@link Gnt.widget.DependencyGrid} class.
     */
    dependencyGridClass     : 'Gnt.widget.DependencyGrid',

    /**
     * @cfg {Object} dependencyGridConfig Configuration options for the `Predecessors` tab.
     * For possible options take a look at the {@link Gnt.widget.DependencyGrid}.
     *
     */
    dependencyGridConfig    : null,

    /**
     * @cfg {String} assignmentGridClass Class representing the grid panel in the `Resources` tab.
     *
     * Override this to provide your own implementation subclassing the {@link Gnt.widget.AssignmentEditGrid} class.
     */
    assignmentGridClass     : 'Gnt.widget.AssignmentEditGrid',

    /**
     * @cfg {Object} assignmentGridConfig Configuration options for the `Resources` tab.
     * For possible options take a look at the {@link Gnt.widget.AssignmentEditGrid}.
     *
     */
    assignmentGridConfig    : null,

    /**
     * @cfg {Object} advancedFormConfig Configuration options for the `Advanced` tab.
     * For possible options take a look at the {@link Gnt.widget.TaskForm}.
     *
     */
    advancedFormConfig      : null,

    /**
     * @cfg {Object} notesConfig Configuration options for the HTML-editor placed in the `Notes` tab.
     * For possible options take a look at the {@link Ext.form.field.HtmlEditor}.
     */
    notesConfig             : null,

    /**
     * @property {Ext.panel.Panel} notesPanel The `Notes` tab.
     * Please use {@link #notesEditor} to access an enclosed HTML-editor.
     */
    notesPanel              : null,

    /**
     * @property {Ext.form.field.HtmlEditor} notesEditor The HTML-editor presented in the `Notes` tab.
     * To specify setting for the HTML-editor please use {@link #notesConfig}.
     */
    notesEditor             : null,


    /**
     * @property {Gnt.widget.TaskForm} taskForm The `General` tab task form.
     * By default it's a {@link Gnt.widget.TaskForm} instance but it might be customized by using {@link #taskFormClass} option.
     */
    taskForm                : null,

    /**
     * @property {Gnt.widget.AssignmentEditGrid} assignmentGrid The grid used for the `Resources` tab.
     *
     */
    assignmentGrid          : null,

    /**
     * @property {Gnt.widget.DependencyGrid} dependencyGrid The `Predecessors` tab instance.
     *
     */
    dependencyGrid          : null,
    /**
     * @property {Gnt.widget.TaskForm} advancedForm The `Advanced` tab form.
     * By default it's a {@link Gnt.widget.TaskForm} instance but it can be customized by using {@link #advancedFormClass} option.
     *
     */
    advancedForm            : null,

    margin                  : 0,

    /**
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

            - generalText         : 'General',
            - resourcesText       : 'Resources',
            - dependencyText      : 'Predecessors',
            - addDependencyText   : 'Add new',
            - dropDependencyText  : 'Remove',
            - notesText           : 'Notes',
            - advancedText        : 'Advanced',
            - wbsCodeText         : 'WBS code',
            - addAssignmentText   : 'Add new',
            - dropAssignmentText  : 'Remove'
     */

    buildItems : function () {
        var me      = this,
            items   = [];

        var clonedStores    = me.clonedStores || {};

        me.taskFormConfig   = me.taskFormConfig || {};

        Ext.applyIf(me.taskFormConfig, {
            showBaseline    : me.showBaseline,
            showRollup      : false //we show it here
        });

        // create TaskForm instance
        me.taskForm = Ext.create(me.taskFormClass, Ext.apply({
            task        : me.task,
            border      : false,
            taskStore   : me.taskStore
        }, me.taskFormConfig));

        items.push(me.taskForm);

        // create DependencyGrid instance
        if (me.showDependencyGrid) {
            me.dependencyGrid = Ext.create(me.dependencyGridClass, Ext.apply({
                allowParentTaskDependencies : me.allowParentTaskDependencies,
                taskModel                   : me.taskStore.model,
                task                        : me.task,
                margin                      : 0,
                border                      : false,
                tbar                        : {
                    layout  : 'auto',
                    items   : [
                        {
                            xtype       : 'button',
                            iconCls     : 'gnt-action-add',
                            text        : me.L('addDependencyText'),
                            itemId      : 'add-dependency-btn',
                            handler     : function () {
                                me.dependencyGrid.insertDependency();
                            }
                        },
                        {
                            xtype       : 'button',
                            iconCls     : 'gnt-action-remove',
                            text        : me.L('dropDependencyText'),
                            itemId      : 'drop-dependency-btn',
                            disabled    : true,
                            handler     : function () {
                                var recs = me.dependencyGrid.getSelectionModel().getSelection();
                                if (recs && recs.length) {
                                    me.dependencyGrid.store.remove(recs);
                                }
                            }
                        }
                    ]
                },
                listeners   : {
                    selectionchange : function (sm, sel) {
                        var grid    = me.dependencyGrid;
                        if (!grid.dropDepBtn) {
                            grid.dropDepBtn  = grid.down('#drop-dependency-btn');
                        }
                        grid.dropDepBtn && grid.dropDepBtn.setDisabled(!sel.length);
                    }
                }
            }, me.dependencyGridConfig));

            items.push(me.dependencyGrid);
        }

        // if AssignmentGrid required
        if (me.showAssignmentGrid && me.assignmentStore && me.resourceStore) {
            // clone assignment and resource stores if they were not copied before
            if (!clonedStores.assignmentStore) clonedStores.assignmentStore = me.cloneAssignmentStore(me.task);
            if (!clonedStores.resourceStore) clonedStores.resourceStore     = me.cloneResourceStore(me.task);

            // create AssignmentGrid instance
            me.assignmentGrid     = Ext.create(me.assignmentGridClass, Ext.apply({
                assignmentStore     : me.assignmentStore,
                resourceStore       : me.resourceStore,
                border              : false,
                margin              : 0,
                // we use clone of assignment store as assignmentGrid.store
                store               : clonedStores.assignmentStore,
                resourceDupStore    : clonedStores.resourceStore,
                tbar                : {
                    layout  : 'auto',
                    items   : [
                        {
                            xtype       : 'button',
                            iconCls     : 'gnt-action-add',
                            text        : me.L('addAssignmentText'),
                            itemId      : 'add-assignment-btn',
                            handler     : function () {
                                me.assignmentGrid.insertAssignment();
                            }
                        },
                        {
                            xtype       : 'button',
                            iconCls     : 'gnt-action-remove',
                            text        : me.L('dropAssignmentText'),
                            itemId      : 'drop-assignment-btn',
                            disabled    : true,
                            handler     : function () {
                                var recs = me.assignmentGrid.getSelectionModel().getSelection();
                                if (recs && recs.length) {
                                    me.assignmentGrid.store.remove(recs);
                                }
                            }
                        }
                    ]
                },
                listeners       : {
                    // we need this to draw selection properly on very first activation of tab
                    // to gracefully process deferredRender = true
                    afterrender : {
                        fn      : function(el) {
                            el.loadTaskAssignments(me.task.get(me.task.idProperty));
                        },
                        single  : true
                    },
                    selectionchange : function(sm, sel) {
                        var grid    = me.assignmentGrid;
                        if (!grid.dropBtn) {
                            grid.dropBtn = grid.down('#drop-assignment-btn');
                        }
                        grid.dropBtn && grid.dropBtn.setDisabled(!sel.length);
                    }
                }
            }, me.assignmentGridConfig));

            items.push(me.assignmentGrid);
        }

        // if advanced form required
        if (me.showAdvancedForm) {
            me.advancedFormConfig = me.advancedFormConfig || {};

            // create TaskForm instance for the "Advanced" tab form
            me.advancedForm = Ext.create(me.advancedFormClass, Ext.applyIf(me.advancedFormConfig, {
                showGeneral             : false,
                showBaseline            : false,
                showCalendar            : true,
                showManuallyScheduled   : true,
                showSchedulingMode      : true,
                showWbsCode             : true,
                showConstraint          : true,
                showRollup              : me.showRollup,
                showReadOnly            : me.showReadOnly,
                layout                  : {
                    type    : 'table',
                    columns : 2
                },
                border                  : false,
                margin                  : 0,
                task                    : me.task,
                taskStore               : me.taskStore
            }));

            items.push(me.advancedForm);
        }

        // create notes panel
        if (me.showNotes) {
            // create notes HtmlEditor instance
            me.notesEditor = Ext.create('Ext.form.field.HtmlEditor', Ext.apply({
                listeners       : {
                    // we need this to draw content of HtmlEditor properly on very first activation of tab
                    // to gracefully process deferredRender = true
                    afterrender : function (el) {
                        me.notesEditor.setValue(me.task.get(me.task.noteField));
                    }
                },

                readOnly        : me.task && !me.task.isEditable(me.task.noteField),

                isDataChanged   : function() {
                    return this.isDirty();
                }
            }, me.notesConfig));

            // we have to wrap it to panel since it's gonna be tab in TabPanel
            // (to avoid some render bugs)
            me.notesPanel = Ext.create('Ext.panel.Panel', {
                border  : false,
                margin  : 0,
                layout  : 'fit',
                items   : me.notesEditor
            });

            items.push(me.notesPanel);
        }

        // make sure that each panel has its title
        if (!me.taskForm.title) me.taskForm.title                                   = me.L('generalText');
        if (me.assignmentGrid && !me.assignmentGrid.title) me.assignmentGrid.title  = me.L('resourcesText');
        if (me.advancedForm && !me.advancedForm.title) me.advancedForm.title        = me.L('advancedText');
        if (me.notesPanel && !me.notesPanel.title) me.notesPanel.title              = me.L('notesText');

        return items;
    },


    bindDependencyGrid : function () {
        var depsClone           = this.clonedStores.dependencyStore;
        var grid                = this.dependencyGrid;

        // dependency grid store have to use cloned task store
        grid.store.taskStore    = this.clonedStores.taskStore;

        if (depsClone) {

            this.mon(grid, {
                loaddependencies : function (grid, store) {
                    depsClone.loadData( store.getRange().concat(Gnt.util.Data.cloneModelSet(grid.oppositeData)) );
                }
            });

            this.mon(grid.store, {
                add     : function (store, records) {
                    depsClone.add(records);
                },
                remove  : function (store, record) {
                    depsClone.remove(record);
                }
            });

            this.dependencyGridBound    = true;
        }
    },

    /**
     * Loads task data into task editor.
     * @param {Gnt.model.Task} task Task to load to editor.
     */
    loadTask : function (task) {
        if (!task) return;

        this.task           = task;

        var me              = this,
            taskForm        = me.taskForm,
            dependencyGrid  = me.dependencyGrid,
            assignmentGrid  = me.assignmentGrid,
            readOnly        = task.isReadOnly();

        // on task loading step let's suppress task updating
        taskForm.setSuppressTaskUpdate(true);
        taskForm.getForm().reset();

        me.callParent(arguments);

        var clonedStores    = me.clonedStores,
            taskBuffer      = me.taskBuffer;

        if (dependencyGrid) {
            if (!me.dependencyGridBound) me.bindDependencyGrid();
            // we always load records into the grid event when tab is not visible
            // since we use its ability to load task dependencies to fill our dependency store clone with records
            dependencyGrid.loadDependencies(task);

            dependencyGrid.tab.setVisible(me.allowParentTaskDependencies || task.isLeaf());
        }

        if (assignmentGrid) {
            // we use clone of assignment store as assignmentGrid.store
            if (clonedStores.assignmentStore !== assignmentGrid.getStore()) assignmentGrid.reconfigure(clonedStores.assignmentStore);
            if (assignmentGrid.resourceDupStore !== clonedStores.resourceStore) assignmentGrid.resourceDupStore = clonedStores.resourceStore;

            assignmentGrid.loadResources(true);
            // load task assignments to grid
            assignmentGrid.loadTaskAssignments(task.getId() || task.getPhantomId());

            assignmentGrid.task = taskBuffer;
        }

        taskForm.loadRecord(task, taskBuffer);

        if (me.advancedForm) {
            // disable 'taskupdated' event processing for advancedForm
            me.advancedForm.setSuppressTaskUpdate(true);

            var form    = me.advancedForm.getForm();

            form.reset();

            // we point advancedForm.taskBuffer to taskForm.taskBuffer
            // it will allow them to share changes of each other
            me.advancedForm.loadRecord(task, taskForm.taskBuffer);

            var field   = form.findField('wbsCode');
            if (field) {
                field.setValue(task.getWBSCode());
            }

            // enable 'taskupdated' event processing for advancedForm back
            me.advancedForm.setSuppressTaskUpdate(false);
        }

        // enable 'taskupdated' event processing back
        taskForm.setSuppressTaskUpdate(false);

        if (me.notesEditor) {
            me.notesEditor.setValue(task.getNote());
        }

        me.setReadOnly(readOnly);

        me.fireEvent('loadtask', me, task);
    },

    updateReadOnly : function () {
        var me             = this,
            task           = me.taskBuffer,
            widgetReadOnly = me.getReadOnly(),
            readOnly       = widgetReadOnly || task.isReadOnly();

        if (task) {

            if (me.taskForm) {
                // we repeat the editor readOnly state on the taskForm
                if (widgetReadOnly != me.taskForm.getReadOnly()) {
                    me.taskForm.setReadOnly(widgetReadOnly);
                } else {
                    me.taskForm.updateReadOnly();
                }
            }

            if (me.assignmentGrid) {
                me.assignmentGrid.setReadOnly(readOnly);
                me.assignmentGrid.down('toolbar').setVisible(!readOnly);
            }

            if (me.dependencyGrid) {
                me.dependencyGrid.down('toolbar').setVisible(!readOnly);
            }

            if (me.notesEditor) {
                me.notesEditor.setReadOnly(readOnly || !task.isEditable(task.noteField));
            }

            if (me.advancedForm) {
                // we repeat the editor readOnly state on the advancedForm
                if (widgetReadOnly != me.advancedForm.getReadOnly()) {
                    me.advancedForm.setReadOnly(widgetReadOnly);
                } else {
                    me.advancedForm.updateReadOnly();
                }
            }
        }
    },


    doValidate : function (invalidComponentsAccFn) {
        var result      = this.callParent(arguments);

        if (this.taskForm && !this.taskForm.isValid()) {
            result = false;
            invalidComponentsAccFn && invalidComponentsAccFn(this.getTabByComponent(this.taskForm), this.taskForm);
        }

        if (this.dependencyGrid && !this.dependencyGrid.isValid()) {
            result = false;
            invalidComponentsAccFn && invalidComponentsAccFn(this.getTabByComponent(this.dependencyGrid), this.dependencyGrid);
        }

        if (this.assignmentGrid && !this.assignmentGrid.isValid()) {
            result = false;
            invalidComponentsAccFn && invalidComponentsAccFn(this.getTabByComponent(this.assignmentGrid), this.assignmentGrid);
        }

        if (this.advancedForm && !this.advancedForm.isValid()) {
            result = false;
            invalidComponentsAccFn && invalidComponentsAccFn(this.getTabByComponent(this.advancedForm), this.advancedForm);
        }

        return result;
    },

    doUpdateTask : function() {
        this.taskForm       && this.taskForm.updateRecord();
        this.advancedForm   && this.advancedForm.updateRecord();
        this.notesEditor    && this.task.set(this.task.noteField, this.notesEditor.getValue());
        this.assignmentGrid && this.assignmentGrid.saveTaskAssignments();
        this.dependencyGrid && this.dependencyGrid.saveDependencies();
    },


    isDataChanged : function(changedComponentsAccFn) {
        var result    = this.callParent(arguments);

        if (this.taskForm && this.taskForm.isDataChanged()) {
            result = true;
            changedComponentsAccFn && changedComponentsAccFn(this.getTabByComponent(this.taskForm));
        }

        if (this.dependencyGrid && this.dependencyGrid.isDataChanged()) {
            result = true;
            changedComponentsAccFn && changedComponentsAccFn(this.getTabByComponent(this.dependencyGrid));
        }

        if (this.assignmentGrid && this.assignmentGrid.isDataChanged()) {
            result = true;
            changedComponentsAccFn && changedComponentsAccFn(this.getTabByComponent(this.assignmentGrid));
        }

        if (this.advancedForm && this.advancedForm.isDataChanged()) {
            result = true;
            changedComponentsAccFn && changedComponentsAccFn(this.getTabByComponent(this.advancedForm));
        }

        if (this.notesEditor && this.notesEditor.isDataChanged()) {
            result = true;
            changedComponentsAccFn && changedComponentsAccFn(this.getTabByComponent(this.notesEditor));
        }

        return result;
    }

});
