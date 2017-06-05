Ext.define('uber.view.tutor.ChatController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.chat',
    
    complete: function () {
    	var me = this;
    	var main = me.view.up('app-main');
    	var mainCard = main.lookupReference('mainCardPanel')
    	var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('feedback');
    },
    
})