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
    				xtype: 'grid',
    				layout: 'fit',
    				store: {
//    					proxy: {
//    				        type: 'ajax',
//    				        url: '/uber2/main/my-session!displayTutorSessions.action',
//    				        reader: {
//    				            type: 'json',
//    				            rootProperty: 'data',
//    				            totalProperty: 'total'
//    				        }
//    				    },
    					data: [
    					       { 'TITLE': 'Session 1', 'SUBJECT': 'Algebra', 'STATUS': 'PENDING', 'DESCRIPTION':'THIS IS A DESCRIPTION'}
					       ]
    				},
    				columns: [{
    					xtype: 'templatecolumn',
    					align: 'left',
    					flex: 1,
    					tpl: [
							"<div class=''>" +
								"<div class=''>" +
									"<div class='title-section' style='display: inline; margin-left: 10px;'><b>Title:</b> {TITLE}</div>" +
									"<div class='subject-section' style='display: inline; margin-left: 10px;'><b>Subject:</b> {SUBJECT}</div>" +
									"<div class='status-section' style='display: inline; margin-left: 10px;'><b>Status:</b> {STATUS}</div>" +
								"</div>" +
								"<hr>" +
								"<div class=''>" +
									"<div class='description-label'><b>Description:</b></div>" +
									"<div class='description-section'>{DESCRIPTION}</div>" +
								"</div>" +
							"</div>",
							]
    				},{
    					xtype: 'actioncolumn',
    					align: 'center',
    					items:[{
    						xtype: 'button',
    						iconCls: 'x-fa fa-ellipsis-h',
    						tooltip: 'Details'
    					}]
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
    					html: '<b>My Tutors</b>'
    				}]
    			},{
    				xtype: 'grid',
    				layout: 'fit',
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
    				xtype: 'grid',
    				layout: 'fit',
    				store: {
//    					proxy: {
//    				        type: 'ajax',
//    				        url: '/uber2/main/my-session!displayTutorSessions.action',
//    				        reader: {
//    				            type: 'json',
//    				            rootProperty: 'data',
//    				            totalProperty: 'total'
//    				        }
//    				    },
    					data: [
    					       { 'TITLE': 'Session 1', 'SUBJECT': 'Algebra', 'STATUS': 'PENDING', 'DESCRIPTION':'THIS IS A DESCRIPTION'}
					       ]
    				},
    				columns: [{
    					xtype: 'templatecolumn',
    					align: 'left',
    					flex: 1,
    					tpl: [
							"<div class=''>" +
								"<div class=''>" +
									"<div class='title-section' style='display: inline; margin-left: 10px;'><b>Title:</b> {TITLE}</div>" +
									"<div class='subject-section' style='display: inline; margin-left: 10px;'><b>Subject:</b> {SUBJECT}</div>" +
									"<div class='status-section' style='display: inline; margin-left: 10px;'><b>Status:</b> {STATUS}</div>" +
								"</div>" +
								"<hr>" +
								"<div class=''>" +
									"<div class='description-label'><b>Description:</b></div>" +
									"<div class='description-section'>{DESCRIPTION}</div>" +
								"</div>" +
							"</div>",
							]
    				},{
    					xtype: 'actioncolumn',
    					align: 'center',
    					items:[{
    						xtype: 'button',
    						iconCls: 'x-fa fa-ellipsis-h',
    						tooltip: 'Details'
    					}]
    				}]
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
    				xtype: 'grid',
    				layout: 'fit',
    				store: {
//    					proxy: {
//    				        type: 'ajax',
//    				        url: '/uber2/main/my-session!displayTutorSessions.action',
//    				        reader: {
//    				            type: 'json',
//    				            rootProperty: 'data',
//    				            totalProperty: 'total'
//    				        }
//    				    },
    					data: [
    					       { 'TITLE': 'Session 1', 'SUBJECT': 'Algebra', 'STATUS': 'PENDING', 'DESCRIPTION':'THIS IS A DESCRIPTION'}
					       ]
    				},
    				columns: [{
    					xtype: 'templatecolumn',
    					align: 'left',
    					flex: 1,
    					tpl: [
							"<div class=''>" +
								"<div class=''>" +
									"<div class='title-section' style='display: inline; margin-left: 10px;'><b>Title:</b> {TITLE}</div>" +
									"<div class='subject-section' style='display: inline; margin-left: 10px;'><b>Subject:</b> {SUBJECT}</div>" +
									"<div class='status-section' style='display: inline; margin-left: 10px;'><b>Status:</b> {STATUS}</div>" +
								"</div>" +
								"<hr>" +
								"<div class=''>" +
									"<div class='description-label'><b>Description:</b></div>" +
									"<div class='description-section'>{DESCRIPTION}</div>" +
								"</div>" +
							"</div>",
							]
    				},{
    					xtype: 'actioncolumn',
    					align: 'center',
    					items:[{
    						xtype: 'button',
    						iconCls: 'x-fa fa-ellipsis-h',
    						tooltip: 'Details'
    					}]
    				}]
    			}]
    		}]
    	}]
    }]
});