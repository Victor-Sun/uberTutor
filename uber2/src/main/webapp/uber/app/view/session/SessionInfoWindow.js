Ext.define('uber.view.session.SessionInfoWindow',{
	extend: 'Ext.window.Window',
	xtype: 'sessionInfoWindow',
	itemId: 'sessionInfoWindow',
	title: 'Session Info',
	controller: 'sessions',
	requestId: '',
	session: '',
	layout: 'fit',
//	closable: false,
	tools: [{
		xtype: 'button',
		itemId: 'acceptSession',
		text: 'accept'
	},{
		xtype: 'button',
		itemId: 'cancelSession',
		text: 'cancel'
	},{
		xtype: 'button',
		itemId: 'closeSession',
		text: 'close'
	}],
	initComponent: function() {
		var me = this;
//		me.store = Ext.create('uber.store.session.SessionInfo',{
//		autoLoad: true,
//			model: 'uber.model.session.SessionInfo',
//			proxy: {
//				type: 'ajax',
//				url: '/uber2/main/my-session!displaySessionInfo.action',
//				params: {
//					requestId:this.requestId,
//				},
//				reader: {
//					type: 'json',
//					rootProperty: 'data'
//				},
////				success: {
////					if (this.status = IN_PROCESS)
////				}
//			},
////		});
		var sessionInfoForm = Ext.create('Ext.form.Panel',{
			layout: {
	            type: 'vbox',
	            align: 'stretch'
	        },
	        defaults: {
	        	layout: {
		            type: 'vbox',
		            align: 'stretch'
		        },
	        	margin: 5,
	        },
			items: [{
				// Request Info
				xtype: 'fieldset',
				defaults: {
					defaults: {
//						labelAlign: 'top',
						readOnly: true,
						margin: 5,
					},
				},
				items: [{
					xtype: 'hiddenfield',
					itemId: 'requestId',
					name: 'requestId',
				},{
					xtype: 'fieldcontainer',
					layout: {
						type: 'hbox',
						align: 'stretch'
					},
					items:[{
						xtype: 'textfield',
						fieldLabel: 'Title',
						name: 'TITLE'
						
					},{
						xtype: 'textfield',
						fieldLabel: 'Status',
						itemId: 'status',
						name: 'STATUS'
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
					layout: {
						type: 'hbox',
						align: 'stretch'
					},
					items: [{
						xtype: 'textarea',
						flex: 1,
						readOnly: true,
	//					labelAlign: 'top',
						fieldLabel: 'Description',
						name: 'DESCRIPTION'
					}]
				}]
			},{
				//Session Info
				xtype: 'fieldset',
				defaults: {
					layout: {
						type: 'hbox',
						align: 'stretch'
					},
					defaults: {
//						labelAlign: 'top',
						readOnly: true,
						margin: 2,
					},
				},
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
						fieldLabel: 'Student ID',
						name: 'STUDENT_ID'
					}]
				},{
					xtype: 'fieldcontainer',
					items: [{
						xtype: 'textfield',
						fieldLabel: 'Create Date',
						itemId: 'createDate',
						name: 'CREATE_DATE',
						hidden: true,
						listeners: {
							afterrender: {
								fn: function (value) {
//									debugger;
									if (value !== "" || value !== null) {
										this.setHidden(false);
									}
								}
							}
						}
					},{
						xtype: 'textfield',
						fieldLabel: 'Update Date',
//						hidden: true,
						name: 'UPDATE_DATE',
						hidden: true,
						listeners: {
							change: {
								fn: function (value) {
//									debugger;
									if (value !== "" || value !== null) {
										this.setHidden(false);
									}
								}
							}
						}
					}]
				},{
					xtype: 'fieldcontainer',
					items: [{
						xtype: 'textfield',
						fieldLabel: 'Pending Date',
						name: 'PENDING_DATE',
						hidden: true,
						listeners: {
							change: {
								fn: function (value) {
//									debugger;
									if (value !== "" || value !== null) {
										this.setHidden(false);
									}
								}
							}
						}
					},{
						xtype: 'textfield',
						fieldLabel: 'Process Date',
//						hidden: true,
						name: 'PROCESS_DATE',
						hidden: true,
						listeners: {
							change: {
								fn: function (value) {
//									debugger;
									if (value !== "" || value !== null) {
										this.setHidden(false);
									}
								}
							}
						}
					}]
				},{
					xtype: 'fieldcontainer',
					items: [{
						xtype: 'textfield',
						fieldLabel: 'Cancel Date',
						name: 'CANCEL_DATE',
						hidden: true,
						listeners: {
							change: {
								fn: function (value) {
//									debugger;
									if (value !== "" || value !== null) {
										this.setHidden(false);
									}
								}
							}
						}
					},{
						xtype: 'textfield',
						fieldLabel: 'Close Date',
						name: 'CLOSE_DATE',
						hidden: true,
						listeners: {
							change: {
								fn: function (value) {
//									debugger;
									if (value !== "" || value !== null) {
										this.setHidden(false);
									}
								}
							}
						}
					}]
				}]
			}]
		});
		
		sessionInfoForm.load({
			model: 'uber.model.session.SessionInfo',
			url: '/uber2/main/my-session!displaySessionInfo.action',
			params: {
				requestId:this.requestId,
			},
			reader: {
				type: 'json',
				rootProperty: 'data'
			},
			success: function () {
				var createDate = sessionInfoForm.down('#createDate');
				if (createDate.getValue() == null) {
					createDate.setValue("");
				} else {
					createDate.setValue(Ext.Date.format(new Date(Ext.decode(createDate.getValue())), 'Y-m-d'));
				}
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