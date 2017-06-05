Ext.define('uber.view.homepage.HomePageController',{
    extend: 'Ext.app.ViewController',
	alias: 'controller.homepage',
	
	
	signup: function () {
		var me = this;
		this.view.destroy();
		var login = Ext.create('uber.view.login.Login');
		var radio = login.down('pageradio').setValue({ab: 1});
		
	},
	
	signin: function () {
		var me = this;
		this.view.destroy();
		var login = Ext.create('uber.view.login.Login');
		var radio = login.down('pageradio').setValue({ab: 2});
	},
	
	
	
});