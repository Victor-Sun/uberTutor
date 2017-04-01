/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.constraint.Base', {

    mixins : ['Gnt.mixin.Localizable'],

    /**
     * @cfg {Object} l10n
     * An object, purposed for the class localization. Contains the following keys/values:
     *
     *      - "name" : "A constraint",
     *      - "Remove the constraint" : "Remove the constraint",
     *      - "Cancel the change and do nothing" : "Cancel the change and do nothing"
     */

    isSatisfied : function (task, date, precision) {
        throw "Abstract method";
    },


    getResolution : function (callback, task, date) {
        var me = this,
            called = false;

        date = date || task.getConstraintDate();

        var next = function () {
            if (!called) {
                called  = true;
                callback.apply(this, arguments);
            }
        };

        return {
            title       : me.L("name"),
            task        : task,
            date        : date,

            resolutions : this.getResolutionOptions(next, task, date),

            getCancelActionOption : function () {
                return this.resolutions[ 0 ];
            },

            cancelAction : function () {
                return this.getCancelActionOption().resolve();
            },

            proceedAction : function () {
                next();
            },

            getResolution : function (id) {
                return Ext.Array.findBy(this.resolutions, function(item) {
                    return item.id == id;
                });
            }
        };
    },


    getResolutionOptions : function (callback, task, date, precision) {
        date = date || task.getConstraintDate();

        var me          = this;

        var resolutions = [{
            id          : 'cancel',
            title       : me.L("Cancel the change and do nothing"),
            resolve     : function () {
                callback(true);
            }
        }];

        me.hasThisConstraintApplied(task) && resolutions.push({
            id      : 'remove-constraint',
            title   : me.L("Remove the constraint"),
            resolve : function () {
                task.setConstraintType('');
                callback();
            }
        });

        return resolutions;
    },


    hasThisConstraintApplied : function (task) {
        return task.getConstraintClass() === this;
    },


    getInitialConstraintDate : function(task) {
        return task.getConstraintDate();
    },


    getDisplayableConstraintDateForFormat : function(date, format, task) {
        return date;
    },


    adjustConstraintDateFromDisplayableWithFormat : function(date, format, task) {
        return date;
    },


    shiftToNearestValidConstraintDate : function(date, format, task) {
        return date;
    },


    statics : {
        /**
         * Returns constraint instance by its type, if type is null or empty string returns null
         *
         * @param {String} type Constraint type to return instance for.
         * @return {Gnt.constraint.Base|null} Constraint class singleton
         */
        getConstraintClass : function (type) {
            var result = !Ext.isEmpty(type) && Ext.ClassManager.getByAlias('gntconstraint.' + type);
            // <debug>
            // Postcondition: constraint class must exist
            Ext.isEmpty(type) || result ||
                Ext.Error.raise("Can't get constraint class, unrecognized constraint type: " + type);
            // </debug>
            return result || null;
        }
    }
});
