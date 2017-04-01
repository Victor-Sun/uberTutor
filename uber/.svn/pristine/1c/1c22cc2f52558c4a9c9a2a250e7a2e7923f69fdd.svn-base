/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Sch.patches.ColumnLayout', {
    extend      : 'Sch.util.Patch',

    target      : 'Ext.grid.ColumnLayout',

    minVersion  : '6.0.2',
    maxVersion  : '6.0.3',

    overrides   : {
        calculate: function (ownerContext) {
            var me = this,
                grid = me.owner.grid,
            // Our TableLayout buddy sets this in its beginLayout so we can work this
            // out together:
                viewContext = ownerContext.viewContext,
                state = ownerContext.state,
                context = ownerContext.context,
                lockingPartnerContext, lockingPartnerGrid, ownerGrid,
                columnsChanged, columns, len, i, column, scrollbarAdjustment, viewOverflowY;

            me.callSuper([ownerContext]);

            if (grid && state.parallelDone) {
                lockingPartnerContext = viewContext.lockingPartnerContext;

                // BUG: lockingPartnerContext must be nulled if partner grid is collapsed or hidden.
                // ==================================================================================
                if (lockingPartnerContext) {
                    lockingPartnerGrid = lockingPartnerContext.target.ownerCt;
                    if (!lockingPartnerGrid.isVisible() || lockingPartnerGrid.collapsed) {
                        lockingPartnerContext = null;
                    }
                }
                ownerGrid = grid.ownerGrid;

                // A force-fit needs to be "reflexed" so check that now. If we have to reflex
                // the items, we need to re-cacheFlexes and invalidate ourselves.
                if (ownerGrid.forceFit && !state.reflexed) {
                    if (me.convertWidthsToFlexes(ownerContext)) {
                        me.cacheFlexes(ownerContext);
                        me.done = false;
                        ownerContext.invalidate({
                            state: {
                                reflexed: true,
                                scrollbarAdjustment: me.getScrollbarAdjustment(ownerContext)
                            }
                        });
                        return;
                    }
                }

                // Once the parallelDone flag goes up, we need to pack up the changed column
                // widths for our TableLayout partner.
                if ((columnsChanged = state.columnsChanged) === undefined) {
                    columns = ownerContext.target.getVisibleGridColumns();
                    columnsChanged = false;

                    for (i = 0, len = columns.length; i < len; i++) {
                        column = context.getCmp(columns[i]);
                        // Since we are parallelDone, all of the children should have width,
                        // so we can

                        if (!column.lastBox || column.props.width !== column.lastBox.width) {
                            (columnsChanged || (columnsChanged = []))[i] = column;
                        }
                    }

                    state.columnsChanged = columnsChanged;
                    // This will trigger our TableLayout partner and allow it to proceed.
                    ownerContext.setProp('columnsChanged', columnsChanged);
                }

                if (ownerContext.manageScrollbar) {
                    // If we changed the column widths, we need to wait for the TableLayout to
                    // return whether or not we have overflowY... well, that is, if we are
                    // needing to tweak the scrollbarAdjustment...
                    scrollbarAdjustment = me.getScrollbarAdjustment(ownerContext);

                    if (scrollbarAdjustment) {
                        // Since we start with the assumption that we will need the scrollbar,
                        // we now need to wait to see if our guess was correct.
                        viewOverflowY = viewContext.getProp('viewOverflowY');
                        if (viewOverflowY === undefined) {
                            // The TableLayout has not determined this yet, so park it.
                            me.done = false;
                            return;
                        }

                        if (!viewOverflowY) {
                            // We have our answer, and it turns out the view did not overflow
                            // (even with the reduced width we gave it), so we need to remove
                            // the scrollbarAdjustment and go again.
                            if (lockingPartnerContext) {
                                // In a locking grid, only the normal side plays this game,
                                // so now that we know the resolution, we need to invalidate
                                // the locking view and its headerCt.
                                lockingPartnerContext.invalidate();
                                lockingPartnerContext.headerContext.invalidate();
                            }
                            viewContext.invalidate();
                            ownerContext.invalidate({
                                state: {
                                    // Pass a 0 adjustment on into our next life. If this is
                                    // the invalidate that resets ownerContext then this is
                                    // put onto the new state. If not, it will reset back to
                                    // undefined and we'll have to begin again (which is the
                                    // correct thing to do in that case).
                                    scrollbarAdjustment: 0
                                }
                            });
                        }
                    }
                    // else {
                    // We originally assumed we would need the scrollbar and since we do
                    // not now, we must be on the second pass, so we can move on...
                    // }
                }
            }
        }
    }
});