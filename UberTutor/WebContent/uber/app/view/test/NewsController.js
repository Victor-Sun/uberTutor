Ext.define('uber.view.test.NewsController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.news',

    init: function (view) {
        // We provide the updater for the activeState config of our View.
        view.updateActiveState = this.updateActiveState.bind(this);
    },

//    onNewsClick: function(btn, menuitem) {
//        var view = this.getView();
//        view.setActiveState(menuitem.subject);
//    },

    onNewsClick: function(btn, menuitem) {
        var view = this.getView();
        view.setActiveState(menuitem.subject);
        var grid = Ext.ComponentQuery.query('#news')[0];
        var store = grid.getStore();
        if ( menuitem.subjects == "all") {
        	store.load();
        } else {
        	store.load({
            	proxy: {
            		params: {
            			subject: menuitem.subject
            		}
            	}
            });
        }
    },
    
    renderTitleColumn: function (value, metaData, record) {
    	debugger;
        var view = this.getView(),
            plugin = view.getPlugin('rowexpander'),
            tpl = view.titleTpl;

        if (!tpl.isTemplate) {
            view.titleTpl = tpl = new Ext.XTemplate(tpl);
        }

//        var data = Ext.Object.chain(record.data);
        var data = record.data;

        data.expanded = plugin.recordsExpanded[record.internalId] ? ' style="display: none"' : '';
//        data.expanded = data.subjectDescription;

        return tpl.apply(data);
    },

    updateActiveState: function (activeState) {
        var viewModel = this.getViewModel();
        var filterButton = this.lookupReference('filterButton');

        filterButton.setActiveItem(activeState);
        viewModel.set('category', activeState);

        this.fireEvent('changeroute', this, 'news/' + activeState);
    },

    //-------------------------------------------------------------------------
    // RowExpander management

    onCompanyClick: function(dv, record, item, index, e) {
//    	debugger;
        if (e.getTarget('.news-toggle')) {
            var grid = this.getView(),
                plugin = grid.getPlugin('rowexpander');

            plugin.toggleRow(index, record);
        }
    },

    onCompanyExpandBody: function (rowNode) {   // , record, expandRow, eOpts
//    	debugger;
        Ext.fly(rowNode).addCls('x-grid-row-expanded');
        Ext.fly(rowNode).down('.news-paragraph-simple').enableDisplayMode().hide();
        Ext.fly(rowNode).down('.expand').enableDisplayMode().hide();
    },

    onCompanyCollapseBody: function (rowNode) {  //, record, expandRow, eOpts
//    	debugger;
        Ext.fly(rowNode).removeCls('x-grid-row-expanded');
        Ext.fly(rowNode).down('.news-paragraph-simple').enableDisplayMode().show();
        Ext.fly(rowNode).down('.expand').enableDisplayMode().show();
    }
});
