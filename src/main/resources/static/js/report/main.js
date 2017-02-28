/**
 * 
 */
$(function() {
    // 文件上传
    $(":file").filestyle({
        icon : false,
        buttonText : "选择文件"
    });
    
    // 导入数据
    $('.btn-import').on('click', function() {
        layer.open({
            type : 1,
            title : '导入检测报告',
            area : '650px',
            btn : [ '导入', '关闭' ],
            yes : function(index, layero) {
                layer.msg("正在导入检测报告，请稍候...");
                $("#import-dialog").submit();
            },
            btn2 : function(index, layero) {

            },
            shadeClose : true,
            content : $('#import-dialog')
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
});