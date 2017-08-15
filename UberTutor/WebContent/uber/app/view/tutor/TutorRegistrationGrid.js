Ext.define('uber.view.tutor.TutorRegistrationGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'tutorRegistrationGrid',
	itemId: 'tutorRegistrationGrid',
	reference: 'tutorRegistrationGrid',
	emptyText: "<h3>You currently don't have any subjects added</h3>",
	flex: 1,
	requires: [
//	   'uber.store.grid.TutorRegistrationGrid',
       'Ext.selection.CellModel',
       'Ext.grid.*',
       'Ext.data.*',
       'Ext.util.*',
       'Ext.form.*',
    ],
    cls: 'uber-panel-inner',
//    subject-grid
    controller: 'tutorRegistration',
    hideHeaders: true,
    
    initComponent: function () {
    	var me = this;
    	var store = Ext.create('uber.store.grid.TutorRegistrationGrid');
    	me.store =  Ext.create('uber.store.grid.TutorRegistrationGrid');
    	var win = Ext.create('uber.view.tutor.EditWindow');
    	me.store.load({
//    		callback : function(r, record, options, success) {
//    	        console.log(r.data)
//    	    }
    		params: {
    			start: 0,
    	        limit: 5
    		}
    	});
    	
    	
    	var onAddClick = function(){
            var window = Ext.create('uber.view.tutor.CategoryWindow');
            window.show();
        };
       
        
    	this.columns = [{
    		xtype: 'templatecolumn',
			align: 'left',
			flex: 1,
			tpl: [
				"<div class='session'>" +
					"<div class='session-frame' >" +
						"<div class='session-info' style=''>" +
							"<div class='session-category' style='display: inline; margin-left: 10px;'><b>Enabled?</b>: {isActive} </div>" +
							"<div class='session-category' style='display: inline; margin-left: 10px;'><b>Category</b>: {categoryTitle} </div>" +
							"<div class='session-subject' style='display: inline; margin-left: 10px;'><b>Subject</b>: {subjectTitle}</div>" +
				            "<div class='session-status' style='display: inline; margin-left: 10px;'><b>Date</b>: {createDate}</div>" +
						"</div>" +
						"<hr>" +
						"<div class='session-description' style='min-height: 50px; margin: 10px;'><b>Description</b>: {description}</div>" +
					"</div>" +
				"</div>",
			]
    	},{
    		xtype: 'actioncolumn',
    		align: 'center',
    		items: [{
    			xtype: 'button',
    			margin: 10,
    			tooltip: 'Enable/Disable',
    			iconCls: 'x-fa fa-check-circle',
    			handler: 'onToggleClick'
    		},{
    			xtype: 'button',
    			margin: 10,
    			tooltip: 'Edit',
    			iconCls: 'x-fa fa-pencil',
    			handler: 'onEditClick'
    		},{
    			xtype: 'button',
    			margin: 10,
    			tooltip: 'Remove',
    			iconCls: 'x-fa fa-trash',
    			handler: 'onRemoveClick'
    		}]
    	}],
    	this.tbar = [{
    		xtype: 'button',
    		text: 'Add',
    		handler: onAddClick
    	}],
    	this.bbar = [{
    		xtype: 'pagingtoolbar',
    		displayInfo: true,
    		store: me.store
    	}]
    	
    	this.callParent(arguments);
    },
});