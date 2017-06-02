Ext.define('uber.view.profile.SearchController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.search',
    
    searchresults: function () {
    	var me = this;
    	var main = me.view.up('app-main');
//    	var main = this.setView('app-main');
//    	mainCard.destroy();
//    	var mainCard = main.lookupReference('mainCardPanel')
//    	var mainLayout = mainCard.getLayout();
//        var card = mainCard.setActiveItem('searchresults');
    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.search.SearchResults'));
    	
    },
    
    onCelldblclick: function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
    	console.log('working');
    	var me = this;
    	var rec = grid.getStore().getAt(rowIndex);
    	console.log(rec.data);
    	var main = me.view.up('app-main');
//    	var main = this.setView('app-main');
//    	mainCard.destroy();
    	
//    	var mainCard = main.lookupReference('mainCardPanel')
//    	var mainLayout = mainCard.getLayout();
//        var card = mainCard.setActiveItem('tutorprofile');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.tutor.TutorProfile'));
    }
})