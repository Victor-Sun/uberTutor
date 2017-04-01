/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 @class Sch.plugin.exporter.SinglePage
 @extends Sch.plugin.exporter.AbstractExporter

 This class extracts all scheduler data to fit in a single page.

 The exporterId of this exporter is `singlepage`
 */


Ext.define('Sch.plugin.exporter.SinglePage', {

    extend  : 'Sch.plugin.exporter.AbstractExporter',

    /**
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

     - name    : 'Single page'
     */

    config  : {
        exporterId : 'singlepage'
    },

    getExpectedNumberOfPages : function () {
        return 1;
    },

    getPaperFormat : function () {
        var me          = this,
            realSize    = me.getTotalSize(),
            dpi         = me.exportConfig.DPI,
            width       = Ext.Number.toFixed(realSize.width / dpi, 1),
            height      = Ext.Number.toFixed(realSize.height / dpi, 1);

        return width+'in*'+height+'in';
    },


    onRowsCollected : function () {
        var me = this;

        me.startPage();
        me.fillGrids();
        me.commitPage();

        me.onPagesExtracted();
    },


    getPageTplData : function () {
        var me          = this,
            realSize    = me.getTotalSize();

        return Ext.apply(me.callParent(arguments), {
            bodyHeight  : realSize.height,
            totalWidth  : realSize.width
        });
    },

    getHeaderTplData : function (pageInfo) {
        var me  = this;

        return Ext.apply(me.callParent(arguments), {
            width       : me.getTotalWidth(),
            height      : me.pageHeaderHeight
        });
    },

    getFooterTplData : function (pageInfo) {
        var me  = this;

        return Ext.apply(me.callParent(arguments), {
            width       : me.getTotalWidth(),
            height      : me.pageHeaderHeight
        });
    },

    fitComponentIntoPage : function () {
        var me          = this,
            lockedGrid  = me.lockedGrid;

        lockedGrid.setWidth(lockedGrid.headerCt.getEl().first().getWidth());
    },

    preparePageToCommit : function () {
        var me              = this,
            frag            = me.callParent(arguments),
            secondaryCanvas = frag.select('.sch-secondary-canvas').first(),
            zones           = secondaryCanvas.select('.sch-zone'),
            lines           = secondaryCanvas.select('.sch-column-line'),
            height          = me.getTotalHeight();

        secondaryCanvas.setTop(0);
        zones.setHeight(height);
        lines.setHeight(height);

        var depsCt = frag.selectNode('.sch-dependencyview-ct');

        if (depsCt) {
            depsCt.innerHTML = me.dependenciesHtml;

            //move the dependencies div to match the position of the dependency lines
            depsCt.style.top = '0px';
            depsCt.style.left = '0px';
            depsCt.style.visibility = 'visible';
        }

        // hiding dependencies
        var normalGrid = me.component.normalGrid,
            tableWidth = normalGrid.el.down(me.tableSelector).getWidth(),
            id         = normalGrid.getView().id,
            el         = frag.select('#' + id).first().dom;

        el.style.width = tableWidth + 'px';

        var normalView = frag.selectNode('#'+me.normalView.id);
        //remove scrollbar
        normalView.style.overflow = 'hidden';

        var splitter = frag.selectNode('.' + Ext.baseCSSPrefix + 'splitter');

        if (splitter) splitter.style.height = '100%';

        return frag;
    }

});