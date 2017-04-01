/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.column.BaselineEndDate

A Column displaying the baseline end date of a task.

    var gantt = Ext.create('Gnt.panel.Gantt', {
        height      : 600,
        width       : 1000,

        columns         : [
            ...
            {
                xtype       : 'baselineenddatecolumn',
                width       : 80
            }
            ...
        ],
        ...
    })

Note, that this class inherit from [Ext.grid.column.Date](http://docs.sencha.com/ext-js/4-2/#!/api/Ext.grid.column.Date) and supports its configuration options, notably the "format" option.
*/
Ext.define('Gnt.column.BaselineEndDate', {
    extend              : 'Gnt.column.EndDate',

    requires            : ['Gnt.field.BaselineEndDate'],

    alias               : [
        'widget.baselineenddatecolumn',
        'widget.ganttcolumn.baselineenddate'
    ],

    width               : 100,

    fieldProperty       : 'baselineEndDateField',

    fieldConfigs        : [ 'instantUpdate', 'adjustMilestones', 'keepDuration', 'validateStartDate', 'fieldProperty' ],

    editor              : 'baselineenddatefield',

    defaultEditor       : 'baselineenddatefield',

    putRawData : function (data, task) {
        if (data && !(data instanceof Date)) {
            data = Ext.Date.parse(data, this.format);
        }

        task.setBaselineEndDate(data);
    }
});
