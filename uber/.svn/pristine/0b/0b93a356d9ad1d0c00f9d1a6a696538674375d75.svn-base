/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Sch.mixin.AbstractSchedulerView
@private

A mixin for {@link Ext.view.View} classes, providing "scheduling" functionality to the consuming view. A consuming class
should have already consumed the {@link Sch.mixin.TimelineView} mixin.

Generally, should not be used directly, if you need to subclass the view, subclass the {@link Sch.view.SchedulerGridView} instead.

*/
Ext.define('Sch.mixin.AbstractSchedulerView', {
    requires                : [
        'Sch.model.Assignment',
        'Sch.template.Event',
        'Sch.eventlayout.Horizontal',
        'Sch.view.Vertical',
        'Sch.eventlayout.Vertical'
    ],

    _cmpCls                 : 'sch-schedulerview',
    scheduledEventName      : 'event',
    eventTemplateClass      : 'Sch.template.Event',

    // The template instance responsible for rendering the event bars
    eventTpl                : null,

    /**
    * @cfg {Number} barMargin
    * Controls how much space to leave between stacked event bars in px
    */
    barMargin               : 0,

    /**
    * @cfg {Boolean} constrainDragToResource Set to true to only allow dragging events within the same resource.
    */
    constrainDragToResource : false,

    // Provided by panel
    allowOverlap            : null,
    readOnly                : null,

    altColCls               : 'sch-col-alt',

    /**
    * @cfg {Boolean} dynamicRowHeight
    * True to layout events without overlapping, meaning the row height will be dynamically calculated to fit any overlapping events.
    */
    dynamicRowHeight        : true,

    /**
    * @cfg {Boolean} managedEventSizing
    * True to size events based on the rowHeight and barMargin settings. Set this to false if you want to control height and top properties via CSS instead.
    */
    managedEventSizing      : true,

    /**
    * @cfg {Boolean} eventAnimations
    * True to animate event updates, currently only used in vertical mode in CSS3 enabled browsers.
    */
    eventAnimations         : true,

    /**
     * @cfg {String} horizontalLayoutCls
     * The class name responsible for the horizontal event layout process. Override this to take control over the layout process.
     */
    horizontalLayoutCls     : 'Sch.eventlayout.Horizontal',


    horizontalEventSorterFn     : null,
    /**
     * @cfg {Function} horizontalEventSorterFn
     *
     *  Override this method to provide a custom sort function to sort any overlapping events. By default,
     *  overlapping events are laid out based on the start date. If the start date is equal, events with earlier end date go first.
     *
     *  Here's a sample sort function, sorting on start- and end date. If this function returns -1, then event a is placed above event b.
     *
     horizontalEventSorterFn : function (a, b) {

            var startA = a.getStartDate(), endA = a.getEndDate();
            var startB = b.getStartDate(), endB = b.getEndDate();

            var sameStart = (startA - startB === 0);

            if (sameStart) {
                return endA > endB ? -1 : 1;
            } else {
                return (startA < startB) ? -1 : 1;
            }
        }
     *
     * @param  {Sch.model.Event} a
     * @param  {Sch.model.Event} b
     * @return {Int}
     */

    /**
     * @cfg {String} verticalLayoutCls
     * The class name responsible for the vertical event layout process. Override this to take control over the layout process.
     */

    verticalLayoutCls       : 'Sch.eventlayout.Vertical',

    /**
     * @cfg {Function} verticalEventSorterFn
     * Override this method to provide a custom sort function to sort any overlapping events. By default,
     * overlapping events are laid out based on the start date. If the start date is equal, events with earlier end date go first.
     *
     * If this function returns -1, then event a is placed above event b.
     * See also {@link #horizontalEventSorterFn} for a description.
     * @param {Sch.model.Event} a
     * @param {Sch.model.Event} b
     * @return {Int}
     */

    verticalEventSorterFn     : null,

    eventCls                : 'sch-event',

    verticalViewClass       : 'Sch.view.Vertical',

    eventStore              : null,
    resourceStore           : null,
    eventLayout             : null,

    /**
     * @event eventrepaint
     * Fires after an event has been repainted by the view.
     * @param {Sch.mixin.AbstractSchedulerView} view The view instance
     * @param {Sch.model.Event} event
     * @param {HTMLElement} node The updated DOM representation of the event
     */

    _initializeSchedulerView        : function() {
        var horLayoutCls = Ext.ClassManager.get(this.horizontalLayoutCls);
        var vertLayoutCls = Ext.ClassManager.get(this.verticalLayoutCls);

        this.eventSelector = '.' + this.eventCls;

        this.eventLayout = {};


        this.eventTpl  = this.eventTpl || Ext.create(this.eventTemplateClass, {
            eventPrefix   : this.eventPrefix,
            resizeHandles : this.eventResizeHandles
        });

        if (horLayoutCls) {

            this.eventLayout.horizontal = new horLayoutCls(
                Ext.apply(
                    // this is required for table layout
                    { timeAxisViewModel : this.timeAxisViewModel },
                    {
                        bandIndexToPxConvertFn    : this.horizontal.layoutEventVertically,
                        bandIndexToPxConvertScope : this.horizontal
                    },
                    this.horizontalEventSorterFn ? { sortEvents: this.horizontalEventSorterFn } : {}
                )
            );
        }

        if (vertLayoutCls) {
            this.eventLayout.vertical = new vertLayoutCls(
                Ext.apply(
                    {},
                    { view : this },
                    this.verticalEventSorterFn ? { sortEvents: this.verticalEventSorterFn } : {}
                )
            );
        }

        this.store              = this.store || this.resourceStore;
        this.resourceStore      = this.resourceStore || this.store;
    },

    generateTplData: function (event, resourceRecord, columnIndex) {
        var renderData = this[this.mode].getEventRenderData(event, resourceRecord, columnIndex),
            start       = event.getStartDate(),
            end         = event.getEndDate(),
            internalCls = event.getCls() || '';

        internalCls += ' sch-event-resizable-' + event.getResizable();

        if (event.dirty)                                        internalCls += ' sch-dirty ';
        if (renderData.endsOutsideView)                         internalCls += ' sch-event-endsoutside ';
        if (renderData.startsOutsideView)                       internalCls += ' sch-event-startsoutside ';
        if (this.eventBarIconClsField)                          internalCls += ' sch-event-withicon ';
        if (event.isDraggable() === false)                      internalCls += ' sch-event-fixed ';
        if (end - start === 0)                                  internalCls += ' sch-event-milestone ';
        if (this.getEventSelectionModel().isSelected(event))    internalCls += ' ' + this.selectedEventCls + ' ';

        // in calendar mode event can be rendered in miltiple columns yet it remains the same
        // to distinguish them we append resource index to element id
        renderData.id          = event.internalId + '-' + resourceRecord.internalId + (this.isCalendar() ? ('-' + columnIndex) : '-x' /* this is important for getElement(s)FromEventRecord() */);
        renderData.internalCls = internalCls;
        renderData.start       = start;
        renderData.end         = end;
        renderData.iconCls     = event.data[this.eventBarIconClsField] || (event.getIconCls && event.getIconCls()) || '';
        renderData.event       = event;

        if (this.eventRenderer) {
            // User has specified a renderer fn, either to return a simple string, or an object intended for the eventBodyTemplate
            var value = this.eventRenderer.call(this.eventRendererScope || this, event, resourceRecord, renderData, columnIndex);

            if (this.eventBodyTemplate) {
                renderData.body = this.eventBodyTemplate.apply(value);
            } else {
                renderData.body = value;
            }
        } else if (this.eventBodyTemplate) {
            // User has specified an eventBodyTemplate, but no renderer - just apply the entire event record data.
            renderData.body = this.eventBodyTemplate.apply(event.data);
        } else if (this.eventBarTextField) {
            // User has specified a field in the data model to read from
            renderData.body = event.data[this.eventBarTextField] || '';
        }
        return renderData;
    },

    /**
    * Resolves the resource based on a dom element
    * @param {HtmlElement} node The HTML element
    * @return {Sch.model.Resource} The resource corresponding to the element, or null if not found.
    */
    resolveResource: function (node) {
        var me = this;
        return me[me.mode].resolveResource(node);
    },

    /**
    * Gets the Ext.util.Region representing the passed resource and optionally just for a certain date interval.
    * @param {Sch.model.Resource} resourceRecord The resource record
    * @param {Date} startDate A start date constraining the region
    * @param {Date} endDate An end date constraining the region
    * @return {Ext.util.Region} The region of the resource
    */
    getResourceRegion: function (resourceRecord, startDate, endDate) {
        return this[this.mode].getResourceRegion(resourceRecord, startDate, endDate);
    },

    /**
    * <p>Returns the event record for a DOM element </p>
    * @param {HTMLElement/Ext.Element} el The DOM node or Ext Element to lookup
    * @return {Sch.model.Event|Null} The event record
    */
    resolveEventRecord: function (el) {
        // Normalize to DOM node
        el = el.dom ? el.dom : el;

        if (!(Ext.fly(el).is(this.eventSelector))) {
            el = Ext.fly(el).up(this.eventSelector);
        }

        return el && this.getEventRecordFromDomId(el.id);
    },

    // TODO: Get rid of this, make it in inline?, move it to mixins/SchedulerView
    resolveEventRecordFromResourceRow: function (el) {
        var me = this,
            sm = me.getEventSelectionModel(),
            resource,
            event;

        el = el.dom ? el.dom : el;
        resource = me.getRecord(el);

        return sm.getFirstSelectedEventForResource(resource);
    },


    /**
    * Returns an assignment record for a DOM element
    *
    * @param {HTMLElement/Ext.Element} el The DOM node or Ext Element to lookup
    * @return {Sch.model.Assignment|Null} The assignment record
    */
    resolveAssignmentRecord : function(el) {
        var me = this,
            assignmentStore = me.getEventStore().getAssignmentStore(),
            assignment = null,
            event,
            resource;

        if (assignmentStore) {
            event = me.getEventRecordFromDomId(el.id);
            resource = me.getResourceRecordFromDomId(el.id);

            if (event && resource) {
                assignment = assignmentStore.getAssignmentForEventAndResource(event, resource);
            }
        }

        return assignment;
    },

    /**
    * <p>Returns the event record for a DOM id </p>
    * @param {String} id The id of the DOM node
    * @return {Sch.model.Event} The event record
    */
    getEventRecordFromDomId: function(id) {
        id = this.getEventIdFromDomNodeId(id);

        return this.getEventStore().getModelByInternalId(id);
    },

    /**
     * Returns a resource record for a DOM id
     * @param {String} id An id of an event DOM node
     * @return {Sch.model.Resource} A resource record
     */
    getResourceRecordFromDomId : function(id) {
        id = this.getResourceIdFromDomNodeId(id);
        return this.getResourceStore().getByInternalId(id);
    },

    /**
    * Checks if a date range is allocated or not for a given resource.
    * @param {Date} start The start date
    * @param {Date} end The end date
    * @param {Sch.model.Event} excludeEvent An event to exclude from the check (or null)
    * @param {Sch.model.Resource} resource The resource
    * @return {Boolean} True if the timespan is available for the resource
    */
    isDateRangeAvailable: function (start, end, excludeEvent, resource) {
        return this.getEventStore().isDateRangeAvailable(start, end, excludeEvent, resource);
    },

    /**
    * Returns events that are (partly or fully) inside the timespan of the current view.
    * @return {Ext.util.MixedCollection} The collection of events
    */
    getEventsInView: function () {
        var viewStart = this.timeAxis.getStart(),
            viewEnd = this.timeAxis.getEnd();

        return this.getEventStore().getEventsInTimeSpan(viewStart, viewEnd);
    },

    /**
    * Returns the current set of rendered event nodes
    * @return {Ext.CompositeElement} The collection of event nodes
    */
    getEventNodes: function () {
        return this.getEl().select(this.eventSelector);
    },

    /**
     * Highlights one or more events in the current timeline view
     *
     * @param {Sch.model.Range/[Sch.model.Range]} date Center date for the viewport.
     */
    highlightEvents : function(eventRecords) {
        var me     = this;
        var elements = [];

        Ext.Array.each([].concat(eventRecords), function(ev) {
            elements.push.apply(elements, me.getElementsFromEventRecord(ev, null, null, true));
        });

        Ext.Array.each([].concat(elements), function(el) {
            Ext.fly(el).addCls('sch-event-highlighted');
        });
    },

    /**
     * Highlights events in the current timeline view that match the passed filter function
     *
     * @param {Sch.model.Range/[Sch.model.Range]} date Center date for the viewport.
     */
    highlightEventsBy : function(fn, scope) {
        var events = this.getEventsInView();

        this.highlightEvents(events.filterBy(fn, scope).getRange());
    },

    /**
     * Clears the highlight of all events
     *
     * @param {Sch.model.Range/[Sch.model.Range]} date Center date for the viewport.
     */
    clearHighlightedEvents : function(fn, scope) {
        this.getEl().select('.sch-event-highlighted').removeCls('sch-event-highlighted');
    },


    onEventCreated: function (newEventRecord) {
        // Empty but provided so that you can override it to supply default record values etc.
    },

    getEventStore: function () {
        return this.eventStore;
    },

    registerEventEditor: function (editor) {
        this.eventEditor = editor;
    },

    getEventEditor: function () {
        return this.eventEditor;
    },

    // Call mode specific implementation
    onEventUpdate: function (store, model, operation) {
        this[this.mode].onEventUpdate(store, model, operation);
    },

    // Call mode specific implementation
    onEventAdd: function (s, recs) {
        // TreeStore 'insert' and 'append' events pass a single Model instance, not an array
        if (!Ext.isArray(recs)) recs = [recs];

        this[this.mode].onEventAdd(s, recs);
    },

    // Call mode specific implementation
    onAssignmentAdd : function(store, assignments) {
        var me = this;

        Ext.Array.each(assignments, function(assignment) {
            var resource = assignment.getResource();
            resource && me.repaintEventsForResource(resource);
        });
    },

    onAssignmentUpdate : function(store, assignment) {
        var me            = this,
            oldResourceId = assignment.previous && assignment.previous[assignment.resourceIdField],
            newResourceId = assignment.getResourceId(),
            oldResource,
            newResource;

        if (oldResourceId) {
            oldResource = me.getResourceStore().getModelById(oldResourceId);
            me.repaintEventsForResource(oldResource);
        }

        if (newResourceId) {
            newResource = me.getResourceStore().getModelById(newResourceId);
            me.repaintEventsForResource(newResource);
        }
    },

    onAssignmentRemove : function(store, assignments) {
        var me = this;

        Ext.Array.each(assignments, function(assignment) {
            var resourceId = assignment.getResourceId();
            var resource = resourceId && me.getResourceStore().getModelById(resourceId);
            resource && me.repaintEventsForResource(resource);
        });
    },

    // Call orientation specific implementation
    onEventRemove: function (s, recs) {
        this[this.mode].onEventRemove(s, recs);
    },

    setEventStore: function (eventStore, initial) {

        var me          = this;
        var oldStore     = me.getEventStore();
        var listenerCfg = {
            scope       : me,
            refresh     : me.onEventDataRefresh,

            // Sencha Touch
            addrecords      : me.onEventAdd,
            updaterecord    : me.onEventUpdate,
            removerecords   : me.onEventRemove,

            // Ext JS
            add         : me.onEventAdd,
            update      : me.onEventUpdate,
            remove      : me.onEventRemove,

            // If the eventStore is a TreeStore
            nodeinsert  : me.onEventAdd,
            nodeappend  : me.onEventAdd
        };

        // In case there is an assigment store used
        var assignmentListenerCfg = {
            scope       : me,
            refresh     : me.onEventDataRefresh,
            load        : me.onEventDataRefresh,
            update      : me.onAssignmentUpdate,
            add         : me.onAssignmentAdd,
            remove      : me.onAssignmentRemove
        };

        // Sencha Touch fires "refresh" when clearing the store. Avoid double repaints
        if (!Ext.versions.touch) {
            listenerCfg.clear = me.onEventDataRefresh;
        }

        if (!initial && me.eventStore) {
            me.eventStore.setResourceStore(null);

            if (eventStore !== me.eventStore && me.eventStore.autoDestroy) {
                me.eventStore.destroy();
            }
            else {
                if (me.mun) {
                    me.mun(me.eventStore, listenerCfg);

                    var oldAssignmentStore = me.eventStore.getAssignmentStore();

                    if (oldAssignmentStore) {
                        me.mun(oldAssignmentStore, assignmentListenerCfg);
                    }
                } else {
                    me.eventStore.un(listenerCfg);
                }
            }

            if (!eventStore) {
                me.eventStore = null;
            }
        }
        if (eventStore) {
            eventStore = Ext.data.StoreManager.lookup(eventStore);

            if (me.mon) {
                me.mon(eventStore, listenerCfg);
            } else {
                eventStore.on(listenerCfg);
            }

            me.eventStore = eventStore;

            eventStore.setResourceStore(me.getResourceStore());

            var assignmentStore = eventStore.getAssignmentStore();

            if (assignmentStore) {
                me.mon(assignmentStore, assignmentListenerCfg);
            }
        }

        if (eventStore && !initial) {
            this.getTimeAxisViewModel().setEventStore(eventStore);
            this.getEventSelectionModel().bindStore(eventStore);

            this.fireEvent('eventstorechange', this, eventStore, oldStore);

            me.refreshView();
        }
    },

    onEventDataRefresh: function () {
        this.refreshKeepingScroll();
    },

    // invoked by the selection model to maintain visual UI cues
    onEventBarSelect: function (record) {
        var me = this,
            event,
            resource;

        if (record instanceof Sch.model.Assignment) {
            event = record.getEvent();
            resource = record.getResource();
        }
        else {
            event = record;
            resource = null;
        }

        Ext.Array.each(me.getElementsFromEventRecord(event, resource), function(el) {
            el.addCls(me.selectedEventCls);
        });
    },

    // invoked by the selection model to maintain visual UI cues
    onEventBarDeselect: function (record) {
        var me = this,
            event,
            resource;

        if (record instanceof Sch.model.Assignment) {
            event = record.getEvent();
            resource = record.getResource();
        }
        else {
            event = record;
            resource = null;
        }

        event && Ext.Array.each(me.getElementsFromEventRecord(event, resource), function(el) {
            el.removeCls(me.selectedEventCls);
        });
    },

    refresh : function() {
        throw 'Abstract method call';
    },

    /**
    * Refreshes the events for a single resource
    * @param {Sch.model.Resource} resource
    */
    repaintEventsForResource : function (record) {
        throw 'Abstract method call';
    },

    /**
     * Refreshes all events in the scheduler view
     */
    repaintAllEvents : function () {
        this.refreshKeepingScroll();
    },

    /**
     * Scrolls an event record into the viewport.
     * If the resource store is a tree store, this method will also expand all relevant parent nodes to locate the event.
     *
     * @param {Sch.model.Event} eventRec, the event record to scroll into view
     * @param {Boolean/Object} highlight, either `true/false` or a highlight config object used to highlight the element after scrolling it into view
     * @param {Boolean/Object} animate, either `true/false` or an animation config object used to scroll the element
     *
     * @deprecated
     */
    scrollEventIntoView: function (eventRec, highlight, animate, callback, scope) {
        var me = this,
            resources = eventRec.getResources();

        resources.length && me.scrollResourceEventIntoView(resources[0], eventRec, null, highlight, animate, callback, scope);
    },

    getResourceStore : function() {
        return this.resourceStore;
    },

    setResourceStore : function(store) {
        var oldStore = this.resourceStore;

        this.resourceStore = store;

        if (store){
            this.fireEvent('resourcestorechange', this, store, oldStore);
        }
    }
});
