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
	    					text: "Click here to make a request",
	    					handler: 'makeRequest'
	    				}]
	    			}]
	    		},{
	    			xtype: 'containerWidget',
	    			itemId: 'currentSession',
	    		    height: 275,
	    			items: [{
	    				xtype: 'widgetTitle',
	    				items: [{
	    					margin: 10,
	    					html: '<b>Current Requests</b>'
	    				}]
	    			},{
	    				// Title, Category, Subject, button for details, if status closed, go to feedback
	    				xtype: 'currentSessions',
	    				flex: 1,
	    				scrollable: 'y'
	    			}]
	    		},{
	    			xtype: 'containerWidget',
	    			itemId: 'myTutors',
	    			height: 275,
	    			items: [{
	    				xtype: 'widgetTitle',
	    				items: [{
	    					margin: 10,
	    					html: '<b>My Tutors</b>'
	    				}]
	    			},{
	    				xtype: 'myTutorsGrid',
	    				flex: 1,
	    				scrollable: 'y'
	    			}]
	    		},{
	    			xtype: 'containerWidget',
	    			itemId: 'openRequests',
	    			hidden: true,
	    			height: 275,
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
	    		},{
	    			xtype: 'containerWidget',
	    			itemId: 'currentRequests',
	    			hidden: true,
	    			height: 275,
	    			items: [{
	    				xtype: 'widgetTitle',
	    				items: [{
	    					margin: 10,
	    					html: "<b>Current Requests (Tutor's) </b>"
	    				}]
	    			},{
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