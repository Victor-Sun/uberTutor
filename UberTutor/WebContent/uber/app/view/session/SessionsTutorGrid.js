Ext.define('uber.view.session.SessionsTutorGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'sessionsTutorGrid',
	controller: 'sessions',
	emptyText: "<h3>You currently don't have any sessions</h3>",
	initComponent: function () {
		var page = 5;
		var me = this;
    	me.store =  Ext.create('uber.store.grid.SessionsTutorGrid',{
    		 pageSize: page,
    	});
    	me.store.load({
    		params: {
    	        start: 0,
    	        limit: page
    	    },
    	    failure: function(form, action) {
				Ext.getBody().unmask();
//				var result = uber.util.Util.decodeJSON(action.response.responseText);
				Ext.Msg.alert('Error', "An error has occured, please try again", Ext.emptyFn);
//				console.log(result.errors.reason);
			}
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
								"<li>Student: {studentName} </li>" +
								"<li>Subject: {subject} </li>" +
								"<li>Create Date: {createDate} </li>" +
							"</ul>" +
						"</div>" +
					"</div>" +
				"</div>",
			]
		},{
			xtype: 'actioncolumn',
			width: 50,
    		items: [{
    			xtype: 'button',
    			itemId: 'details',
    			iconCls: 'x-fa fa-archive',
    			tooltip: 'Details',
    			handler: 'detailClick'
    		},{
    			xtype: 'button',
    			itemId: 'feedback',
    			iconCls: 'x-fa fa-comment',
    			tooltip: 'Feedback',
    			handler: 'feedbackClick',
    			getClass: function (value, meta, record) {
                    if(record.get('status') == 'CLOSED'){
                    	return 'x-fa fa-comment' ;  
                    } else {
                    	return 'x-hidden';
                    }
               },
    		}]
    	}];
		this.dockedItems = [{
    		xtype: 'pagingtoolbar',
    		displayInfo: true,
    		dock: 'bottom',
    		store: me.store
    	}];
//		this.listeners = {
//    		celldblclick: 'onCelldblclickTutor',
//    	};
		this.callParent(arguments);
	}
});