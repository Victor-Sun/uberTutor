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
//        		xtype: 'hiddenfield',
//        		itemId: 'feedbackSubmitted',
//        		name: 'feedbackSubmitted'
//        	},{
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
                    		xtype: 'hidden',
                    		itemId: 'ratingHidden',
                    		name: 'RATING',
                    		listeners: {
                    			change: function (newValue , oldValue , eOpts, value )  {
                    				var rating = this.up('container').down('#rating');
                    				rating.setValue(oldValue);
                    			}
                    		}
                    	},{
                    		xtype: 'rating',
                    		itemId: 'rating',
                    		limit: '5',
                    		listeners: {
                    			change: function (picker, value) {
                    				var ratingHidden = this.up('container').down('#ratingHidden');
                    				if (ratingHidden.getValue() != value) {
                    					ratingHidden.setValue(value);
                    				}
                    	        }
                    		}
                    	}]
                    }]
                }]
        	},{
        		xtype: 'textarea',
                labelAlign: 'top',
                margin: '5 10 5 10',
                height: 125,
//                width: 425,
                itemId: 'feedback',
                name: 'FEEDBACK',
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