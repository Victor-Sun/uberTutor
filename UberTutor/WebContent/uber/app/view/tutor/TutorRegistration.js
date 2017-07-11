Ext.define('uber.view.tutor.TutorRegistration',{
	extend: 'Ext.panel.Panel',
	xtype: 'tutorRegistration',
	itemId: 'tutorRegistration',
	
	requires: ['uber.view.tutor.TutorRegistrationGrid'],
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
//	cls: 'uber-panel',
	items: [{
		xtype: 'panel',
		flex: 1,
//		cls: 'uber-panel-inner',
		layout: {
			type: 'vbox',
			align: 'stretch'
		},
		items: [{
			xtype: 'panel',
//			border: true,
//			cls: 'uber-header',
			layout: 'hbox',
			items: [{
				xtype: 'container',
				items: [{
					margin: 5,
					html: '<h2>Tutor Subjects</h2>'
				}]
			}]
		},{
			xtype: 'tutorRegistrationGrid',
			margin: 5,
			flex: 1
		}]
	}]
});