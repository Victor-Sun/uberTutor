Ext.define('uber.store.grid.SessionsStudentGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.sessionsStudentGrid',
    model: 'uber.model.grid.SessionsStudentGrid',
    pageSize: 10,
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