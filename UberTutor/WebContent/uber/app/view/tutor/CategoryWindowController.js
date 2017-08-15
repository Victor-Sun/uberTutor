Ext.define('uber.view.tutor.CategoryWindowController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.categoryWindow',
    
    submit: function() {
		var me = this;
		var grid = Ext.ComponentQuery.query('#tutorRegistrationGrid')[0];
		var gridStore = grid.getStore();
		var form = Ext.ComponentQuery.query('#subjectForm')[0];
		var model = Ext.create('uber.model.CategoryWindow', form.getValues());
		var validation = model.getValidation(); 
		var getForm = form.getForm();
		var subjectDescription = Ext.ComponentQuery.query('#subjectDescription')[0];
		Ext.getBody().mask('Loading...Please Wait...');
		if (model.isValid()) {
			getForm.submit({
				clientValidation: true,
				url:'/UberTutor/main/tutor-subject-register!save.action',
//				params: {
//	            	model: Ext.encode(getForm.getFieldValues()),
//	            },
	            scope: this,
	            success: function(form, action, response) {
	            	Ext.getBody().unmask();
	            	this.getView().close();
	            	grid.getStore().reload();
	            	Ext.toast({
                    	html: 'Subject register success!',
                    	align: 't',
                    	slideInDuration: 400,
                    	slideBackDuration: 400,
                        minWidth: 400
                    });
	            },
	            failure: function (form, action, response) {
	            	Ext.getBody().unmask();
	                var result = uber.util.Util.decodeJSON(action.response.responseText);
	                Ext.Msg.alert('Error', result.data, Ext.emptyFn);
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
    		Ext.Msg.alert("Validation failed", msg, Ext.emptyFn);
		}
	},
    
    cancel: function() {
    	this.view.close();
    },
    
//    //Error handling	
//    updateErrorState: function(cmp, state) {
//        var me = this,
//            errorCmp = me.lookupReference('formErrorState'),
//            view, form, fields, errors;
//        
//        view = me.getView();
//        form = view.getForm();
//
//        // If we are called from the form's validitychange event, the state will be false if invalid.
//        // If we are called from a field's errorchange event, the state will be the error message.
//        if (state === false || (typeof state === 'string')) {
//            fields = form.getFields();
//            errors = [];
//            
//            fields.each(function(field) {
//                Ext.Array.forEach(field.getErrors(), function(error) {
//                    errors.push({name: field.getFieldLabel(), error: error});
//                });
//            });
//            
//            errorCmp.setErrors(errors);
//            me.hasBeenDirty = true;
//        } else if (state === true) {
//            errorCmp.setErrors();
//        }
//    },
    

});