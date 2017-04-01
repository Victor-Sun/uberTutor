/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
//http://www.sencha.com/forum/showthread.php?296703-iOS-devices-Crash-in-TouchScroller&p=1083470#post1083470
Ext.define('Sch.patches.TouchScroll', {
    extend   : 'Sch.util.Patch',

    target     : 'Ext.scroll.TouchScroller',

    minVersion : '5.1.0',
    maxVersion : '6.2.0',

    overrides  : {
        privates : {
            onEvent: function(e) {
                var me = this;

                if (!me[me.listenerMap[e.type]]) return;

                return this.callParent(arguments);
            }
        }
    }
});