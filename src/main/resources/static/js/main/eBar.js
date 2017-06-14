function echartBar (id,legend,item,datas,unit,title) {
    var option = {
        tooltip : {
            trigger : 'axis'
        },
        title : {
            text: title,
            subtext: '',
            x:'center',
            y:'top'
        },
        legend : {
            data : legend,
            y: 'bottom'
        },
        grid : {
            x : 30,
            y : 50,
            x2 : 80,
            y2 : 80
        },
        toolbox: {
            show : true,
            feature : {
            	/*mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},*/
                saveAsImage : {
                	show: true,
                	title: '保存图片'
            	},
            },
            padding: 25
        },
        calculable : true,
        xAxis : [ {
            type : 'category',
            boundaryGap : true,
            data : item,
            axisLabel : {
                show : true,
                formatter : function(params) {
                    var oneLineLength = 7;
                    var newParamsName = '';
                    var paramsNameNumber = params.length;
                    var provideNumber = oneLineLength;
                    var rowNumber = Math.ceil(paramsNameNumber
                        / provideNumber);
                    if (paramsNameNumber > provideNumber) {
                        for (var p = 0; p < rowNumber; p++) {
                            var tempStr = '';
                            var start = p * provideNumber;
                            var end = start + provideNumber;
                            if (p === rowNumber - 1) {
                                tempStr = params.substring(start,
                                    paramsNameNumber);
                            } else {
                                tempStr = params.substring(start,
                                        end)
                                    + '\n';
                            }
                            newParamsName += tempStr;
                        }
                    } else {
                        newParamsName = params;
                    }
                    return newParamsName;
                }
            }
        } ],
        yAxis : [ {
            type : 'value',
            name : unit ? ('单位：' + unit) : '',
            splitNumber : 4
        } ],
        series : (function() {
            var series = [];
            for (var l = 0; l < datas.length; l++) {
                var d = {};
                d.name = '';
                d.data = [];
                d.name = legend[l];
                d.type = 'bar';
                d.smooth = false;
                d.data = datas[l];
                d.itemStyle = {};
                d.itemStyle = {
            		normal: {  
                        label: {  
                            show: true,//是否展示  
                            position: 'outside',
                            textStyle: {  
                                fontWeight:'bolder',  
                                fontSize : '12',  
                                fontFamily : '微软雅黑',  
                            }  
                        }  
                    }  
                };
                series.push(d);
            }
            return series;
        }()),
        color : ['#19772f','#50b0ae','#f1a330','#d14d58','#b6382b','#dd82b2']
    };
    var line = echarts.init(document.getElementById(id), 'macarons');
    line.setOption(option);
    window.onresize = function() {
        var timeout;
        timeout = setTimeout(function() {
            var alarmTendency = echarts.init(document.getElementById(id), 'macarons');
            alarmTendency.setOption(option);
        }, 200);
    }
}
