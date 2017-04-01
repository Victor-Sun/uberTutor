/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * This mixin is a helper for Gantt panel. It adds Dependency view instance management methods.
 */
Ext.define('Gnt.view.dependency.Mixin', {
    extend : 'Sch.view.dependency.Mixin',

    uses   : [
        'Gnt.view.dependency.View',
        'Gnt.feature.DependencyDragDrop'
    ],

    /**
     * @cfg {Object} dependencyViewConfig
     *
     * A config object to apply to internal instance of the {@link Gnt.view.Dependency}.
     *
     * Support two deprecated since v.4.2 properties:
     *  - dragZoneConfig
     *  - dropZoneConfig
     * please use:
     *  - dependencyDragZoneConfig
     *  - dependencyDropZoneConfig
     * instead
     */
    dependencyViewConfig : null,
    /**
     * @cfg {Boolean} enableDependencyDragDrop
     *
     * `True` to allow creation of dependencies by using drag and drop between task terminals (defaults to `true`)
     */
    enableDependencyDragDrop : true,
    /**
     * @cfg {Object} dragZoneConfig
     *
     * A custom config object to pass on to configure the Ext.dd.DragZone instance used when creating
     * new dependencies
     */
    dependencyDragZoneConfig : null,
    /**
     * @cfg {Object} dropZoneConfig
     *
     * A custom config object to pass on to configure the Ext.dd.DropZone instance used when creating
     * new dependencies
     */
    dependencyDropZoneConfig : null,

    // private
    dependencyDnd : null,
    dependencyViewDeprecatedRelayDetacher : null,

    destroy : function() {
        var me = this;

        Ext.destroyMembers(
            me,
            'dependencyDnd',
            'dependencyViewDeprecatedRelayDetacher'
        );

        me.callParent();
    },

    // TODO: remove this method after 4.2 when scheduling view `dependencyViewConfig` is finally unsupported
    getDependencyViewConfig : function() {
        var me = this;

        return Ext.applyIf(me.callParent() || me.getSchedulingView().dependencyViewConfig || {}, {
            drawDependencies : me.getSchedulingView().drawDependencies !== undefined ? me.getSchedulingView().drawDependencies : true
        });
    },

    // TODO: simplify this method after Gnt.view.dependency.dragZoneConfig is finialy removed
    getDependencyDragZoneConfig : function() {
        var me = this,
            result = me.dependencyDragZoneConfig,
            depViewConfig;

        if (!result) {
            depViewConfig = me.getDependencyViewConfig();
            if (depViewConfig) {
                result = depViewConfig.dragZoneConfig || null;
            }
        }

        return result;
    },

    // TODO: simplify this method after Gnt.view.dependency.dropZoneConfig is finally removed
    getDependencyDropZoneConfig : function() {
        var me = this,
            result = me.dependencyDragZoneConfig,
            depViewConfig;

        if (!result) {
            depViewConfig = me.getDependencyViewConfig();
            if (depViewConfig) {
                result = depViewConfig.dropZoneConfig || null;
            }
        }

        return result;
    },

    createDependencyView : function(config, primaryView) {
        return Sch.view.dependency.View.create(Ext.apply({}, config, { primaryView : primaryView, type : 'basegantt' }));
    },

    setupDependencyView : function(config, primaryView) {
        var me = this;

        me.callParent([config, primaryView]);

        if (me.enableDependencyDragDrop) {
            me.dependencyDnd = Ext.create('Gnt.feature.DependencyDragDrop', {
                el              : primaryView.getEl(),
                rtl             : primaryView.rtl,
                ganttView       : primaryView,
                dragZoneConfig  : me.getDependencyDragZoneConfig(),
                dropZoneConfig  : me.getDependencyDropZoneConfig(),
                dependencyStore : me.getDependencyStore(),
                listeners       : {
                    'beforednd' : me.onBeforeDependencyDrag,
                    'dndstart'  : me.onDependencyDragStart,
                    'drop'      : me.onDependencyDrop,
                    'afterdnd'  : me.onAfterDependencyDragDrop,
                    scope  : me
                }
            });
        }
    },

    // TODO: remove this method after 4.4 when dependency related events of Gnt.view.Gantt are finally unsupported
    setupDependencyViewRelay : function() {
        var me = this;

        me.callParent();

        Ext.destroy(me.dependencyViewDeprecatedRelayDetacher);

        me.dependencyViewDeprecatedRelayDetacher = me.getSchedulingView().relayEvents(me.getDependencyView(), [
            'dependencyclick',
            'dependencydblclick',
            'dependencycontextmenu'
        ]);
    },

    onBeforeDependencyDrag : function (dm, sourceTask) {
        var me = this,
            myResult, schedViewResult, depViewResult;

        // TODO: we do way too much relaying, it should be simplified, firing from dependency view should be enough.
        myResult      = me.fireEvent('beforedependencydrag', me, sourceTask);
        depViewResult = me.getDependencyView().fireEvent('beforednd', me.getDependencyView(), sourceTask);
        // TODO: remove firing from scheduling view after 4.4 when dependency related events are finally unsupported
        //       on the scheduling view
        schedViewResult = me.getSchedulingView().fireEvent('beforedependencydrag', me.getSchedulingView(), sourceTask);

        return myResult !== false && depViewResult !== false && schedViewResult !== false;
    },

    onDependencyDragStart : function (dm) {
        var me = this;

        // TODO: we do way too much relaying, it should be simplified, firing from dependency view should be enough.
        me.fireEvent('dependencydragstart', this);
        me.getDependencyView().fireEvent('dndstart', me.getDependencyView());
        // TODO: remove firing from scheduling view after 4.4 when dependency related events are finally unsupported
        //       on the scheduling view
        me.getSchedulingView().fireEvent('dependencydragstart', me.getSchedulingView());

        if (me.tip) {
            me.tip.disable();
        }

        me.preventOverCls = true;
    },

    onDependencyDrop : function (dm, fromId, toId, type) {
        var me = this,
            taskStore = me.getTaskStore(),
            fromTask = taskStore.getModelById(fromId),
            toTask   = taskStore.getModelById(toId);

        fromTask && fromTask.linkTo(toId, type);

        // TODO: we do way too much relaying, it should be simplified, firing from dependency view should be enough.
        me.fireEvent('dependencydrop', me, fromTask, toTask, type);
        me.getDependencyView().fireEvent('drop', me.getDependencyView(), fromTask, toTask, type);
        // TODO: remove firing from scheduling view after 4.4 when dependency related events are finally unsupported
        //       on the scheduling view
        me.getSchedulingView().fireEvent('dependencydrop', me.getSchedulingView(), fromTask, toTask, type);
    },

    onAfterDependencyDragDrop : function () {
        var me = this;

        // TODO: we do way too much relaying, it should be simplified, firing from dependency view should be enough.
        me.fireEvent('afterdependencydragdrop', me);
        me.getDependencyView().fireEvent('afterdnd', me.getDependencyView());
        // TODO: remove firing from scheduling view after 4.4 when dependency related events are finally unsupported
        //       on the scheduling view
        me.getSchedulingView().fireEvent('afterdependencydragdrop', me.getSchedulingView());

        // Enable tooltip after drag again
        if (me.tip) {
            me.tip.enable();
        }

        me.preventOverCls = false;
    }

    /**
     * @event beforedependencydrag
     *
     * Fires before a dependency drag operation starts (from a "task terminal"). Return false to prevent this operation
     * from starting.
     *
     * @param {Gnt.panel.Gantt} gantt The gantt view instance
     * @param {Gnt.model.Task} taskRecord The source task record
     */

    /**
     * @event dependencydragstart
     *
     * Fires when a dependency drag operation starts
     *
     * @param {Gnt.panel.Gantt} gantt The gantt view instance
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
     */

    /**
     * @event afterdependencydragdrop
     *
     * Always fires after a dependency drag-drop operation
     *
     * @param {Gnt.panel.Gantt} gantt The gantt view instance
     */
});
