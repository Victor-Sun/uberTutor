/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// Override provided by sencha
// https://www.sencha.com/forum/showthread.php?310675-Layout-run-failed-with-syncRowHeight-false
Ext.define('Sch.patches.TableLayout', {
    extend      : 'Sch.util.Patch',

    target      : 'Ext.view.TableLayout',

    minVersion  : '6.0.2',
    maxVersion  : '6.0.3',

    overrides   : {
        calculate: function (ownerContext) {
            var me = this,
                context = ownerContext.context,
                lockingPartnerContext = ownerContext.lockingPartnerContext,
                headerContext = ownerContext.headerContext,
                ownerCtContext = ownerContext.ownerCtContext,
                owner = me.owner,
                columnsChanged = headerContext.getProp('columnsChanged'),
                state = ownerContext.state,
                columnFlusher, otherSynchronizer, synchronizer, rowHeightFlusher,
                bodyDom = owner.body.dom,
                bodyHeight, ctSize, overflowY, normalView, lockedViewHorizScrollBar, normalViewHorizScrollBar;

            // Shortcut when empty grid - let the base handle it.
            // EXTJS-14844: Even when no data rows (all.getCount() === 0) there may be summary rows to size.
            if (!owner.all.getCount() && (!bodyDom || !owner.body.child('table'))) {
                ownerContext.setProp('viewOverflowY', false);
                me.callParent([ownerContext]);
                return;
            }

            if (columnsChanged === undefined) {
                // We cannot proceed when we have rows but no columnWidths determined...
                me.done = false;
                return;
            }

            if (columnsChanged) {
                if (!(columnFlusher = state.columnFlusher)) {
                    // Since the columns have changed, we need to write the widths to the DOM.
                    // Queue (and possibly replace) a pseudo ContextItem, who's flush method
                    // routes back into this class.
                    context.queueFlush(state.columnFlusher = columnFlusher = {
                        ownerContext: ownerContext,
                        columnsChanged: columnsChanged,
                        layout: me,
                        id: me.columnFlusherId,
                        flush: me.flushColumnWidths
                    }, true);
                }

                if (!columnFlusher.flushed) {
                    // We have queued the columns to be written, but they are still pending, so
                    // we cannot proceed.
                    me.done = false;
                    return;
                }
            }

            // They have to turn row height synchronization on, or there may be variable row heights
            // Either no columns changed, or we have flushed those changes.. which means the
            // column widths in the DOM are correct. Now we can proceed to syncRowHeights (if
            // we are locking) or wrap it up by determining our vertical overflow.
            if (ownerContext.doSyncRowHeights) {
                if (!(rowHeightFlusher = state.rowHeightFlusher)) {
                    // When we are locking, both sides need to read their row heights in a read
                    // phase (i.e., right now).
                    if (!(synchronizer = state.rowHeights)) {
                        state.rowHeights = synchronizer = ownerContext.rowHeightSynchronizer;
                        me.owner.syncRowHeightMeasure(synchronizer);
                        ownerContext.setProp('rowHeights', synchronizer);
                    }

                    if (!(otherSynchronizer = lockingPartnerContext.getProp('rowHeights'))) {
                        me.done = false;
                        return;
                    }

                    // Queue (and possibly replace) a pseudo ContextItem, who's flush method
                    // routes back into this class.
                    context.queueFlush(state.rowHeightFlusher = rowHeightFlusher = {
                        ownerContext: ownerContext,
                        synchronizer: synchronizer,
                        otherSynchronizer: otherSynchronizer,
                        layout: me,
                        id: me.rowHeightFlusherId,
                        flush: me.flushRowHeights
                    }, true);
                }

                if (!rowHeightFlusher.flushed) {
                    me.done = false;
                    return;
                }
            }

            me.callParent([ownerContext]);

            if (!ownerContext.heightModel.shrinkWrap) {
                // If the grid is shrink wrapping, we can't be overflowing
                overflowY = false;
                if (!ownerCtContext.heightModel.shrinkWrap) {
                    // We are placed in a fit layout of the gridpanel (our ownerCt), so we need to
                    // consult its containerSize when we are not shrink-wrapping to see if our
                    // content will overflow vertically.
                    ctSize = ownerCtContext.target.layout.getContainerSize(ownerCtContext);
                    if (!ctSize.gotHeight) {
                        me.done = false;
                        return;
                    }

                    bodyHeight = bodyDom.offsetHeight;
                    overflowY = bodyHeight > ctSize.height;
                }
                ownerContext.setProp('viewOverflowY', overflowY);
            }

            // Adjust the presence of X scrollability depending upon whether the headers
            // overflow, and scrollbars take up space.
            // This has two purposes.
            //
            // For lockable assemblies, if there is horizontal overflow in the normal side,
            // The locked side (which shrinkwraps the columns) must be set to overflow: scroll
            // in order that it has acquires a matching horizontal scrollbar.
            //
            // If no locking, then if there is no horizontal overflow, we set overflow-x: hidden
            // This avoids "pantom" scrollbars which are only caused by the presence of another scrollbar.
            if (me.done && Ext.getScrollbarSize().height) {
                if (lockingPartnerContext && owner.isLockedView) {
                    normalView = owner.lockingPartner;
                    lockedViewHorizScrollBar = owner.scrollFlags.x && ownerContext.headerContext.state.boxPlan.tooNarrow;
                    normalViewHorizScrollBar = normalView.scrollFlags.x && lockingPartnerContext.headerContext.state.boxPlan.tooNarrow;

                    if (lockedViewHorizScrollBar !== normalViewHorizScrollBar) {
                        if (normalViewHorizScrollBar) {
                            lockingPartnerContext.setProp('overflowX', true);
                            ownerContext.setProp('overflowX', 'scroll');
                        } else {
                            ownerContext.setProp('overflowX', true);
                            lockingPartnerContext.setProp('overflowX', 'scroll');
                        }
                    } else {
                        ownerContext.setProp('overflowX', normalViewHorizScrollBar);
                        lockingPartnerContext.setProp('overflowX', lockedViewHorizScrollBar);
                    }
                    ownerContext.setProp('overflowY', 'scroll');
                }
                // No locking sides, ensure X scrolling is on if there is overflow, but not if there is no overflow
                // This eliminates "phantom" scrollbars which are only caused by other scrollbars
                else if (!owner.isAutoTree) {
                    ownerContext.setProp('overflowX', !!ownerContext.headerContext.state.boxPlan.tooNarrow);
                }
            }
        }
    }
});