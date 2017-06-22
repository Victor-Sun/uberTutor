//Precondition
//When student navigates to this page, the following information should be brought over
//Category Subject TutorInfo
//

Ext.define('uber.view.tutor.MakeRequest',{
	extend: 'Ext.panel.Panel',
	xtype: 'makerequest',

	controller: 'makerequest',
    layout: {
		type: 'vbox',
		align: 'stretch'
	},
//	cls: 'uber-panel',
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
            displayField: 'title',
            valueField: 'id',
            queryModel: 'local',
            editable:false,
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
            name:'subject'
		});
		
		this.items = [{
	    	xtype: 'panel',
			flex: 1,
//	    	cls: 'uber-panel-inner',
	    	layout: {
	    		type: 'vbox',
	    		align: 'stretch'
	    	},
	    	items: [{
	    		xtype: 'panel',
//	    		border: true,
//	    		cls: 'uber-header',
	    		layout: 'hbox',
	    		items: [{
	    			xtype: 'container',
		            layout: 'hbox',
		            items: [{
		                margin: 5,
		                html: '<h2>Make a request</h2>'
		            }]
	    		}]
	    	},{
	    		xtype: 'form',
	    		margin: 15,
	            cls: 'form-layout',
	            itemId: 'formpanel',
	            layout: {
	                type: 'vbox',
	                align: 'stretch'
	            },
	            defaults: {
	            	labelAlign: 'top',
	            },
	            items: [{
	            	xtype: 'container',
	    			layout: 'hbox',
	    			defaults: {
	        			labelAlign: 'top',
	        			margin: 5
	        		},
	    			items: [category,subject]
	            },{
	            	xtype: 'container',
	            	items: [{
	            		xtype: 'textfield',
	            		labelAlign: 'top',
	            		width: 350,
	            		margin: 5,
	            		name: 'title',
	        			fieldLabel: 'Title (The more descriptive you can be, the better) *'
	            	}]
	            },{
	            	xtype: 'container',
	            	items: [{
	            		xtype: 'textarea',
	            		width: 500,
	            		labelAlign: 'top',
		            	margin: 5,
		            	name: 'description',
		            	fieldLabel: 'Description (max 1000 characters) *'
	            	}]
	            }],
	            //TODO: SUBMIT TUTOR REQUEST
	            dockedItems: [{
	            	xtype: 'toolbar',
	            	dock: 'bottom',
	            	items: [{
	            		xtype: 'button',
	            		text: 'Submit',
	            		handler: 'save'
	            	}]
	            }]
	    	}]
	    }]
		this.callParent(arguments);
	}
    
});