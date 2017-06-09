Ext.define('uber.view.profile.ProfileController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.profile',
    
    profilemanage: function () {
    	var me = this;
    	var main = me.view.up('app-main');
    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.profile.ChangeProfile'));
    },
    
    backprofile: function () {
    	var me = this;
    	var main = me.view.up('app-main');
    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.profile.Profile'));
		profileInfoForm.reload();
    },
    
    registration: function () {
    	var me = this;
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
        var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.tutor.TutorRegistration'));
    },
    
    getProfile: function () {
    	var me = this;
    	var formPanel = this.lookupReference('profileForm');
//    	var model = Ext.create('uber.model.User', formPanel.getValues());
//    	var errors = model.validate();
//    	var form = formPanel.getForm();
//    	var rec = Ext.create('uber.store.Profile');
    	Ext.getBody().mask();

    	formPanel.load({
			//submit form for user signup
			url: '/uber2/main/profile!display.action',
			method: 'GET',
			params: {
				fullname: 'fullname'
			},
			scope: me,
    	    success: function(response, opts) {
    	    	Ext.getBody().unmask();
    	    	var result = uber.util.Util.decodeJSON(response.responseText);
    	    	var obj = Ext.JSON.decode(response.responseText);
    	        console.log(obj);
    	    	Ext.Msg.alert('Error', obj , Ext.emptyFn);
    	    },

    	    failure: function(response, opts) {
    	    	uber.util.Util.handleRequestFailure(response);
    	    }
		})
    },
    
    update: function () {
    	var me = this;
    	var formPanel = this.view;
//    	var model = Ext.create('uber.model.UserInfo', formPanel.getValues());
//    	var form = formPanel.getForm();
    	this.getView().mask('Loading...Please Wait');
    	if(formPanel.getForm().isValid()){
    		formPanel.submit({
    			//submit form for user signup
    			url: '/uber2/main/profile!update.action',
    			method: 'POST',
    			params: {
    				fullname: 'fullname'
    			},
    			success: function(response, opts) {
    				// change to exception output
    				this.getView().unmask();
    				Ext.Msg.alert( '', 'update success', Ext.emptyFn )
    			},

    			failure: function(form, action) {
    				// similar to above
    				this.getView().unmask();
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
//    /uber2/main/profile!registerAsTutor.action
    isTutor: function(checkbox, value){
    	var check = checkbox.getValue();
    	checkbox.submit()
    }
})