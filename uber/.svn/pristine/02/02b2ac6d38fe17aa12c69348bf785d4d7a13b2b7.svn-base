/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.column.EarlyStartDate
@extends Ext.grid.column.Date

A Column displaying the earliest possible start date of a task.
This value is calculated based on earliest end dates of task's incoming dependencies.
If a task has no incoming dependencies then its start date is taken as earliest start date.

    var gantt = Ext.create('Gnt.panel.Gantt', {
        height      : 600,
        width       : 1000,

        columns         : [
            ...
            {
                xtype       : 'earlystartdatecolumn',
                width       : 80
            }
            ...
        ],
        ...
    })

Note, that this class inherit from [Ext.grid.column.Date](http://docs.sencha.com/ext-js/4-2/#!/api/Ext.grid.column.Date) and supports its configuration options, notably the "format".
*/
Ext.define('Gnt.column.EarlyStartDate', {
    extend              : 'Ext.grid.column.Date',

    mixins              : ['Gnt.mixin.Localizable'],

    alias               : [
        'widget.earlystartdatecolumn',
        'widget.ganttcolumn.earlystartdate'
    ],

    /**
     * @cfg {Number} width The width of the column.
     */
    width               : 100,

    /**
     * @cfg {String} align The alignment of the text in the column.
     */
    align               : 'left',

    /**
     * @cfg {Boolean} adjustMilestones When set to `true`, the start/end dates of the milestones will be adjusted -1 day *during rendering and editing*. The task model will still hold unmodified date.
     */
    adjustMilestones    : true,

    constructor : function (config) {
        config          = config || {};

        

        this.text   = config.text || this.L('text');

        this.callParent(arguments);

        this.renderer   = config.renderer || this.rendererFunc;
        this.scope      = config.scope || this;

        this.hasCustomRenderer = true;
    },

    afterRender : function() {
        var panel = this.up('ganttpanel');

        // Make top Gantt panel aware of the need for refreshing locked grid after changes in the dependency store
        panel.registerLockedDependencyListeners();

        this.callParent(arguments);
    },

    rendererFunc : function (value, meta, task) {
        meta.tdCls = (meta.tdCls || '') + ' sch-column-readonly';

        return task.getDisplayStartDate(this.format, this.adjustMilestones, task.getEarlyStartDate());
    }
});
