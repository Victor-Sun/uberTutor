/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.sencha.com/forum/showthread.php?310709-ensureVisible-may-throw-exception-under-certain-conditions
Ext.define('Sch.patches.BufferedRenderer', {
    extend : 'Sch.util.Patch',

    target     : 'Ext.grid.plugin.BufferedRenderer',

    minVersion  : '6.0.2',

    overrides   : {
        onRangeFetched: function(range, start, end, options, fromLockingPartner) {
            var me = this,
                view = me.view,
                viewEl = view.el,
                rows = view.all,
                increment = 0,
                variableRowHeight = me.variableRowHeight,
                lockingPartner = (view.lockingPartner && !fromLockingPartner && !me.doNotMirror) && view.lockingPartner.bufferedRenderer,
                activeEl, calculatedTop, containsFocus, i, newRows, newTop, newFocus, noOverlap, oldStart, partnerNewRows, pos, removeCount, topAdditionSize, topBufferZone;
            // View may have been destroyed since the DelayedTask was kicked off.
            if (view.destroyed) {
                return;
            }
            // If called as a callback from the Store, the range will be passed, if called from renderRange, it won't
            if (range) {
                if (!fromLockingPartner) {
                    // Re-cache the scrollTop if there has been an asynchronous call to the server.
                    me.scrollTop = me.view.getScrollY();
                }
            } else {
                range = me.store.getRange(start, end);
                // Store may have been cleared since the DelayedTask was kicked off.
                if (!range) {
                    return;
                }
            }
            // If we contain focus now, but do not when we have rendered the new rows, we must focus the view el.
            activeEl = Ext.Element.getActiveElement();
            containsFocus = viewEl.contains(activeEl);
            // Best guess rendered block position is start row index * row height.
            // We can use this as bodyTop if the row heights are all standard.
            // We MUST use this as bodyTop if the scroll is a telporting scroll.
            // If we are incrementally scrolling, we add the rows to the bottom, and
            // remove a block of rows from the top.
            // The bodyTop is then incremented by the height of the removed block to keep
            // the visuals the same.
            //
            // We cannot always use the calculated top, and compensate by adjusting the scroll position
            // because that would break momentum scrolling on DOM scrolling platforms, and would be
            // immediately undone in the next frame update of a momentum scroll on touch scroll platforms.
            calculatedTop = start * me.rowHeight;
            // The new range encompasses the current range. Refresh and keep the scroll position stable
            if (start < rows.startIndex && end > rows.endIndex) {
                // How many rows will be added at top. So that we can reposition the table to maintain scroll position
                topAdditionSize = rows.startIndex - start;
                // MUST use View method so that itemremove events are fired so widgets can be recycled.
                view.clearViewEl(true);
                newRows = view.doAdd(range, start);
                view.fireEvent('itemadd', range, start, newRows);
                for (i = 0; i < topAdditionSize; i++) {
                    increment -= newRows[i].offsetHeight;
                }
                // We've just added a bunch of rows to the top of our range, so move upwards to keep the row appearance stable
                newTop = me.bodyTop + increment;
            } else {
                // No overlapping nodes; we'll need to render the whole range.
                // teleported flag is set in getFirstVisibleRowIndex/getLastVisibleRowIndex if
                // the table body has moved outside the viewport bounds
                noOverlap = me.teleported || start > rows.endIndex || end < rows.startIndex;
                if (noOverlap) {
                    view.clearViewEl(true);
                    me.teleported = false;
                }
                if (!rows.getCount()) {
                    newRows = view.doAdd(range, start);
                    view.fireEvent('itemadd', range, start, newRows);
                    newTop = calculatedTop;
                    // Adjust the bodyTop to place the data correctly around the scroll vieport
                    if (noOverlap && variableRowHeight) {
                        topBufferZone = me.scrollTop < me.position ? me.leadingBufferZone : me.trailingBufferZone;

                        // PATCH
                        // https://www.sencha.com/forum/showthread.php?310709-ensureVisible-may-throw-exception-under-certain-conditions
                        var index = Math.min(rows.startIndex + topBufferZone - 1, rows.endIndex);
                        newTop = Math.max(me.scrollTop - rows.item(index, true).offsetTop, 0);
                        // END PATCH
                    }
                }
                // Moved down the dataset (content moved up): remove rows from top, add to end
                else if (end > rows.endIndex) {
                    removeCount = Math.max(start - rows.startIndex, 0);
                    // We only have to bump the table down by the height of removed rows if rows are not a standard size
                    if (variableRowHeight) {
                        increment = rows.item(rows.startIndex + removeCount, true).offsetTop;
                    }
                    newRows = rows.scroll(Ext.Array.slice(range, rows.endIndex + 1 - start), 1, removeCount);
                    // We only have to bump the table down by the height of removed rows if rows are not a standard size
                    if (variableRowHeight) {
                        // Bump the table downwards by the height scraped off the top
                        newTop = me.bodyTop + increment;
                    } else // If the rows are standard size, then the calculated top will be correct
                    {
                        newTop = calculatedTop;
                    }
                } else // Moved up the dataset: remove rows from end, add to top
                {
                    removeCount = Math.max(rows.endIndex - end, 0);
                    oldStart = rows.startIndex;
                    newRows = rows.scroll(Ext.Array.slice(range, 0, rows.startIndex - start), -1, removeCount);
                    // We only have to bump the table up by the height of top-added rows if rows are not a standard size
                    if (variableRowHeight) {
                        // Bump the table upwards by the height added to the top
                        newTop = me.bodyTop - rows.item(oldStart, true).offsetTop;
                        // We've arrived at row zero...
                        if (!rows.startIndex) {
                            // But the calculated top position is out. It must be zero at this point
                            // We adjust the scroll position to keep visual position of table the same.
                            if (newTop) {
                                view.setScrollY(me.position = (me.scrollTop -= newTop));
                                newTop = 0;
                            }
                        }
                        // Not at zero yet, but the position has moved into negative range
                        else if (newTop < 0) {
                            increment = rows.startIndex * me.rowHeight;
                            view.setScrollY(me.position = (me.scrollTop += increment));
                            newTop = me.bodyTop + increment;
                        }
                    } else // If the rows are standard size, then the calculated top will be correct
                    {
                        newTop = calculatedTop;
                    }
                }
                // The position property is the scrollTop value *at which the table was last correct*
                // MUST be set at table render/adjustment time
                me.position = me.scrollTop;
            }
            // We contained focus at the start, but that activeEl has been derendered.
            // Focus the cell's column header.
            if (containsFocus && !viewEl.contains(activeEl)) {
                pos = view.actionableMode ? view.actionPosition : view.lastFocused;
                if (pos && pos.column) {
                    view.onFocusLeave({});
                    // Try to focus the contextual column header.
                    // Failing that, look inside it for a tabbable element.
                    // Failing that, focus the view.
                    // Focus MUST NOT just silently die due to DOM removal
                    if (pos.column.focusable) {
                        newFocus = pos.column;
                    } else {
                        newFocus = pos.column.el.findTabbableElements()[0];
                    }
                    if (!newFocus) {
                        newFocus = view.el;
                    }
                    newFocus.focus();
                }
            }
            // Position the item container.
            newTop = Math.max(Math.floor(newTop), 0);
            if (view.positionBody) {
                me.setBodyTop(newTop);
            }
            // Sync the other side to exactly the same range from the dataset.
            // Then ensure that we are still at exactly the same scroll position.
            if (newRows && lockingPartner && !lockingPartner.disabled) {
                // Set the pointers of the partner so that its onRangeFetched believes it is at the correct position.
                lockingPartner.scrollTop = lockingPartner.position = me.scrollTop;
                if (lockingPartner.view.ownerCt.isVisible()) {
                    partnerNewRows = lockingPartner.onRangeFetched(range, start, end, options, true);
                    // Sync the row heights if configured to do so, or if one side has variableRowHeight but the other doesn't.
                    // variableRowHeight is just a flag for the buffered rendering to know how to measure row height and
                    // calculate firstVisibleRow and lastVisibleRow. It does not *necessarily* mean that row heights are going
                    // to be asymmetric between sides. For example grouping causes variableRowHeight. But the row heights
                    // each side will be symmetric.
                    // But if one side has variableRowHeight (eg, a cellWrap: true column), and the other does not, that
                    // means there could be asymmetric row heights.
                    if (view.ownerGrid.syncRowHeight || (lockingPartner.variableRowHeight !== variableRowHeight)) {
                        me.syncRowHeights(newRows, partnerNewRows);
                    }
                }
                if (lockingPartner.bodyTop !== newTop) {
                    lockingPartner.setBodyTop(newTop);
                }
                // Set the real scrollY position after the correct data has been rendered there.
                // It will not handle a scroll because the scrollTop and position have been preset.
                lockingPartner.view.setScrollY(me.scrollTop);
            }
            // If there are columns to trigger rendering, and the rendered block os not either the view size
            // or, if store count less than view size, the store count, then there's a bug.
            if (view.getVisibleColumnManager().getColumns().length && rows.getCount() !== Math.min(me.store.getCount(), me.viewSize)) {
                Ext.raise('rendered block refreshed at ' + rows.getCount() + ' rows while BufferedRenderer view size is ' + me.viewSize);
            }
            return newRows;
        },

        // In 6.0.2 there's additional check which is failing in gantt due to our refresh blockers. We simply mute
        // that condition for panels with Sch.mixin.TimelinePanel mixed in.
        // covered by 062_reload_store in gantt
        doRefreshView: function(range, startIndex, endIndex, options) {
            var me = this,
                view = me.view,
                rows = view.all,
                previousStartIndex = rows.startIndex,
                previousEndIndex = rows.endIndex,
                previousFirstItem, previousLastItem,
                prevRowCount = rows.getCount(),
                newNodes,
                viewMoved = startIndex !== rows.startIndex,
                calculatedTop, scrollIncrement, restoreFocus;
            // So that listeners to the itemremove events know that its because of a refresh.
            // And so that this class's refresh listener knows to ignore it.
            view.refreshing = me.refreshing = true;
            if (view.refreshCounter) {
                // Give CellEditors or other transient in-cell items a chance to get out of the way.
                if (view.hasListeners.beforerefresh && view.fireEvent('beforerefresh', view) === false) {
                    return view.refreshNeeded = view.refreshing = me.refreshing = false;
                }
                // If focus was in any way in the view, whether actionable or navigable, this will return
                // a function which will restore that state.
                restoreFocus = view.saveFocusState();
                view.clearViewEl(true);
                view.refreshCounter++;
                if (range.length) {
                    newNodes = view.doAdd(range, startIndex);
                    if (viewMoved) {
                        // Try to find overlap between newly rendered block and old block
                        previousFirstItem = rows.item(previousStartIndex, true);
                        previousLastItem = rows.item(previousEndIndex, true);
                        // Work out where to move the view top if there is overlap
                        if (previousFirstItem) {
                            scrollIncrement = -previousFirstItem.offsetTop;
                        } else if (previousLastItem) {
                            scrollIncrement = rows.last(true).offsetTop - previousLastItem.offsetTop;
                        }
                        // If there was an overlap, we know exactly where to move the view
                        if (scrollIncrement) {
                            me.bodyTop = Math.max(me.bodyTop + scrollIncrement, 0);
                            me.scrollTop = me.bodyTop ? me.scrollTop + scrollIncrement : 0;
                        } else // No overlap: calculate the a new body top and scrollTop.
                        {
                            // To position rows, remove table's top border
                            me.bodyTop = calculatedTop = startIndex * me.rowHeight;
                            me.scrollTop = Math.max(calculatedTop - me.rowHeight * (calculatedTop < me.bodyTop ? me.leadingBufferZone : me.trailingBufferZone , 0));
                        }
                    }
                } else // Clearing the view.
                // Ensure we jump to top.
                // Apply empty text.
                {
                    if (me.scrollTop) {
                        me.bodyTop = me.scrollTop = 0;
                    }
                    view.addEmptyText();
                }
                // Keep scroll and rendered block positions synched.
                if (viewMoved) {
                    me.setBodyTop(me.bodyTop);
                    view.suspendEvent('scroll');
                    view.setScrollY(me.position = me.scrollTop);
                    view.resumeEvent('scroll');
                }
                // Correct scroll range
                me.refreshSize();
                view.refreshSize(rows.getCount() !== prevRowCount);
                view.fireEvent('refresh', view, range);
                // If focus was in any way in this view, this will restore it
                restoreFocus();
                view.headerCt.setSortState();
            } else {
                view.refresh();
            }

            // HACK avoid this check for gantt/scheduler components
            if (!(view.ownerGrid.is('timelinegrid,timelinetree')) &&
            // END HACK
                // If there are columns to trigger rendering, and the rendered block is not either the view size
                // or, if store count less than view size, the store count, then there's a bug.
                view.getVisibleColumnManager().getColumns().length && rows.getCount() !== Math.min(me.store.getCount(), me.viewSize)) {
                    Ext.raise('rendered block refreshed at ' + rows.getCount() + ' rows while BufferedRenderer view size is ' + me.viewSize);
            }
            view.refreshNeeded = view.refreshing = me.refreshing = false;
        }
    }
});