package com.qding.bigdata.ds.service;

import java.util.List;

import com.qding.bigdata.ds.model.Module;
import com.qding.bigdata.ds.model.RoleLevel;



public interface ModuleService extends BaseService<Module> {

  List<Module> sortToTree(List<Module> list);

  List<Module> listByRoleLevel(RoleLevel roleLevel);

  void reloadModuleMap();


}
