Ext.define('uber.view.profile.ProfileController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.profile',
    
//    init: function() {
//    	if (this.view.xtype == 'profile') {
//    		debugger;
//    		this.view.down('profileform').load({
//        		url: 'uber2/main/profile!display.action'
//        	});
//    	}
//    },
    
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
    },
    
    getProfile: function () {
    	debugger;
    	var me = this;
    	var formPanel = this.lookupReference('formpanel');
    	var model = Ext.create('uber.model.User', formPanel.getValues());
    	var errors = model.validate();
    	var form = formPanel.getForm();
    	var rec = Ext.create('uber.store.Profile');
    	
    	formPanel.load({
			//submit form for user signup
			url: '/uber2/main/profile!display.action',
			method: 'GET',
			params: {
				fullname: 'fullname'
			},
			scope: me,
    	    success: function(response, opts) {
    	    	var result = uber.util.Util.decodeJSON(response.responseText);
    	    	var obj = Ext.JSON.decode(response.responseText);
    	        console.log(obj);
    	    	Ext.Msg.alert('Error', obj , Ext.emptyFn);
    	    },

    	    failure: function(response, opts) {
    	    	ExtApp.util.Util.handleRequestFailure(response);
    	    }
		})
//    	Ext.Ajax.request({
//			url: '/uber2/main/profile!display.action',
//			params: {
//				processTaskId: 'fullname'
//			},
//    	    scope: me,
//    	    success: function(response, opts) {
//    	    	var result = uber.util.Util.decodeJSON(response.responseText);
//    	    	var obj = Ext.JSON.decode(response.responseText);
//    	         console.log(obj.data);
//    	    	Ext.Msg.alert('Error', obj.data , Ext.emptyFn);
//    	    },
//    	    failure: function(response, opts) {
//    	    	ExtApp.util.Util.handleRequestFailure(response);
//    	    }
//        });
    	
    },
    
    update: function () {
    	var me = this;
    	var formPanel = this.lookupReference('formpanel');
    	var model = Ext.create('uber.model.UserInfo', formPanel.getValues());
    	var errors = model.validate();
    	var form = formPanel.getForm();
    	
    	if (model.isValid()){
    		formPanel.submit({
    			//submit form for user signup
    			url: '/uber2/main/profile!updateProfile.action',
    			method: 'POST',
    			success: function() {
    				// change to exception output
    				Ext.Msg.alert( '', 'update success', Ext.emptyFn )
    			},

    			failure: function() {
    				// similar to above
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
})