Ext.define('uber.view.search.SearchGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'searchgrid',
	layout: 'fit',
	requires: [
        'Ext.grid.filters.Filters',
    ],
	itemId: 'searchgrid',
//	emptyText: "<h3>You currently don't have any sessions</h3>",
	flex: 1,
	hideHeaders: false,
	plugins: 'gridfilters',
	initComponent: function () {
		var me = this;
		var page = 5;
		me.store = Ext.create('uber.store.grid.SearchGrid');
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
			text: 'Student',
			dataIndex: 'userFullname',
			flex: 1,
			filter: {
				type: 'string',
			}
		},{
			text: 'Subject',
			dataIndex: 'subjectTitle',
			flex: 1,
			filter: {
				type: 'string'
			}
		},{
			text: 'Date',
			dataIndex: 'createDate',
			flex: 1,
			filter: {
				type: 'date',
			}
		}];
		this.dockedItems = [{
    		xtype: 'pagingtoolbar',
    		displayInfo: true,
    		dock: 'bottom',
    		store: me.store
    	}];
		this.callParent(arguments);
	}, 
	listeners: {
		celldblclick: function (gridview, rowIndex, colIndex, item, e, record, row) {
			Ext.create('uber.view.search.SearchInfoWindow',{requestId:item.data.requestId}).show();
		}
	}
});