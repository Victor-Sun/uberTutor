Ext.define('uber.view.search.Search', {
	extend: 'Ext.Panel',
	xtype: 'search',
	flex: 1,
	reference: 'searchPanel',
    id: 'searchPanel',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
	items: [{
		xtype: 'fieldset',
//      layout: {
//          type: 'vbox',
//          align: 'stretch'
//      },
		title: 'Search',
 		items: [{
 			xtype: 'textfield',
			name: 'subject',
			label: 'Subject(s)',
			clearIcon: true
//	  	},{
//	  		xtype: 'textfield',
//			name: 'price',
//			label: 'Price/hr',
	  	},{
	  		xtype: 'selectfield',
			name: 'Distance',
			label: 'Distance',
			options: [{
				text: 'less than 1 km'
			},{
				text: 'less than 5 km'
			}]
 		},{
 			xtype: 'toolbar',
 			items: [{
 				xtype: 'button',
 				text: 'Search'
 			},{
 				xtype: 'button',
 				text: 'Reset'
 			}]
 		}]
  	},{
	  	xtype: 'fieldset',
	  	title: 'Search results',
	  	flex: 1,
	  	items: [{
	  		xtype: 'grid',
//		  	height: 100,
		//  	fullscreen: true,
		  	flex: 1,
		  	store: {
		  		fields: ['name', 'subject', 'distance'],
		  		data: [
	  		       { 'name': 'Joe', 'subject': 'Science', 'distance': '3 km away' },
	  		       { 'name': 'April', 'subject': 'Calculus', 'distance': '3 km away' },
	  		       { 'name': 'Harry', 'subject': 'Literature', 'distance': '3 km away' }
  		        ]
		  	},
		  	columns: [{
		  		text: 'Name',
		  		dataIndex: 'name',
//		  		minWidth: 450
		  	},{
		  		text: 'Subject',
		  		dataIndex: 'subject'
//		  	},{
//		  		text: 'Price',
//		  		dataIndex: 'price',
////		  		width: 450
		  	},{
		  		text: 'Distance',
		  		dataIndex: 'distance',
//		  		width: 450
		  	}]
	  	}]
	}]
//	}]
	// config: {
	// 	variableHeights: true,
		
	// }
});