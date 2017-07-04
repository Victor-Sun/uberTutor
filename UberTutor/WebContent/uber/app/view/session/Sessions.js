Ext.define('uber.view.session.Sessions',{
	extend: 'Ext.tab.Panel',
	xtype: 'sessions',
	
	layout: 'fit',
	cls: 'test-tab',
	items: [{
		xtype: 'panel',
		style: {
			'background-color': '#f1f2f4'
		},
		title: 'Admin',
		items: [{
			xtype: 'sessionsAdmin',
		}]
	},{
		xtype: 'panel',
		title: 'Tutor',
		items: [{
			xtype: 'sessionsTutor',
		}]
	},{
		xtype: 'panel',
		title: 'Student',
		items: [{
			xtype: 'sessionsStudent',
		}]
	}]
});