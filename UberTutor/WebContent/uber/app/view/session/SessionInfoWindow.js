Ext.define('uber.view.session.SessionInfoWindow',{
	extend: 'Ext.window.Window',
	xtype: 'sessionInfoWindow',
	itemId: 'sessionInfoWindow',
	title: 'Session Info',
	controller: 'sessions',
	requestId: '',
	session: '',
	layout: 'fit',
	tools: [{
		xtype: 'button',
		itemId: 'acceptSession',
		hidden: true,
		text: 'Accept',
		handler: function () {
			var window = this.up('window');
			var form = window.down('#sessionInfoForm');
			var requestId = form.down('#requestId').getValue();
			var grid = Ext.ComponentQuery.query('grid')[0];
			var store = grid.getStore();
			Ext.getBody().mask('Loading... Please Wait...');
			form.submit({
				url: '/UberTutor/main/my-session!updateRequestToInProcess.action',
				params: {
					requestId:requestId,
				},
    			method: 'POST', 
    			success: function () {
    				Ext.getBody().unmask();
    				grid.getStore().load();
    				window.close();
    				
    			},
    		    failure: function () {
    		    	Ext.getBody().unmask();
    		    } 
			});
		}
	},{
		xtype: 'button',
		itemId: 'cancelSession',
		hidden: true,
		text: 'Cancel Session',
		handler: function () {
			var window = this.up('window');
			var form = window.down('#sessionInfoForm');
			var requestId = form.down('#requestId').getValue();
			var grid = Ext.ComponentQuery.query('grid')[0];
			var store = grid.getStore();
			Ext.getBody().mask('Loading... Please Wait...');
			form.submit({
				url: '/UberTutor/main/my-session!updateRequestToCanceled.action',
				params: {
					requestId:requestId,
				},
    			method: 'POST', 
    			success: function () {
    				Ext.getBody().unmask();
    				grid.getStore().load();
    				window.close();
    			},
    			failure: function(form, action) {
    				Ext.getBody().unmask();
//    				var result = uber.util.Util.decodeJSON(action.response.responseText);
    				Ext.Msg.alert('Error', "An error has occured, please try again", Ext.emptyFn);
//    				console.log(result.errors.reason);
    			},
			});
		}
	},{
		xtype: 'button',
		itemId: 'closeSession',
		hidden: true,
		text: 'Close Session',
		handler: function () {
			var window = this.up('window');
			var form = window.down('#sessionInfoForm');
			var requestId = form.down('#requestId').getValue();
			var grid = Ext.ComponentQuery.query('grid')[0];
			var store = grid.getStore();
			Ext.getBody().mask('Loading... Please Wait...');
			form.submit({
				url: '/UberTutor/main/my-session!updateRequestToClosed.action',
				params: {
					requestId:requestId,
				},
    			method: 'POST', 
    			success: function () {
    				Ext.getBody().unmask();
    				grid.getStore().load();
    				window.close();
    				
    			},
    			failure: function(form, action) {
    				Ext.getBody().unmask();
//    				var result = uber.util.Util.decodeJSON(action.response.responseText);
    				Ext.Msg.alert('Error', "An error has occured, please try again", Ext.emptyFn);
//    				console.log(result.errors.reason);
    			},
			});
		}
	}],
	initComponent: function() {
		var me = this;
		var sessionInfoForm = Ext.create('Ext.form.Panel',{
			itemId: 'sessionInfoForm',
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
					name: 'REQUEST_ID',
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
						itemId: 'tutorName',
						fieldLabel: 'Tutor Name',
						name: 'TUTOR_NAME'
					},{
						xtype: 'textfield',
						itemId: 'studentName',
						fieldLabel: 'Student Name',
						name: 'STUDENT_NAME'
					}]
				},{
					xtype: 'fieldcontainer',
					items: [{
						xtype: 'hidden',
						name: 'TUTOR_ID'
					},{
						xtype: 'hidden',
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
						fieldLabel: 'Pending Date',
						itemId: 'pendingDate',
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
					}]
				},{
					xtype: 'fieldcontainer',
					items: [{
						xtype: 'textfield',
						fieldLabel: 'Process Date',
						itemId: 'processDate',
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
					},{
						xtype: 'textfield',
						fieldLabel: 'Cancel Date',
						itemId: 'cancelDate',
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
					}]
				},{
					xtype: 'fieldcontainer',
					items: [{
						xtype: 'textfield',
						fieldLabel: 'Close Date',
						itemId: 'closeDate',
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
		var dateCheck = function () {
			var createDate = sessionInfoForm.down('#createDate');
			var pendingDate = sessionInfoForm.down('#pendingDate');
			var processDate = sessionInfoForm.down('#processDate');
			var cancelDate = sessionInfoForm.down('#cancelDate');
			var closeDate = sessionInfoForm.down('#closeDate');
			
			if (createDate.getValue() == null || createDate.getValue() == "") {
				createDate.setValue("");
			} else {
				createDate.setValue(Ext.Date.format(new Date(Ext.decode(createDate.getValue())), 'Y-m-d'));
			};
			
			if (pendingDate.getValue() == null || pendingDate.getValue() == "") {
				pendingDate.setValue("");
			} else {
				pendingDate.setValue(Ext.Date.format(new Date(Ext.decode(pendingDate.getValue())), 'Y-m-d'));
			};
			
			if (processDate.getValue() == null || processDate.getValue() == "") {
				processDate.setValue("");
			} else {
				processDate.setValue(Ext.Date.format(new Date(Ext.decode(processDate.getValue())), 'Y-m-d'));
			};
			
			if (cancelDate.getValue() == null || cancelDate.getValue() == "") {
				cancelDate.setValue("");
			} else {
				cancelDate.setValue(Ext.Date.format(new Date(Ext.decode(cancelDate.getValue())), 'Y-m-d'));
			};
			
			if (closeDate.getValue() == null || closeDate.getValue() == "") {
				closeDate.setValue("");
			} else {
				closeDate.setValue(Ext.Date.format(new Date(Ext.decode(closeDate.getValue())), 'Y-m-d'));
			};
		};
		
		var buttonCheck = function (){
			var userNameItemId = Ext.ComponentQuery.query('#userNameItemId')[0];
			var studentName = Ext.ComponentQuery.query('#studentName')[0];
			var tutorName = Ext.ComponentQuery.query('#tutorName')[0];
			var status = Ext.ComponentQuery.query('#status')[0];
			var cancel = Ext.ComponentQuery.query('#cancelSession')[0];
			var close = Ext.ComponentQuery.query('#closeSession')[0];
			var accept = Ext.ComponentQuery.query('#acceptSession')[0];
			if (status.value == 'OPEN') {
				cancel.setHidden(false);
//				if (studentName.value != userNameItemId.text) {
//					accept.setHidden(false);
//				} 
			} else if (status.value == 'PENDING') {
				if ( tutorName.value != userNameItemId.text ) {
					cancel.setHidden(false);
				}
			} else if (status.value == 'IN PROCESS') {
				if ( tutorName.value != userNameItemId.text ) {
					close.setHidden(false);
				}
//			} else if (status.value == 'CLOSED') {
//				windowClose.setHidden(false);
			};
		};
		
		sessionInfoForm.load({
			model: 'uber.model.session.SessionInfo',
			url: '/UberTutor/main/my-session!displayRequestInfo.action',
			params: {
				requestId:this.requestId,
			},
			reader: {
				type: 'json',
				rootProperty: 'data'
			},
			success: function () {
				dateCheck();
				buttonCheck();
			},
			failure: function(form, action) {
				Ext.getBody().unmask();
//				var result = uber.util.Util.decodeJSON(action.response.responseText);
				Ext.Msg.alert('Error', "An error has occured, please try again", Ext.emptyFn);
//				console.log(result.errors.reason);
			},
		});
//		me.store.load();
		this.items = [
			// Session Info Form
			sessionInfoForm
		];
		this.callParent(arguments);
	}
});