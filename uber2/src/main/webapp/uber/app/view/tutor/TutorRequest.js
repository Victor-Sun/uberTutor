Ext.define('uber.view.tutor.TutorRequest',{
	extend: 'Ext.panel.Panel',
	xtype: 'tutorrequest',
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'profile-container',
	items: [{
		xtype: 'panel',
		flex: 1,
    	cls: 'profile-panel',
    	layout: {
    		type: 'vbox',
    		align: 'stretch'
    	},
		items: [{
			xtype: 'panel',
			flex: 1,
			cls: 'tutor-panel',
			border: true,
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			items: [{
				//to do add template for requests
				xtype: 'grid',
				layout: 'fit',
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
			}]
		}]
	}]
})