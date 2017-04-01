/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 @class Gnt.plugin.exporter.MultiPage
 @extends Sch.plugin.exporter.MultiPage

 This class extracts pages in a vertical and horizontal order.

 The exporterId of this exporter is `multipage`
 */

Ext.define('Gnt.plugin.exporter.MultiPage', {

    extend              : 'Sch.plugin.exporter.MultiPage',

    mixins              : ['Gnt.plugin.exporter.mixin.DependencyPainter'],

    normalGridOffset    : 0,


    onRowsCollected : function () {
        var me  = this;

        me.normalGridOffset = 0;

        me.callParent(arguments);
    },


    startPage : function (pattern, newColumnPage) {
        var me = this;

        me.normalGridOffset = pattern.normalGridOffset;

        me.callParent(arguments);
    },


    /**
     * @protected
     * Builds a page frame, a DOM-"skeleton" for a future pages.
     * @param  {Number} colIndex Zero based index of page column to build frame for.
     * @param  {Number} offset   Proper normal grid offset for the page column.
     * @return {Ext.dom.Element} Column page frame.
     */
    buildPageFrame : function (colIndex, offset) {
        var me  = this;

        var copy = me.callParent(arguments);

        // remember locked/normal grids visibility
        copy.normalHidden = me.normalGrid.hidden;
        copy.lockedHidden = me.lockedGrid.hidden;

        return copy;
    }

});
