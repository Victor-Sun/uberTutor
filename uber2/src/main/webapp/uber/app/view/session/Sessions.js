Ext.define('uber.view.session.Sessions',{
	extend: 'Ext.tab.Panel',
	xtype: 'sessions',
	
	layout: 'fit',
	cls: 'test-tab',
	items: [{
		xtype: 'panel',
		title: 'Admin',
		style: {
			backgroundColor: '#f1f2f4',
		},
		items: [{
			xtype: 'sessionsAdmin',
			flex: 1,
		}]
	},{
		xtype: 'sessionsStudent',
		title: 'Student'
	},{
		xtype: 'sessionsTutor',
		title: 'Tutor'
	}]
});