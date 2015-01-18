package donnu.zolotarev.dhoug.Interface;

import donnu.zolotarev.dhoug.Enums.ENTITY;

import java.util.Collection;

public interface IDataHolfer {
     public Collection get(ENTITY entity);
     public void create(ENTITY entity,Collection collection);
}
