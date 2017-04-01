/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * @class Sch.view.Horizontal
 * @private
 *
 * An internal view mixin, purposed to be consumed along with {@link Sch.mixin.AbstractTimelineView}.
 * This class is consumed by the scheduling view and provides the horizontal implementation of certain methods.
 */
Ext.define("Sch.view.Horizontal", {
    requires : [
        'Ext.util.Region',
        'Ext.Element',
        'Ext.Array',
        'Sch.util.Date'
    ],
    // Provided by creator, in the config object
    view: null,

    constructor: function (config) {
        Ext.apply(this, config);
    },

    translateToScheduleCoordinate: function (x) {
        var view = this.view;
        var pos;

        if (view.shouldAdjustForRtl()) {
            pos = view.getEl().getRight() - x;
        } else {
            pos = x - view.getEl().getX();
        }

        return pos + view.getScroll().left;
    },

    translateToPageCoordinate: function (x) {
        var view = this.view;
        return x + view.getEl().getX() - view.getScroll().left;
    },

    getDateFromXY   : function (xy, roundingMethod, local) {
        var coord   = xy[0];

        if (!local) {
            coord = this.translateToScheduleCoordinate(coord);
        }
        return this.view.timeAxisViewModel.getDateFromPosition(coord, roundingMethod);
    },

    getEventRenderData: function (event) {
        var eventStart  = event.getStartDate(),
            eventEnd    = event.getEndDate() || eventStart, // Allow events to be rendered even they are missing an end date
            view        = this.view,
            viewStart   = view.timeAxis.getStart(),
            viewEnd     = view.timeAxis.getEnd(),
            M           = Math,
            startX      = view.getCoordinateFromDate(Sch.util.Date.max(eventStart, viewStart)),
            endX        = view.getCoordinateFromDate(Sch.util.Date.min(eventEnd, viewEnd)),
            data        = { event : event };

        if (this.view.rtl) {
            data.right = M.min(startX, endX);
        } else {
            data.left = M.min(startX, endX);
        }

        data.width = M.max(1, M.abs(endX - startX)) - view.eventBorderWidth;

        if (view.managedEventSizing) {
            data.top = M.max(0, (view.barMargin - ((Ext.isIE && !Ext.isStrict) ? 0 : view.eventBorderWidth - view.cellTopBorderWidth)));
            data.height = view.timeAxisViewModel.rowHeightHorizontal - (2 * view.barMargin) - view.eventBorderWidth;
        }

        data.start              = eventStart;
        data.end                = eventEnd;
        data.startsOutsideView  = eventStart < viewStart;
        data.endsOutsideView    = eventEnd > viewEnd;
        return data;
    },

    /**
    * Gets the Ext.util.Region, relative to the page, represented by the schedule and optionally only for a single resource. This method will call getDateConstraints to
    * allow for additional resource/event based constraints. By overriding that method you can constrain events differently for
    * different resources.
    * @param {Sch.model.Resource} resourceRecord (optional) The resource record
    * @param {Sch.model.Event} eventRecord (optional) The event record
    * @return {Ext.util.Region} The region of the schedule
    */
    getScheduleRegion: function (resourceRecord, eventRecord) {
        var view            = this.view,
            region          = resourceRecord ? Ext.fly(view.getRowNode(resourceRecord)).getRegion() : view.getTableRegion(),
            taStart         = view.timeAxis.getStart(),
            taEnd           = view.timeAxis.getEnd(),
            dateConstraints = view.getDateConstraints(resourceRecord, eventRecord) || { start: taStart, end: taEnd },
            startX          = this.translateToPageCoordinate(view.getCoordinateFromDate(Sch.util.Date.max(taStart, dateConstraints.start))),
            endX            = this.translateToPageCoordinate(view.getCoordinateFromDate(Sch.util.Date.min(taEnd, dateConstraints.end))),
            top             = region.top + view.barMargin,
            bottom          = region.bottom - view.barMargin - view.eventBorderWidth;

        return new Ext.util.Region(top, Math.max(startX, endX), bottom, Math.min(startX, endX));
    },


    /**
    * Gets the Ext.util.Region, relative to the scheduling view element, representing the passed resource and optionally just for a certain date interval.
    * @param {Sch.model.Resource} resourceRecord The resource record
    * @param {Date} startDate A start date constraining the region
    * @param {Date} endDate An end date constraining the region
    * @return {Ext.util.Region} The region of the resource
    */
    getResourceRegion: function (resourceRecord, startDate, endDate) {
        var view        = this.view,
            rowNode     = view.getRowNode(resourceRecord),
            offsets     = Ext.fly(rowNode).getOffsetsTo(view.getEl()),
            taStart     = view.timeAxis.getStart(),
            taEnd       = view.timeAxis.getEnd(),
            start       = startDate ? Sch.util.Date.max(taStart, startDate) : taStart,
            end         = endDate ? Sch.util.Date.min(taEnd, endDate) : taEnd,
            startX      = view.getCoordinateFromDate(start),
            endX        = view.getCoordinateFromDate(end),
            top         = offsets[1] + view.cellTopBorderWidth,
            bottom      = offsets[1] + Ext.fly(rowNode).getHeight() - view.cellBottomBorderWidth;

        if (!Ext.versions.touch) {
            var ctElScroll  = view.getScroll();
            top += ctElScroll.top;
            bottom += ctElScroll.top;
        }
        return new Ext.util.Region(top, Math.max(startX, endX), bottom, Math.min(startX, endX));
    },


    columnRenderer: function (val, meta, resourceRecord, rowIndex, colIndex) {
        var view            = this.view;
        var resourceEvents  = view.getEventStore().filterEventsForResource(resourceRecord, function(event) {
            return view.timeAxis.isRangeInAxis(event);
        });

        if (resourceEvents.length === 0) {
            return;
        }

        // Iterate events belonging to current row
        var eventsTplData = Ext.Array.map(resourceEvents, function(event) {
            return view.generateTplData(event, resourceRecord, rowIndex);
        });

        // Event data is now gathered, calculate layout properties for each event (if dynamicRowHeight is used)
        if (view.dynamicRowHeight) {
            var layout             = view.eventLayout.horizontal;
            var nbrOfBandsRequired = layout.applyLayout(eventsTplData, resourceRecord, this.layoutEventVertically, this);

            meta.rowHeight = (nbrOfBandsRequired * view.timeAxisViewModel.rowHeightHorizontal) - ((nbrOfBandsRequired - 1) * view.barMargin);
        }

        return view.eventTpl.apply(eventsTplData);
    },

    layoutEventVertically : function(bandIndex, eventRecord) {
        var view     = this.view;
        var eventTop = bandIndex === 0 ? view.barMargin : (bandIndex * view.timeAxisViewModel.rowHeightHorizontal - (bandIndex - 1) * view.barMargin);

        if (eventTop >= view.cellBottomBorderWidth) {
            eventTop -= view.cellBottomBorderWidth;
        }

        return eventTop;
    },

    // private
    resolveResource: function (node) {
        var me = this,
            view = me.view,
            eventNode,
            result;

        eventNode = Ext.fly(node).is(view.eventSelector) && node || Ext.fly(node).up(view.eventSelector, null, true);

        if (eventNode) {
            // Fast case
            result = view.getResourceRecordFromDomId(eventNode.id);
        }
        else {
            // Not that fast case
            // I'm not sure if it's really needed, the method documentation doesn't state that node must be
            // within event node. If node might be outside of event node then yes, this branch is needed, otherwise
            // it is not.
            node = view.findRowByChild(node);
            result = node && view.getRecordForRowNode(node) || null;
        }

        return result;
    },

    /**
    *  Returns the region for a "global" time span in the view. Coordinates are relative to element containing the time columns
    *  @param {Date} startDate The start date of the span
    *  @param {Date} endDate The end date of the span
    *  @return {Ext.util.Region} The region for the time span
    */
    getTimeSpanRegion: function (startDate, endDate, useViewSize) {
        var view    = this.view,
            startX  = view.getCoordinateFromDate(startDate),
            endX    = endDate ? view.getCoordinateFromDate(endDate) : startX,
            height, region;

        region = view.getTableRegion();

        if (useViewSize) {
            height = Math.max(region ? region.bottom - region.top: 0, view.getEl().dom.clientHeight); // fallback in case grid is not rendered (no rows/table)
        } else {
            height = region ? region.bottom - region.top: 0;
        }
        return new Ext.util.Region(0, Math.max(startX, endX), height, Math.min(startX, endX));
    },

    /**
    * Gets the start and end dates for an element Region
    * @param {Ext.util.Region} region The region to map to start and end dates
    * @param {String} roundingMethod The rounding method to use
    * @returns {Object} an object containing start/end properties
    */
    getStartEndDatesFromRegion: function (region, roundingMethod) {
        var view          = this.view;
        var leftDate      = view.getDateFromCoordinate(region.left, roundingMethod),
            rightDate     = view.getDateFromCoordinate(region.right, roundingMethod);

        if (leftDate && rightDate) {
            return {
                start   : Sch.util.Date.min(leftDate, rightDate),
                end     : Sch.util.Date.max(leftDate, rightDate)
            };
        }

        return null;
    },

    // private
    onEventAdd: function (s, events) {
        var view = this.view,
            affectedResources = {},
            event, startDate, endDate,
            resources, resource,
            i, l, j, k;

        for (i = 0, l = events.length; i < l; i++) {
            event       = events[i];
            startDate   = event.getStartDate();
            endDate     = event.getEndDate();

            if (startDate && endDate && view.timeAxis.timeSpanInAxis(startDate, endDate)) {
                // repaint row only if event is in time axis
                resources = events[i].getResources(view.getEventStore());

                for (j = 0, k = resources.length; j < k; j++) {
                    resource = resources[j];

                    affectedResources[resource.getId()] = resource;
                }
            }
        }

        Ext.Object.each(affectedResources, function (id, resource) {
            view.repaintEventsForResource(resource);
        });
    },

    // private
    onEventRemove: function (s, eventRecords) {
        var me            = this,
            view          = me.view,
            eventStore    = view.getEventStore(),
            resources,
            nodes;

        resources = Ext.Array.unique(
            Ext.Array.reduce(eventRecords, function(result, r) {
                // It's important to use event store's method here, instead just
                // r.getResources(). r.getResources() will always return empty array here
                // since r is already removed from the event store.
                return result.concat(eventStore.getResourcesForEvent(r));
            }, [])
        );

        nodes = Ext.Array.reduce(eventRecords, function(result, r) {
            return result.concat(view.getElementsFromEventRecord(r, null, null, true));
        }, []);

        nodes = new Ext.CompositeElementLite(nodes);

        nodes.fadeOut({
            callback: function (resource) {
                Ext.Array.forEach(resources, function(resource) {
                    if (view && !view.isDestroyed) {
                        view.store.indexOf(resource) >= 0 && view.repaintEventsForResource(resource);
                    }
                });
            }
        });
    },

    // private
    onEventUpdate: function (eventStore, model) {
        var previous = model.previous || {};
        var view = this.view;
        var timeAxis = view.timeAxis;

        var newStartDate  = model.getStartDate();
        var newEndDate    = model.getEndDate();

        var startDate       = previous.StartDate || newStartDate;
        var endDate         = previous.EndDate || newEndDate;

        // event was visible or visible now
        var eventWasInView  = startDate && endDate && timeAxis.timeSpanInAxis(startDate, endDate);

        var resource;

        // resource has to be repainted only if it was changed and event was rendered/is still rendered
        if (model.resourceIdField in previous && eventWasInView) {
            // If an event has been moved to a new row, refresh old row first
            resource = eventStore.getResourceStore().getById(previous[model.resourceIdField]);
            resource && view.repaintEventsForResource(resource, true);
        }

        // also resource has to be repainted if event was moved inside/outside of time axis
        if ((newStartDate && newEndDate && timeAxis.timeSpanInAxis(newStartDate, newEndDate)) || eventWasInView) {
            Ext.Array.each(model.getResources(), function(resource) {
                view.repaintEventsForResource(resource, true);
            });
        }
    },

    setColumnWidth: function (width, preventRefresh) {
        var view = this.view;

        view.getTimeAxisViewModel().setViewColumnWidth(width, preventRefresh);
    },

    /**
    * Method to get the currently visible date range in a scheduling view. Please note that it only works when the schedule is rendered.
    * @return {Object} object with `startDate` and `endDate` properties.
    */
    getVisibleDateRange: function () {
        var view = this.view;

        if (!view.getEl()) {
            return null;
        }

        var tableRegion = view.getTableRegion(),
            startDate   = view.timeAxis.getStart(),
            endDate     = view.timeAxis.getEnd(),
            width       = view.getWidth();

        if ((tableRegion.right - tableRegion.left) < width) {
            return { startDate: startDate, endDate: endDate };
        }

        var scroll      = view.getScroll();

        var result = {
            startDate   : view.getDateFromCoordinate(scroll.left, null, true),
            endDate     : view.getDateFromCoordinate(scroll.left + width, null, true) || view.timeAxis.getEnd()
        };

        // because of the vertical scrollbar endDate can be resolved to null in the right-most position
        if (!result.endDate) {
            result.endDate = view.timeAxis.getEnd();
        }

        return result;
    },

    /**
     * Gets box for displayed item designated by the record. If several boxes are displayed for the given item
     * then the method returns all of them. Box coordinates are in view coordinate system.
     *
     * Boxes outside scheduling view timeaxis timespan and inside collapsed rows (if row defining store is a tree store)
     * will not be returned. Boxes outside scheduling view vertical visible area (i.e. boxes above currently visible
     * top row or below currently visible bottom row) will be calculated approximately.
     *
     * @param {Sch.model.Event} eventRecord
     * @return {Object/Object[]/null}
     * @return {Boolean} return.rendered Whether the box was calculated for the rendered scheduled record or was
     *                                   approximatelly calculated for the scheduled record outside of the current
     *                                   vertical view area.
     * @return {Number} return.top
     * @return {Number} return.bottom
     * @return {Number} return.start
     * @return {Number} return.end
     * @return {String} return.relPos if the item is not rendered then provides a view relative position one of 'before', 'after'
     * @protected
     */
    getItemBox : function(eventRecord) {
        var me = this;
        return Ext.Array.map(eventRecord.getResources(), function(resourceRecord) {
            return me.getResourceEventBox(eventRecord, resourceRecord);
        });
    },

    // Decide if a record is inside a collapsed tree node, or inside a collapsed group (using grouping feature)
    isRowVisible : function(resourceRecord) {
        var hidden = Ext.isFunction(resourceRecord.isVisible) && !resourceRecord.isVisible();

        if (!hidden && this.view.groupingFeature && !this.view.groupingFeature.disabled) {
            var groups = this.view.resourceStore.getGroups();

            if (groups) {
                var group  = groups.getByKey(resourceRecord.get(this.view.resourceStore.groupField));

                hidden = group && !this.view.groupingFeature.isExpanded(group);
            }
        }

        return !hidden;
    },

    getResourceEventBox : function(eventRecord, resourceRecord) {
        var SUD = Sch.util.Date,
            me = this,
            result = null,
            view = me.view,
            viewStartDate = view.timeAxis.getStart(), // WARNING: timeaxis is a private property of Sch.mixin.AbstractTimelineView
            viewEndDate   = view.timeAxis.getEnd(),   // WARNING: timeaxis is a private property of Sch.mixin.AbstractTimelineView
            eventStartDate = eventRecord.getStartDate(),
            eventEndDate   = eventRecord.getEndDate(),
            eventLayout, rowEventsLayoutData, eventRecordData,
            rowEl, rowIndex, rowTop, rowHeight, rowNbrOfBands,
            eventEls, eventEl, eventElOffsets, eventElBox,
            firstRowIndex, lastRowIndex, firstRowRecord;

        // Checking if event record is within current time axis timespan and is visible, i.e. it's not rendered
        // within a collapsed row (scheduler supports resource tree store as well as flat resource store).
        if (
            eventStartDate && eventEndDate && SUD.intersectSpans(eventStartDate, eventEndDate, viewStartDate, viewEndDate) &&
            this.isRowVisible(resourceRecord)
        ) {

            rowEl = view.getRowNode(resourceRecord);

            // If resource row is rendered
            if (rowEl) {

                // Managed event sizing means that the view is responsible for event height setting, the oposite case
                // is when event height is controlled by CSS's top and height properties.

                // Fast case: managed event sizing on, querying the view for box position and dimensions
                if (view.managedEventSizing) {

                    eventLayout = view.eventLayout.horizontal;

                    var resourceEvents = view.getEventStore().filterEventsForResource(resourceRecord, view.timeAxis.isRangeInAxis, view.timeAxis);

                    // Preparing events layout data for event layout instance to process
                    rowEventsLayoutData = Ext.Array.map(resourceEvents, me.getEventRenderData, me);

                    // Processing event layout data injecting event vertical position into each item of `rowEventsLayoutData`
                    // This layout application takes into account view's `dynamicRowHeight` property
                    eventLayout.applyLayout(rowEventsLayoutData, resourceRecord);

                    // Now we are to find our particular event data inside all events data for the given row record
                    eventRecordData = Ext.Array.findBy(rowEventsLayoutData, function(eventData) {
                        return eventData.event == eventRecord;
                    });

                    // We must find our event record corresponding layout data object here, but just to make sure
                    if (eventRecordData) {

                        // We have event record data with coordinates within the row node, but we need
                        // those coordinates to be translated relative to view's viewport top.

                        rowTop        = Ext.fly(rowEl).getOffsetsTo(view.getNodeContainer())[1];

                        // Finally we have all the data needed to calculated the event record box
                        result = {
                            rendered : true,
                            start    : (eventRecordData.hasOwnProperty('left') ? eventRecordData.left : eventRecordData.right), // it depends on view's `rtl` configuration
                            end      : (eventRecordData.hasOwnProperty('left') ? eventRecordData.left : eventRecordData.right) + eventRecordData.width,
                            top      : rowTop + eventRecordData.top,
                            bottom   : rowTop + eventRecordData.top + eventRecordData.height
                        };
                    }
                }
                // Slow case: managed event sizing off, querying the DOM for box position and dimensions
                else {
                    eventEls = view.getElementsFromEventRecord(eventRecord, eventRecord !== resourceRecord && resourceRecord);

                    // We must have at one and only one element here, but just to make sure
                    if (eventEls.length) {
                        eventEl        = eventEls[0];
                        eventElOffsets = eventEl.getOffsetsTo(view.getEl());
                        eventElBox     = eventEl.getBox();

                        result = {
                            rendered : true,
                            start    : eventElOffsets[0],
                            end      : eventElOffsets[0] + eventElBox.width,
                            top      : eventElOffsets[1],
                            bottom   : eventElOffsets[1] + eventElBox.height
                        };
                    }
                }
            }
            // Resource row is not rendered, and it's not collapsed. We are to calculate event record box approximately.
            else {
                result = {
                    rendered : false,
                    start    : view.getCoordinateFromDate(SUD.max(eventStartDate, viewStartDate)),
                    end      : view.getCoordinateFromDate(SUD.min(eventEndDate, viewEndDate))
                    // top and bottom to go
                };

                // WARNING: view.all is a private property
                firstRowIndex  = view.all.startIndex;
                firstRowRecord = view.getRecord(view.getNode(firstRowIndex));
                rowHeight      = view.getRowHeight();

                if (resourceRecord.isAbove(firstRowRecord)) {
                    result.top    = -2 * rowHeight;
                    result.relPos = 'before';
                }
                else {
                    // WARNING: view.all is a private property
                    lastRowIndex  = view.all.endIndex;
                    // Rows can be stretched, use last row's real coordinates
                    rowEl         = Ext.get(view.getNode(lastRowIndex));
                    result.top    = rowEl.getOffsetsTo(view.getNodeContainer())[1] + rowEl.getHeight();
                    result.relPos = 'after';
                }

                result.bottom = result.top + rowHeight;
            }

            // Some boxes might need special adjustments
            if (result) {
                result = me.adjustItemBox(eventRecord, result);
            }
        }

        return result;
    },

    /**
     * Adjusts task record box if needed
     *
     * @param {Sch.model.Event} eventRecord
     * @param {Object} eventRecordBox
     * @return {Number} eventRecordBox.top
     * @return {Number} eventRecordBox.bottom
     * @return {Number} eventRecordBox.start
     * @return {Number} eventRecordBox.end
     * @return {Object}
     * @return {Number} return.top
     * @return {Number} return.bottom
     * @return {Number} return.start
     * @return {Number} return.end
     * @protected
     */
    adjustItemBox : function(eventRecord, eventRecordBox) {
        return eventRecordBox;
    },

    /**
     * Gets displaying item start side
     *
     * @param {Sch.model.Event} eventRecord
     * @return {String} 'left' / 'right' / 'top' / 'bottom'
     */
    getConnectorStartSide : function(eventRecord) {
        return 'left';
    },

    /**
     * Gets displaying item end side
     *
     * @param {Sch.model.Event} eventRecord
     * @return {String} 'left' / 'right' / 'top' / 'bottom'
     */
    getConnectorEndSide : function(eventRecord) {
        return 'right';
    }
});
