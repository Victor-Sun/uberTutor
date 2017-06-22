Ext.define('uber.view.tutor.TutorRegistrationGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'tutorRegistrationGrid',
	itemId: 'tutorRegistrationGrid',
	reference: 'tutorRegistrationGrid',
	
	flex: 1,
	requires: [
	   'Ext.grid.plugin.RowExpander',
//	   'uber.store.grid.TutorRegistrationGrid',
	   'uber.store.News',
       'Ext.selection.CellModel',
       'Ext.grid.*',
       'Ext.data.*',
       'Ext.util.*',
       'Ext.form.*',
    ],
    cls: 'shadow subject-grid',
    controller: 'tutorRegistration',
//    viewModel: {
//        type: 'news'
//    },
//    bind: '{tutorRegistrationGrid}',
//    bind: '{news}',
    hideHeaders: true,
//    
//    plugins: [{
//        ptype: 'ux-rowexpander',
//        pluginId: 'rowexpander'
//    }],
//    
//    viewConfig: {
//        listeners: {
//            itemclick: 'onCompanyClick',
//            expandbody: 'onCompanyExpandBody',
//            collapsebody: 'onCompanyCollapseBody'
//        }
//    },
    
    initComponent: function () {
    	var me = this;
    	var store = Ext.create('uber.store.grid.TutorRegistrationGrid');
    	me.store =  Ext.create('uber.store.grid.TutorRegistrationGrid');
    	var win = Ext.create('uber.view.tutor.EditWindow');
    	me.store.load({
//    		callback : function(r, record, options, success) {
//    	        console.log(r.data)
//    	    }
    	});
    	
    	
    	var onAddClick = function(){
            var window = Ext.create('uber.view.tutor.CategoryWindow');
            window.show();
        };
       
        
    	this.columns = [{
//    		xtype: 'gridcolumn',
//            dataIndex: 'name',
//            align: 'left',
//            flex: 1,
//            renderer: function (value, metaData, record) {
//            	debugger;
//            	var categoryTitle = record.data.CATEGORY_TITLE;
//            	var subjectTitle = record.data.SUBJECT_TITLE;
//            	var createDate = record.data.CREATE_DATE.time;
//            	var description = record.data.DESCRIPTION;
//                return "<div class='session'>" +
//							"<div class='session-frame' >" +
//							"<div class='session-info' style=''>" +
//								"<div class='session-category' style='display: inline; margin-left: 10px;'><b>Category</b>: " + categoryTitle + " </div>" +
//								"<div class='session-subject' style='display: inline; margin-left: 10px;'><b>Subject</b>: " + subjectTitle + "</div>" +
//								"<div class='session-status' style='display: inline; margin-left: 10px;'><b>Date</b>: " + createDate + "</div>" +
//							"</div>" +
//							"<hr>" +
//							"<div class='session-description' style='min-height: 50px; margin: 10px;'><b>Description</b>: " + description + "</div>" +
//						"</div>" +
//					"</div>";
//            }
//    	},{
    		xtype: 'templatecolumn',
			align: 'left',
			flex: 1,
			tpl: [
				"<div class='session'>" +
					"<div class='session-frame' >" +
						"<div class='session-info' style=''>" +
							"<div class='session-category' style='display: inline; margin-left: 10px;'><b>Category</b>: {CATEGORY_TITLE} </div>" +
							"<div class='session-subject' style='display: inline; margin-left: 10px;'><b>Subject</b>: {SUBJECT_TITLE}</div>" +
							"<div class='session-status' style='display: inline; margin-left: 10px;'><b>Date</b>: {CREATE_DATE}</div>" +
						"</div>" +
						"<hr>" +
						"<div class='session-description' style='min-height: 50px; margin: 10px;'><b>Description</b>: {DESCRIPTION}</div>" +
					"</div>" +
				"</div>",
			]
    	},{
    		xtype: 'actioncolumn',
    		align: 'center',
    		items: [{
//    			xtype: 'button',
//    			iconCls: 'x-fa fa-ellipsis-h',
//    			menu: [{
//    				items: [{
////    					iconCls: 'x-fa fa-pencil',
//        				text: 'Edit'
//    	    		},{
////    	    			iconCls: 'x-fa fa-trash',
//    	    			text: 'Delete'
//    	//              scope: this,
//    	//    			handler: 'onRemoveClick'
//    				}]
//    			}]
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
//    		scope: this,
    		text: 'Add',
    		handler: onAddClick
    	}],
    	
    	this.callParent(arguments);
    },
});