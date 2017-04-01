/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/*
 * @class Gnt.column.ResourceName
 * @extends Ext.grid.Column
 * @private
 * Private class used inside Gnt.widget.AssignmentGrid.
 */
Ext.define('Gnt.column.ResourceName', {
    extend         : 'Ext.grid.column.Column',
    alias          : 'widget.resourcenamecolumn',
    mixins         : ['Gnt.mixin.Localizable'],

    flex           : 1,
    align          : 'left',
    resourceStore  : null,

    constructor : function (config) {
        config = config || {};

        this.text   = config.text || this.L('text');

        Ext.apply(this, config);

        this.scope = this.scope || this;

        this.callParent(arguments);
    },

    renderer      : function (value, m, assignment) {
        var resource = assignment.getResource(this.resourceStore);
    
        return Ext.String.htmlEncode(resource && resource.getName() || value);
    }
});
