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
//    		this.up('loginpage').destroy();
//    		this.view.destroy();
//			Ext.create('uber.view.main.Main');
    		this.login();
    	} else {
    		this.signup();
//    		this.up('loginpage').destroy();
//    		this.view.destroy();
//            Ext.create('uber.view.main.Main');
			// Ext.create('uber.view.verification.Verification');
    	}
    	
    	
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
    	
    	if(model.isValid()){
    		formPanel.submit({ 
    			url: '/uber2/main/login!login.action',
    			method: 'POST', 
    			success: function() {
    		         me.lookupReference('formpanel').up('login').destroy();
    		         Ext.create('uber.view.main.Main');
    		    },
    		    failure: function(form, action) {
    		    	 var result = uber.util.Util.decodeJSON(action.response.responseText);
//    		         console.log('server-side failure with status code ' + response.status);
    		         Ext.Msg.alert('Error', result.data, Ext.emptyFn);
    		    }
    		});
    	} else {
    		var message = "";
    		Ext.each(errors.items,function(rec){
    			message +=rec.getMessage()+"<br>";
    		});
    		Ext.Msg.alert("Validation failed", message, Ext.emptyFn);
//    		Ext.Msg.alert("Error", "Validation failed", Ext.emptyFn);
    	}
    },
    
    onLoginFailure: function (form, action) {

//      this.getView().unmask();

      var result = uber.util.Util.decodeJSON(action.response.responseText);

      switch (action.failureType) {
          case Ext.form.action.Action.CLIENT_INVALID:
          	uber.util.Util.showErrorMsg('Form fields may not be submitted with invalid values');
              break;
          case Ext.form.action.Action.CONNECT_FAILURE:
          	uber.util.Util.showErrorMsg(action.response.responseText);
              break;
          case Ext.form.action.Action.SERVER_INVALID:
          	uber.util.Util.showErrorMsg(result.data);
      	}
    },
  
    signup: function () {
    	var me = this;
    	var formPanel = this.lookupReference('formpanel');
    	var model = Ext.create('uber.model.User', formPanel.getValues());
    	var errors = model.validate();
//    	var form = formPanel.getForm();
    	
    	if (model.isValid()){
    		formPanel.submit({
    			//submit form for user signup
    			url: '/uber2/main/signup!save.action',
    			method: 'POST',
    			success: function() {
//    				Ext.Msg.alert( '', 'registration success', Ext.emptyFn )
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
    }
    
//    onLoginSuccess: function (form, action) {
//    	//if login info is correct
//    	me.lookupReference('formpanel').up('login').destroy();
//        Ext.create('uber.view.main.Main')
//    },
//
//    onLoginFailure: function (form, action) {
//
////        this.getView().unmask();
//
//        var result = uber.util.Util.decodeJSON(action.response.responseText);
//
//        switch (action.failureType) {
//            case Ext.form.action.Action.CLIENT_INVALID:
//            	uber.util.Util.showErrorMsg('Form fields may not be submitted with invalid values');
//                break;
//            case Ext.form.action.Action.CONNECT_FAILURE:
//            	uber.util.Util.showErrorMsg(action.response.responseText);
//                break;
//            case Ext.form.action.Action.SERVER_INVALID:
//            	uber.util.Util.showErrorMsg(result.data);
//        }
//    },
//    
//    login: function () {
////    	debugger;
//    	//1. takes the values from textfields and uses a data model to validate the username and password fields
//    	//2. if valid the form is submitted to the server through ajax
//    	//3. if username and password is valid the login page is destroyed and the main page is loaded
//    	//4. if not valid an alert message will appear
//    	var me = this;
//    	var formPanel = this.lookupReference('formpanel');
//    	var model = Ext.create('uber.model.User', formPanel.getValues());
//    	var errors = model.validate();
//    	
//    	if(model.isValid()){
//    		formPanel.submit({ 
//    			url: '/uber2/main/login!login.action',
//    			method: 'POST', 
//    			success: function() {
//    		         me.lookupReference('formpanel').up('login').destroy();
//    		         Ext.create('uber.view.main.Main');
//    		    },
//    		    failure: function(form, action) {
//    		    	 var result = uber.util.Util.decodeJSON(action.response.responseText);
////    		         console.log('server-side failure with status code ' + response.status);
//    		         Ext.Msg.alert('Error', result.data, Ext.emptyFn);
//    		    }
//    		});
//    	} else {
//    		var message = "";
//    		Ext.each(errors.items,function(rec){
//    			message +=rec.getMessage()+"<br>";
//    		});
//    		Ext.Msg.alert("Validation failed", message, Ext.emptyFn);
////    		Ext.Msg.alert("Error", "Validation failed", Ext.emptyFn);
//    	}
//    } 	
});