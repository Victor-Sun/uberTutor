Ext.define('uber.view.test.TestSession',{
	extend: 'Ext.panel.Panel',
	xtype: 'testSession',
	
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
        		xtype: 'container',
                layout: 'hbox',
                items: [{
                    margin: 5,
                    html: '<h2>My Sessions</h2>'
                }]
        	},{
        		xtype: 'container',
    			layout: 'border',
    			flex: 1,
    			items: [{
    				xtype: 'testSessionGrid',
    				region: 'center'
    	    	}]
        	}]
        }];
    	this.callParent(arguments);
    }
});