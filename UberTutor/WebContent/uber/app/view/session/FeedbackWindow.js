Ext.define('uber.view.session.FeedbackWindow',{
	extend: 'Ext.window.Window',
	itemId: 'feedbackWindow',
	minHeight: 800,
	minWidth: 980,
	layout: 'fit',
	initComponent: function() {
		
		
		var feedback = Ext.create('uber.view.session.Feedback',{
			flex: 2,
		});
		var sessionInfo = Ext.create('Ext.form.Panel',{
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
					labelAlign: 'top',
					margin: '5 15 0 15',
//					flex: 1
				},
				items: [{
					fieldLabel: 'Title',
					name: 'REQUEST_TITLE'
				},{
				   	fieldLabel: 'Subject',
				   	name: 'SUBJECT'
				},{
					fieldLabel: 'Date Created',
					name: 'CREATE_DATE',
					itemId: 'createDate'
				},{
					fieldLabel: 'Date Accepted',
					name: 'PROCESS_DATE',
					itemId: 'processDate'
				},{
					fieldLabel: 'Date Closed',
					name: 'CLOSE_DATE',
					itemId: 'closeDate'
				},{
					xtype: 'textarea',
					fieldLabel: 'Description',
					name: 'SUBJECT_DESCRIPTION'
				}]
			}]
		});
		var tutorInfo = Ext.create('Ext.form.Panel',{
			cls: 'shadow',
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
//			flex: 2,
			items: [{
				xtype: 'fieldcontainer',
				items: [{
					xtype: 'component',
					html: '<h3>Tutor Info</h3>'
				}]
			},{
				xtype: 'fieldcontainer',
//				flex: 1,
				defaults: {
					cls: 'shadow',
					margin: 1
				},
				layout: {
					type: 'hbox',
					align: 'stretch'
				},
				items: [{
					xtype: 'fieldcontainer',
					layout: {
						type: 'hbox',
						align: 'stretch'
					},
					flex: 1,
					items: [{
						xtype: 'textfield',
						flex: 1,
						margin: '5 15 0 15',
						fieldLabel: 'Tutor Name',
						labelAlign: 'top'
					}]
				},{
					xtype: 'fieldcontainer',
					flex: 2,
					layout: {
						type: 'vbox',
					},
					items: [{
						xtype: 'component',
						margin: '5 5 0 15',
	                	html: 'Rating'
					},{
						xtype: 'fieldcontainer',
						
						layout: {
							type: 'hbox',
						},
						margin: '20 5 5 15',
						items: [{
							xtype: 'rating',
	                		limit: '5',
	                		rounding: '0.5',
						},{
							xtype: 'component',
							html: 'xxx out of xxx'
						}]
					}]
				}]
			}]
		});
		
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
		
		sessionInfo.load({
//			model: 'uber.model.session.SessionInfo',
			url: '/uber2/main/my-session!displaySessionInfo.action',
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
	        tutorInfo,
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
			items: [sessionInfo,feedback],
			dockedItems: [{
				xtype: 'toolbar',
				dock: 'bottom',
				items: ['->',{
					xtype: 'button',
					scale: 'large',
					text: 'Submit',
//					handler: function () {
//						feedback.getForm.submit({
//							
//	 					});
//					}
				},'->']
			}]
		}]
	}]
	this.callParent(arguments);
	}
});