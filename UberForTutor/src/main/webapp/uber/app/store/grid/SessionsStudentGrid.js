Ext.define('uber.store.grid.SessionsStudentGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.sessionsStudentGrid',
    model: 'uber.model.grid.SessionsStudentGrid',
    proxy: {
        type: 'ajax',
        url: '/UberForTutor/main/my-session!displayUserSessions.action',
        reader: {
            type: 'json',
            rootProperty: 'data',
            totalProperty: 'total'
        }
    }
});