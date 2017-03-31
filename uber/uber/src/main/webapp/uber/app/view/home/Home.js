Ext.define('uber.view.home.Home',{
	extend: 'Ext.Panel',
	layout: {
        type: 'vbox',
        align: 'stretch'
    },
	xtype: 'home',
	items: [{
        xtype: 'panel',
        flex: 1,
        items: [{
            xtype: 'fieldset',
            title: 'Nearby Tutors',
            items: [{

            }]
        }]
    },{
        xtype: 'panel',
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