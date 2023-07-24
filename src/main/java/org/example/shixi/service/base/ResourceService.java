package org.example.shixi.service.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.shixi.constant.MessageConstant;
import org.example.shixi.exception.ServiceException;
import org.example.shixi.mapper.ResourceMapper;
import org.example.shixi.tables.dto.ResourceTreeDTO;
import org.example.shixi.tables.entity.ResourceEntity;
import org.example.shixi.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ResourceService extends ServiceImpl<ResourceMapper,ResourceEntity> {
    @Autowired
    private RoleResourseService roleResourseService;
    private List<ResourceEntity> resourceEntityList;
    public boolean add(ResourceEntity resourceEntity){
        Assert.notNull(resourceEntity, MessageConstant.RESOURCE_NOT_NULL);
        return super.save(resourceEntity);
    }
    public boolean delete(Integer id) {
        Assert.notNull(id, MessageConstant.ID_NOT_NULL);
        if (roleResourseService.exists(id)) {
            throw new ServiceException(MessageConstant.ROLE_RESOURCE_EXISTS);
        }
        return super.removeById(id);
    }
    //
    public List<ResourceTreeDTO> tree(){
        List<ResourceEntity> listAll=super.list();
        if(CollectionUtils.isEmpty(listAll)){
            return Collections.emptyList();
        }
        List<ResourceTreeDTO>resourceTreeDTOList= BeanUtil.INSTAMCE.copyResourceEntity(listAll);
        return resourceTreeDTOList.stream().filter(resourceTreeDTO -> Objects.isNull(resourceTreeDTO.getParentId()))
                .peek(resourceTreeDTO -> resourceTreeDTO.setChildren(getChildrenTree(resourceTreeDTO,resourceTreeDTOList)))
                .collect(Collectors.toList());
        //将resourceTreeDTOList转为stream然后通过过滤器删除掉空值
    }
    public List<ResourceTreeDTO> tree(List<Integer>roleIdList){
        resourceEntityList=new ArrayList<>();
        List<ResourceEntity> resourceEntityList1=roleResourseService.listResource(roleIdList);
        if(CollectionUtils.isEmpty(resourceEntityList1)){
            return Collections.emptyList();
        }
        List<ResourceEntity> listAll =super.list();
        resourceEntityList1.forEach(resourceEntity -> getParent(resourceEntity,listAll));
        resourceEntityList.addAll(resourceEntityList1);
        List<ResourceTreeDTO> resourceTreeDTOList = BeanUtil.INSTAMCE.copyResourceEntity(resourceEntityList);
        resourceTreeDTOList=resourceTreeDTOList.stream().distinct().toList();
        List<ResourceTreeDTO> finalResourceTreeDTOList = resourceTreeDTOList;
        return resourceTreeDTOList.stream().filter(resourceTreeDTO -> Objects.isNull(resourceTreeDTO.getParentId()))
                .peek(resourceTreeDTO -> resourceTreeDTO.setChildren(getChildrenTree(resourceTreeDTO, finalResourceTreeDTOList)))
                .collect(Collectors.toList());
        //List<Integer>roleIdList 用于查找所需的资源
        //通过获取roleIdList 并存入resourceEntityList 达到所需的情况
        //除此，有可能一些父节点没有被选择，我们需要将其包括进去，通过包括父节点，我们达成了我们的需求。
    }
    private List<ResourceTreeDTO> getChildrenTree(ResourceTreeDTO root, List<ResourceTreeDTO> all) {
        return all.stream().filter(resourceTreeDTO -> root.getId().equals(resourceTreeDTO.getParentId()))
                .peek(resourceTreeDTO -> resourceTreeDTO.setChildren(getChildrenTree(resourceTreeDTO, all)))
                .collect(Collectors.toList());
    }
    private void getParent(ResourceEntity root,List<ResourceEntity>all){
        all.stream().filter(resourceEntity -> resourceEntity.getId().equals(root.getParentId()))
                .forEach(resourceEntity -> {
                    resourceEntityList.add(resourceEntity);
                    getParent(resourceEntity,all);
                });
        /*通过循环过滤得到父节点，然后通过获取父节点进行进一步的递归，一直到主节点。*/
    }
}
