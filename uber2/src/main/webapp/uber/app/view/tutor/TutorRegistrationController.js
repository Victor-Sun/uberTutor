Ext.define('uber.view.tutor.TutorRegistrationController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.tutorRegistration',
    
    refresh: function() {
    	var me = this;
    },
    
    submit: function(btn) {
		var me = this;
		var grid = Ext.getCmp('tutorRegistrationGrid').getStore();
		var form = me.view.down('form').getForm();
		me.view.mask('Please Wait...')
		form.submit({
			clientValidation: true,
			url:'/uber2/main/tutor-subject-register!save.action',
			params: {
            	model: Ext.encode(form.getFieldValues())
            },
            scope: me,
            success: function(form, action) {
            	me.view.unmask();
            	grid.reload();
                me.cancel();
            },
            failure: function(form, action) {
            	me.view.unmask();
            	uber.util.Util.handleFormFailure(action);
            }
		});

    },
    
    cancel: function() {
    	this.view.close();
    },

    onRemoveClick: function () {
    	var me = this;
    	var grid = Ext.getCmp('tutorRegistrationGrid').getStore();
    	Ext.Ajax.request({
//    		url:''
    		params: {
    			SUBJECT_ID:'SUBJECT_ID'
    		},
    		score: me,
    		success: function(response, opts) {
    			var obj = Ext.decode(response.responseText);
    			if (obj.success) {
    				//Refresh grid on sucess
    				grid.reload();
    			} else {
    				uber.util.Util.handlerRequesteFailure(response);
    			}
    		},
    		failure: function(response, opts) {
    			uber.util.Util.handlerRequestFailure(response);
    		}
    	});
    }
})