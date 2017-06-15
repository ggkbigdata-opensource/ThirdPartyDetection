function echartPie (id,legend,item,datas,title) {
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
        	orient : 'vertical',
            data : legend,
            x: '70%',
            y: '10%'
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
        series : (function() {
            var series = [];
            var d = {};
            d.name = '';
            d.data = [];
            d.radius = '70%';
            d.center = ['40%','50%'];
            d.type = 'pie';
            var percent = 0;
            for(var j=0;j<datas.length;j++){
            	percent += datas[j];
            }
            d.data = (function(){
            	var data = [{},{},{},{}];
            	for(var i=0;i<datas.length;i++){
            		data[i]={name:legend[i],value:datas[i]};
            	}
            	return data;
            })();
            d.itemStyle = {};
            d.itemStyle = {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                normal:{ 
                  label:{ 
                    show: true, 
                    formatter: '{b}：{c}（{d}%）' 
                  }, 
                  labelLine :{show:true} 
                } 
            }
            series.unshift(d);
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
