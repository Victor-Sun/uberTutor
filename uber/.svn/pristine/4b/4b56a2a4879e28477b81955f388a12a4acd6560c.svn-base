/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// When input is hidden using vibility : hidden, or display : none focus goes to secondary canvas
// and produces exception, because navigation model cannot resolve position
// Caught by test 1200_label_editing
// Fiddle: https://fiddle.sencha.com/#fiddle/srg
Ext.define('Gnt.patches.LabelEditor', {
    extend      : 'Sch.util.Patch',

    target      : 'Gnt.feature.LabelEditor',
    minVersion  : '6.0.0',
    // Fixed in nightlies from 19.08
    maxVersion  : '6.0.1',

    ieOnly      : true,

    overrides   : {
        constructor: function() {
            this.callParent(arguments);

            if (this.rendered) {
                this.getEl().setVisibilityMode(Ext.dom.Element.OFFSETS);
            } else {
                this.on('render', function () {
                    this.getEl().setVisibilityMode(Ext.dom.Element.OFFSETS);
                }, this, { single : true });
            }
        }
    }
});