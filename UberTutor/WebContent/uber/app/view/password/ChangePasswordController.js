Ext.define('uber.view.password.ChangePasswordController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.changepassword',
    
    changePassword: function () {
//    	debugger;
    	var me = this;
    	var formPanel = this.lookupReference('formpanel');
    	Ext.getBody().mask('Loading...Please Wait');
    	var model = Ext.create('uber.model.Password', formPanel.getValues());
    	var changeResult = Ext.ComponentQuery.query('#passwordChangeResult')[0];
    	var profile = Ext.ComponentQuery.query('#profileTab')[0];
    	var profileForm = Ext.ComponentQuery.query('#profileForm')[0];
        var mainLayout = profile.getLayout();
        var changePasswordForm = Ext.ComponentQuery.query('#changePasswordForm')[0];
    	var validation = model.getValidation(); 
    	if(formPanel.getForm().isValid()){
    		formPanel.submit({
    			//submit form for user signup
    			url: '/UberTutor/main/password!save.action',
    			method: 'POST',
    			success: function() {
    				Ext.getBody().unmask();
    				changePasswordForm.reset();
                    Ext.toast({
                    	html: 'New Password has been saved',
                    	align: 't',
                    	slideInDuration: 400,
                    	slideBackDuration: 400,
                        minWidth: 400
                    });
                    profile.setActiveItem(0);
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
    		// var message = [];
    		// Ext.each(errors.items,function(rec){
    		// 	message +=rec.getMessage()+"<br>";
    		// });
            var msg = "<ul class='error'>";
            for (var i in validation.data) {
                if(validation.data[i] !== true){
                    msg += "<li>" + " " + validation.data[i] + "</li>" ;
                }
            }
            msg += "</ul>";
    		Ext.Msg.alert("Validation failed", msg, Ext.emptyFn);
//    		Ext.Msg.alert("Error", "An error has occured, please check the form and try again", Ext.emptyFn);
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