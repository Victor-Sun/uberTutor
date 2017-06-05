Ext.define('uber.view.password.ChangePasswordController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.changepassword',
    
    changePassword: function () {
    	var me = this;
    	var formPanel = this.lookupReference('formpanel');
    	
    	if(formPanel.getForm().isValid()){
    		formPanel.submit({
    			//submit form for user signup
    			url: '/uber2/main/changepassword!changePassword.action',
    			method: 'POST',
    			success: function() {
    				// change to exception output
    				Ext.Msg.alert( '', 'update success', Ext.emptyFn )
    			},

    			failure: function() {
    				var result = uber.util.Util.decodeJSON(action.response.responseText);
    		        Ext.Msg.alert('Error', result.data, Ext.emptyFn);
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