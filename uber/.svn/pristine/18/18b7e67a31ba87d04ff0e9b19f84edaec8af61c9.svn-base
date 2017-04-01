/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.view.dependency.View', {

    extend : 'Sch.view.dependency.View',

    alias : 'schdependencyview.basegantt',

    requires : [
        'Gnt.view.dependency.Painter'
    ],

    /**
     * @deprecated 4.2
     */
    alternateClassName : 'Gnt.view.Dependency',

    config : {
        painterConfig : {
            type : 'ganttdefault'
        }
    },

    // Since there's only one task per row in Gantt panel we need to update dependencies only for the updated task
    // instead of full redraw as for Scheduler
    onPrimaryViewItemUpdate : function(taskRecord, index, eventNode) {
        this.updateDependencies(taskRecord.getAllDependencies());
    }

    /**
     * @event beforednd
     *
     * Fires before a drag and drop operation is initiated, return false to cancel it
     *
     * @param {Gnt.view.dependency.View} The dependency view instance
     * @param {HTMLNode} node The node that's about to be dragged
     * @param {Ext.EventObject} e The event object
     */

    /**
     * @event dndstart
     *
     * Fires when a dependency drag and drop operation starts
     *
     * @param {Gnt.view.dependency.View} The dependency view instance
     */

    /**
     * @event drop
     *
     * Fires after a drop has been made on a receiving terminal
     *
     * @param {Gnt.view.dependency.View} The dependency view instance
     * @param {Mixed} fromId The source dependency record id
     * @param {Mixed} toId The target dependency record id
     * @param {Number} type The dependency type, see {@link Gnt.model.Dependency} for more information
     */

    /**
     * @event afterdnd
     *
     * Always fires after a dependency drag and drop operation
     *
     * @param {Gnt.view.dependency.View} view The dependency view instance
     */
});
