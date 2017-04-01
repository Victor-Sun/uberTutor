/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Sch.selection.EventModel
@extends Ext.selection.Model

This class provides the basic implementation event selection in a grid.

*/
Ext.define("Sch.selection.EventModel", {
    extend      : 'Ext.selection.Model',

    alias       : 'selection.eventmodel',

    requires    : [ 'Ext.util.KeyNav' ],

    /**
     * @cfg {Boolean} deselectOnContainerClick `True` to deselect all events when user clicks on the underlying space in scheduler. Defaults to `true`.
     */
    deselectOnContainerClick : true,

    // Stores selected record on mousedown event to avoid
    // unselecting record on click
    selectedOnMouseDown : null,

    /**
     * @event beforedeselect
     * Fired before a record is deselected. If any listener returns false, the
     * deselection is cancelled.
     * @param {Sch.selection.EventModel} this
     * @param {Sch.model.Event} record The selected event
     */

    /**
     * @event beforeselect
     * Fired before a record is selected. If any listener returns false, the
     * selection is cancelled.
     * @param {Sch.selection.EventModel} this
     * @param {Sch.model.Event} record The selected event
     */

    /**
     * @event deselect
     * Fired after a record is deselected
     * @param {Sch.selection.EventModel} this
     * @param {Sch.model.Event} record The selected event
     */

    /**
     * @event select
     * Fired after a record is selected
     * @param {Sch.selection.EventModel} this
     * @param {Sch.model.Event} record The selected event
     */

    /**
     */
    bindToView : function(view) {

        var me = this;

        me.view = view;

        me.bindStore(view.getEventStore());

        view.on({
            eventclick     : me.onEventClick,
            eventmousedown : me.onEventMouseDown,
            itemmousedown  : me.onItemMouseDown,
            refresh        : function() {
                me.refresh();
            },
            destroy        : function() {
                me.bindStore(null);
            },
            scope          : me
        });
    },

    bindStore : function(eventStore) {
        if (this.getStore()) {
            this.mun(this.getStore(), 'load', this.onEventStoreLoad, this);
        }

        if (eventStore) {
            this.mon(eventStore, 'load', this.onEventStoreLoad, this);
        }

        this.callParent(arguments);
    },

    onEventStoreLoad : function() {
        this.deselectAll();
    },

    onEventMouseDown: function(view, record, e) {
        // Reset previously stored records
        this.selectedOnMouseDown = null;

        // Change selection before dragging to avoid moving of unselected events
        if (!this.isSelected(record)) {
            this.selectedOnMouseDown = record;
            this.selectWithEvent(record, e);
        }
    },

    onEventClick: function(view, record, e) {
        // Don't change selection if record been already selected on mousedown
        if (!this.selectedOnMouseDown) {
            this.selectWithEvent(record, e);
        }
    },

    onItemMouseDown: function(a, b, c, d, eventObj) {
        if (this.deselectOnContainerClick && !eventObj.getTarget(this.view.eventSelector)) {
            this.deselectAll();
        }
    },

    onSelectChange: function(record, isSelected, suppressEvent, commitFn) {
         var me      = this,
            view   = me.view,
            store   = me.store,
            eventName = isSelected ? 'select' : 'deselect',
            i = 0;

        if ((suppressEvent || me.fireEvent('before' + eventName, me, record)) !== false && commitFn() !== false) {

            if (isSelected) {
                view.onEventBarSelect(record, suppressEvent);
            } else {
                view.onEventBarDeselect(record, suppressEvent);
            }

            if (!suppressEvent) {
                me.fireEvent(eventName, me, record);
            }
        }
    },

    // Not supported.
    selectRange : Ext.emptyFn,

    selectNode: function(node, keepExisting, suppressEvent) {
        var r = this.view.resolveEventRecord(node);
        if (r) {
            this.select(r, keepExisting, suppressEvent);
        }
    },

    deselectNode: function(node, keepExisting, suppressEvent) {
        var r = this.view.resolveEventRecord(node);
        if (r) {
            this.deselect(r, suppressEvent);
        }
    },

    /**
     * Returns first selected event record for the given resource record or null if the resource has no assigned
     * events which are selected.
     *
     * @param {Sch.model.Resource} resource
     * @return {Sch.model.Event}
     */
    getFirstSelectedEventForResource : function(resource) {
        var selections = this.getSelection(),
            event = null,
            i, len, r;

        for (i = 0, len = selections.length; !event && i < len; ++i) {
            r = selections[i];
            if (r.isAssignedTo(resource)) {
                event = r;
            }
        }

        return event;
    },

    getDraggableSelections : function() {
        return Ext.Array.filter(
            this.getSelection(),
            function(record) {
                return record.isDraggable();
            }
        );
    },

    forEachEventRelatedSelection : function(eventRecord, fn) {
        this.isSelected(eventRecord) && fn(eventRecord);
    }
});
