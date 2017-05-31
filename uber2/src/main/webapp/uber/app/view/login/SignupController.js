Ext.define('uber.view.login.SignupController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.signup',
    
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
    				Ext.create('uber.view.verification.Verification');
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

//	updateErrorState: function(cmp, state) {
//		var me = this;
//		errorCmp = 
//	}
})