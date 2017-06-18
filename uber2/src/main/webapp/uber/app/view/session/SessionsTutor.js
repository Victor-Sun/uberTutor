Ext.define('uber.view.session.SessionsTutor',{
	extend: 'Ext.panel.Panel',
	xtype: 'sessionsTutor',
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
	controller: 'sessions',
	initComponent: function () {
		
		var sessionInfoForm = Ext.create('Ext.form.Panel',{
			itemId: 'sessionInfoForm',
			scrollable: 'y',
	    	region: 'south',
	    	height: 450,
	    	split: true,
	    	requestId:'',
	    	collapsible: true,
	    	collapsed: true,
			layout: {
	            type: 'vbox',
	            align: 'stretch'
	        },
			items: [{
				xtype: 'fieldset',
				items: [{
					layout: {
			            type: 'vbox',
			            align: 'stretch'
			        },
					defaults:{
						layout: {
							type: 'vbox',
						},
						defaults: {
							layout: {
								type: 'hbox'
							},
							margin: 5,
						}
					},
					items: [{
						// Request Info
						xtype: 'fieldset',
						items: [{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								fieldLabel: 'Title',
								name: 'TITLE'
							},{
								xtype: 'hidden',
								name: 'REQUEST_ID'
							}]
						},{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								fieldLabel: 'Category',
								name: 'CATEGORY'
							},{
								xtype: 'textfield',
								fieldLabel: 'Subject',
								margin: '0 0 0 5',
								name: 'SUBJECT'
							}]
						},{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textarea',
								fieldLabel: 'Description',
								name: 'DESCRIPTION'
							}]
						}]
					},{
						//Session Info
						xtype: 'fieldset',
						items: [{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								fieldLabel: 'Tutor Name',
								name: 'TUTOR_NAME'
							},{
								xtype: 'textfield',
								fieldLabel: 'Tutor ID',
								name: 'TUTOR_ID'
							}]
						},{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								fieldLabel: 'Student Name',
								name: 'STUDENT_NAME'
							},{
								xtype: 'textfield',
								fieldLabel: 'Tutor ID',
								name: 'STUDENT_ID'
							}]
						},{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								fieldLabel: 'Create Date',
								name: 'CREATE_DATE',
							},{
								xtype: 'textfield',
								fieldLabel: 'UPDATE_DATE',
								name: 'UPDATE_DATE',
							}]
						},{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								fieldLabel: 'PENDING DATE',
								name: 'PENDING_DATE',
							},{
								xtype: 'textfield',
								fieldLabel: 'PROCESS DATE',
								name: 'PROCESS_DATE',
							}]
						},{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								fieldLabel: 'CANCEL_DATE',
								name: 'CANCEL_DATE',
							},{
								xtype: 'textfield',
								fieldLabel: 'CLOSE_DATE',
								name: 'CLOSE_DATE',
							}]
						}]
					}]
				}]
			}]
		});
		
    	this.items = [{
        	xtype: 'panel',
    		flex: 1,
        	cls: 'uber-panel-inner',
        	layout: {
        		type: 'vbox',
        		align: 'stretch'
        	},
        	items: [{
        		xtype: 'toolbar',
    			items: [{
    				xtype: 'button',
    				text: 'Session Info',
    				handler: 'sessioninfo'
    			}]
        	},{
        		xtype: 'container',
                layout: 'hbox',
                items: [{
                    margin: 5,
                    html: '<h2>Sessions</h2>'
                }]
        	},{
        		xtype: 'container',
    			layout: 'border',
    			flex: 1,
    			items: [{
    				xtype: 'sessionsTutorGrid',
    				region: 'center'
    	    	}
//    			,
//    	    	sessionInfoForm
    	    	]
        	}]
        }];
    	this.tbar = [{
//	    	xtype: 'button',
//	    	text: 'Session Admin',
//	    	handler: 'sessionsAdmin'
//	    },{
	    	xtype: 'button',
	    	text: 'Session Student',
	    	handler: 'sessionsStudent'
    	},{
	    	xtype: 'button',
	    	text: 'Session Tutor',
	    	handler: 'sessionsTutor'
	    
    	}];
    	this.callParent(arguments);
    }
});