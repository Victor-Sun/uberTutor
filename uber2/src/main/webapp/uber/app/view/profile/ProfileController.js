Ext.define('uber.view.profile.ProfileController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.profile',

    signout: function () {

    	//Search for ubertab alias and destroy afterwards create login as new view
        Ext.getCmp("uberTab").destroy();
        Ext.Viewport.add(Ext.create('uber.view.login.Login'));
    },

    save: function () {
    	debugger;
    	var me = this;
    	var view = me.getView();
    	var model = Ext.create('uber.model.Profile', view.getValues());
    	//Create message box to alert user of saved changes
    	Ext.Toast('Changes Saved!');
    }
});