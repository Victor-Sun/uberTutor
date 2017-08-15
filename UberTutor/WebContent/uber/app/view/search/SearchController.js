Ext.define('uber.view.profile.SearchController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.search',
    
    searchresults: function () {
    	var me = this;
    	var subject = Ext.ComponentQuery.query('#subject')[0];
//    	var main = me.view.up('app-main');
    	var mainCard = Ext.ComponentQuery.query('#mainCardPanel')[0];
		var remove = mainCard.removeAll();
		var card2 = mainCard.add(Ext.create('uber.view.search.SearchResults'));
		var searchResultGrid = Ext.ComponentQuery.query('#searchresultsgrid')[0];
		
		searchResultGrid.getStore().load({
			url: '/UberTutor/main/search!displayRequests.action',
			params:{
				start: 0,
    	        limit: 5
//				subjectId: subject.value,
//				userId: 
			},
			failure: function(form, action) {
				Ext.getBody().unmask();
//				var result = uber.util.Util.decodeJSON(action.response.responseText);
				Ext.Msg.alert('Error', "An error has occured, please try again", Ext.emptyFn);
//				console.log(result.errors.reason);
			},
		});
    },
    
    onCelldblclick: function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
    	var me = this;
//    	var rec = grid.getStore().getAt(rowIndex);
//    	console.log(rec.data);
    	var tabpanel = me.view.down('tabpanel');
    	var profile = Ext.create({
    		xtype: 'tutorprofile',
    		title: 'Tutor Profile'
    	});
    	tabpanel.add(profile);
    	tabpanel.setActiveTab(profile);
    	
    },
    
    renderTitleColumn: function (value, metaData, record) {
        var view = this.getView(),
            plugin = view.getPlugin('rowexpander'),
            tpl = view.titleTpl;

        if (!tpl.isTemplate) {
            view.titleTpl = tpl = new Ext.XTemplate(tpl);
        }

        var data = Ext.Object.chain(record.data);

//        data.expanded = plugin.recordsExpanded[record.internalId] ? ' style="display: none"' : '';

        return tpl.apply(data);
    },
    
    onCompanyClick: function(dv, record, item, index, e) {
        if (e.getTarget('.news-toggle')) {
            var grid = this.getView(),
                plugin = grid.getPlugin('rowexpander');

            plugin.toggleRow(index, record);
        }
    },

    onCompanyExpandBody: function (rowNode) {   // , record, expandRow, eOpts
        Ext.fly(rowNode).addCls('x-grid-row-expanded');
        Ext.fly(rowNode).down('.news-paragraph-simple').enableDisplayMode().hide();
        Ext.fly(rowNode).down('.expand').enableDisplayMode().hide();
    },

    onCompanyCollapseBody: function (rowNode) {  //, record, expandRow, eOpts
        Ext.fly(rowNode).removeCls('x-grid-row-expanded');
        Ext.fly(rowNode).down('.news-paragraph-simple').enableDisplayMode().show();
        Ext.fly(rowNode).down('.expand').enableDisplayMode().show();
    }
})