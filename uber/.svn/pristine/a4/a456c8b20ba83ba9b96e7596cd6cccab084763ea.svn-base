/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// Column menu won't expand https://www.sencha.com/forum/showthread.php?304517
Ext.define('Sch.patches.Explorer', {

    extend      : 'Sch.util.Patch',

    target    : ['Ext.util.CSS'],

    minVersion  : '6.0.0',

    maxVersion  : '6.0.1',

    applyFn : function () {
        if (Ext.isIE9m) {
            Ext.util.CSS.createStyleSheet('.' + Ext.baseCSSPrefix + 'column-header-trigger { z-index: 10; }');
        }
    }
});