Ext.define('uber.view.common.ContainerWidget', {
    extend: 'Ext.panel.Panel',
    xtype: 'containerWidget',
    
    layout: {
    	type:'vbox',
    	align: 'stretch'
    },
    cls: 'shadow',
    margin: 10,
    height: 235,
//    defaults:{
//    	xtype:'fieldset',
//    	layout:'fit',
//    	items:[{
//    		
//    	}]
//    },
    initComponent: function(){
        var me = this;

        Ext.apply(me, {
        });

        me.callParent(arguments);
    }
});
