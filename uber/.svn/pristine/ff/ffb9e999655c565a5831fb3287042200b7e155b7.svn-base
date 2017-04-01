/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * This mixin adds transaction alike functionality into a model and works in pair with {@link Gnt.data.mixin.ProjectableStore}.
 * If you mix-in this class into a model, make sure you also mix-in {@link Gnt.model.mixin.ProjectableStore} into the store(s)
 * which will work with this model class.
 *
 * Make sure you call the {@link #initProjection} method in your model class constructor, otherwise mixing in this mixin
 * won't have any effect.
 */
Ext.define('Gnt.model.mixin.ProjectableModel', function() {

    var overridables = {};

    /**
     * Initializes record's projectable mechanics.
     * @protected
     * @method initProjectable
     * @member Gnt.model.mixin.ProjectableModel
     */
    function initProjectable() {
        Ext.override(this, overridables);
    }


    // Private
    function getUnprojected(fieldName) {
        return this.data[fieldName];
    }


    /**
     * Checks whether the record is projected, i.e. a projection has changed values for this record.
     *
     * @return {Boolean}
     */
    function isProjected() {
        var me         = this,
            store      = me.getTreeStore && me.getTreeStore() || me.store,
            projection = store && store.getProjection && store.getProjection();

        return projection && projection.hasOwnProperty(me.internalId) ? true : false;
    }


    overridables.get = function get(fieldName) {
        var me         = this,
            store      = me.getTreeStore && me.getTreeStore() || me.store,
            projection = store && store.getProjection && store.getProjection(),
            internalId = me.internalId,
            data, value;

        if (projection && projection.hasOwnProperty(internalId)) {
            data  = projection[ internalId ];
            if (fieldName in data) {
                value = data[ fieldName ];
            }
            else {
                value = me.callParent([ fieldName ]);
            }
        }
        else {
            value = me.callParent([ fieldName ]);
        }

        return value;
    };

    // TODO we should probably handle 'options' object as well
    overridables.set = function set(fieldName, value, options) {
        var me         = this,
            store      = me.getTreeStore && me.getTreeStore() || me.store,
            projection = store && store.getProjection && store.getProjection(),
            internalId = me.internalId,
            data, prop, result, currentValue,
            currentValueAdopted, valueAdopted;

        if (projection) {

            result = [];

            if (typeof arguments[0] === 'object') {
                for (prop in fieldName) {
                    if (fieldName.hasOwnProperty(prop)) {
                        value        = fieldName[ prop ];
                        currentValue = me.get(prop); //me.getUnprojected(prop);

                        valueAdopted        = (value !== undefined && value !== null) ? (value).valueOf() : value;
                        currentValueAdopted = (currentValue !== undefined && currentValue !== null) ? (currentValue).valueOf() : currentValue;

                        if (
                            ((valueAdopted === undefined || valueAdopted === null) && valueAdopted !== currentValueAdopted) ||
                            valueAdopted != currentValueAdopted
                        ) {
                            data = projection[ internalId ] = projection.hasOwnProperty( internalId ) && projection[ internalId ] || {};
                            data[ prop ] = value;
                            result.push(prop);
                        }
                    }
                }
            }
            // method called with a field name
            else {
                currentValue = me.get(fieldName); //me.getUnprojected(fieldName);

                valueAdopted        = (value !== undefined && value !== null) ? (value).valueOf() : value;
                currentValueAdopted = (currentValue !== undefined && currentValue !== null) ? (currentValue).valueOf() : currentValue;

                if (
                    ((valueAdopted === undefined || valueAdopted === null) && valueAdopted !== currentValueAdopted) ||
                    valueAdopted != currentValueAdopted
                ) {
                    data = projection[ internalId ] = projection.hasOwnProperty( internalId ) && projection[ internalId ] || {};
                    data[ fieldName ] = value;
                    result.push(fieldName);
                }
            }
        }
        else {
            result = me.callParent(arguments);
        }

        return result;
    };


    return {
        initProjectable : initProjectable,
        getUnprojected  : getUnprojected,
        isProjected     : isProjected
    };
});
