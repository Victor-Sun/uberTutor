Ext.define('uber.view.tutor.CategoryWindow',{
	extend: 'Ext.window.Window',
	xtype: 'categoryWindow',
	
	width: 400,
    height: 200,
    name: 'addWindow',
    layout: 'fit',
	controller: 'tutorRegistration',
	initComponent: function () {
		var categoryStore = Ext.create('uber.store.category.Category');
		var subjectStore = Ext.create('uber.store.subject.Subject');
		
		categoryStore.load();
//    	 var comboBoxRenderer = function(category) {
//           return function(value) {
//             var idx = category.store.find(category.valueField, value);
//             var rec = category.store.getAt(idx);
//             return (rec === null ? '' : rec.get(category.displayField) );
//           };
//         };
//
//         var comboBoxRenderer2 = function(subject) {
//           return function(value) {
//             var idx = subject.store.find(subject.valueField, value);
//             var rec = subject.store.getAt(idx);
//             return (rec === null ? '' : rec.get(subject.displayField) );
//           };
//         };
		
		var category = Ext.create('Ext.form.field.ComboBox',{
			id: 'category',
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
            name: 'subject'
		});
		
		
        this.items = [{
            xtype: 'form',
            reference: 'addForm',
            layout: {
                type: 'hbox',
                align: 'stretchmax'
            },
            defaults: {
                margin: 15,
            },
            items: [category,subject
//            {
//                xtype: 'combobox',
//                fieldLabel: 'Category',
//                labelAlign: 'top',
//                triggerAction: 'all',
//                store: categoryStore,
//                displayField: 'TITLE',
//                valueField: 'ID',
//                queryModel: 'local',
//                listeners: {
//                    change: function (combo, newValue, oldValue, eOpts) {
//                        subjectStore.load({params:{categoryId:newValue}});
//                    }
//                }
//            },{
//                xtype: 'combobox',
//                fieldLabel: 'Subject',
//                labelAlign: 'top',
//                triggerAction: 'all',
//                store: subjectStore,
//                displayField: 'TITLE',
//                valueField: 'ID',
//                queryMode:'local',
//                name: 'subject'
//            }
            ],
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
        }];
		
		this.callParent();
	}
});