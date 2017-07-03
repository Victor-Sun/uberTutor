Ext.define('uber.view.test.News', {
    extend: 'Ext.grid.Panel',
    xtype: 'news',
    itemId: 'news',
    cls: 'subject-grid',

    requires: [
        'Ext.grid.plugin.RowExpander'
    ],

    config: {
        activeState: null,
        defaultActiveState: 'all'
    },

    controller: 'news',

    viewModel: {
        type: 'news'
    },

    hideHeaders: true,

    bind: '{news}',
    
//    initComponents: function () {
//    	me.store.load()
//    	this.callParents(arguments);
//    };
    tbar: [{
        text: 'All Posts',
        xtype: 'cycle',
        reference: 'filterButton',
        showText: true,
        width: 150,
        textAlign: 'left',

        listeners: {
            change: 'onNewsClick'
        },

        menu: {
            id: 'news-menu',
            items: [{
                text: 'All Posts',
                subject: 'all',
                itemId: 'all',
                checked: true
            },{
            	text: 'Algebra',
            	subject: 'algebra',
            	itemId: 'algebra'
            },{
            	text: 'Calculus',
            	subject: 'calculus',
            	itemId: 'calculus'
            }]
        }
    }],

    columns: [{
        dataIndex: 'title',
        align: 'left',
        flex: 1,
        renderer: 'renderTitleColumn'
    }],

    viewConfig: {
        listeners: {
            itemclick: 'onCompanyClick',
//            expandbody: 'onCompanyExpandBody',
//            collapsebody: 'onCompanyCollapseBody'
        }
    },

    plugins: [{
        ptype: 'ux-rowexpander',
        pluginId: 'rowexpander'
    }],

    // This XTemplate is used by the controller to format the title column.
    titleTpl:
        '<div class="text-wrapper">' +
//            '<div class="news-icon {type}">&nbsp;</div>' +
            '<div class="news-data">' +
//                '<div class="news-picture"><img src="resources/icons/{image}"></div>' +
                '<div class="news-content">' +
                    '<div class="news-title">{requestTitle}</div>' +
                    '<div class="news-small">by <span class="news-author">{studentName}</span>' +
                    '<span class="x-fa fa-calendar"></span>{createDate}' +
                    '<span class="x-fa fa-book"></span>{subject}</div>' +
                    '<div class="news-paragraph news-paragraph-simple" {expanded}>{subjectDescription:ellipsis(130, true)}</div>' +
//                    '<div class="news-toggle expand" {expanded}><span>EXPAND</span>' +
//                    '<img src="resources/icons/expand-news.png"></div>' +
                '</div>' +
            '</div>' +
        '<div>',

    validStates: {
        all: 1,
//        news: 1,
//        forum: 1,
        algebra: 1,
        calculus: 1
    },

    isValidState: function (state) {
        return state in this.validStates;
    }
});
