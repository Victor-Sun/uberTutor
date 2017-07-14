Ext.define('uber.view.session.SessionsStudentGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'sessionsStudentGrid',
	controller: 'sessions',
	emptyText: "<h3>You currently don't have any sessions</h3>",
	hideHeader: true,
	initComponent: function () {
		var page = 5;
		var me = this;
    	me.store =  Ext.create('uber.store.grid.SessionsStudentGrid',{
    		pageSize: page,
    	});
    	me.store.load({
    		params: {
    	        start: 0,
    	        limit: page
    	    },
    	});
    	this.columns = [{
			xtype: 'templatecolumn',
			align: 'left',
			flex: 1,
			tpl: [
				"<div class='session'>" +
					"<div class='session-frame' style='display: inline-block;'>" +
						"<div class='session-left' style='display: inline; float: left;'>" +
							"<ul style='list-style-type: none;'>" +
								"<li>Title: {requestTitle} </li>" +
								"<li>Category: {category} </li>" +
								"<li>Status: {status} </li>" +
							"</ul>" +
						"</div>" +
						"<div class='session-right' style='display: inline; float: right;'>" +
							"<ul style='list-style-type: none;'>" +
								"<li>Tutor: {tutorName} </li>" +
								"<li>Subject: {subject} </li>" +
								"<li>Create Date: {createDate} </li>" +
							"</ul>" +
						"</div>" +
					"</div>" +
				"</div>",
			]
    	},{
    		xtype: 'actioncolumn',
    		items: [{
    			xtype: 'button',
    			itemId: 'feedback',
    			iconCls: 'x-fa fa-comment',
    			tooltip: 'Feedback',
//    			disabled: true,
//    			hidden: 'true',
    			handler: 'feedbackClick'
    		},{
    			xtype: 'button',
    			itemId: 'details',
    			iconCls: 'x-fa fa-archive',
    			tooltip: 'Details',
    			handler: 'detailClick'
    		},{
    			
    		}],
//    		renderer: function (th, val, metadata, record) {
////    			debugger;
//    			var me = this;
//    			var feedback = this.items[1];
//    			switch ( metadata.data.status ) {
//    				case 'CLOSED':
//    					feedback.setVisible(true);
//    					enable(); 
//    			}
//    		}
		}];
    	this.dockedItems = [{
    		xtype: 'pagingtoolbar',
    		displayInfo: true,
    		dock: 'bottom',
    		store: me.store
    	}];
		this.listeners = {
//    		celldblclick: 'onCelldblclick',
    	};
		this.callParent(arguments);
	}
});