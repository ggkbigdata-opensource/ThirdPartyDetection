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
				unit = ''
				datas[0] = result.list;
				//绘制echart图
				echartBar('reportAnalyse',legend,item,datas,unit);
			}
		}
	);
}
//总数据，图2
function showReportList(data) {
    var proxy = "getReportList";
    /*
     * var params = { 'projectName' : null, 'reportNum' : null,
     * 'projectAddress' : null, 'riskLevel' : null, 'qaName' : null, 'token' :
     * sessionStorage.getItem('token') }
     */
    $.post(
        proxy,
        data,
        function(result) {
            if (null != result && 200 == result.code) {
                var data = new Array();
                var index = 0;
                for ( var d in result.data) {
                    var item = new Array()
                    item[0] = result.data[d].reportNum; // 报告编号
                    item[1] = '<span>' + result.data[d].streetName + '</span>'; // 街道名称
                    item[2] = '<span>' + result.data[d].blockName + '</span>'; // 社区名称
                    item[3] = result.data[d].projectName; // 报告名称
                    item[4] = result.data[d].buildingTypeBig; // 建筑类型
                    item[5] = result.data[d].heigthType; // 高度类型
                    item[6] = result.data[d].projectAddress; // 项目地址
                    item[7] = result.data[d].riskLevel; // 风险等级
                    item[8] = result.data[d].score;//得分
                    var idNew = result.data[d].reportNum.substr(2,result.data[d].reportNum.length);
                    item[9] = '<div class="table-toolbar tc">'
                            + '<a class="evaluateReport" target="_blank" href="showAbstractReportPage?reportNum='
                            + idNew
                            + '">分析报告</a>'
                            + '<a class="detectionReport" target="_blank" href="fetchReport/'
                            + idNew
                            + '">检测报告</a></div>'; //
                    data[index++] = item;
                }
                $("#reportListTable").dataTable({
                    "data" : data,
                    language : {
                        url : "css/datatables/Chinese.json"
                    }
                });
                reportList = result.data;
            }
        });
}
//获取下拉框街道社区
function getStreetBlock () {
	//2期获取街道，社区
    $.get("street/getAll",function(data){
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
    					$.get("block/findByStreetId?streetId=" + strId, function(data){
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
    									$('#reportListTable').DataTable().destroy();
    								    $('#reportListTable tbody').text('');
    								    condition = {streetId: trendSId,blockId: trendBId};
    								    showReportList(condition);
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
    				$('#reportListTable').DataTable().destroy();
    			    $('#reportListTable tbody').text('');
    			    condition = {streetId: trendSId,blockId: trendBId};
    			    showReportList(condition);
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
                 	showReportList(condition);
       	        }else{
       	            if (result.msg){
       	               $.messager.alert("操作提示", result.msg, "error");
       	            }
       	        }
            });
        }
    });
}
//列表中的操作：下载
function downloadIn(id){
	url = '<%=request.getContextPath() %>/app/buildingInfo/download?ids=' + id;
	window.open(url,'_blank');
}
//返回按钮
function getReturn(){
	if(isFromReport=='report'){
		window.location.href='<%=request.getContextPath() %>/app/page/street';
	}else if(isFromReport=='block'){
		window.location.href='<%=request.getContextPath() %>/app/page/block';
	}else{
		//window.location.href='<%=request.getContextPath() %>/app/page/buildingSubject';
		history.go(-1);
	}
}
//删除
function deleteReportByReportNum(reportNum) {
    layer.confirm("确认删除报告："+reportNum+"?",{
        title : '删除检测报告：',
        area : '350px',
        btn : [ '删除', '取消' ],
        yes : function(index, layero) {
            //layer.msg("正在删除检测报告，请稍候...");
            $.get("deleteReportByReportNum?reportNum=" + reportNum, function(data,status) {
                if(data.code == 200){
                    layer.msg("删除成功");
                }
                else{
                    layer.msg("删除失败：" + data.message);
                }
                setTimeout(function(){self.location = 'mainEmbed';},500);
            });
        },
        btn2 : function(index, layero) {
        },
        shadeClose : true,
    });
}
function init(){
	condition = {};
	sId = $('#streetSId').val();
	bId = $('#blockBId').val();
	isFromReport = $('#report').val();
	if(isFromReport !=''){
		$('#ifReturn').hide();
	}
	trendSId = sId;
	trendBId = (bId == 'undefined'?'':bId);
	condition = {streetId: trendSId,blockId: trendBId};
	getStreetBlock();
}
init();