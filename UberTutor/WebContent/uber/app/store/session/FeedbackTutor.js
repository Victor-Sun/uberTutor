Ext.define('uber.store.session.FeedbackTutor',{
	extend:'Ext.data.Store',
	alias: 'store.feedbackTutor',
	storeId: 'feedbackTutor',
//	model: 'uber.model.Feedback',
	
	data: [
		{
			'RATING': '4',
			'FEEDBACK': 'THIS IS A TEST'
		}
	]
//	proxy: {
//		type: 'ajax',
//		url: '/UberTutor/main/my-session!displayRequestInfo.action',
//		params: ''
//		reader: {
//			type: 'json',
//			rootProperty: 'users'
//		}
//	},
});