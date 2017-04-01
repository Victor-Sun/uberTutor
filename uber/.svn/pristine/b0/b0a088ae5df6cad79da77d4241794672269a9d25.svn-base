/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

 @class Gnt.plugin.DependencyEditor
 @extends Ext.form.Panel

 {@img gantt/images/dependency-editor.png}

 A plugin (ptype = 'gantt_dependencyeditor') which shows the dependency editor panel, when a user double-clicks a dependency line or arrow.

 To customize the fields created by this plugin, override the `buildFields` method.

 You can add it to your gantt chart like this:

 var gantt = Ext.create('Gnt.panel.Gantt', {

        plugins             : [
            Ext.create("Gnt.plugin.DependencyEditor", {
                // default value
                hideOnBlur      : true
            })
        ],
        ...
    })


 */
Ext.define("Gnt.plugin.DependencyEditor", {
    extend        : "Ext.form.Panel",
    alias         : 'plugin.gantt_dependencyeditor',
    // ptype isn't filled automatically, because we do not extend AbstractPlugin
    ptype         : 'gantt_dependencyeditor',
    mixins        : ['Ext.AbstractPlugin', 'Gnt.mixin.Localizable'],

    requires : [
        'Ext.util.Filter',
        'Ext.form.field.Display',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Number',
        'Gnt.model.Dependency',
        'Ext.data.ArrayStore'
    ],

    /**
     * @cfg {Boolean} hideOnBlur True to hide this panel if a click is detected outside the panel (defaults to true)
     */
    hideOnBlur : true,

    /**
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

        - fromText         : 'From',
        - toText           : 'To',
        - typeText         : 'Type',
        - lagText          : 'Lag',
        - endToStartText   : 'Finish-To-Start',
        - startToStartText : 'Start-To-Start',
        - endToEndText     : 'Finish-To-Finish',
        - startToEndText   : 'Start-To-Finish',
        - okButtonText     : 'Ok',
        - cancelButtonText : 'Cancel',
        - deleteButtonText : 'Delete'
     */

    /**
     * @cfg {Boolean} showLag True to show the lag editor
     */
    showLag : false,

    /**
     * @cfg {String} triggerEvent
     * The event upon which the editor shall be shown. Defaults to 'dependencydblclick'.
     */
    triggerEvent : 'dependencydblclick',

    /**
     * @cfg {Boolean} constrain Pass `true` to enable the constraining - ie editor panel will not exceed the document edges. This option will disable the animation
     * during the expansion. Default value is `false`.
     */
    constrain   : true,

    lockableScope : 'top',
    // 1. We don't use header at all, 2. IE8 takes the use of a header personal and dies in Ext 4.2.1. http://www.sencha.com/forum/showthread.php?271770-4.2.1-getFramingInfoCls-broken-in-IE8
    header      : false,
    border      : false,
    frame       : true,
    labelWidth  : 60,
    floating    : true,
    hideMode    : 'offsets',
    bodyPadding : 10,

    initComponent : function () {
        this.buttons = this.hasOwnProperty('buttons') ? this.buttons : (this.buttons || [
            {
                text    : this.L('okButtonText'),
                itemId  : 'okbutton',
                scope   : this,
                handler : function () {
                    this.getForm().updateRecord(this.dependencyRecord);
                    this.collapse();
                }
            },
            {
                text    : this.L('cancelButtonText'),
                itemId  : 'cancelbutton',
                scope   : this,
                handler : function () {
                    this.collapse();
                }
            },
            {
                text    : this.L('deleteButtonText'),
                itemId  : 'deletebutton',
                scope   : this,
                handler : function () {
                    var dependencyStore = this.taskStore && this.taskStore.getDependencyStore();
                    dependencyStore.remove(this.dependencyRecord);
                    this.collapse();
                }
            }
        ]);

        this.callParent(arguments);

        this.saveButton = this.down('#okbutton');
        this.deleteButton = this.down('#deletebutton');

        this.addCls('sch-gantt-dependencyeditor');
    },

    getState : function () {
        if (this.rendered) {
            return this.callParent(arguments);
        }
    },

    init : function (cmp) {
        cmp.on(this.triggerEvent, this.onTriggerEvent, this);

        this.gantt     = cmp;
        this.taskStore = cmp.getTaskStore();

        // Add fields late, when we have access to taskStore
        this.add(this.buildFields());
    },

    renderAndCollapse : function () {
        this.render(Ext.getBody());

        // Collapse after render, otherwise rendering is messed up
        this.collapse(Ext.Component.DIRECTION_TOP, false);
        this.hide();

        if (this.hideOnBlur) {
            // Hide when clicking outside panel
            this.on({
                show : function () {
                    this.mon(Ext.getBody(), {
                        click : this.onMouseClick,
                        scope : this
                    });
                },

                hide : function () {
                    this.mun(Ext.getBody(), {
                        click : this.onMouseClick,
                        scope : this
                    });
                },

                delay : 50
            });
        }
    },

    /**
     * Expands the editor
     * @param {Gnt.model.Dependency} dependencyRecord The record to show in the editor panel
     * @param {Array} xy the coordinates where the window should be shown
     */
    show : function (dependencyRecord, xy) {
        this.dependencyRecord = dependencyRecord;

        // Load form panel fields
        this.getForm().loadRecord(dependencyRecord);
        this.fromLabel.setValue(Ext.String.htmlEncode(this.dependencyRecord.getSourceTask().getName()));
        this.toLabel.setValue(Ext.String.htmlEncode(this.dependencyRecord.getTargetTask().getName()));

        if (this.typeField) {
            var dependencyStore = this.taskStore && this.taskStore.getDependencyStore(),
                allowedTypes    = dependencyStore && dependencyStore.allowedDependencyTypes;

            // filter out disabled dependency types
            this.typeField.store.filter();

            // if number of allowed dependency types is less 2 we won't allow to edit this field
            this.typeField.setReadOnly(allowedTypes && allowedTypes.length < 2);
        }

        this.callParent([]);
        this.el.setXY(xy);

        this.expand(!this.constrain);

        if (this.constrain) {
            this.doConstrain(Ext.util.Region.getRegion(Ext.getBody()));
        }

        this.saveButton && this.saveButton.setVisible(!this.gantt.isReadOnly());
        this.deleteButton && this.deleteButton.setVisible(!this.gantt.isReadOnly());
    },


    /**
     * This method is being called during form initialization. It should return an array of fields, which will be assigned to the `items` property.
     * @return {Array}
     */
    buildFields : function () {
        var me              = this,
            dependencyStore = me.taskStore && me.taskStore.getDependencyStore();

        var fields = [
            me.fromLabel = new Ext.form.TextField({
                readOnly   : true,
                border     : false,
                fieldLabel : me.L('fromText'),
                cls        : 'sch-gantt-dependencyeditor-readonly'
            }),

            me.toLabel = new Ext.form.TextField({
                readOnly   : true,
                border     : false,
                fieldLabel : me.L('toText'),
                cls        : 'sch-gantt-dependencyeditor-readonly'
            }),

            me.typeField = me.buildTypeField()
        ];

        if (me.showLag) {
            fields.push(
                me.lagField = new Ext.form.NumberField({
                    name       : dependencyStore ? dependencyStore.model.prototype.lagField : Gnt.model.Dependency.prototype.lagField,
                    fieldLabel : me.L('lagText')
                })
            );
        }

        return fields;
    },

    onTriggerEvent : function (depView, record, e, t) {
        if (!this.rendered) this.renderAndCollapse();

        if (record !== this.dependencyRecord) {
            this.show(record, e.getXY());
        }
    },

    filterAllowedTypes : function (record) {
        var dependencyStore = this.taskStore && this.taskStore.getDependencyStore();

        if (!dependencyStore || !dependencyStore.allowedDependencyTypes) return true;

        var allowed = dependencyStore.allowedDependencyTypes;
        var depType = dependencyStore.model.Type;

        for (var i = 0, l = allowed.length; i < l; i++) {
            var type = depType[allowed[i]];
            if (record.getId() == type) return true;
        }

        return false;
    },

    buildTypeField : function () {
        var depClass = this.taskStore ? this.taskStore.getDependencyStore().model : Gnt.model.Dependency;
        var depType  = depClass.Type;

        this.typesFilter = new Ext.util.Filter({
            filterFn : this.filterAllowedTypes,
            scope    : this
        });

        var store = new Ext.data.ArrayStore({
            fields : [
                { name : 'id', type : 'int' },
                'text'
            ],
            data   : [
                [depType.EndToStart, this.L('endToStartText')],
                [depType.StartToStart, this.L('startToStartText')],
                [depType.EndToEnd, this.L('endToEndText')],
                [depType.StartToEnd, this.L('startToEndText')]
            ]
        });

        store.filter(this.typesFilter);

        return new Ext.form.field.ComboBox({
            name          : depClass.prototype.typeField,
            fieldLabel    : this.L('typeText'),
            triggerAction : 'all',
            queryMode     : 'local',
            editable      : false,
            valueField    : 'id',
            displayField  : 'text',
            store         : store
        });
    },

    onMouseClick : function (e) {
        if (
            this.collapsed || e.within(this.getEl()) ||
                // ignore the click on the menus and combo-boxes (which usually floats as the direct child of <body> and
                // leaks through the `e.within(this.getEl())` check
            e.getTarget('.' + Ext.baseCSSPrefix + 'layer') ||

                // if clicks should be ignored for any other element - it should have this class
            e.getTarget('.sch-ignore-click')
        ) {
            return;
        }

        this.collapse();
    },

    // Always hide drag proxy on collapse
    afterCollapse : function () {
        delete this.dependencyRecord;

        // Currently the header is kept even after collapse, so need to hide the form completely
        this.hide();

        this.callParent(arguments);

        if (this.hideOnBlur) {
            // Hide when clicking outside panel
            this.mun(Ext.getBody(), 'click', this.onMouseClick, this);
        }
    }
});
