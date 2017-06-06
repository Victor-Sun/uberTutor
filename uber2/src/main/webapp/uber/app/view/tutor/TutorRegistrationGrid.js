Ext.define('uber.view.tutor.TutorRegistrationGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'tutorRegistrationGrid',
	reference: 'tutorRegistrationGrid',
	
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
    	var store = Ext.create('uber.model.grid.TutorRegistrationGridRow',{
            // destroy the store if the grid is destroyed
            autoDestroy: true,
            model: uber.model.grid.TutorRegistrationGridRow,
            proxy: {
                type: 'ajax',
//                url: '',
                reader: {
                    type: 'json',
                }
            },
            sorters: [{
                property: 'common',
                direction:'ASC'
            }]
        });
    	
    	var comboBoxRenderer = function(category) {
		  return function(value) {
		    var idx = category.store.find(category.valueField, value);
		    var rec = category.store.getAt(idx);
		    return (rec === null ? '' : rec.get(category.displayField) );
		  };
		};
		
		var comboBoxRenderer2 = function(subject) {
			  return function(value) {
			    var idx = subject.store.find(subject.valueField, value);
			    var rec = subject.store.getAt(idx);
			    return (rec === null ? '' : rec.get(subject.displayField) );
			  };
			};
    	
    	var categoryStore = Ext.create('uber.store.category.Category');
    	var subjectStore = Ext.create('uber.store.subject.Subject');
    	categoryStore.load();
    	var category = Ext.create('Ext.form.field.ComboBox',{
    		
    	});
    	
    	
    	var subject = Ext.create('Ext.form.field.ComboBox',{
    		
    	});
    	
    	var onAddClick = function(){
            var window = Ext.create('Ext.window.Window',{
            	width: 400,
            	height: 200,
            	name: 'addWindow',
            	layout: 'fit',
            	items: [{
            		xtype: 'form',
            		reference: 'addForm',
            		layout: {
            			type: 'hbox',
            			align: 'stretchmax'
            		},
            		defaults: {
            			margin: 15,
            		},
            		items: [{
            			xtype: 'combobox',
            			fieldLabel: 'Category',
                		labelAlign: 'top',
                        triggerAction: 'all',
                		store: categoryStore,
                		displayField: 'TITLE',
                		valueField: 'ID',
                		queryModel: 'local',
                		listeners: {
            				change: function (combo, newValue, oldValue, eOpts) {
            					subjectStore.load({params:{categoryId:newValue}});
            				}
            			}
            		},{
            			xtype: 'combobox',
            			fieldLabel: 'Subject',
                		labelAlign: 'top',
                        triggerAction: 'all',
                        store: subjectStore,
                		displayField: 'TITLE',
                		valueField: 'ID',
                		queryMode:'local',
                		name: 'subject'
            		}],
            		dockedItems: [{
            			xtype: 'toolbar',
            			dock: 'bottom',
            			items: [{
            				xtype: 'button',
            				text: 'Submit',
            				handler: 'submit'
            			},'->',{
            				xtype: 'button',
            				text: 'Cancel',
            				handler: 'cancel'
            			}]
            		}]
            	}]
            });
            window.show();
        };
    	
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
                    handler: this.onRemoveClick
                }]
    		}],
    		selModel: {
                type: 'cellmodel'
            },
    		tbar: [{
    			xtype: 'button',
    			scope: this,
				text: 'Add',
				handler: onAddClick
    		}]
    	});
    	this.callParent();
    },
//    loadStore: function() {
//        this.getStore().load();
//    },
    
//    onAddClick: function(){
//        // Create a model instance
//        var rec = new uber.model.grid.TutorRegistrationGridRow({
//            category: '',
//            subject: ''
//        });
//        var newRow = this.store.getCount();
//        rec.data.isNew = true;
//        this.getStore().insert(newRow, rec);
////        this.startEditing(newRow, 0);
//    },
    
    onRemoveClick: function(grid, rowIndex){
        this.getStore().removeAt(rowIndex);
    }
});