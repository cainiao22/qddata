package com.qding.bigdata.ds.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.ModuleDao;
import com.qding.bigdata.ds.model.Module;
import com.qding.bigdata.ds.model.RoleLevel;
import com.qding.bigdata.ds.model.RoleLevelModule;
import com.qding.bigdata.ds.service.ModuleService;
import com.qding.bigdata.ds.service.RoleLevelModuleService;
import com.qding.bigdata.ds.util.CommonUtil;

@Service
public class ModuleServiceImpl extends BaseServiceImpl<Module> implements ModuleService {

  @Autowired
  private ModuleDao moduleDao;
  @Autowired
  private RoleLevelModuleService roleLevelModuleService;

  @Override
  public BaseDao<Module> getDao() {
    return moduleDao;
  }


  @Override
  public void save(Module module) {
    setLevel(module);
    super.save(module);
  }

  private void setLevel(Module module) {
    String pid = module.getPid();
    if (pid.equals("-1")) {
      module.setLevel(1);
    } else {

      Module pModule = new Module();
      pModule.setId(pid);
      pModule = this.getOne(pModule);

      module.setLevel(pModule.getLevel() + 1);
    }

  }

  @Override
  public void update(Module module) {
    setLevel(module);
    setRoleModule(module);
    super.update(module);
  }

  private void setRoleModule(Module module) {
    RoleLevelModule params = new RoleLevelModule();
    params.setModuleId(module.getId());
    List<RoleLevelModule> list = roleLevelModuleService.listAll(params);
    if(list != null){
      for (RoleLevelModule levelModule : list) {
        roleLevelModuleService.delete(new RoleLevelModule(levelModule.getRoleLevelId(), module.getId()));
      }
    }

    String roleLevelsStr = module.getRoleLevelsStr();
    if (CommonUtil.isEmpty(roleLevelsStr)) {
      return;
    }
    String[] roleLevelsSplit = roleLevelsStr.split(",");
    for (String roleLevelId : roleLevelsSplit) {
      roleLevelModuleService.save(new RoleLevelModule(roleLevelId, module.getId()));
    }
  }

  @Override
  public List<Module> sortToTree(List<Module> list) {

    LinkedHashMap<String, List<Module>> subNodeMap = new LinkedHashMap<String, List<Module>>();

    for (Module module : list) {
      String pid = module.getPid();
      if (!subNodeMap.containsKey(pid)) {
        subNodeMap.put(pid, new ArrayList<Module>());
      }
      subNodeMap.get(pid).add(module);
    }

    List<Module> newList = new ArrayList<Module>();
    Module rootModule = new Module();
    rootModule.setId("-1");
    putAllSubModules(subNodeMap, rootModule, newList);
    return newList;

  }

  private void putAllSubModules(LinkedHashMap<String, List<Module>> subNodeMap, Module module,
      List<Module> newList) {

    List<Module> subModuleList = subNodeMap.get(module.getId());
    if (subModuleList == null) {
      return;
    }
    for (Module curModule : subModuleList) {
      newList.add(curModule);
      if (subNodeMap.containsKey(curModule.getId())) {
        putAllSubModules(subNodeMap, curModule, newList);
      }
    }

  }

  @Override
  public void delete(Module module) {
    RoleLevelModule params = new RoleLevelModule();
    params.setModuleId(module.getId());
    List<RoleLevelModule> list = roleLevelModuleService.listAll(params);
    if(list != null){
      for (RoleLevelModule levelModule : list) {
        roleLevelModuleService.delete(new RoleLevelModule(levelModule.getRoleLevelId(), module.getId()));
      }
    }
    super.delete(module);
  }


  @Override
  public void reloadModuleMap() {
    List<Module> allModulesList = this.listAll(new Module());
    Map<String, Module> modulePathMap = new HashMap<String, Module>();
    Map<String, Module> moduleIdMap = new HashMap<String, Module>();
    for (Module module : allModulesList) {
      String[] pathsplit = module.getModuleUrl().split(",");
      for (String path : pathsplit) {
        modulePathMap.put(path, module);
      }
      moduleIdMap.put(module.getId(), module);
    }
    for (String path : modulePathMap.keySet()) {
      Module module = modulePathMap.get(path);

      Constant.MODULEMAP.put(path, this.getModuleNameStr(module, moduleIdMap));
    }

  }

  private String getModuleNameStr(Module module, Map<String, Module> modulePidMap) {
    List<String> moduleNameList = new ArrayList<String>();

    moduleNameList.add(module.getModuleName());
    if (!module.getPid().equals("-1")) {
      moduleNameList.add(modulePidMap.get(module.getPid()).getModuleName());
    }

    String[] moduleNameArr = new String[moduleNameList.size()];
    moduleNameList.toArray(moduleNameArr);
    ArrayUtils.reverse(moduleNameArr);
    return StringUtils.join(moduleNameArr, "->");

  }

  @Cacheable(value = "redisCache", key = "'roleModuleRelation:'.concat(#roleLevel.id)")
  @Override
  public List<Module> listByRoleLevel(RoleLevel roleLevel) {
    
    Set<String> ids = new HashSet<String>();
    List<RoleLevelModule> roleLevelModuleList =
        roleLevelModuleService.listAll(new RoleLevelModule(roleLevel.getId(),null));
    for (RoleLevelModule roleLevelModule : roleLevelModuleList) {
      ids.add(roleLevelModule.getModuleId());
    }
    return this.listByIDs(ids);
  }


@Override
public List<Module> list(Module t) {
	if(StringUtils.isEmpty(t.getSortAndDesc()))
	t.setSortAndDesc("module_level asc ,sort_no asc");
	return super.list(t);
}

}
