/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// Override provided by sencha
// https://www.sencha.com/forum/showthread.php?310675-Layout-run-failed-with-syncRowHeight-false
Ext.define('Sch.patches.Queue', {
    extend      : 'Sch.util.Patch',

    target      : 'Ext.util.Queue',

    minVersion  : '6.0.2',
    maxVersion  : '6.0.3',

    overrides   : {
        add: function (obj, replace) {
            var me = this,
                key = me.getKey(obj),
                prevEntry;

            if (!(prevEntry = me.map[key])) {
                ++me.length;
                me.items.push(obj);
                me.map[key] = obj;
            } else if (replace) {
                me.map[key] = obj;
                me.items[Ext.Array.indexOf(me.items, prevEntry)] = obj;
            }

            return obj;
        }
    }
});