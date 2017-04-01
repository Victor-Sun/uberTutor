/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 @class Gnt.widget.taskeditor.ProjectForm
 @extends Gnt.widget.taskeditor.BaseForm

 This form is used to edit the project properties.
 By default it supports editing of the following fields:

 - the name of the project (project title)
 - the start date of the project
 - the end date of the project
 - the calendar assigned to the project
 - the dependency status, whether the project allows external tasks dependencies

 * **Note:** However this standard set of fields can be easily overwritten (for more details check {@link #items}).

 ## Extending the default field set

 The default field set can be overwritten using the {@link #items} config.
 In case you want to keep the default fields and add some new custom fields, you can use the code below:

            // Extend the standard ProjectForm class
            Ext.define('MyProjectForm', {
                    extend : 'Gnt.widget.taskeditor.ProjectForm',

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
            var form = new MyProjectForm({...});

 */
Ext.define('Gnt.widget.taskeditor.ProjectForm', {
    // This form by default contains various "standard" fields of the project
    // and it "knows" about their "applyChanges" methods (for our fields),
    // and about renamed field names
    // This form can be also used with any other set of fields, provided
    // as the "items" config

    extend                  : 'Gnt.widget.taskeditor.BaseForm',

    alias                   : 'widget.projectform',

    requires                : [
        'Gnt.model.Project',
        'Ext.form.FieldSet',
        'Ext.form.FieldContainer',
        'Ext.form.field.Text',
        'Gnt.field.Calendar',
        'Gnt.field.StartDate',
        'Gnt.field.EndDate',
        'Gnt.field.ReadOnly',
        'Ext.form.field.Checkbox'
    ],

    mixins                  : ['Gnt.mixin.Localizable'],

    alternateClassName      : ['Gnt.widget.ProjectForm'],

    /**
     * @cfg {Object/Object[]} items A single item, or an array of child Components to be added to this container.
     *
     * **Note:** By default this form provide pre-configured set of fields. Using this option will overwrite that field set.
     */

    /**
     * @cfg {Boolean} [showCalendar=true] `true` to show calendarField.
     */
    showCalendar            : false,

    /**
     * @cfg {Object} l10n
     *    A object, purposed for the class localization. Contains the following keys/values:
     *
     * @cfg {String} l10n.nameText                'Name'
     * @cfg {String} l10n.datesText               'Dates'
     * @cfg {String} l10n.startText               'Start'
     * @cfg {String} l10n.finishText              'Finish'
     * @cfg {String} l10n.calendarText            'Calendar'
     * @cfg {String} l10n.allowDependenciesText   'AllowDependencies'
     * @cfg {String} l10n.readOnlyText            'ReadOnly'
     */

    /**
     * @cfg {Object} nameConfig A config object to be applied to the `Name` field.
     */
    nameConfig              : null,

    /*
     * @cfg {Object} readOnlyConfig A config object to be applied to the `ReadOnly` field.
     */
    readOnlyConfig          : null,

    /**
     * @cfg {Object} allowDependenciesConfig A config object to be applied to the `AllowDependencies` field.
     */
    allowDependenciesConfig : null,

    /**
     * @cfg {Object} startConfig A config object to be applied to the `Start` field.
     */
    startConfig             : null,

    /**
     * @cfg {Object} finishConfig A config object to be applied to the `Finish` field.
     */
    finishConfig            : null,

    /**
     * @cfg {Object} calendarConfig A config object to be applied to the `Calendar` field.
     */
    calendarConfig          : null,


    constructor : function(config) {
        config      = config || {};

        var model   = config.taskStore && config.taskStore.projectModel && config.taskStore.projectModel.prototype || Gnt.model.Project.prototype;

        // default field names
        this.fieldNames = {
            calendarIdField         : model.calendarIdField,
            readOnlyField           : model.readOnlyField,
            allowDependenciesField  : model.allowDependenciesField,
            startDateField          : model.startDateField,
            endDateField            : model.endDateField,
            nameField               : model.nameField,
            descriptionField        : model.descriptionField
        };

        this.callParent(arguments);

        this.addBodyCls('gnt-projecteditor-projectform');
    },

    // Builds default set of form fields.
    buildFields : function () {
        var me      = this,
            f       = me.fieldNames;

        me.items    = me.items || [];

        me.items.push(
            {
                xtype       : 'fieldset',
                title       : me.L('projectText'),
                layout      : 'vbox',
                defaults    : {
                    allowBlank  : false
                },
                items       : [
                    me.initFieldDefinition({
                        xtype       : 'textfield',
                        fieldLabel  : me.L('nameText'),
                        name        : f.nameField,
                        labelWidth  : 110,
                        flex        : 1,
                        value       : me.getTaskFieldValue(f.nameField)
                    }, me.nameConfig),

                    me.initFieldDefinition({
                        xtype       : 'readonlyfield',
                        fieldLabel  : me.L('readOnlyText'),
                        name        : f.readOnlyField,
                        labelWidth  : 110,
                        flex        : 1,
                        value       : me.getTaskFieldValue(f.readOnlyField)
                    }, me.readOnlyConfig),

                    me.initFieldDefinition({
                        xtype       : 'checkboxfield',
                        fieldLabel  : me.L('allowDependenciesText'),
                        name        : f.allowDependenciesField,
                        labelWidth  : 110,
                        flex        : 1,
                        value       : me.getTaskFieldValue(f.allowDependenciesField)
                    }, me.allowDependenciesConfig)
                ]
            },
            me.initFieldDefinition({
                xtype               : 'fieldset',
                title               : me.L('datesText'),
                layout              : 'hbox',
                defaults            : {
                    labelWidth  : 110,
                    allowBlank  : false,
                    margin      : '5 5 5 0'
                },
                items               : [
                    me.initFieldDefinition({
                        xtype       : 'startdatefield',
                        fieldLabel  : me.L('startText'),
                        width       : 260,
                        name        : f.startDateField,
                        value       : me.getTaskFieldValue(f.startDateField)
                    }, me.startConfig),

                    me.initFieldDefinition({
                        xtype       : 'enddatefield',
                        fieldLabel  : me.L('finishText'),
                        flex        : 1,
                        labelWidth  : 110,
                        name        : f.endDateField,
                        value       : me.getTaskFieldValue(f.endDateField)
                    }, me.finishConfig)
                ]
            })
        );

        if (me.showCalendar) {
            me.items.push({
                xtype       : 'fieldset',
                layout      : 'hbox',
                defaults    : {
                    labelWidth  : 110,
                    allowBlank  : false,
                    margin      : '5 0 5 0'
                },
                items       : [
                    me.initFieldDefinition({
                        xtype       : 'calendarfield',
                        fieldLabel  : this.L('calendarText'),
                        width       : 260,
                        name        : f.calendarIdField,
                        value       : me.getTaskFieldValue(f.calendarIdField)
                    }, me.calendarConfig)
                ]
            });
        }
    }

});
