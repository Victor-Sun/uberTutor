Ext.define('uber.store.grid.CurrentSessionsGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.currentSessions',
    autoLoad: true,
    model: 'uber.model.grid.SessionsStudentGrid',
    proxy: {
        type: 'ajax',
        url: '/uber2/main/my-session!displayUserSessions.action',
        reader: {
            type: 'json',
            rootProperty: 'data',
            totalProperty: 'total'
        }
    }
});