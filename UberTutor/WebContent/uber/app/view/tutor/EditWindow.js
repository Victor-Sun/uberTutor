Ext.define('uber.view.tutor.EditWindow',{
	extend: 'Ext.window.Window',
	xtype: 'editWindow',
	
	displayId: '',
	initComponent: function () {
		var me = this;
		me.store = Ext.create('uber.store.session.SessionInfo',{
			proxy: {
				type: 'ajax',
//				url: '/UberTutor/main/my-session!displayRequestInfo.action',
				params: {
					requestId:this.id,
				},
				reader: {
					type: 'json',
					rootProperty: 'data'
				},
//				success: {
//					if (this.status = IN_PROCESS)
//				}
			},
		});
		
		var editForm = Ext.create('Ext.form.Panel',{
			layout: {
	            type: 'vbox',
	            align: 'stretch'
	        },
			items: [{
				xtype: 'panel',
				padding: 15,
				items: [{
					layout: {
			            type: 'vbox',
			            align: 'stretch'
			        },
					defaults:{
						layout: {
							type: 'vbox',
						},
						defaults: {
							layout: {
								type: 'hbox'
							},
							margin: 5,
						}
					},
					items: [{
						// Request Info
						xtype: 'fieldset',
						defaults: {
							defaults: {
								labelAlign: 'top'
							},
						},
						items: [{
							xtype: 'fieldcontainer',
							items: [{
								xtype: 'textfield',
								id: 'description',
								fieldLabel: 'Description',
								name: 'description'
							}]
						}]
					}]
				}]
			}]
		});
		
		this.tools = [{
			xtype: 'button',
			hidden: true,
			text: 'Cancel'
		},{
			xtype: 'button',
			hidden: true,
			text: 'Save'
		}];
//		me.store.load();
		this.items = [
			// Session Info Form
			editForm
		];
		this.callParent(arguments);
	}
});