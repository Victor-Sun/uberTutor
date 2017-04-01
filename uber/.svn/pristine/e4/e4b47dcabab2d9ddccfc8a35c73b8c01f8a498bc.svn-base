/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.assembla.com/spaces/bryntum/tickets/2127
// https://www.sencha.com/forum/showthread.php?296729
Ext.define('Gnt.patches.RightClick', {
    extend  : 'Sch.util.Patch',

    target      : 'Gnt.view.Gantt',

    maxVersion  : '6.0.1', // Open in 6.0.0, Fixed in 6.0.1

    overrides   : {
        handleScheduleBarEvent  : function (e) {
            // FF in windows and mac throws click event on right button click, while it shouldn't
            if (Ext.isGecko && e.type === 'click' && e.button === 2) {
                return false;
            }

            return this.callParent(arguments);
        }
    }
});
