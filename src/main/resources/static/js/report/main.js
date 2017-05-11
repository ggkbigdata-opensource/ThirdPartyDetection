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
                setTimeout(function(){self.location = 'main';},500);
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
	console.log(na.value);
	$.post('updateStreet',{'reportNum':id,'streetName':na.value},function(result){
		console.log(result);
		if(result.result == true){
			location.reload();
		}else{
			//layer.alert(result.msg);
		}
	});
}
$(function() {
    // show reportList
    function showReportList() {
        var proxy = "getReportList";
        /*
         * var params = { 'projectName' : null, 'reportNum' : null,
         * 'projectAddress' : null, 'riskLevel' : null, 'qaName' : null, 'token' :
         * sessionStorage.getItem('token') }
         */
        $.getJSON(
            proxy,
            function(result) {
                if (null != result && 200 == result.code) {
                    var data = new Array();
                    var index = 0;
                    for ( var d in result.data) {
                        var item = new Array()
                        item[0] = result.data[d].reportNum; // 报告编号
                        item[1] = '<span style="cursor:pointer;" id="' + result.data[d].reportNum + '" ondblclick="dbclick(this.id,\'' + result.data[d].streetName + '\')">' + (result.data[d].streetName == null?'暂无':result.data[d].streetName) + '</span>'; // 街道名称
                        item[2] = result.data[d].projectName; // 报告名称
                        item[3] = result.data[d].projectAddress; // 项目地址
                        item[4] = result.data[d].riskLevel; // 风险等级
                        item[5] = result.data[d].detectDate;//检测时间
                        item[6] = result.data[d].qaName; // 检测单位
                        item[7] = result.data[d].contactTel; // 联系电话
                        var idNew = result.data[d].reportNum.substr(2,result.data[d].reportNum.length);
                        item[8] = '<div class="table-toolbar tc">'
                                + '<a class="evaluateReport" target="_blank" href="showAbstractReportPage?reportNum='
                                + idNew
                                + '">分析报告</a>;'
                                + '<a class="detectionReport" target="_blank" href="fetchReport/'
                                + idNew
                                + '">检测报告</a>;'
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
                }
            });
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
            	if($('#inputfile1')[0].files.length > 0){
            		layer.msg("正在导入检测报告，请稍候...",{
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
            	            var xhr = new XMLHttpRequest();                              // XMLHttpRequest 对象
            	            xhr.open("post", FileController, true);
            	            xhr.send(form);
            	            xhr.onreadystatechange = function(data){
            	            	if(xhr.readyState ==4&& xhr.status==200){
            	            		var result = eval('(' + data.target.response + ')');
            	            		console.log(result);
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
                layer.msg("正在导入评定结果，请稍候...");
                $("#import-risk-level-dialog").submit();
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

    showReportList();

    $('.evaluateReport').click(function() {
        console.info($(this).attr("reportNum"));
    });

    $('.detectionReport').click(function() {
        console.info($(this).attr("reportNum"));
    });

    $('.deleteReport').click(function() {
        console.info($(this).attr("reportNum"));
    });
});