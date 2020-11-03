package ch.cscf.jeci.web.menu;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Root class of the menu structure. It is responsible to load the XML file and build the hierarchy of objects that represent the menu structure.
 *
 * This class is configured as a singleton bean in the Spring web context.
 *
 * @author Pierre Henry
 */
public class Menu {

    /**
     * The root element from the XML file
     */
    private MenuGroup rootMenuElement;

    private Map<String, MenuElement> elementsByName = new HashMap<>();

    private Menu(Resource xmlFile) throws Exception{
        Resource xmlFile1 = xmlFile;
        Document document;
        InputStream is = xmlFile.getInputStream();
        SAXBuilder builder = new SAXBuilder();
        document = builder.build(is);
        this.rootMenuElement = new MenuGroup(null, document.getRootElement());

        for(MenuElement element : rootMenuElement.getAllElementsInTree()){
            elementsByName.put(element.getName(), element);
        }
    }

    public MenuGroup getRootMenuElement() {
        return rootMenuElement;
    }

    public MenuElement getMenuElementByName(String name){
        return elementsByName.get(name);
    }
}
