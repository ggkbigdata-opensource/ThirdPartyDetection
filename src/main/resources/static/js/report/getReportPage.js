/**
 * 
 */


function submitInfoData() {
    var proxy = "report/submitExtractCode";
    var extracteCode = $("#fetchCode").val();
    var ownerName = $("#ownerName").val();
    var dutyTel = $("#dutyTel").val();
    //console.info(extracteCode);
    //console.info(ownerName);
    //console.info(dutyTel);
    var params = {
         'extracteCode' : extracteCode,
         'ownerName': ownerName,
         'dutyTel' : dutyTel
    }
    if(ownerName == ''){
    	alert('查询单位名不能为空！');
    	return;
    }else if(dutyTel == ''){
    	alert('责任人手机不能为空！');
    	return;
    }else if(extracteCode == ''){
    	alert('报告提取码不能为空！');
    	return;
    }
    $.getJSON(proxy, params, function(result){
        if(result.code == 3) {
            alert("公司名称有误！");
            //self.location = 'getReportPage';
        }else if(result.code == 2){
        	alert("提取码有误！");
        	//self.location = 'getReportPage';
        }else if(result.code == 1){
        	alert("电话有误！");
        	//self.location = 'getReportPage';
        }else {
            //sessionStorage.setItem('verifyToken', result.verifyToken);
            window.location.href ="showDetailReportPage";
        }
    })
}
