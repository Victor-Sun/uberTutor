	Ext.define('uber.model.Feedback',{
	extend: 'Ext.data.Model',
	alias: 'model.feedback',
	fields: [
		{ name: 'RATING', type: 'string' },
		{ name: 'FEEDBACK', type: 'string' },
		
	],
	validators: {
		RATING: [
			{ type: 'presence', message:"Rating required, please select rating"},
		],
		FEEDBACK: [
			{ type: 'presence', message:"Feedback required, please fill in feedback"},
		],
			
	}
});