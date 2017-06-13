Ext.define('uber.view.session.SessionInfoWindow',{
	extend: 'Ext.window.Window',
	xtype: 'sessionInfoWindow',
	
	initComponent() {
		var me = this;
		var sessionInfo = Ext.create('Ext.form.Panel',{
			
		});
		me.store = Ext.create('uber.store.session.SessionInfo',{
			proxy: {
				type: 'ajax',
				url: '/uber2/main/my-session!displaySessionInfo.action',
				params: {
					requestId:this.requestId,
				},
				reader: {
					type: 'json',
					rootProperty: 'data'
				}
			},
		});
		var sessionInfoForm = Ext.create('Ext.form.Panel',{
			
		})
		
		me.store.load();
		this.items = [{
			// Session Info Form
			xtype: 'form',
			items: [{
				xtype: 'textfield',
				fieldLabel: 'Category',
				name: 'category'
			},{
				xtype: 'textfield',
				fieldLabel: 'Subject',
				name: 'subject'
			},{
				xtype: 'textfield',
				fieldLabel: 'Request Open Date',
				name: 'requestOpenDate'
			},{
				xtype: 'textfield',
				fieldLabel: 'Response Date'
			},{
				xtype: 'textfield',
				fieldLabel: 'Confirmation Date'
			}]
		},{
			// Contact Info
			xtype: 'form',
			items: [{
				xtype: 'textfield',
				fieldLabel: 'Tutor Name'
			},{
				xtype: 'textfield',
				fieldLabel: 'Student Name'
			}],
		}];
		this.callParent(arguments);
	}
});