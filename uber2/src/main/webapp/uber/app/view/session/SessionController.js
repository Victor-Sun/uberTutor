Ext.define('uber.view.session.SessionController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.session',
	
    sessioninfo: function () {
    	var me = this;
		var main = me.view.up('app-main');
    	var mainCard = main.lookupReference('mainCardPanel')
    	var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('sessioninfo');
    }
});