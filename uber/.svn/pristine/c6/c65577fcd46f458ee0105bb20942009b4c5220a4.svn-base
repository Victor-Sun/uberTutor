/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// This class is not part of the build, we do not support scheduling from the end project data (to the start, i.e.
// right to left scheduling), and we do not have ASAP constraint in the build either, thus we do not need this constraint.
Ext.define('Gnt.constraint.AsLateAsPossible', {
    extend      : 'Gnt.constraint.Base',

    singleton   : true,

    /**
     * @cfg {Object} l10n
     * An object, purposed for the class localization. Contains the following keys/values:

            - "name" : "As late as possible"
     */

    isSatisfied : function (task) {
        throw "Abstract method";
    }
});
