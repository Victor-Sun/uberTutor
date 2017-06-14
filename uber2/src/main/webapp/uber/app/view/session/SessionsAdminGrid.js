Ext.define('uber.view.session.SessionsAdminGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'sessionAdminGrid',
	controller: 'sessions',
	initComponent: function () {
    	var me = this;
    	var store = Ext.create('uber.store.grid.SessionsAdminGrid');
    	me.store =  Ext.create('uber.store.grid.SessionsAdminGrid');
    	me.store.load();
    	this.columns = [{
    		text: 'Create Date',
			dataIndex: 'OPEN_DATE',
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
    	this.listeners = {
    		celldblclick: 'onCelldblclick',
    		selectionchange: 'onSelectionChange',
    	};
    	this.callParent(arguments);
	}
});