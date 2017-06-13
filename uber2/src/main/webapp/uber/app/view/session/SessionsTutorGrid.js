Ext.define('uber.view.session.SessionsTutorGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'sessionsTutorGrid',
	
	initComponent: function () {
		var me = this;
    	me.store =  Ext.create('uber.store.grid.SessionsTutorGrid');
    	me.store.load();
		this.columns = [{
    		text: 'Create Date',
			dataIndex: 'CREATE_DATE',
			xtype: 'datecolumn',
			format: 'Y-m-d',
			align: 'left',
			flex: 1
		},{
			text: 'Student',
			dataIndex: 'STUDENT_NAME',
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
		this.listeners = {
    		celldblclick: 'onCelldblclick'
    	};
		this.callParent(arguments);
	}
});