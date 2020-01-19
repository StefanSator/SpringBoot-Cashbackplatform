package de.othr.sw.cashbackplatform.entity.statisticrestservice;

import org.thymeleaf.util.Validate;

import java.util.ArrayList;
import java.util.List;

public class BusinessObjectDTO {

    private List<BusinessObject> businessObjectList = new ArrayList<>();

    private List<Long> statisticStructureIDList = new ArrayList<>();



    public void addStatisticStructureID(long id)
    {
        statisticStructureIDList.add(id);
    }


    public void addBusinessObject(BusinessObject businessObject)
    {
        Validate.notNull(businessObject, "The businessObject may not be null");

        businessObjectList.add(businessObject);
    }

    public List<BusinessObject> getBusinessObjectList() {
        return businessObjectList;
    }

    public void setBusinessObjectList(List<BusinessObject> businessObjectList) {
        this.businessObjectList = businessObjectList;
    }

    public List<Long> getStatisticStructureIDList() {
        return statisticStructureIDList;
    }

    public void setStatisticStructureIDList(List<Long> statisticStructureIDList) {
        this.statisticStructureIDList = statisticStructureIDList;
    }
}
