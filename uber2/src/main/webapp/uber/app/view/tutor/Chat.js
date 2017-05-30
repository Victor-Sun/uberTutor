Ext.define('uber.view.tutor.Chat',{
	extend: 'Ext.panel.Panel',
	xtype: 'chat',
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
	controller: 'chat',
	items: [{
		xtype: 'panel',
		layout: {
    		type: 'vbox',
    		align: 'stretch'
    	},
		items: [{
			xtype: 'toolbar',
			items: ['->',{
				xtype: 'button',
				text: 'complete',
				handler: 'complete'
			}]
		},{
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
					xtype: 'component',
					html: 'Joe Smoe'
				},{
					//Area for ratings component
					xtype: 'component'
				}]
			}]
		},{
			xtype: 'panel',
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			flex: 1,
			items: [{
				xtype: 'panel',
				layout: {
					type: 'vbox',
					align: 'stretch'
				},
				flex: 3,
				items: [{
					xtype: 'grid',
					layout: 'fit'
				}]
			},{
				xtype: 'container',
				flex: 1,
				layout: {
					type: 'hbox',
					align: 'stretch'
				},
				items: [{
					xtype: 'textarea',
					flex: 1
				},{
					xtype: 'button',
					text: 'Send'
				}]
			}]
		}]
	}]
});