package ch.cscf.jeci.web.menu;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Abstract class for a menu element. Concrete implementations are groups and items.
 *
 * It contains all the fields and methods that are common to both types of elements.
 *
 * @author Pierre Henry
 */
public abstract class MenuElement {

    /** The name attribute of the element which will be used internally */
    protected String name;

    /** The label attribute of the element which will be used for display. A property name from a source bundle. */
    protected String label;

    /** Permission the user needs to have for the element to be displayed. */
    protected String permission;

    protected Collection<String> parameters;

    protected MenuElement parent;

    public abstract List<MenuElement> getAllElementsInTree();

    /**
     * Builds a new MenuElement from an XML element representing it.
     * @param parent The parent menu element of this element.
     * @param element The XML element representing this element.
     */
    public MenuElement(MenuElement parent, Element element){
        this.parent=parent;
        this.name = element.getAttributeValue("name");
        this.label = element.getAttributeValue("label");
        this.permission= element.getAttributeValue("permission");

        @SuppressWarnings("unchecked")
        List<Element> paramEls = (List<Element>)element.getChildren("param");
        parameters = new ArrayList<>(paramEls.size());
        for(Element paramEl : paramEls){
            parameters.add(paramEl.getText());
        }
    }

    /**
     * Returns the path to this element, starting with the root element and going down the tree until this element.
     * This element is included in the tree (and is always at the last position in the returned listDetailsForMaster).
     * @return
     */
    public List<MenuElement> getPathToElement(){
        List<MenuElement> path;
        if(this.getParent() == null){
            path = new ArrayList<>();
        }else{
            path = getParent().getPathToElement();
        }
        path.add(this);
        return path;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public Collection<String> getParameters() {
        return parameters;
    }

    public MenuElement getParent() {
        return parent;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
