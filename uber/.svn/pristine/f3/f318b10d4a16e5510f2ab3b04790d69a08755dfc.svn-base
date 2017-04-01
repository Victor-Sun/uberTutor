/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.column.Slack
@extends Ext.grid.column.Column

A column showing the available amount of slack for a task. The _slack_ (or _float_) is the amount of time that a task can be delayed
without causing a delay to any of its successors.

The slack is displayed in units specified by the {@link #slackUnit} config (by default it's displayed in _days_).


    var gantt = Ext.create('Gnt.panel.Gantt', {
        height      : 600,
        width       : 1000,

        // Setup your grid columns
        columns         : [
            ...
            {
                xtype       : 'slackcolumn',
                width       : 70
            }
            ...
        ],
        ...
    })

*/
Ext.define('Gnt.column.Slack', {
    extend              : 'Ext.grid.column.Column',

    requires            : ['Ext.Number', 'Sch.util.Date'],

    mixins              : ['Gnt.mixin.Localizable'],

    alias               : [
        'widget.slackcolumn',
        'widget.ganttcolumn.slack'
    ],

    /**
     * @cfg {Number} decimalPrecision A number of digits to show after the dot when rendering the value of the slack.
     */
    decimalPrecision    : 2,

    /**
     * @cfg {Boolean} useAbbreviation When set to `true`, the column will render the abbreviated slack unit name, not full.
     * Useful if the column width is limited.
     */
    useAbbreviation     : false,

    /**
     * @cfg {String} slackUnit The time unit to use when displaying the slack amount.
     */
    slackUnit           : 'd',

    width               : 100,

    align               : 'left',

    constructor : function (config) {
        config = config || {};

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

        value = task.getSlack();

        if (Ext.isNumber(value)) {
            return parseFloat(Ext.Number.toFixed(value, this.decimalPrecision)) + ' ' +
                Sch.util.Date[ this.useAbbreviation ? 'getShortNameOfUnit' : 'getReadableNameOfUnit' ](this.slackUnit, value !== 1);
        }

        return '';
    }
});
