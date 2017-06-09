Ext.define('uber.view.session.MySessionAdmin',{
	extend: 'Ext.panel.Panel',
	xtype: 'mysessionadmin',
	
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
                html: '<h2>My Session (Admin)</h2>'
            }]
    	},{
    		xtype: 'grid',
    		flex:1,
    		columns: [{
    			text: 'Date',
    			flex:1,
    		},{
    			text: 'Student/Tutor',
    			flex:1,
    		},{
    			text: 'Category',
    			flex:1,
    		},{
    			text: 'Status',
    			flex:1,
    		}]
    	}]
    }]
});