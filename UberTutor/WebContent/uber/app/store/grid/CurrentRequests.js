Ext.define('uber.store.grid.CurrentRequests',{
	extend: 'Ext.data.Store',
    alias: 'store.currentRequests',
    autoLoad: true,
//    model: 'uber.model.grid.SessionsTutorGrid',
    proxy: {
        type: 'ajax',
        url: '/UberTutor/main/my-session!displayTutorSessions.action',
        reader: {
            type: 'json',
            rootProperty: 'data',
            totalProperty: 'total'
        }
    }
});