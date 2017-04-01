/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/*
 * @class Gnt.feature.DependencyDragZone
 * @extends Ext.util.Observable
 * @private
 * Internal drag zone class for dependency drag drop.
 */
Ext.define("Gnt.feature.DependencyDragZone", {
    extend : 'Ext.dd.DragZone',

    mixins: {
        observable:  'Ext.util.Observable'
    },

    rtl                    : null,
    useLineProxy           : null,
    terminalSelector       : null,
    ganttView              : null,
    fromText               : null,
    toText                 : null,
    startText              : null,
    endText                : null,
    toolTipTpl             : null,

    constructor : function(el, config) {
        this.mixins.observable.constructor.call(this, config);

        this.callParent(arguments);
    },

    initLineProxy : function (sourceEl, isStart) {
        var lpEl = this.lineProxyEl = this.lineProxyEl || this.el.createChild({ cls : 'sch-gantt-connector-proxy' });
        var adjust = Ext.isIE9m ? 0 : 4;
        var side = this.rtl ? (isStart ? 'r' : 'l') : (isStart ? 'l' : 'r');
        var scroll    = this.ganttView.getScroll();

        lpEl.alignTo(sourceEl, side, [isStart ? -adjust : adjust, 0]);

        Ext.apply(this, {
            containerTop    : this.el.getTop(),
            containerLeft   : this.el.getLeft(),
            startXY         : lpEl.getXY(),
            startScrollLeft : scroll.left,
            startScrollTop  : scroll.top
        });
    },

    onDrag : function(e, t) {
        if (this.useLineProxy) {
            this.updateLineProxy(e.getXY());
        }
    },

    updateLineProxy : function (xy) {
        var lineProxy = this.lineProxyEl,
            scroll    = this.ganttView.getScroll(),
            diffX = xy[0] - this.startXY[0] + scroll.left - this.startScrollLeft,
            diffY = xy[1] - this.startXY[1] + scroll.top - this.startScrollTop,
            newHeight = Math.max(1, Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2)) - 2),
            // Calculate new angle relative to start XY
            rad = Math.atan2(diffY, diffX) - (Math.PI / 2),
            styleBlob;

        if (Ext.isIE9m) {
            var cos = Math.cos(rad),
                sin = Math.sin(rad),
                matrixString = 'progid:DXImageTransform.Microsoft.Matrix(sizingMethod="auto expand", M11 = ' + cos + ', M12 = ' + (-sin) + ', M21 = ' + sin + ', M22 = ' + cos + ')',
                scrollValueTop,
                scrollValueLeft;

            if (scroll.top !== this.startScrollTop) {
                scrollValueTop = this.startScrollTop - this.containerTop;
            } else {
                scrollValueTop = scroll.top - this.containerTop;
            }

            if (scroll.left !== this.startScrollLeft) {
                scrollValueLeft = this.startScrollLeft - this.containerLeft;
            } else {
                scrollValueLeft = scroll.left - this.containerLeft;
            }

            styleBlob = {
                "height"     : newHeight + 'px',
                "top"        : Math.min(0, diffY) + this.startXY[1] + scrollValueTop + (diffY < 0 ? 2 : 0) + 'px',
                "left"       : Math.min(0, diffX) + this.startXY[0] + scrollValueLeft + (diffX < 0 ? 2 : 0) + 'px',
                "filter"     : matrixString,
                "-ms-filter" : matrixString
            };
        } else {
            var rotateString = 'rotate(' + rad + 'rad)';

            styleBlob = {
                "height"            : newHeight + 'px',
                "-o-transform"      : rotateString,
                "-webkit-transform" : rotateString,
                "-ms-transform"     : rotateString,
                "-moz-transform"    : rotateString,
                "transform"         : rotateString
            };
        }

        lineProxy.setStyle(styleBlob);
    },

    
    onStartDrag : function () {

        this.el.addCls('sch-gantt-dep-dd-dragging');
        this.proxy.el.addCls('sch-dd-dependency-proxy');

        this.fireEvent('dndstart', this);

        if (this.useLineProxy) {
            var dd = this.dragData;
            this.initLineProxy(dd.sourceNode, dd.isStart);
            this.lineProxyEl.show();
        }
    },
    
    // On receipt of a mousedown event, see if it is within a draggable element.
    // Return a drag data object if so. The data object can contain arbitrary application
    // data, but it should also contain a DOM element in the ddel property to provide
    // a proxy to drag.
    getDragData : function (e) {
        var sourceNode = e.getTarget(this.terminalSelector);

        if (sourceNode) {
            var sourceTaskRecord = this.ganttView.resolveTaskRecord(sourceNode);
            if (this.fireEvent('beforednd', this, sourceTaskRecord) === false) {
                return null;
            }

            var isStart = !!sourceNode.className.match('sch-gantt-terminal-start');

            var tplData = {
                fromLabel       : this.fromText,
                fromTaskName    : Ext.String.htmlEncode(sourceTaskRecord.getName()),
                fromSide        : isStart ? this.startText : this.endText,
                toLabel         : this.toText,
                toTaskName      : '',
                toSide          : ''
            };

            var ddel = Ext.core.DomHelper.createDom({ html : this.toolTipTpl.apply(tplData) }).firstChild;

            return {
                fromId     : sourceTaskRecord.getId() || sourceTaskRecord.internalId,
                tplData    : tplData,
                isStart    : isStart,
                repairXY   : Ext.fly(sourceNode).getXY(),
                ddel       : ddel,
                sourceNode : Ext.fly(sourceNode).up(this.ganttView.eventSelector)
            };
        }
        return false;
    },

    // Override, get rid of weird highlight fx in default implementation
    afterRepair : function () {
        this.dragging = false;
        if (!this.destroyed) {
            this.el.removeCls('sch-gantt-dep-dd-dragging');
            this.fireEvent('afterdnd', this);
        }
    },

    onMouseUp   : function () {
        this.el.removeCls('sch-gantt-dep-dd-dragging');

        if (this.lineProxyEl) {
            var duration = Ext.isIE9m ? 0 : 400;
            var el = this.lineProxyEl;

            el.animate({
                to       : { height : 0 },
                duration : duration,
                callback : function() {
                    Ext.destroy(el);
                }
            });

            this.lineProxyEl = null;
        }
    },


    // Provide coordinates for the proxy to slide back to on failed drag.
    // This is the original XY coordinates of the draggable element.
    getRepairXY : function () {
        return this.dragData.repairXY;
    },

    destroy : function () {
        Ext.destroy(this.lineProxyEl);

        this.callParent(arguments);
    }
});

