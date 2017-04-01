/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// Private class used to allow row reordering when using the SpreadSheet selection model
Ext.define('Gnt.column.DragDrop', {
    extend                  : 'Ext.grid.column.Column',

    alias                   : [
        'widget.dragdropcolumn',
        'widget.ganttcolumn.dragdrop'
    ],

    width        : 35,
    tdCls        : 'sch-gantt-column-dragdrop',

    cls          : 'sch-gantt-column-dragdrop-header',

    ignoreInAddMenu : true,
    ignoreExport    : true,
    ignoreInExport  : true,
    sortable        : false,
    resizable       : false,
    hideable        : false,
    menuDisabled    : true,
    draggable       : false,
    align           : 'center',

    // private override
    processEvent : function(type) {
        return type !== 'click';
    }
});
