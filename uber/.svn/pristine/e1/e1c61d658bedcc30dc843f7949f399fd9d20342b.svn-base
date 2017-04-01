/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

 @class Sch.mixin.SchedulerView

 A mixin for {@link Ext.view.View} classes, providing "scheduling" functionality to the consuming view. A consuming class
 should have already consumed the {@link Sch.mixin.TimelineView} mixin.

 Generally, should not be used directly, if you need to subclass the view, subclass the {@link Sch.view.SchedulerGridView} instead.

 */
Ext.define('Sch.mixin.SchedulerView', {
    extend : 'Sch.mixin.AbstractSchedulerView',

    mixins : ['Sch.mixin.Localizable'],

    requires : [
        'Sch.patches.DragDropManager',
        'Sch.patches.NavigationModel',
        'Sch.patches.NavigationModel6_0_2',
        'Sch.feature.DragCreator',
        'Sch.feature.DragDrop',
        'Sch.feature.ResizeZone',
        'Sch.column.Resource',
        'Sch.column.Day',
        'Sch.view.Calendar',
        'Ext.XTemplate'
    ],

    /**
     * @property {Sch.feature.SchedulerDragZone} eventDragZone
     * Accessor to the event dragzone (available only if the drag drop feature is enabled)
     */

    /**
     * @cfg {String} eventResizeHandles Defines which resize handles to use. Possible values: 'none', 'start', 'end', 'both'. Defaults to 'end'
     */
    eventResizeHandles : 'end',

    /**
     * An empty function by default, but provided so that you can perform custom validation on
     * the item being dragged. This function is called during a drag and drop process and also after the drop is made.
     * To control what 'this' points to inside this function, use
     * {@link Sch.panel.TimelineGridPanel#validatorFnScope} or {@link Sch.panel.TimelineTreePanel#validatorFnScope}.
     * @param {Sch.model.Event[]} dragRecords an array containing the records for the events being dragged
     * @param {Sch.model.Resource} targetResourceRecord the target resource of the the event
     * @param {Date} date The date corresponding to the drag proxy position
     * @param {Number} duration The duration of the item being dragged in milliseconds
     * @param {Event} e The event object
     * @return {Boolean/Object} true if the drop position is valid, else false to prevent a drop. Or return an object containing a 'valid' boolean and a 'message' string.
     */
    dndValidatorFn : Ext.emptyFn,

    /**
     * An empty function by default, but provided so that you can perform custom validation on
     * an item being resized. To control what 'this' points to inside this function, use
     * {@link Sch.panel.TimelineGridPanel#validatorFnScope} or {@link Sch.panel.TimelineTreePanel#validatorFnScope}.
     * @param {Sch.model.Resource} resourceRecord the resource of the row in which the event is located
     * @param {Sch.model.Event} eventRecord the event being resized
     * @param {Date} startDate
     * @param {Date} endDate
     * @param {Event} e The event object
     * @return {Boolean/Object} true if the resize state is valid, else false to prevent the action. Or return an object containing a 'valid' boolean and a 'message' string.
     */
    resizeValidatorFn : Ext.emptyFn,

    /**
     * An empty function by default, but provided so that you can perform custom validation on the item being created.
     * To control what 'this' points to inside this function, use
     * {@link Sch.panel.TimelineGridPanel#validatorFnScope} or {@link Sch.panel.TimelineTreePanel#validatorFnScope}.
     * @param {Sch.model.Resource} resourceRecord the resource for which the event is being created
     * @param {Date} startDate
     * @param {Date} endDate
     * @param {Event} e The event object
     * @return {Boolean/Object} true if the state is valid, else false to prevent the action. Or return an object containing a 'valid' boolean and a 'message' string.
     */
    createValidatorFn : Ext.emptyFn,

    // Scheduled events: click events --------------------------
    /**
     * @event eventclick
     * Fires when an event is clicked
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event} eventRecord The event record of the clicked event
     * @param {Ext.event.Event} e The event object
     */
    /**
     * @event eventlongpress
     * Fires when an event is longpressed
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event} eventRecord The event record of the clicked event
     * @param {Ext.event.Event} e The event object
     */

    /**
     * @event eventmousedown
     * Fires when a mousedown event is detected on a rendered event
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event} eventRecord The event record
     * @param {Ext.event.Event} e The event object
     */

    /**
     * @event eventmouseup
     * Fires when a mouseup event is detected on a rendered event
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event} eventRecord The event record
     * @param {Ext.event.Event} e The event object
     */

    /**
     * @event eventdblclick
     * Fires when an event is double clicked
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event} eventRecord The event record of the clicked event
     * @param {Ext.event.Event} e The event object
     */

    /**
     * @event eventcontextmenu
     * Fires when contextmenu is activated on an event
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event} eventRecord The event record of the clicked event
     * @param {Ext.event.Event} e The event object
     */

    /**
     * @event eventmouseenter
     * Fires when the mouse moves over an event
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event} eventRecord The event record
     * @param {Ext.event.Event} e The event object
     */
    /**
     * @event eventmouseout
     * Fires when the mouse moves out of an event
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event} eventRecord The event record
     * @param {Ext.event.Event} e The event object
     */

    // Resizing events start --------------------------
    /**
     * @event beforeeventresize
     * Fires before a resize starts, return false to stop the execution
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event} record The record about to be resized
     * @param {Ext.event.Event} e The event object
     */

    /**
     * @event eventresizestart
     * Fires when resize starts
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event} record The event record being resized
     */

    /**
     * @event eventpartialresize
     * Fires during a resize operation and provides information about the current start and end of the resized event
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event} record The event record being resized
     * @param {Date} startDate The new start date of the event
     * @param {Date} endDate The new end date of the event
     * @param {Ext.Element} element The proxy element being resized
     */

    /**
     * @event beforeeventresizefinalize
     * Fires before a succesful resize operation is finalized. Return false from a listener function to prevent the finalizing to
     * be done immedieately, giving you a chance to show a confirmation popup before applying the new values.
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Object} resizeContext An object containing, 'start', 'end', 'newResource' properties.
     * @param {Ext.event.Event} e The event object
     */

    /**
     * @event eventresizeend
     * Fires after a succesful resize operation
     * @param {Mixed} view The scheduler view instance
     * @param {Sch.model.Event} record The updated event record
     */
    // Resizing events end --------------------------

    // Dnd events start --------------------------
    /**
     * @event beforeeventdrag
     * Fires before a dnd operation is initiated, return false to cancel it
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event} record The record corresponding to the node that's about to be dragged
     * @param {Ext.event.Event} e The event object
     */

    /**
     * @event eventdragstart
     * Fires when a dnd operation starts
     * @param {Sch.mixin.SchedulerView} scheduler The scheduler object
     * @param {Array} records the records being dragged
     */

    /**
     * @event beforeeventdropfinalize
     * Fires before a succesful drop operation is finalized.
     * @param {Sch.feature.SchedulerDragZone} dragZone The scheduler dragzone. Scheduler view instance: dragZone.schedulerView.
     * @param {Object} dragContext An object containing, 'start', 'end', 'newResource' properties.
     * @param {Ext.event.Event} e The event object
     */

    /**
     * @event eventdrop
     * Fires after a succesful drag and drop operation
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event[]} records the affected records (if copies were made, they were not inserted into the store)
     * @param {Boolean} isCopy True if the records were copied instead of moved
     */

    /**
     * @event aftereventdrop
     * Fires when after a drag n drop operation, even when drop was performed on an invalid location
     * @param {Mixed} view The scheduler view instance
     */
    // Dnd events end --------------------------

    // Drag create events start --------------------------
    /**
     * @event beforedragcreate
     * Fires before a drag starts, return false to stop the execution
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Resource} resource The resource record
     * @param {Date} date The clicked date on the timeaxis
     * @param {Ext.event.Event} e The event object
     */

    /**
     * @event dragcreatestart
     * Fires before a drag starts, return false to stop the execution
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Ext.Element} el The proxy element
     */

    /**
     * @event beforedragcreatefinalize
     * Fires before a succesful resize operation is finalized. Return false from a listener function to prevent the finalizing to
     * be done immedieately, giving you a chance to show a confirmation popup before applying the new values.
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Object} createContext An object containing, 'start', 'end', 'resourceRecord' properties.
     * @param {Ext.event.Event} e The event object
     * @param {Ext.Element} el The proxy element
     */

    /**
     * @event dragcreateend
     * Fires after a successful drag-create operation
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event} newEventRecord The newly created event record (added to the store in onEventCreated method)
     * @param {Sch.model.Resource} resource The resource record to which the event belongs
     * @param {Ext.event.Event} e The event object
     * @param {Ext.Element} el The proxy element
     */

    /**
     * @event afterdragcreate
     * Always fires after a drag-create operation
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Ext.Element} el The proxy element
     */
    // Drag create events end --------------------------

    /**
     * @event beforeeventadd
     * Fires after a successful drag-create operation, before the new event is added to the store. Return false to prevent the event from being added to the store.
     * @param {Sch.mixin.SchedulerView} view The scheduler view instance
     * @param {Sch.model.Event} newEventRecord The newly created event record
     * @param {[Sch.model.Resource]} resources The resources to which the event is assigned
     */

    /**
     * @event scheduleclick
     * Fires after a click on the schedule area
     * @param {Sch.mixin.SchedulerView} schedulerView The scheduler view object
     * @param {Date} clickedDate The clicked date
     * @param {Number} rowIndex The row index
     * @param {Sch.model.Resource} resource The resource, an event occured on
     * @param {Ext.event.Event} e The event object
     */

    /**
     * @event scheduledblclick
     * Fires after a doubleclick on the schedule area
     * @param {Sch.mixin.SchedulerView} schedulerView The scheduler view object
     * @param {Date} clickedDate The clicked date
     * @param {Number} rowIndex The row index
     * @param {Sch.model.Resource} resource The resource, an event occured on
     * @param {Ext.event.Event} e The event object
     */

    /**
     * @event schedulecontextmenu
     * Fires after a context menu click on the schedule area
     * @param {Sch.mixin.SchedulerView} schedulerView The scheduler view object
     * @param {Date} clickedDate The clicked date
     * @param {Number} rowIndex The row index
     * @param {Sch.model.Resource} resource The resource, an event occured on
     * @param {Ext.event.Event} e The event object
     */

    /**
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

     - loadingText : 'Loading events...'
     */

    calendarViewClass           : 'Sch.view.Calendar',
    lockedGridDependsOnSchedule : null,

    _initializeSchedulerView : function () {
        this.callParent(arguments);

        this.on({
            destroy     : this._destroy,
            afterrender : this._afterRender,
            itemupdate  : this.onRowUpdated,
            scope       : this
        });

        // https://www.assembla.com/spaces/bryntum/tickets/2567
        // 092_rowheight
        // cache has to be cleared on time span change in order to draw correct row height for empty rows
        this.timeAxis.on('beginreconfigure', this.clearRowHeightCache, this);

        // HACK
        // 015_buffered_scroll fails in IE and FF because end row in normal view may have
        // inline style, added by sencha row synchronizer, that will break rows sync
        // TODO: When Ext 5.1.2 is out - try removing this hack
        if (Ext.getVersion().isGreaterThan('5.1.1')) {
            this.on('itemadd', function (records) {
                var el = this.all.item(this.all.endIndex - records.length);
                if (el) {
                    el.dom.style.height = '';
                }
            });
        }
        // ENDHACK

        var me = this;

        if (!this.eventPrefix) {
            throw 'eventPrefix missing';
        }

        this.on({
            resourcestorechange   : this.clearRowHeightCache,
            assignmentstorechange : this.clearRowHeightCache,
            eventstorechange      : this.clearRowHeightCache,
            scope                 : this
        });
    },

    inheritables : function () {
        return {

            // Configuring underlying grid view
            loadingText     : this.L('loadingText'),
            overItemCls     : '',
            trackOver       : false,
            selectedItemCls : '', // We don't want row selection visible here
            // EOF: Configuring underlying grid view

            setReadOnly : function (readOnly) {
                if (this.dragCreator) {
                    this.dragCreator.setDisabled(readOnly);
                }
                this.callParent(arguments);
            },

            repaintEventsForResource : function (resourceRecord, refreshSelections) {
                var me           = this,
                    mode         = me.getMode(),
                    isHorizontal = mode === 'horizontal',
                    // For vertical, we always repaint all events (do per-column repaint is not supported)
                    index        = isHorizontal ? me.indexOf(resourceRecord) : 0;

                if (isHorizontal) {
                    me.eventLayout.horizontal.clearCache(resourceRecord);
                }

                if (index >= 0) {
                    // HACK, Ext insists on performing layouts when refreshing a grid row.
                    // Prevent this, "should be" safe.
                    Ext.suspendLayouts();

                    // we operate "resourceRecord" here since "index" might be incorrect when grouping plugin is used
                    if (isHorizontal) {
                        me.refreshNode(resourceRecord);

                        if (me.lockedGridDependsOnSchedule) {
                            me.lockingPartner.refreshNode(resourceRecord);
                        }
                    }
                    // Use index here to keep vertical view only refreshing first row always
                    else {
                        me.refreshNode(index);
                    }

                    Ext.resumeLayouts();

                    if (refreshSelections) {
                        var sm     = me.getEventSelectionModel();
                        var events = me.getEventStore().getEventsForResource(resourceRecord);

                        Ext.Array.each(events, function (ev) {
                            sm.forEachEventRelatedSelection(ev, function (selectedRecord) {
                                me.onEventBarSelect(selectedRecord, true);
                            });
                        });
                    }
                }
            },

            repaintAllEvents : function () {
                if (this.mode === 'horizontal') {
                    this.refreshView();
                } else {
                    // All events are rendered in first row, no need to do a full refresh
                    this.refreshNode(0);
                }
            },


            handleScheduleEvent : function (e) {
                var te = e.getTarget('.' + this.eventCls, 3),
                    t  = !te && e.getTarget('.' + this.timeCellCls, 3);

                if (t) {
                    var clickedDate  = this.getDateFromDomEvent(e, 'floor');
                    var resourceNode = this.findRowByChild(t);
                    var index        = this.indexOf(resourceNode);

                    var resource;

                    if (this.mode == 'horizontal') {
                        resource = this.getRecordForRowNode(resourceNode);
                    } else {
                        var cellNode = e.getTarget(this.timeCellSelector, 5);

                        if (cellNode) {
                            var cellIndex = typeof cellNode.cellIndex == 'number' ? cellNode.cellIndex : cellNode.getAttribute('data-cellIndex');
                            var header    = this.headerCt.getGridColumns()[cellIndex];

                            resource = header && header.model;
                        }
                    }

                    if (e.type.indexOf('pinch') >= 0) {
                        this.fireEvent('schedule' + e.type, this, e);
                    } else {
                        this.fireEvent('schedule' + e.type, this, clickedDate, index, resource, e);
                    }
                }
            },


            onEventDataRefresh : function () {
                this.clearRowHeightCache();
                this.callParent(arguments);
            },


            onUnbindStore : function (store) {
                store.un({
                    refresh : this.clearRowHeightCache,
                    clear   : this.clearRowHeightCache,
                    load    : this.clearRowHeightCache,

                    scope : this
                });
                this.callParent(arguments);
            },

            // our listeners must go before any other listeners, that's why we override the 'bindStore'
            // instead of `onBindStore`
            bindStore : function (store) {
                store && store.on({
                    refresh : this.clearRowHeightCache,
                    clear   : this.clearRowHeightCache,
                    load    : this.clearRowHeightCache,

                    scope : this
                });
                this.callParent(arguments);
            },

            refreshKeepingScroll : function () {

                this.lockingPartner.saveScrollState();
                this.lockingPartner.refreshView();

                this.callParent(arguments);
                this.lockingPartner.restoreScrollState();
            }
        };
    },

    /**
     * Returns the selection model being used, and creates it via the configuration
     * if it has not been created already.
     * @return {Sch.selection.EventModel} selModel
     */
    getEventSelectionModel : function () {
        var me                = this,
            eventSelModel     = me.eventSelModel,
            eventSelModelType = me.eventSelModelType,
            mode;

        // already has the event selection model
        if (eventSelModel && eventSelModel.events) {
            return eventSelModel;
        }

        if (!eventSelModel) {
            eventSelModel = {};
        }

        if (!eventSelModelType && me.getEventStore().getAssignmentStore()) {
            eventSelModelType = 'assignmentmodel';
        }
        else if (!eventSelModelType) {
            eventSelModelType = 'eventmodel';
        }

        mode = 'SINGLE';

        if (me.simpleSelect) {
            mode = 'SIMPLE';
        } else if (me.multiSelect) {
            mode = 'MULTI';
        }

        Ext.applyIf(eventSelModel, {
            allowDeselect : me.allowDeselect,
            mode          : mode
        });

        if (!eventSelModel.events) {
            eventSelModel = me.eventSelModel = Ext.create('selection.' + eventSelModelType, eventSelModel);
        }

        // lock the selection model if user
        // has disabled selection
        if (me.disableSelection) {
            eventSelModel.locked = true;
        }

        return eventSelModel;
    },

    _afterRender : function () {
        this.setEventStore(this.eventStore, true);

        this.getEventSelectionModel().bindToView(this);

        this.setupEventListeners();

        this.configureFunctionality();

        var resizer = this.headerCt.resizer;

        if (resizer) {
            resizer.doResize = Ext.Function.createSequence(resizer.doResize, this.afterHeaderResized, this);
        }

        // Delete the hoveredEventNode (last item we hovered over) so that after a drag drop the UI considers the
        // mouse to be over the current element
        this.on('itemupdate', function () {
            this.hoveredEventNode = null;
        });
    },

    // private, clean up
    _destroy : function () {
        this.setEventStore(null);
    },


    clearRowHeightCache : function () {
        if (this.mode === 'horizontal') {
            this.eventLayout.horizontal.clearCache();
        }
    },


    configureFunctionality : function () {
        var vfScope = this.validatorFnScope || this;

        if (this.eventResizeHandles !== 'none' && Sch.feature.ResizeZone) {
            this.resizePlug = new Sch.feature.ResizeZone(Ext.applyIf({
                schedulerView : this,

                validatorFn : function (resourceRecord, eventRecord, startDate, endDate) {
                    return (this.allowOverlap || this.isDateRangeAvailable(startDate, endDate, eventRecord, resourceRecord)) &&
                        this.resizeValidatorFn.apply(vfScope, arguments);
                },

                validatorFnScope : this
            }, this.resizeConfig || {}));
        }

        if (this.enableEventDragDrop !== false && Sch.feature.DragDrop) {

            this.dragdropPlug = new Sch.feature.DragDrop(this, {
                validatorFn : function (dragRecords, targetResourceRecord, date, duration) {
                    return (this.allowOverlap || this.isDateRangeAvailable(date, Sch.util.Date.add(date, Sch.util.Date.MILLI, duration), dragRecords[0], targetResourceRecord)) &&
                        this.dndValidatorFn.apply(vfScope, arguments);
                },

                validatorFnScope : this,

                dragConfig : this.dragConfig || {}
            });
        }

        if (this.enableDragCreation !== false && Sch.feature.DragCreator) {
            this.dragCreator = new Sch.feature.DragCreator(Ext.applyIf({
                schedulerView    : this,
                disabled         : this.readOnly,
                validatorFn      : function (resourceRecord, startDate, endDate) {
                    return (this.allowOverlap || this.isDateRangeAvailable(startDate, endDate, null, resourceRecord)) &&
                        this.createValidatorFn.apply(vfScope, arguments);
                },
                validatorFnScope : this
            }, this.createConfig || {}));
        }
    },

    // ---------------------------------------
    // Interaction listeners

    onBeforeDragDrop : function (s, rec, e) {
        return !this.readOnly && !e.getTarget().className.match('sch-resizable-handle');
    },

    onDragDropStart : function () {
        if (this.dragCreator) {
            this.dragCreator.setDisabled(true);
        }

        if (this.tip) {
            this.tip.hide();
            this.tip.disable();
        }

        if (this.overScheduledEventClass) {
            this.setMouseOverEnabled(false);
        }

        // To be able to refresh last hovered node
        this.hoveredEventNode = null;
    },

    onDragDropEnd : function () {
        if (this.dragCreator) {
            this.dragCreator.setDisabled(false);
        }

        if (this.tip) {
            this.tip.enable();
        }

        if (this.overScheduledEventClass) {
            this.setMouseOverEnabled(true);
        }
    },

    onBeforeDragCreate : function (s, resourceRecord, date, e) {
        return !this.readOnly && !e.ctrlKey;
    },

    onDragCreateStart : function () {
        if (this.overScheduledEventClass) {
            this.setMouseOverEnabled(false);
        }

        if (this.tip) {
            this.tip.hide();
            this.tip.disable();
        }

        // While dragging to create an event, we don't want the scroller to interfere
        this.disableViewScroller(true);
    },

    onDragCreateEnd : function (s, newEventRecord, resourceRecord) {
        // If an event editor is defined, it has to manage how/if/when the event is added to the event store
        if (!this.getEventEditor()) {
            // we may not have any resources if we're in Calendar view mode
            var resources = resourceRecord ? [resourceRecord] : [];

            if (this.fireEvent('beforeeventadd', this, newEventRecord, resources) !== false) {
                this.onEventCreated(newEventRecord);
                this.getEventStore().append(newEventRecord);

                // In calendar mode both axes are time so resourceRecord is object {start: foo, end: bar}
                // so we do not need assign this resource to event
                if (this.getMode() != 'calendar') {
                    newEventRecord.assign(resourceRecord);
                }
            }
            this.dragCreator.getProxy().hide();
        }

        if (this.overScheduledEventClass) {
            this.setMouseOverEnabled(true);
        }
    },

    // Empty but provided so that you can override it to supply default record values etc.
    onEventCreated : function (newEventRecord) {
    },

    onAfterDragCreate : function () {
        if (this.overScheduledEventClass) {
            this.setMouseOverEnabled(true);
        }

        if (this.tip) {
            this.tip.enable();
        }

        this.disableViewScroller(false);
    },

    onBeforeResize : function () {
        return !this.readOnly;
    },

    onResizeStart : function () {
        if (this.tip) {
            this.tip.hide();
            this.tip.disable();
        }

        if (this.dragCreator) {
            this.dragCreator.setDisabled(true);
        }

        // While dragging to create an event, we don't want the scroller to interfere
        this.disableViewScroller(true);
    },

    onResizeEnd : function () {
        if (this.tip) {
            this.tip.enable();
        }

        if (this.dragCreator) {
            this.dragCreator.setDisabled(false);
        }

        // While dragging to create an event, we don't want the scroller to interfere
        this.disableViewScroller(false);
    },

    // EOF Interaction listeners
    // ---------------------------------------


    setupEventListeners : function () {
        this.on({
            beforeeventdrag : this.onBeforeDragDrop,
            eventdragstart  : this.onDragDropStart,
            aftereventdrop  : this.onDragDropEnd,

            beforedragcreate : this.onBeforeDragCreate,
            dragcreatestart  : this.onDragCreateStart,
            dragcreateend    : this.onDragCreateEnd,
            afterdragcreate  : this.onAfterDragCreate,

            beforeeventresize : this.onBeforeResize,
            eventresizestart  : this.onResizeStart,
            eventresizeend    : this.onResizeEnd,

            scope : this
        });
    },

    afterHeaderResized : function () {
        var resizer = this.headerCt.resizer;

        // if we perform resize on panel with forceFit cfg set to true events will be sized incorrectly
        if (resizer && this.getMode() !== 'horizontal') {
            // if forceFit is enabled columns cannot be resized
            if (this.panel.forceFit) {
                this.setColumnWidth(resizer.origWidth);
            } else {
                var w = resizer.dragHd.getWidth();
                this.setColumnWidth(w);
            }
        }
    },

    columnRenderer : function (val, meta, record, row, col) {
        return this[this.mode].columnRenderer(val, meta, record, row, col);
    },

    onRowUpdated : function (resourceRecord) {
        var me = this,
            nodes;

        // Only relevant for horizontal mode
        if (me.getMode() === 'horizontal' && me.hasListener('eventrepaint')) {
            Ext.Array.each(resourceRecord.getEvents(), function (event) {
                nodes = me.getElementsFromEventRecord(event, resourceRecord, null, true);
                Ext.Array.each(nodes, function (node) {
                    me.fireEvent('eventrepaint', me, event, node);
                });
            });
        }
    },

    /**
     * Scrolls a resource event record into the viewport.
     *
     * If the resource store is a tree store, this method will also expand all relevant parent nodes
     * to locate the event.
     *
     * @param {Sch.model.Resource} resourceRec A resource record an event record is assigned to
     * @param {Sch.model.Event} eventRec    An event record to scroll into view
     * @param {Number} index                DOM node index, applicable only for Calendar view mode
     * @param {Boolean/Object} highlight    Either `true/false` or a highlight config object used to highlight the element after scrolling it into view
     * @param {Boolean/Object} animate      Either `true/false` or an animation config object used to scroll the element
     */
    scrollResourceEventIntoView : function (resourceRec, eventRec, index, highlight, animate, callback, scope) {
        var me         = this,
            eventStart = eventRec.getStartDate(),
            eventEnd   = eventRec.getEndDate(),
            currentTimeSpanRange,
            el;

        var doScroll = function () {
            // Establishing element to scroll to
            el = me.getElementsFromEventRecord(eventRec, resourceRec, index);
            el = el.length && el[0] || null; // In Calendar view there might be several elements correspond to resource/event pair.
            // Scrolling with view with animation and highlighting if needed
            me.scrollElementIntoView(el, true, animate, highlight, null, callback, scope);
        };

        // Make sure resource is visible if it's part of a TreeStore
        if (me.getResourceStore().isTreeStore) {
            resourceRec.bubble(function (node) {
                node.expand();
            });
        }

        // Make sure event is within current time axis time span
        if (!me.timeAxis.dateInAxis(eventStart) || !me.timeAxis.dateInAxis(eventEnd)) {
            currentTimeSpanRange = me.timeAxis.getEnd() - me.timeAxis.getStart();
            me.timeAxis.setTimeSpan(
                new Date(eventStart.valueOf() - currentTimeSpanRange / 2),
                new Date(eventEnd.getTime() + currentTimeSpanRange / 2)
            );
            // HACK:
            // After a time axis change, the header is resized and Ext JS TablePanel reacts to the size change.
            // Ext JS reacts after a short delay, so we cancel this task to prevent Ext from messing up the scroll sync
            me.up('panel').scrollTask.cancel();
        }

        if (this.getOrientation() === 'horizontal') {

            // Dealing with buffered rendering, making sure row is rendered
            me.up('timelinegrid,timelinetree').ensureVisible(resourceRec, {
                callback : function () {
                    // 1. ensureVisible on a lockable grid will call callback twice one for normal grid and another
                    //    for locked grid
                    // 2. since we do not provide the scope for the callback then 'this' here will point to either
                    //    normal grid or locked grid the callback is called for
                    // 3. We use this.isLocked to execute callback logic for the normal grid only.
                    if (this.isLocked === false) doScroll();
                }
            });
        } else {
            doScroll();
        }
    }
});
