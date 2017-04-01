/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.sencha.com/forum/showthread.php?304819-Click-on-asterisk-throws-exception-in-tree
Ext.define('Sch.patches.TreeNavigationModel', {
    extend      : 'Sch.util.Patch',

    target      : 'Ext.tree.NavigationModel',

    minVersion  : '6.0.0',
    maxVersion  : '6.0.2',

    overrides   : {
        onAsterisk  : function () {
            if (!this.view.ownerCt.expandAll) {
                this.view.lockingPartner.ownerCt.expandAll();
                return;
            }

            this.callParent(arguments);
        }
    }
});
