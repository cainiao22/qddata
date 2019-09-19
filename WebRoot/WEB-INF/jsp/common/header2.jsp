<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<header class="topbar">
    <style>
        a.logout:hover{border-bottom: 2px solid #fdfdfebd; color:#fdfdfebd;}
        .logout{text-align:center;color:#fdfdfebd;margin-left: 8px;}
        #welcome{font-family: 'themify';font-style: normal;font-size: 17px;font-weight: normal;color: #fdfdfe;speak: none;text-transform: none;}
        .userMsg{
            position: absolute;
            list-style: none;
            /*background: url("../../../assets/images/user/u6.png")38px 0px no-repeat;*/
            background-size: contain;
            right: -50px;
            top: -11px;
            width: 190px;
            height: 80px;
        }
       .topbar .dropdown-menu{
            padding: 1.5rem 0;
           /* border-color: rgba(56, 63, 72, 0.1);*/
        }

        .topbar .top-navbar .navbar-nav > .nav-item.show {
            background: rgba(1, 120, 178, 0.05);
        }
        .userMsg li{line-height:30px;}
        .userMsg li a{color:white;}
        .userMsg li i{margin-right: 5px;}
        .userMsg li a:hover {color:#01c0c8;}
        #updatePasswordBtn{  display: none;position: absolute;top: 10px; right: 84px;  }
        #logOutBtn{ position: absolute;  right: 84px; bottom: 30px ; }
    </style>
    <nav class="navbar top-navbar navbar-expand-md navbar-light">
        <!-- Logo -->
        <div class="navbar-header">
            <a class="navbar-brand" href="./index">
                <!-- Logo icon -->
                <b>
                    <!--You can put here icon as well // <i class="wi wi-sunset"></i> //-->
                    <!-- Dark Logo icon -->
                    <img src="./assets/images/logo-icon.png" alt="homepage" class="dark-logo"/>
                    <!-- Light Logo icon -->
                    <img src="./assets/images/logo-light-icon.png" alt="homepage" class="light-logo"/>
                </b>
                <!--End Logo icon -->
                <!-- Logo text -->
                <span>
                         <!-- dark Logo text -->
                         <img src="./assets/images/logo-text.png" alt="homepage" class="dark-logo"/>
                    <!-- Light Logo text -->
                         <img src="./assets/images/logo-light-text2.png" class="light-logo" alt="homepage"/>
                    </span>
            </a>
        </div>
        <!-- End Logo -->
        <div class="navbar-collapse">
            <!-- toggle and nav items -->
            <ul class="navbar-nav mr-auto mt-md-0 ">
                <!-- This is  -->
                <li class="nav-item"><a class="nav-link nav-toggler hidden-md-up text-muted waves-effect waves-dark"
                                        href="javascript:void(0)"><i class="ti-menu"></i></a></li>
                <li class="nav-item"><a
                        class="nav-link sidebartoggler hidden-sm-down text-muted waves-effect waves-dark"
                        href="javascript:void(0)"><i class="icon-arrow-left-circle"></i></a></li>
            </ul>
            <!-- User profile -->
            <ul class="navbar-nav my-lg-0">
                <li class="nav-item dropdown">
                    <i id="welcome"></i>
                    <a class="nav-link dropdown-toggle text-muted waves-effect waves-dark ti-user" href="#"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="color: #fdfdfebd">
                        <%--<a  id="welcome" style="color: #fff;" href="#"><a class="logout" href='logout' s>退出</a>--%>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right animated flipInY">
                        <ul class="userMsg">
                            <%--<li role="separator" class="divider"></li>
                            <li><a href="javascript:void(0)"><i id="welcome" class="ti-user"></i></a></li>
                            <li role="separator" class="divider"></li>--%>
                            <%--<li style="display: none;position: absolute;top: 23px; right: 50px;" id="updatePasswordBtn" ><a href="updatePassword"><i class="fa fa-edit"></i>修改密码</a></li>
                            <li ><a href="logout" style="position: absolute;bottom: 18px; right: 50px;" id="logOutBtn"><i class="fa fa-power-off"></i>退出登录</a></li>--%>
                            <li id="updatePasswordBtn" ><a href="updatePassword"><i class="fa fa-edit"></i>修改密码</a></li>
                            <li ><a href="logout" id="logOutBtn"><i class="fa fa-power-off"></i>退出登录</a></li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</header>
