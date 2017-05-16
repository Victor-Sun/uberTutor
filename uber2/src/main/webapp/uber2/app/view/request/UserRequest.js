Ext.define('uber.view.request.UserRequest', {
	extend: 'Ext.Panel',
	xtype: 'userrequest',
	items: [{
        xtype: 'formpanel',
        items: [{
            xtype: 'fieldset',
            title: 'Request name here',
            items: [{
                xtype: 'textareafield',
                name: 'detail',
                label: 'Details'
            },{
                xtype: 'toolbar',
                items: [{
                    xtype: 'button',
                    text: 'Send Request'
                },{
                    xtype: 'button',
                    text: 'User response example',
                    handler: function() {
                        Ext.Msg.confirm("Notice", "A tutor has accepted your request", Ext.emptyFn);
                    }
                }]
            }]
        }]
    }]
});