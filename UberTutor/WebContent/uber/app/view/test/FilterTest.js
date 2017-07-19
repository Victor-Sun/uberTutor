Ext.define('uber.view.test.FilterTest',{
	extend: 'Ext.panel.Panel',
	
	initComponent: function () {
		var me = this;
		this.items = [{
			xtype: 'form',
			items: [{
				
			}]
		},{
			xtype: 'grid',
			
		}];
		this.callParent(arguments);
	}
});