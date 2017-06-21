Ext.define('uber.view.tutor.TutorRegistrationController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.tutorRegistration',
    
    refresh: function() {
    	var me = this;
    },
    
    submit: function(btn) {
    	debugger;
		var me = this;
		var grid = Ext.ComponentQuery.query('#tutorRegistrationGrid')[0];
		var gridStore = grid.getStore();
//		var form = me.view.down('form')
		var form = Ext.ComponentQuery.query('#subjectForm')[0];
		var getForm = form.getForm();
		Ext.getBody().mask('Loading...Please Wait...')
		getForm.submit({
			clientValidation: true,
			url:'/uber2/main/tutor-subject-register!save.action',
			params: {
            	model: Ext.encode(getForm.getFieldValues())
            },
            scope: me,
            success: function(form, action) {
            	Ext.getBody().unmask();
            	this.getView().close();
            	grid.getStore().reload();
            },
            failure: function (form, action) {
            	Ext.getBody().unmask();
                var result = uber.util.Util.decodeJSON(action.response.responseText);
                Ext.Msg.alert('Error', result.data, Ext.emptyFn);
        	},

		});
    },
    
    cancel: function() {
    	this.view.close();
    },
    
    onEditClick: function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
    	debugger;
    	var record = grid.getStore().getAt(rowIndex.internalId);
//    	console.log(rowIndex.data.ID);
    	var window = Ext.create('uber.view.tutor.EditWindow',{
    		displayId: rowIndex.data.ID,
    	});
    	window.show();
    },
    
    onRemoveClick: function (grid, rowIndex) {
    	var me = this;
    	var record = grid.getStore().getAt(rowIndex);
    	me.view.mask('Please Wait...')
    	Ext.Ajax.request({
    		url:'/uber2/main/tutor-subject-register!removeSubject.action',
    		params: {
    			userSubjectId:record.data.ID
    		},
    		score: me,
    		success: function() {
    			me.view.unmask();
    			Ext.Msg.alert('Success', "Subject was successfully removed!", Ext.emptyFn);
    			grid.getStore().reload();
    		},
    		failure: function(response, opts) {
    			me.view.unmask();
    			uber.util.Util.handlerRequestFailure(response);
    		}
    	});
    },
    
    
    //Subject Grid 
    renderTitleColumn: function (value, metaData, record) {
        var view = this.getView(),
            plugin = view.getPlugin('rowexpander'),
            tpl = view.titleTpl;

        if (!tpl.isTemplate) {
            view.titleTpl = tpl = new Ext.XTemplate(tpl);
        }

        var data = Ext.Object.chain(record.data);

        data.expanded = plugin.recordsExpanded[record.internalId] ? ' style="display: none"' : '';

        return tpl.apply(data);
    },

    updateActiveState: function (activeState) {
        var viewModel = this.getViewModel();
        var filterButton = this.lookupReference('filterButton');

        filterButton.setActiveItem(activeState);
        viewModel.set('category', activeState);

        this.fireEvent('changeroute', this, 'news/' + activeState);
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