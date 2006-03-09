package net.sourceforge.fenixedu.renderers;


import net.sourceforge.fenixedu.renderers.components.HtmlComponent;
import net.sourceforge.fenixedu.renderers.components.HtmlText;
import net.sourceforge.fenixedu.renderers.layouts.Layout;

/**
 * A basic presentation of an integer number.
 * 
 * @author cfgi
 */
public class IntegerRenderer extends OutputRenderer {

    private int base;
    
    public int getBase() {
        return this.base;
    }

    /**
     * Indicates the base in wich the number will be presented.
     * 
     * @property
     */
    public void setBase(int base) {
        this.base = base;
    }

    @Override
    protected Layout getLayout(Object object, Class type) {
        return new Layout() {

            @Override
            public HtmlComponent createComponent(Object object, Class type) {
                Number number = (Number) object;
                
                String text;
                if (number != null && number instanceof Integer) {
                    text = Integer.toString(number.intValue(), getBase());
                }
                else {
                    text = number == null ? "" : number.toString();
                }
                
                return new HtmlText(text);
            }
            
        };
    }
}
