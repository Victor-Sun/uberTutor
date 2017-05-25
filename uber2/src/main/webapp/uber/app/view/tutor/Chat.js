Ext.define('uber.view.tutor.Chat',{
	extend: 'Ext.panel.Panel',
	xtype: 'chat',
	
	layout: 'fit',
	items: [{
		xtype: 'panel',
		border: true,
		cls: 'tutor-panel',
		items: [{
			xtype: 'container',
			padding: '15% 0 0 0',
			items: [{
				xtype: 'container',
				layout: {
					type: 'hbox',
					align: 'stretch'
				},
				items: [{
					xtype: 'container',
					items: [{
						xtype: 'image',
						padding: 10,
						width: 80,
						height: 80
					}]
				},{
					xtype: 'container',
					layout: 'vbox',
					items: [{
						html: 'Joe Smoe'
					},{
						xtype: 'component'
					}]
				}]
			},{
				xtype: 'container',
				items: [{
					
				}]
			}]
		}]
	}]
});