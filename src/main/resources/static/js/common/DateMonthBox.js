/**
 * Created by ZKT on 2016/10/12 012.
 * 构建一个选择年月的时间控件。
 * @param id input标签的ID
 * eg.
 *  <input id="occurTimeStart" style="width:100px">
 *  <script src="<%=request.getContextPath() %>/js/common/DateMonthBox.js"></script>
 *  <script type="text/javascript">
 *      initDateMonthBox(occurTimeStart);
 *  </script>
 */
var initDateMonthBox = function(id) {
    id = "#" + id;
    $(id).datebox({
        onShowPanel: function () {//显示日期选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            if(!_showMonth) { //显示的不是月份层需要触发click事件弹出月份层
                _showMonth = true;
                var _currentDay = p.find('a.datebox-button-a').first();
                //隐藏‘今天’按钮
                _currentDay.text("");
                span.trigger('click'); //触发click事件弹出月份层
                p.find('.calendar-title').css('pointer-events','none');//禁用标题栏月份点击事件，防止用户点击弹出日期层
            }
            if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                tds.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件 // 不清楚这句是否有效，现象是还是会弹到日期层
                    _showMonth = false;  //由于弹到日期层，需要让下次显示面板的时候再触发click事件弹出月份层
                    p.find('.calendar-title').attr('style','');//恢复标题栏月份点击事件，让上面可以触发
                    var year = /\d{4}/.exec(span.html())[0]//得到年份
                        , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    $(id).datebox('hidePanel')//隐藏日期对象
                        .datebox('setValue', year + '-' + (month<10?'0'+month:month)); //设置日期的值
                });
            }, 0);
            yearIpt.unbind();//解绑年份输入框中任何事件
        },
        onHidePanel:function () {
            _showMonth = false;
        },
        parser: function (s) {
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: function (d) {
            return d.getFullYear() + '-' + (d.getMonth()+1<10?'0'+(d.getMonth()+1):d.getMonth()+1);
        }
    });
    var p = $(id).datebox('panel'), //日期选择对象
    tds = false, //日期选择对象中月份
    _showMonth = false, //是否已经显示月份层，点击月份确定后会显示日期层
    // _text = false,
    yearIpt = p.find('input.calendar-menu-year'),//年份输入框
        // span = p.find('div.calendar-title span'), //显示月份层的触发控件 1.4以下版本
    span = p.find('span.calendar-text');// 显示月份层的触发控件 1.4以及以上版本
    $.extend($.fn.datebox.defaults.rules,{
        checkStartDate:{
            validator:function(value, param){
                var nowDate = new Date();
                nowDate = nowDate.getFullYear() + '-' + ((nowDate.getMonth() + 1)<10?'0'+(nowDate.getMonth() + 1):(nowDate.getMonth() + 1))
                return nowDate>value;
            },
            message:"开始月份不能超过当前月份"
        },
        checkEndDate:{
            validator:function(value, param){
                var startTime = $(param[0]).combobox('getValue');
                if(!startTime || startTime.length == 0 || startTime > value){
                    return false;
                }
                return true;
            },
            message:"开始月份为空或结束月份在开始月份之前"
        }
    });
};