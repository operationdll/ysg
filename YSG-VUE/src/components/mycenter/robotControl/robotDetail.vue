<template>
  <div class="search">
    <div class="search_box">
        <div class="divTitle">{{language.myCenter.robotControl}}</div>
        <div class="divmBG"></div>
        <div class="bntDiv">
            <div class="bnt">
                <div>
                    <span class="lArrow" style="margin-top:.48rem;margin-left:-.6rem;"></span>
                        <button class="innerBnt">{{title}}</button>
                    <span class="rArrow" style="margin-top:-.51rem;margin-left:5.2rem;"></span>
                </div>
            </div>
        </div>
        <!--送物-->
        <div id="swDiv" style="display:none;">
            <div class="iptDiv">
                <div class="ipt" style="margin-left:1rem">
                    <span class="cline" style="margin-top:1.3rem;margin-left:-.95rem;"></span>
                    <input type="text" style="text-align:center;width: 1.98rem;margin-top: 1rem;border:0px;color:white;" readonly="ture" @click="isSendStart = true"  v-model="sendStartAdd"/>
                    <span class="cline" style="margin-top: -0.27rem;margin-left: 2.55rem;width: .5rem;"></span>
                    <popup-picker :data="sendStartList" :show="isSendStart" @on-hide="isSendStart = false" :columns="1" @on-change="sendStartChange"></popup-picker>
                </div>
                <div class="ipt" style="margin-left:4rem">
                    <input type="text" style="width: 2rem;margin-top: 1rem;border:0px;margin-left:.7rem;color:white;" placeholder="房间号" v-model="sendEndID"/>
                    <span class="cline" style="margin-top: -.286rem;margin-left: 2.58rem;"></span>
                </div>
            </div>
            <div class="bottomBnt">
                <div class="bottomBntL" style="margin-left:1.2rem" @click="goBack">
                    <span class="bntNode" style="margin-left:.45rem;">
                        取消
                    </span>
                </div>
                <div class="bottomBntR" style="margin-left:2.5rem" @click="sendObject">
                    <span class="bntNode" style="margin-left:1.4rem;">
                        立即出发
                    </span>
                </div>
            </div>
        </div>
        <!--取物-->
        <div id="qwDiv" style="display:none;">
            <div class="iptDiv">
                <div class="ipt" style="margin-left:1rem">
                    <span class="cline" style="margin-top: 1.3rem;margin-left: -.95rem;"></span>
                    <input type="text" style="width: 2rem;margin-top: 1rem;border:0px;margin-left:.7rem;color:white;" placeholder="房间号" v-model="getRoom"/>
                    <span class="cline" style="margin-top: -0.286rem;margin-left: 2.58rem;width: 0.5rem;"></span>
                </div>
                <div class="ipt" style="margin-left:4rem">
                    <span class="cline" style="margin-top: 1.3rem;margin-left: 2.58rem;"></span>
                    <input type="text" style="text-align:center;width: 1.98rem;margin-top: 1rem;border:0px;color:white;" readonly="ture" @click="isGetObj = true"  v-model="getObjText"/>
                    <popup-picker :data="isGetObjList" :show="isGetObj" @on-hide="isGetObj = false" :columns="1" @on-change="getObjChange"></popup-picker>
                </div>
            </div>
            <div class="bottomBnt">
                <div class="bottomBntL" style="margin-left:1.2rem" @click="goBack">
                    <span class="bntNode" style="margin-left:.45rem;">
                        取消
                    </span>
                </div>
                <div class="bottomBntR" style="margin-left:2.5rem" @click="getToDo">
                    <span class="bntNode" style="margin-left:1.4rem;">
                        立即出发
                    </span>
                </div>
            </div>
        </div>
        <!--召唤-->
        <div id="zhDiv" style="display:none;">
            <div class="iptDiv">
                <div class="ipt" style="margin-left:2.5rem">
                    <span class="cline" style="margin-top: 1.304rem;margin-left: -2.48rem;background-size: 2.5rem 0.03rem;width: 2.5rem;"></span>
                    <input type="text" style="text-align:center;width: 1.98rem;margin-top: 1rem;border:0px;color:white;" readonly="ture" @click="isCall = true"  v-model="callText"/>
                    <span class="cline" style="margin-top: -0.27rem;margin-left: 2.58rem;background-size: 2.5rem 0.03rem;width: 2.5rem;"></span>
                    <popup-picker :data="callList" :show="isCall" @on-hide="isCall = false" :columns="1" @on-change="callChange"></popup-picker>
                </div>
            </div>
            <div class="bottomBnt">
                <div class="bottomBntL" style="margin-left:1.2rem" @click="goBack">
                    <span class="bntNode" style="margin-left:.45rem;">
                        取消
                    </span>
                </div>
                <div class="bottomBntR" style="margin-left:2.5rem" @click="callToDo">
                    <span class="bntNode" style="margin-left:1.4rem;">
                        立即出发
                    </span>
                </div>
            </div>
        </div>
        <!--引领-->
        <div id="ylDiv" style="display:none;">
            <div class="iptDiv">
                <div class="ipt" style="margin-left:1rem">
                    <span class="cline" style="margin-top:1.3rem;margin-left:-.95rem;"></span>
                    <input type="text" style="width: 1.98rem;margin-top: 1rem;border:0px;color:white;margin-left:.9rem;" readonly="ture" @click="isType = true"  v-model="typeText"/>
                    <span class="cline" style="margin-top: -0.27rem;margin-left: 2.55rem;width: .5rem;"></span>
                    <popup-picker :data="typeList" :show="isType" @on-hide="isType = false" :columns="1" @on-change="typeChange"></popup-picker>
                </div>
                <div class="ipt" style="margin-left:4rem">
                    <div v-if="typeId==0">
                        <input type="text" style="width: 2rem;margin-top: 1rem;border:0px;margin-left:.7rem;color:white;" placeholder="房间号" v-model="roomID"/>
                        <span class="cline" style="margin-top: -.286rem;margin-left: 2.58rem;"></span>
                    </div>
                    <div v-if="typeId==1">
                        <input type="text" style="width: 1.98rem;margin-top: 1rem;border:0px;color:white;" readonly="ture" @click="isAdd = true"  v-model="addText"/>
                        <span class="cline" style="margin-top: -.286rem;margin-left: 2.58rem;"></span>
                        <popup-picker :data="addList" :show="isAdd" @on-hide="isAdd = false" :columns="1" @on-change="addChange"></popup-picker>
                    </div>
                    <div v-if="typeId==2">
                        <input type="text" style="width: 1.98rem;margin-top: 1rem;border:0px;color:white;" readonly="ture" @click="isLift = true"  v-model="liftText"/>
                        <span class="cline" style="margin-top: -.286rem;margin-left: 2.58rem;"></span>
                        <popup-picker :data="liftList" :show="isLift" @on-hide="isLift = false" :columns="1" @on-change="liftChange"></popup-picker>
                    </div>
                </div>
            </div>
            <div class="bottomBnt">
                <div class="bottomBntL" style="margin-left:1.2rem" @click="goBack">
                    <span class="bntNode" style="margin-left:.45rem;">
                        取消
                    </span>
                </div>
                <div class="bottomBntR" style="margin-left:2.5rem" @click="addToDo">
                    <span class="bntNode" style="margin-left:1.4rem;">
                        立即出发
                    </span>
                </div>
            </div>
        </div>
        <!--返回充电桩-->
        <div id="fhDiv" style="display:none;">
            <div class="iptDiv">
                <div class="ipt" style="margin-left:2.5rem">
                    <span class="cline" style="margin-top: 1.304rem;margin-left: -2.48rem;background-size: 2.5rem 0.03rem;width: 2.5rem;"></span>
                    <input type="text" style="width: 1.98rem;margin-top: 1rem;border:0px;color:white;text-align:center;" readonly="ture" @click="isBack = true"  v-model="backText"/>
                    <span class="cline" style="margin-top: -0.27rem;margin-left: 2.58rem;background-size: 2.5rem 0.03rem;width: 2.5rem;"></span>
                    <popup-picker :data="backList" :show="isBack" @on-hide="isBack = false" :columns="1" @on-change="backChange"></popup-picker>
                </div>
            </div>
            <div class="bottomBnt">
                <div class="bottomBntL" style="margin-left:1.2rem" @click="goBack">
                    <span class="bntNode" style="margin-left:.45rem;">
                        取消
                    </span>
                </div>
                <div class="bottomBntR" style="margin-left:2.5rem" @click="backToDo">
                    <span class="bntNode" style="margin-left:1.4rem;">
                        立即出发
                    </span>
                </div>
            </div>
        </div>
    </div>
  </div>
</template>
<style scoped>
.search_box{
    background: url("../../../assets/images/robotControl/bg.png")center no-repeat;
    background-size: 9.5rem;
    width:100%;
    height:100%;
}

.bntDiv{
    width:100%;
    margin-top:7.6rem;
    display:block;
    position:absolute;
    z-index:1;
    justify-content: center;
    text-align: center;
}

.innerBnt{
    background: url("../../../assets/images/robotControl/selBG1.png")center no-repeat;
    width: 5.6rem;
    height: 1rem;
    border: 0px;
    background-size: 5rem;
    font-family:PingFangSC-Regular;
    font-size:.4rem;
    margin-top: -0.01rem;
    color:#efc567;
    text-align:center;
    margin-left: auto; 
    margin-right: auto;
    z-index: 1;
}

.bnt{
    background: url("../../../assets/images/robotControl/selBG.png")center no-repeat;
    width: 5.6rem;
    height: 1rem;
    border: 0px;
    background-size: 5rem;
    margin-top: .3rem;
    text-align:center;
    margin-left: auto; 
    margin-right: auto;
}

.divTitle{
    font-family:PingFangSC-Regular;
    font-size:.4rem;
    color:white;
    display: block;
    margin-top:.6rem;
    margin-left: 2.9rem;
    position: absolute;
    z-index: 2;
}

.iptDiv{
    display: block;
    width: 100%;
    height: 2.6rem;
    margin-top:9rem;
    position:absolute;
}

.ipt{
    background: url("../../../assets/images/robotControl/selBG2.png")center no-repeat;
    width: 2.6rem;
    height: 2.6rem;
    border: 0px;
    background-size: 2.6rem;
    font-family:PingFangSC-Regular;
    font-size:.4rem;
    text-align:center;
    color:#5F6C88;
    margin-left: auto; 
    margin-right: auto;
    position:absolute;
}

.bottomBnt{
    display: block;
    width: 100%;
    height: 2.6rem;
    margin-top:11.5rem;
    position:absolute;
}

.bottomBntL{
    background: url("../../../assets/images/robotControl/lbnt.png")center no-repeat;
    width: 1.5rem;
    height: 1.5rem;
    border: 0px;
    background-size: 1.5rem;
    text-align:center;
    
    margin-left: auto; 
    margin-right: auto;
    position:absolute;
}

.bottomBntR{
    background: url("../../../assets/images/robotControl/rbnt.png")center no-repeat;
    width: 4rem;
    height: 1.5rem;
    border: 0px;
    background-size: 4rem;
    text-align:center;
    margin-left: auto; 
    margin-right: auto;
    position:absolute;
}

.bntNode{
    font-family:PingFangSC-Regular;
    font-size:.3rem;
    color:black;
    margin-top: .5rem;
    position:absolute;
    display: block;
}

.divmBG{
    width:100%;
    margin-top:1.2rem;
    display:block;
    position:absolute;
    z-index:1;
    justify-content: center;
    text-align: center;
    height: 6rem;
    background: url("../../../assets/images/robotControl/mBG.png")center no-repeat;
    background-size: 7rem 6rem;
}


.lArrow{
    background: url("../../../assets/images/robotControl/lArrow.png")center no-repeat;
    width: 1rem;
    height: .03rem;
    border: 0px;
    background-size: 1rem .03rem;
    z-index: 3;
    display: block;
    position: absolute;
}

.rArrow{
    background: url("../../../assets/images/robotControl/rArrow.png")center no-repeat;
    width: 1rem;
    height: .03rem;
    border: 0px;
    background-size: 1rem .03rem;
    z-index: 3;
    display: block;
    position: absolute;
}

.cline{
    background: url("../../../assets/images/robotControl/line.png")center no-repeat;
    width: 1rem;
    height: .03rem;
    border: 0px;
    background-size: 1rem .03rem;
    z-index: 3;
    display: block;
    position: absolute;
}
</style>

<script>
import { PopupPicker, XButton } from 'vux'
import { mapGetters } from "vuex";
import { mapState } from "vuex";
export default {
  data() {
    return {
      sendStartAdd:'',
      sendStartID:null,
      sendEndID:null,
      sendStartList:[],
      isSendStart:false,
      getObjText:'',
      isGetObjList:[],
      isGetObj:false,
      getID:null,
      getRoom:'',
      callText:'',
      callList:[],
      isCall:false,
      callId:null,
      roomID:'',
      liftText:'',
      liftList:[],
      liftID:null,
      isLift:false,
      addText:'',
      addList:[],
      isAdd:false,
      addID:null,
      list3:[],
      list1:[],
      list2:[],
      typeText:'客房',
      isType:false,
      typeList:[{name:'客房',value:'客房'},{name:'地点',value:'地点'},{name:'电梯',value:'电梯'}],
      typeId:0,
      title:'',
      isBack:false,
      backText:'',
      backList:[]
    };
  },
  created: function() {
    let state = this.$route.query.v;
    if(state==2){//取物
        this.title='取物';
        $(function(){
            $("#qwDiv").show();
        });
    }else if(state==3){//召唤
        this.title='召唤';
        $(function(){
            $("#zhDiv").show();
        });
    }else if(state==4){//引领
        this.title='引领';
        $(function(){
            $("#ylDiv").show();
        });
    }else if(state==5){//返回充电桩
        this.title='返回充电桩';
        $(function(){
            $("#fhDiv").show();
        });
        this.backText = '小雅';
        this.backList = [{name:'小雅',value:'小雅'}];
        if(localStorage.HOTELID==7){
            this.backText = '小雅1';
            this.backList = [{name:'小雅1',value:'小雅1'},{name:'小雅2',value:'小雅2'}];
        }
    }else {//送物
        this.title='送物';
        $(function(){
            $("#swDiv").show();
        });
    }
    let _this = this;
    let params = {
        limit:0,
        hotelid:localStorage.HOTELID,
        type:3
    };
    //初始化出发地点
    this.$store.dispatch("getPositionList", params).then(res => {
          if (res.code == 0) {
              _this.list3 = res.data.list;
              for(let i=0;i<res.data.list.length;i++){
                  let obj = {name:res.data.list[i].position,value:res.data.list[i].position};
                  _this.sendStartList.push(obj);
                  _this.isGetObjList.push(obj);
                  _this.callList.push(obj);
                  _this.addList.push(obj);
              }
              _this.sendStartAdd = _this.sendStartList[0].name;
              _this.getObjText = _this.isGetObjList[0].name;
              _this.callText = _this.callList[0].name;
              _this.addText = _this.addList[0].name;
              _this.sendStartID = res.data.list[0].id;
              _this.getID = res.data.list[0].id;
              _this.callId = res.data.list[0].id;
              _this.addID = res.data.list[0].id;
          }else{
              _this.$dialog.toast({ mes: res.msg, timeout: 1000 });
          }
    });
    //初始化目的地
    let params1 = {
        limit:0,
        hotelid:localStorage.HOTELID,
        type:1
    };
    this.$store.dispatch("getPositionList", params1).then(res => {
        if (res.code == 0) {
            _this.list1 = res.data.list;
            _this.sendEndID = res.data.list1[0].name;
        }else{
            _this.$dialog.toast({ mes: res.msg, timeout: 1000 });
        }
    });
    //初始化目电梯
    let params2 = {
        limit:0,
        hotelid:localStorage.HOTELID,
        type:2
    };
    this.$store.dispatch("getPositionList", params2).then(res => {
        if (res.code == 0) {
            _this.list2 = res.data.list;
            for(let i=0;i<res.data.list.length;i++){
                let obj = {name:res.data.list[i].position,value:res.data.list[i].position};
                _this.liftList.push(obj);
            }
            _this.liftText = _this.liftList[0].name;
            _this.liftID = res.data.list[0].id;
        }else{
            _this.$dialog.toast({ mes: res.msg, timeout: 1000 });
        }
    });
    $(function() {
      $(".navbar-center").css("marginLeft", 0);
      $("body").css({"background":"white"});
    });
  },
  mounted: function() {
    //一级页面falg
    isHomePage(0);
  },
  components:{
      PopupPicker
  },
  computed: {
    ...mapState({
      language: state => state.language.language
    })
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    sendStartChange:function (changeKey) {
      let _this = this;
      this.sendStartAdd = changeKey[0];
      for(let i=0;i<this.list3.length;i++){
          if(this.list3[i].position === this.sendStartAdd){
              _this.sendStartID = this.list3[i].id;
              break;
          }
      }
    },
    sendObject:function () {
        let _this = this;
        let roomNo = '';
        if($.trim(this.sendEndID)==''){
            this.$dialog.toast({ mes: '请输入房间号', timeout: 1000 });
            return;
        }
        for(let i=0;i<this.list1.length;i++){
            if(this.list1[i].position===this.sendEndID){
                roomNo = this.list1[i].id;
                break;
            }
        }
        if(roomNo===''){
            this.$dialog.toast({ mes: '请输入正确的房间号', timeout: 1000 });
            return;
        }

        let alobj = new alertLanguage();
        let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
        let title = obj.title;
        let msg1 = '是否送物?';
        //判断显示中/英文
        if(localStorage.LANGUAGE!='zh'){
            msg1 = 'Whether to deliver something?';
        }
        let sureBnt = obj.sureBnt;
        let cancelBnt = obj.cancelBnt;
        let dialog = window.YDUI.dialog;
        dialog.confirm(title,msg1, [{
            txt: sureBnt,
            color: false,
            callback: function () {
                let params = {
                    hotelid:localStorage.HOTELID,
                    userid:localStorage.userId,
                    start:_this.sendStartID,
                    dest:roomNo
                };
                _this.$store.dispatch("deliverRobot", params).then(res => {
                    if (res.code == 0) {
                        _this.$dialog.toast({ mes: '指令下达成功!', timeout: 1000 });
                        _this.$router.push({path:'/robotControl'});
                    }else{
                        _this.$dialog.toast({ mes: res.msg, timeout: 1000 });
                    }
                });
            }
        },{
            txt: cancelBnt,
            color: false,
            callback: function () {

            }
        }]);
    },
    getObjChange:function (changeKey) {
      let _this = this;
      this.getObjText = changeKey[0];
      for(let i=0;i<this.list3.length;i++){
          if(this.list3[i].position === this.getObjText){
              _this.getID = this.list3[i].id;
              break;
          }
      }
    },
    getToDo:function (changeKey) {
        let _this = this;
        let roomNo = '';
        if($.trim(this.getRoom)==''){
            this.$dialog.toast({ mes: '请输入房间号', timeout: 1000 });
            return;
        }
        for(let i=0;i<this.list1.length;i++){
            if(this.list1[i].position===this.getRoom){
                roomNo = this.list1[i].id;
                break;
            }
        }
        if(roomNo===''){
            this.$dialog.toast({ mes: '请输入正确的房间号', timeout: 1000 });
            return;
        }

        let alobj = new alertLanguage();
        let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
        let title = obj.title;
        let msg1 = '是否取物?';
        //判断显示中/英文
        if(localStorage.LANGUAGE!='zh'){
            msg1 = 'Whether to pick things up?';
        }
        let sureBnt = obj.sureBnt;
        let cancelBnt = obj.cancelBnt;
        let dialog = window.YDUI.dialog;
        dialog.confirm(title,msg1, [{
            txt: sureBnt,
            color: false,
            callback: function () {
                let params = {
                    start:roomNo,
                    dest:_this.getID
                };
                _this.$store.dispatch("robotSend", params).then(res => {
                    if (res.code == 0) {
                        _this.$dialog.toast({ mes: '指令下达成功!', timeout: 1000 });
                        _this.$router.push({path:'/robotControl'});
                    }else{
                        _this.$dialog.toast({ mes: res.msg, timeout: 1000 });
                    }
                });
            }
        },{
            txt: cancelBnt,
            color: false,
            callback: function () {

            }
        }]);
    },
    callChange:function (changeKey) {
      let _this = this;
      this.callText = changeKey[0];
      for(let i=0;i<this.list3.length;i++){
          if(this.list3[i].position === this.callText){
              _this.callId = this.list3[i].id;
              break;
          }
      }
    },
    backChange:function (changeKey) {
      let _this = this;
      this.backText = changeKey[0];
    },
    callToDo:function (changeKey) {
        let _this = this;

        let alobj = new alertLanguage();
        let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
        let title = obj.title;
        let msg1 = '是否召唤?';
        //判断显示中/英文
        if(localStorage.LANGUAGE!='zh'){
            msg1 = 'Whether to summon?';
        }
        let sureBnt = obj.sureBnt;
        let cancelBnt = obj.cancelBnt;
        let dialog = window.YDUI.dialog;
        dialog.confirm(title,msg1, [{
            txt: sureBnt,
            color: false,
            callback: function () {
                let params = {
                    hotelid:localStorage.HOTELID,
                    userid:localStorage.userId,
                    dest:_this.callId
                };
                _this.$store.dispatch("callRobot", params).then(res => {
                    if (res.code == 0) {
                        _this.$dialog.toast({ mes: '指令下达成功!', timeout: 1000 });
                        _this.$router.push({path:'/robotControl'});
                    }else{
                        _this.$dialog.toast({ mes: res.msg, timeout: 1000 });
                    }
                });
            }
        },{
            txt: cancelBnt,
            color: false,
            callback: function () {

            }
        }]);
    },
    liftChange:function (changeKey) {
      let _this = this;
      this.liftText = changeKey[0];
      for(let i=0;i<this.list2.length;i++){
          if(this.list2[i].position === this.liftText){
              _this.liftID = this.list2[i].id;
              break;
          }
      }
    },
    addChange:function (changeKey) {
      let _this = this;
      this.addText = changeKey[0];
      for(let i=0;i<this.list3.length;i++){
          if(this.list3[i].position === this.addText){
              _this.addID = this.list3[i].id;
              break;
          }
      }
    },
    typeChange:function (changeKey) {
      this.typeText = changeKey[0];
      if(this.typeText =='客房'){
          this.typeId = 0;
          this.roomID = "";
      }else if(this.typeText =='地点'){
          this.typeId = 1;
      }else{
          this.typeId = 2;
      }
    },
    addToDo:function (changeKey) {
        let _this = this;
        let params = {
            hotelid:localStorage.HOTELID,
            userid:localStorage.userId,
            dest:'',
            type:'guide'
        };
        if(this.typeId==1){
            params.dest = this.addID;
        }else if(this.typeId==2){
            params.dest = this.liftID;
        }else{
            let roomNo = '';
            if($.trim(this.roomID)==''){
                this.$dialog.toast({ mes: '请输入房间号', timeout: 1000 });
                return;
            }
            for(let i=0;i<this.list1.length;i++){
                if(this.list1[i].position===this.roomID){
                    roomNo = this.list1[i].id;
                    break;
                }
            }
            if(roomNo===''){
                this.$dialog.toast({ mes: '请输入正确的房间号', timeout: 1000 });
                return;
            }
            params.dest = roomNo;
        }

        let alobj = new alertLanguage();
        let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
        let title = obj.title;
        let msg1 = '是否引领?';
        //判断显示中/英文
        if(localStorage.LANGUAGE!='zh'){
            msg1 = 'Whether to lead?';
        }
        let sureBnt = obj.sureBnt;
        let cancelBnt = obj.cancelBnt;
        let dialog = window.YDUI.dialog;
        dialog.confirm(title,msg1, [{
            txt: sureBnt,
            color: false,
            callback: function () {
                //初始化出发地点
                _this.$store.dispatch("callRobot", params).then(res => {
                    if (res.code == 0) {
                        _this.$dialog.toast({ mes: '指令下达成功!', timeout: 1000 });
                        _this.$router.push({path:'/robotControl'});
                    }else{
                        _this.$dialog.toast({ mes: res.msg, timeout: 1000 });
                    }
                });
            }
        },{
            txt: cancelBnt,
            color: false,
            callback: function () {

            }
        }]);
    },
    backToDo:function () {
        let productid = '';
        if(localStorage.HOTELID==1){
            productid = 'HOTQY00SZ201704180503006';
        }else if(localStorage.HOTELID==7){
            productid = 'HOTQY00SZ201806281006015';
            if(this.backText=='小雅2'){
                productid = 'HOTQY00SZ201704180503025';
            }
        }else if(localStorage.HOTELID==6){
            productid = 'HOTQY00SZ201704180503006';
        }else if(localStorage.HOTELID==21){
            productid = 'HOTQY00SZ201802050905032';
        }else if(localStorage.HOTELID==22){
            productid = 'HOTQY00SZ201806281006066';
        }else if(localStorage.HOTELID==24){
            productid = 'HOTQY00SZ201806281006066';
        }else if(localStorage.HOTELID==28){
            productid = 'HOTQY00SZ201806281006043';
        }else if(localStorage.HOTELID==30){
            productid = 'HOTQY00SZ201806281007039';
        }
        if(productid == ''){
            this.$dialog.toast({ mes: '该物业没有机器人服务!', timeout: 1000 });
            return;
        }
        let _this = this;
        let params = {
            userid:localStorage.userId,
            hotelid:localStorage.HOTELID,
            productid:productid
        };
        //初始化出发地点
        this.$store.dispatch("robotBack", params).then(res => {
            if (res.code == 0) {
            }else{
                _this.$dialog.toast({ mes: res.msg, timeout: 1000 });
            }
        });
    }
  },
  watch:{
    isSendStart(val, oldVal){
        if(val){
            $(document.body).css({
                "overflow-y":"hidden"
            });
        }else{
            $(document.body).css({
                "overflow-y":"auto"
            });
        }
    },
    isGetObj(val, oldVal){
        if(val){
            $(document.body).css({
                "overflow-y":"hidden"
            });
        }else{
            $(document.body).css({
                "overflow-y":"auto"
            });
        }
    },
    isCall(val, oldVal){
        if(val){
            $(document.body).css({
                "overflow-y":"hidden"
            });
        }else{
            $(document.body).css({
                "overflow-y":"auto"
            });
        }
    },
    isType(val, oldVal){
        if(val){
            $(document.body).css({
                "overflow-y":"hidden"
            });
        }else{
            $(document.body).css({
                "overflow-y":"auto"
            });
        }
    },
    isAdd(val, oldVal){
        if(val){
            $(document.body).css({
                "overflow-y":"hidden"
            });
        }else{
            $(document.body).css({
                "overflow-y":"auto"
            });
        }
    },
    isLift(val, oldVal){
        if(val){
            $(document.body).css({
                "overflow-y":"hidden"
            });
        }else{
            $(document.body).css({
                "overflow-y":"auto"
            });
        }
    }
  }
};
</script>
