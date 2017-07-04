Ext.define('uber.view.tutor.TutorRegistrationController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.tutorRegistration',
    
    refresh: function() {
    	var me = this;
    },
    
    submit: function(btn) {
		var me = this;
		var grid = Ext.ComponentQuery.query('#tutorRegistrationGrid')[0];
		var gridStore = grid.getStore();
//		var form = me.view.down('form')
		var form = Ext.ComponentQuery.query('#subjectForm')[0];
		var getForm = form.getForm();
		Ext.getBody().mask('Loading...Please Wait...')
		getForm.submit({
			clientValidation: true,
			url:'/UberForTutor/main/tutor-subject-register!save.action',
			params: {
            	model: Ext.encode(getForm.getFieldValues()),
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
    
    onEditClick: function(grid, rowIndex, colIndex, item, e , record) {
    	var editForm = Ext.create('Ext.form.Panel',{
    		itemId: 'editForm',
			layout: {
	            type: 'vbox',
	            align: 'stretch'
	        },
			items: [{
				xtype: 'fieldcontainer',
				layout: {
					type: 'hbox',
					align: 'stretch'
				},
				defaults: {
					labelAlign: 'top',
					readOnly: true,
//					disabled: true,
//					disabledCls: "disabledCls"
				},
				items: [{
					xtype: 'textfield',
					margin: '5 15 0 15',
					name: 'category',
					itemId: 'category',
					fieldLabel: 'Category',
				},{
					xtype: 'textfield',
					margin: '5 15 0 5',
					name: 'subject',
					itemId: 'subject',
					fieldLabel: 'Subject',
				}]
			},{
				xtype: 'textarea',
				margin: 15,
				flex: 1,
				labelAlign: 'top',
				itemId: 'description',
				fieldLabel: 'Description',
				name: 'description'
			}]
		});
		var win = Ext.create('Ext.window.Window', {
			itemId: 'editWindow',
			title: 'Edit Window',
				autoShow: true, 
				width: 400,
		        height: 300,
				displayId: record.data.ID,
				layout: 'fit',
				items: [editForm],
				listeners: {
					show: function() {
						var me = this;
						editForm.down('#description').setValue(record.data.DESCRIPTION);
						editForm.down('#category').setValue(record.data.CATEGORY_TITLE);
						editForm.down('#subject').setValue(record.data.SUBJECT_TITLE);
			        }
				},
				dockedItems: [{
					xtype: 'toolbar',
					dock: 'bottom',
					items: ['->',{
						xtype: 'button',
						text: 'Submit',
						handler: function () {
							var editForm = Ext.ComponentQuery.query('#editForm')[0];
							var description = editForm.down('#description').getValue();
							editForm.submit({
							url: '/UberForTutor/main/tutor-subject-register!editSubject.action',
			    			params: {
			    				userSubjectId:record.data.SUBJECT_ID,
			    				description:description
			    			},
			    			success: function () {
			    				var win = Ext.ComponentQuery.query('#editWindow')[0];
			    				grid.getStore().reload();
									win.close();
			    			},
			    			failure: function () {
			    				Ext.Msg.alert("Error", 'An error has occured', Ext.emptyFn);
			    			}
			    		});
						}
					},{
						xtype: 'button',
						text: 'Cancel',
						handler: function () {
							var win = Ext.ComponentQuery.query('#editWindow')[0];
							win.close();
						}
					}]
				}],
				
		});
	},
    
    onRemoveClick: function (grid, rowIndex) {
    	var me = this;
    	var record = grid.getStore().getAt(rowIndex);
    	me.view.mask('Please Wait...')
    	Ext.Ajax.request({
    		url:'/UberForTutor/main/tutor-subject-register!removeSubject.action',
    		params: {
    			userSubjectId:record.data.ID
    		},
    		score: me,
    		success: function() {
    			me.view.unmask();
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