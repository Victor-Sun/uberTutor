/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

A specialized field to enter percent values.
This class inherits from the standard Ext JS "number" field, so any usual `Ext.form.field.Number` configs can be used.

@class Gnt.field.Percent
@extends Ext.form.field.Number

*/
Ext.define('Gnt.field.Percent', {
    extend              : 'Ext.form.field.Number',

    alias               : 'widget.percentfield',

    mixins              : ['Gnt.mixin.Localizable'],

    alternateClassName  : 'Gnt.widget.PercentField',

    /**
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

     - invalidText : 'Invalid value'
     */

    disableKeyFilter    : false,

    minValue            : 0,
    maxValue            : 100,
    allowExponential    : false,

    baseChars           : '0123456789%',

    constructor : function () {
        this.callParent(arguments);

        this.invalidText = this.L('invalidText');
    },

    valueToRaw: function (value) {
        if (Ext.isNumber(value)) {
            return parseFloat(Ext.Number.toFixed(value, this.decimalPrecision)) + '%';
        }
        return '';
    },

    getErrors: function (value) {
        var percent = this.parseValue(value);

        if (percent === null) {
            if (value !== null && value !== '') {
                return [this.invalidText];
            } else {
                percent = '';
            }
        }
        return this.callParent([percent]);
    }
});
