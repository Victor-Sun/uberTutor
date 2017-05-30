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
            },
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
                xtype: 'component',
                html: '<h3>User Avatar</h3>'
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
                xtype: 'textfield',
                name: 'fullname',
                fieldLabel: 'Name',
                value: 'Joe Smoe'
            },{
                xtype: 'textfield',
                name: 'email',
                fieldLabel: 'Email',
                value: 'Test@Domain.com'
            },{
                xtype: 'textfield',
                name: 'modile',
                fieldLabel: 'Mobile',
                value: '12345678'
            }]
    	}]
    }]
});
