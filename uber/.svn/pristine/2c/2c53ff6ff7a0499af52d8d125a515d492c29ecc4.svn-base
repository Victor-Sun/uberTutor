/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.widget.taskeditor.TaskForm
@extends Gnt.widget.taskeditor.BaseForm

{@img gantt/images/taskeditor-form.png}

This form is used to edit the task properties.
By default it supports editing of the following fields:

 - the name of the task (task title)
 - the start date of the task
 - the end date of the task
 - the task duration
 - the task effort
 - the current status of a task, expressed as the percentage completed
 - the baseline start date of the task (editing of this field is optional)
 - the baseline end date of the task (editing of this field is optional)
 - the baseline status of a task, expressed as the percentage completed (editing of this field is optional)
 - the calendar assigned to task
 - the scheduling mode for the task

* **Note:** However this standard set of fields can be easily overwritten (for more details check {@link #items}).

## Extending the default field set

The default field set can be overwritten using the {@link #items} config.
In case you want to keep the default fields and add some new custom fields, you can use the code below:

    // Extend the standard TaskForm class
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

    // create customized form
    var form = new MyTaskForm({...});

*/
Ext.define('Gnt.widget.taskeditor.TaskForm', {
    // This form by default contains various "standard" fields of the task
    // and it "knows" about their "applyChanges" methods (for our fields),
    // and about renamed field names
    // This form can be also used with any other set of fields, provided
    // as the "items" config

    extend                  : 'Gnt.widget.taskeditor.BaseForm',

    alias                   : 'widget.taskform',

    requires                : [
        'Gnt.model.Task',
        'Ext.form.FieldSet',
        'Ext.form.FieldContainer',
        'Ext.form.field.Text',
        'Ext.form.field.Date',
        'Ext.form.field.Checkbox',
        'Gnt.field.Percent',
        'Gnt.field.StartDate',
        'Gnt.field.EndDate',
        'Gnt.field.Duration',
        'Gnt.field.Effort',
        'Gnt.field.BaselineStartDate',
        'Gnt.field.BaselineEndDate'
    ],

    mixins                  : ['Gnt.mixin.Localizable'],

    alternateClassName      : ['Gnt.widget.TaskForm'],

    /**
     * @cfg {Object/Object[]} items A single item, or an array of child Components to be added to this container.
     *
     * For example:
     *
        var myForm  = new Gnt.widget.taskeditor.TaskForm({
            items       : [
                {
                    xtype       : 'calendarfield',
                    fieldLabel  : 'Calendar',
                    name        : 'CalendarId'
                },
                {
                    xtype       : 'displayfield',
                    fieldLabel  : "WBS",
                    name        : 'wbsCode'
                }
            ],
            task        : myTask,
            taskStore   : myTaskStore
        });


     *
     * **Note:** By default this form provide pre-configured set of fields. Using this option will overwrite that field set.
     */

    /**
     * @cfg {Boolean} [showGeneral=true] `true` to display general fields.
     */
    showGeneral             : true,
    /**
     * @cfg {Boolean} [showBaseline=true] `true` to display baseline fields.
     */
    showBaseline            : true,
    /**
     * @cfg {Boolean} [editBaseline=false] `true` to allow editing of baseline fields.
     */
    editBaseline            : false,

    /**
     * @cfg {Object} l10n
     *    A object, purposed for the class localization. Contains the following keys/values:
     *
     * @cfg {String} l10n.taskNameText            'Name'
     * @cfg {String} l10n.durationText            'Duration'
     * @cfg {String} l10n.datesText               'Dates'
     * @cfg {String} l10n.baselineText            'Baseline'
     * @cfg {String} l10n.startText               'Start'
     * @cfg {String} l10n.finishText              'Finish'
     * @cfg {String} l10n.percentDoneText         'Percent Complete'
     * @cfg {String} l10n.baselineStartText       'Start'
     * @cfg {String} l10n.baselineFinishText      'Finish'
     * @cfg {String} l10n.baselinePercentDoneText 'Percent Complete'
     * @cfg {String} l10n.effortText              'Effort'
     * @cfg {String} l10n.invalidEffortText       'Invalid effort value'
     */

    /**
     * @cfg {Object} taskNameConfig A config object to be applied to the `Name` field.
     */
    taskNameConfig          : null,

    /**
     * @cfg {Object} durationConfig A config object to be applied to the `Duration` field.
     */
    durationConfig          : null,

    /**
     * @cfg {Object} startConfig A config object to be applied to the `Start` field.
     */
    startConfig             : null,

    /**
     * @cfg {Object} finishConfig A config object to be applied to the `Finish` field.
     */
    finishConfig            : null,

    /**
     * @cfg {Object} percentDoneConfig A config object to be applied to the `Percent Complete` field.
     */
    percentDoneConfig       : null,

    /**
     * @cfg {Object} baselineStartConfig A config object to be applied to the `Start` field of the `Baseline` fields container.
     */
    baselineStartConfig     : null,

    /**
     * @cfg {Object} baselineFinishConfig A config object to be applied to the `Finish` field of the `Baseline` fields container.
     */
    baselineFinishConfig    : null,

    /**
     * @cfg {Object} baselinePercentDoneConfig A config object to be applied to the `Percent Complete` field of the `Baseline` fields container.
     */
    baselinePercentDoneConfig   : null,

    /**
     * @cfg {Object} effortConfig A config object to be applied to the `Effort` field.
     */
    effortConfig            : null,


    constructor : function (config) {
        config = config || {};

        this.showBaseline = config.showBaseline;
        this.editBaseline = config.editBaseline;

        var model =  config.taskStore ? config.taskStore.model.prototype : Gnt.model.Task.prototype;

        // default field names
        this.fieldNames = {
            baselineEndDateField        : model.baselineEndDateField,
            baselinePercentDoneField    : model.baselinePercentDoneField,
            baselineStartDateField      : model.baselineStartDateField,
            calendarIdField             : model.calendarIdField,
            readOnlyField               : model.readOnlyField,
            clsField                    : model.clsField,
            draggableField              : model.draggableField,
            durationField               : model.durationField,
            durationUnitField           : model.durationUnitField,
            effortField                 : model.effortField,
            effortUnitField             : model.effortUnitField,
            endDateField                : model.endDateField,
            manuallyScheduledField      : model.manuallyScheduledField,
            nameField                   : model.nameField,
            percentDoneField            : model.percentDoneField,
            resizableField              : model.resizableField,
            rollupField                 : model.rollupField,
            schedulingModeField         : model.schedulingModeField,
            startDateField              : model.startDateField,
            noteField                   : model.noteField,
            constraintTypeField         : model.constraintTypeField,
            constraintDateField         : model.constraintDateField
        };

        this.callParent(arguments);

        this.addBodyCls('gnt-taskeditor-taskform');
    },


    // Builds default set of form fields.
    buildFields : function () {
        var me      = this,
            f       = me.fieldNames;

        me.items = me.items || [];

        if (me.showGeneral) {
            me.items.push(
                me.initFieldDefinition({
                    xtype       : 'textfield',
                    fieldLabel  : me.L('taskNameText'),
                    name        : f.nameField,
                    labelWidth  : 110,
                    allowBlank  : false,
                    flex        : 1,
                    value       : me.getTaskFieldValue(f.nameField)
                }, me.nameConfig),
                {
                    xtype       : 'fieldcontainer',
                    layout      : 'hbox',
                    defaults    : {
                        labelWidth  : 110,
                        allowBlank  : false
                    },
                    items       : [
                        me.initFieldDefinition({
                            xtype       : 'percentfield',
                            fieldLabel  : me.L('percentDoneText'),
                            name        : f.percentDoneField,
                            margin      : '0 0 0 8',
                            //width       : 200,
                            flex        : 1,
                            value       : me.getTaskFieldValue(f.percentDoneField)
                        }, me.percentDoneConfig),

                        me.initFieldDefinition({
                            xtype       : 'durationfield',
                            fieldLabel  : me.L('durationText'),
                            name        : f.durationField,
                            //width       : 200,
                            flex        : 1,
                            value       : me.getTaskFieldValue(f.durationField)
                        }, me.durationConfig)
                    ]
                },
                {
                    xtype               : 'fieldset',
                    title               : me.L('datesText'),
                    layout              : 'hbox',
                    items               : [
                        {
                            xtype       : 'container',
                            layout      : 'anchor',
                            flex        : 1,
                            margin      : '0 8 0 0',
                            defaults    : {
                                labelWidth  : 110
                            },

                            items : [
                                me.initFieldDefinition({
                                    xtype       : 'startdatefield',
                                    fieldLabel  : me.L('startText'),
                                    width       : '100%',
                                    allowBlank  : false,
                                    name        : f.startDateField,
                                    value       : me.getTaskFieldValue(f.startDateField)
                                }, me.startConfig),

                                me.initFieldDefinition({
                                    xtype       : 'effortfield',
                                    fieldLabel  : me.L('effortText'),
                                    name        : f.effortField,
                                    invalidText : me.L('invalidEffortText'),
                                    width       : '100%',
                                    allowBlank  : true,
                                    value       : me.getTaskFieldValue(f.effortField)
                                }, me.effortConfig)
                            ]
                        },

                        me.initFieldDefinition({
                            xtype       : 'enddatefield',
                            fieldLabel  : me.L('finishText'),
                            flex        : 1,
                            labelWidth  : 110,
                            allowBlank  : false,
                            name        : f.endDateField,
                            value       : me.getTaskFieldValue(f.endDateField)
                        }, me.finishConfig)
                    ]
                });
        }

        if (me.showBaseline) {

            me.items.push({
                xtype               : 'fieldset',
                title               : me.L('baselineText'),
                layout              : 'hbox',

                items               : [
                    {
                        xtype       : 'container',
                        layout      : 'anchor',
                        flex        : 1,
                        margin      : '0 8 0 0',
                        defaults    : {
                            labelWidth  : 110,
                            cls         : 'gnt-baselinefield'
                        },

                        items : [
                            me.initFieldDefinition({
                                xtype           : 'baselinestartdatefield',
                                fieldLabel      : me.L('baselineStartText'),
                                name            : f.baselineStartDateField,
                                value           : me.getTaskFieldValue(f.baselineStartDateField),
                                width           : '100%',
                                readOnly        : !me.editBaseline,
                                // Setting forceReadOnly to true disables TaskField and BaseForm logic that switches the field readOnly state
                                // depending on the task being edited isEditable() result or the form readOnly state
                                forceReadOnly   : !me.editBaseline
                            }, me.baselineStartConfig),

                            me.initFieldDefinition({
                                xtype         : 'percentfield',
                                fieldLabel    : me.L('baselinePercentDoneText'),
                                width         : '100%',
                                name          : f.baselinePercentDoneField,
                                value         : me.getTaskFieldValue(f.baselinePercentDoneField),
                                readOnly      : !me.editBaseline,
                                forceReadOnly : !me.editBaseline
                            }, me.baselinePercentDoneConfig)
                        ]
                    },

                    me.initFieldDefinition({
                        xtype           : 'baselineenddatefield',
                        fieldLabel      : me.L('baselineFinishText'),
                        name            : f.baselineEndDateField,
                        cls             : 'gnt-baselinefield',
                        flex            : 1,
                        labelWidth      : 110,
                        value           : me.getTaskFieldValue(f.baselineEndDateField),
                        readOnly        : !me.editBaseline,
                        forceReadOnly   : !me.editBaseline
                    }, me.baselineFinishConfig)
                ]
            });
        }
    },


    updateRecordWithFieldValue : function (task, field) {
        var me = this;

        // Constraint related fields require different approach
        if (field.name != me.fieldNames.constraintTypeField && field.name != me.fieldNames.constraintDateField) {
            return this.callParent(arguments);
        }
    },


    updateRecordFn : function (task) {
        var me                  = this,
            fieldNames          = me.fieldNames,
            form                = me.getForm(),
            constraintTypeField = form.findField(fieldNames.constraintTypeField),
            constraintDateField = form.findField(fieldNames.constraintDateField);

        task.beginEdit();

        this.callParent(arguments);

        // apply constraints if corresponding fields was shown
        // and task has constraint mixin mixed
        if (constraintTypeField && constraintDateField && task.setConstraint) {
            task.setConstraint(
                constraintTypeField.getValue(),
                constraintDateField.getValue()
            );
        }

        task.endEdit();
    },


    buildTaskBuffer : function (task) {
        this.callParent(arguments);

        // "isEditable" result depends on the task parent nodes readonly state
        // so if some of the task parents is readonly
        // we simply return true
        if (!task.getReadOnly() && task.isReadOnly()) {
            this.taskBuffer.isReadOnly = function () { return true; };
        }

    }
});
