/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.assembla.com/spaces/bryntum/tickets/2415-assignment-column-editor-collapses-on-checkcolumn-click/details#
Ext.define('Gnt.patches.CheckboxModel', {
    extend : 'Sch.util.Patch',

    target   : 'Ext.selection.CheckboxModel',

    overrides : {
        renderer : function (value, metaData, record, rowIndex, colIndex, store, view) {
            return '<div class="' + Ext.baseCSSPrefix + 'grid-row-checker" role="button" tabIndex="-1">&#160;</div>';
        }
    }
});