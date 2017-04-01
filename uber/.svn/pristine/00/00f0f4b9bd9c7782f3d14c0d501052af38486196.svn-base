/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * This mixin eliminates differences between flat/tree store in get by [internal] id functionality and it should be
 * mixed into data model stores.
 *
 * It adds two methods {@link #getModelById getModelById()} and {@link #getModelByInternalId getModelByInternalId()}
 * which should be used everywhere in the code instead of native getById() / getByInternalId() methods.
 *
 * @private
 */
Ext.define('Sch.data.mixin.UniversalModelGetter', {

    getModelById : function(id) {
        var me = this;
        return me.getNodeById ? me.getNodeById(id) : me.getById(id);
    },

    getModelByInternalId : function(id) {
        var me = this;
        return me.byInternalIdMap ? me.byInternalIdMap[id] : me.getByInternalId(id);
    }

});
