package de.othr.sw.cashbackplatform.entity.statisticrestservice;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class Attribute extends EntityMasterClass {

    String attributeName;

    @Embedded
    Object attribute = new Object();


    public Attribute()
    {

    }


    public Attribute(String attributeName, Object attribute)
    {
        if(attribute != null && attributeName != null)
        {
            this.attribute = attribute;

            this.attributeName = attributeName;
        }
        else
        {
            // TODO
        }
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Object getAttribute() {
        return attribute;
    }

    public void setAttribute(Object attribute) {
        this.attribute = attribute;
    }
}
