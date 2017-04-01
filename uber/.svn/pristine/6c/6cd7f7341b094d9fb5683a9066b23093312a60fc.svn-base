/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

 @class Sch.panel.TimelineTreePanel
 @extends Ext.tree.Panel
 @mixin Sch.mixin.TimelinePanel

 Internal class.

 */
Ext.define("Sch.panel.TimelineTreePanel", {
    extend              : "Ext.tree.Panel",
    requires            : [
        // need to require grid panel too here, because one of the sub-grids will be a normal grid
        'Ext.grid.Panel',
        'Ext.data.TreeStore',
        // will be used in the `setupLockableTree` of lockable mixin
        'Sch.mixin.FilterableTreeView',
        'Sch.patches.TreeNavigationModel'
    ],
    mixins              : [
        'Sch.mixin.Localizable',
        'Sch.mixin.TimelinePanel'
    ],
    alias               : [ 'widget.timelinetree'],

    useArrows           : true,
    rootVisible         : false,
    lockedXType         : 'treepanel',

    isTimelineTreePanel : true,

    initComponent : function () {
        this.callParent(arguments);
        this.getSchedulingView()._initializeTimelineView();
    }
}, function () {
    this.override(Sch.mixin.TimelinePanel.prototype.inheritables() || {});
});
