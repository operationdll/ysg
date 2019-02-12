//Author:Daniel Duan
//Date:06/07/2018
//设置分页对象
window.Pagination = function(target, options) {
    if (!target) {
        alert('分页初始化错误:找不到target!');
        return null;
    }
    this.target = target;//初始化分页目标对象
    this.options = options || {};//初始化参数
    this.lang = this.options.lang || 'zh';//初始化语言
    this.page = this.options.page || 1;//初始化页数
    this.limit = this.options.limit || 10;//初始化每页显示数量
    this.total = this.options.total || 0;//初始化总数量
    this.pages = Math.ceil(this.total / this.limit);//初始化总页数
    this.pageCallBack = this.options.pageCallBack || function(){};//初始化回调函数
    //初始化分页内容
    if(this.pages>1){
        let prevPageNode = '第';
        let nextPageNode = '页';
        if(this.lang!='zh'){
            prevPageNode = 'Page';
            nextPageNode = '';
        }
        let contentHtml = '';
        for(let i=0;i<this.pages;i++){
            contentHtml = contentHtml + 
                            `
                                <option value="`+(i+1)+`">`+prevPageNode+(i+1)+nextPageNode+`</option>
                            `;
        }
        let targetHtml=`<div style="width:100%;">
                            <center>
                                <div class="pagination">
                                    <select>
                                    `
                                    +contentHtml+
                                    `
                                    </select>
                                </div>
                            </center>
                        </div>`;
        $(this.target).html(targetHtml);
        //添加点击事件
        let _this = this;
        //选择页数
        $(this.target).find("select").change(function(){
            _this.page = Number($(this).val());
            _this.pageCallBack(_this.page);
        });
    }
  };
  //设置分页方法
  // Pagination.prototype = {
  //     pageCallBack: function(callback) {
  //         //callback(this.page);
  //         alert(this.page);
  //     }
  // };