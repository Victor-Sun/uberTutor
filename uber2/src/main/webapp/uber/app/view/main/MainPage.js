Ext.define('uber.view.main.MainPage', {
    extend: 'Ext.panel.Panel',
    xtype: 'mainpage',
    itemId: 'mainpage',
    	
    requires: [
    ],
    
    layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
    items: [{
    	xtype: 'panel',
		flex: 1,
//    	cls: 'uber-panel-inner',
    	layout: {
    		type: 'vbox',
    		align: 'stretch'
    	},
    	items: [{
    		xtype: 'panel',
    		border: true,
    		cls: 'uber-header',
    		layout: 'hbox',
    		items: [{
    			xtype: 'container',
                layout: 'hbox',
                items: [{
                    margin: 5,
                    html: '<h2>Main Page</h2>'
                }]
    		},{
    			xtype: 'container',
    			flex: 1,
    		}]
    	},{
    		xtype: 'container',
    		layout: {
    			type: 'vbox',
    			align: 'stretch'
    		},
    		flex: 1,
    		style: {
    			backgroundColor: '#f5f5f5'
    		},
    		scrollable: 'y',
    		defaults: {
    			height: 250,
//    			margin: '5 10',
    		},
    		items: [{
    			xtype: 'containerWidget',
    			margin: '10 10 0',
    			items: [{
    				xtype: 'widgetTitle',
    				items: [{
    					margin: 10,
    					html: '<b>Open Request Queue</b>'
    				}]
    			},{
    				xtype: 'grid',
    				layout: 'fit',
//    				columns: [{
//    					text: 'Date',
////    					dataIndex: 'CATEGORY',
//    					align: 'left',
//    					flex: 1
//    				},{
//    					text: 'Tutor',
////    					dataIndex: 'CATEGORY',
//    					align: 'left',
//    					flex: 1
//    				},{
//    					text: 'Subject',
////    					dataIndex: 'CATEGORY',
//    					align: 'left',
//    					flex: 1
//    				}]
    			}]
    		},{
    			xtype: 'containerWidget',
    			margin: '10 10 0',
    			items: [{
    				xtype: 'widgetTitle',
    				items: [{
    					margin: 10,
    					html: '<b>My Request</b>'
    				}]
    			},{
    				xtype: 'grid',
    				layout: 'fit',
    			}]
    		},{
    			xtype: 'containerWidget',
    			margin: '10 10 0',
    			items: [{
    				xtype: 'widgetTitle',
    				items: [{
    					margin: 10,
    					html: '<b>My Open Sessions</b>'
    				}]
    			},{
    				xtype: 'grid',
    				layout: 'fit',
//    				columns: [{
//    					text: 'Date',
////    					dataIndex: 'CATEGORY',
//    					align: 'left',
//    					flex: 1
//    				},{
//    					text: 'Tutor',
////    					dataIndex: 'CATEGORY',
//    					align: 'left',
//    					flex: 1
//    				},{
//    					text: 'Subject',
////    					dataIndex: 'CATEGORY',
//    					align: 'left',
//    					flex: 1
//    				}]
    			}]
    		},{
    			xtype: 'containerWidget',
    			margin: '10 10 0',
    			items: [{
    				xtype: 'widgetTitle',
    				items: [{
    					margin: 10,
    					html: '<b>Request Queue</b>'
    				}]
    			},{
    				xtype: 'grid',
    				layout: 'fit',
//    				columns: [{
//    					text: 'Date',
////    					dataIndex: 'CATEGORY',
//    					align: 'left',
//    					flex: 1
//    				},{
//    					text: 'Tutor',
////    					dataIndex: 'CATEGORY',
//    					align: 'left',
//    					flex: 1
//    				},{
//    					text: 'Tutor',
////    					dataIndex: 'CATEGORY',
//    					align: 'left',
//    					flex: 1
//    				},{
//    					text: 'Subject',
////    					dataIndex: 'CATEGORY',
//    					align: 'left',
//    					flex: 1
//    				},{
//    					text: 'Status',
////    					dataIndex: 'CATEGORY',
//    					align: 'left',
//    					flex: 1
//    				}]
    			}]
    		}]
    	}]
    }]
});