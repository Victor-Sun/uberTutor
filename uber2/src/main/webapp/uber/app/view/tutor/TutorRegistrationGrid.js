Ext.define('uber.view.tutor.TutorRegistrationGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'tutorRegistrationGrid',
	reference: 'tutorRegistrationGrid',
	
	flex: 1,
	requires: [
	    
	   'uber.store.grid.TutorRegistrationGrid',
       'Ext.selection.CellModel',
       'Ext.grid.*',
       'Ext.data.*',
       'Ext.util.*',
       'Ext.form.*',
    ],
    cls: 'shadow',
    id: 'tutorRegistrationGrid',
    controller: 'tutorRegistration',
    initComponent: function () {
    	var me = this;
    	var store = Ext.create('uber.store.grid.TutorRegistrationGrid');
    	me.store =  Ext.create('uber.store.grid.TutorRegistrationGrid');
    	me.store.load();
    	
    	var onAddClick = function(){
            var window = Ext.create('uber.view.tutor.CategoryWindow');
            window.show();
        };
    	
    	this.columns = [{
    		text: 'Category',
    		dataIndex: 'CATEGORY_TITLE',
    		align: 'left',
    		flex: 1,
    	},{
    		text: 'Subject',
    		dataIndex: 'SUBJECT_TITLE',
    		align: 'left',
    		flex: 1,
    	},{
    		xtype: 'actioncolumn',
            width: 30,
            sortable: false,
            menuDisabled: true,
            items: [{
                iconCls: 'x-fa fa-minus-circle delete-button',
                tooltip: 'Delete Row',
//                scope: this,
                handler: 'onRemoveClick'
            }]
    	}],
//    	this.selModel = {
//            type: 'cellmodel'
//        },
    	this.tbar = [{
    		xtype: 'button',
//    		scope: this,
    		text: 'Add',
    		handler: onAddClick
    	}],
    	
    	this.callParent(arguments);
    },
});