/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/*
 * @class Gnt.feature.DependencyDropZone
 * @extends Ext.util.Observable
 * @private
 * Internal drop zone class for dependency drag drop.
 */
Ext.define("Gnt.feature.DependencyDropZone", {
    extend : 'Ext.dd.DropZone',

    mixins: {
        observable:  'Ext.util.Observable'
    },

    terminalSelector : null,
    dependencyStore  : null,
    toText           : null,
    startText        : null,
    endText          : null,
    ganttView        : null,

    constructor : function(el, config) {
        this.mixins.observable.constructor.call(this, config);

        this.callParent(arguments);
    },

    getTargetFromEvent : function (e) {
        return e.getTarget(this.terminalSelector);
    },

    // On entry into a target node, highlight that node.
    onNodeEnter        : function (target, dd, e, data) {
        Ext.fly(target).addCls('sch-gantt-terminal-drophover');
    },

    // On exit from a target node, unhighlight that node.
    onNodeOut          : function (target, dd, e, data) {
        Ext.fly(target).removeCls('sch-gantt-terminal-drophover');

        // Clear the proxy text showing the target task
        this.toolTipTpl.overwrite(dd.proxy.el.down('.sch-dd-dependency'), data.tplData);

    },

    onNodeOver : function (target, dd, e, data) {
        var targetRecord = this.ganttView.resolveTaskRecord(target),
            targetId = targetRecord.getId() || targetRecord.internalId,
            isTargetStart = target.className.match('sch-gantt-terminal-start');

        var tplData = {};

        Ext.apply(tplData, {
           toLabel      : this.toText,
           toTaskName   : Ext.String.htmlEncode(targetRecord.getName()),
           toSide       :  isTargetStart ? this.startText : this.endText
        }, data.tplData);

        this.toolTipTpl.overwrite(dd.proxy.el.down('.sch-dd-dependency'), tplData);

        var type = this.resolveType(data.isStart, target);

        if (this.dependencyStore.isValidDependency(data.fromId, targetId, type)) {
            return this.dropAllowed;
        } else {
            return this.dropNotAllowed;
        }
    },

    onNodeDrop : function (target, dd, e, data) {
        var type = this.resolveType(data.isStart, target),
            retVal,
            targetRec = this.ganttView.resolveTaskRecord(target),
            targetId = targetRec.getId() || targetRec.internalId;

        this.el.removeCls('sch-gantt-dep-dd-dragging');

        retVal = this.dependencyStore.isValidDependency(data.fromId, targetId, type);

        if (retVal) {
            this.fireEvent('drop', this, data.fromId, targetId, type);
        }
        this.fireEvent('afterdnd', this);

        return retVal;
    },

    resolveType : function (isFromStart, target) {
        var DepType = Gnt.model.Dependency.Type,
            isToStart = target.className.match('sch-gantt-terminal-start');

        if (isFromStart  && isToStart)  return DepType.StartToStart;
        if (isFromStart  && !isToStart) return DepType.StartToEnd;
        if (!isFromStart && isToStart)  return DepType.EndToStart;

        return DepType.EndToEnd;
    }
});
