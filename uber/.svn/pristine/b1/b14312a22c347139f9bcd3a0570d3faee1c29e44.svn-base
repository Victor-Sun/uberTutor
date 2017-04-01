/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.model.Dependency
@extends Sch.model.Dependency

This class represents a single Dependency in your gantt chart. It is a subclass of the {@link Sch.model.Customizable} class, which in its turn subclasses {@link Ext.data.Model}.
Please refer to documentation of those classes to become familar with the base interface of this class.

A Dependency has the following fields:

- `Id` - The id of the dependency itself
- `From` - The id of the task at which the dependency starts
- `To` - The id of the task at which the dependency ends
- `Lag` - A numeric part of the lag value between the tasks. Negative values are supported. Please note, that any lag-related calculations will be performed
  using project calendar. Also only working time is counted as "lag" time.
- `LagUnit` - A duration unit part of the lag value between the tasks. Default value is "d" (days). Valid values are:
    - "ms" (milliseconds)
    - "s" (seconds)
    - "mi" (minutes)
    - "h" (hours)
    - "d" (days)
    - "w" (weeks)
    - "mo" (months)
    - "q" (quarters)
    - "y" (years)

- `Cls` - A CSS class that will be applied to each rendered dependency DOM element
- `Type` - An integer constant representing the type of the dependency:
    - 0 - start-to-start dependency
    - 1 - start-to-end dependency
    - 2 - end-to-start dependency
    - 3 - end-to-end dependency

Subclassing the Dependency class
--------------------

The name of any field can be customized in the subclass, see the example below. Please also refer to {@link Sch.model.Customizable} for details.

    Ext.define('MyProject.model.Dependency', {
        extend      : 'Gnt.model.Dependency',

        toField     : 'targetId',
        fromField   : 'sourceId',

        ...
    })

*/
Ext.define('Gnt.model.Dependency', {
    extend              : 'Sch.model.Dependency',

    customizableFields     : [
        { name: 'Lag', type : 'number', defaultValue : 0},
        {
            name            : 'LagUnit',
            type            : 'string',
            defaultValue    : "d",
            // make sure the default value is applied when user provides empty value for the field, like "" or null
            convert         : function (value) {
                return value || "d";
            }
        }
    ],

    /**
     * @cfg {String} lagField The name of the field that contains the lag amount.
     */
    lagField        : 'Lag',

    /**
     * @cfg {String} lagUnitField The name of the field that contains the lag unit duration.
     */
    lagUnitField    : 'LagUnit',

    isHighlighted   : false,

    getTaskStore : function() {
        return Ext.isFunction(this.store.getTaskStore) ? this.store.getTaskStore() : this.store.taskStore;
    },

    getEventStore : function() {
        return this.getTaskStore();
    },

    /**
     * Returns the source task of the dependency
     *
     * @return {Gnt.model.Task} The source task of this dependency
     */
    getSourceTask : function(taskStore) {
        return this.getSourceEvent(taskStore);
    },

    /**
     * Sets the source task of the dependency
     *
     * @param {Gnt.model.Task} task The new source task of this dependency
     */
    setSourceTask : function(task) {
        return this.setSourceEvent(task);
    },

    /**
     * Returns the target task of the dependency
     *
     * @return {Gnt.model.Task} The target task of this dependency
     */
    getTargetTask : function(taskStore) {
        return this.getTargetEvent(taskStore);
    },

    /**
     * Sets the target task of the dependency
     *
     * @param {Gnt.model.Task} task The new target task of this dependency
     */
    setTargetTask : function(task) {
        return this.setTargetEvent(task);
    },

    /**
     * @method getLag
     *
     * Returns the amount of lag for the dependency
     *
     * @return {Number} The amount of lag for the dependency
     */

    /**
     * @method setLag
     *
     * Sets the amount of lag for the dependency
     *
     * @param {Number} amount The amount of lag for the dependency
     * @param {String} [unit] Lag duration unit
     */
    setLag : function (amount, unit) {
        var me = this;

        me.beginEdit();
        me.set(me.lagField, amount);
        if (arguments.length > 1) {
            me.setLagUnit(unit);
        }
        me.endEdit();
    },

    /**
     * Returns the duration unit of the lag.
     *
     * @return {String} Lag duration unit
     */
    getLagUnit: function () {
        var me = this;

        return me.get(me.lagUnitField) || 'd';
    },

    /**
     * @method setLagUnit
     *
     * Updates the lag unit of the dependency.
     *
     * @param {String} unit Lag duration unit
     */

    /**
     * Returns `true` if the dependency is valid. Note, this method assumes that the model is part of a {@link Gnt.data.DependencyStore}.
     * Invalid dependencies are:
     * - a task linking to itself
     * - a dependency between a child and one of its parent
     * - transitive dependencies, e.g. if A -> B, B -> C, then A -> C is not valid
     *
     * @return {Boolean}
     */
    isValid : function (taskStore) {
        var me          = this,
            valid       = me.callParent(arguments),
            sourceId    = me.getSourceId(),
            targetId    = me.getTargetId(),
            type        = me.getType();

        if (valid && taskStore !== false && me.store) {
            valid = me.store.isValidDependency(sourceId, targetId, type, null, null, me);
        }

        return valid;
    }

});
