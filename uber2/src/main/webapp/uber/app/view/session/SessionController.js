Ext.define('uber.view.session.SessionController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.session',
	
    sessioninfo: function () {
    	var me = this;
		var main = me.view.up('app-main');
//    	var mainCard = main.lookupReference('mainCardPanel')
//    	var mainLayout = mainCard.getLayout();
//        var card = mainCard.setActiveItem('sessioninfo');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.SessionInfo'));
    }
});