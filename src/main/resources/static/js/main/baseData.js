var _flag = true;
function initDistrict(params) {
    $.getJSON(params.districtUrl , function (json) {
   	 //复制一份数据提供新建修改
        var copyJson = json.rows.slice(0);
        // 新增
        // $('#'+params.newDistrictEle).combobox({
        //     data: copyJson,
        //     valueField: 'id',
        //     textField: 'name',
      	// 		onSelect: function(value) {
        //         if(_flag){
        // 					//选择行政区后加载街道
        // 					if(value && value.id == 0){
        // 						initStreet(params,null);
        // 					}
        // 					if(value && value.id > 0){
        // 						var paramsDistrict = {districtId : value.id, districtName:value.name};
        // 						initStreet(params,paramsDistrict);
        // 					}
    		// 		    }
        //   }
        // });
        // 搜索栏
        json.rows.unshift({id: 0, name: '全部'})
        $('#'+params.searchDistrictEle).combobox({
            data: json.rows,
            valueField: 'id',
            textField: 'name',
            onLoadSuccess: function () { //数据加载完毕事件
  	      			if(params.showDistrict && params.showDistrict.length > 0 && params.showDistrict != 'null'){
    	     				var selected = false;
    	     				$.each(json.rows,function(i,v){
    	     					if(v.name == params.showDistrict){
    	     						selected = true;
    	     						$('#'+params.searchDistrictEle).combobox('select',v.id);
    	     						return ;
    	     					}
    	     				});
  	     			}else if(params.showDistrictId && params.showDistrictId.length > 0 && params.showDistrictId != 'null') {
                        var selected = false;
                        $.each(json.rows, function (i, v) {
                            if (v.id == params.showDistrictId) {
                                selected = true;
                                $('#' + params.searchDistrictEle).combobox('select', v.id);
                                return;
                            }
                        });
                    }else {
                        $('#' + params.searchDistrictEle).combobox('select', json.rows[1].id);
                    }
            },
            onSelect: function(value) {  //选择行政区后加载街道
                if(value && value.id == 0){
                    initStreet(params,null);
                }
                if(value && value.id > 0){
	                    var paramsDistrict = {districtId : value.id, districtName:value.name};
	                    initStreet(params,paramsDistrict);
                }
            }
        });
    });
}

//初始街道条件
function initStreet(params,paramsDistrict) {
    $.getJSON(params.streetUrl, paramsDistrict , function (json) {
  	   var copyJson = json.rows.slice(0);
  	   json.rows.unshift({id: 0, name: '全部'});
  	   if(json.rows.length > 1){
  	 	   // $('#'+params.newStreetEle).combobox({disabled:false});
  	       // $('#'+params.newStreetEle).combobox({
  	       //     data: copyJson,
  	       //     valueField: 'id',
  	       //     textField: 'name',
              //  onSelect: function(value) {  //选择街道后加载社区
              //         if (_flag) {
              //             if(value && value.id == 0){
              //                 initBlock(params,null);
              //             }
              //             if(value && value.id > 0){
              //                 var paramsStreet = {streetId : value.id,streetName:value.name};
              //                 initBlock(params,paramsStreet);
              //             }
              //         }
              //   }
  	       // });
  	       $('#'+params.searchStreetEle).combobox({
               data: json.rows,
               valueField: 'id',
               textField: 'name',
               onLoadSuccess: function () { //初始选择全部 ，触发onSelect
            		if(params.showStreet && params.showStreet.length > 0 && params.showStreet!='null'){
     					var selected = false;
     					$.each(json.rows,function(i,v){
         						if(v.name == params.showStreet){
         							selected = true;
         							$('#'+params.searchStreetEle).combobox('select',v.id);
         							return ;
         						}
     					});
            		}else	if(params.showStreetId && params.showStreetId.length > 0 && params.showStreetId!='null'){
     					var selected = false;
     					$.each(json.rows,function(i,v){
     							if (v.id == params.showStreetId) {
				                        selected = true;
				                        $('#'+params.searchStreetEle).combobox('select',v.id);
				                        return ;
         						}
     					});
            		}else{
            			  $('#'+params.searchStreetEle).combobox('select', 0);
            		}
               },
              onSelect: function(value) {  //选择行政区后加载街道
                  if(value && value.id == 0){
                      initBlock(params,null);
                  }
                  if(value && value.id > 0){
                      var paramsStreet = {streetId : value.id,streetName:value.name};
                      initBlock(params,paramsStreet);
                  }
              }
         });
  	   }else{
            $.messager.alert("提示", "该行政区【"+paramsDistrict.districtName+"】暂无街道数据！", "info");
            $('#'+params.searchStreetEle).combobox('clear');
            $('#'+params.searchStreetEle).combobox('select', 0);
            // $('#'+params.newStreetEle).combobox('clear');
            // $('#'+params.newStreetEle).combobox({disabled:true});
//            $("#"+params.newStreetEle).combobox('loadData',[]);  //设置空值
  	   }
    });
}

//初始化社区
function initBlock(params,paramsStreet) {
    $.getJSON(params.blockUrl, paramsStreet , function (json) {
        var copyJson = json.rows.slice(0);
        json.rows.unshift({id: 0, name: '全部'});
        if (json.rows.length > 1) {
            // $('#'+params.newBlockEle).combobox({disabled:false});
            // $('#'+params.newBlockEle).combobox({
            //     data: copyJson,
            //     valueField: 'id',
            //     textField: 'name',
            //     onSelect: function(value) {  //选择街道后加载社区
            //         if (_flag) {
            //             if(value && value.id == 0){
            //                 initBuilding(params,null);
            //             }
            //             if(value && value.id > 0){
            //                 var paramsBlock = {blockId : value.id,blockName:value.name};
            //                 initBuilding(params,paramsBlock);
            //             }
            //         }
            //     }
            // });
        
            $('#'+params.searchBlockEle).combobox({
                data: json.rows,
                valueField: 'id',
                textField: 'name',
                onLoadSuccess: function () {
                	 //初始选择全部 ，触发onSelect
            		if(params.showBlock && params.showBlock.length > 0 && params.showBlock!='null'){
     					var selected = false;
     					$.each(json.rows,function(i,v){
         						if(v.name == params.showBlock){
         							selected = true;
         							$('#'+params.searchBlockEle).combobox('select',v.id);
         							return ;
         						}
     					});
            		}else	if(params.showBlockId && params.showBlockId.length > 0 && params.showBlockId!='null'){
     					var selected = false;
     					$.each(json.rows,function(i,v){
     							if (v.id == params.showBlockId) {
				                        selected = true;
				                        $('#'+params.searchBlockEle).combobox('select',v.id);
				                        return ;
         						}
     					});
            		}else{
            			  $('#'+params.searchBlockEle).combobox('select', 0);
            		}
                }
            }); 
        }else{
//           $.messager.alert("提示", "该街道【"+paramsStreet.streetName+"】暂无社区数据！", "info");
           $('#'+params.searchBlockEle).combobox('clear');
           $('#'+params.searchBlockEle).combobox('select', 0);
           // $('#'+params.newBlockEle).combobox('clear');
           // $('#'+params.newBlockEle).combobox({disabled:true});
//           $("#"+params.newBlockEle).combobox('loadData',[]);  //设置空值
        }
    });
}

//初始化建筑
// function initBuilding(params,paramsBlock) {
//     $.getJSON(params.buildingUrl, paramsBlock , function (json) {
//         var copyJson = json.rows.slice(0);
//         if (json.rows.length > 0) {
//           $('#'+params.newBuildingEle).combobox({disabled:false});
//           $('#'+params.newBuildingEle).combobox({
//               data: copyJson,
//               valueField: 'id',
//               textField: 'baseName'
//           });
//         }else{
//            $.messager.alert("提示", "该社区【"+paramsBlock.blockName+"】暂无建筑数据！", "info");
//            $('#'+params.newBuildingEle).combobox('clear');
//            $('#'+params.newBuildingEle).combobox({disabled:true});
// //           $("#"+params.newBuildingEle).combobox('loadData',[]);  //设置空值
//         }
//     });
// }

function disableLinkage(flag){
    _flag = !flag;
}

function queryAreaTree(queryAreaTreeUrl,ele,showSelected,buildingQueryAllUrl,newBuildingEle) {
    $.getJSON(queryAreaTreeUrl , function (json) {
        $(ele).combotree({
            data: json.data,
            required: true,
            onLoadSuccess: function (node,data) {
                // 先关闭全部菜单
                // $(ele).combotree('tree').tree("collapseAll");
                // var t = $(ele).combotree('tree');//获取tree对象
                // for (var i=0;i<data.length ;i++ ){
                //     node= t.tree("find",data[i].id);
                //     t.tree('expandAll',node.target);//展开所有节点
                //  }
                // $(ele).combotree('setValue',"天河区");//在设置一下combotree的值即可，value为想选中的那个值，一般从后台取出来在设置的 。
                defaultValue(ele,'1','天河区');//这个方法就是解决默认值的问题核心
            },
            onSelect: function (item) {
                var parent = item;
                var tree = $(ele).combotree('tree');
                var path = new Array();
                do {
                    path.unshift(parent.text);
                    var parent = tree.tree('getParent', parent.target);
                } while (parent);
                var pathStr = '';
                for (var i = 0; i < path.length; i++) {
                    pathStr += path[i];
                    if (i < path.length - 1) {
                        pathStr += ' - ';
                    }
                }
                $(showSelected).text(pathStr);
                if(queryAreaTreeUrl.indexOf('flag=3&hasBuilding=1')!=-1) {
                    var nodeNow = tree.tree('getSelected'); // 得到选择的节点
                    var blcokId = nodeNow.id;
                    $(newBuildingEle).combobox({disabled:true});
                    if(blcokId){
                        // 联动建筑
                        $.getJSON(buildingQueryAllUrl+blcokId, function (json) {
                            var copyJson = json.rows.slice(0);
                            if (json.rows.length > 0) {
                                $(newBuildingEle).combobox({disabled:false});
                                $(newBuildingEle).combobox({
                                    data: copyJson,
                                    valueField: 'id',
                                    textField: 'ownerUnitName'
                                });
                            }else{
                                $.messager.alert("提示", "该社区暂无建筑数据！", "info");
                                // $('#'+params.newBuildingEle).combobox('clear');
                                // $('#'+params.newBuildingEle).combobox({disabled:true});
                            }
                        });
                    }else{
                        $.messager.alert("提示", "该选择社区数据！", "info");
                    }
                }
            }
        });
    });
}

//为combotree增加默认值隐藏节点，解决因异步加载导致默认值id直接显示到文本框中的问题
//cbtid:combotree的id
//defval:生成节点的id
//deftext：生成节点的文本用于显示
function defaultValue(cbtid,defVal,defText){
    var combotree =$(cbtid);
    var tree = combotree.combotree('tree');
    var defNode = tree.tree("find",defVal);
    if(!defNode){
        tree.tree('append', {
            data: [{
                id: defVal,
                text: defText
            }]
        });
        defNode = tree.tree("find",defVal);
        combotree.combotree('setValue',defVal);
        tree.tree('select',defNode.target);
        defNode.target.style.display='none';
    }else{
        combotree.combotree('setValue',defVal);
    }
}





