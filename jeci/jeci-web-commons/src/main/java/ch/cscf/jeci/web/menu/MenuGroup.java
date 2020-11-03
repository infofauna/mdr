package ch.cscf.jeci.web.menu;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * A MenuGroup is a type of menu element used to regroup several elements. It is a node in the menu tree that can have children.
 *
 * It is not associated to an action or a URL.
 *
 * @author Pierre Henry
 */
public class MenuGroup extends MenuElement{

    /** List of children elements */
    private List<MenuElement> children;

    /**
     * Builds a MenuGroup based on the XML element representing it.
     *
     * It also parses the XML element's children and recursively builds the children MenuElements of this MenuGroup.
     *
     * @param parent
     * @param element
     */
    public MenuGroup(MenuElement parent, Element element){
        super(parent, element);

        @SuppressWarnings("unchecked")
        List<Element> childrenEls = (List<Element>)element.getChildren();
        children = new ArrayList<>(childrenEls.size());
        for(Element childEl : childrenEls){
            if(childEl.getName().equals("menuItem")){
                children.add(new MenuItem(this, childEl));
            }else if(childEl.getName().equals("menuGroup")){
                children.add(new MenuGroup(this, childEl));
            }
        }
    }

    /**
     * This method returns a listDetailsForMaster of all elements in the subtree starting at this MenuGroup.
     * The sortOrder is depth first. E.g. :
     *
     * group1
     * |- child group 1
     * |  |-item 1
     * |  |-item 2
     * |
     * |- child group 2
     *    |-child group 3
     *    |  |-item 4
     *    |-item 5
     *
     * group1.getALlElementsInTree() would return :
     * {[group1], [child group 1], [item 1], [item 2], [child group 2], [child group 3], [item 4], [item 5]}
     */
    @Override
    public List<MenuElement> getAllElementsInTree(){
        List<MenuElement> elements = new ArrayList<>();
        elements.add(this);
        for(MenuElement child : getChildren()){
            elements.addAll(child.getAllElementsInTree());
        }
        return elements;
    }

    public List<MenuElement> getChildren() {
        return children;
    }
}

