Ext.define('uber.view.main.MainPage', {
    extend: 'Ext.panel.Panel',
    xtype: 'mainpage',
    itemId: 'mainpage',
    	
    requires: [
    ],
    controller: 'main',
    layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
	
	
	initComponent: function () {
		var me = this;
	    this.items = [{
	    	xtype: 'panel',
			flex: 1,
//	    	cls: 'uber-panel-inner',
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
//	    			height: 250,
	    			margin: '15 20 0',
	    		},
	    		items: [{
	    			xtype: 'containerWidget',
	    			itemId: 'makeRequest',
	    			title: '<b>Make A Request</b>',
	    			items: [{
//	    				xtype: 'widgetTitle',
//	    				items: [{
//	    					margin: 10,
//	    					html: 
//	    				}]
//	    			},{
	    				xtype: 'toolbar',
	    				items: [{
	    					//Navigate to make a request page?
	    					xtype: 'button',
	    					scale: 'large',
	    					text: "Click here to make a request",
	    					handler: 'makeRequest'
	    				}]
	    			}]
	    		},{
	    			xtype: 'containerWidget',
	    			itemId: 'currentSession',
	    			title: '<b>Current Requests</b>',
	    		    height: 275,
	    			items: [{
//	    				xtype: 'widgetTitle',
//	    				items: [{
//	    					margin: 10,
//	    				}]
//	    			},{
	    				// Title, Category, Subject, button for details, if status closed, go to feedback
	    				xtype: 'currentSessions',
	    				flex: 1,
	    				scrollable: 'y'
	    			}]
	    		},{
	    			xtype: 'containerWidget',
	    			itemId: 'myTutors',
	    			title: '<b>My Tutors</b>',
	    			height: 275,
	    			items: [{
//	    				xtype: 'widgetTitle',
//	    				items: [{
//	    					margin: 10,
//	    					html: 
//	    				}]
//	    			},{
	    				xtype: 'myTutorsGrid',
	    				flex: 1,
	    				scrollable: 'y'
	    			}]
	    		},{
	    			xtype: 'containerWidget',
	    			itemId: 'openRequests',
	    			title: '<b>Open Requests</b>',
	    			hidden: true,
	    			height: 275,
	    			layout: {
	    				type: 'vbox',
	    				align: 'stretch'
	    			},
	    			items: [{
//	    				xtype: 'widgetTitle',
//	    				items: [{
//	    					margin: 10,
//	    					html: 
//	    				}]
//	    			},{
	    				//Title, Category, Subject, Description
	    				xtype: 'openRequests',
	    				flex: 1,
	    				scrollable: 'y'
	    			}]
	    		},{
	    			xtype: 'containerWidget',
	    			itemId: 'currentRequests',
	    			title: "<b>Current Requests (Tutor's) </b>",
	    			hidden: true,
	    			height: 275,
	    			items: [{
//	    				xtype: 'widgetTitle',
//	    				items: [{
//	    					margin: 10,
//	    					html: 
//	    				}]
//	    			},{
	    				xtype: 'currentRequests',
	    				flex: 1,
	    				scrollable: 'y'
	    			}]
	    		}]
	    	}]
	    }];	
    	this.callParent(arguments);
    }
});