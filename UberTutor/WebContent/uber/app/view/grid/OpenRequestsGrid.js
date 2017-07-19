Ext.define('uber.view.grid.OpenRequestsGrid',{
	extend:'Ext.grid.Panel',
	xtype: 'openRequests',
	itemId: 'openRequestsGrid',
	layout: 'fit',
	store: 'currentRequests',
	emptyText: "<h3>You currently don't have any open requests</h3>",
//	cls: 'grid-container',
	initComponent: function () {
		var me = this;
		me.store = Ext.create('uber.store.grid.CurrentRequests');
		me.store.load();
		this.columns = [{
			xtype: 'templatecolumn',
			align: 'left',
			flex: 1,
			tpl: [
				"<div class=''>" +
					"<div class=''>" +
						"<div class='title-section' style='display: inline; margin-left: 10px;'><b>Title:</b> {title}</div>" +
						"<div class='subject-section' style='display: inline; margin-left: 10px;'><b>Subject:</b> {subject}</div>" +
						"<div class='status-section' style='display: inline; margin-left: 10px;'><b>Status:</b> {status}</div>" +
					"</div>" +
					"<hr>" +
					"<div class=''>" +
						"<div class='description-label'><b>Description:</b></div>" +
						"<div class='description-section'>{subjectDescription}</div>" +
					"</div>" +
				"</div>",
				]
		},{
			xtype: 'actioncolumn',
			width: 50,
			align: 'center',
			items:[{
				xtype: 'button',
				iconCls: 'x-fa fa-ellipsis-h',
				tooltip: 'Details',
				handler: function (grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
					Ext.create('uber.view.session.SessionInfoWindow',{requestId: rowIndex.data.requestId}).show();
				}
			}]
		}];
		this.dockedItems = [{
//			xtype: 'toolbar',
//			dock: 'top',
//			items: [{
//				xtype: 'combobox',
//				itemId: 'searchBox',
//				
//			},{
//				xtype: 'button',
//				text: 'Search',
//				handler: function () {
//					var searchBox = Ext.ComponentQuery.query('#searchBox')[0];
//					var grid = Ext.ComponentQuery.query('#openRequestsGrid')[0];
//					me.store.load({
//						params: {
//							subject: 
//						}
//					})
//				}
//			}]
//		},{
    		xtype: 'pagingtoolbar',
    		displayInfo: true,
    		dock: 'bottom',
    		store: me.store
    	}];
		this.callParent(arguments);
	}
});