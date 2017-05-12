Ext.define('uber.view.profile.Profile', {
    extend: 'Ext.panel.Panel',
    xtype: 'profile',

    requires: [
    ],

//    controller: 'main',
//    viewModel: 'main',
//    title: '',

    items: [{
    	xtype: 'form',
    	items: [{
    		xtype: 'fieldset',
    		items: [{
    			xtype: 'textfield',
    			fieldLabel: 'First name',
    		},{
    			xtype: 'textfield',
    			fieldLabel: 'Last name'
    		},{
    			xtype: 'textfield',
    			fieldLabel: 'Username'
    		},{
    			xtype: 'textfield',
    			fieldLabel: 'Email'
    		}]
    	},{
    		xtype: 'toolbar',
    		items: [{
    			xtype: 'button',
    			text: 'Save',
    		}]
    	}]
    }]
});
