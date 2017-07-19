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
        		xtype: 'hidden',
        		itemId: 'requestId',
        		name: 'requestId'
        	},{
        		xtype: 'hidden',
        		itemId: 'hasFeedback',
        		name: 'hasFeedback'
        	},{
        		xtype: 'hidden',
        		itemId: 'feedbackId',
        		name: 'FEEDBACK_ID'
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
                    		allowBlank: false,
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
//                    		rounding: '0.5',
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
                allowBlank: false,
                name: 'FEEDBACK',
                fieldLabel: 'Feedback',
        	}]
        }],
    	this.callParent(arguments);
    }
});