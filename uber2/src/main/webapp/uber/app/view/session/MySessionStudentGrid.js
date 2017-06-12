Ext.define('uber.view.session.MySessionStudentGrid',{
	xtype: 'mySessionStudentGrid',
	
	initComponent: function () {
		var me = this;
    	var store = Ext.create('uber.store.grid.MySessionStudentGrid');
    	me.store =  Ext.create('uber.store.grid.MySessionStudentGrid');
    	me.store.load();
		this.columns = [{
			text: 'Date',
			dataIndex: 'date',
			flex:1,
		},{
			text: 'Tutor',
			dataIndex: 'tutorName',
			flex:1,
		},{
			text: 'Category',
			dataIndex: 'category',
			flex:1,
		},{
			text: 'Status',
			dataIndex: 'status',
			flex:1,
		}];
		this.callParent(arguments);
	}
});