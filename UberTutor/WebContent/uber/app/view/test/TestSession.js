Ext.define('uber.view.test.TestSession',{
	extend: 'Ext.panel.Panel',
	xtype: 'testSession',
	
	layout: {
		type: 'fit',
//		align: 'stretch'
	},
	cls: 'uber-panel',
	controller: 'sessions',
	initComponent: function () {
    	this.items = [{
        	xtype: 'panel',
        	cls: 'uber-panel-inner',
        	layout: {
        		type: 'vbox',
        		align: 'stretch'
        	},
        	items: [{
        		xtype: 'container',
                items: [
                	{
//	                    margin: 5,
                		width: 1080,
	                    cls: 'uber-base',
	                    html: '<h2>My Sessions</h2>'
                	}
            	]
        	},{
        		xtype: 'container',
    			layout: {
    				type: 'hbox',
    				align: 'stretch'
    			},
    			flex: 1,
//    			minHeight: 980,
//    			layout: {
//    				type: 'vbox',
//    				align: 'stretch'
//    			},
    			cls: 'uber-container',
    			items: [
    				{
    					xtype: 'panel',
    					region: 'west',
    					flex: 1,
//    					width: '15%',
    				},
    				{
	    				xtype: 'testSessionGrid',
	    				cls: 'uber-base',
	    				width: 1080,
//	    				flex: 1
//	    				height: '100%'
//	    				region: 'center'
    				},
    				{
    					xtype: 'panel',
    					region: 'east',
    					flex: 1,
//    					width: '15%',
    				}
				]
        	}]
        }];
    	this.callParent(arguments);
    }
});