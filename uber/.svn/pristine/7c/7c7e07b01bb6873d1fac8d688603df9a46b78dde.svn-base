/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 *
 * @class Gnt.column.ConstraintDate
 * @extends Ext.grid.column.Date
 *
 * A Column displaying a task's constraint date. The column is editable when adding a
 * `Sch.plugin.TreeCellEditing` plugin to your Gantt panel. The overall setup will look like this:
 *
 *
 *     var gantt = Ext.create('Gnt.panel.Gantt', {
 *         height      : 600,
 *         width       : 1000,
 *
 *         columns         : [
 *             ...
 *             {
 *                 xtype       : 'constraintdatecolumn',
 *                 width       : 80
 *             }
 *             ...
 *         ],
 *         ...
 *     })
 *
 * Note that this class inherit from {@link Ext.grid.column.Date} and supports its configuration options, notably the "format".
*/
Ext.define('Gnt.column.ConstraintDate', {
    extend              : 'Ext.grid.column.Date',

    alias               : [
        'widget.constraintdatecolumn',
        'widget.ganttcolumn.constraintdate'
    ],

    requires            : ['Gnt.field.ConstraintDate'],
    mixins              : ['Gnt.column.mixin.TaskFieldColumn'],

    /**
     * @cfg {string} text The text to show in the column header, defaults to `Mode`
     * @deprecated Please use {@link #l10n l10n} instead.
     */
    /**
     * @cfg {Object} l10n A object, purposed for the class localization.
     * @cfg {String} l10n.text Column title
     */

    /**
     * @cfg {Number} width The width of the column.
     */
    width               : 100,

    /**
     * @cfg {String} align The alignment of the text in the column.
     */
    align               : 'left',

    // Need to properly obtain the data index if none is given
    fieldProperty       : 'constraintDateField',

    editor              : 'constraintdatefield',

    defaultEditor       : 'constraintdatefield',

    initComponent : function () {
        this.initTaskFieldColumn({
            format      : this.editorFormat || this.format || Ext.Date.defaultFormat,
            taskField   : this.fieldProperty
        });

        this.callParent(arguments);
    },

    getValueToRender : function (value, meta, task) {
        return value && Ext.Date.format(this.field.valueToVisible(value, task), this.format) || '';
    }
});
