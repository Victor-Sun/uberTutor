Ext.define('uber.store.grid.SessionsStudentGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.sessionsStudentGrid',
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