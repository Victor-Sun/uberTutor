Ext.define('uber.view.profile.SearchController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.search',
    
    searchresults: function () {
    	debugger;
    	var me = this;
    	var subject = Ext.ComponentQuery.query('#subject')[0];
//    	var main = me.view.up('app-main');
    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.search.SearchResults'));
		var searchResultGrid = Ext.ComponentQuery.query('#searchresultsgrid')[0];
		
		searchResultGrid.getStore().load({
			url: '/UberTutor/main/search!displayRequests.action',
			params:{
				start: 0,
    	        limit: 5
//				subjectId: subject.value,
//				userId: 
			},
		});
    },
    
    onCelldblclick: function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
    	var me = this;
//    	var rec = grid.getStore().getAt(rowIndex);
//    	console.log(rec.data);
    	var tabpanel = me.view.down('tabpanel');
    	var profile = Ext.create({
    		xtype: 'tutorprofile',
    		title: 'Tutor Profile'
    	});
    	tabpanel.add(profile);
    	tabpanel.setActiveTab(profile);
    	
    }
})