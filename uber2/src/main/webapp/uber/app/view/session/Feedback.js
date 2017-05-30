Ext.define('uber.view.session.Feedback', {
    extend: 'Ext.panel.Panel',
    xtype: 'feedback',

    requires: [
       // 'uber.view.profile.ProfileController'
    ],
    
    controller: 'profile',
    layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
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
                html: '<h2>Feedback</h2>'
            }]
    	},{
    		xtype: 'container',
            layout: 'hbox',
            items: [{
                xtype: 'container',
                margin: 5,
                cls: 'shadow image-container',
                items: [{
                	xtype: 'image',
	                width: 80,
	                height: 80,
                }]
                
            },{
                xtype: 'container',
                layout: {
                	type: 'vbox',
                },
                items: [{
                	xtype: 'component',
                	html: '<h3>Username</h3>'
                },{
                	xtype: 'container',
                	margin: 5,
                	layout: {
                		type: 'vbox',
                		
                	},
                	items: [{
                		xtype: 'rating',
                		limit: '5',
                		rounding: '0.5',
                	}]
                }]
            }]
    	},{
    		xtype: 'form',
            margin: 5,
            reference: 'formpanel',
            layout: {
                type: 'vbox',
                align: 'stretchmax'
            },
            defaults: {
                labelAlign: 'top',
                readOnly: true,
                width: 200
//                anchor: '100%'
            },
            items: [{
                xtype: 'textarea',
                width: 425,
                name: 'comment',
                fieldLabel: 'Comment',
            }],
            dockedItems: [{
            	xtype: 'toolbar',
            	dock: 'bottom',
            	items: [{
            		xtype: 'button',
            		text: 'Submit'
            	}]
            }]
    	}]
    }]
});