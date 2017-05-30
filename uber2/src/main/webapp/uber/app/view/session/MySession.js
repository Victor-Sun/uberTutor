Ext.define('uber.view.session.MySession',{
	extend: 'Ext.tab.Panel',
	xtype: 'mysession',
	
	controller: '',
	layout: 'fit',
	items: [{
		xtype: 'mysessionstudent',
		title: 'Student'
	},{
		xtype: 'mysessiontutor',
		title: 'Tutor'
	},{
		xtype: 'mysessionadmin',
		title: 'Admin'
	}]
});