Ext.define('uber.store.grid.SearchGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.searchGrid',
    autoLoad: true,
    model: 'uber.model.grid.SearchGrid',
    pageSize: 5,
    proxy: {
        type: 'ajax',
        url: '/UberTutor/main/search!displayRequests.action',
        reader: {
            type: 'json',
            rootProperty: 'data',
            totalProperty: 'total'
        }
    },
    sorters: [{
        property: 'createDate',
        direction: 'DESC'
    }],
});