Ext.define('uber.view.feedback.Feedback', {
	extend: 'Ext.Panel',
	xtype: 'feedback',
	items: [{
		xtype: 'panel',
		items: [{
			xtype: 'fieldset',
			title: 'Feedback',
			items: [{
				xtype:'textareafield',
				name: 'feedback',
				label: 'Feedback'
			},{
				xtype: 'toolbar',
				items: [{
					xtype: 'button',
					text: 'Submit'
				}]
			}]
		}]
	}]
});