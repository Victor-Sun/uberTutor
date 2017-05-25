Ext.define('uber.view.homepage.Homepage',{
	extend: 'Ext.container.Viewport',
	xtype: 'homepage',
	
	items: [{
		xtype: 'container',
		layout: {
			type: 'vbox',
			align: 'stretch'
		},
		items: [{
			xtype: 'toolbar',
			items: [{
				xtype: 'component',
				html: '<h1>UberTutor</h1>'
			},'->',{
				xtype: 'button',
				text: 'Sign In',
				handler: function() {
    				this.up('homepage').destroy();
    				Ext.create('uber.view.login.LoginPage');
    			}
			}]
		}]
	}]
});