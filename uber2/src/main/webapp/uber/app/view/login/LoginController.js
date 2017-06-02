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
    	
//    	switch (radio.ab) {
//	        case 1:
////    		this.up('loginpage').destroy();
//	   //  		this.view.destroy();
//				// Ext.create('uber.view.main.Main');
//                signup
//	            break;
//	        case 2:
////    		this.up('loginpage').destroy();
//	   //  		this.view.destroy();
//				// Ext.create('uber.view.main.Main');
//                login
//	            break;
//	    }
    	
    	if (radio.ab != '1') {
    		this.login();
    	} else {
    		this.signup();
    	}
    },
    
    onLoginFailure: function (form, action) {
    	var me = this;
        this.getView().unmask();
        var result = uber.util.Util.decodeJSON(action.response.responseText);
        Ext.Msg.alert('Error', result.data, Ext.emptyFn);
//        switch (action.failureType) {
//            case Ext.form.action.Action.CLIENT_INVALID:
//            	uber.util.Util.showErrorMsg('Form fields may not be submitted with invalid values');
//                break;
//            case Ext.form.action.Action.CONNECT_FAILURE:
//            	uber.util.Util.showErrorMsg(action.response.responseText);
//                break;
//            case Ext.form.action.Action.SERVER_INVALID:
//            	uber.util.Util.showErrorMsg(result.data);
//        	}
	},

	onLoginSuccess: function (form, action) {
//		debugger;
		var me = this;
		this.getView().unmask();
  	//if login info is correct
		me.lookupReference('formpanel').up('login').destroy();
		Ext.create('uber.view.main.Main');

		// var mainCard = me.lookupReference('mainCardPanel');
		var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		// var cardItem = mainCard.getActiveItem;
		// var destory = cardItem.destroy();
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
    	var model = Ext.create('uber.model.User', formPanel.getValues());
    	var errors = model.validate();
    	Ext.getBody().mask('Validating... Please Wait...');
    	if(model.isValid()){
    		formPanel.submit({ 
    			url: '/uber2/main/login!login.action',
    			method: 'POST', 
    			clientValidation: true,
    			scope: me,
    			success: 'onLoginSuccess' ,
//    			function() {
//    		         me.lookupReference('formpanel').up('login').destroy();
//    		         Ext.create('uber.view.main.Main');
//    		    },
    		    failure: 'onLoginFailure' 
//    		    function(form, action) {
//    		    	 var result = uber.util.Util.decodeJSON(action.response.responseText);
////    		         console.log('server-side failure with status code ' + response.status);
//    		         Ext.Msg.alert('Error', result.data, Ext.emptyFn);
//    		    }
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
        this.getView().unmask();
        var result = uber.util.Util.decodeJSON(action.response.responseText);
        Ext.Msg.alert('Error', result.data, Ext.emptyFn);
//        switch (action.failureType) {
//            case Ext.form.action.Action.CLIENT_INVALID:
//            	uber.util.Util.showErrorMsg('Form fields may not be submitted with invalid values');
//                break;
//            case Ext.form.action.Action.CONNECT_FAILURE:
//            	uber.util.Util.showErrorMsg(action.response.responseText);
//                break;
//            case Ext.form.action.Action.SERVER_INVALID:
//            	uber.util.Util.showErrorMsg(result.data);
//        	}
	},

	onSignUpSuccess: function (form, action) {
		debugger;
		var me = this;
		this.getView().unmask();
		me.lookupReference('formpanel').up('login').destroy();
		Ext.create('uber.view.main.Main');
		var result = uber.util.Util.decodeJSON(action.response.responseText);
		var userName = result.data.userName;
		var userNameText = Ext.ComponentQuery.query('#userNameItemId')[0];
		userNameText.setText(userName);
	},
    
    signup: function () {
    	var me = this;
    	var formPanel = this.lookupReference('formpanel');
    	var model = Ext.create('uber.model.UserInfo', formPanel.getValues());
    	var errors = model.validate();
//    	var form = formPanel.getForm();
    	
    	if (model.isValid()){
    		formPanel.submit({
    			//submit form for user signup
    			url: '/uber2/main/signup!save.action',
    			method: 'POST',
    			clientValidation: true,
    			scope: me,
    			success: function() {
    				me.lookupReference('formpanel').up('signup').destroy();
    				Ext.create('uber.view.main.Main');
//    				Ext.create('uber.view.verification.Verification');
    			},

    			failure: function(form, action) {
    				var result = uber.util.Util.decodeJSON(action.response.responseText);
    				Ext.Msg.alert('Error', result.data, Ext.emptyFn);
//    				Ext.Msg.alert('', 'registration failure', Ext.emptyFn )
    			},
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