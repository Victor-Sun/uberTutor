Ext.define('uber.view.test.NewsModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.news',

    requires: [
        'uber.model.test.News'
    ],

    formulas: {
        typeFilter: function (get) {
            var category = get('category');
            return this.filters[category];
        }
    },

    filters: {
        all:   [ 'Algebra', 'Calculus' ],
        algebra: [ 'Algebra' ],
        calculus: [ 'Calculus' ]
    },

    stores: {
        news: {
            type: 'news',
            autoLoad: true,
            sorters: [
                { property: 'date', direction: 'DESC' }
            ],
            filters: {
                property: 'subject',
                operator: 'in',
                value: '{typeFilter}'
            }
        }
    }
});
