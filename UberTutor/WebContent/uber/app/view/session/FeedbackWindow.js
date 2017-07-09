Ext.define('uber.view.session.FeedbackWindow',{
	extend: 'Ext.window.Window',
	itemId: 'feedbackWindow',
	minHeight: 750,
	minWidth: 980,
	layout: 'fit',
	requestId: '',
	initComponent: function() {
		var dateCheck = function () {
			var createDate = sessionInfo.down('#createDate');
			var processDate = sessionInfo.down('#processDate');
			var closeDate = sessionInfo.down('#closeDate');
			
			if (createDate.getValue() == null || createDate.getValue() == "") {
				createDate.setValue("");
			} else {
				createDate.setValue(Ext.Date.format(new Date(Ext.decode(createDate.getValue())), 'Y-m-d'));
			};
			
			if (processDate.getValue() == null || processDate.getValue() == "") {
				processDate.setValue("");
			} else {
				processDate.setValue(Ext.Date.format(new Date(Ext.decode(processDate.getValue())), 'Y-m-d'));
			};
			
			if (closeDate.getValue() == null || closeDate.getValue() == "") {
				closeDate.setValue("");
			} else {
				closeDate.setValue(Ext.Date.format(new Date(Ext.decode(closeDate.getValue())), 'Y-m-d'));
			};
		};
		
		var feedback = Ext.create('uber.view.session.Feedback',{
			flex: 2,
		});
		var sessionInfo = Ext.create('Ext.form.Panel',{
			itemId: 'sessionInfo',
			flex: 1,
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			items: [{
				xtype: 'component',
            	html: '<h3>Session Info</h3>'
			},{
				xtype: 'fieldcontainer',
				layout: {
					type: 'vbox',
					align: 'stretch'
				},
				defaults: {
					xtype: 'textfield',
					readOnly: true,
					labelAlign: 'top',
					margin: '5 15 0 15',
//					flex: 1
				},
				items: [{
					fieldLabel: 'Tutor Name',
					name: 'TUTOR_NAME',
				},{
					fieldLabel: 'Title',
					name: 'REQUEST_TITLE'
				},{
				   	fieldLabel: 'Subject',
				   	name: 'SUBJECT'
				},{
					fieldLabel: 'Date Created',
					name: 'CREATE_DATE',
					itemId: 'createDate',
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
					fieldLabel: 'Date Accepted',
					name: 'PROCESS_DATE',
					itemId: 'processDate',
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
					fieldLabel: 'Date Closed',
					name: 'CLOSE_DATE',
					itemId: 'closeDate',
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
					xtype: 'textarea',
					height: 125,
					fieldLabel: 'Description',
					name: 'DESCRIPTION'
				}]
			}]
		});
//		var tutorInfo = Ext.create('Ext.form.Panel',{
//			itemId: 'tutorInfo',
//			cls: 'shadow',
//			layout: {
//				type: 'vbox',
//				align: 'stretch'
//			},
////			flex: 2,
//			items: [{
//				xtype: 'fieldcontainer',
//				items: [{
//					xtype: 'component',
//					html: '<h3>Tutor Info</h3>'
//				}]
//			},{
//				xtype: 'fieldcontainer',
////				flex: 1,
//				layout: {
//					type: 'vbox',
//					align: 'stretch'
//				},
//				items: [{
//					xtype: 'fieldcontainer',
//					layout: {
//						type: 'hbox',
//						align: 'stretch'
//					},
//					flex: 1,
//					items: [{
//						xtype: 'textfield',
//						flex: 1,
//						margin: '5 15 0 15',
//						fieldLabel: 'Tutor Name',
//						readOnly: true,
//						labelAlign: 'top'
//					}]
//				},{
//					xtype: 'fieldcontainer',
//					flex: 2,
//					layout: {
//						type: 'vbox',
//					},
//					items: [{
////						xtype: 'component',
////						margin: '5 5 0 15',
////	                	html: 'Average Rating'
////					},{
////						xtype: 'fieldcontainer',
////						
////						layout: {
////							type: 'hbox',
////						},
////						margin: '20 5 5 15',
////						items: [{
//////							xtype: 'rating',
//////	                		limit: '5',
//////	                		rounding: '0.5',
//////						},{
//////							xtype: 'component',
//////							html: 'xxx out of xxx'
////						}]
//					}]
//				}]
//			}]
//		});
		
		feedback.load({
	    	model: 'uber.model.session.Feedback',
	    	url: '/UberTutor/main/feedback!displayFeedbackInfo.action',
			params: {
				requestId: this.requestId
			},
			reader: {
				type: 'json',
				rootProperty: 'data'
			}
	    });
		sessionInfo.load({
			model: 'uber.model.session.SessionInfo',
			url: '/UberTutor/main/my-session!displaySessionInfo.action',
			params: {
				requestId: this.requestId,
			},
			reader: {
				type: 'json',
				rootProperty: 'data'
			},
			success: function () {
				dateCheck();
			}
		});
		
		this.items = [{
		xtype: 'panel',
		layout: {
			type: 'vbox',
			align: 'stretch'
		},
		defaults: {
			margin: 1,
			cls: 'shadow',
		},
		items: [
	        {
			//Session Info & Feedback
			xtype: 'panel',
			layout: {
				type: 'hbox',
				align: 'stretch'
			},
			defaults: {
				cls: 'shadow',
				margin: 1
			},
			flex: 4,
			items: [{
				xtype: 'container',
				layout: {
					type: 'vbox',
				},
				items: [
//				tutorInfo,
				sessionInfo
				]
			},{
				xtype: 'panel',
				flex: 1,
				layout: {
					type: 'vbox',
					align: 'stretch'
				},
				items: [
//				{
//					xtype: 'container',
//					flex: 1,
//				}
				,feedback,
//				{
//					xtype: 'container',
//					flex: 1,
//				}
				],
				dockedItems: [{
					xtype: 'toolbar',
					dock: 'bottom',
					items: ['->',{
						xtype: 'button',
						scale: 'large',
						text: 'Submit',
						handler: function () {
//							debugger;
							feedback.submit({
								url: '/UberTutor/main/feedback!save.action',
								params: {
									requestId:feedback.down('#requestId').getValue(),
									rating: feedback.down('#rating').getValue(),
									feedback: feedback.down('#feedback').getValue()
								},
								reader: {
									type: 'json',
									rootProperty: 'data'
								},
								success: function() {
									debugger;
								},
								failure: function(form, action) {
									debugger;
									var me = this;
							    	Ext.getBody().unmask();
							        var result = uber.util.Util.decodeJSON(action.response.responseText);
							        Ext.Msg.alert('Error', result.data, Ext.emptyFn);
								}
							});
//							if () {
//								console.log('A feedback has already been submitted for this session');
//							} else if () {
//								feedback.submit({
//									url: '/UberTutor/main/feedback!save.action',
//								});
//							}
						}
					}]
				}]
			}]
		}]
	}];
	this.callParent(arguments);
	}
});