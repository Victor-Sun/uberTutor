Ext.define('uber.store.grid.TutorRegistrationGrid', {
    extend: 'Ext.data.Store',
    alias: 'store.tutorregistrationgride',

    fields: [
        'category', 'subject'
    ],

    proxy: {
        type: 'ajax',
//        url: 'uber2/main/',
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
});
