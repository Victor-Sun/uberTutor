/**
 * This class is the main view for the application. It is specified in app.js as the
 * "mainView" property. That setting causes an instance of this class to be created and
 * added to the Viewport container.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('uber.view.main.Main', {
    extend: 'Ext.tab.Panel',
    xtype: 'app-main',

    requires: [
        'Ext.MessageBox',

        'uber.view.main.MainController',
        'uber.view.main.MainModel'
    ],

    controller: 'main',
    viewModel: 'main',
    title: '',
    layout: {
    	type: 'card',
    	align: 'stretch'
    },
    reference: 'uberTab',
    id: 'uberTab',

    defaults: {
        tab: {
            iconAlign: 'top'
        },
        styleHtmlContent: true,
        layout: 'fit'
    },
    tabBarPosition: 'bottom',
    items: [{
        title: 'Home',
        iconCls: 'x-fa fa-home',
        xtype: 'home'
    },{
    	xtype: 'profile',
        title: 'Profile',
        iconCls: 'x-fa fa-user'
	},{     
		title: 'Search',
		xtype: 'search',
		iconCls: 'x-fa fa-search',
		layout: 'vbox'
    }]
});
