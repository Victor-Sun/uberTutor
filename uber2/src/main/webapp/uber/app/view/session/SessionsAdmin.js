Ext.define('uber.view.session.SessionsAdmin',{
	extend: 'Ext.panel.Panel',
	xtype: 'sessionsAdmin',
	
	layout: {
		type: 'hbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
	controller: 'sessions',
	initComponent: function () {
		this.items = [{
	    	xtype: 'panel',
			flex: 1,
	    	cls: 'uber-panel-inner',
	    	layout: {
	    		type: 'vbox',
	    		align: 'stretch'
	    	},
	    	items: [{
	    		xtype: 'toolbar',
				items: [{
					xtype: 'button',
					text: 'Session Info',
					handler: 'sessioninfo'
				}]
	    	},{
	    		xtype: 'container',
	            layout: 'hbox',
	            items: [{
	                margin: 5,
	                html: '<h2>Sessions</h2>'
	            }]
	    	},{
	    		xtype: 'sessionAdminGrid'
	    	}]
	    },{
	    	xtype: 'panel',
	    	width: 300, 
	    	collapsible: true,
	    }];
		this.tbar = [{
	    	xtype: 'button',
	    	text: 'Session Admin',
	    	handler: 'sessionsAdmin'
	    },{
	    	xtype: 'button',
	    	text: 'Session Tutor',
	    	handler: 'sessionsTutor'
	    },{
	    	xtype: 'button',
	    	text: 'Session Studen',
	    	handler: 'sessionsStudent'
    	}];
		this.callParents(arguments);
	}
});