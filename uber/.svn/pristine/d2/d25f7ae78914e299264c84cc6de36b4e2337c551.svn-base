/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 @class Sch.template.Event

 */
Ext.define("Sch.template.Event", {
    extend : 'Ext.XTemplate',

    eventPrefix   : null,

    // 'none', 'start', 'end' or 'both'
    resizeHandles : null,
    resizeTpl     : '<div class="sch-resizable-handle sch-resizable-handle-DIR"></div>',

    constructor   : function (config) {
        Ext.apply(this, config);

        this.callParent([
            '<tpl for=".">' +
                '<div unselectable="on" tabindex="-1" id="' + this.eventPrefix + '{id}" style="right:{right}px;left:{left}px;top:{top}px;height:{height}px;width:{width}px;{style}" class="sch-event ' + Ext.baseCSSPrefix + 'unselectable {internalCls} {cls}">' +
                    ((this.resizeHandles === 'start' || this.resizeHandles === 'both') ? this.resizeTpl.replace(/DIR/, 'start') : '') +
                    '<div unselectable="on" class="sch-event-inner {iconCls}">' +
                        '{body}' +
                    '</div>' +
                    ((this.resizeHandles === 'end' || this.resizeHandles === 'both') ? this.resizeTpl.replace(/DIR/, 'end') : '') +
                '</div>' +
            '</tpl>'
        ]);
    }
});
