/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * @class Gnt.data.undoredo.Manager
 * @extends Robo.Manager
 *
 * This class provides Gantt-aware undo-redo capabilities for the provided array of {@link Ext.data.Store} instances. To enable undo support for your Gantt chart stores, simply
 * create an UndoManager and configure it with your stores:
 *
 *      var undoManager = new Gnt.data.undoredo.Manager({
            transactionBoundary : 'timeout',
            stores              : [
                taskStore,
                dependencyStore,
                ...
            ]
        });

        undoManager.start();

        yourStore.getAt(0).set('name', 'a new name');

        undoManager.undo(); // Call 'undo' to revert last action
 *
 */
Ext.define('Gnt.data.undoredo.Manager', {
    extend : 'Robo.Manager',

    uses : [
        'Gnt.data.TaskStore',
        'Gnt.data.undoredo.action.taskstore.Update'
    ],

    getStoreTypeListeners : function(store) {
        var me = this,
            listeners = me.callParent([store]);

        if (store instanceof Gnt.data.TaskStore) {
            listeners.update = me.onTaskStoreUpdate;
            listeners.projectionstart  = me.onTaskStoreProjectionStart;
            listeners.projectioncommit = me.onTaskStoreProjectionEnd;
            listeners.projectionreject = me.onTaskStoreProjectionEnd;
        }

        return listeners;
    },

    onTaskStoreUpdate : function(store, record, operation, modifiedFieldNames) {

        if (!this.onAnyChangeInAnyStore(store) ||
            operation != 'edit' ||
            !modifiedFieldNames ||
            !modifiedFieldNames.length ||
            !this.hasPersistableChanges(record, modifiedFieldNames)) {
            return;
        }

        this.currentTransaction.addAction(new Gnt.data.undoredo.action.taskstore.Update({
            record          : record,
            fieldNames      : modifiedFieldNames
        }));
    },

    onTaskStoreProjectionStart : function(store, projectionLevel) {
        var me = this;

        if (projectionLevel == 1 && me.transactionBoundary === 'timeout') {
            if (!me.currentTransaction) {
                // This will start an undo/redo transaction if one isn't started yet
                me.onAnyChangeInAnyStore(store);
            }
            if (me.currentTransaction) {
                me.hold();
            }
        }
    },

    onTaskStoreProjectionEnd : function(store, projection, data, projectionLevel) {
        var me = this;

        if (projectionLevel === 0 && me.transactionBoundary === 'timeout' && me.currentTransaction) {
            me.release();
        }
    }
});
