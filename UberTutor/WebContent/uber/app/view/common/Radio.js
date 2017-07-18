Ext.define('uber.view.common.Radio',{
	extend: 'Ext.form.RadioGroup',
	xtype: 'pageradio',
	reference: 'pageradio',
	itemId: 'pageradio',
    // Arrange radio buttons into two columns, distributed vertically
    columns: 2,
    items: [
        { boxLabel: 'New User', name: 'ab', inputValue: '1'},
        { boxLabel: 'Existing User', name: 'ab', inputValue: '2'}
    ],
    listeners: {
        change: function (field, newValue, oldValue) {
        	var text = this.up('form').down('component');
        	var button = this.up('form').down('button');
        	var email = this.up('form').getForm().findField('email');
        	var password2 = this.up('form').getForm().findField('password2');
            switch (parseInt(newValue['ab'])) {
                case 1:
                	text.update('<h2>Sign Up</h2>');
                	button.setText('Sign Up');
                	email.show();
                	email.allowBlank = false;
                	email.disabled = false;
                	password2.show();
                	password2.allowBlank = false;
                	password2.disabled = false;
                    break;
                case 2:
                	text.update('<h2>Sign In</h2>');
                	button.setText('Sign In');
                	email.setHidden('true');
                	email.allowBlank = true;
                	email.disabled = true;
                	password2.setHidden('true');
                	password2.allowBlank = true;
                	password2.disabled = true;
                    break;
            }
        }
    }
});