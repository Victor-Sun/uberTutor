Ext.define('uber.view.main.MainPage', {
    extend: 'Ext.panel.Panel',
    xtype: 'mainpage',
    itemId: 'mainpage',
    	
    requires: [
    ],
    
    layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
    items: [{
    	xtype: 'panel',
		flex: 1,
    	cls: 'uber-panel-inner',
    	layout: {
    		type: 'vbox',
    		align: 'stretch'
    	},
    	items: [{
    		xtype: 'container',
            layout: 'hbox',
            items: [{
                margin: 5,
                html: '<h2>Main Page</h2>'
            }]
    	},{
    		xtype: 'panel',
    		flex: 1,
    		items: [{
    			html: '<h4>Main page content here</h4>'
    		}]
    	}]
    }]
});