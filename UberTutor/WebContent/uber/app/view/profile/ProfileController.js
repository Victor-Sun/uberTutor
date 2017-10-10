Ext.define('uber.view.profile.ProfileController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.profile',
    
    toggleEdit: function(th, readOnly) {
		var profileForm = Ext.ComponentQuery.query('#profileForm')[0];
		var formFields = profileForm.getForm().getFields();
		var saveButton = Ext.ComponentQuery.query('#saveButton')[0];
		var checkBox = Ext.ComponentQuery.query('#isTutor')[0];
		var display = Ext.ComponentQuery.query('#isTutorDisplay')[0];
		
		if (th.tickCount == 0) {
			Ext.suspendLayouts();
			profileForm.getForm().getFields().each(function(field) {
				field.setReadOnly(false);
				field.setDisabled(false);
				saveButton.setHidden(false);
				checkBox.setHidden(false);
				display.setHidden(true);
			});
			Ext.resumeLayouts();
			th.tickCount = th.tickCount + 1;
		} else {
			Ext.suspendLayouts();
			profileForm.getForm().getFields().each(function(field) {
				field.setReadOnly(true);
				field.setDisabled(true);
				saveButton.setHidden(true);
				checkBox.setHidden(true);
				display.setHidden(false);
			});
			Ext.resumeLayouts();
			th.tickCount = 0 ;
		}
	},
    
//    profilemanage: function () {
//    	var me = this;
//    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
//		var remove = mainCard.removeAll();
//		var card2 = mainCard.add(Ext.create('uber.view.profile.ChangeProfile'));
//    },
    
    profile: function () {
    	var me = this;
    	var profileForm = Ext.ComponentQuery.query('#profileForm')[0];
    	var form = profileForm.getValues();
    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.profile.Profile'));
		profileForm.load({
			url: '/UberTutor/main/profile!display.action',
			params: {
				fullname: form.FULLNAME
			},
			reader: {
				type: 'json',
				rootProperty: 'data'
			},
		});
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
    	var form = formPanel.getValues();
    	Ext.getBody().mask();

    	formPanel.load({
			//submit form for user signup
			url: '/UberTutor/main/profile!display.action',
			method: 'GET',
			params: {
				fullname: 'form.FULLNAME'
			},
			scope: me,
    	    success: function(response, opts) {
    	    	Ext.getBody().unmask();
    	    	var result = uber.util.Util.decodeJSON(response.responseText);
    	    	var obj = Ext.JSON.decode(response.responseText);
//    	        console.log(obj);
    	    	Ext.Msg.alert('Error', obj , Ext.emptyFn);
    	    },

    	    failure: function(response, opts) {
    	    	uber.util.Util.handleRequestFailure(response);
    	    }
		})
    },
    
    save: function () {
		var profileForm = Ext.ComponentQuery.query('#profileForm')[0];
		var form = profileForm.getValues();
		var model = Ext.create('uber.model.Profile', profileForm.getValues());
		var validation = model.getValidation(); 
		var isTutorValue = Ext.ComponentQuery.query('#isTutor')[0];
		var menuButton = Ext.ComponentQuery.query('#userNameItemId')[0];
		var search = Ext.ComponentQuery.query('#menuItemSearch')[0];
		Ext.getBody().mask('Loading...Please Wait');
    	if(profileForm.getForm().isValid()){
    		profileForm.submit({
    			//submit form for user signup
    			url: '/UberTutor/main/profile!save.action',
    			method: 'POST',
    			params: {
    				fullname: form.FULLNAME,
    				email: form.EMAIL,
    				mobile: form.MOBILE,
    				schoolName: form.NAME,
    				bio: form.BIO,
    				isTutor: form.IS_TUTOR
    			},
    			success: function(response, opts) {
    				var profileForm = Ext.ComponentQuery.query('#profileForm')[0];
    				var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
			        var remove = mainCard.removeAll();
			        var card2 = mainCard.add(Ext.create('uber.view.profile.Profile'));
    				Ext.getBody().unmask();
    				profileForm.load({
    					url: '/UberTutor/main/profile!display.action',
    					params: {
    						fullname: form.FULLNAME
    					},
    					reader: {
    						type: 'json',
    						rootProperty: 'data'
    					},
    					failure: function(form, action) {
    	    				Ext.getBody().unmask();
//    	    				var result = uber.util.Util.decodeJSON(action.response.responseText);
    	    				Ext.Msg.alert('Error', "An error has occured, please try again", Ext.emptyFn);
//    	    				console.log(result.errors.reason);
    	    			},
    				});
    	    		if (isTutorValue.getValue() == true) {
    	    			search.show();
    	    		} else {
    	    			search.hide();
    	    		}
    			},
    			failure: function(form, action) {
    				Ext.getBody().unmask();
    				var result = uber.util.Util.decodeJSON(action.response.responseText);
    				Ext.Msg.alert('Error', result.errors.reason, Ext.emptyFn);
//    				console.log(result.errors.reason);
    			},
    		});
    	} else {
    		Ext.getBody().unmask();
    		var msg = "<ul class='error'>";
            for (var i in validation.data) {
                if(validation.data[i] !== true){
                    msg += "<li>" + " " + validation.data[i] + "</li>" ;
                }
            }
            msg += "</ul>";
    		Ext.Msg.alert("Error", msg, Ext.emptyFn);
//    		Ext.Msg.alert("Error", "One or more of the fields is not valid", Ext.emptyFn);
//    		console.log(message);
    	}
	},
    
});