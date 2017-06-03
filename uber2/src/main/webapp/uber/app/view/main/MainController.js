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
    	var me = this;
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
        var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.tutor.TutorRegistration'));
    },
    
    mainpage: function () {
//    	debugger;
    	var me = this;
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
        var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.main.MainPage'));
    },
    
    profile: function() {
//   	    debugger;
		var me = this;
		var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.profile.Profile'));
        
        
//        var result = uber.util.Util.decodeJSON(action.response.responseText);
//		var fullname = result.data.fullname;
//		var email = result.data.email;
//		var mobile = result.data.mobile;
		
//		var profileStore = Ext.create('uber.store.Profile');
		
//        var fullnameText = Ext.ComponentQuery.query('#fullnameItemId')[0];
//        var emailText = Ext.ComponentQuery.query('#emailItemId')[0];
//        var mobileText = Ext.ComponentQuery.query('#mobileId')[0];
//        fullnameText.setValue(fullname);
//        emailText.setValue(email);
//        mobileText.setValue(mobile);
        
    },
    
    search: function() {
    	var me = this;
		var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.search.Search'));
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
		var card2 = mainCard.add(Ext.create('uber.view.session.Sessions'));
    },
    
    logout: function() {
		var me = this;
		this.getView().destroy();
		var login = Ext.create('uber.view.login.Login');
		var radio = login.down('pageradio').setValue({ab: 2});
    }
});
