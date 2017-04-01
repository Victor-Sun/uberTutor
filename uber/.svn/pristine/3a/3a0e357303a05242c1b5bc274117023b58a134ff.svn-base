/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.sencha.com/forum/showthread.php?311875-DomScroller-bug&p=1138744#post1138744
// https://app.assembla.com/spaces/bryntum/tickets/2920/details?comment=1001310103#
// Tested in http://lh/extscheduler4.x/tests/#view/2101_view_rtl.t.js?Ext=6.0.1
Ext.define('Sch.patches.DomScroller', {
    extend      : 'Sch.util.Patch',

    target      : 'Ext.scroll.DomScroller',
    maxVersion  : '6.2.0',

    overrides   : {
        privates : {
            convertX : function (x) {
                if (x == null) return null;

                return this.callParent(arguments);
            }
        }
    }
});