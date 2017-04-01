/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.view.Gantt
@extends Sch.view.TimelineGridView

A view of the gantt panel. Use the {@link Gnt.panel.Gantt#getSchedulingView} method to get its instance from gantt panel.

*/
Ext.define("Gnt.view.Gantt", {
    extend                   : "Sch.view.TimelineGridView",

    alias                    : ['widget.ganttview'],

    requires                 : [
        'Ext.dd.ScrollManager',
        'Sch.patches.DragDropManager',
        'Sch.patches.NavigationModel',
        'Gnt.model.Task',
        'Gnt.template.Task',
        'Gnt.template.ParentTask',
        'Gnt.template.Milestone',
        'Gnt.template.RollupTask',
        'Gnt.template.Deadline',
        'Gnt.feature.TaskDragDrop',
        'Gnt.feature.ProgressBarResize',
        'Gnt.feature.TaskResize',
        'Sch.view.Horizontal'
    ],

    uses                     : [
        'Gnt.feature.LabelEditor',
        'Gnt.patches.LabelEditor',
        'Gnt.feature.DragCreator'
    ],

    mixins                   : [
        'Sch.mixin.GridViewCanvas',
        'Sch.mixin.FilterableTreeView'
    ],

    _cmpCls                  : 'sch-ganttview',

    scheduledEventName       : 'task',

    trackOver                : false,
    toggleOnDblClick         : false,

    // private
    eventSelector            : '.sch-gantt-item',

    eventWrapSelector        : '.sch-event-wrap',

    barMargin                : 4,

    progressBarResizer       : null,
    taskResizer              : null,
    taskDragDrop             : null,
    dragCreator              : null,

    resizeConfig             : null,
    createConfig             : null,
    dragDropConfig           : null,
    progressBarResizeConfig  : null,

    /**
     * @cfg {Object} dependencyViewConfig
     *
     * A config object to apply to internal instance of the {@link Gnt.view.Dependency}. Inner properties like {@link Gnt.view.Dependency#dragZoneConfig} and {@link Gnt.view.Dependency#dropZoneConfig}
     * will be applied to the dependency drag- and dropzone instances respectively.
     *
     * @deprecated 4.2 use {@link Gnt.view.dependency.Mixin#dependencyViewConfig} instead
     */

    externalGetRowClass      : null,

    /**
     * @cfg {Number} outsideLabelsGatherWidth Defines width of special zone outside (before and after) of visible area within which tasks will be still rendered into DOM.
     * This is used to render partially visible labels of invisible tasks bordering with visible area.
     *
     * Increase this value to see long labels, set to 0 if you want to hide labels of invisible tasks completely.
     */
    outsideLabelsGatherWidth : 200,

    // Task click-events --------------------------
    /**
     * @event taskclick
     * Fires when a task is clicked
     *
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord The task record
     * @param {Ext.EventObject} e The event object
     */

    /**
     * @event taskdblclick
     * Fires when a task is double clicked
     *
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord The task record
     * @param {Ext.EventObject} e The event object
     */

    /**
     * @event taskcontextmenu
     * Fires when contextmenu is activated on a task
     *
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord The task record
     * @param {Ext.EventObject} e The event object
     */

    // Resizing events start --------------------------
    /**
     * @event beforetaskresize
     * Fires before a resize starts, return false to stop the execution
     *
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord The task about to be resized
     * @param {Ext.EventObject} e The event object
     */

    /**
     * @event taskresizestart
     * Fires when resize starts
     *
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord The task about to be resized
     */

    /**
     * @event partialtaskresize
     * Fires during a resize operation and provides information about the current start and end of the resized event
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     *
     * @param {Gnt.model.Task} taskRecord The task being resized
     * @param {Date} startDate The start date of the task
     * @param {Date} endDate The end date of the task
     * @param {Ext.Element} element The element being resized
     */

    /**
     * @event aftertaskresize
     * Fires after a succesful resize operation
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord The task that has been resized
     */

    // Task progress bar resizing events start --------------------------
    /**
     * @event beforeprogressbarresize
     * Fires before a progress bar resize starts, return false to stop the execution
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord The record about to be have its progress bar resized
     */

    /**
     * @event progressbarresizestart
     * Fires when a progress bar resize starts
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord The record about to be have its progress bar resized
     */

    /**
     * @event afterprogressbarresize
     * Fires after a succesful progress bar resize operation
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord record The updated record
     */

    // Dnd events start --------------------------
    /**
     * @event beforetaskdrag
     * Fires before a task drag drop is initiated, return false to cancel it
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord The task record that's about to be dragged
     * @param {Ext.EventObject} e The event object
     */

    /**
     * @event taskdragstart
     * Fires when a dnd operation starts
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord The record being dragged
     */

    /**
     * @event beforetaskdropfinalize
     * Fires before a succesful drop operation is finalized. Return false to finalize the drop at a later time.
     * To finalize the operation, call the 'finalize' method available on the context object. Pass `true` to it to accept drop or false if you want to cancel it
     * NOTE: you should **always** call `finalize` method whether or not drop operation has been canceled
     * @param {Mixed} view The gantt view instance
     * @param {Object} dragContext An object containing 'record', 'start', 'finalize' properties.
     * @param {Ext.EventObject} e The event object
     */

    /**
     * @event beforetaskresizefinalize
     * Fires before a succesful resize operation is finalized. Return false to finalize the resize at a later time.
     * To finalize the operation, call the 'finalize' method available on the context object. Pass `true` to it to accept drop or false if you want to cancel it
     * NOTE: you should **always** call `finalize` method whether or not drop operation has been canceled
     * @param {Mixed} view The gantt view instance
     * @param {Object} resizeContext An object containing 'record', 'start', 'end', 'finalize' properties.
     * @param {Ext.EventObject} e The event object
     */

    /**
     * @event beforedragcreatefinalize
     * Fires before a succesful create operation is finalized. Return false to finalize creating at a later time.
     * To finalize the operation, call the 'finalize' method available on the context object. Pass `true` to it to accept drop or false if you want to cancel it
     * NOTE: you should **always** call `finalize` method whether or not drop operation has been canceled
     * @param {Mixed} view The gantt view instance
     * @param {Object} createContext An object containing 'record', 'start', 'end', 'finalize' properties.
     * @param {Ext.EventObject} e The event object
     */

    /**
     * @event taskdrop
     * Fires after a succesful drag and drop operation
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord The dropped record
     */

    /**
     * @event aftertaskdrop
     * Fires after a drag and drop operation, regardless if the drop valid or invalid
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     */

    // Label editors events --------------------------
    /**
     * @event labeledit_beforestartedit
     * Fires before editing is started for a field
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord The task record
     */

    /**
     * @event labeledit_beforecomplete
     * Fires after a change has been made to a label field, but before the change is reflected in the underlying field.
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Mixed} value The current field value
     * @param {Mixed} startValue The original field value
     * @param {Gnt.model.Task} taskRecord The affected record
     */

    /**
     * @event labeledit_complete
     * Fires after editing is complete and any changed value has been written to the underlying field.
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Mixed} value The current field value
     * @param {Mixed} startValue The original field value
     * @param {Gnt.model.Task} taskRecord The affected record
     */

    // Drag create events start --------------------------
    /**
     * @event beforedragcreate
     * Fires before a drag create operation starts, return false to prevent the operation
     * @param {Gnt.view.Gantt} gantt The gantt view
     * @param {Gnt.model.Task} task The task record being updated
     * @param {Date} date The date of the drag start point
     * @param {Ext.EventObject} e The event object
     */

    /**
     * @event dragcreatestart
     * Fires before a drag starts, return false to stop the operation
     * @param {Gnt.view.Gantt} view The gantt view
     */

    /**
     * @event dragcreateend
     * Fires after a successful drag-create operation
     * @param {Gnt.view.Gantt} view The gantt view
     * @param {Gnt.model.Task} task The updated task record
     * @param {Ext.EventObject} e The event object
     */

    /**
     * @event afterdragcreate
     * Always fires after a drag-create operation
     * @param {Gnt.view.Gantt} view The gantt view
     */
    // Drag create events end --------------------------


    /**
     * @event scheduleclick
     * Fires after a click on the schedule area
     * @param {Gnt.view.Gantt} ganttView The gantt view object
     * @param {Date} clickedDate The clicked date
     * @param {Number} rowIndex The row index
     * @param {Ext.EventObject} e The event object
     */

    /**
     * @event scheduledblclick
     * Fires after a doubleclick on the schedule area
     * @param {Gnt.view.Gantt} ganttView The gantt view object
     * @param {Date} clickedDate The clicked date
     * @param {Number} rowIndex The row index
     * @param {Ext.EventObject} e The event object
     */

    /**
     * @event schedulecontextmenu
     * Fires after a context menu click on the schedule area
     * @param {Gnt.view.Gantt} ganttView The gantt view object
     * @param {Date} clickedDate The clicked date
     * @param {Number} rowIndex The row index
     * @param {Ext.EventObject} e The event object
     */

    constructor : function (config) {
        config = config || {};

        if (config) {
            this.externalGetRowClass = config.getRowClass;

            delete config.getRowClass;
        }

        this.callParent(arguments);

        this.on({
            itemupdate   : this.onRowUpdate,
            scope        : this
        });

        this.mon(this.taskStore, {
            update       : this.onTaskStoreUpdate,
            scope        : this
        });

        this.initTreeFiltering();
    },

    onBeforeIndentationChange   : function () {
        var position = this.getNavigationModel().getPosition();
        if (position && position.record) {
            this._lastNavigatedRecord = position.record;
        }
    },

    onIndentationChange    : function () {
        this._lastNavigatedRecord && this.getNavigationModel().setPosition(this._lastNavigatedRecord);
        delete this._lastNavigatedRecord;
    },

    onRender : function () {
        // 213_indent
        // save navigation position during indenting
        this.mon(this.getTaskStore(), {
            beforeindentationchange : this.onBeforeIndentationChange,
            indentationchange       : this.onIndentationChange,
            scope                   : this
        });

        this.configureLabels();
        this.setupGanttEvents();
        this.setupTemplates();
        this.callParent(arguments);
    },

    /**
     * Returns the associated dependency store
     * @return {Gnt.data.TaskStore}
     */
    getDependencyStore : function () {
        return this.dependencyStore;
    },

    configureFeatures : function () {
        if (this.enableProgressBarResize !== false) {
            this.progressBarResizer = Ext.create("Gnt.feature.ProgressBarResize", Ext.apply({
                ganttView: this
            }, this.progressBarResizeConfig || {}));

            this.on({
                beforeprogressbarresize : this.onBeforeTaskProgressBarResize,
                progressbarresizestart  : this.onTaskProgressBarResizeStart,
                afterprogressbarresize  : this.onTaskProgressBarResizeEnd,
                scope                   : this
            });
        }

        if (this.taskResizeHandles !== 'none') {

            this.taskResizer = Ext.create("Gnt.feature.TaskResize", Ext.apply({
                ganttView           : this,
                validatorFn         : this.resizeValidatorFn || Ext.emptyFn,
                validatorFnScope    : this
            }, this.resizeConfig || {}));

            this.on({
                beforedragcreate       : this.onBeforeDragCreate,
                beforetaskresize       : this.onBeforeTaskResize,
                taskresizestart        : this.onTaskResizeStart,
                aftertaskresize        : this.onTaskResizeEnd,
                progressbarresizestart : this.onTaskResizeStart,
                afterprogressbarresize : this.onTaskResizeEnd,
                scope                  : this
            });
        }

        if (this.enableTaskDragDrop) {
            this.taskDragDrop = Ext.create("Gnt.feature.TaskDragDrop", this.getEl(), Ext.apply({
                gantt                       : this,
                taskSelector                : this.eventSelector,
                deadlineSelector            : '.gnt-deadline-indicator',
                validatorFn                 : this.dndValidatorFn || Ext.emptyFn,
                validatorFnScope            : this,
                skipWeekendsDuringDragDrop  : this.taskStore.skipWeekendsDuringDragDrop
            }, this.dragDropConfig));

            this.on({
                beforetaskdrag  : this.onBeforeTaskDrag,
                taskdragstart   : this.onDragDropStart,
                aftertaskdrop   : this.onDragDropEnd,
                scope: this
            });
        }

        if (this.enableDragCreation) {
            this.dragCreator = Ext.create("Gnt.feature.DragCreator", Ext.apply({
                ganttView           : this,
                validatorFn         : this.createValidatorFn || Ext.emptyFn,
                validatorFnScope    : this
            }, this.createConfig));
        }
    },

    /**
     * Returns the template for the task. Override this template method to supply your own custom UI template for a certain type of task.
     *
     * @template
     * @protected
     * @param {Gnt.model.Task} task The task to get template for.
     * @param {Boolean} isBaseline True to return the template for a baseline version of the task.
     * @return {Gnt.template.Template} Template for the task.
     */
    getTemplateForTask : function (task, isBaseline) {
        if (task.isMilestone(isBaseline)) {
            return this.milestoneTemplate;
        }
        if (task.isLeaf()) {
            return this.eventTemplate;        // return baseline templates
        }
        return this.parentEventTemplate;
    },

    refreshNotReadOnlyChildNodes : function (record) {
        record.cascadeBy({
            // if a child is explicitly marked as readonly then parent readonly change
            // doesn't affect the child state so we don't cascade it or its children
            before : function (child) {
                return child == record || !child.getReadOnly();
            },
            after : function (child) {
                if (child !== record) {
                    this.refreshNode(child);
                }
            },
            scope : this
        });
    },

    setShowRollupTasks : function (show) {

        this.showRollupTasks = show;

        var parentNodes = {};

        this.taskStore.getRootNode().cascadeBy(function (node) {

            if (node.getRollup()) {
                var parentNode = node.parentNode;

                parentNodes[parentNode.internalId] = parentNode;
            }
        });

        for (var id in parentNodes) {
            var index = this.store.indexOf(parentNodes[id]);

            if (index >= 0) {
                this.refreshNode(index);
            }
        }
    },

    //Todo combine generic parts this function with columnRenderer
    getRollupRenderData : function (parentModel) {

        var rollupData  = [];

        var ta          = this.timeAxis,
            viewStart   = ta.getStart(),
            viewEnd     = ta.getEnd();

        for (var i = 0; i < parentModel.childNodes.length; i++) {

            var taskModel = parentModel.childNodes[i];
            var taskStart = taskModel.getStartDate();
            var taskEnd   = taskModel.getEndDate() || taskStart && Sch.util.Date.add(taskStart, taskModel.getDurationUnit() || Sch.util.Date.DAY, 1);

            if (taskModel.getRollup() && taskStart && taskEnd) {

                if (Sch.util.Date.intersectSpans(taskStart, taskEnd, viewStart, viewEnd)) {

                   var data = {}, isMileStone = taskModel.isMilestone();

                   data.isRollup = true;
                   data.id = 'rollup_' + taskModel.getId();

                    var endsOutsideView  = taskEnd > viewEnd,
                        startsInsideView = Sch.util.Date.betweenLesser(taskStart, viewStart, viewEnd),
                        taskStartX       = this.getCoordinateFromDate(startsInsideView ? taskStart : viewStart),
                        taskEndX         = this.getCoordinateFromDate(endsOutsideView ? viewEnd : taskEnd),
                        itemWidth        = isMileStone ? 0 : taskEndX - taskStartX;

                    data.offset = (isMileStone ? (taskEndX || taskStartX) - this.getXOffset(taskModel) : taskStartX);
                    data.tpl    = isMileStone ? this.milestoneTemplate : this.eventTemplate;
                    data.cls    = taskModel.getCls();
                    data.ctcls  = '';
                    data.record = taskModel;

                    if (isMileStone) {
                        data.side = Ext.isIE8m ? Math.round(0.3 * this.getRowHeight()) : Math.round(0.5 * this.getRowHeight());
                        data.ctcls += ' sch-gantt-milestone';
                    } else {
                        data.width = Math.max(1, itemWidth);

                        if (endsOutsideView) {
                            data.ctcls += ' sch-event-endsoutside ';
                        }

                        if (!startsInsideView) {
                            data.ctcls += ' sch-event-startsoutside ';
                        }

                        data.ctcls += ' sch-gantt-task';
                    }

                    if (taskModel.isReadOnly()) {
                        data.ctcls += ' sch-gantt-task-readonly';
                    }

                    if (taskModel.isProject) {
                        data.ctcls += ' sch-gantt-project-task';
                    }

                    data.cls += ' sch-rollup-task';

                    rollupData.push(data);
                }
            }
        }

        return rollupData;
    },

    getLabelRenderData : function (taskModel) {
        var left       = this.leftLabelField,
            right      = this.rightLabelField,
            top        = this.topLabelField,
            bottom     = this.bottomLabelField,
            value,
            renderData = {};

        if (left) {
            value = left.dataIndex ? taskModel.data[ left.dataIndex ] : undefined;

            renderData.leftLabel = left.renderer ? left.renderer.call(left.scope || this, value, taskModel) : Ext.util.Format.htmlEncode(value);
        }

        if (right) {
            value = right.dataIndex ? taskModel.data[ right.dataIndex ] : undefined;

            renderData.rightLabel = right.renderer ? right.renderer.call(right.scope || this, value, taskModel) : Ext.util.Format.htmlEncode(value);
        }

        if (top) {
            value = top.dataIndex ? taskModel.data[ top.dataIndex ] : undefined;

            renderData.topLabel = top.renderer ? top.renderer.call(top.scope || this, value, taskModel) : Ext.util.Format.htmlEncode(value);
        }

        if (bottom) {
            value = bottom.dataIndex ? taskModel.data[ bottom.dataIndex ] : undefined;

            renderData.bottomLabel = bottom.renderer ? bottom.renderer.call(bottom.scope || this, value, taskModel) : Ext.util.Format.htmlEncode(value);
        }
    
        return renderData;
    },

    // private
    columnRenderer    : function (value, meta, taskModel) {

        var taskStart   = taskModel.getStartDate(),
            ta          = this.timeAxis,
            D           = Sch.util.Date,
            tplData     = {},
            cellResult  = '',
            ctcls       = '',
            viewStart   = ta.getStart(),
            viewEnd     = ta.getEnd(),
            isMilestone = taskModel.isMilestone(),
            labelsRenderDataPrepared = false,
            userData, startsInsideView, endsOutsideView;

        if (taskStart) {
            var taskEnd         = taskModel.getEndDate() || D.add(taskStart, taskModel.getDurationUnit() || D.DAY, 1),
                tick            = ta.getAt(0),
                // milliseconds per pixel ratio
                msPerPx         = (tick.getEndDate() - tick.getStartDate()) / this.timeAxisViewModel.getTickWidth(),
                timeDelta       = msPerPx * this.outsideLabelsGatherWidth,
                // if task belongs to the visible time span
                doRender        = D.intersectSpans(taskStart, taskEnd, viewStart, viewEnd),
                renderBuffer    = this.outsideLabelsGatherWidth > 0,
                // if task belongs to the buffered zone before/after visible time span
                renderAfter     = renderBuffer && D.intersectSpans(taskStart, taskEnd, viewEnd, new Date(viewEnd.getTime() + timeDelta)),
                renderBefore    = renderBuffer && D.intersectSpans(taskStart, taskEnd, new Date(viewStart.getTime() - timeDelta), viewStart);

            // if task belongs to the visible time span
            // or belongs to the buffered zone before/after visible time span
            if (doRender || renderAfter || renderBefore) {
                endsOutsideView     = taskEnd > viewEnd;
                startsInsideView    = D.betweenLesser(taskStart, viewStart, viewEnd);

                var taskStartX, taskEndX, itemWidth;

                // regular case ..task intersects visible time span
                if (doRender) {
                    taskStartX  = this.getCoordinateFromDate(startsInsideView ? taskStart : viewStart);
                    taskEndX    = this.getCoordinateFromDate(endsOutsideView ? viewEnd : taskEnd);
                    itemWidth   = isMilestone ? 0 : taskEndX - taskStartX;
                // task belongs to the buffered zone before/after visible time span
                } else {
                    startsInsideView = true;
                    itemWidth = 0;

                    if (renderAfter) {
                        taskStartX  = Math.floor(this.getCoordinateFromDate(viewEnd) + (taskStart - viewEnd) / msPerPx);
                    } else {
                        taskStartX  = Math.floor(this.getCoordinateFromDate(viewStart) - (viewStart - taskEnd) / msPerPx);
                    }
                }

                var taskOffset = isMilestone ? (taskEndX || taskStartX) - this.getXOffset(taskModel) : taskStartX;

                // if task is partially hidden progress bar should be rendered accordingly
                // eg. task is halfway done and rendered only half of the task
                // progress bar in this case should be hidden (width is 0)
                var percentDone = Math.min(taskModel.getPercentDone() || 0, 100) / 100,
                    percentDoneAtDate,
                    percentDoneX,
                    progressBarWidth;

                var parts   = taskModel.getSegments(),
                    segments;

                // if task is split
                if (parts) {
                    var percentDoneDuration = 0,
                        partsNumber         = parts.length,
                        i, part;

                    // since task is fragmented we cannot use just: (taskEnd - taskStart) * percentDone
                    // we have to get sum of all parts instead
                    for (i = 0; i < partsNumber; i++) {
                        part                = parts[i];
                        percentDoneDuration += (part.getEndDate() - part.getStartDate()) * percentDone;
                    }

                    segments    = [];

                    var partStartX, partEndX, partStartDate, partEndDate;

                    for (i = 0; i < partsNumber; i++) {

                        part            = parts[i];

                        var segment     = {},
                            segmentCls  = part.getCls() || '';

                        partEndDate     = part.getEndDate() || taskModel.getStartDate();
                        partStartDate   = part.getStartDate();

                        // if this segment starts in the visible area
                        if (D.betweenLesser(partStartDate, viewStart, viewEnd)) {
                            partStartX      = this.getCoordinateFromDate( partStartDate );

                            // if it ends in visible area as well
                            if (D.betweenLesser(partEndDate, viewStart, viewEnd)) {
                                partEndX    = this.getCoordinateFromDate( partEndDate );
                            } else {
                                partEndX    = this.getCoordinateFromDate( viewEnd );
                            }

                        // if its start is invisible
                        } else {
                            partStartX  = this.getCoordinateFromDate( viewStart );

                            // if end is visible
                            if (D.betweenLesser(partEndDate, viewStart, viewEnd)) {
                                partEndX    = this.getCoordinateFromDate( partEndDate );

                            // if both ends are invisible lets move them outside of visible area
                            } else if (partStartDate > viewEnd && partEndDate > viewEnd) {
                                partStartX  = partEndX = this.getCoordinateFromDate( viewEnd ) + 100;
                            } else if (partStartDate < viewStart && partEndDate < viewStart) {
                                partStartX  = partEndX = this.getCoordinateFromDate( viewStart ) - 100;

                            // if segment start before view start and ends after view end
                            } else {
                                partEndX    = this.getCoordinateFromDate( viewEnd );
                            }
                        }


                        segment.left    = partStartX - taskStartX;
                        segment.width   = partEndX - partStartX;

                        if (!percentDoneAtDate) {

                            percentDoneDuration     -= (partEndDate - partStartDate);

                            if (percentDoneDuration <= 0) {

                                percentDoneAtDate   = D.add(partEndDate, D.MILLI, percentDoneDuration);

                                // mark part that has progress bar slider
                                segmentCls          += ' sch-segment-in-progress';

                                percentDoneX        = this.getCoordinateFromDate(percentDoneAtDate);

                                // get progress bar size for this part
                                segment.progressBarWidth    = Math.min(Math.abs(percentDoneX - partStartX), segment.width);

                            // all parts before the time span that has "percentDoneAtDate" have 100% percent done
                            } else {
                                segment.progressBarWidth    = part.width;
                            }

                        // all parts after the time span that has "percentDoneAtDate" have zero percent done
                        } else {
                            segment.progressBarWidth        = 0;
                        }

                        segment.percentDone   = percentDone * 100;

                        Ext.apply(segment, part.data);

                        segment.cls             = segmentCls;
                        segment.SegmentIndex    = i;

                        segments.push(segment);
                    }

                    segments[0].cls                 += ' sch-gantt-task-segment-first';
                    segments[partsNumber - 1].cls   += ' sch-gantt-task-segment-last';

                // if task is NOT split
                } else {

                    // picks date between task start and end according to percentDone value
                    percentDoneAtDate = new Date((taskEnd - taskStart) * percentDone + taskStart.getTime());

                    if (percentDoneAtDate < viewStart) {
                        percentDoneAtDate = viewStart;
                    } else if (percentDoneAtDate > viewEnd) {
                        percentDoneAtDate = viewEnd;
                    }

                }

                percentDoneX = this.getCoordinateFromDate(percentDoneAtDate);

                // what if rtl?
                // in case task is rendered outside of view and has width 0, we should also set progress bar
                // width to 0 or progress bar will be visible as a 1px width vertical lines
                progressBarWidth = Math.min(Math.abs(percentDoneX - taskStartX), itemWidth);

                // Data provided to the Task XTemplate is composed in these steps
                //
                // 1. Get the default data from the Task Model
                // 2. Apply internal rendering properties: id, sizing, position etc
                // 3. Allow user to add extra properties at runtime using the eventRenderer template method
                tplData = {
                    // Core properties
                    id               : taskModel.internalId + '-x-x',
                    offset           : taskOffset,
                    width            : Math.max(1, itemWidth),
                    ctcls            : '',
                    cls              : '',
                    print            : this._print,
                    record           : taskModel,
                    percentDone      : percentDone * 100,
                    progressBarWidth : Math.max(0, progressBarWidth - 2*this.eventBorderWidth),
                    segments         : segments
                };

                // Get data from user "renderer"
                userData = this.eventRenderer && this.eventRenderer.call(this.eventRendererScope || this, taskModel, tplData, taskModel.store) || {};

                // Labels

                Ext.apply(tplData, this.getLabelRenderData(taskModel));

                labelsRenderDataPrepared = true;

                if (userData) {
                    Ext.apply(tplData, userData);
                }

                var dataCls = ' sch-event-resizable-' + taskModel.getResizable();

                if (isMilestone) {
                    tplData.side = Math.round((this.enableBaseline ? 0.4 : 0.5) * this.getRowHeight());
                    ctcls += " sch-gantt-milestone";
                } else {
                    tplData.width = Math.max(1, itemWidth);

                    if (endsOutsideView) {
                        ctcls += ' sch-event-endsoutside ';
                    }

                    if (!startsInsideView) {
                        ctcls += ' sch-event-startsoutside ';
                    }

                    if (taskModel.isLeaf()) {
                        ctcls += " sch-gantt-task";
                    } else {
                        ctcls += " sch-gantt-parent-task";
                    }

                }

                if (taskModel.isReadOnly()) {
                    ctcls += " sch-gantt-task-readonly";
                }

                if (taskModel.isProject) {
                    ctcls += " sch-gantt-project-task";
                }

                if (taskModel.dirty)                    dataCls += ' sch-dirty ';
                if (taskModel.isDraggable() === false)  dataCls += ' sch-event-fixed ';

                dataCls += taskModel.isSegmented() ? ' sch-event-segmented ' :  ' sch-event-notsegmented ';

                tplData.cls = (tplData.cls || '') + (taskModel.getCls() || '') + dataCls;
                tplData.ctcls += ' ' + ctcls;

                if (this.showRollupTasks) {

                    var rollupData = this.getRollupRenderData(taskModel);

                    if (rollupData.length > 0) {
                        cellResult += this.rollupTemplate.apply(rollupData);
                    }
                }

                cellResult += this.getTemplateForTask(taskModel).apply(tplData);
            }
        }

        // if baselines enabled
        if (this.enableBaseline) {

            // userData might be not initialized if we do not render the task bar (unscheduled or out of visible span)
            if (!userData) {
                userData    = this.eventRenderer && this.eventRenderer.call(this, taskModel, tplData, taskModel.store) || {};
            }

            // render baseline bar
            cellResult += this.baselineRenderer(taskModel, userData, viewStart, viewEnd, labelsRenderDataPrepared);
        }

        var deadline = taskModel.getDeadlineDate();
        if (deadline) {
            cellResult += this.deadlineTemplate.apply({
                dir     : this.rtl ? 'right' : 'left',
                offset  : this.getCoordinateFromDate( deadline ),
                date    : this.getFormattedEndDate(deadline),
                cls     : (new Date() > deadline && !taskModel.isCompleted()) ? 'gnt-deadline-indicator-late' : ''
            });
        }

        return cellResult;
    },


    baselineRenderer : function (taskModel, userData, viewStart, viewEnd, labelsRenderDataPrepared) {
        var D                   = Sch.util.Date,
            taskBaselineStart   = taskModel.getBaselineStartDate(),
            taskBaselineEnd     = taskModel.getBaselineEndDate();

        if (taskBaselineStart && taskBaselineEnd && D.intersectSpans(taskBaselineStart, taskBaselineEnd, viewStart, viewEnd)) {
            var endsOutsideView             = taskBaselineEnd > viewEnd;
            var startsInsideView            = D.betweenLesser(taskBaselineStart, viewStart, viewEnd);

            userData = userData || {};

            var isBaselineMilestone     = taskModel.isBaselineMilestone(),
                baseStartX              = this.getCoordinateFromDate(startsInsideView ? taskBaselineStart : viewStart),
                baseEndX                = this.getCoordinateFromDate(endsOutsideView ? viewEnd : taskBaselineEnd),
                baseWidth               = Math.max(1, isBaselineMilestone ? 0 : baseEndX - baseStartX),
                baseTpl                 = this.getTemplateForTask(taskModel, true),
                data                    = {
                    progressBarStyle : userData.baseProgressBarStyle || '',
                    // Putting 'base-' as suffix ('-base') conflicts with task element id creating rules where
                    // task element id is [commonprefix]-(task.internalId)-(resource.internalId)-(partnum)
                    id               : 'base-' + taskModel.internalId,
                    // TODO: this should use same rendering as the regular task
                    progressBarWidth : Math.min(100, taskModel.getBaselinePercentDone()) * baseWidth / 100,
                    percentDone      : taskModel.getBaselinePercentDone(),
                    offset           : isBaselineMilestone ? (baseEndX || baseStartX) - this.getXOffset(taskModel, true) : baseStartX,
                    print            : this._print,
                    width            : Math.max(1, baseWidth),
                    baseline         : true
                };

            var ctcls                   = '';

            if (isBaselineMilestone) {
                data.side               = Math.round(0.40 * this.getRowHeight());
                ctcls                   = "sch-gantt-milestone-baseline sch-gantt-baseline-item";
            } else if (taskModel.isLeaf()) {
                ctcls                   = "sch-gantt-task-baseline sch-gantt-baseline-item";
            } else {
                ctcls                   = "sch-gantt-parenttask-baseline sch-gantt-baseline-item";
            }

            if (endsOutsideView) {
                ctcls                   += ' sch-event-endsoutside ';
            }

            if (!startsInsideView) {
                ctcls                   += ' sch-event-startsoutside ';
            }

            // HACK, a bit inconsistent. 'basecls' should probably end up on the task el instead of the wrapper.
            data.ctcls                  = ctcls + ' ' + (userData.basecls || '');

            if (!labelsRenderDataPrepared) {
                Ext.apply(data, this.getLabelRenderData(taskModel));
            }

            return baseTpl.apply(data);
        }

        return '';
    },


    setupTemplates : function () {

        var tplCfg = {
            leftLabel                : this.leftLabelField,
            rightLabel               : this.rightLabelField,
            topLabel                 : this.topLabelField,
            bottomLabel              : this.bottomLabelField,
            prefix                   : this.eventPrefix,
            taskResizeHandles        : this.taskResizeHandles,
            enableDependencyDragDrop : this.enableDependencyDragDrop !== false,
            allowParentTaskDependencies : this.allowParentTaskDependencies !== false,
            enableProgressBarResize  : this.enableProgressBarResize,
            rtl                      : this.rtl
        };

        var config;

        if (!this.eventTemplate) {
            config = this.taskBodyTemplate ? Ext.apply({ innerTpl : this.taskBodyTemplate }, tplCfg) : tplCfg;
            this.eventTemplate = Ext.create("Gnt.template.Task", config);
        }

        if (!this.parentEventTemplate) {
            config = this.parentTaskBodyTemplate ? Ext.apply({ innerTpl : this.parentTaskBodyTemplate }, tplCfg) : tplCfg;
            this.parentEventTemplate = Ext.create("Gnt.template.ParentTask", config);
        }

        if (!this.milestoneTemplate) {
            config = this.milestoneBodyTemplate ? Ext.apply({ innerTpl : this.milestoneBodyTemplate }, tplCfg) : tplCfg;
            this.milestoneTemplate = Ext.create("Gnt.template.Milestone", config);
        }

        if (!this.rollupTemplate) {
            this.rollupTemplate = Ext.create("Gnt.template.RollupTask");
        }

        if (!this.deadlineTemplate) {
            this.deadlineTemplate = Ext.create("Gnt.template.Deadline");
        }
    },

    /**
     * Wrapper function returning the dependency manager instance
     *
     * @return {Gnt.view.Dependency} dependencyManager The dependency manager instance
     *
     * @deprecated 4.2 Use {Gnt.view.dependency.Mixin#getDependencyView} instead
     */
    getDependencyView : function () {
        return this.ownerGrid.getDependencyView();
    },


    /**
     * Returns the associated task store
     * @return {Gnt.data.TaskStore}
     */
    getTaskStore     : function () {
        return this.taskStore;
    },

    // To be compatible with Sch.view.dependency.View
    getEventStore : function() {
        return this.getTaskStore();
    },

    // private
    setupGanttEvents : function () {
        var me          = this,
            taskStore   = this.taskStore;

        if (this.toggleParentTasksOnClick) {
            this.on({
                taskclick : function (view, model) {

                    if (!model.isLeaf() && (!taskStore.isTreeFiltered() || taskStore.allowExpandCollapseWhileFiltered)) {
                        // Since row is being repainted in the DOM, no native 'dblclick' event will be fired
                        // We need to detect this case and fake it
                        var dblClickHandler = function() {
                            me.fireEvent.apply(this, ['taskdblclick'].concat(Array.prototype.slice.apply(arguments)));
                        };

                        me.on('taskclick', dblClickHandler);

                        // Remove listener after 300ms
                        me.unbindListenerTimer = setTimeout(function() {
                            me.un('taskclick', dblClickHandler);
                        }, 300);

                        model.isExpanded() ? model.collapse() : model.expand();
                    }
                }
            });
        }
    },

    // private
    configureLabels  : function () {

        Ext.Array.each(['left', 'right', 'top', 'bottom'], function(pos) {

            var field = this[pos+'LabelField'];

            if (field) {
                if (Ext.isString(field)) {
                    field = this[pos + 'LabelField'] = { dataIndex : field };
                }

                // Initialize editor (if defined)
                if (field.editor) {
                    field.editor = Ext.create("Gnt.feature.LabelEditor", this, {
                        labelPosition : pos,
                        field         : field.editor,
                        dataIndex     : field.dataIndex
                    });
                }
            }
        }, this);

        this.on('labeledit_beforestartedit', this.onBeforeLabelEdit, this);
    },

    // private
    onBeforeTaskDrag : function (p, record) {
        return !this.readOnly && record.isDraggable() !== false && (this.allowParentTaskMove || record.isLeaf());
    },

    onDragDropStart : function () {
        if (this.tip) {
            // HACK tip disable doesn't work in Ext 5.1
            // http://www.sencha.com/forum/showthread.php?296286-Ext.Tooltip-disable-doesn-t-work-in-5.1&p=1081931#post1081931
            this.tip.on('beforeshow', this.falseReturningFn);
        }
    },

    falseReturningFn : function() { return false; },

    onDragDropEnd : function () {
        if (this.tip) {
            this.tip.un('beforeshow', this.falseReturningFn);
        }
    },

    onTaskProgressBarResizeStart : function () {
        if (this.tip) {
            this.tip.hide();
            this.tip.disable();
        }
    },

    onTaskProgressBarResizeEnd : function () {
        if (this.tip) {
            this.tip.enable();
        }
    },

    onTaskResizeStart : function () {
        var scrollable = this.getScrollable();

        if (this.tip) {
            this.tip.hide();
            this.tip.disable();
        }

        // While resizing a task, we don't want the scroller to interfere
        scrollable.setDisabled && scrollable.setDisabled(true);
    },

    onTaskResizeEnd : function () {
        var scrollable = this.getScrollable();

        if (this.tip) {
            this.tip.enable();
        }

        // While resizing a task, we don't want the scroller to interfere
        scrollable.setDisabled && scrollable.setDisabled(false);
    },

    // private
    onBeforeDragCreate : function () {
        return !this.readOnly;
    },

    // private
    onBeforeTaskResize : function (view, task) {
        return !this.readOnly && task.getSchedulingMode() !== 'EffortDriven';
    },

    onBeforeTaskProgressBarResize : function () {
        return !this.readOnly;
    },

    onBeforeLabelEdit : function () {
        return !this.readOnly;
    },

    afterRender : function () {
        this.callParent(arguments);

        this.getEl().on('mousemove', this.configureFeatures, this, { single : true });

        Ext.dd.ScrollManager.register(this.el);
    },

    resolveTaskRecord : function (el) {
        var node = this.findItemByChild(el);

        if (node) {
            return this.getRecord(node);
        }
        return null;
    },

    resolveEventRecord : function (el) {
        return this.resolveTaskRecord(el);
    },

    resolveEventRecordFromResourceRow: function (el) {
        return this.resolveTaskRecord(el);
    },

    /**
     * Highlights a task and optionally any dependent tasks. Highlighting will add the `sch-gantt-task-highlighted`
     * class to the task row element.
     *
     * @param {Mixed} task Either a task record or the id of a task
     * @param {Boolean} highlightDependentTasks `true` to highlight the depended tasks. Defaults to `true`
     *
     */
    highlightTask : function (task, highlightDependentTasks) {
        if (!(task instanceof Ext.data.Model)) {
            task = this.taskStore.getModelById(task);
        }

        if (task) {
            task.isHighlighted = true;

            var el = this.getRow(task);
            if (el) {
                Ext.fly(el).addCls('sch-gantt-task-highlighted');
            }

            if (highlightDependentTasks !== false) {
                for (var i = 0, l = task.successors.length; i < l; i++) {
                    var dep     = task.successors[ i ];

                    this.highlightDependency(dep);
                    this.highlightTask(dep.getTargetTask(), highlightDependentTasks);
                }
            }
        }
    },


    /**
     * Un-highlights a task and optionally any dependent tasks.
     *
     * @param {Mixed} task Either a task record or the id of a task
     * @param {Boolean} includeSuccessorTasks `true` to also highlight successor tasks. Defaults to `true`
     *
     */
    unhighlightTask : function (task, includeSuccessorTasks) {
        if (!(task instanceof Ext.data.Model)) {
            task = this.taskStore.getModelById(task);
        }

        if (task) {
            task.isHighlighted = false;

            var el = this.getRow(task);
            if (el) {
                Ext.fly(el).removeCls('sch-gantt-task-highlighted');
            }

            if (includeSuccessorTasks !== false) {
                for (var i = 0, l = task.successors.length; i < l; i++) {
                    var dep     = task.successors[ i ];

                    this.unhighlightDependency(dep);
                    this.unhighlightTask(dep.getTargetTask(), includeSuccessorTasks);
                }
            }
        }
    },


    getRowClass : function (task) {
        var cls = '';

        if (task.isHighlighted) cls = 'sch-gantt-task-highlighted';

        if (this.externalGetRowClass) cls += ' ' + (this.externalGetRowClass.apply(this, arguments) || '');

        return cls;
    },


    // private
    clearSelectedTasksAndDependencies : function () {
        this.ownerGrid.getDependencyView().clearSelectedDependencies();
        this.el.select('.sch-gantt-task-highlighted').removeCls('sch-gantt-task-highlighted');

        this.taskStore.getRootNode().cascadeBy(function (task) {
            task.isHighlighted = false;
        });
    },


    /**
     * Returns the critical path(s) that can affect the end date of the project
     * @return {Array} paths An array of arrays (containing task chains)
     */
    getCriticalPaths : function () {
        return this.taskStore.getCriticalPaths();
    },


    /**
     * Highlights the critical path(s) that can affect the end date of the project.
     */
    highlightCriticalPaths : function () {
        // First clear any selected tasks/dependencies
        this.clearSelectedTasksAndDependencies();

        var paths   = this.getCriticalPaths(),
            dm      = this.getDependencyView(),
            t, i, l, depRecord;

        Ext.Array.each(paths, function (tasks) {
            for (i = 0, l = tasks.length; i < l; i++) {
                t = tasks[i];
                this.highlightTask(t, false);

                if (i < l - 1) {

                    for (var j = 0, m = t.predecessors.length; j < m; j++) {
                        if (t.predecessors[j].getSourceId() == tasks[i + 1].getId()) {
                            depRecord = t.predecessors[j];
                            break;
                        }
                    }

                    dm.highlightDependency(depRecord);
                }
            }
        }, this);

        this.addCls('sch-gantt-critical-chain');
    },


    /**
     * Removes the highlighting of the critical path(s).
     */
    unhighlightCriticalPaths : function () {
        this.removeCls('sch-gantt-critical-chain');

        this.clearSelectedTasksAndDependencies();
    },


    //private
    getXOffset               : function (task, isBaseline) {
        var offset      = 0;

        if (task.isMilestone(isBaseline)) {
            // For milestones, the offset should be half the square diagonal (Math.sqrt(2) / 4)
            offset      = Math.floor(this.getRowHeight() * 0.3535533905932738) - 2;
        }

        return offset;
    },

    //private
    onDestroy                : function () {
        if (this.rendered) {
            Ext.dd.ScrollManager.unregister(this.el);
        }

        clearTimeout(this.scrollTimer);
        clearTimeout(this.unbindListenerTimer);

        this.callParent(arguments);
    },

    /**
     * Convenience method wrapping the dependency manager method which highlights the elements representing a particular dependency
     * @param {Mixed} record Either the id of a record or a record in the dependency store
     */
    highlightDependency : function (record) {
        this.ownerGrid.getDependencyView().highlightDependency(record);
    },

    /**
     * Convenience method wrapping the dependency manager method which unhighlights the elements representing a particular dependency
     * @param {Mixed} record Either the id of a record or a record in the dependency store
     */
    unhighlightDependency  : function (record) {
        this.ownerGrid.getDependencyView().unhighlightDependency(record);
    },

    /**
     * Returns the editor defined for the left task label
     * @return {Gnt.feature.LabelEditor} editor The editor
     */
    getLeftEditor : function () {
        return this.leftLabelField && this.leftLabelField.editor;
    },

    /**
     * Returns the editor defined for the right task label
     * @return {Gnt.feature.LabelEditor} editor The editor
     */
    getRightEditor : function () {
        return this.rightLabelField && this.rightLabelField.editor;
    },

    /**
     * Returns the editor defined for the top task label
     * @return {Gnt.feature.LabelEditor} editor The editor
     */
    getTopEditor : function () {
        return this.topLabelField && this.topLabelField.editor;
    },

    /**
     * Returns the editor defined for the bottom task label
     * @return {Gnt.feature.LabelEditor} editor The editor
     */
    getBottomEditor : function () {
        return this.bottomLabelField && this.bottomLabelField.editor;
    },

    /**
     * Programmatically activates the editor for the left label
     * @param {Gnt.model.Task} record The task record
     */
    editLeftLabel : function (record) {
        var ed = this.getLeftEditor();
        if (ed) {
            ed.edit(record);
        }
    },

    /**
     * Programmatically activates the editor for the right label
     * @param {Gnt.model.Task} record The task record
     */
    editRightLabel : function (record) {
        var ed = this.getRightEditor();
        if (ed) {
            ed.edit(record);
        }
    },

    /**
     * Programmatically activates the editor for the top label
     * @param {Gnt.model.Task} record The task record
     */
    editTopLabel : function (record) {
        var ed = this.getTopEditor();
        if (ed) {
            ed.edit(record);
        }
    },

    /**
     * Programmatically activates the editor for the bottom label
     * @param {Gnt.model.Task} record The task record
     */
    editBottomLabel : function (record) {
        var ed = this.getBottomEditor();
        if (ed) {
            ed.edit(record);
        }
    },

    /**
     * @private
     * @deprecated 4.0
     */
    getDependenciesForTask : function (record) {
        // <debug>
        window.console && console.warn && console.warn('`Gnt.view.Gantt::getDependenciesForTask()` is deprecated, use `task.getAllDependencies()` instead');
        // </debug>
        return record.getAllDependencies();
    },

    // Repaint parents of rollup tasks and readonly child tasks
    onRowUpdate : function (store, index) {
        var record = this.store.getAt(index);
        var prev = record.previous;

        if (prev) {
            // The code below will handle the redraw when user does "setRollup" on some task
            // However generally the parent tasks are refreshed at the end of the cascading in the `onAfterCascade` method
            // of the Gnt.panel.Gantt
            if (record.parentNode && (record.rollupField in prev || record.getRollup())) {
                this.refreshNode(record.parentNode);
            }

            // refresh the task child nodes when the task readOnly status changes
            if (record.readOnlyField in prev) {
                this.refreshNotReadOnlyChildNodes(record);
            }
        }
    },

    // Repaint parents of rollup tasks if such tasks are hidden due to collapsed parent and update is done
    // via undo-redo (though there might be other cases when rollup task is changed and those changes might
    // not propagate to a parent task, if, for example, recalculate parents or cascade changes are off)
    onTaskStoreUpdate : function(taskStore, task, operation, modifiedFieldNames, details) {
        var prev = task.previous;

        if (prev && task.getRollup() && task.parentNode && !task.parentNode.expanded && taskStore.isUndoingOrRedoing()) {
            this.refreshNode(task.parentNode);
        }
    },

    handleScheduleEvent : function (e) {
        var t = e.getTarget('.' + this.timeCellCls, 3);

        if (t) {
            var rowNode = this.findRowByChild(t);

            if (e.type.indexOf('pinch') >= 0) {
                this.fireEvent('schedule' + e.type, this, e);
            } else {
                this.fireEvent('schedule' + e.type, this, this.getDateFromDomEvent(e, 'floor'), this.indexOf(rowNode), e);
            }
        }
    },


    /**
     *  Scrolls a task record into the viewport.
     *  This method will also expand all relevant parent nodes to locate the event.
     *
     *  @param {Gnt.model.Task} taskRec, the task record to scroll into view
     *  @param {Boolean/Object} highlight, either `true/false` or a highlight config object used to highlight the element after scrolling it into view
     *  @param {Boolean/Object} animate, either `true/false` or an animation config object used to scroll the element
     */
    scrollEventIntoView : function (taskRec, highlight, animate, callback, scope) {
        scope = scope || this;

        var me = this;
        var taskStore = this.taskStore;

        var basicScroll = function (el, scrollHorizontally) {

            // HACK
            // After a time axis change, the header is resized and Ext JS TablePanel reacts to the size change.
            // Ext JS reacts after a short delay, so we cancel this task to prevent Ext from messing up the scroll sync
            me.up('panel').scrollTask.cancel();

            me.scrollElementIntoView(el, scrollHorizontally, animate);

            if (highlight) {
                if (typeof highlight === "boolean") {
                    el.highlight();
                } else {
                    el.highlight(null, highlight);
                }
            }

            // XXX callback will be called too early, need to wait for scroll & highlight to complete
            callback && callback.call(scope);
        };

        // Make sure the resource is expanded all the way up first.
        if (!taskRec.isVisible()) {
            taskRec.bubble(function (node) {
                node.expand();
            });
        }

        var targetEl;

        var startDate = taskRec.getStartDate();
        var endDate = taskRec.getEndDate();
        var isScheduled = Boolean(startDate && endDate);

        if (isScheduled) {
            var timeAxis = this.timeAxis;

            // If task is not in the currently viewed time span, change time span
            if (!timeAxis.dateInAxis(startDate) || !timeAxis.dateInAxis(endDate)) {
                var range = timeAxis.getEnd() - timeAxis.getStart();

                timeAxis.setTimeSpan(new Date(startDate.getTime() - range / 2), new Date(endDate.getTime() + range / 2));
            }
            targetEl = this.getElementFromEventRecord(taskRec);
        } else {
            // No date information in the task, scroll to row element instead
            targetEl = this.getNode(taskRec);

            if (targetEl) {
                targetEl = Ext.fly(targetEl).down(this.getCellSelector());
            }
        }

        if (targetEl) {
            basicScroll(targetEl, isScheduled);
        } else {
            if (this.bufferedRenderer) {

                this.scrollTimer = Ext.Function.defer(function () {
                    me.bufferedRenderer.scrollTo(taskRec, false, function () {
                        // el should be present now
                        var targetEl = me.getElementFromEventRecord(taskRec);

                        if (targetEl) {
                            basicScroll(targetEl, true);
                        } else {
                            callback && callback.call(scope);
                        }
                    });

                }, 10);
            }
        }
    },

    /**
     * Gets the task box of the given task.
     *
     * @param {Sch.model.Event} taskRecord
     * @return {Object/Object[]/Null}
     * @return {Number} return.top
     * @return {Number} return.bottom
     * @return {Number} return.start
     * @return {Number} return.end
     * @return {Boolean} return.rendered Whether the box was calculated for the rendered scheduled record or was
     *                                   approximately calculated for the scheduled record outside of the current
     *                                   vertical view area.
     * @return {String} return.relPos if the item is not rendered then provides a view relative position one of 'before', 'after'
     */
    getItemBox : function(taskRecord) {
        var DATE = Sch.util.Date,
            me = this,
            result = null,
            viewStartDate  = me.timeAxis.getStart(), // WARNING: timeaxis is the private property of Sch.mixin.AbstractTimelineView
            viewEndDate    = me.timeAxis.getEnd(),   // WARNING: timeaxis is the private property of Sch.mixin.AbstractTimelineView
            // For milestones we tend to use end date since there might be a milestone having non-zero duration and in such case
            // no full length task bar is rendered, we just render a "diamond" on the end date
            taskStartDate  = taskRecord.isMilestone() && taskRecord.getEndDate() || taskRecord.getStartDate(),
            taskEndDate    = taskRecord.getEndDate(),
            taskStore      = me.getTaskStore(),
            taskStartX, taskEndX,
            rowEl, rowIndex, rowTop, rowHeight,
            firstRowIndex, lastRowIndex, firstRowRecord;

        // Checking if task record is:
        result = (
            // scheduled;
            taskStartDate && taskEndDate &&
            // - visible, i.e. it's not within a collapsed row
            // - actually rendered or might be filtered out by Sencha's filters
            me.store.indexOf(taskRecord) >= 0 &&
            // - not filtered out
            (!taskStore.isTreeFiltered() || taskStore.lastTreeFilter.filter.call(taskStore.lastTreeFilter.scope || taskStore, taskRecord))
        ) || null;

        if (result) {

            rowEl = me.getNode(taskRecord);

            // If task row is rendered
            if (rowEl) {
                var taskNodeTop, taskNodeBottom;
                var nodeContainer       = me.getNodeContainer();
                var OUTSIDE_VIEW_OFFSET = 40; // To make sure non-relevant dependency lines aren't seen

                if (taskRecord.isMilestone()) {
                    var verticalMargin = me.getRowHeight() * 0.16;

                    rowTop          = Ext.fly(rowEl).getOffsetsTo(nodeContainer)[1];
                    taskNodeTop     = rowTop + verticalMargin;
                    taskNodeBottom  = rowTop + me.getRowHeight() - verticalMargin;
                } else {
                                                                                                                                               // task bar node might be outside visible time axis
                    var taskNode    = Ext.fly(rowEl).down('.' + Ext.baseCSSPrefix + 'grid-cell-inner > .sch-event-wrap .sch-gantt-item', true) || rowEl;

                    taskNodeTop     = Ext.fly(taskNode).getOffsetsTo(nodeContainer)[1];
                    taskNodeBottom  = taskNodeTop + Ext.fly(taskNode).getHeight();
                }

                taskStartX = me.getCoordinateFromDate(DATE.min(DATE.max(taskStartDate, viewStartDate), viewEndDate));
                taskEndX   = me.getCoordinateFromDate(DATE.max(DATE.min(taskEndDate, viewEndDate), viewStartDate));

                // Make sure start/end points are not in view
                if (viewStartDate > taskStartDate) taskStartX   -= OUTSIDE_VIEW_OFFSET;
                if (viewStartDate > taskEndDate) taskEndX       -= OUTSIDE_VIEW_OFFSET;
                if (viewEndDate < taskStartDate) taskStartX     += OUTSIDE_VIEW_OFFSET;
                if (viewEndDate < taskEndDate) taskEndX         += OUTSIDE_VIEW_OFFSET;

                // Finally we have all the data needed to calculate the task record box
                result = {
                    rendered   : true,
                    start      : taskStartX,
                    end        : taskEndX,
                    top        : Math.round(taskNodeTop),
                    bottom     : Math.round(taskNodeBottom)
                };
            }
            // Resource row is not rendered and it's not collapsed. We calculate a task box approximately.
            else {
                result = {
                    rendered   : false,
                    start      : me.getCoordinateFromDate(DATE.max(taskStartDate, viewStartDate)),
                    end        : me.getCoordinateFromDate(DATE.min(taskEndDate, viewEndDate))
                    // top and bottom to go
                };

                // WARNING: view.all is a private property
                firstRowIndex  = me.all.startIndex;
                firstRowRecord = me.getRecord(firstRowIndex);

                if (firstRowRecord) {
                    rowHeight      = me.getRowHeight();

                    if (taskRecord.isAbove(firstRowRecord)) {
                        result.top    = -2 * rowHeight;
                        result.relPos = 'before';
                    }
                    else {
                        // WARNING: view.all is the private property
                        lastRowIndex  = me.all.endIndex;
                        rowEl         = Ext.get(me.getNode(lastRowIndex));
                        result.top    = rowEl.getOffsetsTo(me.getNodeContainer())[1] + rowEl.getHeight();
                        result.relPos = 'after';
                    }

                    result.bottom = result.top + rowHeight;
                } else {
                    result = null;
                }
            }

            // Milestone boxes need special adjustments
            if (result) {
                result = me.adjustItemBox(taskRecord, result);
            }
        }

        return result;
    },

    /**
     * Adjusts task record box if needed
     *
     * @param {Gnt.model.Task} taskRecord
     * @param {Object} taskBox
     * @return {Number} taskBox.top
     * @return {Number} taskBox.bottom
     * @return {Number} taskBox.start
     * @return {Number} taskBox.end
     * @return {Object}
     * @return {Number} return.top
     * @return {Number} return.bottom
     * @return {Number} return.start
     * @return {Number} return.end
     * @protected
     */
    adjustItemBox : function(taskRecord, taskBox) {
        var result = taskBox;

        if (taskRecord.isMilestone()) {
            result = {
                rendered   : taskBox.rendered,
                top        : taskBox.top,
                bottom     : taskBox.bottom + 1,
                start      : taskBox.start - 8,
                end        : taskBox.end + 8,
                relPos     : taskBox.relPos
            };
        }

        return result;
    },

    getDataForTooltipTpl : function (record, triggerElement) {
        var match = triggerElement.id.match(/rollup_(.*)$/);

        // If hovering a rollup miniature, we should show the rolled up task info
        if (match && match[1]) {
            var rolledUpRecord = this.getTaskStore().getNodeById(match[1]);

            if (rolledUpRecord) {
                record = rolledUpRecord;
            }
        }

        var data = this.callParent([record, triggerElement]);

        data._useBaselineData = Boolean(Ext.fly(triggerElement).up('.sch-gantt-baseline-item'));

        return data;
    }

    // Deprecated dependency related events
    // NOTE: remove it after 4.4

    /**
     * @event beforedependencydrag
     *
     * Fires before a dependency drag operation starts (from a "task terminal"). Return false to prevent this operation
     * from starting.
     *
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord The source task record
     *
     * @deprecated 4.2 Use {@link Gnt.panel.Gantt#beforedependencydrag} instead
     */

    /**
     * @event dependencydragstart
     *
     * Fires when a dependency drag operation starts
     *
     * @param {Gnt.view.Gantt} gantt The gantt view instance
     *
     * @deprecated 4.2 Use {@link Gnt.panel.Gantt#dependencydragstart} instead
     */

    /**
     * @event dependencydrop
     *
     * Fires when a dependency drag drop operation has completed successfully and a new dependency has been created.
     *
     * @param {Gnt.panel.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} fromRecord The source task record
     * @param {Gnt.model.Task} toRecord The destination task record
     * @param {Number} type The dependency type
     *
     * @deprecated 4.2 Use {@link Gnt.panel.Gantt#dependencydrop} instead
     */

    /**
     * @event afterdependencydragdrop
     *
     * Always fires after a dependency drag-drop operation
     *
     * @param {Gnt.panel.Gantt} gantt The gantt view instance
     *
     * @deprecated 4.2 Use {@link Gnt.panel.Gantt#afterdependencydragdrop} instead
     */

    /**
     * @event dependencyclick
     *
     * Fires after clicking on a dependency line/arrow
     *
     * @param {Gnt.view.Dependency} view The dependency view instance
     * @param {Gnt.model.Dependency} record The dependency record
     * @param {Ext.EventObject} event The event object
     * @param {HTMLElement} target The target of this event
     *
     * @deprecated 4.2 Use {@link Gnt.panel.Gantt#dependencyclick} instead
     */

    /**
     * @event dependencycontextmenu
     *
     * Fires after right clicking on a dependency line/arrow
     *
     * @param {Gnt.view.Dependency} view The dependency view instance
     * @param {Gnt.model.Dependency} record The dependency record
     * @param {Ext.EventObject} event The event object
     * @param {HTMLElement} target The target of this event
     *
     * @deprecated 4.2 Use {@link Gnt.panel.Gantt#dependencycontextmenu} instead
     */

    /**
     * @event dependencydblclick
     *
     * Fires after double clicking on a dependency line/arrow
     *
     * @param {Gnt.view.Dependency} view The dependency view instance
     * @param {Gnt.model.Dependency} record The dependency record
     * @param {Ext.EventObject} event The event object
     * @param {HTMLElement} target The target of this event
     *
     * @deprecated 4.2 Use {@link Gnt.panel.Gantt#dependencydblclick} instead
     */
});
