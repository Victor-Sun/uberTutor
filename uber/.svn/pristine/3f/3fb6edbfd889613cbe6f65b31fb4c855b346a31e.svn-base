/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 @class Gnt.widget.taskeditor.BaseForm
 @extends Ext.form.Panel

 A mixin with common functionality for the taskform {@link Gnt.widget.taskeditor.TaskEditor}.

 */
Ext.define('Gnt.widget.taskeditor.BaseForm', {

    extend                  : 'Ext.form.Panel',

    /**
     * @cfg {Boolean} highlightTaskUpdates `true` to highlight fields updates initiated by changes of another fields.
     */
    highlightTaskUpdates    : true,

    /**
     * @cfg {Gnt.model.Task} task A task to load to the form.
     */
    /**
     * @property {Gnt.model.Task} task The task loaded in the form.
     */
    task                    : null,

    /**
     * @cfg {Gnt.model.Task} taskBuffer A task used to keep intermediate values of fields implemented by {@link Gnt.field.mixin.TaskField} mixin.
     */
    /**
     * @property {Gnt.model.Task} taskBuffer A task used to keep intermediate values of fields implemented by {@link Gnt.field.mixin.TaskField} mixin.
     */
    taskBuffer              : null,

    /**
     * @cfg {Gnt.data.TaskStore} taskStore A store with tasks.
     *
     * **Note:** This is required option if task being loaded isn't yet belong to any task store.
     */
    taskStore               : null,

    taskListeners           : null,

    autoScroll              : true,

    defaults                : {
        labelWidth : 110
    },

    bodyPadding             : 5,

    border                  : false,
    layout                  : 'anchor',
    defaultType             : 'textfield',

    initComponent : function () {
        // if task provided on construction step
        if (this.task) {
            // get actual field names from task
            this.fieldNames = this.getFieldNames(this.task);
        }

        // if no fields definition provided we make default fields set
        if (!this.items) {
            this.buildFields();
        }

        this.callParent(arguments);

        if (this.task) {
            this.loadRecord(this.task, this.taskBuffer);
        }
    },

    getFieldNames : function (task) {
        if (!task) return;

        var result = {};

        for (var i in this.fieldNames) {
            result[i] = task[i];
        }

        return result;
    },

    // Renames form fields according to provided task model.
    renameFields : function (task) {
        var newFields   = this.getFieldNames(task);
        if (!newFields) return;

        var form    = this.getForm(),
            changed = false,
            field;

        for (var i in this.fieldNames) {
            field = form.findField(this.fieldNames[i]);

            // check if field name should be changed
            if (field && newFields[i] && newFields[i] != field.name) {
                changed     = true;
                field.name  = newFields[i];
            }
        }

        // if something was changed
        if (changed) {
            // keep new fields' names dictionary
            this.fieldNames = newFields;
        }
    },

    /**
     * Suppress task updates invoking by form fields. Calls setSuppressTaskUpdate() of each field that supports this method.
     * @param {Boolean} state Suppress or allow task updating.
     */
    setSuppressTaskUpdate : function (state) {
        var fields  = this.getForm().getFields();

        fields.each(function (field) {
            // if field contains setTask() method
            field.setSuppressTaskUpdate && field.setSuppressTaskUpdate(state);
        });
    },

    isDataChanged : function() {
        return this.isDirty();
    },


    buildTaskBuffer : function (task) {
        var me  = this;

        me.taskBuffer             = task.copy();
        // since copy() doesn't copy taskStore let`s copy it ourself
        me.taskBuffer.taskStore   = task.taskStore;
    },


    /**
     * Loads an Gnt.model.Task into this form.
     * @param {Gnt.model.Task} task The record to edit.
     * @param {Gnt.model.Task} [taskBuffer] The record to be used as a buffer to keep changed values of fields which implement {@link Gnt.field.mixin.TaskField}
     * mixin interface. This parameter can be used in case when you want to implement two form instances instantly
     * reflecting changes of each other:
     *
     *      // create 1st TaskForm instance
     *      var taskForm = Ext.create('Gnt.widget.taskeditor.TaskForm');
     *      // load record into 1st form
     *      taskForm.loadRecord(someTask);
     *
     *      // create 2nd TaskForm instance
     *      var anotherForm = Ext.create('Gnt.widget.taskeditor.TaskForm');
     *      // load the same record into 2nd form
     *      // and set to share taskBuffer with 1st form to immediately refect changes of each other
     *      anotherForm.loadRecord(someTask, taskForm.taskBuffer);
     */
    loadRecord : function (task, taskBuffer) {
        var me          = this;

        // if new or another task loading
        if (task && task !== me.task) {
            // let's rename form fields according to task model
            me.renameFields(task);
        }

        me.task         = task;
        me.taskBuffer   = taskBuffer;

        // if no pre-created taskBuffer provided, let`s create it
        if (!me.taskBuffer) {
            me.buildTaskBuffer(task);
        }

        // destroy previous task listeners if any
        me.taskListeners && me.taskListeners.destroy();

        // listen to 'taskupdated' event and update fields "readonly" state
        me.taskListeners = me.mon(me.taskBuffer, {
            taskupdated : me.onTaskUpdated,
            destroyable : true,
            scope       : me
        });

        var form        = me.getForm();

        // following code is modified implementation
        // of Ext.form.Basic setValues() method
        form._record    = task;

        this.suspendLayouts();

        var data = task.getData();

        form.getFields().each(function (field) {
            if (field.getName() in data) {
                // if field contains setTask() method
                // we gonna use it since setTask() execute setValue()
                if (field.setTask) {
                    field.setTask(me.taskBuffer);
                } else {
                    // set field value
                    field.setValue(data[field.getName()]);
                }

                // and set its readOnly state depending on gantt readOnly state and task.isEditable() result
                me.updateFieldReadOnly(field);

                if (form.trackResetOnLoad) {
                    field.resetOriginalValue();
                }
            }
        });

        this.resumeLayouts(true);

        this.fireEvent('afterloadrecord', this, task);
    },


    updateFieldReadOnly : function (field) {
        var me = this;

        if (!field.disabled) {

            // Having forceReadOnly=true on a field disables TaskField and BaseForm logic that switches the field readOnly state
            // depending on the task being edited isEditable() result or the form readOnly state
            if (!field.forceReadOnly) {

                var isTaskField = field.isTaskField;

                // if the form is readOnly
                if (me.getReadOnly()) {
                    // we set the field readOnly too
                    field.setReadOnly(true);
                    // if it's a TaskField we suspend its own readOnly mechanism to prevent it from enabling the field back
                    isTaskField && field.suspendReadOnlyUpdate();

                // if the form is editable
                } else {
                    // if it's not a TaskField we take the task.isEditable() result into account
                    if (!isTaskField) {
                        var isEditable = me.taskBuffer.isEditable(field.name);

                        if (this.editable === false) {
                            if (isEditable && field.inputEl) {
                                field.inputEl.dom.readOnly = true;
                            }
                        }

                        field.setReadOnly(!isEditable);

                    } else {
                        field.resumeReadOnlyUpdate();
                        field.updateReadOnly(me.taskBuffer);
                    }
                }

            }
        }
    },


    // Updates readonly state of all the form fields
    updateReadOnly : function () {
        var me      = this,
            form    = me.getForm(),
            data    = me.taskBuffer.getData();

        form.getFields().each(function (field) {
            if (field.getName() in data) me.updateFieldReadOnly(field);
        });
    },


    /**
     * Applies the values from this form into the passed {@link Gnt.model.Task} object.
     * If the task is not specified, it will attempt to update (if it exists) the record provided to {@link #loadRecord}.
     * @param {Gnt.model.Task} [task] The record to apply change to.
     */
    updateRecord : function (task) {
        var me = this;

        task = task || me.task;

        if (task && me.fireEvent('beforeupdaterecord', me, task, me.updateRecordFn) !== false) {
            me.setSuppressTaskUpdate(true);

            me.updateRecordFn.call(me, task);

            me.setSuppressTaskUpdate(false);
            me.fireEvent('afterupdaterecord', me, task);
            return true;
        }

        return false;
    },


    updateRecordWithFieldValue : function (task, field) {
        var modelField = task.getField(field.name);

        // if the field has applyChanges() method
        // we use it to apply changes to the task
        if (field.applyChanges) {
            field.applyChanges(task);

        // if there is a matching model field and the form field is supposed to be submittable (not displayfield or smth)
        } else if (modelField && field.name in this.getForm().getFieldValues()) {
            task.set(field.name, field.getValue());
        }
    },


    /**
     * A function that iterates the form fields and applies to changes to it. Override this function for custom logic.
     * @param task
     */
    updateRecordFn : function (task) {
        var me      = this;

        task.beginEdit();

        me.getForm().getFields().each(function (field) {
            me.updateRecordWithFieldValue(task, field);
        });

        task.endEdit();
    },


    // Applies "task", "taskStore", "highlightTaskUpdates" and "readOnly" configs to a field
    initFieldDefinition : function (field, cfg) {
        var me              = this;

        var commonParams    = {
            taskStore               : me.taskStore,
            task                    : me.task,
            highlightTaskUpdates    : me.highlightTaskUpdates
        };

        // if field isn't already read only then let's take into account Task.isEditable() result
        if (!field.readOnly && me.task) {
            commonParams.readOnly   = !me.task.isEditable(field.name);
        }

        return Ext.apply(field, commonParams, cfg);
    },


    // Gets the task field value
    getTaskFieldValue : function (field) {
        var me      = this,
            task    = this.task;

        return task ? task.get(me.fieldNames[field]) : '';
    },


    onTaskUpdated : function (task, field) {
        // let's update fields "readonly" status after task data has been modified
        this.updateReadOnly();
    },


    getReadOnly : function () {
        return this.readOnly;
    },


    setReadOnly : function (readOnly) {
        this.readOnly = readOnly;

        this.updateReadOnly();
    }

});
