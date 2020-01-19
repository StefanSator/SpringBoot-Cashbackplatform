package de.othr.sw.cashbackplatform.entity.statisticrestservice;

import org.thymeleaf.util.Validate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class BusinessObject extends EntityMasterClass {

    @OneToMany(cascade = {CascadeType.PERSIST})
    List<Attribute> attributeList = new ArrayList<>();



    // FOREIGN KEY
    Long dataModelId;

    // FOREIGN KEY
    Long customerId;

    // TODO Date is deprecated
    // The BusinessObject will be automatically deleted after the set Date is over
    Date willExpandOn;


    public BusinessObject()
    {

    }

    public BusinessObject(Long dataModelId, Long customerId)
    {
        Validate.isTrue(dataModelId > 0, "The dataModelId must be greater than 0");

        Validate.isTrue(customerId > 0, "The customerId must be greater than 0");

        this.dataModelId = dataModelId;

        this.customerId = customerId;
    }

    public Date getWillExpandOn() {
        return willExpandOn;
    }

    public void setWillExpandOn(Date willExpandOn) {
        this.willExpandOn = willExpandOn;
    }

    public void addAttribute(String attributeName, Object attributeValue)
    {
        Attribute attribute = new Attribute(attributeName, attributeValue);

        attributeList.add(attribute);
    }

    public Object getAttributeByName(String attributeName) throws Exception
    {
        // TODO - validation
        return attributeList.stream()
                            .filter(a -> a.getAttributeName().equals(attributeName))
                            .findFirst()
                            .map(oa -> {
                                return oa.getAttribute();
                            })
                            .orElseThrow(Exception::new);
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }

    public Long getDataModelId() {
        return dataModelId;
    }

    public void setDataModelId(Long dataModelId) {
        this.dataModelId = dataModelId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }


}
