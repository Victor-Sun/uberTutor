Ext.define('uber.view.session.MySessionStudent',{
	extend: 'Ext.panel.Panel',
	xtype: 'mysessionstudent',
	
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
                html: '<h2>My Session (Student)</h2>'
            }]
    	}]
    }]
});