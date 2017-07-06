Ext.define('uber.view.grid.OpenRequestsGrid',{
	extend:'Ext.grid.Panel',
	xtype: 'openRequests',
	layout: 'fit',
	store: 'currentRequests',
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
			align: 'center',
			items:[{
				xtype: 'button',
				iconCls: 'x-fa fa-ellipsis-h',
				tooltip: 'Details'
			}]
		}];
		this.callParent(arguments);
	}
});