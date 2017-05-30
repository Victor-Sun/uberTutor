Ext.define('uber.view.tutor.TutorRegistration',{
	extend: 'Ext.panel.Panel',
	xtype: 'tutorregistration',
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
	items: [{
		xtype: 'panel',
		flex: 1,
		cls: 'uber-panel-inner',
		layout: {
			type: 'vbox',
			align: 'stretch'
		},
		items: [{
			xtype: 'container',
			layout: 'hbox',
			items: [{
				margin: 5,
				html: '<h2>Tutor Registration</h2>'
			}]
		},{
			xtype: 'form',
			layout: {
				type: 'vbox',
				align: 'stretchmax'
			},
			items: [{
				xtype: 'combobox',
				labelAlign: 'top',
				fieldLabel: 'Category'
			}],
		},{
			xtype: 'grid',
			flex: 1,
			columns: [{
				text: 'Subject',
				dataIndex: 'subject',
				flex: 1
			}],
//			dockedItems: [{
//				xtype: 'toolbar',
//				dock: 'bottom',
//				items: [{
//					xtype: 'button',
//					text: 'Submit'
//				},'->',{
//					xtype: 'button',
//					text: "Can't find Subject/Category?",
//					handler: function () {
//						Ext.create('uber.view.tutor.RequestCategory').show();
//					}
//				}]
//			}]
		}]
	}]
});