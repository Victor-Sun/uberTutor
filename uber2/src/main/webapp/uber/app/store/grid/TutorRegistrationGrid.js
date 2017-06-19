Ext.define('uber.store.grid.TutorRegistrationGrid', {
    extend: 'Ext.data.Store',
    alias: 'store.tutorRegistrationGrid',
//    TODO: fields for grid
    model: 'uber.model.grid.TutorRegistrationGrid',
    remoteFilter: true,
    proxy: {
        type: 'ajax',
        url: '/uber2/main/tutor-subject-register!displayUserSubjects.action',
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
});
