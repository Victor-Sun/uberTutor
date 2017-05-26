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
			text: 'username',
			menu: {
				items: [{
					text: 'Search for Tutors',
					handler: 'search'
				},{
					html: '<hr>'
				},{
					text: 'Profile',
					handler: 'profile'
				},{
					text: 'Requests',
					handler: 'requests'
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
				cls: 'main-container',
//				scrollable: 'y',
				layout: {
					type: 'card',
					anchor: '100%'
				},
				items: [{
					xtype: 'profile',
					itemId: 'profile'
				},{
					xtype: 'profilemanagement',
					itemId: 'profilemanagement'
				},{
					xtype: 'search',
					itemId: 'search'
				},{
					xtype: 'searchresults',
					itemId: 'searchresults'
				},{
					xtype: 'tutorprofile',
					itemId: 'tutorprofile'
				},{
					xtype: 'tutorrequest',
					itemId: 'tutorrequest'
				},{
					xtype: 'feedback',
					itemId: 'feedback'
				},{
					xtype: 'tutorrequest',
					itemId: 'tutorrequest'
				}]
			}]
			
		}]
    }]
});
