Ext.define('uber.store.grid.CurrentSessionsGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.currentSessions',
    autoLoad: true,
    model: 'uber.model.grid.SessionsStudentGrid',
    proxy: {
        type: 'ajax',
        url: '/UberTutor/main/my-session!displayUserRequests.action',
        reader: {
            type: 'json',
            rootProperty: 'data',
            totalProperty: 'total'
        }
    }
});