Ext.define('uber.store.session.SessionInfo',{
	extend:'Ext.data.Store',
	alias: 'store.sessionInfo',
	storeId: 'sessionInfo',
	model: 'uber.model.session.SessionInfo',
//	proxy: {
//		type: 'ajax',
//		url: '/uber2/main/my-session!displaySessioninfo.action',
//		params: ''
//		reader: {
//			type: 'json',
//			rootProperty: 'users'
//		}
//	},
});