Ext.define('uber.view.session.SessionInfoWindow',{
	extend: 'Ext.window.Window',
	xtype: 'sessionInfoWindow',
	
	title: 'Session Info',
	controller: 'sessions',
	layout: 'fit',
	initComponent() {
		
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
				}
			},
		});
		var sessionInfoForm = Ext.create('Ext.form.Panel',{
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
							type: 'hbox',
						}
					},
					items: [{
						xtype: 'fieldcontainer',
						items: [{
							xtype: 'textfield',
							fieldLabel: 'Title',
							name: 'TITLE'
						},{
							xtype: 'textfield',
							fieldLabel: 'Tutor Name',
							name: 'TUTOR_NAME'
						},{
							xtype: 'textfield',
							fieldLabel: 'Student Name',
							name: 'STUDENT_NAME'
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
							name: 'SUBJECT'
						}]
					},{
						xtype: 'fieldcontainer',
						items: [{
							xtype: 'textfield',
							fieldLabel: 'Create Date',
							name: 'CREATE_DATE',
							listeners: {
								change: 'valueCheck'
							}
						},{
							xtype: 'textfield',
							fieldLabel: 'PENDING DATE',
							name: 'PENDING_DATE',
							hidden: true,
							listeners: {
								change: 'valueCheck'
							}
						},{
							xtype: 'textfield',
							fieldLabel: 'PROCESS DATE',
							hidden: true,
							name: 'PROCESS_DATE',
							listeners: {
								change: 'valueCheck'
							}
						}]
					},{
						xtype: 'fieldcontainer',
						items: [{
							xtype: 'textfield',
							fieldLabel: 'CANCEL_DATE',
							hidden: true,
							name: 'CANCEL_DATE',
							listeners: {
								change: 'valueCheck'
							}
						},{
							xtype: 'textfield',
							fieldLabel: 'CLOSE_DATE',
							hidden: true,
							name: 'CLOSE_DATE',
							listeners: {
								change: 'valueCheck'
							}
						},{
							xtype: 'textfield',
							fieldLabel: 'UPDATE_DATE',
							hidden: true,
							name: 'UPDATE_DATE',
							listeners: {
								change: 'valueCheck'
							}
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
		
//		me.store.load();
		this.items = [
			// Session Info Form
			sessionInfoForm
		];
		this.callParent(arguments);
	}
});