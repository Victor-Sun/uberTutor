/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Sch.model.Dependency
@extends Sch.model.Customizable

This class represents a single Dependency between two events. It is a subclass of the {@link Sch.model.Customizable}
class, which in its turn subclasses {@link Ext.data.Model}.
Please refer to documentation of those classes to become familar with the base interface of this class.

A Dependency has the following fields:

- `Id` - The id of the dependency itself
- `From` - The id of the event at which the dependency starts
- `To` - The id of the event at which the dependency ends
- `Cls` - A CSS class that will be applied to each rendered dependency DOM element
- `Type` - An integer constant representing the type of the dependency:
    - 0 - start-to-start dependency
    - 1 - start-to-end dependency
    - 2 - end-to-start dependency
    - 3 - end-to-end dependency
- `Bidirectional` - A boolean indicating if a dependency goes both directions (default false)

Subclassing the Dependency class
--------------------

The name of any field can be customized in the subclass, see the example below. Please also refer to {@link Sch.model.Customizable}
for details.

    Ext.define('MyProject.model.Dependency', {
        extend      : 'Sch.model.Dependency',

        toField     : 'targetId',
        fromField   : 'sourceId',

        ...
    })

*/
Ext.define('Sch.model.Dependency', {
    extend              : 'Sch.model.Customizable',

    requires            : [
        'Sch.model.Range'
    ],

    inheritableStatics  : {
        /**
         * @static
         * @property {Object} Type The enumerable object, containing names for the dependency types integer constants.
         */
        Type    : {
            StartToStart    : 0,
            StartToEnd      : 1,
            EndToStart      : 2,
            EndToEnd        : 3
        }
    },

    idProperty          : 'Id',

    customizableFields     : [

        // 3 mandatory fields
        { name: 'From' },
        { name: 'To' },
        { name: 'Type', type : 'int', defaultValue : 2},
        { name: 'Cls', defaultValue : ''},
        { name: 'Bidirectional', type : 'boolean'}
    ],

    /**
     * @cfg {String} fromField The name of the field that contains the id of the source event.
     */
    fromField       : 'From',

    /**
     * @cfg {String} toField The name of the field that contains the id of the target event.
     */
    toField         : 'To',

    /**
     * @cfg {String} typeField The name of the field that contains the dependency type.
     */
    typeField       : 'Type',

    /**
     * @cfg {String} clsField The name of the field that contains a CSS class that will be added to the rendered dependency elements.
     */
    clsField        : 'Cls',

    /**
     * @cfg {String} clsField The name of the boolean field that controls if arrows should be drawn at both start and end points.
     */
    bidirectionalField        : 'Bidirectional',

    constructor     : function(config) {
        var me = this;

        me.callParent(arguments);

        if (config) {
            // Allow passing in event instances too
            if (config[me.fromField] && config[me.fromField] instanceof Sch.model.Range) {
                me.setSourceEvent(config[me.fromField]);

                delete config.fromField;
            }

            if (config[me.toField] && config[me.toField] instanceof Sch.model.Range) {
                me.setTargetEvent(config[me.toField]);

                delete config.toField;
            }
        }
    },

    getEventStore : function() {
        return this.store.getEventStore();
    },

    /**
     * Returns the source event of the dependency
     *
     * @return {Sch.model.Event} The source event of this dependency
     */
    getSourceEvent : function(eventStore) {
        var me = this;
        return (eventStore || me.getEventStore()).getModelById(me.getSourceId());
    },

    /**
     * Sets the source event of the dependency
     *
     * @param {Sch.model.Event} event The new source event of this dependency
     */
    setSourceEvent : function(event) {
        this.setSourceId(event.getId());
    },

    /**
     * Returns the target event of the dependency
     *
     * @return {Sch.model.Event} The target event of this dependency
     */
    getTargetEvent : function(eventStore) {
        var me = this;
        return (eventStore || me.getEventStore()).getModelById(me.getTargetId());
    },

    /**
     * Sets the target event of the dependency
     *
     * @param {Sch.model.Event} event The new target event of this dependency
     */
    setTargetEvent : function(event) {
        this.setTargetId(event.getId());
    },

    /**
     * Returns the source event id of the dependency
     *
     * @return {Mixed} The id of the source event for the dependency
     *
     * @method getFrom
     */
    /**
     * Returns the source event id of the dependency
     *
     * @return {Mixed} The id of the source event for the dependency
     */
    getSourceId : function() { return this.getFrom(); },
    // TODO: remove commented if everything is ok
    /*
    getSourceId : function() {
        return this.get(this.fromField);
    },
    */

    /**
     * Sets the source event id of the dependency
     *
     * @param {Mixed} id The id of the source event for the dependency
     *
     * @method setFrom
     */
    /**
     * Sets the source event id of the dependency
     *
     * @param {Mixed} id The id of the source event for the dependency
     */
    setSourceId : function(id) { return this.setFrom(id); },
    // TODO: remove commented if everything is ok
    /*
    setSourceId : function(id) {
        var me = this;
        me.set(me.fromField, id);
    },
    */

    /**
     * Returns the target event id of the dependency
     *
     * @return {Mixed} The id of the target event for the dependency
     *
     * @method getTo
     */
    /**
     * Returns the target event id of the dependency
     *
     * @return {Mixed} The id of the target event for the dependency
     */
    getTargetId : function() { return this.getTo(); },
    // TODO: remove commented if everything is ok
    /*
    getTargetId : function() {
        var me = this;
        return me.get(me.toField);
    },
    */

    /**
     * Sets the target event id of the dependency
     *
     * @param {Mixed} id The id of the target event for the dependency
     *
     * @method setTo
     */
    /**
     * Sets the target event id of the dependency
     *
     * @param {Mixed} id The id of the target event for the dependency
     */
    setTargetId : function(id) { return this.setTo(id); },
    // TODO: remove commented if everything is ok
    /*
    setTargetId : function(id) {
        var me = this;
        me.set(me.toField, id);
    },
    */

    /**
     * @method getType
     *
     * Returns the dependency type
     * @return {Mixed} The type of the dependency
     */

    /**
     * @method setType
     *
     * Sets the dependency type
     * @param {Mixed} type The type of the dependency
     */

    /**
     * @method getCls
     *
     * Returns the name of field holding the CSS class for each rendered dependency element
     *
     * @return {String} The cls field
     */

    /**
     * Returns true if the linked events have been persisted (e.g. neither of them are 'phantoms')
     *
     * @return {Boolean} true if this model can be persisted to server.
     */
    isPersistable : function() {
        var me = this,
            source = me.getSourceEvent(),
            target = me.getTargetEvent();

        return source && !source.phantom && target && !target.phantom;
    },

    /**
     * Returns `true` if the dependency is valid. Has valid type and both source and target ids set and not links to itself.
     *
     * @return {Boolean}
     */
    isValid : function (taskStore) {
        var me          = this,
            valid       = me.callParent(arguments),
            sourceId    = me.getSourceId(),
            targetId    = me.getTargetId(),
            type        = me.getType();

        return Ext.isNumber(type) && !Ext.isEmpty(sourceId) && !Ext.isEmpty(targetId) && sourceId != targetId;
    },

    getDateRange : function() {
        var sourceTask = this.getSourceEvent();
        var targetTask = this.getTargetEvent();

        if (sourceTask && targetTask && sourceTask.isScheduled() && targetTask.isScheduled()) {
            var Type = this.self.Type;
            var sourceDate, targetDate;

            switch(this.getType()) {
                case Type.StartToStart:
                    sourceDate = sourceTask.getStartDate();
                    targetDate = targetTask.getStartDate();
                    break;

                case Type.StartToEnd:
                    sourceDate = sourceTask.getStartDate();
                    targetDate = targetTask.getEndDate();
                    break;

                case Type.EndToEnd:
                    sourceDate = sourceTask.getEndDate();
                    targetDate = targetTask.getEndDate();
                    break;

                case Type.EndToStart:
                    sourceDate = sourceTask.getEndDate();
                    targetDate = targetTask.getStartDate();
                    break;
            }

            return {
                start : Sch.util.Date.min(sourceDate, targetDate),
                end   : Sch.util.Date.max(sourceDate, targetDate)
            };
        }

        return null;
    }
});
