Ext.define('uber.view.search.Search', {
	extend: 'Ext.Panel',
	xtype: 'search',
	items: [{
			xtype: 'toolbar',
            items: [{
                xtype: 'button',
                text: 'Search',
                handler: function() {
                    Ext.getCmp("searchPanel").setActiveItem(0);
                }
            },{
                xtype: 'button',
                text: 'Search Results',
                handler: function() {
                    Ext.getCmp("searchPanel").setActiveItem(1);
                }
            }]
        },{
       		xtype: 'panel',
            flex: 1,
            reference: 'searchPanel',
            id: 'searchPanel',
            layout: {
                type: 'card',
                align: 'stretch'
            },
       		items: [{
       			xtype: 'container',
                layout: {
                    type: 'vbox',
                    align: 'stretch'
                },
       			title: 'Search',
           		items: [{
           			xtype: 'textfield',
					name: 'subject',
					label: 'Subject(s)',
					clearIcon: true
	        	},{
	        		xtype: 'textfield',
					name: 'name',
					label: 'Price/hr',
					placeHolder: 'me@domail.com'
	        	},{
	        		xtype: 'selectfield',
					name: 'Distance',
					label: 'Distance',
                    options: [{
                        text: 'less than 1 km'
                    },{
                        text: 'less than 5 km'
                    }]
           		},{
           			xtype: 'toolbar',
           			items: [{
           				xtype: 'button',
           				text: 'Search'
           			},{
           				xtype: 'button',
           				text: 'Reset'
           			}]
           		}]
            },{
                xtype: 'grid',
                title: 'Search Results',
                layout: 'fit',
                fullscreen: true,
                flex: 1,
                store: {
                    fields: ['name', 'price', 'distance'],
                    data: [
                        { 'name': 'Joe', 'price': '$10', 'distance': '3 km away' },
                        { 'name': 'Joe', 'price': '$10', 'distance': '3 km away' },
                        { 'name': 'Joe', 'price': '$10', 'distance': '3 km away' }
                    ]
                },
                columns: [{
                    text: 'Name',
                    dataIndex: 'name',
                    minWidth: 450
                },{
                    text: 'Price',
                    dataIndex: 'price',
                    width: 450
                },{
                    text: 'Distance',
                    dataIndex: 'distance',
                    width: 450
                }]
       		}]
		}]
	// config: {
	// 	variableHeights: true,
		
	// }
});