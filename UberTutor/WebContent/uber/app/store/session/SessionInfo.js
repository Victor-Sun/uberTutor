Ext.define('uber.store.session.SessionInfo',{
	extend:'Ext.data.Store',
	alias: 'store.sessionInfo',
	storeId: 'sessionInfo',
	model: 'uber.model.session.SessionInfo',
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