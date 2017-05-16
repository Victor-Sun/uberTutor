Ext.define('uber.view.verification.Verification', {
	extend: 'Ext.Panel',
	xtype: 'verification',
	controller: 'verification',

	requires: [
		'uber.view.verification.VerificationController'
	],
	items: [{
		xtype: 'fieldset',
		margin: 20,
		items: [{
			xtype: 'panel',
			margin: 15,
			layout: {
				type: 'hbox',
				align: 'stretch'
			},
			items: [{
				xtype: 'component',
				flex: 1
			},{
				xtype: 'component',
				html: '<p>An email has been sent to'+ ' <b> ' + 'EMAIL@DOMAIN.COM' + ' </b> ' + 'with a verification code. Please input the code in the field below <br> *<b>note</b>* <b>verification code is 123456</b></p>'
			},{
				xtype: 'component',
				flex: 1
			}]
		},{
			xtype: 'fieldset',
			items: [{
				xtype: 'textfield',
				name: 'verifycode',
//				value: '123456'
				placeHolder: '000000'

			}]
		},{
			xtype: 'container',
			items: [{
				xtype: 'button',
				text: 'Verify',
				handler: 'submit'
			}]
		}]
	}]
});