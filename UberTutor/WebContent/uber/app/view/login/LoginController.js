Ext.define('uber.view.login.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',
    
    setlogin: function () {
        var me = this;
        var status = me.lookupReference('pageradio');
        var radio = status.setValue({
            rb : 2
        });
    },

    loginPageStatus: function (){
    	var me = this;
    	var status = me.lookupReference('pageradio');
    	var radio = status.getValue();
    	
    	if (radio.ab != '1') {
    		me.login();
    	} else {
    		me.signup();
    	}
    },
    
    onLoginFailure: function (form, action, response) {
    	var me = this;
    	Ext.getBody().unmask();
        var result = uber.util.Util.decodeJSON(action.response.responseText);
        Ext.Msg.alert('Error', result.data, Ext.emptyFn);
	},

	onLoginSuccess: function (form, action, response) {
		var me = this;
		Ext.getBody().unmask();
  	//if login info is correct
		Ext.ComponentQuery.query('#loginSignUpForm')[0].up('login').destroy();
		Ext.create('uber.view.main.Main');

		var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var card2 = mainCard.add(Ext.create('uber.view.main.MainPage'));
        var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('mainpage');

		var result = uber.util.Util.decodeJSON(action.response.responseText);
		var userName = result.data.userName;
		var userNameText = Ext.ComponentQuery.query('#userNameItemId')[0];
		userNameText.setText(userName);
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
    
    login: function () {
    	//1. takes the values from textfields and uses a data model to validate the username and password fields
    	//2. if valid the form is submitted to the server through ajax
    	//3. if username and password is valid the login page is destroyed and the main page is loaded
    	//4. if not valid an alert message will appear
    	var me = this; 
    	var formPanel = Ext.ComponentQuery.query('#loginSignUpForm')[0];
    	var model = Ext.create('uber.model.SignIn', formPanel.getValues());
    	var errors = model.validate();
    	Ext.getBody().mask('Validating... Please Wait...');
//    	if(model.isValid()){
//    		formPanel.submit({ 
//    			url: '/UberTutor/main/login!login.action',
//    			method: 'POST', 
//    			submitEmptyText: false,
//    			clientValidation: true,
//    			scope: me,
//    			success: 'onLoginSuccess',
//    			failure: 'onLoginFailure' 
////    		    failure: function(response, opts) {
////    		         console.log('server-side failure with status code ' + response.status);
////    		         Ext.Msg.alert('', 'Error', Ext.emptyFn);
////    		    }
//    		});
//    	} else {
//    		Ext.getBody().unmask();
//    		var message = "";
//    		Ext.each(errors.items,function(rec){
//    			message +=rec.getMessage()+"<br>";
//    		url: '/UberTutor/main/login!login.action',
//    		});
//    		Ext.Msg.alert("Error", "The following errors occured: <br>" + message, Ext.emptyFn);
//    	}
    	if(formPanel.getForm().isValid()){
    		formPanel.submit({ 
    			url: '/UberTutor/main/login!login.action',
    			submitEmptyText: false,
    			method: 'POST', 
    			clientValidation: true,
    			scope: me,
    			success: 'onLoginSuccess' ,
    		    failure: 'onLoginFailure' 
    		});
    	} else {
    		Ext.getBody().unmask();
    		var message = "";
    		Ext.each(errors.items,function(rec){
    			message +=rec.getMessage()+"<br>";
    		});
    		Ext.Msg.alert("Validation failed", message, Ext.emptyFn);
    	}
    },
    
    onSignUpFailure: function (form, action) {
    	var me = this;
    	Ext.getBody().unmask();
        var result = uber.util.Util.decodeJSON(action.response.responseText);
        Ext.Msg.alert('Error', result.data, Ext.emptyFn);
	},

	onSignUpSuccess: function (form, action) {
		var me = this;
		Ext.getBody().unmask();
		
		Ext.ComponentQuery.query('#loginSignUpForm')[0].up('login').destroy();
		Ext.create('uber.view.main.Main');
		var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
//		var card2 = mainCard.add(Ext.create('uber.view.profile.Profile'));
//		var mainLayout = mainCard.getLayout();
//	    var card = mainCard.setActiveItem('profileTab');
		var card2 = mainCard.add(Ext.create('uber.view.profile.Profile'));
        var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem(card2);
        
		var result = uber.util.Util.decodeJSON(action.response.responseText);
		var userName = result.data.username;
		var userNameText = Ext.ComponentQuery.query('#userNameItemId')[0];
		userNameText.setText(userName);
	},
    
    signup: function () {
    	var me = this;
    	var formPanel = Ext.ComponentQuery.query('#loginSignUpForm')[0];
    	var model = Ext.create('uber.model.SignUp', formPanel.getValues());
    	var errors = model.validate();
    	Ext.getBody().mask('Validating... Please Wait...');
    	if(model.isValid()){
    		formPanel.submit({ 
    			url: '/UberTutor/main/signup!save.action',
    			method: 'POST', 
    			scope: this,
    			success: 'onSignUpSuccess',
    			failure: 'onSignUpFailure' 
//    		    failure: function(response, opts) {
//    		         console.log('server-side failure with status code ' + response.status);
//    		         Ext.Msg.alert('', 'Error', Ext.emptyFn);
//    		    }
    		});
    	} else {
    		Ext.getBody().unmask();
    		var message = "";
    		Ext.each(errors.items,function(rec){
    			message +=rec.getMessage()+"<br>";
    		});
    		Ext.Msg.alert("Error", "The following errors occured: <br>" + message, Ext.emptyFn);
    	}
//    	if(formPanel.getForm().isValid()){
//    		formPanel.submit({
//    			//submit form for user signup
//    			url: '/UberTutor/main/signup!save.action',
//    			method: 'POST',
//    			clientValidation: true,
//    			scope: me,
//    			success: 'onSignUpSuccess',
//
//    			failure: 'onSignUpFailure' 
//    		})
//    	} else {
//    		var message = "";
//    		Ext.each(errors.items, function(rec){
//    			message +=rec.getMessage()+"<br>"
//    		});
//    		Ext.Msg.alert("Error", message, Ext.emptyFn);
//    	}
    },
});