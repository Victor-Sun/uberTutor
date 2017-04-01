/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * Dependency view is the utilitary view working alongside the primary `item` view of an Ext.Component, the only
 * requirement for the primary `item` view is to support the following interface:
 *
 *  - isItemCanvasAvailable([layer : Number]) : Boolean
 *  - getItemCanvas(layer : Number, canvasSpec : Object) : Ext.dom.Element
 *  - getItemBox(itemModel) : Object/Object[] {top : Number, bottom : Number, start : Number, end : Number, rendered : Boolean}
 *  - getStartConnectorSide(itemModel) : ['top'/'bottom'/'left'/'right']
 *  - getEndConnectorSide(itemModel) : ['top'/'bottom'/'left'/'right']
 */
Ext.define('Sch.view.dependency.View', {

    alias : 'schdependencyview.base',

    mixins : [
        'Ext.mixin.Factoryable',
        'Ext.mixin.Observable'
    ],

    uses : [
        'Ext.data.StoreManager',
        'Ext.Array',
        'Ext.dom.CompositeElementLite',
        'Sch.view.dependency.Painter'
    ],

    config : {
        /**
         * @cfg {Sch.view.SchedulerGridView} primaryView (required)
         *
         * Primary view instance
         */
        primaryView   : null,
        /**
         * @cfg {Sch.data.EventStore|String} dependencyStore
         *
         * Dependency store this view will work with, if none given then dependency store will be taken from the primary view.
         */
        dependencyStore : null,
        /**
         * @cfg {Boolean} drawDependencies
         *
         * Set to false to turn dependency drawing off
         *
         * @private
         */
        drawDependencies : true,
        /**
         * @cfg {Object} painterConfig
         *
         * Dependency painter instance config
         */
        painterConfig : {
            canvasCls : 'sch-dependencyview-ct'
        },
        /**
         * @cfg {Number} canvasLayer
         *
         * Dependency canvas layer position (i.e. z-index)
         *
         * @private
         */
        canvasLayer : 0,

        /**
         *
         * @cfg {String} overCls
         *
         * The CSS class to add to a dependency line when hovering over it
         */
        overCls : null
    },

    // private
    renderTimer                 : null,
    painter                     : null,
    primaryViewDetacher         : null,
    primaryViewLockableDetacher : null,
    primaryViewElDetacher       : null,
    dependencyStoreDetacher     : null,

    constructor : function(config) {
        var me = this;

        // Just in case
        me.callParent([config]);

        // Initializing observable mixin
        me.mixins.observable.constructor.call(me, config);

        // Since we do not inherit from the Ext.Component the call to `initConfig` is required
        me.initConfig(config);

        // <debug>
        Ext.Assert && Ext.Assert.isObject(
            me.getPrimaryView(),
            'Dependency view requires a primary view to be configured in'
        );
        Ext.Assert && Ext.Assert.isFunctionProp(
            me.getPrimaryView(),
            'isItemCanvasAvailable',
            'Dependency view requires `Sch.mixin.GridViewCanvas` mixin to be mixed into scheduling view, or the corresponding interface to be implemented'
        );
        Ext.Assert && Ext.Assert.isFunctionProp(
            me.getPrimaryView(),
            'getItemCanvasEl',
            'Dependency view requires `Sch.mixin.GridViewCanvas` mixin to be mixed into scheduling view, or the corresponding interface to be implemented'
        );
        // </debug>

        me.painter = me.createPainter(Ext.apply({}, { rtl : me.getPrimaryView().rtl }, me.getPainterConfig()));

        if (me.canDrawDependencies()) {
            me.startDrawDependencies();
        }
    },

    destroy : function() {
        this.stopDrawDependencies();
        if (Ext.isNumber(this.renderTimer)) {
            clearTimeout(this.renderTimer);
        }
    },

    destroyDetachers : function() {
        var me = this;

        Ext.destroyMembers(this, [
            'primaryViewDetacher',
            'primaryViewLockableDetacher',
            'primaryViewElDetacher',
            'dependencyStoreDetacher'
        ]);
    },

    destroyDependencyCanvas : function() {
        var me = this;

        if (me.isDependencyCanvasPresent()) {
            Ext.destroy(me.getDependencyCanvas());
        }
    },

    /**
     * Checks if the view is ready to draw dependencies
     *
     * @param {Boolean} ignoreDrawDependencies
     * @param {Boolean} ignoreDependencyCanvas
     * @param {Boolean} ignoreDependencyStore
     *
     * @return {Boolean}
     */
    canDrawDependencies : function(ignoreDrawDependencies, ignoreDependencyCanvas, ignoreDependencyStore) {
        var me = this;

        return !!(
            me.painter &&
            (ignoreDrawDependencies || me.getDrawDependencies()) &&
            (ignoreDependencyCanvas || me.isDependencyCanvasAvailable()) &&
            (ignoreDependencyStore  || me.getDependencyStore())
        );
    },

    startDrawDependencies : function() {
        var me = this,
            primaryView = me.getPrimaryView(),
            dependencyStore = me.getDependencyStore(),
            lockableView;

        me.primaryViewDetacher = primaryView.on(
            Ext.applyIf({
                destroyable : true
            }, me.getPrimaryViewListeners())
        );

        me.primaryViewElDetacher = primaryView.getEl().on(
            Ext.applyIf({
                destroyable : true
            }, me.getPrimaryViewElListeners())
        );

        // WARNING: view.grid and view.grid.ownerLockable are private properties
        lockableView = primaryView.grid.ownerLockable && primaryView.grid.ownerLockable.getView();
        if (primaryView != lockableView) {
            me.primaryViewLockableDetacher = lockableView.on(
                Ext.applyIf({
                    destroyable : true
                }, me.getPrimaryViewLockableListeners())
            );
        }

        me.dependencyStoreDetacher = dependencyStore.on(
            Ext.applyIf({
                destroyable : true
            }, me.getDependencyStoreListeners())
        );

        me.scheduleAllDependenciesRendering();
    },

    stopDrawDependencies : function() {
        var me = this;

        me.destroyDetachers();
        me.destroyDependencyCanvas();
    },

    updatePrimaryView : function(newView, oldView) {
        var me = this,
            lockableView;

        if (oldView) {
            me.stopDrawDependencies();
        }

        if (newView) {

            if (!me.getDependencyStore() && newView.getEventStore()) {
                me.setDependencyStore(newView.getEventStore().getDependencyStore());
            }

            if (me.canDrawDependencies()) {
                me.startDrawDependencies();
            }
        }
    },

    applyDependencyStore : function(store) {
        return store && Ext.StoreMgr.lookup(store);
    },

    updateDependencyStore : function(newStore, oldStore) {
        var me = this;

        if (oldStore) {
            me.stopDrawDependencies();
        }

        if (newStore && me.canDrawDependencies()) {
            me.startDrawDependencies();
        }
    },

    updateDrawDependencies : function(newValue, oldValue) {
        var me = this;

        if (newValue && me.canDrawDependencies(true)) {
            me.startDrawDependencies();
        }
        else if (!newValue) {
            me.stopDrawDependencies();
        }
    },

    applyPainterConfig : function(config) {
        // Do not share single config instance among several dependency canvas instances
        return Ext.isObject(config) ? Ext.apply({}, config) : config;
    },

    updatePainterConfig : function(config) {
        var me = this;

        if (me.painter) {

            if (config) {
                config = Ext.apply({}, { rtl : me.getPrimaryView().rtl }, config);
            }

            // In this case we are to re-create painter instance since painter type is changed
            if (config && 'type' in config && config.type !== me.painter.type) {

                Ext.destroy(me.painter);
                me.painter = me.createPainter(config);
            }
            // In this case we just update current painter configuration
            else {
                me.painter.setConfig(config);
            }

            // New config might provide as with new canvas specification, thus the current one should be
            // destroyed if any
            me.destroyDependencyCanvas();

            if (me.canDrawDependencies()) {
                me.scheduleAllDependenciesRendering();
            }
        }
    },

    updateCanvasLayer : function(layer) {
        var me = this;

        me.destroyDependencyCanvas();

        if (me.canDrawDependencies()) {

            me.scheduleAllDependenciesRendering();
        }
    },

    /**
     * Gets scheduling view listeners object
     *
     * @return {Object}
     * @protected
     */
    getPrimaryViewListeners : function() {
        var me = this;

        return {
            'itemadd'         : me.scheduleAllDependenciesRendering,
            'itemremove'      : me.scheduleAllDependenciesRendering,
            'itemupdate'      : me.onPrimaryViewItemUpdate,
            'refresh'         : me.scheduleAllDependenciesRendering,
            'bufferedrefresh' : me.scheduleAllDependenciesRendering,
            // Row expander plugin events
            'expandbody'      : me.scheduleAllDependenciesRendering,
            'collapsebody'    : me.scheduleAllDependenciesRendering,
            // Bryntum custom event
            'eventrepaint'    : me.scheduleAllDependenciesRendering,
            scope             : me
        };
    },

    /**
     * If scheduling view is part of the lockable grid then gets top level lockable view listeners object
     *
     * @return {Object}
     * @protected
     */
    getPrimaryViewLockableListeners : function() {
        var me = this;

        return {
            // Row expander plugin events
            'expandbody'      : me.scheduleAllDependenciesRendering,
            'collapsebody'    : me.scheduleAllDependenciesRendering,
            scope             : me
        };
    },

    /**
     * Get's primary view element listeners
     *
     * @return {Object}
     * @protected
     */
    getPrimaryViewElListeners : function() {
        var me = this;

        return {
            'dblclick'    : me.onPrimaryViewDependencyElPointerEvent,
            'click'       : me.onPrimaryViewDependencyElPointerEvent,
            'contextmenu' : me.onPrimaryViewDependencyElPointerEvent,
            'mouseover'   : me.onPrimaryViewDependencyElPointerEvent,
            'mouseout'    : me.onPrimaryViewDependencyElPointerEvent,

            delegate      : '.sch-dependency',
            scope         : me
        };
    },

    /**
     * Gets dependency store listeners
     *
     * @return {Object}
     * @protected
     */
    getDependencyStoreListeners : function() {
        var me = this;

        return {
            'add'     : me.onDependencyStoreAdd,
            'remove'  : me.onDependencyStoreRemove,
            'update'  : me.onDependencyStoreUpdate,
            'refresh' : me.onDependencyStoreRefresh,
            'clear'   : me.onDependencyStoreClear,
            scope     : me
        };
    },

    /**
     * Creates painter class instance
     *
     * @param {Object} config Painter config
     * @return {Sch.view.dependency.Painter}
     * @protected
     */
    createPainter : function(config) {
        return Sch.view.dependency.Painter.create(config);
    },

    /**
     * Clones currently using dependency painter
     *
     * @return {Sch.view.dependency.Painter/Null}
     * @public
     */
    clonePainter : function() {
        var me = this;
        return me.painter && me.painter.clone() || null;
    },

    /**
     * Checks if dependency canvas is available for drawing
     *
     * @return {Boolean}
     * @protected
     */
    isDependencyCanvasAvailable : function() {
        var primaryView = this.getPrimaryView();

        return primaryView && primaryView.isItemCanvasAvailable();
    },

    /**
     * Checks if dependency canvas is present in the DOM
     *
     * @return {Boolean}
     *
     * @protected
     */
    isDependencyCanvasPresent : function() {
        var me = this,
            primaryView = me.getPrimaryView();

        return primaryView && primaryView.isItemCanvasAvailable(me.getCanvasLayer());
    },

    /**
     * Returns dependency canvas element
     *
     * @reutrn {Ext.dom.Element}
     * @protected
     */
    getDependencyCanvas : function() {
        var me = this;

        return me.getPrimaryView().getItemCanvasEl(me.getCanvasLayer(), me.painter.getCanvasSpecification());
    },

    /**
     * Checks if dependency rendering has been scheduled
     *
     * @return {Boolean}
     * @protected
     */
    isDependencyRenderingScheduled : function() {
        return !!this.renderTimer;
    },

    /**
     * Schedules full dependency redrawing
     *
     * @protected
     */
    scheduleAllDependenciesRendering : function() {
        var me = this,
            hiddenParent;

        if (!me.isDependencyRenderingScheduled() && me.canDrawDependencies()) {
            me.renderTimer = setTimeout(function() {
                me.renderTimer = null;
                me.renderAllDependencies();
            });
        }
    },

    /**
     * Renders all the dependencies for the current view
     */
    renderAllDependencies : function() {
        var me = this,
            containerEl,
            hiddenParent = me.getPrimaryView().up("{isHidden()}");

        // If a parent container is hidden, stop and wait for it to become visible
        if (hiddenParent) {
            // just for isDependencyRenderingScheduled() returned correct state, in this case it's scheduled
            // but not on timer but on parent `show` event.
            me.renderTimer = true;
            this.mon(hiddenParent, 'show', function() {
                me.renderTimer = null;
                me.renderAllDependencies();
            }, null, { single : true });
        }
        // If parent container is visible, as well as we are visible then scheduling call to renderAllDependencies
        else if (me.canDrawDependencies() && me.fireEvent('beforerefresh', me) !== false) {

            containerEl = me.getDependencyCanvas();

            me.renderDependencies(me.getDependenciesToRender(), true);

            me.fireEvent('refresh', me);
        }
    },

    getDependenciesToRender : function() {
        return this.getDependencyStore().getRange();
    },

    /**
     * Renders dependencies for given dependency records
     *
     * @param {Sch.model.Dependency|Sch.model.Dependency[]} dependencyRecords
     * @param {Boolean} [overwrite=false]
     */
    renderDependencies : function(dependencyRecords, overwrite) {
        var me       = this;

        if (me.canDrawDependencies()) {

            me.painter.paint(me.getPrimaryView(), me.getDependencyCanvas(), dependencyRecords || [], overwrite);
        }
    },

    /**
     * Re-renders dependencies for given dependency records
     *
     * @param {Sch.model.Dependency|Sch.model.Dependency[]} dependencyRecords
     */
    updateDependencies : function(dependencyRecords) {
        var me = this;

        // Clearing previous dependency elements
        me.clearDependencies(dependencyRecords);
        // Appending new dependency elements keeping everything else intact
        me.renderDependencies(dependencyRecords, false);
    },

    /**
     * Clears dependencies for given dependency records
     *
     * @param {Sch.model.Dependency|Sch.model.Dependency[]} dependencyRecords
     */
    clearDependencies : function(dependencyRecords) {
        var me = this;

        if (me.canDrawDependencies() && me.isDependencyCanvasPresent()) {
            Ext.destroy(me.getElementsForDependency(dependencyRecords));
        }
    },

    /**
     * Removes all drawn dependencies elements from the canvas
     */
    clearAllDependencies : function() {
        this.renderDependencies([], true); // overwrite with nothing is the same is clear
    },

    /**
     * Retrieve the elements representing a particular dependency or dependencies
     *
     * @param {Sch.model.Dependency/Sch.model.Dependency[]} dependencyRecords dependency record(s)
     * @return {Ext.CompositeElementLite/Ext.CompositeElement/false}
     */
    getElementsForDependency : function(dependencyRecords) {
        var me = this,
            result = false;

        if (me.canDrawDependencies() && me.isDependencyCanvasPresent()) {
            result = me.painter.getElementsForDependency(me.getDependencyCanvas(), dependencyRecords);
        }

        return result;
    },

    /**
     * Returns dependency record corresponding to the given element
     *
     * @param {HTMLElement/Ext.dom.Element/String} el
     * @return {Sch.model.Dependency/Null}
     */
    getDependencyForElement : function(el) {
        var me         = this,
            depStore   = me.getDependencyStore(),
            dependency = null,
            depInternalId;

        if (depStore) {
            depInternalId = me.painter.getElementDependencyInternalId(el);
            dependency    = depStore.getByInternalId(depInternalId);
        }

        return dependency;
    },

    /**
     * Returns all the elements representing the rendered dependencies
     *
     * @return {Ext.CompositeElementLite/Ext.CompositeElement/false}
     */
    getDependencyElements : function() {
        var me = this,
            result = false,
            canvasDom;

        if (me.canDrawDependencies() && me.isDependencyCanvasPresent()) {
            result = me.painter.getDependencyElements(me.getDependencyCanvas());
        }

        return result;
    },

    /**
     * Highlight the elements representing a dependency
     *
     * @param {Mixed} record Either the id of a record or a record in the dependency store
     * @param {String} [cls] The CSS class to use for highlighting. Defaults to the selected-state CSS class.
     */
    highlightDependency : function(record, cls) {
        var me = this;

        if (me.canDrawDependencies() && me.isDependencyCanvasPresent()) {

            if (!(record instanceof Ext.data.Model)) {
                record = me.getDependencyStore().getById(record);
            }

            if (record) {
                me.painter.highlightDependency(me.getDependencyCanvas(), record, cls);
                // NOTE: It's not view's task to do this, but kept it for backward compatibility
                //       MVC way would be to add 'isHighlighted' non persistent field to the dependency model
                record.isHighlighted = true;
            }
        }
    },

    /**
     * Remove highlight of the elements representing a particular dependency
     *
     * @param {Mixed} record Either the id of a record or a record in the dependency store
     * @param {String} [cls] The CSS class to use for highlighting. Defaults to the selected-state CSS class.
     */
    unhighlightDependency : function(record, cls) {
        var me = this;

        if (me.canDrawDependencies() && me.isDependencyCanvasPresent()) {

            if (!(record instanceof Ext.data.Model)) {
                record = me.getDependencyStore().getById(record);
            }

            if (record) {
                me.painter.unhighlightDependency(me.getDependencyCanvas(), record, cls);
                // NOTE: It's not view's task to do that, but I've kept it for backward compatibility
                //       MVC way would be to add 'isHighlighted' non persistent field to the dependency model
                record.isHighlighted = false;
            }
        }
    },

    /**
     * Gets selected (highlighted) dependency elements
     *
     * @return {Ext.CompositeElementLite/Ext.CompositeElement/false}
     */
    getSelectedDependencyElements : function() {
        var me = this,
            result = false;

        if (me.canDrawDependencies() && me.isDependencyCanvasPresent()) {
            result = me.painter.getSelectedDependencyElements(me.getDependencyCanvas());
        }

        return result;
    },

    /**
     * Remove highlighting from any dependency line currently highlighted
     */
    clearSelectedDependencies : function() {
        var me = this;

        if (me.canDrawDependencies() && me.isDependencyCanvasPresent()) {

            me.painter.clearSelectedDependencies(me.getDependencyCanvas());

            // NOTE: It's not view's task to do that, but I've kept it for backward compatibility
            //       MVC way would be to add 'isHighlighted' non persistent field to the dependency model
            me.getDependencyStore().each(function(dependency) {
                dependency.isHighlighted = false;
            });
        }
    },

    onPrimaryViewItemUpdate : function(eventRecord, index, eventNode) {
        this.scheduleAllDependenciesRendering();
    },

    onPrimaryViewDependencyElPointerEvent : function(event, elDom, options) {
        var me = this,
            dependencyId,
            dependency;

        dependencyId = me.painter.getElementDependencyInternalId(elDom);
        dependency = me.getDependencyStore().getByInternalId(dependencyId);

        if (dependency) {
            var overCls = this.getOverCls();

            me.fireEvent('dependency' + event.type, me, dependency, event, elDom);

            if (overCls) {

                if (event.type === 'mouseover') {
                    this.highlightDependency(dependency, overCls);
                } else if (event.type === 'mouseout') {
                    this.unhighlightDependency(dependency, overCls);
                }
            }
        }
    },

    onDependencyStoreAdd : function(store, dependencies) {
        this.renderDependencies(dependencies);
    },

    onDependencyStoreRemove : function(store, dependencies) {
        this.clearDependencies(dependencies);
    },

    onDependencyStoreUpdate : function(store, dependency) {
        this.updateDependencies(dependency);
    },

    onDependencyStoreRefresh : function(store) {
        this.scheduleAllDependenciesRendering();
    },

    onDependencyStoreClear : function(store) {
        this.scheduleAllDependenciesRendering();
    }

    /**
     * @event refresh
     *
     * Fires after the view has fully rendered all the dependencies in the underlying store.
     *
     * @param {Sch.view.Dependency} view The dependency view instance
     */

    /**
     * @event dependencyclick
     *
     * Fires after clicking on a dependency line/arrow
     *
     * @param {Sch.view.dependency.View} view The dependency view instance
     * @param {Sch.model.Dependency} record The dependency record
     * @param {Ext.event.Event} event The event object
     * @param {HTMLElement} target The clicked DOM element
     */

    /**
     * @event dependencycontextmenu
     *
     * Fires after right clicking on a dependency line/arrow
     *
     * @param {Sch.view.dependency.View} view The dependency view instance
     * @param {Sch.model.Dependency} record The dependency record
     * @param {Ext.event.Event} event The event object
     * @param {HTMLElement} target The clicked DOM element
     */

    /**
     * @event dependencydblclick
     *
     * Fires after double clicking on a dependency line/arrow
     *
     * @param {Sch.view.dependency.View} view The dependency view instance
     * @param {Sch.model.Dependency} record The dependency record
     * @param {Ext.event.Event} event The event object
     * @param {HTMLElement} target The clicked DOM element
     */

    /**
     * @event dependencymouseover
     *
     * Fires when hovering over a dependency line/arrow
     *
     * @param {Sch.view.dependency.View} view The dependency view instance
     * @param {Sch.model.Dependency} record The dependency record
     * @param {Ext.event.Event} event The event object
     * @param {HTMLElement} target The target DOM element
     */

    /**
     * @event dependencymouseout
     *
     * Fires when leaving a dependency line/arrow
     *
     * @param {Sch.view.dependency.View} view The dependency view instance
     * @param {Sch.model.Dependency} record The dependency record
     * @param {Ext.event.Event} event The event object
     * @param {HTMLElement} target The target DOM element
     */
});
