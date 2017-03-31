Ext.define('uber.view.login.LoginPage', {
	extend: 'Ext.Panel',
	xtype: 'loginpage',
	items: [{
		xtype: 'tabpanel',
		fullscreen: true,
    	tabBarPosition: 'bottom',
		items: [{
			items: [{
				xtype: 'login'
			},{
				xtype: 'signup'
			}]
		}]
	}]
});