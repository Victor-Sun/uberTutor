Ext.define('uber.view.test.TestRequest',{
	extend: 'Ext.panel.Panel',
	xtype: 'testRequest',
	layout: 'border',
	
	controller: 'test',
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
	initComponent: function () {
		var me = this;
		var searchGrid = Ext.create('uber.view.search.SearchGrid');
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
				items: [{
					html: '<h2>Search</h2>'
				}]
			},{
				xtype: 'testRequestGrid',
			}]
		}]
		this.callParent(arguments);
	}
})