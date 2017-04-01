/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/*
 * @class Sch.column.timeAxis.Horizontal
 * @extends Ext.grid.column.Column
 *
 *
 * A simple grid column providing a visual representation of the time axis. This class does not produce any real Ext JS grid columns, instead it just renders a Sch.view.HorizontalTimeAxis inside its element.
 * This class can represent up to three different axes, that are defined in the view preset config object.
 */
Ext.define("Sch.column.timeAxis.Horizontal", {
    extend : 'Ext.grid.column.Column',
    alias  : 'widget.timeaxiscolumn',

    draggable     : false,
    groupable     : false,
    hideable      : false,
    sortable      : false,
    resizable     : false,
    menuDisabled  : true,
    cls           : 'sch-simple-timeaxis',
    tdCls         : 'sch-timetd',
    enableLocking : false,
    locked        : false,
    requires      : [
        'Sch.view.HorizontalTimeAxis'
    ],


    timeAxisViewModel         : null,
    headerView                : null,

    // Disable Ext JS default header hover highlight
    hoverCls                  : '',
    ownHoverCls               : 'sch-column-header-over',

    /*
     * @cfg {Boolean} trackHeaderOver `true` to highlight each header cell when the mouse is moved over it.
     */
    trackHeaderOver           : true,

    /*
     * @cfg {Number} compactCellWidthThreshold The minimum width for a bottom row header cell to be considered 'compact',
     * which adds a special CSS class to the header row.
     */
    compactCellWidthThreshold: (Ext.theme && Ext.theme.name.toLowerCase() === 'classic') ? 15 : 35,

    afterRender : function () {
        var me = this;

        // HACK relying on private accessor 'titleEl'
        var ct = me.titleEl.createChild({
            cls : 'sch-horizontaltimeaxis-ct'
        });

        me.headerView = new Sch.view.HorizontalTimeAxis({
            model                     : me.timeAxisViewModel,
            containerEl               : ct,
            hoverCls                  : me.ownHoverCls,
            trackHeaderOver           : me.trackHeaderOver,
            compactCellWidthThreshold : me.compactCellWidthThreshold
        });

        me.headerView.on('refresh', me.onTimeAxisViewRefresh, me);

        me.ownerCt.on('afterlayout', function() {
            // column can be rendered after grid was reconfigured
            if (!me.ownerCt) {
                return;
            }

            // If the container of this column changes size, we need to re-evaluate the size for the
            // time axis view
            me.mon(me.ownerCt, "resize", me.onHeaderContainerResize, me);

            if (this.getWidth() > 0) {
                // set height for horizontal time axis to make it calculate height properly
                me.headerView.height = me.getHeight();
                // In case the timeAxisViewModel is shared, no need to update it
                if (me.getAvailableWidthForSchedule() === me.timeAxisViewModel.getAvailableWidth()) {
                    me.headerView.render();
                } else {
                    me.timeAxisViewModel.update(me.getAvailableWidthForSchedule());
                }
                me.setWidth(me.timeAxisViewModel.getTotalWidth());
            }
        }, null, { single : true });

        this.enableBubble('timeheaderclick', 'timeheaderdblclick', 'timeheadercontextmenu', 'horizontaltimeaxiscolumnrender');

        me.relayEvents(me.headerView, [
            'timeheaderclick',
            'timeheaderdblclick',
            'timeheadercontextmenu'
        ]);

        me.callParent(arguments);
        
        // we don't need timeline to be focusble, it messes 'timeheader*' events and looses scroll in IE
        me.focusable = false;

        this.fireEvent('horizontaltimeaxiscolumnrender', this);
    },

    initRenderData : function () {
        var me = this;

        me.renderData.headerCls = me.renderData.headerCls || me.headerCls;
        return me.callParent(arguments);
    },

    destroy : function () {
        if (this.headerView) {
            this.headerView.destroy();
            this.headerView = null;
        }
        this.callParent(arguments);
    },

    onTimeAxisViewRefresh : function () {
        // Make sure we don't create an infinite loop
        this.headerView.un('refresh', this.onTimeAxisViewRefresh, this);

        this.setWidth(this.timeAxisViewModel.getTotalWidth());

        this.headerView.on('refresh', this.onTimeAxisViewRefresh, this);
    },

    getAvailableWidthForSchedule : function () {
        // When applying state to rendered scheduler, ext will remove content from normal and locked headers keeping
        // instances alive (#2580)
        if (!this.ownerCt) {
            return 0;
        }

        // In case owner container is hidden then it and it's items will return zero width, if so we fallback to
        // lastBox private property of Ext.AbstractComponent
        var available = this.ownerCt.isVisible(true) ? this.ownerCt.getWidth() : (this.ownerCt.lastBox && this.ownerCt.lastBox.width || 0),
            items = this.ownerCt.items,
            item;

        // substracting the widths of all columns starting from 2nd ("right" columns)
        for (var i = 1; i < items.length; i++) {
            item = items.get(i);
            if (!item.hidden) {
                available -= item.isVisible(true) ? item.getWidth() : (item.lastBox && item.lastBox.width || 0);
            }
        }

        return available - Ext.getScrollbarSize().width - 1;
    },

    onResize : function () {
        this.callParent(arguments);
        this.timeAxisViewModel.setAvailableWidth(this.getAvailableWidthForSchedule());
    },

    onHeaderContainerResize : function (header, width, height, oldWidth, oldHeight) {
        this.timeAxisViewModel.setAvailableWidth(this.getAvailableWidthForSchedule());

        if (height !== oldHeight) {
            this.headerView.height = height;
            this.headerView.render();
        }
    },

    /*
     * Refreshes the column header contents. Useful if you have some extra meta data in your timeline header that
     * depends on external data such as the EventStore or ResourceStore.
     */
    refresh                 : function () {
        if (this.rendered) {
            // Update the model, but don't fire any events which will fully redraw view
            this.timeAxisViewModel.update(null, true);

            // Now the model state has been refreshed so headers can be rerendered
            this.headerView.render();
        }
    }
});


