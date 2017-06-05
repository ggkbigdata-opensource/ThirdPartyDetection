var sId,//传参社区、
	bId,//传参街道id
	streets,//街道数据
	bData,//社区数据
	trendSId,//趋势分析街道id
	trendBId,//趋势分析社区id
	item,//图项目
	datas,//图数据
	unit,//图单位
	legend,//图例
	condition;//图2条件
//走势图，图1
function getTrendChart () {
	$.post(
		'trendChart',
		{
			streetId: trendSId == undefined ? '' : trendSId,
			blockId: trendBId == undefined ? '' : trendBId,
		},
		function (result) {
			if(result != '' || result != null){
				legend = ['检测报告'];
				item = ['危险等级一','危险等级二','危险等级三','危险等级四'];
				datas = [[]];
				unit = '个'
				datas[0] = result.list;
				//绘制echart图
				echartBar('buildAnalyse',legend,item,datas,unit);
			}
		}
	);
}
//总数据，图2
function getAllDatas(data){
	$.post(
		'<%=request.getContextPath() %>/app/buildingInfo/queryPage',
		data,
		function(result){
			if(result!=null && result.rows.length>0){
				var data = [];
				var index = 0;
				for(var d in result.rows){
					var arr = [];
					arr[0] = result.rows[d].propertyCompanyName;
					arr[1] = '<div class="table-toolbar tc">' + result.rows[d].streetName + '</div>';
					arr[2] = '<div class="table-toolbar tc">' + result.rows[d].blockName + '</div>';
					arr[3] = result.rows[d].constructionCategory;
					arr[4] = result.rows[d].buildingTypeBig;
					arr[5] = result.rows[d].heightType;
					arr[6] = result.rows[d].constructionHeight;
					arr[7] = result.rows[d].securityPersonLiable;
					arr[8] = result.rows[d].itemNumber;
					arr[9] = '<div class="table-toolbar tc">'
                        + '<a class="evaluateReport" href="#" onclick="checkStreetIn(\'' + result.rows[d].id + '\')">查看</a>'
                        + '<a class="detectionReport" href="#" onclick="downloadIn(\'' + result.rows[d].id + '\')">下载</a>'
                        + '<a class="deleteReport" href="#" onclick="destroyStreet(\''
                        + result.rows[d].id
                        + '\')">删除</a>' + '</div>';
					data[index++] = arr;
				}
				$('#buildTable').dataTable({
                    "data" : data,
                    /* language : {
                        url : "${pageContext.request.contextPath}/resuources/datatables/Chinese.json"
                    } */
                    'language' : {
                        "sProcessing":   "处理中...",
                        "sLengthMenu":   "显示 _MENU_ 项结果",
                        "sZeroRecords":  "没有匹配结果",
                        "sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                        "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
                        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                        "sInfoPostFix":  "",
                        "sSearch":       "搜索:",
                        "sUrl":          "",
                        "sEmptyTable":     "表中数据为空",
                        "sLoadingRecords": "载入中...",
                        "sInfoThousands":  ",",
                        "oPaginate": {
                            "sFirst":    "首页",
                            "sPrevious": "上一页",
                            "sNext":     "下一页",
                            "sLast":     "末页"
                        },
                        "oAria": {
                            "sSortAscending":  ": 以升序排列此列",
                            "sSortDescending": ": 以降序排列此列"
                        }
                    }
                });
				buildDatas = result.rows;
			}
		}
   	);
}
//获取下拉框街道社区
function getStreetBlock () {
	//2期获取街道，社区
    $.get("${pageContext.request.contextPath}/app/area/street/getAll",function(data){
    	if(data){
    		//用于查询
    		streets = data;
    		streets.unshift({id:'',name:'全部'})
    		$('#streetId').combobox({
    			data: streets,
    			valueField: 'id',
    			textField: 'name',
    			onLoadSuccess: function () {
    				for(var i=0;i<streets.length;i++){
    					if(sId == streets[i].id){
    						$('#streetId').combobox('select',i);
    						break;
    					}
    				}
    			},
    			onChange: function(){
    				trendSId = $('#streetId').combobox('getValue');
    				if($('#streetId').combobox('getValue') != ''){
    					var strId = $('#streetId').combobox('getValue');
    					$.get("${pageContext.request.contextPath}/app/area/street/getBlock?streetId=" + strId, function(data){
    						bData = data;
    						bData.unshift({id:'',name:'全部'});
    						if(bData.length>=0){
    							$('#blockId').combobox({
    								data: bData,
    								valueField: 'id',
    								textField:'name',
    								onLoadSuccess: function(){
    									if(bId !=0 || bId != ''){//首次跳转进来有指定社区
    										for(var i=0;i<bData.length;i++){
    											if(bId == bData[i].id){
    												trendBId = bData[i].id;
    												$('#blockId').combobox('setValue',bData[i].id);
    												break;
    											}
    										}
    										bId = 'first';
    									}else{
    										$('#blockId').combobox('select',0);
    									}
    								},
    								onChange: function(){
    									//图表渲染
    									trendBId = $('#blockId').combobox('getValue');
    									getTrendChart();
    									$('#buildTable').DataTable().destroy();
    								    $('#buildTable tbody').text('');
    								    condition = {streetId: trendSId,blockId: trendBId};
    									getAllDatas(condition);
    									//分析图标题
    									if(trendBId != ''){
    										for(var i=0;i<bData.length;i++){
    											if(trendBId == bData[i].id){
    												$('#thisStreetBlock').text(streetName + bData[i].name);
    												break;
    											}
    										}
    									}else{
    										$('#thisStreetBlock').text(streetName);
    									}
    								}
    							});
    						}
    					});
    				}else{//街道点击全部
    					$('#blockId').combobox('setValue','');
    					trendBId = $('#blockId').combobox('getValue');
    				}
    				//图表渲染
    				trendBId = '';
    				getTrendChart();
    				$('#buildTable').DataTable().destroy();
    			    $('#buildTable tbody').text('');
    			    condition = {streetId: trendSId,blockId: trendBId};
    				getAllDatas(condition);
    				//分析图标题
    				var streetName = '';
    				if(trendSId != ''){
    					for(var i=0;i<streets.length;i++){
    						if(trendSId == streets[i].id){
    							streetName = streets[i].name + '街道';
    							$('#thisStreetBlock').text(streets[i].name + '街道');
    							break;
    						}
    					}
    				}else{
    					$('#thisStreetBlock').text('');
    				}
    			}
    		});
    	}
    });
}

//列表中的操作：删除
function destroyStreet(id){
	$.messager.confirm('提示', '是否删除该数据?', function (r) {
        if (!r) {
            return;
        }else{
            $.post(UrlConfig.deleteDataByIds, {  
            	ids : id  
            }, 
            function(result) {  
            	if(result.successful){
               	  	$.messager.alert("操作提示", result.msg, "info");
               	 	$('#buildTable').DataTable().destroy();
                 	$('#buildTable tbody').text('');
                 	condition = {streetId: trendSId,blockId: trendBId};
                 	getAllDatas(condition);
       	        }else{
       	            if (result.msg){
       	               $.messager.alert("操作提示", result.msg, "error");
       	            }
       	        }
            });
        }
    });
}
//关闭查看
function cancelAction(){
	$('#dlgBuildingSubject_check').dialog('close');
}
//列表中的操作：下载
function downloadIn(id){
	url = '<%=request.getContextPath() %>/app/buildingInfo/download?ids=' + id;
	window.open(url,'_blank');
}
//返回按钮
function getReturn(){
	if(isFromStreet=='street'){
		window.location.href='<%=request.getContextPath() %>/app/page/street';
	}else if(isFromStreet=='block'){
		window.location.href='<%=request.getContextPath() %>/app/page/block';
	}else{
		window.location.href='<%=request.getContextPath() %>/app/page/buildingSubject';
	}
}
function init(){
	condition = {};
	sId = getParam('streetId');
	bId = getParam('blockId');
	isFromStreet = getParam('isFromStreet');
	trendSId = sId;
	trendBId = (bId == 'undefined'?'':bId);
	condition = {streetId: trendSId,blockId: trendBId};
	getStreetBlock();
}
init();