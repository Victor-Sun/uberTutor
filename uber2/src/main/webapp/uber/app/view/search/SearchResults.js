Ext.define('uber.view.search.SearchResults',{
	extend: 'Ext.panel.Panel',
	xtype: 'searchresults',
	layout: 'border',
	cls: 'search-panel',
	
	controller: 'search',
		
	items: [{
		xtype: 'panel',
		region: 'north',
		height: '15%'
	},{
		xtype: 'panel',
		region: 'west',
		width: '20%',
	},{
		xtype: 'panel',
		region: 'east',
		width: '20%',
		items: [{
			xtype: 'form',
			border: true,
//			margin: 5,
//			padding: 5,
			defaults: {
				margin: 5,
			},
			items: [{
				xtype: 'fieldset',
				defaults: {
					anchor: '100%'
				},
				items: [{
					xtype: 'combobox',
					fieldLabel: 'category'
				},{
					xtype: 'combobox',
					fieldLabel: 'subject'
				},{
					xtype: 'toolbar',
					items: [{
						xtype: 'button',
						text: 'Search'
					}]
				}],
			}]
		}]
	},{
		xtype: 'panel',
		border: true,
		region: 'center',
		items: [{
			xtype: 'grid',
			layout: 'fit',
			columns: [{
				text: 'Name',
				dataIndex: 'name',
				flex: 1
			},{
				text: 'column2',
				flex: 1
			},{
				text: 'column3',
				flex: 1
			}],
			store: {
				fields: [ 'name'],
				data: [
				     { name: 'Charles'},
				     { name: 'Phillip'}
		       ]
			},
			listeners: {
				celldblclick: 'onCelldblclick'
			}	
		}]
	}]
})