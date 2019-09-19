<%--
  Created by IntelliJ IDEA.
  User: QDHL2
  Date: 2018/6/25
  Time: 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="./css/lpUserAuthority/demo.css" type="text/css">
<link rel="stylesheet" href="./css/lpUserAuthority/metroStyle/metroStyle.css" type="text/css">
<!-- 引入 Bootstrap -->
<link href="./css/lpUserAuthority/bootstrap.min.css" rel="stylesheet">
<c:import url="common/importJs2.jsp"></c:import>
<script type="text/javascript" src="./assets/plugins/zTree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="./assets/plugins/zTree/jquery.ztree.excheck-3.5.min.js"></script>
<head>
    <meta charset="utf-8"/>
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="common/importCss2.jsp"></c:import>
</head>
<body>
<!--#f0f6e4-->
<div class="boxs clearfix">
    <div class="boxd">
        <div class="left-box">
            <h2>人员列表</h2>
            <ul class="name-list">
                <!--<li>张三00001</li>-->
            </ul>
        </div>
        <div class="right-box">
            <h2>权限设置</h2>
            <div class="content_wrap">
                <div class="zTreeDemoBackground left">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
        <!--保存-->
        <button type="button" class="btn " onclick="saveqx();">保存</button>
    </div>
</div>
</body>
<script type='text/javascript'>
    var userid , username;
    var pp=[];
    // 人员列表
    function Getlist() {
        // 人员列表
        $.ajax({
            type: 'GET',
            url: './lpUserList',
            success:function(data) {
                if(data != null && data != undefined ){
                    $.each(data,function(index, item){
                        var lis =document.createElement('li');
                        lis.innerHTML = item.realName + item.userName;
                        lis.id = item.id;  // 人员id
                        lis.dataname = item.realName;  // 人员名
                        lis.username = item.userName;
                        lis.moduleName=item.moduleName;
                        $('.name-list').append(lis); // 添加在ul里面

                    })
                    //点击li 获取当前内容
                    $('.name-list li').click( function() {
                        $(this).addClass('actives')      //为选中项添加高亮
                            .siblings().removeClass('actives')//去除其他项的高亮形式
                            .end();
                        // 获取当前点击的内容
                        console.log($(this).context.id);
                        console.log($(this).context.username);
                        var idx = $(this).context.id;  //点击当前人员id
                        var names = $(this).context.username;  // 点击当前人员name名
                        var moduleName=$(this).context.moduleName;
                        userid=idx;
                        username=names;
                        // 请求权限列表
                        $.ajax({
                            type: 'POST',
                            url: './getRegionIdListByUser',
                            data: {
                                'userName': names,
                                'moduleName': moduleName
                            },
                            success: function(datas) {
                                pp= datas.projectSet;
                                /*console.log(datas);
                                console.log(pp);*/
                                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                                treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                                for(var s=0;s<pp.length;s++){
                                    var node = treeObj.getNodeByParam("id", pp[s], null);
                                    if(node!=null){
                                        treeObj.checkNode(node, true, true);
                                    }
                                }
                            },
                            error: function(err){
                                //请求出错处理
                                 alert(err);
                            }
                        })
                    })

                }
            },
            error: function(err) {
                alert(err);
            }
        })

    }
    var zNodes;
    var treeObj;
    var setting;
    // 权限列表
    function Jurisdiction() {
        $.ajax({
            type: 'post',
            url: './getRegionList',
            success:function(data) {
                var id1  = data['region_id'];
                var name1 = data['region_name'];
                var level1=data['level'];
                var areaList  = data['areaList'];
                var area = [];
                for(var j=0;j<areaList.length;j++){
                    var id2=areaList[j]['region_id'];
                    var name2 = areaList[j]['region_name'];
                    var pid2 =areaList[j]['pid'];
                    var level2=areaList[j]['level'];
                    var cityList  =areaList[j]['cityList'];
                    var city=[];
                    if (typeof(cityList) != "undefined")
                    {
                        for(var l=0;l<cityList.length;l++){
                            var id3=cityList[l]['region_id'];
                            var name3 =cityList[l]['region_name'];
                            var pid3 =cityList[l]['pid'];
                            var level3=cityList[l]['level'];
                            var projectList  =cityList[l]['projectList'];
                            var project=[];
                            if (typeof(projectList) != "undefined")
                            {
                                for(var k=0;k<projectList.length;k++){
                                    var id4=projectList[k]['region_id'];
                                    var name4 =projectList[k]['region_name'];
                                    var pid4 =projectList[k]['pid'];
                                    var level4=projectList[k]['level'];
                                    project[k]={name:name4,id:id4,pid:pid4,level: level4};
                                }
                            }
                            city[l]={name:name3,id:id3,pid:pid3,level: level3, children:project}
                        }
                    }
                    area[j]={name:name2,id:id2,pid:pid2,level: level2,open:true,children:city }
                }
                zNodes={name:name1,id:id1,level: level1,open:true,children:area};
                //js方法
                function Check(event, treeId, treeNode){
                    if(treeNode.isParent){
                        var checked4=true;
                        if(treeNode.getParentNode() != null){
                            var peerNodes2=  treeNode.getParentNode().children;
                            for(var b=0;b<peerNodes2.length;b++){
                                var checked3 = peerNodes2[b].checked;
                                if(checked3){
                                }else{
                                    checked4=false;
                                }
                            }
                            treeNode.getParentNode().checked =checked4;
                            treeObj.updateNode(treeNode.getParentNode());
                        }
                    }else{
                        if(treeNode.getParentNode() != null){
                            var peerNodes1=  treeNode.getParentNode().children;
                            var checked1=true;
                            for(var a=0;a<peerNodes1.length;a++){
                                var checked2 = peerNodes1[a].checked;
                                if(checked2){
                                }else{
                                    checked1=false;
                                }
                            }
                            treeNode.getParentNode().checked =checked1;
                            treeObj.updateNode(treeNode.getParentNode());
                            var checked5=true;
                            if(treeNode.getParentNode().getParentNode() != null){
                                var peerNodes3=  treeNode.getParentNode().getParentNode().children;
                                for(var c=0;c<peerNodes3.length;c++){
                                    var checked6 = peerNodes3[c].checked;
                                    if(checked6){
                                    }else{
                                        checked5=false;
                                    }
                                }
                                treeNode.getParentNode().getParentNode().checked =checked5;
                                treeObj.updateNode(treeNode.getParentNode().getParentNode());
                            }
                        }
                    }
                }

                // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
                setting = {
                    check:{
                        chkboxType: { "Y": "s", "N": "s" },
                        enable: true,
                        autoCheckTrigger:true,
                        chkStyle:"checkbox"
                    },
                    callback:{
                        onCheck:Check
                    }
                };
                //页面加载成功后,开始加载树形结构
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）

            }
        })
    }

    /*
     data:    {
     simpleData:{
     enable:true
     }
     },
     var zNodes =[
     { id:1, pId:0, name:"随意勾选 1", open:false},
     { id:11, pId:1, name:"随意勾选 1-1", open:true},
     { id:111, pId:11, name:"随意勾选 1-1-1"},
     { id:112, pId:11, name:"随意勾选 1-1-2"},
     { id:12, pId:1, name:"随意勾选 1-2", open:true},
     { id:121, pId:12, name:"随意勾选 1-2-1"},
     { id:122, pId:12, name:"随意勾选 1-2-2"}
     ];*/
    $(document).ready(function(){
        Getlist();
        //console.log(${projectSet});
        Jurisdiction();
    });
    function  saveqx() {
        var nodes=treeObj.getCheckedNodes(true);
        var a=[];
        var b=[],b1=0;
        var c=[],c1=0;
        var d=[],d1=0;
        for(var i=0;i<nodes.length;i++){
            if(nodes[i].level=="0"){
                a[0]={regionId:nodes[i].id,regionName:nodes[i].name,regionLevel:(nodes[i].level+1)};
            }
            if(nodes[i].level=="1"){
                b[b1]={regionId:nodes[i].id,regionName:nodes[i].name,regionLevel:(nodes[i].level+1),parentId:nodes[i].pid};
                b1+=1;
            }
            if(nodes[i].level=="2"){
                c[c1]={regionId:nodes[i].id,regionName:nodes[i].name,regionLevel:(nodes[i].level+1),parentId:nodes[i].pid};
                c1+=1;
            }
            if(nodes[i].level=="3"){
                d[d1]={regionId:nodes[i].id,regionName:nodes[i].name,regionLevel:(nodes[i].level+1),parentId:nodes[i].pid};
                d1+=1;
            }
        }
        var m=[], w=0;
        var ma;
        if(a.length>0){
            ma=JSON.stringify(a);
        }else {
            if(b.length>1){
                 for(var i=0;i<b.length;i++){
                     var cl= c.length
                     for(var j=cl-1;j>=0;j--){
                         if(c[j].parentId==b[i].regionId){
                             c.splice(j,1);
                         }
                         var dl=d.length;
                         //遍历所有社区
                         for(var k=dl-1;k>=0;k--){
                             if ( d[k].parentId==c[j].regionId) {
                                     d.splice(k,1);
                             }
                         }
                     }
                     m[w]=b[i];
                     w+=1;
                 }
                for(var j=0;j<c.length;j++){
                    m[w]=c[j];
                    w+=1;
                }
                for(var k=0;k<d.length;k++){
                    m[w]=d[k];
                    w+=1;
                }

            }else{
                if(c.length>1){
                     for(var j=0;j<c.length;j++){
                         var dl=d.length;
                         for(var k=dl-1;k>=0;k--){
                             if ( d[k].parentId==c[j].regionId) {
                                 d.splice(k,1);
                             }
                         }
                     m[w]=c[j];
                     w+=1;
                     }
                    for(var k=0;k<d.length;k++){
                        m[w]=d[k];
                        w+=1;
                    }
                }else{
                    if(d.length>1){
                        for(var k=0;k<d.length;k++){
                            m[w]=d[k];
                            w+=1;
                        }
                    }
                }
            }
       /* var a="";
        var b="";
        var c="";
        var d="";
        for(var i=0;i<nodes.length;i++){
            if(nodes[i].level=="0"){
                a+='{"regionId":"'+nodes[i].id+'","regionName":"'+nodes[i].name+'","regionLevel":"'+(nodes[i].level+1)+'"}';
            }
            if(nodes[i].level=="1"){
                b+='{"regionId":"'+nodes[i].id+'","regionName":"'+nodes[i].name+'","parentId":"'+nodes[i].pid+'","regionLevel":"'+(nodes[i].level+1)+'"};';
            }
            if(nodes[i].level=="2"){
                c+='{"regionId":"'+nodes[i].id+'","regionName":"'+nodes[i].name+'","parentId":"'+nodes[i].pid+'","regionLevel":"'+(nodes[i].level+1)+'"};';
            }
            if(nodes[i].level=="3"){
                d+='{"regionId":"'+nodes[i].id+'","regionName":"'+nodes[i].name+'","parentId":"'+nodes[i].pid+'","regionLevel":"'+(nodes[i].level+1)+'"};';
            }
        }
        b=b.split(";");
        c=c.split(";");
        d=d.split(";");
        var m=[], w=0;
        var n=[],x=0;
        if(a.length>0){
            m[0]=a;
        }else {
            if(b.length>1){
                for(var i=0;i<b.length-1;i++){
                    m[w]=b[i];
                    w+=1;
                }
                for(var j=0;j<c.length-1;j++){
                    m[w]=c[j];
                    w+=1;
                }
                for(var k=0;k<d.length-1;k++){
                        m[w]=d[k];
                        w+=1;
                }
            }else{
                if(c.length>1){
                    for(var j=0;j<c.length-1;j++){
                        m[w]=c[j];
                        w+=1;
                    }
                    for(var k=0;k<d.length-1;k++){
                            m[w]=d[k];
                            w+=1;
                    }
                }else{
                    if(d.length>1){
                        for(var k=0;k<d.length-1;k++){
                            m[w]=d[k];
                            w+=1;
                        }
                    }
                }
            }*/

            ma=JSON.stringify(m);
        }
        $.ajax({
            type: 'post',
            url: './lpsave',
            data:'id='+userid+'&v='+ma,
            dataType:'json',
            success:function() {
                alert("保存成功");
            }
        });
    }

</script>

</html>