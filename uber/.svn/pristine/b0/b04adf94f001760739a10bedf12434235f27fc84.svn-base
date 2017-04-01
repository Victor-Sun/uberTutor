/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.patches.SelectionExtender', {
    extend : 'Sch.util.Patch',

    target     : 'Ext.grid.selection.SelectionExtender',
    minVersion : '6.0.0',

    applyFn     : function () {
        var overrides = {
            // prevent selection extending to normal view
            onDrag : function(e) {
                if (!Ext.fly(e.getTarget()).up('.sch-ganttview')) {
                    this.callParent(arguments);
                }
            }
        };

        if (Ext.getVersion().isLessThan('6.0.1')) {
            // fixes bug that is reproducible in this fiddle: https://fiddle.sencha.com/#fiddle/102p
            // select row #3 and click 'remove'
            overrides.setHandle = function (firstPos, lastPos) {
                firstPos && firstPos.record && lastPos && lastPos.record && this.callParent(arguments);
            };
        }

        Ext.ClassManager.get(this.target).override(overrides);
    }
});