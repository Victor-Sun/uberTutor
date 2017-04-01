/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * Dependency store event->dependencies cache.
 * Uses event records or event record ids as keys.
 *
 * The cache uses 3 keys for each event:
 * - {EventId} - contains both successors and predecessors
 * - {EventId}-succ - contains successors only
 * - {EventId}-pred - contains predecessors only
 *
 * @private
 */
Ext.define('Sch.data.util.EventDependencyCache', {

    extend                  : 'Sch.util.Cache',

    dependencyStore         : null,
    dependencyStoreDetacher : null,
    eventStoreDetacher      : null,

    constructor : function(dependencyStore) {
        var me = this,
            eventStore = dependencyStore.getEventStore();

        function onDependencyAdd(store, dependencies) {
            Ext.Array.each(dependencies, function(dependency) {
                var sourceId = dependency.getSourceId(),
                    targetId = dependency.getTargetId();

                if (sourceId) {
                    me.add(sourceId, dependency);
                }

                if (targetId) {
                    me.add(targetId, dependency);
                }

                if (sourceId && targetId) {
                    me.addSuccessor(sourceId, dependency);
                    me.addPredecessor(targetId, dependency);
                }
            });
        }

        function onDependencyRemove(store, dependencies) {
            Ext.Array.each(dependencies, function(dependency) {
                var sourceId = dependency.getSourceId(),
                    targetId = dependency.getTargetId();

                if (sourceId) {
                    me.remove(sourceId, dependency);
                    me.removeSuccessor(sourceId, dependency);
                }

                if (targetId) {
                    me.remove(targetId, dependency);
                    me.removePredecessor(targetId, dependency);
                }
            });
        }

        function onDependencyUpdate(store, dependency, operation) {
            var sourceIdField    = dependency.fromField,
                targetIdField    = dependency.toField,
                sourceIdChanged  = dependency.previous && sourceIdField in dependency.previous,
                targetIdChanged  = dependency.previous && targetIdField in dependency.previous,
                previousSourceId = sourceIdChanged && dependency.previous[sourceIdField],
                previousTargetId = targetIdChanged && dependency.previous[targetIdField];

            if (sourceIdChanged) {
                me.move(previousSourceId, dependency.getSourceId(), dependency);
                me.moveSuccessors(previousSourceId, dependency.getSourceId(), dependency);
            }

            if (targetIdChanged) {
                me.move(previousTargetId, dependency.getTargetId(), dependency);
                me.movePredecessors(previousTargetId, dependency.getTargetId(), dependency);
            }
        }

        function onDependencyStoreClearOrReset(store) {
            me.clear();
        }

        function onDependencyStoreEventStoreChange(store, eventStore) {
            me.clear();
            attachToEventStore(eventStore);
        }

        function onEventIdChanged(eventStore, event, oldId, newId) {
            me.move(oldId, newId);
            me.moveSuccessors(oldId, newId);
            me.movePredecessors(oldId, newId);
        }

        function onEventRemove(eventStore, events) {
            Ext.Array.each(events, function(event) {
                me.clear(event);
                me.clearSuccessors(event);
                me.clearPredecessors(event);
            });
        }

        function onEventStoreClearOrReset() {
            me.clear();
        }

        function attachToEventStore(store) {
            Ext.destroy(me.eventStoreDetacher);
            me.eventStoreDetacher = store && store.on({
                'idchanged'      : onEventIdChanged,
                'remove'         : onEventRemove,
                'cacheresethint' : onEventStoreClearOrReset,
                'clear'          : onEventStoreClearOrReset,
                'rootchange'     : onEventStoreClearOrReset,
                // subscribing to the CRUD using priority - should guarantee that our listeners
                // will be called first (before any other listeners, that could be provided in the "listeners" config)
                // and state in other listeners will be correct
                priority        : 100,
                destroyable     : true
            });
        }

        me.dependencyStoreDetacher = dependencyStore.on({
            'add'              : onDependencyAdd,
            'remove'           : onDependencyRemove,
            'update'           : onDependencyUpdate,
            'cacheresethint'   : onDependencyStoreClearOrReset,
            'clear'            : onDependencyStoreClearOrReset,
            'eventstorechange' : onDependencyStoreEventStoreChange,
            // subscribing to the CRUD using priority - should guarantee that our listeners
            // will be called first (before any other listeners, that could be provided in the "listeners" config)
            // and state in other listeners will be correct
            priority           : 100,
            destroyable        : true
        });

        me.dependencyStoreFiltersDetacher = dependencyStore.getFilters().on({
            endupdate : onDependencyStoreClearOrReset,
            // priority is calculated as:
            // Ext.util.Collection.$endUpdatePriority + 1
            // to reset our cache before ExtJS "on filter end update" listeners run
            priority    : 1002,
            destroyable : true
        });

        attachToEventStore(eventStore);

        me.dependencyStore = dependencyStore;
    },

    destroy : function() {
        var me = this;
        Ext.destroyMembers(
            me,
            'dependencyStoreDetacher',
            'eventStoreDetacher'
        );
        me.dependencyStore = null;
    },

    get : function(k, fn) {
        var me = this,
            idtype,
            id, type;

        if (!fn) {
            k = me.key(k);

            idtype  = me.self.splitKey(k);
            id      = idtype.id;
            type    = idtype.type;

            switch (type) {
                case 'pred':
                    fn = function() {
                        return Ext.Array.filter(me.get(id), function(dependency) {
                            return dependency.getTargetId() == id;
                        });
                    };
                    break;
                case 'succ':
                    fn = function() {
                        return Ext.Array.filter(me.get(id), function(dependency) {
                            return dependency.getSourceId() == id;
                        });
                    };
                    break;
                default:
                    fn = function() {
                        return Ext.Array.filter(me.dependencyStore.getRange(), function(dependency) {
                            return dependency.getTargetId() == id || dependency.getSourceId() == id;
                        });
                    };
            }
        }

        return me.callParent([k, fn]);
    },

    getSuccessors : function(k) {
        var me = this;
        return me.get(me.self.makeSuccessorsKey(me.key(k)));
    },

    getPredecessors : function(k) {
        var me = this;
        return me.get(me.self.makePredecessorsKey(me.key(k)));
    },

    addSuccessor : function(k, v) {
        var me = this;
        return me.add(me.self.makeSuccessorsKey(me.key(k)), v);
    },

    addPredecessor : function(k, v) {
        var me = this;
        return me.add(me.self.makePredecessorsKey(me.key(k)), v);
    },

    removeSuccessor : function(k, v) {
        var me = this;
        return me.remove(me.self.makeSuccessorsKey(me.key(k)), v);
    },

    removePredecessor : function(k, v) {
        var me = this;
        return me.remove(me.self.makePredecessorsKey(me.key(k)), v);
    },

    moveSuccessors : function(oldKey, newKey, v) {
        var me = this;
        return arguments.length >= 3 ?
            me.move(me.self.makeSuccessorsKey(me.key(oldKey)), me.self.makeSuccessorsKey(me.key(newKey)), v) :
            me.move(me.self.makeSuccessorsKey(me.key(oldKey)), me.self.makeSuccessorsKey(me.key(newKey)));
    },

    movePredecessors : function(oldKey, newKey, v) {
        var me = this;
        return arguments.length >= 3 ?
            me.move(me.self.makePredecessorsKey(me.key(oldKey)), me.self.makePredecessorsKey(me.key(newKey)), v) :
            me.move(me.self.makePredecessorsKey(me.key(oldKey)), me.self.makePredecessorsKey(me.key(newKey)));
    },

    clearSuccessors : function(k) {
        var me = this;
        return me.clear(me.self.makeSuccessorsKey(me.key(k)));
    },

    clearPredecessors : function(k) {
        var me = this;
        return me.clear(me.self.makePredecessorsKey(me.key(k)));
    },

    inheritableStatics : {
        splitKey : function(k) {
            k = k.split('@#!#@');
            return {
                id   : k[0],
                type : k.length && k[1] || false
            };
        },

        makeSuccessorsKey : function(k) {
            return k + '@#!#@succ';
        },

        makePredecessorsKey : function(k) {
            return k + '@#!#@pred';
        }
    }
});
