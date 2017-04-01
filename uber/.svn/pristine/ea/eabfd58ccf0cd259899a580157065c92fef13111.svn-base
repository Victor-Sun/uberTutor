/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.column.StartDate
@extends Ext.grid.column.Date

A Column representing a `StartDate` field of a task. The column is editable, however to enable the editing you will need to add a
{@link Sch.plugin.TreeCellEditing} plugin to your gantt panel. The overall setup will look like this:

    var gantt = Ext.create('Gnt.panel.Gantt', {
        height  : 600,
        width   : 1000,

        columns : [
            ...
            {
                xtype : 'startdatecolumn',
                width : 80
            }
            ...
        ],

        plugins : [
            Ext.create('Sch.plugin.TreeCellEditing', {
                clicksToEdit : 1
            })
        ],
        ...
    })

Note, that this column will provide only a day-level editor (using a subclassed Ext JS DateField). If you need a more precise editing (ie also specify
the start hour/minute) you will need to provide your own field which should subclass {@link Gnt.field.StartDate}. See [forum thread][1] for more information.

Also note, that this class inherits from Ext.grid.column.Date and supports its configuration options, notably the "format" option.

[1]: http://bryntum.com/forum/viewtopic.php?f=16&t=2277&start=10#p13964

*/
Ext.define('Gnt.column.StartDate', {
    extend              : 'Ext.grid.column.Date',
    alias               : [
        'widget.startdatecolumn',
        'widget.ganttcolumn.startdate'
    ],

    requires            : ['Gnt.field.StartDate'],

    mixins              : ['Gnt.column.mixin.TaskFieldColumn'],

    /**
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

            - text : 'Start'
     */

    /**
     * @cfg {Number} width A width of the column, default value is 100
     */
    width               : 100,

    /**
     * @cfg {String} align An align of the text in the column, default value is 'left'
     */
    align               : 'left',

    /**
     * @cfg {String} editorFormat A date format to be used when editing the value of the column. By default it is the same as `format` configuration
     * option of the column itself.
     */
    editorFormat        : null,

    /**
     * @cfg {Boolean} adjustMilestones When set to `true`, the start/end dates of the milestones will be adjusted -1 day *during rendering and editing*. The task model will still hold unmodified date.
     */
    adjustMilestones    : true,

    /**
     * @cfg {Boolean} keepDuration Pass `true` to keep the duration of the task ("move" the task), `false` to change the duration ("resize" the task).
     */
    keepDuration        : true,

    fieldProperty       : 'startDateField',

    fieldConfigs        : [ 'instantUpdate', 'adjustMilestones', 'keepDuration', 'fieldProperty' ],

    editor              : 'startdatefield',

    defaultEditor       : 'startdatefield',

    initComponent : function () {
        this.initTaskFieldColumn({
            format      : this.editorFormat || this.format || Ext.Date.defaultFormat
        });

        this.callParent(arguments);
    },


    getValueToRender : function (value, meta, task) {
        return value && Ext.Date.format(this.field.valueToVisible(value, task), this.format) || '';
    },

    /**
     * Will validate and insert previously prepared assignment data
     * @param {Object[]} data Data to insert, should be valid input for store.add method
     * @param {Gnt.model.Task} task Record being populated with this data
     */
    putRawData : function (data, task) {
        if (data && !(data instanceof Date)) {
            data = Ext.Date.parse(data, this.format);
        }

        task.setStartDate(data);
    }
});
