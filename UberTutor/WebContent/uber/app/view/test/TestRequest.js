Ext.define('uber.view.test.TestRequest',{
	extend: 'Ext.panel.Panel',
	xtype: 'testRequest',
	layout: 'border',
	
	controller: 'test',
	
	layout: {
		type: 'fit',
	},
	cls: 'uber-panel',
	initComponent: function () {
		var me = this;
		var searchGrid = Ext.create('uber.view.search.SearchGrid');
		this.items = [
			{
				xtype: 'panel',
				cls: 'uber-panel-inner',
				layout: {
					type: 'vbox',
					align: 'stretch'
				},
				items: [
					{
						xtype: 'container',
						items: [
							{
								width: 1080,
								cls: 'uber-base',
								html: '<h2>Search</h2>'
							}
						]
					},
					{
						xtype: 'container',
						layout: {
							type: 'hbox',
							align: 'stretch'
						},
						flex: 1,
						cls: 'uber-container',
						items: [
							{
								xtype: 'panel',
								region: 'west',
								flex: 1
							},
							{
								xtype: 'testRequestGrid',
//								region: 'center',
								width: 1080,
								cls: 'uber-base'
							},
							{
								xtype: 'panel',
								region: 'east',
								flex: 1
							}
						]
					}
				]
			}
		]
		this.callParent(arguments);
	}
})