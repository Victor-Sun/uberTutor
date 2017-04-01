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
        'uber.view.main.MainModel',
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
        styleHtmlContent: true
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
    // },{
    //     title: 'Search',
    //     xtype: 'search',
    //     iconCls: 'x-fa fa-search',
    //     layout: 'vbox'
    // },{
    //     title: 'User<br>Request',
    //     iconCls: 'x-fa fa-bullhorn',
    //     xtype: 'userrequest'
   	// },{
    // 	title: 'Tutor<br>Response',
    // 	iconCls: 'x-fa fa-comment-o',
    // 	xtype: 'tutorresponse'
    // },{
    // 	title: 'Feedback',
    // 	iconCls: 'x-fa fa-pencil-square-o',
    // 	xtype: 'feedback'
    // },{
    //     title: 'Verification',
    //     xtype: 'verification'
    // },{
    // 	title: 'Login/<br>Signup',
    //     layout: 'vbox',
    // 	items: [{
    //         xtype: 'toolbar',
    //         items: [{
    //             xtype: 'button',
    //             text: 'Login',
    //             handler: function() {
    //                 Ext.getCmp("sULPanel").setActiveItem(0);
    //             }
    //         },{
    //             xtype: 'button',
    //             text: 'Sign Up',
    //             handler: function() {
    //                 Ext.getCmp("sULPanel").setActiveItem(1);
    //             }
    //         }]
    //     },{
    // 		xtype: 'panel',
    //         flex: 1,
    //         reference: 'sULPanel',
    //         id: 'sULPanel',
    // 		layout: {
    // 			type: 'card',
    //             align: 'stretch'
    // 		},
    // 		items: [{
    // 			title: 'Login',
    // 			xtype: 'login'
    // 		},{
    // 			title: 'Signup',
    // 			xtype: 'signup'
    // 		}]
    // 	}]
    }]
});
