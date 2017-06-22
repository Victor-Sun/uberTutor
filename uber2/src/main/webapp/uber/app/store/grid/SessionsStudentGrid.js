Ext.define('uber.store.grid.SessionsStudentGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.sessionsStudentGrid',
//    TODO: fields for grid
    model: 'uber.model.grid.SessionsStudentGrid',
    proxy: {
        type: 'ajax',
        url: '/uber2/main/my-session!displayUserSessions.action',
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
});