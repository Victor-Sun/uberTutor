Ext.define('uber.view.search.SearchResults',{
	extend: 'Ext.panel.Panel',
	xtype: 'searchresults',
	layout: 'border',
	
	
	controller: 'search',
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
	items: [{
		xtype: 'panel',
		cls: 'uber-panel-inner',
		layout: {
			type: 'vbox',
			align: 'stretch'
		},
		items: [{
			//search results grid
			xtype: 'container',
			items: [{
				html: '<h2>Search Results</h2>'
			}]
		},{
			xtype: 'tabpanel',
			flex: 1,
			layout: 'fit',
			items: [{
				xtype: 'searchresultsgrid',
				title: 'Search Results Grid'
			}]
		}]
	}]
})