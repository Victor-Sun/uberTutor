/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.assembla.com/spaces/bryntum/tickets/2415-assignment-column-editor-collapses-on-checkcolumn-click/details#
Ext.define('Gnt.patches.NavigationModel', {
    extend : 'Sch.util.Patch',

    target   : 'Ext.grid.NavigationModel',

    maxVersion : '6.0.2',

    overrides : {
        onCellMouseDown : function (view, cell, cellIndex, record, row, recordIndex, mousedownEvent) {
            var targetComponent = Ext.Component.fromElement(mousedownEvent.target, cell),
                ac;

            // If actionable mode, and
            //  (mousedown on a tabbable, or anywhere in the ownership tree of the active component),
            // we should not get involved.
            // The tabbable element will be part of actionability.
            if (view.actionableMode && (mousedownEvent.getTarget(null, null, true).isTabbable() || ((ac = Ext.ComponentManager.getActiveComponent()) && ac.owns(mousedownEvent)))) {
                return;
            }

            // If the event is a touchstart, leave it until the click to focus.
            if (mousedownEvent.pointerType !== 'touch') {
                mousedownEvent.preventDefault();
                this.setPosition(mousedownEvent.position, null, mousedownEvent);
            }

            // If mousedowning on a focusable Component.
            // After having set the position according to the mousedown, we then
            // enter actionable mode and focus the component just as if the user
            // Had navigated here and pressed F2.
            if (targetComponent && targetComponent.isFocusable && targetComponent.isFocusable()) {
                view.setActionableMode(true, mousedownEvent.position);

                // Focus the targeted Component
                targetComponent.focus();
            }
        }
    }
});
