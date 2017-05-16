Ext.define('uber.view.home.Home',{
	extend: 'Ext.panel.Panel',
	xtype: 'home',
	layout: {
        type: 'vbox',
        align: 'stretch'
    },
	items: [{
        xtype: 'container',
        flex: 1,
         items: [{
            xtype: 'fieldset',
            title: 'Request Status',
            items: [{
            	
            }]
        }]
// },{
// 	xtype: 'panel',
//     flex: 1,
//      items: [{
//         xtype: 'fieldset',
//         title: 'Calendar'
//     }]
    }]
});