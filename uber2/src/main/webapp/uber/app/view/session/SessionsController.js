Ext.define('uber.view.session.SessionsController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.sessions',
    
    onSelectionChange: function(grid, records, eOpts) {
    	var sessionInfoForm = this.view.query('#sessionInfoForm')[0]
        if (records[0]) {
        	sessionInfoForm.getForm().loadRecord(records[0]);
        	sessionInfoForm.requestId = records[0].get('REQUEST_ID');
        }
    	sessionInfoForm.load({
			url: '/uber2/main/my-session!displaySessionInfo.action',
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
    	console.log("Request Id:" + record.data.REQUEST_ID);
    	Ext.create('uber.view.session.SessionInfoWindow',{
    		requestId: record.data.REQUEST_ID,
    		status: record.data.STATUS
    	}).show();
    },
    
    feedback: function () {
    	var me = this;
		var main = me.view.up('app-main');
        
        var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.session.Feedback'));
    },
    
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