/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 @class Gnt.widget.AssignmentGrid
 @extends Ext.grid.Panel

 A class used to display and edit the task assignments. You can configure this through the {@link Gnt.widget.AssignmentField#gridConfig gridConfig} object
 available on the {@link Gnt.widget.AssignmentField} class.

 */
Ext.define('Gnt.widget.AssignmentGrid', {
    extend : 'Ext.grid.Panel',
    alias  : 'widget.assignmentgrid',

    requires : [
        'Ext.data.Store',
        'Ext.grid.plugin.CellEditing',
        'Gnt.column.ResourceName',
        'Gnt.column.AssignmentUnits'
    ],

    /**
     * @cfg {Ext.data.Store} assignmentStore A store with assignments
     */
    assignmentStore : null,

    /**
     * @cfg {Ext.data.Store} resourceStore A store with resources
     */
    resourceStore : null,

    readOnly : false,
    cls      : 'gnt-assignmentgrid',

    taskId : null,

    cellEditing : null,

    // The sorting function defining the order of the resources
    sortResourcesFn       : null,
    assignmentUnitsEditor : null,

    selModel : {
        selType   : 'checkboxmodel',
        mode      : 'MULTI',
        checkOnly : true
    },

    viewConfig : {
        markDirty : false
    },

    initComponent : function (config) {
        var me = this;

        if (!this.readOnly) {
            this.plugins = this.buildPlugins();
        }

        this.store = this.store || new Ext.data.Store({
            proxy       : 'memory',
            autoDestroy : true,
            model       : this.assignmentStore.getModel()
        });

        this.columns = this.columns || this.buildColumns();

        this.sortResourcesFn = this.sortResourcesFn || function (assignment1, assignment2) {
            var units1 = assignment1.getUnits(),
                units2 = assignment2.getUnits();

            // If both resources are assigned, sort them by Name
            if ((!units1 && !units2) || (units1 && units2)) {
                return assignment1.getResource(me.resourceStore).getName() <
                assignment2.getResource(me.resourceStore).getName() ? -1 : 1;
            }

            return units1 ? -1 : 1;
        };

        this.loadResources();

        this.mon(this.resourceStore, {
            datachanged : this.loadResources,
            scope       : this
        });

        this.callParent(arguments);

        this.getSelectionModel().on({
            select   : this.onSelect,
            deselect : this.onDeselect,
            scope    : this
        });
    },

    onSelect : function (sm, rec) {
        if ((!this.cellEditing || !this.cellEditing.getActiveEditor()) && !rec.getUnits()) {
            rec.setUnits(rec.getField(rec.unitsField).defaultValue);
        }
    },

    onDeselect : function (sm, rec) {
        rec.setUnits(0);
    },

    loadResources : function () {
        var resourceIdField = this.assignmentStore.getModel().prototype.resourceIdField;
        var unitsField      = this.assignmentStore.getModel().prototype.unitsField;

        var data = Ext.Array.map(this.resourceStore.getRange(), function (resource) {
            var assignmentData              = {};

            assignmentData[resourceIdField] = resource.getId();
            assignmentData[unitsField] = '';

            return assignmentData;
        });
        this.store.loadData(data);
    },

    // @private
    buildPlugins : function () {
        var cellEditing = this.cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit : 1
        });

        cellEditing.on('edit', this.onEditingDone, this);

        return [
            cellEditing
        ];
    },

    hide : function () {
        this.cellEditing.cancelEdit();
        this.callParent(arguments);
    },

    onEditingDone : function (ed, e) {
        // Make sure row is selected after editing a cell
        if (e.value) {
            this.getSelectionModel().select(e.record, true);
        } else {
            this.getSelectionModel().deselect(e.record);
            e.record.reject();
        }
    },

    // @private
    buildColumns : function () {
        return [
            {
                xtype         : 'resourcenamecolumn',
                resourceStore : this.resourceStore
            },
            {
                xtype : 'assignmentunitscolumn'
            }
        ];
    },

    setEditableFields : function (taskId) {

        if (!this.assignmentUnitsEditor) {
            var column = this.down('assignmentunitscolumn');

            this.assignmentUnitsEditor = column && column.getEditor();
        }

        if (this.assignmentUnitsEditor) {
            var taskStore = this.assignmentStore && this.assignmentStore.getTaskStore(),
                task      = taskStore && taskStore.getModelById(taskId);

            if (task) {
                switch (task.getSchedulingMode()) {
                    case 'DynamicAssignment' :
                        this.assignmentUnitsEditor.setReadOnly(true);
                        break;
                    default :
                        this.assignmentUnitsEditor.setReadOnly(false);
                }
            }
        }
    },

    loadTaskAssignments : function (taskId) {
        var store = this.store,
            sm    = this.getSelectionModel();

        this.taskId = taskId;

        // clear all checkboxes
        sm.deselectAll(true);

        // Reset all "Units" values of all resource assignment records first
        for (var i = 0, l = store.getCount(); i < l; i++) {
            // should be ok to use field names here, since we are inheriting directly from Gnt.model.Assignment
            var record                     = store.getAt(i);
            record.data[record.unitsField] = 0;
            record.data[record.idProperty] = null;
            // after each saveTaskAssignments we should call loadTaskAssignments to set proper __id__'s on task assignments
            delete record.__id__;
        }

        var taskAssignments = this.assignmentStore.getAssignmentsForTask(taskId);

        Ext.Array.each(taskAssignments, function (assignment) {
            var resourceAssignmentRecord = store.findRecord(assignment.resourceIdField, assignment.getResourceId(), 0, false, true, true);

            if (resourceAssignmentRecord) {
                var data = Ext.apply({}, assignment.data);
                delete data[assignment.idProperty];

                resourceAssignmentRecord.set(data);
                // can't assign to "idProperty" of the record because if "id" is missing
                // the store internal mapping will be broken (and "indexOf" method of the store will stop working)
                resourceAssignmentRecord.__id__ = assignment.getId();

                // mark the record with checkbox
                sm.select(resourceAssignmentRecord, true, true);
            }
        });

        // HACK: Weird Safari only bug
        // https://www.assembla.com/spaces/bryntum/tickets/1810-assignment-editor-doesn-t-work-on-safari#/activity/ticket:
        if (Ext.isSafari) {
            this.focus();
        }

        // Apply sort to show assigned resources at the top
        store.sort({
            sorterFn : this.sortResourcesFn
        });

        // HACK Ext JS saves the sorter, remove it explicitly
        store.getSorters().removeAll();

        var view = this.getView();

        // HACK
        // setPosition in 6.0.0 won't change position in filtered view: https://fiddle.sencha.com/#fiddle/1904
        // Not reproducible in 6.0.1+ so no point to report to sencha.
        if (Ext.getVersion().equals('6.0.0.640')) {
            view.navigationModel.setPosition(null);
        }

        // https://www.assembla.com/spaces/bryntum/tickets/2602
        var newPosition = (new Ext.grid.CellContext(view)).setPosition(0, 0);
        // focus first cell to enable key navigation
        view.focusCell(newPosition);

        this.setEditableFields(taskId);
    },

    saveTaskAssignments : function () {
        var aStore = this.assignmentStore,
            taskId = this.taskId;

        var assignmentsToStay = {};
        var newAssignments    = [];

        this.getSelectionModel().selected.each(function (resourceAssignmentRecord) {
            var units = resourceAssignmentRecord.getUnits();

            if (units > 0) {
                // if not undefined that means resource was assigned to another task
                var id = resourceAssignmentRecord.__id__;

                if (id) {
                    var newData = Ext.apply({}, resourceAssignmentRecord.data);
                    delete newData[resourceAssignmentRecord.idProperty];

                    assignmentsToStay[id] = true;

                    aStore.getModelById(id).set(newData);
                } else {
                    var newAssignment = resourceAssignmentRecord.copy();
                    newAssignment.setTaskId(taskId);

                    assignmentsToStay[newAssignment.internalId] = true;

                    newAssignments.push(newAssignment);
                }
            }
        });

        var assignmentsToRemove = [];

        // Remove any assignments that
        // - are not phantom
        // - and have been unchecked (and thus are not included in `assignmentsToStay`)
        aStore.each(function (assignment) {
            //   assignment is for our task       | not phantom |       was unchecked
            if (assignment.getTaskId() == taskId && !assignmentsToStay[assignment.getId() || assignment.internalId]) {
                assignmentsToRemove.push(assignment);
            }
        });

        // Fire this event so UI can ignore the datachanged events possibly fired below
        aStore.fireEvent('beforetaskassignmentschange', aStore, taskId, newAssignments);

        aStore.suspendAutoSync();

        aStore.remove(assignmentsToRemove);

        // Add selected assignments for this task
        aStore.add(newAssignments);

        aStore.resumeAutoSync();

        // Fire this event so UI can just react and update the row for the task
        aStore.fireEvent('taskassignmentschanged', aStore, taskId, newAssignments);

        if (aStore.autoSync) {
            aStore.sync();
        }
    },

    isDataChanged : function () {
        var me = this;

        return me.store &&
            me.store.getUpdatedRecords().length > 0 ||
            me.store.getNewRecords().length > 0 ||
            me.store.getRemovedRecords().length > 0;
    },


    isDataValid : function () {
        return this.store.findBy(function (record) {
                return !record.isValid();
            }) < 0;
    },

    isEditing : function() {
        return Boolean(this.cellEditing.getActiveEditor());
    }
});
