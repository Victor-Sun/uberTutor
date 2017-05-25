Ext.define('uber.view.profile.Profile', {
    extend: 'Ext.panel.Panel',
    xtype: 'profile',

    requires: [
       // 'uber.view.profile.ProfileController'
    ],
    cls: 'profile-panel-outer',
    controller: 'profile',
    layout: 'center',
    items: [{
    	xtype: 'panel',
    	border: true,
    	cls: 'profile-panel',
    	layout: {
    		type: 'vbox',
    		align: 'stretch'
    	},
    	items: [{
    		xtype: 'container',
            cls: 'profile-wrap',
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
            items: [{
                xtype: 'image',
                border: true,
                width: 80,
                height: 80,
                style: {
                    borderRadius: '5px'
                }
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
