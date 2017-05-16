Ext.define('uber.view.login.SignUpController',{
	extend: 'Ext.app.ViewController',
	alias: 'controller.signup',

	signup: function () {
		debugger;
		var me = this;
		var formPanel = this.lookupReference('formpanel');
//		var model = Ext.create('uber.model.User', formPanel.getValues());
//		var errors = model.validate();
		var form = formPanel.getForm();

		if (form.isValid()){
//			var out = [];
//			Ext.Object.each(form.getValues(), function(key, value){
//			out.push(key + ' = ' + value);
//			});
//			Ext.Msg.alert('Submitted Values', out.join('<br />'));

			form.submit({
				//submit form for user signup
				url: '/uber2/main/signup!signup.action',
				method: 'POST',
				success: function() {
					Ext.Msg.alert( '', 'registration success', Ext.emptyFn )
				},

				failure: function() {
					Ext.Msg.alert('', 'registration failure', Ext.emptyFn )
				}
			})
		}

//		if (model.isValid()){
//		var out = [];
//		Ext.Object.each(model.getValues(), function(key, value){
//		out.push(key + '=' + value);
//		});
//		Ext.Msg.alert('Submitted Values', out.join('<br />'));

////		formPanel.submit({
////		//submit form for user signup
////		url: '/uber2/main/signup!signup.action',
////		method: 'POST',
////		success: function() {
////		Ext.Msg.alert( '', 'registration success', Ext.emptyFn )
////		},

////		failure: function() {
////		Ext.Msg.alert('', 'registration failure', Ext.emptyFn )
////		},
////		})
//		} else {
//		var message = "";
//		Ext.each(errors.items, function(rec){
//		message +=rec.getMessage()+"<br>"
//		});
//		Ext.Msg.alert("Error", message, Ext.emptyFn);
//		}
	}
})