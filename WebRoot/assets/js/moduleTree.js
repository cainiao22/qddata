var setting = {
    data: {
        simpleData: {
            enable: true
        }
    }
};
$(document).ready(function () {
    $.fn.zTree.init($("#treeItem"), setting, zNodes);
});