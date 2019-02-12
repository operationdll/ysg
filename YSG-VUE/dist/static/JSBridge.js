/**
 * 处理JS 与iOS 交互 && 处理JS 与 安卓交互
 */

//初始化集团id
localStorage.groupid = 3;

//定义版本号，通过后台的版本管理可以进行强制更新提示
localStorage.version = "1.0";

var openMap, getLocation, setMessageTag, removeMessageTag;

if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
  /**
   * 处理JS 与iOS 交互
   */
  //01----->基础参数设置 *  设置平台标识
  localStorage.platform = "1";

  //02----->方法 *  打开地图
  openMap = function(lng, lat) {
    // alert(lng +'=='+lat)
    window.webkit.messageHandlers.openMap.postMessage({ lng: lng, lat: lat });
  };

  //03----->方法 *  获取当前位置
  getLocation = function() {
    window.webkit.messageHandlers.getLocation.postMessage(" ");
  };
  function setLocationLat(lat, lng) {
    //设置当前经纬度
    localStorage.lat = lat;
    localStorage.lng = lng;
  }
  //04----->方法 *  设置消息推送别名

  setMessageTag = function(a, b, c) {
    var obj = {
      alias_type: a,
      alias: b,
      tag: c
    };
    window.webkit.messageHandlers.addUMTagAlias.postMessage(obj);
  };
} else if (/(Android)/i.test(navigator.userAgent)) {
  /**
   * 处理JS 与 安卓交互
   */
  //01----->基础参数设置 *  设置平台标识
  localStorage.platform = "2";

  //02----->方法 *  打开地图
  openMap = function(lng, lat) {
    JSInterface.openMap(lng, lat);
  };

  //03----->方法 *  获取当前位置
  getLocation = function() {
    JSInterface.getLocation();
  };
  setLocationLat = function(lat, lng) {
    localStorage.lat = lat;
    localStorage.lng = lng;
  };

  //04----->方法 *  设置消息推送别名
  function setMessageTag(a, b, c) {
    JSInterface.setMessageTag(a, b, c);
  }
}

//05----->方法 *  安卓打开pdf方法
function openPdf(url) {
  JSInterface.openPDF(url);
}

//06----->方法 *  安卓打开视频的方法
function openVideo(url) {
  JSInterface.openVideo(url);
}
//07----->方法 *  安卓打开原生页面的方法
function openUrl(url, title) {
  JSInterface.openUrl(url, title);
}

//08----->方法 *  iOS打开原生页面的方法
function openFile(url) {
  window.webkit.messageHandlers.openFile.postMessage(url);
}

//09----->方法 *  安卓获取消息列表的方法
function getNotifaction() {
  var obj = JSInterface.getNotifaction();
  return JSON.parse(obj);
}

//10----->方法 *  安卓获判断返回标识
function isHomePage(flag) {
  JSInterface.isHomePage(flag);
}

//11----->方法 *  iOS清除别名
function removeAllTagAlias(a, b, c) {
  var obj = {
    alias_type: a,
    alias: b,
    tag: c
  };
  window.webkit.messageHandlers.removeAllTagAlias.postMessage(obj);
}

function removeMessageTag(a, b, c) {
  JSInterface.removeMessageTag(a, b, c);
}

//12----->方法 *  安卓清除别名
function startXmlNetWork(xmlUrl) {
  JSInterface.startXmlNetWork(xmlUrl);
}
function setXmlData(data) {
  console.log("xml", data);
}

//13----->方法 *  安卓打开外部浏览器
function getSystemBrowser(url) {
  JSInterface.getSystemBrowser(url);
}

//14----->方法 *  iOS打开外部浏览器
function openSafariUrl(url) {
  window.webkit.messageHandlers.openSafariUrl.postMessage(url);
}

//15----->方法 *  iOS版本更新

function updataApp(params) {
  // window.webkit.messageHandlers.updataApp.postMessage(params);
  //更新版本
  if (params.version == params.versionLocal) {
     return;
  } else {	
	if (confirm("检查到新的版本，若不更新，可能无法接受推送消息。是否前往商店下载更新? / New version detected, you may not receive push notification if not updating. Are you going to update now?") == true) {  
		let appUrl = "itms-apps://itunes.apple.com/cn/app/ascott-iservice/id1268497466?l=en&mt=8";
		window.webkit.messageHandlers.openSafariUrl.postMessage(appUrl); 
	} else {
		return;
	}
  }
}
//16----->方法 *  安卓版本更新
function checkVersion(params) {
  JSInterface.checkVersion(params);
}

/**
 * 初始化数据
 */
//初始化位置
getLocation();

function openBooking(url) {
  if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
    window.webkit.messageHandlers.openSafariUrl.postMessage(url);
  } else {
    JSInterface.getSystemBrowser(url);
  }
}

//住客验证登录信息,并重新获取token
function startLogin() {
  if(localStorage.idType == 1&&localStorage.TOKEN){
    let baseUrl = "http://devservice.easyiservice.com";
    let params = {
        fullname: localStorage.FULLNAME,
        groupid: localStorage.groupid,
        hotelid: localStorage.HOTELID,
        identity: localStorage.identity,
        lang: 'zh',
        platform: localStorage.platform,
        room_no: localStorage.ROOM_INFO,
        propertyinterfId: localStorage.propertyinterfId
    };
    let str = "";
    let time = "";
    $.ajax({
        url: baseUrl+'/system/getTime',
        type:'GET',
        async:false,
        timeout:5000,
        dataType:'json',
        success:function(data){ 
            time = data.data.time;
        }
    });
    params.time = time;
    if(time!=""){
      let p = Object.keys(params).sort();
      for (let k in p){
          str += (p[k]+params[p[k]]);
      }
      str += "CtUyV$8MGoK8u5L*P0Q50T/b8S9iclS*LQqo";
      let sign = md5(str);
      params.sign = sign;
      $.ajax({
          url: baseUrl+'/user/login',
          type:'POST',
          data:params,
          timeout:5000,
          dataType:'json',   
          success:function(res){
            if (res.code == 0) {
              localStorage.TOKEN = res.data.token;
            }else{
              //验证失败退出友盟
              let obj={
                  userType:'staff',
                  userId:localStorage.userId,
                  hotelId: [
                      'hotel_'+localStorage.HOTELID,
                      'group_1',
                      localStorage.LANGUAGE
                  ]
              }
              removeAllTagAlias(obj.userType,obj.userId,obj.hotelId);
              localStorage.TOKEN = null;
            }
          }
      });
    }
  }
}

//调用微信小程序
// @params
// var obj = {
//   username: 'gh_ed26ff61e540', //小程序username(原始ID)
//   type: 0,  //0是正式版，1是开发版，2是体验版
//   path:'pages/index/index?name=aaa&room=0702' //访问页面路径
// };
function launchMiniProgramme(params) {
  if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
    window.webkit.messageHandlers.launchMiniProgramme.postMessage(params);
  } else {
    JSInterface.launchMiniProgramme(params);
  }
}