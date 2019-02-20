package com.redsamurai.view.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.controller.ControllerContext;
import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichColumn;

import oracle.binding.BindingContainer;

import org.apache.myfaces.trinidad.change.AttributeComponentChange;
import org.apache.myfaces.trinidad.change.ComponentChange;
import org.apache.myfaces.trinidad.change.SessionChangeManager;

public class CustomPersistenceChangeManager extends SessionChangeManager {
    public CustomPersistenceChangeManager() {
        super();
    }

    @Override
    public void addComponentChange(FacesContext facesContext, UIComponent uiComponent,
                                   ComponentChange componentChange) {
        if (uiComponent instanceof RichColumn && componentChange instanceof AttributeComponentChange) {
            AttributeComponentChange c = (AttributeComponentChange) componentChange;

            String id = getPersistenceId(uiComponent.getClientId(FacesContext.getCurrentInstance()));

            System.out.println(id + " : " + c.getAttributeName() + " : " + c.getAttributeValue());
        }
    }

    public String getPersistenceId(String componentId) {
        return getCurrentTaskFlowId() + " : " + getCurrentFragmentId() + " : " + componentId;
    }

    private static String getCurrentTaskFlowId() {
        String tfName =
            ControllerContext.getInstance()
                                         .getCurrentViewPort()
                                         .getTaskFlowContext()
                                         .getTaskFlowId() != null ? ControllerContext.getInstance()
                                                                                     .getCurrentViewPort()
                                                                                     .getTaskFlowContext()
                                                                                     .getTaskFlowId()
                                                                                     .getFullyQualifiedName() :
            "unboundedTF";

        return tfName;
    }

    private static String getCurrentFragmentId() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        if (bindings != null)
            return bindings.getName();

        return "noPageBinding";
    }
}
