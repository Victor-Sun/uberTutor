Ext.define('uber.view.tutor.TutorProfile',{
	extend: 'Ext.panel.Panel',
	xtype: 'tutorprofile',
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'profile-container',
	items: [{
		xtype: 'panel',
		flex: 1,
		cls: 'profile-panel',
		layout: {
			type: 'vbox',
			align: 'stretch'
		},
		items: [{
			xtype: 'container',
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			items: [{
				xtype: 'container',
				layout: {
					type: 'hbox',
					algin: 'stretch'
				},
				items: [{
					xtype: 'container',
					items: [{
						xtype: 'image',
						padding: 10,
						width: 80,
						height: 80
					}]
				},{
					xtype: 'container',
					items: [{
						html: 'Joe Smoe'
					},{
						xtype: 'component'
					}]
				}]
			},{
				xtype: 'container',
				type: 'vbox',
				items: [{
					xtype: 'htmleditor'
				},{
					xtype: 'textarea'
				}]
			}]
		}],
		dockedItems: [{
			xtype: 'toolbar',
			dock: 'bottom',
			items: [{
				xtype: 'button',
				text: 'Connect'
			}]
		}]
	}]
});