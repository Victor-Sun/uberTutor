Ext.define('uber.view.session.SessionsTutorGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'sessionsTutorGrid',
	
	initComponent: function () {
		var me = this;
    	me.store =  Ext.create('uber.store.grid.SessionsTutorGrid');
    	me.store.load();
		this.columns = [{
			xtype: 'templatecolumn',
			align: 'left',
			flex: 1,
			tpl: [
				"<div class='session'>" +
//					"<div class='session-toolbar'>" +
//						"<button type='button' onclick='' class='x-fa fa-comments'></button>" +
//					"</div>" +
					"<div class='session-frame' style='display: inline-block;'>" +
						"<div class='session-left' style='display: inline; float: left;'>" +
							"<ul style='list-style-type: none;'>" +
								"<li>Title: {title} </li>" +
								"<li>Category: {category} </li>" +
								"<li>Status: {status} </li>" +
							"</ul>" +
						"</div>" +
						"<div class='session-right' style='display: inline; float: right;'>" +
							"<ul style='list-style-type: none;'>" +
								"<li>Student: {studentName} </li>" +
								"<li>Subject: {subject} </li>" +
								"<li>Create Date: {createDate} </li>" +
							"</ul>" +
						"</div>" +
					"</div>" +
				"</div>",
				]
    	}];
		this.listeners = {
    		celldblclick: 'onCelldblclick',
    	};
		this.callParent(arguments);
	}
});