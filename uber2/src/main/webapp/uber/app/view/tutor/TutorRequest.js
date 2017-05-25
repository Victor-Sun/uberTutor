Ext.define('uber.view.tutor.TutorRequest',{
	extend: 'Ext.panel.Panel',
	xtype: 'tutorrequest',
	
	layout: 'fit',
	items: [{
		xtype: 'panel',
		items: [{
			xtype: 'panel',
			cls: 'tutor-panel',
			border: true,
			layout: {
				type: 'hbox',
				align: 'stretch'
			},
			items: [{
				//to do add template for requests
				xtype: 'grid'
			}]
		}]
	}]
})