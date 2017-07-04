Ext.define('uber.view.session.SessionsAdmin',{
	extend: 'Ext.panel.Panel',
	xtype: 'sessionsAdmin',
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
	controller: 'sessions',
	model: 'session',
	initComponent: function () {
		var me = this;
//		var createDate = this.view.query('#sessionInfoForm')[0].records[0].get('CREATE_DATE');
		var sessionInfoForm = Ext.create('Ext.form.Panel',{
			itemId: 'sessionInfoForm',
			scrollable: 'y',
	    	region: 'south',
	    	height: 475,
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
							},{
								
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
								itemId:'createDate',
								editable: false,
								hideTrigger: true,
//								format: 'Y d m'
//								listeners: {
//									change: function (th, oldValue, newValue){
//										var epoch = this.value;
//										var date = Number(epoch);
//								        var dt = new Date(date);
//								        var newDate = Ext.Date.format(dt, 'Y-m-d');
//								        th.setValue(newDate);
//									}
//								}
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
	    	}]
		},{
			xtype: 'container',
			layout: 'border',
			flex: 1,
			items: [{
				xtype: 'sessionAdminGrid',
				region: 'center'
	    	}
//			,
//	    	sessionInfoForm
	    	]
	    }];
		this.tbar = [{
	    	xtype: 'button',
	    	text: 'Session Admin',
	    	handler: 'sessionsAdmin'
	    },{
	    	xtype: 'button',
	    	text: 'Session Tutor',
	    	handler: 'sessionsTutor'
	    },{
	    	xtype: 'button',
	    	text: 'Session Student',
	    	handler: 'sessionsStudent'
    	}];
		this.callParent(arguments);
	}
});