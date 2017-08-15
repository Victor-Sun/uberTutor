Ext.define('uber.view.search.SearchGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'searchgrid',
	layout: 'fit',
	requires: [
        'Ext.grid.filters.Filters',
        'Ext.grid.plugin.RowExpander'
    ],
	itemId: 'searchgrid',
//	emptyText: "<h3>You currently don't have any sessions</h3>",
	flex: 1,
//	hideHeaders: true,
	initComponent: function () {
		var me = this;
		store = Ext.create('uber.store.grid.SearchGrid');
		var page = 5;
		Ext.apply(me, {
			store: store,
		});
		this.columns = [{
			//displays :Name, Rating, Member Since, Successful Requests
			// SUBJECT DATE USER TITLE
//			xtype: 'templatecolumn',
//			align: 'left',
//			flex: 1,
//			tpl: [
//				"<div class=''>" +
//					"<div class=''>" +
//						"<div class='subject-section' style='display: inline; margin-left: 10px;'><b>Subject:</b> {subjectTitle}</div>" +
//						"<div class='status-section' style='display: inline; margin-left: 10px;'><b>Date:</b> {createDate}</div>" +
////						"<div class='status-section' style='display: inline; margin-left: 10px;'><b>User:</b> {userFullname} (user rating: {userrating})</div>" +
//						"<div class='status-section' style='display: inline; margin-left: 10px;'><b>User:</b> {userFullname}</div>" +
//					"</div>" +
////					"<hr>" +
//					"<div class=''>" +
////						"<div class='description-label'><b>Description (Short):</b> {requestTitle}</div>" +
//						"<div class='description-label'><b>Description:</b> {requestTitle}</div>" +
//					"</div>" +
//				"</div>",
//			]
//		},{
//			xtype: 'actioncolumn',
//    		align: 'middle',
//    		width: 75,
//    		items: [{
//    			xtype: 'button',
//    			itemId: 'details',
//    			iconCls: 'x-fa fa-archive',
//    			tooltip: 'Accept',
////    			handler: 'detailClick'
//    		}]
//		},{
//			dataIndex: 'title',
//	        flex: 1,
//	        renderer: 'renderTitleColumn'
//		},{
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
		},{
			text: 'Average Rating',
			dataIndex: 'averageRating',
			flex: 1,
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