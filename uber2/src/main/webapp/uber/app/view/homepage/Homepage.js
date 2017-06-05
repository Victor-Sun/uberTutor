Ext.define('uber.view.homepage.Homepage',{
	extend: 'Ext.container.Viewport',
	xtype: 'homepage',
	itemId: 'homepage',
	
	requires: [ 'uber.view.login.Login', 'uber.view.homepage.HomePageController'],
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	controller: 'homepage',
	items: [{
		xtype: 'panel',
		flex: 1,
		layout: {
			type: 'border',
		},
		items: [{
			xtype: 'toolbar',
			cls: 'shadow nav-bar',
	    	region: 'north',
			items: [{
				xtype: 'component',
				html: '<h1 style="color: #fff;">UberTutor</h1>'
			},'->',{
				xtype: 'button',
				text: 'Sign Up',
				handler: 'signup'
			},{
				xtype: 'button',
				text: 'Sign In',
				handler: 'signin'
			}]
		},{
			xtype: 'panel',
			region: 'center',
			flex: 1,
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
		            	xtype: 'component',
		                margin: 5,
		                html: '<h2>UberTutor</h2>'
		            }]
		    	},{
		    		xtype: 'panel',
		    		flex: 1,
		    		layout: {
		    			type: 'vbox',
		    			align: 'stretch'
		    		},
		    		items: [{
		    			flex: 1,
		    			html: '<h4>Homepage content here</h4>'
		    		}]
		    	}]
			}]
		}]
	}]
});