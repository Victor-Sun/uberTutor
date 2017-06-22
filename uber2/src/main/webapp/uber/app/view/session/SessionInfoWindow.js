Ext.define('uber.view.session.SessionInfoWindow',{
	extend: 'Ext.window.Window',
	xtype: 'sessionInfoWindow',
	
	title: 'Session Info',
	controller: 'sessions',
	requestId: '',
	session: '',
	layout: 'fit',
	initComponent: function() {
		
		var me = this;
		me.store = Ext.create('uber.store.session.SessionInfo',{
			proxy: {
				type: 'ajax',
				url: '/uber2/main/my-session!displaySessionInfo.action',
				params: {
					requestId:this.requestId,
				},
				reader: {
					type: 'json',
					rootProperty: 'data'
				},
//				success: {
//					if (this.status = IN_PROCESS)
//				}
			},
		});
		var sessionInfoForm = Ext.create('Ext.form.Panel',{
			layout: {
	            type: 'vbox',
	            align: 'stretch'
	        },
			items: [{
				xtype: 'panel',
				padding: 15,
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
						defaults: {
							defaults: {
								labelAlign: 'top'
							},
						},
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
							layout: {
								type: 'hbox',
								align: 'stretch'
							},
							items: [{
								xtype: 'textfield',
								fieldLabel: 'Category',
								name: 'CATEGORY'
							},{
								xtype: 'textfield',
								fieldLabel: 'Subject',
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
								fieldLabel: 'Update Date',
								hidden: true,
								name: 'UPDATE_DATE',
							}]
						},{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								fieldLabel: 'Pending Date',
								name: 'PENDING_DATE',
								hidden: true,
							},{
								xtype: 'textfield',
								fieldLabel: 'Process Date',
								hidden: true,
								name: 'PROCESS_DATE',
							}]
						},{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								fieldLabel: 'Cancel Date',
								hidden: true,
								name: 'CANCEL_DATE',
							},{
								xtype: 'textfield',
								fieldLabel: 'Close Date',
								hidden: true,
								name: 'CLOSE_DATE',
							}]
						}]
					}]
				}]
			}]
		});
		
		sessionInfoForm.load({
			url: '/uber2/main/my-session!displaySessionInfo.action',
			params: {
				requestId:this.requestId,
			},
			reader: {
				type: 'json',
				rootProperty: 'data'
			}
		});
		this.tools = [{
			xtype: 'button',
			hidden: true,
			text: 'cancel'
		},{
			xtype: 'button',
			hidden: true,
			text: 'close'
		},{
			xtype: 'button',
			hidden: true,
			text: 'accept'
		}];
//		me.store.load();
		this.items = [
			// Session Info Form
			sessionInfoForm
		];
		this.callParent(arguments);
	}
});