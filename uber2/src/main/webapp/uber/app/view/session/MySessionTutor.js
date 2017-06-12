Ext.define('uber.view.session.MySessionTutor',{
	extend: 'Ext.panel.Panel',
	xtype: 'mysessiontutor',
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
	controller: 'session',
    items: [{
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
                html: '<h2>My Session (Tutor)</h2>'
            }]
    	},{
    		xtype: 'grid',
    		flex:1,
    		columns: [{
    			text: 'Date',
    			dataIndex: 'date',
    			flex:1,
    		},{
    			text: 'Student',
    			dataIndex: 'student',
    			flex:1,
    		},{
    			text: 'Category',
    			dataIndex: 'category',
    			flex:1,
    		},{
    			text: 'Status',
    			dataIndex: 'status',
    			flex:1,
    		}]
    	}]
    }]
});