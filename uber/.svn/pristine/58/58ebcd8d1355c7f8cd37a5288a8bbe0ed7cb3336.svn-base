/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 @class Sch.feature.SchedulerDragZone
 @extends Ext.dd.DragZone

 A custom scheduler dragzone that also acts as the dropzone, and optionally
 constrains the drag to the resource area that contains the dragged element.

 Generally it should not need to be used directly.
 To configure drag and drop use {@link Sch.mixin.SchedulerPanel#cfg-dragConfig SchedulerPanel} dragConfig instead.
 */
Ext.define("Sch.feature.SchedulerDragZone", {
    extend : "Ext.dd.DragZone",

    requires : [
        'Sch.tooltip.Tooltip',
        'Ext.dd.StatusProxy'
    ],

    repairHighlight      : false,
    repairHighlightColor : 'transparent',
    // this has to be set to `false` because we will manually register the view element in the ScrollManager
    // we don't need to register the dragged element in it
    containerScroll      : false,

    /**
     * @cfg {Boolean} showTooltip Specifies whether or not to show tooltip while dragging event
     */
    showTooltip : true,

    /**
     * @cfg {Ext.tip.ToolTip/Object} tip
     *
     * The tooltip instance to show while dragging event or a configuration object
     */
    tip : null,

    // this property is taking part in coordinates calculations in alignElWithMouse
    // these adjustments required for correct positioning of proxy on mouse move after view scroll
    deltaSetXY : null,

    schedulerView : null,

    _onValidDropTimer : null,

    // The last 'good' coordinates received by mousemove events (needed when a scroll event happens, which doesn't contain XY info)
    lastXY : null,

    /**
     * @type {Boolean} showExactDropPosition When enabled, the event being dragged always "snaps" to the exact start date that it will have after drop.
     */
    showExactDropPosition : false,

    /**
     * @cfg {Boolean} enableCopy true to enable copy by pressing modifier key
     * (see {@link #enableCopyKey enableCopyKey}) during drag drop.
     */
    enableCopy : false,

    /**
     *
     * @cfg {String} enableCopyKey
     * Modifier key that should be pressed during drag drop to copy item.
     * Available values are 'CTRL', 'ALT', 'SHIFT'
     */
    enableCopyKey : 'SHIFT',

    /**
     * @cfg {Object} validatorFn
     *
     * An empty function by default, but provided so that you can perform custom validation on
     * the item being dragged. This function is called during the drag and drop process and also after the drop is made
     * @param {Sch.model.Event[]} dragRecords an array containing the records for the events being dragged
     * @param {Sch.model.Resource} targetResourceRecord the target resource of the the event
     * @param {Date} date The date corresponding to the current mouse position
     * @param {Number} duration The duration of the item being dragged
     * @param {Event} e The event object
     * @return {Boolean} true if the drop position is valid, else false to prevent a drop
     */
    validatorFn : function (dragRecords, targetResourceRecord, date, duration, e) {
        return true;
    },

    /**
     * @cfg {Object} validatorFnScope
     * The scope for the {@link #validatorFn}
     */
    validatorFnScope : null,
    
    copyKeyPressed : false,

    /**
     * @cfg {String} dragDropProxyCls Special CSS class added to drag proxy to perform lookups
     * @required
     * @private
     */
    dragDropProxyCls : 'sch-dd-ref',

    /**
     * @constructor
     * @param {String/HTMLElement/Ext.dom.Element} el The container element or its ID
     * @param {Object} config The object containing the configuration of this model.
     */
    constructor : function (el, config) {
        // Drag drop won't work in IE8 if running in an iframe
        // https://www.assembla.com/spaces/bryntum/tickets/712#/activity/ticket:
        if (Ext.isIE8m && window.top !== window) {
            Ext.dd.DragDropManager.notifyOccluded = true;
        }

        var proxy = this.proxy = this.proxy || new Ext.dd.StatusProxy({
            shadow         : false,
            dropAllowed    : this.dropAllowed,
            dropNotAllowed : this.dropNotAllowed,

            // HACK, we want the proxy inside the scheduler, so that when user drags the event
            // out of the scheduler el, the event should be cropped by the scheduler edge
            ensureAttachedToBody : Ext.emptyFn
        });

        this.callParent(arguments);
        this.isTarget   = true;
        this.scroll     = false;
        this.ignoreSelf = false;

        var schedulerView = this.schedulerView;

        if (schedulerView.touchScroll) {
            // disable tooltips for touch devices
            this.showTooltip = false;
        }

        schedulerView.el.appendChild(proxy.el);

        this.bindRightClickPreventer();

        proxy.addCls('sch-dragproxy');

        // Activate the auto-scrolling behavior during the drag drop process
        schedulerView.on({
            eventdragstart : function () {
                Sch.util.ScrollManager.activate(schedulerView, schedulerView.constrainDragToResource && schedulerView.getMode());
            },

            aftereventdrop : function () {
                Sch.util.ScrollManager.deactivate();
            },

            scope : this
        });

        if (this.showTooltip) {
            var tip         = this.tip,
                containerEl = schedulerView.up('[lockable=true]').el;

            if (tip instanceof Ext.tip.ToolTip) {
                tip.schedulerView = schedulerView;
            } else {
                this.tip = new Sch.tooltip.Tooltip(Ext.apply({
                    schedulerView : schedulerView,
                    cls           : 'sch-dragdrop-tip',
                    constrainTo   : containerEl
                }, tip));
            }
        }
    },

    bindRightClickPreventer : function () {
        var eventName;

        if (Ext.isIE10) {
            eventName = 'contextmenu';
        } else {
            // fixes exception in IE9<, chrome, ff
            eventName = 'mousedown';
        }

        this.schedulerView.el.on(eventName, this.preventRightClick, this, { priority : 999 });
    },

    destroy : function () {
        this.callParent(arguments);

        clearTimeout(this._onValidDropTimer);

        Ext.destroyMembers(
            this,
            'tip'
        );
    },

    // Prevents right clicking when dragging is in progress
    preventRightClick : function (e) {
        if (this.dragging && e.button !== 0) {
            // While dragging event clone is under pointer, so when user open context menu
            // scheduler also tries to resolve event and fails. When button is released, event dropped correctly
            // Preventing event is required for IE10 under windows 7. Otherwise it would be enough just to return false
            e.stopEvent();
            return false;
        }
    },

    // @OVERRIDE
    autoOffset : function () {
        this.setDelta(0, 0);
    },

    // private
    setupConstraints : function (constrainRegion, elRegion, xOffset, yOffset, isHorizontal, tickSize, constrained) {
        this.clearTicks();

        var xTickSize = isHorizontal && !this.showExactDropPosition && tickSize > 1 ? tickSize : 0;
        var yTickSize = !isHorizontal && !this.showExactDropPosition && tickSize > 1 ? tickSize : 0;

        this.resetConstraints();

        this.initPageX = constrainRegion.left + xOffset;
        this.initPageY = constrainRegion.top + yOffset;

        var width  = elRegion.right - elRegion.left;
        var height = elRegion.bottom - elRegion.top;

        // if `constrained` is false then we haven't specified getDateConstraint method and should constrain mouse position to scheduling area
        // else we have specified date constraints and so we should limit mouse position to smaller region inside of constrained region using offsets and width.
        if (isHorizontal) {
            if (constrained) {
                this.setXConstraint(constrainRegion.left + xOffset, constrainRegion.right - width + xOffset, xTickSize);
            } else {
                this.setXConstraint(constrainRegion.left, constrainRegion.right, xTickSize);
            }
            this.setYConstraint(constrainRegion.top + yOffset, constrainRegion.bottom - height + yOffset, yTickSize);
        } else {
            this.setXConstraint(constrainRegion.left + xOffset, constrainRegion.right - width + xOffset, xTickSize);
            if (constrained) {
                this.setYConstraint(constrainRegion.top + yOffset, constrainRegion.bottom - height + yOffset, yTickSize);
            } else {
                this.setYConstraint(constrainRegion.top, constrainRegion.bottom, yTickSize);
            }
        }
    },

    // @OVERRIDE
    setXConstraint : function (iLeft, iRight, iTickSize) {
        this.leftConstraint  = iLeft;
        this.rightConstraint = iRight;

        this.minX = iLeft;
        this.maxX = iRight;
        if (iTickSize) {
            this.setXTicks(this.initPageX, iTickSize);
        }

        this.constrainX = true;
    },

    // @OVERRIDE
    setYConstraint : function (iUp, iDown, iTickSize) {
        this.topConstraint    = iUp;
        this.bottomConstraint = iDown;

        this.minY = iUp;
        this.maxY = iDown;
        if (iTickSize) {
            this.setYTicks(this.initPageY, iTickSize);
        }

        this.constrainY = true;
    },

    // These cause exceptions, and are not needed
    onDragEnter : Ext.emptyFn,
    onDragOut   : Ext.emptyFn,


    setVisibilityForSourceEvents : function (show) {
        Ext.Array.each(this.dragData.getEventBarElements(), function (el) {
            el[show ? 'show' : 'hide']();
        });
    },


    // private
    onDragOver : function (e) {

        if (e && e.event.touches && e.event.touches.length > 1) {
            // Force a stop if multi touch is detected
            Ext.dd.DragDropManager.handleMouseUp(e);
            return;
        }

        var xy = e ? e.getXY() : this.lastXY;

        // May have a situation where user grabs an event, drags it outside the view - then when back in the view the first thing
        // that happens is a view scroll
        if (!xy) return;

        this.checkShiftChange();

        var dd = this.dragData;

        if (!dd.originalHidden) {
            // Hide dragged event elements at this time
            this.setVisibilityForSourceEvents(false);

            dd.originalHidden = true;
        }

        var start    = dd.startDate;
        var resource = dd.newResource;
        var view     = this.schedulerView;

        this.updateDragContext(e);

        if (this.showExactDropPosition) {
            var isHorizontal = view.isHorizontal();
            var timeDiff     = view.getDateFromCoordinate(isHorizontal ? xy[0] : xy[1]) - dd.sourceDate;
            var realStart    = new Date(dd.origStart - 0 + timeDiff);
            var offset       = view.timeAxisViewModel.getDistanceBetweenDates(realStart, dd.startDate);

            if (dd.startDate > view.timeAxis.getStart()) {
                var proxyEl = this.proxy.el;
                
                if (offset) {
                    if (view.isHorizontal()) {
                        proxyEl.setX(xy[0] + (this.schedulerView.shouldAdjustForRtl() ? -offset : offset));
                    } else {
                        proxyEl.setY(xy[1] + offset);
                    }
                }
            }
        }

        if (dd.startDate - start !== 0 || resource !== dd.newResource) {
            this.schedulerView.fireEvent('eventdrag', this.schedulerView, dd.draggedRecords, dd.startDate, dd.newResource, dd);
        }

        if (this.showTooltip) {
            this.tip.realign();
            this.tip.update(dd.startDate, dd.endDate, dd.valid, dd.message);
        }

        if (e) {
            this.lastXY = [e.browserEvent.clientX + document.body.scrollLeft, e.getY()];
        }
    },

    getCoordinate : function (coord) {
        switch (this.schedulerView.getMode()) {
            case 'horizontal'   :
                return coord[0];
            /* pass through */
            case 'vertical'     :
                return coord[1];
            /* pass through */
            case 'calendar'     :
                return coord;
            /* pass through */
        }
    },


    getDragData : function (e) {
        var s = this.schedulerView,
            t = e.getTarget(s.eventSelector);

        if (!t || e.event.touches && e.event.touches.length > 1) return;

        var eventRecord      = s.resolveEventRecord(t),
            resourceRecord   = s.resolveResource(t),
            assignmentRecord = s.resolveAssignmentRecord(t);

        // there will be no event record when trying to drag the drag creator proxy for example
        if (!eventRecord || eventRecord.isDraggable() === false || s.fireEvent('beforeeventdrag', s, eventRecord, e) === false) {
            return null;
        }

        var xy          = e.getXY(),
            eventEl     = Ext.get(t),
            eventXY     = eventEl.getXY(),
            offsets     = [xy[0] - eventXY[0], xy[1] - eventXY[1]],
            eventRegion = eventEl.getRegion();

        this.lastXY = null;

        var isHorizontal = s.getMode() === 'horizontal';

        s.constrainDragToResource && !resourceRecord &&
        Ext.Error.raise('Resource could not be resolved for event: ' + eventRecord.getId());

        var dateConstraints = s.getDateConstraints(s.constrainDragToResource ? resourceRecord : null, eventRecord);

        this.setupConstraints(
            s.getScheduleRegion(s.constrainDragToResource ? resourceRecord : null, eventRecord),
            eventRegion,
            offsets[0], offsets[1],
            isHorizontal,
            s.getSnapPixelAmount(),
            Boolean(dateConstraints)
        );

        var origStart      = eventRecord.getStartDate(),
            origEnd        = eventRecord.getEndDate(),
            timeAxis       = s.timeAxis,
            relatedRecords = this.getRelatedRecords(assignmentRecord || eventRecord) || [],
            eventBarEls    = s.getElementsFromEventRecord(eventRecord, s.isCalendar() ? null : resourceRecord);

        // Collecting additional elements to drag
        Ext.Array.each(relatedRecords, function (r) {
            if (r instanceof Sch.model.Assignment) {
                eventBarEls = eventBarEls.concat(s.getElementsFromEventRecord(r.getEvent(), r.getResource()));
            }
            else {
                eventBarEls = eventBarEls.concat(s.getElementsFromEventRecord(r));
            }
        });
        eventBarEls        = Ext.Array.unique(eventBarEls); // I'm not sure if it's required, but this way it seems safer

        var dragData = {
            offsets  : offsets,
            repairXY : eventXY,

            prevScroll : s.getScroll(),

            dateConstraints : dateConstraints,

            eventBarEls : eventBarEls,

            // During infinite scroll the scheduling view might be refreshed, due to time axis reconfiguration,
            // thus destroying previously stored DOM elements (and possibly new DOMs rendered),
            // by getting stored event elements via this method we make sure to always get fresh Elements
            // and ignore stale ones.
            getEventBarElements : function () {
                return dragData.eventBarEls = Ext.Array.map(dragData.eventBarEls, function (el) {
                    return el.dom && el || Ext.get(el.id);
                });
            },

            draggedRecords : [assignmentRecord || eventRecord].concat(relatedRecords),

            resourceRecord : resourceRecord,

            sourceDate : s.getDateFromCoordinate(this.getCoordinate(xy)),
            origStart  : origStart,
            origEnd    : origEnd,
            startDate  : origStart,
            endDate    : origEnd,
            timeDiff   : 0,

            startsOutsideView : origStart < timeAxis.getStart(),
            endsOutsideView   : origEnd > timeAxis.getEnd(),

            duration : origEnd - origStart,

            bodyScroll : Ext.getBody().getScroll(),
            eventObj   : e // So we can know if SHIFT/CTRL was pressed
        };

        dragData.ddel = this.getDragElement(eventEl, dragData);

        return dragData;
    },

    onStartDrag : function () {
        var s  = this.schedulerView,
            dd = this.dragData;

        // To make sure any elements made visible by hover are not visible when the original element is hidden (using visibility:hidden)
        Ext.Array.each(dd.getEventBarElements(), function (el) {
            el.removeCls('sch-event-hover');
        });

        s.fireEvent('eventdragstart', s, dd.draggedRecords);

        s.getScrollable().on('scroll', this.onViewScroll, this);
    },

    alignElWithMouse : function (el, iPageX, iPageY) {
        this.callParent(arguments);

        // HACK proxy position calculation is off the first drag action in IE with RTL + viewport
        if (Ext.isIE && this.schedulerView.rtl && this.schedulerView.up('[isViewport]')) {
            this.deltaSetXY = null;
            this.callParent(arguments);
        }

        var oCoord = this.getTargetCoord(iPageX, iPageY),
            fly    = el.dom ? el : Ext.fly(el, '_dd');

        // original method limits task position by viewport dimensions
        // our drag proxy is located on secondary canvas and can have height larger than viewport
        // so we have to set position relative to bigger secondary canvas
        this.setLocalXY(
            fly,
            oCoord.x + this.deltaSetXY[0],
            oCoord.y + this.deltaSetXY[1]
        );
    },

    onViewScroll : function (scrollable, left, top) {
        var proxy              = this.proxy,
            s                  = this.schedulerView,
            dd                 = this.dragData,
            rtlWithoutViewport = s.rtl && !s.up('[isViewport]'); // Ext JS has different behavior for viewport case vs non-viewport case.

        this.setVisibilityForSourceEvents(false);

        var xy = proxy.getXY();
        // getScroll works different for RTL case: it will return 0 when view is scrolled max to the right
        var newXY;

        if (rtlWithoutViewport) {
            newXY = [xy[0] - left + dd.prevScroll.left, xy[1] + top - dd.prevScroll.top];
        } else {
            newXY = [xy[0] + left - dd.prevScroll.left, xy[1] + top - dd.prevScroll.top];
        }

        var deltaSetXY = this.deltaSetXY;
        if (rtlWithoutViewport) {
            this.deltaSetXY = [deltaSetXY[0] - left + dd.prevScroll.left, deltaSetXY[1] + top - dd.prevScroll.top];
        } else {
            this.deltaSetXY = [deltaSetXY[0] + left - dd.prevScroll.left, deltaSetXY[1] + top - dd.prevScroll.top];
        }
        dd.prevScroll = { left : left, top : top };

        proxy.setXY(newXY);

        this.onDragOver();
    },


    /**
     * Returns true if the current action is a copy action.
     * @returns {boolean}
     */

    isCopyKeyPressed : function () {
        return Boolean(this.enableCopy && this.dragData.eventObj[this.enableCopyKey.toLowerCase() + 'Key']);
    },

    checkShiftChange : function () {
        var copyKeyPressed = this.isCopyKeyPressed(),
            dd             = this.dragData;

        if (copyKeyPressed !== this.copyKeyPressed) {
            this.copyKeyPressed = copyKeyPressed;

            if (copyKeyPressed) {
                dd.refElements.addCls('sch-event-copy');
                this.setVisibilityForSourceEvents(true);
            } else {
                dd.refElements.removeCls('sch-event-copy');
                this.setVisibilityForSourceEvents(false);
            }
        }
    },


    onKey : function (e) {
        if (this.enableCopy && e.getKey() === e[this.enableCopyKey]) this.checkShiftChange();

        // Simulate abort on ESC key
        if (e.getKey() === e.ESC) {
            this.dragData.ddCallbackArgs = [e.getTarget(), e, this.id];
            this.finalize(false);
            Ext.dd.DragDropManager.stopDrag(e);
            Ext.dd.DragDropManager.stopEvent(e);
        }
    },


    // HACK, overriding private method, proxy needs to be shown before aligning to it
    startDrag : function () {
        Ext.getDoc().on({
            keydown    : this.onKey,
            keyup      : this.onKey,
            useCapture : true,
            scope      : this
        });

        var retVal   = this.callParent(arguments);
        var dragData = this.dragData;

        // This is the representation of the original element inside the proxy
        dragData.refElement  = this.proxy.el.down('.' + this.dragDropProxyCls);
        dragData.refElements = this.proxy.el.select('.sch-event');

        // The dragged element should not be in hover state
        dragData.refElement.removeCls('sch-event-hover');

        if (this.showTooltip) {
            this.tip.update(dragData.origStart, dragData.origEnd, true);
            // Seems required as of Ext 4.1.0, to clear the visibility:hidden style.
            this.tip.setStyle('visibility');
            this.tip.show(dragData.refElement, dragData.offsets[0]);
        }

        this.copyKeyPressed = this.isCopyKeyPressed();

        if (this.copyKeyPressed) {
            dragData.refElements.addCls('sch-event-copy');
            dragData.originalHidden = true;
        }

        return retVal;
    },


    endDrag : function () {
        this.schedulerView.getScrollable().un('scroll', this.onViewScroll, this);

        Ext.getDoc().un({
            keydown    : this.onKey,
            keyup      : this.onKey,
            useCapture : true,
            scope      : this
        });

        this.callParent(arguments);
    },

    onMouseUp : function () {
        if (!this.dragging) {
            // Reset drag proxy position on a simple mouse click (which triggers a change in the 'left' position of the proxy el)
            this.afterDragFinalized();
        }
    },

    afterDragFinalized : function () {
        // https://www.assembla.com/spaces/bryntum/tickets/1524#/activity/ticket:
        // If drag is done close to the edge to invoke scrolling, the proxy could be left there and interfere
        // with the view sizing if the columns are shrunk.
        this.proxy.el.dom.style.top = this.proxy.el.dom.style.left = 0;
    },

    updateRecords : function (context) {
        var me                    = this,
            schedulerView         = me.schedulerView,
            eventStore            = schedulerView.getEventStore(),
            resourceStore         = schedulerView.getResourceStore(),
            assignmentStore       = eventStore.getAssignmentStore(),
            newResource           = context.newResource,
            draggedRecord         = context.draggedRecords[0],
            relatedDraggedRecords = context.draggedRecords.slice(1),
            resourceRecord        = context.resourceRecord,
            copyKeyPressed        = me.isCopyKeyPressed(),
            startDate             = context.startDate,
            timeDiff              = context.timeDiff,
            viewMode              = schedulerView.getMode();

        // Scheduler multiple assignment mode
        if (assignmentStore && eventStore instanceof Sch.data.EventStore) {
            me.updateRecordsMultipleAssignmentMode(startDate, timeDiff, draggedRecord, relatedDraggedRecords, resourceRecord, newResource, eventStore, resourceStore, assignmentStore, copyKeyPressed, viewMode);
        }
        // Gantt mode (and task store instead of event store)
        else if (assignmentStore) {
            me.updateRecordsSingleAssignmentMode(startDate, timeDiff, draggedRecord.getEvent(), Ext.Array.map(relatedDraggedRecords, function (r) {
                return r.getEvent();
            }), resourceRecord, newResource, eventStore, resourceStore, copyKeyPressed, viewMode);
        }
        // Scheduler single assignment mode
        else {
            me.updateRecordsSingleAssignmentMode(startDate, timeDiff, draggedRecord, relatedDraggedRecords, resourceRecord, newResource, eventStore, resourceStore, copyKeyPressed, viewMode);
        }

        // Tell the world there was a successful drop
        schedulerView.fireEvent('eventdrop', schedulerView, context.draggedRecords, copyKeyPressed);
    },

    updateRecordsSingleAssignmentMode : function (startDate, timeDiff, draggedEvent, relatedEvents, fromResource, toResource, eventStore, resourceStore, copy, viewMode) {
        // The code is written to emit as little store events as possible
        var me    = this,
            toAdd = [];

        if (copy) {
            draggedEvent = draggedEvent.fullCopy(null);
            toAdd.push(draggedEvent);
        }

        // Process original dragged record
        draggedEvent.beginEdit();

        // in calendar view resources are just time spans, so we have to skip this part
        if (!copy && toResource !== fromResource && fromResource instanceof Sch.model.Resource && toResource instanceof Sch.model.Resource) {
            draggedEvent.reassign(fromResource, toResource);
        }
        else if (toResource !== fromResource && fromResource instanceof Sch.model.Resource && toResource instanceof Sch.model.Resource) {
            draggedEvent.assign(toResource);
        }

        draggedEvent.setStartDate(startDate, true, eventStore.skipWeekendsDuringDragDrop);
        draggedEvent.endEdit();

        // in calendar view drag&drop doesn't change resource
        if (viewMode !== 'calendar') {
            // Process related records
            var indexDiff = resourceStore.indexOf(fromResource) - resourceStore.indexOf(toResource);

            Ext.Array.each(relatedEvents, function (related) {
                // grabbing resources early, since after ".copy()" the record won't belong to any store
                // and ".getResources()" won't work
                var relatedResources = related.getResources();

                if (copy) {
                    related = related.fullCopy(null);
                    toAdd.push(related);
                }

                related.beginEdit();

                // calculate new startDate (and round it) based on timeDiff
                related.setStartDate(me.adjustStartDate(related.getStartDate(), timeDiff), true, eventStore.skipWeekendsDuringDragDrop);

                indexDiff !== 0 && relatedResources.length && Ext.Array.each(relatedResources, function (r) {
                    var newIndex = resourceStore.indexOf(r) - indexDiff,
                        newResource;

                    if (newIndex < 0) {
                        newIndex = 0;
                    }
                    else if (newIndex >= resourceStore.getCount()) {
                        newIndex = resourceStore.getCount() - 1;
                    }

                    newResource = resourceStore.getAt(newIndex);
                    related.reassign(r, newResource);
                });

                related.endEdit();
            });
        }

        if (toAdd.length) {
            eventStore.append(toAdd);
        }
    },

    updateRecordsMultipleAssignmentMode : function (startDate, timeDiff, draggedAssignment, relatedAssignments, fromResource, toResource, eventStore, resourceStore, assignmentStore, copy, viewMode) {
        var me = this;

        Ext.Array.each([].concat(draggedAssignment, relatedAssignments), function (assignment) {
            var event = assignment.getEvent();

            event.setStartDate(me.adjustStartDate(event.getStartDate(), timeDiff), true, eventStore.skipWeekendsDuringDragDrop);

            // if we dragged the event to a different resource
            if (viewMode != 'calendar' && fromResource !== toResource) {
                if (copy) {
                    event.assign(toResource);
                } else if (!event.isAssignedTo(toResource)) {
                    event.reassign(assignment.getResource(), toResource);
                } else {
                    event.unassign(assignment.getResource());
                }
            }
        });
    },

    isValidDrop : function (oldResource, newResource, sourceRecord) {
        // Not allowed to assign an event twice to the same resource -
        // which might happen when we deal with an assignment store
        if (oldResource !== newResource) {
            // if we operate assignments
            if (sourceRecord instanceof Sch.model.Assignment) {
                return !sourceRecord.getEvent().isAssignedTo(newResource);
            } else {
                return !sourceRecord.isAssignedTo(newResource);
            }
        }

        return true;
    },


    resolveResource : function (xy) {
        var proxyDom   = this.proxy.el.dom;
        var bodyScroll = this.dragData.bodyScroll;

        proxyDom.style.display = 'none';
        var node               = document.elementFromPoint(xy[0] - bodyScroll.left, xy[1] - bodyScroll.top);

        // IE8 likes it twice, for simulated events..
        if (Ext.isIE8 && window.top.Siesta) {
            node = document.elementFromPoint(xy[0] - bodyScroll.left, xy[1] - bodyScroll.top);
        }

        proxyDom.style.display = 'block';

        if (!node) {
            return null;
        }

        var view = this.schedulerView;

        // If we hover a table row border we will match a row element here.
        // We then need to adjust the Y-pos to get a cell which gives us the correct cell index.
        if (node.className.match(Ext.baseCSSPrefix + 'grid-item')) {
            return this.resolveResource([xy[0], xy[1] + 3]);
        }

        if (!node.className.match(view.timeCellCls)) {
            var parent = Ext.fly(node).up('.' + view.timeCellCls);

            if (parent) {
                node = parent.dom;
            } else {
                return null;
            }
        }
        return view.resolveResource(node);
    },

    adjustStartDate : function (startDate, timeDiff) {
        var s = this.schedulerView;

        return s.timeAxis.roundDate(new Date(startDate - 0 + timeDiff), s.snapRelativeToEventStartDate ? startDate : false);
    },

    // private
    updateDragContext : function (e) {
        var dd      = this.dragData,
            xy      = e ? e.getXY() : this.lastXY,
            // Ext JS might convert e.getXY() coordinates to be calculated from right side if Scheduler is in a viewport
            // So for resource lookup we always use the raw browser XY since we rely on document.elementFromPoint
            rawXY   = e ? [e.browserEvent.clientX + document.body.scrollLeft, e.getY()] : this.lastXY;

        if (!dd.refElement) {
            return;
        }

        var s           = this.schedulerView,
            proxyRegion = dd.refElement.getRegion();

        if (s.timeAxis.isContinuous()) {
            if (
                (s.isHorizontal() && this.minX < xy[0] && xy[0] < this.maxX) ||
                (!s.isHorizontal() && this.minY < xy[1] && xy[1] < this.maxY)
            ) {
                var newDate = s.getDateFromCoordinate(this.getCoordinate(xy));

                dd.timeDiff = newDate - dd.sourceDate;
                // calculate and round new startDate based on actual dd.timeDiff
                dd.startDate = this.adjustStartDate(dd.origStart, dd.timeDiff);
                dd.endDate   = new Date(dd.startDate - 0 + dd.duration);
            }

        } else {
            var range = this.resolveStartEndDates(proxyRegion);

            dd.startDate = range.startDate;
            dd.endDate   = range.endDate;

            dd.timeDiff = dd.startDate - dd.origStart;
        }

        dd.newResource = s.constrainDragToResource ? dd.resourceRecord : (this.resolveResource(rawXY) || dd.newResource || dd.resourceRecord);

        if (dd.newResource) {
            var result = !e ||this.validatorFn.call(this.validatorFnScope || this, dd.draggedRecords, dd.newResource, dd.startDate, dd.duration, e);

            if (!result || typeof result === 'boolean') {
                dd.valid   = result !== false;
                dd.message = '';
            } else {
                dd.valid   = result.valid !== false;
                dd.message = result.message;
            }
        } else {
            dd.valid = false;
        }
    },

    /**
     * Provide your custom implementation of this to allow additional selected records to be dragged together with the original one.
     * @param {Ext.data.Model} eventRecord The eventRecord about to be dragged
     * @return {[Ext.data.Model]} An array of event records to drag together with the original event
     */
    getRelatedRecords : function (eventRecord) {
        var view   = this.schedulerView,
            sm     = view.getEventSelectionModel(),
            result = sm.getDraggableSelections();

        return Ext.Array.filter(result, function (selectedRecord) {
            return eventRecord !== selectedRecord;
        });
    },

    /**
     * This function should return a DOM node representing the markup to be dragged. By default it just returns the selected element(s) that are to be dragged.
     * If dragging multiple events, the clone of the original item should be assigned the special CSS class {@link #dragDropProxyCls}
     * @param {Ext.Element} sourceEl The event element that is the source drag element
     * @param {Object} dragData The drag drop context object
     * @return {HTMLElement} The DOM node to drag
     */
    getDragElement : function (sourceEl, dragData) {
        var eventBarEls = dragData.getEventBarElements();
        var copy;
        var retVal;
        var offsetX     = dragData.offsets[0];
        var offsetY     = dragData.offsets[1];
        var side        = this.schedulerView.getHorizontalPositionSide();

        if (eventBarEls.length > 1) {
            var ctEl = Ext.core.DomHelper.createDom({
                tag   : 'div',
                cls   : 'sch-dd-wrap',
                style : { overflow : 'visible' }
            });

            Ext.Array.each(eventBarEls, function (el) {
                copy = el.dom.cloneNode(true);

                copy.id = Ext.id();

                if (el.dom === sourceEl.dom) {
                    // Using Ext fly here seems buggy in Ext 5.0.1
                    copy.className += ' ' + this.dragDropProxyCls;

                    // removing this will fail 012_dragdrop tests in IE8
                    if (Ext.isIE8) {
                        Ext.fly(copy).addCls(this.dragDropProxyCls);
                    }
                }

                ctEl.appendChild(copy);

                var elOffsets = el.getOffsetsTo(sourceEl);

                // Adjust each element offset to the source event element
                copy.style[side] = -elOffsets[0] + 'px';
                copy.style.top     = -elOffsets[1] + 'px';
            }, this);

            retVal = ctEl;
        } else {
            copy    = sourceEl.dom.cloneNode(true);
            copy.id = Ext.id();

            // Special handling for RTL mode
            copy.style.left = (!this.schedulerView.rtl || this.schedulerView.shouldAdjustForRtl() ? -offsetX : (offsetX - sourceEl.getWidth())) + 'px';
            copy.style.top   = -offsetY + 'px';

            // Using Ext fly here seems buggy in Ext 5.0.1
            // removing this will fail 061_dragdrop_sanity test in IE8
            copy.className += ' ' + this.dragDropProxyCls;

            // removing this will fail 012_dragdrop tests in IE8
            if (Ext.isIE8) {
                Ext.fly(copy).addCls(this.dragDropProxyCls);
            }

            retVal = copy;
        }

        // TODO: fix this, it's written as if we will always have 1 element being dragged.
        // If event rendering is not using px values (could be overridden to % values in CSS) we need to
        // put a height in place for the proxy element to look correctly
        if (!sourceEl.dom.style.height) {
            Ext.fly(retVal).setHeight(sourceEl.getHeight());
        }

        return retVal;
    },


    onDragDrop : function (e, id) {

        this.updateDragContext(e);

        var me         = this,
            s          = me.schedulerView,
            target     = me.cachedTarget || Ext.dd.DragDropMgr.getDDById(id),
            dragData   = me.dragData,
            modified   = false,
            doFinalize = true;

        if (me.tip) {
            me.tip.hide();
        }

        // Used later in finalizeDrop
        dragData.ddCallbackArgs = [target, e, id];

        if (dragData.valid && dragData.startDate && dragData.endDate) {
            dragData.finalize = function () {
                me.finalize.apply(me, arguments);
            };

            // Allow implementer to take control of the flow, by returning false from this listener,
            // to show a confirmation popup etc.
            doFinalize = s.fireEvent('beforeeventdropfinalize', me, dragData, e) !== false;

            // Internal validation, making sure all dragged records fit inside the view
            if (doFinalize && me.isValidDrop(dragData.resourceRecord, dragData.newResource, dragData.draggedRecords[0])) {
                modified = (dragData.startDate - dragData.origStart) !== 0 || dragData.newResource !== dragData.resourceRecord;
            }
        }

        if (doFinalize) {
            me.finalize(dragData.valid && modified);
        } else {
            // In case Ext JS sets a very high Z-index, lower it temporarily so it doesn't interfere with popups etc
            me.proxy.el.addCls('sch-before-drag-finalized');
        }
    },

    finalize : function (updateRecords) {
        var me         = this,
            view       = me.schedulerView,
            eventStore = view.getEventStore(),
            dragData   = me.dragData;

        me.proxy.el.removeCls('sch-before-drag-finalized');

        if (updateRecords) {
            // Catch one more edge case, if a taskStore with calendars is used - there is a possible scenario where the UI isn't
            // repainted. In gantt+scheduler demo, move an event in the scheduler a few px and it disappears since Calendar adjusts its start date and scheduler is unaware of this.
            var updated,
                checkerFn = function () {
                    updated = true;
                };

            eventStore.on('update', checkerFn, null, { single : true });
            me.updateRecords(dragData);
            eventStore.un('update', checkerFn, null, { single : true });

            if (!updated) {
                me.onInvalidDrop.apply(me, dragData.ddCallbackArgs);
            } else {
                // For our good friend IE9, the pointer cursor gets stuck without the defer
                if (Ext.isIE9) {
                    me.proxy.el.setStyle('visibility', 'hidden');
                    me._onValidDropTimer = Ext.Function.defer(me.onValidDrop, 10, me, dragData.ddCallbackArgs);
                } else {
                    me.onValidDrop.apply(me, dragData.ddCallbackArgs);
                }
                view.fireEvent('aftereventdrop', view, dragData.draggedRecords);
            }
            me.afterDragFinalized();
        } else {
            me.onInvalidDrop.apply(me, dragData.ddCallbackArgs);
        }

    },


    // HACK: Override for IE, if you drag the task bar outside the window or iframe it crashes (missing e.target)
    // https://www.assembla.com/spaces/bryntum/tickets/716
    onInvalidDrop : function (target, e, id) {
        if (!e) {
            e      = target;
            target = e.getTarget() || document.body;
        }

        if (this.tip) {
            this.tip.hide();
        }

        this.setVisibilityForSourceEvents(true);

        var s           = this.schedulerView,
            retVal      = this.callParent([target, e, id]);

        s.fireEvent('aftereventdrop', s, this.dragData.draggedRecords);

        this.afterDragFinalized();

        return retVal;
    },


    resolveStartEndDates : function (proxyRegion) {
        var dd    = this.dragData,
            startEnd,
            start = dd.origStart,
            end   = dd.origEnd;

        var DATE = Sch.util.Date;

        if (!dd.startsOutsideView) {
            startEnd = this.schedulerView.getStartEndDatesFromRegion(proxyRegion, 'round');
            if (startEnd) {
                start = startEnd.start || dd.startDate;
                end   = DATE.add(start, DATE.MILLI, dd.duration);
            }
        } else if (!dd.endsOutsideView) {
            startEnd = this.schedulerView.getStartEndDatesFromRegion(proxyRegion, 'round');
            if (startEnd) {
                end   = startEnd.end || dd.endDate;
                start = DATE.add(end, DATE.MILLI, -dd.duration);
            }
        }

        return {
            startDate : start,
            endDate   : end
        };
    }

});
