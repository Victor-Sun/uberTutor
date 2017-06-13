Ext.define('uber.view.session.Sessions',{
	extend: 'Ext.tab.Panel',
	xtype: 'sessions',
	
	layout: 'fit',
	cls: 'test-tab',
	items: [{
		xtype: 'panel',
		title: 'Admin',
		items: [{
			xtype: 'sessionsAdmin',
//			flex: 1,
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