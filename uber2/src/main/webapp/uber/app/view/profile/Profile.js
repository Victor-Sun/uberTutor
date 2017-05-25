Ext.define('uber.view.profile.Profile', {
    extend: 'Ext.panel.Panel',
    xtype: 'profile',

    requires: [
       // 'uber.view.profile.ProfileController'
    ],
    
    controller: 'profile',
    layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'profile-container',
    items: [{
    	xtype: 'panel',
		flex: 1,
    	cls: 'profile-panel',
    	layout: {
    		type: 'vbox',
    		align: 'stretch'
    	},
    	items: [{
    		xtype: 'container',
            layout: 'hbox',
            items: [{
                margin: 5,
                html: '<h2>Personal Information</h2>'
            },{
                xtype: 'button',
                margin: 5,
                text: 'edit',
                handler: 'profilemanage'
            }]
        },{
            xtype: 'container',
            layout: 'hbox',
            style: {
                borderRadius: '5px'
            },
            items: [{
                xtype: 'image',
                width: 80,
                height: 80,
                
            },{
                xtype: 'component',
                html: '<h3>User Avatar</h3>'
            }]
        },{
            xtype: 'form',
            margin: 5,
            reference: 'formpanel',
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            defaults: {
                labelAlign: 'top',
                anchor: '100%'
            },
            items: [{
                xtype: 'textfield',
                name: 'fullname',
                fieldLabel: 'Name'
            },{
                xtype: 'textfield',
                name: 'email',
                fieldLabel: 'Email'
            },{
                xtype: 'textfield',
                name: 'modile',
                fieldLabel: 'Mobile'
            }]
    	}]
    }]
});
