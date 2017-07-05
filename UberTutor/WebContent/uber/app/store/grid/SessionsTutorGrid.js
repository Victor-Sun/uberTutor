Ext.define('uber.store.grid.SessionsTutorGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.sessionsTutorGrid',
    model: 'uber.model.grid.SessionsTutorGrid',
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