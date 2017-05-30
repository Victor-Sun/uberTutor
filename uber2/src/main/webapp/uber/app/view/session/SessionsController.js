Ext.define('uber.view.session.SessionsController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.sessions',
    
//    request: function () {
//    	var me = this;
//    	var main = me.view.up('app-main');
////    	var main = this.setView('app-main');
////    	mainCard.destroy();
//    	var mainCard = main.lookupReference('mainCardPanel')
//    	var mainLayout = mainCard.getLayout();
//        var card = mainCard.setActiveItem('mysessiontutor');
//    },
    
    onCelldblclick: function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
    	var me = this;
//    	var rec = grid.getStore().getAt(rowIndex);
//    	console.log(rec.data);
    	var main = me.view.up('app-main');
    	var mainCard = main.lookupReference('mainCardPanel')
    	var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('mysessiontutor');
    },
    
    feedback: function () {
    	var me = this;
		var main = me.view.up('app-main');
    	var mainCard = main.lookupReference('mainCardPanel')
    	var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('feedback');
    },
    
    mysession: function () {
    	var me = this;
		var main = me.view.up('app-main');
    	var mainCard = main.lookupReference('mainCardPanel')
    	var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('mysession');
    },
    
    student: function () {
    	var me = this;
		var main = me.view.up('app-main');
    	var mainCard = main.lookupReference('mainCardPanel')
    	var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('mysessionstudent');
    },
    
    tutor: function () {
    	var me = this;
		var main = me.view.up('app-main');
    	var mainCard = main.lookupReference('mainCardPanel')
    	var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('mysessiontutor');
    },
    
    admin: function () {
    	var me = this;
		var main = me.view.up('app-main');
    	var mainCard = main.lookupReference('mainCardPanel')
    	var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('mysessionadmin');
    },
    
    sessioninfo: function () {
    	var me = this;
		var main = me.view.up('app-main');
    	var mainCard = main.lookupReference('mainCardPanel')
    	var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('sessioninfo');
    }
})