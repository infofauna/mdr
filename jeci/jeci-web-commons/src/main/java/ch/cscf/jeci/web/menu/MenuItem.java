package ch.cscf.jeci.web.menu;


import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an item in the menu that is associated to a URL.
 * This type of item cannot have children.
 * @author Pierre Henry
 */
public class MenuItem extends MenuElement{
    private String url;


    public MenuItem(MenuElement parent, Element element) {
        super(parent, element);
        this.url = element.getAttributeValue("url");
    }

    public String getUrl() {
        return url;
    }

    @Override
    public List<MenuElement> getAllElementsInTree() {
        List<MenuElement> elements = new ArrayList<>();
        elements.add(this);
        return  elements;
    }
}