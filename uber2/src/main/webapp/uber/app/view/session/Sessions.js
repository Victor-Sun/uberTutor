Ext.define('uber.view.session.Sessions',{
	extend: 'Ext.panel.Panel',
	xtype: 'sessions',
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
	controller: 'sessions',
	items: [{
		xtype: 'panel',
		flex: 1,
    	cls: 'uber-panel-inner',
    	layout: {
    		type: 'vbox',
    		align: 'stretch'
    	},
		items: [{
			xtype: 'toolbar',
			items: [{
				xtype: 'button',
				text: 'My Session Tabs',
				handler: 'mysession'
			},{
				xtype: 'button',
				text: 'Session Info',
				handler: 'sessioninfo'
			},{
				xtype: 'button',
				text: 'Feedback',
				handler: 'feedback'
			}]
		},{
			xtype: 'container',
			items: [{
				margin: 5,
				xtype: 'component',
				html: '<h2>Sessions</h2>'
			}]
		},{
			//to do add template for sessions
			xtype: 'grid',
			margin: 5,
			flex: 1,
//			layout: 'fit',
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