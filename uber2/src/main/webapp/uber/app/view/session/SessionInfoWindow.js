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
								name: 'title'
							},{
								xtype: 'hidden',
								name: 'requestId'
							}]
						},{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								fieldLabel: 'Category',
								name: 'category'
							},{
								xtype: 'textfield',
								fieldLabel: 'Subject',
								name: 'subject'
							}]
						},{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textarea',
								fieldLabel: 'Description',
								name: 'description'
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
								name: 'tutorName'
							},{
								xtype: 'textfield',
								fieldLabel: 'Tutor ID',
								name: 'tutorId'
							}]
						},{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								fieldLabel: 'Student Name',
								name: 'studentName'
							},{
								xtype: 'textfield',
								fieldLabel: 'Tutor ID',
								name: 'studentId'
							}]
						},{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								fieldLabel: 'Create Date',
								name: 'createDate',
							},{
								xtype: 'textfield',
								fieldLabel: 'Update Date',
								hidden: true,
								name: 'updateDate',
							}]
						},{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								fieldLabel: 'Pending Date',
								name: 'pendingDate',
								hidden: true,
							},{
								xtype: 'textfield',
								fieldLabel: 'Process Date',
								hidden: true,
								name: 'processDate',
							}]
						},{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								fieldLabel: 'Cancel Date',
								hidden: true,
								name: 'canceDate',
							},{
								xtype: 'textfield',
								fieldLabel: 'Close Date',
								hidden: true,
								name: 'closeDate',
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