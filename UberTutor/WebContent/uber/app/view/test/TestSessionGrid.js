Ext.define('uber.view.session.TestSessionGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'testSessionGrid',
	itemId: 'testSessionGrid',
	controller: 'sessions',
	emptyText: "<h3>You currently don't have any sessions</h3>",
	cls: 'shadow',
	
	initComponent: function () {
		var grid = this;
//		var view = grid.getView();
		var page = 5;
		var me = this;
    	me.store =  Ext.create('Ext.data.Store',{
	   		 pageSize: page,
	   		 fields: [ 
	   			 "tutorName", 
	   			 "studentName", 
	   			 "requestTitle", 
	   			 "requestId", 
	   			 "subject", 
	   			 "category", 
	   			 { name: 'createDate', type: 'date', 
	            	convert:function(v,record){
	            		var date = record.data.createDate;
	            		if ( date== null || date == "") {
	            			return "";
	            		} else {
	            			return Ext.Date.format(new Date(date), "Y-m-d, g:i a");
	            		}
	            		
	            	}
	            }, 
	            "status", 
	            "subjectDescription" 
	            ],
	   		 sorters: [{
	   			 property: 'createDate',
	   			 direction: 'ASC'
	   		 }],
	   		 data: [
	   			{
	   				 "studentId":4,
	   				 "requestId":12,
	   				 "subject":"Algebra",
	   				 "subjectDescription":"TEST AGAIN",
	   				 "studentName":"111",
	   				 "requestTitle":"TEST",
	   				 "category":"Math",
	   				 "createDate":1500307056929,
	   				 "status":"OPEN",
	   				 "hasFeedback":"false"
				 },
	   			 {
	   				 "studentId":4,
	   				 "tutorId":2,
	   				 "tutorName":"tutor",
	   				 "requestId":13,
	   				 "subject":"Algebra",
	   				 "subjectDescription":"TEST AGAIN",
	   				 "studentName":"111",
	   				 "requestTitle":"TEST",
	   				 "category":"Algebra",
	   				 "createDate":1500307056929,
	   				 "status":"IN PROCESS",
	   				 "hasFeedback":"false"
				 },
				 {
	   				 "studentId":4,
	   				 "tutorId":2,
	   				 "tutorName":"tutor",
	   				 "requestId":14,
	   				 "subject":"Algebra",
	   				 "subjectDescription":"TEST AGAIN",
	   				 "studentName":"111",
	   				 "requestTitle":"TEST",
	   				 "category":"Calculus",
	   				 "createDate":1500307056929,
	   				 "status":"CLOSED",
	   				 "hasFeedback":"false"
				 }
   			 ],
	   	});
    	var feedbackFormStudent = Ext.create('uber.view.session.Feedback',{
    		dockedItems: [{
    			xtype: 'toolbar',
    			dock: 'bottom',
    			items: ['->',{
    				xtype: 'button',
    				text: 'Reset'
    			},{
    				xtype: 'button',
    				text: 'Submit',
    				handler: function () {
    					var rating = Ext.ComponentQuery.query('#ratingHidden')[0];
				    	var model = Ext.create('uber.model.Feedback', feedbackFormStudent.getValues());
				    	var validation = model.getValidation();
				    	Ext.MessageBox.confirm('Confirm', 'Are you sure you want submit your feedback? This cannot be undone.', function () {
				    		
				    	}, this);
    				}
    			}]
    		}]
    	});
    	var feedbackFormTutor = Ext.create('uber.view.session.Feedback',{
    		dockedItems: [{
    			xtype: 'toolbar',
    			dock: 'bottom',
    			items: ['->',{
    				xtype: 'button',
    				text: 'Reset'
    			},{
    				xtype: 'button',
    				text: 'Submit',
    				handler: function () {
    					var rating = Ext.ComponentQuery.query('#ratingHidden')[0];
				    	var model = Ext.create('uber.model.Feedback', feedbackFormTutor.getValues());
				    	var validation = model.getValidation();
				    	Ext.MessageBox.confirm('Confirm', 'Are you sure you want submit your feedback? This cannot be undone.', function () {
				    		
				    	}, this);
    				}
    			}]
    		}]
    	});
    	var feedbackStudent = Ext.create('Ext.form.FieldSet',{
    		title: 'Feedback Student',
    		itemId: 'feedbackStudent',
    		hidden: true,
        	items: [feedbackFormStudent]
    	});
    	var feedbackTutor = Ext.create('Ext.form.FieldSet',{
    		title: 'Feedback Tutor',
    		itemId: 'feedbackTutor',
    		hidden: true,
        	items: [feedbackFormTutor]
    	});
//    	var test = function () {
//    		var studentName = Ext.ComponentQuery.query('#studentName')[0].getValue();
//			var tutorName = Ext.ComponentQuery.query('#tutorName')[0].getValue();
//			var username = Ext.ComponentQuery.query('#userNameItemId')[0].getText();
//    	
//    		if ( username = tutorName) {
//    			
//    		} 
//    	};
//    	var task = new Ext.util.DelayedTask(function () {
//    		
//    	});
    	me.store.load({
    		params: {
    	        start: 0,
    	        limit: page
    	    },
    	    success: function () {
    		},
    	    failure: function(form, action) {
				Ext.getBody().unmask();
				var result = uber.util.Util.decodeJSON(action.response.responseText);
				Ext.Msg.alert('Error', "An error has occured, please try again", Ext.emptyFn);
//				console.log(result.errors.reason);
			}
    	});
		Ext.apply(this, {
			plugins: [{
				ptype: 'rowwidget',
				pluginId: 'rowwidget',
				widget: {
		            xtype: 'form',
		            itemId: 'rowWidgetForm',
		            bodyPadding: 20,
		            bind: true,
		            items: [
		                {
		                    xtype: 'fieldset',
		                    title: 'Session Details',
		                    itemId: 'sessionDetails',
		                    items: [
		                    	{
			                    	xtype: 'fieldcontainer',
			                    	layout: {
			                    		type: 'hbox'
			                    	},
			                    	items: [
			                    		{
					                    	xtype: 'hidden',
					                    	itemId: 'requestId',
					                    	name: 'requestId',
					                    	bind: {
					                    		value: '{record.requestId}'
					                    	}
			                    		},
			                    		{
			                    			xtype: 'hidden',
			                    			itemId: 'hasFeedback',
			                    			name: 'hasFeedback',
			                    			bind: {
			                    				value: '{record.hasFeedback}'
			                    			}
			                    		},
			                    		{
				                    		xtype: 'textfield',
						                    name: 'studentName',
						                    itemId: 'studentName',
						                    fieldLabel: 'student name',
						                    bind: {
						                        value: '{record.studentName}'
						                    }
						                },
						                {
						                    xtype: 'textfield',
						                    name: 'tutorName',
						                    itemId: 'tutorName',
						                    fieldLabel: 'tutor name',
						                    bind: {
						                        value: '{record.tutorName}'
						                    }
				                    	}
					                ]
			                    },
		                    	{
		                    		xtype: 'fieldcontainer',
		                    		layout: {
		                    			type: 'hbox'
		                    		},
		                    		items: [
		                    			{
					                    	xtype: 'textfield',
					                    	fieldLabel: 'Request Title',
					                    	name: 'requestTitle',
					                    	bind: {
					                    		value: '{record.requestTitle}'
					                    	}
					                    },
					                    {
					                    	xtype: 'textfield',
					                    	fieldLabel: 'Status',
					                    	itemId: 'status',
					                    	name: 'status',
					                    	bind: {
					                    		value: '{record.status}'
					                    	},
					                    	listeners: {
					                    		change: function ( th , newValue , oldValue , eOpts ) {
					                    			debugger;
					                    			var feedbackStudent = Ext.ComponentQuery.query('#feedbackStudent')[0];
					                    			var feedbackTutor = Ext.ComponentQuery.query('#feedbackTutor')[0];
					                    			var hasFeedback = Ext.ComponentQuery.query('#hasFeedback')[0].getValue();
					                    			var studentName = Ext.ComponentQuery.query('#studentName')[0].getValue();
					                    			var tutorName = Ext.ComponentQuery.query('#tutorName')[0].getValue();
					                    			var username = Ext.ComponentQuery.query('#userNameItemId')[0].getText();
					                    			if ( newValue == "CLOSED") {
					                    				feedbackStudent.show();
					                    				feedbackTutor.show();
					                    				//check for student form
//					                    				if ( username == studentName ) {
//					                    					if ( hasFeedback != "true") {
//						                    					feedbackFormTutor.disabled();
//						                    					feedbackFormTutor.addDocked({
//						                    						xtype: 'component',
//						                    						dock: 'top',
//						                    						html: '<p>Tutor has not submitted any feedback yet</p>'
//						                    					});
//						                    					
//						                    				}
//					                    				} else if ( username == tutorName ) {
//					                    					if ( hasFeedback != "true" ) {
//					                    						feedbackFormStudent.disabled();
//					                    						feedbackFormStudent.addDocked({
//					                    							xtype: 'component',
//					                    							dock: 'top',
//					                    							html: '<p>Student has not submitted any feedback yet</p>'
//					                    						});
//					                    					}
//					                    				}
					                    			}
					                    			
//					                    			if ( username == "admin" ) {
//														feedbackTutor.show();
//														feedbackStudent.show();
//					                    			} else if ( newValue == "CLOSED" && username == studentName ) {
//					                    				feedbackStudent.show();
//					                    			} else if ( newValue == "CLOSED" && username == tutorName ){
//					                    				feedbackTutor.show();
//					                    			} else {
//					                    				feedbackStudent.hide();
//														feedbackTutor.hide();
//					                    			}
					                    		}
					                    	}
					                    }
				                    ]
		                    	},
			                    {
			                    	xtype: 'fieldcontainer',
			                    	layout: {
			                    		type: 'hbox'
			                    	},
			                    	items: [{
			                    		xtype: 'textfield',
				                    	fieldLabel: 'Category',
				                    	name: 'category',
				                    	bind: {
				                    		value: '{record.category}'
				                    	}
				                    },
				                    {
				                    	xtype: 'textfield',
				                    	fieldLabel: 'Subject',
				                    	name: 'subject',
				                    	bind: {
				                    		value: '{record.subject}'
				                    	}
			                    	}]
			                    },
			                    {
			                    	xtype: 'fieldcontainer',
			                    	layout: {
			                    		type: 'hbox'
			                    	},
			                    	items: [{
			                    		xtype: 'textarea',
			                    		fieldLabel: 'Description',
			                    		name: 'description',
			                    		bind: {
			                    			value: '{record.subjectDescription}'
			                    		}
			                    	}]
			                    }
			                ]
		                },
		                {
		                	xtype: 'fieldset',
		                	title: 'Contact Info',
		                	itemId: 'contactInfo',
		                	items: [{
		                		xtype: 'fieldcontainer',
		                		layout: {
		                    		type: 'hbox'
		                    	},
		                		items: [{
		                			xtype: 'textfield',
		                			name: 'tutorEmail',
		                			fieldLabel: 'Tutor Email'
		                		},
		                		{
		                			xtype: 'textfield',
		                			name: 'studentEmail',
		                			fieldLabel: 'Student Email'
		                		}]
		                	}]
		                },
		                //Feedback  Section
		                feedbackStudent, feedbackTutor
		            ]
		        },
		        listeners: {
//		        	viewready: function (grid, td, cellIndex, record, tr, rowIndex, e, eOpts, rowNode, record, expandRowNode) {
//		        		debugger;
//		        	}
		        },
		    }],
			columns: [{
				dataIndex: 'requestTitle',
				text: 'Title',
				flex: 1
			},{
				dataIndex: 'studentName',
				text: 'Student',
				flex: 1
			},{
				dataIndex: 'tutorName',
				text: 'Tutor',
				flex: 1
			},{
				dataIndex: 'createDate',
				text: 'Create Date',
				flex: 1
			},{
				dataIndex: 'subject',
				text: 'Subject',
				flex: 1
			},{
				dataIndex: 'status',
				text: 'Status',
				flex: 1
			},{
				xtype: 'actioncolumn',
				align: 'left',
				width: 75,
	    		items: [{
//	    			xtype: 'button',
//	    			itemId: 'details',
//	    			iconCls: 'x-fa fa-archive',
//	    			tooltip: 'Details',
////	    			handler: 'detailClick'
//	    			handler: function (btn, rowIndex, rowIdx, record, grid) {
//	    				debugger;
//	    				btn.up('grid').getPlugin('rowwidget').toggleRow(rowIndex, record);
//	    			}
//	    		},{
	    			xtype: 'button',
	    			itemId: 'cancel',
	    			tooltip: 'Cancel Session',
	    			getClass: function (value, meta, record) {
	                    if(record.get('status') == 'OPEN'){
	                    	return 'x-fa fa-close' ;  
	                    } else {
	                    	return 'x-hidden';
	                    }
	               },
	    		},{
	    			xtype: 'button',
	    			itemId: 'close',
	    			tooltip: 'Close Session',
	    			iconCls: 'x-fa fa-stop',
	    			getClass: function (value, meta, record) {
	                    if(record.get('status') == 'IN PROCESS'){
	                    	return 'x-fa fa-stop' ;  
	                    } else {
	                    	return 'x-hidden';
	                    }
	               },
//	    		},{
//	    			xtype: 'button',
//	    			itemId: 'feedback',
//	    			iconCls: 'x-fa fa-comment',
//	    			tooltip: 'Feedback',
////	    			handler: 'feedbackClick',
//	    			getClass: function (value, meta, record) {
//	                    if(record.get('status') == 'CLOSED'){
//	                    	return 'x-fa fa-comment' ;  
//	                    } else {
//	                    	return 'x-hidden';
//	                    }
//	               },
	    		}]
	    	}],
			dockedItems: [{
				xtype: 'form',
				dock: 'top',
				items: [{
					xtype: 'toolbar',
					dock: 'top',
					defaults: {
						labelWidth: 50,
					},
					items: [{
						xtype: 'component',
						html: '<b>Filters:</b>'
					},' ',{
						xtype: 'datefield',
						name: 'date',
						fieldLabel: 'Date'
					},'-',{
						xtype: 'displayfield',
						fieldLabel: 'Time'
					},{
						xtype: 'numberfield',
						name: 'timeHour',
						maxValue: 12,
						minValue: 1
					},{
						xtype: 'component',
						html: '<b>:</b>'
					},{
						xtype: 'numberfield',
						name: 'timeMinute',
						maxValue: 59,
						minValue: 00
					},{
						xtype: 'combobox',
						name: 'period',
						displayField: 'period',
						valueField: 'period',
						store: {
							fields: ['period'],
							data: [
								{'period': 'AM'},
								{'period': 'PM'}
							]
						},
					}]
				},{
					xtype: 'toolbar',
					dock: 'top',
					defaults: {
						labelWidth: 50,
					},
					items: [{
						xtype: 'combobox',
						name: 'subject',
						fieldLabel: 'Subject'
					},'-',{
						xtype: 'combobox',
						name: 'status',
						fieldLabel: 'Status',
						displayField: 'status',
						valueField: 'status',
						store: {
							fields: ['status'],
							data: [
								{'status': 'OPEN'},
								{'status': 'IN PROCESS'},
								{'status': 'CLOSED'},
								{'status': 'CANCELED'}
							]
						}
					}]
				},{
					xtype: 'toolbar',
					dock: 'top',
					items: ['->',{
						xtype: 'button',
						text: 'Apply',
						handler: function () {
							debugger;
							var me = this;
//							Ext.getBody().mask('Validating... Please Wait...');
							var form = me.up('toolbar').up('form').getForm();
							console.log(Ext.encode(form.getFieldValues()));
//							var store = Ext.create();
//							store.getProxy().extraParams = {
//								searchModel: Ext.encode(form.getFieldValues())
//							};
//							store.load();
//					    	store.on('load', function() {
//					    		me.view.down('grid').setStore(this);
//					    		me.view.down('pagingtoolbar').setStore(this);
//					    		me.view.unmask();
//					    	});
						}
					},{
						xtype: 'button',
						text: 'Reset',
						handler: function () {
							var me = this;
							var form = me.up('toolbar').up('form').getForm();
							form.reset();
						}
//					},{
//						xtype: 'button',
//						text: 'Test',
//						handler: function () {
//							debugger;
//							var grid = Ext.ComponentQuery.query('#testRequestGrid')[0];
//							  // assuming that we need to expand the first column's menu
//							var column = grid.columns[0];
//							var hc = grid.view.headerCt;
//							hc.showMenuBy(column.el.dom, column)
//						}
					}]
				}]
			},{
	    		xtype: 'pagingtoolbar',
	    		displayInfo: true,
	    		displayMsg: 'Displaying sessions {0} - {1} of {2}',
                emptyMsg: "No sessions to display",
	    		dock: 'bottom',
	    		store: me.store
	    	}],
	    	listeners: {
//	        		celldblclick: 'onCelldblclickTutor',
	    			viewready: function (record) {
	    				for (var i = 0; i < grid.getStore().data.length; i++) { 
	    				    var element = Ext.get(grid.getView().getRow(i));
//	    				    var record = grid.getStore().getAt(i);
	    				}
    			}
	    	},
	    	viewConfig:{
	    		listeners: {
	    			expandbody: function (rowNode, record, expandRowNode) {
//	    				debugger;
	    			}
	    		}
	    	}
		});
		this.listeners = {
//    		celldblclick: 'onCelldblclickTutor',
			viewready: function (record) {
				for (var i = 0; i < grid.getStore().data.length; i++) { 
				    var element = Ext.get(grid.getView().getRow(i));
//				    var record = grid.getStore().getAt(i);
				}
			}
    	};
		this.callParent(arguments);
	}
});