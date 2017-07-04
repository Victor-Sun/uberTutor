Ext.define('uber.view.session.Feedback', {
    extend: 'Ext.form.Panel',
    xtype: 'feedback',

    requires: [
       // 'uber.view.profile.ProfileController'
    ],
    
    controller: 'profile',
    layout: {
		type: 'vbox',
		align: 'stretch'
	},
    items: [{
    	xtype: 'panel',
		flex: 1,
    	layout: {
    		type: 'vbox',
    		align: 'stretch'
    	},
    	items: [{
    		xtype: 'container',
            layout: 'hbox',
            items: [{
                margin: 5,
                html: '<h3>Feedback</h3>'
            }]
    	},{
    		xtype: 'container',
            layout: 'hbox',
            items: [{
                xtype: 'container',
                margin: 5,
                layout: {
                	type: 'vbox',
                },
                items: [{
                	xtype: 'component',
                	margin: '5 5 0 15',
                	html: 'Rating'
                },{
                	xtype: 'container',
                	margin: '20 5 5 15',
                	items: [{
                		xtype: 'rating',
                		limit: '5',
                		rounding: '0.5',
                	}]
                }]
            }]
    	},{
    		xtype: 'fieldcontainer',
            margin: 5,
            reference: 'formpanel',
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            defaults: {
                labelAlign: 'top',
                readOnly: true,
            },
            items: [{
                xtype: 'textarea',
                margin: '5 10 5 10',
                height: 75,
//                width: 425,
                name: 'comment',
                fieldLabel: 'Comment',
            }]
    	}]
    }]
});