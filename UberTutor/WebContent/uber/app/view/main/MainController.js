/**
 * This class is the controller for the main view for the application. It is specified as
 * the "controller" of the Main view class.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('uber.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    alias: 'controller.main',

//    onItemSelected: function (sender, record) {
//        Ext.Msg.confirm('Confirm', 'Are you sure?', 'onConfirm', this);
//    },
//
//    onConfirm: function (choice) {
//        if (choice === 'yes') {
//            //
//        }
//    },
    
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
		
		var userName = Ext.ComponentQuery.query('#userNameItemId')[0].text;
		var search = Ext.ComponentQuery.query('#menuItemSearch')[0];
		Ext.Ajax.request({
			url: '/UberTutor/main/profile!display.action',
			params: {
				fullname: userName
			},
			success: function(response, opts) {
				var obj = Ext.decode(response.responseText);
				var tutor = obj.data.IS_TUTOR;
				var tutorCheck = Ext.ComponentQuery.query('#isTutorCheck')[0].setValue(tutor);
				var currentRequests = Ext.ComponentQuery.query('#currentRequests')[0];
				var openRequests = Ext.ComponentQuery.query('#openRequests')[0];
//				console.dir(obj);
				if (tutor == "Y") {
					search.show();
					currentRequests.show();
					openRequests.show();
				}
				
			},
			failure: function(response, opts) {
//				console.log('server-side failure with status code ' + response.status);
			}
		});
    },
    
    profile: function() {
		var me = this;
		var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.profile.Profile'));
    },
    
    search: function() {
    	var me = this;
		var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.search.Search'));
		var searchGrid = Ext.ComponentQuery.query('#searchgrid')[0];
		searchGrid.getStore().load({
//			params:{
//				subjectId: subject.value,
////				userId: 
//			},
			failure: function(form, action) {
				Ext.getBody().unmask();
//				var result = uber.util.Util.decodeJSON(action.response.responseText);
				Ext.Msg.alert('Error', "An error has occured, please try again", Ext.emptyFn);
//				console.log(result.errors.reason);
			},
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
