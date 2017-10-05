Ext.define('uber.view.search.SearchInfoWindow',{
	extend: 'Ext.window.Window',
	xtype: 'searchInfoWindow',
	controller: 'search',
	requestId: '',
	layout: 'fit',
	tools: [{
		xtype: 'button',
		itemId: 'acceptSession',
//		hidden: true,
		text: 'Accept',
		handler: function () {
			debugger;
			var window = this.up('window');
			var form = window.down('#searchInfoForm');
			var requestId = form.down('#requestId').getValue();
//			var grid = Ext.ComponentQuery.query('grid')[0];
//			var store = grid.getStore();
			Ext.getBody().mask('Loading... Please Wait...');
			form.submit({
				url: '/UberTutor/main/my-session!updateRequestToInProcess.action',
				params: {
					requestId:requestId,
				},
    			method: 'POST', 
    			success: function (form, action, response) {
    				debugger;
    				Ext.getBody().unmask();
    				window.grid.getStore().load();
    				window.close();
    				
    			},
    			failure: function (form, action, response) {
    		    	var me = this;
    		    	Ext.getBody().unmask();
    		        var result = uber.util.Util.decodeJSON(action.response.responseText);
    		        Ext.Msg.alert('Error', result.data, Ext.emptyFn);
    			},
			});
		}
	}],
	initComponent: function() {
		var me = this;
		var searchInfoForm = Ext.create('Ext.form.Panel',{
			itemId: 'searchInfoForm',
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
//						xtype: 'textfield',
//						fieldLabel: 'Tutor Name',
//						name: 'TUTOR_NAME'
//					},{
						xtype: 'textfield',
						itemId: 'studentName',
						fieldLabel: 'Student Name',
						name: 'STUDENT_NAME'
					},{
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
					}]
				},{
					xtype: 'fieldcontainer',
					items: [{
//						xtype: 'hidden',
//						name: 'TUTOR_ID'
//					},{
						xtype: 'hidden',
						name: 'STUDENT_ID'
					}]
				},{
//					xtype: 'fieldcontainer',
//					items: [{
//						xtype: 'textfield',
//						fieldLabel: 'Create Date',
//						itemId: 'createDate',
//						name: 'CREATE_DATE',
//						hidden: true,
//						listeners: {
//							change: {
//								fn: function (value) {
//									if (value !== "" || value !== null) {
//										this.setHidden(false);
//									}
//								}
//							}
//						}
//					},{
//						xtype: 'textfield',
//						fieldLabel: 'Pending Date',
//						itemId: 'pendingDate',
//						name: 'PENDING_DATE',
//						hidden: true,
//						listeners: {
//							change: {
//								fn: function (value) {
//									if (value !== "" || value !== null) {
//										this.setHidden(false);
//									}
//								}
//							}
//						}
//					}]
//				},{
//					xtype: 'fieldcontainer',
//					items: [{
//						xtype: 'textfield',
//						fieldLabel: 'Process Date',
//						itemId: 'processDate',
//						name: 'PROCESS_DATE',
//						hidden: true,
//						listeners: {
//							change: {
//								fn: function (value) {
//									if (value !== "" || value !== null) {
//										this.setHidden(false);
//									}
//								}
//							}
//						}
//					},{
//						xtype: 'textfield',
//						fieldLabel: 'Cancel Date',
//						itemId: 'cancelDate',
//						name: 'CANCEL_DATE',
//						hidden: true,
//						listeners: {
//							change: {
//								fn: function (value) {
//									if (value !== "" || value !== null) {
//										this.setHidden(false);
//									}
//								}
//							}
//						}
					}]
//				},{
//					xtype: 'fieldcontainer',
//					items: [{
//						xtype: 'textfield',
//						fieldLabel: 'Close Date',
//						itemId: 'closeDate',
//						name: 'CLOSE_DATE',
//						hidden: true,
//						listeners: {
//							change: {
//								fn: function (value) {
//									if (value !== "" || value !== null) {
//										this.setHidden(false);
//									}
//								}
//							}
//						}
//					}]
//				}]
			}]
		});
		var dateCheck = function () {
			var createDate = searchInfoForm.down('#createDate');
			
			if (createDate.getValue() == null || createDate.getValue() == "") {
				createDate.setValue("");
			} else {
				createDate.setValue(Ext.Date.format(new Date(Ext.decode(createDate.getValue())), 'Y-m-d'));
			};
			
		};
		
//		var buttonCheck = function (){
//			var status = Ext.ComponentQuery.query('#status')[0];
//			var cancel = Ext.ComponentQuery.query('#cancelSession')[0];
//			var close = Ext.ComponentQuery.query('#closeSession')[0];
//			var accept = Ext.ComponentQuery.query('#acceptSession')[0];
//			var windowClose = Ext.ComponentQuery.query('#closeWindow')[0];
//			if (status.value == 'OPEN') {
//				accept.setHidden(false);
//			} else if (status.value == 'PENDING') {
//				cancel.setHidden(false);
//			} else if (status.value == 'IN PROCESS') {
//				close.setHidden(false);
////			} else if (status.value == 'CLOSED') {
////				windowClose.setHidden(false);
//			};
//		};
		
		searchInfoForm.load({
//			model: 'uber.model.SearchInfoWindow',
			url: '/UberTutor/main/search!displayRequestInfo.action',
			params: {
				requestId:this.requestId,
			},
			reader: {
				type: 'json',
				rootProperty: 'data'
			},
			success: function () {
				dateCheck();
			}
		});
		this.items = [
			// Session Info Form
			searchInfoForm
		];
		this.callParent(arguments);
	}
});