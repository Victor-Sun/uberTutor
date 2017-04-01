/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.model.Task
@extends Sch.model.Range

This class represents a single task in your Gantt chart.

The inheritance hierarchy of this class includes the {@link Sch.model.Customizable} and {@link Ext.data.Model} classes. Fields that begin
 with a capital letter come from this class directly, and lowercase field names are inherited from {@link Ext.data.NodeInterface}.
This class will not only inherit fields but also a set of methods that stem from the {@link Ext.data.NodeInterface}.
Please refer to the documentation of those classes to become familiar with the base interface of this class.

By default, a Task has the following fields as seen below.

Task Fields
------

- `Id` - (mandatory) a unique identifier of the task
- `Name` - the name of the task (task title)
- `StartDate` - the start date of the task in the ISO 8601 format. See {@link Ext.Date} for a formats definitions.
- `EndDate` - the end date of the task in the ISO 8601 format, **see "Start and End dates" section for important notes**
- `Duration` - the numeric part of the task duration (the number of units)
- `DurationUnit` - the unit part of the task duration (corresponds to units defined in `Sch.util.Date`), defaults to "d" (days). Valid values are:
     - "ms" (milliseconds)
     - "s" (seconds)
     - "mi" (minutes)
     - "h" (hours)
     - "d" (days)
     - "w" (weeks)
     - "mo" (months)
     - "q" (quarters)
     - "y" (years)
- `Effort` - the numeric part of the task effort (the number of units). The effort of the "parent" tasks will be automatically set to the sum
of efforts of their "child" tasks
- `EffortUnit` - the unit part of the task effort (corresponds to units defined in `Sch.util.Date`), defaults to "h" (hours). Valid values are:
     - "ms" (milliseconds)
     - "s" (seconds)
     - "mi" (minutes)
     - "h" (hours)
     - "d" (days)
     - "w" (weeks)
     - "mo" (months)
     - "q" (quarters)
     - "y" (years)
- `PercentDone` - the current status of a task, expressed as the percentage completed (integer from 0 to 100)
- `Cls` - A CSS class that will be applied to each rendered task DOM element
- `BaselineStartDate` - the baseline start date of the task in the ISO 8601 format. See {@link Ext.Date} for a formats definitions.
- `BaselineEndDate` - the baseline end date of the task in the ISO 8601 format, **see "Start and End dates" section for important notes**
- `BaselinePercentDone` - the baseline status of a task, expressed as the percentage completed (integer from 0 to 100)
- `CalendarId` - the id of the calendar, assigned to task. Allows you to set the time when task can be performed.
Should be only provided for specific tasks - all tasks by default are assigned to the project calendar, provided as the
{@link Gnt.data.TaskStore#calendar} option.
- `SchedulingMode` - the field, defining the scheduling mode for the task. Based on this field some fields of the task
will be "fixed" (should be provided) and some - computed. See {@link #getSchedulingMode} for details.
- `ManuallyScheduled` - When set to `true`, the `StartDate` of the task will not be changed by any of its incoming dependencies
or constraints. Also, a manually scheduled parent task is not affected by its child tasks and behaves like any other normal task.
- `ConstraintType` - A string containing the alias for a constraint class (w/o the `gntconstraint` prefix). Valid values are:

  - "finishnoearlierthan"
  - "finishnolaterthan"
  - "mustfinishon"
  - "muststarton"
  - "startnoearlierthan"
  - "startnolaterthan"

If you want to define your own custom constraint class, you need to alias it:

        Ext.define('MyConstraint', {
            extend      : 'Gnt.constraint.Base',

            alias       : 'gntconstraint.myconstraint',
            ...
        });

- `ConstraintDate` - A date, defining the constraint boundary date, if applicable.
- `Note` A freetext note about the task.
- `Rollup` Set this to 'true' if the task should rollup to its parent task.
- `ShowInTimeline` Set this to true if this task should be shown in the Timeline widget
- `DeadlineDate` A deadline date for this task
- `ReadOnly` 'true' to indicate that a task cannot be modified.

If you want to add new fields or change the name/options for the existing fields,
you can do that by subclassing this class (see example below).

Subclassing the Task class
--------------------

The name of any field can be customized in the subclass. Please refer to {@link Sch.model.Customizable} for details.

    Ext.define('MyProject.model.Task', {
        extend              : 'Gnt.model.Task',

        nameField           : 'myName',
        percentDoneField    : 'percentComplete',

        isAlmostDone : function () {
            return this.get('percentComplete') > 80;
        },
        ...
    });

Creating a new Task instance programmatically
--------------------

To create a new task programmatically, simply call the Gnt.model.Task constructor and pass in any default field values.

    var newTask = new Gnt.model.Task({
        Name            : 'An awesome task',
        PercentDone     : 80, // So awesome it's almost done
        ...
    });

    // To take weekends and non-working time into account, the new task needs a reference to the task store (which has access to the global calendar)
    newTask.taskStore = taskStore;

    // Initialize new task to today
    newTask.setStartDate(new Date());

    // This is a leaf task
    newTask.set('leaf', true);

    // Now it will appear in the UI if the Gantt panel is rendered
    taskStore.getRootNode().appendChild(newTask);


Start and End dates
-------------------

For all tasks, the range between start date and end date is supposed to be not-inclusive on the right side: StartDate <= date < EndDate.
So, for example, the task which starts at 2011/07/18 and has 2 days duration, should have the end date: 2011/07/20, **not** 2011/07/19 23:59:59.

Also, both start and end dates of tasks in our components are *points* on time axis. For example, if user specifies that some task starts
01/01/2013 and has 1 day duration, that means the start point is 01/01/2013 00:00 and end point is 02/01/2013 00:00.
However, its a common requirement, to *display* such task as task with both start and end date as 01/01/2013. Because of that,
during rendering the end date is adjusted so for such task user will see a 01/01/2013 end date. In the model layer however, the precise point is stored.

Conversion to "days" duration unit
-----------------------------------

Some duration units cannot be converted to "days" consistently. For example a month may have 28, 29, 30 or 31 days. The year may have 365 or 366 days and so on.
So in such conversion operations, we will always assume that a task with a duration of 1 month will have a duration of 30 days.
This is {@link Gnt.data.Calendar#daysPerMonth a configuration option} of the calendar class.

Task API
-------

One important thing to consider is that, if you are using the availability/scheduling modes feature, then you need to use the task API call to update the fields like `StartDate / EndDate / Duration`.
Those calls will calculate the correct value of each the field, taking into account the information from calendar/assigned resources.

Server-side integration
-----------------------

Also, at least for now you should not use the "save" method of the model available in Ext 4:

    task.save() // WON'T WORK

This is because there are some quirks in using CRUD for Ext tree stores. These quirks are fixed in the TaskStore. To save the changes in task to server
use the "sync" method of the task store:

    taskStore.sync() // OK

*/
Ext.define('Gnt.model.Task', {
    extend              : 'Sch.model.Range',

    alias               : 'gntmodel.event',

    requires            : [
        'Sch.util.Date',
        'Ext.data.NodeInterface'
    ],

    uses                : [
        'Gnt.model.TaskSegment'
    ],

    mixins              : [
        'Gnt.model.mixin.ProjectableModel',
        'Gnt.model.task.More',
        'Gnt.model.task.Constraints',
        'Gnt.model.task.Splittable'
    ],

    segmentClassName    : 'Gnt.model.TaskSegment',

    /**
     * @cfg {String} idProperty The name of the field treated as this Model's unique id.
     */
    idProperty          : "Id",

    customizableFields     : [
        { name: 'Duration', type: 'number', allowNull: true },
        { name: 'Effort', type: 'number', allowNull: true },
        { name: 'EffortUnit', type: 'string', defaultValue: 'h' },
        { name: 'CalendarId', type: 'string'},
        { name: 'Note', type: 'string'},

        {
            name: 'DurationUnit',
            type: 'string',
            defaultValue: "d",
            // make sure the default value is applied when user provides empty value for the field, like "" or null
            convert: function (value) {
                return value || "d";
            }
        },
        { name: 'PercentDone', type: 'number', defaultValue: 0 },

        { name: 'ConstraintType', type: 'string', defaultValue: '' },
        { name: 'ConstraintDate', type: 'date', dateFormat: 'c' },

        { name: 'ManuallyScheduled', type: 'boolean', defaultValue: false },
        { name: 'SchedulingMode', type: 'string', defaultValue: 'Normal' },

        { name: 'BaselineStartDate', type: 'date', dateFormat: 'c' },
        { name: 'BaselineEndDate', type: 'date', dateFormat: 'c' },
        { name: 'BaselinePercentDone', type: 'int', defaultValue: 0 },
        { name: 'Draggable', type: 'boolean', persist: false, defaultValue : true },   // true or false
        { name: 'Resizable', persist: false, defaultValue : '' },                      // true, false, 'start' or 'end'
        { name: 'ReadOnly', persist : false, type : 'bool', defaultValue : false },


        { name: 'Rollup', type: 'boolean', defaultValue: false },
        {
            name    : 'Segments',
            persist : true,

            convert : function (value, record) {
                return record.processSegmentsValue(value, record);
            },

            serialize : function (value) {
                if (!value) return null;

                return Ext.Array.map([].concat(value), function(segment) {
                    return segment.serialize();
                });
            }
        },
        // Two fields which specify the relations between "phantom" tasks when they are
        // being sent to the server to be created (e.g. when you create a new task containing a new child task).
        { name: 'PhantomId', type : 'string' },
        { name: 'PhantomParentId', type : 'string' },

        { name : 'ShowInTimeline', type : 'bool' },
        { name : 'DeadlineDate', type: 'date', dateFormat: 'c' },

        // Override NodeInterface defaults
        { name: 'index', type : 'int', persist : true }
    ],

    /**
     * @cfg {String} constraintTypeField The name of the field specifying the constraint type of this task.
     */
    constraintTypeField     : 'ConstraintType',

    /**
     * @cfg {String} constraintDateField The name of the field specifying the constraint date for this task.
     */
    constraintDateField     : 'ConstraintDate',

    /**
     * @cfg {String} draggableField The name of the field specifying if the event should be draggable in the timeline
     */
    draggableField          : 'Draggable',

    /**
     * @cfg {String} resizableField The name of the field specifying if/how the event should be resizable.
     */
    resizableField          : 'Resizable',

    /**
     * @cfg {String} nameField The name of the field that holds the task name. Defaults to "Name".
     */
    nameField               : 'Name',

    /**
     * @cfg {String} durationField The name of the field holding the task duration.
     */
    durationField           : 'Duration',

    /**
     * @cfg {String} durationUnitField The name of the field holding the task duration unit.
     */
    durationUnitField       : 'DurationUnit',

    /**
     * @cfg {String} effortField The name of the field holding the value of task effort.
     */
    effortField             : 'Effort',

    /**
     * @cfg {String} effortUnitField The name of the field holding the task effort unit.
     */
    effortUnitField         : 'EffortUnit',

    /**
     * @cfg {String} percentDoneField The name of the field specifying the level of completion.
     */
    percentDoneField        : 'PercentDone',

    /**
     * @cfg {String} manuallyScheduledField The name of the field defining if a task is manually scheduled or not.
     */
    manuallyScheduledField  : 'ManuallyScheduled',

    /**
     * @cfg {String} schedulingModeField The name of the field defining the scheduling mode of the task.
     */
    schedulingModeField     : 'SchedulingMode',

    /**
     * @cfg {String} rollupField The name of the field specifying if the task should rollup to its parent task.
     */
    rollupField             : 'Rollup',

    /**
     * @cfg {String} calendarIdField The name of the field defining the id of the calendar for this specific task. Task calendar has the highest priority.
     */
    calendarIdField         : 'CalendarId',

    /**
     * @cfg {String} baselineStartDateField The name of the field that holds the task baseline start date.
     */
    baselineStartDateField  : 'BaselineStartDate',

    /**
     * @cfg {String} baselineEndDateField The name of the field that holds the task baseline end date.
     */
    baselineEndDateField    : 'BaselineEndDate',

    /**
     * @cfg {String} baselinePercentDoneField The name of the field specifying the baseline level of completion.
     */
    baselinePercentDoneField    : 'BaselinePercentDone',

    /**
     * @cfg {String} noteField The name of the field specifying the task note.
     */
    noteField               : 'Note',

    /**
     * @cfg {String} segmentsField The name of the field specifying the task segments.
     */
    segmentsField           : 'Segments',

    /*
     * @cfg {Boolean} readOnlyField The name of the field specifying if the task is read only. When set to true, a task
     * is not draggable, resizable and for all its fields {@link #isEditable} returns `false`
     */
    readOnlyField           : 'ReadOnly',

    /**
     * @cfg {Gnt.data.Calendar} calendar
     * Optional. An explicitly provided {@link Gnt.data.Calendar calendar} instance. Usually will be retrieved by the task from the {@link Gnt.data.TaskStore task store}.
     */
    calendar                : null,

    /**
     * @cfg {Gnt.data.DependencyStore} dependencyStore
     * Optional. An explicitly provided {@link Gnt.data.DependencyStore} with dependencies information. Usually will be retrieved by the task from the {@link Gnt.data.TaskStore task store}.
     */
    dependencyStore         : null,

    /**
     * @cfg {Gnt.data.TaskStore} taskStore
     * Optional. An explicitly provided Gnt.data.TaskStore with tasks information. Usually will be set by the {@link Gnt.data.TaskStore task store}.
     */
    taskStore               : null,

    /**
     * @cfg {String} phantomIdField The name of the field specifying the phantom id when this task is being 'realized' by the server.
     */
    phantomIdField          : 'PhantomId',

    /**
     * @cfg {String} phantomParentIdField The name of the field specifying the parent task phantom id when this task is being 'realized' by the server.
     */
    phantomParentIdField    : 'PhantomParentId',

    /**
     * @cfg {String} showInTimelineField The name of the field saying if the task has to be displayed in a project timeline view.
     */
    showInTimelineField     : 'ShowInTimeline',

    /**
     * @cfg {String} deadlineDateField The name of the field that holds the task deadline date.
     */
    deadlineDateField  : 'DeadlineDate',

    normalized              : false,

    recognizedSchedulingModes   : [ 'Normal', 'FixedDuration', 'EffortDriven', 'DynamicAssignment' ],

    /**
     * Returns the constraint type of the task.
     * @method getConstraintType
     * @return {String} Constraint type. The type string might be one of the following values:
     *
     *  - `finishnoearlierthan`
     *  - `finishnolaterthan`
     *  - `mustfinishon`
     *  - `muststarton`
     *  - `startnoearlierthan`
     *  - `startnolaterthan`
     */

    /**
     * Returns the constraint date of the task.
     * @method getConstraintDate
     * @return {Date} Constraint date
     */

    /**
     * @cfg {Boolean} convertEmptyParentToLeaf
     *
     * This configuration option allows you to control whether an empty parent task should be converted into a leaf. Note, that
     * it's not a new field, but a regular configuration property of this class.
     *
     * Usually you will want to enable/disable it for the whole class:
     *

    Ext.define('MyApp.model.Task', {
        extend                      : 'Gnt.model.Task',

        convertEmptyParentToLeaf    : false
    })

     */
    convertEmptyParentToLeaf    : true,

    /**
     * @cfg {Boolean} autoCalculateEffortForParentTask
     *
     * This configuration option enables auto-calculation of the effort value for the parent task. When this option is enabled,
     * effort value of the parent tasks becomes not editable.
     *
     * Usually you will want to enable/disable it for the whole class:
     *

    Ext.define('MyApp.model.Task', {
        extend                              : 'Gnt.model.Task',

        autoCalculateEffortForParentTask    : false
    })

     *
     */
    autoCalculateEffortForParentTask        : true,

    /**
     * @cfg {Boolean} autoCalculatePercentDoneForParentTask
     *
     * This configuration option enables auto-calculation of the percent done value for the parent task. When this option is enabled,
     * percent done value of the parent tasks becomes not editable.
     *
     * Usually you will want to enable/disable it for the whole class:
     *

    Ext.define('MyApp.model.Task', {
        extend                                  : 'Gnt.model.Task',

        autoCalculatePercentDoneForParentTask   : false
    })

     *
     */
    autoCalculatePercentDoneForParentTask   : true,



    isHighlighted               : false,

    calendarWaitingListener     : null,

    childTasksDuration          : null,
    completedChildTasksDuration : null,

    totalCount                  : null,

    /**
     * @property {Gnt.model.Dependency[]} predecessors An array of dependencies, which are predecessors for this task.
     * To access this property safely you can use {@link #getIncomingDependencies} method.
     *
     * @deprecated 4.2 Use {@link getIncomingDependencies} method instead
     *
     * NOTE: The property is managed by Gnt.data.util.TaskDependencyCache class
     */
    predecessors : null,

    /**
     * @property {Gnt.model.Dependency[]} successors An array of dependencies, which are successors for this task.
     * To access this property safely you can use {@link #getOutgoingDependencies} method.
     *
     * @deprecated 4.2 Use {@link getOutgoingDependencies} method instead
     *
     * NOTE: The property is managed by Gnt.data.util.TaskDependencyCache class
     */
    successors : null,

    /**
     * @private
     * @deprecated Please use {@link #getAssignments} method.
     * @property {Gnt.model.Assignment[]} assignments An array of assignments for this task.
     * To access this property safely you can use {@link #getAssignments} method.
     */

    // special flag, that prevents parent from being converted into leafs when using "replaceChild" method
    // see `data_components/077_task_replace_child.t.js`
    removeChildIsCalledFromReplaceChild     : false,

    // see comments in `endEdit` override
    savedDirty                  : null,

    useOwnCalendarAsConverter   : false,

    constructor : function () {
        this._singleProp = {};

        this.initProjectable();

        this.callParent(arguments);

        if (this.phantom) {
            this.data[ this.phantomIdField ]    = this.getId();
        }

        if (this.id === 'root') {
            this.convertEmptyParentToLeaf = false;
        }

        // NOTE: The properties are managed by Gnt.data.util.TaskDependencyCache class
        // TODO: Remove this code when those properties are remove from Task interface
        this.predecessors = [];
        this.successors   = [];
    },


    // should be called once after initial loading - will convert the "EndDate" field to "Duration"
    // the model should have the link to calendar
    normalize: function () {
        var durationUnit    = this.getDurationUnit(),
            startDate       = this.getStartDate(),
            endDate         = this.getEndDate(),
            data            = this.data,
            taskStore       = this.getTaskStore(true),
            schedulingMode  = this.getSchedulingMode();

        if (schedulingMode == 'Manual') {
            schedulingMode  = data[ this.schedulingModeField ] = 'Normal';
            data[ this.manuallyScheduledField ] = true;
        }

        var endDateField    = this.endDateField;

        // normalize segments if required
        if (taskStore && this.isSegmented()) {
            this.normalizeSegments();

            var last;
            // if task is still segmented after segments normalization
            // let's set the task end to the last segment finish
            if (last = this.getLastSegment()) {
                endDate = data[ endDateField ] = last.getEndDate();
            }
        }

        var duration        = this.getDuration();
        var effortField     = this.effortField;

        if (endDate && this.inclusiveEndDate) {
            // End date supplied, if end dates are inclusive we need to adjust them -
            // but only IF:
            //      * The end-date dateFormat does not contain any hour info, OR
            //      * The end-date dateFormat does contain any hour info AND it has no hours/minutes/seconds/ms

            var format = this.getField(endDateField).dateFormat;

            var doAdjust = (format && !Ext.Date.formatContainsHourInfo(format)) ||
                (endDate.getHours() === 0 && endDate.getMinutes() === 0 && endDate.getSeconds() === 0 && endDate.getMilliseconds() === 0);

            if (doAdjust) {
                if (Ext.isNumber(duration)) {
                    // Recalculate end date based on duration
                    endDate = data[ endDateField ] = this.calculateEndDate(startDate, duration, durationUnit);
                } else {
                    // Simply add 1 day to end date
                    endDate = data[ endDateField ] = Ext.Date.add(endDate, Ext.Date.DAY, 1);
                }
            }
        }

        // for all scheduling modes
        if (duration == null && startDate && endDate) {
            duration    = data[ this.durationField ] = this.calculateDuration(startDate, endDate, durationUnit);
        }

        if ((schedulingMode == 'Normal' || this.isManuallyScheduled()) && endDate == null && startDate && Ext.isNumber(duration)) {
            endDate     = data[ endDateField ] = this.calculateEndDate(startDate, duration, durationUnit);
        }

        // accessing the field value directly here, since we are interested in "raw" value
        // `getEffort` now returns 0 for empty effort values
        var effort          = this.get(effortField),
            effortUnit      = this.getEffortUnit();

        switch (schedulingMode) {

            case 'FixedDuration' :

                if (endDate == null && startDate && Ext.isNumber(duration)) {
                    endDate = data[ endDateField ] = this.calculateEndDate(startDate, duration, durationUnit);
                }

                if (effort == null && startDate && endDate) {
                    data[ effortField ] = this.calculateEffort(startDate, endDate, effortUnit);
                }

                break;

            case 'EffortDriven' :

                if (effort == null && startDate && endDate) {
                    data[ effortField ] = this.calculateEffort(startDate, endDate, effortUnit);
                }

                if (endDate == null && startDate && effort) {
                    data[ endDateField ]  = this.calculateEffortDrivenEndDate(startDate, effort, effortUnit);

                    // for "effortDriven" task, user can only provide StartDate and Effort - that's all we need
                    if (duration == null) {
                        data[ this.durationField ] = this.calculateDuration(startDate, data[ endDateField ], durationUnit);
                    }
                }

                break;

            default :

                if (endDate == null && startDate && Ext.isNumber(duration)) {
                    endDate = data[endDateField] = this.calculateEndDate(startDate, duration, durationUnit);
                }

            break;
        }

        var calendarId      = this.getCalendarId();

        if (calendarId) this.setCalendarId(calendarId, true);

        this.normalized = true;
    },


    getUnitConverter : function () {
        return this.useOwnCalendarAsConverter && this.getCalendar() || this.getProjectCalendar();
    },


    // recursive task
    normalizeParent : function () {
        var childNodes              = this.childNodes;

        var totalEffortInMS         = 0;
        var totalDurationInMS       = 0;
        var completedDurationInMS   = 0;

        var autoCalculatePercentDoneForParentTask   = this.autoCalculatePercentDoneForParentTask;
        var autoCalculateEffortForParentTask        = this.autoCalculateEffortForParentTask;

        for (var i = 0; i < childNodes.length; i++) {
            var child               = childNodes[ i ];
            var isLeaf              = child.isLeaf();

            if (!isLeaf) child.normalizeParent();

            if (autoCalculateEffortForParentTask) {
                totalEffortInMS         += child.getEffort('MILLI');
            }

            if (autoCalculatePercentDoneForParentTask) {
                var durationInMS        = isLeaf ? child.getDuration('MILLI') || 0 : child.childTasksDuration;

                totalDurationInMS       += durationInMS;
                completedDurationInMS   += isLeaf ? durationInMS * (child.getPercentDone() || 0) : child.completedChildTasksDuration;
            }
        }

        if (autoCalculatePercentDoneForParentTask) {
            this.childTasksDuration             = totalDurationInMS;
            this.completedChildTasksDuration    = completedDurationInMS;

            var newPercentDone          = totalDurationInMS ? completedDurationInMS / totalDurationInMS : 0;

            if (this.getPercentDone() != newPercentDone)    this.data[ this.percentDoneField ] = newPercentDone;
        }

        if (autoCalculateEffortForParentTask) {
            if (this.getEffort('MILLI') != totalEffortInMS) this.data[ this.effortField ] = this.getUnitConverter().convertMSDurationToUnit(totalEffortInMS, this.getEffortUnit());
        }
    },


    /**
     * Returns the {@link Gnt.data.Calendar calendar} instance, associated with this task. If task has no own calendar, it will be recursively looked up
     * starting from task's parent. If no one from parents have own calendar then project calendar will be returned.
     * See also `ownCalendarOnly` parameter and {@link #getOwnCalendar}, {@link #getProjectCalendar} methods.
     *
     * @param {Boolean} ownCalendarOnly (optional) When set to true, return only own calendar of this task and `null` if task has no calendar
     *
     * @return {Gnt.data.Calendar} calendar
     */
    getCalendar: function (ownCalendarOnly) {
        return ownCalendarOnly ? this.getOwnCalendar() : this.getOwnCalendar() || this.parentNode && this.parentNode.getCalendar() || this.getProjectCalendar();
    },


    /**
     * Returns the {@link Gnt.data.Calendar calendar} instance, associated with this task (if any). See also {@link #calendarIdField}.
     *
     * @return {Gnt.data.Calendar} calendar
     */
    getOwnCalendar : function () {
        var calendarId    = this.get(this.calendarIdField);

        return calendarId ? Gnt.data.Calendar.getCalendar(calendarId) : this.calendar;
    },

    // TODO: cache project
    /**
     * Returns the {@link Gnt.model.Project project} instance, associated with this task if this task belongs to a project
     *
     * @return {Gnt.model.Project} project
     */
    getProject : function () {
        var me      = this,
            project = null;

        this.bubble(function (task) {
            if (me !== task && task.isProject) {
                project = task;
                return false;
            }
        }, this);

        return project;
    },


    /**
     * Returns the {@link Gnt.data.Calendar calendar} instance, associated with the project of this task (with the TaskStore instance
     * this task belongs to).
     *
     * @return {Gnt.data.Calendar} calendar
     */
    getProjectCalendar: function () {
        var store       = this.getTaskStore(true);
        var calendar    = store && store.getCalendar() || this.parentNode && this.parentNode.getProjectCalendar() || this.isRoot() && this.calendar;

        if (!calendar) {
            Ext.Error.raise("Can't find a project calendar in `getProjectCalendar`");
        }

        return calendar;
    },


    /**
     * Sets the {@link Gnt.data.Calendar calendar}, associated with this task. Calendar must have a {@link Gnt.data.Calendar#calendarId calendarId} property
     * defined, which will be saved in the `CalendarId` field of this task.
     *
     * @param {Gnt.data.Calendar/String} calendar A calendar instance or string with calendar id
     * @param {Function} [callback] Callback function to call after task calendar has been changed and possible changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    setCalendar: function (calendar, callback) {
        var me = this,
            isCalendarInstance  = calendar instanceof Gnt.data.Calendar;

        if (isCalendarInstance && !calendar.calendarId) {
            throw new Error("Can't set calendar w/o `calendarId` property");
        }

        return me.setCalendarId(isCalendarInstance ? calendar.calendarId : calendar, false, callback);
    },


    setCalendarId : function(calendarId, isInitial, callback) {
        var me = this;

        if (!isInitial) {
            me.propagateChanges(
                function() {
                    return me.setCalendarIdWithoutPropagation(calendarId, isInitial);
                },
                callback
            );
        }
        else {
            me.setCalendarIdWithoutPropagation(calendarId, isInitial);
        }
    },


    onCalendarChange : function (calendar) {
        if (!this.isReadOnly()) {
            this.adjustToCalendarWithoutPropagation();
        }
    },


    setCalendarIdWithoutPropagation : function (calendarId, isInitial) {
        var propagate = false;

        if (calendarId instanceof Gnt.data.Calendar) calendarId = calendarId.calendarId;

        var prevCalendarId  = this.getCalendarId();

        if (prevCalendarId != calendarId || isInitial) {

            propagate = true;

            if (this.calendarWaitingListener) {
                this.calendarWaitingListener.destroy();
                this.calendarWaitingListener = null;
            }

            var listeners       = {
                calendarchange  : this.onCalendarChange,
                scope           : this
            };

            var prevInstance    = this.calendar || Gnt.data.Calendar.getCalendar(prevCalendarId);

            // null-ifying the "explicit" property - it should not be used at all generally, only "calendarId"
            this.calendar   = null;

            prevInstance && prevInstance.un(listeners);

            this.set(this.calendarIdField, calendarId);

            var calendarInstance    = Gnt.data.Calendar.getCalendar(calendarId);

            if (calendarInstance) {
                calendarInstance.on(listeners);

                if (!isInitial) this.onCalendarChange();
            } else {
                this.calendarWaitingListener = Ext.data.StoreManager.on('add', function () {
                    calendarInstance    = Gnt.data.Calendar.getCalendar(calendarId);

                    if (calendarInstance) {
                        this.calendarWaitingListener.destroy();
                        this.calendarWaitingListener = null;

                        calendarInstance.on(listeners);

                        this.onCalendarChange();
                    }
                }, this, { destroyable : true });
            }
        }

        return propagate;
    },


    /**
     * Returns the dependency store, associated with this task.
     *
     * @return {Gnt.data.DependencyStore} The dependency store instance
     */
    getDependencyStore: function () {
        var taskStore = this.getTaskStore(true),
            dependencyStore = taskStore && taskStore.getDependencyStore();

        return dependencyStore;
    },


    /**
     * Returns the resource store, associated with this task.
     *
     * @return {Gnt.data.Resource} The resource store instance
     */
    getResourceStore : function () {
        var taskStore = this.getTaskStore(true);
        return taskStore && taskStore.getResourceStore();
    },


    /**
     * Returns the assignment store, associated with this task.
     *
     * @return {Gnt.data.AssignmentStore} The assignment store instance
     */
    getAssignmentStore : function () {
        var taskStore = this.getTaskStore(true);
        return taskStore && taskStore.getAssignmentStore();
    },


    /**
     * Returns the {@link Gnt.data.TaskStore task store} instance, associated with this task
     *
     * @return {Gnt.data.TaskStore} task store
     */
    getTaskStore: function (ignoreAbsense) {
        var me = this;

        if (!me.taskStore) {
            me.taskStore = me.getTreeStore() || me.parentNode && me.parentNode.getTaskStore(ignoreAbsense);
        }

        if (!me.taskStore && !ignoreAbsense) {
            Ext.Error.raise("Can't find a taskStore in `getTaskStore`");
        }

        return me.taskStore;
    },

    getEventStore : function() {
        return this.getTaskStore();
    },

    /**
     * Provides a reference to a {@link Gnt.data.TaskStore task store} instance, which the task will use to access the global
     * {@link Gnt.data.Calendar calendar}. Calling this does *not* add the model to the task store. Call this method if you want to use
     * methods like {@link #setStartDate} or {@link #setEndDate} that should take the store calendar into account.
     *
     * @param {Gnt.data.TaskStore} taskStore The task store
     */
    setTaskStore : function (taskStore) {
        this.taskStore = taskStore;
    },


    /**
     * Returns true if the task is manually scheduled or if it is completed. Manually scheduled task is not affected by incoming dependencies or
     * constraints. Also, the manually scheduled parent task is not affected by its child tasks positions and behaves like any other normal task.
     *
     * @return {Boolean} The value of the ManuallyScheduled field
     */
    isManuallyScheduled : function () {
        return this.get(this.schedulingModeField) === 'Manual' ||
               this.get(this.manuallyScheduledField) ||
               this.isCompleted();
    },


    isShowInTimeline : function () {
        return Boolean(this.getShowInTimeline());
    },

    /*
     * Sets the task manually scheduled status.
     * If that field was set to "Manual", calling this method with false value will set the scheduling mode to "Normal".
     *
     * @param {Boolean} value The new value of the SchedulingMode field
     * @param {Function} [callback] Callback function to call after effort has been set and possible changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    setManuallyScheduled: function (value, callback) {

        var me  = this;

        me.propagateChanges(
            function() {
                return me.setManuallyScheduledWithoutPropagation(value);
            },
            callback
        );
    },

    setManuallyScheduledWithoutPropagation : function(value) {
        var me = this,
            propagationSource;

        if (me.isManuallyScheduled() != value) {
            this.set(me.manuallyScheduledField, value);

            // if the task is no longer manually scheduled we align it by incoming dependencies
            if (!value) propagationSource = me.getPredecessors()[0];

            propagationSource = propagationSource || me;
        }

        return propagationSource;
    },


    /**
     * @method getSchedulingMode
     *
     * Returns the scheduling mode of this task. May be one of the
     * following strings:
     *
     * - `Normal` is the default (and backward compatible) mode. It means the task will be scheduled based on information
     * about its start/end dates, task own calendar (project calendar if there's no one) and calendars of the assigned resources.
     *
     * - `FixedDuration` mode means, that task has fixed start and end dates, but its effort will be computed dynamically,
     * based on the assigned resources information. Typical example of such task is - meeting. Meetings typically have
     * pre-defined start and end dates and the more people are participating in the meeting, the more effort is spent on the task.
     * When duration of such task increases, its effort is increased too (and vice-versa). Note: fixed start and end dates
     * here doesn't mean that a user can't update them via GUI, the only field which won't be editable in GUI is the effort field,
     * it will be calculated according to duration and resources assigned to the task.
     *
     * - `EffortDriven` mode means, that task has fixed effort and computed duration. The more resources will be assigned
     * to this task, the less the duration will be. The typical example will be a "paint the walls" task -
     * several painters will complete it faster.
     *
     * - `DynamicAssignment` mode can be used when both duration and effort of the task are fixed. The computed value in this
     * case will be - the assignment units of the resources assigned. In this mode, the assignment level of all assigned resources
     * will be updated to evenly distribute the task's workload among them.
     *
     * @return {String} scheduling mode string
     */


    /**
     * Sets the scheduling mode for this task.
     *
     * @param {String} value Name of the scheduling mode. Should be one of the
     * following strings:
     *
     * - `Normal` is the default (and backward compatible) mode. It means the task will be scheduled based on information
     * about its start/end dates, task own calendar (project calendar if there's no one) and calendars of the assigned resources.
     *
     * - `FixedDuration` mode means, that task has fixed start and end dates, but its effort will be computed dynamically,
     * based on the assigned resources information. Typical example of such task is - meeting. Meetings typically have
     * pre-defined start and end dates and the more people are participating in the meeting, the more effort is spent on the task.
     * When duration of such task increases, its effort is increased too (and vice-versa). Note: fixed start and end dates
     * here doesn't mean that a user can't update them via GUI, the only field which won't be editable in GUI is the effort field,
     * it will be calculated according to duration and resources assigned to the task.
     *
     * - `EffortDriven` mode means, that task has fixed effort and computed duration. The more resources will be assigned
     * to this task, the less the duration will be. The typical example will be a "paint the walls" task -
     * several painters will complete it faster.
     *
     * - `DynamicAssignment` mode can be used when both duration and effort of the task are fixed. The computed value in this
     * case will be - the assignment units of the resources assigned. In this mode, the assignment level of all assigned resources
     * will be updated to evenly distribute the task's workload among them.
     *
     * - `Manual` **this mode is deprecated. Please set "ManuallyScheduled" field to `true` instead.**
     *
     * @param {Function} [callback] Callback function to call after task's scheduling mode has been changed and possible
     *  changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for cancelling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    setSchedulingMode : function(value, callback) {
        var me = this;

        me.propagateChanges(
            function () {
                return me.setSchedulingModeWithoutPropagation(value);
            },
            callback
        );
    },


    setSchedulingModeWithoutPropagation : function(value) {
        var me = this,
            propagationSource;

        // <debug>
        Ext.Array.contains(me.recognizedSchedulingModes, value) ||
           Ext.Error.raise("Unrecognized scheduling mode: " + value);
        // </debug>

        if (me.getSchedulingMode() != value) {

            me.set(this.schedulingModeField, value);

            switch (value) {
                case 'FixedDuration'    : me.updateEffortBasedOnDuration(); break;
                case 'EffortDriven'     : me.updateSpanBasedOnEffort(); break;
            }

            var predecessors = me.getPredecessors();

            if (predecessors.length) {
                propagationSource = predecessors[0];
            } else {
                propagationSource = me;
            }
        }

        return propagationSource;
    },


    /**
     * @method getSegments
     * Gets segments of the task
     * @returns {Gnt.model.TaskSegment[]} Task segments
     */

    skipWorkingTime : function (date, duration, isForward, segments) {
        var result;
        var durationLeft;

        isForward   = isForward !== false;

        var cfg             = {
            isForward   : isForward,
            segments    : segments || false,
            // take resources into account if any
            resources   : this.hasResources(),
            fn          : function (from, to) {
                var diff            = to - from,
                    dstDiff         = new Date(from).getTimezoneOffset() - new Date(to).getTimezoneOffset();

                if (diff >= durationLeft) {
                    result          = new Date((isForward ? from : to) - 0 + (isForward ? 1 : -1) * durationLeft);

                    return false;
                } else {
                    durationLeft    -= diff + dstDiff * 60 * 1000;
                }
            }
        };

        if (Ext.isObject(date)) {
            Ext.apply(cfg, date);
        } else {
            if (isForward) {
                cfg.startDate   = date;
            } else {
                cfg.endDate     = date;
            }
        }

        durationLeft    = duration || cfg.duration;

        if (!durationLeft) return date;

        this.forEachAvailabilityInterval(cfg);

        return result;
    },

    /**
     * @ignore
     */
    skipNonWorkingTime : function (date, isForward, segments) {
        var skipped     = false;

        isForward       = isForward !== false;

        var cfg         = {
            isForward   : isForward,
            segments    : segments || false,
            // take resources into account if any
            resources   : this.hasResources(),
            fn          : function (from, to) {
                // if found interval has zero time length then nothing to skip so we just ignore it.
                // TODO: need to review a possibility to move this condition right into forEachAvailabilityInterval() body
                if (from !== to) {
                    date        = isForward ? from : to;
                    skipped     = true;

                    return false;
                }
            }
        };

        if (Ext.isObject(date)) {
            Ext.apply(cfg, date);
        } else {
            if (isForward) {
                cfg.startDate   = date;
            } else {
                cfg.endDate     = date;
            }
        }

        // resetting the date to the earliest availability interval
        this.forEachAvailabilityInterval(cfg);

        return skipped ? new Date(date) : this.getCalendar().skipNonWorkingTime(date, isForward);
    },


    /**
     * @method getStartDate
     *
     * Returns the start date of this task
     *
     * @return {Date} start date
     */


    /**
     * Depending from the arguments, set either `StartDate + EndDate` fields of this task, or `StartDate + Duration`
     * considering the weekends/holidays rules. The modifications are wrapped with `beginEdit/endEdit` calls.
     *
     * @param {Date} date Start date to set
     * @param {Boolean} [keepDuration=true] Pass `true` to keep the duration of the task ("move" the task), `false` to change the duration ("resize" the task).
     * @param {Boolean} [skipNonWorkingTime=taskStore.skipWeekendsDuringDragDrop] Pass `true` to automatically move the start date to the earliest available working time (if it falls on non-working time).
     * @param {Function} [callback] Callback function to call after start date has been set and changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    setStartDate : function (date, keepDuration, skipNonWorkingTime, callback) {
        var me  = this;

        me.propagateChanges(
            function () {
                return me.setStartDateWithoutPropagation(date, keepDuration, skipNonWorkingTime);
            },
            callback
        );
    },


    // TODO: refactor this
    setStartDateWithoutPropagation : function (date, keepDuration, skipNonWorkingTime) {
        var me = this,
            taskStore = me.getTaskStore(true),
            duration, endDate;

        // {{{ Parameters normalization
        keepDuration = keepDuration !== false;

        if (taskStore && skipNonWorkingTime !== true && skipNonWorkingTime !== false) {
            skipNonWorkingTime = taskStore.skipWeekendsDuringDragDrop;
        }
        else if (skipNonWorkingTime !== true && skipNonWorkingTime !== false) {
            skipNonWorkingTime = false;
        }
        // }}}

        me.beginEdit();

        if (!date) {
            me.set(me.durationField, null);
            me.set(me.startDateField, null);
            me.setSegmentsWithoutPropagation(null);

        } else {
            if (skipNonWorkingTime) {
                // for milestones we skip non-working backwards, for normal tasks - forward
                date = me.skipNonWorkingTime(date, !me.isMilestone());
            }

            me.set(me.startDateField, date);

            // recalculate split dates
            if (taskStore && me.isSegmented()) {
                me.updateSegmentsDates();
            }

            if (keepDuration !== false) {
                me.set(me.endDateField, me.recalculateEndDate(date));
            } else {
                endDate  = this.getEndDate();

                if (endDate) {
                    // truncate segments that don't fit into master task range and shrink/expand last segment
                    this.constrainSegments();

                    me.set(me.durationField, me.calculateDuration(date, endDate, me.getDurationUnit()));
                }
            }
        }
        // eof "has `date`" branch

        duration            = me.getDuration();
        endDate             = me.getEndDate();

        if (date && endDate && (duration === undefined || duration === null)) {
            me.set(me.durationField, me.calculateDuration(date, endDate, me.getDurationUnit()));
        }

        me.onPotentialEffortChange();

        me.endEdit();

        return true;
    },


    /**
     * @method getEndDate
     *
     * Returns the end date of this task
     *
     * @return {Date} end date
     */


    /**
     * Depending from the arguments, set either `StartDate + EndDate` fields of this task, or `EndDate + Duration`
     * considering the weekends/holidays rules. The modifications are wrapped with `beginEdit/endEdit` calls.
     *
     * @param {Date} date End date to set
     * @param {Boolean} [keepDuration=true] Pass `true` to keep the duration of the task ("move" the task), `false` to change the duration ("resize" the task).
     * @param {Boolean} [skipNonWorkingTime=taskStore.skipWeekendsDuringDragDrop] Pass `true` to automatically move the end date to the previous working day (if it falls on weekend/holiday).
     * @param {Function} [callback] Callback function to call after end date has been set and changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    setEndDate : function (date, keepDuration, skipNonWorkingTime, callback) {
        var me  = this;

        me.propagateChanges(
            function() {
                return me.setEndDateWithoutPropagation(date, keepDuration, skipNonWorkingTime);
            },
            callback
        );
    },

    // TODO: refactor this
    setEndDateWithoutPropagation : function (date, keepDuration, skipNonWorkingTime) {
        var me = this,
            taskStore = me.getTaskStore(true),
            duration, startDate;

        // {{{ Parameters normalization
        keepDuration = keepDuration !== false;

        if (skipNonWorkingTime !== true && skipNonWorkingTime !== false && taskStore) {
            skipNonWorkingTime = taskStore.skipWeekendsDuringDragDrop;
        }
        else if (skipNonWorkingTime !== true && skipNonWorkingTime !== false) {
            skipNonWorkingTime = false;
        }
        // }}}

        me.beginEdit();

        var currentEndDate    = me.getEndDate();

        if (!date) {
            me.set(me.durationField, null);
            me.set(me.endDateField, null);
            me.setSegments(null);
        } else {
            startDate       = me.getStartDate();
            // task end date cannot be less than its start date
            if (date < startDate && keepDuration === false) {
                date        = startDate;
            }

            if (skipNonWorkingTime) {
                date        = me.skipNonWorkingTime(date, false);
            }

            if (keepDuration !== false) {
                duration    = me.getDuration();

                if (Ext.isNumber(duration)) {

                    // recalculate segments dates (we need this to calculate the task start date properly)
                    if (taskStore && me.isSegmented() && (date - currentEndDate)) {
                        me.updateSegmentsDates({
                            isForward   : false,
                            endDate     : date
                        });
                    }

                    me.set(me.startDateField, me.calculateStartDate(date, duration, me.getDurationUnit()));
                    me.set(me.endDateField, date);
                } else {
                    me.set(me.endDateField, date);
                }
            } else {
                var wasMilestone    = me.isMilestone();

                // if end date after adjusting to calendar is less than start date
                // then it's going to be a milestone and we set start date equal to adjusted end date
                if (date < startDate) {
                    me.set(me.startDateField, date);
                }

                me.set(me.endDateField, date);

                me.constrainSegments();

                if (startDate) {
                    me.set(me.durationField, me.calculateDuration(startDate, date, me.getDurationUnit()));

                    // if we converted to regular task from milestone
                    // let's make sure that task start is adjusted to the calendar
                    if (wasMilestone && !me.isMilestone()) {
                        var properStartDate = me.skipNonWorkingTime(startDate, true);
                        if (properStartDate - startDate !== 0) {
                            // set start date adjusted to the calendar
                            me.set(me.startDateField, properStartDate);
                        }
                    }
                }
            }
        }

        duration            = me.getDuration();
        startDate           = me.getStartDate();

        if (date && startDate && (duration === undefined || duration === null)) {
            me.set(me.durationField, me.calculateDuration(startDate, date, me.getDurationUnit()));
        }

        me.onPotentialEffortChange();

        me.endEdit();

        return true;
    },

    /**
     * Sets the `StartDate / EndDate / Duration` fields of this task, considering the availability/holidays information.
     * The modifications are wrapped with `beginEdit/endEdit` calls.
     *
     * @param {Date} startDate Start date to set
     * @param {Date} endDate End date to set
     * @param {Boolean} [skipNonWorkingTime=taskStore.skipWeekendsDuringDragDrop] Pass `true` to automatically move the start/end dates to the next/previous working day (if they falls on weekend/holiday).
     * @param {Function} [callback] Callback function to call after start/end date has been set and changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    setStartEndDate : function (startDate, endDate, skipNonWorkingTime, callback) {
        var me  = this;

        // This is required to have Data components tests green
        skipNonWorkingTime = skipNonWorkingTime || false;

        me.propagateChanges(
            function() {
                return me.setStartEndDateWithoutPropagation(startDate, endDate, skipNonWorkingTime);
            },
            callback
        );
    },

    setStartEndDateWithoutPropagation : function(startDate, endDate, skipNonWorkingTime) {
        var me = this,
            taskStore = me.getTaskStore(true);

        // {{{ Parameters normalization
        if (skipNonWorkingTime !== true && skipNonWorkingTime !== false && taskStore) {
            skipNonWorkingTime = taskStore.skipWeekendsDuringDragDrop;
        }
        else if (skipNonWorkingTime !== true && skipNonWorkingTime !== false) {
            skipNonWorkingTime = false;
        }
        // }}}

        if (skipNonWorkingTime) {
            startDate = startDate && me.skipNonWorkingTime(startDate, true);
            endDate   = endDate && me.skipNonWorkingTime(endDate, false);

            if (endDate < startDate) {
                startDate = endDate;
            }
        }

        var currentStartDate    = me.getStartDate(),
            currentEndDate      = me.getEndDate();

        me.beginEdit();

        me.set(me.startDateField, startDate);
        me.set(me.endDateField,   endDate);

        // recalculate split dates
        if (me.getTaskStore(true) && me.isSegmented() && ((startDate - currentStartDate) || (endDate - currentEndDate))) {
            me.updateSegmentsDates();
        }

        if (endDate - currentEndDate) {
            me.constrainSegments();
        }

        me.set(me.durationField, me.calculateDuration(startDate, endDate, me.getDurationUnit()));

        me.onPotentialEffortChange();

        me.endEdit();

        return true;
    },


    /**
     * Shift the dates for the date range by the passed amount and unit
     * @param {String} unit The unit to shift by (e.g. range.shift(Sch.util.Date.DAY, 2); ) to bump the range 2 days forward
     * @param {Number} amount The amount to shift
     * @param {Function} [callback] Callback function to call after task has been shifted and changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    shift : function(unit, amount, callback) {
        var me        = this,
            startDate = me.getStartDate(),
            endDate   = me.getEndDate();

        me.setStartEndDate(
            startDate && Sch.util.Date.add(startDate, unit, amount),
            endDate && Sch.util.Date.add(endDate, unit, amount),
            undefined,
            callback
        );
    },


    /**
     * Returns the duration of the task expressed in the unit passed as the only parameter (or as specified by the DurationUnit for the task).
     *
     * @param {String} unit (optional) Unit to return the duration in. Defaults to the `DurationUnit` field of this task
     *
     * @return {Number} duration
     */
    getDuration: function (unit) {
        if (!unit) return this.get(this.durationField);

        var converter       = this.getUnitConverter(),
            durationInMS    = converter.convertDurationToMs(this.get(this.durationField), this.get(this.durationUnitField));

        return converter.convertMSDurationToUnit(durationInMS, unit);
    },


    /**
     * Returns the effort of the task expressed in the unit passed as the only parameter (or as specified by the EffortUnit for the task).
     *
     * @param {String} unit Unit to return the effort in. Defaults to the `EffortUnit` field of this task
     *
     * @return {Number} effort
     */
    getEffort: function (unit) {
        var fieldValue      = this.get(this.effortField) || 0;

        if (!unit) return fieldValue;

        var converter       = this.getUnitConverter(),
            durationInMS    = converter.convertDurationToMs(fieldValue, this.getEffortUnit());

        return converter.convertMSDurationToUnit(durationInMS, unit);
    },


    /**
     * Sets the `Effort + EffortUnit` fields of this task. In case the task has the `EffortDriven`
     * scheduling mode will also update the duration of the task accordingly.
     * In case of `DynamicAssignment` mode - will update the assignments.
     *
     * The modifications are wrapped with `beginEdit/endEdit` calls.
     *
     * @param {Number} number The number of duration units
     * @param {String} [unit=task.getEffortUnit()] The unit of the effort.
     * @param {Function} [callback] Callback function to call after effort has been set and possible changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    setEffort: function (number, unit, callback) {
        var me  = this;

        me.propagateChanges(
            function() {
                return me.setEffortWithoutPropagation(number, unit);
            },
            callback
        );
    },


    setEffortWithoutPropagation : function(number, unit) {
        var me = this;

        // {{{ Parameters normalization
        unit = unit || me.getEffortUnit();
        // }}}

        me.beginEdit();

        me.set(me.effortField, number);
        me.set(me.effortUnitField, unit);

        switch (me.getSchedulingMode()) {
            case 'EffortDriven'         : me.updateSpanBasedOnEffort(); break;
            case 'DynamicAssignment'    : me.updateAssignments();       break;
        }

        me.endEdit();

        return true;
    },


    /**
     * Returns the "raw" calendar duration (difference between end and start date) of this task in the given units.
     *
     * Please refer to the "Task durations" section for additional important details about duration units.
     *
     * @param {String} unit Unit to return return the duration in. Defaults to the `DurationUnit` field of this task
     *
     * @return {Number} duration
     */
    getCalendarDuration: function (unit) {
        return this.getUnitConverter().convertMSDurationToUnit(this.getEndDate() - this.getStartDate(), unit || this.get(this.durationUnitField));
    },


    /**
     * Sets the `Duration + DurationUnit + EndDate` fields of this task, considering the weekends/holidays rules.
     * The modifications are wrapped with `beginEdit/endEdit` calls.
     *
     * May also update additional fields, depending from the scheduling mode.
     *
     * @param {Number} number The number of duration units
     * @param {String} [unit=task.getDurationUnit()] The unit of the duration.
     * @param {Function} [callback] Callback function to call after duration has been set and possible changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    setDuration : function(number, unit, callback) {
        var me  = this;

        me.propagateChanges(
            function() {
                return me.setDurationWithoutPropagation(number, unit);
            },
            callback
        );
    },


    setDurationWithoutPropagation: function(number, unit) {
        var me = this;
        // If a task has end predecessors, and no start predecessors we update the start date instead of the end date
        var updateStartDate = !me.isManuallyScheduled() && me.hasEndPredecessorsButNoStartPredecessors();
        var newEndDate      = null;
        var newStartDate    = null;
        var endDate         = me.getEndDate();

        // {{{ Parameters normalization
        unit = unit || me.getDurationUnit();
        // }}}

        var wasMilestone = me.isMilestone();

        me.beginEdit();

        // Provide project start date (if any) or now as start date if it isn't already set
        if (Ext.isNumber(number) && !me.getStartDate()) {
            if (endDate) {
                // If task has end date but no start date and we're setting the duration, modify start date (handled below)
                updateStartDate = true;
            } else {
                var taskStore       = me.getTaskStore(true);
                newStartDate        = (taskStore && taskStore.getProjectStartDate()) || Ext.Date.clearTime(new Date());
                me.setStartDateWithoutPropagation(newStartDate);
            }
        }

        this.constrainSegments({ duration : number, unit : unit });

        if (Ext.isNumber(number)) {
            if (updateStartDate && me.getEndDate()) {
                newStartDate = me.calculateStartDate(me.getEndDate(), number, unit);
            } else {
                newEndDate = me.calculateEndDate(me.getStartDate(), number, unit);
            }
        }

        // Don't clear the end date if task isn't properly scheduled
        if (!updateStartDate && (newEndDate || this.getStartDate())) {
            me.set(me.endDateField, newEndDate);
        }

        if (updateStartDate && (newStartDate || this.getEndDate())) {
            me.set(me.startDateField, newStartDate);
        }

        me.set(me.durationField, number);
        me.set(me.durationUnitField, unit);

        // if task is switched to/from milestone then we also need
        // to check if start/end dates are adjusted to the calendar
        if (me.isMilestone() != wasMilestone) {
            // if it's not a milestone now
            if (wasMilestone) {
                // check if start date is adjusted to calendar
                var startDate       = me.getStartDate();
                if (startDate) {
                    var properStartDate = me.skipNonWorkingTime(startDate, true);
                    if (properStartDate - startDate !== 0) {
                        // set start date adjusted to the calendar
                        me.set(me.startDateField, properStartDate);
                    }
                }
            // if it's a milestone
            } else if (newEndDate) {
                // skip non-working time backward
                var properEndDate   = me.skipNonWorkingTime(newEndDate, false);
                if (properEndDate - newEndDate !== 0) {
                    // set start/end dates adjusted to the calendar
                    me.set(me.startDateField, properEndDate);
                    me.set(me.endDateField, properEndDate);
                }
            }
        }

        me.onPotentialEffortChange();

        me.endEdit();

        return true;
    },


    calculateStartDate : function (endDate, duration, unit, options) {
        if (!duration) return endDate;

        unit = unit || this.getDurationUnit();

        var schedulingMode                            = this.getSchedulingMode(),
            isStartDateCalculationRequiresAssignments = schedulingMode != 'FixedDuration' && schedulingMode != 'DynamicAssignment' && schedulingMode != 'EffortDriven';

        options         = Ext.apply({
            endDate     : endDate,
            isForward   : false,
            // if there are any assignments, need to take them into account when calculating the duration
            // but only for "normal" scheduling mode
            // for "EffortDriven" one should use "calculateEffortDrivenEndDate"
            // for "FixedDuration/DynamicAssignment" assignments should not affect the end date of the task
            resources   : isStartDateCalculationRequiresAssignments && this.hasResources()
        }, options);

        if (this.getTaskStore(true) && (this.isSegmented() || isStartDateCalculationRequiresAssignments)) {

            var leftDuration    = this.getUnitConverter().convertDurationToMs(duration, unit);
            var startDate;

            this.forEachAvailabilityInterval(options, function (intervalStart, intervalEnd) {
                var intervalDuration    = intervalEnd - intervalStart;

                if (intervalDuration >= leftDuration) {

                    startDate           = new Date(intervalEnd - leftDuration);

                    return false;

                } else {
                    var dstDiff     = new Date(intervalEnd).getTimezoneOffset() - new Date(intervalStart).getTimezoneOffset();
                    leftDuration    -= intervalDuration + dstDiff * 60 * 1000;
                }
            });


            return startDate;

        } else {
            // otherwise just consult the calendar
            return this.getCalendar().calculateStartDate(endDate, duration, unit);
        }
    },


     //Recalculates a task end date based on a new startdate (use task start date if omitted)
    recalculateEndDate : function (startDate) {
        var me = this,
            result,
            duration;

        startDate = startDate || me.getStartDate();

        if (startDate && me.getSchedulingMode() == 'EffortDriven') {
            result = me.calculateEffortDrivenEndDate(startDate, me.getEffort());
        }
        else {
            duration = me.getDuration();

            if (startDate && Ext.isNumber(duration)) {
                result = me.calculateEndDate(startDate, duration, me.getDurationUnit());
            }
            else {
                result = me.getEndDate();
            }
        }

        return result;
    },


    calculateEndDate : function (startDate, duration, unit, options) {
        unit = unit || this.getDurationUnit();

        if (!duration) return startDate;

        options = Ext.apply({ startDate : startDate }, options);

        var schedulingMode  = this.getSchedulingMode(),
            endDate;

        // if there are any assignments, need to take them into account when calculating the duration
        // but only for "normal" scheduling mode
        // for "FixedDuration/DynamicAssignment" assignments should not affect the end date of the task
        // and for "EffortDriven" mode one should use "calculateEffortDrivenEndDate"
        var isEndDateCalculationRequiresAssignments = schedulingMode != 'FixedDuration' && schedulingMode != 'DynamicAssignment' && schedulingMode != 'EffortDriven';

        if (this.getTaskStore(true) && (this.isSegmented() || isEndDateCalculationRequiresAssignments)) {
            var leftDuration    = this.getUnitConverter().convertDurationToMs(duration, unit);

            options.resources   = isEndDateCalculationRequiresAssignments && this.hasResources();

            this.forEachAvailabilityInterval(options, function (from, till) {
                var intervalDuration    = till - from;

                if (intervalDuration >= leftDuration) {

                    endDate             = new Date(from + leftDuration);

                    return false;

                } else {
                    var dstDiff     = new Date(from).getTimezoneOffset() - new Date(till).getTimezoneOffset();
                    leftDuration    -= intervalDuration + dstDiff * 60 * 1000;
                }
            });

        } else {
            // otherwise just consult the calendar
            return this.getCalendar().calculateEndDate(startDate, duration, unit);
        }

        return endDate;
    },


    calculateDuration : function (startDate, endDate, unit, options) {
        unit = unit || this.getDurationUnit();

        if (!startDate || !endDate) {
            return 0;
        }

        // if there are any assignments, need to take them into account when calculating the duration
        if (this.getTaskStore(true)) {
            var durationInMS    = 0;

            this.forEachAvailabilityInterval(
                Ext.apply({
                    startDate   : startDate,
                    endDate     : endDate,
                    resources   : this.hasResources()
                }, options),
                function (from, till) {
                    var dstDiff     = new Date(from).getTimezoneOffset() - new Date(till).getTimezoneOffset();
                    durationInMS    += till - from + dstDiff * 60 * 1000;
                }
            );

            return this.getUnitConverter().convertMSDurationToUnit(durationInMS, unit);
        } else {
            // otherwise just consult the calendar
            return this.getCalendar().calculateDuration(startDate, endDate, unit);
        }
    },


    isCalendarApplicable : function (calendarId) {
        var startDate   = this.getStartDate();

        if (!startDate) return true;

        var taskStore   = this.getTaskStore(true);
        if (!taskStore) return true;

        var endDate     = Sch.util.Date.add(startDate, 'd', (taskStore && taskStore.availabilitySearchLimit) || 5*365);

        var assignments         = this.getAssignments();
        var resourcesCalendars  = [];

        Ext.Array.each(assignments, function (assignment) {
            var resource    = assignment.getResource();

            if (resource) {
                resourcesCalendars.push(resource.getCalendar());
            }
        });

        if (!resourcesCalendars.length) return true;

        var calendar = Gnt.data.Calendar.getCalendar(calendarId);

        for (var i = 0, l = resourcesCalendars.length; i < l; i++) {
            if (calendar.isAvailabilityIntersected(resourcesCalendars[i], startDate, endDate)) return true;
        }

        return false;
    },


    forEachAvailabilityInterval : function (options, func, scope) {
        func                        = func || options.fn;
        scope                       = scope || options.scope || this;

        var me                      = this,
            startDate               = options.startDate,
            endDate                 = options.endDate,
            includeEmptyIntervals   = options.includeEmptyIntervals,
            needResources           = options.resources,
            useSegments             = options.segments || (options.segments !== false),
            // isForward enabled by default
            isForward               = options.isForward !== false,
            DATE                    = Sch.util.Date,
            cursorDate, segments;

        // need taskStore to get default `availabilitySearchLimit` value
        var store                   = this.getTaskStore(true);

        var i, k, l, interval, intervalStart, intervalEnd;

        if (isForward) {
            if (!startDate) throw new Error("forEachAvailabilityInterval: `startDate` is required when `isForward` is true");

            // if no boundary we still have to specify some limit
            if (!endDate) endDate = DATE.add(startDate, 'd', options.availabilitySearchLimit || (store && store.availabilitySearchLimit) || 5*365);

            cursorDate  = new Date(startDate);
        } else {
            if (!endDate) throw new Error("forEachAvailabilityInterval: `endDate` is required when `isForward` is false");

            // if no boundary we still have to specify some limit
            if (!startDate) startDate = DATE.add(endDate, 'd', - (options.availabilitySearchLimit || (store && store.availabilitySearchLimit) || 5*365));

            cursorDate  = new Date(endDate);
        }

        var taskCalendar                = this.getOwnCalendar(),
            projectCalendar             = this.getProjectCalendar(),

            resourceByCalendar          = {},
            calendars                   = [];

        // if we take resources into account
        if (needResources) {

            var resourceFound   = false;
            // we can provide list of assignments as well
            var assignments     = options.assignments;

            // helper function to prepare resources data
            var handleResource  = function (resource) {
                var resourceId  = resource.getId(),
                    assignment  = assignments && Ext.Array.findBy(assignments, function (a) {
                        return a.getResourceId() == resourceId;
                    }) || me.getAssignmentFor(resource),
                    calendar    = resource.getCalendar(),
                    id          = calendar.getCalendarId();

                if (!resourceByCalendar[id]) {
                    resourceByCalendar[id]  = [];

                    calendars.push(calendar);
                }

                resourceByCalendar[id].push({
                    assignment      : assignment,
                    resourceId      : resourceId,
                    units           : assignment && assignment.getUnits()
                });

                resourceFound   = true;
            };

            // user has provided the resources to use for iteration
            if (needResources !== true) {

                Ext.each(needResources, handleResource);

            // otherwise retrieve all assigned resources
            } else {
                Ext.Array.each(this.getAssignments(), function (assignment) {
                    var resource    = assignment.getResource();

                    if (resource) handleResource(resource);
                });
            }

            // if there are no resources - then iterator should not be called by contract, just return
            if (!resourceFound) return;

        // if we don't use resource calendars for calculation then we gonna use the task/project calendar
        } else {
            taskCalendar    = taskCalendar || projectCalendar;
        }

        var splits, splitStart, splitEnd;

        if (useSegments) {
            if (!Ext.isArray(useSegments)) {
                segments  = this.getSegments();
            } else {
                segments  = useSegments;
            }

            if (segments) {

                splits  = [];

                // Let's walk over the segment and "invert" them.
                // We need to operate non working intervals (splits) instead of StartDate-EndDate pairs.
                for (i = 0, l = segments.length; i < l - 1; i++) {
                    // split start is the current segment EndDate and split end is the next segment StartDate
                    splitStart = segments[i].getEndDate() - 0;
                    splitEnd   = segments[i + 1].getStartDate() - 0;

                    // we use the split only if it intersects w/ the requested timespan
                    if (splitStart > startDate || splitEnd < endDate) {
                        splits.push([ segments[i].getEndDate() - 0, segments[i + 1].getStartDate() - 0 ]);
                    }
                }

                if (!splits.length) {
                    splits = null;
                }
            }
        }


        while (isForward ? cursorDate < endDate : cursorDate > startDate) {
            var pointsByTime        = {};
            var pointTimes          = [];
            var cursorDT            = cursorDate - (isForward ? 0 : 1);

            // if a task has a custom calendar
            if (taskCalendar) {
                var taskIntervals       = taskCalendar.getAvailabilityIntervalsFor(cursorDT);

                // the order of intervals processing doesn't matter here, since we are just collecting the "points of interest"
                for (k = 0, l = taskIntervals.length; k < l; k++) {
                    interval            = taskIntervals[ k ];
                    intervalStart       = interval.startDate - 0;
                    intervalEnd         = interval.endDate - 0;

                    if (!pointsByTime[ intervalStart ]) {
                        pointsByTime[ intervalStart ] = [];

                        pointTimes.push(intervalStart);
                    }
                    pointsByTime[ intervalStart ].push({
                        type            : '00-taskAvailailabilityStart',
                        typeBackward    : '01-taskAvailailabilityStart'
                    });

                    pointTimes.push(intervalEnd);

                    pointsByTime[ intervalEnd ] = pointsByTime[ intervalEnd ] || [];
                    pointsByTime[ intervalEnd ].push({
                        type            : '01-taskAvailailabilityEnd',
                        typeBackward    : '00-taskAvailailabilityEnd'
                    });
                }
            }

            // If we take the task segmentation into account
            if (splits) {
                var from, till;

                if (isForward) {
                    from    = cursorDate;
                    till    = DATE.getStartOfNextDay(cursorDate);
                } else {
                    from    = DATE.getEndOfPreviousDay(cursorDate);
                    till    = cursorDate;
                }

                for (i = 0, l = splits.length; i < l; i++) {
                    splitStart = splits[i][0];
                    splitEnd   = splits[i][1];

                    if (splitStart < till && splitEnd > from) {

                        splitStart = DATE.constrain(splitStart, from, till) - 0;
                        splitEnd = DATE.constrain(splitEnd, from, till) - 0;

                        if (!pointsByTime[ splitStart ]) {
                            pointsByTime[ splitStart ] = [];

                            pointTimes.push(splitStart);
                        }

                        pointsByTime[ splitStart ].push({
                            type         : '05-taskSegmentEnd',
                            typeBackward : '04-taskSegmentEnd'
                        });

                        if (!pointsByTime[ splitEnd ]) {
                            pointsByTime[ splitEnd ] = [];

                            pointTimes.push(splitEnd);
                        }

                        pointsByTime[ splitEnd ].push({
                            type         : '04-taskSegmentStart',
                            typeBackward : '05-taskSegmentStart'
                        });

                        // Since segments cannot overlap by contract we can break the loop here
                        break;
                    }
                }
            }

            var resourceList;

            // loop over resources having custom calendars
            for (i = 0, l = calendars.length; i < l; i++) {
                var cal                 = calendars[ i ],
                    resourceIntervals   = cal.getAvailabilityIntervalsFor(cursorDT);

                resourceList        = resourceByCalendar[ cal.getCalendarId() ];

                // using "for" instead of "each" should be blazing fast! :)
                // the order of intervals processing doesn't matter here, since we are just collecting the "points of interest"
                for (k = 0; k < resourceIntervals.length; k++) {
                    interval            = resourceIntervals[ k ];
                    intervalStart   = interval.startDate - 0;
                    intervalEnd     = interval.endDate - 0;

                    if (!pointsByTime[ intervalStart ]) {
                        pointsByTime[ intervalStart ] = [];

                        pointTimes.push(intervalStart);
                    }
                    pointsByTime[ intervalStart ].push({
                        type            : '02-resourceAvailailabilityStart',
                        typeBackward    : '03-resourceAvailailabilityStart',
                        resources       : resourceList
                    });

                    if (!pointsByTime[ intervalEnd ]) {
                        pointsByTime[ intervalEnd ] = [];

                        pointTimes.push(intervalEnd);
                    }
                    pointsByTime[ intervalEnd ].push({
                        type            : '03-resourceAvailailabilityEnd',
                        typeBackward    : '02-resourceAvailailabilityEnd',
                        resources       : resourceList
                    });
                }
            }

            pointTimes.sort(function(a, b) { return a - b; });

            var inTaskCalendar      = false,
                inSegment           = true,
                currentResources    = {},
                resourceCounter     = 0,
                points, point, m, n;

            if (isForward) {
                for (i = 0, l = pointTimes.length; i < l; i++) {
                    points          = pointsByTime[ pointTimes[ i ] ];

                    points.sort(function (a, b) { return a.type < b.type ? 1 : -1; });

                    for (k = 0; k < points.length; k++) {
                        point           = points[ k ];

                        switch (point.type) {
                            case '00-taskAvailailabilityStart' : inTaskCalendar  = true; break;

                            case '01-taskAvailailabilityEnd' : inTaskCalendar  = false; break;

                            case '02-resourceAvailailabilityStart' :
                                resourceList    = point.resources;
                                for (m = 0, n = resourceList.length; m < n; m++) {
                                    currentResources[resourceList[m].resourceId]    = resourceList[m];
                                    resourceCounter++;
                                }
                                break;

                            case '03-resourceAvailailabilityEnd' :
                                resourceList    = point.resources;
                                for (m = 0, n = resourceList.length; m < n; m++) {
                                    delete currentResources[resourceList[m].resourceId];
                                    resourceCounter--;
                                }
                                break;

                            case '04-taskSegmentStart' : inSegment = true; break;

                            case '05-taskSegmentEnd' : inSegment = false; break;
                        }
                    }

                    if ((inTaskCalendar || !taskCalendar) && (!splits || inSegment) && (!needResources || resourceCounter || includeEmptyIntervals)) {
                        intervalStart       = pointTimes[ i ];
                        intervalEnd         = pointTimes[ i + 1 ];

                        // availability interval is out of [ startDate, endDate )
                        if (intervalStart >= endDate || intervalEnd <= startDate) continue;

                        if (intervalStart < startDate) intervalStart = startDate - 0;
                        if (intervalEnd > endDate) intervalEnd = endDate - 0;

                        if (func.call(scope, intervalStart, intervalEnd, currentResources) === false) return false;
                    }
                }
            } else {
                for (i = pointTimes.length - 1; i >= 0; i--) {
                    points          = pointsByTime[ pointTimes[ i ] ];

                    points.sort(function (a, b) { return a.typeBackward < b.typeBackward ? 1 : -1; });

                    for (k = 0; k < points.length; k++) {
                        point           = points[ k ];

                        switch (point.typeBackward) {
                            case '00-taskAvailailabilityEnd' : inTaskCalendar  = true; break;

                            case '01-taskAvailailabilityStart' : inTaskCalendar  = false; break;

                            case '02-resourceAvailailabilityEnd' :
                                resourceList    = point.resources;
                                for (m = 0, n = resourceList.length; m < n; m++) {
                                    currentResources[resourceList[m].resourceId]    = resourceList[m];
                                    resourceCounter++;
                                }
                                break;

                            case '03-resourceAvailailabilityStart' :
                                resourceList    = point.resources;
                                for (m = 0, n = resourceList.length; m < n; m++) {
                                    delete currentResources[resourceList[m].resourceId];
                                    resourceCounter--;
                                }
                                break;

                            case '04-taskSegmentEnd' : inSegment = true; break;

                            case '05-taskSegmentStart' : inSegment = false; break;
                        }
                    }

                    if ((inTaskCalendar || !taskCalendar) && (!splits || inSegment) && (!needResources || resourceCounter || includeEmptyIntervals)) {
                        intervalStart       = pointTimes[ i - 1 ];
                        intervalEnd         = pointTimes[ i ];

                        // availability interval is out of [ startDate, endDate )
                        if (intervalStart > endDate || intervalEnd <= startDate) continue;

                        if (intervalStart < startDate) intervalStart = startDate - 0;
                        if (intervalEnd > endDate) intervalEnd = endDate - 0;

                        if (func.call(scope, intervalStart, intervalEnd, currentResources) === false) return false;
                    }
                }
            }
            // eof backward branch

            // does not perform cloning internally!
            cursorDate       = isForward ? DATE.getStartOfNextDay(cursorDate) : DATE.getEndOfPreviousDay(cursorDate);
        }
        // eof while
    },

    // iterates over the common availability intervals for tasks and resources in between `startDate/endDate`
    // note, that function will receive start/end dates as number, not dates (for optimization purposes)
    // this method is not "normalized" intentionally because of performance considerations
    forEachAvailabilityIntervalWithResources : function (options, func, scope) {
        if (!options.resources) options.resources = true;

        this.forEachAvailabilityInterval.apply(this, arguments);
    },


    calculateEffortDrivenEndDate : function (startDate, effort, unit) {
        if (!effort) return startDate;

        var effortInMS      = this.getUnitConverter().convertDurationToMs(effort, unit || this.getEffortUnit());

        var endDate         = new Date(startDate);

        this.forEachAvailabilityIntervalWithResources({ startDate : startDate }, function (intervalStartDate, intervalEndDate, currentResources) {
            var totalUnits          = 0;

            for (var i in currentResources) totalUnits += currentResources[ i ].units;

            var intervalDuration    = intervalEndDate - intervalStartDate;
            var availableEffort     = totalUnits * intervalDuration / 100;

            if (availableEffort >= effortInMS) {

                endDate             = new Date(intervalStartDate + effortInMS / availableEffort * intervalDuration);

                return false;

            } else {
                effortInMS          -= availableEffort;
            }
        });

        return endDate;
    },


    // this method has a contract that all child parents should already have refreshed data, so it should be called
    // in the "bubbling" order - starting from deeper nodes to closer to root
    refreshCalculatedParentNodeData : function () {

        var autoCalculatePercentDoneForParentTask   = this.autoCalculatePercentDoneForParentTask;
        var autoCalculateEffortForParentTask        = this.autoCalculateEffortForParentTask;

        var childNodes                  = this.childNodes;
        var length                      = childNodes.length;
        var changedFields               = {};

        if (length > 0 && (autoCalculateEffortForParentTask || autoCalculatePercentDoneForParentTask)) {
            var totalEffortInMS         = 0;
            var totalDurationInMS       = 0;
            var completedDurationInMS   = 0;

            for (var k = 0; k < length; k++) {
                var childNode           = childNodes[ k ];

                // We could end up here as a result of taskStore#removeAll which means some of the child nodes could already
                // be removed
                if (childNode.parentNode) {
                    var isLeaf              = childNode.isLeaf();

                    if (autoCalculateEffortForParentTask) totalEffortInMS += childNode.getEffort('MILLI');

                    if (autoCalculatePercentDoneForParentTask) {
                        var durationInMS        = isLeaf ? childNode.getDuration('MILLI') || 0 : childNode.childTasksDuration;

                        totalDurationInMS       += durationInMS;
                        completedDurationInMS   += isLeaf ? durationInMS * (childNode.getPercentDone() || 0) : childNode.completedChildTasksDuration;
                    }
                }
            }

            if (autoCalculateEffortForParentTask && this.getEffort('MILLI') != totalEffortInMS) {
                changedFields.Effort        = true;
                this.setEffortWithoutPropagation(this.getUnitConverter().convertMSDurationToUnit(totalEffortInMS, this.getEffortUnit()));
            }

            if (autoCalculatePercentDoneForParentTask) {
                this.childTasksDuration             = totalDurationInMS;
                this.completedChildTasksDuration    = completedDurationInMS;

                var newPercentDone          = totalDurationInMS ? completedDurationInMS / totalDurationInMS : 0;

                if (this.getPercentDone() != newPercentDone) {
                    changedFields.PercentDone       = true;
                    this.setPercentDone(newPercentDone);
                }
            }
        }


        var startChanged, endChanged;

        if (!this.isRoot() && length > 0 && !this.isManuallyScheduled() && !this.isReadOnly()) {

            var minDate  = new Date(-8640000000000000),
                maxDate  = new Date(+8640000000000000),
                earliest = new Date(+8640000000000000), //new Date(maxDate)
                latest   = new Date(-8640000000000000); //new Date(minDate) - this works incorrect in FF

            for (var i = 0; i < length; i++) {
                var r       = childNodes[i];

                earliest    = Sch.util.Date.min(earliest, r.getStartDate() || earliest);
                latest      = Sch.util.Date.max(latest, r.getEndDate() || latest);
            }

            // This could happen if a parent task has two children, one having just start date and another having just an end date
            if (latest < earliest && earliest < maxDate && latest > minDate) {
                var tmp;

                tmp         = latest;
                latest      = earliest;
                earliest    = tmp;
            }

            startChanged    = changedFields.StartDate = earliest - maxDate !== 0 && this.getStartDate() - earliest !== 0;
            endChanged      = changedFields.EndDate = latest - minDate !== 0 && this.getEndDate() - latest !== 0;

            // special case to only trigger 1 update event and avoid extra "recalculateParents" calls
            // wrapping with `beginEdit / endEdit` is not an option, because they do not nest (one "endEdit" will "finalize" all previous "beginEdit")
            if (startChanged && endChanged) {
                this.setStartEndDateWithoutPropagation(earliest, latest, false);
            } else if (startChanged) {
                this.setStartDateWithoutPropagation(earliest, false, false);
            } else if (endChanged) {
                this.setEndDateWithoutPropagation(latest, false, false);
            }
        }

        return changedFields;
    },


    // This function is mostly used for backward compatibility as it does not trigger the changes propagation
    recalculateParents: function () {
        var parent = this.parentNode;

        parent && (
            parent.refreshCalculatedParentNodeData(),
            !this.getTaskStore().cascading && parent.recalculateParents()
        );
        /*
        var parent = this.parentNode;

        if (parent) {
            var changedFields   = parent.refreshCalculatedParentNodeData();
            var startChanged    = changedFields.StartDate;
            var endChanged      = changedFields.EndDate;

            // if `startChanged` or `endChanged` is true, then propagation to parent task has alreday happened in the
            // `onTaskUpdated` method of the TaskStore (during setStart/EndDate call), otherwise need to propagate it manually
            //
            // In the case of cascading, the store listeners are temporarily disabled so we should bubble up if there's a change
            if ((this.getTaskStore().cascading && (startChanged || endChanged)) || (!startChanged && !endChanged)) {
                if (!parent.isRoot()) parent.recalculateParents();
            }
        }
        */
    },


    /**
     * Returns true if this task is a milestone (has the same start and end dates).
     *
     * @param {Boolean} isBaseline Whether to check for baseline dates instead of "normal" dates. If this argument is provided with
     * "true" value, this method returns the result from the {@link #isBaselineMilestone} method.
     *
     * @return {Boolean}
     */
    isMilestone : function (isBaseline) {

        if (isBaseline) return this.isBaselineMilestone();

        // a summary task may have zero duration when "recalculateParents" is on
        // and a child task has working time on the summary task non-working time
        // so we operate start and end date pair here
        if (!this.get('leaf')) {

            var startDate = this.getStartDate(),
                endDate   = this.getEndDate();

            if (startDate && endDate) {
                return endDate - startDate === 0;
            }

        }

        return this.getDuration() === 0;
    },

    /**
     * Converts this task to a milestone (start date will match the end date).
     *
     * @param {Function} [callback] Callback function to call after task has been converted and possible changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    convertToMilestone : function(callback) {
        var me = this;

        me.propagateChanges(
            function() {
                return me.convertToMilestoneWithoutPropagation();
            },
            callback
        );
    },


    convertToMilestoneWithoutPropagation: function() {
        var me = this,
            propagate = false;

        if (!me.isMilestone()) {
            propagate = me.setStartDateWithoutPropagation(me.getEndDate(), false);
            propagate = propagate && me.setDurationWithoutPropagation(0);
        }

        return propagate;
    },

    /**
     * Converts a milestone task to a regular task with a duration of 1 [durationUnit].
     *
     * @param {Function} [callback] Callback function to call after task has been converted and possible changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    convertToRegular : function(callback) {
        var me = this;

        me.propagateChanges(
            function() {
                return me.convertToRegularWithoutPropagation();
            },
            callback
        );
    },


    convertToRegularWithoutPropagation : function() {
        var me = this,
            propagate = false,
            unit,
            newStart;

        if (me.isMilestone()) {
            unit = me.get(me.durationUnitField);
            newStart = me.calculateStartDate(me.getStartDate(), 1, unit);

            propagate = me.setDurationWithoutPropagation(1, unit);
            // we set the `moveParentAsGroup` flag to false, because in this case we don't want/need to
            // change any of child tasks
            propagate = propagate && me.setStartDateWithoutPropagation(newStart, true, false, false);
        }

        return propagate;
    },

    /**
     * Returns true if this task is a "baseline" milestone (has the same start and end baseline dates) or false if it's not or the dates are wrong.
     *
     * @return {Boolean}
     */
    isBaselineMilestone: function() {
        var baseStart = this.getBaselineStartDate(),
            baseEnd   = this.getBaselineEndDate();

        if (baseStart && baseEnd){
            return baseEnd - baseStart === 0;
        }

        return false;
    },


    // Sets the task "leaf" attribute to `false` and resets `Segments` field
    // since a parent task cannot be split
    markAsParent : function() {
        var me = this;

        me.isSegmented() && me.setSegmentsWithoutPropagation(null); // Parent task should never be split
        me.set('leaf', false);
    },


    /**
     * Returns the duration unit of the task.
     * @return {String} the duration unit
     */
    getDurationUnit: function () {
        return this.get(this.durationUnitField) || 'd';
    },

    /**
     * @method setDurationUnit
     *
     * Updates the duration unit of the task.
     *
     * @param {String} unit New duration unit
     * @return {String} the duration unit
     */


    /**
     * Returns the effort unit of the task.
     * @return {String} the effort unit
     */
    getEffortUnit: function () {
        return this.get(this.effortUnitField) || 'h';
    },

    /**
     * @method setEffortUnit
     *
     * Updates the effort unit of the task.
     *
     * @param {String} unit New effort unit
     * @return {String} the effort unit
     */

    /**
     * @method setDeadlineDate
     *
     * Sets the deadline date for this task
     *
     * @param {Date} date
     */

    /**
     * @method getDeadlineDate
     *
     * Returns the task deadline date
     *
     * @return {Date} date
     */

    /**
     * @method setRollup
     *
     * Controls if this task should roll up to its parent
     *
     * @param {Boolean} rollup
     */

    /**
     * @method getRollup
     *
     * Gets the rollup value for this task
     *
     * @return {Boolean} value
     */

    /**
     * @method setPercentDone
     *
     * Sets the percent complete value of the task
     *
     * @param {Number} value The new value
     */

    /**
     * @method getPercentDone
     *
     * Gets the percent complete value of the task
     * @return {Number} The percent complete value of the task
     */

    /**
     * @method getCls
     *
     * Returns the name of field holding the CSS class for each rendered task element
     *
     * @return {String} cls The cls field
     */

    /**
     * @method getBaselineStartDate
     *
     * Returns the baseline start date of this task
     *
     * @return {Date} The baseline start date
     */

    /**
     * @method setBaselineStartDate
     *
     * Sets the baseline start date of this task
     *
     * @param {Date} date
     */

    /**
     * @method getBaselineEndDate
     *
     * Returns the baseline end date of this task
     *
     * @return {Date} The baseline end date
     */

    /**
     * @method setBaselineEndDate
     *
     * Sets the baseline end date of this task
     *
     * @param {Date} date
     */

    /**
     * @method setBaselinePercentDone
     *
     * Sets the baseline percent complete value
     *
     * @param {Number} value The new value
     */

    /**
     * Gets the baseline percent complete value
     * @return {Number} The percent done level of the task
     */
    getBaselinePercentDone : function() {
        return this.get(this.baselinePercentDoneField) || 0;
    },

    /**
     * Returns true if the Task can be persisted (e.g. task and resource are not 'phantoms')
     *
     * @return {Boolean} true if this model can be persisted to server.
     */
    isPersistable : function() {
        var parent = this.parentNode;
        return !parent || !parent.phantom || parent.isRoot();
    },

    /**
     * Returns an array of Gnt.model.Resource instances assigned to this Task.
     *
     * @return {Gnt.model.Resource[]} resources
     */
    getResources : function () {
        var me = this,
            assignmentStore = me.getAssignmentStore();

        return assignmentStore && assignmentStore.getResourcesForEvent(me) || [];
    },

    /**
     * Returns an array of Gnt.model.Assignment instances associated with this Task.
     *
     * @return {Gnt.model.Assignment[]} resources
     */
    getAssignments : function () {
        var me = this,
            assignmentStore = me.getAssignmentStore();

        return assignmentStore && assignmentStore.getAssignmentsForTask(me) || [];
    },

    /**
     * Returns true if this task has any assignments. **Note**, that this function returns `true` even if all assignment records are invalid
     * (ie pointing to non-existing resource in the resource store).
     *
     * @return {Boolean}
     */
    hasAssignments : function () {
        return this.getAssignments().length > 0;
    },

    /**
     * Returns true if this task has any assignments with valid resources. Returns `true` only if at least one assignment record is valid -
     * pointing to existed resource record in the resource store.
     *
     * @return {Boolean}
     */
    hasResources : function () {
        var assignments = this.getAssignments(),
            result = false,
            i, len;

        for (i = 0, len = assignments.length; !result && i < len; i++) {
            result = !!assignments[i].getResource();
        }

        return result;
    },

    /**
     * If given resource is assigned to this task, returns a Gnt.model.Assignment record.
     * Otherwise returns `null`
     *
     * @param {Gnt.model.Resource/Mixid} resourceOrId The instance of {@link Gnt.model.Resource} or resource id
     *
     * @return {Gnt.model.Assignment|null}
     */
    getAssignmentFor : function(resource) {
        var me = this,
            assignmentStore = me.getAssignmentStore();

        return assignmentStore && assignmentStore.getAssignmentForEventAndResource(me, resource) || null;
    },

    /**
     * @method isAssignedTo
     * Returns true if the task is assigned to a certain resource.
     *
     * @param {Sch.model.Resource} resource The resource to query for
     * @return {Boolean}
     */
    isAssignedTo : function(resource) {
        var me = this,
            assignmentStore = me.getAssignmentStore();

        return assignmentStore && assignmentStore.isTaskAssignedToResource(me, resource) || false;
    },

    /**
     * Assigns this task to the passed Resource or Resource Id.
     *
     * @param {Gnt.model.Resource/Mixed} resource The instance of a {@link Gnt.model.Resource resource} or its id.
     * @param {Number} units The integer value for the {@link Gnt.model.Assignment#unitsField Units field} of the assignment record.
     * @param {Function} [callback] Callback function to call after resource has been assigned and possible changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    assign : function(resource, units, callback) {
        var me = this,
            compatResult,
            cancelFn;

        me.propagateChanges(
            function() {
                return me.assignWithoutPropagation(resource, units, function cancelAndResultFeedback(fn, result) {
                    cancelFn = fn;
                    compatResult = result;
                });
            },
            function onPropagationComplete(cancelChanges, affectedTasks) {
                cancelChanges && cancelFn && cancelFn();
                callback && callback(cancelChanges, affectedTasks);
            }
        );

        return compatResult;
    },


    assignWithoutPropagation : function (resource, units, cancelAndResultFeedback) {
        var me              = this,
            cancelActions   = [],
            taskStore       = me.getTaskStore(),
            assignmentStore = taskStore.getAssignmentStore(),
            resourceStore   = taskStore.getResourceStore(),
            assignment,
            resourceId;

        // {{{ Parameter normalization
        units = units || 100;
        // }}}

        // Preconditions:
        // TODO: wrap it into debug tags when our build system will support debug builds
        !me.getAssignmentFor(resource) ||
            Ext.Error.raise("Resource can't be assigned twice to the same task");

        // If we have a resource model instance but it's not in the resource store then adding it,
        // the resource is proably a phantom record
        if (resource instanceof Gnt.model.Resource && resourceStore.indexOf(resource) == -1) {
            resourceId = resource.getId();
            resourceStore.add(resource);
            cancelActions.push(function() {
                resourceStore.remove(resource);
            });
        }
        // If we have a resource model already in the store then just getting it's id
        else if (resource instanceof Gnt.model.Resource) {
            resourceId = resource.getId();
        }
        // If we don't have a resource model then we must have a resource id, and if a resource with the given id
        // is present in the store then we can proceed
        else if (resourceStore.indexOfId(resource) >= 0) {
            resourceId = resource;
        }
        // Otherwise we have nothing to assign to the task, raising an error
        else {
            // <debug>
            Ext.Error.raise("Can't assign resource to a task, task's resource store doesn't contain resource id given");
            // </debug>
            // @TODO: #2773 - Rhyno parse error - Syntax error while building the app
            var foo = false;
        }

        assignment = assignmentStore.assignTaskToResource(me, resourceId, units);

        cancelActions.push(function() {
            assignmentStore.unassignTaskFromResource(me, resourceId);
        });

        cancelAndResultFeedback && cancelAndResultFeedback(function() {
            Ext.Array.each(cancelActions, function(action) {
                action();
            });
        }, assignment[0]);

        return true;
    },

    /**
     * Un-assign a resource from this task
     *
     * @param {Gnt.model.Resource/Number} resource An instance of the {@link Gnt.model.Resource} class or a resource id
     * @param {Function} [callback] Callback function to call after resource has been unassigned and possible changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    unassign : function () {
        return this.unAssign.apply(this, arguments);
    },


    unAssign : function (resource, callback) {
        var me = this,
            cancelFn;

        me.propagateChanges(
            function() {
                return me.unassignWithoutPropagation(resource, function cancelFeedback(fn) {
                    cancelFn = fn;
                });
            },
            function onPropagationComplete(cancelChanges, affectedTasks) {
                cancelChanges && cancelFn && cancelFn();
                callback && callback(cancelChanges, affectedTasks);
            }
        );
    },


    unassignWithoutPropagation : function (resource, cancelFeedback) {
        var me               = this,
            resourceId       = resource instanceof Gnt.model.Resource ? resource.getId() : resource,
            assignmentStore  = me.getAssignmentStore(),
            assignment       = me.getAssignmentFor(resourceId),
            indexOfAssignment;

        // <debug>
        assignment ||
            Ext.Error.raise("Can't unassign resource `" + resourceId + "` from task `" + me.getId() + "` resource is not assigned to the task!");
        // </debug>

        indexOfAssignment = assignmentStore.indexOf(assignment);
        assignmentStore.unassignTaskFromResource(me, resource);

        cancelFeedback && cancelFeedback(function() {
            assignmentStore.insert(indexOfAssignment, assignment);
        });

        return true;
    },

    /**
     * Reassigns a task from old resource to a new one.
     *
     * @param {Gnt.model.Resource/Mixed} oldResource A resource to unassign from
     * @param {Gnt.model.Resource/Mixed} newResource A resource to assign to
     * @param {Function} [callback] Callback function to call after resource has been reassigned and possible changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    reassign : function (oldResource, newResource, callback) {
        var me = this,
            unassignCancelFn,
            assignCancelFn;

        me.propagateChanges(
            function() {
                var units = me.getAssignmentFor(oldResource).getUnits();
                var propagate = false;

                propagate = me.unassignWithoutPropagation(oldResource, function unassignCancelFeedback(fn) {
                    unassignCancelFn = fn;
                });
                propagate = propagate && me.assignWithoutPropagation(newResource, units, function assignCancelFeedback(fn) {
                    assignCancelFn = fn;
                });

                return propagate;
            },
            function onPropagationComplete(cancelChanges, affectedTasks) {
                if (cancelChanges) {
                    assignCancelFn && assignCancelFn();
                    unassignCancelFn && unassignCancelFn();
                }
                callback && callback(cancelChanges, affectedTasks);
            }
        );
    },

    // TODO: interceptor is needed only for Gnt.view.Dependency, ask Nick if it can be removed
    /**
     * Links a task to another one given in `toId` with typed dependency given in `type`.
     *
     * @param {Gnt.model.Task|Number} toId
     * @param {Integer} [type=Gnt.model.Dependency.Type.EndToStart] dependency type see {@link Gnt.model.Dependency#Type}.
     * @param {Function} [callback] Callback function to call after tasks has been linked and possible changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    linkTo : function(toId, type, callback, /* private */interceptor) {
        var me = this,
            cancelFn;

        me.propagateChanges(
            function() {
                return me.linkToWithoutPropagation(toId, type, function cancelFeedback(fn) {
                    cancelFn = fn;
                }, interceptor);
            },
            function onPropagationComplete(cancelChanges, affectedTasks) {
                cancelChanges && cancelFn && cancelFn();
                callback && callback(cancelChanges, affectedTasks);
            }
        );
    },


    linkToWithoutPropagation : function(toId, type, cancelFeedback, /* deprecated */interceptor) {
        var me              = this,
            fromId          = me.getId(),
            taskStore       = me.getTaskStore(),
            dependencyStore = me.getDependencyStore(),
            newDependency;

        // {{{ Parameters normalization
        toId   = toId instanceof Gnt.model.Task ? toId.getId() : toId;
        type   = ((type === null || type === undefined) && Gnt.model.Dependency.Type.EndToStart) || type;
        // }}}

        // <debug>
        // Preconditions:
        taskStore.getModelById(toId instanceof Gnt.model.Task ? toId.getId() : toId) != -1 ||
            Ext.Error.raise("Can't link task `" + fromId + "` to task with id `" + toId + "` the task is not present in the task store!");
        // </debug>

        newDependency = new dependencyStore.model();
        newDependency.setSourceId(fromId);
        newDependency.setTargetId(toId);
        newDependency.setType(type);

        if (dependencyStore.isValidDependency(newDependency) && (!interceptor || interceptor(newDependency) !== false)) {
            dependencyStore.add(newDependency);
        }

        cancelFeedback && cancelFeedback(function() {
            dependencyStore.remove(newDependency);
        });

        return me;
    },


    /**
     * Unlinks a task from another one given in `fromId`.
     *
     * @param {Gnt.model.Task|Number} fromId
     * @param {Function} [callback] Callback function to call after tasks has been unlinked and possible changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    unlinkFrom : function(fromId, callback) {
        var me = this,
            cancelFn;

        me.propagateChanges(
            function() {
                return me.unlinkFromWithoutPropagation(fromId, function cancelFeedback(fn) {
                    cancelFn = fn;
                });
            },
            function onPropagationComplete(cancelChanges, affectedTasks) {
                cancelChanges && cancelFn && cancelFn();
                callback && callback(cancelChanges, affectedTasks);
            }
        );
    },


    unlinkFromWithoutPropagation : function(fromId, cancelFeedback) {
        var me                 = this,
            toId               = me.getId(),
            dependencyStore    = me.getDependencyStore(),
            dependency,
            indexOfDependency;

        // {{{ Parameters normalization
        fromId = fromId instanceof Gnt.model.Task ? fromId.getId() : fromId;
        // }}}

        dependency = dependencyStore.getByTaskIds(fromId, toId);

        // <debug>
        // Preconditions:
        dependency ||
            Ext.Error.raise("Can't unlink task '" + toId + "' from task '" + fromId + ", tasks are not linked!");
        // </debug>

        indexOfDependency = dependencyStore.indexOf(dependency);

        dependencyStore.remove(dependency);

        cancelFeedback && cancelFeedback(function() {
            dependencyStore.insert(indexOfDependency, dependency);
        });

        return me;
    },


    // side-effects free method - suitable for use in "normalization" stage
    // calculates the effort based on the assignments information
    calculateEffort : function (startDate, endDate, unit) {
        // effort calculation requires both dates
        if (!startDate || !endDate) return 0;

        var totalEffort     = 0;

        this.forEachAvailabilityIntervalWithResources({ startDate : startDate, endDate : endDate }, function (intervalStartDate, intervalEndDate, currentAssignments) {
            var totalUnits          = 0;

            for (var i in currentAssignments) totalUnits += currentAssignments[ i ].units;

            totalEffort             += (intervalEndDate - intervalStartDate) * totalUnits / 100;
        });

        return this.getUnitConverter().convertMSDurationToUnit(totalEffort, unit || this.getEffortUnit());
    },


    updateAssignments : function () {
        var startDate                   = this.getStartDate();
        var endDate                     = this.getEndDate();

        // do nothing if task is not scheduled
        if (!startDate || !endDate) return;

        var totalTime                   = 0;

        this.forEachAvailabilityIntervalWithResources({ startDate : startDate, endDate : endDate }, function (intervalStartDate, intervalEndDate, currentAssignments) {

            for (var resourceId in currentAssignments) {
                totalTime               += intervalEndDate - intervalStartDate;
            }
        });

        // no available resources?
        if (!totalTime) {
            return;
        }

        var effortInMS      = this.getEffort(Sch.util.Date.MILLI);

        Ext.Array.each(this.getAssignments(), function (assignment) {
            assignment.setUnits(effortInMS / totalTime * 100);
        });
    },


    updateEffortBasedOnDuration : function () {
        this.setEffortWithoutPropagation(this.calculateEffort(this.getStartDate(), this.getEndDate()));
    },


    // Alias for updateEffortBasedOnDuration(). Added to have symmetry with updateSpanBasedOnEffort.
    updateEffortBasedOnSpan : function () {
        this.updateEffortBasedOnDuration();
    },


    updateSpanBasedOnEffort : function () {
        // we have to update startDate because duration change can turn the task into a milestone
        // and for milestones we should set startDate to the end of last working period
        this.setStartEndDateWithoutPropagation(this.getStartDate(), this.recalculateEndDate());
    },


    onPotentialEffortChange : function () {
        var me = this,
            taskStore = me.getTaskStore(true);

        if (me.isTaskStored() && (!taskStore || !taskStore.isUndoingOrRedoing())) {
            switch (me.getSchedulingMode()) {
                case 'FixedDuration'        : me.updateEffortBasedOnDuration(); break;
                case 'DynamicAssignment'    : me.updateAssignments(); break;
            }
        }
    },


    onAssignmentMutation : function () {
        var me = this,
            taskStore = me.getTaskStore(true);

        if (me.isTaskStored() && (!taskStore || !taskStore.isUndoingOrRedoing())) {
            switch (this.getSchedulingMode()) {
                case 'FixedDuration'     : this.updateEffortBasedOnDuration(); break;
                case 'EffortDriven'      : this.updateSpanBasedOnEffort(); break;
                case 'DynamicAssignment' : this.updateAssignments(); break;
            }
        }
    },


    onAssignmentStructureMutation : function () {
        var me = this,
            taskStore = me.getTaskStore(true);

        if (me.isTaskStored() && (!taskStore || !taskStore.isUndoingOrRedoing())) {
            switch (this.getSchedulingMode()) {
                case 'FixedDuration'        : this.updateEffortBasedOnDuration(); break;
                case 'EffortDriven'         : this.updateSpanBasedOnEffort(); break;
                case 'DynamicAssignment'    : this.updateAssignments(); break;
            }
        }
    },


    /**
     * Adjusts the task start/end properly according to the calendar dates.
     * @param {Function} [callback] Callback function to call after the task has been adjusted and possible changes among dependent tasks were propagated.
     * @param {Boolean} callback.cancelChanges Flag showing that the setting has caused a constraint violation
     *  and a user opted for canceling the change and thus nothing has been updated.
     * @param {Object}   callback.affectedTasks Object containing a map (by id) of tasks affected by changes propagation.
     */
    adjustToCalendar : function (callback) {
        var me = this;

        me.propagateChanges(
            function() {
                return me.adjustToCalendarWithoutPropagation();
            },
            callback
        );
    },


    adjustToCalendarWithoutPropagation : function () {
        var me        = this,
            taskStore = me.getTaskStore(true),
            propagate = false;

        if (taskStore) {

            if (me.get('leaf')) {
                me.setStartDateWithoutPropagation(me.getStartDate(), true, taskStore.skipWeekendsDuringDragDrop);
                me.alignByIncomingDependenciesWithoutPropagation(taskStore, null);
                propagate = me;

            } else if (me.getStartDate() && me.getEndDate()) {
                me.set(me.durationField, me.calculateDuration(me.getStartDate(), me.getEndDate(), me.getDurationUnit()));
                propagate = me;
            }
        }

        return propagate;
    },

    /**
     Returns if the task is readonly. When readonly is `true` the task {@link #isEditable} returns `false` for all its fields.
     @return {Boolean} Boolean value, indicating whether the model is readonly
     */
    isReadOnly : function () {
        var result = false;

        this.bubble(function (task) {
            if (task.getReadOnly()) {
                result = true;
                return false;
            }
        }, this);

        return result;
    },

    /*
     * @method setReadOnly
     * Sets if the given task is readonly. You can subclass this class and override this method to provide your own logic.
     *
     * When the task is readonly {@link #isEditable} returns `false` for all fields except the readonly field.
     * A task in readonly state will not allow dependency creation.
     *
     * @param {String} value indicating if the task is readonly
     */

    /**
     * Checks if the given task field is editable. You can subclass this class and override this method to provide your own logic.
     *
     * It takes the scheduling mode of the task into account. For example for "FixedDuration" mode, the "Effort"
     * field is calculated and should not be updated by user directly.
     *
     * @param {String} fieldName Name of the field
     * @return {Boolean} Boolean value, indicating whether the given field is editable
     */
    isEditable : function (fieldName) {
        // if some parent is readonly
        if (!this.getReadOnly() && this.isReadOnly()) {
            return false;
        }

        if (fieldName === this.readOnlyField) return true;

        // check if the task is readonly
        if (this.getReadOnly()) return false;

        if (!this.isLeaf()) {
            if (fieldName === this.effortField && this.autoCalculateEffortForParentTask) return false;
            if (fieldName === this.percentDoneField && this.autoCalculatePercentDoneForParentTask) return false;
        }

        if ((fieldName === this.durationField || fieldName === this.endDateField) && this.getSchedulingMode() === 'EffortDriven') {
            return false;
        }

        if (fieldName === this.effortField && this.getSchedulingMode() === 'FixedDuration') {
            return false;
        }

        return true;
    },


    /**
     * @method isDraggable
     *
     * Returns true if event can be drag and dropped
     * @return {Mixed} The draggable state for the event.
     */
    isDraggable: function () {
        return this.getDraggable();
    },

    /**
     * @method setDraggable
     *
     * Sets the new draggable state for the event
     * @param {Boolean} draggable true if this event should be draggable
     */

    /**
     * @method isResizable
     *
     * Returns true if event can be resized, but can additionally return 'start' or 'end' indicating how this event can be resized.
     * @return {Mixed} The resource Id
     */
    isResizable: function () {
        return this.getResizable();
    },

    /**
     * @method getWBSCode
     *
     * Returns WBS code of task.
     * @return {String} The WBS code string
     */
    getWBSCode: function () {
        var indexes     = [],
            task        = this;

        while (task.parentNode) {
            indexes.push(task.data.index + 1);
            task        = task.parentNode;
        }

        return indexes.reverse().join('.');
    },


    resetTotalCount : function (preventCaching) {
        var task            = this;

        while (task) {
            task.totalCount = preventCaching ? -1 : null;
            task            = task.parentNode;
        }
    },

    /**
     * Returns total count of child nodes and their children.
     *
     * @return {Number} Total count of child nodes
     */
    getTotalCount : function () {
        var totalCount          = this.totalCount;
        var cachingPrevented    = totalCount == -1;

        // `cachingPrevented` (totalCount == -1) will cause the value to be always recalculated
        if (totalCount == null || cachingPrevented) {
            var childNodes  = this.childNodes;

            totalCount      = childNodes.length;

            for (var i = 0, l = childNodes.length; i < l; i++) {
                totalCount  += childNodes[ i ].getTotalCount();
            }

            if (cachingPrevented)
                return totalCount;
            else
                this.totalCount = totalCount;
        }

        return totalCount;
    },


    /**
     * @method getPreviousSiblingsTotalCount
     * Returns count of all sibling nodes (including their children).
     *
     * @return {Number}
     */
    getPreviousSiblingsTotalCount : function () {
        var task    = this.previousSibling,
            count   = this.data.index;

        while (task) {
            count   += task.getTotalCount();
            task    = task.previousSibling;
        }

        return count;
    },


    /**
     * Returns count of all predecessors nodes (including their children).
     * @return {Number}
     * @deprecated Was renamed to {@link #getPreviousSiblingsTotalCount} since `predecessor` term has another meaning in the gantt context.
     */
    getPredecessorsCount : function () {
        return this.getPreviousSiblingsTotalCount.apply(this, arguments);
    },


    /**
     * @method getSequenceNumber
     *
     * Returns the sequential number of the task. A sequential number means the ordinal position of the task in the total dataset, regardless
     * of its nesting level and collapse/expand state of any parent tasks. The root node has a sequential number equal to 0.
     *
     * For example, in the following tree data sample sequential numbers are specified in the comments:

        root : {
            children : [
                {   // 1
                    leaf : true
                },
                {       // 2
                    children : [
                        {   // 3
                            children : [
                                {   // 4
                                    leaf : true
                                },
                                {   // 5
                                    leaf : true
                                }
                            ]
                        }]
                },
                {   // 6
                    leaf : true
                }
            ]
        }

     * If we will collapse some of the parent tasks, sequential number of collapsed tasks won't change.
     *
     * See also {@link Gnt.data.TaskStore#getBySequenceNumber}.
     *
     * @return {Number} The code
     */
    getSequenceNumber: function () {
        var code    = 0,
            task    = this;

        while (task.parentNode) {
            code    += task.getPredecessorsCount() + 1;
            task    = task.parentNode;
        }

        return code;
    },

    // generally should be called on root node only
    getBySequenceNumber : function (number) {
        var resultNode = null,
            childNode, totalCount;

        if (number === 0) {
            resultNode = this;
        } else if (number > 0 && number <= this.getTotalCount()) {
            number--;

            for (var i = 0, l = this.childNodes.length; i < l; i++) {
                childNode       = this.childNodes[i];
                totalCount      = childNode.getTotalCount();

                if (number > totalCount)
                    number      -= totalCount + 1;
                else {
                    childNode   = this.childNodes[i];
                    resultNode  = childNode.getBySequenceNumber(number);
                    break;
                }
            }
        }

        return resultNode;
    },

    /**
     * @method getDisplayStartDate
     *
     * Returns the formatted start date value to be used in the UI.
     * @param {String} format Date format.
     * @param {Boolean} [adjustMilestones=true] If true, milestones will display one day earlier than the actual raw date.
     * @param {Date} [value=this.getStartDate()] Start date value. If not specified, the Task start date will be used.
     * @return {String} Formatted start date value.
     */
    getDisplayStartDate : function (format, adjustMilestones, value, returnDate, isBaseline) {
        format = format || Ext.Date.defaultFormat;

        // if no value specified then we'll take task start date
        if (arguments.length < 3) {
            value       = this.getStartDate();
            // by default we consider adjustMilestones enabled
            if (arguments.length < 2) adjustMilestones = true;
        }

        if (value && adjustMilestones && this.isMilestone(isBaseline) && value - Ext.Date.clearTime(value, true) === 0 && !Ext.Date.formatContainsHourInfo(format)) {
            value       = Sch.util.Date.add(value, Sch.util.Date.MILLI, -1);
        }

        return returnDate ? value : (value ? Ext.util.Format.date(value, format) : '');
    },

    /**
     * @method getDisplayEndDate
     *
     * Returns the formatted end date value to be used in the UI.
     * **Note** that the end date of tasks in the Gantt chart is not inclusive, however this method will compensate the value.
     * For example, if you have a 1 day task which starts at **2011-07-20T00:00:00** and ends at **2011-07-21T00:00:00** (remember the end date is not inclusive),
     * this method will return **2011-07-20** if called with 'Y-m-d'.

            var task = new Gnt.model.Task({
                StartDate : new Date(2011, 6, 20),
                EndDate   : new Date(2011, 6, 21)
            });

            // below code will display "2011/07/20"
            alert(task.getDisplayEndDate("Y/m/d"));

     * @param {String} format Date format (required).
     * @param {Boolean} [adjustMilestones=true] If true, milestones will display one day earlier than the actual raw date.
     * @param {Date} [value=this.getEndDate()] End date value. If not specified, the Task end date will be used.
     * @return {String} The formatted end date value.
     */
    getDisplayEndDate : function (format, adjustMilestones, value, returnDate, isBaseline) {
        format = format || Ext.Date.defaultFormat;

        if (arguments.length < 3) {
            value       = this.getEndDate();
            if (arguments.length < 2) adjustMilestones = true;
        }

        if (value && (!this.isMilestone(isBaseline) || adjustMilestones) && value - Ext.Date.clearTime(value, true) === 0 && !Ext.Date.formatContainsHourInfo(format)) {
            value       = Sch.util.Date.add(value, Sch.util.Date.MILLI, -1);
        }

        return returnDate ? value : (value ? Ext.util.Format.date(value, format) : '');
    },

    /**
     * @method setResizable
     *
     * Sets the new resizable state for the event. You can specify true/false, or 'start'/'end' to only allow resizing one end of an event.
     * @param {Boolean} resizable true if this event should be resizable
     */


    copy : function () {
        var result      = this.callParent(arguments),
            segments    = result.getSegments();

        // for a segmented task we also make a copy of each segment
        if (segments) {
            for (var i = 0; i < segments.length; i++) {
                var segment     = segments[i];

                segments[i]     = segment.copy(segment.getId(), false);
            }
        }

        return result;
    },


    // Does a regular copy but also copies references to the model taskStore etc
    // Intended to be used when copying a task that will be added to the same taskStore
    fullCopy : function (model) {
        var cp = this.callParent(arguments);

        cp.taskStore = this.getTaskStore();

        return cp;
    },


    commit: function () {
        this.callParent(arguments);

        this.commitSegments();
    },


    reject: function () {
        this.callParent(arguments);

        this.rejectSegments();
    },

    isUnscheduled : function () {
        return !this.getStartDate() || !this.getEndDate();
    },

    isTaskStored : function() {
        // We can't rely on this.taskStore here only, it's value is managed in setRoot/onTaskRemoved method of the task store
        // and there's a time when task is removed already but onTaskRemoved() handle hasn't completed yet
        return !!this.getTreeStore();
        /*
        var root = this;
        while (!root.isRoot()) {
            root = root.parentNode;
        }
        return this.taskStore && this.taskStore.getRoot() === root;
        */
    }

}, function () {
    // Do this first to be able to override NodeInterface methods
    Ext.data.NodeInterface.decorate(this);

    this.override({

        // @OVERRIDE
        insertBefore : function (node, refNode) {
            node        = this.createNode(node);

            if (!node) return;

            var store                   = this.getTaskStore(true),
                root                    = store && store.getRoot(),
                phantomParentIdField    = this.phantomParentIdField,
                phantom                 = this !== root && this.phantom,
                isMove                  = !!node.parentNode,
                internalId              = this.getId();

            if (phantom) {
                this.data[this.phantomIdField] = internalId;
            }

            if (internalId !== node.data[phantomParentIdField]) {
                var newPhantomParentIdValue = phantom ? internalId : null;

                if (!node.phantom && node.data[phantomParentIdField] !== newPhantomParentIdValue) {
                    node.modified                       = node.modified || {};
                    node.modified[phantomParentIdField] = node.data[phantomParentIdField];
                }

                node.data[phantomParentIdField]    = newPhantomParentIdValue;
            }

            var refNodeIndex            = refNode && refNode.get('index');

            this.resetTotalCount(isMove);

            // Scan for and remove invalid dependencies since a parent task may not have dependencies to its children etc.
            // Has to be done before callParent where the node move happens
            if (isMove && node.hasDependencies() && !store.isUndoingOrRedoing()) {
                node.removeDependenciesToParents(this);
            }

            var res                     = this.callParent(arguments);

            // Scan for and remove invalid dependencies since a parent task may not have dependencies to its children etc.
            if (isMove) {
                // if the task has dependencies
                if (this.hasDependencies()) {
                    // we just potentially changed dependencies setup so need to reset the dependency store cache
                    store.getDependencyStore().resetMethodsCache();
                    // Scan for and remove invalid dependencies.
                    if (!store.isUndoingOrRedoing()) {
                        this.removeInvalidDependencies();
                    }
                }
                this.resetTotalCount();
            }

            // fix for ExtJS 5.1.0 bug:
            // https://www.sencha.com/forum/showthread.php?300695-getChanges-doesn-t-include-quot-index-quot-after-insertBefore-call&p=1098694#post1098694
            if (refNode && refNode.get('index') != refNodeIndex) {
                refNode.modified        = refNode.modified || {};
                refNode.modified.index  = refNodeIndex;
            }

            return res;
        },

        // @OVERRIDE
        appendChild : function (nodes, suppressEvents, commit) {
            nodes                       = nodes instanceof Array ? nodes : [ nodes ];

            var store                   = this.getTaskStore(true),
                root                    = store && store.getRoot(),
                isMove                  = false,
                phantomParentIdField    = this.phantomParentIdField,
                phantom                 = this !== root && this.phantom,
                internalId              = this.getId(),
                nodesCreated            = 0;

            if (store && nodes.length > 1) {
                store.suspendAutoRecalculateParents++;
            }

            for (var i = 0; i < nodes.length; i++) {
                var node = this.createNode(nodes[ i ]);

                if (!node) continue;

                nodesCreated++;

                nodes[ i ] = node;

                // appending child that is already in the same tree, will first remove it from previous parent.
                // Removing is hidden inside of the `appendChild` implementation and causes various side effects
                // which re-fills the `totalCount` cache with wrong value. Need to suspend caching during parent
                // "appendChild" implementation
                if (node.parentNode) {
                    isMove = true;
                    // Has to be done before callParent where the node move happens
                    if (node.hasDependencies() && store && !store.isUndoingOrRedoing()) {
                        node.removeDependenciesToParents(this);
                    }
                }

                if (internalId !== node.data[phantomParentIdField]) {
                    var newPhantomParentIdValue = phantom ? internalId : null;

                    if (!node.phantom && node.data[phantomParentIdField] !== newPhantomParentIdValue) {
                        node.modified                       = node.modified || {};
                        node.modified[phantomParentIdField] = node.data[phantomParentIdField];
                    }

                    node.data[phantomParentIdField]    = newPhantomParentIdValue;
                }
            }

            if (!nodesCreated) return;

            if (phantom) {
                this.data[ this.phantomIdField ]    = internalId;
            }

            this.resetTotalCount(isMove);

            // convert a single element array back to just element, to avoid extra function call
            var res     = this.callParent([ nodes.length > 1 ? nodes : nodes[ 0 ], suppressEvents, commit ]);

            if (isMove) {
                // if the task has dependencies
                if (this.hasDependencies()) {
                    // we just potentially changed dependencies setup so need to reset the dependency store cache
                    store.getDependencyStore().resetMethodsCache();
                    // Scan for and remove invalid dependencies.
                    if (store && !store.isUndoingOrRedoing()) {
                        this.removeInvalidDependencies();
                    }
                }

                this.resetTotalCount();
            }

            if (store && !store.isUndoingOrRedoing()) {
                this.beginEdit();

                // Bugfix ticket #1401
                this.markAsParent();
                // since the task became a parent we switch its scheduling mode to 'Normal' (ticket #1441)
                this.set(this.schedulingModeField, 'Normal');

                if (store && nodes.length > 1) {
                    store.suspendAutoRecalculateParents--;
                }

                this.endEdit();
            }

            if (store && store.recalculateParents && !store.suspendAutoRecalculateParents && !this.isRoot() && !store.cascading && !store.isUndoingOrRedoing()) {
                nodes[0].recalculateParents();
            }

            return res;
        },

        // @OVERRIDE
        removeChild : function (node, destroy, suppressEvents, isMove) {
            var me                  = this,
                needToConvertToLeaf = !me.removeChildIsCalledFromReplaceChild && me.convertEmptyParentToLeaf && me.childNodes.length == 1,
                taskStore           = me.getTaskStore(true),
                result;

            me.resetTotalCount();

            // need to reset the flag early, because the removal operation may cause some side effects (event listeners)
            // flag should be already reset in those listeners
            me.removeChildIsCalledFromReplaceChild    = false;

            // Calling parent
            result = me.callParent(arguments);

            // In case of node move we need to reset the total count cache one more time here.
            // This is for the case, when we append/insert some existing node to a different position
            // in its parent node. In this case, the total count cache will be originally reset in our
            // overrides for `insertBefore` or `appendChild`. This is supposed to be enough, but its not,
            // because before doing actuall append, not first will be removed from the parent ("removeChild" call
            // is part of the `appendChild/insertBefore` methods. The listeners of `remove` event may call
            // `getTotalCount` and fill the cache. Then, we continue to actual node insertion, but cache is already filled
            // with wrong data.
            if (isMove) {
                me.resetTotalCount();
            }

            // If this parent has children left, recalculate it's start/end dates if required
            if (me.childNodes.length > 0 && taskStore && taskStore.recalculateParents && !taskStore.suspendAutoRecalculateParents && !taskStore.isUndoingOrRedoing()) {
                // If the parent has some children left then recalculate it's start/end dates if required
                //me.refreshCalculatedParentNodeData();
                me.childNodes[0].recalculateParents();
            }

            // If the parent has no other children, change it to a leaf task
            if (needToConvertToLeaf && !me.isRoot() && taskStore && !taskStore.isUndoingOrRedoing()) {
                me.convertEmptyParentToLeafTask();
            }

            return result;
        },

        replaceChild : function () {
            // flag will be reset in the `removeChild` override
            this.removeChildIsCalledFromReplaceChild    = true;

            this.callParent(arguments);
        },

        removeAll : function () {
            var isLeaf    = this.isLeaf(),
                taskStore = this.getTaskStore(true);

            this.resetTotalCount();
            this.callParent(arguments);

            // if we don't know the task taskStore we cannot set its duration to 1 day (which happens in convertEmptyParentToLeafTask())
            if (!isLeaf && this.convertEmptyParentToLeaf && taskStore) {
                this.convertEmptyParentToLeafTask();
            }
        },


        // @OVERRIDE
        createNode : function (node) {

            var me          = this,
                store       = me.getTaskStore(true),
                root        = store && store.getRoot(),
                reader;

            if (store) {
                reader      = store.getProxy().getReader();

                var originalGetChildType = reader.getChildType;

                // TODO: check this in ext5.1.2+
                // parent "createNode" doesnot fallback to the reader model
                // and raises exception because of that (in ext5.1.0/ext5.1.1)
                reader.getChildType = function() {
                    return originalGetChildType.apply(this, arguments) || this.getModel();
                };
            }

            node    = this.callParent(arguments);

            // restore original reader.getChildType
            if (store && reader) delete reader.getChildType;

            // project nodes under the root allowed only
            if (store && root !== this && node.isProject) {
                return;
            }

            var needsNormalization = store && store.autoNormalizeNodes && !node.normalized && !node.normalizeScheduled;

            node = this.callParent(arguments);

            if (needsNormalization) {

                var prevUpdateInfo  = node.updateInfo;

                node.updateInfo = function () {

                    prevUpdateInfo.apply(this, arguments);
                    delete node.updateInfo;

                    // normalization needs to fully set up node, this happens after 1) createNode 2) updateNodeInfo
                    node.normalize();
                };

                //createNode is called multiple times before the node is normalized.
                //For preventing a chain of calls to updateInfo and normalize is created we set a property to schedule normalization only once
                node.normalizeScheduled = true;

            }

            return node;
        }
    });
});
