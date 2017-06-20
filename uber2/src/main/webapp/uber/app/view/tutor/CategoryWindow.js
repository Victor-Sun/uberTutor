Ext.define('uber.view.tutor.CategoryWindow',{
	extend: 'Ext.window.Window',
	xtype: 'categoryWindow',
	
//	width: 400,
    height: 350,
    title: 'Add Subject Window',
    name: 'addWindow',
    layout: 'fit',
	controller: 'tutorRegistration',
	initComponent: function () {
		var categoryStore = Ext.create('uber.store.category.Category');
		var subjectStore = Ext.create('uber.store.subject.Subject');
		
		categoryStore.load();
		
		var category = Ext.create('Ext.form.field.ComboBox',{
			id: 'category',
			fieldLabel: 'Category',
            labelAlign: 'top',
            triggerAction: 'all',
            store: categoryStore,
            displayField: 'TITLE',
            valueField: 'ID',
            queryModel: 'local',
            editable:false,
            name: 'category',
            listeners: {
                change: function (combo, newValue, oldValue, eOpts) {
                    subjectStore.load({params:{categoryId:newValue}});
                    if (subject.newValue != ''){
                    	subject.setValue('');
                    }
                }
            }  
		});
		var subject = Ext.create('Ext.form.field.ComboBox',{
			id: 'subject',
			fieldLabel: 'Subject',
            labelAlign: 'top',
            triggerAction: 'all',
            store: subjectStore,
            displayField: 'TITLE',
            valueField: 'ID',
            queryMode:'local',
            editable:false,
            name:'subject'
		});
		
        this.items = [{
            xtype: 'form',
            itemId: 'subjectForm',
            reference: 'addForm',
            layout: {
            	type: 'vbox',
            	align: 'stretchmax'
            },
            defaults: {
                margin: 5,
                defaults: {
                	padding: 5
                }
            },
            items: [{
            	xtype: 'fieldcontainer',
            	layout: {
                    type: 'hbox',
                    align: 'stretchmax'
                },
            	items: [category,subject]
            },{
            	xtype: 'fieldcontainer',
            	flex: 1,
            	layout: {
                    type: 'hbox',
                    align: 'stretch'
                },
            	items: [{
            		xtype: 'textarea',
            		flex: 1,
            		fieldLabel: 'Description (Max 1000 characters)',
            		labelAlign: 'top',
            		anchor: '100%',
            		name: 'description',
            		height: 100
            	}]
            }],
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'bottom',
                items: ['->',{
                    xtype: 'button',
                    text: 'Submit',
                    handler: 'submit'
                },{
                    xtype: 'button',
                    text: 'Cancel',
                    handler: 'cancel'
                }]
            }]
        }];
		
		this.callParent();
	}
});