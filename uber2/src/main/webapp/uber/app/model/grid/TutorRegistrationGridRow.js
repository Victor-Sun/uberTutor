Ext.define('uber.model.grid.TutorRegistrationGridRow', {
    extend: 'uber.model.Base',
    alias: 'model.gridRow',
    
    fields: [
        // the 'name' below matches the tag name to read
        {name: 'categories'},
        {name: 'subject'}
    ]
});