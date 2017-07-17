Ext.define('uber.view.session.SessionsController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.sessions',
    
    onSelectionChange: function(grid, records, eOpts) {
    	var sessionInfoForm = this.view.query('#sessionInfoForm')[0]
        if (records[0]) {
        	sessionInfoForm.getForm().loadRecord(records[0]);
        	sessionInfoForm.requestId = records[0].get('requestId');
        };
    	sessionInfoForm.load({
			url: '/UberTutor/main/my-session!displayRequestInfo.action',
			params: {
				requestId:sessionInfoForm.requestId,
			},
			reader: {
				type: 'json',
				rootProperty: 'data'
			}
        });
        if (sessionInfoForm.getCollapsed() != false) {
        	sessionInfoForm.setCollapsed(false);
        }
    },
    
    valueCheck: function (th , field, newValue, oldValue) {
		if (newValue != null) {
			th.setVisible(true);
		} else {
			th.setHidden(true);
		}
	},
    
    sessioninfo: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.SessionInfo'));
    },
    
    sessionsAdmin: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.SessionsAdmin'));
    },
    
    sessionsTutor: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.SessionsTutor'));
    },
    
    sessionsStudent: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.SessionsStudent'));
    },

    onCelldblclick: function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
//    	console.log("RequestId:" + record.data.requestId);
    	Ext.create('uber.view.session.SessionInfoWindow',{
    		requestId: record.data.requestId,
//    		status: record.data.status
    	}).show();
//    	var sessionInfoWindow = Ext.ComponentQuery.query('#sessionInfoWindow')[0];
//    	var requestId = sessionInfoWindow.down('#requestID').setValue(record.data.requestID);
//    	var status = sessionInfoWindow.down('#status').setValue(record.data.status);
    },
    
    onCelldblclickTutor: function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
//    	console.log("RequestId:" + record.data.requestId);
    	var form = Ext.create('Ext.form.Panel',{
    		items: [{
    			xtype: 'textfield',
    			name: 'requestId',
    			value: record.data.requestId
    		}]
    	});
    	form.submit({
    		url: '/UberTutor/main/my-session!updateRequestToInProcess.action',
			method: 'POST',
			failure: function(form, action) {
				Ext.getBody().unmask();
//				var result = uber.util.Util.decodeJSON(action.response.responseText);
				Ext.Msg.alert('Error', "An error has occured, please try again", Ext.emptyFn);
//				console.log(result.errors.reason);
			},
    	});
    	Ext.create('uber.view.session.SessionInfoWindow',{
    		requestId: record.data.requestId,
//    		status: record.data.status
    	}).show();
//    	var sessionInfoWindow = Ext.ComponentQuery.query('#sessionInfoWindow')[0];
//    	var requestId = sessionInfoWindow.down('#requestID').setValue(record.data.requestID);
//    	var status = sessionInfoWindow.down('#status').setValue(record.data.status);
    	
    },
    
    detailClick: function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
//    	console.log("RequestId:" + rowIndex.data.requestId);
    	Ext.create('uber.view.session.SessionInfoWindow',{requestId: rowIndex.data.requestId}).show();
    },
    
    feedbackClick: function(gridview, rowIndex, colIndex, item, e, record, row) {
    	 var requestId = record.data.requestId;
         var studentId = record.data.studentId;
         var tutorId = record.data.tutorId;
         var window = Ext.create('uber.view.session.FeedbackWindow',{requestId: requestId, studentId: studentId, tutorId: tutorId}).show();
         var feedback = window.down('#feedback');
         var sessionInfo = window.down('#sessionInfo');
         var requestIdField = feedback.down('hiddenfield');
         requestIdField.setValue(requestId);
         var studentIdField = sessionInfo.down('#studentId');
         studentIdField.setValue(studentId);
         var tutorIdField = sessionInfo.down('#tutorId');
         tutorIdField.setValue(tutorId);
		
    },
    
//    feedback: function () {
//    	var me = this;
//		var main = me.view.up('app-main');
//        
//        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
//		var remove = mainCard.removeAll();
//		var card2 = mainCard.add(Ext.create('uber.view.session.Feedback'));
//    },
    
    mysession: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.MySession'));
    },
    
    student: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.MySessionStudent'));
    },
    
    tutor: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.MySessionTutor'));
    },
    
    admin: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.MySessionAdmin'));
    },
    
    sessioninfo: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.SessionInfo'));
    }
})