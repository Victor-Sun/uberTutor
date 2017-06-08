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

    onRemoveClick: function (grid, rowIndex) {
    	var me = this;
    	var record = grid.getStore().getAt(rowIndex);
    	Ext.Ajax.request({
    		url:'/uber2/main/tutor-subject-register!removeSubject.action',
    		params: {
    			SUBJECT_ID:record.data.SUBJECT_ID
    		},
    		score: me,
    		success: function() {
    			grid.getStore().reload();
    			uber.util.Util.showToast("Subject was successfully removed!");
    		},
    		failure: function(response, opts) {
    			uber.util.Util.handlerRequestFailure(response);
    		}
    	});
    }
})