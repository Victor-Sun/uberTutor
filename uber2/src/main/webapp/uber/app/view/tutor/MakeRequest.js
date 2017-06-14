//Precondition
//When student navigates to this page, the following information should be brought over
//Category Subject TutorInfo
//

Ext.define('uber.view.tutor.MakeRequest',{
	extend: 'Ext.panel.Panel',
	xtype: 'makerequest',

    layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
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
	    	xtype: 'panel',
			flex: 1,
	    	cls: 'uber-panel-inner',
	    	layout: {
	    		type: 'vbox',
	    		align: 'stretch'
	    	},
	    	items: [{
	    		xtype: 'container',
	            layout: 'hbox',
	            items: [{
	                margin: 5,
	                html: '<h2>Make a request</h2>'
	            }]
//	    	},{
//	    		xtype: 'fieldset',
//	    		title: 'Tutor Info',
//	    		defaults: {
//	    			labelAlign: 'top',
//	    		},
//	    		items: [{
//	    			xtype: 'textfield',
//	    			margin: 5,
//	    			fieldLabel: 'Tutor Name'
//	    		},{
//	    			xtype: 'container',
//	    			layout: 'hbox',
//	    			defaults: {
//	        			labelAlign: 'top',
//	        			margin: 5
//	        		},
//	    			items: [{
//	    				xtype: 'textfield',
//		    			fieldLabel: 'Category'
//		    		},{
//		    			xtype: 'textfield',
//		    			fieldLabel: 'Subject'
//	    			}]
//	    		}]
	    	},{
	    		xtype: 'form',
	    		margin: 15,
	            cls: 'form-layout',
	            reference: 'formpanel',
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
	        			fieldLabel: 'Title'
	            	}]
	            },{
	            	xtype: 'container',
	            	items: [{
	            		xtype: 'textarea',
	            		width: 350,
	            		labelAlign: 'top',
		            	margin: 5,
		            	fieldLabel: 'Description'
	            	}]
	            }],
	            //TODO: SUBMIT TUTOR REQUEST
	            dockedItems: [{
	            	xtype: 'toolbar',
	            	dock: 'bottom',
	            	items: [{
	            		xtype: 'button',
	            		text: 'Submit',
	            		handler: function () {
	            			var form = this.up('form');
	            			var formValue = form.getValues();
	            			console.log(formValue);
	            			Ext.toast('You have submitted the following values!'+ formValue);
	            		}
	            	}]
	            }]
	    	}]
	    }]
		this.callParent(arguments);
	}
    
});