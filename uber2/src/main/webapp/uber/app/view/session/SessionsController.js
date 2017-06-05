Ext.define('uber.view.session.SessionsController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.sessions',
    
    
    onCelldblclick: function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
    	var me = this;
//    	var rec = grid.getStore().getAt(rowIndex);
//    	console.log(rec.data);
    	var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.MySession'));
    },
    
    feedback: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.Feedback'));
    },
    
    mysession: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.MySession'));
    },
    
    student: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.MySessionStudent'));
    },
    
    tutor: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.MySessionTutor'));
    },
    
    admin: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.MySessionAdmin'));
    },
    
    sessioninfo: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.SessionInfo'));
    }
})