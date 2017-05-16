/**
 * This class is the main view for the application. It is specified in app.js as the
 * "mainView" property. That setting automatically applies the "viewport"
 * plugin causing this view to become the body element (i.e., the viewport).
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('uber.view.main.Main', {
	extend: 'Ext.container.Viewport',
    xtype: 'app-main',

    requires: [
        'Ext.plugin.Viewport',
        'Ext.window.MessageBox',

        'uber.view.main.MainController',
        'uber.view.main.MainModel',
        'uber.view.main.List'
    ],

    controller: 'main',
    viewModel: 'main',
    
    items: [{
    	xtype: 'tabpanel',
    	rotation: 0,
    	reference: 'uberTabPanel',
    	id: 'uberTabPanel',
    	items: [{
            title: 'Home',
//          xtype: 'home',
            iconCls: 'x-fa fa-home',
        },{
            title: 'Profile',
            xtype: 'profile',
            iconCls: 'x-fa fa-user',
        }]
    }]
});
