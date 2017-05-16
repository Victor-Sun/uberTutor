Ext.define('uber.view.request.TutorResponse', {
	extend: 'Ext.Panel',
	xtype: 'tutorresponse',
	items: [{
		xtype: 'formpanel',
		items: [{
			xtype: 'fieldset',
    		title: 'Request name here',
    		items: [{
    			xtype: 'textareafield',
    			name: 'detail',
    			label: 'Details'
    		},{
    			xtype: 'toolbar',
    			items: [{
    				xtype: 'button',
    				text: 'Accept'
    			},{
    				xtype: 'button',
    				text: 'Decline'
    			}]
    		}]
		}]
	}]
});