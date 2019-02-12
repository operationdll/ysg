function alertLanguage(){
    this.language ={
        zh : {
            title:'提示信息',
            sureBnt:'确认',
            cancelBnt:'取消',
            shopping:{msg:'是否购买选中的物品？'},
            successMsg:'提交成功!',
            sytemBusy:'系统繁忙.',
            breakfastMsg:'请重新登陆获得二维码！',
            washMsg:'收取洗衣'
        },
        en : {
            title:'Information',
            sureBnt:'confirm',
            cancelBnt:'cancel',
            shopping:{msg:'Do you confirm the purchase of this item?'},
            successMsg:'Submitted successfully!',
            sytemBusy:'The system is busy.',
            breakfastMsg:'Please login again to get the QR code!',
            washMsg:'Pick up the laundry'
        }
    };
    this.getAlertMsg = function(localLANGUAGE){
        let obj=this.language.zh;
        if(localLANGUAGE==="en"){
            obj=this.language.en;
        }
        return obj;
    };
}
