/**
 * 工具类
 */

//3秒弹窗
function popEffect(text) {
    var times = 2;
    $('.pubPopup,.pubMask').show();
    $('.pubPopup .popCon').text(text);
    var i = setInterval(function () {
        times--;
        if (times == 1) {
            $('.pubPopup,.pubMask').addClass('popFadeOut');
        }
        if (times == 0) {
            clearInterval(i);
            $('.pubPopup,.pubMask').hide().removeClass('popFadeOut');
            times = 2;
        }
    }, 1000);
}