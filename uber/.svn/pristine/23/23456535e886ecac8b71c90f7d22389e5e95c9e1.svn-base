/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.panel.TimelineScheduler
@extends Sch.panel.SchedulerGrid

A class implementing a __project timeline__ panel, a special view that visualizes only key tasks and milestones.
It might be useful to see a high level project overview without extra unneeded details.

The panel retrieves tasks to show from the provided {@link #taskStore task store}. It shows only tasks that have the `ShowInTimeline` field set to `true`:

    var taskStore = new Gnt.data.TaskStore({
        root : {
            expanded : true,
            children : [
                // this task will be seen in the project timeline panel
                { Name : "Task 1", StartDate : "2015-08-05", Duration : 2, ShowInTimeline : true }
                // ..and this one won't
                { Name : "Task 2", StartDate : "2015-08-05", Duration : 1 }
            ]
        }
    })

    var timelinePanel = Ext.create("Gnt.panel.TimelineScheduler", {
        taskStore     : taskStore
    });

* **Note:** To show/hide tasks in the timeline, there is a special Gantt chart column {@link Gnt.column.ShowInTimeline}.

{@img gantt/images/timeline-scheduler-panel.png}
 */
Ext.define("Gnt.panel.TimelineScheduler", {
    extend : 'Sch.panel.SchedulerGrid',

    requires : [
        'Ext.data.StoreManager',
        'Sch.data.ResourceStore',
        'Sch.data.EventStore'
    ],

    alias : 'widget.gantt_timelinescheduler',

    /**
     * @cfg {Gnt.data.TaskStore} taskStore (required) The {@link Gnt.data.TaskStore store} holding the tasks.
     * The store is tracked to display tasks having `ShowInTimline` field set to `true`.
     */
    taskStore : null,

    split : false,

    /**
     * @cfg resourceStore
     * @hide
     */

    /**
     * @cfg eventStore
     * @hide
     */

    /**
     * @cfg {Number} milestoneBottomPadding Space in the bottom of the panel to leave
     * for milestones to extend outside the row.
     */
    milestoneBottomPadding : 23,
    barMargin              : 0,
    header                 : false,
    enableColumnMove       : false,
    enableColumnHide       : false,
    enableColumnResize     : false,
    sortableColumns        : false,
    trackMouseOver         : false,
    bufferedRenderer       : false,
    border                 : false,
    rowLines               : false,
    columnLines            : false,
    readOnly               : true,
    resizeHandles          : 'none',
    enableDragCreation     : false,
    enableEventDragDrop    : false,
    forceFit               : true,
    autoAdjustTimeAxis     : false,
    leftTimespanMargin     : 25,
    rightTimespanMargin    : 50,

    resourceStoreClass : 'Sch.data.ResourceStore',
    eventStoreClass    : 'Sch.data.EventStore',

    refreshMainRowTimeout : 5,

    needToZoom                  : false,
    destroyStores               : true,
    variableRowHeight           : false,
    syncRowHeight               : false,
    lockedGridDependsOnSchedule : false,

    initComponent : function () {
        var me = this;

        me.addCls('sch-gantt-timeline-scheduler');

        me.setTaskStore(this.taskStore);

        Ext.apply(me, {
            // override few normal grid view methods
            normalViewConfig : {
                onEventUpdate : Ext.Function.bind(me.onEventUpdate, me),
                onEventAdd    : Ext.Function.bind(me.onEventAdd, me),
                onEventRemove : Ext.Function.bind(me.onEventRemove, me)
            },

            resourceStore  : me.buildResourceStore(),
            eventStore     : me.buildEventStore(),
            // repeating refreshMainRow calls will be swallowed during refreshMainRowTimeout milliseconds
            refreshMainRow : Ext.Function.createBuffered(me.refreshMainRow, me.refreshMainRowTimeout, me)
        });

        var root = me.taskStore.getRoot();

        if (root && root.childNodes.length) {
            // wait till the 1st layout happen to make sure the panel has width fullfilled
            me.on({
                afterlayout : me.fillStoreFromTaskStore,
                scope       : me,
                single      : true
            });
        }

        me.callParent(arguments);

        me.on('resize', me.onSchedResize, me);

        me.resourceRecord = me.resourceStore.first();

        // HACK prevent scheduler from considering milestones when calculating number of bands to use for the
        // event bars - since milestones are put at the bottom.
        var view        = me.getSchedulingView(),
            horizontal  = view.eventLayout.horizontal,
            oldLayoutFn = horizontal.applyLayout;

        horizontal.applyLayout = function (events, resource) {
            events = Ext.Array.filter(events, function (ev) {
                return ev.event.getDuration() > 0;
            });
            return oldLayoutFn.call(this, events, resource);
        };
        // EOF HACK


        this.registerRenderer(this.rowHeightRenderer, this);
    },


    setTaskStore : function (taskStore, prevTaskStore) {
        var me        = this;
        var listeners = {
            load       : me.fillStoreFromTaskStore,
            nodeappend : me.onTaskAdded,
            update     : me.onTaskUpdated,
            noderemove : me.onTaskRemoved,
            clear      : me.onTaskStoreClear,

            scope : me
        };

        prevTaskStore && me.mun(prevTaskStore, listeners);

        taskStore    = Ext.data.StoreManager.lookup(taskStore);

        me.mon(taskStore, listeners);
        me.taskStore = taskStore;

        return taskStore;
    },


    buildResourceStore : function (config) {
        return Ext.create(this.resourceStoreClass, Ext.apply({
            storeId : null,
            data    : [
                { Id : 1 }
            ]
        }, config));
    },


    buildEventStore : function (config) {
        return Ext.create(this.eventStoreClass, Ext.apply({
            storeId : null,

            // Since we will fill the store with Tasks instead of events and we work without assignment store
            // we have to disable resource events cache because it is designed to work with Events only
            createResourceEventsCache : Ext.emptyFn,

            filterEventsForResource : function () {
                return Ext.Array.filter(this.getRange(), function(event) {
                    return event.isScheduled();
                });
            },

            getEventsForResource : function () {
                return this.getRange();
            },

            isDateRangeAvailable : function (start, end, excludeEvent, resource) {
                if (!start || !end) return true;

                var DATE   = Sch.util.Date,
                    events = this.getRange(),
                    available;

                for (var i = 0, l = events.length; i < l; i++) {
                    var ev = events[i];

                    available = (
                    excludeEvent === ev ||
                    ev.getDuration() === 0 || !ev.getStartDate() || !ev.getEndDate() || !DATE.intersectSpans(start, end, ev.getStartDate(), ev.getEndDate())
                    );

                    // stop looping if interval is occupied by a non excluding event
                    if (!available) break;
                }

                return available;
            }
        }, config));
    },


    refreshMainRow : function (forceZoomToFit) {
        // if we need to zoom
        if (forceZoomToFit || this.needToZoom) {
            this.zoomToFit();

        } else {
            this.getSchedulingView().repaintEventsForResource(this.resourceRecord);
            this.fitEvents();
        }
    },


    eventRenderer : function (ev, resource, meta) {
        if (ev.store.isDateRangeAvailable(ev.getStartDate(), ev.getEndDate(), ev, resource)) {
            meta.cls = 'sch-gantt-timeline-stretch';
        }

        if (!ev.isMilestone()) {
            meta.style = 'line-height:' + meta.height + 'px;';
        }

        return ev.getName();
    },

    // Just a simple renderer to set the row height
    rowHeightRenderer : function (v, meta) {
        meta.style = 'height:' + this.getAvailableRowHeight() + 'px';
    },


    onTaskStoreClear : function () {
        this.eventStore.removeAll();
    },


    onTaskRemoved : function (store, removedNode, isMove) {
        if (!isMove) {
            this.eventStore.remove(removedNode);

            // Clear any child nodes too
            Ext.Array.each(removedNode.childNodes || [], function (node) {
                this.onTaskRemoved(store, node);
            }, this);
        }
    },


    onTaskAdded : function (store, task) {
        if (!this.taskStore.isSettingRoot) {
            if (task.getShowInTimeline()) {
                this.eventStore.add(task);
            }
        }
    },


    // On a change in the task store we only check if ShowInTimeline flag is changed
    onTaskUpdated : function (store, task, operation, modifiedFields) {
        // if ShowInTimeline field was changed
        if (modifiedFields) {
            if (Ext.Array.contains(modifiedFields, task.showInTimelineField)) {

                if (task.getShowInTimeline()) {
                    this.eventStore.add(task);
                } else {
                    this.eventStore.remove(task);
                }
            }
        }
    },


    onEventAdd : function () {
        
        // check if timespan needs to be revalidated (if it's not detected already)
        if (!this.needToZoom) {
            var timespan    = this.eventStore.getTotalTimeSpan();
            this.needToZoom = timespan.start < this.getStart() || timespan.end > this.getEnd();
        }

        this.refreshMainRow();
    },


    onEventRemove : function (store, tasks) {
        // check if timespan needs to be revalidated (if it's not detected already)
        if (!this.needToZoom) {
            var timespan = this.eventStore.getTotalTimeSpan();

            // check if timespan needs to be revalidated
            for (var i = 0; i < tasks.length; i++) {
                var task = tasks[i];

                if (timespan.start > task.getStartDate() || timespan.end < task.getEndDate()) {
                    this.needToZoom = true;
                    break;
                }
            }
        }

        this.refreshMainRow();
    },


    onEventUpdate : function (store, task, operation, modifiedFields) {
        var prev     = task.previous,
            timespan = this.eventStore.lastTotalTimeSpan;

        // if task start/end has been changed
        if (modifiedFields) {
            // check if timespan needs to be revalidated (if it's not detected already)
            if (!this.needToZoom && prev && timespan && (task.startDateField in prev || task.endDateField in prev)) {
                // if the task an edge task..
                this.needToZoom = (task.startDateField in prev && timespan.start - prev[task.startDateField] === 0) ||
                (task.endDateField in prev && timespan.end - prev[task.endDateField] === 0) ||
                    // ..or it just became an edge task
                (task.startDateField in prev && timespan.start > task.getStartDate()) ||
                (task.endDateField in prev && timespan.end < task.getEndDate());
            }

            this.refreshMainRow();
        }
    },


    fillStoreFromTaskStore : function () {
        var timelineTasks = [],
            milestones    = [];

        this.taskStore.forEachTaskUnordered(function (task) {
            if (task.getShowInTimeline()) {
                if (task.isMilestone()) {
                    milestones.push(task);
                } else {
                    timelineTasks.push(task);
                }
            }
        });

        // milestones go after other tasks to have them drawn above by default
        timelineTasks = timelineTasks.concat(milestones);


        this.eventStore.removeAll(true);

        if (timelineTasks.length || this.eventStore.getCount()) {
            var view = this.getSchedulingView();

            view.blockRefresh = true;
            this.eventStore.loadData(timelineTasks);
            view.blockRefresh = false;

            this.zoomToFit();
        }
    },


    zoomToFit : function () {
        // TODO: problem of this method it potentially causes double refresh:
        // 1st - because of zooming, 2nd - because of fitEvents() call (in case row height gets changed)

        this.suspendLayouts();

        // reset flag requiring zooming to be done
        this.needToZoom = false;

        this.callParent([{
            leftMargin  : this.leftTimespanMargin,
            rightMargin : this.rightTimespanMargin
        }]);

        // causes view refresh if row height is changed
        this.fitEvents();

        this.resumeLayouts();
    },


    onSchedResize : function (cmp, width, height, oldWidth, oldHeight) {
        if (height !== oldHeight) {
            this.fitEvents();
        }
    },


    fitEvents : function (cmp, width, height, oldWidth, oldHeight) {
        if (this.eventStore.getCount() > 0) {
            var nbrBands = this.getSchedulingView().eventLayout.horizontal.nbrOfBandsByResource[this.resourceRecord.internalId] || 1;

            // causes view refresh if row height is changed
            this.setRowHeight(Math.ceil(this.getAvailableRowHeight() / nbrBands));
        }
    },

    getAvailableRowHeight : function () {
        return this.getSchedulingView().getHeight() - this.milestoneBottomPadding;
    }
});

