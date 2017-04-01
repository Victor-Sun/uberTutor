/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * @class Gnt.feature.TaskDragDrop
 * @extends Ext.dd.DragZone
 * @private
 *
 * Internal plugin enabling drag and drop for tasks
 */
Ext.define("Gnt.feature.TaskDragDrop", {
    extend : "Ext.dd.DragZone",

    requires : [
        'Gnt.Tooltip',
        'Ext.dd.StatusProxy'
    ],

    /**
     * @cfg {Boolean} useTooltip `false` to not show a tooltip while dragging
     */
    useTooltip      : true,

    /**
     * @cfg {Object} tooltipConfig A custom config object to apply to the {@link Gnt.Tooltip} instance.
     */
    tooltipConfig   : null,

    /**
     * @cfg {Function} validatorFn An empty function by default.
     * Provide to perform custom validation on the item being dragged.
     * This function is called during the drag and drop process and also after the drop is made.
     * @param {Gnt.model.Task} record The record being dragged
     * @param {Date} date The new start date
     * @param {Number} duration The duration of the item being dragged, in minutes
     * @param {Ext.EventObject} e The event object
     * @return {Boolean} true if the drop position is valid, else false to prevent a drop
     */
    validatorFn     : function (record, date, duration, e) { return true; },

    /**
     * @cfg {Object} validatorFnScope
     * The scope for the validatorFn, defaults to the gantt view instance
     */
    validatorFnScope : null,

    /**
     * @cfg {Boolean} showExactDropPosition When enabled, the task being dragged always "snaps" to the exact start date / duration that it will have after being drop.
     */
    showExactDropPosition : false,

    // has to be set to `false` - we'll register the gantt view in the ScrollManager manually
    containerScroll : false,

    dropAllowed     : "sch-gantt-dragproxy",
    dropNotAllowed  : "sch-gantt-dragproxy",

    // Reference to the gantt view
    gantt           : null,

    // Don't seem to need these
    onDragEnter     : Ext.emptyFn,
    onDragOut       : Ext.emptyFn,

    tip                         : null,
    skipWeekendsDuringDragDrop  : false,
    taskSelector                : null,
    deadlineSelector            : null,

    invalidTargetSelector : [
        // Stop task drag and drop when a resize handle, a terminal or a parent task is clicked
        '.' + Ext.baseCSSPrefix + 'resizable-handle',
        '.sch-resizable-handle',
        '.sch-gantt-terminal',
        '.sch-gantt-progressbar-handle',
        '.sch-rollup-task',
        '.sch-gantt-baseline-item .sch-gantt-item'
    ].join(','),

    constructor : function (viewEl, config) {
        // We want the proxy to live inside the view parent element since the view element is volatile as it refreshes.
        var el = config.el = viewEl.up();

        config          = config || {};
        Ext.apply(this, config);

        // Drag drop won't work in IE8 if running in an iframe
        // https://www.assembla.com/spaces/bryntum/tickets/712#/activity/ticket:
        if (Ext.isIE && (Ext.isIE8 || Ext.ieVersion < 9) && window.top !== window) {
            Ext.dd.DragDropManager.notifyOccluded = true;
        }

        this.proxy      = this.proxy || new Ext.dd.StatusProxy({
            shadow               : false,
            dropAllowed          : "sch-gantt-dragproxy",
            dropNotAllowed       : "sch-gantt-dragproxy",

            // HACK, we want the proxy inside the gantt chart, otherwise drag drop breaks in fullscreen mode
            ensureAttachedToBody : Ext.emptyFn
        });

        this.gantt.rtl && this.proxy.addCls('sch-rtl');

        var me          = this,
            gantt       = me.gantt;

        if (me.useTooltip) {
            me.tip      = new Gnt.Tooltip(Ext.apply({
                cls   : 'gnt-dragdrop-tip',
                gantt : gantt
            }, me.tooltipConfig));
        }

        me.callParent([ el, Ext.apply(config, { ddGroup : gantt.id + '-task-dd' }) ]);

        me.scroll       = false;
        me.isTarget     = true;
        me.ignoreSelf   = false;

        el.appendChild(me.proxy.el);

        gantt.on({
            destroy : me.destroy,
            scope   : me
        });
    },


    destroy : function () {
        if (this.tip) {
            this.tip.destroy();
        }
        clearTimeout(this.validDropTimer);
        this.callParent(arguments);
    },

    // On receipt of a mousedown event, see if it is within a draggable element.
    // Return a drag data object if so. The data object can contain arbitrary application
    // data, but it should also contain a DOM element in the ddel property to provide
    // a proxy to drag.
    getDragData : function (e) {
        if (Ext.fly(e.getTarget()).is(this.invalidTargetSelector)) {
            return;
        }

        var taskEl            = e.getTarget(this.taskSelector);
        var deadlineEl        = !taskEl && e.getTarget(this.deadlineSelector);
        var elementToDrag     = taskEl || deadlineEl;

        if (elementToDrag) {
            var ganttView           = this.gantt;
            var segmentNode         = taskEl && e.getTarget('.sch-gantt-task-segment');
            var sourceTask          = ganttView.resolveTaskRecord(elementToDrag);
            var segmentIndex        = 0;
            var minDate, maxDate, constrainingRegion;

            if (!sourceTask || sourceTask.isReadOnly()) {
                return;
            }

            // Check if we're dragging a split-task segment
            if (segmentNode) {
                segmentIndex = Number(segmentNode.getAttribute('data-segmentIndex'));

                // Dragging the first segment means move the entire task, otherwise drag the segment node
                if (segmentIndex > 0) {
                    elementToDrag = segmentNode;
                    sourceTask = sourceTask.getSegment(segmentIndex);
                }
            }

            if (taskEl && ganttView.fireEvent('beforetaskdrag', ganttView, sourceTask, e) === false) {
                return;
            }

            var xy                  = e.getXY();

            var copy                = elementToDrag.cloneNode(true),
                increment           = this.showExactDropPosition ? 0 : ganttView.getSnapPixelAmount(),
                origXY              = Ext.fly(elementToDrag).getXY();

            var offsets             = [ xy[ 0 ] - origXY[ 0 ], xy[ 1 ] - origXY[ 1 ] ];

            copy.id                 = Ext.id();
            var height              = Ext.fly(elementToDrag).getHeight();
            var width               = Ext.fly(elementToDrag).getWidth();

            // Height needs to be hardcoded since the copied task bar is put in the drag proxy
            Ext.fly(copy).setHeight(height);

            if (Ext.isIE8m  && taskEl && sourceTask.isMilestone()) {
                Ext.fly(copy).setSize(height + 5, height + 5);
            }

            if (!ganttView.rtl) {
                copy.style.left = -offsets[0] + 'px';
            }
            else {
                copy.style.left = width - offsets[0] + 'px';
            }

            // BEGIN CONSTRAINING PROXY ELEMENT

            if (segmentIndex > 0) {
                var previousSibling     = sourceTask.getPrevSegment();
                var nextSibling         = sourceTask.getNextSegment();
                minDate                 = Sch.util.Date.max(previousSibling.getEndDate(), ganttView.timeAxis.getStart());
                maxDate                 = nextSibling ? Sch.util.Date.min(nextSibling.getStartDate(), ganttView.timeAxis.getEnd()) : ganttView.timeAxis.getEnd();

                // Limit the dragging to the sibling segments (if any)
                constrainingRegion      = {
                    left  : ganttView.getCoordinateFromDate(minDate, false) + offsets[0],
                    right : ganttView.getCoordinateFromDate(maxDate, false) - width + offsets[0]
                };
            } else {
                constrainingRegion = Ext.fly(ganttView.findItemByChild(elementToDrag)).getRegion();
            }

            this.constrainTo(
                constrainingRegion,
                Ext.fly(elementToDrag).getRegion(),
                offsets[ 0 ],
                offsets[ 1 ]
            );

            // EOF CONSTRAINING PROXY ELEMENT

            if (increment >= 1) {
                this.setXConstraint(this.leftConstraint, this.rightConstraint, increment);
            }

            return {
                sourceNode     : elementToDrag,
                repairXY       : origXY,
                offsetX        : offsets[0],
                ddel           : copy,
                record         : sourceTask,
                duration       : Sch.util.Date.getDurationInMinutes(sourceTask.getStartDate(), sourceTask.getEndDate()),
                startPointDate : ganttView.getDateFromCoordinate(xy[0]),
                minDate        : minDate,
                maxDate        : maxDate,
                start          : null,
                isTaskDrag     : Boolean(taskEl),
                originalStart  : taskEl ? sourceTask.getStartDate() : sourceTask.getDeadlineDate(),
                // cached value of the validity of the drop position
                // Initialize to false for task drag, this value is continuously updated on mouse move.
                // For deadline elements, no validation is done and the operation is assumed to always be valid
                valid          : Boolean(deadlineEl)
            };
        }
    },

    onDragOver : function (e) {
        var dragContext = this.dragData,
            task        = dragContext.record,
            origStart   = dragContext.originalStart,
            gantt       = this.gantt,
            newStart    = gantt.getDateFromCoordinate(e.getXY()[ 0 ]);

        if (!dragContext.hidden) {
            Ext.fly(dragContext.sourceNode).hide();
            dragContext.hidden = true;
        }

        // User might have dragged proxy outside of the time axis area, just ignore
        if (!newStart) return;

        var timeDiff    = newStart - dragContext.startPointDate;
        var realStart   = new Date(origStart - 0 + timeDiff);
        var proxyEl     = this.proxy.el;

        if (dragContext.minDate) {
            realStart = Sch.util.Date.constrain(new Date(origStart - 0 + timeDiff), dragContext.minDate, dragContext.maxDate);
        }

        // the time diff method can be used for continuous time axis only
        // fallback to proxy element position resolving for filtered time axis
        if (gantt.timeAxis.isContinuous()) {
            newStart        = gantt.timeAxis.roundDate(realStart, gantt.snapRelativeToEventStartDate ? origStart : false);
        } else {
            // Adjust x position for certain task types
            var x           = proxyEl.getX() + (gantt.rtl ? proxyEl.getWidth() : 0) + gantt.getXOffset(task) - dragContext.offsetX;
            newStart        = gantt.getDateFromXY([ x, 0 ], 'round');
        }

        if (dragContext.isTaskDrag) {
            this.onTaskDrag(task, e, newStart, realStart);
        } else if (newStart && newStart - origStart !== 0) {
            this.onCustomElementDrag(task, e, newStart);
        }

        dragContext.start      = newStart;
    },

    onTaskDrag : function(task, e, newStart, realStart) {
        var dragContext = this.dragData;
        var gantt       = this.gantt;

        if (this.showExactDropPosition && this.skipWeekendsDuringDragDrop) {
            var offsetX         = 0;
            var afterDropStart  = task.skipNonWorkingTime(newStart, !task.isMilestone());
            var newEnd          = task.recalculateEndDate(afterDropStart);

            if (realStart.getTime() != afterDropStart.getTime()) {
                offsetX         = gantt.timeAxisViewModel.getDistanceBetweenDates(realStart, afterDropStart);
            }

            if (realStart > gantt.timeAxis.getStart()) {
                Ext.fly(dragContext.ddel.id).setWidth(gantt.timeAxisViewModel.getDistanceBetweenDates(
                        afterDropStart,
                        Sch.util.Date.min(newEnd, gantt.timeAxis.getEnd()))
                );

                if (offsetX) {
                    this.proxy.setX(this.proxy.getX() + offsetX);
                }
            }
        }

        if (newStart && newStart - dragContext.start !== 0) {
            dragContext.valid      = this.validatorFn.call(
                this.validatorFnScope || gantt,
                task,
                newStart,
                dragContext.duration,
                e
            ) !== false;

            if (this.tip) {
                var end = task.calculateEndDate(newStart, task.getDuration(), task.getDurationUnit());

                this.updateTip(task, newStart, end, dragContext.valid);
            }
        }
    },

    onCustomElementDrag : function(task, e, date) {
        if (this.tip) {
            this.updateTip(task, date, date, true);
        }
    },

    onStartDrag : function () {
        var dd  = this.dragData;
        var rec = dd.record;
        var tip = this.tip;

        if (tip) {
            tip.enable();
            this.updateTip(rec, this.dragData.originalStart, rec.getEndDate());
            tip.showBy(this.dragData.sourceNode, dd.repairXY[0] + dd.offsetX);
        }

        if (this.dragData.isTaskDrag) {
            this.gantt.fireEvent('taskdragstart', this.gantt, rec);
        }
    },


    updateTip : function (record, start, end) {
        if (this.dragData.isTaskDrag) {
            if (record.isMilestone() && start - Ext.Date.clearTime(start, true) === 0) {
                start   = Sch.util.Date.add(start, Sch.util.Date.MILLI, -1);
                end     = Sch.util.Date.add(end, Sch.util.Date.MILLI, -1);
            }
            this.tip.updateContent(start, end, true, record);
        } else {
            // nullify the start and pass only end date if we're dealing with a single point in time and not a range
            this.tip.updateContent(null, start, true, record);
        }
    },


    // Override, get rid of weird highlight fx in default implementation
    afterRepair : function () {
        Ext.fly(this.dragData.sourceNode).show();
        if (this.tip) {
            this.tip.hide();
        }
        this.dragging = false;
    },

    // Provide coordinates for the proxy to slide back to on failed drag.
    // This is the original XY coordinates of the draggable element.
    getRepairXY : function () {
        this.dragData.isTaskDrag && this.gantt.fireEvent('aftertaskdrop', this.gantt);

        return this.dragData.repairXY;
    },

    onDragDrop : function (e, id) {
        var me          = this,
            target      = me.cachedTarget || Ext.dd.DragDropMgr.getDDById(id),
            dragContext = me.dragData,
            gantt       = me.gantt,
            start       = dragContext.start,
            doFinalize  = true,
            validDrop   = dragContext.valid && start && dragContext.originalStart - start !== 0;

        dragContext.ddCallbackArgs = [ target, e, id ];

        if (this.tip) {
            this.tip.disable();
        }

        // If all seems ok, allow implementer to do an asynchronous last validation
        if (dragContext.isTaskDrag && validDrop) {
            dragContext.finalize   = function () { me.finalize.apply(me, arguments); };

            // Allow implementor to take control of the flow, by returning false from this listener,
            // to show a confirmation popup etc.
            doFinalize          = gantt.fireEvent('beforetaskdropfinalize', gantt, dragContext, e) !== false;
        }

        if (doFinalize) {
            this.finalize(validDrop);
        }
    },

    finalize    : function (updateRecords) {
        var me          = this,
            dragContext = this.dragData,
            gantt       = this.gantt,
            task        = dragContext.record,
            start       = dragContext.start,
            previousStartDate,
            currentStartDate;

        if (updateRecords) {
            previousStartDate = dragContext.originalStart;

            if (dragContext.isTaskDrag) {
                task.setStartDate(start, true, this.skipWeekendsDuringDragDrop, function() {

                    currentStartDate = task.getStartDate();

                    if (previousStartDate - currentStartDate !== 0) {
                        gantt.fireEvent('taskdrop', gantt, task);
                        // For our good friend IE9, the pointer cursor gets stuck without the defer
                        if (Ext.isIE9) {
                            me.proxy.el.setStyle('visibility', 'hidden');
                            me.validDropTimer = Ext.Function.defer(me.onValidDrop, 10, me, dragContext.ddCallbackArgs);
                        } else {
                            me.onValidDrop.apply(me, dragContext.ddCallbackArgs);
                        }

                    } else {
                        me.onInvalidDrop.apply(me, dragContext.ddCallbackArgs);
                    }

                    gantt.fireEvent('aftertaskdrop', gantt, task);
                });
            } else {
                task.setDeadlineDate(start);
                me.onValidDrop.apply(me, dragContext.ddCallbackArgs);
            }
        } else {
            me.onInvalidDrop.apply(me, dragContext.ddCallbackArgs);
            dragContext.isTaskDrag && gantt.fireEvent('aftertaskdrop', gantt, task);
        }
    },

    // HACK: Override for IE (or touch capable monitors), if you drag the task bar outside the window or iframe it crashes (missing e.target)
    // Ext JS calls with only the 'e' argument in this case which is a bit weird
    // https://www.assembla.com/spaces/bryntum/tickets/716
    onInvalidDrop : function(target, e, id) {
        if (!e) {
            e = target;
            target = target.getTarget() || document.body;
        }

        if (this.tip) {
            this.tip.disable();
        }

        return this.callParent([target, e, id]);
    },

    // CONSTRAINING OVERRIDES
    // @OVERRIDE
    autoOffset : function () {
        this.setDelta(0, 0);
    },

    // @OVERRIDE
    setXConstraint : function (iLeft, iRight, iTickSize) {
        this.leftConstraint = iLeft;
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
        this.topConstraint = iUp;
        this.bottomConstraint = iDown;

        this.minY = iUp;
        this.maxY = iDown;
        if (iTickSize) {
            this.setYTicks(this.initPageY, iTickSize);
        }

        this.constrainY = true;
    },

    constrainTo : function (constrainingRegion, elRegion, offsetX, offsetY) {
        this.resetConstraints();

        this.initPageX  = constrainingRegion.left + offsetX;
        this.initPageY  = elRegion.top + offsetY;

        this.setXConstraint(constrainingRegion.left, constrainingRegion.right, this.xTickSize);
        this.setYConstraint(elRegion.top - 1, elRegion.top - 1, this.yTickSize);
    },
    // EOF CONSTRAINING OVERRIDES


    // SCROLLMANAGER OVERRIDES
    startDrag : function() {
        this.gantt.el.ddScrollConfig    = {
            increment       : Ext.dd.ScrollManager.increment,
            hthresh         : Ext.dd.ScrollManager.hthresh,
            // disable the vertical container scroll while dragging the task
            vthresh         : -1
        };

        return this.callParent(arguments);
    },


    endDrag : function() {
        // remove previous constraints for container scroll
        delete this.gantt.el.ddScrollConfig;

        return this.callParent(arguments);
    }
    // EOF SCROLLMANAGER OVERRIDES
});

