Ext.define('uber.view.login.SignupController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.signup',
    
    signup: function () {
    	debugger;
    	var me = this;
    	var formPanel = this.lookupReference('formpanel');
    	var model = Ext.create('uber.model.User', formPanel.getValues());
    	var errors = model.validate();
    	var form = formPanel.getForm();
    	
    	if (model.isValid()){
    		formPanel.submit({
    			//submit form for user signup
    			url: '/uber2/main/signup!save.action',
    			method: 'POST',
    			success: function() {
    				Ext.Msg.alert( '', 'registration success', Ext.emptyFn )
    			},

    			failure: function() {
    				Ext.Msg.alert('', 'registration failure', Ext.emptyFn )
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