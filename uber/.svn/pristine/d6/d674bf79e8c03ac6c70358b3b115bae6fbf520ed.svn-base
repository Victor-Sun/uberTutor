/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.model.utilization.UtilizationNegotiationStrategyMixin', {

    uses : [
        'Gnt.model.utilization.DefaultUtilizationNegotiationStrategy'
    ],

    utilizationNegotiationStrategyClass : 'Gnt.model.utilization.DefaultUtilizationNegotiationStrategy',
    utilizationNegotiationStrategy : null,

    initUtilizationNegotiationStrategyMixin : function(config) {
        var me = this;

        if (config && config.hasOwnProperty('utilizationNegotiationStrategy')) {
            me.utilizationNegotiationStrategy = config.utilizationNegotiationStrategy;
            delete config.utilizationNegotiationStrategy;
        }
        else {
            me.utilizationNegotiationStrategy = Ext.create(me.utilizationNegotiationStrategyClass);
        }

        return config;
    },

    getUtilizationNegotiationStrategy : function() {
        return this.utilizationNegotiationStrategy;
    },

    setUtilizationNegotiationStrategy : function(strategy) {
        var me = this;

        if (me.utilizationNegotiationStrategy !== strategy && strategy) {
            me.utilizationNegotiationStrategy = strategy;
        }
    }
});
