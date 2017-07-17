Ext.define('uber.view.tutor.CategoryWindow',{
	extend: 'Ext.window.Window',
	xtype: 'categoryWindow',
	
//	width: 400,
    height: 350,
    title: 'Add Subject',
    name: 'addWindow',
    layout: 'fit',
	controller: 'categoryWindow',
	initComponent: function () {
		var categoryStore = Ext.create('uber.store.category.Category');
		var subjectStore = Ext.create('uber.store.subject.Subject');
		
		categoryStore.load({
			failure: function(form, action) {
				Ext.getBody().unmask();
//				var result = uber.util.Util.decodeJSON(action.response.responseText);
				Ext.Msg.alert('Error', "An error has occured, please try again", Ext.emptyFn);
//				console.log(result.errors.reason);
			},
		});
		
		var category = Ext.create('Ext.form.field.ComboBox',{
			id: 'category',
			fieldLabel: 'Category',
            labelAlign: 'top',
            triggerAction: 'all',
            store: categoryStore,
            displayField: 'title',
            valueField: 'id',
            queryModel: 'local',
            editable:false,
            allowBlank: false,
            name: 'category',
            listeners: {
                change: function (combo, newValue, oldValue, eOpts) {
                    subjectStore.load({params:{categoryId:newValue}});
                    subject.clearValue();
                }
            }  
		});
		var subject = Ext.create('Ext.form.field.ComboBox',{
			id: 'subject',
			fieldLabel: 'Subject',
            labelAlign: 'top',
            triggerAction: 'all',
            store: subjectStore,
            displayField: 'title',
            valueField: 'id',
            queryMode:'local',
            editable:false,
            allowBlank: false,
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
            		itemId: 'subjectDescription',
            		allowBlank: false,
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