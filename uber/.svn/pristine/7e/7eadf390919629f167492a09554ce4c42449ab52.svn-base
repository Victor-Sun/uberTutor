/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// Sencha scrolls cell
Ext.define('Sch.patches.NavigationModel6_0_2', {
    extend : 'Sch.util.Patch',

    target   : 'Ext.grid.NavigationModel',

    minVersion : '6.0.2',

    overrides : {
        focusPosition: function(position) {
            var me = this,
                view, row, scroller;
            me.item = me.cell = null;
            if (position && position.record && position.column) {
                view = position.view;
                // If the position is passed from a grid event, the rowElement will be stamped into it.
                // Otherwise, select it from the indicated item.
                if (position.rowElement) {
                    row = me.item = position.rowElement;
                } else {
                    // Get the dataview item for the position's record
                    row = view.getRowByRecord(position.record);
                }
                // If there is no item at that index, it's probably because there's buffered rendering.
                // This is handled below.
                if (row) {
                    // If the position is passed from a grid event, the cellElement will be stamped into it.
                    // Otherwise, select it from the row.
                    me.cell = position.cellElement || Ext.fly(row).down(position.column.getCellSelector(), true);
                    // Maintain the cell as a Flyweight to avoid transient elements ending up in the cache as full Ext.Elements.
                    if (me.cell) {
                        me.cell = new Ext.dom.Fly(me.cell);
                        // Maintain lastFocused in the view so that on non-specific focus of the View, we can focus the view's correct descendant.
                        view.lastFocused = me.lastFocused = me.position.clone();
                        // Use explicit scrolling rather than relying on the browser's focus behaviour.
                        // Scroll on focus overscrolls. scrollIntoView scrolls exatly correctly.
                        scroller = view.getScrollable();

                        // PATCH Do not scroll into view cells from timeline view (gantt, scheduler, etc.)
                        if (scroller && !view.mixins['Sch.mixin.TimelineView']) {
                        // END PATCH

                            scroller.scrollIntoView(me.cell);
                        }
                        me.focusItem(me.cell);
                        view.focusEl = me.cell;
                    } else // Cell no longer in view. Clear current position.
                    {
                        me.position.setAll();
                        me.record = me.column = me.recordIndex = me.columnIndex = null;
                    }
                } else // View node no longer in view. Clear current position.
                // Attempt to scroll to the record if it is in the store, but out of rendered range.
                {
                    row = view.dataSource.indexOf(position.record);
                    me.position.setAll();
                    me.record = me.column = me.recordIndex = me.columnIndex = null;
                    // The reason why the row could not be selected from the DOM could be because it's
                    // out of rendered range, so scroll to the row, and then try focusing it.
                    if (row !== -1 && view.bufferedRenderer) {
                        me.lastKeyEvent = null;
                        view.bufferedRenderer.scrollTo(row, false, me.afterBufferedScrollTo, me);
                    }
                }
            }
        }
    }
});