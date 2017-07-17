Ext.define('uber.view.grid.MyTutorsGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'myTutorsGrid',
	
	hideHeaders: true,
	flex: 1,
	layout: 'fit',
	emptyText: "<h3>You currently don't have any recent tutors</h3>",
	initComponent: function () {
		var me = this;
		me.store = Ext.create('uber.store.grid.MyTutorsGrid',{pageSize: 5});
		me.store.load();
		this.columns = [{
//			dataIndex: 'tutor',
//			text: 'Tutor',
//			align: 'left',
//			flex: 1
//		},{
//			dataIndex: 'subjects',
//			text: 'Subjects',
//			align: 'left',
//			flex: 1,
//		},{
//			dataIndex: 'rating',
//			text: 'Averate Rating',
//			align: 'left',
//			flex: 1,
//		},{
			xtype: 'templatecolumn',
			flex: 1,
			align: 'left',
			tpl: [
				"<div class=''>" +
					"<div class=''>" +
						"<div class='title-section' style='display: inline; margin-left: 10px;'><b>Tutor:</b> {tutorFullname}</div>" +
						"<div class='status-section' style='display: inline; margin-left: 10px;'><b>Date:</b> {createDate}</div>" +
					"</div>" +
					"<hr>" +
					"<div class=''>" +
						"<div class='description-label'><b>Subject:</b></div>" +
						"<div class='description-section'>{subjectTitle}</div>" +
					"</div>" +
				"</div>",
			]
		
		}];
		this.dockedItems = [{
    		xtype: 'pagingtoolbar',
    		displayInfo: true,
    		dock: 'bottom',
    		store: me.store
    	}];
		this.callParent(arguments);
	}
});