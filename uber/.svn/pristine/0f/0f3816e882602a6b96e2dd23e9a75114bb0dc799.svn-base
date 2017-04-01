/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.data.ResourceUtilizationEventStore', {
    extend             : 'Sch.data.EventStore',
    model              : 'Gnt.model.UtilizationEvent',

    storeId            : null,

    /**
     * Returns this store (utilization information) model which corresponding to particular original model a resource or
     * an assignment.
     *
     * @param {Gnt.model.Resource|Gnt.model.Assignment} originalModel
     * @return {Gnt.model.UtilizationResource}
     */
    getModelByOriginal : function (originalModel) {
        var me = this;
        return me.getModelById(me.model.getSurrogateIdFor(originalModel));
    }
});
