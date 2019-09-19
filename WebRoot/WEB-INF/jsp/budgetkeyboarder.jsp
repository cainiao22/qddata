<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta HTTP-EQUIV="pragma" content="no-cache">
    <meta HTTP-EQUIV="Cache-Control" content="no-cache, must-revalidate">
    <meta HTTP-EQUIV="expires" content="0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="common/importCss2.jsp"></c:import>
    <link rel="stylesheet" href="./assets/plugins/jiashicang/jiashicangform.css">
    <link rel="stylesheet" href="./assets/plugins/jiashicang/iconfont.css">
    <style>
        .pagination > li > a,
        .pagination > li > span {
            position: relative;
            float: left;
            padding: 6px 12px;
            line-height: 1.42857;
            text-decoration: none;
            color:#c7c9cc;
            background-color: #272c33;
            border: 1px solid #6a6f76;
            margin-left: -1px;
        }
        .pagination > .active > a,
        .pagination > .active > span,
        .pagination > .active > a:hover,
        .pagination > .active > span:hover,
        .pagination > .active > a:focus,
        .pagination > .active > span:focus {
            background-color: #383f48;
            border-color: #6a6f76;
        }

        .block{
            /* padding-top: 0%;
             padding-right: 84%;*/
        }
        #form-app{
            background: RGB(29,44,51);
            width: 100%;
            padding: 20px 2%;
            overflow: auto;
        }
    </style>
</head>
<!-- END HEAD -->
<body class="fix-header fix-sidebar card-no-border">
<div class="preloader">
    <svg class="circular" viewBox="25 25 50 50">
        <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10"/>
    </svg>
</div>
<div id="main-wrapper">
    <c:import url="common/header2.jsp"></c:import>
    <!-- End Topbar header -->
    <aside class="left-sidebar">
        <!-- Sidebar scroll-->
        <div class="scroll-sidebar">
            <!-- Sidebar navigation-->
            <nav class="sidebar-nav">
                <ul id="sidebarnav">
                    <c:import url="common/sidebarMenu2.jsp"></c:import>
                </ul>
            </nav>
            <!-- End Sidebar navigation -->
        </div>
        <!-- End Sidebar scroll-->
    </aside>
    <!-- Page wrapper  -->
    <div class="page-wrapper">
        <!-- Container fluid  -->
        <div class="container-fluid">
            <!-- Bread crumb and right sidebar toggle -->
            <div class="row page-titles">
                <div class="col-md-6 col-8 align-self-center">
                    <h3 class="text-themecolor m-b-0 m-t-0">${ sidebar_menu.parentName}</h3>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="javascript:void(0)">${ sidebar_menu.parentName}</a></li>
                        <li class="breadcrumb-item active">${ sidebar_menu.name} </li>
                    </ol>
                </div>
            </div>


            <div id="form-app">
                <form-title :receive="receive" :getsave="getsave" :setmodifyele="setmodifyele" :deletemodify="deletemodify" :tatolshow="tatolshow"></form-title>

                <div class="form-tab">
                    <div class="fixedmonth">
                        <table border="1" width="100%" height="100%" borderColor="#ccc">
                            <tr><td v-bind:style="{color: '#1D2C33', height: screenHeight}">月份</td></tr>
                            <tr><td v-bind:style="{height: screenHeight}">月份</td></tr>
                            <tr v-for='(txt,i) in tabley' v-bind:style="{height: screenHeight}">
                                <td>{{txt}}</td>
                            </tr>
                        </table>

                    </div>
                    <div class="form-con">
                        <table border="1" :width="adaptionwidth()" height="100%" borderColor="#ccc" ref="table2">
                            <tr v-if="yetaiselected == 0" v-bind:style="{color: '#00FFFF'}">
                                <td></td>
                                <td v-for='(txt,i) in tablex1' v-bind:style="{fontWeight: (i==0)?700:300, color: i == 7?'#FF00CC':'#00FFFF'}" :colspan="(i == 0 || i == 5)?3:2">{{txt}}</td>
                            </tr>
                            <tr v-if="yetaiselected == 0">
                                <td></td>
                                <td v-for='(txt,i) in tablex4' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex3' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex3' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex3' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex3' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex4' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                            </tr>
                            <%----%>
                            <tr v-if="yetaiselected != 0" v-bind:style="{color: '#00FFFF'}">
                                <td></td>
                                <td v-for='(txt,i) in tatolshow != 1?tablex2:tablex9' v-bind:style="{fontWeight: (i==0)?700:300, color: i == 7?'#FF00CC':'#00FFFF'}" :colspan="yetaiselected >= 5?3:2">{{txt}}</td>
                            </tr>
                            <tr v-if="yetaiselected < 5 && yetaiselected != 0">
                                <td></td>
                                <td v-for='(txt,i) in tablex3' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex3' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex3' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex3' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex3' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex3' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex3' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-if="tatolshow != 1" v-for='(txt,i) in tablex3' v-bind:style="{color: '#FF00CC'}">{{txt}}</td>
                            </tr>
                            <%--<tr v-if="yetaiselected == 3">--%>
                                <%--<td></td>--%>
                                <%--<td v-for='(txt,i) in tablex10' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>--%>
                                <%--<td v-for='(txt,i) in tablex10' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>--%>
                                <%--<td v-for='(txt,i) in tablex10' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>--%>
                                <%--<td v-for='(txt,i) in tablex10' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>--%>
                                <%--<td v-for='(txt,i) in tablex10' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>--%>
                                <%--<td v-for='(txt,i) in tablex10' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>--%>
                                <%--<td v-for='(txt,i) in tablex10' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>--%>
                                <%--<td v-if="tatolshow != 1" v-for='(txt,i) in tablex10' v-bind:style="{color: '#FF00CC'}">{{txt}}</td>--%>
                            <%--</tr>--%>
                            <tr v-if="yetaiselected >= 5">
                                <td></td>
                                <td v-for='(txt,i) in tablex4' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex4' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex4' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex4' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex4' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex4' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-for='(txt,i) in tablex4' v-bind:style="{color: txt == '归档收入'?'#00FFFF':''}">{{txt}}</td>
                                <td v-if="tatolshow != 1" v-for='(txt,i) in tablex4' v-bind:style="{color: '#FF00CC'}">{{txt}}</td>
                            </tr>

                            <tr v-for='(txt,i) in tabley' v-if="yetaiselected == 0" ref="tr">
                                <td></td>
                                <td v-for='(txt,i) in tablex4'></td>
                                <td v-for='(txt,i) in tablex3'></td>
                                <td v-for='(txt,i) in tablex3'></td>
                                <td v-for='(txt,i) in tablex3'></td>
                                <td v-for='(txt,i) in tablex3'></td>
                                <td v-for='(txt,i) in tablex4'></td>
                            </tr>
                            <tr v-for='(txt,i) in tabley' v-if="yetaiselected < 5 && yetaiselected != 0" ref="tr">
                                <td></td>
                                <td v-for='txt in tablex3'></td>
                                <td v-for='txt in tablex3'></td>
                                <td v-for='txt in tablex3'></td>
                                <td v-for='txt in tablex3'></td>
                                <td v-for='txt in tablex3'></td>
                                <td v-for='txt in tablex3'></td>
                                <td v-for='txt in tablex3'></td>
                                <td v-if="tatolshow != 1" v-for='txt in tablex3'></td>
                            </tr>
                            <%--<tr v-for='(txt,i) in tabley' v-if="yetaiselected == 3" ref="tr">--%>
                                <%--<td></td>--%>
                                <%--<td v-for='(txt,i) in tablex10'></td>--%>
                                <%--<td v-for='(txt,i) in tablex10'></td>--%>
                                <%--<td v-for='(txt,i) in tablex10'></td>--%>
                                <%--<td v-for='(txt,i) in tablex10'></td>--%>
                                <%--<td v-for='(txt,i) in tablex10'></td>--%>
                                <%--<td v-for='(txt,i) in tablex10'></td>--%>
                                <%--<td v-for='(txt,i) in tablex10'></td>--%>
                                <%--<td v-if="tatolshow != 1" v-for='(txt,i) in tablex10'></td>--%>
                            <%--</tr>--%>
                            <tr v-for='(txt,i) in tabley' v-if="yetaiselected >= 5" ref="tr">
                                <td></td>
                                <td v-for='(txt,i) in tablex4'></td>
                                <td v-for='(txt,i) in tablex4'></td>
                                <td v-for='(txt,i) in tablex4'></td>
                                <td v-for='(txt,i) in tablex4'></td>
                                <td v-for='(txt,i) in tablex4'></td>
                                <td v-for='(txt,i) in tablex4'></td>
                                <td v-for='(txt,i) in tablex4'></td>
                                <td v-if="tatolshow != 1" v-for='(txt,i) in tablex4'></td>
                            </tr>
                        </table>
                    </div>

                    <div class="savesuccess" ref="savesuccess">保存成功</div>
                </div>

                <div v-if="maskshow == 1" class="form-mask" @click="dragorgropnoeffect" ref="mask">
                    <div class="form-tip" ref="tips">
                        <div class="tip-tit" @mousedown="tipsdragordrop">
                            <span class="iconfont icon-warning"></span>
                            <span>错误提示</span>
                            <span @click="closetips">×</span>
                        </div>
                        <div class="tip-con">
                            <div>
                                <span>数据录入有误！违反以下原则:</span>
                            </div>
                            <div>
                                <span v-if="errornumber == 1">- 录入数据非数字</span>
                                <span v-if="errornumber2 == 1">- 查验处非0</span>
                            </div>
                            <div>
                                <button @click="closetips">确定</button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

            <template id="form-tit">
                <div class="form-tit">
                    <div>
                        <div>
                            <span>业态:</span>
                            <select @change="yetaichoice($event)" name="" id="">
                                <option :selected="watchyetaichange == 1?(yetaivalue == i?'selected':''):(yetaiselected == i?'selected':'')" v-bind:value="i" v-for='(txt,i) in yetaiselect'>{{txt}}</option>
                            </select>
                        </div>
                        <div>
                            <span>交易类型:</span>
                            <select @change="transactionchioce($event)" name="" id="">
                                <option :selected="watchyetaichange == 1?(transactionvalue == i?'selected':''):(watchtransactionchange == 1?(transactionvalue == i?'selected':''):(transactionselected == i?'selected':''))" v-bind:value="i" v-for='(txt,i) in transactionselect'>{{txt}}</option>
                            </select>
                        </div>
                        <div>
                            <span>年份:</span>
                            <select @change="yearchioce($event)" name="" id="">
                                <option :selected="yearselected == txt?'selected': ''" v-bind:value="txt" v-for='(txt,i) in yearselect'>{{txt}}</option>
                            </select>
                        </div>
                        <button @click="query">查询</button>
                        <button v-show="tatolshow != 1" @click="pointsavebutton" v-bind:style="{display: 'block'}">保存</button>
                        <button v-show="tatolshow != 1" @click="getmodify" ref="modify">修改</button>
                    </div>
                    <div v-if="whethergiveup == 1" class="giveupmask">
                        <div class="giveupframe">
                            <div class="tip-tit">
                                <span>操作提示</span>
                                <span @click="nogiveupmodify">×</span>
                            </div>
                            <div class="tip-con">
                                <div>
                                    <span>是否放弃当前修改？</span>
                                </div>
                                <div>
                                    <button @click="giveupmodify">是</button>
                                    <button @click="nogiveupmodify">否</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </template>

            <!-- footer -->
            <c:import url="common/footer.jsp"></c:import>
            <!-- End footer -->
        </div>
        <!-- End Page wrapper  -->
    </div>

    <!-- End Wrapper -->
    <c:import url="common/importJs2.jsp"></c:import>

    <script src="./assets/plugins/jiashicang/vue.js" type="text/javascript"></script>
    <script src="./assets/plugins/jiashicang/vue-resource.js" type="text/javascript"></script>
    <script type="text/javascript">
        var currUrl =  "http://"+window.location.host;
        Vue.component('form-title', {
            template: '#form-tit',
            props: ['receive', 'getsave', 'setmodifyele', 'deletemodify', 'tatolshow'],
            data: function () {
                return {
                    yetaiselect: ['全部', '400', '千丁云', '龙湖专项', '物联网', '生活服务'],
                    transactionselect1: ['全部', '龙湖', '外拓'],
                    transactionselect2: ['合计', '龙湖', '外拓-片区'],
                    transactionselect3: ['合计', '外拓'],
                    transactionselect4: ['合计', '龙湖', '外拓-片区', '外拓-非片区', '狄耐克'],
                    transactionselect5: ['合计', '龙湖', '外拓', '周期购'],
                    transactionselect6: ['合计'],
                    transactionselect7: ['全部'],
                    transactionselect: [],
                    yearselect: [],
                    yearselected: (new Date()).getFullYear(),
                    yetaiselected: 0,
                    transactionselected: 0,
                    yetaivalue: 0,
                    transactionvalue: 0,
                    yearvalue: (new Date()).getFullYear(),
                    whethergiveup: 0,
                    implementfun: 0,
                    judgeclickquery: 0,
                    watchtransactionchange: 0,
                    watchyetaichange: 0,
                }
            },
            methods: {
                yetaichoice:function (event) {
                    var e = event ? event: window.event;
                    e.stopPropagation();
                    var shu = e.target.value;
                    this.yetaivalue = shu;
                    switch(shu){
                        case '0':
                            this.transactionselect = this.transactionselect7;
                            this.transactionselected = 0;
                            break;
                        case '1':
                            this.transactionselect = this.transactionselect2;
                            this.transactionvalue = 0;
                            break;
                        case '2':
                            this.transactionselect = this.transactionselect6;
                            this.transactionvalue = 0;
                            break;
                        case '3':
                            this.transactionselect = this.transactionselect6;
                            this.transactionvalue = 0;
                            break;
                        case '4':
                            this.transactionselect = this.transactionselect4;
                            this.transactionvalue = 0;
                            break;
                        case '5':
                            this.transactionselect = this.transactionselect5;
                            this.transactionvalue = 0;
                            break;
                        default:
                            this.transactionselect = this.transactionselect7;
                            this.transactionvalue = 0;
                            break;
                    };
                },
                transactionchioce: function(event) {
                    var e = event ? event: window.event;
                    e.stopPropagation();
                    this.transactionvalue = e.target.value;
                },
                years: function () {

                    var year = 2014, legs = (new Date()).getFullYear() - 2014 + 6;
                    var arr = [];
                    for(var i=0; i< legs; i++){
                        arr.push(year);
                        year++;
                    }
                    this.yearselect = arr;
                },
                yearchioce: function (event) {
                    var e = event ? event: window.event;
                    e.stopPropagation();
                    this.yearvalue = e.target.value;
                },
                query: function (e) {
                    var e = e ? e: window.event;
                    e.stopPropagation();
                    if(this.$refs.modify){
                        if(this.$refs.modify.innerHTML == '放弃修改'){
//                            this.$refs.modify.innerHTML = '修改';
//                            this.$refs.modify.style.background = '#199ED8';
//                            this.deletemodify();
                            this.judgeclickquery = 1;
                            this.whethergiveup = 1;
                            this.implementfun = 2;
                        }else{
                            this.yetaiselected = this.yetaivalue;
                            this.changeselecttype(this.yetaiselected);
                            this.transactionselected = this.transactionvalue;
                            this.yearselected = this.yearvalue;
                            this.watchtransactionchange = 0;
                            this.watchyetaichange = 0;
                            var that = this;
                            setTimeout(function(){
                                that.receive(that.yetaiselected, that.transactionselected, that.yearselected);
                            }, 100)
                        }
                    }else{
                        this.yetaiselected = this.yetaivalue;
                        this.changeselecttype(this.yetaiselected);
                        this.transactionselected = this.transactionvalue;
                        this.yearselected = this.yearvalue;
                        this.watchtransactionchange = 0;
                        this.watchyetaichange = 0;

                        var that = this;
                        setTimeout(function(){
                            that.receive(that.yetaiselected, that.transactionselected, that.yearselected);
                        }, 100)
                    }
//                    this.yetaiselected = this.yetaivalue;
//                    this.receive(this.yetaiselected, this.transactionselected, this.yearselected);
                },
                getmodify: function (e) {
                    var e = e ? e: window.event;
                    e.stopPropagation();
                    if(this.$refs.modify.innerHTML == '放弃修改'){
                        this.whethergiveup = 1;
                        this.implementfun = 0;
                    }else{
                        this.whethergiveup = 0;
                        this.$refs.modify.innerHTML = '放弃修改';
                        this.$refs.modify.style.background = '#FF0066';
                        this.setmodifyele()
                    }
                },
                pointsavebutton: function (e) {
                    var e = e ? e: window.event;
                    e.stopPropagation();
                    if(this.$refs.modify){
                        if(this.$refs.modify.innerHTML == '放弃修改'){
                            this.$refs.modify.innerHTML = '修改';
                            this.$refs.modify.style.background = '#199ED8';
                            this.getsave();
                        }
                    }

                },
                //是否放弃修改
                giveupmodify: function(){
                    this.whethergiveup = 0;
                    this.$refs.modify.innerHTML = '修改';
                    this.$refs.modify.style.background = '#199ED8';
                    if(this.implementfun == 0){
                        this.deletemodify();
                    }else if(this.implementfun == 1){
                        this.getsave();
                    }else if(this.implementfun == 2){
                        this.deletemodify();
                        this.yetaiselected = this.yetaivalue;
                        this.changeselecttype(this.yetaiselected);

                        this.transactionselected = this.transactionvalue;
                        this.yearselected = this.yearvalue;
                        this.watchtransactionchange = 0;
                        this.watchyetaichange = 0;

                        var that = this;
                        setTimeout(function(){
                            that.receive(that.yetaiselected, that.transactionselected, that.yearselected);
                        }, 100)
                    }

                },
                nogiveupmodify: function(){
                    this.whethergiveup = 0;

                    if(this.judgeclickquery == 1){
                        this.judgeclickquery = 0;

//                        this.transactionvalue = 0;

                        this.changeselecttype(this.yetaiselected);
                    }
                    this.watchtransactionchange = 0;
                    this.watchyetaichange = 0;
                },
                //选择选项的类型
                changeselecttype: function(shu){
                    switch(shu){
                        case '0':
                            this.transactionselect = this.transactionselect7;
                            this.transactionselected = 0;
                            break;
                        case '1':
                            this.transactionselect = this.transactionselect2;
                            break;
                        case '2':
                            this.transactionselect = this.transactionselect6;
                            break;
                        case '3':
                            this.transactionselect = this.transactionselect6;
                            break;
                        case '4':
                            this.transactionselect = this.transactionselect4;
                            break;
                        case '5':
                            this.transactionselect = this.transactionselect5;
                            break;
                        default:
                            this.transactionselect = this.transactionselect7;
                            break;
                    };
                }
            },
            created: function(){
                this.transactionselect = this.transactionselect7;
                this.years();
                var myDate = new Date();
                this.yearselected = myDate.getFullYear();

                this.receive(this.yetaiselected, this.transactionselected, myDate.getFullYear());
            },
            watch:{
                transactionvalue: function(val, oldVal){//普通的watch监听
//                    console.log(this.watchtransactionchange)
                    this.watchtransactionchange = 1;
                },
                yetaivalue: function(val, oldVal){//普通的watch监听
//                    console.log(this.watchyetaichange, 4444444)
                    this.watchyetaichange = 1;
                },
            }
        });

        new Vue({
            el: '#form-app',
            data: {
                yetaiselected: 0,
                transactionselected: 0,
                yearselected: 0,
                tabley: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月', '合计'],
                tablex1: ['全部-月预算目标', '400', '千丁云', '龙湖专项','物联网', '生活服务'],
                tablex2: ['业态-月预算目标', 'week1', 'week2', 'week3', 'week4', 'week5', 'week6', '查验'],
                tablex3: ['归档收入', '归档毛利'],
                tablex4: ['GSV', '归档收入', '归档毛利'],
                tablex5: ['月份', '龙湖-月预算目标', '千丁云', '龙湖专项', '物联网', '400'],
                tablex6: ['外拓-月预算目标', '400', '千丁云', '龙湖专项', '广告', '物联网', '生活服务'],
                tablex8: ['片区', '非片区'],
                tablex9: ['业态-月预算目标', 'week1', 'week2', 'week3', 'week4', 'week5', 'week6'],
                tablex10: ['广告点位', '归档收入', '归档毛利'],
                errornumber: 0,
                errornumber2: 0,
                errorelement: '',
                recorddata: [],
                tatolshow: 0,
                maskshow: 0,
                index: 0,
                screenHeight: 0,
            },
            methods: {
                receive: function (yetaiselected, transactionselected, yearselected) {
//                    console.log(yetaiselected, transactionselected, yearselected)
                    this.yetaiselected = yetaiselected;
                    this.transactionselected = transactionselected;
                    this.yearselected = yearselected;
                    this.resetvalue();

                    if(yetaiselected != 0){
                        this.getquery();
                    }else{
                        this.getqueryall();
                    }
                },
                getquery: function(){

//                    if(this.yearselected != 0){
                    var params = this.getIdentification();

                    var time1 = Date.parse(new Date());

                    this.$http.get(currUrl+'/budgetkeyboarderlist?first_format_en='+params.yetai+'&second_format_en='+params.type+'&year='+this.yearselected+'&second_format_total='+params.typeall+'&t='+time1).then(function(res){
                        if(res.data && res.data.data != '' && res.data.data.length != 0){

                            var list = this.Transformationdata1(res.data.data);

                            this.recorddata = list;
                            var trs = this.$refs.tr;

                            for(var i=0; i<trs.length-1; i++){
                                var childs = trs[i].children;
                                var item = list[i];
                                for(var j=0; j<7; j++){
                                    if(item[j]){
                                        if(childs.length < 18){
                                            if(j ==0){
                                                childs[j*2+1].innerHTML = item[j].shouru_month?this.fmoney(item[j].shouru_month):'';
                                                childs[j*2+2].innerHTML = item[j].maoli_month?this.fmoney(item[j].maoli_month):'';
                                            }else{
                                                childs[j*2+1].innerHTML = item[j].shouru_week?this.fmoney(item[j].shouru_week):'';
                                                childs[j*2+2].innerHTML = item[j].maoli_week?this.fmoney(item[j].maoli_week):'';
                                            }
                                        }else{
                                            if(j == 0){
                                                childs[j*3+1].innerHTML = item[j].gsv_month?this.fmoney(item[j].gsv_month):'';
                                                childs[j*3+2].innerHTML = item[j].shouru_month?this.fmoney(item[j].shouru_month):'';
                                                childs[j*3+3].innerHTML = item[j].maoli_month?this.fmoney(item[j].maoli_month):'';
                                            }else{
                                                childs[j*3+1].innerHTML = item[j].gsv_week?this.fmoney(item[j].gsv_week):'';
                                                childs[j*3+2].innerHTML = item[j].shouru_week?this.fmoney(item[j].shouru_week):'';
                                                childs[j*3+3].innerHTML = item[j].maoli_week?this.fmoney(item[j].maoli_week):'';
                                            }
                                        }
                                    }
                                }
                            }

                            this.tatalvalue(trs, 2);
                        }else{
                            this.recorddata = [];
                            var trs = this.$refs.tr;
                            this.tatalvalue(trs, 2);
                        }
                    })
//                    }

                },
                //查询全部数据
                getqueryall: function(){
                    var params = this.getIdentification();

                    this.$http.get(this.getlocation() + 'dataApiQuery/qintianjian_jiashicang_yusuan?year='+this.yearselected).then(function(res){
                        if(res.data && res.data.data != '' && res.data.data.length != 0){
                            var alldatas = [];
                            for(var m=1; m<=12; m++){
                                var list = this.Transformationdata3([''+(m<10?('0'+m):m)], res.data.data, 'month');
                                alldatas.push(this.Transformationdata3(["all", "400", "QDY", "LHZX", "WLW", "SHFW"], list, 'first_format_en', true));
                            }
                            var trs = this.$refs.tr;

                            for(var i=0; i<trs.length-1; i++){
                                var childs = trs[i].children;
                                var item = alldatas[i];
                                for(var j=0; j<6; j++){
                                    if(item[j]){
                                        if(j == 0){
                                            childs[1].innerHTML = item[j].gsv_month?this.fmoney(item[j].gsv_month):'';
                                            childs[2].innerHTML = item[j].shouru_month?this.fmoney(item[j].shouru_month):'';
                                            childs[3].innerHTML = item[j].maoli_month?this.fmoney(item[j].maoli_month):'';
                                        }else if(j == 5){
                                            childs[12].innerHTML = item[j].gsv_month?this.fmoney(item[j].gsv_month):'';
                                            childs[13].innerHTML = item[j].shouru_month?this.fmoney(item[j].shouru_month):'';
                                            childs[14].innerHTML = item[j].maoli_month?this.fmoney(item[j].maoli_month):'';
                                        }else{
                                            childs[j*2+2].innerHTML = item[j].shouru_month?this.fmoney(item[j].shouru_month):'';
                                            childs[j*2+3].innerHTML = item[j].maoli_month?this.fmoney(item[j].maoli_month):'';
                                        }
                                    }
                                }
                            }

                            this.tatalvalue(trs, 2);
                        }else{
                            this.recorddata = [];
                            var trs = this.$refs.tr;
                            this.tatalvalue(trs, 2);
                        }
                    })
                },
                //转换数据
                Transformationdata1: function(res){
                    var arrall = [];
                    for(var i=1; i<=12; i++){
                        var arr = [];
                        for(var j=0; j<res.length; j++){
                            if(Number(res[j].month) == i){
                                arr.push(res[j]);
                            }
                        }
                        arrall.push(this.Transformationdata2(arr));
                    }
                    return arrall;
                },
                Transformationdata2: function(res){
                    var arr = [];
                    var index = 0;
                    for(var i=0; i<=6; i++){
                        for(var j=0; j<res.length; j++){
                            if(Number(res[j].week) == i){
                                index = 1;
                                arr.push(res[j]);
                            }
                        }
                        if(index != 1){
                            arr.push([]);
                        }else{
                            index = 0;
                        }
                    }
                    return arr;
                },
                Transformationdata3: function(canzhao, res, ming, kong){
                    var arr = [], judge=0;

                    for(var i=0; i<canzhao.length; i++){
                        for(var j=0; j<res.length; j++){
                            if(res[j][ming] == canzhao[i]){
                                arr.push(res[j]);
                                judge++;
                            }
                        }
                        if(judge == 0){
                            if(kong){
                                arr.push('');
                            }
                        }else{
                            judge=0;
                        }
                    }
                    return arr;
                },
                getsave: function(){

                    var trs = this.$refs.tr;
                    for(var i=0; i<trs.length; i++){
                        var childs = trs[i].children;
                        for(var j=1; j<childs.length; j++){
                            this.highlightshow(childs[j], '');
                            if(i<trs.length-1){
                                if(this.yetaiselected < 5 && this.yetaiselected != 0){
                                    if(j<15){
                                        var inp = childs[j].querySelector('input');
                                        if(inp){
                                            childs[j].innerHTML = inp.value;
                                        }
                                    }
                                }else if(this.yetaiselected >= 5){
                                    if(j<22){
                                        var inp = childs[j].querySelector('input');
                                        if(inp){
                                            childs[j].innerHTML = inp.value;
                                        }
                                    }
                                }
                            }

                        }
                    }
                    this.tatalvalue(trs, 2);
                    this.Inspectionnotforzero();

                    var list = this.recorddata;
                    var arr=[];
                    var judgeupdate = 0;
                    for(var i=0; i<trs.length-1; i++){
                        var childs = trs[i].children;
                        var item = list[i];
                        for(var j=0; j<7; j++){
                            var obj = {
                                "id": "",
                                "year": "",
                                "first_format": "",
                                "first_format_en": null,
                                "second_format": "",
                                "second_format_en": null,
                                "third_format": null,
                                "third_format_en": null,
                                "month": "0",
                                "shouru_month": 0,
                                "maoli_month": 0,
                                "gsv_month": 0,
                                "week": 0,
                                "shouru_week": 0,
                                "maoli_week": 0,
                                "gsv_week": 0,
                                "data_dt": ""
                            }
                            this.index = j
                            if(childs.length < 18){
                                if(j == 0){
                                    obj.shouru_month = childs[j*2+1].innerHTML != ''?childs[j*2+1].innerHTML:0;
                                    obj.maoli_month = childs[j*2+2].innerHTML != ''?childs[j*2+2].innerHTML:0;
                                    judgeupdate = 1;
                                }else{
                                    obj.maoli_week = childs[j*2+2].innerHTML != ''?childs[j*2+2].innerHTML:0;
                                    obj.shouru_week = childs[j*2+1].innerHTML != ''?childs[j*2+1].innerHTML:0;
                                    judgeupdate = 1;
                                }
                                if(item && item.length != 0 && item[j]){
                                    if(item[j].id){
                                        obj.id = item[j].id;
                                        obj.data_dt = item[j].data_dt;
                                    }
                                }
                                obj.week = this.index;
                            }else if(childs.length > 18 &&childs.length < 26){
                                if(j == 0){
                                    obj.gsv_month = childs[j*3+1].innerHTML != ''?childs[j*3+1].innerHTML:0;
                                    obj.shouru_month = childs[j*3+2].innerHTML != ''?childs[j*3+2].innerHTML:0;
                                    obj.maoli_month = childs[j*3+3].innerHTML != ''?childs[j*3+3].innerHTML:0;
                                    judgeupdate = 1;
                                }else{
                                    obj.gsv_week = childs[j*3+1].innerHTML != ''?childs[j*3+1].innerHTML:0;
                                    obj.shouru_week = childs[j*3+2].innerHTML != ''?childs[j*3+2].innerHTML:0;
                                    obj.maoli_week = childs[j*3+3].innerHTML != ''?childs[j*3+3].innerHTML:0;
                                    judgeupdate = 1;
                                }
                                if(item && item.length != 0 && item[j]){
                                    if(item[j].id){
                                        obj.id = item[j].id;
                                        obj.data_dt = item[j].data_dt;
                                    }
                                }
                                obj.week = this.index;
                            }
                            if(judgeupdate == 1){
                                var params = this.getIdentification();
                                obj.month = (i+1)<10?'0'+(i+1):(i+1);
                                obj.first_format_en = params.yetai;
                                obj.second_format_en = params.type;
                                obj.first_format = params.yetaishu;
                                obj.second_format = params.typeshu;
                                obj.year = this.yearselected;
                                arr.push(obj);
                                judgeupdate = 0;
                            }
                        }

                    }

                    if(this.errornumber == 0 && this.errornumber2 == 0){
                        var params = this.getIdentification();
                        var str = JSON.stringify(arr);

                        this.$http.post(currUrl+'/budgetkeyboarderSave', {bugetReportJson: str}, {emulateJSON: true}).then(function(res){
                            this.$refs.savesuccess.style.display = 'block';
                            var that = this;
                            setTimeout(function(){
                                that.$refs.savesuccess.style.display = 'none';
                            }, 500)

                            if(res.data && res.data.data != '' && res.data.data.length != 0){

                                var list = this.Transformationdata1(res.data.data);

                                this.recorddata = list;
                                var trs = this.$refs.tr;

                                for(var i=0; i<trs.length-1; i++){
                                    var childs = trs[i].children;
                                    var item = list[i];
                                    for(var j=0; j<7; j++){
                                        if(item[j]){
                                            if(childs.length < 18){
                                                if(j ==0){
                                                    childs[j*2+1].innerHTML = item[j].shouru_month?this.fmoney(item[j].shouru_month):'';
                                                    childs[j*2+2].innerHTML = item[j].maoli_month?this.fmoney(item[j].maoli_month):'';
                                                }else{
                                                    childs[j*2+1].innerHTML = item[j].shouru_week?this.fmoney(item[j].shouru_week):'';
                                                    childs[j*2+2].innerHTML = item[j].maoli_week?this.fmoney(item[j].maoli_week):'';
                                                }
                                            }else{
                                                if(j == 0){
                                                    childs[j*3+1].innerHTML = item[j].gsv_month?this.fmoney(item[j].gsv_month):'';
                                                    childs[j*3+2].innerHTML = item[j].shouru_month?this.fmoney(item[j].shouru_month):'';
                                                    childs[j*3+3].innerHTML = item[j].maoli_month?this.fmoney(item[j].maoli_month):'';
                                                }else{
                                                    childs[j*3+1].innerHTML = item[j].gsv_week?this.fmoney(item[j].gsv_week):'';
                                                    childs[j*3+2].innerHTML = item[j].shouru_week?this.fmoney(item[j].shouru_week):'';
                                                    childs[j*3+3].innerHTML = item[j].maoli_week?this.fmoney(item[j].maoli_week):'';
                                                }
                                            }
                                        }
                                    }
                                }
                            }else{
                                this.recorddata = [];
                            }
                        })
                    }

                },
                setmodifyele: function () {
                    var trs = this.$refs.tr;
                    for(var i=0; i<trs.length; i++){
                        var childs = trs[i].children;
                        for(var j=1; j<childs.length; j++){
                            if(i<trs.length-1){
                                if(this.yetaiselected < 5 && this.yetaiselected != 0){
                                    if(j<15){
                                        var inp = document.createElement('input');
                                        inp.value = childs[j].innerHTML;
                                        childs[j].innerHTML = '';
                                        childs[j].appendChild(inp);
                                    }
                                }else if(this.yetaiselected >= 5){
                                    if(j<22){
                                        var inp = document.createElement('input');
                                        inp.value = childs[j].innerHTML;
                                        childs[j].innerHTML = '';
                                        childs[j].appendChild(inp);
                                    }
                                }
                            }
                        }
                    }
                    this.losefocus();
                },
                deletemodify: function () {
                    var trs = this.$refs.tr;
                    for(var i=0; i<trs.length; i++){
                        var childs = trs[i].children;
                        for(var j=1; j<childs.length; j++){
                            this.highlightshow(childs[j], '');
                            if(i<trs.length-1){
                                if(this.yetaiselected < 5 && this.yetaiselected != 0){
                                    if(j<15){
                                        var inp = childs[j].querySelector('input');
                                        childs[j].removeChild(inp);
                                    }
                                }else if(this.yetaiselected >= 5){
                                    if(j<22){
                                        var inp = childs[j].querySelector('input');
                                        childs[j].removeChild(inp);
                                    }
                                }
                            }
                        }
                    }
                    this.getquery();
                },
                losefocus: function () {
                    var that = this;
//                  if(this.yetaiselected != 0){
                    var trs = this.$refs.tr;
                    for(var i=0; i<trs.length; i++){
                        var childs = trs[i].children;
                        for(var j=1; j<childs.length; j++){
                            if(childs[j].children[0]){
                                childs[j].children[0].onblur = function(){
                                    var txt = this.value;

                                    if(txt != ''){
                                        if(txt.indexOf(' ') >= 0 || txt == null){
                                            that.maskshow = 1;
                                            that.errornumber = 1;
                                            that.errorelement = this;
                                            that.highlightshow(this.parentNode, 'red');
                                        }else{
                                            if(!that.isRealNum(txt)){
                                                that.maskshow = 1;
                                                that.errornumber = 1;
                                                that.errorelement = this;

                                                that.highlightshow(this.parentNode, 'red');
                                            }else{
                                                that.highlightshow(this.parentNode, '');
                                                this.value = that.fmoney(txt, 2);
                                                that.tatalvalue(trs, 1);
                                            }
                                        }
                                    }
                                };
                            }
                        }
                    }
//                  }
                },
                tatalvalue: function (trs, panduan) {
                    for(var i=0; i<trs.length; i++){
                        var childs = trs[i].children;
                        var value = 0;
                        var value2 = 0;
                        var value3 = 0;
                        for(var j=1; j<childs.length; j++){
                            if(i < trs.length-1){
                                if(panduan == 1){
                                    if(this.yetaiselected < 5 && this.yetaiselected != 0){
                                        if(j%2 == 1){
                                            if(j == 15){
                                                childs[j].innerHTML = this.judgenullortrue(childs[j].parentNode, 1, 'hang')?this.fmoney(value/100, 2):'';
                                            }else{
                                                value += Math.round(j==1?Number(!isNaN(childs[j].children[0].value)?childs[j].children[0].value*100:0):-(Number(!isNaN(childs[j].children[0].value)?childs[j].children[0].value*100:0)));
                                            }
                                        }
                                        if(j%2 == 0){
                                            if(j == 16){
                                                childs[j].innerHTML = this.judgenullortrue(childs[j].parentNode, 1, 'hang')?this.fmoney(value2/100, 2):'';
                                            }else{
                                                value2 += Math.round(j==2?Number(!isNaN(childs[j].children[0].value)?childs[j].children[0].value*100:0):-(Number(!isNaN(childs[j].children[0].value)?childs[j].children[0].value*100:0)));
                                            }
                                        }
                                    }else if(this.yetaiselected >= 5){
                                        if(j%3 == 1){
                                            if(j == 22){
                                                childs[j].innerHTML = this.judgenullortrue(childs[j].parentNode, 2, 'hang')?this.fmoney(value/100, 2):'';
                                            }else{
                                                value += Math.round(j==1?Number(!isNaN(childs[j].children[0].value)?childs[j].children[0].value*100:0):-(Number(!isNaN(childs[j].children[0].value)?childs[j].children[0].value*100:0)));
                                            }
                                        }
                                        if(j%3 == 2){
                                            if(j == 23){
                                                childs[j].innerHTML = this.judgenullortrue(childs[j].parentNode, 2, 'hang')?this.fmoney(value2/100, 2):'';
                                            }else{
                                                value2 += Math.round(j==2?Number(!isNaN(childs[j].children[0].value)?childs[j].children[0].value*100:0):-(Number(!isNaN(childs[j].children[0].value)?childs[j].children[0].value*100:0)));
                                            }
                                        }
                                        if(j%3 == 0){
                                            if(j == 24){
                                                childs[j].innerHTML = this.judgenullortrue(childs[j].parentNode, 2, 'hang')?this.fmoney(value3/100, 2):'';
                                            }else{
                                                value3 += Math.round(j==3?Number(!isNaN(childs[j].children[0].value)?childs[j].children[0].value*100:0):-(Number(!isNaN(childs[j].children[0].value)?childs[j].children[0].value*100:0)));
                                            }
                                        }
                                    }
                                }else{
                                    if(this.yetaiselected < 5 && this.yetaiselected != 0){
                                        if(j%2 == 1){
                                            if(j == 15){
                                                childs[j].innerHTML = this.judgenullortrue(childs[j].parentNode, 3, 'hang')?this.fmoney(value/100, 2):'';
                                            }else{
                                                value += Math.round(j==1?Number(!isNaN(childs[j].innerHTML)?childs[j].innerHTML*100:0):-(Number(!isNaN(childs[j].innerHTML)?childs[j].innerHTML*100:0)));
                                            }
                                        }
                                        if(j%2 == 0){
                                            if(j == 16){
                                                childs[j].innerHTML = this.judgenullortrue(childs[j].parentNode, 3, 'hang')?this.fmoney(value2/100, 2):'';
                                            }else{
                                                value2 += Math.round(j==2?Number(!isNaN(childs[j].innerHTML)?childs[j].innerHTML*100:0):-(Number(!isNaN(childs[j].innerHTML)?childs[j].innerHTML*100:0)));
                                            }
                                        }
                                    }else if(this.yetaiselected >= 5){
                                        if(j%3 == 1){
                                            if(j == 22){
                                                childs[j].innerHTML = this.judgenullortrue(childs[j].parentNode, 4, 'hang')?this.fmoney(value/100, 2):'';
                                            }else{
                                                value += Math.round(j==1?Number(!isNaN(childs[j].innerHTML)?childs[j].innerHTML*100:0):-(Number(!isNaN(childs[j].innerHTML)?childs[j].innerHTML*100:0)));
                                            }
                                        }
                                        if(j%3 == 2){
                                            if(j == 23){
                                                childs[j].innerHTML = this.judgenullortrue(childs[j].parentNode, 4, 'hang')?this.fmoney(value2/100, 2):'';
                                            }else{
                                                value2 += Math.round(j==2?Number(!isNaN(childs[j].innerHTML)?childs[j].innerHTML*100:0):-(Number(!isNaN(childs[j].innerHTML)?childs[j].innerHTML*100:0)));
                                            }
                                        }
                                        if(j%3 == 0){
                                            if(j == 24){

                                                childs[j].innerHTML = this.judgenullortrue(childs[j].parentNode, 4, 'hang')?this.fmoney(value3/100, 2):'';
                                            }else{
                                                value3 += Math.round(j==3?Number(!isNaN(childs[j].innerHTML)?childs[j].innerHTML*100:0):-(Number(!isNaN(childs[j].innerHTML)?childs[j].innerHTML*100:0)));
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }

                    var child2s = trs[0].children;
                    var lieeles = [];
                    for(var x=1; x<child2s.length; x++){
                        var valuetotal = 0;
                        for(var y=0; y<trs.length; y++){
                            var child3s = trs[y].children;
                            if(y == 12){
                                if(panduan == 1){
                                    child3s[x].innerHTML = this.judgenullortrue(lieeles, 5, 'lie')?this.fmoney(valuetotal, 2):'';
                                }else{
                                    child3s[x].innerHTML = this.judgenullortrue(lieeles, 6, 'lie')?this.fmoney(valuetotal, 2):'';
                                }

                                lieeles = [];
                            }else{
                                lieeles.push(child3s[x]);
                                if(this.yetaiselected < 5 && this.yetaiselected != 0){
                                    if(x>=15){
                                        valuetotal += Number(child3s[x].innerHTML);
                                    }else{
                                        if(panduan == 1){
                                            valuetotal += Number(!isNaN(child3s[x].children[0].value)?child3s[x].children[0].value:0);
                                        }else{
                                            valuetotal += Number(!isNaN(child3s[x].innerHTML)?child3s[x].innerHTML:0);
                                        }
                                    }
                                }else if(this.yetaiselected >= 5 || this.yetaiselected == 0){
                                    if(x>=22){
                                        valuetotal += Number(child3s[x].innerHTML);
                                    }else{
                                        if(panduan == 1){
                                            valuetotal += Number(!isNaN(child3s[x].children[0].value)?child3s[x].children[0].value:0);
                                        }else{
                                            valuetotal += Number(!isNaN(child3s[x].innerHTML)?child3s[x].innerHTML:0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                adaptionwidth: function () {
                    var that = this;
                    var arr = [];
                    setTimeout(function(){
                        var trs = that.$refs.tr;
                        for(var i=0; i<trs.length; i++){
                            var childs = trs[i].children;
                            for(var j=1; j<childs.length; j++){
                                arr.push(parseInt(childs[j].innerHTML?childs[j].innerHTML:0))
                            }
                        }
//                        console.log(arr)
//                        console.log(that.getmaxvalue(arr))
                    }, 500)


                    if(this.yetaiselected < 5 && this.yetaiselected != 0){
                        if(this.transactionselected == 0){
                            return '110%';
                        }else{
                            return '120%';
                        }
                    }else if(this.yetaiselected >= 5){
                        if(this.transactionselected == 0){
                            return '150%';
                        }else{
                            return '180%';
                        }
                    }

                },
                isRealNum: function (val) {
                    // isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
                    if(!isNaN(val)){
                        return true;
                    }else{
                        return false;
                    }
                },
                resetvalue: function () {
                    var trs = this.$refs.tr;
                    if(trs){
                        for(var i=0; i<trs.length; i++){
                            var childs = trs[i].children;
                            for(var j=1; j<childs.length; j++){
                                if(childs[j].innerHTML != ''){
                                    childs[j].innerHTML = '';
                                }
                            }
                        }
                    }
                },
                closetips: function () {
                    this.maskshow = 0;
                    if(this.errornumber == 1){
                        this.errorelement.value = '';
                        this.errorelement.focus();
                        var trs = this.$refs.tr;
                        this.errorelement.parentNode.style.color = '';
                        var childs = this.errorelement.parentNode.parentNode.children;
                        for(var i=1; i<childs.length; i++){
                            if(childs[i].children[0]){
                                childs[i].children[0].style.color = '';
                            }
                        }
                    }
                    this.errornumber = 0;
                    this.errornumber2 = 0;
                },
                Inspectionnotforzero: function () {
                    var trs = this.$refs.tr;
                    for(var i=0; i<trs.length; i++){
                        var childs = trs[i].children;
                        for(var j=1; j<childs.length; j++){
                            if(i<trs.length-1){
                                var txt = childs[j].innerHTML;
                                if(this.yetaiselected < 5 && this.yetaiselected != 0){
                                    if(j == 15){
                                        if(childs[j].innerHTML != 0){
                                            this.maskshow = 1;
                                            this.errornumber2 = 1;
                                            childs[j].style.color = 'red';
                                        }else{
                                            childs[j].style.color = '#fdfdfd';
                                        }
                                    }else if(j == 16){
                                        if(childs[j].innerHTML != 0){
                                            this.maskshow = 1;
                                            this.errornumber2 = 1;
                                            childs[j].style.color = 'red';
                                        }else{
                                            childs[j].style.color = '#fdfdfd';
                                        }
                                    }else if(j<15){
                                        if(txt != ''){
                                            if(!this.isRealNum(txt)){
                                                this.errornumber = 1;
                                                this.highlightshow(childs[j], 'red');
                                            }
                                        }
                                    }
                                }else if(this.yetaiselected >= 5){

                                    if(j == 22){
                                        if(childs[j].innerHTML != 0){
                                            this.maskshow = 1;
                                            this.errornumber2 = 1;
                                            childs[j].style.color = 'red';
                                        }else{
                                            childs[j].style.color = '#fdfdfd';
                                        }
                                    }else if(j == 23){
                                        if(childs[j].innerHTML != 0){
                                            this.maskshow = 1;
                                            this.errornumber2 = 1;
                                            childs[j].style.color = 'red';
                                        }else{
                                            childs[j].style.color = '#fdfdfd';
                                        }
                                    }else if(j == 24){
                                        if(childs[j].innerHTML != 0){
                                            this.maskshow = 1;
                                            this.errornumber2 = 1;
                                            childs[j].style.color = 'red';
                                        }else{
                                            childs[j].style.color = '#fdfdfd';
                                        }
                                    }else if(j<22){
                                        if(txt != ''){
                                            if(!this.isRealNum(txt)){
                                                this.errornumber = 1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                //数值千分位的同时保留两位小数，四舍五入
                fmoney: function(s, n){
                    n = n > 0 && n <= 20 ? n : 2;
                    s = parseFloat((s + '').replace(/[^\d\.-]/g, '')).toFixed(n) + '';
                    var l = s.split('.') [0].split('').reverse(),
                        r = s.split('.') [1];
                    var  t = '';
                    for (var i = 0; i < l.length; i++) {
                        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? '' : '');
                    }
                    var res = t.split('').reverse().join('') + '.' + r;
                    if(res.indexOf(',') == 0){
                        res = res.substring(1);
                    }
                    if(res.indexOf('-,') == 0){
                        res = "-"+res.substring(2);
                    }
                    return res;
                },
                highlightshow: function(tr, color){
                    tr.style.color = color;
                    var inputs = tr.querySelectorAll('input');
                    if(inputs){
                        for(var i=0; i<inputs.length; i++){
                            inputs[i].style.color = color;
                        }
                    }
                },
                //取得业态和交易类型的标识
                getIdentification: function(){
                    var obj = {
                        yetai: '',
                        type: '',
                        yetaishu: '',
                        typeshu: '',
                        typeall: '',
                    }
                    switch(Number(this.yetaiselected)){
                        case 0:
                            this.tatolshow = 1;
                            break;
                        case 1:
                            obj.yetai = '400';
                            obj.yetaishu = '400';
                            switch(Number(this.transactionselected)){
                                case 0:
                                    obj.type = '400HJ';
                                    obj.typeshu = '合计';
                                    obj.typeall = '400LH,400WT';
                                    this.tatolshow = 1;
                                    break;
                                case 1:
                                    obj.type = '400LH';
                                    obj.typeshu = '龙湖';
                                    obj.typeall = '';
                                    this.tatolshow = 0;
                                    break;
                                case 2:
                                    obj.type = '400WT';
                                    obj.typeshu = '外拓';
                                    obj.typeall = '';
                                    this.tatolshow = 0;
                                    break;
                                default:
                                    obj.type = '400HJ';
                                    obj.typeshu = '合计';
                                    obj.typeall = '400LH,400WT';
                                    this.tatolshow = 1;
                                    break;
                            }
                            break;
                        case 2:
                            obj.yetai = 'WYY';
                            obj.yetaishu = '物业云';
                            obj.type = 'WYYWTPQ';
                            obj.typeshu = '外拓-片区';
                            obj.typeall = '';
                            this.tatolshow = 0;
                            break;
                        case 3:
                            obj.yetai = 'WYY';
                            obj.yetaishu = '物业云';
                            obj.type = 'WYYLH';
                            obj.typeshu = '龙湖';
                            obj.typeall = '';
                            this.tatolshow = 0;
                            break;
                        case 4:
                            obj.yetai = 'WLW';
                            obj.yetaishu = '物联网';
                            switch(Number(this.transactionselected)){
                                case 0:
                                    obj.type = 'WLWHJ';
                                    obj.typeshu = '合计';
                                    obj.typeall = 'WLWLH,WLWWTFPQ,WLWWTPQ,WLWDNK';
                                    this.tatolshow = 1;
                                    break;
                                case 1:
                                    obj.type = 'WLWLH';
                                    obj.typeshu = '龙湖';
                                    obj.typeall = '';
                                    this.tatolshow = 0;
                                    break;
                                case 2:
                                    obj.type = 'WLWWTFPQ';
                                    obj.typeshu = '外拓-非片区';
                                    obj.typeall = '';
                                    this.tatolshow = 0;
                                    break;
                                case 3:
                                    obj.type = 'WLWWTPQ';
                                    obj.typeshu = '外拓-片区';
                                    obj.typeall = '';
                                    this.tatolshow = 0;
                                    break;
                                case 4:
                                    obj.type = 'WLWDNK';
                                    obj.typeshu = '狄耐克';
                                    obj.typeall = '';
                                    this.tatolshow = 0;
                                    break;
                                default:
                                    obj.type = 'WLWHJ';
                                    obj.typeshu = '合计';
                                    obj.typeall = 'WLWLH,WLWWTFPQ,WLWWTPQ,WLWDNK';
                                    this.tatolshow = 1;
                                    break;
                            }
                            break;
                        case 5:
                            obj.yetai = 'SHFW';
                            obj.yetaishu = '生活服务';
                            switch(Number(this.transactionselected)){
                                case 0:
                                    obj.type = 'SHFWHJ';
                                    obj.typeshu = '合计';
                                    obj.typeall = 'SHFWLH,SHFWWT,SHFWZQG';
                                    this.tatolshow = 1;
                                    break;
                                case 1:
                                    obj.type = 'SHFWLH';
                                    obj.typeshu = '龙湖';
                                    obj.typeall = '';
                                    this.tatolshow = 0;
                                    break;
                                case 2:
                                    obj.type = 'SHFWWT';
                                    obj.typeshu = '外拓';
                                    obj.typeall = '';
                                    this.tatolshow = 0;
                                    break;
                                case 3:
                                    obj.type = 'SHFWZQG';
                                    obj.typeshu = '周期购';
                                    obj.typeall = '';
                                    this.tatolshow = 0;
                                    break;
                                default:
                                    obj.type = 'SHFWHJ';
                                    obj.typeshu = '合计';
                                    obj.typeall = 'SHFWLH,SHFWWT,SHFWZQG';
                                    this.tatolshow = 1;
                                    break;
                            }
                            break;
                        default:
//                            obj.yetai = 'QB';
//                            obj.yetaishu = '全部';
//                            obj.typeall = '';
//                            switch(Number(this.transactionselected)){
//                                case 0:
//                                    obj.type = 'QBQB';
//                                    obj.typeshu = '全部';
//                                    break;
//                                case 1:
//                                    obj.type = 'QBLH';
//                                    obj.typeshu = '龙湖';
//                                    break;
//                                case 2:
//                                    obj.type = 'QBWT';
//                                    obj.typeshu = '外拓';
//                                    break;
//                                default:
//                                    obj.type = 'QBQB';
//                                    obj.typeshu = '全部';
//                                    break;
//                            }
                            break;
                    };

                    return obj;
                },
                //判断本行或本列的数据为不为空
                judgenullortrue: function(eles, type, word){
                    var flg = false;
                    var flg1 = false;
                    if(word == 'hang'){
                        var childs = eles.children;
                        for(var i=1; i<childs.length; i++){
                            if(type == 1){
                                if(i<15){
                                    if(childs[i].children[0].value != ''){
                                        flg = true;
                                    }
                                }
                            }else if(type == 2){
                                if(i<22){
                                    if(childs[i].children[0].value != ''){
                                        flg = true;
                                    }
                                }
                            }else if(type == 3){
                                if(i<15){
                                    if(childs[i].innerHTML != ''){
                                        flg = true;
                                    }
                                }
                            }else if(type == 4){
                                if(i<22){
                                    if(childs[i].innerHTML != ''){
                                        flg = true;
                                    }
                                }
                            }
                        }
                        return flg;
                    }else{
                        for(var i=0; i<eles.length; i++){
                            if(type == 5){
                                if(eles[i].innerHTML != ''){
                                    flg1 = true;
                                }
                            }else if(type == 6){
                                if(eles[i].innerHTML != ''){
                                    flg1 = true;
                                }
                            }

                        }
                        return flg1;
                    }

                },
                // 提示框可被拖拽,鼠标按下的时候
                tipsdragordrop: function(e){
                    var e = e ? e: window.event;
                    e.stopPropagation();
                    var x1 = e.clientX;
                    var y1  = e.clientY;
                    var that = this;
                    var flg = true;
                    var left1 = parseInt(that.getCss(that.$refs.tips, 'left'));
                    var top1 = parseInt(that.getCss(that.$refs.tips, 'top'));
                    var w= parseInt(that.getCss(that.$refs.mask, 'width')) - parseInt(that.getCss(that.$refs.tips, 'width'));
                    var h = parseInt(that.getCss(that.$refs.mask, 'height')) - parseInt(that.getCss(that.$refs.tips, 'height'));;
                    document.onmousemove = function(e){
                        var e = e ? e: window.event;
                        if(flg){
                            var x = e.clientX;
                            var y = e.clientY;
                            var disx = x - x1 + left1;
                            var disy = y - y1 + top1;
                            if(disx < 0){
                                disx = 0;
                            }
                            if(disx > w){
                                disx = w;
                            }
                            if(disy < 0){
                                disy = 0;
                            }
                            if(disy > h){
                                disy = h;
                            }
                            that.$refs.tips.style.left = disx + 'px';
                            that.$refs.tips.style.top = disy + 'px';
                        }
                    };
                    document.onmouseup = function(e){
                        var e = e ? e: window.event;
                        e.stopPropagation();
                        flg = false;
                    }
                },
                //获取相关CSS属性
                getCss: function(o,key){
                    return o.currentStyle? o.currentStyle[key] : document.defaultView.getComputedStyle(o,false)[key];
                },
                //点击遮罩层无效的效果
                dragorgropnoeffect: function(e){
                    var e = e ? e: window.event;
                    e.stopPropagation();
                    return false;
                },
                //获取数组的最大值
                getmaxvalue: function(arr){
                    var max = Math.max.apply(null,arr);
                    return max;
                },
                //接口的代理
                //代理走的方式
                getlocation: function(){
                    if(location.host === 'qddata.qdingnet.com'){
                        return location.origin + '/getHttpData?url=http://yushanfang.bigdata.qdingnet.com/'
                    }else if(location.host === 'qa-qddata.bigdata.qdingnet.com'){
                        return location.origin + '/getHttpData?url=http://qa-yushanfang.bigdata.qdingnet.com/'
                    }else{
                        return 'http://dev-qddata.bigdata.qdingnet.com/getHttpData?url=http://dev-yushanfang.bigdata.qdingnet.com/'
                    }
                }
            },
            created: function () {
//                this.getqueryall()
            },
            mounted () {

                this.screenHeight = this.getCss(this.$refs.tr[2].children[3], 'height')
                const that = this
                window.onresize = () => {
                    return (() => {
                        window.screenHeight = this.getCss(this.$refs.tr[2].children[3], 'height')
                    that.screenHeight = window.screenHeight
                })()
                }
            },
            watch: {
                screenHeight: function (val) {
                    this.screenHeight = val;
                }
            }
        })


        Vue.http.options.emulateHTTP = true;
        Vue.http.options.headers = {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        }


    </script>



</body>
</html>
