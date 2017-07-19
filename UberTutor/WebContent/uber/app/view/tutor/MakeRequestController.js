Ext.define('uber.view.tutor.MakeRequestController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.makerequest',
    
    save: function () {
    	var formpanel = Ext.ComponentQuery.query('#formpanel')[0];
    	var model = Ext.create('uber.model.MakeRequest', formpanel.getValues());
    	var errors = model.validate();
    	if(formpanel.isValid()){
    		Ext.getBody().mask('Validating... Please Wait...');
    		formpanel.submit({
    			url: '/UberTutor/main/make-request!save.action',
    			params: {
    				subjectId: formpanel.down('#subject').getValue()
    			},
        		method: 'POST',
    			success: function(form, response, opts) {
//    				console.log(response);
    				var me = this;
    		        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
    		        Ext.getBody().unmask();
    				var remove = mainCard.removeAll();
    				var card2 = mainCard.add(Ext.create('uber.view.session.SessionsStudent'));
    			},
    			failure: function(response, opts) {
    				Ext.getBody().unmask();
    				// similar to above
    				var result = uber.util.Util.decodeJSON(response.responseText);
    				console.log(" success: " + result.success + " result: " + result.data);
    				Ext.Msg.alert('Error', " success: " + result.success + " result: " + result.data, Ext.emptyFn);
    			},
    		})
    	} else {
    		Ext.getBody().unmask();
    		var message = "";
    		Ext.each(errors.items, function(rec){
    			message +=rec.getMessage()+"<br>";
    		});
    		Ext.Msg.alert("Error", message, Ext.emptyFn);
//    		Ext.Msg.alert("Error", "One or more of the fields is not valid", Ext.emptyFn);
    	}
    },
    
    student: function () {
    	
    },
})