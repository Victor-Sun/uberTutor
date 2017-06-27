Ext.define('uber.model.test.News', {
    extend: 'uber.model.Base',

    fields: [
        'type',
//        { name: 'date', type: 'date', dateFormat: 'Y-m-d H:i:s' },
        'date',
        'time',
        'author',
        'image',
        'title',
        'paragraph'
    ]
});
