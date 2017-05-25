Ext.define('uber.view.tutor.Feedback',{
	extend: 'Ext.panel.Panel',
	
	items: [{
		//panel for padding
		xtype: 'container',
		cls: 'content-container',
		items: [{
			//main content container
			xtype: 'panel',
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			items; [{
				xtype; 'container',
				items: [{
				}]
			},{
				xtype: 'panel',
				items: [{
					xtype: 'htmleditor'
				}],
				dockedItems: [{
					xtype: 'toolbar',
					items: [{
						xtype: 'button',
						text: 'submit'
					}]
				}]
			}]
		}]
	}]
});