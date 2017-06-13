Ext.define('uber.view.session.SessionsStudent',{
	extend: 'Ext.panel.Panel',
	xtype: 'sessionsStudent',
	
	layout: {
		type: 'vbox',
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
        		xtype: 'sessionsStudentGrid',
        	}]
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
    	this.callParent(arguments);
    }
});