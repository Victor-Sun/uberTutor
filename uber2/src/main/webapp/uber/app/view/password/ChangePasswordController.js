Ext.define('uber.view.password.ChangePasswordController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.changepassword',
    
    changePassword: function () {
    	var me = this;
    	var formPanel = this.lookupReference('formpanel');
    	Ext.getBody().mask('Loading...Please Wait');
//    	var errors = model.validate(); console.log(errors);
    	if(formPanel.getForm().isValid()){
    		formPanel.submit({
    			//submit form for user signup
    			url: '/UberTutor/main/password!save.action',
    			method: 'POST',
    			success: function() {
    				// change to exception output
    				Ext.getBody().unmask();
//    				Ext.Msg.alert( '', 'password change success', Ext.emptyFn );
    				var changePasswordForm = Ext.ComponentQuery.query('#changePasswordForm')[0];
    				changePasswordForm.reset();
    			},

    			failure: function (form, action) {
    				Ext.getBody().unmask();
    				var result = uber.util.Util.decodeJSON(action.response.responseText);
    				Ext.Msg.alert('Error', result.data, Ext.emptyFn);
    			},
    		})
    	} 
    	else {
    		Ext.getBody().unmask();
//    		var data="";
//    		errors.each(function (item, index, length) {
//			  // Each item in the errors collection is an instance of the Ext.data.Error class.
//			  data = data + '|'+item.getField()+' - '+ item.getMessage() +'|';
//			});
//    		Ext.Msg.alert("Error", message, Ext.emptyFn);
    		Ext.Msg.alert("Error", "An error has occured, please check the form and try again", Ext.emptyFn);
    	}
    },
    
    profile: function() {
		var me = this;
		var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.profile.Profile'));
    },
    
    changepassword: function() {
    	var me = this;
    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.password.ChangePassword'));
    },
    
    logout: function() {
		var me = this;
		this.getView().destroy();
		var login = Ext.create('uber.view.login.Login');
		var radio = login.down('pageradio').setValue({ab: 2});
    }
    
});