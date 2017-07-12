Ext.define('uber.store.grid.OpenRequests',{
	extend: 'Ext.data.Store',
    alias: 'store.openRequests',
    autoLoad: true,
//    model: 'uber.model.grid.SessionsTutorGrid',
    proxy: {
        type: 'ajax',
        url: '/UberTutor/main/main-page!displayCurrentTutorRequests.action',
        reader: {
            type: 'json',
            rootProperty: 'data',
            totalProperty: 'total'
        }
    }
});