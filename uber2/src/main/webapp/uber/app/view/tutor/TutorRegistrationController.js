Ext.define('uber.view.tutor.TutorRegistrationController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.tutorRegistration',
    
    submit: function(btn) {
    	debugger;
		var me = this;
		var form = me.view.down('form').getForm();
		me.view.mask('Please Wait...')
		form.submit({
			clientValidation: true,
			url:'/uber2/main/tutor-subject-register!displayUserSubjects.action',
			params: {
            	model: Ext.encode(form.getFieldValues())
            },
            scope: me,
            success: function(form, action) {
            	me.view.unmask();
            	me.view.parentCaller.refreshView();
                me.cancel();
            },
            failure: function(form, action) {
            	me.view.unmask();
            	uber.util.Util.handleFormFailure(action);
            }
		});
//		var rec = new uber.model.grid.TutorRegistrationGridRow({
//            category: value.category,
//            subject: value.subject
//        });
//		grid.getStore().insert(0, rec);
    },
    
    cancel: function() {
    	this.view.close();
    },

    onRemoveClick: function(grid, rowIndex){
        this.getStore().removeAt(rowIndex);
    },


    
})