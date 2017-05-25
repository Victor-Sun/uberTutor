Ext.define('uber.view.search.Search',{
	extend: 'Ext.panel.Panel',
	xtype: 'search',
	layout: 'center',
	cls: 'search-panel',
	items: [{
		xtype: 'panel',
		cls: 'search-panel-inner',
		width: 440,
		border: true,
		layout: {
			type: 'vbox',
			align: 'stretch'
		},
		items: [{
			xtype: 'container',
			flex: 1,
			cls: 'search-container',
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			defaults: {
				margin: 5,
			},
			items: [{
				xtype: 'container',
				items: [{
					html: '<h2>Search for a Tutor</h2>'
				},{
					html: '<ul>' +
					'<li>Description here</li>' +
					'</ul>',
				}]
			},{
				xtype: 'form',
				layout: 'hbox',
				defaults: {
					xtype: 'combobox',
					labelAlign: 'top',
					flex: 1,
					margin: 5,
					anchor: '100%'
				},
				items: [{
					fieldLabel: 'Category'
				},{
					fieldLabel: 'Subject'
				}],
				dockedItems: [{
					xtype: 'toolbar',
					dock: 'bottom',
					items: [{
						xtype: 'button',
						text: 'search'
					}]
				}]
			}]
		}]
	}]
})