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
//    	var main = this.setView('app-main');
//    	mainCard.destroy();
    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.profile.ChangeProfile'));
//    	var mainCard = main.lookupReference('mainCardPanel');
//    	var mainLayout = mainCard.getLayout();
//        var card = mainCard.setActiveItem('changeprofile');
    },
    
    backprofile: function () {
    	var me = this;
    	var main = me.view.up('app-main');
//    	var main = this.setView('app-main');
//    	mainCard.destroy();
    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.profile.Profile'));
//    	var mainCard = main.lookupReference('mainCardPanel');
//    	var mainLayout = mainCard.getLayout();
//        var card = mainCard.setActiveItem('profile');
    },
    
    getProfile: function () {
    	debugger;
    	var me = this;
    	var formPanel = this.lookupReference('formpanel');
    	var model = Ext.create('uber.model.User', formPanel.getValues());
    	var errors = model.validate();
    	var form = formPanel.getForm();
    	
    	formPanel.submit({
			//submit form for user signup
			url: '/uber2/main/profile!display.action',
			method: 'POST',
//			params: {
//				username: username
//			},
			success: function() {
				// change to exception output
				Ext.Msg.alert( '', 'update success', Ext.emptyFn )
			},

			failure: function() {
				// similar to above
				Ext.Msg.alert('', 'update failure', Ext.emptyFn )
			},
		})	
    },
    
    update: function () {
    	var me = this;
    	var formPanel = this.lookupReference('formpanel');
    	var model = Ext.create('uber.model.User', formPanel.getValues());
    	var errors = model.validate();
    	var form = formPanel.getForm();
    	
    	if (model.isValid()){
    		formPanel.updateRecord({
    			//submit form for user signup
    			url: '/uber2/main/profile!update.action',
    			method: 'POST',
    			success: function() {
    				// change to exception output
    				Ext.Msg.alert( '', 'update success', Ext.emptyFn )
    			},

    			failure: function() {
    				// similar to above
    				Ext.Msg.alert('', 'update failure', Ext.emptyFn )
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