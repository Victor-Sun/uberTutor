Ext.define('uber.view.tutor.TutorRegistrationController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.tutorRegistration',
    
    refresh: function() {
    	var me = this;
    },
    
    cancel: function() {
    	this.view.close();
    },
    
    showToast: function(s, title) {
        Ext.toast({
            html: s,
            closable: false,
            align: 't',
            slideInDuration: 400,
            minWidth: 400
        });
    },
    
    showResult: function(btn, text) {
        this.showToast(Ext.String.format('You clicked the {0} button', btn));
    },
    
    onToggleClick: function (grid, rowIndex, colIndex, item, e, record) {
    	Ext.MessageBox.confirm('Confirm', 'Are you sure?', this.showResult, this);
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
					name: 'categoryTitle',
					itemId: 'category',
					fieldLabel: 'Category',
				},{
					xtype: 'textfield',
					margin: '5 15 0 5',
					name: 'subjectTitle',
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
				name: 'description',
				allowBlank: false
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
						editForm.down('#description').setValue(record.data.description);
						editForm.down('#category').setValue(record.data.categoryTitle);
						editForm.down('#subject').setValue(record.data.subjectTitle);
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
							var model = Ext.create('uber.model.EditWindow', editForm.getValues());
							var validation = model.getValidation(); 
							if (model.isValid()) {
								editForm.submit({
									url: '/UberTutor/main/tutor-subject-register!editSubject.action',
					    			params: {
					    				userSubjectId:record.data.userSubjectId,
					    				description:description
					    			},
					    			success: function () {
					    				var win = Ext.ComponentQuery.query('#editWindow')[0];
					    				win.close();
					    				grid.getStore().load();
											
					    			},
					    			failure: function(form, action) {
					    				Ext.getBody().unmask();
		//			    				var result = uber.util.Util.decodeJSON(action.response.responseText);
					    				Ext.Msg.alert('Error', "An error has occured, please try again", Ext.emptyFn);
		//			    				console.log(result.errors.reason);
					    			}
					    		});
							} else {
								Ext.getBody().unmask();
								var msg = "<ul class='error'>";
					            for (var i in validation.data) {
					                if(validation.data[i] !== true){
					                    msg += "<li>" + " " + validation.data[i] + "</li>" ;
					                }
					            }
					            msg += "</ul>";
					    		Ext.Msg.alert("Validation failed", msg, Ext.emptyFn);
							}
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
	
//	showResult: function(btn, text) {
//        debugger;
//    },
    
    onRemoveClick: function (grid, rowIndex) {
    	var me = this;
    	var record = grid.getStore().getAt(rowIndex);
    	Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?', function(btn) {
    		if (btn == "yes") {
    	    	Ext.getBody().mask('Please Wait...')
    	    	Ext.Ajax.request({
    	    		url:'/UberTutor/main/tutor-subject-register!removeSubject.action',
    	    		params: {
    	    			userSubjectId:record.data.userSubjectId,
    	    		},
    	    		scope: me,
    	    		success: function() {
    	    			Ext.getBody().unmask();
    	    			grid.getStore().reload();
    	    		},
    	    		failure: function(response, opts) {
    	    			Ext.getBody().unmask();
//    	    			uber.util.Util.handlerRequestFailure(response);
    	    			Ext.Msg.alert('Error', "An error has occured, please try again", Ext.emptyFn);
    	    		}
    	    	});
    		}
    	}, this);
    },
//    //Subject Grid 
//    renderTitleColumn: function (value, metaData, record) {
//        var view = this.getView(),
//            plugin = view.getPlugin('rowexpander'),
//            tpl = view.titleTpl;
//
//        if (!tpl.isTemplate) {
//            view.titleTpl = tpl = new Ext.XTemplate(tpl);
//        }
//
//        var data = Ext.Object.chain(record.data);
//
//        data.expanded = plugin.recordsExpanded[record.internalId] ? ' style="display: none"' : '';
//
//        return tpl.apply(data);
//    },
//
//    updateActiveState: function (activeState) {
//        var viewModel = this.getViewModel();
//        var filterButton = this.lookupReference('filterButton');
//
//        filterButton.setActiveItem(activeState);
//        viewModel.set('category', activeState);
//
//        this.fireEvent('changeroute', this, 'news/' + activeState);
//    },
//    
//    onCompanyClick: function(dv, record, item, index, e) {
//        if (e.getTarget('.news-toggle')) {
//            var grid = this.getView(),
//                plugin = grid.getPlugin('rowexpander');
//
//            plugin.toggleRow(index, record);
//        }
//    },

//    onCompanyExpandBody: function (rowNode) {   // , record, expandRow, eOpts
//        Ext.fly(rowNode).addCls('x-grid-row-expanded');
//        Ext.fly(rowNode).down('.news-paragraph-simple').enableDisplayMode().hide();
//        Ext.fly(rowNode).down('.expand').enableDisplayMode().hide();
//    },
//
//    onCompanyCollapseBody: function (rowNode) {  //, record, expandRow, eOpts
//        Ext.fly(rowNode).removeCls('x-grid-row-expanded');
//        Ext.fly(rowNode).down('.news-paragraph-simple').enableDisplayMode().show();
//        Ext.fly(rowNode).down('.expand').enableDisplayMode().show();
//    }
});