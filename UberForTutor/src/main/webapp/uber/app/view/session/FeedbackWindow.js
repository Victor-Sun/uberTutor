Ext.define('uber.view.session.FeedbackWindow',{
	extend: 'Ext.window.Window',
	itemId: 'feedbackWindow',
	minHeight: 800,
	minWidth: 980,
	layout: 'fit',
	items: [{
		xtype: 'panel',
		layout: {
			type: 'vbox',
			align: 'stretch'
		},
		defaults: {
			margin: 1,
			cls: 'shadow',
		},
		items: [{
			//Tutor Info
			xtype: 'form',
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
				flex: 1,
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
					flex: 1,
					items: [{
						xtype: 'textfield',
						fieldLabel: 'Tutor Name',
						labelAlign: 'top'
					}]
				},{
					xtype: 'fieldcontainer',
					flex: 2,
					layout: {
						type: 'hbox',
					},
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
		},{
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
				//Session Info
				xtype: 'form',
				flex: 1,
				layout: {
					type: 'vbox'
				},
				items: [{
					xtype: 'component',
                	html: '<h3>Session Info</h3>'
				},{
					xtype: 'fieldcontainer',
					defaults: {
						labelAlign: 'top'
					},
					items: [{
						xtype: 'textfield',
						fieldLabel: 'Title'
					},{
						xtype: 'textfield',
					   	fieldLabel: 'Subject'
					},{
						xtype: 'textfield',
						fieldLabel: 'Date Created'
					},{
						xtype: 'textfield',
						fieldLabel: 'Date Accepted'
					},{
						xtype: 'textfield',
						fieldLabel: 'Date Closed'
					},{
						xtype: 'textarea',
						fieldLabel: 'Description'
					}]
				}]
			},{
				xtype: 'feedback',
				flex: 2,
			}]
		}]
	}]
});