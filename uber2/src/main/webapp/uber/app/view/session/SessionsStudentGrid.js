Ext.define('uber.view.session.SessionsStudentGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'sessionsStudentGrid',
	
	initComponent: function () {
		var me = this;
//    	var store = Ext.create('uber.store.grid.SessionsStudentGrid');
//    	me.store =  Ext.create('uber.store.grid.SessionsStudentGrid');
//    	me.store.load();
		this.columns = [{
    		text: 'Create Date',
			dataIndex: 'CREATE_DATE',
			xtype: 'datecolumn',
			format: 'Y-m-d',
			align: 'left',
			flex: 1
		},{
			text: 'Tutor',
			dataIndex: 'TUTOR_NAME',
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