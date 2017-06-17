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
    
    onLoginFailure: function (form, action) {
    	var me = this;
    	Ext.getBody().unmask();
        var result = uber.util.Util.decodeJSON(action.response.responseText);
        Ext.Msg.alert('Error', result.data, Ext.emptyFn);
	},

	onLoginSuccess: function (form, action) {
		var me = this;
		Ext.getBody().unmask();
  	//if login info is correct
		me.lookupReference('formpanel').up('login').destroy();
		Ext.create('uber.view.main.Main');

		var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var card2 = mainCard.add(Ext.create('uber.view.main.MainPage'));
        var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('mainpage');

		var result = uber.util.Util.decodeJSON(action.response.responseText);
		var userName = result.data.userName;
		var userNameText = Ext.ComponentQuery.query('#userNameItemId')[0];
		userNameText.setText(userName);
	},
    
    login: function () {
    	//1. takes the values from textfields and uses a data model to validate the username and password fields
    	//2. if valid the form is submitted to the server through ajax
    	//3. if username and password is valid the login page is destroyed and the main page is loaded
    	//4. if not valid an alert message will appear
    	var me = this;
    	var formPanel = this.lookupReference('formpanel');
//    	var model = Ext.create('uber.model.User', formPanel.getValues());
    	Ext.getBody().mask('Validating... Please Wait...');
    	if(formPanel.getForm().isValid()){
    		formPanel.submit({ 
    			url: '/uber2/main/login!login.action',
    			submitEmptyText: false,
    			method: 'POST', 
    			clientValidation: true,
    			scope: me,
    			success: 'onLoginSuccess' ,
    		    failure: 'onLoginFailure' 
    		});
    	} else {
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
		
		me.lookupReference('formpanel').up('login').destroy();
		Ext.create('uber.view.main.Main');
		var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var card2 = mainCard.add(Ext.create('uber.view.profile.ChangeProfile'));
		var mainLayout = mainCard.getLayout();
	    var card = mainCard.setActiveItem('changeprofile');
        
		var result = uber.util.Util.decodeJSON(action.response.responseText);
		var userName = result.data.username;
		var userNameText = Ext.ComponentQuery.query('#userNameItemId')[0];
		userNameText.setText(userName);
	},
    
    signup: function () {
    	var me = this;
    	var formPanel = this.lookupReference('formpanel');
    	Ext.getBody().mask('Validating... Please Wait...');
    	if(formPanel.getForm().isValid()){
    		formPanel.submit({
    			//submit form for user signup
    			url: '/uber2/main/signup!save.action',
    			method: 'POST',
    			clientValidation: true,
    			scope: me,
    			success: 'onSignUpSuccess',

    			failure: 'onSignUpFailure' 
    		})
    	} else {
    		var message = "";
    		Ext.each(errors.items, function(rec){
    			message +=rec.getMessage()+"<br>"
    		});
    		Ext.Msg.alert("Error", message, Ext.emptyFn);
    	}
    },
});