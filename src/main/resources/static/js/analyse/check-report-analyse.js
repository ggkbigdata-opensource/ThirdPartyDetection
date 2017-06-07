/**
 * 
 */

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
function dbclick(id,name){
	var text = document.getElementById(id);
	if(name === 'null'){
		text.outerHTML = '<input name="' + id + '" style="width:60%;margin-right:5px;" value="" /><button onclick="getId(\'' + id + '\')">修改</button>';
	}else{
		text.outerHTML = '<input name="' + id + '" style="width:60%;margin-right:5px;" value="' + name + '" /><button onclick="getId(\'' + id + '\')">修改</button>';
	}
}
function getId(id){
	var na = document.getElementsByName(id)[0];
	$.post('updateStreet',{'reportNum':id,'streetName':na.value},function(result){
		if(result.result == true){
			location.reload();
		}else{
			//layer.alert(result.msg);
		}
	});
}


function init(){
	$.get("street/getAll",function(data){
		if(data){
			//用于查询
			streets = data;
			streets.unshift({id:'',name:'全部'})
			$('#tStreet').combobox({
				data: streets,
				valueField: 'id',
				textField: 'name',
				onLoadSuccess: function () {
		    		$('#tStreet').combobox('select',0);
				},
				onChange: function(){
					var sId = $('#tStreet').combobox('getValue');
					getThirdEchart(sId);
				}
			});
			$('#uStreet').combobox({
				data: streets,
				valueField: 'id',
				textField: 'name',
				onLoadSuccess: function () {
		    		$('#uStreet').combobox('select',0);
				},
				onChange: function(){
					var sId = $('#uStreet').combobox('getValue');
					getFourthEchart(sId);
				}
			});
			$('#hStreet').combobox({
				data: streets,
				valueField: 'id',
				textField: 'name',
				onLoadSuccess: function () {
		    		$('#hStreet').combobox('select',0);
				},
				onChange: function(){
					var sId = $('#hStreet').combobox('getValue');
					getFifthEchart(sId);
				}
			});
			
		}
	});
	getFirstAndSecondEchart();
	getThirdEchart();
	getFourthEchart();
	getFifthEchart();
	//首次加载datatable
	showReportList();
}
init();
function getFirstAndSecondEchart(){
	$.get('streetAndScore',function(result){
		if(result && result.length>0){
			var items=[];
			var datas=[];
			var legend=['各街道得分'];
			var unit='';
			var title = '天河区综合得分';
			datas[0] = [];
			for(var i=0;i<result.length;i++){
				items.push(result[i].streetName);
				datas[0].push(parseFloat(result[i].score).toFixed(3));
			}
			echartBar('streetAndScore',legend,items,datas,unit,title);
		}
	});
	$.get('levelAndScore',function(results){
		if(results && results.length>0){
			var items=[];
			var datas=[];
			var legend=['一级','二级','三级','四级'];
			var unit='个';
			var title = '天河区街道单位等级';
			for(var i=0;i<results.length;i++){
				items.push(results[i].streetName);
			}
			for(var j=0;j<results[0].list.length;j++){
				datas[j]=[];
				for(var k=0;k<results.length;k++){
					datas[j].push(results[k].list[j]);
				}
			}
			echartBar('scoreAndLevel',legend,items,datas,unit,title);
		}
	});
}
function getThirdEchart(sId){
	$.get('streetAndType',{streetId:sId},function(result){
		if(result && result.list.length>0){
			var items=['中学','交通枢纽建筑','公共娱乐建筑','养老院','医院','商业建筑','大学','小学','幼儿园','教育科研建筑','文化设施建筑','物流仓储建筑','科研院','综合性办公建筑','行政办公建筑','院所等教育科研建筑'];
			var datas=[];
			var legend=['报告个数'];
			var unit='个';
			var sName = $('#tStreet').combobox('getText');
			var title = (sName=='全部'?'天河区':(sName+'街道')) + '建筑类型消防设施隐患';
			datas[0]=[];
			for(var i=0;i<result.list.length;i++){
				datas[0].push(result.list[i]);
			}
			echartBar('streetAndType',legend,items,datas,unit,title);
		}
	});
}
function getFourthEchart(sId){
	$.get('streetAndDepartment',{streetId:sId},function(result){
		if(result && result.length>0){
			var items=['医院','幼儿园','小学','中学','大学','养老院'];
			var datas=[];
			var legend=['数量','得分'];
			var unit='个';
			var sName = $('#uStreet').combobox('getText');
			var title = (sName=='全部'?'天河区':(sName+'街道')) + '监管单位等级、得分';
			var ps = '卫生和计划生育局（医院）、教育局（小学，中学，大学）、民政厅（养老院）';
			datas[0]=[];
			datas[1]=[];
			for(var i=0;i<result.length;i++){
				datas[0].push(result[i].count);
				datas[1].push(parseFloat(result[i].score).toFixed(3));
			}
			echartBar('streetAndUnit',legend,items,datas,unit,title,ps);
		}
	});
}
function getFifthEchart(sId){
	$.get('heightAndScore',{streetId:sId},function(result){
		if(result && result.length>0){
			var items=['多层建筑','高层建筑','超高层建筑'];
			var datas=[];
			var legend=['数量','得分'];
			var unit='个';
			var sName = $('#hStreet').combobox('getText');
			var title = (sName=='全部'?'天河区':(sName+'街道')) + '不同高度类型建筑消防设施等级、得分';
			datas[0]=[];
			datas[1]=[];
			for(var i=0;i<result.length;i++){
				datas[0].push(result[i].count);
				datas[1].push(parseFloat(result[i].score).toFixed(3));
			}
			echartBar('streetAndHeight',legend,items,datas,unit,title);
		}
	});
}
var reportList = [];
// show reportList
function showReportList(data) {
    var proxy = "getReportList";
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
                    item[8] = parseFloat(result.data[d].score).toFixed(3);//得分
                    var idNew = result.data[d].reportNum.substr(2,result.data[d].reportNum.length);
                    item[9] = '<div class="table-toolbar tc">'
                            + '<a class="evaluateReport" target="_blank" href="showAbstractReportPage?reportNum='
                            + idNew
                            + '">分析报告</a>'
                            + '<a class="detectionReport" target="_blank" href="fetchReport/'
                            + idNew
                            + '">检测报告</a>'
                            + '<a class="deleteReport" " href="JavaScript: " onclick="deleteReportByReportNum(\''
                            + idNew
                            + '\')">删除</a>' + '</div>'; //
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

//跳转分析
function toAnalyse(sId,bId){
	window.location.href='mainEmbeddedAnalyse?streetId=' + sId + '&blockId=' + (bId == undefined?'':bId);
}
    
    
    
    
    
    // 文件上传
    $(":file").filestyle({
        icon : false,
        buttonText : "选择文件"
    });

    // 导入检测报告数据
    $('#btn-import-check-report').on('click', function() {
        layer.open({
            type : 1,
            title : '导入检测报告',
            area : '650px',
            btn : [ '导入', '关闭' ],
            yes : function(index, layero) {
            	var percent='0%';
            	if($('#inputfile1')[0].files.length > 0){
            		for(var q=0;q<$('#inputfile1')[0].files.length;q++){
            			if($('#inputfile1')[0].files[q].name.indexOf('.pdf') == -1 && $('#inputfile1')[0].files[q].name.indexOf('.PDF') == -1){
            				layer.alert('存在不正确的格式文件，请选择PDF文件导入！');
            				return;
            			}
            		}
            		layer.msg("<span>正在导入检测报告，进度<span id='percent'>0%</span></span>",{
            			shade:0.5,
            			shadeClose: false,
            			time: 0
            		});
            		 (function UpladFile() {
            	            var fileObj = document.getElementById("import-dialog")[0].files; // 获取文件对象
            	            var FileController = "/third/uploadReport";                    // 接收上传文件的后台地址 
            	            var form = new FormData();                                    // FormData 对象
            	            for(var i=0;i<fileObj.length;i++){
            	            	form.append("files", fileObj[i]);                            // 文件对象
            	            }
            	            var xhr = new XMLHttpRequest();
            	            xhr.upload.addEventListener("progress", uploadProgress, false); 
            	            // XMLHttpRequest 对象
            	            xhr.open("post", FileController, true);
            	            xhr.send(form);
            	            function uploadProgress(evt) {  
            	                if (evt.lengthComputable) {  
            	                  var percentComplete = Math.round(evt.loaded * 100 / evt.total);
            	                  if(percentComplete > 5){
            	                	  percent = (percentComplete-5).toString() + '%';
            	                  }else{
            	                	  percent = (percentComplete).toString() + '%';
            	                  }
            	                  $('#percent').text(percent);
            	                }  
            	                else {  
            	                }  
            	              }
            	            xhr.onreadystatechange = function(data){
            	            	if(xhr.readyState ==4&& xhr.status==200){
            	            		percent = '100%';
            	            		$('#percent').text(percent);
            	            		var result = eval('(' + data.target.response + ')');
            	            		if(!result.result && !result.status){
            	            			layer.alert(result.msg);
            	            			return;
            	            		}
            	            		if(!result.status && result.result){
            	            			layer.confirm(result.msg,{
            	            				btn: ['是','否'],
            	            				shade: 0.5,
            	            				shadeClose: false
            	            			},function(){
            	            				layer.msg("正在导入检测报告，请稍候...",{
            	            					shade:0.5,
            	            					shadeClose: false,
            	            					time: 0
            	            				});
            	            				var FileControllerAgain = '/third/uploadReportAgain';
            	            				var xhrAgain = new XMLHttpRequest();
            	            				var formA = new FormData();                                    // FormData 对象
            	            	            for(var i=0;i<fileObj.length;i++){
            	            	            	formA.append("files", fileObj[i]);                            // 文件对象
            	            	            }
            	            				xhrAgain.open("post", FileControllerAgain, true);
            	            				xhrAgain.send(formA);
            	            				xhrAgain.onreadystatechange = function (dataAgain){
            	            					if(xhrAgain.readyState ==4&& xhrAgain.status==200){
            	            						var resultAgain = eval('(' + dataAgain.target.response + ')');
            	            						if(!resultAgain.result){
            	            							layer.alert(resultAgain.msg);
            	            	            			return;
            	            						}
            	            						if(resultAgain.status){
            	            							layer.confirm(resultAgain.msg,{
            	            								btn: ['确定'],
            	            								shade: 0.5,
            	            								shadeClose: false
            	            							},function(){
            	            								location.reload();
            	            							});
            	            						}else{
            	            							layer.alert(resultAgain.msg);
            	            						}
            	            					}else{
            	            						layer.alert('网络有误，请刷新页面！');
            	            					}
            	            				}
            	            			},function(){
            	            			});
            	            		}else{
            	            			layer.confirm(result.msg,{
            	            				btn: ['确定'],
            	            				shade:0.5,
            	            				shadeClose: false
            	            			},function(){
            	            				location.reload();
            	            			});
            	            		}
            	            	}
            	            }
            	        })()
            	}else{
            		layer.alert('请选择导入文件！');
            	}
            },
            btn2 : function(index, layero) {

            },
            shade: false,
            shadeClose : false,
            content : $('#import-dialog')
        });
    });
    
    // 导入评分结果数据
    $('#btn-import-risk-level').on('click', function() {
        layer.open({
            type : 1,
            title : '导入评定结果',
            area : '650px',
            btn : [ '导入', '关闭' ],
            yes : function(index, layero) {
            	if($('#inputfile2')[0].files.length > 0){
            		for(var i=0;i<$('#inputfile2')[0].files.length;i++){
            			if($('#inputfile2')[0].files[i].name.indexOf('.xls') == -1 && $('#inputfile2')[0].files[i].name.indexOf('.xlsx') == -1) {
            				layer.alert('存在不正确的格式文件，请选择excel文件导入！');
            				return;
            			}
            		}
            		layer.msg("正在导入检测报告，请稍候...",{
            			shade:0.5,
            			shadeClose: false,
            			time: 0
            		});
                   /* $("#import-risk-level-dialog").submit();*/
                    var fileObj = document.getElementById("import-risk-level-dialog")[0].files; // 获取文件对象
    	            var FileController = "/third/uploadRiskLevel";                    // 接收上传文件的后台地址 
    	            var form = new FormData();                                    // FormData 对象
    	            for(var i=0;i<fileObj.length;i++){
    	            	form.append("files", fileObj[i]);                            // 文件对象
    	            }
    	            var xhr = new XMLHttpRequest();                              // XMLHttpRequest 对象
    	            xhr.open("post", FileController, true);
    	            xhr.send(form);
    	            xhr.onreadystatechange = function(data){
    	            	if(xhr.readyState ==4&& xhr.status==200){
    	            		var result = eval('(' + data.target.response + ')');
    	            		layer.confirm(result.msg,{
	            				btn: ['确定'],
	            				shade:0.5,
	            				shadeClose: false
	            			},function(){
	            				location.reload();
	            			});
    	            	}else{
    	            		layer.alert('网络有误，请刷新页面！');
    	            	}
    	            }
            	}else{
            		layer.alert('请选择导入文件！');
            	}
            },
            btn2 : function(index, layero) {

            },
            shadeClose : true,
            content : $('#import-risk-level-dialog')
        });
    });
    
    // 批量删除
    $('.btn-batchdelete').on('click', function() {
        layer.confirm('是否批量删除选中数据记录?', {
            icon : 0,
            title : '批量删除'
        }, {
            btn : [ '确认', '取消' ]
        });
    });
    // 提示信息
    // 删除
    $('.btn-delete').on('click', function() {
        layer.confirm('是否删除本条数据记录?', {
            icon : 0,
            title : '删除数据'
        }, {
            btn : [ '确认', '取消' ]
        });
    });
    // 提示信息
    $('.btn-export').on('click', function() {
        layer.confirm('是否导出评估报告?', {
            icon : 0,
            title : '导出报告'
        }, {
            btn : [ '确认', '取消' ]
        });
    });

    

    $('.evaluateReport').click(function() {
        console.info($(this).attr("reportNum"));
    });

    $('.detectionReport').click(function() {
        console.info($(this).attr("reportNum"));
    });

    $('.deleteReport').click(function() {
        console.info($(this).attr("reportNum"));
    });
