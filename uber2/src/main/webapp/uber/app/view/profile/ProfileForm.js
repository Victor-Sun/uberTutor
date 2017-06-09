Ext.define('uber.view.profile.ProfileForm',{
    extend: 'Ext.form.Panel',
    xtype: 'profileform',
    
    margin: 5,
    controller: 'profile',
    reference: 'profileForm',
    itemId: 'profileForm',
    layout: {
        type: 'vbox',
        align: 'stretchmax'
    },
    defaults: {
        labelAlign: 'top',
        width: 200
//        anchor: '100%'
    },
    initComponent(){
        var checkboxForm = Ext.create ('Ext.form.Panel',{
            reference: 'checkboxForm',
            itemId: 'checkboxForm',
            items: [{
                xtype: 'radiogroup',
                readOnly: false,
                fieldLabel: 'Is Tutor?',
                columns: 2,
//                name: 'IS_TUTOR',
//                inputValue: 'Y',
//              hideLabel: true,
//              scope: this,
                labelAlign: 'left',
                labelWidth: 75,
                id: 'isTutor',
                checkCount:0,
                items: [
                	{ boxLabel: 'Yes', name: 'IS_TUTOR', inputValue: 'Y' },
                    { boxLabel: 'No', name: 'IS_TUTOR', inputValue: 'N' },
                ],
                listeners: {
                    change: function (th , field, newValue, oldValue) {
                    	debugger;
                        var bio = Ext.getCmp('bio');
                        var subject = Ext.getCmp('subject');
                        var checkbox = this.getValue();
                        var formPanel = this.up('form');
                        
                        if(th.checkCount > 0){
                            formPanel.submit({
                                //submit form for user signup
                                url: '/uber2/main/profile!registerAsTutor.action',
                                method: 'POST',
                                params: {
                                    fullname: 'fullname'
                                },
                                success: function(response, opts) {
                                    // change to exception output
//                                	Ext.Msg.alert('Error', "Value Loaded/Updated", Ext.emptyFn);
                                },

                                failure: function(response, opts) {
                                    // similar to above
                                    var result = uber.util.Util.decodeJSON(response.responseText);
                                    Ext.Msg.alert('Error', result.data, Ext.emptyFn);
                                },
                            });
                        };
//                        switch (parseInt(checkbox)) {
//	                        case 'Y':
//	                        	bio.setVisible(true);
//	                            subject.setVisible(true);
//	                            break;
//	                        case 'N':
//	                        	 bio.setVisible(false);
//	                             subject.setVisible(false);
//	                            break;
//	                    };
                        
                        if (checkbox.IS_TUTOR == 'Y')            
                        {
                            bio.setVisible(true);
                            subject.setVisible(true);
                        } else if (checkbox.IS_TUTOR == 'N') 
        				{
                            bio.setVisible(false);
                            subject.setVisible(false);
                        };
                        th.checkCount = th.checkCount + 1;
                    },
                }
            }]
        });
        if (checkboxForm.isValid()) {
            checkboxForm.load({
                url:'/uber2/main/profile!display.action',
                method: 'GET',
                success: function () {
                    
                },
                failure: function () {
                    uber.util.Util.showToast("Error loading checkbox value");
                }
            })
        }
        this.items = [{
            xtype: 'textfield',
            name: 'FULLNAME',
            fieldLabel: 'Name',
            readOnly: true,
            itemId: 'fullname'
        },{
            xtype: 'textfield',
            name: 'EMAIL',
            fieldLabel: 'Email',
            readOnly: true,
            itemId: 'email'
        },{
            xtype: 'textfield',
            name: 'MOBILE',
            fieldLabel: 'Mobile',
            readOnly: true,
            itemId: 'mobile'
        },{
            xtype: 'textfield',
            name: 'NAME',
            fieldLabel: 'School',
            readOnly: true,
            itemId: 'school'
        },{
            xtype: 'textarea',
            name: 'BIO',
            maxLength: 1000,
            fieldLabel: 'Bio',
            readOnly: true,
            hidden: true,
            id: 'bio'
        },{
            xtype: 'button',
            name: 'addSubject',
            text: 'Add Subject',
            hidden: true,
            id: 'subject',
            handler: 'registration'
        },
        checkboxForm,
//        {
//          xtype: 'button',
//          name: 'buttonTest',
//          text: 'button test',
//          handler: function() {
//              var profileForm = this.up('form');
//              profileForm.reload();
//          }
//        }
        ];
        
         
        this.callParent();
    },
});