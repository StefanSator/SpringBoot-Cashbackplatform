package de.othr.sw.cashbackplatform.entity.statisticrestservice;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class EntityMasterClass {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long entityId;

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    @Override public boolean equals(Object o)
    {

        if (o == null) return false;

        if (getClass() != o.getClass()) return false;

        final EntityMasterClass other = (EntityMasterClass) o;

        if (!Objects.equals(entityId, other.entityId)) return false;

        return true;

    }


    @Override public int hashCode()
    {
        if (entityId==-1) {
            return 0;
        }
        else
        {
            String entityIdString = String.valueOf(entityId);

            return entityIdString.hashCode();
        }

    }

    }
