//Validation for Sign In
Ext.define('uber.model.EditWindow',{
	extend: 'Ext.data.Model',
	alias: 'model.editWindow',
	
	fields: [
		{ name: 'description', type: 'string' },
	],
	validators: {
		description: { type: 'presence', message: 'Description required, please fill in description' },
	}
})