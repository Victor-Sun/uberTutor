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
//    	debugger;
    	//fetches data from forms
    	var me = this;
    	var formPanel = this.lookupReference('formpanel');
    	var model = Ext.create('uber.model.User', formPanel.getValues());
    	var errors = model.validate();
    	var check = errors.isValid();
//    	console.log(model);
//    	console.log(errors);
//    	console.log(check);
    	
    	if(errors.isValid()){
    		formPanel.submit({ 
    			url: '/uber/main/login!login.action',
    			method: 'post', 
    			success: function(response, opts) {
    		         var obj = Ext.decode(response.responseText);
    		         console.dir(obj);
    		     },

    		     failure: function(response, opts) {
    		         console.log('server-side failure with status code ' + response.status);
    		     }
//    			success: function() { 
//    				Ext.Msg.alert("success"); }, 
//    			failure: function() { 
//    				Ext.Msg.alert("error"); 
//    			}
    		});
    	}
//    		formPanel.submit({
//                clientValidation: true,
//                url: '/uber/main/login!login.action',
//                scope: me,
//                success: function(){
//                	Ext.Msg.alert('Submit sucess!')
//                },
//                failure: function(form,result){
//                	var message = "";
//                	Ext.each(result.erros,function(rec, i) {
//                		message += rec.message+"<br>";
//                	});
//                	Ext.Msg.alert(" Submit failed ", message);
//                }
//            });
    	else {
    		var message = "";
    		Ext.each(errors.items,function(rec){
    			message +=rec.getMessage()+"<br>";
    		});
    		Ext.Msg.alert("Validation failed", message);
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