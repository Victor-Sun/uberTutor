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
        
        'uber.view.main.MainPage',
        'uber.view.main.MainController',
        'uber.view.main.MainModel',
    ],

    controller: 'main',
    viewModel: 'main',
    layout: 'border',
    items: [{
    	xtype: 'toolbar',
    	cls: 'shadow nav-bar',
    	region: 'north',
		items: [{
			xtype: 'component',
			html: '<h1 style="color: #fff;">UberTutor</h1>'
		},'->',{
			xtype: 'button',
			iconCls: 'x-fa fa-user',
			itemId:'userNameItemId',
			text: '',
			menu: {
				items: [{
					text: 'Main Page',
					handler: 'mainpage'
				},{
					text: 'Search for Tutors',
					handler: 'search'
//				},{
//					html: '<hr>'
				},{
					text: 'Change Password',
					handler: 'changepassword'
				},{
					text: 'Profile',
					handler: 'profile'
				},{
					text: 'Sessions',
					handler: 'sessions'
				},{
					text: 'Tutor Registration',
					handler: 'registration'
				},{
					text: 'Logout',
					handler: 'logout'
				}],
			}
		}]
	},{
		xtype: 'container',
		region: 'center',
		reference: 'mainContainerWrap',
		cls: 'main-container-wrap',
		layout: {
	        type: 'border',
//	        animate: true,
//	        animatePolicy: {
//	            x: true,
//	            width: true
//	        }
	    },
		items: [{
			xtype: 'panel',
			region: 'center',
			layout:{
				type:'vbox',
				align:'stretch'
			},
			style: {
				'background-color': 'transparent',
			},
			items: [{
				xtype: 'container',
				flex: 1,
				reference: 'mainCardPanel',
				itemId: 'mainCardPanel',
				cls: 'main-container',
//				scrollable: 'y',
				layout: {
					type: 'card',
					anchor: '100%'
				},
				items: [{
//					xtype: 'mainpage',
//					itemId: 'mainpage'
//				},{
//					xtype: 'profile',
//					itemId: 'profile'
//				},{
//					xtype: 'changeprofile',
//					itemId: 'changeprofile'
//				},{
//					xtype: 'changepassword',
//					itemId: 'changepassword'
//				},{
//					xtype: 'search',
//					itemId: 'search'
//				},{
//					xtype: 'searchresults',
//					itemId: 'searchresults'
//				},{
//					xtype: 'tutorprofile',
//					itemId: 'tutorprofile'
//				},{
//					xtype: 'makerequest',
//					itemId: 'makerequest'
//				},{
//					xtype: 'feedback',
//					itemId: 'feedback'
//				},{
//					xtype: 'sessions',
//					itemId: 'sessions'
//				},{
//					xtype: 'mysession',
//					itemId: 'mysession'
//				},{
//					xtype: 'mysessionstudent',
//					itemId: 'mysessionstudent'
//				},{
//					xtype: 'mysessiontutor',
//					itemId: 'mysessiontutor'
//				},{
//					xtype: 'mysessionadmin',
//					itemId: 'mysessionadmin'
//				},{
//					xtype: 'sessioninfo',
//					itemId: 'sessioninfo'
//				},{
//					xtype: 'tutorregistration',
//					itemId: 'tutorregistration'
				}]
			}]
			
		}]
    }]
});
