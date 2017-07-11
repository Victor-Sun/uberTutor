/**
 * This class is the controller for the main view for the application. It is specified as
 * the "controller" of the Main view class.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('uber.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    alias: 'controller.main',

    onItemSelected: function (sender, record) {
        Ext.Msg.confirm('Confirm', 'Are you sure?', 'onConfirm', this);
    },

    onConfirm: function (choice) {
        if (choice === 'yes') {
            //
        }
    },
    
    registration: function () {
    	var me = this;
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
        var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.tutor.TutorRegistration'));
    },
    
    mainpage: function () {
    	var me = this;
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
        var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.main.MainPage'));
    },
    
    profile: function() {
		var me = this;
		var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.profile.Profile'));
    },
    
    search: function() {
    	debugger;
    	var me = this;
		var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.search.SearchResults'));
		var searchResultGrid = Ext.ComponentQuery.query('#searchresultsgrid')[0];
		
		searchResultGrid.getStore().load({
			url: '/UberTutor/main/search!displayRequests.action',
//			params:{
//				subjectId: subject.value,
////				userId: 
//			},
		});
    },
    
    changepassword: function() {
    	var me = this;
    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.password.ChangePassword'));
    },
    
    sessions: function () {
    	var me = this;
    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.SessionsStudent'));
    },
    
    makeRequest: function () {
    	var me = this;
    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.tutor.MakeRequest'));
    },
    
    test: function () {
    	var me = this;
    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.test.News'));
    },
    
    openRequest: function () {
    	var me = this;
    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.tutor.OpenRequest'));
    },
    
    logout: function() {
		var me = this;
		this.getView().destroy();
		var login = Ext.create('uber.view.login.Login');
		var radio = login.down('pageradio').setValue({ab: 2});
    }
});
