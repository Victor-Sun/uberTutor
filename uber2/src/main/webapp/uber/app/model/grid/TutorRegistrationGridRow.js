Ext.define('uber.model.grid.TutorRegistrationGrid', {
    extend: 'uber.model.Base',
    alias: 'model.tutorregistrationgrid',
    
    fields: [
        // the 'name' below matches the tag name to read
        {name: 'categories'},
        {name: 'subject'}
    ]
});