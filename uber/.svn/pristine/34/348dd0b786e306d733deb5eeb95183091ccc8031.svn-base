/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 @class Sch.template.Dependency

 The HTML template used to visualise a line between two tasks.
 */
Ext.define("Sch.template.Dependency", {
    extend : 'Ext.XTemplate',

    disableFormats : true,

    rtl         : null,

    text :
        '<tpl if="startArrow">'+
            '<div style="__SIDE__:{startArrow.side}px;top:{startArrow.top}px" class="sch-dependency sch-dependency-arrow sch-dependency-start-arrow sch-dependency-arrow-{startArrow.dir} {[ this.getSuffixedCls(values.cls, "-arrow") ]} {[ values.isHighlighted ? "sch-dependency-selected" : "" ]}" <tpl if="dependencyId">data-sch-dependency-id="{[values.dependencyId]}"</tpl>></div>'+
        '</tpl>'+
        '<tpl for="segments">'+
            '<div class="sch-dependency sch-dependency-line sch-dependency-line-{dir} {[ parent.isHighlighted ? "sch-dependency-selected" : "" ]} {[ this.getSuffixedCls(parent.cls, "-line") ]}" style="__SIDE__:{side}px;top:{top}px;<tpl if="width !== null && width !== undefined">width:{width}px;</tpl><tpl if="height !== null && height !== undefined">height:{height}px</tpl>" <tpl if="parent.dependencyId">data-sch-dependency-id="{parent.dependencyId}"</tpl>></div>'+
        '</tpl>'+
        '<tpl if="endArrow">'+
            '<div style="__SIDE__:{endArrow.side}px;top:{endArrow.top}px" class="sch-dependency sch-dependency-arrow sch-dependency-end-arrow sch-dependency-arrow-{endArrow.dir} {[ this.getSuffixedCls(values.cls, "-arrow") ]} {[ values.isHighlighted ? "sch-dependency-selected" : "" ]}" <tpl if="dependencyId">data-sch-dependency-id="{dependencyId}"</tpl>></div>'+
        '</tpl>',
    
    constructor   : function (config) {
        Ext.apply(this, config);

        this.text = this.text.replace(/__SIDE__/g, this.rtl ? 'right' : 'left');

        this.callParent([this.text]);
    },
    
    getSuffixedCls : function(cls, suffix) {
        var result = '';

        if (cls && cls.indexOf(' ') != -1) {
            result = cls.replace(/^\s*(.*)\s*$/, '$1').split(/\s+/).join(suffix + ' ') + suffix;
        }
        else if (cls) {
            result = cls + suffix;
        }

        return result;
    }
});
