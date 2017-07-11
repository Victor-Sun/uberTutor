Ext.define('uber.view.search.Search',{
	extend: 'Ext.panel.Panel',
	xtype: 'search',
	layout: 'border',
	
	controller: 'search',
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
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
				//search results grid
				//TODO Change to be loaded first instead
				//On render auto load results
				xtype: 'container',
				items: [{
					html: '<h2>Search</h2>'
				}]
			},{
				xtype: 'searchgrid',
			}]
		}]
		this.callParent(arguments);
	}
})