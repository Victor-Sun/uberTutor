Ext.define('uber.view.tutor.EditWindow',{
	xtype: 'editWindow',
	initComponent(){
		var me = this;
		me.store = Ext.create('uber.store.session.SessionInfo',{
			proxy: {
				type: 'ajax',
//				url: '/uber2/main/my-session!displaySessionInfo.action',
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
								fieldLabel: 'Description',
								name: 'description'
							}]
						}]
					}]
				}]
			}]
		});
		
//		editForm.load({
//			url: '/uber2/main/my-session!displaySessionInfo.action',
//			params: {
//				requestId:this.requestId,
//			},
//			reader: {
//				type: 'json',
//				rootProperty: 'data'
//			}
//		});
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