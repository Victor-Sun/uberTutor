Ext.define('uber.view.login.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',

    onLoginSuccess: function () {
    	//if login info is correct
    },

    onLoginFailure: function () {
		//if login info is incorrect
	},
    
//	test: function () {
//		var model = Ext.create('User',{
//			username: 'admin',
//			password: '123456'
//		})
//	},
	
    login: function () {
    	//fetches data from forms
    	var me = this;
    	var formPanel = this.lookupReference('formpanel');
    	var model = Ext.create('uber.model.User', formPanel.getValues());
    	var errors = model.validate();
    	var errors2 = model.isValid();
    	var check = errors.isValid();
//    	console.log(model);
//    	console.log(errors);
//    	console.log(check);
    	
    	if(model.isValid()){
    		formPanel.submit({ 
    			url: '/uber/main/login!login.action',
    			method: 'POST', 
    			success: function() {
//    		         var obj = Ext.decode(response.responseText);
//    		         console.dir(obj);
//    		         console.log('sucess');
//    				 Ext.Msg.alert('','', )
    		         this.up('panel').destroy();
    		         Ext.Viewport.add(Ext.create('uber.view.main.Main'));
    		    },

    		    failure: function(response, opts) {
    		         console.log('server-side failure with status code ' + response.status);
    		         Ext.Msg.alert('', 'Error', Ext.emptyFn);
    		    }
    		});
    	} else {
    		var message = "";
    		Ext.each(errors.items,function(rec){
    			message +=rec.getMessage()+"<br>";
    		});
    		Ext.Msg.alert("Validation failed", message, Ext.emptyFn);
    	}
    	
    	
//    	if (form == test) {
//    		this.getView().destroy();
//        	Ext.Viewport.add(Ext.create('uber.view.main.Main'));
//    	} else {
//    		Ext.Msg.Alert ()
//    	}
//    	console.log(form);
//    	
//      if(form.isValid()) {
//      	console.log(form)
//      }
//                    else alert('Invalid form');
    }, 	 

    signup: function () {
    	this.getView().destroy();
    	Ext.Viewport.add(Ext.create('uber.view.login.SignUp'));
    }
    
});