Ext.define('uber.view.search.SearchResultsGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'searchresultsgrid',
	itemId: 'searchresultsgrid',
	flex: 1,
	hideHeaders: false,
//	layout: 'fit',
//	store: {
//		fields: [ 'name', 'signupDate', 'rating', 'sucessfulRequests'],
//		data: [
//		     { name: 'Charles', signupDate: '6/7/2009', rating: '4', successfulRequests: '20'},
//		     { name: 'Phillip', signupDate: '5/9/2005', rating: '4.5', successfulRequests: '45'}
//       ]
//	},
	initComponent: function () {
		var me = this;
		me.store = Ext.create('uber.store.grid.SearchResultGrid');
		this.columns = [{
			//displays :Name, Rating, Member Since, Successful Requests
			// SUBJECT DATE USER TITLE
//			xtype: 'templatecolumn',
//			align: 'left',
//			flex: 1,
//			tpl: [
//				"<div class='session'>" +
//				"<div class='session-frame' style='display: inline-block;'>" +
//					"<div class='session-left' style='display: inline; float: left;'>" +
//						"<ul style='list-style-type: none;'>" +
//							"<li>Name: {name} </li>" +
//							"<li>Member Since: {signupDate} </li>" +
//						"</ul>" +
//					"</div>" +
//					"<div class='session-right' style='display: inline; float: right;'>" +
//						"<ul style='list-style-type: none;'>" +
//							"<li>Rating: {rating}/10</li>" +
//							"<li>Successful Requests: {successfulRequests} </li>" +
//						"</ul>" +
//					"</div>" +
//				"</div>" +
//				"</div>",
//		      ]
			text: 'Title',
			dataIndex: 'requestTitle',
			flex: 1
		},{
			text: 'Subject',
			dataIndex: 'subject',
			flex: 1
		},{
			text: 'Date',
			dataIndex: 'createDate',
			flex: 1
		},{
			text: 'Student',
			dataIndex: 'studentName',
			flex: 1
		},{
			xtype: 'actioncolumn',
			items: [{
				iconCls: 'x-fa fa-user',
				margin: 5,
				tooltip: 'Details',
				handler: function() {
					Ext.create('Ext.window.Window',{
						header: false,
						width: 900,
						height: 700,
						layout: 'fit',
						items: [{
							xtype: 'tutorprofile',
							title: 'Profile'
						}],
						dockedItems: [{
							xtype: 'toolbar',
							dock: 'bottom',
							items: [{
								xtype: 'button',
								text: 'Close',
								handler: function () {
									var window = this.up('window');
									window.close();
								}
							}]
						}]
					}).show();
				}
			}]
		}];
		this.callParent(arguments);
	}, 
	listeners: {
		celldblclick: 'onCelldblclick'
	}
});