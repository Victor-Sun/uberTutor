Ext.define('uber.view.tutor.TutorRegistrationController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.tutorRegistration',
    
    refresh: function() {
    	var me = this;
    },
    
    submit: function(btn) {
		var me = this;
		var grid = Ext.getCmp('tutorRegistrationGrid').getStore();
		var form = me.view.down('form').getForm();
		me.view.mask('Loading...Please Wait...')
		form.submit({
			clientValidation: true,
			url:'/uber2/main/tutor-subject-register!save.action',
			params: {
            	model: Ext.encode(form.getFieldValues())
            },
            scope: me,
            success: function(form, action) {
            	me.view.unmask();
            	grid.reload();
                me.cancel();
            },
            failure: function (form, action) {
            	me.view.unmask();
                var result = uber.util.Util.decodeJSON(action.response.responseText);
                Ext.Msg.alert('Error', result.data, Ext.emptyFn);
        	},

		});
    },
    
    cancel: function() {
    	this.view.close();
    },

    onRemoveClick: function (grid, rowIndex) {
    	var me = this;
    	var record = grid.getStore().getAt(rowIndex);
    	me.view.mask('Please Wait...')
    	Ext.Ajax.request({
    		url:'/uber2/main/tutor-subject-register!removeSubject.action',
    		params: {
    			SUBJECT_ID:record.data.SUBJECT_ID
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
    	debugger;
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