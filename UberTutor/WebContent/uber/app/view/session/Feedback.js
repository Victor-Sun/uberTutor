Ext.define('uber.view.session.Feedback', {
    extend: 'Ext.form.Panel',
    xtype: 'feedback',
    itemId: 'feedback',
    
    requires: [
       // 'uber.view.profile.ProfileController'
    ],
    
    controller: 'profile',
    layout: {
		type: 'vbox',
		align: 'stretch'
	},
    initComponent: function () {
    	this.items = [{
        	xtype: 'panel',
    		flex: 1,
        	layout: {
        		type: 'vbox',
        		align: 'stretch'
        	},
        	items: [{
        		xtype: 'hiddenfield',
        		itemId: 'feedbackSubmitted',
        		name: 'feedbackSubmitted'
        	},{
        		xtype: 'hiddenfield',
        		itemId: 'requestId',
        		name: 'requestId'
        	},{
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
                    		itemId: 'rating',
                    		name: 'rating',
                    		limit: '5',
                    		rounding: '0.5',
                    	}]
                    }]
                }]
        	},{
        		xtype: 'textarea',
                labelAlign: 'top',
                margin: '5 10 5 10',
                height: 125,
//                width: 425,
                name: 'feedback',
                fieldLabel: 'Feedback',
//        		xtype: 'fieldcontainer',
//                margin: 5,
//                reference: 'formpanel',
//                layout: {
//                    type: 'vbox',
//                    align: 'stretch'
//                },
//                defaults: {
//                    
//                    readOnly: true,
//                },
//                items: [{
//                    
//                }]
        	}]
        }],
    	this.callParent(arguments);
    }
});