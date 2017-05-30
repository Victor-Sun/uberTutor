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
        	var field = this.up('form').getForm().findField('email');
        	//var value = Ext.ComponentQuery.query('radiofield[name=cstgrp]');
            //console.log(newValue['cstgrp']);

            switch (parseInt(newValue['ab'])) {
                case 1:
//                	debugger;
                	text.update('<h2>Sign Up</h2>');
                	button.setText('Sign Up');
//                	field.setHidden('false');
                	field.show();
                    break;
                case 2:
                	text.update('<h2>Sign In</h2>');
                	button.setText('Sign In');
                	field.setHidden('true');
                    break;
            }
        }
    }
});