Ext.define('uber.view.session.MySessionAdminGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'mySessionAdminGrid',
	initComponent: function () {
    	var me = this;
    	var store = Ext.create('uber.store.grid.MySessionAdminGrid');
    	me.store =  Ext.create('uber.store.grid.MySessionAdminGrid');
    	me.store.load();
    	this.columns = [{
    		text: 'Create Date',
			dataIndex: 'CREATE_DATE',
//			xtype: 'datecolumn',
//			format: 'Y-m-d',
			align: 'left',
			flex: 1
		},{
			text: 'Student',
			dataIndex: 'STUDENT_ID',
			align: 'left',
			flex: 1
		},{
			text: 'Tutor',
			dataIndex: 'TUTOR_ID',
			align: 'left',
			flex: 1
		},{
			text: 'Category',
			dataIndex: 'CATEGORY',
			align: 'left',
			flex: 1
		},{
			text: 'Subject',
			dataIndex: 'SUBJECT',
			align: 'left',
			flex: 1
		},{
			text: 'Status',
			dataIndex: 'STATUS',
			align: 'left',
			flex: 1
    	}];
    	this.callParent(arguments);
	}
});