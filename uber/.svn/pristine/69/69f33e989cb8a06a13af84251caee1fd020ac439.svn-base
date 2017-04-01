/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.column.mixin.TaskFieldColumn
This class implement common logic for fields that have a field mixed with {@link Gnt.field.mixin.TaskField} class as an editor.
Also it makes the column localizable by mixing it with {@link Gnt.mixin.Localizable} class.
*/
Ext.define('Gnt.column.mixin.TaskFieldColumn', {

    extend              : 'Ext.Mixin',

    mixins              : [
        'Gnt.mixin.Localizable'
    ],

    /**
     * @cfg {Boolean} instantUpdate Set to `true` to instantly apply any changes in the field to the task.
     * This option is just translated to the {@link Gnt.field.mixin.TaskField#instantUpdate} config option.
     */
    instantUpdate       : false,

    /**
     * @property {Ext.form.field.Field} Reference to the field used by the editor
     */
    field               : null,

    fieldProperty       : '',

    fieldConfigs        : 'instantUpdate,fieldProperty',

    defaultEditor       : 'textfield',

    useRenderer         : true,
    
    mixinConfig         : {

        after           : {
            initComponent   : 'afterInitComponent',
            onRender        : '_beforeRender'
        },

        // "afterIf" adds a post-action exactly like normal "after" does
        // except in case the method being extended doesn't exist it adds it
        // So for example having this config:
        //      afterIf        : {
        //          applyColumnCls : 'applyColumnCls'
        //      }
        // Will result appending mixin's applyColumnCls code after the existing applyColumnCls method code
        // ..or creating a new applyColumnCls method using mixin's applyColumnCls method
        afterIf        : {
            applyColumnCls  : 'applyColumnCls'
        }
    },

    _beforeRender : function() {
        // Save original state so we can enable/disable this when needed
        var ed = this.getEditor && this.getEditor();

        if (ed && ed.setInstantUpdate) {
            ed.originalInstantUpdate = ed.instantUpdate;
            ed.setInstantUpdate(false);
        }
    },

    initTaskFieldColumn : function (editorCfg) {
        this.text       = this.config.text || this.L('text');

        this.initColumnEditor(editorCfg);

        this.scope     = this.scope    || this;

        if (this.useRenderer) {
            this.renderer  = this.renderer || this.taskFieldRenderer;
        }

        this.on('added', this.onColumnAdded, this);
    },


    applyColumnCls : function (value, meta, task) {
        if (!task.isEditable(this.dataIndex)) {
            meta.tdCls      = (meta.tdCls || '') + ' sch-column-readonly';
        }
    },


    afterInitComponent : function () {
        // Make sure Ext 'understands' this column has its own renderer which makes sure this column is always updated
        // if any task field is changed
        this.hasCustomRenderer  = true;
    },


    initColumnEditor : function (editorCfg) {
        var editor = this.editor;

        // if editor provided
        if (editor) {
            // xtype provided
            if ('string' == typeof editor) {
                editor  = { xtype : editor };
            }

            // if it's not a made instance yet
            if (!editor.isInstance) {

                if (!editor.xtype && !editor.xclass) {
                    editor.xtype = this.defaultEditor;
                }

                // relay configs listed in "fieldConfigs" to the editor
                var cfg     = Ext.copyTo(Ext.apply({}, editorCfg), this, this.fieldConfigs, true);

                this.editor = Ext.ComponentManager.create(Ext.apply(cfg, editor));
            }

            this.field    = this.editor;
        }

    },


    onColumnAdded : function() {
        var gantt        = this.up('[taskStore]');
        var taskStore    = gantt.taskStore;

        if (!this.dataIndex) {
            this.dataIndex = taskStore.model.prototype[ this.fieldProperty ];
        }

        // if the column wants to track the gantt readonly state
        if (this.onReadOnlySet) {
            this.mon(gantt, 'setreadonly', this.onReadOnlySet, this);
        }
    },


    getValueToRender : function (value, meta, task) {
        var field   = this.field;

        return field && field.valueToVisible && field.valueToVisible(value, task) || value;
    },


    taskFieldRenderer : function (value, meta, task) {
        var result  = Ext.util.Format.htmlEncode( this.getValueToRender.apply(this, arguments) );

        this.applyColumnCls(value, meta, task);

        return result;
    },


    afterClassMixedIn : function (cls) {
        var mixin       = this.prototype,
            mixinConfig = mixin.mixinConfig,
            befores     = mixinConfig && mixinConfig.beforeIf,
            afters      = mixinConfig && mixinConfig.afterIf;

        befores && Ext.Object.each(befores, function (key, value) {
            if (key in cls.prototype) {

                cls.addMember(key, function () {
                    if (mixin[value].apply(this, arguments) !== false) {
                        return this.callParent(arguments);
                    }
                });

            } else {

                cls.addMember(key, function () {
                    mixin[value].apply(this, arguments);
                });

            }
        });

        afters && Ext.Object.each(afters, function (key, value) {
            if (key in cls.prototype) {

                cls.addMember(key, function () {
                    this.callParent(arguments);
                    mixin[value].apply(this, arguments);
                });

            } else {

                cls.addMember(key, function () {
                    mixin[value].apply(this, arguments);
                });

            }
        });
    }
});
