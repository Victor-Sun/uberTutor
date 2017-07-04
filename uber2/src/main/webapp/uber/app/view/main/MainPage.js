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
//    			height: 250,
//    			margin: '5 10',
    		},
    		items: [{
    			xtype: 'containerWidget',
    			margin: '10 10 0',
    			items: [{
    				xtype: 'widgetTitle',
    				items: [{
    					margin: 10,
    					html: '<b>Make A Request</b>'
    				}]
    			},{
    				xtype: 'toolbar',
    				items: [{
    					//Navigate to make a request page?
    					xtype: 'button',
    					scale: 'large',
    					text: "Click here to make a request"
    				}]
    			}]
    		},{
    			xtype: 'containerWidget',
    		    height: 235,
    			margin: '10 10 0',
    			items: [{
    				xtype: 'widgetTitle',
    				items: [{
    					margin: 10,
    					html: '<b>Current Sessions</b>'
    				}]
    			},{
    				// Title, Category, Subject, button for details, if status closed, go to feedback
    				xtype: 'currentSessions',
    				flex: 1,
    				scrollable: 'y'
    			}]
    		},{
    			xtype: 'containerWidget',
    		    height: 235,
    			margin: '10 10 0',
    			items: [{
    				xtype: 'widgetTitle',
    				items: [{
    					margin: 10,
    					html: '<b>My Tutors</b>'
    				}]
    			},{
    				xtype: 'grid',
    				flex: 1,
    				layout: 'fit',
    				store: {
    					field: [{
    						name: 'tutor',
    						type: 'string'
    					},{
    						name: 'rating',
    						type: 'integer'
    					}],
    					data: [{
    						'tutor': 'tutor', 'rating': 5
    					},{
    						'tutor': 'tutor2', 'rating': 4 
    					}]
    				},
    				columns: [{
    					dataIndex: 'tutor',
    					align: 'left',
    					flex: 1
//    				},{
//    					dataIndex: 'rating'
    				}]
    			}]
    		},{
    			xtype: 'containerWidget',
    		    height: 235,
    			margin: '10 10 0',
    			items: [{
    				xtype: 'widgetTitle',
    				items: [{
    					margin: 10,
    					html: '<b>Current Requests</b>'
    				}]
    			},{
    				xtype: 'currentRequests',
    				flex: 1,
    				scrollable: 'y'
    			}]
    		},{
    			xtype: 'containerWidget',
    		    height: 235,
    			margin: '10 10 0',
    			layout: {
    				type: 'vbox',
    				align: 'stretch'
    			},
    			items: [{
    				xtype: 'widgetTitle',
    				items: [{
    					margin: 10,
    					html: '<b>Open Requests</b>'
    				}]
    			},{
    				//Title, Category, Subject, Description
    				xtype: 'openRequests',
    				flex: 1,
    				scrollable: 'y'
    			}]
    		}]
    	}]
    }]
});