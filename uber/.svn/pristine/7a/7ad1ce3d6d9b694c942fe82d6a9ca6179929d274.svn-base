/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.template.ParentTask
@extends Ext.XTemplate

Class used to render a parent task.
*/
Ext.define("Gnt.template.ParentTask", {
    extend : 'Gnt.template.Template',

    /**
     * @cfg {String} innerTpl The template defining the inner visual markup for the task.
     */
    innerTpl    : '<div class="sch-gantt-progress-bar" style="width:{progressBarWidth}px;{progressBarStyle}">&#160;</div>'+
        '<div class="sch-gantt-parenttask-arrow sch-gantt-parenttask-leftarrow"></div>' +
        '<div class="sch-gantt-parenttask-arrow sch-gantt-parenttask-rightarrow"></div>',

    getInnerTpl : function(cfg) {
        return '<div id="' + cfg.prefix + '{id}" class="sch-gantt-item sch-gantt-parenttask-bar {cls}" style="width:{width}px; {style}">'+
                    this.innerTpl +
                    // Left / Right terminals
                    ((cfg.enableDependencyDragDrop && cfg.allowParentTaskDependencies) ? this.dependencyTerminalMarkup : '') +
                '</div>';

    }
});
