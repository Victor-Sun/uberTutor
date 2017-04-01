/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/*
 * @class Sch.column.timeAxis.Vertical
 *
 * @extends Ext.grid.column.Column
 * A Column representing the time axis in vertical orientation
 * @constructor
 * @param {Object} config The configuration options
 */
Ext.define('Sch.column.timeAxis.Vertical', {

    extend : 'Ext.grid.column.Column',

    alias : 'widget.verticaltimeaxis',


    /*
     * Default timeaxis column properties
     */
    align : 'right',

    width                 : 100,
    draggable             : false,
    groupable             : false,
    hideable              : false,
    sortable              : false,
    menuDisabled          : true,
    timeAxis              : null,
    timeAxisViewModel     : null,
    cellTopBorderWidth    : null,
    cellBottomBorderWidth : null,
    totalBorderWidth      : null,
    enableLocking         : false,
    locked                : true,
    dataIndex             : 'start',

    initComponent : function () {
        this.callParent(arguments);
        this.tdCls = (this.tdCls || '') + ' sch-verticaltimeaxis-cell';
        this.scope = this;

        this.addCls('sch-verticaltimeaxis-header');
        this.totalBorderWidth = this.cellTopBorderWidth + this.cellBottomBorderWidth;
    },

    renderer : function (val, meta, record, rowIndex) {
        var hc          = this.timeAxisViewModel.getBottomHeader();

        meta.style      = 'height:' + (this.timeAxisViewModel.getTickWidth() - this.totalBorderWidth) + 'px';

        if (hc.renderer) {
            return hc.renderer.call(hc.scope || this, record.data.start, record.data.end, meta, rowIndex);
        } else {
            return Ext.Date.format(val, hc.dateFormat);
        }
    }
});

