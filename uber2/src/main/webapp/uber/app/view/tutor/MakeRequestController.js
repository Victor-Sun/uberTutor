Ext.define('uber.view.tutor.MakeRequestController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.makerequest',
    
    save: function () {
    	debugger;
    	var formpanel = Ext.ComponentQuery.query('#formpanel')[0];
    	if(formpanel.isValid()){
    		formpanel.submit({
    			url: '/uber2/main/Make-request!save.action',
        		method: 'POST',
    			success: function(response, opts) {
    				console.log(response);
    				this.student();
    			},
    			failure: function(response, opts) {
    				// similar to above
    				var result = uber.util.Util.decodeJSON(response.responseText);
    				console.log(response);
    				console.log(" success: " + result.success + " result: " + result.data);
    				Ext.Msg.alert('Error', " success: " + result.success + " result: " + result.data, Ext.emptyFn);
    			},
    		})
    	} else {
    		var message = "";
    		Ext.each(errors.items, function(rec){
    			message +=rec.getMessage();
    		});
    		Ext.Msg.alert("Error", message, Ext.emptyFn);
    	}
    },
    
    student: function () {
    	var me = this;
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.MySessionStudent'));
    },
})