Ext.define('uber.view.chat.Chat',{
	extend: 'Ext.tab.Panel',
	itemId: 'chatWindow',
	requires: [
		'uber.view.chat.ChatController'
	],
	controller: 'chat',
	shadow: true,
	tabBar: {
		layout: {
			pack: 'center'
		}
	},
	activeTab: 1,
	defaults: {
		scrollable: true
	},
	// Make dynamic so user can get multiple tabs 
	items:[
		
	]
});