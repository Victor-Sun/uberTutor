/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.sencha.com/forum/showthread.php?305411-Tabbing-in-grid-picker-stops-editing
Ext.define('Gnt.patches.ComponentManager', {
    extend : 'Sch.util.Patch',

    target : 'Ext.ComponentManager',

    minVersion : '6.0.0',

    applyFn : function () {
        Ext.ComponentManager.onGlobalFocus = function(e) {
            var me = this,
                toElement = e.toElement,
                fromElement = e.fromElement,
                toComponent = Ext.Component.fromElement(toElement),
                fromComponent = Ext.Component.fromElement(fromElement),
                commonAncestor = me.getCommonAncestor(fromComponent, toComponent),
                event, targetComponent;
            if (fromComponent && !(fromComponent.destroyed || fromComponent.destroying)) {
                if (fromComponent.handleBlurEvent) {
                    fromComponent.handleBlurEvent(e);
                }
                // Call onFocusLeave on the component axis from which focus is exiting
                for (targetComponent = fromComponent; targetComponent && targetComponent !== commonAncestor; targetComponent = targetComponent.getRefOwner()) {

                    // Sencha set refreshing flag so that onFocusLeave caused by removing a focused element
                    // does not exit actionableMode. This can happen is following scenario:
                    // 1) editor is closed (to be moved, or to stop editing)
                    // 2) editor element is removed from dom -> focus event happens
                    // 3) component manager get component that lost it's focus and start traversing its parents
                    // and call 'onFocusLeave' on them
                    // 4) grid picker knows, that he shouldn't stop editing on that particular event because
                    // of the special flag
                    // 5) but component manager goes further and at some point it'll reach another editor
                    // which doesn't know about it's grid picker child (this is what sencha meant by 'not really
                    // supported' I guess) and it'll complete edit
                    //
                    // ancestor tree is like
                    // numberfield -> editor -> view -> panel -> assignmentfield -> editor -> treeview -> lockedgrid....

                    // Idea behind that fix is simple - if any view has 'refreshing' flag during focus leave, we should
                    // stop focus event.

                    // covered by test 068_assignment_crud
                    // https://www.sencha.com/forum/showthread.php?305411-Tabbing-in-grid-picker-stops-editing

                    // Let's reduce cases to only one we need - assignmentgrid
                    if (targetComponent instanceof Gnt.widget.AssignmentGrid &&
                        // focus event can happen in many cases, we need only one when view has this flag
                        targetComponent.view.refreshing &&
                        // this is not really necessary, but let's reduce our fix even more
                        targetComponent.getRefOwner() instanceof Gnt.field.Assignment) {
                        break;
                    }

                    if (!(targetComponent.destroyed || targetComponent.destroying)) {
                        targetComponent.onFocusLeave({
                            event: e.event,
                            type: 'focusleave',
                            target: fromElement,
                            relatedTarget: toElement,
                            fromComponent: fromComponent,
                            toComponent: toComponent
                        });
                    }
                }
            }
            if (toComponent && !toComponent.destroyed) {
                if (toComponent.handleFocusEvent) {
                    toComponent.handleFocusEvent(e);
                }
                // Call onFocusEnter on the component axis to which focus is entering
                for (targetComponent = toComponent; targetComponent && targetComponent !== commonAncestor; targetComponent = targetComponent.getRefOwner()) {
                    targetComponent.onFocusEnter({
                        event: e.event,
                        type: 'focusenter',
                        relatedTarget: fromElement,
                        target: toElement,
                        fromComponent: fromComponent,
                        toComponent: toComponent
                    });
                }
            }
        };
    }
});