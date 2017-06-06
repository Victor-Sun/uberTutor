Ext.define('uber.view.tutor.TutorRegistrationGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'tutorRegistrationGrid',
	
	flex: 1,
	requires: [
	   'uber.store.category.Category',
	           
       'Ext.selection.CellModel',
       'Ext.grid.*',
       'Ext.data.*',
       'Ext.util.*',
       'Ext.form.*',
    ],
    controller: 'tutorRegistration',
    frame: true,
    initComponent: function () {
    	var store = new Ext.data.Store({
            // destroy the store if the grid is destroyed
            autoDestroy: true,
            model: uber.model.grid.TutorRegistrationGridRow,
//            proxy: {
//                type: 'ajax',
//                url: '',
//                reader: {
//                    type: 'json',
//                }
//            },
            sorters: [{
                property: 'common',
                direction:'ASC'
            }]
        });
    	var categoryStore = Ext.create('uber.store.category.Category');
    	var subjectStore = Ext.create('uber.store.subject.Subject');
    	categoryStore.load();
    	var category = Ext.create('Ext.form.field.ComboBox',{
            triggerAction: 'all',
    		store: categoryStore,
    		displayField: 'TITLE',
    		valueField: 'TITLE',
    		queryModel: 'local',
    		listeners: {
				change: function (combo, newValue, oldValue, eOpts) {
					subjectStore.load({params:{categoryId:newValue}});
				}
			}
    	});
    	
    	
    	var subject = Ext.create('Ext.form.field.ComboBox',{
            triggerAction: 'all',
            store: subjectStore,
    		displayField: 'TITLE',
    		valueField: 'ID',
    		queryMode:'local',
    		name: 'subject'
    	});
    	
    	Ext.apply(this, {
    		plugins: {ptype: 'cellediting', clicksToEdit: 1},
    	    store: store,
    		columns: [{
    			xtype: 'checkcolumn',
    	        dataIndex: '',
    	        width: 90,
    	        stopSelection: false
    		},{
    			text: 'Category',
    			dataIndex: 'category',
    			flex: 1,
    			editor: category,
    		},{
    			text: 'Subject',
    			dataIndex: 'subject',
    			flex: 1,
    			editor: subject
    		},{
    			xtype: 'actioncolumn',
                width: 30,
                sortable: false,
                menuDisabled: true,
                items: [{
                    iconCls: 'x-fa fa-minus-circle',
                    tooltip: 'Delete Row',
                    scope: this,
                    handler: this.onRemoveClick
                }]
    		}],
    		selModel: {
                type: 'cellmodel'
            },
    		tbar: [{
    			xtype: 'button',
    			scope: this,
				text: 'Add Category/Subject',
				handler: this.onAddClick
    		}]
    	});
    	this.callParent();
    },
//    loadStore: function() {
//        this.getStore().load();
//    },
    
    onAddClick: function(){
        // Create a model instance
        var rec = new uber.model.grid.TutorRegistrationGridRow({
            category: '',
            subject: ''
        });
        var newRow = this.store.getCount();
        rec.data.isNew = true;
        this.getStore().insert(newRow, rec);
//        this.startEditing(newRow, 0);
    },
    
    onRemoveClick: function(grid, rowIndex){
        this.getStore().removeAt(rowIndex);
    }
});