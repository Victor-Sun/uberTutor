Ext.define('uber.view.test.TestRequestGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'testRequestGrid',
	layout: 'fit',
	requires: [
        'Ext.grid.filters.Filters',
        'Ext.grid.plugin.RowExpander'
    ],
	itemId: 'testRequestGrid',
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