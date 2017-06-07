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
//	cls: 'uber-panel',
    items: [{
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
    	},{
    		xtype: 'fieldset',
    		title: 'Tutor Info',
    		defaults: {
    			labelAlign: 'top',
    		},
    		items: [{
    			xtype: 'textfield',
    			margin: 5,
    			fieldLabel: 'Tutor Name'
    		},{
    			xtype: 'container',
    			layout: 'hbox',
    			defaults: {
        			labelAlign: 'top',
        			margin: 5
        		},
    			items: [{
    				xtype: 'textfield',
	    			fieldLabel: 'Category'
	    		},{
	    			xtype: 'textfield',
	    			fieldLabel: 'Subject'
    			}]
    		}]
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
            	items: [{
            		xtype: 'textfield',
            		labelAlign: 'top',
            		width: 350,
            		margin: 5,
        			fieldLabel: 'Title'
            	}]
            },{
            	xtype: 'textarea',
            	margin: 5,
            	fieldLabel: 'Description'
            }],
            //TODO: SUBMIT TUTOR REQUEST
//            dockedItems: [{
//            	xtype: 'toolbar',
//            	dock: 'bottom',
//            	items: [{
//            		xtype: 'button',
//            		text: 'Submit'
//            	}]
//            }]
    	}]
    }]
});