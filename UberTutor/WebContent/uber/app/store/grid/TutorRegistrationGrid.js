Ext.define('uber.store.grid.TutorRegistrationGrid', {
    extend: 'Ext.data.Store',
    alias: 'store.tutorRegistrationGrid',
    model: 'uber.model.grid.TutorRegistrationGrid',
    remoteFilter: true,
    proxy: {
        type: 'ajax',
        url: '/UberTutor/main/tutor-subject-register!displayUserSubjects.action',
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
});
