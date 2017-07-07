Ext.define('uber.store.grid.SearchResultGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.searchResultGrid',
    autoLoad: true,
//    model: 'uber.model.grid.SessionsStudentGrid',
    proxy: {
        type: 'ajax',
        url: '/UberTutor/main/Search!displayRequests.action',
        reader: {
            type: 'json',
            rootProperty: 'data',
            totalProperty: 'total'
        }
    }
});