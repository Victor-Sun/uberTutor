Ext.define('uber.view.tutor.TutorRegistrationGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'tutorRegistrationGrid',
	reference: 'tutorRegistrationGrid',
	
	flex: 1,
	requires: [
//	   'uber.store.category.Category',
	    
	   'uber.store.grid.TutorRegistrationGrid',
       'Ext.selection.CellModel',
       'Ext.grid.*',
       'Ext.data.*',
       'Ext.util.*',
       'Ext.form.*',
    ],
    controller: 'tutorRegistration',
//    store: 'tutorregistrationgrid',
    frame: true,
//    plugins: {ptype: 'cellediting', clicksToEdit: 1},
    initComponent: function () {
    	var me = this;
    	me.store =  Ext.create('uber.store.grid.TutorRegistrationGrid');
    	
//    	Ext.apply(this, {
//    		
//    	});
    	
    	
    	var onAddClick = function(){
            var window = Ext.create('uber.view.tutor.CategoryWindow');
            window.show();
        };
    	
    	
    	this.columns = [{
    		xtype: 'checkcolumn',
            dataIndex: '',
            width: 90,
            stopSelection: false
    	},{
    		text: 'Category',
    		dataIndex: 'category',
    		align: 'left',
    		flex: 1,
    	},{
    		text: 'Subject',
    		dataIndex: 'subject',
    		align: 'left',
    		flex: 1,
    	},{
    		xtype: 'actioncolumn',
            width: 30,
            sortable: false,
            menuDisabled: true,
            items: [{
                iconCls: 'x-fa fa-minus-circle',
                tooltip: 'Delete Row',
                scope: this,
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
    	}]
    	
    	this.callParent();
    },
});