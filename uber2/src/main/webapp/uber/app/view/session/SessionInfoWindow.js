Ext.define('uber.view.session.SessionInfoWindow',{
	extend: 'Ext.window.Window',
	xtype: 'sessionInfoWindow',
	itemId: 'sessionInfoWindow',
	title: 'Session Info',
	controller: 'sessions',
	requestId: '',
	session: '',
	layout: 'fit',
	closable: false,
	tools: [{
		xtype: 'button',
		itemId: 'acceptSession',
//		hidden: true,
		text: 'accept',
		handler: function () {
			var form = this.up('window').down('form');
			Ext.getBody().mask('Loading... Please Wait...');
			form.submit({
				url: '/uber2/main/my-session-action!updateSessionToInProcess.action',
    			method: 'POST', 
    			success: function () {
    				Ext.getBody().unmask();
    				form.load();
    			},
    		    failure: function () {
    		    	Ext.getBody().unmask();
    		    } 
			});
		}
	},{
		xtype: 'button',
		itemId: 'cancelSession',
//		hidden: true,
		text: 'Cancel Session',
		handler: function () {
			var form = this.up('window').down('form');
			Ext.getBody().mask('Loading... Please Wait...');
			form.submit({
				url: '/uber2/main/my-session-action!updateSessionToCanceled.action',
    			method: 'POST', 
    			success: function () {
    				Ext.getBody().unmask();
    				form.load();
    			},
    		    failure: function () {
    		    	Ext.getBody().unmask();
    		    } 
			});
		}
	},{
		xtype: 'button',
		itemId: 'closeSession',
//		hidden: true,
		text: 'Close Session',
		handler: function () {
			var form = this.up('window').down('form');
			Ext.getBody().mask('Loading... Please Wait...');
			form.submit({
				url: '/uber2/main/my-session-action!updateSessionToClosed.action',
    			method: 'POST', 
    			success: function () {
    				Ext.getBody().unmask();
    				form.load();
    			},
    		    failure: function () {
    		    	Ext.getBody().unmask();
    		    } 
			});
		}
	},{
		xtype: 'button',
		itemId: 'closeWindow',
		text: 'Close Window',
		handler: function () {
			this.up('window').close();
		}
	}],
	initComponent: function() {
		var me = this;
//		me.store = Ext.create('uber.store.session.SessionInfo',{
//			autoLoad: true,
//			model: 'uber.model.session.SessionInfo',
//			proxy: {
//				type: 'ajax',
//				url: '/uber2/main/my-session!displaySessionInfo.action',
//				params: {
//					requestID:this.requestID,
//				},
//				reader: {
//					type: 'json',
//					rootProperty: 'data'
//				},
//				success: function () {
//					var status = Ext.ComponentQuery.query('#status')[0];
//					var cancel = Ext.ComponentQuery.query('#cancelSession')[0];
//					var close = Ext.ComponentQuery.query('#closeSession')[0];
//					var accept = Ext.ComponentQuery.query('#acceptSession')[0];
//					if (status == 'PENDING') {
//						cancel.show();
//					} else if (status == 'IN_PROCESS') {
//						close.show();
//					} else if (status == 'CLOSED') {
//						
//					}	
//				}
//			}
//		});
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
					xtype: 'hidden',
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
						name: 'REQUEST_TITLE'
						
					},{
						xtype: 'textfield',
						fieldLabel: 'Status',
						itemId: 'status',
						name: 'STATUS'
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
						name: 'SUBJECT_DESCRIPTION'
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
				var status = Ext.ComponentQuery.query('#status')[0];
				var cancel = Ext.ComponentQuery.query('#cancelSession')[0];
				var close = Ext.ComponentQuery.query('#closeSession')[0];
				var accept = Ext.ComponentQuery.query('#acceptSession')[0];
				if (createDate.getValue() == null || createDate.getValue() == "") {
					createDate.setValue("");
				} else {
					console.log(Ext.Date.format(new Date(Ext.decode(createDate.getValue())), 'Y-m-d'));
					createDate.setValue(Ext.Date.format(new Date(Ext.decode(createDate.getValue())), 'Y-m-d'));
				}
				if (status == 'PENDING') {
					cancel.show();
				} else if (status == 'IN_PROCESS') {
					close.show();
				} else if (status == 'CLOSED') {
					
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