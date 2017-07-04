Ext.define('uber.view.tutor.RequestCategory',{
	extend: 'Ext.window.Window',
	xtype: 'requestcategory',
	
	items: [{
		xtype: 'form',
		margin: 15,
		items: [{
			xtype: 'textfield',
			fieldLabel: 'Category',
		},{
			xtype: 'textfield',
			fieldLabel: 'Subject'
		}]
		
	}],
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'bottom',
		items: [{
			xtype: 'button',
			text: 'Submit'
		}]
	}]
});