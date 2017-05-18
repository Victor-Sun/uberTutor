Ext.define('uber.view.profile.Profile', {
    extend: 'Ext.panel.Panel',
    xtype: 'profile',

    requires: [
       'uber.view.profile.ProfileController'
    ],

    controller: 'profile',
//    viewModel: 'main',
//    title: '',

    items: [{
    	xtype: 'form',
    	reference: 'formpanel',
    	items: [{
    		xtype: 'fieldset',
    		items: [{
    			xtype: 'textfield',
    			name: 'fullname',
    			fieldLabel: 'Full name'
    		},{
    			xtype: 'textfield',
    			name: 'username',
    			fieldLabel: 'Username'
    		},{
    			xtype: 'textfield',
    			name: 'email',
    			fieldLabel: 'Email'
    		}]
    	},{
    		xtype: 'toolbar',
    		items: [{
    			xtype: 'button',
    			text: 'Save',
    			handler:'update'
    		}]
    	}]
    }]
});
