/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// Override provided by sencha
// https://www.sencha.com/forum/showthread.php?310675-Layout-run-failed-with-syncRowHeight-false
Ext.define('Sch.patches.LayoutContext', {
    extend      : 'Sch.util.Patch',

    target      : 'Ext.layout.Context',

    minVersion  : '6.0.2',
    maxVersion  : '6.0.3',

    overrides: {
        queueFlush: function (item, replace) {
            this.flushQueue.add(item, replace);
        }
    }
});