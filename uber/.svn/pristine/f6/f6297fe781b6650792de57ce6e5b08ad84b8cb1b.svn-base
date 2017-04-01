/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.column.EndDate
@extends Ext.grid.column.Date

A Column showing the `EndDate` field of the tasks. The column is editable when adding a
`Sch.plugin.TreeCellEditing` plugin to your gantt panel. The overall setup will look like this:

    var gantt = Ext.create('Gnt.panel.Gantt', {
        height  : 600,
        width   : 1000,

        columns : [
            ...
            {
                xtype : 'enddatecolumn',
                width : 80
            }
            ...
        ],

        plugins : [
            Ext.create('Sch.plugin.TreeCellEditing', {
                clicksToEdit: 1
            })
        ],
        ...
    })

Note, that this column will provide only a day-level editor (using a subclassed Ext JS DateField). If you need a more precise editing (ie also specify
the start hour/minute) you will need to provide your own field (which should extend {@link Gnt.field.EndDate}). See this [forum thread][1] for more information.

[1]: http://bryntum.com/forum/viewtopic.php?f=16&t=2277&start=10#p13964

Note that the end date of task in gantt is not inclusive, however, this column will compensate the value when rendering or editing.
So for example, if you have a 1 day task which starts at 2011/07/20 and ends at 2011/07/21 (remember end date is not inclusive!),
this column will show the `2011/07/20` when rendering. It will also increase the date by 1 day after being edited.

Also note, that this class inherits from Ext.grid.column.Date and supports its configuration options, notably the "format" option.

*/
Ext.define("Gnt.column.EndDate", {
    extend                  : "Ext.grid.column.Date",

    alias                   : [
        'widget.enddatecolumn',
        'widget.ganttcolumn.enddate'
    ],

    requires                : [
        'Ext.grid.CellEditor',
        'Gnt.field.EndDate'
    ],

    mixins                  : ['Gnt.column.mixin.TaskFieldColumn'],

    /**
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

        - text : 'Finish'
     */

    /**
     * @cfg {Number} width The width of the column.
     */
    width                   : 100,

    /**
     * @cfg {String} align The alignment of the text in the column.
     */
    align                   : 'left',

    /**
     * @cfg {String} editorFormat A date format to be used when editing the value of the column. By default it is the same as `format` configuration
     * option of the column itself.
     */
    editorFormat            : null,

    /**
     * @cfg {Boolean} adjustMilestones When set to `true`, the start/end dates of the milestones will be adjusted -1 day *during rendering and editing*. The task model will still hold unmodified date.
     */
    adjustMilestones        : true,

    /**
     * @cfg {Boolean} validateStartDate When set to `true`, the column will validate a "startDate <= endDate" condition and won't allow user to save the invalid end date.
     * Set it to `false` if you use different validation mechanism.
     */
    validateStartDate       : true,

    /**
     * @cfg {Boolean} keepDuration Pass `true` to keep the duration of the task ("move" the task), `false` to change the duration ("resize" the task).
     */
    keepDuration            : false,

    fieldProperty           : 'endDateField',

    fieldConfigs            : [ 'instantUpdate', 'adjustMilestones', 'keepDuration', 'validateStartDate', 'fieldProperty' ],

    editor                  : 'enddatefield',

    defaultEditor           : 'enddatefield',

    initComponent : function () {
        this.initTaskFieldColumn({
            format : this.editorFormat || this.format || Ext.Date.defaultFormat
        });

        this.callParent(arguments);
    },


    getValueToRender : function (value, meta, task) {
        return value && Ext.Date.format( this.field.valueToVisible(value, task), this.format ) || '';
    },

    putRawData : function (data, task) {
        if (data && !(data instanceof Date)) {
            data = Ext.Date.parse(data, this.format);
        }

        if (data && task.getStartDate() && task.getStartDate() > data) {
            task.setDuration(0);
        }

        task.setEndDate(data, task.isMilestone(), false);
    }
});
