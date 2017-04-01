/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.column.AddNew
@extends Ext.grid.column.Column

A column allowing the user to add a new column to the Gantt chart. To include your own custom columns in this list,
just create an alias for them starting with 'widget.ganttcolumn.XXX'. Example:

    Ext.define('Your.column.DeadlineDate', {
        extend              : 'Ext.grid.column.Date',

        alias               : [
            'widget.ganttcolumn.mydeadlinedate'
        ],

        ...
    });

*/
Ext.define("Gnt.column.AddNew", {
    extend : "Ext.grid.column.Column",

    alias : [
        "widget.addnewcolumn",
        "widget.ganttcolumn.addnew"
    ],

    requires : [
        'Ext.form.field.ComboBox',
        'Ext.Editor'
    ],

    mixins : ['Gnt.mixin.Localizable'],

    /**
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

     - text  : 'Add new column...'
     */

    text           : '',
    width          : 100,
    resizable      : false,
    menuDisabled   : true,
    sortable       : false,
    draggable      : false,
    colEditor      : null,
    colEditorStore : null,
    layout         : 'fit',
    ignoreInAddMenu : true,
    ignoreInExport  : true,

    // Sencha flag for copy/paste operations
    ignoreExport    : true,

    /**
     * @cfg {Array} [columnList] An array of column definition objects. It should be a list containing data as seen below
     *
     *      [
     *          { clsName : 'Gnt.column.StartDate', text : 'Start Date', config : {...} },
     *          { clsName : 'Gnt.column.Duration', text : 'Duration', config : {...} },
     *          ...
     *      ]
     *
     * If not provided, a list containing all the columns from the `Gnt.column.*` namespace will be created.
     * This default list can also be retrieved by {@link #buildDefaultColumnList} static method:
     *
     *     // get default column list
     *     var columns  = Gnt.column.AddNew.buildDefaultColumnList();
     *
     *     // find Gnt.column.StartDate column entry
     *     var column   = Ext.Array.findBy(columns, function (c) { return c.clsName == 'Gnt.column.StartDate'; });
     *
     *     // set custom date format to Gnt.column.StartDate
     *     column.config    = { format : 'Y' };
     *     column.text      = 'Start year';
     *
     *     Ext.create('Gnt.panel.Gantt', {
     *         ...
     *         columns  : [
     *             ...
     *             {
     *                 xtype        : 'addnewcolumn',
     *                 // provide customized list
     *                 columnList   : columns
     *             }
     *         ]
     *     });
     *
     */
    columnList : null,

    initComponent : function () {

        this.addCls('gnt-addnewcolumn');

        this.items = this.getColEditor();

        this.text = this.text || this.L('text');
        
        this.callParent(arguments);
    },

    getGantt : function () {
        if (!this.gantt) {
            this.gantt = this.up('ganttpanel');
        }

        return this.gantt;
    },

    /**
     * @protected
     */
    getColEditor : function () {
        var me = this,
            editor;

        if (!me.colEditor) {
            editor = me.colEditor = new Ext.form.field.ComboBox({
                itemId         : 'addNewEditor',
                displayField   : 'text',
                valueField     : 'clsName',
                hideTrigger    : true,
                queryMode      : 'local',
                multiSelect    : false,
                value          : this.L('text'),
                listConfig     : {
                    itemId   : 'addNewEditorComboList',
                    minWidth : 150
                },
                store          : me.getColEditorStore(),
                pickerAlign    : 'tl-bl', // never risk that the editor appears above the column
                listeners      : {
                    focus  : me.onInputFocus,
                    blur   : me.onFieldBlur,
                    select : me.onSelect,
                    scope  : me
                }
            });

            if (Ext.isIE8m) {
                // Make IE8 play nice and have text vertically centered
                editor.on('resize', function(field, width, height, oldWidth, oldHeight) {
                    field.inputEl.setStyle('line-height', + height + 'px');
                    // Force repaint of element
                    field.setValue(field.getValue() + ' ');
                });
            }
        }

        return me.colEditor;
    },

    onFieldBlur : function(field, e) {
        var picker = field.getPicker();
        var pickerNode = picker.el && picker.el.dom;

        // Clicks on scrollbar in the picker should not trigger anything
        if (!e.relatedTarget || e.relatedTarget !== pickerNode) {
            this.resetField();
        }
    },

    resetField : function() {
        this.getColEditor().setValue(this.L('text'));
        this.getColEditor().collapse();
    },

    /**
     * @protected
     */
    getColEditorStore : function () {
        var me = this;

        if (!me.colEditorStore) {
            me.columnList = me.columnList || Gnt.column.AddNew.buildDefaultColumnList();

            me.colEditorStore = new Ext.data.Store({
                fields  : ['text', 'clsName', 'config'],
                data    : me.columnList,
                sorters : [{
                    property  : 'text',
                    direction : 'ASC'
                }]
            });
        }

        return me.colEditorStore;
    },

    /**
     * @private
     */
    onInputFocus : function (field) {
        field.setValue();
        field.expand();
    },

    /**
     * @private
     */
    onSelect : function (combo, records) {
        this.resetField();
        this.getColEditor().blur();
        this.addColumn(Ext.isArray(records) ? records[0] : records);
    },

    /**
     * @protected
     */
    addColumn : function (record) {
        var me      = this;
        var rec     = record;
        var owner   = me.up('headercontainer'); // HACK
        var text    = rec.get('text');
        var config  = Ext.apply({}, rec.get('config'));
        var clsName = rec.get('clsName') || config.xclass || 'Ext.grid.column.Column';

        Ext.require(clsName, function () {
            var cls = Ext.ClassManager.get(clsName);

            config.xclass = clsName;
            config.text   = text;

            var col = Ext.create(config);

            owner.insert(owner.items.indexOf(me), col);
        });
    },

    destroy : function () {
        this.colEditorStore && this.colEditorStore.destroy();

        this.callParent(arguments);
    },

    statics : {
        /**
         * Builds the default column list to show in the combo box picker. The list will contain all columns matching the "widget.ganttcolumn.XXX" alias.
         *
         * @return {Object[]} Returns array of objects having following properties:
         * @return {String} return.clsName  Column class name
         * @return {String} return.text     Column label
         * @return {String} return.config   (optional) Column instance configuration
         */
        buildDefaultColumnList : function () {
            var list = [];

            Ext.Array.each(Ext.ClassManager.getNamesByExpression('widget.ganttcolumn.*'), function (name) {
                var cls = Ext.ClassManager.get(name);

                if (cls && !cls.prototype.ignoreInAddMenu) {
                    list.push({
                        clsName : name,
                        text    : cls.prototype.localize ? cls.prototype.localize('text') : cls.prototype.text
                    });
                }
            });

            return list.sort(function (a, b) {
                return a.text > b.text ? 1 : -1;
            });
        }
    }
});
